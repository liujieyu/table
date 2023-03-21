package com.example.table.controller;

import com.example.table.entity.*;
import com.example.table.service.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

import com.example.table.util.CellUtil;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    StAlarminfoService stAlarminfoService;
    @Autowired
    SmsBoxsendedService smsBoxsendedService;
    @Autowired
    StGateDdService stGateDdService;
    @Autowired
    WrpFieldinfoService wrpFieldinfoService;
    @Autowired
    StStratingMService stStratingMService;
    @Autowired
    StRealMefileService stRealMefileService;
    @Autowired
    StNeedwaterPlanService stNeedwaterPlanService;
    @Autowired
    StStsectionStationService stStsectionStationService;
    //导出泄洪信息
    @ResponseBody
    @RequestMapping(value="/exportgate",method = RequestMethod.GET)
    public void exportgate(HttpServletResponse response, AlarmParam alarmParam) {
        List<StGateDd> datalist=stGateDdService.selectStGateDdByPage(alarmParam);
        String sheetName = "";
        if(alarmParam.getSign()==1){
            sheetName="先调导流洞方案泄流曲线表";
        }else{
            sheetName="先调溢洪洞方案泄流曲线表";
        }
        String[] head0 = new String[] { "序号","库水位(m)", "导流洞", "导流洞", "溢洪洞", "溢洪洞", "联合泄流量(m³/s)" };
        String[] head1 = new String[] { "闸门开启高度(m)", "泄流量(m³/s)", "闸门开启高度(m)", "泄流量(m³/s)" };
        //对应excel中的行和列，下表从0开始{"开始行,结束行,开始列,结束列"}
        String[] headnum0 = new String[] { "1,2,0,0", "1,2,1,1","1,1,2,3", "1,1,4,5", "1,2,6,6"};
        Integer[] colwidth = new Integer[] { head0[0].getBytes().length, head0[1].getBytes().length,head1[0].getBytes().length, head1[1].getBytes().length, head1[2].getBytes().length, head1[3].getBytes().length, head0[6].getBytes().length};
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        // 表头标题样式
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 22);// 字体大小
        HSSFCellStyle headstyle = workbook.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        headstyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        headstyle.setLocked(true);
        // 列名样式
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);// 字体大小
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setLocked(true);
        // 普通单元格样式（中文）
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 12);
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFont(font2);
        style2.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style2.setWrapText(true); // 换行
        style2.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        style2.setBorderBottom(BorderStyle.THIN); //下边框
        style2.setBorderLeft(BorderStyle.THIN);//左边框
        style2.setBorderTop(BorderStyle.THIN);//上边框
        style2.setBorderRight(BorderStyle.THIN);//右边框
        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length-1));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        String title="康苏水库闸门调度方案泄流曲线表（计算值）";
        CellUtil.setCellValue(cell, title);
        //表头列名
        row = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        //设置合并单元格的参数 因为下标从0开始，所以这里表示的是excel中的第二行
        row = sheet.createRow(2);
        for (int i = 0; i < head0.length; i++) {
            if(i==0||i==1||i==6){
                cell = row.createCell(i);
                cell.setCellValue(head0[i]);
                cell.setCellStyle(style);
            }
        }
        for (int j = 0; j < head1.length; j++) {
            cell = row.createCell(j + 2);
            cell.setCellValue(head1[j]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        //库水位、闸门开启高度合并单元格
        Map<String,Object> map1=new HashMap<>();
        map1.put("col",1);map1.put("start",3);map1.put("end",3);
        Map<String,Object> map2=new HashMap<>();
        map2.put("col",2);map2.put("start",3);map2.put("end",3);
        Map<String,Object> map3=new HashMap<>();
        map3.put("col",4);map3.put("start",3);map3.put("end",3);
        // 设置列值-内容
        for (int i = 0; i < datalist.size(); i++) {
            row = sheet.createRow(i + 3);
            StGateDd obj=datalist.get(i);
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            CellUtil.setCellValue(cell, obj.getRowId());
            cell=row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            CellUtil.setCellValue(cell, obj.getZ());
            cell=row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            CellUtil.setCellValue(cell, obj.getDlkdname());
            cell=row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            CellUtil.setCellValue(cell, obj.getDlQ());
            cell=row.createCell(4);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            CellUtil.setCellValue(cell, obj.getXhkdname());
            cell=row.createCell(5);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            CellUtil.setCellValue(cell, obj.getXhQ());
            cell=row.createCell(6);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            CellUtil.setCellValue(cell, obj.getSumQ());
            if(i>0){
                StGateDd bobj=datalist.get(i-1);
                if((double)bobj.getZ()==(double)obj.getZ()){
                    map1.put("end",i+3);
                    if(i==datalist.size()-1){
                        sheet.addMergedRegion(new CellRangeAddress((int)map1.get("start"), (int)map1.get("end"), (int)map1.get("col"), (int)map1.get("col")));
                    }
                }else{
                    if((int)map1.get("start")<(int)map1.get("end")){
                        sheet.addMergedRegion(new CellRangeAddress((int)map1.get("start"), (int)map1.get("end"), (int)map1.get("col"), (int)map1.get("col")));
                    }
                    map1.put("start",i+3);map1.put("end",i+3);
                }
                if(bobj.getDlkdname().equals(obj.getDlkdname())){
                    map2.put("end",i+3);
                    if(i==datalist.size()-1){
                        sheet.addMergedRegion(new CellRangeAddress((int)map2.get("start"), (int)map2.get("end"), (int)map2.get("col"), (int)map2.get("col")));
                    }
                }else{
                    if((int)map2.get("start")<(int)map2.get("end")){
                        sheet.addMergedRegion(new CellRangeAddress((int)map2.get("start"), (int)map2.get("end"), (int)map2.get("col"), (int)map2.get("col")));
                    }
                    map2.put("start",i+3);map2.put("end",i+3);
                }
                if(bobj.getXhkdname().equals(obj.getXhkdname())){
                    map3.put("end",i+3);
                    if(i==datalist.size()-1){
                        sheet.addMergedRegion(new CellRangeAddress((int)map3.get("start"), (int)map3.get("end"), (int)map3.get("col"), (int)map3.get("col")));
                    }
                }else{
                    if((int)map3.get("start")<(int)map3.get("end")){
                        sheet.addMergedRegion(new CellRangeAddress((int)map3.get("start"), (int)map3.get("end"), (int)map3.get("col"), (int)map3.get("col")));
                    }
                    map3.put("start",i+3);map3.put("end",i+3);
                }
            }
        }
        //设置列宽
        for (int i= 0; i<colwidth.length;i++){
            sheet.setColumnWidth(i,colwidth[i]*256+1000);
        }
        //设置行高
        sheet.setDefaultRowHeight((short)360);
        try {
            String fileName = java.net.URLEncoder.encode("闸门调度泄流信息", "UTF-8");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            response.setContentType("application/x-download;charset=utf-8");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + fileName + ".xls");
            OutputStream os = response.getOutputStream();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            byte[] b = new byte[1024];
            while ((bais.read(b)) > 0) {
                os.write(b);
            }
            bais.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出站点预警
    @ResponseBody
    @RequestMapping(value="/exportsite",method = RequestMethod.GET)
    public void exportsite(HttpServletResponse response, AlarmParam alarmParam){
        if(alarmParam.getOrderBy()!=null){
            if(alarmParam.getOrderBy().equals("STNM")){
                alarmParam.setOrderBy("b.STNM");
            }
            if(alarmParam.getOrderBy().equals("TYPENAME")){
                alarmParam.setOrderBy("(select stuff((select ',' + type from ST_STBPRP_F f where f.STCD =b.STCD for xml path('')),1,1,''))");
            }
            if(alarmParam.getOrderBy().equals("TMSTR")){
                alarmParam.setOrderBy("a.TM");
            }
            if(alarmParam.getOrderBy().equals("ALARMNAME")){
                alarmParam.setOrderBy("a.Alarm");
            }
            if(alarmParam.getOrderBy().equals("CONTENTS")){
                alarmParam.setOrderBy("a.CONTENTS");
            }
            if(alarmParam.getOrderBy().equals("MV")){
                alarmParam.setOrderBy("a.MV");
            }
            if(alarmParam.getOrderBy().equals("AlARMV")){
                alarmParam.setOrderBy("a.AlARMV");
            }
            if(alarmParam.getOrderBy().equals("ADNM")){
                alarmParam.setOrderBy("b.ADDVCD");
            }
        }
        List<StAlarminfo> list=stAlarminfoService.selectWarmInfoByPage(alarmParam);
        String[] head0 = new String[] { "站名", "监测类型", "预警时间", "预警等级","预警内容", "测量值", "预警指标","站址"};
        Integer[] colwidth = new Integer[8];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length;
        }
        String sheetName="站点预警";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            StAlarminfo obj=list.get(i);
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String stnm=obj.getStnm();
            CellUtil.setCellValue(cell, stnm);
            if(stnm.getBytes().length>colwidth[0]){
                colwidth[0]=stnm.getBytes().length;
            }
            cell=row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval=obj.getTypename();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[1]){
                colwidth[1]=celval.getBytes().length;
            }
            cell=row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getTmstr();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[2]){
                colwidth[2]=celval.getBytes().length;
            }
            cell=row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getAlarmname();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[3]){
                colwidth[3]=celval.getBytes().length;
            }
            cell=row.createCell(4);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getContents();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[4]){
                colwidth[4]=celval.getBytes().length;
            }
            cell=row.createCell(5);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getMv().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[5]){
                colwidth[5]=celval.getBytes().length;
            }
            cell=row.createCell(6);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getAlarmV().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[6]){
                colwidth[6]=celval.getBytes().length;
            }
            cell=row.createCell(7);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getAdnm();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[7]){
                colwidth[7]=celval.getBytes().length;
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"站点预警");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出预警短信信息
    @ResponseBody
    @RequestMapping(value="/exportsened",method = RequestMethod.GET)
    public void exportboxsened(HttpServletResponse response, AlarmParam alarmParam){
        List<SmsBoxsended> list=smsBoxsendedService.selectSmsBoxsendedByPage(alarmParam);
        String[] head0 = new String[] { "预警时间", "预警等级", "预警内容","对象姓名"};
        Integer[] colwidth = new Integer[4];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length;
        }
        String sheetName="预警短信";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            SmsBoxsended obj=list.get(i);
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval=obj.getSendtime();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[0]){
                colwidth[0]=celval.getBytes().length;
            }
            cell=row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getAppid();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[1]){
                colwidth[1]=celval.getBytes().length;
            }
            cell=row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getContent();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[2]){
                colwidth[2]=celval.getBytes().length;
            }
            cell=row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getAddtion1();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[3]){
                colwidth[3]=celval.getBytes().length;
            }
        }

        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"预警短信");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出发送短信信息
    @ResponseBody
    @RequestMapping(value="/exportsending",method = RequestMethod.GET)
    public void exportboxsending(HttpServletResponse response, AlarmParam alarmParam){
        List<SmsBoxsended> list;
        List<Map<String,Object>> tables=wrpFieldinfoService.selectSysFormInfoByFCodeAndFNum("FSJG20190927",alarmParam.getSign());
        if(tables.size()>1){
            String subsql="";
            for(int i=0;i<tables.size();i++){
                Map<String,Object> map=tables.get(i);
                String table=map.get("value").toString();
                String result=map.get("label").toString();
                subsql+="select id,appid,'"+result+"' as result,'"+table+"' as tablename,receiver,sendtime,content,addtion1,addtion2,addtion3 from "+table;
                if(i<tables.size()-1){
                    subsql+=" union all ";
                }
            }
            alarmParam.setSubsql(subsql);
            list=smsBoxsendedService.selectSmsBoxSendAllByPage(alarmParam);
        }else{
            Map<String,Object> map=tables.get(0);
            alarmParam.setTable(map.get("value").toString());
            alarmParam.setResult(map.get("label").toString());
            list=smsBoxsendedService.selectSmsBoxSendTableByPage(alarmParam);
        }
        String[] head0 = new String[] {"短信类型","发送结果","发送时间","接收号码","发送内容","对象姓名","单位","职务"};
        Integer[] colwidth = new Integer[8];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length;
        }
        String sheetName="发送短信";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            SmsBoxsended obj=list.get(i);
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval=obj.getAppid();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[0]){
                colwidth[0]=celval.getBytes().length;
            }
            cell=row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getResult();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[1]){
                colwidth[1]=celval.getBytes().length;
            }
            cell=row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getSendtime();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[2]){
                colwidth[2]=celval.getBytes().length;
            }
            cell=row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getReceiver();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[3]){
                colwidth[3]=celval.getBytes().length;
            }
            cell=row.createCell(4);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getContent();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[4]){
                colwidth[4]=celval.getBytes().length;
            }
            cell=row.createCell(5);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getAddtion1();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[5]){
                colwidth[5]=celval.getBytes().length;
            }
            cell=row.createCell(6);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getAddtion2();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[6]){
                colwidth[6]=celval.getBytes().length;
            }
            cell=row.createCell(7);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getAddtion3();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[7]){
                colwidth[7]=celval.getBytes().length;
            }

        }

        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"发送短信");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出接收短信信息
    @ResponseBody
    @RequestMapping(value="/exportreceived",method = RequestMethod.GET)
    public void exportboxreceived(HttpServletResponse response, AlarmParam alarmParam){
        List<SmsBoxreceived> list=smsBoxsendedService.selectSmsBoxreceivedByPage(alarmParam);
        String[] head0 = new String[] { "接收时间", "发送号码", "短信内容"};
        Integer[] colwidth = new Integer[3];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length;
        }
        String sheetName="接收短信";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            SmsBoxreceived obj=list.get(i);
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval=obj.getInserttime();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[0]){
                colwidth[0]=celval.getBytes().length;
            }
            cell=row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getSender();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[1]){
                colwidth[1]=celval.getBytes().length;
            }
            cell=row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getContent();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[2]){
                colwidth[2]=celval.getBytes().length;
            }
        }

        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"接收短信");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出值班安排信息
    @ResponseBody
    @RequestMapping(value="/exportdutyplan/{date}",method = RequestMethod.GET)
    public void exportdutyplan(HttpServletResponse response,@PathVariable("date") String date){
        Map<String,Object> map=smsBoxsendedService.selectDutyPlanByDateAndCols(date);
        List<Map<String,Object>> list=(List<Map<String, Object>>) map.get("rows");
        List<Map<String,Object>> cols= (List<Map<String, Object>>) map.get("cols");
        String[] head0 = new String[cols.size()];
        for(int i=0;i<cols.size();i++){
            Map<String,Object> maph=cols.get(i);
            head0[i]=(String)maph.get("label");
        }
        Integer[] colwidth = new Integer[cols.size()];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length;
        }
        String sheetName="值班安排";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            for(int h=0;h<cols.size();h++){
                Map<String,Object> mapd=cols.get(h);
                String var=(String)mapd.get("prop");
                cell=row.createCell(h);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=(String)obj.get(var);
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[h]){
                    colwidth[h]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"值班安排");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //预警指标导出
    @ResponseBody
    @RequestMapping(value="/exporttarget/{sign}",method = RequestMethod.GET)
    public void exporttarget(HttpServletResponse response,@PathVariable("sign") int sign){
        //sign 1 降雨指标 2 河道指标  3 水库指标
        String[] head0;
        String[] cols;
        Integer[] colwidth;
        String sheetName;
        List<Map<String,Object>> list=new ArrayList<>();
        HSSFWorkbook workbook = new HSSFWorkbook();
        if(sign==1){
            head0=new String[]{"站名","I级(3H降雨)","II级(6H降雨)","III级(12H降雨)","IV级(24H降雨)"};
            cols=new String[]{"STNM","TH_P","SH_P","TWH_P","OD_P"};
            sheetName="降雨预警指标";
            Map<String,Object> map1=new HashMap<>();
            map1.put("STNM","预警雨量站");map1.put("TH_P","24.1");map1.put("SH_P","24.1");map1.put("TWH_P","24.1");map1.put("OD_P","24.1");
            list.add(map1);
            Map<String,Object> map2=new HashMap<>();
            map2.put("STNM","坝上雨量站");map2.put("TH_P","24.1");map2.put("SH_P","24.1");map2.put("TWH_P","24.1");map2.put("OD_P","24.1");
            list.add(map2);
            Map<String,Object> map3=new HashMap<>();
            map3.put("STNM","入库雨量站");map3.put("TH_P","24.1");map3.put("SH_P","24.1");map3.put("TWH_P","24.1");map3.put("OD_P","24.1");
            list.add(map3);
        }else if(sign==2){
            head0=new String[]{"站名","I级(校核洪水位)","II级(保证水位)","III级(警戒水位)"};
            cols=new String[]{"STNM","XHWL","BZWL","AWL"};
            sheetName="河道预警指标";
            Map<String,Object> map1=new HashMap<>();
            map1.put("STNM","入库水位站");map1.put("XHWL","2713.50");map1.put("BZWL","2713.00");map1.put("AWL","2712.500");
            list.add(map1);
        }else{
            head0=new String[]{"站名","I级(校核洪水位)","II级(正常蓄水位)","III级(汛限水位)"};
            cols=new String[]{"STNM","XHWL","ZCWL","FWL"};
            sheetName="水库预警指标";
            Map<String,Object> map1=new HashMap<>();
            map1.put("STNM","坝前水位站");map1.put("XHWL","2524.120");map1.put("ZCWL","2520.200");map1.put("FWL","2518.200");
            list.add(map1);
        }
        colwidth=new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length;
        }
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            for(int h=0;h<cols.length;h++){
                cell=row.createCell(h);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=(String)obj.get(cols[h]);
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[h]){
                    colwidth[h]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //通讯录导出
    @ResponseBody
    @RequestMapping(value="/exportaddr",method = RequestMethod.GET)
    public void exportaddr(HttpServletResponse response){
        String[] head0=new String[]{"姓名","部门","职务","办公电话","手机号码","年份"};
        String[] cols=new String[]{"NM","UNIT","POSITION","OPHONE","MOBILE","YR"};
        Integer[] colwidth=new Integer[cols.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length;
        }
        String sheetName="通讯录";
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> map1=new HashMap<>();
        map1.put("NM","张晓");map1.put("UNIT","乌恰县水利局");map1.put("POSITION","党组书记、副局长");map1.put("OPHONE","");map1.put("MOBILE","18997696458");map1.put("YR","2019");
        list.add(map1);
        Map<String,Object> map2=new HashMap<>();
        map2.put("NM","马国成");map2.put("UNIT","乌恰县水利局");map2.put("POSITION","副局长");map2.put("OPHONE","");map2.put("MOBILE","15700991168");map2.put("YR","2019");
        list.add(map2);
        Map<String,Object> map3=new HashMap<>();
        map3.put("NM","沈飞");map3.put("UNIT","康苏水库工程管理处");map3.put("POSITION","法人代表");map3.put("OPHONE","");map3.put("MOBILE","18709083988");map3.put("YR","2019");
        list.add(map3);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            for(int h=0;h<cols.length;h++){
                cell=row.createCell(h);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=(String)obj.get(cols[h]);
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[h]){
                    colwidth[h]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出率定结果信息
    @ResponseBody
    @RequestMapping(value="/exportldresult",method = RequestMethod.GET)
    public void exportldresult(HttpServletResponse response, LvDingParam param){
        String title="年份："+param.getYear()+"年  站点名称："+param.getStnm()+"  测量类型："+param.getMetypename()+"  所属渠道："+param.getCanalname()+"  最大水深："+param.getMaxz()+"m";
        List<StZqrlB> list= stStratingMService.selectSteqrlBList(param);
        String[] head0 = new String[] { "水深", "流量"};
        Integer[] colwidth = new Integer[2];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"率定".getBytes().length;
        }
        String sheetName="率定结果";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFCellStyle styleh = workbook.createCellStyle();
        styleh.setFont(font);
        styleh.setWrapText(true); // 换行
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        HSSFRow rowh = sheet.createRow(0);
        rowh.setHeight((short) 0x349);
        HSSFCell cellh = rowh.createCell(0);
        cellh.setCellStyle(styleh);
        CellUtil.setCellValue(cellh, title);
        HSSFRow row = sheet.createRow(1);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        DecimalFormat df1 = new DecimalFormat("######0.00");
        DecimalFormat df2 = new DecimalFormat("######0.000");
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            StZqrlB obj=list.get(i);
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval=df1.format(obj.getZ());
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[0]){
                colwidth[0]=celval.getBytes().length;
            }
            cell=row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=df2.format(obj.getQ());
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[1]){
                colwidth[1]=celval.getBytes().length;
            }
        }

        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"率定结果");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出文件信息
    @ResponseBody
    @RequestMapping(value="/exportmefile",method = RequestMethod.GET)
    public void exportmefile(HttpServletResponse response, MedataParam medataParam){
        if(medataParam.getOrderBy()!=null){
            if(medataParam.getOrderBy().equals("TMSTR")){
                medataParam.setOrderBy("TM");
            }
            if(medataParam.getOrderBy().equals("ME_TMSTR")){
                medataParam.setOrderBy("ME_TM");
            }
            if(medataParam.getOrderBy().equals("RESULT")){
                medataParam.setOrderBy("AUDINTING");
            }
        }
        List<StRealMefile> list=stRealMefileService.selectStRealMefileByPage(medataParam);
        String[] head0 = new String[] { "上传日期", "上传人", "文件名称","测量日期","测量人","审核状态","审核人"};
        Integer[] colwidth = new Integer[7];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"率定".getBytes().length;
        }
        String sheetName="率定数据管理";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            StRealMefile obj=list.get(i);
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval=obj.getTmstr();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[0]){
                colwidth[0]=celval.getBytes().length;
            }
            cell=row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getUpMan();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[1]){
                colwidth[1]=celval.getBytes().length;
            }
            cell=row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getFileName();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[2]){
                colwidth[2]=celval.getBytes().length;
            }
            cell=row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getMeTmstr();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[3]){
                colwidth[3]=celval.getBytes().length;
            }
            cell=row.createCell(4);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getMeMan();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[4]){
                colwidth[4]=celval.getBytes().length;
            }
            cell=row.createCell(5);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getResult();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[5]){
                colwidth[5]=celval.getBytes().length;
            }
            cell=row.createCell(6);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getAudiMan()==null?"":obj.getAudiMan();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[6]){
                colwidth[6]=celval.getBytes().length;
            }
        }

        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出需水计划
    @ResponseBody
    @RequestMapping(value="/exportwaterplan",method = RequestMethod.GET)
    public void exportwaterplan(HttpServletResponse response,LvDingParam param){
        List<StNeedwaterPlan> list=stNeedwaterPlanService.selectWaterPlan(param);
        String[] head0 = new String[] { "管理机构", "支渠名称", "一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月","年度总计"};
        Integer[] colwidth = new Integer[15];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"率定".getBytes().length;
        }
        String sheetName="需水计划";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        int rowcount=0;
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            if(i==0){
                rowcount=i+1;
            }else{
                rowcount+=1;
            }
            row = sheet.createRow(rowcount);
            StNeedwaterPlan obj=list.get(i);
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval=obj.getOrganName();
            if(celval==null){
                celval="";
            }
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[0]){
                colwidth[0]=celval.getBytes().length;
            }
            cell=row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getCanalName();
            if(celval==null){
                celval="";
            }
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[1]){
                colwidth[1]=celval.getBytes().length;
            }
            cell=row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getOne().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[2]){
                colwidth[2]=celval.getBytes().length;
            }
            cell=row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getTwo().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[3]){
                colwidth[3]=celval.getBytes().length;
            }
            cell=row.createCell(4);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getThree().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[4]){
                colwidth[4]=celval.getBytes().length;
            }
            cell=row.createCell(5);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getFour().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[5]){
                colwidth[5]=celval.getBytes().length;
            }
            cell=row.createCell(6);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getFive().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[6]){
                colwidth[6]=celval.getBytes().length;
            }
            cell=row.createCell(7);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getSix().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[7]){
                colwidth[7]=celval.getBytes().length;
            }
            cell=row.createCell(8);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getSeven().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[8]){
                colwidth[8]=celval.getBytes().length;
            }
            cell=row.createCell(9);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getEight().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[9]){
                colwidth[9]=celval.getBytes().length;
            }
            cell=row.createCell(10);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getNine().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[10]){
                colwidth[10]=celval.getBytes().length;
            }
            cell=row.createCell(11);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getTen().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[11]){
                colwidth[11]=celval.getBytes().length;
            }
            cell=row.createCell(12);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getEleven().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[12]){
                colwidth[12]=celval.getBytes().length;
            }
            cell=row.createCell(13);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getTwelve().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[13]){
                colwidth[13]=celval.getBytes().length;
            }
            cell=row.createCell(14);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getSumw().toString();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[14]){
                colwidth[14]=celval.getBytes().length;
            }
            if(i<list.size()-1 && obj.getChildren().size()>0){
                for(int j=0;j<obj.getChildren().size();j++){
                    StNeedwaterPlan child=obj.getChildren().get(j);
                    rowcount+=1;
                    row = sheet.createRow(rowcount);
                    cell=row.createCell(0);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getOrganName();
                    if(celval==null){
                        celval="";
                    }
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[0]){
                        colwidth[0]=celval.getBytes().length;
                    }
                    cell=row.createCell(1);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getCanalName();
                    if(celval==null){
                        celval="";
                    }
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[1]){
                        colwidth[1]=celval.getBytes().length;
                    }
                    cell=row.createCell(2);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getOne().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[2]){
                        colwidth[2]=celval.getBytes().length;
                    }
                    cell=row.createCell(3);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getTwo().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[3]){
                        colwidth[3]=celval.getBytes().length;
                    }
                    cell=row.createCell(4);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getThree().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[4]){
                        colwidth[4]=celval.getBytes().length;
                    }
                    cell=row.createCell(5);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getFour().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[5]){
                        colwidth[5]=celval.getBytes().length;
                    }
                    cell=row.createCell(6);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getFive().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[6]){
                        colwidth[6]=celval.getBytes().length;
                    }
                    cell=row.createCell(7);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getSix().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[7]){
                        colwidth[7]=celval.getBytes().length;
                    }
                    cell=row.createCell(8);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getSeven().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[8]){
                        colwidth[8]=celval.getBytes().length;
                    }
                    cell=row.createCell(9);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getEight().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[9]){
                        colwidth[9]=celval.getBytes().length;
                    }
                    cell=row.createCell(10);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getNine().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[10]){
                        colwidth[10]=celval.getBytes().length;
                    }
                    cell=row.createCell(11);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getTen().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[11]){
                        colwidth[11]=celval.getBytes().length;
                    }
                    cell=row.createCell(12);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getEleven().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[12]){
                        colwidth[12]=celval.getBytes().length;
                    }
                    cell=row.createCell(13);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getTwelve().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[13]){
                        colwidth[13]=celval.getBytes().length;
                    }
                    cell=row.createCell(14);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval=child.getSumw().toString();
                    CellUtil.setCellValue(cell, celval);
                    if(celval.getBytes().length>colwidth[14]){
                        colwidth[14]=celval.getBytes().length;
                    }
                }
            }
        }

        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"需水计划");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出用水额定计划
    @ResponseBody
    @RequestMapping(value="/exportwaterquota",method = RequestMethod.GET)
    public void exportwaterquota(HttpServletResponse response,LvDingParam param){
        List<StNeedwaterPlan> list=stNeedwaterPlanService.selectWaterquota(param);
        String[] head0 = new String[] { "管理机构", "支渠名称", "一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月","年度总计"};
        Integer[] colwidth = new Integer[15];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"率定".getBytes().length;
        }
        String sheetName="用水额定计划";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        int rowcount=0;
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                rowcount = i + 1;
            } else {
                rowcount += 1;
            }
            row = sheet.createRow(rowcount);
            StNeedwaterPlan obj = list.get(i);
            Double[] array= {obj.getOne(),obj.getTwo(),obj.getThree(),obj.getFour(),obj.getFive(),obj.getSix(),obj.getSeven(),obj.getEight(),obj.getNine(),obj.getTen(),obj.getEleven(),obj.getTwelve(),obj.getSumw()};
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval=obj.getOrganName();
            if(celval==null){
                celval="";
            }
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[0]){
                colwidth[0]=celval.getBytes().length;
            }
            cell=row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            celval=obj.getCanalName();
            if(celval==null){
                celval="";
            }
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[1]){
                colwidth[1]=celval.getBytes().length;
            }
            for(int j=0;j<array.length;j++){
                cell=row.createCell(2+j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                celval=array[j].toString();
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[2+j]){
                    colwidth[2+j]=celval.getBytes().length;
                }
            }
            if(obj.getChildren().size()>0) {
                for (int j = 0; j < obj.getChildren().size(); j++) {
                    StNeedwaterPlan child = obj.getChildren().get(j);
                    Double[] subarr1= {child.getOne(),child.getTwo(),child.getThree(),child.getFour(),child.getFive(),child.getSix(),child.getSeven(),child.getEight(),child.getNine(),child.getTen(),child.getEleven(),child.getTwelve(),child.getSumw()};
                    rowcount += 1;
                    row = sheet.createRow(rowcount);
                    cell = row.createCell(0);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval = child.getOrganName();
                    if (celval == null) {
                        celval = "";
                    }
                    CellUtil.setCellValue(cell, celval);
                    if (celval.getBytes().length > colwidth[0]) {
                        colwidth[0] = celval.getBytes().length;
                    }
                    cell = row.createCell(1);
                    cell.setCellStyle(style2);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    celval = child.getCanalName();
                    if (celval == null) {
                        celval = "";
                    }
                    CellUtil.setCellValue(cell, celval);
                    if (celval.getBytes().length > colwidth[1]) {
                        colwidth[1] = celval.getBytes().length;
                    }
                    for(int h=0;h<subarr1.length;h++){
                        cell = row.createCell(2+h);
                        cell.setCellStyle(style2);
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        celval = subarr1[h].toString();
                        CellUtil.setCellValue(cell, celval);
                        if (celval.getBytes().length > colwidth[2+h]) {
                            colwidth[2+h] = celval.getBytes().length;
                        }
                    }
                    if(child.getChildren()!=null && child.getChildren().size()>0){
                        for(int k=0;k<child.getChildren().size();k++) {
                            StNeedwaterPlan subobj = child.getChildren().get(k);
                            Double[] subarr2 = {subobj.getOne(), subobj.getTwo(), subobj.getThree(), subobj.getFour(), subobj.getFive(), subobj.getSix(), subobj.getSeven(), subobj.getEight(), subobj.getNine(), subobj.getTen(), subobj.getEleven(), subobj.getTwelve(), subobj.getSumw()};
                            rowcount += 1;
                            row = sheet.createRow(rowcount);
                            cell = row.createCell(0);
                            cell.setCellStyle(style2);
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            celval = subobj.getOrganName();
                            if (celval == null) {
                                celval = "";
                            }
                            CellUtil.setCellValue(cell, celval);
                            if (celval.getBytes().length > colwidth[0]) {
                                colwidth[0] = celval.getBytes().length;
                            }
                            cell = row.createCell(1);
                            cell.setCellStyle(style2);
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            celval = subobj.getCanalName();
                            if (celval == null) {
                                celval = "";
                            }
                            CellUtil.setCellValue(cell, celval);
                            if (celval.getBytes().length > colwidth[1]) {
                                colwidth[1] = celval.getBytes().length;
                            }
                            for (int s = 0; s < subarr2.length; s++) {
                                cell = row.createCell(2 + s);
                                cell.setCellStyle(style2);
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                celval = subarr2[s].toString();
                                CellUtil.setCellValue(cell, celval);
                                if (celval.getBytes().length > colwidth[2 + s]) {
                                    colwidth[2 + s] = celval.getBytes().length;
                                }
                            }
                        }
                    }
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"用水额定计划");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出用水统计
    @ResponseBody
    @RequestMapping(value="/exportusewater",method = RequestMethod.GET)
    public void exportusewater(HttpServletResponse response,LvDingParam param){
        String title="年份："+param.getYear()+"  支渠名称："+param.getCanalname()+"  单位：用水量（万m³）  用水率（%）";
        List<StNeedwaterPlan> list=stNeedwaterPlanService.selectUsewaterByCanalcode(param);
        String[] head0 = new String[] { "名称","一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月","年度总计"};
        Integer[] colwidth = new Integer[14];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"率定".getBytes().length;
        }
        String sheetName="用水统计";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        HSSFCellStyle styleh = workbook.createCellStyle();
        styleh.setFont(font);
        styleh.setWrapText(true); // 换行
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        HSSFRow rowh = sheet.createRow(0);
        rowh.setHeight((short) 0x180);
        HSSFCell cellh = rowh.createCell(0);
        cellh.setCellStyle(styleh);
        CellUtil.setCellValue(cellh, title);
        HSSFRow row = sheet.createRow(1);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        DecimalFormat df1 = new DecimalFormat("######0.00");
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            StNeedwaterPlan obj=list.get(i);
            Double[] array= {obj.getOne(),obj.getTwo(),obj.getThree(),obj.getFour(),obj.getFive(),obj.getSix(),obj.getSeven(),obj.getEight(),obj.getNine(),obj.getTen(),obj.getEleven(),obj.getTwelve(),obj.getSumw()};
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval=obj.getStnm();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[0]){
                colwidth[0]=celval.getBytes().length;
            }
            for(int j=0;j<array.length;j++){
                cell=row.createCell(1+j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                celval=array[j].toString();
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[1+j]){
                    colwidth[1+j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"用水统计");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出标准断面站点参数
    @ResponseBody
    @RequestMapping(value="/exportstsection",method = RequestMethod.GET)
    public void exportstsection(HttpServletResponse response,LvDingParam param){
        if(param.getOrderBy()!=null && param.getOrderBy().equals("TYPENAME")){
            param.setOrderBy("TYPES");
        }
        List<StStsectionStation> list= stStsectionStationService.selectStStsectionStationByPage(param);
        String[] head0 = new String[] { "站名", "所在渠道","断面类型","渠底宽度","边坡系数","糙率","底坡","最大水深(m)","最大流量(m³/s)"};
        Integer[] colwidth = new Integer[9];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"率定".getBytes().length;
        }
        String sheetName="标准断面站点参数";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            StStsectionStation obj=list.get(i);
            String[] array={obj.getStnm(),obj.getCanalName(),obj.getTypename(), CellUtil.Nullvalue(obj.getB()),CellUtil.Nullvalue(obj.getM()),CellUtil.Nullvalue(obj.getN()),CellUtil.Nullvalue(obj.getI()),CellUtil.Nullvalue(obj.getMaxZ()),CellUtil.Nullvalue(obj.getMaxQ())};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出巴歇尔槽站点参数
    @ResponseBody
    @RequestMapping(value="/exportpars",method = RequestMethod.GET)
    public void exportpars(HttpServletResponse response,LvDingParam param){
        if(param.getOrderBy()!=null && param.getOrderBy().equals("MODELNAME")){
            param.setOrderBy("MODEL");
        }
        List<StParStation> list=stStsectionStationService.selectStParStationByPage(param);
        String[] head0 = new String[] { "站名", "所在渠道","巴歇尔槽型号","最大水深(m)","最大流量(m³/s)"};
        Integer[] colwidth = new Integer[5];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName="巴歇尔槽站点参数";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            StParStation obj=list.get(i);
            String[] array={obj.getStnm(),obj.getCanalName(),obj.getModelname(), CellUtil.Nullvalue(obj.getzMax()), CellUtil.Nullvalue(obj.getqMax())};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出标准巴歇尔槽参数
    @ResponseBody
    @RequestMapping(value="/exportparshall",method = RequestMethod.GET)
    public void exportparshall(HttpServletResponse response,LvDingParam param){
        List<StParshallFlume> list=stStsectionStationService.selectStParshallFlumeByPage(param);
        String[] head0 = new String[] { "型号","水深范围(m)", "水深范围(m)", "测流范围(m³/s)", "测流范围(m³/s)", "规格尺寸(m)", "收缩部","收缩部","收缩部","喉部","喉部","喉部","扩散段","扩散段","扩散段","淹没度(%)","流量系数","流量系数" };
        String[] head1 = new String[] { "型号", "最小", "最大", "最小","最大","规格尺寸(m)","宽度","长度","高度","宽度","长度","高度","宽度","长度","高度","淹没度(%)","系数1","系数2" };
        String[] headnum0 = new String[] { "0,1,0,0", "0,0,1,2","0,0,3,4", "0,1,5,5", "0,0,6,8","0,0,9,11","0,0,12,14","0,1,15,15","0,0,16,17"};
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName="标准巴歇尔槽参数";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        row = sheet.createRow(1);
        for(int i=0;i<head1.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(head1[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            StParshallFlume obj=list.get(i);
            String[] array={CellUtil.Nullvalue(obj.getModel()),CellUtil.Nullvalue(obj.getzMin()),CellUtil.Nullvalue(obj.getzMax()), CellUtil.Nullvalue(obj.getqMin()), CellUtil.Nullvalue(obj.getqMax()),obj.getSize(),CellUtil.Nullvalue(obj.getB1()),CellUtil.Nullvalue(obj.getL1()),CellUtil.Nullvalue(obj.getLa()),CellUtil.Nullvalue(obj.getB()),CellUtil.Nullvalue(obj.getL()),CellUtil.Nullvalue(obj.getN()),CellUtil.Nullvalue(obj.getB2()),CellUtil.Nullvalue(obj.getL2()),CellUtil.Nullvalue(obj.getK()),CellUtil.Nullvalue(obj.getYmd()),CellUtil.Nullvalue(obj.getC()),CellUtil.Nullvalue(obj.getN1())};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出水闸站点参数
    @ResponseBody
    @RequestMapping(value="/exportlock",method = RequestMethod.GET)
    public void exportlock(HttpServletResponse response,LvDingParam param){
        if(param.getOrderBy()!=null && param.getOrderBy().equals("SLTPNAME")){
            param.setOrderBy("SLTP");
        }
        List<StGateStation> list=stStsectionStationService.selectStGateStationByPage(param);
        String[] head0 = new String[] { "站名", "所在渠道","水闸类型","水闸宽度","最大开度","闸前","闸前","闸后","闸后"};
        String[] head1 = new String[] { "站名", "所在渠道","水闸类型","水闸宽度","最大开度","最大水深","最大流量","最大水深","最大流量"};
        String[] headnum0 = new String[] {"0,1,0,0","0,1,1,1","0,1,2,2","0,1,3,3","0,1,4,4","0,0,5,6","0,0,7,8"};
                Integer[] colwidth = new Integer[9];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName="水闸站点参数";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        row = sheet.createRow(1);
        for(int i=0;i<head1.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(head1[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            StGateStation obj=list.get(i);
            String[] array={obj.getStnm(),obj.getCanalName(),obj.getSltpname(), CellUtil.Nullvalue(obj.getGthlntwd()), CellUtil.Nullvalue(obj.getGateHi()),CellUtil.Nullvalue(obj.getzMaxF()),CellUtil.Nullvalue(obj.getqMaxF()),CellUtil.Nullvalue(obj.getzMaxB()),CellUtil.Nullvalue(obj.getqMaxB())};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出值班通讯录
    @ResponseBody
    @RequestMapping(value="/exportdutyinfo",method = RequestMethod.GET)
    public void exportdutyinfo(HttpServletResponse response,AlarmParam param){
        List<HhpdiDutyaddrlist> list=smsBoxsendedService.selectDutyAddrListByPage(param);
        String[] head0 = new String[] { "姓名", "部门","职务","办公电话","手机号码"};
        Integer[] colwidth = new Integer[5];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName="值班通讯录";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            HhpdiDutyaddrlist obj=list.get(i);
            String[] array={obj.getNm(),obj.getUnit(),obj.getPosition(), obj.getOphone(), obj.getMobile()};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出雨量预警指标
    @ResponseBody
    @RequestMapping(value="/exportrainalarm",method = RequestMethod.GET)
    public void exportrainalarm(HttpServletResponse response,LvDingParam lvDingParam){
        if(lvDingParam.getOrderBy().equals("STNM")){
            lvDingParam.setOrderBy("b.STNM");
        }else if(lvDingParam.getOrderBy().equals("EWLNAME")){
            lvDingParam.setOrderBy("a.EWL");
        }else{
            lvDingParam.setOrderBy("a."+lvDingParam.getOrderBy());
        }
        List<StPpAlarm> list=stAlarminfoService.selectStPpAlarmByPage(lvDingParam);
        String[] head0 = new String[] { "站名", "预警等级","前72H降雨","1H降雨","3H降雨","6H降雨","12H降雨","24H降雨"};
        Integer[] colwidth = new Integer[8];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName="降雨预警指标";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            StPpAlarm obj=list.get(i);
            String[] array={obj.getStnm(),obj.getEwlname(),CellUtil.Nullvalue(obj.getPtdP()), CellUtil.Nullvalue(obj.getOhP()), CellUtil.Nullvalue(obj.getThP()),CellUtil.Nullvalue(obj.getShP()),CellUtil.Nullvalue(obj.getTwhP()),CellUtil.Nullvalue(obj.getOdP())};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出水库水位预警指标
    @ResponseBody
    @RequestMapping(value="/exportrsvalarm",method = RequestMethod.GET)
    public void exportrsvalarm(HttpServletResponse response,LvDingParam lvDingParam){
        if(lvDingParam.getOrderBy().equals("STNM")){
            lvDingParam.setOrderBy("b.STNM");
        }else{
            lvDingParam.setOrderBy("a."+lvDingParam.getOrderBy());
        }
        List<StRsvAlarm> list=stAlarminfoService.selectStRswAlarmByPage(lvDingParam);
        String[] head0 = new String[] { "站名", "校核洪水位","正常蓄水位","4-6月汛限水位","7-9月汛限水位","死水位","设计洪水位"};
        Integer[] colwidth = new Integer[7];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName="水库水位预警指标";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            StRsvAlarm obj=list.get(i);
            String[] array={obj.getStnm(),CellUtil.Nullvalue(obj.getXhwl()),CellUtil.Nullvalue(obj.getZcwl()), CellUtil.Nullvalue(obj.getFwl()), CellUtil.Nullvalue(obj.getFwl79()),CellUtil.Nullvalue(obj.getSwl()),CellUtil.Nullvalue(obj.getSjwl())};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出河道水位预警指标
    @ResponseBody
    @RequestMapping(value="/exportriveralarm",method = RequestMethod.GET)
    public void exportriveralarm(HttpServletResponse response,LvDingParam lvDingParam){
        if(lvDingParam.getOrderBy().equals("STNM")){
            lvDingParam.setOrderBy("b.STNM");
        }else{
            lvDingParam.setOrderBy("a."+lvDingParam.getOrderBy());
        }
        List<StRiverAlarm> list=stAlarminfoService.selectStRiverAlarmByPage(lvDingParam);
        String[] head0 = new String[] { "站名", "校核洪水位","保证水位","警戒水位","设计洪水位","经验水位"};
        Integer[] colwidth = new Integer[6];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName="河道水位预警指标";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            StRiverAlarm obj=list.get(i);
            String[] array={obj.getStnm(),CellUtil.Nullvalue(obj.getXhwl()),CellUtil.Nullvalue(obj.getBzwl()), CellUtil.Nullvalue(obj.getAwl()), CellUtil.Nullvalue(obj.getSjwl()),CellUtil.Nullvalue(obj.getJywl())};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出雨情时段表
    @ResponseBody
    @RequestMapping(value="/exportrainhour",method = RequestMethod.GET)
    public void exportrainhour(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> list=stAlarminfoService.selectHourRainExport(medataParam);
        String[] head0 = new String[] { "乡镇", "站名","累计降雨量(mm)"};
        Integer[] colwidth = new Integer[3];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName=medataParam.getBegintime().substring(0,13)+"时至"+medataParam.getEndtime().substring(0,13)+"时降雨量";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("ZHEN").toString(),obj.get("STNM").toString(),CellUtil.Nullvalue(Double.parseDouble(obj.get("P").toString()))};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"时段降雨量");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出雨情日表
    @ResponseBody
    @RequestMapping(value="/exportrainday",method = RequestMethod.GET)
    public void exportrainday(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> list=stAlarminfoService.selectDayRainExport(medataParam);
        String[] head0 = new String[] {"站名","日降雨量(mm)","8:00-9:00","9:00-10:00","10:00-11:00","11:00-12:00","12:00-13:00","13:00-14:00","14:00-15:00","15:00-16:00","16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00","20:00-21:00","21:00-22:00","22:00-23:00","23:00-0:00","0:00-1:00","1:00-2:00","2:00-3:00","3:00-4:00","4:00-5:00","5:00-6:00","6:00-7:00","7:00-8:00","归属单位","乡镇"};
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName=medataParam.getBegintime()+"降雨量";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("STNM").toString(),CellUtil.Nullvalue(obj.get("P")),CellUtil.Nullvalue(obj.get("8:00-9:00")),CellUtil.Nullvalue(obj.get("9:00-10:00")),CellUtil.Nullvalue(obj.get("10:00-11:00")),CellUtil.Nullvalue(obj.get("11:00-12:00")),CellUtil.Nullvalue(obj.get("12:00-13:00"))
                    ,CellUtil.Nullvalue(obj.get("13:00-14:00")),CellUtil.Nullvalue(obj.get("14:00-15:00")),CellUtil.Nullvalue(obj.get("15:00-16:00")),CellUtil.Nullvalue(obj.get("16:00-17:00")),CellUtil.Nullvalue(obj.get("17:00-18:00")),CellUtil.Nullvalue(obj.get("18:00-19:00")),CellUtil.Nullvalue(obj.get("19:00-20:00")),CellUtil.Nullvalue(obj.get("20:00-21:00"))
                    ,CellUtil.Nullvalue(obj.get("21:00-22:00")),CellUtil.Nullvalue(obj.get("22:00-23:00")),CellUtil.Nullvalue(obj.get("23:00-0:00")),CellUtil.Nullvalue(obj.get("0:00-1:00")),CellUtil.Nullvalue(obj.get("1:00-2:00")),CellUtil.Nullvalue(obj.get("2:00-3:00")),CellUtil.Nullvalue(obj.get("3:00-4:00")),CellUtil.Nullvalue(obj.get("4:00-5:00"))
                    ,CellUtil.Nullvalue(obj.get("5:00-6:00")),CellUtil.Nullvalue(obj.get("6:00-7:00")),CellUtil.Nullvalue(obj.get("7:00-8:00")),obj.get("STGR").toString(),obj.get("AD_NM").toString()};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"日降雨量");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出雨情月表
    @ResponseBody
    @RequestMapping(value="/exportrainmonth",method = RequestMethod.GET)
    public void exportrainmonth(HttpServletResponse response,MedataParam medataParam){
        String[] datestr=medataParam.getBegintime().split("-");
        medataParam.setYear(Integer.parseInt(datestr[0]));
        medataParam.setMonth(Integer.parseInt(datestr[1]));
        //月份天数判断标识
        int monthsign=0;
        switch (medataParam.getMonth()){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: monthsign=31;break;
            case 2: if((medataParam.getYear()%4==0 && medataParam.getYear()%100!=0)||(medataParam.getYear()%400==0)){
                monthsign=29;
                break;
            }else{
                monthsign=28;
                break;
            }
            case 4:
            case 6:
            case 9:
            case 11:monthsign=30;break;
        }
        List<Map<String,Object>> list=stAlarminfoService.selectMonthRainExport(medataParam);
        String[] head0;
        if(monthsign==31){
            head0 = new String[] {"站名","月降雨量(mm)","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","归属单位","乡镇"};
        }else if(monthsign==29){
            head0 = new String[] {"站名","月降雨量(mm)","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","归属单位","乡镇"};
        }else if(monthsign==28){
            head0 = new String[] {"站名","月降雨量(mm)","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","归属单位","乡镇"};
        }else {
            head0 = new String[] {"站名","月降雨量(mm)","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","归属单位","乡镇"};
        }
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName=medataParam.getBegintime()+"降雨量";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String[] array;
            if(monthsign==31){
                array=new String[] {obj.get("STNM").toString(),CellUtil.Nullvalue(obj.get("P")),CellUtil.Nullvalue(obj.get("1")),CellUtil.Nullvalue(obj.get("2")),CellUtil.Nullvalue(obj.get("3")),CellUtil.Nullvalue(obj.get("4")),CellUtil.Nullvalue(obj.get("5"))
                        ,CellUtil.Nullvalue(obj.get("6")),CellUtil.Nullvalue(obj.get("7")),CellUtil.Nullvalue(obj.get("8")),CellUtil.Nullvalue(obj.get("9")),CellUtil.Nullvalue(obj.get("10")),CellUtil.Nullvalue(obj.get("11")),CellUtil.Nullvalue(obj.get("12")),CellUtil.Nullvalue(obj.get("13"))
                        ,CellUtil.Nullvalue(obj.get("14")),CellUtil.Nullvalue(obj.get("15")),CellUtil.Nullvalue(obj.get("16")),CellUtil.Nullvalue(obj.get("17")),CellUtil.Nullvalue(obj.get("18")),CellUtil.Nullvalue(obj.get("19")),CellUtil.Nullvalue(obj.get("20")),CellUtil.Nullvalue(obj.get("21"))
                        ,CellUtil.Nullvalue(obj.get("22")),CellUtil.Nullvalue(obj.get("23")),CellUtil.Nullvalue(obj.get("24")),CellUtil.Nullvalue(obj.get("25")),CellUtil.Nullvalue(obj.get("26")),CellUtil.Nullvalue(obj.get("27")),CellUtil.Nullvalue(obj.get("28")),CellUtil.Nullvalue(obj.get("29")),CellUtil.Nullvalue(obj.get("30")),CellUtil.Nullvalue(obj.get("31")),obj.get("STGR").toString(),obj.get("AD_NM").toString()};
            }else if(monthsign==29){
                array=new String[] {obj.get("STNM").toString(),CellUtil.Nullvalue(obj.get("P")),CellUtil.Nullvalue(obj.get("1")),CellUtil.Nullvalue(obj.get("2")),CellUtil.Nullvalue(obj.get("3")),CellUtil.Nullvalue(obj.get("4")),CellUtil.Nullvalue(obj.get("5"))
                        ,CellUtil.Nullvalue(obj.get("6")),CellUtil.Nullvalue(obj.get("7")),CellUtil.Nullvalue(obj.get("8")),CellUtil.Nullvalue(obj.get("9")),CellUtil.Nullvalue(obj.get("10")),CellUtil.Nullvalue(obj.get("11")),CellUtil.Nullvalue(obj.get("12")),CellUtil.Nullvalue(obj.get("13"))
                        ,CellUtil.Nullvalue(obj.get("14")),CellUtil.Nullvalue(obj.get("15")),CellUtil.Nullvalue(obj.get("16")),CellUtil.Nullvalue(obj.get("17")),CellUtil.Nullvalue(obj.get("18")),CellUtil.Nullvalue(obj.get("19")),CellUtil.Nullvalue(obj.get("20")),CellUtil.Nullvalue(obj.get("21"))
                        ,CellUtil.Nullvalue(obj.get("22")),CellUtil.Nullvalue(obj.get("23")),CellUtil.Nullvalue(obj.get("24")),CellUtil.Nullvalue(obj.get("25")),CellUtil.Nullvalue(obj.get("26")),CellUtil.Nullvalue(obj.get("27")),CellUtil.Nullvalue(obj.get("28")),CellUtil.Nullvalue(obj.get("29")),obj.get("STGR").toString(),obj.get("AD_NM").toString()};
            }else if (monthsign==28){
                array=new String[] {obj.get("STNM").toString(),CellUtil.Nullvalue(obj.get("P")),CellUtil.Nullvalue(obj.get("1")),CellUtil.Nullvalue(obj.get("2")),CellUtil.Nullvalue(obj.get("3")),CellUtil.Nullvalue(obj.get("4")),CellUtil.Nullvalue(obj.get("5"))
                        ,CellUtil.Nullvalue(obj.get("6")),CellUtil.Nullvalue(obj.get("7")),CellUtil.Nullvalue(obj.get("8")),CellUtil.Nullvalue(obj.get("9")),CellUtil.Nullvalue(obj.get("10")),CellUtil.Nullvalue(obj.get("11")),CellUtil.Nullvalue(obj.get("12")),CellUtil.Nullvalue(obj.get("13"))
                        ,CellUtil.Nullvalue(obj.get("14")),CellUtil.Nullvalue(obj.get("15")),CellUtil.Nullvalue(obj.get("16")),CellUtil.Nullvalue(obj.get("17")),CellUtil.Nullvalue(obj.get("18")),CellUtil.Nullvalue(obj.get("19")),CellUtil.Nullvalue(obj.get("20")),CellUtil.Nullvalue(obj.get("21"))
                        ,CellUtil.Nullvalue(obj.get("22")),CellUtil.Nullvalue(obj.get("23")),CellUtil.Nullvalue(obj.get("24")),CellUtil.Nullvalue(obj.get("25")),CellUtil.Nullvalue(obj.get("26")),CellUtil.Nullvalue(obj.get("27")),CellUtil.Nullvalue(obj.get("28")),obj.get("STGR").toString(),obj.get("AD_NM").toString()};
            }else {
                array=new String[] {obj.get("STNM").toString(),CellUtil.Nullvalue(obj.get("P")),CellUtil.Nullvalue(obj.get("1")),CellUtil.Nullvalue(obj.get("2")),CellUtil.Nullvalue(obj.get("3")),CellUtil.Nullvalue(obj.get("4")),CellUtil.Nullvalue(obj.get("5"))
                        ,CellUtil.Nullvalue(obj.get("6")),CellUtil.Nullvalue(obj.get("7")),CellUtil.Nullvalue(obj.get("8")),CellUtil.Nullvalue(obj.get("9")),CellUtil.Nullvalue(obj.get("10")),CellUtil.Nullvalue(obj.get("11")),CellUtil.Nullvalue(obj.get("12")),CellUtil.Nullvalue(obj.get("13"))
                        ,CellUtil.Nullvalue(obj.get("14")),CellUtil.Nullvalue(obj.get("15")),CellUtil.Nullvalue(obj.get("16")),CellUtil.Nullvalue(obj.get("17")),CellUtil.Nullvalue(obj.get("18")),CellUtil.Nullvalue(obj.get("19")),CellUtil.Nullvalue(obj.get("20")),CellUtil.Nullvalue(obj.get("21"))
                        ,CellUtil.Nullvalue(obj.get("22")),CellUtil.Nullvalue(obj.get("23")),CellUtil.Nullvalue(obj.get("24")),CellUtil.Nullvalue(obj.get("25")),CellUtil.Nullvalue(obj.get("26")),CellUtil.Nullvalue(obj.get("27")),CellUtil.Nullvalue(obj.get("28")),CellUtil.Nullvalue(obj.get("29")),CellUtil.Nullvalue(obj.get("30")),obj.get("STGR").toString(),obj.get("AD_NM").toString()};
            }
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"月降雨量");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出雨情年表
    @ResponseBody
    @RequestMapping(value="/exportrainyear",method = RequestMethod.GET)
    public void exportrainyear(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> list=stAlarminfoService.selectYearRainExport(medataParam);
        String[] head0 = new String[] {"站名","年降雨量(mm)","1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月","归属单位","乡镇"};
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName=medataParam.getYear()+"年降雨量";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("STNM").toString(),CellUtil.Nullvalue(obj.get("P")),CellUtil.Nullvalue(obj.get("1月")),CellUtil.Nullvalue(obj.get("2月")),CellUtil.Nullvalue(obj.get("3月")),CellUtil.Nullvalue(obj.get("4月")),CellUtil.Nullvalue(obj.get("5月"))
                    ,CellUtil.Nullvalue(obj.get("6月")),CellUtil.Nullvalue(obj.get("7月")),CellUtil.Nullvalue(obj.get("8月")),CellUtil.Nullvalue(obj.get("9月")),CellUtil.Nullvalue(obj.get("10月")),CellUtil.Nullvalue(obj.get("11月")),CellUtil.Nullvalue(obj.get("12月"))
                    ,obj.get("STGR").toString(),obj.get("AD_NM").toString()};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"年降雨量");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出水库小时表
    @ResponseBody
    @RequestMapping(value="/exportskhour",method = RequestMethod.GET)
    public void exportskhour(HttpServletResponse response,AlarmParam alarmparam){
        Map<String,Object> allmap=wrpFieldinfoService.selectStRsvrHByHours(alarmparam);
        Map<String,Object> total=(Map<String,Object>)allmap.get("total");
        List<Map<String,Object>> info=(List<Map<String,Object>>)allmap.get("header");//stAlarminfoService.selectRsvrInfoExport();
        List<Map<String,Object>> list=(List<Map<String,Object>>)allmap.get("rows");//stAlarminfoService.selectRsvrHourExport(medataParam);
        String[] head0 = new String[] { "时间", "平均水位(m)","水势","平均入库流量(m³/s)","平均出库流量(m³/s)","当前库容(万m³)"};
        String[] headnum0 = new String[] {"0,0,0,5","1,1,1,5","2,2,1,5","3,3,1,5"};
        Integer[] colwidth = new Integer[6];
        for(int i=0;i<head0.length;i++){
            if(i==0){
                colwidth[i]="正常蓄水位(m)文字".getBytes().length;
            }else{
                colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
            }
        }
        String sheetName=alarmparam.getBegintime().substring(0,13)+"至"+alarmparam.getEndtime().substring(0,13)+"水库水位小时表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 13);
        font.setBold(true);
        HSSFCellStyle style0 =workbook.createCellStyle();
        style0.setFont(font);
        style0.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style0.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        HSSFRow row0 = sheet.createRow(0);
        row0.setHeight((short) 0x200);
        HSSFCell cell0=row0.createCell(0);
        cell0.setCellStyle(style0);
        cell0.setCellValue(alarmparam.getBegintime().substring(0,13)+"时至"+alarmparam.getEndtime().substring(0,13)+"时  总入库水量："+total.get("INW").toString()+"万m³    总出库水量："+total.get("OUTW").toString()+"万m³");
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        HSSFRow row = sheet.createRow(1);
        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("站名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(info.get(0).get("STNM").toString());
        cell.setCellStyle(style);
        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("汛限水位(m)");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(info.get(0).get("FWL").toString());
        cell.setCellStyle(style);
        row = sheet.createRow(3);
        cell = row.createCell(0);
        cell.setCellValue("正常蓄水位(m)");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(info.get(0).get("ZCWL").toString());
        cell.setCellStyle(style);
        row=sheet.createRow(4);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 5);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("DT").toString(),CellUtil.Nullvalue(obj.get("RZ")),CellUtil.Nullvalue(obj.get("CV")),CellUtil.Nullvalue(obj.get("INQ")),CellUtil.Nullvalue(obj.get("OTQ")),CellUtil.Nullvalue(obj.get("KR"))};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"水库小时表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出水库入库出库水量
    @ResponseBody
    @RequestMapping(value="/exportsktime",method = RequestMethod.GET)
    public void exportsktime(HttpServletResponse response,AlarmParam alarmparam){
        Map<String,Object> allmap=wrpFieldinfoService.selectStRsvrHByHours(alarmparam);
        Map<String,Object> total=(Map<String,Object>)allmap.get("total");
        List<Map<String,Object>> info=(List<Map<String,Object>>)allmap.get("header");
        Integer[] colwidth = new Integer[2];
        for(int i=0;i<2;i++){
            if(i==0){
                colwidth[i]="正常蓄水位(万m³)文字".getBytes().length;
            }else{
                colwidth[i]="一二三四五六七八九十十一十二文字".getBytes().length;
            }
        }
        String sheetName=alarmparam.getAdcd()+"入库出库水量";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        //设置行高
        sheet.setDefaultRowHeight((short)0x150);
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 13);
        font.setBold(true);
        HSSFCellStyle style0 =workbook.createCellStyle();
        style0.setFont(font);
        style0.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style0.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        HSSFRow row0 = sheet.createRow(0);
        row0.setHeight((short) 0x200);
        HSSFCell cell0=row0.createCell(0);
        cell0.setCellStyle(style0);
        cell0.setCellValue(alarmparam.getAdcd()+"入库出库水量信息");
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        HSSFRow row = sheet.createRow(1);
        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("总入库水量(万m³)");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(total.get("INW").toString());
        cell.setCellStyle(style);
        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("总出库库水量(万m³)");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(total.get("OUTW").toString());
        cell.setCellStyle(style);
        row = sheet.createRow(3);
        cell = row.createCell(0);
        cell.setCellValue("汛限水位(m)");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(info.get(0).get("FWL").toString());
        cell.setCellStyle(style);
        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("正常蓄水位(m)");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(info.get(0).get("ZCWL").toString());
        cell.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"水库出库入库水量信息");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出水库历史表
    @ResponseBody
    @RequestMapping(value="/exportskhistory",method = RequestMethod.GET)
    public void exportskhistory(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> list=stAlarminfoService.selectRsvrHistoryExport(medataParam);
        String[] head0 = new String[] { "时间", "水位(m)","水势","入库流量(m³/s)","出库流量(m³/s)","蓄水量(10⁶m³)"};
        Integer[] colwidth = new Integer[6];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName="水库水位历史表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String ssicon="";
            if(obj.get("CV")!=null) {
                Double ss = Double.parseDouble(obj.get("CV").toString());
                if (ss > 0) {
                    ssicon = "↑";
                } else if (ss == 0) {
                    ssicon = "-";
                } else {
                    ssicon = "↓";
                }
            }
            String[] array={obj.get("TM").toString(),CellUtil.Nullvalue(obj.get("RZ")),ssicon,CellUtil.Nullvalue(obj.get("INQ")),CellUtil.Nullvalue(obj.get("OTQ")),CellUtil.Nullvalue(obj.get("W"))};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"水库水位历史表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出水库日表
    @ResponseBody
    @RequestMapping(value="/exportskday",method = RequestMethod.GET)
    public void exportskday(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> list=stAlarminfoService.selectRsvrDayExport(medataParam);
        String[] head0 = new String[] { "站名", "平均水位(m)","平均入库流量(m³/s)","平均出库流量(m³/s)","日蓄水量(10⁶m³)","日入库水量(10⁶m³)","日出库水量(10⁶m³)","日最高水位(m)","日最高水位时刻","日最低水位(m)","日最低水位时刻","汛限水位4-6月(m)","汛限水位7-9月(m)","水库等级","归属单位","地址"};
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName=medataParam.getBegintime()+"水库水位日表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("STNM").toString(),CellUtil.Nullvalue(obj.get("RZ")),CellUtil.Nullvalue(obj.get("INQ")),CellUtil.Nullvalue(obj.get("OTQ")),CellUtil.Nullvalue(obj.get("DW")),CellUtil.Nullvalue(obj.get("IDW")),CellUtil.Nullvalue(obj.get("ODW")),CellUtil.Nullvalue(obj.get("Max_RZ")),obj.get("Max_TM").toString(),CellUtil.Nullvalue(obj.get("Min_RZ")),obj.get("Min_TM").toString(),CellUtil.Nullvalue(obj.get("FWL")),CellUtil.Nullvalue(obj.get("FWL79")),obj.get("LEVEL").toString(),obj.get("STGR").toString(),obj.get("AD_NM").toString()};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"水库水位日表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出水库月表
    @ResponseBody
    @RequestMapping(value="/exportskmonth",method = RequestMethod.GET)
    public void exportskmonth(HttpServletResponse response,MedataParam medataParam){
        String[] datestr=medataParam.getBegintime().split("-");
        medataParam.setYear(Integer.parseInt(datestr[0]));
        medataParam.setMonth(Integer.parseInt(datestr[1]));
        List<Map<String,Object>> list=stAlarminfoService.selectRsvrMonthExport(medataParam);
        String[] head0 = new String[] { "站名", "平均水位(m)","平均入库流量(m³/s)","平均出库流量(m³/s)","月蓄水量(10⁶m³)","月最高水位(m)","月最高水位时刻","月最低水位(m)","月最低水位时刻","汛限水位4-6月(m)","汛限水位7-9月(m)","水库等级","归属单位","地址"};
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName=medataParam.getBegintime()+"水库水位月表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("STNM").toString(),CellUtil.Nullvalue(obj.get("RZ")),CellUtil.Nullvalue(obj.get("INQ")),CellUtil.Nullvalue(obj.get("OTQ")),CellUtil.Nullvalue(obj.get("MW")),CellUtil.Nullvalue(obj.get("Max_RZ")),obj.get("Max_TM").toString(),CellUtil.Nullvalue(obj.get("Min_RZ")),obj.get("Min_TM").toString(),CellUtil.Nullvalue(obj.get("FWL")),CellUtil.Nullvalue(obj.get("FWL79")),obj.get("LEVEL").toString(),obj.get("STGR").toString(),obj.get("AD_NM").toString()};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"水库水位月表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出河道小时表
    @ResponseBody
    @RequestMapping(value="/exporthdhour",method = RequestMethod.GET)
    public void exporthdhour(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> info=stAlarminfoService.selectRiverInfoExport();
        List<Map<String,Object>> list=stAlarminfoService.selectRiverHourExport(medataParam);
        String[] head0 = new String[] { "时间", "平均水位(m)","水势","平均流量(m³/s)","小时流量(10⁶m³)"};
        String[] headnum0 = new String[] {"0,0,1,4","1,1,1,4"};
        Integer[] colwidth = new Integer[5];
        for(int i=0;i<head0.length;i++){
            if(i==0){
                colwidth[i]="警戒水位(m)文字".getBytes().length;
            }else{
                colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
            }
        }
        String sheetName=medataParam.getBegintime().substring(0,13)+"至"+medataParam.getEndtime().substring(0,13)+"河道水位小时表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        cell = row.createCell(0);
        cell.setCellValue("站名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(info.get(0).get("STNM").toString());
        cell.setCellStyle(style);
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("警戒水位(m)");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(CellUtil.Nullvalue(info.get(0).get("AWL")));
        cell.setCellStyle(style);
        row=sheet.createRow(2);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 3);
            Map<String,Object> obj=list.get(i);
            String ssicon="";
            if(obj.get("FLOODWATER")!=null) {
                Double ss = Double.parseDouble(obj.get("FLOODWATER").toString());
                if (ss > 0) {
                    ssicon = "↑";
                } else if (ss == 0) {
                    ssicon = "-";
                } else {
                    ssicon = "↓";
                }
            }
            String[] array={obj.get("DT").toString(),CellUtil.Nullvalue(obj.get("AVGWATER")),ssicon,CellUtil.Nullvalue(obj.get("AVGFLOW")),CellUtil.Nullvalue(obj.get("HOURFLOW"))};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"河道水位小时表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出河道历史表
    @ResponseBody
    @RequestMapping(value="/exporthdhistory",method = RequestMethod.GET)
    public void exporthdhistory(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> list=stAlarminfoService.selectRiverHistoryExport(medataParam);
        String[] head0 = new String[] { "时间", "水位(m)","水势","流量(m³/s)"};
        Integer[] colwidth = new Integer[4];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName="河道水位历史表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String ssicon="";
            if(obj.get("CV")!=null) {
                Double ss = Double.parseDouble(obj.get("CV").toString());
                if (ss > 0) {
                    ssicon = "↑";
                } else if (ss == 0) {
                    ssicon = "-";
                } else {
                    ssicon = "↓";
                }
            }
            String[] array={obj.get("TM").toString(),CellUtil.Nullvalue(obj.get("Z")),ssicon,CellUtil.Nullvalue(obj.get("Q"))};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"河道水位历史表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出河道日表
    @ResponseBody
    @RequestMapping(value="/exporthdhday",method = RequestMethod.GET)
    public void exporthdhday(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> list=stAlarminfoService.selectRiverDayExport(medataParam);
        String[] head0 = new String[] { "站名", "平均水位(m)","平均流量(m³/s)","日流量(10⁶m³)","日最高水位(m)","日最高水位时刻","日最低水位(m)","日最低水位时刻","警戒水位(m)","归属单位","地址"};
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName=medataParam.getBegintime()+"河道水位日表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("STNM").toString(),CellUtil.Nullvalue(obj.get("RZ")),CellUtil.Nullvalue(obj.get("INQ")),CellUtil.Nullvalue(obj.get("DW")),CellUtil.Nullvalue(obj.get("Max_RZ")),obj.get("Max_TM").toString(),CellUtil.Nullvalue(obj.get("Min_RZ")),obj.get("Min_TM").toString(),CellUtil.Nullvalue(obj.get("AWL")),obj.get("STGR").toString(),obj.get("AD_NM").toString()};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"河道水位日表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出河道月表
    @ResponseBody
    @RequestMapping(value="/exporthdhmonth",method = RequestMethod.GET)
    public void exporthdhmonth(HttpServletResponse response,MedataParam medataParam){
        String[] datestr=medataParam.getBegintime().split("-");
        medataParam.setYear(Integer.parseInt(datestr[0]));
        medataParam.setMonth(Integer.parseInt(datestr[1]));
        List<Map<String,Object>> list=stAlarminfoService.selectRiverMonthExport(medataParam);
        String[] head0 = new String[] { "站名", "平均水位(m)","平均流量(m³/s)","月流量(10⁶m³)","月最高水位(m)","月最高水位时刻","月最低水位(m)","月最低水位时刻","警戒水位(m)","归属单位","地址"};
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        String sheetName=medataParam.getBegintime()+"河道水位月表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("STNM").toString(),CellUtil.Nullvalue(obj.get("RZ")),CellUtil.Nullvalue(obj.get("INQ")),CellUtil.Nullvalue(obj.get("MW")),CellUtil.Nullvalue(obj.get("Max_RZ")),obj.get("Max_TM").toString(),CellUtil.Nullvalue(obj.get("Min_RZ")),obj.get("Min_TM").toString(),CellUtil.Nullvalue(obj.get("AWL")),obj.get("STGR").toString(),obj.get("AD_NM").toString()};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"河道水位月表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出最新小时流量水量报表
    @ResponseBody
    @RequestMapping(value="/exportqandwhour",method = RequestMethod.GET)
    public void exportqandwhour(HttpServletResponse response,MedataParam medataParam){
        Calendar calendar=Calendar.getInstance();
        int y=calendar.get(Calendar.YEAR);
        int m=calendar.get(Calendar.MONTH);
        int d=calendar.get(Calendar.DATE);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int lasthour=hour-1;
        String title="康苏水库"+y+"年"+m+"月"+d+"日"+lasthour+":00时-"+hour+":00时  水情测报报表";
        Map<String,Object> map=wrpFieldinfoService.selectLastHourExcelData();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("最新小时水情测报报表");// 创建一个表
        //设置列宽
        sheet.setColumnWidth(0,5000);
        for (int i= 1; i<7;i++){
            sheet.setColumnWidth(i,3800);
        }
        //设置行高
        sheet.setDefaultRowHeight((short)360);
        // 表头标题样式
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 14);// 字体大小
        headfont.setBold(true);//是否加粗
        HSSFCellStyle headstyle = workbook.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        headstyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        headstyle.setBorderBottom(BorderStyle.THIN);
        headstyle.setBorderLeft(BorderStyle.THIN);
        headstyle.setBorderRight(BorderStyle.THIN);
        headstyle.setBorderTop(BorderStyle.THIN);
        headstyle.setLocked(true);
        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 6));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 6));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 4, 6));
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, title);
        for(int h=1;h<7;h++){
            cell=row.createCell(h);
            cell.setCellStyle(headstyle);
        }
        // 标题名样式
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);// 字体大小
        font.setBold(true);//是否加粗
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setWrapText(true);
        style.setLocked(true);
        row=sheet.createRow(3);
        for(int h=0;h<7;h++){
            cell=row.createCell(h);
            cell.setCellStyle(style);
        }
        row=sheet.createRow(7);
        for(int h=0;h<7;h++){
            cell=row.createCell(h);
            cell.setCellStyle(style);
        }
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short) 0x200);
        HSSFCell cell0 = row1.createCell(0);HSSFCell cell2 = row1.createCell(2);HSSFCell cell4 = row1.createCell(4);
        cell0.setCellStyle(style);cell2.setCellStyle(style);cell4.setCellStyle(style);
        cell0.setCellValue("当前库容");cell2.setCellValue("当前水位");cell4.setCellValue("入库水量");
        cell0=row1.createCell(6);
        cell0.setCellStyle(style);
        HSSFRow row2 = sheet.createRow(2);
        row2.setHeight((short) 0x200);
        HSSFCell cell20=row2.createCell(0);HSSFCell cell22=row2.createCell(2);HSSFCell cell24=row2.createCell(4);
        cell20.setCellStyle(style);cell22.setCellStyle(style);cell24.setCellStyle(style);
        cell20.setCellValue("入库流量");cell22.setCellValue("出库流量");cell24.setCellValue("出库水量");
        cell20=row2.createCell(6);
        cell20.setCellStyle(style);
        HSSFRow row4=sheet.createRow(4);
        row4.setHeight((short) 0x200);
        HSSFCell cell40=row4.createCell(0);HSSFCell cell42=row4.createCell(2);HSSFCell cell44=row4.createCell(4);
        cell40.setCellStyle(style);cell42.setCellStyle(style);cell44.setCellStyle(style);
        cell40.setCellValue("闸门名称");cell42.setCellValue("闸门开度（m）");cell44.setCellValue("开度时间");
        cell40=row4.createCell(1);cell40.setCellStyle(style);cell40=row4.createCell(3);cell40.setCellStyle(style);cell40=row4.createCell(3);cell40.setCellStyle(style);cell40=row4.createCell(6);cell40.setCellStyle(style);
        HSSFRow row8=sheet.createRow(8);
        row8.setHeight((short)0x300);
        HSSFCell cell80=row8.createCell(0),cell81=row8.createCell(1),cell82=row8.createCell(2),cell83=row8.createCell(3),cell84=row8.createCell(4),cell85=row8.createCell(5),cell86=row8.createCell(6);
        cell80.setCellStyle(style);cell81.setCellStyle(style);cell82.setCellStyle(style);cell83.setCellStyle(style);cell84.setCellStyle(style);cell85.setCellStyle(style);cell86.setCellStyle(style);
        cell80.setCellValue("站点名称");cell81.setCellValue("降雨\n"+"（mm）");cell82.setCellValue("实时水位\n"+"（m）");cell83.setCellValue("入库流量 \n"+"（m³/s）");cell84.setCellValue("入库水量\n"+"（万m³）");cell85.setCellValue("出库流量\n"+"（m³/s）");cell86.setCellValue("出库水量\n"+"（万m³）");
        // 普通单元格样式（中文）
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 12);
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFont(font2);
        style2.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        style2.setWrapText(true); // 换行
        style2.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        style2.setBorderBottom(BorderStyle.THIN); //下边框
        style2.setBorderLeft(BorderStyle.THIN);//左边框
        style2.setBorderTop(BorderStyle.THIN);//上边框
        style2.setBorderRight(BorderStyle.THIN);//右边框
        HSSFCell cell1 = row1.createCell(1);HSSFCell cell3 = row1.createCell(3);HSSFCell cell5 = row1.createCell(5);
        cell1.setCellStyle(style2);cell3.setCellStyle(style2);cell5.setCellStyle(style2);
        cell1.setCellValue((String)map.get("Value1"));cell3.setCellValue((String)map.get("Value2"));cell5.setCellValue((String)map.get("Value3"));
        HSSFCell cell21=row2.createCell(1);HSSFCell cell23=row2.createCell(3);HSSFCell cell25=row2.createCell(5);
        cell21.setCellStyle(style2);cell23.setCellStyle(style2);cell25.setCellStyle(style2);
        cell21.setCellValue((String)map.get("Value4"));cell23.setCellValue((String)map.get("Value5"));cell25.setCellValue((String)map.get("Value6"));
        HSSFRow row5=sheet.createRow(5);
        row5.setHeight((short) 0x200);
        HSSFCell cell50=row5.createCell(0);HSSFCell cell52=row5.createCell(2);HSSFCell cell54=row5.createCell(4);
        cell50.setCellStyle(style2);cell52.setCellStyle(style2);cell54.setCellStyle(style2);
        cell50.setCellValue("灌溉洞工作闸");cell52.setCellValue((String)map.get("Value7"));cell54.setCellValue((String)map.get("Value8"));
        cell50=row5.createCell(1);cell50.setCellStyle(style2);cell50=row5.createCell(3);cell50.setCellStyle(style2);cell50=row5.createCell(5);cell50.setCellStyle(style2);cell50=row5.createCell(6);cell50.setCellStyle(style2);
        HSSFRow row6=sheet.createRow(6);
        row6.setHeight((short) 0x200);
        HSSFCell cell60=row6.createCell(0);HSSFCell cell62=row6.createCell(2);HSSFCell cell64=row6.createCell(4);
        cell60.setCellStyle(style2);cell62.setCellStyle(style2);cell64.setCellStyle(style2);
        cell60.setCellValue("导流洞工作闸");cell62.setCellValue((String)map.get("Value9"));cell64.setCellValue((String)map.get("Value10"));
        cell60=row6.createCell(1);cell60.setCellStyle(style2);cell60=row6.createCell(3);cell60.setCellStyle(style2);cell60=row6.createCell(5);cell60.setCellStyle(style2);cell60=row6.createCell(6);cell60.setCellStyle(style2);
        List<Map<String,Object>> datalist=new ArrayList<Map<String,Object>>();
        Map<String,Object> dataobj0=new HashMap<String,Object>();
        dataobj0.put("v0","预警雨量站");dataobj0.put("v1","Value11");dataobj0.put("v2","－");dataobj0.put("v3","－");dataobj0.put("v4","－");dataobj0.put("v5","－");dataobj0.put("v6","－");
        datalist.add(dataobj0);
        Map<String,Object> dataobj1=new HashMap<String,Object>();
        dataobj1.put("v0","入库流量站");dataobj1.put("v1","Value12");dataobj1.put("v2","Value13");dataobj1.put("v3","Value14");dataobj1.put("v4","Value15");dataobj1.put("v5","－");dataobj1.put("v6","－");
        datalist.add(dataobj1);
        Map<String,Object> dataobj2=new HashMap<String,Object>();
        dataobj2.put("v0","坝上水位雨量站");dataobj2.put("v1","Value16");dataobj2.put("v2","Value17");dataobj2.put("v3","－");dataobj2.put("v4","－");dataobj2.put("v5","－");dataobj2.put("v6","－");
        datalist.add(dataobj2);
        Map<String,Object> dataobj3=new HashMap<String,Object>();
        dataobj3.put("v0","生态用水流量站");dataobj3.put("v1","－");dataobj3.put("v2","Value18");dataobj3.put("v3","－");dataobj3.put("v4","－");dataobj3.put("v5","Value19");dataobj3.put("v6","Value20");
        datalist.add(dataobj3);
        Map<String,Object> dataobj4=new HashMap<String,Object>();
        dataobj4.put("v0","灌溉用水流量站");dataobj4.put("v1","－");dataobj4.put("v2","Value21");dataobj4.put("v3","－");dataobj4.put("v4","－");dataobj4.put("v5","Value22");dataobj4.put("v6","Value23");
        datalist.add(dataobj4);
        Map<String,Object> dataobj5=new HashMap<String,Object>();
        dataobj5.put("v0","溢洪洞出库流量站");dataobj5.put("v1","－");dataobj5.put("v2","Value24");dataobj5.put("v3","－");dataobj5.put("v4","－");dataobj5.put("v5","Value25");dataobj5.put("v6","Value26");
        datalist.add(dataobj5);
        /*Map<String,Object> dataobj6=new HashMap<String,Object>();
        dataobj6.put("v0","灌溉洞");dataobj6.put("v1","－");dataobj6.put("v2","－");dataobj6.put("v3","－");dataobj6.put("v4","－");dataobj6.put("v5","Value27");dataobj6.put("v6","Value28");
        datalist.add(dataobj6);*/
        Map<String,Object> dataobj7=new HashMap<String,Object>();
        dataobj7.put("v0","导流洞出库流量站");dataobj7.put("v1","－");dataobj7.put("v2","Value31");dataobj7.put("v3","－");dataobj7.put("v4","－");dataobj7.put("v5","Value29");dataobj7.put("v6","Value30");
        datalist.add(dataobj7);
        for(int i=0;i<datalist.size();i++){
            row6=sheet.createRow(9+i);
            row6.setHeight((short) 0x200);
            Map<String,Object> dataobj=datalist.get(i);
            for(int j=0;j<7;j++){
                cell60=row6.createCell(j);
                cell60.setCellStyle(style2);
                String name=(String)dataobj.get("v"+j);
                if(name.indexOf("Value")>-1){
                    cell60.setCellValue((String)map.get(name));
                }else{
                    cell60.setCellValue(name);
                }
            }
        }
        try {
            String fileName = java.net.URLEncoder.encode("最新小时水情测报报表", "UTF-8");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            response.setContentType("application/x-download;charset=utf-8");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + fileName + ".xls");
            OutputStream os = response.getOutputStream();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            byte[] b = new byte[1024];
            while ((bais.read(b)) > 0) {
                os.write(b);
            }
            bais.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出率定站点信息
    @ResponseBody
    @RequestMapping(value="/exportlvsite",method = RequestMethod.GET)
    public void exportlvsite(HttpServletResponse response){
        String[] head0=new String[]{"站点编码","站点名称"};
        String[] cols=new String[]{"STCD","STNM"};
        Integer[] colwidth=new Integer[cols.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length;
        }
        String sheetName="站点编码对应表";
        List<StZqrlB> list=stStratingMService.selectStStratingMByCdAndNm();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            StZqrlB obj=list.get(i);
            cell=row.createCell(0);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval=obj.getStcd();
            CellUtil.setCellValue(cell, celval);
            if(celval.getBytes().length>colwidth[0]){
                colwidth[0]=celval.getBytes().length;
            }
            cell=row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            String celval2=obj.getStnm();
            CellUtil.setCellValue(cell, celval2);
            if(celval2.getBytes().length>colwidth[1]){
                colwidth[1]=celval2.getBytes().length;
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,sheetName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出渠道日表
    @ResponseBody
    @RequestMapping(value="/exportqdhday",method = RequestMethod.GET)
    public void exportqdhday(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> list=stAlarminfoService.selectQudaoDayExport(medataParam);
        String[] head0 = new String[] { "序号","站名", "平均水深(m)","平均流量(m³/s)","日累计水量(m³)","站点类型","所属渠道","地址"};
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文".getBytes().length;
        }
        String sheetName=medataParam.getBegintime()+"渠道水情日表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        style2.setBorderBottom(BorderStyle.THIN); //下边框
        style2.setBorderLeft(BorderStyle.THIN);//左边框
        style2.setBorderTop(BorderStyle.THIN);//上边框
        style2.setBorderRight(BorderStyle.THIN);//右边框
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("RowId").toString(),obj.get("STNM").toString(),CellUtil.Zvalue(obj.get("DZ")),CellUtil.Nullvalue(obj.get("DQ")),CellUtil.Nullvalue(obj.get("DWQ")),obj.get("STYPE").toString(),CellUtil.Nullvalue(obj.get("CanalName")),CellUtil.Nullvalue(obj.get("ADNM"))};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"渠道水情日表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出渠道月表
    @ResponseBody
    @RequestMapping(value="/exporthqdmonth",method = RequestMethod.GET)
    public void exporthqdmonth(HttpServletResponse response,MedataParam medataParam){
        String[] datestr=medataParam.getBegintime().split("-");
        medataParam.setYear(Integer.parseInt(datestr[0]));
        medataParam.setMonth(Integer.parseInt(datestr[1]));
        List<Map<String,Object>> list=stAlarminfoService.selectQudaoMonthExport(medataParam);
        String[] head0 = new String[] { "序号","站名", "平均水深(m)","平均流量(m³/s)","月累计水量(m³)","站点类型","所属渠道","地址"};
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文".getBytes().length;
        }
        String sheetName=medataParam.getBegintime()+"渠道水情月表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        style2.setBorderBottom(BorderStyle.THIN); //下边框
        style2.setBorderLeft(BorderStyle.THIN);//左边框
        style2.setBorderTop(BorderStyle.THIN);//上边框
        style2.setBorderRight(BorderStyle.THIN);//右边框
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("RowId").toString(),obj.get("STNM").toString(),CellUtil.Zvalue(obj.get("MA_Z")),CellUtil.Nullvalue(obj.get("MA_Q")),CellUtil.Nullvalue(obj.get("MWQ")),obj.get("STYLE").toString(),CellUtil.Nullvalue(obj.get("CANALNAME")),CellUtil.Nullvalue(obj.get("ADNM"))};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"渠道水情月表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出渠道小时表
    @ResponseBody
    @RequestMapping(value="/exporthqdhour",method = RequestMethod.GET)
    public void exporthqdhour(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> sitelist=stAlarminfoService.selectQudaoSiteName(medataParam);
        String[] head0 = new String[sitelist.size()*3+1];
        String[] head1 = new String[sitelist.size()*3+1];
        String[] headnum0 = new String[sitelist.size()+1];
        head0[0]="时间";head1[0]="时间";headnum0[0]="0,1,0,0";
        for(int i=0;i<sitelist.size();i++){
            Map<String,Object> map=sitelist.get(i);
            String stnm=map.get("stnm").toString();
            head0[i*3+1]=stnm;head1[i*3+2]="";head1[i*3+3]="";
            head1[i*3+1]="平均水深";head1[i*3+2]="平均流量";head1[i*3+3]="小时累计水量";
            headnum0[i+1]="0,0,"+(i*3+1)+","+(i*3+3)+"";
        }
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head1.length;i++){
            colwidth[i]=head1[i].getBytes().length+"文".getBytes().length;
        }
        String sheetName=medataParam.getBegintime().substring(0,13)+"至"+medataParam.getEndtime().substring(0,13)+"渠道水情小时表";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        row = sheet.createRow(1);
        for(int i=0;i<head1.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(head1[i]);
            cell.setCellStyle(style);
        }
        //动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
        }
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        style2.setBorderBottom(BorderStyle.THIN); //下边框
        style2.setBorderLeft(BorderStyle.THIN);//左边框
        style2.setBorderTop(BorderStyle.THIN);//上边框
        style2.setBorderRight(BorderStyle.THIN);//右边框
        List<Map<String,Object>> list=stAlarminfoService.selectQudaoHourExport(sitelist,medataParam);
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            Map<String,Object> obj=list.get(i);
            String[] array=new String[sitelist.size()*3+1];
            array[0]=obj.get("dt").toString();
            for(int j=0;j<sitelist.size();j++){
                String stcd=sitelist.get(j).get("stcd").toString();
                array[j*3+1]=CellUtil.Zvalue(obj.get(stcd+"平均水深"));
                array[j*3+2]=CellUtil.Nullvalue(obj.get(stcd+"平均流量"));
                array[j*3+3]=CellUtil.Nullvalue(obj.get(stcd+"小时累计水量"));
            }
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
        try {
            CellUtil.responseExcel(response,workbook,"渠道小时表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出渠道历史表
    @ResponseBody
    @RequestMapping(value="/exporthqdlishi",method = RequestMethod.GET)
    public void exporthqdlishi(HttpServletResponse response,MedataParam medataParam){
        List<Map<String,Object>> sitelist=stAlarminfoService.selectQudaoSiteName(medataParam);
        HSSFWorkbook workbook = new HSSFWorkbook();
        for(int i=0;i<sitelist.size();i++){
            Map<String,Object> obj=sitelist.get(i);
            String stnm=obj.get("stnm").toString();
            String stcd=obj.get("stcd").toString();
            MedataParam p=new MedataParam();
            p.setStcd(stcd);
            p.setStnm(stnm);
            p.setBegintime(medataParam.getBegintime());
            p.setEndtime(medataParam.getEndtime());
            exportExcels(workbook,i,p);
        }
        try {
            CellUtil.responseExcel(response,workbook,"渠道水情历史表");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //生成多sheet表格
    private void exportExcels(HSSFWorkbook workbook, int sheetNum, MedataParam medataParam){
        List<Map<String,Object>> list=stAlarminfoService.selectQudaoLsExport(medataParam);
        String head=medataParam.getBegintime().substring(0,13)+"至"+medataParam.getEndtime().substring(0,13);
        String[] head0 = new String[] { "时间", "水深(m)","流量(m³/s)"};
        Integer[] colwidth = new Integer[head0.length];
        for(int i=0;i<head0.length;i++){
            colwidth[i]=head0[i].getBytes().length+"文字".getBytes().length;
        }
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, medataParam.getStnm());// 创建一个表
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
        HSSFCellStyle style =CellUtil.getHearderStyle(workbook);
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        cell = row.createCell(0);
        cell.setCellValue(head);
        cell.setCellStyle(style);
        row = sheet.createRow(1);
        //表头列名
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style);
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        HSSFCellStyle style2 =CellUtil.getCellStyle(workbook);
        style2.setBorderBottom(BorderStyle.THIN); //下边框
        style2.setBorderLeft(BorderStyle.THIN);//左边框
        style2.setBorderTop(BorderStyle.THIN);//上边框
        style2.setBorderRight(BorderStyle.THIN);//右边框
        // 设置列值-内容
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 2);
            Map<String,Object> obj=list.get(i);
            String[] array={obj.get("tm").toString(),CellUtil.getZval(obj.get("z")),CellUtil.getQval(obj.get("q"))};
            for(int j=0;j<array.length;j++){
                cell=row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String celval=array[j]==null?"":array[j];
                CellUtil.setCellValue(cell, celval);
                if(celval.getBytes().length>colwidth[j]){
                    colwidth[j]=celval.getBytes().length;
                }
            }
        }
        //设置列宽和行高
        CellUtil.setWidthAndHeight(sheet,colwidth);
    }
}
