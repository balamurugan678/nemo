package com.novacroft.nemo.common.application_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.UserService;
import com.novacroft.nemo.common.data_service.UserDataService;
import com.novacroft.nemo.common.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDataService userDataService;

    public void setUserDataService(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @Override
    @Transactional
    public User findUserById(String userId) {
        return userDataService.findById(userId);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userDataService.findAll();
    }
}
