package com.example.billing;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class BillingClient {

    private static final Logger logger = LoggerFactory.getLogger(BillingClient.class);

    String serviceEndpoint;

    RestTemplate template;

    public BillingClient(RestTemplate restTemplate, String serviceEndpoint){
        this.template = restTemplate;
        this.serviceEndpoint = serviceEndpoint;
    }

    @HystrixCommand(fallbackMethod = "billUserFallback")
    public void billUser(String userId, int paymentAmount){
        Payment payment = new Payment();
        payment.setPaymentAmount(paymentAmount);
        template.postForEntity(serviceEndpoint, payment, String.class);
    }

    public void billUserFallback(String userId, int paymentAmount){
        logger.info("billUserFallback {} {}",userId,paymentAmount);
    }
}
