package com.example.demo.mapper;

import com.example.demo.entity.Categories;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CategoriesMapper {
    List<Categories> getCategories();

    int addCategories(Categories categories);

    int updateCategories(Categories categories);

    int deleteCategories(int id);

    List<Categories> selectAllCategories();
}