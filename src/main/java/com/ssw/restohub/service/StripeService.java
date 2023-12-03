package com.ssw.restohub.service;

import com.ssw.restohub.pojo.ChargeRequest;
import com.ssw.restohub.pojo.PaymentResponse;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;
import com.stripe.model.PaymentIntent;


public interface StripeService {

    PaymentResponse charge(ChargeRequest chargeRequest) throws StripeException;

}
