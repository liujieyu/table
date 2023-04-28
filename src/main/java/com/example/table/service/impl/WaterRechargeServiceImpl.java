package com.example.table.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.table.mapper.WaterFarmUsersMapper;
import com.example.table.mapper.WaterMeterMapper;
import com.example.table.pojo.*;
import com.example.table.mapper.WaterRechargeMapper;
import com.example.table.service.WaterRechargeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  充值读表服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2023-04-18
 */
@Service
public class WaterRechargeServiceImpl extends ServiceImpl<WaterRechargeMapper, WaterRecharge> implements WaterRechargeService {
    @Autowired
    WaterRechargeMapper waterRechargeMapper;
    @Autowired
    WaterMeterMapper waterMeterMapper;
    @Autowired
    WaterFarmUsersMapper waterFarmUsersMapper;

    //水费充值
    public Map<String,Object> insertWaterRechargeInfo(WaterRecharge pojo){
        Date now= new Date();
        int nowyear=now.getYear();
        int nowmonth=now.getMonth()+1;
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        pojo.setCreatetime(sdf.format(now));
        QueryWrapper<WaterMeter> queryWrapper=new QueryWrapper<>();
        queryWrapper.inSql("READTIME","select max(READTIME) from WATER_METER where READTIME<'"+pojo.getCreatetime()+"' and FARMCODE='"+pojo.getFarmcode()+"'")
                    .eq("FARMCODE",pojo.getFarmcode());
        //最近一次读表信息
        WaterMeter watermeter=waterMeterMapper.selectList(queryWrapper).get(0);
        WaterParam param=new WaterParam();
        param.setStcd(pojo.getFarmcode());
        param.setYear(Integer.parseInt(pojo.getCreatetime().substring(0,4)));
        param.setShowsign(pojo.getSyssign());
        //农户用户执行水价标准信息
        FarmStandardShow standobj=waterRechargeMapper.selectFarmStandardInfo(param).get(0);
        //本年度充值统计信息
        List<WaterRecharge> listsum=waterRechargeMapper.selectFarmBuyWaterTatalInfo(param);
        int basewater=standobj.getBasewater();

        //第一次充值
        if(watermeter.getWaternum()==0){
            int buywater=(int)Math.floor(pojo.getAmount()/standobj.getBaseprice());
            pojo.setBuywater(buywater);
            pojo.setBasewater(buywater);
            pojo.setBuyfirst(0);
            pojo.setBuysecond(0);
            pojo.setBuythird(0);
            pojo.setLastsurplus(0);
            pojo.setRemainwater(buywater);
        }else{
                //和水表相同年份且之前有充值情况
                if(!(listsum==null || listsum.get(0)==null)){
                    WaterRecharge sumobj=listsum.get(0);
                    //三级水量不为0
                    if(sumobj.getBuythird()>0){
                        int buywater=(int)Math.floor(pojo.getAmount()/standobj.getThrprice());
                        pojo.setBuywater(buywater);
                        pojo.setBasewater(0);
                        pojo.setBuyfirst(0);
                        pojo.setBuysecond(0);
                        pojo.setBuythird(buywater);
                    }//二级水量不为0
                    else if(sumobj.getBuysecond()>0){
                        double twolimamount=standobj.getTowprice()*(standobj.getTwouplim()-standobj.getOneuplim()-sumobj.getBuysecond());
                        //本次金额超过二级水量上限
                        if(pojo.getAmount()>twolimamount){
                            double thramount=pojo.getAmount()-twolimamount;
                            int thrwater=(int)Math.floor(thramount/standobj.getThrprice());
                            int totalwater=standobj.getTwouplim()-sumobj.getBuysecond()+thrwater;
                            pojo.setBuywater(totalwater);
                            pojo.setBasewater(0);
                            pojo.setBuyfirst(0);
                            pojo.setBuysecond(standobj.getTwouplim()-sumobj.getBuysecond());
                            pojo.setBuythird(thrwater);
                        }else{
                            int buywater=(int)Math.floor(pojo.getAmount()/standobj.getTowprice());
                            pojo.setBuywater(buywater);
                            pojo.setBasewater(0);
                            pojo.setBuyfirst(0);
                            pojo.setBuysecond(buywater);
                            pojo.setBuythird(0);
                        }
                    }//一级水量不为0
                    else if(sumobj.getBuyfirst()>0){
                            double onelimamount=standobj.getOneprice()*(standobj.getOneuplim()-sumobj.getBuyfirst());
                            //本次金额超过一级水量上限
                            if(pojo.getAmount()>onelimamount){
                                double secamount=pojo.getAmount()-onelimamount;
                                int secwater=(int)Math.floor(secamount/standobj.getTowprice());
                                int totalwater=standobj.getOneuplim()-sumobj.getBuyfirst()+secwater;
                                pojo.setBuywater(totalwater);
                                pojo.setBasewater(0);
                                pojo.setBuyfirst(standobj.getOneuplim()-sumobj.getBuyfirst());
                                pojo.setBuysecond(secwater);
                                pojo.setBuythird(0);
                            }else{
                                int buywater=(int)Math.floor(pojo.getAmount()/standobj.getOneprice());
                                pojo.setBuywater(buywater);
                                pojo.setBasewater(0);
                                pojo.setBuyfirst(buywater);
                                pojo.setBuysecond(0);
                                pojo.setBuythird(0);
                            }
                    }//未超水
                    else{
                        double upbamount=standobj.getBaseprice()*(standobj.getBasewater()-sumobj.getBuywater());
                        //本次金额超过基础水量上限
                        if(pojo.getAmount()>upbamount){
                            double friamount=pojo.getAmount()-upbamount;
                            int friwater=(int)Math.floor(friamount/standobj.getOneprice());
                            int totalwater=standobj.getBasewater()-sumobj.getBuywater()+friwater;
                            pojo.setBuywater(totalwater);
                            pojo.setBasewater(standobj.getBasewater()-sumobj.getBuywater());
                            pojo.setBuyfirst(friwater);
                            pojo.setBuysecond(0);
                            pojo.setBuythird(0);
                        }else{
                            int buywater=(int)Math.floor(pojo.getAmount()/standobj.getBaseprice());
                            pojo.setBuywater(buywater);
                            pojo.setBasewater(buywater);
                            pojo.setBuyfirst(0);
                            pojo.setBuysecond(0);
                            pojo.setBuythird(0);
                        }
                    }
                }
                else{
                    //该年第一次缴费（去年水费没有用完）
                    if(watermeter.getAvailable()>=0) {
                        int buywater = (int) Math.floor(pojo.getAmount() / standobj.getBaseprice());
                        pojo.setBuywater(buywater);
                        pojo.setBasewater(buywater);
                        pojo.setBuyfirst(0);
                        pojo.setBuysecond(0);
                        pojo.setBuythird(0);
                    }else{
                        //该年第一次缴费 将去年的欠费补上
                            int lastwater=Math.abs(watermeter.getSurplus());
                            int basew=0,firstw=0,secondw=0,thirdw=0;
                            if(watermeter.getYieldthird()>0){
                                if(lastwater>watermeter.getYieldthird()){
                                    thirdw=watermeter.getYieldthird();
                                    secondw=lastwater-watermeter.getYieldthird();
                                }else{
                                    thirdw=lastwater;
                                }
                            }else if(watermeter.getYieldsecond()>0){
                                if(lastwater>watermeter.getYieldsecond()){
                                    secondw=watermeter.getYieldsecond();
                                    firstw=lastwater-watermeter.getYieldsecond();
                                }else{
                                    secondw=lastwater;
                                }
                            }else if(watermeter.getYieldfirst()>0){
                                if(lastwater>watermeter.getYieldfirst()){
                                    firstw=watermeter.getYieldfirst();
                                    basew=lastwater-watermeter.getYieldfirst();
                                }else{
                                    firstw=lastwater;
                                }
                            }else{
                                basew=lastwater;
                            }
                            int buywater = (int) Math.floor((pojo.getAmount()+watermeter.getAvailable()) / standobj.getBaseprice());
                            pojo.setBuywater(buywater+lastwater);
                            pojo.setBasewater(buywater+basew);
                            pojo.setBuyfirst(firstw);
                            pojo.setBuysecond(secondw);
                            pojo.setBuythird(thirdw);
                }

            }
            pojo.setLastsurplus(watermeter.getSurplus());
            pojo.setRemainwater(pojo.getBuywater()+pojo.getLastsurplus());
        }
        //新增充值信息
        waterRechargeMapper.insert(pojo);
        //更新读表信息的缴费时间
        WaterMeter upobj=new WaterMeter();
        upobj.setId(watermeter.getId());
        upobj.setPaytime(pojo.getCreatetime());
        waterMeterMapper.updateById(upobj);
        Map<String,Object> map=new HashMap<>();
        map.put("rechargeinfo",pojo);
        map.put("farminfo",standobj);
        return map;
    }
    //抄表
    public Map<String,Object> insertWaterMeterInfo(WaterMeter pojo){
        Date now= new Date();
        int nowyear=now.getYear()+1900;
        int nowmonth=now.getMonth()+1;
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        pojo.setReadtime(sdf.format(now));
//        int nowyear=Integer.parseInt(pojo.getReadtime().substring(0,4));
//        int nowmonth=Integer.parseInt(pojo.getReadtime().substring(5,7));
        QueryWrapper<WaterRecharge> queryWrapper=new QueryWrapper<>();
        queryWrapper.inSql("CREATETIME","select max(CREATETIME) from WATER_RECHARGE where CREATETIME<'"+pojo.getReadtime()+"' and FARMCODE='"+pojo.getFarmcode()+"'")
                .eq("FARMCODE",pojo.getFarmcode());
        //最近一次充值信息
        List<WaterRecharge> rechargelist=waterRechargeMapper.selectList(queryWrapper);
        WaterParam param=new WaterParam();
        param.setStcd(pojo.getFarmcode());
        param.setYear(Integer.parseInt(pojo.getReadtime().substring(0,4)));
        param.setShowsign(pojo.getSyssign());
        //农户用户执行水价标准信息
        FarmStandardShow standobj=waterRechargeMapper.selectFarmStandardInfo(param).get(0);
        //上期读表信息
        QueryWrapper<WaterMeter> queryMeter=new QueryWrapper<>();
        queryMeter.inSql("READTIME","select max(READTIME) from WATER_METER where READTIME<'"+pojo.getReadtime()+"' and FARMCODE='"+pojo.getFarmcode()+"'")
                .eq("FARMCODE",pojo.getFarmcode());
        //最近一次读表信息
        WaterMeter lastmeter=waterMeterMapper.selectOne(queryMeter);
        pojo.setLastnum(lastmeter.getWaternum());
        pojo.setWateryield(pojo.getWaternum()-lastmeter.getWaternum());
        int tjyear=nowyear;
        if(nowmonth==1){
            tjyear=nowyear-1;
        }
        QueryWrapper<WaterMeter> querysum=new QueryWrapper<>();
        querysum.select("SUM(WATERYIELD) as WATERYIELD","SUM(YIELDBASE) as YIELDBASE","SUM(YIELDFIRST) as YIELDFIRST","SUM(YIELDSECOND) as YIELDSECOND","SUM(YIELDTHIRD) as YIELDTHIRD")
                .eq("FARMCODE",pojo.getFarmcode()).apply("DATEPART(year,READTIME)={0}",tjyear).apply("DATEPART(MONTH,READTIME)>1");
        //获取该年份抄表累计水量值
        WaterMeter summeter=waterMeterMapper.selectOne(querysum);
        //未充值情况下查表
        if(rechargelist==null || rechargelist.size()==0 || rechargelist.get(0)==null){
            pojo.setYieldbase(pojo.getWateryield());
            pojo.setYieldfirst(0);
            pojo.setYieldsecond(0);
            pojo.setYieldthird(0);
            pojo.setWaterrate(standobj.getBaseprice()*pojo.getWateryield());
            pojo.setAvailable(-pojo.getWaterrate());
            pojo.setSurplus(-pojo.getWateryield());
        }else{
            WaterRecharge recharge=rechargelist.get(0);
            //第一次查表
            if(summeter==null || summeter.getWateryield()==0){
                if(standobj.getBasewater()<pojo.getWateryield()){
                    pojo.setYieldbase(standobj.getBasewater());
                    pojo.setYieldfirst(pojo.getWateryield()-standobj.getBasewater());
                    pojo.setWaterrate(standobj.getBaseprice()*pojo.getWateryield()+standobj.getOneprice()*pojo.getYieldfirst());
                }else{
                    pojo.setYieldbase(pojo.getWateryield());
                    pojo.setYieldfirst(0);
                    pojo.setWaterrate(standobj.getBaseprice()*pojo.getWateryield());
                }
                pojo.setYieldsecond(0);
                pojo.setYieldthird(0);
                pojo.setAvailable(recharge.getAmount()-pojo.getWaterrate()+lastmeter.getAvailable());
                pojo.setSurplus(recharge.getBuywater()-pojo.getWateryield()+lastmeter.getSurplus());
            }else{
                if(summeter.getYieldthird()>0){
                    pojo.setYieldbase(0);
                    pojo.setYieldfirst(0);
                    pojo.setYieldsecond(0);
                    pojo.setYieldthird(pojo.getWateryield());
                    pojo.setWaterrate(standobj.getThrprice()*pojo.getWateryield());
                }else if(summeter.getYieldsecond()>0){
                    pojo.setYieldbase(0);
                    pojo.setYieldfirst(0);
                    if((standobj.getTwouplim()-standobj.getOneuplim())<pojo.getWateryield()+summeter.getYieldsecond()){
                        pojo.setYieldsecond(standobj.getTwouplim()-standobj.getOneuplim()-summeter.getYieldsecond());
                        pojo.setYieldthird(pojo.getWateryield()-pojo.getYieldsecond());
                        pojo.setWaterrate(standobj.getThrprice()*pojo.getYieldthird()+standobj.getTowprice()*pojo.getYieldsecond());
                    }else{
                        pojo.setYieldsecond(pojo.getWateryield());
                        pojo.setYieldthird(0);
                        pojo.setWaterrate(standobj.getTowprice()*pojo.getYieldsecond());
                    }
                }else if(summeter.getYieldfirst()>0){
                    pojo.setYieldbase(0);
                    pojo.setYieldthird(0);
                    if(standobj.getOneuplim()<pojo.getWateryield()+summeter.getYieldfirst()){
                        pojo.setYieldfirst(standobj.getOneuplim()-summeter.getYieldfirst());
                        pojo.setYieldsecond(pojo.getWateryield()-pojo.getYieldfirst());
                        pojo.setWaterrate(standobj.getTowprice()*pojo.getYieldsecond()+standobj.getOneprice()*pojo.getYieldfirst());
                    }else{
                        pojo.setYieldfirst(pojo.getWateryield());
                        pojo.setYieldsecond(0);
                        pojo.setWaterrate(standobj.getOneprice()*pojo.getYieldfirst());
                    }
                }else{
                    pojo.setYieldsecond(0);
                    pojo.setYieldthird(0);
                    if(standobj.getBasewater()<pojo.getWateryield()+summeter.getYieldbase()){
                        pojo.setYieldbase(standobj.getBasewater()-summeter.getYieldbase());
                        pojo.setYieldfirst(pojo.getWateryield()-pojo.getYieldbase());
                        pojo.setWaterrate(standobj.getOneprice()*pojo.getYieldfirst()+standobj.getBaseprice()*pojo.getYieldbase());
                    }else{
                        pojo.setYieldbase(pojo.getWateryield());
                        pojo.setYieldfirst(0);
                        pojo.setWaterrate(standobj.getBaseprice()*pojo.getYieldbase());
                    }
                }
                if(lastmeter.getPaytime()!=null && lastmeter.getPaytime().equals(recharge.getCreatetime())){
                    pojo.setAvailable(recharge.getAmount()-pojo.getWaterrate()+lastmeter.getAvailable());
                    pojo.setSurplus(recharge.getBuywater()-pojo.getWateryield()+lastmeter.getSurplus());
                }else{
                    pojo.setAvailable(lastmeter.getAvailable()-pojo.getWaterrate());
                    pojo.setSurplus(lastmeter.getSurplus()-pojo.getWateryield());
                }
            }
        }
        //新增抄表信息
        waterMeterMapper.insert(pojo);
        Map<String,Object> map=new HashMap<>();
        map.put("meterinfo",pojo);
        map.put("farminfo",standobj);
        return map;
    }
    //分页查询充值记录
    public List<RechargeShow> selectRechargShowInfoByPage(WaterParam waterParam){
        return waterRechargeMapper.selectRechargShowInfoByPage(waterParam);
    }
    //充值记录总记录数
    public Integer selectRechargShowInfoByCount(WaterParam waterParam){
        return waterRechargeMapper.selectRechargShowInfoByCount(waterParam);
    }
    //分页查询收费通知
    public List<MeterShow> selectMeterShowInfoByPage(WaterParam waterParam){
        return waterRechargeMapper.selectMeterShowInfoByPage(waterParam);
    }
    //收费通知总记录数
    public Integer selectMeterShowInfoByCount(WaterParam waterParam){
        return waterRechargeMapper.selectMeterShowInfoByCount(waterParam);
    }
    //充值统计信息
    public Map<String,Object> selectSumRecharge(WaterParam waterParam){
        Map<String, Object> map = new HashMap<>();
        Integer count=waterRechargeMapper.selectSumRechargeByCount(waterParam);
        List<RechargeShow> list=new ArrayList<>();
        if(count>0){
            list=waterRechargeMapper.selectSumRechargeByPage(waterParam);
        }
        if(waterParam.isIssum()) {
            QueryWrapper<WaterRecharge> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("SUM(AMOUNT) as AMOUNT", "SUM(BUYWATER) as BUYWATER", "SUM(BASEWATER) as BASEWATER", "SUM(BUYFIRST) as BUYFIRST", "SUM(BUYSECOND) as BUYSECOND", "SUM(BUYTHIRD) as BUYTHIRD")
                    .eq("SYSSIGN", waterParam.getShowsign()).apply("DATEPART(year,CREATETIME)={0}", waterParam.getYear());
            if (waterParam.getStnm() != null && !waterParam.getStnm().equals("")) {
                queryWrapper.inSql("FARMCODE", "select FARMCODE from WATER_FARM_USERS where SYSSIGN='" + waterParam.getShowsign() + "' and FARMNAME like '%" + waterParam.getStnm() + "%'");
            }
            if (waterParam.getCanalcode() != null && !waterParam.getCanalcode().equals("")) {
                queryWrapper.inSql("FARMCODE", "select FARMCODE from WATER_FARM_USERS where SYSSIGN='" + waterParam.getShowsign() + "' and CANALCODE ='" + waterParam.getCanalcode() + "'");
            }
            WaterRecharge sumobj = waterRechargeMapper.selectOne(queryWrapper);
            map.put("rechargesum", sumobj);
            //累计承包面积
            QueryWrapper<WaterFarmUsers> sumareaWarpper=new QueryWrapper<>();
            sumareaWarpper.select("SUM(AREA) as AREA").eq("SYSSIGN", waterParam.getShowsign()).inSql("FARMCODE","select distinct FARMCODE from WATER_RECHARGE where SYSSIGN='"+waterParam.getShowsign()+"' and DATEPART(year,CREATETIME)="+waterParam.getYear());
            if (waterParam.getStnm() != null && !waterParam.getStnm().equals("")) {
                sumareaWarpper.inSql("FARMCODE", "select FARMCODE from WATER_FARM_USERS where SYSSIGN='" + waterParam.getShowsign() + "' and FARMNAME like '%" + waterParam.getStnm() + "%'");
            }
            if (waterParam.getCanalcode() != null && !waterParam.getCanalcode().equals("")) {
                sumareaWarpper.inSql("FARMCODE", "select FARMCODE from WATER_FARM_USERS where SYSSIGN='" + waterParam.getShowsign() + "' and CANALCODE ='" + waterParam.getCanalcode() + "'");
            }
            WaterFarmUsers areaobj=waterFarmUsersMapper.selectOne(sumareaWarpper);
            if(areaobj==null){
                map.put("areasum",0);
            }else{
                map.put("areasum",areaobj.getArea());
            }
        }
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    //农户用户超水信息查询
    public Map<String,Object> selectMeterSumInfo(WaterParam waterParam){
        Map<String, Object> map = new HashMap<>();
        Integer count=waterRechargeMapper.selectMeterSumByCount(waterParam);
        List<MeterShow> list=new ArrayList<>();
        if(count>0){
            list=waterRechargeMapper.selectMeterSumByPage(waterParam);
        }
        if(waterParam.isIssum()) {
            QueryWrapper<WaterMeter> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("SUM(WATERRATE) as WATERRATE", "SUM(WATERYIELD) as WATERYIELD", "SUM(YIELDBASE) as YIELDBASE", "SUM(YIELDFIRST) as YIELDFIRST", "SUM(YIELDSECOND) as YIELDSECOND", "SUM(YIELDTHIRD) as YIELDTHIRD")
                    .eq("SYSSIGN", waterParam.getShowsign()).apply("(DATEPART(year,READTIME)={0} AND DATEPART(MONTH,READTIME)>1 OR DATEPART(year,READTIME)={1} and DATEPART(MONTH,READTIME)=1)", waterParam.getYear(), waterParam.getNextyear());
            if (waterParam.getStnm() != null && !waterParam.getStnm().equals("")) {
                queryWrapper.inSql("FARMCODE", "select FARMCODE from WATER_FARM_USERS where SYSSIGN='" + waterParam.getShowsign() + "' and FARMNAME like '%" + waterParam.getStnm() + "%'");
            }
            if (waterParam.getCanalcode() != null && !waterParam.getCanalcode().equals("")) {
                queryWrapper.inSql("FARMCODE", "select FARMCODE from WATER_FARM_USERS where SYSSIGN='" + waterParam.getShowsign() + "' and CANALCODE ='" + waterParam.getCanalcode() + "'");
            }
            WaterMeter sumobj = waterMeterMapper.selectOne(queryWrapper);
            map.put("metersum", sumobj);
            //累计承包面积
            QueryWrapper<WaterFarmUsers> sumareaWarpper=new QueryWrapper<>();
            sumareaWarpper.select("SUM(AREA) as AREA").eq("SYSSIGN", waterParam.getShowsign()).inSql("FARMCODE","select distinct FARMCODE from WATER_METER where SYSSIGN='"+waterParam.getShowsign()+"' and (DATEPART(year,READTIME)="+waterParam.getYear()+" AND DATEPART(MONTH,READTIME)>1 OR DATEPART(year,READTIME)="+waterParam.getNextyear()+" and DATEPART(MONTH,READTIME)=1)");
            if (waterParam.getStnm() != null && !waterParam.getStnm().equals("")) {
                sumareaWarpper.inSql("FARMCODE", "select FARMCODE from WATER_FARM_USERS where SYSSIGN='" + waterParam.getShowsign() + "' and FARMNAME like '%" + waterParam.getStnm() + "%'");
            }
            if (waterParam.getCanalcode() != null && !waterParam.getCanalcode().equals("")) {
                sumareaWarpper.inSql("FARMCODE", "select FARMCODE from WATER_FARM_USERS where SYSSIGN='" + waterParam.getShowsign() + "' and CANALCODE ='" + waterParam.getCanalcode() + "'");
            }
            WaterFarmUsers areaobj=waterFarmUsersMapper.selectOne(sumareaWarpper);
            if(areaobj==null){
                map.put("areasum",0);
            }else{
                map.put("areasum",areaobj.getArea());
            }

        }
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    //农户用户节水信息查询
    public Map<String,Object> selectMeterBackSumInfo(WaterParam waterParam){
        Map<String,Object> map=new HashMap<>();
        Integer count=waterRechargeMapper.selectMeterBackSumByCount(waterParam);
        List<MeterBackShow> list=new ArrayList<>();
        MeterBackShow sumobj=new MeterBackShow();
        if(count>0){
            if(waterParam.getOrderBy().equals("TOTALWATER")){
                waterParam.setOrderBy("(t.BASEWATER*f.AREA-WATERYIELD)");
            }
            if(waterParam.getOrderBy().equals("ONEWATER")){
                waterParam.setOrderBy("(t.BASEWATER*f.AREA-WATERYIELD)");
            }
            if(waterParam.getOrderBy().equals("TWOWATER")){
                waterParam.setOrderBy("(t.BASEWATER*f.AREA-WATERYIELD-t.SAVEONEUP*f.AREA)");
            }
            if(waterParam.getOrderBy().equals("THRIDWATER")){
                waterParam.setOrderBy("(t.BASEWATER*f.AREA-WATERYIELD-t.SAVEONEUP*f.AREA-t.SAVETWOUP*f.AREA)");
            }
            if(waterParam.getOrderBy().equals("REALBASE")){
                waterParam.setOrderBy("WATERYIELD");
            }
            if(waterParam.getOrderBy().equals("BACKAMOUNT")){
                waterParam.setOrderBy("((t.BASEWATER*f.AREA-WATERYIELD-t.SAVEONEUP*f.AREA-t.SAVETWOUP*f.AREA)*t.SAVETHRPR+(t.BASEWATER*f.AREA-WATERYIELD-t.SAVEONEUP*f.AREA)*t.SAVETWOPR+(t.BASEWATER*f.AREA-WATERYIELD)*SAVEONEPR)");
            }
            list=waterRechargeMapper.selectMeterBackSumByPage(waterParam);
            for(int i=0;i<list.size();i++){
                MeterBackShow obj=list.get(i);
                caculateback(obj);
            }
            if(waterParam.isIssum()) {
                //累计信息
                waterParam.setBegincount(1);
                waterParam.setEndcount(9999999);
                List<MeterBackShow> listsum = waterRechargeMapper.selectMeterBackSumByPage(waterParam);
                for (int i = 0; i < listsum.size(); i++) {
                    MeterBackShow obj = listsum.get(i);
                    caculateback(obj);
                    if (i == 0) {
                        sumobj.setBackamount(obj.getBackamount());
                        sumobj.setWateryield(obj.getWateryield());
                        sumobj.setRealbase(obj.getRealbase());
                        sumobj.setOnewater(obj.getOnewater());
                        sumobj.setTwowater(obj.getTwowater());
                        sumobj.setThridwater(obj.getThridwater());
                        sumobj.setTotalwater(obj.getTotalwater());
                        sumobj.setArea(obj.getArea());
                    } else {
                        sumobj.setBackamount(obj.getBackamount() + sumobj.getBackamount());
                        sumobj.setWateryield(obj.getWateryield() + sumobj.getWateryield());
                        sumobj.setRealbase(obj.getRealbase() + sumobj.getRealbase());
                        sumobj.setOnewater(obj.getOnewater() + sumobj.getOnewater());
                        sumobj.setTwowater(obj.getTwowater() + sumobj.getTwowater());
                        sumobj.setThridwater(obj.getThridwater() + sumobj.getThridwater());
                        sumobj.setTotalwater(obj.getTotalwater() + sumobj.getTotalwater());
                        sumobj.setArea(obj.getArea()+sumobj.getArea());
                    }
                }
            }
        }
        if(waterParam.isIssum()) {
            map.put("backsum", sumobj);
        }
        map.put("rows",list);
        map.put("total",count);
        return map;
    }
    //计算回购价格
    private void caculateback(MeterBackShow obj) {
        Integer totalback=obj.getBasewater()-obj.getWateryield();
        //未节水
        if(totalback<0){
            obj.setBackamount(0.00);
            obj.setTotalwater(0);
            obj.setOnewater(0);
            obj.setTwowater(0);
            obj.setThridwater(0);
            obj.setRealbase(obj.getBasewater());
         //节水
        }else{
            obj.setTotalwater(totalback);
            obj.setRealbase(obj.getWateryield());
            //三级节水
            if(totalback>obj.getSavetwoup()){
                obj.setThridwater(totalback-obj.getSavetwoup());
                obj.setTwowater(obj.getSavetwoup()-obj.getSaveoneup());
                obj.setOnewater(obj.getSaveoneup());
             //二级节水
            }else if(totalback>obj.getSaveoneup()){
                obj.setThridwater(0);
                obj.setTwowater(totalback-obj.getSaveoneup());
                obj.setOnewater(obj.getSaveoneup());
            }//一级节水
            else{
                obj.setThridwater(0);
                obj.setTwowater(0);
                obj.setOnewater(totalback);
            }
            obj.setBackamount(obj.getSavethrpr()*obj.getThridwater()+obj.getSavetwopr()*obj.getTwowater()+obj.getSaveonepr()*obj.getOnewater());
        }
    }
}
