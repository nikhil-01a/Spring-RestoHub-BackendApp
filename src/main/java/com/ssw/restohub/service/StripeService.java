package com.ssw.restohub.service;

import com.ssw.restohub.pojo.ChargeRequest;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;


public interface StripeService {

    Charge charge(ChargeRequest chargeRequest) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException, CardException;

}
