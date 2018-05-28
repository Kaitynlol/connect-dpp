package com.dpp.services;

import com.dpp.entity.OrderDtoResponse;
import com.dpp.storage.StorageException;
import java.util.List;

public interface DataLoaderService {

  List<OrderDtoResponse> dataCsvLoadInDataBase(String file) throws StorageException;

}
