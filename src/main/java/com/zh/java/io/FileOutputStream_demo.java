package com.zh.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author zhanghe
 * @Desc: 文件输出字节流
 * @Date 2019/3/19 9:28
 */
public class FileOutputStream_demo {

    public static void main(String[] args) {
        fileOutputSteamTest();
    }

    /**
     * FileOutputStream 文件输出流是用于将数据写入到输出流File
     * FileOutputStream用于写入诸如图像数据的原始字节流。 对于写入字符流，请考虑使用FileWriter 。
     * \n是换行，英文是New line。\r是回车，英文是Carriage return
     */
    public static void fileOutputSteamTest() {
        //1. 创建File对象
        File file = new File("D:\\app\\mytest.txt");
        String context = "\r\n新年快乐";  //要写入的文件
        //2. 建立输出流
        FileOutputStream fos = null;
        try {
            //3. 实例化对象--采用追加模式
            fos = new FileOutputStream(file, true);  //第二个参数true ，则字节将被写入文件的末尾而不是开头
            //4. 写入内容转成字节
            byte[] bytes = context.getBytes();
            //5. 直接写入
            fos.write(bytes);
            //6. 保证数据能够全部输出
            fos.flush();  //刷新此输出流并强制任何缓冲的输出字节被写出。
            System.out.println("写入完成，请查看文件内容：" + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //7. 关闭输出流
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
