package com.esd.esd_6200.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.esd.esd_6200.pojo.Review;

import jakarta.persistence.EntityManager;
import java.util.List;

@Repository
public class ReviewRepository {

    private final SessionFactory sessionFactory;

    public ReviewRepository(EntityManager entityManager) {
        this.sessionFactory = entityManager.unwrap(Session.class).getSessionFactory();
    }

    public List<Review> findBookById(Long bookId, int pageNumber, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        Query<Review> query = session.createQuery(
            "from Review r where r.bookId = :bookId order by r.date desc", Review.class);
        query.setParameter("bookId", bookId);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public Review findByUserEmailAndBookId(String userEmail, Long bookId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Review> query = session.createQuery(
            "from Review r where r.userEmail = :userEmail and r.bookId = :bookId",
            Review.class);
        query.setParameter("userEmail", userEmail);
        query.setParameter("bookId", bookId);
        return query.uniqueResult();
    }

    public void deleteAllByBookId(Long bookId) {
        Session session = sessionFactory.getCurrentSession();
        Query<?> query = session.createQuery("delete from Review where bookId = :bookId");
        query.setParameter("bookId", bookId);
        query.executeUpdate();
    }

    public void save(Review review) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(review);
    }
}