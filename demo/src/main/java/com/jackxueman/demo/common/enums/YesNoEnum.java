package com.jackxueman.demo.common.enums;

/**
 * 表示是/否的枚举
 */
public enum YesNoEnum {

    NO(0, "否"),
    YES(1, "是");

    private int code;

    private String value;

    YesNoEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
