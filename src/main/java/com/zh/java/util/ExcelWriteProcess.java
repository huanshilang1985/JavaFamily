package com.zh.java.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Author: zhanghe
 * Desc: Excel大数据量写入
 *       开发环境1.7，业务需求是用户上传模板，还有对应的一个XML文件，用于描述模板与Bean之间的关系
 * Date: Created in 2017/12/28 22:00
 */
public abstract class ExcelWriteProcess<T> {

    private Log logger = LogFactory.getLog(ExcelWriteProcess.class);

    private File excelFile;      //Excel文件
    private Integer count;       //导出总记录数

    final int maxRow = 5000;  //每次读取Excel记录数上限

    /**
     * 私有构造方法，屏蔽使用
     */
    private ExcelWriteProcess() {
    }

    /**
     * 公开的构造方法
     *
     * @param excelFile   Excel文件
     * @param count      总记录数
     */
    public ExcelWriteProcess(File excelFile, Integer count) {
        this.excelFile = excelFile;
        this.count = count;
    }

    public int getMaxRow() {
        return maxRow;
    }

    /**
     * 写入Excel重写方法
     * @return
     */
    public abstract List<T> callbackWrite(int startRow);

    /**
     * Excel写入方法
     * @return
     */
    public boolean writeExcel() {
        try {
            Workbook workbook = this.readExcelBook(excelFile);
            Sheet sheet = workbook.getSheetAt(0); //提取Sheet页

            int startRow = 0; //记录需要导入的起始行
            int headRow =0;   //记录需要导入的起始行
            Map<Integer, String> map = new HashMap<Integer, String>(); //记录写入单元格的行标记
            for (int i = 0; i < 10; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    int cellnum = 0;
                    while (true) {
                        Cell cell = row.getCell(cellnum);
                        if (cell != null) {
                            //发现导入标记，拼接get方法名
                            String cellValue = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(cellValue) && cellValue.contains("${") && cellValue.contains("}")) {
                                String tempValue = cellValue.substring(cellValue.indexOf(".") + 1, cellValue.lastIndexOf("}"));
                                String getMethod = "";
                                if(Character.isUpperCase(tempValue.charAt(1))){
                                    getMethod = "get" + tempValue;
                                } else {
                                    getMethod = "get" + tempValue.substring(0, 1).toUpperCase() + tempValue.substring(1);
                                }
                                map.put(cellnum, getMethod); //记录MAP，key：列号，value:get方法
                            }
                        } else if (cellnum > 5) {  //如果找到5列之后，单元格是空值，表示没有导入标记
                            break;
                        }
                        cellnum++;
                    }
                    //找到导入标记后，退出循环
                    if (MapUtils.isNotEmpty(map)) {
                        startRow = i;
                        headRow = i;
                        break;
                    }
                } else {
                    break;
                }
            }
            if(MapUtils.isEmpty(map)){
                logger.error("Excel模板未发现写入标识：" + excelFile.getName());
                return false;
            }

            //定义日期类型导出格式
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat format= workbook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("yyyy/mm/dd"));

            while (true) {
                //接收传入的数据
                List<T> list = this.callbackWrite(startRow - headRow);  //当前行-起始行=查询start
                if (MapUtils.isNotEmpty(map) && CollectionUtils.isNotEmpty(list) && list.size()>0) {
                    //遍历List，写入数据
                    for (T t : list) {
                        Class clazz = t.getClass();
                        Row row = sheet.createRow(startRow);  //打开行
                        Set<Integer> keySet = map.keySet();
                        for (Integer i : keySet) {
                            //根据方法返回值类型，向单元格写入内容。
                            Method method = clazz.getMethod(map.get(i));
                            Class returnType = method.getReturnType();
                            if ("java.lang.String".equals(returnType.getName())) {
                                String value = (String) method.invoke(t);
                                Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
                                cell.setCellValue(StringUtils.isNotBlank(value) ? value : "");
                            } else if ("java.lang.Integer".equals(returnType.getName())) {
                                Integer value = (Integer) method.invoke(t);
                                Cell cell = row.createCell(i, Cell.CELL_TYPE_NUMERIC);
                                if(value != null){
                                    cell.setCellValue(value);
                                } else {
                                    cell.setCellValue("");
                                }
                            } else if ("java.lang.Long".equals(returnType.getName())) {
                                Long value = (Long) method.invoke(t);
                                Cell cell = row.createCell(i, Cell.CELL_TYPE_NUMERIC);
                                if(value != null){
                                    cell.setCellValue(value);
                                } else {
                                    cell.setCellValue("");
                                }
                            } else if ("java.lang.Double".equals(returnType.getName())) {
                                Double value = (Double) method.invoke(t);
                                Cell cell = row.createCell(i, Cell.CELL_TYPE_NUMERIC);
                                if(value != null){
                                    cell.setCellValue(value);
                                } else {
                                    cell.setCellValue("");
                                }
                            } else if ("java.util.Date".equals(returnType.getName())) {
                                Date date = (Date) method.invoke(t);
                                Cell cell = row.createCell(i, Cell.CELL_TYPE_NUMERIC);
                                if(date != null){
                                    cell.setCellValue(date);
                                    cell.setCellStyle(cellStyle);
                                } else {
                                    cell.setCellValue("");
                                }
                            }
                        }
                        startRow++;
                    }
                    OutputStream stream = new FileOutputStream(excelFile.getPath());  //创建文件流
                    workbook.write(stream);   //写入数据
                    stream.close();  //关闭文件流
                } else {
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("ExcelWriteProcess 写入异常：", e);
        }
        return false;
    }



    public boolean writeExcelBig() throws IOException {

        try{
            if (excelFile == null || !excelFile.exists()) {
                logger.error("readExcel Excel文件不存在");
                return false;
            }
            XSSFWorkbook wb = null;
            if (excelFile.getName().endsWith(".xlsx")) {
                wb = new XSSFWorkbook(new FileInputStream(excelFile));
            }else{
                logger.error("readExcel Excel文件格式错误！");
                return false;
            }
            Sheet sheet = wb.getSheetAt(0); //提取Sheet页
            Map<Integer, String> map = new HashMap<Integer, String>(); //记录写入单元格的行标记
            int lastRowNum = sheet.getLastRowNum();
            Row lastRow = sheet.getRow(lastRowNum);
            if (lastRow != null) {
                int cellnum = 0;
                while (true) {
                    Cell cell = lastRow.getCell(cellnum);
                    if (cell != null) {
                        //发现导入标记，拼接get方法名
                        String cellValue = cell.getStringCellValue();
                        if (StringUtils.isNotBlank(cellValue) && cellValue.contains("${") && cellValue.contains("}")) {
                            String tempValue = cellValue.substring(cellValue.indexOf(".") + 1, cellValue.lastIndexOf("}"));
                            String getMethod = "";
                            if(Character.isUpperCase(tempValue.charAt(1))){
                                getMethod = "get" + tempValue;
                            } else {
                                getMethod = "get" + tempValue.substring(0, 1).toUpperCase() + tempValue.substring(1);
                            }
                            map.put(cellnum, getMethod); //记录MAP，key：列号，value:get方法
                        }
                    } else if (cellnum > 5) {  //如果找到5列之后，单元格是空值，表示没有导入标记
                        break;
                    }
                    cellnum++;
                }
            }
            sheet.removeRow(lastRow);
            if(MapUtils.isEmpty(map)){
                logger.error("Excel模板未发现写入标识：" + excelFile.getName());
                return false;
            }
            int startRow = lastRowNum; //记录需要导入的起始行
            int headRow =lastRowNum;   //记录需要导入的起始行
            SXSSFWorkbook workbook = new SXSSFWorkbook(wb, 5000);
            Sheet sh = workbook.getSheetAt(0);
            //定义日期类型导出格式
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat format= workbook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("yyyy/mm/dd"));

            while (true) {
                //接收传入的数据
                List<T> list = this.callbackWrite(startRow - headRow);  //当前行-起始行=查询start
                if (MapUtils.isNotEmpty(map) && CollectionUtils.isNotEmpty(list) && list.size()>0) {
                    //遍历List，写入数据
                    for (T t : list) {
                        Class clazz = t.getClass();
                        Row row = sh.createRow(startRow);  //打开行
                        Set<Integer> keySet = map.keySet();
                        for (Integer i : keySet) {
                            //根据方法返回值类型，向单元格写入内容。
                            Method method = clazz.getMethod(map.get(i));
                            Class returnType = method.getReturnType();
                            if ("java.lang.String".equals(returnType.getName())) {
                                String value = (String) method.invoke(t);
                                Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
                                cell.setCellValue(StringUtils.isNotBlank(value) ? value : "");
                            } else if ("java.lang.Integer".equals(returnType.getName())) {
                                Integer value = (Integer) method.invoke(t);
                                Cell cell = row.createCell(i, Cell.CELL_TYPE_NUMERIC);
                                if(value != null){
                                    cell.setCellValue(value);
                                } else {
                                    cell.setCellValue("");
                                }
                            } else if ("java.lang.Long".equals(returnType.getName())) {
                                Long value = (Long) method.invoke(t);
                                Cell cell = row.createCell(i, Cell.CELL_TYPE_NUMERIC);
                                if(value != null){
                                    cell.setCellValue(value);
                                } else {
                                    cell.setCellValue("");
                                }
                            } else if ("java.lang.Double".equals(returnType.getName())) {
                                Double value = (Double) method.invoke(t);
                                Cell cell = row.createCell(i, Cell.CELL_TYPE_NUMERIC);
                                if(value != null){
                                    cell.setCellValue(value);
                                } else {
                                    cell.setCellValue("");
                                }
                            } else if ("java.util.Date".equals(returnType.getName())) {
                                Date date = (Date) method.invoke(t);
                                Cell cell = row.createCell(i, Cell.CELL_TYPE_NUMERIC);
                                if(date != null){
                                    cell.setCellValue(date);
                                    cell.setCellStyle(cellStyle);
                                } else {
                                    cell.setCellValue("");
                                }
                            }
                        }
                        startRow++;
                    }

                } else {
                    break;
                }
            }
            OutputStream stream = new FileOutputStream(excelFile.getPath());  //创建文件流
            workbook.write(stream);   //写入数据
            stream.close();  //关闭文件流
            return true;
        }catch (Exception e){
            logger.info("writeExcelBigError",e);

        }
        return false;
    }

    /**
     * 读取Excel文件
     *
     * @param excelFile Excel文件
     * @return Workbook
     */
    private Workbook readExcelBook(File excelFile) throws Exception {
        try {
            if (excelFile == null || !excelFile.exists()) {
                logger.error("readExcel Excel文件不存在");
                return null;
            }
            Workbook workbook = null;
            if (excelFile.getName().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(new FileInputStream(excelFile));
            } else if (excelFile.getName().endsWith(".xls")) {
                workbook = new HSSFWorkbook(new FileInputStream(excelFile));
            } else {
                logger.error("readExcel Excel文件格式错误！");
            }
            return workbook;
        } catch (IOException e) {
            logger.error("读取Excel文件异常：" + excelFile.getName(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        String a = "aBbc";
        String b = "bbad";

        System.out.println(Character.isUpperCase(a.charAt(1)));

//        String a = "${list.skuName}";
//        System.out.println(a.indexOf("."));
//        System.out.println(a.lastIndexOf("}"));
//        String b = a.substring(a.indexOf(".") + 1, a.lastIndexOf("}"));
//        String setMethod = "get" + b.substring(0, 1).toUpperCase() + b.substring(1);
//        System.out.println(setMethod);
    }

}
