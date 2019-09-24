package com.gk.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gk.demo.entity.User;
import com.gk.demo.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
	
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	public Optional<User> 	getUserById(long id){
		return userRepository.findById(id);
	}
	
	public User updateUserById(long id, User user) {
		user.setId(id);
		return userRepository.save(user);
	}
	
	public void deleteUserById(long id) {
		if(userRepository.findById(id).isPresent())
			userRepository.deleteById(id);
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
