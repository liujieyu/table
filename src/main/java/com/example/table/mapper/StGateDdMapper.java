package com.example.table.mapper;

import com.example.table.entity.AlarmParam;
import com.example.table.entity.StGateDd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujieyu
 * @since 2019-10-10
 */
@Component
public interface StGateDdMapper extends BaseMapper<StGateDd> {
    //泄洪信息总记录数
    Integer selectStGateDdByCount(AlarmParam alarmParam);
    //泄洪信息分页查询
    List<StGateDd> selectStGateDdByPage(AlarmParam alarmParam);
    //泄洪信息图形数据
    List<StGateDd> selectStGateDdByType(AlarmParam alarmParam);
}
