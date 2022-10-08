package com.xuhao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (FileInfo)表实体类
 *
 * @author makejava
 * @since 2022-09-18 11:06:10
 */
@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("xh_fileinfo")
public class FileInfo{
    @TableId
    private Long id;
    //文件的名字
    private String fileName;
    //文件外链的路径
    private String url;
    //文件是否有上级目录，无则为-1，有则为上级目录的id
    private Long parentId;
    //0为未删除，1为已删除
    private Integer delFlag;
    //文件拥有者的id
    private Long ownerId;
    
    private Date createTime;


}

