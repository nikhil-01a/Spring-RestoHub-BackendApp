package com.ssw.restohub.controllers;

import com.ssw.restohub.data.MenuItem;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.enums.AppRole;
import com.ssw.restohub.pojo.MenuItemRequest;
import com.ssw.restohub.repositories.MenuItemRepository;
import com.ssw.restohub.repositories.UserRepository;
import com.ssw.restohub.service.MenuItemService;
import com.ssw.restohub.service.RestaurantService;
import com.ssw.restohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ManagerViewController {

    private MenuItemService menuItemService;
    private UserService userService;
    private RestaurantService restaurantService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ManagerViewController(PasswordEncoder passwordEncoder,MenuItemService menuItemService,UserService userService,RestaurantService restaurantService,UserRepository userRepository){
        this.menuItemService = menuItemService;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/api/manager/saveMenuItem")
    public ResponseEntity<Object> saveMenuItem(@RequestBody MenuItemRequest menuItemRequest){
        try {
            Optional<MenuItem> menuItem = menuItemService.getMenuItemByName(menuItemRequest.getMenuItem().getName(),menuItemRequest.getRestaurantId());
            if(menuItem.isEmpty()){
                return ResponseEntity.ok(menuItemService.saveMenuItem(menuItemRequest));
            }
            else {
                return new ResponseEntity<>("An item under this name already exists for this restaurant!",HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>("Could not save item!",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/api/manager/menuItems")
    public ResponseEntity<List<MenuItem>> getAllMenuItems(@RequestParam(value = "restaurantId") Long restaurantId){
        List<MenuItem> menuItems = menuItemService.getAllMenuItems(restaurantId);
        return new ResponseEntity<>(menuItems,HttpStatus.OK);
    }

    @PutMapping(value = "/api/manager/updateMenuItem")
    public ResponseEntity<Object> updateMenuItem(@RequestBody  MenuItem menuItem){
        try {
            return ResponseEntity.ok(menuItemService.updateMenuItem(menuItem));
        } catch (Exception e){
            return new ResponseEntity<>("Couldn't Update Menu Item", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/api/manager/deleteMenuItem")
    public ResponseEntity<Object> deleteMenuItem(@RequestParam(value = "menuItemId") Long menuItemId){
        try {
            return ResponseEntity.ok(menuItemService.deleteMenuItem(menuItemId));
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/manager/save-staff")
    public ResponseEntity<Object> saveUser(@RequestBody UserRole userRole) {
        Optional<UserRole> userRoleCheck = userService.userExistsCheck(userRole.getEmail());
        if (userRoleCheck.isEmpty()) {
            userRole.setPassword(passwordEncoder.encode(userRole.getPassword()));
            if (userRole.getAppRole().equals(AppRole.RESTAURANT_STAFF)) {
                Optional<Restaurant> assignedRestaurant = restaurantService.getRestaurantById(userRole.getRestaurantId());
                if (!assignedRestaurant.isPresent()) {
                    return new ResponseEntity<>("Restaurant Not Found", HttpStatus.NOT_FOUND);
                }
                userRole.setRestaurantId(assignedRestaurant.get().getId());
            }
            userRepository.save(userRole);
            return new ResponseEntity<>(userRole, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email already exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/api/manager/getAllStaff")
    public ResponseEntity<List<UserRole>> getAllStaff(@RequestParam(value = "restaurantId") Long restaurantId){
        try {
            return new ResponseEntity<>(userRepository.findUserRoleByRestaurantIdAndAppRole(restaurantId,AppRole.RESTAURANT_STAFF),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/api/manager/deleteStaff")
    public ResponseEntity<Object> deleteStaff(@RequestParam(value = "email") String email){
        if (userService.deleteByEmail(email)) {
            return new ResponseEntity<>(email+" deleted successfully!",HttpStatus.OK);
        }else {
            return new ResponseEntity<>(email+" not found!",HttpStatus.NOT_FOUND);
        }
    }



}
