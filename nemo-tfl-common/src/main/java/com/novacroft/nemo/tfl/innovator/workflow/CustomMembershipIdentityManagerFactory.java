package com.novacroft.nemo.tfl.innovator.workflow;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.MembershipIdentityManager;

public class CustomMembershipIdentityManagerFactory implements SessionFactory {

    public CustomMembershipIdentityManagerFactory() {
    }

    @Override
    public Class<?> getSessionType() {
        return MembershipIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return new CustomMembershipEntityManager();
    }
}
