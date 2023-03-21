package com.example.table.service.impl;

import com.example.table.entity.StHpC;
import com.example.table.mapper.StHpCMapper;
import com.example.table.service.StHpCService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sa
 * @since 2019-09-17
 */
@Service
public class StHpCServiceImpl extends ServiceImpl<StHpCMapper, StHpC> implements StHpCService {
    @Autowired
    StHpCMapper stHpCMapper;
    @Override
    public List<StHpC> selectByPrimaryKey(int id){
        return stHpCMapper.selectByPrimaryKey(id);
    }
}
