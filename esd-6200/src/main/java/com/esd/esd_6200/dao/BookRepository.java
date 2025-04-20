package com.esd.esd_6200.dao;



import com.esd.esd_6200.config.HibernateUtil;
import com.esd.esd_6200.pojo.Book;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import java.util.List;

@Repository
public class BookRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookRepository(EntityManager entityManager) {
        this.sessionFactory = entityManager.unwrap(Session.class).getSessionFactory();
    }
    
//    public List<Book> findAll() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//        	System.out.println("***IN DAO**");
//        	System.out.println(session.createQuery("FROM Book", Book.class).list());
//            return session.createQuery("FROM Book", Book.class).list();
//        }
//    }
    public List<Book> findAll(Pageable pageable) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder queryString = new StringBuilder("FROM Book b");
            
            // Apply sorting if provided
            if (pageable.getSort().isSorted()) {
                queryString.append(" ORDER BY ");
                boolean first = true;
                
                for (Sort.Order order : pageable.getSort()) {
                    if (!first) {
                        queryString.append(", ");
                    }
                    queryString.append("b.").append(order.getProperty())
                            .append(" ").append(order.getDirection().name());
                    first = false;
                }
            }
            
            Query<Book> query = session.createQuery(queryString.toString(), Book.class);
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            
            return query.getResultList();
        }
    }
    
    public Page<Book> findByTitleContaining(String title, Pageable pageable) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Create base query
            String countQueryString = "SELECT COUNT(b) FROM Book b WHERE LOWER(b.title) LIKE LOWER(:title)";
            String queryString = "FROM Book b WHERE LOWER(b.title) LIKE LOWER(:title)";
            
            // Apply sorting if provided
            if (pageable.getSort().isSorted()) {
                StringBuilder sortClause = new StringBuilder(" ORDER BY ");
                boolean first = true;
                
                for (Sort.Order order : pageable.getSort()) {
                    if (!first) {
                        sortClause.append(", ");
                    }
                    sortClause.append("b.").append(order.getProperty())
                           .append(" ").append(order.getDirection().name());
                    first = false;
                }
                queryString += sortClause.toString();
            }
            
            // Execute count query
            Query<Long> countQuery = session.createQuery(countQueryString, Long.class);
            countQuery.setParameter("title", "%" + title + "%");
            long total = countQuery.uniqueResult();
            
            // Execute main query with pagination
            Query<Book> query = session.createQuery(queryString, Book.class);
            query.setParameter("title", "%" + title + "%");
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            
            List<Book> books = query.getResultList();
            
            return new PageImpl<>(books, pageable, total);
        }
    }
    
    public Page<Book> findByCategoryContaining(String category, Pageable pageable) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Create base query
            String countQueryString = "SELECT COUNT(b) FROM Book b WHERE LOWER(b.category) LIKE LOWER(:category)";
            String queryString = "FROM Book b WHERE LOWER(b.category) LIKE LOWER(:category)";
            
            // Apply sorting if provided
            if (pageable.getSort().isSorted()) {
                StringBuilder sortClause = new StringBuilder(" ORDER BY ");
                boolean first = true;
                
                for (Sort.Order order : pageable.getSort()) {
                    if (!first) {
                        sortClause.append(", ");
                    }
                    sortClause.append("b.").append(order.getProperty())
                           .append(" ").append(order.getDirection().name());
                    first = false;
                }
                queryString += sortClause.toString();
            }
            
            // Execute count query
            Query<Long> countQuery = session.createQuery(countQueryString, Long.class);
            countQuery.setParameter("category", "%" + category + "%");
            long total = countQuery.uniqueResult();
            
            // Execute main query with pagination
            Query<Book> query = session.createQuery(queryString, Book.class);
            query.setParameter("category", "%" + category + "%");
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            
            List<Book> books = query.getResultList();
            
            return new PageImpl<>(books, pageable, total);
        }
    }
    
//    @Transactional
//    public List<Book> findAll() {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("FROM Book", Book.class).getResultList();
//    }

    public Book findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, id);
        }
    }
    
//    public Page<Book> findByTitleContaining(String title, Pageable pageable) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            // Get total count for this query
//            Query<Long> countQuery = session.createQuery(
//                    "SELECT COUNT(b) FROM Book b WHERE b.title LIKE :title", 
//                    Long.class);
//            countQuery.setParameter("title", "%" + title + "%");
//            Long total = countQuery.getSingleResult();
//            
//            // Get paginated results
//            Query<Book> query = session.createQuery(
//                    "FROM Book b WHERE b.title LIKE :title", 
//                    Book.class);
//            query.setParameter("title", "%" + title + "%");
//            query.setFirstResult((int) pageable.getOffset());
//            query.setMaxResults(pageable.getPageSize());
//            
//            // Apply sorting if present
//            if (pageable.getSort().isSorted()) {
//                pageable.getSort().forEach(order -> {
//                    String property = order.getProperty();
//                    String direction = order.getDirection().name();
//                    query.getQueryString().concat(" ORDER BY b." + property + " " + direction);
//                });
//            }
//            
//            List<Book> books = query.getResultList();
//            return new PageImpl<>(books, pageable, total);
//        }
//    }
    
    public Page<Book> findByCategory(String category, Pageable pageable) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Get total count for this query
            Query<Long> countQuery = session.createQuery(
                    "SELECT COUNT(b) FROM Book b WHERE b.category = :category", 
                    Long.class);
            countQuery.setParameter("category", category);
            Long total = countQuery.getSingleResult();
            
            // Get paginated results
            Query<Book> query = session.createQuery(
                    "FROM Book b WHERE b.category = :category", 
                    Book.class);
            query.setParameter("category", category);
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            
            // Apply sorting if present
            if (pageable.getSort().isSorted()) {
                pageable.getSort().forEach(order -> {
                    String property = order.getProperty();
                    String direction = order.getDirection().name();
                    query.getQueryString().concat(" ORDER BY b." + property + " " + direction);
                });
            }
            
            List<Book> books = query.getResultList();
            return new PageImpl<>(books, pageable, total);
        }
    }


//    public List<Book> findByTitleContaining(String title, int offset, int pageSize) {
//        Session session = sessionFactory.getCurrentSession();
//        Query<Book> query = session.createQuery("from Book b where lower(b.title) like :title", Book.class);
//        query.setParameter("title", "%" + title.toLowerCase() + "%");
//        query.setFirstResult(offset);
//        query.setMaxResults(pageSize);
//        return query.getResultList();
//    }

//    public List<Book> findByCategory(String category, int offset, int pageSize) {
//        Session session = sessionFactory.getCurrentSession();
//        Query<Book> query = session.createQuery("from Book b where b.category = :category", Book.class);
//        query.setParameter("category", category);
//        query.setFirstResult(offset);
//        query.setMaxResults(pageSize);
//        return query.getResultList();
//    }

    public Book save(Book book) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(book);
                transaction.commit();
                return book;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
    public Book update(Book book) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Book updatedBook = session.merge(book);
                transaction.commit();
                return updatedBook;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
    
    public void delete(Book book) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(book);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Book book = session.get(Book.class, id);
                if (book != null) {
                    session.remove(book);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
    
    public boolean existsById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Book book = session.get(Book.class, id);
            return book != null;
        }
    }
    
    public long count() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(b) FROM Book b", Long.class);
            return query.uniqueResult();
        }
    }
}
