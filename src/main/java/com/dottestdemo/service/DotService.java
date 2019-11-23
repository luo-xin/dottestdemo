package com.dottestdemo.service;

import com.dottestdemo.bean.Dot;
import com.dottestdemo.dao.HiveDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class DotService {

    @Resource
    private HiveDao hiveDao;

    public boolean queryEvent(Dot dot) throws Exception {
//        return hiveDao.queryEvent(dot.getDt(),);


    }
}
