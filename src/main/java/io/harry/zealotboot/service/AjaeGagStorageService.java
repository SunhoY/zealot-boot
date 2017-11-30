package io.harry.zealotboot.service;

import io.harry.zealotboot.service.exception.ServiceException;

import java.io.InputStream;

public interface AjaeGagStorageService {
    void uploadImage(InputStream inputStream, String fileName) throws ServiceException;
}
