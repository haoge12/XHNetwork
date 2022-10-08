package com.xuhao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuhao.entity.FileInfo;
import com.xuhao.utils.ResponseResult;


/**
 * (FileInfo)表服务接口
 *
 * @author makejava
 * @since 2022-09-18 11:06:10
 */
public interface FileInfoService extends IService<FileInfo> {

//    ResponseResult listRootFiles(Long id);

    ResponseResult listRootFiles1(String path);
}
