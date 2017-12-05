package io.harry.zealotboot.service;

import io.harry.zealotboot.service.exception.ServiceException;

import java.io.InputStream;
import java.net.URL;

public interface AjaeGagStorageService {
    URL uploadImage(InputStream inputStream) throws ServiceException;
}
