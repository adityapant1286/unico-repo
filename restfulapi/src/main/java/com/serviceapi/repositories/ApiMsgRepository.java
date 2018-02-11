package com.serviceapi.repositories;


import com.serviceapi.model.ApiMsg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiMsgRepository extends JpaRepository<ApiMsg, Integer> {}
