package com.qiuzhiguo.springdemo.util;

/**
 * @author feng
 * @date 2019/11/5
 * discription:*
 * 返回结果集规范
 *      code:0成功 其它，失败
 *      msg:string
 *      data:传递的数据(object)
 */
public class Res {
    private String msg;
    private Object data;
    private Integer code;

    /**
     * 检查结果
     * @param res
     * @return
     */
    public static boolean checkResult(Res res){
        if (res.getCode()==0||res.getCode().equals(0)||res.getCode().equals("0"))
            return true;
        return false;
    }

    public Res(Integer code, String msg, Object data){
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static Res ok(){
        return new Res(0,null,null);
    }

    public static Res ok(Object data){
        return new Res(0,null,data);
    }

    public static Res ok(String msg,Object data){
        return new Res(0,msg,data);
    }

    public static Res fail(Integer code,String msg,Object data){
        return new Res(code,msg,data);
    }

    public static Res fail(){
        return new Res(1,null,null);
    }
    public static Res fail(String msg){
        return new Res(1,msg,null);
    }
    public static Res fail(String msg,Object data){
        return new Res(1,msg,data);
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
