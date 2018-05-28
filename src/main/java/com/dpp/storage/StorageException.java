package com.dpp.storage;

public class StorageException extends RuntimeException {

  /**
  * 
  */
  private static final long serialVersionUID = 3061724189602921083L;

  public StorageException(String message) {
    super(message);
  }

  public StorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
