package com.example.table.util;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public class CellUtil {
    public static String returnCellValue(HSSFCell cell){
        String cellvalue = "";
        if (null != cell) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    return String.valueOf(cell.getNumericCellValue()).trim();
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    return String.valueOf(cell.getStringCellValue()).trim();
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    return String.valueOf(cell.getBooleanCellValue()).trim();
                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                    return String.valueOf(cell.getCellFormula()).trim();
                case HSSFCell.CELL_TYPE_BLANK: // 空值
                    return "";
                case HSSFCell.CELL_TYPE_ERROR: // 故障
                    return "";
                default:
                    return "";
            }
        } else {

        }
        return cellvalue;
    }

    //避免cell.setCellValue(checkOrderQmSave.getSellOrderNo())中参数为空就会报错
    public static void setCellValue(HSSFCell cell, Object object){
        if(object == null){
            cell.setCellValue("");
        }else{
            if (object instanceof String) {
                cell.setCellValue(String.valueOf(object));
            }else if(object instanceof Long){
                Long temp = (Long)object;
                String cellvalue = new DecimalFormat("#0.00").format(temp.doubleValue());
                cell.setCellValue(cellvalue);
            }else if(object instanceof Double){
                Double temp = (Double)object;
                String cellvalue = new DecimalFormat("#0.00").format(temp.doubleValue());
                cell.setCellValue(cellvalue);
            }else if(object instanceof Float){
                Float temp = (Float)object;
                String cellvalue = new DecimalFormat("#0.00").format(temp.doubleValue());
                cell.setCellValue(cellvalue);
            }else if(object instanceof Integer){
                Integer temp = (Integer)object;
                cell.setCellValue(temp.intValue());
            }else if(object instanceof BigDecimal){
                BigDecimal temp = (BigDecimal)object;
                String cellvalue = new DecimalFormat("#0.00").format(temp.doubleValue());
                cell.setCellValue(cellvalue);
            }else{
                cell.setCellValue("");
            }
        }
    }
    public static void setCellValue(HSSFCell cell, Object object, String model){
        if(object == null){
            cell.setCellValue("");
        }else{
            if (object instanceof String) {
                cell.setCellValue(String.valueOf(object));
            }else if(object instanceof Long){
                Long temp = (Long)object;
                String cellvalue = new DecimalFormat("#0.00").format(temp.doubleValue());
                cell.setCellValue(cellvalue);
            }else if(object instanceof Double){
                Double temp = (Double)object;
                String cellvalue = new DecimalFormat("#0.00").format(temp.doubleValue());
                cell.setCellValue(cellvalue);
            }else if(object instanceof Float){
                Float temp = (Float)object;
                String cellvalue = new DecimalFormat("#0.00").format(temp.doubleValue());
                cell.setCellValue(cellvalue);
            }else if(object instanceof Integer){
                Integer temp = (Integer)object;
                cell.setCellValue(temp.intValue());
            }else if(object instanceof BigDecimal){
                BigDecimal temp = (BigDecimal)object;
                String cellvalue = new DecimalFormat("#0.00").format(temp.doubleValue());
                cell.setCellValue(cellvalue);
            }else if(object instanceof java.sql.Date){
                cell.setCellValue(new SimpleDateFormat(model).format(object));
            }else if(object instanceof java.util.Date){
                cell.setCellValue(new SimpleDateFormat(model).format(object));
            }else{
                cell.setCellValue("");
            }
        }
    }
    public static void setCellValue(HSSFCell cell, String object){
        if(object == null){
            cell.setCellValue("");
        }else{
            cell.setCellValue(object);
        }
    }
    public static void setCellValue(HSSFCell cell, Long object){
        if(object == null){
            cell.setCellValue("");
        }else{
            cell.setCellValue(object.doubleValue());
        }
    }
    public static void setCellValue(HSSFCell cell, Double object){
        if(object == null){
            cell.setCellValue("");
        }else{
            cell.setCellValue(object.doubleValue());
        }
    }
    public static void setCellValue(HSSFCell cell, double object){

        cell.setCellValue(object);

    }
    public static void setCellValue(HSSFCell cell, Date object, String model){
        if(object == null){
            cell.setCellValue("");
        }else{
            cell.setCellValue(new SimpleDateFormat(model).format(object));
        }
    }
    public static void setCellValue(HSSFCell cell, java.util.Date object, String model){
        if(object == null){
            cell.setCellValue("");
        }else{
            cell.setCellValue(new SimpleDateFormat(model).format(object));
        }
    }
    public static void setCellValue(HSSFCell cell, BigDecimal object){
        if(object == null){
            cell.setCellValue("");
        }else{
            cell.setCellValue(object.toString());
        }
    }

    //判断EXCEL表格高度用 row.setHeight((short) CellUtil.getExcelCellAutoHeight(TAR_VAL_ALL_STRING, 280, 30));
    public static float getExcelCellAutoHeight(String str,float defaultRowHeight, int fontCountInline) {
        int defaultCount = 0;
        for (int i = 0; i < str.length(); i++) {
            int ff = getregex(str.substring(i, i + 1));
            defaultCount = defaultCount + ff;
        }
        if (defaultCount > fontCountInline){
            return ((int) (defaultCount / fontCountInline) + 1) * defaultRowHeight;//计算
        } else {
            return defaultRowHeight;
        }
    }
    public static int getregex(String charStr) {
        if("".equals(charStr) || charStr == null){
            return 1;
        }
        // 判断是否为字母或字符
        if (Pattern.compile("^[A-Za-z0-9]+$").matcher(charStr).matches()) {
            return 1;
        }
        // 判断是否为全角
        if (Pattern.compile("[\u4e00-\u9fa5]+$").matcher(charStr).matches()) {
            return 2;
        }
        //全角符号 及中文
        if (Pattern.compile("[^x00-xff]").matcher(charStr).matches()) {
            return 2;
        }
        return 1;
    }
    //表头样式
    public static HSSFCellStyle getHearderStyle(HSSFWorkbook workbook){
        // 列名样式
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);// 字体大小
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        style.setLocked(true);
        return style;
    }
    //设置列宽和行高
    public static void setWidthAndHeight(HSSFSheet sheet,Integer[] colwidth){
        //设置列宽
        for (int i= 0; i<colwidth.length;i++){
            Integer width=colwidth[i]*346;
            if(width>25000){
                width=25000;
            }
            sheet.setColumnWidth(i,width);
        }
        //设置行高
        sheet.setDefaultRowHeight((short)360);
    }
    //普通单元格样式
    public static HSSFCellStyle getCellStyle(HSSFWorkbook workbook){
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style.setWrapText(true); // 换行
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        return style;
    }
    //输出Excel文件
    public static void responseExcel(HttpServletResponse response,HSSFWorkbook workbook,String fname) throws IOException {
       // String fileName = new String(fname.getBytes("gb2312"), "ISO8859-1");
        String fileName=java.net.URLEncoder.encode(fname, "UTF-8");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        response.setContentType("application/x-download;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        OutputStream os = response.getOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        byte[] b = new byte[1024];
        while ((bais.read(b)) > 0) {
            os.write(b);
        }
        bais.close();
        os.flush();
        os.close();
    }
    public static boolean checkFile(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            return false;
        }
        return true;
    }
    //Excel工作空间
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith("xlsx")){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
    public static String Nullvalue(Double t){
        if(t==null){
            return "";
        }else{
            return t.toString();
        }
    }
    public static String Nullvalue(Integer t){
        if(t==null){
            return "";
        }else{
            return t.toString();
        }
    }
    public static String Nullvalue(Object t){
        if(t==null){
            return "";
        }else {
            if (t instanceof BigDecimal) {
                if(((BigDecimal) t).doubleValue()==0.00){
                    return "";
                }
            }
            return t.toString();
        }
    }
    public static String Zvalue(Object t){
        if(t==null){
            return "";
        }else{
            if (t instanceof BigDecimal) {
                if(((BigDecimal) t).doubleValue()==0.00){
                    return "";
                }
            }
            return new DecimalFormat("#0.00").format(t);
        }
    }

    public static String getZval(Object t){
        if(t==null){
            return "";
        }else{
            return new DecimalFormat("#0.00").format(t);
        }
    }

    public static String getQval(Object t){
        if(t==null){
            return "";
        }else {
            return t.toString();
        }
      }
}
