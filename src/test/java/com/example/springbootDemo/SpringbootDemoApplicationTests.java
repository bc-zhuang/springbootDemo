package com.example.springbootDemo;

import com.example.springbootDemo.sys.entity.User;
import com.example.springbootDemo.sys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Function;

@SpringBootTest
class SpringbootDemoApplicationTests {

	@Resource
	private UserMapper userMapper;

	@Test
	void testMapper() {
		List<User> users = userMapper.selectList(null);
		users.forEach(System.out::println);
	}

}
