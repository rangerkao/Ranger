<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ page import="java.sql.*,java.util.*,java.text.*,javax.naming.*,javax.sql.*,java.io.*" %>
<%@ page  import="org.apache.poi.hssf.usermodel.*,org.apache.poi.hssf.util.*,org.apache.xmlbeans.XmlObject,org.apache.poi.ss.usermodel.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
<%

	//file name
 	String tfilename = "test.xlsx";
	//space real path
	String WebDirPath = request.getRealPath("/");
	//file real path
	String ExcelSampleFilePath = WebDirPath + tfilename;
 
	//read file
	FileInputStream  fs  = new FileInputStream(ExcelSampleFilePath);
	//new Book
	HSSFWorkbook  wb         = new HSSFWorkbook(fs);
	fs.close();

	//new sheet
	HSSFSheet     sheet1     = wb.getSheetAt(0);
	//new row
	HSSFRow       row          = null;  
	//new cell
	HSSFCell        cell           = null; 
   
	int rowCount=0;
  	int cellCount=0;
  	
  	//set style
  	HSSFCellStyle style1 = wb.createCellStyle();
  	//set background
	style1.setFillForegroundColor(HSSFColor.BLUE.index);
	style1.setFillPattern((short)1);

	Font font = wb.createFont();
    font.setColor(HSSFColor.WHITE.index);
	style1.setFont(font); 
  	
   
	//設定第一種 儲存格 的 屬性 cellStyle 宣告樣式
	HSSFCellStyle cellStyle  = wb.createCellStyle();
	cellStyle.setWrapText(true);  //儲存格自動換列  cellStyle
    
  	//以下開始塞資料
	row = sheet1.createRow(0);  //索引都是從0開始算起
	cell = row.createCell(0);
	cell.setCellValue("Hello World!!\n點部落格");
	//配合文字縮放cell寬度
	sheet1.autoSizeColumn(0); 
	
	//結合cell    new CellRangeAddress(sRow,eRow,sCell,eCell)
	sheet1.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount-1,cellCount,cellCount+6));
	
	
	//結束塞資料
   
	//儲存 excel 暫存檔
	String ExcelTempFilePath ="export_service_hours_ministry_excelTemp.xls";
   	FileOutputStream fos =new FileOutputStream(WebDirPath + ExcelTempFilePath);
   	wb.write(fos);
   	fos.close();
   	wb.close();
%>
</body>
</html>