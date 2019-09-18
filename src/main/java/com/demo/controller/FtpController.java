package com.demo.controller;

import com.demo.utils.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Ftp控制类，上传图片和返回路径
 * @author jack
 * @date 2019-09-10
 */
@Controller
@RequestMapping(value = "ftp")
public class FtpController {

    private final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
    @Autowired
    private FtpUtil ftpUtil;

    /**
     * 上传图片到Ftp的接口
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException{
        Map<String, String> map = new HashMap<>();
        map.put("code","0");
        map.put("msg","上传文件失败");
        String fileName = file.getOriginalFilename();
        logger.info("上传文件：{}", fileName);
        InputStream inputStream = file.getInputStream();
        String filePath = null;
        Boolean flag = ftpUtil.uploadFile(fileName, inputStream);
        if(flag == true){
            logger.info("上传文件成功！");
            filePath = ftpUtil.FTP_BASEPATH + fileName;
            map.put("code","1");
            map.put("msg","上传文件成功");
        }
        map.put("path",filePath);
        //返回的路径可以
        return map;
    }
}
