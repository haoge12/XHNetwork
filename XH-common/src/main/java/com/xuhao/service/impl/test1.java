package com.xuhao.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class test1{
    public static void main(String[] args) throws IOException{
        List<String> filePathList = cutFile("C:\\Users\\haogebabing\\Desktop\\121.flv", 1024 * 1024 * 20);
        String s = mergeFile(filePathList, "C:\\Users\\haogebabing\\Desktop\\1\\");
        System.out.println(s);
//        List<String> tempFileName = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            final File tempFile = File.createTempFile("java", ".flv");//创建临时文件
//            System.out.println(tempFile);
//            String[] split = tempFile.getCanonicalPath().split("\\\\");
//            tempFileName.add(split[split.length - 1]);
//        }



//        for (int i = 0; i < tempFileName.size(); i++) {
//            File file = new File("C:\\Users\\HAOGEB~1\\AppData\\Local\\Temp\\" + tempFileName.get(i));
//            file.delete();
//        }
//        System.out.println(split[split.length - 1]);
//        System.out.println("临时文件所在的本地路径：" + tempFile.getCanonicalPath().split("\\")[]);
//
    }
    /**
     *
     * @Description:   合并文件
     * @param:         @param splitSmallFileList 小文件路径
     * @param:         @param mergeFileDir 合并的文件存储的文件夹
     * @param:         @param mergeFileName 合并的文件新名
     * @return:        String 合并的文件路径
     * @throws
     */
    private static String mergeFile(List<String> splitSmallFileList, String mergeFileDir){
        try {
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(mergeFileDir).append(splitSmallFileList.get(0));
            String newMergeFileName=stringBuilder.toString();
            //输出流，写文件,true表示追加写而不覆盖
            OutputStream outputStream=new BufferedOutputStream(new FileOutputStream(newMergeFileName,true));
            //输入流，读文件
            Vector<InputStream> vector=new Vector<InputStream>();
            for (int i = 1; i < splitSmallFileList.size(); i++) {
                vector.add(new BufferedInputStream(new FileInputStream(splitSmallFileList.get(i))));
            }
            //SequenceInputStream，实现批量输入流的按序列读
            SequenceInputStream sequenceInputStream=new SequenceInputStream(vector.elements());
            //10字节的缓存
            byte[] cache=new byte[1024*10];
            int len=-1;
            while ((len=sequenceInputStream.read(cache))!=-1) {
                //分段写
                outputStream.write(cache,0,len);
            }
            //强制将所有缓冲的输出字节被写入磁盘，更可靠
            outputStream.flush();
            outputStream.close();
            sequenceInputStream.close();
            // 删除临时存储的文件
            for (int i = 1; i < splitSmallFileList.size(); i++) {
                File file = new File(splitSmallFileList.get(i));
                file.delete();
            }
            //返回新合成的文件
            return newMergeFileName;

        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件未找到",e);
        } catch (IOException e) {
            throw new RuntimeException("文件传输异常",e);
        }


    }

    /**
     *
     * @param src 源文件的路径
     * @param num 每个文件的大小
     * @return 分割后文件的绝对路径（第一个为文件名）
     */
    private  static List<String> cutFile(String src, int num) {
        FileInputStream fis = null;
        List<String> tempFilePath = new ArrayList<>();
//        File file = null;
        try {
            // 以字节流进行存储
            fis = new FileInputStream(src);
            File file = new File(src);
            //创建规定大小的byte数组
            byte[] bytes = new byte[num];
            int len = 0;
            //分别找到原大文件的文件名和文件类型
            String name = file.getName();
            tempFilePath.add(name);
            String[] split = name.split("\\.");
            // 得到文件的后缀名
            String fileType = split[split.length - 1];
            //遍历将大文件读入byte数组中，当byte数组读满后写入对应的小文件中
            while ((len = fis.read(bytes)) != -1) {
                // 创建本地临时文件
                File tempFile = File.createTempFile("java", "." + fileType);
                // 得到文件的路径名
                String[] temp_split = tempFile.getCanonicalPath().split("\\\\");
                String[] split1 = temp_split[temp_split.length - 1].split("\\.");
                tempFilePath.add(tempFile.getCanonicalPath());
//                String substring = name2.substring(0, lastIndexOf);
//                String substring2 = name2.substring(lastIndexOf, name2.length());
                FileOutputStream fos = new FileOutputStream(tempFile.getCanonicalPath());
//                FileOutputStream fos = new FileOutputStream(
//                        erc + "\\"+ split1[split1.length - 2]
//                        + "." + fileType);
                //将byte数组写入对应的小文件中
                fos.write(bytes, 0, len);
                //结束资源
                fos.close();
//                name++;
            }

//            System.out.println(tempFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    //结束资源
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(tempFilePath);
        return tempFilePath;
    }

}

