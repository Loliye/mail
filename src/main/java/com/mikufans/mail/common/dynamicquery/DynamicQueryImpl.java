package com.mikufans.mail.common.dynamicquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class DynamicQueryImpl implements DynamicQuery
{
    Logger logger = LoggerFactory.getLogger(DynamicQueryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager()
    {
        return entityManager;
    }

    @Override
    public void save(Object entity)
    {
        entityManager.persist(entity);
    }

    @Override
    public void update(Object entity)
    {
        entityManager.persist(entity);
    }

    @Override
    public <T> void delete(Class<T> entityClass, Object entityId)
    {
        delete(entityClass, new Object[]{entityId});
    }

    @Override
    public <T> void delete(Class<T> entityClass, Object[] entityIds)
    {
        for (Object id : entityIds)
        {
            entityManager.remove(entityManager.getReference(entityClass, id));
        }
    }

    private Query createNativeQeury(String sql, Object... params)
    {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && params.length > 0)
        {
            for (int i = 0; i < params.length; i++)
            {
                query.setParameter(i + 1, params[i]);
            }
        }
        return query;
    }

    @Override
    public Long nativQueryCount(String nativeSql, Object... params)
    {
        Object count = createNativeQeury(nativeSql, params).getSingleResult();
        return ((Number) count).longValue();
    }
}
