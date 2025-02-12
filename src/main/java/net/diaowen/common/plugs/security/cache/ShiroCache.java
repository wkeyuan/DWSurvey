package net.diaowen.common.plugs.security.cache;

import net.diaowen.common.plugs.cache.redis.RedisManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

/**
 * 重写Shiro的Cache保存读取
 * @param <K>
 * @param <V>
 */

public class ShiroCache<K,V> implements Cache<K,V> {

    private RedisManager redisManager;

    private String cacheName;

    public ShiroCache(RedisManager redisManager,String cacheName) {
        this.redisManager = redisManager;
        this.cacheName = cacheName;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public Object get(Object key) throws CacheException{
        String tempKey= this.getKey(key);
        Object result=null;
        if(redisManager.hashHasKey(cacheName,tempKey)){
            result = redisManager.hashGet(cacheName,tempKey);
        }
        return result;
    }

    /**
     * 保存缓存
     * @param key
     * @param value
     * @return
     * @throws CacheException
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        String tempKey= this.getKey(key);
        redisManager.hashSet(cacheName,tempKey,value, 30*60);
        return value;
    }

    /**
     * 移除缓存
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public Object remove(Object key) throws CacheException {
        Object value = null;
        String tempKey= this.getKey(key);
        if(redisManager.hashHasKey(cacheName,tempKey)){
            value = redisManager.hashGet(cacheName,tempKey);
            redisManager.hashDel(cacheName,tempKey);
        }
        return value;
    }

    @Override
    public void clear() throws CacheException {
        redisManager.deleteKey(cacheName);
    }

    @Override
    public int size() {
        return redisManager.hashSize(cacheName).intValue();
    }

    @Override
    public Set<K> keys() {
        return (Set<K>) redisManager.hashMKeys(cacheName);
    }

    @Override
    public Collection<V> values() {
        return (Collection<V>) redisManager.hashMValues(cacheName);
    }

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return
     */
    private String getKey(Object key) {
        return key.toString();
    }

}
