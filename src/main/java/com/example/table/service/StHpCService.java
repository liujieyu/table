package com.example.table.service;

import com.example.table.entity.StHpC;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sa
 * @since 2019-09-17
 */
public interface StHpCService extends IService<StHpC> {
    List<StHpC> selectByPrimaryKey(int id);
}
