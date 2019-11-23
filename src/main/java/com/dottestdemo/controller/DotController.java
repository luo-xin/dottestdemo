package com.dottestdemo.controller;

import com.dottestdemo.bean.Dot;
import com.dottestdemo.service.DotService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dot")
public class DotController {

    @Resource
    private DotService dotService;


    @RequestMapping(value = "/check", method = {RequestMethod.POST})
    public @ResponseBody
    ModelAndView check(Dot dot) {
        Map<String, Boolean> resultMap = new HashMap<>();

        System.out.println(dot.getDt());
        System.out.println(dot.getEvent());

        boolean dotRst = false;
        try {
            dotRst = dotService.queryEvent(dot);

            resultMap.put("result", dotRst);
        } catch (Exception e) {
            e.printStackTrace();

            resultMap.put("result", false);
        }

        return new ModelAndView(new MappingJackson2JsonView(), resultMap);
    }
}
