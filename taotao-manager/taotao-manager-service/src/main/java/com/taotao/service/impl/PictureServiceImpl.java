package com.taotao.service.impl;

import com.taotao.common.utils.FTPUtil;
import com.taotao.common.utils.IdUtil;
import com.taotao.service.PictureService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PictureServiceImpl implements PictureService {

    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    @Override
    public Map uploadPicture(MultipartFile uploadFile) {
        Map resultMap = new HashMap();

        //生成一个新的文件名
        //1.取原始文件名
        String oldName = uploadFile.getOriginalFilename();
        //2.用UUID.randomUUID()生成新文件名
        String newName = IdUtil.genImageName();
        newName = newName + oldName.substring(oldName.lastIndexOf("."));

        //图片上传
        String imagePath = new DateTime().toString("/yyyy/MM/dd");
        boolean result = false;
        try {
            result = FTPUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD,
                    FTP_BASE_PATH, imagePath, newName, uploadFile.getInputStream());
        } catch (IOException e) {
            resultMap.put("error", 1);
            resultMap.put("message", "文件上传发生异常");
            return resultMap;
        }

        //返回结果
        if(!result){
            resultMap.put("error", 1);
            resultMap.put("message", "文件上传失败");
            return resultMap;
        }
        resultMap.put("error", 0);
        resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
        return resultMap;
    }

}
