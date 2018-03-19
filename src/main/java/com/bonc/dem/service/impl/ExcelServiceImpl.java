package com.bonc.dem.service.impl;

import com.bonc.dem.config.ExcelConfig;
import com.bonc.dem.enumc.City;
import com.bonc.dem.pojo.ExcelPojo;
import com.bonc.dem.service.ExcelService;
import com.bonc.dem.util.DateUtils;
import com.bonc.dem.util.ExcelUtils;
import com.bonc.dem.util.FileUtil;
import com.bonc.dem.util.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ExcelServiceImpl implements ExcelService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ExcelConfig excelConfig;

    @Autowired
    ExcelUtils excelUtil;

    XSSFWorkbook workbook;


    /***
     *  1.先判断附件区有无要发送的附件
     *          有：前一天的excel位模板继续写    无：clone模板继续写
     *
     */
    @Override
    public void record(Map map) {

        if (!FileUtil.isAttachmentExist(excelConfig.getAttachmentName(DateUtils.getYesterday()))) {
            workbook = excelUtil.getWorkbook(FileUtil.getResourceDir("templates/" + excelConfig.getTemplatesName()));
            excelUtil.getNewSheet(workbook, String.format("%d月订单生产量明细", DateUtils.getMonth(new Date())));
        } else {
            workbook = excelUtil.getWorkbook(FileUtil.getResourceDir("attachment/" + excelConfig.getAttachmentName(DateUtils.getYesterday())));
        }

        //获取日期判断是本月第一天就新起sheet
        if (1 == DateUtils.getDay(new Date())) {
            excelUtil.getNewSheet(workbook, String.format("%d月订单生产量明细", DateUtils.getMonth(new Date())));
        }

        //获取最后一页的最后一行 写入
        data2Excel(workbook.getSheetAt(workbook.getNumberOfSheets() - 1), map);
        excelUtil.createExcel(workbook, String.format("%s\\%s", FileUtil.getResourceDir("attachment"), excelConfig.getAttachmentName(new Date())));

    }

    private void data2Excel(XSSFSheet sheet, Map<String, ExcelPojo> map) {
        sheet.shiftRows(sheet.getLastRowNum(), sheet.getLastRowNum(), 1);
        XSSFRow row = sheet.createRow(sheet.getLastRowNum() - 1);
        //日期
        XSSFCell cell0 = excelUtil.setCellValue(row, 0, DateUtils.parseDateToStr(new Date(), DateUtils.DATE_FORMAT_YYYY_MM_DD));
        excelUtil.setDateStyle(workbook, cell0);

        int proCount = 0;
        int proSuccess = 0;

        //for (Map.Entry<String, ExcelPojo> entry : map.entrySet()) {
        //    for(City city : City.values()){
        //        if(StringUtils.equals(entry.getKey(), city.getValue())){
        //            this.setValue(row, city.getIndex(), entry.getValue().getAmount(), entry.getValue().getSuccess());
        //            proCount += entry.getValue().getAmount();
        //            proSuccess += entry.getValue().getSuccess();
        //        }
        //    }
        //}

        for(City city : City.values()){
            this.setValue(row, city.getIndex(), 0, 0);
            for (Map.Entry<String, ExcelPojo> entry : map.entrySet()) {
                if(StringUtils.equals(entry.getKey(), city.getValue())){
                    this.setValue(row, city.getIndex(), entry.getValue().getAmount(), entry.getValue().getSuccess());
                    proCount += entry.getValue().getAmount();
                    proSuccess += entry.getValue().getSuccess();
                }
            }
        }

        //全省
        this.setValue(row, 0, proCount, proSuccess);
    }

    private void setValue(XSSFRow row, int index, int count, int success) {
        excelUtil.setDataStyle(workbook, excelUtil.setCellValue(row, index * 3 + 1, count));
        excelUtil.setDataStyle(workbook, excelUtil.setCellValue(row, index * 3 + 2, success));
        excelUtil.setPercentStyle(workbook, excelUtil.setCellValue(row, index * 3 + 3, 0 == count ? 0 : (double) success / count));
    }

}
