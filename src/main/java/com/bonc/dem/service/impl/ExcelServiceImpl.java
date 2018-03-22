package com.bonc.dem.service.impl;

import com.bonc.dem.MyApplicationRunner;
import com.bonc.dem.config.ExcelConfig;
import com.bonc.dem.entity.ExcelEntity;
import com.bonc.dem.enumc.City;
import com.bonc.dem.repository.ExcelRepository;
import com.bonc.dem.service.ExcelService;
import com.bonc.dem.util.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    ExcelConfig excelConfig;

    @Autowired
    ExcelUtils excelUtil;

    @Autowired
    ExcelRepository excelRepository;

    XSSFWorkbook workbook;


    /***
     *  1.先判断附件区有无要发送的附件
     *          有：前一天的excel位模板继续写    无：clone模板继续写
     *
     */
    @Override
    public void makeExcel() {

        if (!AttachmentUtils.isFileExist(excelConfig.getAttachmentPath() + excelConfig.getAttachmentName(DateUtils.getYesterday()))) {
            workbook = excelUtil.getWorkbook(AttachmentUtils.getResourceDir("templates/" + excelConfig.getTemplatesName()));
            excelUtil.getNewSheet(workbook, String.format("%d月订单生产量明细", DateUtils.getMonth(new Date())));
        } else {
            workbook = excelUtil.getWorkbook(new File(excelConfig.getAttachmentPath() + excelConfig.getAttachmentName(DateUtils.getYesterday())));
        }

        //获取日期判断是本月第一天就新起sheet
        if (1 == DateUtils.getDay(new Date())) {
            excelUtil.getNewSheet(workbook, String.format("%d月订单生产量明细", DateUtils.getMonth(new Date())));
        }

        List<String> days = excelRepository.findAllDate("201707%");
        int rowIndex = workbook.getSheetAt(workbook.getActiveSheetIndex()).getLastRowNum();
        for (String day : days) {
            writeOneLine(rowIndex, day);
            rowIndex ++;
        }
        writeCount(rowIndex);
        excelUtil.createExcel(workbook, excelConfig.getAttachmentPath() + excelConfig.getAttachmentName(new Date()));
    }

    private void writeOneLine(int rowIndex, String day) {
        XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
        XSSFRow row = sheet.createRow(rowIndex);
        //日期
        XSSFCell cell0 = excelUtil.setCellValue(row, 0, day);
        excelUtil.setDateStyle(workbook, cell0);

        int proCount = 0;
        int proSuccess = 0;

        for (City city : City.values()) {
            this.setValue(row, city.getIndex(), null, null);
            List<ExcelEntity> list = excelRepository.findByDate(day);
            for (ExcelEntity ee : list) {
                if (StringUtils.equals(city.getValue(), ee.getCity())) {
                    this.setValue(row, city.getIndex(), ee.getAmount(), ee.getSuccess());
                    if (null != ee.getAmount())
                        proCount += ee.getAmount();

                    if (null != ee.getSuccess())
                        proSuccess += ee.getSuccess();
                    break;
                }
            }
        }

        MyApplicationRunner.result[0] = String.valueOf(proCount);
        MyApplicationRunner.result[1] = String.valueOf(proSuccess);
        MyApplicationRunner.result[2] = MathUtils.getPercent(proSuccess, proCount);

        //全省
        this.setValue(row, 0, proCount, proSuccess);
    }

    private void writeCount(int rowIndex) {
        String date = "201707%";
        XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
        XSSFRow row = sheet.createRow(rowIndex);
        //本月合计
        XSSFCell cell0 = excelUtil.setCellValue(row, 0, "本月合计");
        excelUtil.setDataStyle(workbook, cell0);

        int proCount = 0;
        int proSuccess = 0;

        for (City city : City.values()) {
            Integer amount = excelRepository.findAmountByCityAndMonth(city.getValue(), date);
            Integer success = excelRepository.findSuccessByCityAndMonth(city.getValue(), date);
            this.setValue(row,
                    city.getIndex(),
                    amount,
                    success);
            if (null != amount)
                proCount += amount;

            if (null != success)
                proSuccess += success;
        }

        this.setValue(row, 0, proCount, proSuccess);
    }


    private void setValue(XSSFRow row, int index, Integer count, Integer success) {
        excelUtil.setDataStyle(workbook, excelUtil.setCellValue(row, index * 3 + 1, count));
        excelUtil.setDataStyle(workbook, excelUtil.setCellValue(row, index * 3 + 2, success));
        excelUtil.setDataStyle(workbook, excelUtil.setCellValue(row, index * 3 + 3, MathUtils.getPercent(success, count)));
    }

}
