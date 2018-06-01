package com.jackxueman.demo.common.exception;


import com.jackxueman.demo.common.enums.ResultCodeEnum;

/**
 * base exception
 */
public class BaseException extends RuntimeException {

    private int code;

    private String msg;

    public BaseException(ResultCodeEnum result) {
        super(result.getMsg());
        this.code = result.getCode();
        this.msg = result.getMsg();
    }

    public BaseException(ResultCodeEnum result, String external) {
        super(result.getMsg() + ":" + external);
        this.code = result.getCode();
        this.msg = result.getMsg() + ":" + external;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseException{" + "code=" + code + ", msg='" + msg + '\'' + "} " + super.toString();
    }
}
