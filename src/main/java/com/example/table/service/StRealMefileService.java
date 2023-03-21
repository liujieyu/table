package com.example.table.service;

import com.example.table.entity.MedataParam;
import com.example.table.entity.StRealMedata;
import com.example.table.entity.StRealMefile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujieyu
 * @since 2019-10-29
 */
public interface StRealMefileService extends IService<StRealMefile> {
    //率定数据上传信息总记录数
    Integer selectStRealMefileCount(MedataParam medataParam);
    //率定数据上传信息分页查询
    List<StRealMefile> selectStRealMefileByPage(MedataParam medataParam);
    //根据测量编码查询率定数据上传信息
    StRealMefile selectStRealMefileByMecode(MedataParam medataParam);
    //率定数据上传信息详情总记录数
    Integer selectStRealMedataCount(MedataParam medataParam);
    //率定数据上传信息详情
    List<StRealMedata> selectStRealMedataDetail(MedataParam medataParam);
    //新增率定数据上传信息
    void insertStRealMefile(MedataParam medataParam);
    //修改率定数据上传信息
    void updateStRealMefile(MedataParam medataParam);
    //新增率定数据上传信息详情
    void insertStRealMedata(List<MedataParam> list);
    //判断文件是否存在
    Integer checkMefileExist(MedataParam medataParam);
    //插入到率定结果表中
    void insertLdresult(MedataParam medataParam);
    //获取数据库最大测量编号
    String selectMaxMecode(MedataParam medataParam);
    //导入Excel
    String  readExcel(MultipartFile file, List<MedataParam> dataList,String meCode);
}
