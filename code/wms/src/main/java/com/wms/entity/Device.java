package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Device对象", description="")
public class Device implements Serializable {

//    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备描述")
    private String description;

    @ApiModelProperty(value = "用户id")
    private Integer userid;

    @ApiModelProperty(value = "设备类型,默认为其他设备（4）车载设备（1）、智能家居（2）、可穿戴设备（3）")
    private Integer type;

    @ApiModelProperty(value = "是否活跃，默认为1")
    private Integer active;


}
