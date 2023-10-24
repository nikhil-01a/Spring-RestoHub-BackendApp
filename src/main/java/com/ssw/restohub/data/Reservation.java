package com.ssw.restohub.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="restaurantId", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "partySize", nullable = false)
    private Integer partySize;

    @Column(name = "reservationDate", nullable = false, updatable = true, insertable = true)
    private Date reservationDate;

    @Column(name = "createTime", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date createTime = new Date();

    @Column(name = "updateTime", insertable = false)
    private Date updateTime = new Date();
}
