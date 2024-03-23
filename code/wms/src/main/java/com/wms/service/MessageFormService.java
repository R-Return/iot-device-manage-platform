package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.MessageForm;
import com.baomidou.mybatisplus.extension.service.IService;


public interface MessageFormService extends IService<MessageForm> {
    
    IPage pageCC(IPage<MessageForm> page, Wrapper wrapper);
    boolean msgHandle(String msg);
}
