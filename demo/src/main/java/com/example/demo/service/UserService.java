package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User userServiceLogin(User user){
        System.out.println("用户登录service");
        return userMapper.userMapperLogin(user);
    }

    public HashMap<String, Object> selectAllUser(  User user){
       ;
       HashMap<String, Object> userMap = new HashMap<>();
       List<User> users = userMapper.selectAllUser(user.getPage(),user.getPageSize());
       int total = userMapper.selectUserTotal();
       userMap.put("total",total);
       userMap.put("data",users);
        return  userMap;
    }

    public int addUser(User user){
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(String.valueOf(now));
        String uuid = UUID.randomUUID().toString().replace("-","");
                user.setUserCode(uuid);
        return userMapper.addUser(user);
    }

    public int updateUser(User user){
        return userMapper.updateUser(user);
    }

    public int deleteUser(int id){
        return userMapper.deleteUser(id);
    }
}
