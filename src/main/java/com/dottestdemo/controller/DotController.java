package com.dottestdemo.controller;

import com.dottestdemo.service.DotService;
import com.dottestdemo.utils.ReadJsonFileUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dot")
public class DotController {

    @Resource
    private DotService dotService;

    @Resource
    @Qualifier("hiveDruidDataSource")
    DataSource druidDataSource;

    @RequestMapping(value = "/check", method = {RequestMethod.POST})
    public @ResponseBody
    ModelAndView check(@RequestParam("file") MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>();
        if (file.isEmpty()) {
            resultMap.put("message", "上传失败，请选择文件");
            return new ModelAndView(new MappingJackson2JsonView(), resultMap);
        }
        try {
            Map<String, Object> mmp = ReadJsonFileUtil.getMap(file.getInputStream());

            // Map<String, Object> mmp = ReadJsonFileUtil.getMap("dot.json");
            //mmp.forEach((k, v) -> {
            //  System.out.println(k + ": " + v);
            //});

            // 验证打点
            List<Map<String, Object>> datas = dotService.queryEvent(mmp);

            resultMap.put("data", datas);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView(new MappingJackson2JsonView(), resultMap);
    }
}
