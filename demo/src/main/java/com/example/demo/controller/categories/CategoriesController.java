package com.example.demo.controller.categories;

import com.example.demo.entity.Categories;
import com.example.demo.service.CategoriesService;
import com.example.demo.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired
    CategoriesService categoriesService;

    @GetMapping("/list")
    public R<List<Categories>> selectAllCategories() {
        List<Categories> categories = categoriesService.selectAllCategories();
        if (categories == null) {
            return R.success("暂无数据");
        }
        return R.success("查询成功", categories);
    }


    @PostMapping("/add")
    public R<Object> addCategory(@RequestBody Categories categories) {
        try {
            if (categories.getName()==null||categories.getName().trim().isEmpty()) {
                return R.failed("分类名称不能为空");
            }
            int affectedRows = categoriesService.addCategories(categories);
            if (affectedRows == 0) {
                return R.failed("添加失败");
            }
            return R.success("添加成功");
        }catch (Exception e){
            return R.failed("添加失败"+e.getMessage());
        }
    }


    @DeleteMapping("del/{id}")
    public R<Object> delCategory(@PathVariable int id) {
        try {
            int affectedRows = categoriesService.deleteCategories(id);
            if (affectedRows == 0) {
                return R.failed("删除失败");
            }
            return R.success("删除成功");
        }catch (Exception e){
            return R.failed(e.getMessage());
                            }
        }

    @PutMapping("/update")
    public R<Object> updateCategory(@RequestBody Categories categories) {
    try {
            if (categories.getName()==null||categories.getName().trim().isEmpty()) {
            return R.failed("分类名称不能为空");
            }
            int affectedRows = categoriesService.updateCategories(categories);
            if (affectedRows == 0) {
                return R.failed("更新失败");
            }
            return R.success("更新成功");
    }catch (Exception e){
        return R.failed("更新失败"+e.getMessage());
    }
    }

    @GetMapping("/{id}")
    public R<Categories> getCategoryById(@PathVariable int id) {
        try{
            Categories category=categoriesService.getCategoryById(id);
            if (category == null) {
                return R.failed("分类不存在");
            }
            return R.success("查询成功",category);
        }catch (Exception e){
            return R.failed("查询失败"+e.getMessage());
    }
}}
