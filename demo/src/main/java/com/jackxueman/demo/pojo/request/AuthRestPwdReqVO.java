package com.jackxueman.demo.pojo.request;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 重置密码请求VO
 */
public class AuthRestPwdReqVO {

    @NotEmpty(message = "请输入原密码")
    private String oldPassword;

    @NotEmpty(message = "请输入新密码")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "AuthRestPwdReqVO{" + "oldPassword='" + oldPassword + '\'' + ", newPassword='" + newPassword + '\'' + '}';
    }
}
