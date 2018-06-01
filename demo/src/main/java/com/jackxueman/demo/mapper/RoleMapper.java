package com.jackxueman.demo.mapper;


import com.jackxueman.demo.mapper.generate.RoleSrcMapper;
import com.jackxueman.demo.pojo.entity.Role;

public interface RoleMapper extends RoleSrcMapper {

    /**
     * 插入：生成主键，用于事务方法
     *
     * @param role
     * @return
     */
    @Override
    int insert(Role role);
}
