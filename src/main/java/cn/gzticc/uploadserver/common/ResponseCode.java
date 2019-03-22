package cn.gzticc.uploadserver.common;

/**
 * 响应枚举类
 */
public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    ILLEGAL_ARGUMENT(2,"参数错误"),
    SYS_UNKNOWN_ERROR(-200,"系统繁忙,请稍后再试");

    private final int code;
    private final String msg;


    ResponseCode(int code,String desc){
        this.code = code;
        this.msg = desc;
    }

    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

}
