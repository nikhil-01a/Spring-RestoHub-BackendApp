package com.ssw.restohub.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "managerName")
    private String managerName;

    @Column(name = "managerEmail",unique = true)
    private String managerEmail;

    @Column(name = "streetAddress1", nullable = false)
    private String streetAddress1;

    @Column(name = "streetAddress2")
    private String streetAddress2;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zipCode", nullable = false)
    private String zipCode;

    @Column(name = "url")
    private String url;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "createTime", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date createTime = new Date();

    @Column(name = "updateTime",insertable = false)
    private Date updateTime = new Date();
}
