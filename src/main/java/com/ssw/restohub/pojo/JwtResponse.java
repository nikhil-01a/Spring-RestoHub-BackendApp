package com.ssw.restohub.pojo;

import com.ssw.restohub.data.UserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private String jwtToken;
    private UserRole userRole;

}
