package com.dottestdemo.dao;


import com.dottestdemo.bean.Dot;
import com.dottestdemo.service.DotService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class HiveDao {

    @Resource
    @Qualifier("hiveDruidDataSource")
    DataSource druidDataSource;


    // 查询打点数据
    public List<Map<String, Object>> queryEvent(String dt, String[] deviceNames, String[] events) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        Connection conn = druidDataSource.getConnection();
//        '选房大师_地图页_页面进入','选房大师_地图页_开始吧'

        String eventStr = "";
        for (int i = 0; i < events.length; i++) {
            eventStr = ",'" + events[i] + "'" + eventStr;
        }
        if (!"".equals(eventStr)) {
            eventStr = eventStr.substring(1, eventStr.length());
        }

        String deviceNameStr = "";
        for (int i = 0; i < deviceNames.length; i++) {
            deviceNameStr = ",'" + deviceNames[i] + "'" + deviceNameStr;
        }
        if (!"".equals(deviceNameStr)) {
            deviceNameStr = deviceNameStr.substring(1, deviceNameStr.length());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select * from fangdd_data.user_trace where");
        if (!"".equals(dt)) {
            sb.append(" dt = '" + dt + "'");
        }
        if (!"".equals(eventStr)) {
            sb.append(" and event in (" + eventStr + ")");
        }
        if (!"".equals(deviceNameStr)) {
            sb.append(" and devicename in (" + deviceNameStr + ")");
        }

        System.out.println(sb.toString());

        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sb.toString());

        int columnCount = res.getMetaData().getColumnCount();
        while (res.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String mKey = res.getMetaData().getColumnName(i);
                mKey = mKey.substring("user_trace.".length(), mKey.length());

                map.put(mKey, res.getObject(i));
//                System.out.printf("shujuku -- %s %s \n", mKey, res.getObject(i));
            }
            list.add(map);
        }

        res.close();
        stmt.close();

        return list;
    }
}
