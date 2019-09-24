package com.gk.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gk.demo.entity.User;
import com.gk.demo.exceptions.UserExistsException;
import com.gk.demo.exceptions.UserNotFoundException;
import com.gk.demo.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
	
	public User createUser(User user) throws UserExistsException{
		User existingUser=userRepository.findByUsername(user.getUsername());
		if(existingUser!=null)
			throw new UserExistsException("User already exists in user repository");
		return userRepository.save(user);
	}
	
	public Optional<User> getUserById(long id) throws UserNotFoundException{
		Optional<User> user=userRepository.findById(id);
		if(!user.isPresent())
			throw new UserNotFoundException("user not found in user repository");
		return user;
	}
	
	public User updateUserById(long id, User user) throws UserNotFoundException {
		Optional<User> optionaluser=userRepository.findById(id);
		if(!optionaluser.isPresent())
			throw new UserNotFoundException("user not found in user repository, provide the correct user id");
		user.setId(id);
		return userRepository.save(user);
	}
	
	public void deleteUserById(long id) {	
		Optional<User> optionaluser=userRepository.findById(id);
		if(!optionaluser.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user not found in user repository, provide the correct user id");
		
			userRepository.deleteById(id);
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
