package com.gk.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gk.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByUsername(String username);

}
