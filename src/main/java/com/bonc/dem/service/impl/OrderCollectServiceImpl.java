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
        return shopOrderRepository.kaihuSucess(time, code);
    }

    @Override
    public List<Object[]> getFail(String time, Integer code) {
        return shopOrderRepository.kaihuFail(time, code);
    }

    @Override
    public Map<String, ExcelPojo> getExcelData(Date date, Integer code) {

        String dateStr = DateUtils.parseDateToStr(date, DateUtils.DATE_FORMAT_YYYYMMDD);
        Map<String, ExcelPojo> map = new HashMap<>();
        for (Object[] sop : this.getSuccess(dateStr, code)) {
            String owner = ((String) sop[0]).substring(0, 4);
            if (map.containsKey(owner)) {
                map.put(owner, new ExcelPojo(owner, map.get(owner).getSuccess() + ((BigInteger) sop[1]).intValue(), 0));
            } else {
                map.put(owner, new ExcelPojo(owner, ((BigInteger) sop[1]).intValue(), 0));
            }
        }

        for (Object[] sop : this.getFail(dateStr, code)) {
            String owner = ((String) sop[1]).substring(0, 4);
            if (map.containsKey(owner)) {
                map.get(owner).setFail(map.get(owner).getFail()+((BigDecimal) sop[0]).intValue());
            } else {
                map.put(owner, new ExcelPojo(owner, 0,((BigDecimal) sop[0]).intValue()));
            }
        }
        return map;
    }

}
