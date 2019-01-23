package com.xlizy.middleware.cc.server.common.utils;

/**
 * 字符串工具类
 * @author xlizy
 * @date 2018/3/13
 */
public class StringUtils extends org.springframework.util.StringUtils {

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 数组转字符串
     * */
    public static String arr2str(String[] strs,String delimiter){
        String res = "";
        if (strs != null && strs.length > 0) {
            for (String s : strs) {
                res += s;
                res += delimiter;
            }
            res = res.substring(0,res.lastIndexOf(delimiter));
        }
        return res;
    }
}
