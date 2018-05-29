package com.dpp.controllers;

import com.dpp.entity.OrderSearchRequest;
import com.dpp.entity.OrderDtoResponse;
import com.dpp.services.DataProcessingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDataController {

  @Autowired
  private DataProcessingService service;


  @RequestMapping(value = "/getAll",
      method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Handle UI to Service Request")
  public List<OrderDtoResponse> getAllAvailableOrders() throws IOException {
    return service.getAvailableOrders();
  }

  @RequestMapping(value = "/getByFilter",
      method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Handle UI to Service Request")
  public List<OrderDtoResponse> getAvailableOrders_new(
      @ApiParam(value = "region", required = false
      ) @RequestParam(required = false) Integer region,
      @ApiParam(value = "min_initial_price", required = false
      ) @RequestParam(required = false) Double minInitialPrice,
      @ApiParam(value = "max_initial_price", required = false
      ) @RequestParam(required = false) Double maxInitialPrice,
      @ApiParam(value = "purchaseCode", required = false
      ) @RequestParam(required = false) Integer purchaseCode,
      @ApiParam(value = "lawCode", required = false
      ) @RequestParam(required = false) Integer lawCode,
      @ApiParam(value = "requirements", required = false
      ) @RequestParam(required = false) List<Integer> requirements,
      @ApiParam(value = "inn", required = false
      ) @RequestParam(required = false) BigInteger inn)
      throws IOException {
    OrderSearchRequest request = new OrderSearchRequest(region, minInitialPrice, maxInitialPrice,
        purchaseCode, lawCode, requirements, inn);
    return service.getAvailableOrders(request);
  }


}
