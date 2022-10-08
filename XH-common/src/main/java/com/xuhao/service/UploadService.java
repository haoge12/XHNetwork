package com.xuhao.service;

import com.xuhao.entity.Chunk;
import com.xuhao.utils.ResponseResult;

import org.apache.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UploadService{
//    ResponseResult upload(MultipartFile file, Long parentId);
//
//    ResponseResult merge(String filename, String guid) throws FileNotFoundException;

    ResponseResult uploadFile(HttpServletRequest request, Chunk chunk) throws IOException, ServletException;

    ResponseResult mergeFile(String fileName, String guid, Long parentId) throws IOException;

//    ResponseResult downloadFile(String url, HttpServletResponse response) throws UnsupportedEncodingException;

}
