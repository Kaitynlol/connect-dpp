package com.dpp.services;

import com.dpp.entity.OrderSearchRequest;
import com.dpp.entity.OrderDtoResponse;
import com.dpp.entity.OrderEntity;
import java.util.List;

public interface DataProcessingService {

  List<OrderDtoResponse> dataPreparationExecute(List<OrderEntity> list);

  List<OrderDtoResponse> getAvailableOrders();

  List<OrderDtoResponse> getAvailableOrders(OrderSearchRequest request);
}
