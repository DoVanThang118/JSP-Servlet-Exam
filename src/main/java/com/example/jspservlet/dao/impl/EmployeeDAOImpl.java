package com.example.jspservlet.dao.impl;

import com.example.jspservlet.dao.EmployeeDAO;
import com.example.jspservlet.entity.Employee;
import com.example.jspservlet.entity.Student;
import com.example.jspservlet.util.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
        return manager.find(Employee.class, id);
    }

    @Override
    public void save(Employee employee) {
        try {
            transaction.begin();
            if (manager.find(employee.getClass(), employee.getId()) != null) {
                manager.merge(employee);
            } else {
                manager.persist(employee);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int id) {
        try {
            transaction.begin();
            Employee employee = manager.find(Employee.class, id);
            if (employee != null) {
                manager.remove(employee);
            }
            transaction.commit();

        }catch (Exception e){
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> findPage(int page, int size) {
        return null;
    }

    @Override
    public List<Employee> findByKeyWord(String fullname, String address, String position, String department) {
        String queryString = "SELECT s FROM Employee s " +
                "WHERE s.fullname LIKE :fullname " +
                "and s.address like :address " +
                "and s.position like :position " +
                "and s.department like :department";
        TypedQuery<Employee> query = manager.createQuery(queryString, Employee.class);
        query.setParameter("fullname", "%" + fullname + "%");
        query.setParameter("address", "%" + address + "%");
        query.setParameter("position", "%" + position + "%");
        query.setParameter("department", "%" + department + "%");
        return query.getResultList();
    }
}
