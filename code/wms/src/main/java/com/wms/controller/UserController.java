package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Menu;
import com.wms.entity.User;
import com.wms.service.MenuService;
import com.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }
    @GetMapping("/findByName")
    public Result findByNo(@RequestParam String name){
        List list = userService.lambdaQuery().eq(User::getName,name).list();
        return list.size()>0?Result.res(1,list):Result.res(0);
    }
    
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateById(user)?Result.res(1):Result.res(0);
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return userService.removeById(id)?Result.res(1):Result.res(0);
    }
    
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list = userService.lambdaQuery()
                .eq(User::getName,user.getName())
                .eq(User::getPassword,user.getPassword()).list();


        if(list.size()>0){
            User user1 = (User)list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright,user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menuList);
            return Result.res(1,res);
        }
        return Result.res(0);
    }
    
    //注册
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.save(user)?Result.res(1):Result.res(0);
    }
    
    
    //修改信息
    @PostMapping("/change")
    public Result change(@RequestBody User user){
        return userService.updateById(user)?Result.res(1):Result.res(0);
    }
    

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }


}
