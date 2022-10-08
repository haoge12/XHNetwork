package com.xuhao.Controller;

import com.xuhao.service.FileInfoService;
import com.xuhao.utils.ResponseResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;


@RestController
@RequestMapping("/list")
public class ListController{

    @Autowired
    private FileInfoService fileInfoService;
//    @GetMapping("/listAll/{id}")
//    public ResponseResult listRootFiles(@PathVariable("id") Long id){
//        return fileInfoService.listRootFiles(id);
//    }
//    @GetMapping("/listAll")
//    public ResponseResult listRootFiles(Long id){
//        return fileInfoService.listRootFiles(id);
//    }
    @GetMapping("/listAll")
    public ResponseResult listRootFiles1(@PathParam("path") String path){
        return fileInfoService.listRootFiles1(path);
    }
//    @GetMapping("/listAll")
//    public ResponseResult listChildrenFiles(Model model){
//        model.addAttribute("all");
//        return fileInfoService.listChildrenFiles();
//    }

}
