package com.jay.shiro.cache;

import com.jay.redis.RedisService;
import com.jay.shiro.SerializerUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;

import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public final class RedisCache<K, V> implements Cache<K, V> {

    private static final Logger LOGGER = getLogger(RedisCache.class);
    private static final String DEFAULT_CHARSET = "UTF-8";

    private RedisService cache;

    /**
     * 用于Cache的Redis key前缀
     */
    private String keyPrefix;

    /**
     * Cache缓存时间，单位秒
     */
    private Long liveSeconds;

    /**
     * 通过一个JedisManager实例构造RedisCache
     */
    public RedisCache(RedisService cache) {
        if (null == cache) {
            throw new IllegalArgumentException("Cache对象为空.");
        }
        this.cache = cache;
    }

    /**
     * 通过一个JedisManager实例和前缀值构造RedisCache
     */
    public RedisCache(RedisService cache, String prefix) {
        this(cache);
        this.keyPrefix = prefix;
    }

    /**
     * 通过一个JedisManager实例和前缀值构造RedisCache（支持失效时间，单位秒）
     */
    public RedisCache(RedisService cache, String prefix, Long liveSeconds) {
        this(cache, prefix);
        this.liveSeconds = liveSeconds;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public Long getLiveSeconds() {
        return liveSeconds;
    }

    public void setLiveSeconds(Long liveSeconds) {
        this.liveSeconds = liveSeconds;
    }

    /**
     * 获得byte[]型的key
     *
     * @param key key值
     * @return byte[]型的key
     */
    private byte[] getByteKey(K key) {
        if (key instanceof String) {
            String preKey = this.keyPrefix + key;
            return preKey.getBytes(Charset.forName(DEFAULT_CHARSET));
        } else {
            return SerializerUtil.serialize(key);
        }
    }

    /**
     * 根据key获取缓存的数据
     *
     * @param key 键
     * @return 值
     * @throws CacheException
     */
    @Override
    @SuppressWarnings("unchecked")
    public V get(K key) throws CacheException {
        try {
            if (null == key) {
                return null;
            } else {
                final Object obj = cache.get(getByteKey(key));
                return (V) obj;
            }
        } catch (Exception t) {
            throw new CacheException(t);
        }
    }

    /**
     * 与put方法是反操作
     *
     * @param key
     * @param value
     * @return
     * @throws CacheException
     * @see #get(Object)
     */
    @Override
    public V put(K key, V value) throws CacheException {
        try {
            cache.setEx(getByteKey(key), value, liveSeconds);
            return value;
        } catch (Exception t) {
            throw new CacheException(t);
        }
    }

    /**
     * Shiro的logout方法会自动调用此方法
     *
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public V remove(K key) throws CacheException {
        try {
            V previous = get(key);
            //从Redis中删除指定key的缓存
            cache.del(getByteKey(key));
            return previous;
        } catch (Exception t) {
            throw new CacheException(t);
        }
    }

    @Override
    public void clear() throws CacheException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("从Redis中删除所有对象.");
        }
        try {
            cache.flushDB();
        } catch (Exception t) {
            throw new CacheException(t);
        }
    }

    @Override
    public int size() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("查看Redis中有多少数据.");
        }
        try {
            final Long longSize = cache.dbSize();
            return longSize.intValue();
        } catch (Exception t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Set<K> keys() {
        try {
            final Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                return keys.stream().map(key -> (K) key).collect(Collectors.toSet());
            }
        } catch (Exception t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Collection<V> values() {
        try {
            //根据前缀，获取所有Key，再获取所有的Value
            final Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptyList();
            } else {
                final List<V> values = new ArrayList<>(keys.size());
                for (byte[] key : keys) {
                    final V value = get((K) key);
                    if (null != value) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            }
        } catch (Exception t) {
            throw new CacheException(t);
        }
    }
}
