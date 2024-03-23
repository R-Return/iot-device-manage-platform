package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MessageForm对象", description="")
public class MessageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    @ApiModelProperty(value = "设备Id")
    @TableField("deviceId")
    private Integer deviceid;

    @ApiModelProperty(value = "是否触发警报，0表示正常，1表示触发警报")
    private Integer alert;

    @ApiModelProperty(value = "警报信息")
    private String info;

    @ApiModelProperty(value = "纬度")
    private Double lat;

    @ApiModelProperty(value = "经度")
    private Double lng;

    @ApiModelProperty(value = "时间戳")
    @TableField("timeStamp")
    private String timestamp;

    @ApiModelProperty(value = "设备发送的值")
    private Integer value;


}
