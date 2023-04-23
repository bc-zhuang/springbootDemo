package com.example.springbootDemo.sys.controller;

import com.example.springbootDemo.common.vo.JwtResponse;
import com.example.springbootDemo.common.vo.Result;
import com.example.springbootDemo.sys.entity.User;
import com.example.springbootDemo.sys.service.IUserService;
import com.example.springbootDemo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 123
 * @since 2023-04-10
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Resource
    private JwtUtil jwtUtil;

    @GetMapping("/all")
    public Result<List<User>> getAllUsers(){
        List<User> list = userService.list();
        return Result.success(list,"查询成功");
    }

    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data = userService.login(user);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20002, "用户名或密码错误");

    }

    @PostMapping("/login-jwt")
    public JwtResponse login(@RequestParam(name = "userName") String userName,
                             @RequestParam(name = "passWord") String passWord){
        String jwt = "";
        try {
            jwt = userService.login(userName, passWord);
            return JwtResponse.success(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            return JwtResponse.fail(jwt);
        }
    }

    @RequestMapping("/test")
    public Result<Map<String, Object>> test(@RequestParam("jwt") String jwt) {
        //这个步骤可以使用自定义注解+AOP编程做解析jwt的逻辑，这里为了简便就直接写在controller里
//        Claims claims = jwtUtil.parseJWT(jwt);
//        String name = claims.get("name", String.class);
//        String password = claims.get("password", String.class);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("name", name);
//        map.put("password", password);
//        map.put("code", "20000");
//        map.put("msg", "请求成功");
        Map<String, Object> map = userService.getJwtInfo(jwt);
        if (map != null){
            return Result.success(map);
        }
        return Result.fail(20003, "用户登录信息无效，请重新登录");
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestParam("token") String token){
        // 根据token获取用户信息，redis
        Map<String,Object> data = userService.getUserInfo(token);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20003, "用户登录信息无效，请重新登录");
    }

}
