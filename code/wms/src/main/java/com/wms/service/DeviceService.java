package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Device;
import com.baomidou.mybatisplus.extension.service.IService;


public interface DeviceService extends IService<Device> {
    
    IPage pageCC(IPage<Device> page, Wrapper wrapper);
    void updateAlert(Integer deviceid, Integer alert);
}
