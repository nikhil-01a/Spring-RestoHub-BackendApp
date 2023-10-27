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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UserRole")
public class UserRole implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "userAccess")
    @Enumerated(EnumType.STRING)
    private UserAccess userAccess;
    @Column(name = "appRole")
    @Enumerated(EnumType.STRING)
    private AppRole appRole;
    @Column(name = "createTime",updatable = false,insertable = false)
    private Date createTime;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime",insertable = false)
    private Date updateTime;
    @Column(name = "updateUser")
    @JsonProperty(value = "updateUser")
    private String updateUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;     // TODO: Implement this after some time!!! Very Important
    }

    @Override
    public String getUsername() {
        return this.email;  // Retuning our email field as the official username to Spring Security's 'UserDetails' class
    }

    @Override
    public String getPassword() {
        return this.password; // Retuning our password field as the official username to Spring Security's 'UserDetails' class
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
