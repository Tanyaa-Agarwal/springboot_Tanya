package com.example.springbootcrud.controller;

import com.example.springbootcrud.exception.UserNotFoundException;
import com.example.springbootcrud.model.User;
import com.example.springbootcrud.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)  //functionality to launch a Spring TestContext Framework
@WebMvcTest(value = UserController.class) //WebMvcTest annotation is used for unit testing Spring MVC applicatio
@WithMockUser
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc; //MockMvc is the main entry point for server-side Spring MVC test support. It allows us to execute requests against the test context.

    @MockBean
    private UserService userService; // A mock of userService is created and auto-wired into the UserController.

    User mockUser = new User("tan123","Tanmay",12,"Dehradun");


    String exampleUserJson ="{  \"username\":\"tan123\",\n" +
            "    \"name\":\"Tanmay\",\n" +
            "    \"age\":12,\n" +
            "    \"address\":\"Dehradun\"}";
    @Test
    public void retrieveDetailsForUser() throws Exception {

        Mockito.when(
                userService.getUserByUsername(Mockito.anyString())
        ).thenReturn(mockUser);
        //Mocking the method getUserByUsername to return the specific mockUser when invoked.

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/users/tan123").accept(
                MediaType.APPLICATION_JSON);
        //Creating a Request builder to be able to execute a get request to uri “/users/tan123” with accept header as “application/json”

        //mockMvc is used to perform the request and return the response back.
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());
        String expected = "{username:tan123,name:Tanmay,age:12,address:Dehradun}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
        //This allows us to do partial asserts against a JSON String. We are passing strict as false since we do not want to check for all fields in the response.

        System.out.println("Test Successful");
    }


    @Test
    public void testfindAllUsers() throws Exception {
        User mockUser = new User("tan123","Tanmay",12,"Dehradun");
        List<User> users = Arrays.asList(mockUser);

        Mockito.when(userService.getUsers()).thenReturn(users);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/users").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());
        String expected = "[{username:tan123,name:Tanmay,age:12,address:Dehradun}]";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);

        System.out.println("Test Successful");
    }



    @Test
    public void should_throw_exception_when_user_doesnt_exist_delete() throws Exception {
        User user = new User();
        user.setUsername("pri");
        user.setName("Priyanshi");
        user.setAge(30);
        user.setAddress("Bareily");

        Mockito.doThrow(new UserNotFoundException(user.getUsername())).when(userService).deleteUser(user.getUsername());

        mockMvc.perform(delete("/users/" + user.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        System.out.println("Test Successful");
    }

    @Test
    public void should_throw_exception_when_user_doesnt_exist_get() throws Exception {
        User user = new User();
        user.setUsername("pri");
        user.setName("Priyanshi");
        user.setAge(30);
        user.setAddress("Bareily");

        Mockito.doThrow(new UserNotFoundException(user.getUsername())).when(userService).getUserByUsername(user.getUsername());

        mockMvc.perform(get("/users/" + user.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        System.out.println("Test Successful");

    }

}
