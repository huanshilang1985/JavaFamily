package com.zh.java.io;

import java.io.File;

/**
 * @Author zhanghe
 * @Desc:
 * @Date 2019/8/22 9:52
 */
public class FileDemo {

    public static void main(String[] args) {
        File file = new File("E:\\架构师课程\\06.性能调优（六）");
//        File file = new File("D:\\BaiduNetdiskDownload\\04.spring微服务专题（四）\\41-SpringBoot2.X快速构建和配置-荆轲\\MySpringBoot10\\mvnw【瑞客论坛 www.ruike1.com】.cmd");
//        String fileName = file.getName();
//        if (fileName.contains("【瑞客论坛 www.ruike1.com】")) {
//            String path = file.getPath();
//            String newName = path.replaceAll("【瑞客论坛 www.ruike1.com】", "");
////            String suffix = newName.substring(fileName.lastIndexOf("."), fileName.length());
//            file.renameTo(new File(newName));
//            System.out.println("");
//        }

        dealFile(file);
    }

    public static void dealFile(File file) {
        if (file != null) {
            String fileName = file.getName();
            if (fileName.contains("【瑞客论坛 www.ruike1.com】")) {
                String path = file.getPath();
                String newName = path.replaceAll("【瑞客论坛 www.ruike1.com】", "");
                file.renameTo(new File(newName));
            }
            File[] files1 = file.listFiles();
            if (files1 != null && files1.length > 0) {
                for (File temp : files1) {
                    dealFile(temp);
                }
            }
        }
    }

}