package com.pedantic.services;

import com.pedantic.config.SecurityUtil;
import com.pedantic.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequestScoped
public class UserService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    SecurityUtil securityUtil;

    public void saveUser(User user) {

        if (user.getId() == null) {
            user.setPassword(securityUtil.encodeText(user.getPassword()));

            entityManager.persist(user);

        } else {
            entityManager.merge(user);
        }
    }


    public List<User> getUsers() {
//Go fetch users from the database and return in a list

//        User user = new User();
//        user.setEmail("bla@bla.com");
//        user.setNameOfUser("Frank Kane");
//        user.setPassword("sfljgnosjngjngjonojkn");
//        user.setId(52L);
//
//        User user1 = new User();
//        user1.setEmail("foo@bar.com");
//        user1.setNameOfUser("Adam Bien");
//        user1.setPassword("sfljgnos15151515dfjkvhuidfnvn");
//        user1.setId(58L);
//
//
//        User user2 = new User();
//        user2.setEmail("trump@gov.us");
//        user2.setNameOfUser("Donald Trump");
//        user2.setPassword("covfefe");
//        user2.setId(45L);


        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    public String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }


    public User findUserByCredentials(String email, String password) {
        return entityManager.createNamedQuery(User.FIND_USER_BY_CREDENTIALS, User.class).setParameter("email", email)
                .setParameter("password", securityUtil.encodeText(password)).getSingleResult();

    }

}
