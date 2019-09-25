package com.gk.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.gk.demo.entity.User;
import com.gk.demo.exceptions.UserExistsException;
import com.gk.demo.exceptions.UserNameFoundException;
import com.gk.demo.exceptions.UserNotFoundException;
import com.gk.demo.service.UserService;

@RestController
@RequestMapping("user")
@Validated
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getAllUser(){
		return userService.getAllUser();
	}
	
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@Valid @RequestBody User user,UriComponentsBuilder builder) {
		try {
			userService.createUser(user);
			HttpHeaders headers=new HttpHeaders();
			headers.setLocation(builder.path("/user/users/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
		} catch (UserExistsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	@GetMapping("/users/{id}")
	public Optional<User> getUserById(@PathVariable("id") @Min(1) long id ){
		try {
			return userService.getUserById(id);
		}catch(UserNotFoundException ex){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
		}
		
	}
	
	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable("id") long id, @RequestBody User user) {
		try {
			return userService.updateUserById(id, user);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") long id) {
		userService.deleteUserById(id);
	}
	
	@GetMapping("/byusername/{username}")
	public User getUserByUsername(@PathVariable("username") String username) throws UserNotFoundException {
		User user=userService.getUserByUsername(username);
		if(user==null)
			throw new UserNotFoundException("username -"+username+" not exist in user repository");
		return user;
	}

}
