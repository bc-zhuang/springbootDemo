package com.example.springbootDemo.sys.service;

import com.example.springbootDemo.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 123
 * @since 2023-04-10
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    String login(String userName, String passWord) throws Exception;

    Map<String, Object> getJwtInfo(String jwt);
}
