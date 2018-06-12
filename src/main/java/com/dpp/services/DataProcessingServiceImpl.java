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

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
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
    public Long dataPreparationExecute(List<OrderEntity> list) {
        List<OrderDtoResponse> preparedList = new ArrayList<>();
        AtomicInteger countErrors = new AtomicInteger();
        Observable<OrderEntity> entityObservable = Observable.fromIterable(list);

        entityObservable.flatMap(e -> Observable.just(e).subscribeOn(Schedulers.computation()).onExceptionResumeNext(Observable.empty()).map(b -> matureEntity(b)))
                .subscribe(response -> persistEntity(response),
                        error -> countErrors.getAndIncrement());
        Long count = Long.valueOf(orderRepository.count());
        responseService
                .setInfo(new InfoDto(String.valueOf(count), countErrors.toString()));
        return Long.valueOf(orderRepository.count());
    }

    @Transactional
    void persistEntity(OrderDtoResponse response) {
        log.info("Subscriber got " +
                response.getProduct() + " on  " +
                Thread.currentThread().getName());
        if (!response.isError()) {
            orderRepository.save(response);
        }
    }

    private OrderDtoResponse matureEntity(OrderEntity e) {
        OrderDtoResponse value = new OrderDtoResponse();
        if (e.getCode() != null && !(e.getCode()).equalsIgnoreCase("'\\uFEFF'")) {

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
                log.error(exc.getMessage() + "for entry: " + e.getCode());
                value.setError(true);
            }
        }
        return value;
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
