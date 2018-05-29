package com.dpp.entity;

import java.math.BigInteger;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderSearchRequest {

  private Integer region;

  private Double minInitialPrice;

  private Double maxInitialPrice;

  private Integer purchaseCode;

  private Integer lawCode;

  private List<Integer> requirements;

  private BigInteger inn;

}
