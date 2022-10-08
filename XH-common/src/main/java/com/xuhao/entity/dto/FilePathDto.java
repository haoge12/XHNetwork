package com.xuhao.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FilePathDto{
    private String fileName;
    //文件外链的路径
    private String url;
    //文件是否有上级目录，无则为-1，有则为上级目录的id
    private Long parentId;
    //文件拥有者的id
    private Long ownerId;
}
