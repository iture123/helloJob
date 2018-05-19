package com.helloJob.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helloJob.commons.base.BaseController;
import com.helloJob.commons.csrf.CsrfToken;
import com.helloJob.commons.shiro.captcha.DreamCaptcha;
import com.helloJob.commons.utils.StringUtils;

/**
 * @description：登录退出
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
@Controller
public class LoginController extends BaseController {
    @Autowired
    private DreamCaptcha dreamCaptcha;
    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

    /**
     * GET 登录
     * @return {String}
     */
    @GetMapping("/login")
    @CsrfToken(create = true)
    public String login() {
        logger.info("GET请求登录");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        return "login";
    }

    /**
     * POST 登录 shiro 写法
     *
     * @param username 用户名
     * @param password 密码
     * @return {Object}
     */
    @PostMapping("/login")
    @CsrfToken(remove = true)
    @ResponseBody
    public Object loginPost(HttpServletRequest request, HttpServletResponse response,
            String username, String password, String captcha, 
            @RequestParam(value = "rememberMe", defaultValue = "0") Integer rememberMe) {
        logger.info("POST请求登录");
        // 改为全部抛出异常，避免ajax csrf token被刷新
        if (StringUtils.isBlank(username)) {
        	return renderError("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
        	return renderError("密码不能为空");
        }
        if (StringUtils.isBlank(captcha)) {
        	return renderError("验证码不能为空");
        }
        if (!dreamCaptcha.validate(request, response, captcha)) {
        	return renderError("验证码错误");
        }
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 设置记住密码
        token.setRememberMe(1 == rememberMe);
        try {
            user.login(token);
            return renderSuccess();
        } catch (UnknownAccountException e) {
        	return renderError("账号不存在！");
           // throw new RuntimeException("账号不存在！", e);
        } catch (DisabledAccountException e) {
        	return renderError("账号未启用！");
           // throw new RuntimeException("账号未启用！", e);
        } catch (IncorrectCredentialsException e) {
        	return renderError("密码错误！");
           // throw new RuntimeException("密码错误！", e);
        } catch (Throwable e) {
        	return renderError(e.getMessage());
            //throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 未授权
     * @return {String}
     */
    @GetMapping("/unauth")
    public String unauth() {
        if (SecurityUtils.getSubject().isAuthenticated() == false) {
            return "redirect:/login";
        }
        return "unauth";
    }

    /**
     * 退出
     * @return {Result}
     */
    @PostMapping("/logout")
    @ResponseBody
    public Object logout() {
        logger.info("登出");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return renderSuccess();
    }

}
