package com.example.demo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    // 测试 HTTP 接口
    @Test
    void testGetUserListViaHttp() {
        // 发送 GET 请求并验证响应,这里不能直接转成list，会有类型擦除
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "/users/",          // URL
                HttpMethod.GET,     // HTTP方法
                null,               // 请求体（GET请求可传null）
                new ParameterizedTypeReference<List<User>>() {} // 泛型类型包装
        );
        List<User> users = response.getBody();
        assertFalse(users.isEmpty(),  "返回的用户列表不应为空");
        assertTrue(users.get(0).getName().equals("王明"), "应包含预设用户王明");
    }
    @Test
    void testPostUserListViaHttp(){
        User user=new User(100L,"测试用户",25);
        ResponseEntity<String>response=restTemplate.postForEntity("/users/",user,String.class);
        assertEquals("success", response.getBody());
    }
    //检验getSpecial是否成功
    @Test
    void testGetUserSpecialViaHttp(){
        ResponseEntity<User> response = restTemplate.exchange(
                "/users/1",          // URL
                HttpMethod.GET,     // HTTP方法
                null,               // 请求体（GET请求可传null）
                new ParameterizedTypeReference<User>() {} // 泛型类型包装
        );
        User user = response.getBody();
        assertTrue(user.getId()==(1L),"找到！");
        assertFalse(user.getId()!=1L,"找错了！");
    }
    @Test
    void testDelCanWork(){
        User user=new User(99999,"测试用户99999",99999);
        ResponseEntity<String>response=restTemplate.postForEntity("/users/",user,String.class);
        //检验思路：本来不在&&加了在&&减了不在
        /*暂时不做*/
    }
    /*
    // 直接测试 Mapper 数据库操作
    @Test
    void testUserMapperOperations() throws Exception {
        // 初始化 MyBatis 会话
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

        try (SqlSession session = factory.openSession(true))  { // 自动提交事务
            UserMapper mapper = session.getMapper(UserMapper.class);

            // 验证查询功能
            List<User> users = mapper.findAll();
            assertFalse(users.isEmpty(),  "数据库应至少包含一条记录");

            // 验证插入功能
            User newUser = new User(2L,  "李红",20);
            mapper.insertUser(newUser);

            // 验证插入后的数据
            List<User> updatedUsers = mapper.findAll();
            assertEquals(2, updatedUsers.size(),  "插入后记录数应增加");

        }
    }*/
}