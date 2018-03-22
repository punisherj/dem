package com.bonc.dem.service.impl;

import com.bonc.dem.entity.ExcelEntity;
import com.bonc.dem.pojo.ExcelPojo;
import com.bonc.dem.repository.ExcelRepository;
import com.bonc.dem.repository.ShopOrderRepository;
import com.bonc.dem.service.OrderCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderCollectServiceImpl implements OrderCollectService {

    @Autowired
    ShopOrderRepository shopOrderRepository;

    @Autowired
    ExcelRepository excelRepository;

    @Override
    public List<Object[]> getSuccess(String time, Integer code) {
        return shopOrderRepository.createAccountSuccess(time, code);
    }

    @Override
    public List<Object[]> getFail(String time, Integer code) {
        return shopOrderRepository.createAccountFail(time, code);
    }

    @Override
    public void getExcelData(String dateStr, Integer code) {

        Map<String, ExcelPojo> map = new HashMap<>();
        for (Object[] sop : this.getSuccess(dateStr, code)) {
            map.put((String) sop[0], new ExcelPojo((String) sop[0], ((BigInteger) sop[1]).intValue(), null));
        }

        for (Object[] sop : this.getFail(dateStr, code)) {
            if (map.containsKey(sop[1])) {
                map.get(sop[1]).setFail(((BigDecimal) sop[0]).intValue());
            } else {
                map.put((String) sop[1], new ExcelPojo((String) sop[1], null, ((BigDecimal) sop[0]).intValue()));
            }
        }
        this.saveExcel(map, dateStr);
    }

    private void saveExcel(Map<String, ExcelPojo> map, String date) {
        for (Map.Entry<String, ExcelPojo> entry : map.entrySet()) {
            ExcelPojo ep = entry.getValue();
            excelRepository.save(new ExcelEntity(ep.getAmount(), ep.getSuccess(), ep.getCity(), date));
        }
    }
}
