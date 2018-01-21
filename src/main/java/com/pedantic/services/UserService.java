package com.pedantic.services;

import com.pedantic.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequestScoped
public class UserService {

    @PersistenceContext
    EntityManager entityManager;

    public void saveUser(User user) {
        entityManager.persist(user);
    }


    public List<User> getUsers() {
//Go fetch users from the database and return in a list

        User user = new User();
        user.setEmail("bla@bla.com");
        user.setNameOfUser("Frank Kane");
        user.setPassword("sfljgnosjngjngjonojkn");
        user.setId(52L);

        User user1 = new User();
        user1.setEmail("foo@bar.com");
        user1.setNameOfUser("Adam Bien");
        user1.setPassword("sfljgnos15151515dfjkvhuidfnvn");
        user1.setId(58L);


        User user2 = new User();
        user2.setEmail("trump@gov.us");
        user2.setNameOfUser("Donald Trump");
        user2.setPassword("covfefe");
        user2.setId(45L);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);

        return userList;
    }

}
