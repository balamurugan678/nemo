package com.novacroft.nemo.common.listener.hibernate;

import com.novacroft.nemo.common.data_access.SystemParameterDAO;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

@Component
public class SequenceGenerator implements SaveOrUpdateEventListener {

    private static final long serialVersionUID = -8781486373846800689L;

    static final String GET = "get";
    static final String SET = "set";

    @Autowired
    protected SystemParameterDAO systemParameterDAO;

    public SystemParameterDAO getSystemParameterDAO() {
        return systemParameterDAO;
    }

    public void setSystemParameterDAO(SystemParameterDAO systemParameterDAO) {
        this.systemParameterDAO = systemParameterDAO;
    }

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
        GeneratedSequenceDependence appGeneratedSequenceDependence =
                AnnotationUtils.findAnnotation(event.getObject().getClass(), GeneratedSequenceDependence.class);
        if (appGeneratedSequenceDependence != null) {
            String[] sequenceNames = appGeneratedSequenceDependence.sequenceNames();
            String[] fieldNames = appGeneratedSequenceDependence.fieldNames();
            Object targetObject = event.getObject();
            int index = 0;
            for (String fieldName : fieldNames) {
                String sequenceName = sequenceNames[index++];
                final String getterMethodName = deriveJavaBeanMethodNameFromFieldAndPrefix(GET, fieldName);
                final String setterMethodName = deriveJavaBeanMethodNameFromFieldAndPrefix(SET, fieldName);
                Method getterMethod;
                try {
                    getterMethod = event.getObject().getClass().getMethod(getterMethodName, (Class<?>[]) null);
                    Class<?>[] parameterTypes = new Class[1];
                    parameterTypes[0] = Long.class;
                    final Method setterMethod = event.getObject().getClass().getMethod(setterMethodName, parameterTypes);
                    final Object currentSequenceFieldValue = ReflectionUtils.invokeMethod(getterMethod, targetObject);

                    if (currentSequenceFieldValue == null) {
                        Long sequenceVal = systemParameterDAO.getNextSequenceNumber(sequenceName);
                        setterMethod.invoke(targetObject, sequenceVal);
                    }
                } catch (Exception e) {
                    throw new ApplicationServiceException(e.getMessage(), e.getCause());
                }
            }
        }

    }

    private String deriveJavaBeanMethodNameFromFieldAndPrefix(String prefix, String fieldName) {
        return prefix.concat(initCap(fieldName));
    }

    private String initCap(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1, text.length());
    }
}
