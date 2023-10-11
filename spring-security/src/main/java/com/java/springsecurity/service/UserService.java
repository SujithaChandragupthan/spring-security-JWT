package com.java.springsecurity.service;

import com.java.springsecurity.dto.Product;
import com.java.springsecurity.entity.User;
import com.java.springsecurity.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class UserService {

    List<Product> productList = null;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadProductsFromDB() {
        productList = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Product.builder()
                        .productId(i)
                        .name("product" + i)
                        .qty(new Random().nextInt(10))
                        .price(new Random().nextInt(5000)).build())
                .collect(Collectors.toList());
    }

    public List<Product> getProductList(){
        return productList;
    }

    public Product getProduct(int id){
        return productList.stream()
                .filter(product -> product.getProductId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("product" + id + "not found"));
    }

    public String addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User added to System";
    }
}
