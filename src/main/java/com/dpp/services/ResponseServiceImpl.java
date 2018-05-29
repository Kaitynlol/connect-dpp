package com.dpp.services;

import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl implements ResponseService {

  private InfoDto response;

  @Override
  public void setInfo(InfoDto info) {
    response = info;

  }

  @Override
  public InfoDto getInfo() {
    if (response != null) {
      return response;
    } else {
      return new InfoDto("None", "None");
    }
  }
}
