package com.example.table.service.impl;

import com.example.table.entity.LvDingParam;
import com.example.table.entity.WrpIrrbsmanageinstitution;
import com.example.table.entity.StNeedwaterPlan;
import com.example.table.mapper.StNeedwaterPlanMapper;
import com.example.table.service.StNeedwaterPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2019-11-12
 */
@Service
public class StNeedwaterPlanServiceImpl extends ServiceImpl<StNeedwaterPlanMapper, StNeedwaterPlan> implements StNeedwaterPlanService {
    @Autowired
    StNeedwaterPlanMapper stNeedwaterPlanMapper;
    //需水计划
    public List<StNeedwaterPlan> selectWaterPlan(LvDingParam param){
        StNeedwaterPlan totalobj=new StNeedwaterPlan();
        List<StNeedwaterPlan> alllst=new ArrayList<>();
        List<StNeedwaterPlan> list=stNeedwaterPlanMapper.selectWaterPlan(param);
        for(int i=0;i<list.size();i++){
            StNeedwaterPlan obj=list.get(i);
            obj.setId((alllst.size()+1)*100+i%100);
            if(i==0){
                StNeedwaterPlan parent=new StNeedwaterPlan();
                parent.setId(alllst.size()+1);
                parent.setOrganCode(obj.getOrganCode());
                parent.setOrganName(obj.getOrganName());
                parent.setCanalName("合计");
                parent.setOne(obj.getOne());
                parent.setTwo(obj.getTwo());
                parent.setThree(obj.getThree());
                parent.setFour(obj.getFour());
                parent.setFive(obj.getFive());
                parent.setSix(obj.getSix());
                parent.setSeven(obj.getSeven());
                parent.setEight(obj.getEight());
                parent.setNine(obj.getNine());
                parent.setTen(obj.getTen());
                parent.setEleven(obj.getEleven());
                parent.setTwelve(obj.getTwelve());
                parent.setSumw(obj.getSumw());
                parent.setChildren(new ArrayList<StNeedwaterPlan>());
                obj.setOrganName("");
                parent.getChildren().add(obj);
                alllst.add(parent);
                totalobj.setOrganName("合计");
                totalobj.setOne(obj.getOne());
                totalobj.setTwo(obj.getTwo());
                totalobj.setThree(obj.getThree());
                totalobj.setFour(obj.getFour());
                totalobj.setFive(obj.getFive());
                totalobj.setSix(obj.getSix());
                totalobj.setSeven(obj.getSeven());
                totalobj.setEight(obj.getEight());
                totalobj.setNine(obj.getNine());
                totalobj.setTen(obj.getTen());
                totalobj.setEleven(obj.getEleven());
                totalobj.setTwelve(obj.getTwelve());
                totalobj.setSumw(obj.getSumw());
            }else{
                totalobj.setOne(obj.getOne()+totalobj.getOne());
                totalobj.setTwo(obj.getTwo()+totalobj.getTwo());
                totalobj.setThree(obj.getThree()+totalobj.getThree());
                totalobj.setFour(obj.getFour()+totalobj.getFour());
                totalobj.setFive(obj.getFive()+totalobj.getFive());
                totalobj.setSix(obj.getSix()+totalobj.getSix());
                totalobj.setSeven(obj.getSeven()+totalobj.getSeven());
                totalobj.setEight(obj.getEight()+totalobj.getEight());
                totalobj.setNine(obj.getNine()+totalobj.getNine());
                totalobj.setTen(obj.getTen()+totalobj.getTen());
                totalobj.setEleven(obj.getEleven()+totalobj.getEleven());
                totalobj.setTwelve(obj.getTwelve()+totalobj.getTwelve());
                totalobj.setSumw(obj.getSumw()+totalobj.getSumw());
                StNeedwaterPlan beforobj=list.get(i-1);
                if(beforobj.getOrganCode().equals(obj.getOrganCode())){
                    StNeedwaterPlan parent=alllst.get(alllst.size()-1);
                    parent.setOne(obj.getOne()+parent.getOne());
                    parent.setTwo(obj.getTwo()+parent.getTwo());
                    parent.setThree(obj.getThree()+parent.getThree());
                    parent.setFour(obj.getFour()+parent.getFour());
                    parent.setFive(obj.getFive()+parent.getFive());
                    parent.setSix(obj.getSix()+parent.getSix());
                    parent.setSeven(obj.getSeven()+parent.getSeven());
                    parent.setEight(obj.getEight()+parent.getEight());
                    parent.setNine(obj.getNine()+parent.getNine());
                    parent.setTen(obj.getTen()+parent.getTen());
                    parent.setEleven(obj.getEleven()+parent.getEleven());
                    parent.setTwelve(obj.getTwelve()+parent.getTwelve());
                    parent.setSumw(obj.getSumw()+parent.getSumw());
                    obj.setOrganName("");
                    parent.getChildren().add(obj);
                }else{
                    StNeedwaterPlan parent=new StNeedwaterPlan();
                    parent.setId(alllst.size()+1);
                    parent.setOrganCode(obj.getOrganCode());
                    parent.setOrganName(obj.getOrganName());
                    parent.setCanalName("合计");
                    parent.setOne(obj.getOne());
                    parent.setTwo(obj.getTwo());
                    parent.setThree(obj.getThree());
                    parent.setFour(obj.getFour());
                    parent.setFive(obj.getFive());
                    parent.setSix(obj.getSix());
                    parent.setSeven(obj.getSeven());
                    parent.setEight(obj.getEight());
                    parent.setNine(obj.getNine());
                    parent.setTen(obj.getTen());
                    parent.setEleven(obj.getEleven());
                    parent.setTwelve(obj.getTwelve());
                    parent.setSumw(obj.getSumw());
                    parent.setChildren(new ArrayList<StNeedwaterPlan>());
                    obj.setOrganName("");
                    parent.getChildren().add(obj);
                    alllst.add(parent);
                }
            }
        }
        if(alllst.size()>0) {
            alllst.add(totalobj);
        }
        return alllst;
    }
    //获取管理机构信息
    public List<Map<String,Object>> selectGljgInfo(String types){
        return stNeedwaterPlanMapper.selectGljgInfo(types);
    }
    //用水定额计划
    public List<StNeedwaterPlan> selectWaterquota(LvDingParam param){
        List<StNeedwaterPlan> alllst=new ArrayList<>();
        StNeedwaterPlan totalobj=new StNeedwaterPlan();
        if(param.getOrganCode()==null || param.getOrganCode().equals("")){
            List<Map<String,Object>> top=selectGljgInfo("1");
            totalobj.setOrganName(top.get(0).get("label").toString());
            totalobj.setOrganCode(top.get(0).get("value").toString());
            totalobj.setCanalName("合计");
            totalobj.setId(1);
            totalobj.setChildren(new ArrayList<StNeedwaterPlan>());
            alllst.add(totalobj);
        }
        List<StNeedwaterPlan> list=stNeedwaterPlanMapper.selectWaterquota(param);
        for(int i=0;i<list.size();i++){
            StNeedwaterPlan obj=list.get(i);
            if(param.getOrganCode()==null || param.getOrganCode().equals("")){
                obj.setId((totalobj.getChildren().size()+1)*100+i%100);
            }else{
                obj.setId((alllst.size()+1)*100+i%100);
            }

            if(i==0){
                StNeedwaterPlan parent=new StNeedwaterPlan();
                if(param.getOrganCode()==null || param.getOrganCode().equals("")){
                    parent.setId((totalobj.getChildren().size()+1)*10+1);
                }else{
                    parent.setId(alllst.size()+1);
                }
                parent.setOrganCode(obj.getOrganCode());
                parent.setOrganName(obj.getOrganName());
                parent.setCanalName("合计");
                parent.setOne(obj.getOne());
                parent.setTwo(obj.getTwo());
                parent.setThree(obj.getThree());
                parent.setFour(obj.getFour());
                parent.setFive(obj.getFive());
                parent.setSix(obj.getSix());
                parent.setSeven(obj.getSeven());
                parent.setEight(obj.getEight());
                parent.setNine(obj.getNine());
                parent.setTen(obj.getTen());
                parent.setEleven(obj.getEleven());
                parent.setTwelve(obj.getTwelve());
                parent.setSumw(obj.getSumw());
                parent.setChildren(new ArrayList<StNeedwaterPlan>());
                obj.setOrganName("");
                parent.getChildren().add(obj);

                if(param.getOrganCode()==null || param.getOrganCode().equals("")) {
                    totalobj.setOne(obj.getOne());
                    totalobj.setTwo(obj.getTwo());
                    totalobj.setThree(obj.getThree());
                    totalobj.setFour(obj.getFour());
                    totalobj.setFive(obj.getFive());
                    totalobj.setSix(obj.getSix());
                    totalobj.setSeven(obj.getSeven());
                    totalobj.setEight(obj.getEight());
                    totalobj.setNine(obj.getNine());
                    totalobj.setTen(obj.getTen());
                    totalobj.setEleven(obj.getEleven());
                    totalobj.setTwelve(obj.getTwelve());
                    totalobj.setSumw(obj.getSumw());
                    totalobj.getChildren().add(parent);
                }else{
                    alllst.add(parent);
                }
            }else{
                if(param.getOrganCode()==null || param.getOrganCode().equals("")) {
                    totalobj.setOne(obj.getOne() + totalobj.getOne());
                    totalobj.setTwo(obj.getTwo() + totalobj.getTwo());
                    totalobj.setThree(obj.getThree() + totalobj.getThree());
                    totalobj.setFour(obj.getFour() + totalobj.getFour());
                    totalobj.setFive(obj.getFive() + totalobj.getFive());
                    totalobj.setSix(obj.getSix() + totalobj.getSix());
                    totalobj.setSeven(obj.getSeven() + totalobj.getSeven());
                    totalobj.setEight(obj.getEight() + totalobj.getEight());
                    totalobj.setNine(obj.getNine() + totalobj.getNine());
                    totalobj.setTen(obj.getTen() + totalobj.getTen());
                    totalobj.setEleven(obj.getEleven() + totalobj.getEleven());
                    totalobj.setTwelve(obj.getTwelve() + totalobj.getTwelve());
                    totalobj.setSumw(obj.getSumw() + totalobj.getSumw());
                }
                StNeedwaterPlan beforobj=list.get(i-1);
                if(beforobj.getOrganCode().equals(obj.getOrganCode())){
                    StNeedwaterPlan parent;
                    if(param.getOrganCode()==null || param.getOrganCode().equals("")){
                        parent=totalobj.getChildren().get(totalobj.getChildren().size()-1);
                    }else{
                        parent=alllst.get(alllst.size()-1);
                    }
                    parent.setOne(obj.getOne()+parent.getOne());
                    parent.setTwo(obj.getTwo()+parent.getTwo());
                    parent.setThree(obj.getThree()+parent.getThree());
                    parent.setFour(obj.getFour()+parent.getFour());
                    parent.setFive(obj.getFive()+parent.getFive());
                    parent.setSix(obj.getSix()+parent.getSix());
                    parent.setSeven(obj.getSeven()+parent.getSeven());
                    parent.setEight(obj.getEight()+parent.getEight());
                    parent.setNine(obj.getNine()+parent.getNine());
                    parent.setTen(obj.getTen()+parent.getTen());
                    parent.setEleven(obj.getEleven()+parent.getEleven());
                    parent.setTwelve(obj.getTwelve()+parent.getTwelve());
                    parent.setSumw(obj.getSumw()+parent.getSumw());
                    obj.setOrganName("");
                    parent.getChildren().add(obj);
                }else{
                    StNeedwaterPlan parent=new StNeedwaterPlan();
                    if(param.getOrganCode()==null || param.getOrganCode().equals("")){
                        parent.setId((totalobj.getChildren().size()+1)*10+1);
                    }else {
                        parent.setId(alllst.size() + 1);
                    }
                    parent.setOrganCode(obj.getOrganCode());
                    parent.setOrganName(obj.getOrganName());
                    parent.setCanalName("合计");
                    parent.setOne(obj.getOne());
                    parent.setTwo(obj.getTwo());
                    parent.setThree(obj.getThree());
                    parent.setFour(obj.getFour());
                    parent.setFive(obj.getFive());
                    parent.setSix(obj.getSix());
                    parent.setSeven(obj.getSeven());
                    parent.setEight(obj.getEight());
                    parent.setNine(obj.getNine());
                    parent.setTen(obj.getTen());
                    parent.setEleven(obj.getEleven());
                    parent.setTwelve(obj.getTwelve());
                    parent.setSumw(obj.getSumw());
                    parent.setChildren(new ArrayList<StNeedwaterPlan>());
                    obj.setOrganName("");
                    parent.getChildren().add(obj);
                    if(param.getOrganCode()==null || param.getOrganCode().equals("")) {
                        totalobj.getChildren().add(parent);
                    }else{
                        alllst.add(parent);
                    }
                }
            }
        }
        return alllst;
    }
    //支渠用水管理
    public List<StNeedwaterPlan> selectUsewaterByCanalcode(LvDingParam param){
        List<StNeedwaterPlan> list= stNeedwaterPlanMapper.selectUsewaterByCanalcode(param);
        if(list==null || list.size()==0){

        }else if(list.size()==1){
            StNeedwaterPlan obj=new StNeedwaterPlan();
            StNeedwaterPlan total=new StNeedwaterPlan();
            total.setStnm("用水率");
            if(list.get(0).getMemo().equals("0")){
                obj.setStnm("实际用水");
                list.get(0).setStnm("用水定额");
                list.add(obj);
                total.setOne(0.00);total.setTwo(0.00);total.setThree(0.00);total.setFour(0.00);total.setFive(0.00);total.setSix(0.00);total.setSeven(0.00);total.setEight(0.00);
                total.setNine(0.00);total.setTen(0.00);total.setEleven(0.00);total.setTwelve(0.00);total.setSumw(0.00);
                list.add(total);
            }else{
                obj.setStnm("用水定额");
                list.get(0).setStnm("实际用水");
                list.add(0,obj);
                total.setOne(100.00);total.setTwo(100.00);total.setThree(100.00);total.setFour(100.00);total.setFive(100.00);total.setSix(100.00);total.setSeven(100.00);total.setEight(100.00);
                total.setNine(100.00);total.setTen(100.00);total.setEleven(100.00);total.setTwelve(100.00);total.setSumw(100.00);
                list.add(total);
            }
        }else{
            StNeedwaterPlan total=new StNeedwaterPlan();
            StNeedwaterPlan quota=list.get(0);
            quota.setStnm("用水定额");
            StNeedwaterPlan real=list.get(1);
            real.setStnm("实际用水");
            if(quota.getOne().doubleValue()==0){
                total.setOne(real.getOne().doubleValue()==0?0.00:100.00);
            }else{
                total.setOne(new BigDecimal(real.getOne()/quota.getOne()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            }
            if(quota.getTwo().doubleValue()==0){
                total.setTwo(real.getTwo().doubleValue()==0?0.00:100.00);
            }else{
                total.setTwo(new BigDecimal(real.getTwo()/quota.getTwo()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            }
            total.setThree(quota.getThree().doubleValue()==0?real.getThree().doubleValue()==0?0.00:100.00:new BigDecimal(real.getThree()/quota.getThree()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setFour(quota.getFour().doubleValue()==0?real.getFour().doubleValue()==0?0.00:100.00:new BigDecimal(real.getFour()/quota.getFour()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setFive(quota.getFive().doubleValue()==0?real.getFive().doubleValue()==0?0.00:100.00:new BigDecimal(real.getFive()/quota.getFive()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setSix(quota.getSix().doubleValue()==0?real.getSix().doubleValue()==0?0.00:100.00:new BigDecimal(real.getSix()/quota.getSix()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setSeven(quota.getSeven().doubleValue()==0?real.getSeven().doubleValue()==0?0.00:100.00:new BigDecimal(real.getSeven()/quota.getSeven()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setEight(quota.getEight().doubleValue()==0?real.getEight().doubleValue()==0?0.00:100.00:new BigDecimal(real.getEight()/quota.getEight()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setNine(quota.getNine().doubleValue()==0?real.getNine().doubleValue()==0?0.00:100.00:new BigDecimal(real.getNine()/quota.getNine()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setTen(quota.getTen().doubleValue()==0?real.getTen().doubleValue()==0?0.00:100.00:new BigDecimal(real.getTen()/quota.getTen()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setEleven(quota.getEleven().doubleValue()==0?real.getEleven().doubleValue()==0?0.00:100.00:new BigDecimal(real.getEleven()/quota.getEleven()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setTwelve(quota.getTwelve().doubleValue()==0?real.getTwelve().doubleValue()==0?0.00:100.00:new BigDecimal(real.getTwelve()/quota.getTwelve()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setSumw(quota.getSumw().doubleValue()==0?real.getSumw().doubleValue()==0?0.00:100.00:new BigDecimal(real.getSumw()/quota.getSumw()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            total.setStnm("用水率");
            list.add(total);
        }
        return list;
    }
    //管理所-渠道树形数据
    public WrpIrrbsmanageinstitution selectOrganCanalTree(LvDingParam param){
        List<WrpIrrbsmanageinstitution> list=stNeedwaterPlanMapper.selectOrganCanalTree(param);
        WrpIrrbsmanageinstitution all=list.get(0);
        int id=0;
        all.setId(0);
        all.setCanalCode(all.getOrganCode());
        all.setCanalName(all.getOrganName());
        all.setChildren(new ArrayList<>());
        for(int i=1;i<list.size();i++){
            WrpIrrbsmanageinstitution obj=list.get(i);
            if(i==1){
                id++;
                WrpIrrbsmanageinstitution parent=new WrpIrrbsmanageinstitution();
                parent.setId(id);
                parent.setCanalCode(obj.getOrganCode());
                parent.setCanalName(obj.getOrganName());
                parent.setChildren(new ArrayList<>());
                parent.getChildren().add(obj);
                obj.setId(id*100+parent.getChildren().size());
                all.getChildren().add(parent);
            }else{
                WrpIrrbsmanageinstitution before=list.get(i-1);
                if(before.getOrganCode().equals(obj.getOrganCode())){
                    all.getChildren().get(all.getChildren().size()-1).getChildren().add(obj);
                    obj.setId(id*100+all.getChildren().get(all.getChildren().size()-1).getChildren().size());
                }else{
                    id++;
                    WrpIrrbsmanageinstitution parent=new WrpIrrbsmanageinstitution();
                    parent.setId(id);
                    parent.setCanalCode(obj.getOrganCode());
                    parent.setCanalName(obj.getOrganName());
                    parent.setChildren(new ArrayList<>());
                    parent.getChildren().add(obj);
                    obj.setId(id*100+parent.getChildren().size());
                    all.getChildren().add(parent);
                }
            }
        }
        return all;
    }
    public List<WrpIrrbsmanageinstitution> selectOrganCanalDemo(LvDingParam param){
        return stNeedwaterPlanMapper.selectOrganCanalTree(param);
    }
}
