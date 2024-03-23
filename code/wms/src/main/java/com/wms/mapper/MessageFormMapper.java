package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.Device;
import com.wms.entity.MessageForm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface MessageFormMapper extends BaseMapper<MessageForm> {
    IPage pageCC(IPage<MessageForm> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}

