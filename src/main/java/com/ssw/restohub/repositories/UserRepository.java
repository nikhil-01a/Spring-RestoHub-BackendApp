package com.ssw.restohub.repositories;

import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.enums.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserRole, Integer> {

    Optional<UserRole> findByEmail(String email);

    List<UserRole> findUserRoleByRestaurantIdAndAppRole(Long id, AppRole appRole);

}
