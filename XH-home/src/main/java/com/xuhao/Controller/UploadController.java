package com.xuhao.Controller;

import com.xuhao.entity.Chunk;
import com.xuhao.service.UploadService;
import com.xuhao.utils.DownLoadUtils;
import com.xuhao.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@RestController
public class UploadController{
    @Autowired
    private UploadService uploadService;

    @ResponseBody
    @PostMapping("/upload")
    public ResponseResult upload(HttpServletRequest request, Chunk chunk) throws IOException, ServletException{
        return uploadService.uploadFile(request, chunk);
    }
    /**
     * 合并所有分片
     */
    @GetMapping("/merge")
    public ResponseResult mergeFile(@PathParam("parentId") Long parentId, @PathParam("fileName") String fileName, @PathParam("guid") String guid) throws Exception{
        return uploadService.mergeFile(fileName, guid, parentId);
    }
//    @GetMapping("/download")
//    public ResponseResult downloadFile(@PathParam("url")String url, HttpServletResponse response) throws UnsupportedEncodingException{
//        return uploadService.downloadFile(url, response);
//    }
//    @GetMapping("/download")
//    public void downloadFile(String filePath, HttpServletResponse response) throws IOException{
//        String fileName = "template.xlsx";
//        // 设置信息给客户端不解析
//        String type = new MimetypesFileTypeMap().getContentType(fileName);
//        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
//        response.setHeader("Content-type",type);
//        // 设置编码
//        String code = new String(fileName.getBytes("utf-8"), "iso-8859-1");
//        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
//        response.setHeader("Content-Disposition", "attachment;filename=" + code);
//        response.setContentType("application/octet-stream;charset=ISO8859-1");
//        response.addHeader("Pargam", "no-cache");
//        response.addHeader("Cache-Control", "no-cache");
//        DownLoadUtils.download(fileName, response);
//    }

}
