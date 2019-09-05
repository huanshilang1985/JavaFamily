package com.zh.java.io;

import java.io.*;

/**
 * @Author zhanghe
 * @Desc:
 * @Date 2019/3/19 15:24
 */
public class ObjectInputStream_demo {

    public static void main(String[] args) {
        objectInputStreamTest();
    }

    public static void objectInputStreamTest(){
        //1. 初始化ObjectInputStream对象
        ObjectInputStream ois = null;
        FileInputStream fis = null;

        try {
            //2. 实例化ObjectInputStream对象，读取文件
            fis = new FileInputStream(new File("D:\\app\\student.txt"));
            ois = new ObjectInputStream(fis);
            //3. 读取文件内容
            Student student = (Student) ois.readObject();
            System.out.println(student.getName());
            System.out.println(student.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //4. 关闭流
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
