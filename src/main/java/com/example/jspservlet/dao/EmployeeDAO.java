package com.example.jspservlet.dao;

import com.example.jspservlet.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> findAll();
    Employee findId(int id);
    void save(Employee employee);
    public void remove(int id);
    List<Employee> findPage(int page, int size);
    List<Employee> findByKeyWord(String keyword, String code);
}
