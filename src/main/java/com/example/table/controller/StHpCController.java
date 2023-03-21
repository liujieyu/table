package com.example.table.controller;
import com.example.table.entity.StHpC;
import com.example.table.service.StHpCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sa
 * @since 2019-09-17
 */
@RestController
@RequestMapping("/testroot")
public class StHpCController {
    @Autowired
    StHpCService stHpCService;
    @RequestMapping("index")
    public StHpC index(){
        StHpC sthpc=new StHpC();
        sthpc.setId(1);
        sthpc.setDt(new Date());
        sthpc.setP(234.56);
        sthpc.setStcd("001");
        sthpc.setStnm("黄材雨量站");
        sthpc.setPrcd("暴雨");
        sthpc.setTm(13);
        return sthpc;
    }
    @RequestMapping(value="/raininfo/{id}",method = RequestMethod.GET)
    @ResponseBody
    public StHpC getRain(@PathVariable("id") Integer id){
        ArrayList<StHpC> list=(ArrayList<StHpC>)stHpCService.selectByPrimaryKey(id);
        if(list.size()>0){
            return list.get(0);
        }else{
            return new StHpC();
        }

    }
}
