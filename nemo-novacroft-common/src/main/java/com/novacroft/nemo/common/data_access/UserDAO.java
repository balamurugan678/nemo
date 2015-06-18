package com.novacroft.nemo.common.data_access;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.domain.User;

/**
 * TfL Application Event data access class implementation.
 */

@Repository("userDAO")
public class UserDAO {
    private SessionFactory sessionFactory;

    public User findById(String id) {
        User user;
        user = (User) getSession().get(User.class, id);
        return user;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public List<User> findAll() {
        return findByCriteria();
    }

    @SuppressWarnings("unchecked")
    public List<User> findByExample(User userEntity) {
        return getSession().createCriteria(User.class).add(Example.create(userEntity)).list();
    }

    @SuppressWarnings("unchecked")
    protected List<User> findByCriteria(Criterion... criterion) {
        Criteria criteria = getSession().createCriteria(User.class);
        for (Criterion c : criterion) {
            criteria.add(c);
        }
        return criteria.list();
    }
}
