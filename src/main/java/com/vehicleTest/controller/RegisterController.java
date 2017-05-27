package com.vehicleTest.controller;

import com.vehicleTest.Util.VehicleTestUtil;
import com.vehicleTest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by zyl on 2017/5/9.
 */
@Controller
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    //注册界面
//    @RequestMapping(path = {"/reg"}, method = {RequestMethod.GET, RequestMethod.POST})
//    public String reg(){
//        return "regist";
//    }

    //注册操作
    @RequestMapping(path = {"/reg_res"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String  reg(Model model, @RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("password1") String password1,
                       HttpServletResponse response, @RequestParam("telephone") String telephone,
                       @RequestParam("address") String address) {
        if(!password1.equals(password)){
            model.addAttribute("psw_error","输入密码不一致");
            return "reg";
        }
        Map res=userService.register(username,password,telephone,address);
        if (res.get("msg")!= null){
            int id=userService.getUser(username).getId();
            Cookie cookie = new Cookie("id", String.format("%d",id));
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("username",username);
            return "reg_res";
        }else {
            if(res.get("username_error")!=null)
                model.addAttribute("username_error", res.get("username_error"));
            if(res.get("psw_error")!=null)
                model.addAttribute("psw_error", res.get("psw_error"));
            if(res.get("tel_error")!=null)
                model.addAttribute("tel_error", res.get("tel_error"));
            if(res.get("address_error")!=null)
                model.addAttribute("address_error", res.get("address_error"));
                return "reg";
        }
    }
}
