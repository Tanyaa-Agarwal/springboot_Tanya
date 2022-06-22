package com.example.springbootcrud.service;

import com.example.springbootcrud.exception.UserAlreadyExists;
import com.example.springbootcrud.exception.UserNotFoundException;
import com.example.springbootcrud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String,String,User> hashOperations;
    @PostConstruct
    private void intializeHashOperations() {

        hashOperations = redisTemplate.opsForHash();
    }

//    private static List<User> list = new ArrayList<User>();
//
//    static {
//        list.add(new User("tagarwal","Tanya",20,"12-Bangalore"));
//        list.add(new User("ujjwal1","Ujjwal",21,"1-Lucknow"));
//    }

    public List<User> getUsers(){
        return hashOperations.values("User");
//        return list;
    }

    public User getUserByUsername(String username){
//        for(User user:list)
//        {
//            if(user.getUsername().equals(username))
//                return user;
//        }
        try{
            User user =(User)hashOperations.get("User", username);
            if(!user.equals(null))
            return user;
        }
        catch (NullPointerException e)
        {
           throw new UserNotFoundException("user not found");
        }
        return null;


    }

    public User addUser(User user){

            boolean check_is_added=hashOperations.putIfAbsent("User", user.getUsername(), user);
            if(check_is_added)
                return user;
            else
                throw new UserAlreadyExists("user already exists");


//        for(User u:list)
//        {
//            if(u.getUsername().equals(user.getUsername()))
//               throw new UserAlreadyExists("User already exits with the username: "+user.getUsername());
//        }
//        list.add(user);
//        return user;
    }

    public User updateUser(User user){
//        for(User u :list)
//        {
//            if(u.getUsername().equals(user.getUsername()))
//            {
//               u.setName(user.getName());
//               u.setAddress(user.getAddress());
//               u.setAge(user.getAge());
//               return u;
//            }
//
//        }
//        throw new UserNotFoundException("User not found with username: "+user.getUsername());
        try {

            hashOperations.put("User", user.getUsername(), user);
            return user;
        }
        catch (Exception e)
        {
            throw e;
        }

    }

    public void deleteUser(String username){
//        for(User user:list)
//        {
//            if(user.getUsername().equals(username))
//            {
//            	 list.remove(user);
//            	 return;
//            }
//
//        }
//       throw new UserNotFoundException("User not found with username: "+username);
      int check_if_deleted= Math.toIntExact(hashOperations.delete("User", username));
        if(check_if_deleted==1)
            return;
        else
            throw new UserNotFoundException("user not found");
    }
}
