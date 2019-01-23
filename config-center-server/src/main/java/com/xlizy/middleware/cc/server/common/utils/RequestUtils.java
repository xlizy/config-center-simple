package com.xlizy.middleware.cc.server.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求工具类
 * 从Request对象中获取一些信息
 * @author xlizy
 * @date 2018/3/10
 * */
public class RequestUtils {
    
    public static <T> Map<String,Object> getConditions(T obj){
        return getConditions(obj,null);
    }

    public static <T> Map<String,Object> getConditions(T obj, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>(16);
        if (obj != null) {
            try {
                for (Field field : obj.getClass().getDeclaredFields()) {
                    Method method=obj.getClass().getMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1));
                    Object temp = method.invoke(obj);
                    if(temp!=null&&temp.toString().trim().length()>0){
                        map.put(field.getName(), temp);
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if(request != null){
            try {
                map.put("limitStart", Integer.valueOf(StringUtils.isBlank(request.getParameter("start"))?"0":request.getParameter("start")));
                map.put("limitEnd", Integer.valueOf(StringUtils.isBlank(request.getParameter("end"))?"25":request.getParameter("end")));
            } catch (NumberFormatException e) {
            	e.printStackTrace();
            }
            try {
                map.put("limitEnd", Integer.valueOf(StringUtils.isBlank(request.getParameter("limit"))?"25":request.getParameter("limit")));
                map.put("sort", request.getParameter("sort"));
                map.put("dir", request.getParameter("dir"));
            } catch (NumberFormatException e) {
            	e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 从request中获取分配配置
     * */
    public static int[] getPageLimit(HttpServletRequest request){
        int[] limits = new int[]{0, Integer.MAX_VALUE};
        try {
            limits[0] = Integer.valueOf(StringUtils.isBlank(request.getParameter("start"))?"0":request.getParameter("start"));
        }catch (Exception e){}
        try {
            limits[1] = Integer.valueOf(request.getParameter("limit"));
        }catch (Exception e){}
        return limits;
    }

    /**
     * 功能描述：获取真实的IP地址
     * 获取客户端的IP地址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。
     * 但是在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址了。
     * 经过代理以后，由于在客户端和服务之间增加了中间层，
     * 因此服务器无法直接拿到客户端的IP，服务器端应用也无法直接通过转发请求的地址返回给客户端。
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String unknown= "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public RequestUtils() {
    }
}
