package com.serviceapi.soapendpoint;


import com.service.soapapi.soap.types.gcd.GcdListResponse;
import com.service.soapapi.soap.types.gcd.ObjectFactory;
import com.serviceapi.model.ApiMsg;
import com.serviceapi.service.ApiMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Endpoint
public class GcdEndpoint {

    private static final String QUEUE_NAME = "controller.msg.queue";

    @Autowired
    private ApiMsgService service;

    @Autowired
    private ConnectionFactory apiMsgJmsFactory;

    private JmsTemplate jmsTemplate;

    @PostConstruct
    public void init() { this.jmsTemplate = new JmsTemplate(apiMsgJmsFactory); }

    @PayloadRoot(namespace = "http://soapapi.service.com/soap/types/gcd", localPart = "GcdRequest")
    @ResponsePayload
    public int gcd(@RequestPayload String request) {

        ApiMsg msg = (ApiMsg) jmsTemplate.receiveAndConvert(QUEUE_NAME);

        if(Objects.isNull(msg))
            return 0;

        int gcd = calculateGcd(msg.getI1(), msg.getI2());
        msg.setGcd(gcd);
        service.save(msg);

        return gcd;
    }

    @PayloadRoot(namespace = "http://soapapi.service.com/soap/types/gcd", localPart = "GcdListRequest")
    @ResponsePayload
    public GcdListResponse gcdList(@RequestPayload String request) {
        ObjectFactory factory = new ObjectFactory();
        GcdListResponse response = factory.createGcdListResponse();

        List<Integer> integerList = service.list().stream().map(ApiMsg::getGcd).collect(Collectors.toList());

        response.getGsdList().addAll(integerList);

        return response;
    }

    @PayloadRoot(namespace = "http://soapapi.service.com/soap/types/gcd", localPart = "GcdSumRequest")
    @ResponsePayload
    public int gcdSum(@RequestPayload String request) {

        return service.list().stream().mapToInt(ApiMsg::getGcd).sum();
    }


    public static int calculateGcd(int n1, int n2) {
        return (n1 == 0 || n2 == 0) ? (n1 + n2) : calculateGcd(n2, n1 % n2);
    }

}
