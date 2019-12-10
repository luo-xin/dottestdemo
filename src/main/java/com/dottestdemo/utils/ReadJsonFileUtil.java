package com.dottestdemo.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReadJsonFileUtil {
    public static Map getMap(String path) {
        Map map = new LinkedHashMap();
        try {
            //路径
            ClassPathResource classPathResource = new ClassPathResource(path);
            //读取文件信息
            String str = IOUtils.toString(new InputStreamReader(classPathResource.getInputStream(), "UTF-8"));
            //转换为Map对象
            map = JSONObject.parseObject(str, LinkedHashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map getMap(InputStream is) {
        Map map = new LinkedHashMap();
        try {
            //读取文件信息
            String str = IOUtils.toString(is, "UTF-8");
            //转换为Map对象
            map = JSONObject.parseObject(str, LinkedHashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
