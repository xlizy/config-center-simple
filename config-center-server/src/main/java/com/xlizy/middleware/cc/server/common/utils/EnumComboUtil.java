package com.xlizy.middleware.cc.server.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.server.common.base.EnumCombo;

import java.util.List;

/**
 * 枚举下拉框工具类
 * @author xlizy
 * @date 2018/3/13
 */
public class EnumComboUtil {

    /**
     * 将list转换为Extjs能认的数组
     * */
    public static JSONArray getComboArray(List<EnumCombo> list){
        JSONArray array = new JSONArray();
        for (EnumCombo combo : list) {
            JSONObject e = new JSONObject();
            e.put("value",combo.getValue());
            e.put("desc",combo.getDesc());
            array.add(e);
        }
        return array;
    }

}
