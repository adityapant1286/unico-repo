package com.serviceapi.service;

import com.serviceapi.model.ApiMsg;

import java.util.List;

public interface ApiMsgService {

    void save(ApiMsg apiMsg);

    List<ApiMsg> list();
}
