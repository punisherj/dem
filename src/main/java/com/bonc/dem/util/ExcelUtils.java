package com.bonc.dem.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class ExcelUtils {

    public XSSFWorkbook getWorkbook(File f) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workbook;
    }

    public void createExcel(XSSFWorkbook workbook, String path) {

        if (!new File(path).exists()) {
            new File(path.substring(0, path.lastIndexOf('/'))).mkdirs();
        }

        try {
            FileOutputStream out = new FileOutputStream(path);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delSheet(XSSFWorkbook workbook, int deleteSheetIndex) {
        workbook.removeSheetAt(deleteSheetIndex);
    }

    public void cloneSheet(XSSFWorkbook workbook, int cloneSheetIndex, String newName) {
        workbook.cloneSheet(cloneSheetIndex, newName);
    }

    public void getNewSheet(XSSFWorkbook workbook, String newName) {
        this.cloneSheet(workbook, 0, newName);
        workbook.setActiveSheet(workbook.getNumberOfSheets() - 1);
        workbook.setSheetHidden(0, true);
    }

    public Integer getLastRow(XSSFSheet sheet) {
        return sheet.getLastRowNum() + 1;
    }

    public void setDateStyle(XSSFWorkbook workbook, XSSFCell cell) {
        XSSFCellStyle ztStyle = this.setDataStyle(workbook, cell);
        ztStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        ztStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font ztFont = workbook.createFont();
        ztFont.setBold(true);
        ztFont.setFontHeightInPoints((short) 11);
        ztFont.setFontName("微软雅黑");
        ztStyle.setFont(ztFont);
        cell.setCellStyle(ztStyle);
    }

    public XSSFCellStyle setDataStyle(XSSFWorkbook workbook, XSSFCell cell) {
        XSSFCellStyle ztStyle = workbook.createCellStyle();
        ztStyle.setAlignment(HorizontalAlignment.CENTER);
        ztStyle.setBorderTop(BorderStyle.THIN);
        ztStyle.setBorderBottom(BorderStyle.THIN);
        ztStyle.setBorderLeft(BorderStyle.THIN);
        ztStyle.setBorderRight(BorderStyle.THIN);
        Font ztFont = workbook.createFont();
        ztFont.setFontHeightInPoints((short) 11);
        ztFont.setFontName("微软雅黑");
        ztStyle.setFont(ztFont);
        cell.setCellStyle(ztStyle);
        return ztStyle;
    }

    public XSSFCellStyle setPercentStyle(XSSFWorkbook workbook, XSSFCell cell) {
        XSSFCellStyle ztStyle = this.setDataStyle(workbook, cell);
        ztStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0%"));
        return ztStyle;
    }

    public XSSFCell setCellValue(XSSFRow row, Integer index, Object value) {
        //全省总量
        XSSFCell cell = row.getCell(index);
        if (null == cell) {
            cell = row.createCell(index);
        }
        if (value instanceof Double) {
            cell.setCellValue((double) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else {
            cell.setCellValue((String) value);
        }
        return cell;
    }
}
