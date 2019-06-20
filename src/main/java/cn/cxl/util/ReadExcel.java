package cn.cxl.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ReadExcel {

    //读取Excel
    public Map<String,List<List<String>>>  readExcel(String path){
        String fileType=path.substring(path.lastIndexOf('.')+1);
        Map<String,List<List<String>>> map=new HashMap<String,List<List<String>>>();
        InputStream is=null;
        try{
            is=new FileInputStream(path);
            Workbook wb=null;
            if(fileType.equals("xls")){
                wb=new HSSFWorkbook(is);
            }else if (fileType.equals("xlsx")){
                wb=new XSSFWorkbook(is);
            }else {
                return null;
            }
            int sheetCount=wb.getNumberOfSheets();
            for(int i=0;i<sheetCount;i++){
                List<List<String>> list=new ArrayList<>();
                Sheet sheet=wb.getSheetAt(i);
                for (Row row:sheet) {
                    ArrayList<String> arrayList=new ArrayList<String>();
                    for (Cell cell:row){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        arrayList.add(cell.getStringCellValue());
                    }
                    list.add(arrayList);
                }
                map.put(wb.getSheetName(i),list);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    //写入excel
    public void writeExcel(String path,Map<String,List<List<String>>> map,Map<Integer,Integer> colWidthMap){
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建字体对象并编写字体类型和字体大小
        HSSFFont font=wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)11);
        //创建单元格样式并添加自定义样式的字体对象
        HSSFCellStyle style=wb.createCellStyle();
        style.setFont(font);
        for(String key:map.keySet()){
            HSSFSheet sheet=wb.createSheet(key);
            //如果设置了列宽，就循环遍历设置表格列的宽度
            if(map.size()!=0){
                for (Integer index:colWidthMap.keySet()) {
                    sheet.setColumnWidth(index,colWidthMap.get(index));
                }
            }

            List<List<String>> lists = map.get(key);
            int rows=0;
            for(List<String> list:lists){
                HSSFRow row=sheet.createRow(rows);
                int cells=0;
                for (String str:list){
                    HSSFCell cell=row.createCell(cells);
                    cell.setCellValue(str);
                    //单元格添加自定义的样式
                    cell.setCellStyle(style);
                    System.out.println("开始"+str);
                    cells++;
                }
                rows++;
            }
        }
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(path);
            wb.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
