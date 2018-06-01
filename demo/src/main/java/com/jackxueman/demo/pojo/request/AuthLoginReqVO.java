package com.jackxueman.demo.pojo.request;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 登录请求VO
 */
public class AuthLoginReqVO {

    @NotEmpty(message = "请输入身份证")
    private String identity;

    @NotEmpty(message = "请输入密码")
    private String password;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthLoginReqVO{" + "identity='" + identity + '\'' + ", password='" + password + '\'' + '}';
    }
}
