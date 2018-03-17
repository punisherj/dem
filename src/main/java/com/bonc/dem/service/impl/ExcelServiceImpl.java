package com.bonc.dem.service.impl;

import com.bonc.dem.config.ExcelConfig;
import com.bonc.dem.dao.PersonDao;
import com.bonc.dem.service.ExcelService;
import com.bonc.dem.util.DateUtil;
import com.bonc.dem.util.ExcelUtil;
import com.bonc.dem.util.FileUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExcelServiceImpl implements ExcelService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ExcelConfig excelConfig;

    @Autowired
    PersonDao personDao;

    @Autowired
    ExcelUtil excelUtil;

    XSSFWorkbook workbook;


    /***
     *  1.先判断附件区有无要发送的附件
     *          有：前一天的excel位模板继续写    无：clone模板继续写
     *
     */
    @Override
    public void record() {

        if (!FileUtil.isAttachmentExist(excelConfig.getAttachmentName())) {
            workbook = excelUtil.getWorkbook(FileUtil.getResourceDir("templates/" + excelConfig.getTemplatesName()));
            excelUtil.getNewSheet(workbook, String.format("%d月订单生产量明细", DateUtil.getMonth(new Date())));
        } else {
            workbook = excelUtil.getWorkbook(FileUtil.getResourceDir("attachment/" + excelConfig.getAttachmentName(DateUtil.getYesterday())));
        }

        //获取日期判断是本月第一天就新起sheet
        if (1 == DateUtil.getDay(new Date())) {
            excelUtil.getNewSheet(workbook, String.format("%d月订单生产量明细", DateUtil.getMonth(new Date())));
        }

        Map<Integer, Integer[]> map = new HashMap<>();
        map.put(1, new Integer[]{21, 15});
        map.put(2, new Integer[]{40, 20});
        map.put(3, new Integer[]{0, 0});
        map.put(4, new Integer[]{0, 0});
        map.put(5, new Integer[]{0, 0});
        map.put(6, new Integer[]{20, 15});
        map.put(7, new Integer[]{20, 15});
        map.put(8, new Integer[]{20, 15});
        map.put(9, new Integer[]{20, 15});
        map.put(10, new Integer[]{20, 15});
        map.put(11, new Integer[]{20, 15});
        map.put(12, new Integer[]{20, 15});
        map.put(13, new Integer[]{20, 15});
        map.put(14, new Integer[]{15, 15});

        //获取最后一页的最后一行 写入
        data2Excel(workbook.getSheetAt(workbook.getNumberOfSheets() - 1), map);
        excelUtil.createExcel(workbook, String.format("%s\\%s", FileUtil.getResourceDir("attachment"), excelConfig.getAttachmentName(new Date())));

    }

    private void data2Excel(XSSFSheet sheet, Map<Integer, Integer[]> map) {
        sheet.shiftRows(sheet.getLastRowNum(), sheet.getLastRowNum(), 1);
        XSSFRow row = sheet.createRow(sheet.getLastRowNum()-1);
        //日期
        XSSFCell cell0 = excelUtil.setCellValue(row, 0, DateUtil.parseDateToStr(new Date(),DateUtil.DATE_FORMAT_YYYY_MM_DD));
        excelUtil.setDateStyle(workbook, cell0);

        int proCount = 0;
        int proSuccess = 0;

        for (Map.Entry<Integer, Integer[]> entry : map.entrySet()) {
            this.setValue(row,entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
            proCount += entry.getValue()[0];
            proSuccess += entry.getValue()[1];
        }

        //全省
        this.setValue(row,0, proCount, proSuccess);
    }

    private void setValue(XSSFRow row, int index, int count, int success) {
        excelUtil.setDataStyle(workbook, excelUtil.setCellValue(row, index * 3 + 1, count));
        excelUtil.setDataStyle(workbook, excelUtil.setCellValue(row, index * 3 + 2, success));
        excelUtil.setPercentStyle(workbook, excelUtil.setCellValue(row, index * 3 + 3, 0==count?0:(double) success / count));
    }

}
