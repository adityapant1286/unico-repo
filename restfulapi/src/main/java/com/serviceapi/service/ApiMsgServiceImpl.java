package com.serviceapi.service;

import com.serviceapi.model.ApiMsg;
import com.serviceapi.repositories.ApiMsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiMsgServiceImpl implements ApiMsgService {

    @Autowired
    private ApiMsgRepository repository;

    @Override
    public void save(ApiMsg apiMsg) {
        repository.save(apiMsg);
    }


    @Override
    public List<ApiMsg> list() {
        return repository.findAll();
    }
}
