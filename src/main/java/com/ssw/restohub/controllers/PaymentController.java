package com.ssw.restohub.controllers;

import com.ssw.restohub.pojo.ChargeRequest;
import com.ssw.restohub.pojo.PaymentResponse;
import com.ssw.restohub.service.StripeService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class PaymentController {

    private StripeService paymentsService;

    @Autowired
    public PaymentController(StripeService paymentsService){
        this.paymentsService = paymentsService;
    }

    @PostMapping("/api/payment/charge")
    public ResponseEntity<?> charge(@RequestBody ChargeRequest chargeRequest) {
        try {
            PaymentResponse charge = paymentsService.charge(chargeRequest);
            return ResponseEntity.ok(charge);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
