package com.example.greetingapp.repositories;

import com.example.greetingapp.models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {


}