package com.dpp.services;

import com.dpp.entity.OrderDtoResponse;
import com.dpp.entity.OrderEntity;
import com.dpp.storage.StorageException;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataLoaderServiceImpl implements DataLoaderService {

  @Autowired
  private DataProcessingService processingService;

  @Override
  public List<OrderDtoResponse> dataCsvLoadInDataBase(String fileName) throws StorageException {
    CsvToBean<OrderEntity> csv = new CsvToBean<OrderEntity>();
    try (FileReader flr = new FileReader(fileName)) {
      CSVReader csvReader = new CSVReader(flr);
      // Set column mapping strategy
      List<OrderEntity> list = csv.parse(setColumMapping(), csvReader);
      list.remove(0);
      return processingService.dataPreparationExecute(list);
    } catch (IOException e) {
      throw new StorageException(e.getMessage());
    }
  }


  private static ColumnPositionMappingStrategy<OrderEntity> setColumMapping() {
    ColumnPositionMappingStrategy<OrderEntity> strategy =
        new ColumnPositionMappingStrategy<OrderEntity>();
    strategy.setType(OrderEntity.class);
    Class<OrderEntity> aClass = OrderEntity.class;
    Field[] fields = aClass.getDeclaredFields();
    String[] columns = new String[fields.length];
    for (int i = 0; i < fields.length; i++) {
      columns[i] = fields[i].getName();
    }
    strategy.setColumnMapping(columns);
    return strategy;
  }
}
