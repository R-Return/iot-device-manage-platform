package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Device;
import com.wms.entity.MessageForm;
import com.wms.service.DeviceService;
import com.wms.service.MessageFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/message")
public class MessageFormController {
    
    @Autowired
    private MessageFormService messageFormService;
    
    @Autowired
    private DeviceService deviceService;
    
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        Integer deviceId =Integer.valueOf((String) param.get("deviceId"));
//        Integer alert = param.get("alert") == null?-1:Integer.parseInt((String) param.get("alert"));
        Integer alert = param.get("alert") == null?-1: (Integer) param.get("alert");
        System.out.println("alert=="+alert);
        Page<MessageForm> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        
        LambdaQueryWrapper<MessageForm> lambdaQueryWrapper = new LambdaQueryWrapper();
        
        lambdaQueryWrapper.eq(MessageForm::getDeviceid,deviceId);
        
        if(alert != -1){
            lambdaQueryWrapper.eq(MessageForm::getAlert,alert);
        }
        System.out.println(("准备查询"));
        IPage result = messageFormService.pageCC(page,lambdaQueryWrapper);
        System.out.println("total=="+result.getTotal());
        
        return Result.res(1, result.getRecords(),result.getTotal());
    }
    
    @PostMapping("/messageNum")
    public Result countType(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        Integer userId = (Integer) param.get("userId");
        System.out.println("userId==" + userId);
        //查询设备
        List list = deviceService.lambdaQuery().eq(Device::getUserid,userId).list();
        Integer messageNum = 0;
        if(list.size() != 0){
            for(int i = 0;i<list.size();i++){
                Device device = (Device) list.get(i);
                Integer deviceId = device.getId();
                System.out.println("deviceId=="+deviceId);
                //获得消息数
                messageNum += messageFormService.lambdaQuery().eq(MessageForm::getDeviceid,deviceId).count();
            }
        }
        return Result.res(1, messageNum);
        
    }
    

}
