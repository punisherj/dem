package com.bonc.dem.service;

import com.bonc.dem.pojo.ExcelPojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderCollectService {

    List<Object[]> getSuccess(String time, Integer code);
    List<Object[]> getFail(String time, Integer code);
    Map<String, ExcelPojo> getExcelData(Date date, Integer code);
}
