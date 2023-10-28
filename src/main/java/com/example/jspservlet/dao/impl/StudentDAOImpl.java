package com.example.jspservlet.dao.impl;

import com.example.jspservlet.dao.StudentDAO;
import com.example.jspservlet.entity.Student;
import com.example.jspservlet.util.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    EntityManager manager;
    EntityTransaction transaction;
    public StudentDAOImpl() {
        manager = PersistenceUtil.createEntityManagerFactory().createEntityManager();
        transaction = manager.getTransaction();
    }

    //lấy hêt Student
    @Override
    public List<Student> findAll(){
        List<Student> students = new ArrayList<>();
        try{
            Query query  = manager.createQuery("select s from Student s");
            return query.getResultList();
        }catch (Exception ex){
            System.out.printf(ex.getMessage());
        }
        return students;
    }

    public Student findId(int id) {
        return manager.find(Student.class, id);
    }

    @Override
    public void save(Student student) {
        try {
            transaction.begin();
            if (manager.find(student.getClass(), student.getId()) != null) {
                manager.merge(student); // Cập nhật đối tượng
            } else {
                manager.persist(student); // Tạo mới đối tượng
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void remove(int id){
        try {
            transaction.begin();
            Student student = manager.find(Student.class, id);
            if (student != null) {
                manager.remove(student);
            }
            transaction.commit();

        }catch (Exception e){
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    //Tim theo keyword
    @Override
    public List<Student> findByKeyWord(String keyword, String code){
        String queryString = "SELECT s FROM Student s WHERE s.name LIKE :keyword and s.code like :code";
        TypedQuery<Student> query = manager.createQuery(queryString, Student.class);
        query.setParameter("keyword", "%" + keyword + "%");
        query.setParameter("code", "%" + code + "%");
        return query.getResultList();
    }

    //Tim kiem phan trang
    @Override
    public List<Student> findPage(int page, int size){
        String jpql = "Select s from Student s";
        TypedQuery<Student> query = manager.createQuery(jpql, Student.class);
        query.setFirstResult(page*size);
        query.setMaxResults(size);
        return query.getResultList();
    }
}
