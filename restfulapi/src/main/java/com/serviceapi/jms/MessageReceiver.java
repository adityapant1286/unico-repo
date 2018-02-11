package com.serviceapi.jms;

import com.serviceapi.model.ApiMsg;
import com.serviceapi.service.ApiMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.serviceapi.util.ApiConstants.QUEUE_NAME;

//@Component
public class MessageReceiver {

//    @Autowired
//    private ApiMsgService service;
//
//    @JmsListener(destination = QUEUE_NAME, containerFactory = "apiMsgJmsFactory")
//    public void receiveMessage(ApiMsg apiMsg) {
//
//        service.save(apiMsg);
//    }
}
