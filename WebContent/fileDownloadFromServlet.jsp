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
	
	SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd_HHmmss"); //日期格式
	String fileNameDate = SDF.format(new java.util.Date());//把今天的日期格式化字串
	String filename = fileNameDate + "_sfle060.xlsx";
	
	 //準備讓使用者下載檔案
	 out.clear();
	 //一定要加charset=iso-8859-1，否則文件內容會亂碼
	 //參考：http://www.west263.com/info/html/chengxusheji/Javajishu/20080225/33537.html
	 response.setContentType("application/octet-stream; charset=iso-8859-1;");
	 response.setHeader("content-disposition","attachment; filename="+filename);
	
	 int byteRead;//設定int，等一下要讀檔用的
	
	  //fis.read()會開始一個byte一個byte讀檔，讀到的byte傳給byteRead    
	  //若fis.read()傳回-1，表示讀完了
	
	  while(-1 != (byteRead = fis.read()))
	    {
	      out.write(byteRead);
	    }
	  fis.close(); 

%>
</body>
</html>