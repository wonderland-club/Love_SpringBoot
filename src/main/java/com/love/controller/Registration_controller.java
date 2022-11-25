package com.love.controller;

import com.love.Mapper.UserMapper;
import com.love.Mapper.User_id_cardMapper;
import com.love.Mapper.User_loveMapper;
import com.love.module.User;
import com.love.module.UserExample;
import com.love.module.User_id_card;
import com.love.module.User_love;
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
    @Autowired
    User_id_cardMapper user_id_cardMapper;

    @Autowired
    User_loveMapper userLoveMapper;

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> registration(@RequestBody Map<String, String> newUser) {
        userExample = new UserExample();
        criteria = userExample.createCriteria();
        Map<String, String> hashMap = new HashMap<>();

        criteria.andEmailEqualTo(newUser.get("email"));
        List<User> userList = userMapper.selectByExample(userExample);
//        查询此邮箱之前有无注册
        if (userList.size() < 1) {
            User user = new User();
            user.setEmail(newUser.get("email"));
            user.setUserName(newUser.get("user_name"));
            user.setGender(newUser.get("gender"));
            user.setPassword(newUser.get("password"));
            user.setRole("ordinary");
            userMapper.insert(user);
            System.out.println(user.getUserName());
            hashMap.put("user", user.getUserName());
//            查找该用户id
            userExample = new UserExample();
            criteria = userExample.createCriteria();
            criteria.andEmailEqualTo(newUser.get("email"));
            List<User> user_id_List = userMapper.selectByExample(userExample);
            int user_id = user_id_List.get(0).getId();
            hashMap.put("user_id", user_id + "");
//            添加外键 user_id_card 实名认证
            User_id_card user_id_card = new User_id_card();
            user_id_card.setUserId(user_id);
            user_id_cardMapper.insert(user_id_card);
//            添加外键 user_love 绑定伴侣
            User_love user_love = new User_love();
            user_love.setUserId(user_id);
            userLoveMapper.insert(user_love);

            return ResponseEntity.ok(hashMap);
        }
        hashMap.put("error", "该邮箱以注册");
        return ResponseEntity.status(400).body(hashMap);
    }
}
