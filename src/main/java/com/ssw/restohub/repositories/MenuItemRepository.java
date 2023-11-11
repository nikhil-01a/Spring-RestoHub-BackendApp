package com.ssw.restohub.repositories;

import com.ssw.restohub.data.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
}
