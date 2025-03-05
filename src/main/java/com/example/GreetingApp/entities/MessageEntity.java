package com.example.greetingapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MessageEntity {

    String message;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    public MessageEntity(){
    }

    public MessageEntity(String message) {
        this.message = message;
        id = null;
    }

    public String getMessage() {
        return message;
    }

    public Long getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}