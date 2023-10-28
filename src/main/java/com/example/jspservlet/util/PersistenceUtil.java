package com.example.jspservlet.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {
    private static EntityManagerFactory entityManagerFactory;

    public static synchronized EntityManagerFactory createEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("data");
        }
        return  entityManagerFactory;
    }
}
