package com.example.fitnesstracker.repository;

import com.example.fitnesstracker.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
}
