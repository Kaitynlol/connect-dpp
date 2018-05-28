package com.dpp.repositories;

import com.dpp.entity.OrderDtoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDtoResponse, String>,
    JpaSpecificationExecutor<OrderDtoResponse> {

}
