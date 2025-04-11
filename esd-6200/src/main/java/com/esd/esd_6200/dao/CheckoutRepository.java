package com.esd.esd_6200.dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.esd.esd_6200.config.HibernateUtil;
import com.esd.esd_6200.pojo.Checkout;

import java.util.List;

@Repository
public class CheckoutRepository {
    
    public Checkout findByUserEmailAndBookId(String userEmail, Long bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Checkout WHERE userEmail = :userEmail AND bookId = :bookId", 
                    Checkout.class)
                    .setParameter("userEmail", userEmail)
                    .setParameter("bookId", bookId)
                    .uniqueResult();
        }
    }
    
    public List<Checkout> findBooksByUserEmail(String userEmail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Checkout WHERE userEmail = :userEmail", 
                    Checkout.class)
                    .setParameter("userEmail", userEmail)
                    .getResultList();
        }
    }
    
    public void deleteAllByBookId(Long bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createQuery(
                        "DELETE FROM Checkout WHERE bookId = :bookId")
                        .setParameter("bookId", bookId)
                        .executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
    
    public Checkout save(Checkout checkout) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(checkout);
                transaction.commit();
                return checkout;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
    
    public Checkout update(Checkout checkout) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(checkout);
                transaction.commit();
                return checkout;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
    
    public void delete(Checkout checkout) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // First get a reference to ensure the entity is attached to the session
                Checkout managedCheckout = session.get(Checkout.class, checkout.getId());
                if (managedCheckout != null) {
                    session.remove(managedCheckout);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
    
    public Checkout findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Checkout.class, id);
        }
    }
    
    public List<Checkout> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Checkout", Checkout.class).list();
        }
    }
}