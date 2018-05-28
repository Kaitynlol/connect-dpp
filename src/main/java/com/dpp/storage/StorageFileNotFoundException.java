package com.dpp.storage;

public class StorageFileNotFoundException extends StorageException {

  /**
  * 
  */
  private static final long serialVersionUID = 8124901390611492263L;

  public StorageFileNotFoundException(String message) {
    super(message);
  }

  public StorageFileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
