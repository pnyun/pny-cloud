package com.pny.core.util;

import com.pny.core.entity.ToMapDTO;
import com.pny.util.CollectionUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;

/**
 * 对象传输工具
 * 
 * @author pmyun
 *
 */
public class DTOUtil {

    public static <T> Map<String, Object> toMapPage(Page<T> page) {

        Map<String, Object> map = new HashMap<>();

        map.put("total", page.getTotal());
        map.put("size", page.getSize());
        map.put("pages", page.getPages());
        map.put("current", page.getCurrent());
        map.put("records", page.getRecords());

        return map;
    }

    /**
     * Page 对象转Map
     * 
     * @param page
     * @param records
     * @return
     */
    public static <T> Map<String, Object> toMapPage(Page<T> page, List<Map<String, Object>> records) {

        Map<String, Object> map = new HashMap<>();

        map.put("total", page.getTotal());
        map.put("size", page.getSize());
        map.put("pages", page.getPages());
        map.put("current", page.getCurrent());
        map.put("records", records);

        return map;
    }

    /**
     * 对象集合转 map
     * 
     * @param list
     * @return
     */
    public static List<Map<String, Object>> getMapDTOList(List<? extends ToMapDTO> list) {

        if (CollectionUtil.isBlank(list)) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> result = list.stream().map(t -> {
            return t.toDTO();
        }).collect(Collectors.toList());

        return result;
    }

    /**
     * 对象集合转 map
     * 
     * @param obj
     * @return
     */
    public static Map<String, Object> objToMap(Object obj) {

        if (obj == null) {
            return new HashMap<>();
        }

        String text = JSONObject.toJSONString(obj);

        if (StringUtils.isBlank(text)) {
            return new HashMap<>();
        }

        Map<String, Object> result = JSONObject.parseObject(text);

        return result;
    }

}
