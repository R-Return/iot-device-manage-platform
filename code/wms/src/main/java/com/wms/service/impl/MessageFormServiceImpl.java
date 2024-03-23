package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wms.common.Result;
import com.wms.common.mqtt.SpringUtil;
import com.wms.controller.DeviceController;
import com.wms.entity.Device;
import com.wms.entity.Message;
import com.wms.entity.MessageForm;
import com.wms.mapper.MessageFormMapper;
import com.wms.service.DeviceService;
import com.wms.service.MessageFormService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Component
public class MessageFormServiceImpl extends ServiceImpl<MessageFormMapper, MessageForm> implements MessageFormService {
    private MessageFormMapper messageFormMapper = SpringUtil.getBean(MessageFormMapper.class);
    
    @Override
    public IPage pageCC(IPage<MessageForm> page, Wrapper wrapper) {
        return messageFormMapper.pageCC(page,wrapper);
    }
    
    private DeviceService deviceService = SpringUtil.getBean(DeviceService.class);
    @Override
    public boolean msgHandle(String msg) {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageForm messageForm = new MessageForm();
//        DeviceServiceImpl deviceService = new DeviceServiceImpl();
        try {
            if(!copy(objectMapper.readValue(msg,Message.class),messageForm))return false;
            deviceService.updateAlert(messageForm.getDeviceid(),messageForm.getAlert());
            messageFormMapper.insert(messageForm);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean copy(Message message, MessageForm messageForm) {
        try {
            messageForm.setAlert(message.getAlert());
            String name = message.getClientId();
            
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(name);
            if(!matcher.find())return false;
            Integer deviceId = Integer.parseInt(matcher.group());
            messageForm.setDeviceid(deviceId);
            
            messageForm.setInfo(message.getInfo());
            messageForm.setLat(message.getLat());
            messageForm.setLng(message.getLng());
            messageForm.setTimestamp(message.getTimestamp());
            messageForm.setValue(message.getValue());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
