package com.xlizy.middleware.cc.client.core;

import com.alibaba.fastjson.JSONObject;

import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 客户应用通过此类获取配置
 * @author xlizy
 * @date 2018/5/14
 */
public final class ConfigCenter {

    /** 读写锁 */
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /** 存储配置信息 */
    private volatile static Properties prop = new Properties();

    /** 获取所有配置 */
    public static Properties getProperties(){
        Properties properties;
        lock.readLock().lock();
        try {
            properties = prop;
        } finally {
            lock.readLock().unlock();
        }
        return properties;
    }

    /** 根据key获取配置,返回Object类型 */
    public static Object get(String key){
        Object obj;
        lock.readLock().lock();
        try {
            obj = prop.get(key);
        } finally {
            lock.readLock().unlock();
        }
        return obj;
    }

    /** 根据key获取配置,返回String类型 */
    public static String getPropertie(String key){
        String str;
        lock.readLock().lock();
        try {
            str = prop.getProperty(key);
        } finally {
            lock.readLock().unlock();
        }
        return str;
    }

    /** 根据key获取配置,返回Object类型,如果没有则返回默认值 */
    public static String getPropertie(String key,String def){
        String str;
        lock.readLock().lock();
        try {
            str = prop.getProperty(key,def);
        } finally {
            lock.readLock().unlock();
        }
        return str;
    }

    /** 根据props对象重新加载配置信息 */
    private static void reloadProperties(Properties props){
        lock.writeLock().lock();
        try {
            prop.clear();
            for (Object s : props.keySet()) {
                prop.put(s,props.get(s));
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /** 根据props对象重新加载配置信息 */
    private static void reloadProperties(JSONObject props){
        lock.writeLock().lock();
        try {
            prop.clear();
            for (String s : props.keySet()) {
                prop.put(s,props.get(s));
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

}
