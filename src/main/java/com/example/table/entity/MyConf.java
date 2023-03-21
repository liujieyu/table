package com.example.table.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyConf {
    @Value("lddata.savepath")
    private String savepath;
    public String getSavepath(){
        return savepath;
    }
}
