package com.novacroft.nemo.tfl.innovator.workflow;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;

public class CustomGroupIdentityManagerFactory implements SessionFactory {

    public CustomGroupIdentityManagerFactory() {
    }

    @Override
    public Class<?> getSessionType() {
        return GroupIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return new CustomGroupEntityManager();
    }
}
