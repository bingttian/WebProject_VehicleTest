package com.vehicleTest.controller;

import com.vehicleTest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by zyl on 2017/5/9.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    //起始登录界面
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return "Login";
        }
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                return "main";
            }
        }
        return "Login";
    }

    //登录操作
    @RequestMapping(path = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String  login(Model model, @RequestParam("username") String username,
                       @RequestParam("password") String password,
                         HttpServletResponse response) {
        Map res=userService.login(username,password);
        if(res.get("msg") !=null){
            int id=userService.getUser(username).getId();
            Cookie cookie = new Cookie("id", String.format("%d",id));
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/main";
        }else {
            if(res.get("user_msg")!=null)
            model.addAttribute("username_error",res.get("user_msg").toString());
            if(res.get("psw_msg")!=null)
            model.addAttribute("password_error",res.get("psw_msg").toString());
            return "Login";
        }
    }
}
