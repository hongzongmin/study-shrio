package com.jackxueman.demo.pojo.response;

/**
 * 修改密码响应VO
 */
public class AuthResetPwdRespVO {

    private Boolean success;

    public AuthResetPwdRespVO(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "AuthResetPwdRespVO{" + "success=" + success + '}';
    }
}
