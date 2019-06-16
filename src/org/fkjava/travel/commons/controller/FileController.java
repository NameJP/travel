package org.fkjava.travel.commons.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.fkjava.travel.commons.domain.FileInfo;
import org.fkjava.travel.commons.service.FileService;
import org.fkjava.travel.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
@RequestMapping("file")
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping
	@ResponseBody
	public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {

		// 把上传之后得到的文件信息的ID，作为attachment返回给浏览器！
		String name = file.getOriginalFilename();// 获取原始文件名
		String contentType = file.getContentType();
		long contentLength = file.getSize();
		try (InputStream inputStream = file.getInputStream()) {// 获取文件内容
			Result<String> result = this.fileService.upload(name, contentType, contentLength, inputStream);
			return result;
		}
	}

	@PostMapping("wang")
	@ResponseBody
	public WangResult uploadWangEditor(@RequestParam("file") MultipartFile file, WebRequest request) throws IOException {

		// 把上传之后得到的文件信息的ID，作为attachment返回给浏览器！
		String name = file.getOriginalFilename();// 获取原始文件名
		String contentType = file.getContentType();
		long contentLength = file.getSize();
		try (InputStream inputStream = file.getInputStream()) {// 获取文件内容
			Result<String> result = this.fileService.upload(name, contentType, contentLength, inputStream);
			WangResult wr = new WangResult();
			wr.getData().add(request.getContextPath() + "/commons/file/" + result.getAttachment());
			
			return wr;
		}
	}

	private static class WangResult {
		// 错误代码
		private int errno = 0;
		// 图片的下载地址
		private List<String> data = new LinkedList<>();

		public int getErrno() {
			return errno;
		}

		public void setErrno(int errno) {
			this.errno = errno;
		}

		public List<String> getData() {
			return data;
		}

		public void setData(List<String> data) {
			this.data = data;
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<StreamingResponseBody> download(@PathVariable("id") String id) {
		FileInfo info = this.fileService.getFileInfo(id);

		BodyBuilder builder = ResponseEntity.ok();// HTTP 200，表示请求成功
		builder.contentLength(info.getContentLength());// 文件大小
		builder.contentType(MediaType.valueOf(info.getContentType()));// 文件类型
		// 内容处置方式，attachment表示附件，直接访问URL的时候会弹出保存窗口，filename指定文件名
		// 注意：不同的浏览器，filename的编码方式不同，需要根据不同的浏览器进行判断，目前不考虑
		builder.header("Content-Disposition", "attachment;filename=" + info.getName());

		StreamingResponseBody body = (out) -> {
			try (InputStream in = this.fileService.getFileContent(info)) {
				// 流复制，把in的内容复制到out里面
				byte[] buf = new byte[1024];// 桶、缓冲
				for (int len = in.read(buf); // 1.读取一次，返回读取的字节数量
						len != -1; // 2.数量不为-1，表示有数据
						len = in.read(buf)// 4.再读取一次
				) {
					// 3.读取到数据以后，把数据写入输出流
					out.write(buf, 0, len);
				}
			}
		};

		// 返回响应对象
		ResponseEntity<StreamingResponseBody> entity = builder.body(body);
		return entity;
	}
}
