<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ page import="java.sql.*,java.util.*,java.text.*,javax.naming.*,javax.sql.*,java.io.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
<%
	String WebDirPath = request.getRealPath("/");
	String ExcelTempFilePath ="export_service_hours_ministry_excelTemp.xlsx";
	FileInputStream fis = new FileInputStream(WebDirPath + ExcelTempFilePath);
	
	SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd_HHmmss"); //����榡
	String fileNameDate = SDF.format(new java.util.Date());//�⤵�Ѫ�����榡�Ʀr��
	String filename = fileNameDate + "_sfle060.xlsx";
	
	 //�ǳ����ϥΪ̤U���ɮ�
	 out.clear();
	 //�@�w�n�[charset=iso-8859-1�A�_�h��󤺮e�|�ýX
	 //�ѦҡGhttp://www.west263.com/info/html/chengxusheji/Javajishu/20080225/33537.html
	 response.setContentType("application/octet-stream; charset=iso-8859-1;");
	 response.setHeader("content-disposition","attachment; filename="+filename);
	
	 int byteRead;//�]�wint�A���@�U�nŪ�ɥΪ�
	
	  //fis.read()�|�}�l�@��byte�@��byteŪ�ɡAŪ�쪺byte�ǵ�byteRead    
	  //�Yfis.read()�Ǧ^-1�A���Ū���F
	
	  while(-1 != (byteRead = fis.read()))
	    {
	      out.write(byteRead);
	    }
	  fis.close(); 

%>
</body>
</html>