package com.zh.java.io;

import java.io.*;

/**
 * @Author zhanghe
 * @Desc: 字符输入流
 * stream 结尾的IO类表示字节流
 * Reader 接口的IO类表示字符流
 * InputStreamReader是从字节流到字符流的桥：它读取字节，并使用指定的charset将其解码为字符 。
 * 构造函数可以指定编码格式，FileReader不行。
 * @Date 2019/3/19 9:33
 */
public class InputStreamReader_demo {

    public static void main(String[] args) {
        inputSteamReaderTest();
    }


    public static void inputSteamReaderTest() {
        //1. 新建File对象
        File file = new File("D:\\app\\mytest.txt");
        //2. 新建InputSteamReader对象
        InputStreamReader reader = null;
        try {
            //3. 开始读取数据
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            int data = 0;
            //4. read方法一次只读一个字符
            while ((data = reader.read()) != -1) {
                System.out.println((char) data);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //5. 关闭读取流
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
