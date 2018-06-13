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
import javax.transaction.Transactional;
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

  @Autowired
  private ResponseService responseService;


  private final EnhancedRandom enhancedRandom =
      EnhancedRandomBuilder.aNewEnhancedRandomBuilder().maxCollectionSize(15)
          .build();
  private Transaction transaction;


  @Override
  @Transactional
  public List<OrderDtoResponse> dataPreparationExecute(List<OrderEntity> list) {
    List<OrderDtoResponse> preparedList = new ArrayList<>();
    AtomicInteger countErrors = new AtomicInteger();
    list.parallelStream().forEach(e -> {
      if (e.getCode() != null && !(e.getCode()).equalsIgnoreCase("'\\uFEFF'")) {
        OrderDtoResponse value;
        try {
          value = OrderDtoResponse.newBuilder()
              .setProduct(e.getObjectOfOrder())
              .withLawCode(e.getFz()).setRegion(e.getInn(), e.getAddress())
              .setFinalPrice(e.getBet(), e.getTradeResults())
              .setInitialPrice(e.getStartingPrice())
              .setRequirements(e.getRequirementsForMembers())
              .withPurchaseCode(e.getProcedureType()).setInn(e.getInn())
              .build();
        } catch (Exception exc) {
          log.error(exc.getMessage()+ "for entry: "+e.getCode());
          countErrors.getAndIncrement();
          value = null;
        }
        if (value != null) {
          preparedList.add(value);
        }
      }
    });
    responseService
        .setInfo(new InfoDto(String.valueOf(preparedList.size()), countErrors.toString()));
    orderRepository.save(preparedList);
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
