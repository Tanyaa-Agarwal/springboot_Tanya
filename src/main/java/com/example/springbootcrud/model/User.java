package com.example.springbootcrud.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;


@Component
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String username;
    private String name;
    private int age;
    private String address;

    public User(String username, String name, int age, String address) {
        this.username = username;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public User() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
