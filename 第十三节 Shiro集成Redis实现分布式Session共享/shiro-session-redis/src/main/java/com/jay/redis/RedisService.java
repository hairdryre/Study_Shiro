package com.jay.redis;

import org.springframework.dao.DataAccessException;

import java.util.Set;

/**
 * @author jay.zhou
 * @date 2019/1/6
 * @time 13:39
 * Redis服务接口
 */
public interface RedisService {

    /**
     * 添加缓存数据（如果给定key已存在，则进行覆盖）
     *
     * @param key key值
     * @param obj 缓存数据
     * @throws DataAccessException 数据访问异常
     */
    void set(final byte[] key, final byte[] obj) throws DataAccessException;

    /**
     * 添加缓存数据（如果给定key已存在，则进行覆盖）
     *
     * @param key key值
     * @param obj 缓存数据
     * @throws DataAccessException 数据访问异常
     */
    <T> void set(final byte[] key, final T obj) throws DataAccessException;

    /**
     * 添加缓存数据（如果给定key已存在，则进行覆盖）
     *
     * @param key key值
     * @param obj 缓存数据
     * @throws DataAccessException 数据访问异常
     */
    <T> void set(final String key, final T obj) throws DataAccessException;

    /**
     * 添加缓存数据（如果给定key已存在，则不进行覆盖，直接返回false）
     *
     * @param key key值
     * @param obj 缓存数据
     * @return 执行操作是否成功
     * @throws DataAccessException 数据访问异常
     */
    <T> Boolean setNX(final String key, final T obj) throws DataAccessException;

    /**
     * 添加缓存数据，设定缓存失效时间
     *
     * @param key           key值
     * @param obj           缓存数据
     * @param expireSeconds 失效时间（单位秒）
     * @throws DataAccessException 数据访问异常
     */
    <T> void setEx(final byte[] key, final T obj, final Long expireSeconds) throws DataAccessException;

    /**
     * 添加缓存数据，设定缓存失效时间
     *
     * @param key           key值
     * @param obj           缓存数据
     * @param expireSeconds 失效时间（单位秒）
     * @throws DataAccessException 数据访问异常
     */
    <T> void setEx(final String key, final T obj, final Long expireSeconds) throws DataAccessException;

    /**
     * 根据key获取对应的缓存数据
     *
     * @param key key值
     * @return 缓存数据
     * @throws DataAccessException 数据访问异常
     */
    <T> T get(final byte[] key) throws DataAccessException;

    /**
     * 根据key获取对应的缓存数据
     *
     * @param key key值
     * @return 缓存数据
     * @throws DataAccessException 数据访问异常
     */
    <T> T get(final String key) throws DataAccessException;

    /**
     * 根据key删除对应的缓存数据
     *
     * @param key key值
     * @return 删除的缓存数据条数
     * @throws DataAccessException 数据访问异常
     */
    Long del(final byte[] key) throws DataAccessException;

    /**
     * 获取缓存中匹配key值的所有键
     *
     * @param key key值
     * @return 匹配的所有键
     * @throws DataAccessException 数据访问异常
     */
    Set<byte[]> keys(final String key) throws DataAccessException;

    /**
     * 检查key是否已经存在
     *
     * @param key key值
     * @return 是否已经存在
     * @throws DataAccessException 数据访问异常
     */
    Boolean exists(final String key) throws DataAccessException;

    /**
     * 清空所有缓存数据
     *
     * @return 执行操作是否成功
     * @throws DataAccessException 数据访问异常
     */
    Boolean flushDB() throws DataAccessException;

    /**
     * 查看缓存里有多少数据
     *
     * @return 缓存数据条数
     * @throws DataAccessException 数据访问异常
     */
    Long dbSize() throws DataAccessException;

    /**
     * 检查是否连接成功
     *
     * @return 是否连接成功
     * @throws DataAccessException 数据访问异常
     */
    String ping() throws DataAccessException;
}
