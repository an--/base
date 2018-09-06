package base.tool.file.excel;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * workbook生成工具 Created by an on 15/4/2.
 */
public class WorkbookWriter {

    public static final short defaultMainTitleRowHeight = 700;
    public static final short defaultMaintitleFontHeight = 280;

    public static final short defaultTitleRowHeight = 500;
    public static final short defaultTitleFontHeight = 220;


    public static final short defaultDataRowHeight = 300;
    public static final short defaultDataFontHeight = 180;

    public static final short defauletColumnWeight = 3800;

    private Workbook workbook;

    private byte sheetIndex;

    private String sheetName;

    private String mainTitle;

    /**
     * 在表格中显示的列名称
     */
    private String[] columnTitles;

    /**
     * 传入数据 map 对象的 key 名称，与 columnTitles 顺序对应
     */
    private String[] fieldNames;

    /**
     * 数据，每个 map 对应一行数据，key 值对应 fieldNames 中的值
     */
    private List<Map<String, Object>> recordMaps;

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
        if (null == workbook) {
            throw new NullPointerException("workbook cant be null ");
        }
        this.sheetIndex = (byte) this.workbook.getNumberOfSheets();
        initStyle();
    }

    public WorkbookWriter(XSSFWorkbook workbook, byte sheetIndex) {
        if (null == workbook) {
            throw new NullPointerException("workbook cant be null ");
        }
        this.sheetIndex = sheetIndex;
        initStyle();
    }


    /**
     * 通过 表头，数据 map 字段名称，数据 map 列表构建一个 WorkbookWriter 对象
     *
     * @param columnTitles
     * @param fieldNames
     * @param records
     */
    public WorkbookWriter(String[] columnTitles, String[] fieldNames,
            List<Map<String, Object>> records) {
        this();
        this.columnTitles = columnTitles;
        this.fieldNames = fieldNames;
        this.recordMaps = records;
    }


    /**
     * 将 workbook 写入输出流
     *
     * @param out 输出流
     * @throws IOException
     */
    public void write(OutputStream out) throws IOException {
        buildWorkbook().write(out);
    }


    /**
     * 在sheet中填充数据
     */
    public void fillSheet(Sheet sheet, int startRowI) {
        int recordSize = recordMaps.size();
        int columnSize = fieldNames.length;
        for (int recordI = 0; recordI < recordSize; recordI++) {
            Map<String, Object> record = recordMaps.get(recordI);
            Row row = sheet.createRow(recordI + startRowI);
            if (null != dataRowHeight) {
                row.setHeight(dataRowHeight);
            }
            for (int columni = 0; columni < columnSize; columni++) {
                if (null != this.columnweight) {
                    sheet.setColumnWidth(columni, this.columnweight);
                }
                Cell cell = row.createCell(columni);
                Object cellValue = record.get(fieldNames[columni]);
                if (null == cellValue) {
                    cell.setCellType(CellType.BLANK);
                } else {
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(cellValue.toString());
                }
                if (null != dataCellStyle) {
                    cell.setCellStyle(dataCellStyle);
                }
            }
        }
    }

    /**
     * 生成 workbook
     */
    public Workbook buildWorkbook() {
        Sheet sheet;
        int numberOfSheets = workbook.getNumberOfSheets();
        if (numberOfSheets <= this.sheetIndex) {
            do {
                sheet = workbook.createSheet();
            } while (++numberOfSheets <= this.sheetIndex);
        } else {
            sheet = workbook.getSheetAt(this.sheetIndex);
        }
        if (null != sheetName) {
            workbook.setSheetName(0, sheetName);
        }

        int rowIndex = 0;
        int columnSize;
        if (null == fieldNames) {
            fieldNames = getFieldNamsByRecords(recordMaps);
        }

        if (null == columnTitles) {
            columnTitles = fieldNames;
        } else if (columnTitles.length < fieldNames.length) {
            String[] _columnTitles = new String[fieldNames.length];
            System.arraycopy(columnTitles, 0, _columnTitles, 0, columnTitles.length);
            System.arraycopy(fieldNames, columnTitles.length, _columnTitles, columnTitles.length,
                    fieldNames.length - columnTitles.length);
            this.columnTitles = _columnTitles;
        }

        columnSize = columnTitles.length;
        if (null != mainTitle) {
            Row mainTitleRow = sheet.createRow(rowIndex++);
            if (null != mainTitleRowHeight) {
                mainTitleRow.setHeight(mainTitleRowHeight);
            }
            Cell mainTitleCell = mainTitleRow.createCell(0);
            mainTitleCell.setCellType(CellType.STRING);
            mainTitleCell.setCellValue(mainTitle);
            if (null != mainTitleCellStyle) {
                mainTitleCell.setCellStyle(mainTitleCellStyle);
            }
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnSize - 1));
        }
        Row titleRow = sheet.createRow(rowIndex++);
        if (null != titleRowHeight) {
            titleRow.setHeight(titleRowHeight);
        }
        for (int columnI = 0; columnI < columnSize; columnI++) {
            Cell titleCell = titleRow.createCell(columnI);
            titleCell.setCellType(CellType.STRING);
            titleCell.setCellValue(columnTitles[columnI]);
            if (null != columnTitleCellStyle) {
                titleCell.setCellStyle(columnTitleCellStyle);
            }
        }

        fillSheet(sheet, rowIndex);

        return workbook;
    }

    /**
     * 获取结果集中的所有字段名
     */
    public final String[] getFieldNamsByRecords(List<Map<String, Object>> records) {
        Map<String, Object> record0 = records.get(0);
        Set<String> fieldNameSet = record0.keySet();
        this.fieldNames = new String[fieldNameSet.size()];
        int i = 0;
        for (Iterator<String> it = fieldNameSet.iterator(); it.hasNext(); ) {
            fieldNames[i++] = it.next();
        }
        return this.fieldNames;
    }

    private void initStyle() {
        this.mainTitleRowHeight = (null == this.mainTitleRowHeight ? this.defaultMainTitleRowHeight
                : this.mainTitleRowHeight);
        this.titleRowHeight = (null == this.titleRowHeight ? this.defaultTitleRowHeight
                : this.titleRowHeight);
        this.dataRowHeight = (null == this.dataRowHeight ? this.defaultDataRowHeight
                : this.dataRowHeight);
        this.columnweight = (null == this.columnweight ? this.defauletColumnWeight
                : this.columnweight);
        if (null == mainTitleCellStyle) {
            mainTitleCellStyle = workbook.createCellStyle();
            mainTitleCellStyle.setAlignment(HorizontalAlignment.CENTER);
            mainTitleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font mainTitleFont = workbook.createFont();
            mainTitleFont.setFontHeight(defaultMaintitleFontHeight);
            mainTitleCellStyle.setFont(mainTitleFont);
        }
        if (null == columnTitleCellStyle) {
            columnTitleCellStyle = workbook.createCellStyle();
            columnTitleCellStyle.setAlignment(HorizontalAlignment.CENTER);
            columnTitleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font titleFont = workbook.createFont();
            titleFont.setFontHeight(defaultTitleFontHeight);
            columnTitleCellStyle.setFont(titleFont);
        }
        if (null == dataCellStyle) {
            dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setAlignment(HorizontalAlignment.LEFT);
            dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font dataFont = workbook.createFont();
            dataFont.setFontHeight(defaultDataFontHeight);
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

    public List<Map<String, Object>> getRecordMaps() {
        return recordMaps;
    }

    public void setRecordMaps(List<Map<String, Object>> recordMaps) {
        this.recordMaps = recordMaps;
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
