package com.jackxueman.demo.mapper.generate;


import com.jackxueman.demo.pojo.entity.Privilege;
import com.jackxueman.demo.pojo.entity.PrivilegeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrivilegeSrcMapper {
    long countByExample(PrivilegeExample example);

    int deleteByExample(PrivilegeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Privilege record);

    int insertSelective(Privilege record);

    List<Privilege> selectByExample(PrivilegeExample example);

    Privilege selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Privilege record, @Param("example") PrivilegeExample example);

    int updateByExample(@Param("record") Privilege record, @Param("example") PrivilegeExample example);

    int updateByPrimaryKeySelective(Privilege record);

    int updateByPrimaryKey(Privilege record);
}