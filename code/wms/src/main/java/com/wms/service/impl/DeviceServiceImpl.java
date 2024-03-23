package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.mqtt.SpringUtil;
import com.wms.entity.Device;
import com.wms.mapper.DeviceMapper;
import com.wms.service.DeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {
    DeviceMapper deviceMapper = SpringUtil.getBean(DeviceMapper.class);
    @Override
    public IPage pageCC(IPage<Device> page, Wrapper wrapper) {
        return deviceMapper.pageCC(page,wrapper);
    }
    
    public void updateAlert(Integer deviceid, Integer alert) {
        Device device = deviceMapper.selectById(deviceid);
        if(device == null)return;
        if(device.getActive() == 0 && alert == 0){
            device.setActive(1);
            deviceMapper.updateById(device);
        }else if(device.getActive() == 1 && alert == 1){
            device.setActive(0);
            deviceMapper.updateById(device);
        }
    }

}
