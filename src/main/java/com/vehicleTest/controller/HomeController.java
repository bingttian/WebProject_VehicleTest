package com.vehicleTest.controller;

import com.vehicleTest.model.UpFile;
import com.vehicleTest.model.User;
import com.vehicleTest.service.UpFileService;
import com.vehicleTest.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by zyl on 2017/5/10.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private  UpFileService upFileService;

    //动态跳转页面
    @RequestMapping(path = {"/{formName}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String form(@PathVariable String formName){
        return formName;
    }

    //退出登录
    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(HttpServletRequest request, HttpServletResponse response, @CookieValue("id") String id){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("id")){
                cookie.setValue(null);
                cookie.setMaxAge(0);    // 立即销毁cookie
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }
        return "redirect:/index";
    }

    //上传文件
    @RequestMapping(value="/upfile",method=RequestMethod.POST)
    public String upload(HttpServletRequest request,Model model,
                         @RequestParam("file") MultipartFile file,
                         @CookieValue("id") String id,
                         @RequestParam("description") String description) throws Exception {
        // 如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            // 上传文件路径
            String path = "D:\\upload";
            // 上传文件名
            String username=userService.getUser(Integer.parseInt(id)).getName();
            String filename = username+file.getOriginalFilename();
            File filepath = new File(path, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            // 将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
            Map<String,Object> map = upFileService.addFile(filename,username,description);
            Map<String,Object> data = upFileService.readTxtFile(path + File.separator + filename,filename);
            model.addAttribute("data",data);
            model.addAttribute("msg",map.get("msg"));
            showUser(model,id);
            return "upload";
        } else {
            model.addAttribute("msg","上传失败");
            return "redirect:/upload";
        }
    }

    public void showUser(Model model, String id ){
        User user =userService.getUser(Integer.parseInt(id));
        model.addAttribute("id",user.getId());
        model.addAttribute("username",user.getName());
    }

    @RequestMapping(path = {"/top"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String top(Model model, @CookieValue("id") String id ){
        showUser(model,id);
        return "top";
    }

    @RequestMapping(path = {"/user"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String user(Model model, @CookieValue("id") String id ){
        User user=userService.getUser(Integer.parseInt(id));
        model.addAttribute("username",user.getName());
        model.addAttribute("telephone",user.getTelephone());
        model.addAttribute("address",user.getAddress());
        return "user";
    }

    @RequestMapping(path = {"/history"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String history(Model model, @CookieValue("id") String id ){
        User user=userService.getUser(Integer.parseInt(id));
        List<UpFile> upFiles=upFileService.getFileByUser(user.getName());    //获取用户所有上传记录的集合
        Map<String,Object> data =new HashedMap();
        for(int i=0;i<upFiles.size();i++){
            Map<String,Object> line =new HashedMap();
            SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            line.put("upDate",sdf.format(upFiles.get(i).getUp_time()));
            line.put("description",upFiles.get(i).getDescription());
            line.put("result",upFiles.get(i).getResult());
            data.put("line"+i,line);
        }
        model.addAttribute("data",data);
        return "history";
    }

    @RequestMapping(path = {"/change"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String change(Model model, @CookieValue("id") String id,
                         @RequestParam("username") String username,@RequestParam("telephone") String telephone,
                         @RequestParam("address") String address){
        if(username !=""){
            String msg=userService.changeUsername(id,username);
            model.addAttribute("msg_name",msg);
        }
        if(telephone !=""){
            String msg=userService.changeTelephone(id,telephone);
            model.addAttribute("msg_tel",msg);
        }
        if(address !=""){
            String msg=userService.changeAddress(id,address);
            model.addAttribute("msg_address",msg);
        }
        User user=userService.getUser(Integer.parseInt(id));
        model.addAttribute("username",user.getName());
        model.addAttribute("telephone",user.getTelephone());
        model.addAttribute("address",user.getAddress());
        return "user";
    }
}
