package com.esd.esd_6200.dao;



import com.esd.esd_6200.pojo.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.List;

@Repository
public class BookRepository {

    private final SessionFactory sessionFactory;

    public BookRepository(EntityManager entityManager) {
        this.sessionFactory = entityManager.unwrap(Session.class).getSessionFactory();
    }

    public Book findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    public List<Book> findByTitleContaining(String title, int offset, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        Query<Book> query = session.createQuery("from Book b where lower(b.title) like :title", Book.class);
        query.setParameter("title", "%" + title.toLowerCase() + "%");
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<Book> findByCategory(String category, int offset, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        Query<Book> query = session.createQuery("from Book b where b.category = :category", Book.class);
        query.setParameter("category", category);
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(book);
    }
}
