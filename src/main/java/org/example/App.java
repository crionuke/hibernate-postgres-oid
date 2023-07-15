package org.example;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.configure("hibernate.cfg.xml");

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();

            User user = new User();
            try (InputStream inputStream = App.class.getClassLoader()
                                                    .getResourceAsStream("profile.png");) {
                user.setName("User");
                user.setPhoto(IOUtils.toByteArray(inputStream));

                session.beginTransaction();
                session.persist(user);
                session.getTransaction()
                       .commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

            User result = session.find(User.class,
                                       user.getId());
            System.out.println(Arrays.toString(result.getPhoto()));
        }
    }

}
