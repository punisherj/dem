package com.bonc.dem.pojo;

import com.bonc.dem.annotation.ExcelResources;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {

    @Id
    private String id;
    private String name;
    private Integer age;

    public Person(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    @ExcelResources(title="编号",order=1)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ExcelResources(title="姓名",order=2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelResources(title="年龄",order=3)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
