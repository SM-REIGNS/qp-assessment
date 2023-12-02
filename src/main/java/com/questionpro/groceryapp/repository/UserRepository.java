package com.questionpro.groceryapp.repository;

import com.questionpro.groceryapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}