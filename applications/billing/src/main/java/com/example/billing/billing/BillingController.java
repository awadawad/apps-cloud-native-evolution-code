package com.example.billing.billing;

import com.example.payments.Gateway;
import com.example.payments.RecurlyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {

    @Autowired
    Gateway paymentGateway;

    @RequestMapping(value = "/reoccurringPayment", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<String> reoccurringPayment(@RequestBody Payment payment){
        ResponseEntity<String> responseEntity = null;
        String response = "{errors: []}";

        if(paymentGateway.createReocurringPayment(payment.getPaymentAmount())){
            responseEntity = new ResponseEntity<String>(response, HttpStatus.CREATED);

        }
        else{
            response = "{\n" +
                        "\t\"errors\": [{\n" +
                        "\t\t\"error\": \"ERROR\"\n" +
                        "\t}]\n" +
                         "}";
            responseEntity = new ResponseEntity<String>(response,HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
