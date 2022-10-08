package com.xuhao.entity.vo;

import com.xuhao.entity.FileInfo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FileInfoVo{
    private Long id;
    //文件的名字
    private String fileName;
    //文件外链的路径
    private String url;
    //文件是否有上级目录，无则为-1，有则为上级目录的id
    private Long parentId;
    //文件拥有者的id
    private Long ownerId;

    private Date createTime;
    private List<FileInfoVo> children;
}
