package com.xuhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuhao.entity.FileInfo;
import com.xuhao.entity.dto.UserDtoRegister;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import javax.websocket.server.PathParam;


/**
 * (FileInfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-18 11:06:10
 */
public interface FileInfoMapper extends BaseMapper<FileInfo> {
    List<FileInfo> selectFileByParentId(@Param("parentId") Long parentId, @Param("ownerId") Long ownerId,
                                        @Param("delFlag") Integer delFlag);

}

