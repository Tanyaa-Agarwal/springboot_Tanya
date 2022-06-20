package com.example.springbootcrud.service;

import com.example.springbootcrud.model.User;

import java.util.ArrayList;
import java.util.List;

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
            if(user.getUsername()==username)
                return user;
        }
        return null;
    }

    public User addUser(User user){
        list.add(user);
        return user;
    }

    public User updateUser(User user){
        for(User u :list)
        {
            if(u.getUsername()== user.getUsername())
            {
               u.setName(user.getName());
               u.setAddress(user.getAddress());
               u.setAge(user.getAge());
            }
            return user;
        }
        return null;
    }

    public void deleteUser(String username){
        for(User user:list)
        {
            if(user.getName()==username)
                list.remove(user);
        }
        return ;
    }
}
