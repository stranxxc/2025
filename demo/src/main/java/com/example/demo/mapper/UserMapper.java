package com.example.demo.mapper;


import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User userMapperLogin(User user);
    List<User> selectAllUser(@Param("page") int page, @Param("pageSize") int pageSize);

    int selectUserTotal();

    int insertUser(User user);

    int updateUser(User user);

    int deleteUser(int id);


    int addUser(User user);

    List<User> selectUsersByIds(List<Integer> ids);
    User selectUserById(Integer id);
}
