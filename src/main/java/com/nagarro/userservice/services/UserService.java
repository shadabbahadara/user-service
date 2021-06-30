package com.nagarro.userservice.services;

import java.util.List;

import com.nagarro.userservice.models.User;

public interface UserService {

	List<User> getAllUsers();

	User getUser(int userId);

	User createUser(User user);

	User updateUser(int userId, User user);

	void deleteUser(int userId);

}
