package com.example.table.service.impl;

import com.example.table.entity.LvDingParam;
import com.example.table.entity.StStratingM;
import com.example.table.entity.StStsectionStation;
import com.example.table.entity.StZqrlB;
import com.example.table.mapper.StStratingMMapper;
import com.example.table.service.StStratingMService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2019-10-25
 */
@Service
public class StStratingMServiceImpl extends ServiceImpl<StStratingMMapper, StStratingM> implements StStratingMService {
    @Autowired
    StStratingMMapper stratingMMapper;
    //查询所有站点
    public List<StStratingM> selectStStratingMAll(LvDingParam param){
        return stratingMMapper.selectStStratingMAll(param);
    }
    //根据stcd查询最大水深和最大流量
    public StStsectionStation selectBzdmMaxByStcd(LvDingParam param){
        List<StStsectionStation> list=stratingMMapper.selectBzdmMaxByStcd(param);
        if(list!=null && list.size()>0){
            return list.get(0);
        }else{
            return null;
        }

    }
    public StStsectionStation selectBxecMaxByStcd(LvDingParam param){
        List<StStsectionStation> list=stratingMMapper.selectBxecMaxByStcd(param);
        if(list!=null && list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
    public StStsectionStation selectSzMaxByStcd(LvDingParam param){
        List<StStsectionStation> list=stratingMMapper.selectSzMaxByStcd(param);
        if(list!=null && list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
    public Map<String,Object> selectParshallflume(LvDingParam param){
        List<Map<String,Object>> list=stratingMMapper.selectParshallflume(param);
        if(list!=null && list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
    //查询列表
    public List<StZqrlB> selectSteqrlBList(LvDingParam param){
        return stratingMMapper.selectSteqrlBList(param);
    }
    //新增
    public void insertSteqrlB(LvDingParam param){
        stratingMMapper.insertSteqrlB(param);
    }
    //修改
    public void updateSteqrlB(LvDingParam param){
        stratingMMapper.updateSteqrlB(param);
    }
    //删除
    public void deletesTeqrlB(LvDingParam param){
        stratingMMapper.deletesTeqrlB(param);
    }
    //查询详情
    public StZqrlB selectTeqrlBById(LvDingParam param){
       return stratingMMapper.selectTeqrlBById(param);
    }
    //修改糙率
    public Integer updateCl(LvDingParam param){
      return stratingMMapper.updateCl(param);
    }
    //判断率定结果是否存在
    public Integer selectResultByExist(LvDingParam param){
        return stratingMMapper.selectResultByExist(param);
    }
    //获取率定站点编码和名称
    public List<StZqrlB> selectStStratingMByCdAndNm(){
        return stratingMMapper.selectStStratingMByCdAndNm();
    }
    //判断站点对应编号是否正确
    public Integer selectStcdStStratingMByStnm(LvDingParam param){
        return stratingMMapper.selectStcdStStratingMByStnm(param);
    }
    //判断该年份的率定结果信息是否存在
    public Integer selectResultByStcdAndYear(LvDingParam param){
        return stratingMMapper.selectResultByStcdAndYear(param);
    }
    //批量新增率定结果信息
    public Integer insertSteqrlBObjs(List<StZqrlB> ldresults){
        return stratingMMapper.insertSteqrlBObjs(ldresults);
    }
}

