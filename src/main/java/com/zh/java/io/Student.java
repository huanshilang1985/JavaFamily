package com.zh.java.io;

import java.io.Serializable;

/**
 * @Author zhanghe
 * @Desc:
 * @Date 2019/3/19 14:59
 */
public class Student implements Serializable {

    //序列化ID
    private static final long serialVersionUID = -7102162962133118024L;

    private String name;

    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
