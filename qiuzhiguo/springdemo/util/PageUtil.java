package com.qiuzhiguo.springdemo.util;

/**
 * @author feng
 * @date 2019/12/25
 * discription:*
 */
public class PageUtil {
    private Integer code;
    private String msg;
    private Object data;
    private Integer count;


    public PageUtil(){}

    public PageUtil(int code,String msg,Object data,int count){
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    public static PageUtil ok(Object data,int count){
        return new PageUtil(0,"",data,count);
    }
    public static PageUtil fail(String msg){
        return new PageUtil(1,msg,null,0);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
