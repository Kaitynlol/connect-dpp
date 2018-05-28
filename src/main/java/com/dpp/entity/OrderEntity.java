package com.dpp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
  /** The code. */
  @Id
  private String code;

  private String idOfTrade;

  private String bet;

  /** The trade results. */
  private String tradeResults;

  /** The address. */
  private String address;

  /** The starting price. */
  private String startingPrice;

  private String inn;

  private String fz;

  private String objectOfOrder;


  private String registery;

  private String requirementsForMembers;

}
