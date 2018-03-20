package com.bonc.dem;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {

    public static String[] result = null;

    @Override
    public void run(ApplicationArguments var1) {
        result = new String[3];
    }
}
