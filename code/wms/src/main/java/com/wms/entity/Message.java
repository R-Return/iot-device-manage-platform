package com.wms.entity;

import lombok.Data;

@Data
public class Message {
    private Integer alert;
    private String clientId;
    private String info;
    private Double lat;
    private Double lng;
    private String timestamp;
    private Integer value;
}
