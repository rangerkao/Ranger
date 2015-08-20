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
  	
   
	//�]�w�Ĥ@�� �x�s�� �� �ݩ� cellStyle �ŧi�˦�
	HSSFCellStyle cellStyle  = wb.createCellStyle();
	cellStyle.setWrapText(true);  //�x�s��۰ʴ��C  cellStyle
    
  	//�H�U�}�l����
	row = sheet1.createRow(0);  //���޳��O�q0�}�l��_
	cell = row.createCell(0);
	cell.setCellValue("Hello World!!\n�I������");
	//�t�X��r�Y��cell�e��
	sheet1.autoSizeColumn(0); 
	
	//���Xcell    new CellRangeAddress(sRow,eRow,sCell,eCell)
	sheet1.addMergedRegion(new CellRangeAddress(rowCount-1,rowCount-1,cellCount,cellCount+6));
	
	
	//��������
   
	//�x�s excel �Ȧs��
	String ExcelTempFilePath ="export_service_hours_ministry_excelTemp.xls";
   	FileOutputStream fos =new FileOutputStream(WebDirPath + ExcelTempFilePath);
   	wb.write(fos);
   	fos.close();
   	wb.close();
%>
</body>
</html>