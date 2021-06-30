package com.nagarro.userservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.userservice.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
