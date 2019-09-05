package com.zh.java.io;

import java.io.*;

/**
 * @Author zhanghe
 * @Desc: 字符缓冲流
 * @Date 2019/3/18 17:13
 */
public class BufferedReader_demo {

    public static void main(String[] args) {
        bufferReaderTest();
    }

    /**
     * BufferedReader 字符缓冲流
     * 从字符输入流读取文本，缓冲字符，以提供字符，数组和行的高效读取。
     * 也可以使用FileReader，InputSteamReader作为构造参数，也是定义编码集，可以实现字节流到字符流的缓冲
     */
    public static void bufferReaderTest() {
        //1. 新建reader--filereader、bufferedreader
        FileReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            //2. 用FileReader读取文件
            reader = new FileReader(new File("D:\\app\\mytest.txt"));
            //3. 用BufferedReader缓冲
            bufferedReader = new BufferedReader(reader);
            StringBuffer sBuffer = new StringBuffer();
            //step 2 读取数据
            String temp = "";
            int count = 0;
            while ((temp = bufferedReader.readLine()) != null) {
                sBuffer.append(temp);
                count++;
                System.out.println("第" + count + "行：" + temp);
            }
            System.out.println("总文件：" + sBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //从内到外
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
