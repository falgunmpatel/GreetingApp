package com.example.greetingapp.repositories;

import com.example.greetingapp.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends JpaRepository<MessageEntity, Long> {


}