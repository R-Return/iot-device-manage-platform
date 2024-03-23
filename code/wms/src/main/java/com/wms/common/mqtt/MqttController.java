package com.wms.common.mqtt;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class MqttController {
    @ResponseBody
    @GetMapping(value = "/push")
    public Object push(@Param("topic") String topic,
                       @Param("msg") String msg) {
        MqttConsumer.publish(topic, msg);
        return "发布成功";
    }
    
    /**
     * 测试接收消息
     */
    @ResponseBody
    @GetMapping(value = "/subscribe")
    public Object subscribe(@Param("topic") String topic,
                            @Param("qus") int qus) {
        MqttConsumer.subscribe(topic, qus);
        return "订阅主题"+topic+"成功";
    }
}
