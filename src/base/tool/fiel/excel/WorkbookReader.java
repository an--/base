package base.tool.fiel.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * workbook 读取工具
 * Created by an on 15/4/2.
 */
public class WorkbookReader {


    /**
     * workbook inStream to List Map,all cell Value Type is String;
     * @param inStream  workbook InputStream
     * @param cellNameArray （可以为空）field name,result map key;如果column 对应的field name为 null, 该 column 的数据使用 columnIndex 作为key`
     * @param sheetsIndex   index of sheet;（可以为空）如果 null 默认为 0
     * @param targetRows    int[];指定读取数据的行号，如果null,使用 startRowNum,startRowNum;
     * @param startRowNum   int;数据开始的行号，默认为 sheet.getStartRowNum();
     * @param endRowNum     int;数据结束的行号，默认为 sheet.getEndRowNum();
     * @param startColumn   int;指定数据起始列，如果 null,则使用 row.getFirstCellNum();如果targetColumns != null 优先使用 targetColums
     * @param endColumn     int;指定数据结束列，如果 null,则使用 row.getLastCellNum();如果targetColumns != null 优先使用 targetColums
     * @param targetColumns int[];指定读取数据的列号，如果null,使用 firstColumn,endColumn;
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<Map<String,String>> readStreamToListMapStringValue( InputStream inStream, String[] cellNameArray , Integer sheetsIndex , int[] targetRows, Integer startRowNum , Integer endRowNum , int[] targetColumns, Integer startColumn , Integer endColumn ) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(inStream);
        return readToListMapStringValue(workbook, cellNameArray, sheetsIndex ,targetRows, startRowNum, endRowNum,targetColumns,startColumn,endColumn);
    }


    /**
     * workbook to list
     * @param workbook
     * @return
     */
    public static List<Map<String,Object>> readToListMapObject(Workbook workbook , String[] cellNameArray , Integer sheetsIndex , Integer startRowNum , Integer endRowNum ) {
        if( null == workbook ){
            return null;
        }

        List<Map<String,Object>> rowList = new ArrayList<Map<String, Object>>();
        int sheetSize = workbook.getNumberOfSheets();
        if( null == startRowNum ){
            startRowNum = 0;
        }
        if( 0 < sheetSize ){
            for( int sheeti = 0 ; sheeti < sheetSize ; sheeti++ ){
                if( null != sheetsIndex && !( new Integer(sheeti) ).equals( sheetsIndex ) ){
                    continue;
                }
                Sheet sheet = workbook.getSheetAt(sheeti);
                rowList.addAll(sheetToListMapObject(sheet, cellNameArray, startRowNum, endRowNum));
            }

            return rowList;
        }else{
            return rowList;
        }
    }

    /**
     * workbook  to List Map,all cell Value Type is String.
     * @param workbook  workbook
     * @param cellNameArray （可以为空）field name,result map key;如果column 对应的field name为 null, 该 column 的数据使用 columnIndex 作为key`
     * @param sheetsIndex   index of sheet;（可以为空）如果 null 默认为 0
     * @param targetRows    int[];指定读取数据的行号，如果null,使用 startRowNum,startRowNum;
     * @param startRowNum   int;数据开始的行号，默认为 sheet.getStartRowNum();
     * @param endRowNum     int;数据结束的行号，默认为 sheet.getEndRowNum();
     * @param startColumn   int;指定数据起始列，如果 null,则使用 row.getFirstCellNum();如果targetColumns != null 优先使用 targetColums
     * @param endColumn     int;指定数据结束列，如果 null,则使用 row.getLastCellNum();如果targetColumns != null 优先使用 targetColums
     * @param targetColumns int[];指定读取数据的列号，如果null,使用 firstColumn,endColumn;
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<Map<String,String>> readToListMapStringValue(Workbook workbook , String[] cellNameArray , Integer sheetsIndex , int[] targetRows , Integer startRowNum , Integer endRowNum , int[] targetColumns , Integer startColumn , Integer endColumn ) {
        if( null == workbook ){
            return null;
        }

        List<Map<String,String>> rowList = new ArrayList<Map<String, String>>();
        int sheetSize = workbook.getNumberOfSheets();

        if( 0 < sheetSize ){
            for( int sheeti = 0 ; sheeti < sheetSize ; sheeti++ ){
                if( null != sheetsIndex && !( new Integer(sheeti) ).equals( sheetsIndex ) ){
                    continue;
                }
                Sheet sheet = workbook.getSheetAt(sheeti);
                rowList.addAll(sheetToListMapStringValue(sheet, cellNameArray, targetRows, startRowNum, endRowNum,targetColumns,startColumn,endColumn));
            }

            return rowList;
        }else{
            return rowList;
        }
    }

    /**
     * sheet to List Map,
     * if allCellToString is true, all cell setCellType(Cell.CELL_TYPE_STRING) .
     * @param sheet
     * @param cellNameArray
     * @param startRowNum
     * @param endRowNum
     * @return
     */
    public static List<Map<String,Object>> sheetToListMapObject( Sheet sheet , String[] cellNameArray , Integer startRowNum , Integer endRowNum ){
        if( null == sheet ){
            return null;
        }

        List<Map<String,Object>> rowList = new ArrayList<Map<String, Object>>();

        Iterator<Row> rowIterator = sheet.rowIterator();

        if( null == startRowNum ){
            startRowNum = sheet.getFirstRowNum();
        }
        if( null == endRowNum ){
            endRowNum = sheet.getLastRowNum();
        }

        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            int rowNum = row.getRowNum();
            if( startRowNum <= rowNum && endRowNum >= rowNum ){
                rowList.add(rowToMapObject( row, cellNameArray, null));
            }
        }
        return rowList;
    }

    /**
     *
     * @param sheet
     * @param cellNameArray
     * @param startRowNum
     * @param endRowNum
     * @return
     */
    public static List<Map<String,String>> sheetToListMapStringValue( Sheet sheet , String[] cellNameArray , int[] targetRows, Integer startRowNum , Integer endRowNum , int[] targetColumns , Integer firstColumnNum , Integer lastColumnNum ){
        if( null == sheet ){
            return null;
        }

        List<Map<String,String>> rowList = new ArrayList<Map<String, String>>();

        if( null == targetColumns ){
            if( null == firstColumnNum || 0 > firstColumnNum ){
                firstColumnNum = 0;
            }
            if( null != lastColumnNum && 0 <= lastColumnNum ){
                targetColumns = new int[lastColumnNum - firstColumnNum + 1 ];
                for( int i = firstColumnNum ; i <= lastColumnNum ; i++ ){
                    targetColumns[i] = i;
                }
            }
        }

        if( null != targetRows ){
            for( int i = 0 ,l = targetRows.length; i < l ; i++ ){
                Row row = sheet.getRow(targetRows[i]);
                rowList.add(rowToMapStringValue( row, cellNameArray, targetColumns));
            }
        }else{
            if( null == startRowNum ){
                startRowNum = sheet.getFirstRowNum();
            }
            if( null == endRowNum ){
                endRowNum = sheet.getLastRowNum();
            }
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                int rowNum = row.getRowNum();
                if( startRowNum <= rowNum && endRowNum >= rowNum ){
                    rowList.add(rowToMapStringValue( row, cellNameArray, targetColumns));
                }
            }
        }
        return rowList;
    }



    /**
     * row to map
     * @param row
     * @param cellNameArray  cellName as key
     * @param targetColumns   target columns,if targetColumn does not contain column index , ignored;
     * @return
     */
    public static Map<String,Object> rowToMapObject( Row row , String[] cellNameArray , int[] targetColumns ){
        if( null == row ){
            return null;
        }
        Map<String,Object> rowMap = new HashMap<String, Object>();
        int lastColumnNum = row.getLastCellNum(),firstColumnNum = row.getFirstCellNum();
        if( null != targetColumns ){
            for( int i = 0,l= targetColumns.length; i >= firstColumnNum && i < l && i <= lastColumnNum ; i++ ){
                Cell cell = row.getCell(i);
                String cellName = i + "";
                if( null != cellNameArray && i < cellNameArray.length){
                    cellName = cellNameArray[i];
                }
                Object cellValue = getCellOriginalValue(cell);
                rowMap.put(cellName , cellValue);
            }
        }else{
            Iterator<Cell> cellIterator = row.cellIterator();
            while ( cellIterator.hasNext() ){
                Cell cell = cellIterator.next();
                int columnI = cell.getColumnIndex();
                String cellName = columnI + "";
                Object cellValue = getCellOriginalValue(cell) ;
                if( null != cellNameArray && columnI < cellNameArray.length){
                    cellName = cellNameArray[columnI];
                }
                rowMap.put(cellName , cellValue);
            }
        }
        return rowMap;
    }


    /**
     * row to map,all cell value to String.
     * @param row
     * @param cellNameArray
     * @param targetColumns
     * @return
     */
    public static Map<String,String> rowToMapStringValue( Row row , String[] cellNameArray , int[] targetColumns ){
        if( null == row ){
            return null;
        }
        Map<String,String> rowMap = new HashMap<String, String>();
        int lastColumnNum = row.getLastCellNum(), firstColumnNum = row.getFirstCellNum();
        if( null != targetColumns ){
            for( int i = 0,l= targetColumns.length; i < l && i >= firstColumnNum && i <= lastColumnNum ; i++ ){
                Cell cell = row.getCell(i);
                String cellName = i + "";
                if( null != cellNameArray && i < cellNameArray.length){
                    cellName = cellNameArray[i];
                }
                String cellValue = getCellStringValue(cell);
                rowMap.put(cellName , cellValue);
            }
        }else{
            Iterator<Cell> cellIterator = row.cellIterator();
            while ( cellIterator.hasNext() ){
                Cell cell = cellIterator.next();
                int columnI = cell.getColumnIndex();
                String cellName = columnI + "";
                if( null != cellNameArray && columnI < cellNameArray.length){
                    cellName = cellNameArray[columnI];
                }
                String cellValue = getCellStringValue(cell);
                rowMap.put(cellName , cellValue);
            }
        }
        return rowMap;
    }

    /**
     * return cell orginal value
     * @param cell
     * @return
     */
    public static Object getCellOriginalValue( Cell cell ){
        if( null == cell ){
            return null;
        }
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_BLANK :
                return null;
            case Cell.CELL_TYPE_ERROR :
                return null;
            case Cell.CELL_TYPE_BOOLEAN :
                return  cell.getBooleanCellValue();
            case Cell.CELL_TYPE_NUMERIC :
                return cell.getNumericCellValue();
            case Cell.CELL_TYPE_STRING :
                return cell.getStringCellValue();
            default:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
        }
    }

    /**
     * set cellType,then getStringCellValue
     * @param cell
     * @return
     */
    public static String getCellStringValue( Cell cell ){
        if( null == cell ){
            return null;
        }
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_STRING :
                return cell.getStringCellValue();
            default:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
        }
    }

}
