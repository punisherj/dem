package com.bonc.dem.service;

import java.util.Map;

public interface ExcelService {

    /***
     * 注意：在复制sheet页时 即使是存在的excel也需要重新写入！
     *
     */
    void record(Map map);
}
