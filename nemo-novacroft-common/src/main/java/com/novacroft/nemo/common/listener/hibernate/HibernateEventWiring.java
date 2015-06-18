package com.novacroft.nemo.common.listener.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component(value = "payeeRequestSettlementInterceptor")
public class HibernateEventWiring {

    @Autowired(required = false)
    protected SessionFactory sessionFactory;

    @Autowired(required = false)
    protected SaveOrUpdateEventListener listener;

    @PostConstruct
    public void registerListeners() {
        if (null != sessionFactory && null != listener) {
            EventListenerRegistry registry =
                    ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);
            registry.getEventListenerGroup(EventType.SAVE_UPDATE).appendListener(listener);
            registry.getEventListenerGroup(EventType.SAVE).appendListener(listener);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SaveOrUpdateEventListener getListener() {
        return listener;
    }

    public void setListener(SaveOrUpdateEventListener listener) {
        this.listener = listener;
    }

}
