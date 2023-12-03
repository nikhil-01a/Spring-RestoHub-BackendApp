package com.ssw.restohub.service.impl;

import com.ssw.restohub.pojo.ChargeRequest;
import com.ssw.restohub.pojo.PaymentResponse;
import com.ssw.restohub.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class StripeServiceImpl implements StripeService {
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentResponse charge(ChargeRequest chargeRequest) throws StripeException {
        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount((long) chargeRequest.getAmount()) // amount should be in the smallest currency unit
                .setCurrency(ChargeRequest.Currency.USD.toString().toLowerCase())
                .setPaymentMethod(chargeRequest.getStripeToken()) // Assuming this is the payment method ID
                .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.AUTOMATIC)
                .setConfirm(true)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(createParams);

        // Instead of sending the entire PaymentIntent object, send a simplified version
        PaymentResponse response = new PaymentResponse();
        response.setId(paymentIntent.getId());
        response.setAmount(paymentIntent.getAmount());
        response.setCurrency(paymentIntent.getCurrency());
        response.setStatus(paymentIntent.getStatus());

        return response;
    }
}
