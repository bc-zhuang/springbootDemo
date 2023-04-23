package com.example.springbootDemo.common.vo;

import lombok.Data;

@Data
public class BaseResponse {
    private String code;

    private String msg;

    public static BaseResponse success() {
        return new BaseResponse("20000", "成功");
    }

    public static BaseResponse fail() {
        return new BaseResponse("1", "失败");
    }
    //构造器、getter、setter方法
    BaseResponse(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
