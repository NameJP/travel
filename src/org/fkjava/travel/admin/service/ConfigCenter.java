package org.fkjava.travel.admin.service;

import org.fkjava.travel.admin.domain.SystemSettings;
import org.fkjava.travel.core.vo.Result;
import org.springframework.data.domain.Page;

public interface ConfigCenter {

	Result<SystemSettings> saveSystemSettings(SystemSettings settings);

	Page<SystemSettings> findSystemSettings(String key, Integer number);

	void deleteSystemSettingsById(String id);
}
