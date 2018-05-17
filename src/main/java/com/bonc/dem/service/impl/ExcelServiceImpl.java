package com.bonc.dem.service.impl;

import com.bonc.dem.MyApplicationRunner;
import com.bonc.dem.config.AttachmentConfig;
import com.bonc.dem.config.TemplateConfig;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private AttachmentConfig attachment;

    @Autowired
    private TemplateConfig template;

    @Autowired
    private ExcelUtils excelUtil;

    @Autowired
    private ExcelRepository excelRepository;

    XSSFWorkbook workbook;

    @Override
    public void makeExcel(String date) {

        String attachmentPath = attachment.getPath();
        String yesterdayFilePath = attachmentPath + attachment.getName(DateUtils.getYesterday(date));

        boolean isYesterdayExit = FileUtils.isFileExist(yesterdayFilePath);

        if (!isYesterdayExit) {
            workbook = excelUtil.getWorkbook(template.getTemplateFile());
        } else {
            workbook = excelUtil.getWorkbook(new File(yesterdayFilePath));
        }

        //获取日期判断是本月第一天就新起sheet
        if (!isYesterdayExit || StringUtils.equals("01", StringUtils.getDateStrDay(date))) {
            excelUtil.getNewSheet(workbook, String.format("%s月订单生产量明细", StringUtils.getDateStrMonth(date)));
        }

        int rowIndex = 2;
        for (String day : excelRepository.findAllDate(date.substring(0, date.length() - 2) + "%")) {
            this.writeOneLine(rowIndex, day);
            rowIndex++;
        }
        this.writeCount(rowIndex, date.substring(0, date.length() - 2) + "%");
        excelUtil.createExcel(workbook, attachmentPath + attachment.getName(date));
    }

    private void writeOneLine(int rowIndex, String day) {
        XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
        XSSFRow row = sheet.createRow(rowIndex);
        //日期
        XSSFCell cell0 = excelUtil.setCellValue(row, 0, day.replace("-","/"));
        excelUtil.setDateStyle(workbook, cell0);

        int proCount = 0;
        int proSuccess = 0;

        List<ExcelEntity> list = excelRepository.findByDate(day);
        Map<String, ExcelEntity> excelMap = list.stream().collect(Collectors.toMap(ExcelEntity::getCity, a -> a));

        for (City city : City.values()) {
            ExcelEntity ee = excelMap.get(city.getValue());
            if(null != ee) {
                this.setValue(row, city.getIndex(), ee.getAmount(), ee.getSuccess());
                if (null != ee.getAmount()) {
                    proCount += ee.getAmount();
                }

                if (null != ee.getSuccess()) {
                    proSuccess += ee.getSuccess();
                }
            } else {
                this.setValue(row, city.getIndex(), null, null);
            }
        }

        MyApplicationRunner.tc.setCount(String.valueOf(proCount));
        MyApplicationRunner.tc.setSuccess(String.valueOf(proSuccess));
        MyApplicationRunner.tc.setSuccessPercent(MathUtils.getPercent(proSuccess, proCount));

        //全省
        this.setValue(row, 0, proCount, proSuccess);
    }

    private void writeCount(int rowIndex, String date) {
        XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
        XSSFRow row = sheet.createRow(rowIndex);
        //本月合计
        XSSFCell cell0 = excelUtil.setCellValue(row, 0, "本月合计");
        excelUtil.setCountStyle(workbook, cell0);

        int proCount = 0;
        int proSuccess = 0;

        for (City city : City.values()) {
            Integer amount = excelRepository.findAmountByCityAndMonth(city.getValue(), date);
            Integer success = excelRepository.findSuccessByCityAndMonth(city.getValue(), date);
            this.setValue(row, city.getIndex(), amount, success);
            if (null != amount) {
                proCount += amount;
            }

            if (null != success) {
                proSuccess += success;
            }
        }
        this.setValue(row, 0, proCount, proSuccess);
    }


    private void setValue(XSSFRow row, int index, Integer count, Integer success) {
        excelUtil.setDataStyle(workbook, excelUtil.setCellValue(row, index * 3 + 1, count));
        excelUtil.setDataStyle(workbook, excelUtil.setCellValue(row, index * 3 + 2, success));
        excelUtil.setDataStyle(workbook, excelUtil.setCellValue(row, index * 3 + 3, MathUtils.getPercent(success, count)));
    }

}
