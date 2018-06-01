package com.jackxueman.demo.mapper.generate;


import com.jackxueman.demo.pojo.entity.RolePrivilege;
import com.jackxueman.demo.pojo.entity.RolePrivilegeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePrivilegeSrcMapper {
    long countByExample(RolePrivilegeExample example);

    int deleteByExample(RolePrivilegeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RolePrivilege record);

    int insertSelective(RolePrivilege record);

    List<RolePrivilege> selectByExample(RolePrivilegeExample example);

    RolePrivilege selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RolePrivilege record, @Param("example") RolePrivilegeExample example);

    int updateByExample(@Param("record") RolePrivilege record, @Param("example") RolePrivilegeExample example);

    int updateByPrimaryKeySelective(RolePrivilege record);

    int updateByPrimaryKey(RolePrivilege record);
}