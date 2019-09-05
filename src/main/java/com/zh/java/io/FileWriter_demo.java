package com.zh.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author zhanghe
 * @Desc: 文件写入字符流
 * @Date 2019/3/19 9:27
 */
public class FileWriter_demo {

    public static void main(String[] args) {
        fileWriterTest();
    }

    /**
     * 文件写入字符流
     */
    public static void fileWriterTest() {
        //1. 新建File对象
        File file = new File("D:\\app\\mytest.txt");
        String context = "\r\n我是谁！";
        //2. 新建FileReader
        FileWriter writer = null;
        try {
            //3. 实例化对象--采用追加模式
            writer = new FileWriter(file, true);//第二个参数true ，则字节将被写入文件的末尾而不是开头
            writer.write(context);  // 写入内容
            writer.flush();   // 刷新输入流
            System.out.println("写入完成，请查看文件内容：" + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭写入流
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
