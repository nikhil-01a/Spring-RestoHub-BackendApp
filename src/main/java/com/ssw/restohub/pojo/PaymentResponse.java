package com.ssw.restohub.pojo;

import lombok.Data;

@Data
public class PaymentResponse {
        private String id;
        private Long amount;
        private String currency;
        private String status;

}
