package com.zh.java.io;

import java.io.File;
import java.io.IOException;

/**
 * @Author zhanghe
 * @Desc:
 * @Date 2019/3/18 15:49
 */
public class File_demo {

    public static void main(String[] args) {
//        String path = "D:\\app\\Logs";
//        listAllFiles(path);
        createFile();
    }

    /**
     * 创建文件
     */
    public static void createFile(){
        //1. 指定路径在新建文件对象
        File file = new File("D:\\app\\mytest.txt");
        try {
            if(!file.exists()){
                file.createNewFile();
                System.out.println("文件创建完成，"+ file.getAbsolutePath());
            } else {
                System.out.println("这个文件已存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归实现打印某个文件夹下左右文件
     * 递归：就是方法本身调用方法自己
     *
     * @param path 文件路径
     */
    public static void listAllFiles(String path) {
        //1. 创建File对象
        File file = new File(path);
        //2. 判断file是一个文件夹 isDirectory
        if (file.isDirectory()) {
            //3. 获取file下的所有文件和文件夹
            File[] files = file.listFiles();
            //4. 遍历file的下级files
            for (File f : files) {
                if (f.isFile()) {
                    //5. 如果f是一个文件，输出文件的绝对路径
                    System.out.println(f.getAbsolutePath());
                } else {
                    //6. 如果是文件夹，递归掉用自己，层层输出
                    listAllFiles(f.getAbsolutePath());
                }
            }
        } else {
            //file是一个文件，就直接输出文件的绝对路径
            System.out.println(file.getAbsolutePath());
        }
    }

}
