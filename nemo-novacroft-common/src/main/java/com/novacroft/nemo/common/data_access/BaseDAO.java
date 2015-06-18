package com.novacroft.nemo.common.data_access;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

/**
 * Data access class base specification.
 */
public interface BaseDAO<ENTITY> {
    ENTITY findById(Long id);

    List<ENTITY> findAll();

    List<ENTITY> findByExample(ENTITY exampleEntity);

    List<ENTITY> findByExampleWithOrderBy(ENTITY exampleEntity, Order order);

    ENTITY findByExampleUniqueResult(ENTITY exampleEntity);

    ENTITY findByQueryUniqueResult(String hsql, Object... parameters);

    List findByQuery(String hsql, Object... parameters);

    List findByQueryWithLimit(String hsql, int startCount, int endCount, Object... parameters);

    ENTITY createOrUpdate(ENTITY entity);

    void delete(ENTITY entity);

    void setSessionFactory(SessionFactory sessionFactory);

    Criteria createCriteria(Class clazz);

    Query createQuery(String query);

    Query createSQLQuery(String query);

    void flush();

    Long getNextSequenceNumber(String sequenceName);

    ENTITY findByExternalId(Long externalId);

    Long getInternalIdFromExternalId(Long externalId);

    List findByQueryUsingNamedParameters(String hsql, Map<String, Object> parameters);

    ENTITY findByQueryUniqueResultUsingNamedParameters(String hsql, Map<String, Object> parameters);

    List findBySqlQueryWithLimitUsingNamedParameters(String sql, int startCount, int endCount, Map<String, Object> parameters);

	void clear();

    Long getNumberOfResultsFromQuery(String hsql, Map<String, Object> parameters);

    List<ENTITY> findByExampleWithSoonestOrderBy(ENTITY exampleEntity, String fulfilmentQueue);
}
