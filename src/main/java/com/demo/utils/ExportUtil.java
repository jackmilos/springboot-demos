package com.demo.utils;

import com.demo.dao.mapper.UserMapper;
import com.demo.entity.User;
import com.demo.service.UserService;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出到Excel表格辅助类
 */
@Component
public class ExportUtil {
    @Autowired
    private UserService userService;

    @SuppressWarnings("dep-ann")
    public void ExportExcel(){
        XSSFWorkbook workbook =  new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("0");
        for (int i = 0;i < 9; i++){
            sheet.setColumnWidth(i,4300);
        }

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT); // 水平居中
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 上下居中

        /**
         * 标题样式 样式
         */
        XSSFFont titleFont = workbook.createFont();
        titleFont.setFontHeight(24);
        titleFont.setBold(true);
        CellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
        titleCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 上下居中
        titleCellStyle.setFont(titleFont);

        /**
         * 主 标题 在这里插入主标题
         */
        Row titleRow;
        Cell titleCell;
        sheet.addMergedRegion(new CellRangeAddress((short) 0,(short) 2,(short) 0,(short) 8));
        for (int i = 0; i < 2; i++) {
            titleRow = sheet.createRow(i);
            for (int j = 0; j < 9; j++) {
                titleCell = titleRow.createCell(j);
                titleCell.setCellType(CellType.STRING);
                titleCell.setCellStyle(titleCellStyle);
                titleCell.setCellValue("test");
            }
        }

        List<User> userList = userService.listUser();

        String[] coltitle = {"ID","姓名","性别","年龄","账号"};
        /**
         * 列 标题 在这里插入标题
         */
        Row rowLabel;
        Cell cellLabel;
        for (int i = 3; i < 4; i++) {
            rowLabel = sheet.createRow(i);
            for (int j = 0; j < 5; j++) {
                cellLabel = rowLabel.createCell(j);
                cellLabel.setCellType(CellType.STRING);
                cellLabel.setCellValue(coltitle[j]);
            }
        }

        /**
         * 列 数据 在这里插入数据
         */
        System.out.println("正在导出数据。。。");
        Row rowCheck;
        Cell cellCheck;
        for (int i = 0; i < userList.size(); i++) {
            rowCheck = sheet.createRow(i+4);
            for (int j = 0; j < 5; j++) {
                cellCheck = rowCheck.createCell(j);
                cellCheck.setCellType(CellType.STRING);
                cellCheck.setCellStyle(cellStyle);
//                cellCheck.setCellValue("i="+i+";j="+j);
                switch (j){
                    case 0:cellCheck.setCellValue(userList.get(i).getId()); continue;
                    case 1:cellCheck.setCellValue(userList.get(i).getU_name()); continue;
                    case 2:cellCheck.setCellValue(userList.get(i).getSex()==1?"男":userList.get(i).getSex()==2?"女":"未知"); continue;
                    case 3:cellCheck.setCellValue(userList.get(i).getAge()); continue;
                    case 4:cellCheck.setCellValue(userList.get(i).getUsername()); continue;
                }
            }
        }
        /**
         * 页脚
         */
        //当前用户桌面
//        File desktopDir = FileSystemView.getFileSystemView()
//                .getHomeDirectory();
//        String desktopPath = desktopDir.getAbsolutePath();
//        File file = new File(desktopPath + "\\这是生成的Excel表格.xlsx");

        exportOutPutExcel("D:\\users.xlsx",workbook);
        System.out.println("导出成功");
    }
    /**
     * 设置Excel页脚
     */
    public void setExcelFooterName(String customExcelFooterName,int setExcelFooterNumber,XSSFWorkbook workbook){
        workbook.setSheetName(setExcelFooterNumber,customExcelFooterName);
    }
    /**
     * 输出流 导出Excel到桌面
     */
    public void exportOutPutExcel(String exportPositionPath,XSSFWorkbook workbook){
        try{
            File file = new File(exportPositionPath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
