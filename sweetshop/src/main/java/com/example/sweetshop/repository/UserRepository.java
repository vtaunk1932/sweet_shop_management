
package com.example.sweetshop.repository;

import com.example.sweetshop.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
