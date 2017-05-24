package com.vehicleTest.service;

import com.vehicleTest.DAO.UpFileDAO;
import com.vehicleTest.model.UpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyl on 2017/5/10.
 */
@Service
public class UpFileService {
    private  static final Logger logger= LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UpFileDAO upFileDAO;

    public UpFile getFileByName(String filename){
        return upFileDAO.selectByFile(filename);
    }

    public UpFile getFileByUser(String user){
        return upFileDAO.selectByUser(user);
    }

    public  void  deleteFile(String filename){
        upFileDAO.deleteByName(filename);
    }

    public Map<String,Object> addFile(String filename, String username,String description){
        Map<String,Object> map=new HashMap();
        if(upFileDAO.selectByFile(filename) != null){
            map.put("msg","文件已存在");
            return map;
        }
        UpFile upFile=new UpFile();
        upFile.setFilename(filename);
        upFile.setUp_time(new Date());
        upFile.setUsername(username);
        if(description.length()>1024){
            map.put("msg","故障描述字数超过上限");
        }else{
            upFile.setDescription(description);
            upFileDAO.addfile(upFile);
            map.put("msg","文件上传成功");
        }
        return  map;
    }

    /**
     * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流 4：一行一行的写入到str。readline()。 备注：需要考虑的是异常情况
     */
    public Map<String,Object> readTxtFile(String filePath,String filename) {
        Map<String,Object> map=new HashMap();
        String[] data;
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            int i = 0;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                data=lineTxt.split("=");
                map.put(data[0],data[1]);
                i++;
            }
            UpFile upFile=new UpFile();
            upFile.setFilename(filename);
            /*******************诊断点**********************/
            upFile.setZhouzhong((String)map.get("轴重"));
            upFile.setZhidong((String)map.get("制动"));
            /***********************************************/
            upFileDAO.updateInfo(upFile);
            read.close();
        } catch (Exception e) {
            map.put("msg","读取文件内容出错");
            return map;
        }
        map.put("msg","文件解析成功");
        return map;
    }
}
