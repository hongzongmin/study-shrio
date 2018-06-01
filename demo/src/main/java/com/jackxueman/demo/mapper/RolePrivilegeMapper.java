package com.jackxueman.demo.mapper;

import com.jackxueman.demo.mapper.generate.RolePrivilegeSrcMapper;
import com.jackxueman.demo.pojo.entity.RolePrivilege;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * RolePrivilegeSrcMapper
 */
public interface RolePrivilegeMapper extends RolePrivilegeSrcMapper {

    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    int insertBatch(@Param("list") List<RolePrivilege> records);

    /**
     * 按权限码获取角色id
     *
     * @param code 权限码
     * @return
     */
    @Select("select distinct(role_id) from role_privilege where privilege_code = #{code} and is_delete = 0")
    List<Long> selectRoleIdByPrivCode(@Param("code") String code);
    
}
