package com.jackxueman.demo.mapper.generate;


import com.jackxueman.demo.pojo.entity.Role;
import com.jackxueman.demo.pojo.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleSrcMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}