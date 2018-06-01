package com.jackxueman.demo.pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

/**
 * 获取用户登录信息响应VO
 */
public class AuthLoginInfoRespVO {

    private Long id;

    private String name;

    private String ip;

    private String city;

    private String identity;

    private Boolean isAdmin;

    private Set<String> privList;

    private Long departmentId;

    private Integer departmentLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    @JsonProperty("isAdmin")
    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Set<String> getPrivList() {
        return privList;
    }

    public void setPrivList(Set<String> privList) {
        this.privList = privList;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(Integer departmentLevel) {
        this.departmentLevel = departmentLevel;
    }

    @Override
    public String toString() {
        return "AuthLoginInfoRespVO{" + "id=" + id + ", name='" + name + '\'' + ", ip='" + ip + '\'' + ", city='"
                + city + '\'' + ", identity='" + identity + '\'' + ", isAdmin=" + isAdmin + ", privList=" +
                privList + ", departmentId=" + departmentId + ", departmentLevel=" + departmentLevel + '}';
    }
}
