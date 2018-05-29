package com.dpp.services;

import com.dpp.storage.StorageException;

public interface DataLoaderService {

  Long dataCsvLoadInDataBase(String file) throws StorageException;

}
