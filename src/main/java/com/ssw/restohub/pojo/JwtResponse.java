package com.ssw.restohub.pojo;

import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.data.UserRole;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private String jwtToken;
    private UserRole userRole;
    private Optional<Restaurant> restaurant;

}
