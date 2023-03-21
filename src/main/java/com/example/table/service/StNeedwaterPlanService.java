package com.example.table.service;

import com.example.table.entity.LvDingParam;
import com.example.table.entity.StNeedwaterPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.table.entity.WrpIrrbsmanageinstitution;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujieyu
 * @since 2019-11-12
 */
public interface StNeedwaterPlanService extends IService<StNeedwaterPlan> {
    //需水计划
    List<StNeedwaterPlan> selectWaterPlan(LvDingParam param);
    //获取管理机构信息
    List<Map<String,Object>> selectGljgInfo(String types);
    //用水定额计划
    List<StNeedwaterPlan> selectWaterquota(LvDingParam param);
    //支渠用水管理
    List<StNeedwaterPlan> selectUsewaterByCanalcode(LvDingParam param);
    //管理所-渠道树形数据
    WrpIrrbsmanageinstitution selectOrganCanalTree(LvDingParam param);

    List<WrpIrrbsmanageinstitution> selectOrganCanalDemo(LvDingParam param);
}
