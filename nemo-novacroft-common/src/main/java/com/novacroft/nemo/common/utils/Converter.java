package com.novacroft.nemo.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novacroft.nemo.common.transfer.ConverterNullable;

/**
 * Convert from one object to another.
 * This will match any equally name fields and copy from one object to another.
 * Mainly used for copying from a Domain Object to a Data Transfer Object.
 */

public final class Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);
    private static final int SETCOUNT = 3;
    private static final int ISCOUNT = 2;
    private static final String GET = "get";
    private static final String SET = "set";
    private static final String IS = "is";
    private static final String ILLEGALARGUMENTEXCEPTION = "Error: null method argument(s) passed.";

    private Converter() {
    }

    public static Object convert(Object main, Object copy) {
        if (main == null || copy == null) {
            throw new IllegalArgumentException(ILLEGALARGUMENTEXCEPTION);
        }

        Object object = main;
        Method[] methods = object.getClass().getMethods();
        Map<String, Object> methodValues = new HashMap<String, Object>();
        for (Method method : methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            String methodName = method.getName();
            if (parameterTypes.length == 0 && method.getReturnType() != null &&
                    (methodName.startsWith(GET) || methodName.startsWith(IS))) {
                String propertyName =
                        (methodName.startsWith(GET) ? methodName.substring(SETCOUNT) : methodName.substring(ISCOUNT))
                                .toLowerCase();
                Object result;
                try {
                    result = method.invoke(object, new Object[]{});
                    methodValues.put(propertyName, result);
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    logError(propertyName, e, main, copy, parameterTypes, methodValues);
                }
            }
        }

        Object copyObject = copy;
        Method[] dtoMethods = copyObject.getClass().getMethods();
        for (Method method : dtoMethods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            String methodName = method.getName();
            if (parameterTypes.length == 1 && (methodName.startsWith(SET) || methodName.startsWith(IS))) {
                String propertyName =
                        (methodName.startsWith(SET) ? methodName.substring(SETCOUNT) : methodName.substring(ISCOUNT))
                                .toLowerCase();
                try {
                    ConverterNullable objectNullable = (ConverterNullable) main;
                    if ((methodValues.get(propertyName) != null || objectNullable.isNullable()) &&
                            methodValues.containsKey(propertyName)) {
                        method.invoke(copyObject, methodValues.get(propertyName));
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException e) {
                    try {
                        if (methodValues.get(propertyName) != null) {
                            method.invoke(copyObject, methodValues.get(propertyName));
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        logError(propertyName, ex, main, copy, parameterTypes, methodValues);
                    }
                }
            }
        }
        return copyObject;
    }

    private static void logError(String propertyName, Exception e, Object main, Object copy, Class<?>[] parameterTypes,
                                 Map<String, Object> methodValues) {
        LOGGER.warn("Property = " + propertyName + "    Error = " + e.getMessage());
        LOGGER.warn("Main Class = " + main.getClass() + ", property class = " + (methodValues.get(propertyName) != null? methodValues.get(propertyName).getClass(): propertyName));
        LOGGER.warn("Copy Class = " + copy.getClass() + ", property class = " + (parameterTypes.length > 0?parameterTypes[0].getClass():null));
    }
}
