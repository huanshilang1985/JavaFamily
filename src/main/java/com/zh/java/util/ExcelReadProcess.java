package com.zh.java.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: zhanghe
 * Desc: Excel大数据量分批读取
 *       开发环境1.7，业务需求是用户上传模板，还有对应的一个XML文件，用于描述模板与Bean之间的关系
 * Date: Created in 2017/12/27 20:09
 */
public abstract class ExcelReadProcess<T> {

    private Log logger = LogFactory.getLog(ExcelReadProcess.class);

    final int maxRow = 5000;  //每次读取Excel记录数上限

    private File excelFile;   //Excel文件
    private File templeFile;  //配置文件

    /**
     * 私有构造方法，屏蔽使用
     */
    private ExcelReadProcess() {
    }

    /**
     * 公开的构造方法
     *
     * @param excelFile  Excel文件
     * @param templeFile 配置文件
     */
    public ExcelReadProcess(File excelFile, File templeFile) {
        this.excelFile = excelFile;
        this.templeFile = templeFile;
    }

    /**
     * 抽象回调方法，提供重写
     *
     * @param list
     */
    public abstract boolean callbackRead(List<T> list);

    /**
     * 读取Excel方法
     */
    public void readExcel() {
        int startRow = 2;   //开始行（说明、标题占2行）
        try {
            Workbook workbook = readExcelBook(excelFile);
            Map<Integer, Method> map = readTemplateFile(templeFile);
            Class clazz = getTemplateClass(templeFile);

            if (workbook == null || MapUtils.isEmpty(map) || clazz == null) {
                logger.error("readExcel have file is null");
                return;
            }

            //分批提取Excel数据
            while (true) {
                Sheet sheet = workbook.getSheetAt(0); //提取Sheet页
                List<T> list = new ArrayList<T>();
                int count = 0; //记录每次提交的总行数
                while (true) {
                    Object obj = clazz.newInstance();
                    Row row = sheet.getRow(startRow);
                    if (row == null) {
                        break; //如果行为空，直接返回
                    }

                    // 判断是否存在数据
                    int nullIndex = 0;
                    for (int i = 0; i < map.size(); i++) {
                        String value = this.getCellValue(row.getCell(i)); //单元格的值
                        try {
                            if (StringUtils.isBlank(value)) {
                                nullIndex++;  //依据所有提取的单元格全为空，表示空行
                                continue;
                            }

                            Method method = map.get(i);    //获取set方法
                            if (method != null) {
                                Class[] params = method.getParameterTypes();
                                String paramType = params[0].getName();   //获取set方法的参数类型
                                if ("java.lang.String".equals(paramType)) {  //根据数据类型转换
                                    method.invoke(obj, (String) value);
                                } else if ("java.lang.Integer".equals(paramType)) {
                                    method.invoke(obj, Integer.valueOf(value));
                                } else if ("java.lang.Long".equals(paramType)) {
                                    method.invoke(obj, Long.valueOf(value));
                                } else if ("java.lang.Double".equals(paramType)) {
                                    method.invoke(obj, Double.valueOf(value));
                                } else if ("java.util.Date".equals(paramType)) {
                                    method.invoke(obj, DateUtil.stringToDate(value, "yyyy-MM-dd hh:mm:ss"));
                                } else {
                                    logger.error("readExcel 未定义的参数类型" + paramType);
                                }
                            }
                        } catch (Exception e) {
                            logger.error("读取单元格出错，行数：" + startRow + ", 单元格：" + i + "，值：" + value, e);
                        }
                    }
                    if(nullIndex == map.size()){
                        break; //发现空行，退出循环
                    }
                    list.add((T) obj); //转换为泛型对象
                    startRow++;  //行号自增
                    count++;    //总数自增
                    if (count >= maxRow) { //当提取的总数达到上限，跳出内层循环
                        break;
                    }
                }

                if (CollectionUtils.isNotEmpty(list)) {
                    boolean result = callbackRead(list);    //执行回调方法
                    if(!result){ //回调方法执行失败，跳出循环
                        break;
                    }
                } else {
                    break; //list为空时，跳出外层循环
                }
            }
        } catch (Exception e) {
            logger.error("出现异常：文件名：" + excelFile.getName() + ", 行数：" + startRow + "", e);
        }
    }

    /**
     * 读取Excel文件
     *
     * @param excelFile Excel文件
     * @return Workbook
     */
    private Workbook readExcelBook(File excelFile)  throws Exception {
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

    /**
     * 读取模板文件
     *
     * @param file 模板文件
     * @return Map<Integer, Method>
     */
    private Map<Integer, Method> readTemplateFile(File file)  throws Exception {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(file);
            Element foo = doc.getRootElement();
            Element loop = foo.getChild("worksheet").getChild("loop");
            String entityPath = loop.getAttribute("varType").getValue();

            Class clazz = Class.forName(entityPath);  //Java对象
            Method[] methods = clazz.getMethods();   //获得所有方法
            Map<String, Method> mdMap = this.getMethodMap(methods); //把所有方法转换成Map

            Element section = loop.getChild("section");
            List<Element> mapppings = section.getChildren("mapping");
            Map<Integer, Method> map = new HashMap<Integer, Method>();
            if (CollectionUtils.isNotEmpty(mapppings)) {
                for (Element el : mapppings) {
                    String att = el.getValue();
                    String attname = att.substring(att.lastIndexOf(".") + 1, att.length());
                    String setMethod = "set" + attname.substring(0, 1).toUpperCase() + attname.substring(1);
                    map.put(el.getAttribute("col").getIntValue(), mdMap.get(setMethod)); //
                }
            } else {
                logger.error("readExcel Excel转换模板异常");
            }
            return map;
        } catch (Exception e) {
            logger.error("readExcel Excel转换模板读取异常：" + file.getName(), e);
        }
        return null;
    }

    /**
     * 获得模板文件中的Class
     *
     * @param configFile 模板文件
     * @return Class
     */
    private Class getTemplateClass(File configFile)  throws Exception {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(configFile);
            Element foo = doc.getRootElement();
            Element loop = foo.getChild("worksheet").getChild("loop");
            String entityPath = loop.getAttribute("varType").getValue();

            return Class.forName(entityPath);  //Java对象
        } catch (Exception e) {
            logger.error("getTemplateClass error : ", e);
        }
        return null;
    }

    /**
     * 获取类方法键值对
     *
     * @param ms
     * @return
     */
    private Map<String, Method> getMethodMap(Method[] ms)  throws Exception {
        Map<String, Method> map = new HashMap<String, Method>();
        for (Method m : ms) {
            map.put(m.getName(), m);
        }
        return map;
    }

    private String getCellValue(Cell cell) throws Exception {
        boolean time = false; //判断是否是时间类型
        String value = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                //如果为时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                    time = true;
                    break;
                } else { //数字类型处理：小数为0取证，小数不为0保留2位小数
                    Double aDouble = cell.getNumericCellValue();
                    if (aDouble.intValue() - aDouble == 0) {//判断是否符合取整条件
                        value = String.valueOf(aDouble.intValue());
                    } else {
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setGroupingUsed(false);         //禁止千分位
                        nf.setMaximumFractionDigits(2);    //保留两位小数
//                        nf.setRoundingMode(RoundingMode.UP);  // 如果不需要四舍五入，可以使用RoundingMode.DOWN
                        value = String.valueOf(nf.format(aDouble));
                    }
                    break;
                }
            case HSSFCell.CELL_TYPE_STRING: // 字符串
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                value = cell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                cell.setCellType(Cell.CELL_TYPE_STRING);
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BLANK: // 空值
                value = "";
                break;
            case HSSFCell.CELL_TYPE_ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return time ? value : value.replaceAll(" ","");  //非时间类型的值要去掉空格
    }

}
