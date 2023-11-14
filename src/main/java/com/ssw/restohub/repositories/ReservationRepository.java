package com.ssw.restohub.repositories;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.pojo.ReservationBean;
import com.ssw.restohub.projection.UnavailableReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    String GET_RESERVATION_FOR_RESTAURANT_AND_TIME_FRAME =
             "    select * from reservation " +
             "    where restaurant_id = :restaurantId " +
             "    and reservation_date > TO_TIMESTAMP(:startDate, 'YYYY-MM-DD') " +
             "    and reservation_date < TO_TIMESTAMP(:endDate, 'YYYY-MM-DD') " +
             "    and checked_in = false ";

    @Query(value = GET_UNAVAILABLE_TIMES, nativeQuery = true)
    List<UnavailableReservationTime> getAllUnavailableTimes(
            @Param(value="restaurantId") Long restaurantId,
            @Param(value="partySize") Integer partySize);

    @Query(value = GET_RESERVATION_FOR_RESTAURANT_AND_TIME_FRAME, nativeQuery = true)
    List<Reservation> getReservationsForRestaurantAndTimeFrame(
            @Param(value="restaurantId") Long restaurantId,
            @Param(value="startDate") String startDate,
            @Param(value="endDate") String endDate);

    Optional<Reservation> findByReservationCode(String reservationCode);
}
