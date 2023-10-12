package com.java.springsecurity.controller;

import com.java.springsecurity.dto.AuthRequest;
import com.java.springsecurity.dto.Product;
import com.java.springsecurity.entity.User;
import com.java.springsecurity.service.JWTService;
import com.java.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/welcome")
    public String welcomeMsg() {

        return "Welcome to User controller";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/products/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllTheProduct() {
        return userService.getProductList();
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getAllTheProduct(@PathVariable int id) {
        return userService.getProduct(id);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("User name not found");
        }
    }
}
