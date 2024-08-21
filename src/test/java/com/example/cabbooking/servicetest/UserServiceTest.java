package com.example.cabbooking.servicetest;


import com.example.cabbooking.model.User;
import com.example.cabbooking.vo.UserDetailsInVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testAddUser() {
        UserDetailsInVO userDetails = new UserDetailsInVO();
        userDetails.setUserName("John");
        userDetails.setGender("M");
        userDetails.setAge(30);

        userService.addUser(userDetails);

        User user = userService.getUser("John");

        assertNotNull(user);
        assertEquals("John", user.getName());
        assertEquals("M", user.getGender());
        assertEquals(30, user.getAge());
    }

    @Test
    void testGetUser_UserExists() {
        UserDetailsInVO userDetails = new UserDetailsInVO();
        userDetails.setUserName("Jane");
        userDetails.setGender("F");
        userDetails.setAge(25);

        userService.addUser(userDetails);

        User user = userService.getUser("Jane");

        assertNotNull(user);
        assertEquals("Jane", user.getName());
        assertEquals("F", user.getGender());
        assertEquals(25, user.getAge());
    }

    @Test
    void testGetUser_UserDoesNotExist() {
        User user = userService.getUser("NonExistentUser");

        assertNull(user);
    }
}

