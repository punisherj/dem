package com.bonc.dem.dao;

import com.bonc.dem.pojo.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonDao extends JpaRepository<Person, Long> {
    //@Query("select p from Person p where p.age=?1")
    List<Person> findByAge(Integer age);
}
