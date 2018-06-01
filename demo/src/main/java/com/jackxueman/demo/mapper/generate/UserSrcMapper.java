package com.jackxueman.demo.mapper.generate;


import com.jackxueman.demo.pojo.entity.User;
import com.jackxueman.demo.pojo.entity.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserSrcMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}