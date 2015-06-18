package com.novacroft.nemo.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_access.UserDAO;
import com.novacroft.nemo.common.data_service.UserDataService;
import com.novacroft.nemo.common.domain.User;

@Service(value = "userDataService")
@Transactional(readOnly = true)
public class UserDataServiceImpl implements UserDataService {
    protected UserDAO dao;

    @Override
    @Autowired
    @Qualifier("userDAO")
    public void setDao(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(String id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public User getNewEntity() {
        return new User();
    }
}
