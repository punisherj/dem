package com.bonc.dem.service.impl;

import com.bonc.dem.pojo.ExcelPojo;
import com.bonc.dem.repository.ShopOrderRepository;
import com.bonc.dem.service.OrderCollectService;
import com.bonc.dem.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderCollectServiceImpl implements OrderCollectService {

    @Autowired
    ShopOrderRepository shopOrderRepository;

    @Override
    public List<Object[]> getSuccess(String time, Integer code) {
        return shopOrderRepository.createAccountSuccess(time, code);
    }

    @Override
    public List<Object[]> getFail(String time, Integer code) {
        return shopOrderRepository.createAccountFail(time, code);
    }

    @Override
    public Map<String, ExcelPojo> getExcelData(Date date, Integer code) {

        String dateStr = DateUtils.parseDateToStr(date, DateUtils.DATE_FORMAT_YYYYMMDD);
        Map<String, ExcelPojo> map = new HashMap<>();
        for (Object[] sop : this.getSuccess(dateStr, code)) {
            map.put((String)sop[0], new ExcelPojo((String)sop[0], ((BigInteger) sop[1]).intValue(),null));
        }

        for (Object[] sop : this.getFail(dateStr, code)) {
            if (map.containsKey(sop[1])) {
                map.get(sop[1]).setFail(((BigDecimal) sop[0]).intValue());
            } else {
                map.put((String) sop[1], new ExcelPojo((String) sop[1], null,((BigDecimal) sop[0]).intValue()));
            }
        }
        return map;
    }

}
