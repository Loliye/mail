package com.mikufans.mail.common.dynamicquery;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface DynamicQuery
{
    void save(Object entity);

    void update(Object entity);

    <T> void delete(Class<T> entityClass,Object entityId);

    <T> void delete(Class<T> entityClass,Object[] entityIds);

    /**
     * 执行nativeSql统计查询
     * @param nativeSql
     * @param params
     * @return
     */
    Long nativQueryCount(String nativeSql,Object... params);
}
