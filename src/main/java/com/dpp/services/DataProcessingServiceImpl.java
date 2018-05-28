package com.dpp.services;

import com.dpp.entity.OrderSearchRequest;
import com.dpp.entity.OrderDtoResponse;
import com.dpp.entity.OrderEntity;
import com.dpp.repositories.OrderRepository;
import com.dpp.repositories.OrderSpecification;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataProcessingServiceImpl implements DataProcessingService {

  @Autowired
  private OrderRepository orderRepository;


  private final EnhancedRandom enhancedRandom =
      EnhancedRandomBuilder.aNewEnhancedRandomBuilder().maxCollectionSize(15)
          .build();
  private Transaction transaction;

  @Override
  public List<OrderDtoResponse> dataPreparationExecute(List<OrderEntity> list) {
    List<OrderDtoResponse> preparedList = new ArrayList<>();
    AtomicInteger countErrors = new AtomicInteger();
    list.forEach(e -> {
      if (e.getCode() != null && !(e.getCode()).equalsIgnoreCase("'\\uFEFF'")) {
        OrderDtoResponse value;
        try {
          value = OrderDtoResponse.newBuilder().setCode(e.getCode())
              .setProduct(e.getIdOfTrade())
              .withLawCode(e.getFz()).setRegion(e.getInn())
              .setFinalPrice(e.getBet(), e.getTradeResults())
              .setInitialPrice(e.getStartingPrice())
              .setRequirements(e.getRequirementsForMembers())
              .withPurchaseCode(e.getObjectOfOrder()).setInn(e.getInn())
              .build();
        } catch (Exception exc) {
          log.error(exc.getMessage());
          countErrors.getAndIncrement();
          value = null;
        }
        if (value != null) {
          log.debug(value.toString());
          preparedList.add(orderRepository.save(value));
        }

      }
      log.info("==============================================");
      log.info("Записей подготовлено: "+preparedList.size());
      log.info("Колличество ошибок: "+countErrors);
      log.info("==============================================");

    });
    return preparedList;
  }

  @Override
  public List<OrderDtoResponse> getAvailableOrders() {
    return orderRepository.findAll();
  }

  @Override
  public List<OrderDtoResponse> getAvailableOrders(OrderSearchRequest request) {
    OrderSpecification spec = new OrderSpecification(request);
    return orderRepository.findAll(spec);
  }

  @Data
  @AllArgsConstructor
  private static class Transaction {

    public List<OrderDtoResponse> response;
  }
}
