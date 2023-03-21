package com.example.table.controller;


import com.example.table.entity.*;
import com.example.table.service.StStratingMService;
import com.example.table.util.CellUtil;
import com.example.table.util.MathUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liujieyu
 * @since 2019-10-25
 */
@RestController
@RequestMapping("/ldresult")
public class StStratingMController {
    @Autowired
    StStratingMService stStratingMService;
    //查询所有站点
    @ResponseBody
    @RequestMapping(value="/typesite",method = RequestMethod.GET)
    public List<StStratingM> gettypesite(LvDingParam param){
        return stStratingMService.selectStStratingMAll(param);
    }
    //根据stcd查询最大水深和最大流量
    @ResponseBody
    @RequestMapping(value="/maxinfo",method = RequestMethod.GET)
    public StStsectionStation getMaxinfo(LvDingParam param){
        if(param.getMetype()==1){
            return stStratingMService.selectBzdmMaxByStcd(param);
        }else if(param.getMetype()==2){
            return stStratingMService.selectBxecMaxByStcd(param);
        }else if(param.getMetype()==3){
            return stStratingMService.selectSzMaxByStcd(param);
        }else{
            return new StStsectionStation();
        }
    }
    //查询列表
    @ResponseBody
    @RequestMapping(value="/eqrllist",method = RequestMethod.GET)
    public Map<String,Object> geteqrllist(LvDingParam param){
        List<StZqrlB> currentlist= stStratingMService.selectSteqrlBList(param);
        param.setYear(param.getYear()-1);
        List<StZqrlB> lastlist= stStratingMService.selectSteqrlBList(param);
        Map<String,Object> map=new HashMap<>();
        map.put("curlist",currentlist);
        map.put("lastlist",lastlist);
        return map;
    }
    //新增
    @RequestMapping(value="/addeqrl",method = RequestMethod.POST)
    @ResponseBody
    public String addeqrl(@RequestBody LvDingParam param){
        stStratingMService.insertSteqrlB(param);
        return "ok";
    }
    //修改
    @RequestMapping(value="/modifyeqrl",method = RequestMethod.POST)
    @ResponseBody
    public String modifyeqrl(@RequestBody LvDingParam param){
        stStratingMService.updateSteqrlB(param);
        return "ok";
    }
    //删除
    @RequestMapping(value="/deleteeqrl",method = RequestMethod.GET)
    @ResponseBody
    public String deleteeqrl(LvDingParam param){
        stStratingMService.deletesTeqrlB(param);
        return "ok";
    }
    //查询详情
    @ResponseBody
    @RequestMapping(value="/eqrlobj",method = RequestMethod.GET)
    public StZqrlB geteqrlobj(LvDingParam param){
        return stStratingMService.selectTeqrlBById(param);
    }
    //计算流量
    @RequestMapping(value="/getq",method = RequestMethod.POST)
    @ResponseBody
    public Double getQCaculate(@RequestBody LvDingParam param){
        Double q=0.00;
        if(param.getMetype()==1){
            if(param.getB()==null){
                param.setB(0.0);
            }
            if(param.getM()==null){
                param.setM(0.0);
            }
            if(param.getN()==null){
                param.setN(0.0);
            }
            if(param.getI()==null){
                param.setI(0.0);
            }
            if(param.getTypes()==1){
                param.setM(0.0);
            }else{

            }
            q= MathUtil.standardstsection_cal(param.getB(),param.getM(),param.getN(),param.getI(),param.getZ());
        }else if(param.getMetype()==2){
            Map<String,Object> map=stStratingMService.selectParshallflume(param);
            BigDecimal c=(BigDecimal)map.get("C");
            BigDecimal n1=(BigDecimal)map.get("N1");
            q=MathUtil.parshall_cal(param.getZ(),c.doubleValue(),n1.doubleValue());
        }else if(param.getMetype()==3){
            q=MathUtil.gate_cal(param.getGthlntwd(),param.getHi(),param.getFz(),param.getBz());
        }
        return q;
    }
    //修改糙率
    @RequestMapping(value="/modifycl",method = RequestMethod.GET)
    @ResponseBody
    public String modifycl(LvDingParam param){
       int sign = stStratingMService.updateCl(param);
       if(sign>0){
           return "ok";
       }else{
           return "no";
       }

    }
    //判断率定结果是否存在
    @RequestMapping(value="/existresult",method = RequestMethod.GET)
    @ResponseBody
    public Integer existresult(LvDingParam param){
       return stStratingMService.selectResultByExist(param);
    }
    //文件上传 批量导入率定结果数据
    @RequestMapping("upload")
    @ResponseBody
    public Map<String,Object> upload (@RequestParam("file") MultipartFile file) {
        Map<String,Object> mapobj=new HashMap<>();
        //文件保存成功或失败标识
        int sign=0;
        //返回信息
        String info="";
        //sheet工作表序号
        int sheetnum=0;
        //判断文件后缀名
        boolean isxls=CellUtil.checkFile(file);
        if(!isxls){
            info="请上传Excel文件！";
            mapobj.put("sign",sign);
            mapobj.put("info",info);
            return mapobj;
        }
        //获得Workbook工作薄对象
        Workbook workbook = CellUtil.getWorkBook(file);
        if(workbook==null){
            info="Excel文件为空！";
            mapobj.put("sign",sign);
            mapobj.put("info",info);
            return mapobj;
        }
        //循环工作表
        Sheet sheet = null;
        for(int s=0;s<workbook.getNumberOfSheets();s++){
            sheet=workbook.getSheetAt(s);
                String valid="";
                //获取工作表名称
                String sheetname=sheet.getSheetName();
                valid=sheetname+"：";
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                Row row = sheet.getRow(0);
                Cell cell1 = row.getCell(1);
                String yearval=CellUtil.getCellValue(cell1);
                if(yearval==""){
                    valid+="年份不能为空！";
                    try {
                        Integer.parseInt(yearval);
                    }catch (NumberFormatException exception){
                        valid+="年份为数值型！";
                    }
                }
                Cell cell2 = row.getCell(4);
                String stcdval=CellUtil.getCellValue(cell2);
                if(stcdval==""){
                    valid+="站点编码不能为空！";
                }
                Cell cell3 = row.getCell(7);
                String stnmval=CellUtil.getCellValue(cell3);
                if(stnmval==""){
                    valid+="站点名称不能为空！";
                }
                if(valid.equals(sheetname+"：")){
                    LvDingParam param=new LvDingParam();
                    param.setStcd(stcdval);
                    param.setStnm(stnmval);
                    param.setYear(Integer.parseInt(yearval));
                    Integer ishas=stStratingMService.selectStcdStStratingMByStnm(param);
                    if(ishas.intValue()==0){
                        valid+="站点编号或者名称错误！";
                    }
                    Integer exist=stStratingMService.selectResultByStcdAndYear(param);
                    if(exist.intValue()>0){
                        valid+=param.getYear()+"年此站点率定结果数据已存在！";
                    }
                }
                row = sheet.getRow(1);
                //获得当前行的开始列
                int firstCellNum = row.getFirstCellNum();
                //获得当前行的列数
                int lastCellNum = row.getLastCellNum();
                if(firstCellNum>0 || lastCellNum>11){
                    valid+="率定数据格式不对！";
                }
                if(!valid.equals(sheetname+"：")){
                    info+=valid;
                }
        }

        if(info!=""){
            mapobj.put("sign",sign);
            mapobj.put("info",info);
            return mapobj;
        }
        for(int s=0;s<workbook.getNumberOfSheets();s++){
            sheet=workbook.getSheetAt(s);
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                Row row0 = sheet.getRow(0);
                Cell cell1 = row0.getCell(1);
                String yearval=CellUtil.getCellValue(cell1);
                Cell cell2 = row0.getCell(4);
                String stcdval=CellUtil.getCellValue(cell2);
                Cell cell3 = row0.getCell(7);
                String stnmval=CellUtil.getCellValue(cell3);
                List<StZqrlB> ldrusults=new ArrayList<StZqrlB>();
                for(int i=2;i<=lastRowNum;i++){
                    Row row=sheet.getRow(i);
                    double z0=Double.parseDouble(CellUtil.getCellValue(row.getCell(0)));
                    //获得当前行的列数
                    int lastCellNum = row.getLastCellNum();
                    for(int j=1;j<lastCellNum;j++){
                        String valstr=CellUtil.getCellValue(row.getCell(j));
                        if(valstr==""){
                            continue;
                        }
                        StZqrlB obj=new StZqrlB();
                        obj.setStcd(stcdval);obj.setStnm(stnmval);obj.setYr(Integer.parseInt(yearval));
                        obj.setQ(Double.parseDouble(valstr));
                        double h=z0+(double)(j-1)/100;
                        obj.setZ(h);
                        ldrusults.add(obj);
                    }
                }
                if(ldrusults.size()>0){
                    stStratingMService.insertSteqrlBObjs(ldrusults);
                }
        }
        sign=1;
        info="上传成功！";
        mapobj.put("sign",sign);
        mapobj.put("info",info);
        return mapobj;
    }
}
