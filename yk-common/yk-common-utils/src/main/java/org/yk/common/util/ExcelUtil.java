package org.yk.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


@SuppressWarnings("rawtypes")
public class ExcelUtil {

	private final static Logger log = LoggerFactory.getLogger(ExcelUtil.class);
	private final static String XLS = "xls";
	private final static String XLSX = "xlsx";
	
	 /** 
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上 
     *  
     * @param title 
     *            表格标题名 
     * @param headers 
     *            表格属性列名数组 
     * @param dataset 
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据) 
     * @param out 
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中 
     * @param pattern 
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd" 
     */ 
	public static void exportExcel(String title, String[] headers, Collection dataset, OutputStream out){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth(20);
		writeHeader(sheet,headers);
		writeContent(workbook,sheet,title,headers,dataset);
		//导出并关闭流
		try {
			workbook.write(out);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} finally {
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
	}
	
	/**
	 * 对excel的简单读取
	 * 
	 * 注意：定义的clazz 类定义字段必须用string类型
	 * excel字段要与class中的字段顺序保持一致
	 * */
	public static <T> List<T> readExcel(String fileName,InputStream is,Class<T> clazz) {
		Workbook workbook = getWorkBook(fileName, is);
		List<T> result = new ArrayList<T>();
		if (workbook != null) {
			//循环所有sheet
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if (sheet == null) {
					continue;
				}
				int firstRowNum = sheet.getFirstRowNum();
				if(firstRowNum == 0){
					firstRowNum = 1;
				}
				int lastRowNum = sheet.getLastRowNum();
				// 循环所有行
				for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}
					T obj = createInstanceByRow(row,clazz);
					if(obj == null){
						continue;
					}
					result.add(obj);
				}
			}
		}
		return result;
	}
	
	private static <T> T createInstanceByRow(Row row,Class<T> clazz){
		// 获得当前行的开始列
		int firstCellNum = row.getFirstCellNum();
		// 获得当前行的列数
		int lastCellNum = row.getLastCellNum();
		// 循环当前行
		T obj = null;
		try {
			Field[] fields = clazz.getDeclaredFields();
			obj = clazz.newInstance();
			for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
				Cell cell = row.getCell(cellNum);
				if (cell == null) {
					continue;
				}
				String cellValue = getCellValue(cell);
				Field field = fields[cellNum];
				String fieldName = field.getName();
				String getMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				Method method = clazz.getMethod(getMethodName, String.class);
				method.invoke(obj, cellValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	@SuppressWarnings("deprecation")
	private static String getCellValue(Cell cell){
		String cellValue = "";
		if(cell == null){
			return cellValue;
		}
		//把数字当成String来读，避免出现1读成1.0的情况
		if(cell.getCellTypeEnum() == CellType.NUMERIC){
			cell.setCellType(CellType.STRING);
		}
		//判断数据的类型
        switch (cell.getCellType()){
	        case Cell.CELL_TYPE_NUMERIC: //数字
	            cellValue = String.valueOf(cell.getNumericCellValue());
	            break;
	        case Cell.CELL_TYPE_STRING: //字符串
	            cellValue = String.valueOf(cell.getStringCellValue());
	            if(!StringUtils.isEmpty(cellValue)){
	            	cellValue=cellValue.trim();
	            }
	            break;
	        case Cell.CELL_TYPE_BOOLEAN: //Boolean
	            cellValue = String.valueOf(cell.getBooleanCellValue());
	            break;
	        case Cell.CELL_TYPE_FORMULA: //公式
	            cellValue = String.valueOf(cell.getCellFormula());
	            break;
	        case Cell.CELL_TYPE_BLANK: //空值 
	            cellValue = "";
	            break;
	        case Cell.CELL_TYPE_ERROR: //故障
	            cellValue = "非法字符";
	            break;
	        default:
	            cellValue = "未知类型";
	            break;
        }
		return cellValue;
	}
	
	private static Workbook getWorkBook(String fileName,InputStream is) {
    	//创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			if(fileName.endsWith(XLS)){
				//2003
				workbook = new HSSFWorkbook(is);
			}else if(fileName.endsWith(XLSX)){
				//2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		return workbook;
	}
	
	
   
	
	private static void writeHeader(HSSFSheet sheet,String[] headers){
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
	}

	private static void writeContent(HSSFWorkbook workbook,HSSFSheet sheet,String title,String[] headers,Collection dataset) {
		if(dataset == null || dataset.size() == 0){
			return;
		}
		Field[] fields = dataset.iterator().next().getClass().getDeclaredFields();
		// 遍历集合数据，产生数据行
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		String pattern = "yyyy-MM-dd";
		Iterator<Object> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			Object t = it.next();
			index++;
			HSSFRow row = sheet.createRow(index);
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			int j = 0;
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					if (fieldName.equalsIgnoreCase("serialVersionUID")) {
						continue;
					}
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(j, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6,index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串处理
						if (value == null) {
							value = "";
						} else {
							textValue = value.toString();
						}
					}
					HSSFCell cell = row.createCell(j);
					j++;
					if (textValue != null) {
						HSSFRichTextString richString = new HSSFRichTextString(textValue);
						cell.setCellValue(richString);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}
}
