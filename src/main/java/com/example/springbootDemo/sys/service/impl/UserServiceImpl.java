package com.example.springbootDemo.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springbootDemo.common.vo.Result;
import com.example.springbootDemo.sys.entity.User;
import com.example.springbootDemo.sys.mapper.RoleMapper;
import com.example.springbootDemo.sys.mapper.UserMapper;
import com.example.springbootDemo.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootDemo.util.JwtUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 123
 * @since 2023-04-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private JwtUtil jwtUtil;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Override
    public Map<String, Object> login(User user) {
        // 根据用户名和密码登录
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        // eq为等于
        queryWrapper.eq(User::getUsername,user.getUsername());  // username = ""
        //        1. 其中User::getUsername的意思就相当于：
        //        1.1 实例化一个User对象
        //        User user = new User();
        //        1.2 调用对象User的get方法，这里调用的是getUsername:
        //        user.getUsername();
        //        2.eq方法相当于赋值“=”
        //        即将Username的值为参数user.getUsername()，注意此时使用的是get方法而不是set方法
        queryWrapper.eq(User::getPassword,user.getPassword());  // password = ""

//        User loginUser = this.baseMapper.selectOne(queryWrapper);
        User loginUser = userMapper.selectOne(queryWrapper);

//        roleMapper.selectList(null);

        // 结果不为空，则生成token，并将用户信息存入redis
        if(loginUser != null){
            // 暂时用UUID，终极方案jwt
            String key = "user:" + UUID.randomUUID();

            // 存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key, loginUser, 30, TimeUnit.MINUTES);

            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }

        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        // 根据token获取用户信息，redis
        Object obj = redisTemplate.opsForValue().get(token);
        if(obj != null){
            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getUsername());
            data.put("avatar", loginUser.getAvatar());

            // 角色
            List<String> roleList = this.baseMapper.getRoleNamesByUserId(loginUser.getId());
            data.put("roles",roleList);

            return data;
        }
        return null;
    }

    public String login(String userName, String passWord) throws Exception {
        //登录验证
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,userName);
        queryWrapper.eq(User::getPassword,passWord);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        //如果能查出，则表示账号密码正确，生成jwt返回
        String uuid = UUID.randomUUID().toString().replace("-", "");
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", user.getUsername());
        map.put("password", user.getPassword());

        String key = jwtUtil.createJWT(uuid, "login subject", 0L, map);

        redisTemplate.opsForValue().set(key, user, 30, TimeUnit.MINUTES);

//        return jwtUtil.createJWT(uuid, "login subject", 0L, map);
        return key;
    }

    @Override
    public Map<String, Object> getJwtInfo(String jwt) {
        Object obj = redisTemplate.opsForValue().get(jwt);
        if(obj != null){
            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getUsername());
            //data.put("password", loginUser.getPassword());
            data.put("avatar", loginUser.getAvatar());

            // 角色
            List<String> roleList = this.baseMapper.getRoleNamesByUserId(loginUser.getId());
            data.put("roles",roleList);

            return data;
        }
        return null;
    }
}
