package com.wms.common;

import lombok.Data;
@Data
public class Result {

    private int code;//编码 1/0
    private String msg;//成功/失败
    private Long total;//总记录数
    private Object data;//数据
    
    
    public static Result res(int code){
        return result(code,0L,null);
    }
    

    public static Result res(int code, Object data){
        return result(code,0L,data);
    }

    public static Result res(int code, Object data, Long total){
        return result(code,total,data);
    }

    private static Result result(int code,Long total,Object data){
        Result res = new Result();
        res.setData(data);
//        res.setMsg(msg);
        res.setCode(code);
        res.setTotal(total);
        return res;
    }

}
