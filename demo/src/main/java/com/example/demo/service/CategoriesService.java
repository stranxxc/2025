package com.example.demo.service;

import com.example.demo.entity.Categories;
import com.example.demo.mapper.CategoriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesMapper categoriesMapper;

    public List<Categories> selectAllCategories() {
        List<Categories> categories = categoriesMapper.selectAllCategories();
        System.out.println("从数据库获取的分类数据数量"+categories.size());

        List<Categories> roots = new ArrayList<>();
        HashMap<Integer,Categories> cateMap = new HashMap<>();
        categories.forEach(item->{
            cateMap.put(item.getId(), item);
            if(item.getParentId()==0){
                roots.add(item);
            }else{
                cateMap.get(item.getParentId()).addChildren(item);
            }
        });
        return roots;
    }

    public int addCategories(Categories categories) {
        LocalDateTime now = LocalDateTime.now();
        categories.setCreateTime(now);
        return categoriesMapper.addCategories(categories);
    }
    public int updateCategories(Categories categories) {
        categories.setUpdateTime(LocalDateTime.now());
        return categoriesMapper.updateCategories(categories);
    }
    public int deleteCategories(int id) {
        List<Categories> allCategories = categoriesMapper.selectAllCategories();
        boolean hasChildren=allCategories.stream().anyMatch(category->category.getParentId()!=null&&category.getParentId().equals(id));
        if(hasChildren){
            throw new RuntimeException("该分类下存在子分类，请先删除子分类");
        }
        return categoriesMapper.deleteCategories(id);
    }
    public Categories getCategoryById(int id) {
        List<Categories> allCategories = categoriesMapper.selectAllCategories();
        return allCategories.stream().filter(category->category.getId().equals(id)).findFirst().orElse(null);
    }
}

