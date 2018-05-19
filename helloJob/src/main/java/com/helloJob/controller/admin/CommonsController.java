package com.helloJob.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.helloJob.commons.shiro.captcha.DreamCaptcha;

/**
 * 通用的控制器
 * @author L.cm
 *
 */
@Controller
public class CommonsController {
    @Autowired
    private DreamCaptcha dreamCaptcha;
    
    /**
     * 图标页
     */
    @GetMapping("icons.html")
    public String icons() {
        return "icons";
    }
    
    /**
     * 图形验证码
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        dreamCaptcha.generate(request, response);
    }
    
}
