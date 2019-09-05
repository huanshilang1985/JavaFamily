package com.zh.java.io;

import java.io.*;

/**
 * @Author zhanghe
 * @Desc: OutputStreamWriter是字符的桥梁流以字节流：向其写入的字符编码成使用指定的字节charset 。
 * @Date 2019/3/19 11:08
 */
public class OutputStreamWriter_demo {

    public static void main(String[] args) {
        String context = "\r\n又是一条新数据";    //待写入的信息
        File file = new File("D:\\app\\mytest.txt");   //待写入的文件
        //1. 新建流对象
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        try {
            //读取文件，并采用追加模式
            fos = new FileOutputStream(file, true);
            osw = new OutputStreamWriter(fos);
//            osw = new OutputStreamWriter(new FileOutputStream(new File("D:\\app\\mytest.txt"), true);  //整合写法
            osw.write(context);
            osw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(osw != null){
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
