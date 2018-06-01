package com.jackxueman.demo.common.enums;

/**
 * 用户状态枚举
 */
public enum UserStatusEnum {

    NORMAL(0, "正常"),
    FORBIDDEN(1, "禁用");

    private int code;

    private String value;

    UserStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static String getValueByCode(int code) {
        for (UserStatusEnum statusEnum : values()) {
            if (statusEnum.getCode() == code) {
                return statusEnum.getValue();
            }
        }
        return null;
    }
}
