package com.vehicleTest.service;

import com.vehicleTest.DAO.UserDAO;
import com.vehicleTest.Util.VehicleTestUtil;
import com.vehicleTest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zyl on 2017/5/9.
 */
@Service
public class UserService {
    private  static final Logger logger= LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public User getUser(String username){
        return userDAO.selectByName(username);
    }

    public Map<String,Object> register(String username, String password){
        Map<String, Object> map = new HashMap<String, Object>();
        if(username ==""){
            map.put("username_error","用户名不能为空");
            return  map;
        }
        if(password == ""){
            map.put("psw_error","密码不能为空");
            return  map;
        }

        User user=userDAO.selectByName(username);

        if(user !=null){
            map.put("username_error","用户名已经被注册");
            return  map;
        }

        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(VehicleTestUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        map.put("msg","注册成功");
        return  map;
    }



    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (username == "") {
            map.put("user_msg", "用户名不能为空");
            return map;
        }

        if (password == null) {
            map.put("psw_msg", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if(user == null){
            map.put("user_msg", "输入有误");
            return map;
        }

        if (!VehicleTestUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("user_msg", "输入有误");
            return map;
        }

        map.put("msg", "登录成功");
        return map;
    }
}
