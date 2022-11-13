package com.love.controller;

import com.love.module.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Longin_controller {
    @GetMapping("/me")
    public ResponseEntity<Object> me() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            Map<String, String> map = new HashMap<>();
            map.put("username", user.getEmail());
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.status(400).body("error");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> map) {
        String email = map.get("email");
        String password = map.remove("password");
        AuthenticationToken token = new UsernamePasswordToken(email, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            SecurityUtils.getSubject().getSession().setTimeout(-1000L);
            return ResponseEntity.ok(map);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(400).body("error");
        }
    }

    @GetMapping("logout")
    public ResponseEntity<Object> logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User User = (User) subject.getPrincipal();
            Map<String, String> map = new HashMap<>();
            map.put("username", User.getEmail());
            subject.logout();
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.status(400).body("error");
    }

}
