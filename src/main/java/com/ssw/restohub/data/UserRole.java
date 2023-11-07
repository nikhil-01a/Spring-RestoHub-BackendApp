package com.ssw.restohub.data;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssw.restohub.enums.AppRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @JsonIgnore
    @Column(name = "password")
    private String password;
    @Column(name = "appRole")
    @Enumerated(EnumType.STRING)
    private AppRole appRole;
    @JsonIgnore
    @Column(name = "createTime", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date createTime;
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime",insertable = false)
    private Date updateTime;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;     // TODO: Implement this after some time!!! Very Important
    }

    @Override
    public String getUsername() {
        return this.email;  // Retuning our email field as the official username to Spring Security's 'UserDetails' class
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password; // Retuning our password field as the official username to Spring Security's 'UserDetails' class
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
