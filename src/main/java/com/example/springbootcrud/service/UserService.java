package com.example.springbootcrud.service;

import com.example.springbootcrud.exception.UserAlreadyExists;
import com.example.springbootcrud.exception.UserNotFoundException;
import com.example.springbootcrud.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {

    private static List<User> list = new ArrayList<User>();

    static {
        list.add(new User("tagarwal","Tanya",20,"12-Bangalore"));
        list.add(new User("ujjwal1","Ujjwal",21,"1-Lucknow"));
    }

    public List<User> getUsers(){
        return list;
    }

    public User getUserByUsername(String username){
        for(User user:list)
        {
            if(user.getUsername().equals(username))
                return user;
        }
        throw new UserNotFoundException("User not found with username: "+username);
    }

    public User addUser(User user){
        for(User u:list)
        {
            if(u.getUsername().equals(user.getUsername()))
               throw new UserAlreadyExists("User already exits with the username: "+user.getUsername());
        }
        list.add(user);
        return user;
    }

    public User updateUser(User user){
        for(User u :list)
        {
            if(u.getUsername().equals(user.getUsername()))
            {
               u.setName(user.getName());
               u.setAddress(user.getAddress());
               u.setAge(user.getAge());
               return u;
            }
         
        }
        throw new UserNotFoundException("User not found with username: "+user.getUsername());
    }

    public void deleteUser(String username){
        for(User user:list)
        {
            if(user.getUsername().equals(username))
            {
            	 list.remove(user);
            	 return;
            }
               
        }
       throw new UserNotFoundException("User not found with username: "+username);
    }
}
