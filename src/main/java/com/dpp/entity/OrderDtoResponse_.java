package com.dpp.entity;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderDtoResponse.class)
public abstract class OrderDtoResponse_ {

	public static volatile SingularAttribute<OrderDtoResponse, String> product;
	public static volatile SingularAttribute<OrderDtoResponse, Integer> purchaseCode;
	public static volatile SingularAttribute<OrderDtoResponse, Integer> requirements;
	public static volatile SingularAttribute<OrderDtoResponse, Integer> code;
	public static volatile SingularAttribute<OrderDtoResponse, Double> initialPrice;
	public static volatile SingularAttribute<OrderDtoResponse, BigInteger> inn;
	public static volatile SingularAttribute<OrderDtoResponse, Double> finalPrice;
	public static volatile SingularAttribute<OrderDtoResponse, Integer> lawCode;
	public static volatile SingularAttribute<OrderDtoResponse, Integer> region;

}

