package com.example.jspservlet.dao;

import com.example.jspservlet.entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    List<Student> findAll();
    Student findId(int id);
    void save(Student student);
    public void remove(int id);
    List<Student> findPage(int page, int size);
    List<Student> findByKeyWord(String keyword, String code);
}
