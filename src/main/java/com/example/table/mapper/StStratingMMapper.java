package com.example.table.mapper;

import com.example.table.entity.LvDingParam;
import com.example.table.entity.StStratingM;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.table.entity.StStsectionStation;
import com.example.table.entity.StZqrlB;
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
 * @since 2019-10-25
 */
@Component
public interface StStratingMMapper extends BaseMapper<StStratingM> {
    //查询所有站点
    List<StStratingM> selectStStratingMAll(LvDingParam param);
    //根据stcd查询最大水深和最大流量
    List<StStsectionStation> selectBzdmMaxByStcd(LvDingParam param);
    List<StStsectionStation> selectBxecMaxByStcd(LvDingParam param);
    List<StStsectionStation> selectSzMaxByStcd(LvDingParam param);
    List<Map<String,Object>> selectParshallflume(LvDingParam param);
    //查询列表
    List<StZqrlB> selectSteqrlBList(LvDingParam param);
    //新增
    void insertSteqrlB(LvDingParam param);
    //修改
    void updateSteqrlB(LvDingParam param);
    //删除
    void deletesTeqrlB(LvDingParam param);
    //查询详情
    StZqrlB selectTeqrlBById(LvDingParam param);
    //修改糙率
    Integer updateCl(LvDingParam param);
    //判断率定结果是否存在
    Integer selectResultByExist(LvDingParam param);
    //获取率定站点编码和名称
    List<StZqrlB> selectStStratingMByCdAndNm();
    //判断站点对应编号是否正确
    Integer selectStcdStStratingMByStnm(LvDingParam param);
    //判断该年份的率定结果信息是否存在
    Integer selectResultByStcdAndYear(LvDingParam param);
    //批量新增率定结果信息
    Integer insertSteqrlBObjs(@Param("ldresults") List<StZqrlB> ldresults);
}
