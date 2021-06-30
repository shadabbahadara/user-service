package com.nagarro.userservice.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.kafka.KafkaProducer;
import com.nagarro.userservice.data.UserRepository;
import com.nagarro.userservice.exceptions.UserNotFoundException;
import com.nagarro.userservice.models.User;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private KafkaProducer producer;

	@Override
	public List<User> getAllUsers() {
		LOG.info("fetching all available users...");
		producer.send("users list retrieved");
		return userRepository.findAll();
	}

	@Override
	public User getUser(int userId) {
		LOG.info("fetching user details for user id: {}", userId);
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			LOG.error("user {} does not exist in the database!", userId);
			throw new UserNotFoundException();
		}
		producer.send("user with id: " + userId + " retrieved");
		return user.get();
	}

	@Override
	public User createUser(User user) {
		User userFromDB = userRepository.save(user);
		producer.send("new user created");
		return userFromDB;
	}

	@Override
	public User updateUser(int userId, User user) {
		User userFromDB = getUser(userId);
		updateUser(user, userFromDB);
		userFromDB = userRepository.save(userFromDB);
		producer.send("user with id: " + userId + " updated");
		return userFromDB;
	}

	private void updateUser(User src, User destination) {
		destination.setName(src.getName());
		destination.setAge(src.getAge());
		destination.setEmail(src.getEmail());
	}

	@Override
	public void deleteUser(int userId) {
		getUser(userId);
		userRepository.deleteById(userId);
		producer.send("user with id: " + userId + " removed");
	}

}
