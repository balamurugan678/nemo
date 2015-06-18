package com.novacroft.nemo.tfl.innovator.workflow;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;

public class CustomUserIdentityManagerFactory implements SessionFactory {

    public CustomUserIdentityManagerFactory() {
    }

    @Override
    public Class<?> getSessionType() {
        return UserIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return new CustomUserEntityManager();
    }
}
