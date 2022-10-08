package com.xuhao.service.impl;


import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xuhao.entity.Chunk;
import com.xuhao.entity.FileInfo;
import com.xuhao.enums.AppHttpCodeEnum;
import com.xuhao.mapper.FileInfoMapper;
import com.xuhao.service.UploadService;
import com.xuhao.utils.PathUtils;
import com.xuhao.utils.ResponseResult;
import com.xuhao.utils.SecurityUtils;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
//import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import lombok.Data;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService{
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String filePathTemp;
    private String filePath;
    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public ResponseResult uploadFile(HttpServletRequest request, Chunk chunk) throws IOException{
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
            MultipartFile file = chunk.getFile();
            if(file == null){
                throw new ServiceException("参数验证失败！");
            }
            Integer chunkNumber = chunk.getChunkNumber();
            if(chunkNumber == null){
                chunkNumber = 0;
            }
//            System.out.println(filePathTemp + File.separator + chunk.getIdentifier());
            File outFile = new File(filePathTemp + File.separator + chunk.getIdentifier(),  chunkNumber + ".part");
            InputStream inputStream = file.getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, outFile);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult mergeFile(String fileName, String guid, Long parentId) throws IOException{
       // 源地址文件夹
        File file1 = new File(filePathTemp + File.separator + guid);

        System.out.println("filePath123" + filePathTemp + File.separator + "temp" + File.separator +  fileName);
        if (file1.isDirectory()) {
            // 找出该文件夹下的所有文件
            File[] files = file1.listFiles();
            if (files != null && files.length > 0) {
                File partFile = new File(filePath + File.separator + fileName);
                // 拼成一个文件
                for (int i = 1; i <= files.length; i++) {
                    File s = new File(filePathTemp + File.separator + guid, i + ".part");
                    FileOutputStream destTempfos = new FileOutputStream(partFile, true);
                    FileUtils.copyFile(s, destTempfos);
                    destTempfos.close();
                }
                FileUtils.deleteDirectory(file1);
            // TODO 将文件转换为MultipartFile来上传到七牛云
//            FileInputStream inputStream = new FileInputStream(partFile);
//            MultipartFile file = new MockMultipartFile(partFile.getName(), partFile.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            // TODO 测试大容量文件上传，注释掉上传到七牛云上的代码，下面还有文件夹删除的一并也给注释了
//            String filePath = PathUtils.generateFilePath(Objects.requireNonNull(file.getOriginalFilename()));
//            String url = null;
            String url = filePathTemp + File.separator + "temp" + File.separator +  fileName;
            // TODO 测试大容量文件上传，注释掉上传到七牛云上的代码，下面还有文件夹删除的一并也给注释了
//            String url = uploadOss(file, filePath);
//            if("操作失败".equals(url)){
//                return ResponseResult.errorResult(404, "操作失败");
//            }

            // 获取登录用户的用户id
            Long userId = SecurityUtils.getUserId();
            LambdaQueryWrapper<FileInfo> queryWrapper = new LambdaQueryWrapper<>();
            // 查询为同一个目录下的文件
            queryWrapper.eq(FileInfo::getParentId, parentId);
            // 查询为同一个人的文件
            queryWrapper.eq(FileInfo::getOwnerId, userId);
            List<FileInfo> fileInfos = fileInfoMapper.selectList(queryWrapper);




            String[] split = fileName.split("\\.");
            StringBuilder prefixFileName = new StringBuilder(split[0]);
            for (int i = 1; i < split.length - 1; i++) {
                prefixFileName.append(".").append(split[i]);
            }

//            String prefixFileName = split[0];
            String suffixFileName = "." + split[split.length - 1];

//            String s = file.getOriginalFilename().split("\\.")[0];
            // 找名字一样的，计算个数
            String finalPrefixFileName = prefixFileName.toString();
            System.out.println("finalPrefixFileName123" + finalPrefixFileName);
            // TODO (未完成) (当文件名中有两个及以上.时不能检测出文件重名问题)
//            List<Boolean> collect = fileInfos.stream()
//                .map(fileInfo -> fileInfo.getFileName().equals(finalPrefixFileName))
//                .collect(Collectors.toList());
                List<Boolean> collect = fileInfos.stream()
                        .map(fileInfo -> fileInfo.getFileName().equals(fileName))
                        .collect(Collectors.toList());
//            System.out.println(fileNameList);
//        System.out.println(collect);
            // 处理文件名冲突的情况
            Bag bag = new HashBag(collect);
            int count = bag.getCount(true);
            FileInfo fileInfo1 = new FileInfo();


            if(count >= 1){
                SimpleDateFormat sdf = new SimpleDateFormat("_yyyyMMdd_HHmmss");
                String datePath = sdf.format(new Date());
//                System.out.println(datePath);
//                fileInfo1.setFileName(file.getOriginalFilename() + "(" + String.valueOf(count) + ")");
                fileInfo1.setFileName(prefixFileName + datePath + suffixFileName);
            }else{
//                fileInfo1.setFileName(file.getOriginalFilename());
                fileInfo1.setFileName(fileName);
            }
            fileInfo1.setOwnerId(userId);
            fileInfo1.setParentId(parentId);
            fileInfo1.setUrl(url);
            fileInfoMapper.insert(fileInfo1);
            // TODO 删除存储在本地的文件
//            partFile.delete();
            }
        }
        return ResponseResult.okResult();
    }


//    @Override
//    public ResponseResult downloadFile(String url, HttpServletResponse response){
//        try {
//// path是指想要下载的文件的路径
//            File file = new File(url);
////            log.info(file.getPath());
//// 获取文件名
//            String filename = file.getName();
//// 获取文件后缀名
//            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
////            log.info("文件后缀名：" + ext);
//
//// 将文件写入输入流
//            FileInputStream fileInputStream = new FileInputStream(file);
//            InputStream fis = new BufferedInputStream(fileInputStream);
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
////            fis.close();
//
//// 清空response
//            response.reset();
//// 设置response的Header
//            response.setCharacterEncoding("UTF-8");
////Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
////attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
//// filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
//            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
//// 告知浏览器文件的大小
//            response.addHeader("Content-Length", "" + file.length());
//            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
////            return
////            outputStream.write(buffer);
////            outputStream.flush();
//            return ResponseResult.okResult(fis);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return ResponseResult.errorResult(AppHttpCodeEnum.DOWNLOAD_FAIL);
////        return ResponseResult.okResult();
//    }

    private String uploadOss(MultipartFile imgFile, String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;

        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://ri4ninb6q.bkt.clouddn.com/" + filePath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "操作失败";

    }
}

