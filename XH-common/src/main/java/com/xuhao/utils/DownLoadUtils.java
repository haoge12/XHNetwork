package com.xuhao.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class DownLoadUtils {
 
    public static void download(String filename, HttpServletResponse res) throws IOException{
        // 发送给客户端的数据
        // 读取filename
        ClassPathResource classPathResource = new ClassPathResource("templates/"+filename);
        long length = classPathResource.getFile().length();
        res.addHeader("Content-Length",String.valueOf(length));
        OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        InputStream inputStream =classPathResource.getInputStream();
        bis = new BufferedInputStream(inputStream);
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
        bis.close();
        outputStream.close();
    }
}