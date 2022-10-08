package com.xuhao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuhao.constants.SystemConstants;
import com.xuhao.entity.FileInfo;
import com.xuhao.entity.vo.FileInfoVo;
import com.xuhao.mapper.FileInfoMapper;
import com.xuhao.service.FileInfoService;
import com.xuhao.utils.BeanCopyUtils;
import com.xuhao.utils.ResponseResult;
import com.xuhao.utils.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (FileInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-09-18 11:06:10
 */
@Service("filepathService")
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService{
    @Autowired
    private FileInfoMapper fileInfoMapper;
//    @Override
//    public ResponseResult listRootFiles(Long id){
//        Long userId = SecurityUtils.getUserId();
//        LambdaQueryWrapper<FileInfo> queryWrapper = new LambdaQueryWrapper<>();
//        // 属于当前用户的文件信息
//        queryWrapper.eq(FileInfo::getOwnerId, userId);
//        queryWrapper.eq(FileInfo::getParentId, id);
//        List<FileInfo> list = list(queryWrapper);
//        List<FileInfoVo> fileInfoVos = BeanCopyUtils.copyBeanList(list, FileInfoVo.class);
//        return ResponseResult.okResult(fileInfoVos);
//    }

    @Override
    public ResponseResult listRootFiles1(String path){
        System.out.println(path);
        if(!StringUtils.hasText(path)){
            Long userId = SecurityUtils.getUserId();
            LambdaQueryWrapper<FileInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FileInfo::getOwnerId, userId);
            List<FileInfo> lists = list(queryWrapper);
            List<FileInfo> collect = lists.stream().filter(list -> list.getParentId() == 0L).collect(Collectors.toList());
//            List<FileInfo> fileInfos = fileInfoMapper.selectFileByParentId(0L);
            List<FileInfoVo> fileInfoVos = BeanCopyUtils.copyBeanList(collect, FileInfoVo.class);
            return ResponseResult.okResult(fileInfoVos);
        }
//        System.out.println("split:" + Arrays.toString(path.split("/")));
        String[] split = path.split("/");
//        System.out.println("split1:" + Long.parseLong(split[split.length - 1]));
//        System.out.println("length:" + split.length);
//
//        System.out.println(split);
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<FileInfo> queryWrapper = new LambdaQueryWrapper<>();
        // 属于当前用户的文件信息
        // split:[, 1, 4]
        // ownerId = 1
        queryWrapper.eq(FileInfo::getOwnerId, userId);
        queryWrapper.eq(FileInfo::getFileName,  split[split.length - 1]);
        List<FileInfo> lists = list(queryWrapper);
        // 每个文件的parentId是唯一的
        List<FileInfo> fileInfos = fileInfoMapper.selectFileByParentId(lists.get(0).getId(), userId, SystemConstants.NOT_DELETED);
        List<FileInfoVo> fileInfoVos = BeanCopyUtils.copyBeanList(fileInfos, FileInfoVo.class);
        System.out.println(fileInfoVos);
        return ResponseResult.okResult(fileInfoVos);
    }

}
