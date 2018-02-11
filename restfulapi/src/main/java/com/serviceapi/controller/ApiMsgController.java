package com.serviceapi.controller;

import com.serviceapi.model.ApiMsg;
import com.serviceapi.service.ApiMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.serviceapi.util.ApiConstants.QUEUE_NAME;
import static com.serviceapi.util.ApiConstants.RESP_PUSH_CREATED;

@RestController
@RequestMapping("/apiMsg")
public class ApiMsgController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ApiMsgService service;

    @PostMapping("/push")
    public ResponseEntity<String> push(@RequestParam(value = "i1") int i1, @RequestParam(value = "i2") int i2) {

        jmsTemplate.convertAndSend(QUEUE_NAME, new ApiMsg(i1, i2));

        return new ResponseEntity<>(RESP_PUSH_CREATED, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ApiMsg>> list() {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

}
