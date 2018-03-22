package com.bonc.dem.service;

import java.util.List;

public interface OrderCollectService {

    List<Object[]> getSuccess(String time, Integer code);
    List<Object[]> getFail(String time, Integer code);
    void getExcelData(String date, Integer code);
}
