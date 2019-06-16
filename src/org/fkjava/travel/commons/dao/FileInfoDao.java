package org.fkjava.travel.commons.dao;

import org.fkjava.travel.commons.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoDao extends JpaRepository<FileInfo, String> {

}
