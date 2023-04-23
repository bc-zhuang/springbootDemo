package com.example.springbootDemo.sys.mapper;

import com.example.springbootDemo.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 123
 * @since 2023-04-10
 */
@Repository("userMapper")
public interface UserMapper extends BaseMapper<User> {

    public void findUserById(Integer id);
    public List<String> getRoleNamesByUserId(Integer userId);

}
