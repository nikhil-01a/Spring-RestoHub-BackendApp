package com.ssw.restohub.repositories;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.projection.UnavailableReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    String GET_UNAVAILABLE_TIMES  =
            "    select rsv.reservation_date as reservationDate " +
            "    from reservation rsv " +
            "    inner join restaurant rest on (rsv.restaurant_id = rest.id) " +
            "    where rest.id = :restaurantId " +
            "    group by reservation_date, rest.capacity " +
            "    having sum(party_size) >= rest.capacity" +
            "    and :partySize > (rest.capacity - sum(party_size))";

    @Query(value = GET_UNAVAILABLE_TIMES, nativeQuery = true)
    List<UnavailableReservationTime> getAllUnavailableTimes(@Param(value="restaurantId") Long restaurantId, @Param(value="partySize") Integer partySize);
}
