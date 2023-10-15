package com.ssw.restohub.repositories;

import com.ssw.restohub.data.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserRole, Integer> {

    UserRole findUserRoleByUserId(String username);

}
