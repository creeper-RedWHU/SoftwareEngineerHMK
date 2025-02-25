package com.example.demo;

import lombok.Data;

//@Data
public class User {

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