package com.bonc.dem.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TotalCount {
    private String count;
    private String success;
    private String successPercent;

    private static TotalCount instance ;
    
    private TotalCount(){
        
    }
    
    public static TotalCount getInstance(){
        if (instance == null) {
            synchronized (TotalCount.class){
                if (instance == null) {
                    instance = new TotalCount() ;
                }
            }
        }
        return instance ;
    }
    
}
