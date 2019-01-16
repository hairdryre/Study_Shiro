package com.jay.shiro;

import org.slf4j.Logger;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author jay.zhou
 * @date 2019/1/15
 * @time 13:28
 * 源码参考来源：https://blog.csdn.net/zsg88/article/details/72788514
 */
public final class SerializerUtil {

    private static final Logger LOGGER = getLogger(SerializerUtil.class);

    private static final JdkSerializationRedisSerializer SERIALIZER = new JdkSerializationRedisSerializer();

    private SerializerUtil() {
    }

    /**
     * 序列化对象
     * 将对象转化为字节数组存储
     *
     * @param obj 对象
     * @return 序列化对象字节数组
     */
    public static <T> byte[] serialize(T obj) {
        try {
            return SERIALIZER.serialize(obj);
        } catch (SerializationException e) {
            LOGGER.error("序列化失败:{}", e);
            return new byte[0];
        }
    }

    /**
     * 反序列化对象
     * 将字节数组反序列化为对象
     *
     * @param bytes 字节数组
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] bytes) {
        try {
            final Object obj = SERIALIZER.deserialize(bytes);
            return (T) obj;
        } catch (SerializationException e) {
            LOGGER.error("反序列化失败:{}", e);
            return null;
        }
    }
}