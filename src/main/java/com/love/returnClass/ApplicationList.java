package com.love.returnClass;

import com.love.Mapper.UserMapper;
import com.love.controller.AddressBook_controller;
import com.love.module.User;
import com.love.module.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

public class ApplicationList {
    private int user_id;
    private String email;
    private String username;
    private String gender;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public HashMap<String, String> getEmail_userName_gender() {
        HashMap<String, String> user = new HashMap<>();
        user.put("email", email);
        user.put("username", username);
        user.put("gender", gender);
        return user;
    }

    public void setEmail_userName_gender(User user) {
        this.email = user.getEmail();
        this.username = user.getUserName();
        this.gender = user.getGender();
    }

    public ApplicationList(int applicant_id) {
        this.user_id = applicant_id;
    }
}
