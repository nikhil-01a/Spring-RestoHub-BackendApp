package com.ssw.restohub.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Getter
@Setter
@JsonDeserialize
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationBean {
    private Long restaurantId;
    private String reservationDate;
    private Integer partySize;
    private String firstName;
    private String lastName;
    private String emailAddress;
}
