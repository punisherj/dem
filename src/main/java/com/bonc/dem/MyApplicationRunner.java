package com.bonc.dem;

import com.bonc.dem.pojo.TotalCount;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {

    public static final TotalCount tc = TotalCount.getInstance();

    @Override
    public void run(ApplicationArguments var1) {

    }
}
