package com.example.cabbooking.service;

import com.example.cabbooking.vo.UserDetailsInVO;
import org.springframework.stereotype.Service;
import com.example.cabbooking.model.User;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();



    public void addUser(UserDetailsInVO userDetails) {
        User user = new User(userDetails.getUserName(), userDetails.getGender(), userDetails.getAge());
        users.put(user.getName(), user);
    }

    public User getUser(String username) {
        return users.get(username);
    }
}

