package com.ssw.restohub.service;

import com.ssw.restohub.data.MenuItem;
import com.ssw.restohub.pojo.MenuItemRequest;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public interface MenuItemService {

    List<MenuItem> getAllMenuItems(Long restaurantId);

    MenuItem getMenuItem(Long menuItemId);

    List<MenuItem> getAllMenuItemsById(List<Long> menuItemsIdList);

    double getMenuItemPrice(Long menuItemId);

    Optional<MenuItem> getMenuItemByName(String menuItemName,Long restaurantId);

    MenuItem saveMenuItem(MenuItemRequest menuItemRequest);

    MenuItem updateMenuItem(MenuItem menuItem);

    String deleteMenuItem(Long menuItemId);

}
