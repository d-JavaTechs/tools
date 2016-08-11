package pn.eric.poi;

/**
 * @author Eric
 */
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * *
 */

public class POIExcelUtil {

    /** 总行数 */
    private int totalRows = 0;

    /** 总列数 */
    private int totalCells = 0;


    /**限制的总行数*/
    private int limitTotalRows =500;

    /** 扫描全部的sheet 默认为false*/
    private boolean scanAllSheet = false;

    /** 构造方法 */
    public POIExcelUtil()
    {}

    private DecimalFormat df = new DecimalFormat("0.00");

    /**
     * <ul>
     * <li>Description:[根据文件名读取excel文件]</li>
     * <li>Created by [Huyvanpull] [Jan 20, 2010]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     *
     * @param fileName
     * @return
     * @
     * @throws Exception
     */
    public List<ArrayList<String>> read(String fileName)
    {
        List<ArrayList<String>> dataLst = new ArrayList<ArrayList<String>>();

        try
        {
            boolean isExcel2003 = true;
            /** 对文件的合法性进行验证 */
            if (fileName.matches("^.+\\.(?i)(xlsx)$"))
            {
                isExcel2003 = false;
            }

            File file = new File(fileName);
            /** 调用本类提供的根据流读取的方法 */
            dataLst = read(new FileInputStream(file), isExcel2003);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        /** 返回最后读取的结果 */
        return dataLst;
    }

    /**
     * <ul>
     * <li>Description:[根据流读取Excel文件]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     *
     * @param inputStream
     * @param isExcel2003
     * @return
     * @
     */
    public List<ArrayList<String>> read(InputStream inputStream, boolean isExcel2003)
    {
        List<ArrayList<String>> dataLst = null;
        try
        {
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = isExcel2003 ? new HSSFWorkbook(inputStream)
                    : new XSSFWorkbook(inputStream);
            dataLst = read(wb);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return dataLst;
    }

    /**
     * <ul>
     * <li>Description:[得到总行数]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     *
     * @return
     */
    public int getTotalRows()
    {
        return totalRows;
    }

    /**
     * <ul>
     * <li>Description:[得到总列数]</li>
     * <ul>
     *
     * @return
     */
    public int getTotalCells()
    {
        return totalCells;
    }

    /**
     * <li>Description:[读取数据]</li>
     *
     * @param wb
     * @return
     * @
     */
    public List<ArrayList<String>> read(Workbook wb)
    {
        List<ArrayList<String>> dataLst = new ArrayList<>();

        if(scanAllSheet){
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                getSheet(wb, dataLst,i);
            }
        }else {
            getSheet(wb, dataLst,0);
        }

        return dataLst;
    }

    private void getSheet(Workbook wb, List<ArrayList<String>> dataLst,int i) {
        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(i);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if (this.totalRows >= 1 && sheet.getRow(0) != null)
        {
            //用第一行的单元格个数来确定列数不是很好，取起始位置中间位置以及最好位置的行数的最大值似乎更好。
            int startCells = sheet.getRow(0).getLastCellNum();
            int endCells = sheet.getRow(this.totalRows-1) == null ? 0:sheet.getRow(this.totalRows-1).getLastCellNum();
            int middleCells = sheet.getRow(this.totalRows/2) == null ? 0 : sheet.getRow(this.totalRows/2).getLastCellNum();
            this.totalCells = (startCells>=middleCells)?(startCells>=endCells?startCells:endCells):(middleCells>=endCells?middleCells:endCells);
        }

        /** 循环Excel的行  zheng.sk,如果去掉第一行，则从1开始循环*/
        for (int r = 1; r < this.totalRows; r++)
        {
            Row row = sheet.getRow(r);
            //如果每行第一列为空则默认这列都为空
            if (row == null || row.getCell(0) == null)
            {
                continue;
            }

            ArrayList<String> rowLst = new ArrayList<String>();

            /** 循环Excel的列   */
            for (short c = 0; c < row.getLastCellNum(); c++)
            {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (cell == null)
                {
                    rowLst.add(cellValue);
                    continue;
                }
                //zheng.sk  对于数字的类型转换
                if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
                    //读取excel整数的时候默认会加个小数点,为了不让加小数点特作此判断
                    DecimalFormat df = new DecimalFormat("0.000");
//                    String numberValue = String.valueOf(cell.getNumericCellValue());
                    String numberValue = df.format(cell.getNumericCellValue());//防止读取成为科学计数法
                    if(numberValue.contains(".")){
                        String[] splitStr = numberValue.split("\\.");
                        String round  = splitStr[0];
                        String decimal  = splitStr[1];
                        if (Long.valueOf(decimal) == 0){
                            rowLst.add(round);
                            //由于日期在Excel内是以double值存储的，所以日期格式要特别注意。统一转换成为"yyyy-MM-dd HH:mm:ss"格式
                        }else if(HSSFDateUtil.isCellDateFormatted(cell)){
                            rowLst.add(dateFormat(cell.getDateCellValue(),"yyyy-MM-dd HH:mm:ss"));
                        }else {
                            rowLst.add(String.valueOf(df.format(cell.getNumericCellValue())));
                        }
                    }else{
                        rowLst.add(numberValue);
                    }
                }else{
                    rowLst.add(cell.getStringCellValue());
                }
            }

            //如果某行全部列的值都为空或者空字符串则认为此行为空，不做处理
            boolean isNotEmpty = false;
            for (String cellValue : rowLst) {
                if (cellValue != null && cellValue.trim().length() > 0)isNotEmpty = true;
            }

            if (isNotEmpty){
                dataLst.add(rowLst);
            }
        }
    }


    public String dateFormat(Date date,String fmt){
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        return  format.format(date);
    }


    /**
     * <ul>
     * <li>Description:[测试main方法]</li>
     * <li>Midified by [modifier] [modified time]</li>
     * <ul>
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        List<ArrayList<String>> dataLst = new POIExcelUtil().read("d:/aa.xls");
        System.out.println("rowSize:"+dataLst.size());
        for(int i=0;i<dataLst.size();i++){
            ArrayList<String> cellLst = dataLst.get(i);
            System.out.println("cellSize:"+cellLst.size());
            for(int j=0;j<cellLst.size();j++){
                System.out.print(cellLst.get(j)+"|");
            }
        }
        System.out.println("OK");
    }


    public boolean isScanAllSheet() {
        return scanAllSheet;
    }

    public void setScanAllSheet(boolean scanAllSheet) {
        this.scanAllSheet = scanAllSheet;
    }
}