package com.demo.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;
import sun.net.ftp.FtpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * FtpUtil辅助工具类 FTP文件上传类
 *
 * @author jack
 * @date 2019-09-10
 */
@Component
public class FtpUtil {

    /**
     * ftp服务器的ip地址
     */
    private static final String FTP_ADDRESS = "192.168.215.128";
    /**
     * 端口号
     */
    private static final int FTP_PORT = 21;
    /**
     * ftp授权的用户名
     */
    private static final String FTP_USERNAME = "test";
    /**
     * 密码
     */
    private static final String FTP_PASSWORD= "123456";
    /**
     * ftp中图片路径
     */
    public final String FTP_BASEPATH = "/tmp/test/ImgLib";
    public boolean uploadFile(String originFileName, InputStream input){
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try{
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);
            ftp.login(FTP_USERNAME,FTP_PASSWORD);
            reply = ftp.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)){
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(FTP_BASEPATH);
            ftp.changeWorkingDirectory(FTP_BASEPATH);
            ftp.storeFile(originFileName, input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(ftp.isConnected()){
                try{
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }
}
