package com.xlizy.middleware.cc.client.core;

import com.alibaba.fastjson.JSONObject;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
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
    /** 需要置空的属性,原因可能是配置中心将它设为'未启用了' */
    private volatile static Set<String> need_set_null_prop = new HashSet<>();

    public static Set<String> getNeedSetNullProp(){
        Set<String> set;
        lock.readLock().lock();
        try {
            set = need_set_null_prop;
        } finally {
            lock.readLock().unlock();
        }
        return set;
    }

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
            Set<String> oldP = new HashSet();
            Set<String> newP = new HashSet();
            for (Object s : prop.keySet()) {
                oldP.add(s.toString());
            }
            for (String s : props.keySet()) {
                newP.add(s);
            }
            oldP.removeAll(newP);
            need_set_null_prop.clear();
            need_set_null_prop.addAll(oldP);
            prop.clear();
            for (String s : props.keySet()) {
                prop.put(s,props.get(s));
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

}
