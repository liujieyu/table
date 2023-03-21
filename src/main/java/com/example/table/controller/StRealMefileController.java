package com.example.table.controller;


import com.example.table.entity.MedataParam;
import com.example.table.entity.StRealMedata;
import com.example.table.entity.StRealMefile;
import com.example.table.service.StRealMefileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
 * @since 2019-10-29
 */
@RestController
@RequestMapping("/strealmanage")
public class StRealMefileController {
    @Autowired
    StRealMefileService stRealMefileService;
    //率定数据管理查询
    @ResponseBody
    @RequestMapping(value="/getmefile",method = RequestMethod.GET)
    public Map<String,Object> getMefileInfo(MedataParam medataParam){
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
        Integer count=stRealMefileService.selectStRealMefileCount(medataParam);
        List<StRealMefile> list=stRealMefileService.selectStRealMefileByPage(medataParam);
        Map<String,Object> map=new HashMap<>();
        map.put("total",count);
        map.put("rows",list);
        return map;
    }
    //判断此文件是否已存在
    @ResponseBody
    @RequestMapping(value="/existfile",method = RequestMethod.GET)
    public String existFile(MedataParam medataParam){
        Integer i=stRealMefileService.checkMefileExist(medataParam);
        if(i!=null && i==1){
            return "1";
        }else{
            return "0";
        }
    }
    //文件上传和导入
    @RequestMapping("uploadfile")
    @ResponseBody
    public String uploadfile (@RequestParam("file") MultipartFile file,MedataParam medataParam) {
        List<MedataParam> list=new ArrayList<>();
        String mecode=stRealMefileService.selectMaxMecode(medataParam);
        String erro=stRealMefileService.readExcel(file,list,mecode);
        if(erro.equals("ok") && list.size()>0){
            medataParam.setMeCode(mecode);
            medataParam.setAudinting(1);
            medataParam.setFileName(file.getOriginalFilename());
            stRealMefileService.insertStRealMefile(medataParam);
            stRealMefileService.insertStRealMedata(list);
        }else{
            if(erro.equals("ok")){
                return "文件中无数据！";
            }else{
                return erro;
            }
        }
        return "ok";
    }
    //上传文件详情(上传文件详情和测量数据详情)
    @ResponseBody
    @RequestMapping(value="/filedetail",method = RequestMethod.GET)
    public Map<String,Object> filedetail(MedataParam medataParam){
        StRealMefile mefile=stRealMefileService.selectStRealMefileByMecode(medataParam);
        Integer count=stRealMefileService.selectStRealMedataCount(medataParam);
        List<StRealMedata> list=stRealMefileService.selectStRealMedataDetail(medataParam);
        Map<String,Object> map=new HashMap<>();
        map.put("info",mefile);
        map.put("total",count);
        map.put("rows",list);
        return map;
    }
    //审核
    @ResponseBody
    @RequestMapping(value="/auditing",method = RequestMethod.GET)
    public String auditing(MedataParam medataParam){
        stRealMefileService.updateStRealMefile(medataParam);
        //审核通过
        if(medataParam.getAudinting()==2){
            stRealMefileService.insertLdresult(medataParam);
        }
        return "ok";
    }
}
