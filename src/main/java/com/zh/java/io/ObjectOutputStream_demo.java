package com.zh.java.io;

import java.io.*;

/**
 * @Author zhanghe
 * @Desc:  ObjectOutputStream将Java对象的原始数据类型和图形写入OutputStream。
 *         可以使用ObjectInputStream读取（重构）对象。 可以通过使用流的文件来实现对象的持久存储
 * @Date 2019/3/19 14:58
 */
public class ObjectOutputStream_demo {

    public static void main(String[] args) {
        objectOutputSteamTest();
    }

    public static void objectOutputSteamTest(){
        //1. 定义Java对象，继承序列化
        Student student = new Student("zhanghe",18);
        //2. 指定要创建的文件
        File file = new File("D:\\app\\student.txt");
        //3. 定义输出流
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            //4. 实例化输出流
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            //5. 写入对象
            oos.writeObject(student);
            oos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6. 关闭流
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}