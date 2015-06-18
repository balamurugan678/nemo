package com.novacroft.nemo.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_access.UserDAO;
import com.novacroft.nemo.common.domain.User;

public interface UserDataService {

    User findById(String id);

    List<User> findAll();

    void setDao(UserDAO dao);

    User getNewEntity();
}
