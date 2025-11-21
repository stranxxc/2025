package com.example.demo.controller.user;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public R<User> login(@RequestBody User user){
        User result = userService.userServiceLogin(user);
        if (result == null){
            return R.failed("登陆失败");
        }
        return R.success("登陆成功",result);
    }
    @GetMapping("/list")
    public R<HashMap<String, Object>> selectUsers(User user){
        if (user.getPageSize() <=  0||user.getPage() <= 0) {
           user.setPage(1);
            user.setPageSize(1);
        }
        user.setPage((user.getPage() - 1) * user.getPageSize());
        HashMap<String, Object> result = userService.selectAllUser(user);
        if (result == null){
            return R.success("暂无数据");
        }
        return R.success("查询成功",result);
    }

    @PostMapping("/add")
    public R<Object> addUser(@RequestBody User user){
        int affectRows = userService.addUser(user);
        if(affectRows==0){
            return R.failed("添加失败");
        }
        return R.success("添加成功");
    }

    @PutMapping("/update")
    public R<Object> updateUser(@RequestBody User user){
        int affectRows = userService.updateUser(user);
        if(affectRows==0){
            return R.failed("添加失败");
        }
        return R.success("添加成功");
    }

    @DeleteMapping("/del/{id}")
    public R<Object> deleteUser(@PathVariable int id){
        int affectRows = userService.deleteUser(id);
        if(affectRows==0){
            return R.failed("添加失败");
        }
        return R.success("添加成功");
    }

}

