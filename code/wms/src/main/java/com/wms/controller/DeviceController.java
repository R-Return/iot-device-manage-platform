package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Device;
import com.wms.service.DeviceService;
import com.wms.service.impl.DeviceServiceImpl;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    //新增
    @PostMapping("/save")
    public Result register(@RequestBody Device device){
        System.out.println("device=="+device);
        return deviceService.save(device)?Result.res(1):Result.res(0);
    }
    
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return deviceService.removeById(id)?Result.res(1):Result.res(0);
    }
    
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Device device){
        return deviceService.updateById(device)?Result.res(1):Result.res(0);
    }
    
    @GetMapping("/findByName")
    public Result findByNo(@RequestParam String name){
        List list = deviceService.lambdaQuery().eq(Device::getName,name).list();
        return list.size()>0?Result.res(1,list):Result.res(0);
    }
    
 
//    public void updateAlert(Integer deviceId, Integer alert) {
//        DeviceServiceImpl deviceService = new DeviceServiceImpl();
//        System.out.println("deviceId=="+deviceId);
//        Device device = deviceService.getById(deviceId);
//        System.out.println("device=="+device);
//        if(alert == 1 && device.getActive() == 1){
//            device.setActive(0);
//        }else if(alert == 0 && device.getActive() == 0){
//            device.setActive(1);
//        }
//        deviceService.updateById(device);
//
//    }
        @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        Integer userId = (Integer) param.get("userId");
        System.out.println("userId=="+userId);
        String name = (String)param.get("name");
        Integer type = param.get("type") == null?0:(Integer)param.get("type");

        Page<Device> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Device> lambdaQueryWrapper = new LambdaQueryWrapper();
        
       lambdaQueryWrapper.eq(Device::getUserid,userId);
       
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Device::getName,name);
        }
        if(type != 0){
            lambdaQueryWrapper.eq(Device::getType,type);
        }
        IPage result = deviceService.pageCC(page,lambdaQueryWrapper);
        System.out.println("total=="+result.getTotal());

        return Result.res(1, result.getRecords(),result.getTotal());
    }
    
    @PostMapping("/countType")
    public Result countType(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        Integer userId = (Integer) param.get("userId");
        System.out.println("userId=="+userId);
        List<Integer> l = new ArrayList<>();
        for(int i = 1; i <= 4; i++){
            LambdaQueryWrapper<Device> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(Device::getUserid,userId);
            lambdaQueryWrapper.eq(Device::getType,i);
            int count = deviceService.count(lambdaQueryWrapper);
            l.add(count);
        }
        
        //查询总数
        LambdaQueryWrapper<Device> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Device::getUserid,userId);
        l.add(deviceService.count(lambdaQueryWrapper));
        
        //查询活跃数
        lambdaQueryWrapper.eq(Device::getActive,1);
        l.add(deviceService.count(lambdaQueryWrapper));

        return Result.res(1, l);
    }
    
}
