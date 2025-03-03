package com.example.demo;

import lombok.Data;

//@Data
@SuppressWarnings("all")
public class User {
    User(){}
    User(long Id,String nm,Integer a){id=Id;name=nm;age=a;}
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public long getId(){return id;}
    private String name;
    public void setName(String n){name=n;}
    public String getName(){return name;}
    private Integer age;
    public void setAge(Integer a){age=a;}
    public Integer getAge(){return age;}

}