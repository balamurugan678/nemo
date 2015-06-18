package com.novacroft.nemo.test_support;

import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.novacroft.nemo.common.domain.User;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;

public class CustomUserEntityManagerTestUtil {
    public static final String USER_ID = "First Stage";
    public static final String USER_FIRST_NAME = "First";
    public static final String USER_LAST_NAME = "Stage";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email@email.email";
    public static final String GROUP_ID = AgentGroup.FIRST_STAGE_APPROVER.code();

    public static User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        user.setPassword(PASSWORD);
        user.setEmailAddress(EMAIL);
        return user;
    }

    public static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setFirstName(USER_FIRST_NAME);
        userEntity.setLastName(USER_LAST_NAME);
        userEntity.setPassword(PASSWORD);
        userEntity.setEmail(EMAIL);
        userEntity.setRevision(1);
        return userEntity;
    }
}
