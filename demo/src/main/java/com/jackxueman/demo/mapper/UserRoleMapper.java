package com.jackxueman.demo.mapper;


import com.jackxueman.demo.common.mybatis.SimpleSelectInExtendedLanguageDriver;
import com.jackxueman.demo.mapper.generate.UserRoleSrcMapper;
import com.jackxueman.demo.pojo.entity.UserRole;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserRoleMapper extends UserRoleSrcMapper {

    /**
     * 批量插入
     *
     * @param userRoles
     * @return
     */
    int insertBatch(@Param("list") List<UserRole> userRoles);

    /**
     * 按角色id获取用户id
     *
     * @param roleIds 角色id列表
     * @return
     */
    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select("select user_id from user_role where role_id in (#{roleIds}) and is_delete = 0")
    List<Long> selectUserIdByRoleIds(@Param("roleIds") List<Long> roleIds);
}
