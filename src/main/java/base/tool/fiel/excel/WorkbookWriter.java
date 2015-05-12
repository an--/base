package base.tool.fiel.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * workbook生成工具
 * Created by an on 15/4/2.
 */
public class WorkbookWriter {

    public static final short defaultMainTitleRowHeight = 800;

    public static final short defaultTitleRowHeight = 700;

    public static final short defaultDataRowHeight = 600;

    public static final short defaultMaintitleFontHeight = 400;
    public static final short defaultTitleFontHeight = 320;
    public static final short defaultDataFontHeight = 280;

    public static final short defauletColumnWeight = 5000;

    private Workbook workbook;

    private byte sheetIndex;

    private String sheetName;

    private String mainTitle;

    private String[] columnTitles;

    private String[] fieldNames;

    private List<Map<String , Object>> records;

    private CellStyle mainTitleCellStyle;

    private CellStyle dataCellStyle;

    private CellStyle columnTitleCellStyle;

    private Short mainTitleRowHeight;

    private Short titleRowHeight;

    private Short dataRowHeight;

    private Short columnweight;

    public WorkbookWriter() {
        workbook = new XSSFWorkbook();
        this.sheetIndex = 0;
        initStyle();
    }

    public WorkbookWriter(XSSFWorkbook workbook) {
        if( null == workbook){
            throw new NullPointerException( "workbook cant be null " );
        }
        this.sheetIndex = (byte) this.workbook.getNumberOfSheets();
        initStyle();
    }

    public WorkbookWriter(XSSFWorkbook workbook, byte sheetIndex) {
        if( null == workbook){
            throw new NullPointerException( "workbook cant be null " );
        }
        this.sheetIndex = sheetIndex;
        initStyle();
    }

    /**
     * 在sheet中填充数据
     * @param sheet
     */
    public void fillSheet( Sheet sheet , int startRowI ){
        int recordSize = records.size();
        int columnSize = fieldNames.length;
        for( int recordI = 0 ; recordI < recordSize ; recordI++ ){
            Map<String , Object> record = records.get(recordI);
            Row row = sheet.createRow(recordI+startRowI);
            if(null != dataRowHeight){
                row.setHeight(dataRowHeight);
            }
            for( int columni = 0 ; columni < columnSize ; columni++ ){
                if( null != this.columnweight){
                    sheet.setColumnWidth( columni , this.columnweight );
                }
                Cell cell = row.createCell(columni);
                Object cellValue = record.get(fieldNames[columni]);
                if( null == cellValue ){
                    cell.setCellType(cell.CELL_TYPE_BLANK);
                }else{
                    cell.setCellType( Cell.CELL_TYPE_STRING );
                    cell.setCellValue( cellValue.toString() );
                }
                if( null != dataCellStyle ){
                    cell.setCellStyle(dataCellStyle);
                }
            }
        }
    }

    /**
     * 生成 workbook
     * @return
     */
    public Workbook buildWorkbook(){
        Sheet sheet;
        int numberOfSheets = workbook.getNumberOfSheets();
        if( numberOfSheets <= this.sheetIndex ) {
            do {
                sheet = workbook.createSheet();
            } while (++numberOfSheets <= this.sheetIndex);
        }else{
            sheet = workbook.getSheetAt(this.sheetIndex);
        }
        if( null != sheetName ){
            workbook.setSheetName(0 , sheetName);
        }

        int rowIndex = 0;
        int columnSize;
        if( null == fieldNames ){
            fieldNames = getFieldNamsByRecords(records);
        }

        if( null == columnTitles ){
            columnTitles = fieldNames;
        }else if( columnTitles.length < fieldNames.length ){
            String[] _columnTitles = new String[fieldNames.length];
            System.arraycopy( columnTitles , 0 , _columnTitles , 0 , columnTitles.length );
            System.arraycopy( fieldNames , columnTitles.length , _columnTitles , columnTitles.length , fieldNames.length - columnTitles.length );
            this.columnTitles = _columnTitles;
        }

        columnSize = columnTitles.length;
        if( null != mainTitle ){
            Row mainTitleRow = sheet.createRow(rowIndex++);
            if( null != mainTitleRowHeight ){
                mainTitleRow.setHeight( mainTitleRowHeight );
            }
            Cell mainTitleCell = mainTitleRow.createCell( 0 );
            mainTitleCell.setCellType(Cell.CELL_TYPE_STRING);
            mainTitleCell.setCellValue(mainTitle);
            if( null != mainTitleCellStyle ){
                mainTitleCell.setCellStyle(mainTitleCellStyle);
            }
            sheet.addMergedRegion( new CellRangeAddress(0,0,0,columnSize-1));
        }
        Row titleRow = sheet.createRow(rowIndex++);
        if( null != titleRowHeight ){
            titleRow.setHeight(titleRowHeight);
        }
        for( int columnI = 0; columnI < columnSize ; columnI++ ){
            Cell titleCell = titleRow.createCell(columnI);
            titleCell.setCellType(Cell.CELL_TYPE_STRING);
            titleCell.setCellValue( columnTitles[columnI] );
            if( null != columnTitleCellStyle ){
                titleCell.setCellStyle(columnTitleCellStyle);
            }
        }

        fillSheet(sheet , rowIndex );

        return workbook;
    }

    /**
     * 获取结果集中的所有字段名
     * @param records
     * @return
     */
    public final String[] getFieldNamsByRecords( List<Map<String,Object>> records ){
        Map<String,Object> record0 = records.get(0);
        Set<String> fieldNameSet = record0.keySet();
        this.fieldNames = new String[fieldNameSet.size() ];
        int i = 0;
        for(Iterator<String> it = fieldNameSet.iterator() ; it.hasNext() ;){
            fieldNames[i++] = it.next();
        }
        return this.fieldNames;
    }

    private void initStyle( ){
        this.mainTitleRowHeight = ( null == this.mainTitleRowHeight ? this.defaultMainTitleRowHeight : this.mainTitleRowHeight );
        this.titleRowHeight = ( null == this.titleRowHeight ? this.defaultTitleRowHeight : this.titleRowHeight );
        this.dataRowHeight = ( null == this.dataRowHeight ? this.defaultDataRowHeight : this.dataRowHeight );
        this.columnweight = ( null == this.columnweight ? this.defauletColumnWeight : this.columnweight );
        if( null == mainTitleCellStyle ){
            mainTitleCellStyle = workbook.createCellStyle();
            mainTitleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            mainTitleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            Font mainTitleFont = workbook.createFont();
            mainTitleFont.setFontHeight( defaultMaintitleFontHeight );
            mainTitleCellStyle.setFont(mainTitleFont);
        }
        if( null == columnTitleCellStyle ){
            columnTitleCellStyle = workbook.createCellStyle();
            columnTitleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            columnTitleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            Font titleFont = workbook.createFont();
            titleFont.setFontHeight(defaultTitleFontHeight );
            columnTitleCellStyle.setFont(titleFont);
        }
        if( null == dataCellStyle ){
            dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
            dataCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            Font dataFont = workbook.createFont();
            dataFont.setFontHeight( defaultDataFontHeight );
            dataCellStyle.setFont(dataFont);
        }
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public byte getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(byte sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String[] getColumnTitles() {
        return columnTitles;
    }

    public void setColumnTitles(String[] columnTitles) {
        this.columnTitles = columnTitles;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String[] fieldNames) {
        this.fieldNames = fieldNames;
    }

    public List<Map<String, Object>> getRecords() {
        return records;
    }

    public void setRecords(List<Map<String, Object>> records) {
        this.records = records;
    }

    public CellStyle getMainTitleCellStyle() {
        return mainTitleCellStyle;
    }

    public void setMainTitleCellStyle(CellStyle mainTitleCellStyle) {
        this.mainTitleCellStyle = mainTitleCellStyle;
    }

    public CellStyle getDataCellStyle() {
        return dataCellStyle;
    }

    public void setDataCellStyle(CellStyle dataCellStyle) {
        this.dataCellStyle = dataCellStyle;
    }

    public CellStyle getColumnTitleCellStyle() {
        return columnTitleCellStyle;
    }

    public void setColumnTitleCellStyle(CellStyle columnTitleCellStyle) {
        this.columnTitleCellStyle = columnTitleCellStyle;
    }

    public Short getMainTitleRowHeight() {
        return mainTitleRowHeight;
    }

    public void setMainTitleRowHeight(Short mainTitleRowHeight) {
        this.mainTitleRowHeight = mainTitleRowHeight;
    }

    public Short getTitleRowHeight() {
        return titleRowHeight;
    }

    public void setTitleRowHeight(Short titleRowHeight) {
        this.titleRowHeight = titleRowHeight;
    }

    public Short getDataRowHeight() {
        return dataRowHeight;
    }

    public void setDataRowHeight(Short dataRowHeight) {
        this.dataRowHeight = dataRowHeight;
    }

    public Short getColumnweight() {
        return columnweight;
    }

    public void setColumnweight(Short columnweight) {
        this.columnweight = columnweight;
    }
}
