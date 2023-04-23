package com.example.springbootDemo.common.vo;

public class JwtResponse extends BaseResponse {

    private String jwtData;

    public static JwtResponse success(String jwtData) {
        BaseResponse success = BaseResponse.success();
        return new JwtResponse(success.getCode(), success.getMsg(), jwtData);
    }

    public static JwtResponse fail(String jwtData) {
        BaseResponse fail = BaseResponse.fail();
        return new JwtResponse(fail.getCode(), fail.getMsg(), jwtData);
    }

    JwtResponse(String code, String msg, String jwtData){
        super(code, msg);
        this.jwtData = jwtData;
    }

    public String getJwtData() {
        return jwtData;
    }

    public void setJwtData(String jwtData) {
        this.jwtData = jwtData;
    }
}
