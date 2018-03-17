package com.bonc.dem.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResources {

    /**
      * name Excel列名
      * @return
      */
    String title();

    /**
      * id Excel ID
      * @return
      */
    int order() default 9999;
}
