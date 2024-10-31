package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    
    boolean existsByEmail(String email);

    User findByEmail(String email);

}