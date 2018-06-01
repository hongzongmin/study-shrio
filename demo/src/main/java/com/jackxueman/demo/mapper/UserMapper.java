package com.jackxueman.demo.mapper;

import com.jackxueman.demo.common.mybatis.SimpleSelectInExtendedLanguageDriver;
import com.jackxueman.demo.mapper.generate.UserSrcMapper;
import com.jackxueman.demo.pojo.entity.User;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * UserMapper
 */
public interface UserMapper extends UserSrcMapper {

    /**
     * 插入记录，返回主键
     *
     * @param record
     * @return
     */
    @Override
    int insert(User record);

    int update(@Param("departmentIdList") List<Long> departmentIdList);

    /**
     * 按部门id查询用户id
     *
     * @param departmentId 部门id
     * @return
     */
    @Select("select id from user where department_id = #{departmentIds} and is_delete = 0")
    List<Long> selectIdByDepartmentId(@Param("departmentIds") Long departmentId);

    /**
     * 按部门id查询用户id
     *
     * @param departmentId 部门id
     * @return
     */
    @Select("select id from user where department_id = #{departmentId}")
    List<Long> selectIdByDepartmentIdContainsDeleted(@Param("departmentId") Long departmentId);

    /**
     * 按部门id查询用户id
     *
     * @param departmentIds 部门id列表
     * @return
     */
    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select("select id from user where department_id in (#{departmentIds}) and is_delete = 0")
    List<Long> selectIdByDepartmentIds(@Param("departmentIds") List<Long> departmentIds);

    /**
     * 按部门id查询用户id
     *
     * @param departmentIds 部门id列表
     * @return
     */
    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select("select id from user where department_id in (#{departmentIds})")
    List<Long> selectIdByDepartmentIdsContainsDeleted(@Param("departmentIds") List<Long> departmentIds);
}
