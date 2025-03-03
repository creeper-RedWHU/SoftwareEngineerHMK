package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @GetMapping("/")
    public List<User>getUserList() throws IOException {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生产SqlSession对象
        SqlSession session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        UserMapper userMP = session.getMapper(UserMapper.class);
        //5.使用代理对象执行方法
        List<User> users = userMP.findAll();
        //6.释放资源
        session.close();
        in.close();
        return users;
    }
    @PostMapping("/")
    public String postUser(@RequestBody User user) throws IOException {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生产SqlSession对象
        SqlSession session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        UserMapper userMP = session.getMapper(UserMapper.class);
        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
        userMP.insertUser(user);
        session.commit();
        in.close();
        session.close();
        return "success";
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) throws IOException {
        // url中的id可通过@PathVariable绑定到函数的参数中
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //3.使用工厂生产SqlSession对象
        SqlSession session = factory.openSession();
        //4.使用SqlSession创建Dao接口的代理对象
        UserMapper userMP = session.getMapper(UserMapper.class);
        //5.使用代理对象执行方法
        User user = userMP.find(id);
        in.close();
        session.close();
        return user;
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id)throws IOException {
        InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=builder.build(in);
        SqlSession session=factory.openSession();
        UserMapper userMapper=session.getMapper(UserMapper.class);
        userMapper.del(id);
        session.commit();
        in.close();
        session.close();
        return "success";
    }
    @PutMapping("/{id}")
    public String putUser(@PathVariable Long id, @RequestBody User user) throws IOException {
        InputStream in=Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=builder.build(in);
        SqlSession session=factory.openSession();
        UserMapper userMapper=session.getMapper(UserMapper.class);

        User u = userMapper.find(id);
        userMapper.del(id);

        u.setName(user.getName());
        u.setAge(user.getAge());
        userMapper.insertUser(u);
        session.commit();
        in.close();
        session.close();
        return "success";
    }
}