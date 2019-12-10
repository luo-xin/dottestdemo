package com.dottestdemo.service;

import com.dottestdemo.bean.Dot;
import com.dottestdemo.dao.HiveDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class DotService {

    @Resource
    private HiveDao hiveDao;

    public List<Map<String, Object>> queryEvent(Map<String, Object> map) throws Exception {
        // 参数 dot
        List<Map<String, Object>> events = (List<Map<String, Object>>) map.get("dot");
        List<String> eventArr = new ArrayList<>();
        Map<String, String> deviceMap = new HashMap<>();

        String dt = "";
        for (int i = 0; i < events.size(); i++) {
            Map<String, Object> m = events.get(i);

            Object nameObj = m.get("name");
            if (null != nameObj) {
                eventArr.add(nameObj.toString());
            }


            Object dnObj = m.get("device_type");
            if (null != dnObj) {
                deviceMap.put(dnObj.toString(), "");
            }

            dt = m.get("dt").toString();
        }

        String[] eventArrSt = new String[eventArr.size()];
        for (int i = 0; i < eventArr.size(); i++) {
            eventArrSt[i] = eventArr.get(i);
        }

        Object[] dnObjArr = deviceMap.keySet().toArray();
        String[] deviceNameSt = new String[dnObjArr.length];
        for (int i = 0; i < dnObjArr.length; i++) {
            deviceNameSt[i] = dnObjArr[i].toString();
        }

        // 查询结果
        List<Map<String, Object>> list = hiveDao.queryEvent(dt, deviceNameSt, eventArrSt);

        // 遍历参数 map
        events.forEach(mmp -> {
            Object deviceTypeObj = mmp.get("devicetype");
            String deviceType = null == deviceTypeObj ? "" : deviceTypeObj.toString();

            Object eventNameObj = mmp.get("name");
            String eventName = null == eventNameObj ? "" : eventNameObj.toString();

            Object targetUrlObj = mmp.get("targetUrl");
            String targetUrl = null == targetUrlObj ? "" : targetUrlObj.toString();

            Object moduleObj = mmp.get("module");
            String module = null == moduleObj ? "" : moduleObj.toString();

            Object currPageUrlObj = mmp.get("currPageUrl");
            String currPageUrl = null == currPageUrlObj ? "" : currPageUrlObj.toString();

            Object positionObj = mmp.get("position");
            String position = null == positionObj ? "" : positionObj.toString();

            Object houseIdObj = mmp.get("house_id");
            Long houseId = null == houseIdObj ? 0 : Long.valueOf(houseIdObj.toString());

            Object typeObj = mmp.get("type");
            String type = null == typeObj ? "" : typeObj.toString();


            // 和数据库中的数据进行匹配
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> lMap = list.get(i);

                Object ldeviceTypeObj = lMap.get("devicetype");
                String ldeviceType = null == ldeviceTypeObj ? "" : ldeviceTypeObj.toString();

                Object leventNameObj = lMap.get("event");
                String leventName = null == leventNameObj ? "" : leventNameObj.toString(); // 数据库中的名字

                Object ltargetUrlObj = lMap.get("targetUrl");
                String ltargetUrl = null == ltargetUrlObj ? "" : ltargetUrlObj.toString();

                Object lmoduleObj = lMap.get("module");
                String lmodule = null == lmoduleObj ? "" : lmoduleObj.toString();

                Object lcurrPageUrlObj = lMap.get("currPageUrl");
                String lcurrPageUrl = null == lcurrPageUrlObj ? "" : lcurrPageUrlObj.toString();

                Object lpositionObj = lMap.get("position");
                String lposition = null == lpositionObj ? "" : lpositionObj.toString();

                Object lhouseIdObj = lMap.get("house_id");
                Long lhouseId = null == lhouseIdObj ? Long.valueOf(-1) : Long.valueOf(lhouseIdObj.toString());

                Object ltypeObj = lMap.get("type");
                String ltype = null == ltypeObj ? "" : ltypeObj.toString();

                // 验证打点, 默认 true
                boolean check = true;

                if (check && null != deviceTypeObj) {
                    if (deviceType.equals(ldeviceType)) {
                        check = true;
                    } else {
                        check = false;
                    }
                }

                if (check && null != eventNameObj) {
                    if (eventName.equals(leventName)) {
                        check = true;
                    } else {
                        check = false;
                    }
                }

                if (check && null != targetUrlObj) {
                    if (targetUrl.equals(ltargetUrl)) {
                        check = true;
                    } else {
                        check = false;
                    }
                }

                if (check && null != moduleObj) {
                    if (module.equals(lmodule)) {
                        check = true;
                    } else {
                        check = false;
                    }
                }

                if (check && null != currPageUrlObj) {
                    if (currPageUrl.equals(lcurrPageUrl)) {
                        check = true;
                    } else {
                        check = false;
                    }
                }

                if (check && null != positionObj) {
                    if (position.equals(lposition)) {
                        check = true;
                    } else {
                        check = false;
                    }
                }

                if (check && null != houseIdObj) {
                    if (houseId.equals(lhouseId)) {
                        check = true;
                    } else {
                        check = false;
                    }
                }

                if (check && null != typeObj) {
                    if (type.equals(ltype)) {
                        check = true;
                    } else {
                        check = false;
                    }
                }


                // 赋值打点状态
                mmp.put("check_result", check ? "打点成功" : "打点失败");

                if (check) {
                    // 如果已经有数据, 则说明打点成功
                    break;
                }
            }
        });


        // 打印
        events.forEach((mmp) -> {
            mmp.forEach((k, v) -> {


                System.out.println(k + ": " + v);
                System.out.println(k + ": " + v);
            });
            System.out.println("----------");
        });

        return events;
    }
}
