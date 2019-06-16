package org.fkjava.travel.commons.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fkjava.travel.admin.dao.SystemSettingsDao;
import org.fkjava.travel.admin.domain.SystemSettings;
import org.fkjava.travel.commons.dao.FileInfoDao;
import org.fkjava.travel.commons.domain.FileInfo;
import org.fkjava.travel.commons.service.FileService;
import org.fkjava.travel.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalDiskFileService implements FileService {
	// 保存文件的目录对应的参数的KEY
	private final String dirKey = "storage.file.dir";
	// 保存文件的目录，dir可以写死，也可以通过SystemSettingsDao对象来查询！
	private String dir = null;
	private Logger log = LogManager.getLogger(LocalDiskFileService.class);

	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private SystemSettingsDao systemSettingsDao;

	@Override
	@Transactional
	public Result<String> upload(String name, String contentType, long contentLength, InputStream inputStream) {
		// 1.生成随机文件名
		String randomFileName = UUID.randomUUID().toString();

		// 2.创建文件信息对象
		FileInfo info = new FileInfo();

		// 3.把文件信息封装到对象中，包括文件原始名称、随机文件名、文件类型、文件大小
		info.setContentLength(contentLength);
		info.setContentType(contentType);
		info.setName(name);
		info.setRandomFileName(randomFileName);

		// 4.保存文件信息到数据库
		info = this.fileInfoDao.save(info);

		String dir = getDir();

		// 得到文件存储的目标路径
		Path target = Paths.get(dir, randomFileName);
		try {
			Files.copy(inputStream, target);
		} catch (IOException e) {
			throw new RuntimeException("保存文件出现异常：" + e.getMessage(), e);
		}

		// 没有出现异常，就返回上传成功，并把文件的id返回给浏览器！
		return Result.of(Result.STATUS_OK, "文件上传成功", info.getId());
	}

	private String getDir() {
		// 保存文件内容到硬盘
		// 同步是为了保证多个用户同时上传的时候，线程的安全性。
		synchronized (dirKey) {
			// 这个判断是为了避免重复读取参数，修改参数以后，需要重启程序才能生效
			if (dir == null) {
				// 根据KEY获取系统参数
				SystemSettings settings = this.systemSettingsDao.findByKey(dirKey);
				if (settings != null) {
					dir = settings.getValue();
				} else {
					log.warn("没有找到 {} 参数，使用默认路径保存文件！", dirKey);
					// 没有参数，可以考虑选择默认参数，或者抛出异常
					dir = "/home/working/var/tmp";
				}
			}
		}
		return dir;
	}

	public FileInfo getFileInfo(String id) {
		// Java 8的流式编程方式
		return this.fileInfoDao.findById(id)// 根据id查找对象
				.orElseThrow(() -> {// 如果没有找到对象，抛出异常
					return new RuntimeException("文件信息不存在");
				});
	}

	@Override
	public InputStream getFileContent(FileInfo info) throws IOException {
		String dir = this.getDir();
		File file = new File(dir, info.getRandomFileName());
		FileInputStream in = new FileInputStream(file);
		return in;
	}
}
