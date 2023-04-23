package com.example.springbootDemo.sys.mapper;

import com.example.springbootDemo.sys.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 123
 * @since 2023-04-11
 */
@Repository("roleMapper")
public interface RoleMapper extends BaseMapper<Role> {

    public void findRoleById(Integer id);

}
