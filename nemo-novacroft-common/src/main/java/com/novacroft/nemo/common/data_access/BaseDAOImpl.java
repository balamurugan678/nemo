package com.novacroft.nemo.common.data_access;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.novacroft.nemo.common.domain.BaseEntity;

/**
 * Base data access class implementation.
 */
public class BaseDAOImpl<ENTITY extends BaseEntity> implements BaseDAO<ENTITY> {
    protected static final Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);
    private final Class<ENTITY> entityClass;
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public BaseDAOImpl() {
        this.entityClass = (Class<ENTITY>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    public ENTITY findById(Long id) {
        ENTITY entity = (ENTITY) getSession().load(this.entityClass, id);
        logStatistics();
        return entity;
    }

    @Override
    public List<ENTITY> findAll() {
        return findByCriteria();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ENTITY> findByExample(ENTITY exampleEntity) {
        List<ENTITY> list = getSession().createCriteria(this.entityClass).add(Example.create(exampleEntity)).list();
        logStatistics();
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ENTITY> findByExampleWithOrderBy(ENTITY exampleEntity, Order order) {
        Criteria criteria = getSession().createCriteria(this.entityClass);
        criteria.add(Example.create(exampleEntity));
        criteria.addOrder(order);
        List<ENTITY> list = criteria.list();
        logStatistics();
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ENTITY findByExampleUniqueResult(ENTITY exampleEntity) {
        ENTITY entity =
                (ENTITY) getSession().createCriteria(this.entityClass).add(Example.create(exampleEntity)).uniqueResult();
        logStatistics();
        return entity;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List findByQuery(String hsql, Object... parameters) {
        Query query = getSession().createQuery(hsql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        List list = query.list();
        logStatistics();
        return list;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List findByQueryUsingNamedParameters(String hsql, Map<String, Object> namedParameters) {
        Query query = getSession().createQuery(hsql);
        setNamedParameters(namedParameters, query);
        List list = query.list();
        logStatistics();
        return list;
    }

    private void setNamedParameters(Map<String, Object> parameters, Query query) {
        for (String keyVal : parameters.keySet()) {
            query.setParameter(keyVal, parameters.get(keyVal));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ENTITY findByQueryUniqueResultUsingNamedParameters(String hsql, Map<String, Object> namedParameters) {
        Query query = getSession().createQuery(hsql);
        setNamedParameters(namedParameters, query);
        ENTITY entity = (ENTITY) query.uniqueResult();
        logStatistics();
        return entity;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List findByQueryWithLimit(String hsql, int startCount, int endCount, Object... parameters) {
        Query query = getSession().createQuery(hsql);
        query.setFirstResult(startCount);
        query.setMaxResults(endCount);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        List list = query.list();
        logStatistics();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ENTITY findByQueryUniqueResult(String hsql, Object... parameters) {
        Query query = getSession().createQuery(hsql);
        for (int i = 0; i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        ENTITY entity = (ENTITY) query.uniqueResult();
        logStatistics();
        return entity;
    }

    @Override
    public ENTITY createOrUpdate(ENTITY entity) {
        getSession().saveOrUpdate(entity);
        logStatistics();
        return entity;
    }

    @Override
    public void delete(ENTITY entity) {
        getSession().delete(entity);
        logStatistics();
    }

    @SuppressWarnings("unchecked")
    protected List<ENTITY> findByCriteria(Criterion... criterion) {
        Criteria criteria = getSession().createCriteria(this.entityClass);
        for (Criterion c : criterion) {
            criteria.add(c);
        }
        List<ENTITY> list = criteria.list();
        logStatistics();
        return list;
    }

    @Override
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Criteria createCriteria(Class clazz) {
        return getSession().createCriteria(clazz);
    }

    @Override
    public Query createQuery(String query) {
        return getSession().createQuery(query);
    }

    @Override
    public Query createSQLQuery(String query) {
        return getSession().createSQLQuery(query);
    }

    @Override
    public void flush() {
        getSession().flush();
    }
    
    @Override
    public void clear() {
        getSession().clear();
    }

    protected void logStatistics() {
        if (logger.isDebugEnabled()) {
            Statistics statistics = getSession().getSessionFactory().getStatistics();
            logger.debug(String.format("2nd Level Cache: hits [%s]; misses [%s]; puts [%s]",
                    statistics.getSecondLevelCacheHitCount(), statistics.getSecondLevelCacheMissCount(),
                    statistics.getSecondLevelCachePutCount()));
            statistics.logSummary();
        }
    }

    /**
     * TODO: The method has potential SQL Injection vulnerabilities due to
     * sequenceName concatenated together to build SQL. This code needs to be
     * reviewed again later.
     */
    @Override
    public Long getNextSequenceNumber(String sequenceName) {
        Session session = getSession().getSessionFactory().openSession();
        Query query = session.createSQLQuery("select " + sequenceName + ".nextval as nextval from dual").addScalar("nextval");
        Long nextSequenceNumber = ((BigDecimal) query.uniqueResult()).longValue();
        session.close();
        return nextSequenceNumber;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ENTITY findByExternalId(Long externalId) {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("externalId", externalId));
        return (ENTITY) criteria.uniqueResult();
    }

    @Override
    public Long getInternalIdFromExternalId(Long externalId) {
        ENTITY entity = findByExternalId(externalId);
        return (entity != null) ? entity.getId() : null;
    }

    @Override
    public List findBySqlQueryWithLimitUsingNamedParameters(String sql, int startCount, int endCount,
                                                            Map<String, Object> parameters) {
        Query query = createSQLQuery(sql);
        query.setFirstResult(startCount);
        query.setMaxResults(endCount);
        setNamedParameters(parameters, query);
        return query.list();
    }

    @Override
    public Long getNumberOfResultsFromQuery(String hsql, Map<String, Object> parameters) {
        Query query = createQuery(hsql);
        setNamedParameters(parameters, query);
        return (Long) query.uniqueResult();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<ENTITY> findByExampleWithSoonestOrderBy(ENTITY exampleEntity, String fulfilmentQueue) {
        Criteria criteria = getSession().createCriteria(this.entityClass);
        Criterion fulfilmentType = Restrictions.eq("status", fulfilmentQueue);
        criteria.add(fulfilmentType).addOrder(Order.asc("orderDate")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                        .setFetchMode("items", FetchMode.JOIN);
        return criteria.list();
    }
}
