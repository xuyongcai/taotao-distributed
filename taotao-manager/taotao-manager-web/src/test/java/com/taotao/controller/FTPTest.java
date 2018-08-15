package com.taotao.controller;

import com.taotao.common.utils.FTPUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class FTPTest {

    @Test
    public void testFtpClient() throws Exception {
        //创建一个FtpClient对象
        FTPClient ftpClient = new FTPClient();
        //创建ftp连接。默认是21端口
        ftpClient.connect("localhost", 21);
        //登录ftp服务器，使用用户名和密码
        ftpClient.login("xiaochai", "xiaochai");
        //上传文件。
        //读取本地文件
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\passion\\Pictures\\Saved Pictures\\0130120812.jpg"));
        //设置上传的路径
        ftpClient.changeWorkingDirectory("D:\\ftpserver\\ftpfile");
        //修改上传文件的格式
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //第一个参数：服务器端文档名
        //第二个参数：上传文档的inputStream
        ftpClient.storeFile("hello1.jpg", inputStream);
        //关闭连接
        ftpClient.logout();

    }

    @Test
    public void testFtpUtil() throws Exception {
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\passion\\Pictures\\Saved Pictures\\0130120812.jpg"));
        FTPUtil.uploadFile("locaohost", 21, "xiaochai", "xiaochai", "D:\\ftpserver\\ftpfile", "/2015/09/04", "hello.jpg", inputStream);

    }

}
