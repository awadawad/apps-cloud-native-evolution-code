package com.example.ums.subscriptions;

import com.example.billing.BillingClient;
import com.example.billing.ChargeUser;
import com.example.email.SendEmail;
import com.example.payments.RecurlyGateway;
import com.example.subscriptions.CreateSubscription;
import com.example.subscriptions.Subscription;
import com.example.subscriptions.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/subscriptions")
public class Controller {

    @Autowired
    SubscriptionRepository subscriptions;

    @Autowired
    private BillingClient client;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Subscription> index() {
        return subscriptions.all();
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody  ResponseEntity<String> create(@RequestBody Map<String, String> params) {

        ChargeUser paymentCreator = new ChargeUser(new RecurlyGateway());
        SendEmail emailSender = new SendEmail();

        new CreateSubscription(client , emailSender, subscriptions)
                .run(params.get("userId"), params.get("packageId"));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
