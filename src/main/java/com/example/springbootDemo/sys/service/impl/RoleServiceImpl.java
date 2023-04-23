package com.example.springbootDemo.sys.service.impl;

import com.example.springbootDemo.sys.entity.Role;
import com.example.springbootDemo.sys.mapper.RoleMapper;
import com.example.springbootDemo.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 123
 * @since 2023-04-11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
