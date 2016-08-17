package pn.eric.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
/**
 * @author Eric
 * @date
 */
public class ExcelUtil<T> {


    public  void generateSingleKsserviceSheetExcel(String fileDir,String  sheetName,String[] titleO,String[] titleT, List<T> datas,Class<T> clz){
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        //新建文件
        FileOutputStream out = null;
        try {
            //添加表头
            Row row = workbook.getSheet(sheetName).createRow(0);    //创建第一行
            for(int i = 0;i < titleO.length;i++){
                Cell cell = row.createCell(i);
                cell.setCellValue(titleO[i]);
            }
            Row rowData ;
            Cell cell;
            for(int i=0;i<datas.size();i++){
                rowData = sheet.createRow(i+1);
                for (int columnIndex = 0; columnIndex < titleO.length; columnIndex++) {  //遍历表头
                    String title = titleO[columnIndex];
                    String UTitle = Character.toUpperCase(title.charAt(0))+ title.substring(1, title.length()); // 使其首字母大写;
                    String methodName  = "get"+UTitle;
                    Method method = clz.getDeclaredMethod(methodName); // 设置要执行的方法
                    String dataT = method.invoke(datas.get(i)).toString(); // 执行该get方法,即要插入的数据
                    cell = rowData.createCell(columnIndex);
                    cell.setCellValue(dataT);
                }
            }

            for(int i = 0;i < titleT.length;i++){
                cell = row.getCell(i);
                cell.setCellValue(titleT[i]);
            }

            out = new FileOutputStream(fileDir);
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


