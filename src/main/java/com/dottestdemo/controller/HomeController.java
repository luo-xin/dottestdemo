package com.dottestdemo.controller;

import com.dottestdemo.bean.Dot;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class HomeController {


    /**
     * @return
     * @RequestMapping("/") public String index() {
     * return "Hello world!";
     * }
     * @RequestMapping("/sayhello") public String sayHello(Integer id) throws Exception {
     * System.out.println("------ " + id + " -------");
     * String event = dotService.queryEvent(id);
     * <p>
     * return "这是测试页面: " + event;
     * }
     */

    @RequestMapping("/")
    public ModelAndView LoginIndex() {
        ModelAndView mav = new ModelAndView("/index");


        return mav;
    }

//    @RequestMapping(value = "/check", method = RequestMethod.POST)
//    public String login(@ModelAttribute Dot dot) {
//        String event = dot.getEvent();
//        String devicetype = dot.getDevicetype();
//        String targeturl = dot.getTargeturl();
//        String sourceid = dot.getSourceid();
//        String type = dot.getType();
//        if (event.equals("1") && targeturl.equals("1") && devicetype.equals("1") && sourceid.equals("1") && type.equals("1")) {
//            return "查询成功";
//        } else {
//            return "查询失败";
//        }
//    }
}
