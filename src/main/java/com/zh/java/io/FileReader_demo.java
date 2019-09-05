package com.zh.java.io;

import java.io.*;

/**
 * @Author zhanghe
 * @Desc: 文件读取字符流
 * @Date 2019/3/18 16:35
 */
public class FileReader_demo {

    public static void main(String[] args) {
        fileReaderTest();
    }

    /**
     * 文件读取字符流
     */
    public static void fileReaderTest() {
        //1. 新建File对象
        File file = new File("D:\\app\\mytest.txt");
        //2. 新建FileReader
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            int data = 0; //缓冲字符个数
            //3. 定义字符数组接收字符
            char[] chars = new char[10];
            String s = "";
            //4. 循环读取，data=-1表示已经读完
            while ((data = reader.read(chars)) != -1) {
                String str = new String(chars, 0, data);
                s += str;
            }
            //5. 输出读取的内容
            System.out.println("读取的文件：" + s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6. 关闭字符流
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
