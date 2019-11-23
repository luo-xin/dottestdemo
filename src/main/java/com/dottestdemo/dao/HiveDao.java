package com.dottestdemo.dao;

import com.dottestdemo.bean.Dot;
import org.apache.parquet.Strings;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class HiveDao {

    private Connection conn;

    public boolean queryEvent(String dt, String[] events) throws Exception {
        System.out.println("-----conn ---: " + conn);
        if (null == conn) {
            conn = DriverManager.getConnection(
                    "jdbc:hive2://10.50.255.141:10000/default", "luoxin01", "luoxin01@123");
        }

//        String querySQL = "select devicename,event from fangdd_data.user_trace where dt='" + dt + "' and event in " +
//                "('选房大师_地图页_页面进入','选房大师_地图页_开始吧')";

        String eventsStr = "";
        for (int i = 0; i < events.length; i++) {
            eventsStr = eventsStr + ",'" + events[i] + "'";
        }
        eventsStr = eventsStr.substring(1, eventsStr.length());

        StringBuilder sql = new StringBuilder();
//        sql.append("select devicename, event from fangdd_data.user_trace");
//        sql.append("select count(0) from fangdd_data.user_trace");
        sql.append("select devicename, event, type from fangdd_data.user_trace");
        sql.append(" where dt = " + dt);
        sql.append(" and event in (" + eventsStr + ")");

        System.out.println(sql);

        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery(sql.toString()); // 执行查询语句

        int resultCount = 0;
        while (res.next()) {
            resultCount = res.getInt(1);
            System.out.println("统计结果 " + res.getInt(1));
//            System.out.println("查询结果" + res.getString(1) + "  –>  value:" + res.getString(2));
        }

        stmt.close();
        res.close();

        if (resultCount > 0) {
            return true;
        }

        return false;
    }


}
