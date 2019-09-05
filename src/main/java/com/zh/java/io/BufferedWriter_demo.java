package com.zh.java.io;

import java.io.*;

/**
 * @Author zhanghe
 * @Desc: 字符缓冲流
 * @Date 2019/3/19 9:29
 */
public class BufferedWriter_demo {

    public static void main(String[] args) {
        bufferedWriterTest();
    }

    /**
     * 字节写入缓冲流
     * 使用BufferedReader、BufferedWriter实现文件复制
     */
    public static void bufferedWriterTest(){
        long start = System.currentTimeMillis();
        //1. 创建原文件、目标文件对象（）
        File srcFile = new File("D:\\app\\mytest.txt");
        File tarFile = new File("D:\\app\\mytest2.txt");
        //2. 初始化bufferedReader、bufferedWriter
        FileReader reader = null; //或使用 InputStreamReader，定义编码集
        FileWriter writer = null; //或使用 OutputStreamWriter，定义编码集
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            //3. 指定复制的源文件和目标文件（目前文件不存在会自动创建）
            reader = new FileReader(srcFile);
            writer = new FileWriter(tarFile);
            //4. 实例化BufferedReader、BufferedWriter
            br = new BufferedReader(reader);
            bw = new BufferedWriter(writer);
            //5. br读一行，bw写一行
            String temp = "";
            while ((temp = br.readLine()) != null) {
                bw.write(temp); //把读取的内容写到目标文件
                bw.newLine(); //写一个换行符
            }
            long end = System.currentTimeMillis();
            System.out.println("复制文件共消耗" + (end- start) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //5. 关闭缓冲流
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
