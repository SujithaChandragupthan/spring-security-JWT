package com.java.springsecurity.controller;

import com.java.springsecurity.dto.Product;
import com.java.springsecurity.entity.User;
import com.java.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/welcome")
    public String welcomeMsg(){

        return "Welcome to User controller";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("/products/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllTheProduct(){
        return userService.getProductList();
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getAllTheProduct(@PathVariable int id){
        return userService.getProduct(id);
    }
}
