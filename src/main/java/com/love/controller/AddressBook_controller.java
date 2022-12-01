package com.love.controller;

import com.love.Mapper.Companion_pplication_listMapper;
import com.love.Mapper.UserMapper;
import com.love.Mapper.User_loveMapper;
import com.love.module.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class AddressBook_controller {

    public static String[] httpBody_error_ok_Bound_email_same = {"error", "ok", "Bound", "email_same"};

    //伴侣申请列表
    @Autowired
    Companion_pplication_listMapper companion_pplication_listMapper;
    Companion_pplication_listExample companion_pplication_listExample;
    Companion_pplication_listExample.Criteria Companion_pplication_list_criteria;
    //用户
    @Autowired
    UserMapper userMapper;
    UserExample userExample;
    UserExample.Criteria user_Criteria;
    //伴侣表
    @Autowired
    User_loveMapper user_loveMapper;
    User_loveExample user_loveExample;
    User_loveExample.Criteria user_love_criteria;

    /*API*/
    //    申请绑定情侣关系
    @GetMapping("/application_binding")
    public ResponseEntity<String> Binding_Relationships(@RequestParam(value = "user_email") String user_email, @RequestParam(value = "love_email") String love_email) {
        Object relationships = whether_binding(user_email, love_email);
        //若邮箱存在和邮箱不重复则为true
        if (relationships != httpBody_error_ok_Bound_email_same[0] && relationships != httpBody_error_ok_Bound_email_same[3]) {
            User user = (User) ((HashMap<?, ?>) relationships).get("user_email");
            User love = (User) ((HashMap<?, ?>) relationships).get("love_email");
            //申请绑定情侣关系 在数据库中插入
            Companion_pplication_list companion_pplication_list = new Companion_pplication_list();
            companion_pplication_list.setUserId(user.getId());
            companion_pplication_list.setApplicantId(love.getId());
            companion_pplication_listMapper.insert(companion_pplication_list);
            return ResponseEntity.ok().body(httpBody_error_ok_Bound_email_same[1]);
        } else if (relationships.equals(httpBody_error_ok_Bound_email_same[2])) {
            //bound 已绑定
            return ResponseEntity.ok().body(httpBody_error_ok_Bound_email_same[2]);
        } else if (relationships.equals(httpBody_error_ok_Bound_email_same[0])) {
            //用户不存在
            return ResponseEntity.ok().body(httpBody_error_ok_Bound_email_same[0]);
        }
        //邮箱重复
        return ResponseEntity.ok().body(httpBody_error_ok_Bound_email_same[3]);
    }

    /*API*/
    //  绑定情侣关系
    public ResponseEntity<String> binding(@RequestParam(value = "user_email") String user_email, @RequestParam(value = "love_email") String love_email) throws ParseException {
        //再次确定双方没有其他伴侣关系
        Object relationships = whether_binding(user_email, love_email);
        //若邮箱存在和邮箱不重复则为true
        if (relationships != httpBody_error_ok_Bound_email_same[0] && relationships != httpBody_error_ok_Bound_email_same[3]) {
            User user = (User) ((HashMap<?, ?>) relationships).get("user_email");
            User love = (User) ((HashMap<?, ?>) relationships).get("love_email");
            //获取当前时间
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String data = ft.format(dNow);
            Date date2 = ft.parse(data);
            //将user_love 类插入user_love 用户表
            user_loveExample = new User_loveExample();
            user_love_criteria = user_loveExample.createCriteria();
            User_love user_love = new User_love();
            user_love.setUserId(user.getId());
            user_love.setLoveId(love.getId());
            user_love.setUserloveTime(date2);
            user_loveMapper.insert(user_love);
            return ResponseEntity.ok().body(httpBody_error_ok_Bound_email_same[1]);
        } else if (relationships.equals(httpBody_error_ok_Bound_email_same[0])) {
            return ResponseEntity.ok().body(httpBody_error_ok_Bound_email_same[0]);
        } else if (relationships.equals(httpBody_error_ok_Bound_email_same[3])) {
            return ResponseEntity.ok().body(httpBody_error_ok_Bound_email_same[3]);
        }
        //已绑定
        return ResponseEntity.ok().body(httpBody_error_ok_Bound_email_same[2]);
    }

    /*API*/
    //    根据email查询用户有无伴侣
    @GetMapping("/check_if_there_is_a_binding")
    public ResponseEntity<Boolean> Check_if_there_is_a_binding(@RequestParam(value = "email") String email) {
        // with_or_without_binding  有伴侣返回true 无伴侣返回false
        if (with_or_without_binding(email)) {
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.ok().body(false);
    }

    /*function*/
    //  根据邮箱 判断双方  有无  绑定情侣关系
    //  如果用户不存在或邮箱返回
    //  如果已绑定或未绑定，返回双方的user类
    public Object whether_binding(String user_email, String love_email) {
        //判断邮箱是否相同
        if (!user_email.equals(love_email)) {
            //根据email 判断用户存不存在
            User user = according_to_email_to_id(user_email);
            User love = according_to_email_to_id(love_email);
            if (user != null && love != null) {
                //要返回的用户类
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("user_email", user);
                userMap.put("love_email", love);
                //判断双方用户有无绑定其他关系
                if (with_or_without_binding(user_email) && with_or_without_binding(love_email)) {
                    //bound 已绑定
                    userMap.put("relationships", httpBody_error_ok_Bound_email_same[2]);
                    return userMap;
                }
                //无 绑定
                userMap.put("relationships", httpBody_error_ok_Bound_email_same[1]);
                return userMap;
            }
            //用户不存在
            return httpBody_error_ok_Bound_email_same[0];
        }
        //邮箱重复
        return httpBody_error_ok_Bound_email_same[3];
    }

    /*function*/
    //    根据email 判断此用户有无伴侣
    public Boolean with_or_without_binding(String email) {
        //1.判断用户是否存在
        //2.得到对面的id
        //3.在查询此 user_love 伴侣表中有无 伴侣
        //4.有伴侣返回true 无伴侣返回false
        //获取用户类
        User user_or_null = according_to_email_to_id(email);
        if (user_or_null != null) {
            //2.
            user_loveExample = new User_loveExample();
            user_love_criteria = user_loveExample.createCriteria();
            user_love_criteria.andUserIdEqualTo(user_or_null.getId());
            //3.
            List<User_love> user_loves_list = user_loveMapper.selectByExample(user_loveExample);
            //4.
            return user_loves_list.get(0).getLoveId() != null;
        }
        return false;
    }

    /*function*/
    //    根据邮箱查找 id ;如果找到了，返回用户类，如果没有，返回null
    public User according_to_email_to_id(String email) {
        userExample = new UserExample();
        user_Criteria = userExample.createCriteria();
        user_Criteria.andEmailEqualTo(email);
        List<User> userList = userMapper.selectByExample(userExample);

        if (userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }
}
