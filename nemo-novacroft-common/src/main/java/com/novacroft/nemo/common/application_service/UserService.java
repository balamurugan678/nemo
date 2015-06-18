package com.novacroft.nemo.common.application_service;

import java.util.List;

import com.novacroft.nemo.common.domain.User;

public interface UserService {
    User findUserById(String userId);

    List<User> findAll();
}
