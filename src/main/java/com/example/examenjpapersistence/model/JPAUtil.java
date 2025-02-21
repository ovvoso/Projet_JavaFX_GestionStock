package com.example.examenjpapersistence.model;

import javax.persistence.EntityManager;  // Retour à javax.persistence
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE";

    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null){
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    public static void shutdown(){
        if (factory!=null){
            factory.close();
        }
    }


    public static void close() {
    }
}
