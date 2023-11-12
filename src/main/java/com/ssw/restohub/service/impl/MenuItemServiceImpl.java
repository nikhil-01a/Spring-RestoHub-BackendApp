package com.ssw.restohub.service.impl;

import com.ssw.restohub.data.MenuItem;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.pojo.MenuItemRequest;
import com.ssw.restohub.repositories.MenuItemRepository;
import com.ssw.restohub.service.MenuItemService;
import com.ssw.restohub.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private MenuItemRepository menuItemRepository;
    private RestaurantService restaurantService;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,RestaurantService restaurantService){
        this.menuItemRepository = menuItemRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    public MenuItem saveMenuItem(MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRequest.getMenuItem();
        menuItem.setRestaurant(new Restaurant());
        menuItem.getRestaurant().setId(menuItemRequest.getRestaurantId());
        return menuItemRepository.save(menuItem);
    }

    @Override
    public List<MenuItem> getAllMenuItems(Long restaurantId){
        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(restaurantId);
        return menuItems;
    }

    @Override
    public List<MenuItem> getAllMenuItemsById(List<Long> menuItemsIdList){
        List<MenuItem> menuItems = menuItemRepository.findAllById(menuItemsIdList);
        return menuItems;
    }

    @Override
    public MenuItem getMenuItem(Long menuItemId) {
        Optional<MenuItem> menuItem = menuItemRepository.findMenuItemById(menuItemId);
        if (menuItem.isPresent()){
            return menuItem.get();
        }else {
            throw new RuntimeException("Menu item doesn't exist");
        }
    }

    @Override
    public double getMenuItemPrice(Long menuItemId){
        return menuItemRepository.findMenuItemById(menuItemId).get().getPrice();
    }

    @Override
    public Optional<MenuItem> getMenuItemByName( String menuItemName,Long restaurantId) {
        Optional<MenuItem> menuItem = menuItemRepository.findMenuItemsByNameAndRestaurantId(menuItemName,restaurantId);
        return menuItem;
    }

    @Override
    public MenuItem updateMenuItem(MenuItem menuItem) {
        Optional<MenuItem> existingMenuItem = menuItemRepository.findById(menuItem.getId());
        if(existingMenuItem.isPresent()){
            MenuItem updatedMenuItem = menuItem;
            menuItem.setRestaurant(existingMenuItem.get().getRestaurant());
            menuItemRepository.save(updatedMenuItem);
            return updatedMenuItem;
        }else {
            throw new RuntimeException("Menu item doesn't exist");
        }
    }

    @Override
    public String deleteMenuItem(Long menuItemId) {
        if(menuItemRepository.existsById(menuItemId)){
            String menuItemName = menuItemRepository.findById(menuItemId).get().getName();
            menuItemRepository.deleteById(menuItemId);
            return menuItemName + " deleted successfully!";
        }else{
            throw new RuntimeException("Menu item not found");
        }
    }
}
