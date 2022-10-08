package com.xuhao.utils;


import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils{
    public BeanCopyUtils() {
    }

    public static <V> V copyBean(Object source, Class<V> clazz) {
        V o = null;
        try {
            // 通过反射创建对象
            o = clazz.newInstance();
            BeanUtils.copyProperties(source, o);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static <Object, V> List<V> copyBeanList(List<Object> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
