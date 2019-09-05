package com.zh.java.io;

import java.io.*;

/**
 * @Author zhanghe
 * @Desc: 文件输入字节流
 * @Date 2019/3/18 16:06
 */
public class FileInputStream_demo {

    public static void main(String[] args) {
        fileInputSteamTest();
    }

    /**
     * FileInputStream 文件输入字节流
     * 可阅读大部分文件。主要用户图像、视频的读取，要阅读字符串，请考虑使用FileReader
     */
    public static void fileInputSteamTest() {
        File file = new File("D:\\app\\mytest.txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] bytes = new byte[10];  //定义字节数组，每次读取一个数据的字节量
            int data = 0; //记录字节数
            int count = 0;  //记录读取次数
            String s = "";  //临时记录读信息
            //data= -1 表示已经都完
            while ((data = fis.read(bytes)) != -1) {
                //data读入缓冲区的总字节数
                String str = new String(bytes, 0, data);
                count++;
                System.out.println("第" + count + "次，读入的内容为：" + str);
                s += str;
            }
            System.out.println(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭输出流
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
