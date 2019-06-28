package com.cqyc.shixun.controller;

import com.cqyc.shixun.comm.exception.ExceptionEnums;
import com.cqyc.shixun.comm.exception.YcException;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 读写文件控制器
 */
@RestController
public class IOFileController {

    /**
     *  读取图片
     */
    @GetMapping("/readPic")
    public void showImg(String imgpath, HttpServletResponse response) throws IOException {
        //存放图片的文件夹地址
        String path="D:\\mywork\\springbootdemo\\";
        FileInputStream fileIS =null;
        OutputStream outputStream = null;
        try {
            fileIS = new FileInputStream(path+imgpath);
            //得到文件大小
            int i = fileIS.available();
            //准备一个字节数组存放二进制图片
            byte[] data = new byte[i];
            //读取字节数组的依据
            fileIS.read(data);
            //设置返回文件类型
            response.setContentType("image/*");
            //得到向客户端输出二进制数据的对象
            outputStream = response.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
        }catch (Exception e){
            System.out.println("系统找不到图像文件："+path+imgpath);
            return;
        }finally {
            //关闭输出流
            outputStream.close();
            //关闭输入流
            fileIS.close();
        }
    }

    @PostMapping("/writeFile")
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile img){
        //做文件上传处理
        if (img.isEmpty()) {
            throw new YcException(ExceptionEnums.UPLOAD_IMAGE_ERROR);
        }
        String fileName = UUID.randomUUID().toString().substring(1,10) + img.getOriginalFilename();
        String path = "D:\\mywork\\springbootdemo\\";
        File file = new File(path + fileName);
        //如果该图片父级文件夹不存在，则创建
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        try {
            img.transferTo(file);
        } catch (IOException e) {
            throw new YcException(ExceptionEnums.UPLOAD_IMAGE_ERROR);
        }
        Map<String, String> map = new HashMap<>();
        map.put("msg","ok");
        map.put("code","0");
        map.put("fileName",fileName);
        return  ResponseEntity.ok(map);
    }
}
