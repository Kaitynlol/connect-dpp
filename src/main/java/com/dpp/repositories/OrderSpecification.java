package com.dpp.repositories;

import com.dpp.entity.OrderDtoResponse;
import com.dpp.entity.OrderDtoResponse_;
import com.dpp.entity.OrderSearchRequest;
import com.google.common.base.Strings;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification implements Specification<OrderDtoResponse> {

  private OrderSearchRequest criteria;

  public OrderSpecification(OrderSearchRequest search) {
    this.criteria = search;
  }

  @Override
  public Predicate toPredicate(Root<OrderDtoResponse> root, CriteriaQuery<?> query,
      CriteriaBuilder cb) {
    final List<Predicate> predicates = new ArrayList<Predicate>();

    if (criteria.getRegion() != null && criteria.getRegion() != 0) {
      predicates.add(cb.equal(root.get(OrderDtoResponse_.region), criteria.getRegion()));
    }

    if (criteria.getMinInitialPrice() != null && criteria.getMaxInitialPrice() != null) {
      predicates.add(cb.between(root.get(OrderDtoResponse_.initialPrice),
          criteria.getMinInitialPrice(), criteria.getMaxInitialPrice()));

    }
    if (criteria.getPurchaseCode() != null && criteria.getPurchaseCode() != 0) {
      predicates
          .add(cb.equal(root.get(OrderDtoResponse_.purchaseCode), criteria.getPurchaseCode()));
    }

    if (criteria.getLawCode() != null
        && BigDecimal.ZERO.compareTo(BigDecimal.valueOf(criteria.getLawCode())) != 0) {
      predicates.add(cb.equal(root.get(OrderDtoResponse_.lawCode), criteria.getLawCode()));
    }

    if (criteria.getInn() != null && criteria.getInn() != 0) {
      predicates.add(cb.equal(root.get(OrderDtoResponse_.inn), criteria.getInn()));
    }

    if (criteria.getRequirements() != null && !criteria.getRequirements().isEmpty()) {
      Predicate requirementsPredicate = cb.in(root.get(OrderDtoResponse_.requirements));
      requirementsPredicate.in(criteria.getRequirements());
      predicates
          .add(requirementsPredicate);
    }
    return cb.and(predicates.toArray(new Predicate[predicates.size()]));
  }
}
