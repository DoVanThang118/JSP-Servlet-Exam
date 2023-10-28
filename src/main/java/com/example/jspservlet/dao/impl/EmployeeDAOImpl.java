package com.example.jspservlet.dao.impl;

import com.example.jspservlet.dao.EmployeeDAO;
import com.example.jspservlet.entity.Employee;
import com.example.jspservlet.entity.Student;
import com.example.jspservlet.util.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    EntityManager manager;
    EntityTransaction transaction;
    public EmployeeDAOImpl() {
        manager = PersistenceUtil.createEntityManagerFactory().createEntityManager();
        transaction = manager.getTransaction();
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try{
            Query query  = manager.createQuery("select s from Employee s");
            return query.getResultList();
        }catch (Exception ex){
            System.out.printf(ex.getMessage());
        }
        return employees;
    }

    @Override
    public Employee findId(int id) {
        return null;
    }

    @Override
    public void save(Employee employee) {
        try {
            transaction.begin();
            if (manager.find(employee.getClass(), employee.getId()) != null) {
                manager.merge(employee); // Cập nhật đối tượng
            } else {
                manager.persist(employee); // Tạo mới đối tượng
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Employee> findPage(int page, int size) {
        return null;
    }

    @Override
    public List<Employee> findByKeyWord(String keyword, String code) {
        return null;
    }
}
