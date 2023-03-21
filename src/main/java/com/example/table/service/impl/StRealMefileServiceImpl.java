package com.example.table.service.impl;

import com.example.table.entity.MedataParam;
import com.example.table.entity.StRealMedata;
import com.example.table.entity.StRealMefile;
import com.example.table.mapper.StRealMefileMapper;
import com.example.table.service.StRealMefileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.table.util.CellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2019-10-29
 */
@Service
public class StRealMefileServiceImpl extends ServiceImpl<StRealMefileMapper, StRealMefile> implements StRealMefileService {
    @Autowired
    StRealMefileMapper stRealMefileMapper;
    //率定数据上传信息总记录数
    public Integer selectStRealMefileCount(MedataParam medataParam){
        return stRealMefileMapper.selectStRealMefileCount(medataParam);
    }
    //率定数据上传信息分页查询
    public List<StRealMefile> selectStRealMefileByPage(MedataParam medataParam){
        return stRealMefileMapper.selectStRealMefileByPage(medataParam);
    }
    //根据测量编码查询率定数据上传信息
    public StRealMefile selectStRealMefileByMecode(MedataParam medataParam){
        List<StRealMefile> list =stRealMefileMapper.selectStRealMefileByMecode(medataParam);
        if(list!=null && list.size()>0){
            return list.get(0);
        }else{
            return new StRealMefile();
        }
    }
    //率定数据上传信息详情总记录数
    public Integer selectStRealMedataCount(MedataParam medataParam){
        return stRealMefileMapper.selectStRealMedataCount(medataParam);
    }
    //率定数据上传信息详情
    public List<StRealMedata> selectStRealMedataDetail(MedataParam medataParam){
        return stRealMefileMapper.selectStRealMedataDetail(medataParam);
    }
    //新增率定数据上传信息
    public void insertStRealMefile(MedataParam medataParam){
        stRealMefileMapper.insertStRealMefile(medataParam);
    }
    //修改率定数据上传信息
    public void updateStRealMefile(MedataParam medataParam){
        stRealMefileMapper.updateStRealMefile(medataParam);
    }
    //新增率定数据上传信息详情
    public void insertStRealMedata(List<MedataParam> list){
        stRealMefileMapper.insertStRealMedata(list);
    }
    //判断文件是否存在
    public Integer checkMefileExist(MedataParam medataParam){
        return stRealMefileMapper.checkMefileExist(medataParam);
    }
    //插入到率定结果表中
    public void insertLdresult(MedataParam medataParam){
        stRealMefileMapper.insertLdresult(medataParam);
    }
    //获取数据库最大测量编号
    public String selectMaxMecode(MedataParam medataParam){
        String[] tmarray=medataParam.getTm().split("-");
        String meCode=tmarray[0]+tmarray[1]+tmarray[2];
        String maxcode=stRealMefileMapper.selectMaxMecode(meCode);
        if(maxcode==null || maxcode==""){
            return meCode+"0001";
        }else{
            String start=maxcode.substring(0,8);
            String end=maxcode.substring(8);
            int bh=Integer.parseInt(end)+1;
            String substr = String.format("%04d", bh);
            String value=start+substr;
            return value;
        }
    }
    //导入Excel
    public String  readExcel(MultipartFile file, List<MedataParam> dataList,String meCode){
        String erro="";
        try {
            //获得Workbook工作薄对象
            Workbook workbook = CellUtil.getWorkBook(file);
            //dataList 创建返回对象，把每行中的值作为一个对象，所有行作为一个集合返回
            if(workbook != null){
                for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                    String sheetName = workbook.getSheetAt(sheetNum).getSheetName();
                    // 根据name判断sheet类型
                    if (sheetName.indexOf("率定")>-1) {

                        Sheet sheet = workbook.getSheetAt(sheetNum);
                        if(sheet == null){
                            continue;
                        }
                        //获得当前sheet的开始行
                        int firstRowNum  = sheet.getFirstRowNum();
                        //获得当前sheet的结束行
                        int lastRowNum = sheet.getLastRowNum();
                        //循环除了第一行的所有行
                        // 代码集数据
                        // 获取列标题
                        Row row0 = sheet.getRow(firstRowNum);
                        // 如果列名不对 终止导入  暴露错误
                        for(int cellNum = 0; cellNum <= 5;cellNum++){
                            Cell cell = row0.getCell(cellNum);
                            if (!"站点名称".equals(CellUtil.getCellValue(cell))&&cellNum==0) {
                                erro=sheetName +"第1列标题为站点名称";
                                return erro;
                            }else
                            if (!"站点编码".equals(CellUtil.getCellValue(cell))&&cellNum==1) {
                                erro=sheetName +"第2列标题为站点编码";
                                return erro;
                            }else
                            if (!"渠道编码".equals(CellUtil.getCellValue(cell))&&cellNum==2) {
                                erro=sheetName +"第3列标题为渠道编码";
                                return erro;
                            }else
                            if (!"水深".equals(CellUtil.getCellValue(cell))&&cellNum==3) {
                                erro="第4列标题为水深";
                                return erro;
                            }else
                            if (!"流量".equals(CellUtil.getCellValue(cell))&&cellNum==4) {
                                erro=sheetName +"第5列标题为流量";
                                return erro;
                            }else
                            if (!"测量日期".equals(CellUtil.getCellValue(cell))&&cellNum==5) {
                                erro=sheetName +"第6列标题为测量日期";
                                return erro;
                            }

                        }

                        for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                            //获得当前行
                            Row row = sheet.getRow(rowNum);
                            if(row == null){
                                continue;
                            }

                            if (row != null ){
                                //获得当前行的开始列
                                int firstCellNum = row.getFirstCellNum();
                                //获得当前行的列数
                                int lastCellNum = row.getPhysicalNumberOfCells();
                                String[] cells = new String[row.getPhysicalNumberOfCells()];
                                //循环当前行
                                MedataParam data=new MedataParam();
                                for(int cellNum = firstCellNum; cellNum <= lastCellNum;cellNum++){
                                    Cell cell = row.getCell(cellNum);
                                    //站点名称
                                    String valus=CellUtil.getCellValue(cell);
                                    if (cellNum==0){
                                        if (valus!=null && !valus.replaceAll(" ","").equals("")){
                                            data.setStnm(valus);
                                        }else{
                                            erro=sheetName + "第"+rowNum+1+"行第1列站点名称不能为空!";
                                            return erro;
                                        }
                                    }else if (cellNum==1){
                                        // 站点编码
                                        if (valus!=null && !valus.replace(" ","").equals("")){
                                            data.setStcd(valus);
                                        }else{
                                            erro=sheetName + "第"+rowNum+1+"行第2列站点编码不能为空!";
                                            return erro;
                                        }

                                    }else if (cellNum==2){
                                        // 渠道编码
                                        data.setCanalCode(valus);
                                    }else if (cellNum==3){
                                        // 水深
                                        if (valus!=null && !valus.replaceAll(" ","").equals("")){
                                            try
                                            {
                                                Double dCheckValue = Double.parseDouble(valus);
                                                if (dCheckValue instanceof Double == false)
                                                {
                                                    erro=sheetName + "第"+rowNum+1+"行第4列水深为数值!";
                                                    return erro;
                                                }
                                                data.setZ(dCheckValue);
                                            }
                                            catch(NumberFormatException e)
                                            {
                                                erro=sheetName + "第"+rowNum+1+"行第4列水深为数值!";
                                                return erro;
                                            }
                                        }else{
                                            erro=sheetName + "第"+rowNum+1+"行第4列水深不能为空!";
                                            return erro;
                                        }
                                    }else if (cellNum==4){
                                        //流量
                                        if (valus!=null && !valus.replaceAll(" ","").equals("")){
                                            try
                                            {
                                                Double dCheckValue = Double.parseDouble(valus);
                                                if (dCheckValue instanceof Double == false)
                                                {
                                                    erro=sheetName + "第"+rowNum+1+"行第5列流量为数值!";
                                                    return erro;
                                                }
                                                data.setQ(dCheckValue);
                                            }
                                            catch(NumberFormatException e)
                                            {
                                                erro=sheetName + "第"+rowNum+1+"行第5列流量为数值!";
                                                return erro;
                                            }
                                        }else{
                                            erro=sheetName + "第"+rowNum+1+"行第5列流量不能为空!";
                                            return erro;
                                        }
                                    }else if (cellNum==5){
                                        //测量日期
                                        if (valus!=null && !valus.replaceAll(" ","").equals("")){
                                            data.setMeTm(valus);
                                        }else{
                                            erro=sheetName + "第"+rowNum+1+"行第6列测量日期不能为空!";
                                            return erro;
                                        }
                                    }

                                }
                                // 添加到集合中
                                data.setMeCode(meCode);
                                dataList.add(data);


                            }else{
                                erro=sheetName + "无数据!";
                                return erro;
                            }

                        }
                        workbook.close();
                        erro="ok";
                    }
                }

            }

        }catch (IOException e){
            erro="导入失败！";
        }
        return erro;
    }
}
