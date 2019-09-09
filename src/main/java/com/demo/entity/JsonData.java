package com.demo.entity;


import com.demo.enums.HttpStatusEnum;

/**
 * @author jack
 * 在此类中定义Json数据类型属性和格式
 */
public class JsonData {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private Object data;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JsonData(){
    }

    public JsonData(Integer code, Object data, String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    /**
     * @return 成功，包含默认200状态码和请求成功描述
     */
    public static JsonData buildSuccess(){
        return new JsonData(HttpStatusEnum.SUCCESS.getCode(), null, HttpStatusEnum.SUCCESS.getInfo());
    }

    /**
     * @param data 数据
     * @return 成功，包含默认200状态码和请求成功描述
     */
    public static JsonData buildSuccess(Object data){
        return new JsonData(HttpStatusEnum.SUCCESS.getCode(), data, HttpStatusEnum.SUCCESS.getInfo());
    }

    /**
     * @param data 数据
     * @param msg 描述信息
     * @return 成功，包含默认200状态码
     */
    public static JsonData buildSuccess(Object data, String msg){
        return new JsonData(HttpStatusEnum.SUCCESS.getCode(), data, msg);
    }

    /**
     * @param data 数据
     * @param code 状态码
     * @return 成功，不包含描述
     */
    public static JsonData buildSuccess(Object data, int code){
        return new JsonData(code, data, null);
    }

    public static JsonData buildError(Integer code, String msg){
        return new JsonData(code, null, msg);
    }

    @Override
    public String toString(){
        return "JsonData [code=" + code + ", data=" + data + ", msg=" + msg
                + "]";
    }
}
