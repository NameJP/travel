package org.fkjava.travel.commons.service;

import java.io.IOException;
import java.io.InputStream;

import org.fkjava.travel.commons.domain.FileInfo;
import org.fkjava.travel.core.vo.Result;

public interface FileService {

	Result<String> upload(String name, String contentType, long contentLength, InputStream inputStream);

	FileInfo getFileInfo(String id);

	InputStream getFileContent(FileInfo info) throws IOException;

}
