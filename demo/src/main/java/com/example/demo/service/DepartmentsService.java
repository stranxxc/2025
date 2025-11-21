package com.example.demo.service;

import com.example.demo.entity.Departments;
import com.example.demo.entity.User;
import com.example.demo.mapper.DepartmentsMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentsService {
    @Autowired
    private DepartmentsMapper departmentsMapper;

    @Autowired // 添加缺失的注解
    private UserMapper userMapper;

    public List<Departments> selectAllDepartments() {
        List<Departments> departments = departmentsMapper.selectAllDepartments();
        System.out.println("从数据库中获取的部门数据量" + departments.size());

        // 获取所有manager_id
        List<Integer> managerIds = departments.stream()
                .map(Departments::getManagerId)
                .filter(id -> id != null && id > 0)
                .distinct()
                .collect(Collectors.toList());

        // 从user表获取负责人信息
        Map<Integer, String> managerNameMap = new HashMap<>();
        if (!managerIds.isEmpty()) {
            List<User> users = userMapper.selectUsersByIds(managerIds);
            for (User user : users) {
                managerNameMap.put(user.getId(), user.getRealName());
            }
        }

        // 设置负责人姓名
        for (Departments dept : departments) {
            if (dept.getManagerId() != null && dept.getManagerId() > 0) {
                String managerName = managerNameMap.get(dept.getManagerId());
                if (managerName != null) {
                    dept.setManagerName(managerName);
                }
            }
        } // 修复：添加缺失的右括号，关闭for循环

        // 构建树形结构
        List<Departments> roots = new ArrayList<>();
        HashMap<Integer, Departments> deptMap = new HashMap<>();

        // 第一次遍历：将所有部门放入map
        departments.forEach(item -> {
            deptMap.put(item.getId(), item);
            // 初始化子部门列表
            if (item.getChildren() == null) {
                item.setChildren(new ArrayList<>());
            }
        });

        // 第二次遍历：构建树形结构
        departments.forEach(item -> {
            if (item.getParentId() == 0) {
                roots.add(item);
            } else {
                Departments parent = deptMap.get(item.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(item);
                }
            }
        });

        return roots;
    }

    public int addDepartments(Departments departments) {
        LocalDateTime now = LocalDateTime.now();
        departments.setCreateTime(String.valueOf(now));
        if(departments.getDeptCode()==null||departments.getDeptCode().trim().isEmpty()){
            departments.setDeptCode("DEPT_"+System.currentTimeMillis());
        }
        return departmentsMapper.addDepartment(departments);
    }

    public int deleteDepartments(int id) {
        List<Departments> allDepartments = departmentsMapper.selectAllDepartments();
        boolean hasChildren = allDepartments.stream()
                .anyMatch(dept -> dept.getParentId() == id);
        if (hasChildren) {
            throw new RuntimeException("该部门下存在子部门，请先删除子部门");
        }
        return departmentsMapper.deleteDepartment(id);
    }

    // 修复：将此方法移出 deleteDepartments 方法
    public Departments getDepartmentsById(int id) {
        Departments departments = departmentsMapper.getDepartmentsById(id);
        if (departments != null && departments.getManagerId() != null && departments.getManagerId() > 0) {
            User user = userMapper.selectUserById(departments.getManagerId());
            if (user != null) {
                departments.setManagerName(user.getRealName());
            }
        }
        return departments;
    }

    public int updateDepartments(Departments departments) {
        // 检查部门是否存在
        Departments existingDept = getDepartmentsById(departments.getId());
        if (existingDept == null) {
            throw new RuntimeException("部门不存在");
        }

        // 检查父部门不能是自己
        if (departments.getParentId() == departments.getId()) {
            throw new RuntimeException("父部门不能是自己");
        }

        // 检查循环引用 - 防止将部门设置为自己的子部门
        List<Departments> allDepartments = departmentsMapper.selectAllDepartments();
        if (isCircularReference(departments.getId(), departments.getParentId(), allDepartments)) {
            throw new RuntimeException("不能创建循环部门引用");
        }

        return departmentsMapper.updateDepartment(departments);
    }

    // 辅助方法：检查是否会导致循环引用
    private boolean isCircularReference(int deptId, int newParentId, List<Departments> allDepartments) {
        if (newParentId == 0) return false;

        int currentId = newParentId;
        while (currentId != 0) {
            if (currentId == deptId) {
                return true;
            }
            Optional<Departments> currentDept = allDepartments.stream()
                    .filter(d -> d.getId() == currentId)
                    .findFirst();
            if (currentDept.isPresent()) {
                currentId = currentDept.get().getParentId();
            } else {
                break;
            }
        }
        return false;
    }
}