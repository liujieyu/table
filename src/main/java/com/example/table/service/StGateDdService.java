package com.example.table.service;

import com.example.table.entity.AlarmParam;
import com.example.table.entity.StGateDd;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujieyu
 * @since 2019-10-10
 */
public interface StGateDdService extends IService<StGateDd> {
    //泄洪信息总记录数
    Integer selectStGateDdByCount(AlarmParam alarmParam);
    //泄洪信息分页查询
    List<StGateDd> selectStGateDdByPage(AlarmParam alarmParam);
    //泄洪信息图形数据
    List<StGateDd> selectStGateDdByType(AlarmParam alarmParam);
}
