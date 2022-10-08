package com.xuhao.service.impl;

import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.xml.crypto.Data;

public class test2{
    public static void main(String[] args){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String datePath = sdf.format(new Date());
        System.out.println(datePath);
//        //uuid作为文件名
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        //后缀和文件后缀一致
//        int index = fileName.lastIndexOf(".");
//        // test.jpg -> .jpg
//        String fileType = fileName.substring(index);
//        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }

}
