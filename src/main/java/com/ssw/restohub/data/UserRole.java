package com.ssw.restohub.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssw.restohub.enums.AppRole;
import com.ssw.restohub.enums.ApplicationName;
import com.ssw.restohub.enums.UserAccess;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UserRole")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "userId")
    private String userId;
    @Column(name = "userAccess")
    @Enumerated(EnumType.STRING)
    private UserAccess userAccess;
    @Column(name = "appName")
    @Enumerated(EnumType.STRING)
    private ApplicationName applicationName;
    @Column(name = "appRole")
    @Enumerated(EnumType.STRING)
    private AppRole appRole;
    @Column(name = "role")
    private String role;
    @Column(name = "createTime",nullable = false,updatable = false,insertable = false)
    private Date createTime;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime",insertable = false)
    private Date updateTime;
    @Column(name = "updateUser")
    @JsonProperty(value = "userName")
    private String updateUser;

}
