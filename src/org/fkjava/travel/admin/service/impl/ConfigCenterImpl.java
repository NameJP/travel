package org.fkjava.travel.admin.service.impl;

import org.fkjava.travel.admin.dao.SystemSettingsDao;
import org.fkjava.travel.admin.domain.SystemSettings;
import org.fkjava.travel.admin.service.ConfigCenter;
import org.fkjava.travel.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ConfigCenterImpl implements ConfigCenter {

	@Autowired
	private SystemSettingsDao systemSettingsDao;

	@Override
	@Transactional
	public Result<SystemSettings> saveSystemSettings(SystemSettings settings) {
		if (StringUtils.isEmpty(settings.getId())) {
			settings.setId(null);
		}

		Result<SystemSettings> result;

		SystemSettings old = this.systemSettingsDao.findByKey(settings.getKey());
		if (settings.getId() == null) {
			if (old == null) {
				this.systemSettingsDao.save(settings);
				result = Result.of(Result.STATUS_OK, "新增系统参数成功");
			} else {
				result = Result.of(Result.STATUS_ERROR, "新增系统参数失败，key已经被其他参数占用！");
			}
		} else {
			if (settings.getId().equals(old.getId())) {
				this.systemSettingsDao.save(settings);
				result = Result.of(Result.STATUS_OK, "修改系统参数成功");
			} else {
				result = Result.of(Result.STATUS_ERROR, "修改系统参数失败，key已经被其他参数占用！");
			}
		}

		return result;
	}

	@Override
	public Page<SystemSettings> findSystemSettings(String key, Integer number) {
		Pageable pageable = PageRequest.of(number, 8);
		Page<SystemSettings> page;
		if (key == null) {
			page = this.systemSettingsDao.findAll(pageable);
		} else {
			// 模糊查询，通常会使用搜索引擎(Lucene、Solr)来实现
			key = "%" + key + "%";
			page = this.systemSettingsDao.findByKeyLike(pageable);
		}
		return page;
	}

	@Override
	public void deleteSystemSettingsById(String id) {
		this.systemSettingsDao.deleteById(id);
	}
}
