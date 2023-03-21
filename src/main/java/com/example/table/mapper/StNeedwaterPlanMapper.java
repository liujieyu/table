package com.example.table.mapper;

import com.example.table.entity.LvDingParam;
import com.example.table.entity.StNeedwaterPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.table.entity.WrpIrrbsmanageinstitution;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujieyu
 * @since 2019-11-12
 */
@Component
public interface StNeedwaterPlanMapper extends BaseMapper<StNeedwaterPlan> {
    //需水计划
    List<StNeedwaterPlan> selectWaterPlan(LvDingParam param);
    //获取管理机构信息
    List<Map<String,Object>> selectGljgInfo(@Param("types") String types);
    //用水定额计划
    List<StNeedwaterPlan> selectWaterquota(LvDingParam param);
    //支渠用水管理
    List<StNeedwaterPlan> selectUsewaterByCanalcode(LvDingParam param);
    //管理所-渠道树形数据
    List<WrpIrrbsmanageinstitution> selectOrganCanalTree(LvDingParam param);
}
