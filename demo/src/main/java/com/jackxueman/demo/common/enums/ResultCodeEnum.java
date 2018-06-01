package com.jackxueman.demo.common.enums;

/**
 * result code
 */
public enum ResultCodeEnum {

    /**
     * 成功
     **/
    SUCCESS(0, "成功"),

    /**
     * 鉴权相关错误码
     **/
    AUTH_FAIL(401, "认证失败"),
    ACCESS_DENIED(401, "授权失败"),
    SESSION_TIMEOUT(401, "SESSION超时"),
    NO_OPERATE_PERMISSION(403, "没有操作权限"),
    SESSION_INVALID(405, "SESSION失效"),

    /**
     * 异常错误码
     */
    UN_KNONW_ERROR(10000, "未知异常"),
    METHOD_UN_IMPL(10001, "当前操作未实现"),
    ILLEGAL_REQ(10004, "非法请求"),

    /**
     * 网络相关错误码
     */
    NETWORK_EXP(10020, "网络异常"),
    NETWORK_TIMEOUT(10021, "网络超时"),
    EXTERNAL_SERVICE__EXP(10022, "外部服务器异常"),
    EXTERNAL_SERVICE_TIMEOUT(10023, "外部服务器超时"),

    /**
     * 数据库相关错误码
     */
    DB_ERROR(10040, "数据库操作异常"),
    DB_TIMEOUT(10041, "数据库访问超时"),
    DATA_EXISTS(10042, "数据已存在"),
    DATA_NOT_EXISTS(10043, "数据不存在"),

    /**
     * 参数相关错误码
     */
    PARAM_NIL(10060, "参数不能为空值"),
    PARAM_ERR(10061, "参数错误"),
    PARAM_OUT_RANGE(10062, "参数值超出允许范围"),
    PARAM_OUT_LEN(10063, "参数值长度超过限制"),

    /**
     * 公共接口错误码
     */
    FILE_EMPTY(10070, "文件内容为空"),
    UNSUPPORT_FILE_TYPE(10071, "不支持的文件类型"),
    FILE_READ_ERROR(10072, "文件读取失败"),
    FILE_NOT_EXISTS(10073, "文件不存在"),
    FILE_SIZE_EXCEED(10074, "文件过大"),
    FILE_DATA_SIZE_EXCEED(10075, "单次上传数据量超过限定值"),

    /**
     * 鉴权相关错误码
     */
    NAME_PASSWORD_ERROR(20000, "用户名或密码错误"),
    USERNAME_NOT_EXISTS(20001, "用户名不存在"),
    USER_INVALID(20002, "账号已冻结"),
    IP_LIMIT(20003, "IP限制"),
    MAC_LIMIT(20004, "MAC限制"),
    ROLE_NOT_ASSIGNED(20005, "用户未分配角色"),
    DEPARTMENT_NOT_ASSIGNED(20006, "用户未分配部门"),
    NAME_PASSWORD_ERROR_THREE_TIMES(20007, "用户名密码输错3次"),

    /**
     * 角色相关错误码
     */
    ROLE_NOT_EXISTS(20100, "角色不存在"),
    ROLE_NAME_EXISTS(20101, "角色名称已存在"),

    /**
     * 用户相关错误码
     */
    USER_NOT_EXISTS(20200, "用户不存在"),
    USER_IDENTITY_EXISTS(20201, "用户身份证已存在");


    private int code;
    private String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
