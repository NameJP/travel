package org.fkjava.travel.admin.dao;

import org.fkjava.travel.admin.domain.SystemSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSettingsDao extends JpaRepository<SystemSettings, String> {

	SystemSettings findByKey(String key);

	Page<SystemSettings> findByKeyLike(Pageable pageable);
}
