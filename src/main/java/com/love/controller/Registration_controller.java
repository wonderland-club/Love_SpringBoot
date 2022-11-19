package com.love.controller;

import com.love.Mapper.UserMapper;
import com.love.module.User;
import com.love.module.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Registration_controller {
    @Autowired
    UserMapper userMapper;

    UserExample userExample;

    UserExample.Criteria criteria;

    @PostMapping("/registration")
    public ResponseEntity<Map<String,String>> registration(@RequestBody Map<String, String> newUser) {
        userExample = new UserExample();
        criteria = userExample.createCriteria();
        Map<String,String>  hashMap = new HashMap<>();

        criteria.andEmailEqualTo(newUser.get("email"));
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList.size() < 1) {
            User user = new User();
            user.setEmail(newUser.get("email"));
            user.setUserName(newUser.get("user_name"));
            user.setGender(newUser.get("gender"));
            user.setPassword(newUser.get("password"));
            user.setRole("ordinary");
            userMapper.insert(user);
            System.out.println(user.getUserName());
            hashMap.put("user",user.getUserName());
            return ResponseEntity.ok(hashMap);
        }
        hashMap.put("error","该邮箱以注册");
        return ResponseEntity.status(400).body(hashMap);
    }
}
