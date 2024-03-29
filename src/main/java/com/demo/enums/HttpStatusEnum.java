package com.demo.enums;

/**
 * 请求返回值枚举
 */
public enum HttpStatusEnum {

    SUCCESS(200, "请求成功"), BAD_REQUEST(400, "报文语法错误"), UNAUTHORIZED(401, "用户未认证"),
    FORBIDDEN(403, "拒绝访问"), NOT_FOUND(404, "资源不存在"), INTERNAL_SERVER_ERROR(500, "服务器错误");

    private int code;
    private String info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private HttpStatusEnum(int code, String info){
        this.code = code;
        this.info = info;
    }

    public static HttpStatusEnum getInfoByCode(int code){
        for(HttpStatusEnum status :values()){
            if(status.getCode() == code){
                return status;
            }
        }
        return null;
    }
}
