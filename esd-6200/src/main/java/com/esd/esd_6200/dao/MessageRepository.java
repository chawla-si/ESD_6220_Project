package com.esd.esd_6200.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.esd.esd_6200.pojo.Message;

import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepository {
    
    private final SessionFactory sessionFactory;
    
    @Autowired
    public MessageRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public Message save(Message message) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(message);
            tx.commit();
            return message;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    public Optional<Message> findById(Long id) {
        Session session = sessionFactory.openSession();
        Message message = session.get(Message.class, id);
        session.close();
        return Optional.ofNullable(message);
    }
    
    public Page<Message> findByUserEmail(String userEmail, Pageable pageable) {
        Session session = sessionFactory.openSession();
        
        // Use "messages" table name explicitly in native SQL
        Query<Message> query = session.createNativeQuery(
            "SELECT * FROM messages WHERE user_email = :email ORDER BY id", 
            Message.class);
        query.setParameter("email", userEmail);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        List<Message> messages = query.getResultList();
        
        // Get total count for pagination
        Query<Long> countQuery = session.createNativeQuery(
            "SELECT COUNT(*) FROM messages WHERE user_email = :email", 
            Long.class);
        countQuery.setParameter("email", userEmail);
        Long total = countQuery.getSingleResult();
        
        session.close();
        return new PageImpl<>(messages, pageable, total);
    }
    
    public Page<Message> findByClosed(@RequestParam("closed") boolean closed, Pageable pageable) {
        Session session = sessionFactory.openSession();
        Query<Message> query = session.createQuery(
            "FROM Message WHERE closed = :closed ORDER BY id", 
            Message.class);
        query.setParameter("closed", closed);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        
        List<Message> messages = query.getResultList();
        
        // Get total count for pagination
        Query<Long> countQuery = session.createQuery(
            "SELECT COUNT(m) FROM Message m WHERE m.closed = :closed", 
            Long.class);
        countQuery.setParameter("closed", closed);
        Long total = countQuery.getSingleResult();
        
        session.close();
        return new PageImpl<>(messages, pageable, total);
    }
}
