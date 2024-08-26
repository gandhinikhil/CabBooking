package com.example.cabbooking.servicetest;

import com.example.cabbooking.entity.User;
import com.example.cabbooking.repository.UserRepository;
import com.example.cabbooking.vo.UserDetailsInVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final Map<String, UserDetailsInVO> users = new HashMap<>();

   public UserService(UserRepository userRepository){
        this.userRepository= userRepository;
    }



    public void addUser(UserDetailsInVO userDetails) {
        UserDetailsInVO user = new UserDetailsInVO(1,userDetails.getUserName(), userDetails.getGender(), userDetails.getAge(), LocalDateTime.now());
        users.put(user.getUserName(), user);
    }
    public User saveUser(UserDetailsInVO userDetailsInVO){
        User user = new User();
        user.setName(userDetailsInVO.getUserName());
        user.setAge(userDetailsInVO.getAge());
        user.setGender(userDetailsInVO.getGender());
        return userRepository.save(user);
    }

    public UserDetailsInVO getUser(String username) {
        return users.get(username);
    }
}

