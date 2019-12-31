package com.snake.hibernate;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateDB {

    public void saveResults(Object dbData) {
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            // start a transaction
            log.info("start a transaction");
            transaction = session.beginTransaction();
            log.info("Transaction successful");
            // save the student objects
            session.save(dbData);
            log.info("Player information saved");
            // commit transaction
            transaction.commit();
            log.info("Transaction commit");
        } catch (Exception e) {
            log.error("Exception", e);
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Database Transaction failed", e);
        }
    }

}
