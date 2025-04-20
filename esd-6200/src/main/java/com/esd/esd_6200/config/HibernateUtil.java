package com.esd.esd_6200.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;

import com.esd.esd_6200.pojo.Book;
import com.esd.esd_6200.pojo.Checkout;
import com.esd.esd_6200.pojo.Message;
import com.esd.esd_6200.pojo.Review;


@Component
public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registry
                registry = new StandardServiceRegistryBuilder()
                    .configure() // loads hibernate.cfg.xml from resources folder
                    .build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);
                
                // Explicitly register the Book class
                sources.addAnnotatedClass(Book.class);
                sources.addAnnotatedClass(Review.class);
                sources.addAnnotatedClass(Checkout.class);
                sources.addAnnotatedClass(Message.class);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}