<%@ page language="java" contentType="text/html; charset=BIG5"   pageEncoding="BIG5"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>

  <script>
  //read table dom to excel,maybe occur memory not enough error，function input (table id name,file name)
/*  var tableToExcel = (function() {
      var uri = 'data:application/vnd.ms-excel;base64,'
        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
        , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
        , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
      return function(table, name) {
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
        window.location.href = uri + base64(format(template, ctx))
      }
    })() */
</script>

</head>
<body>
<%
//將畫面table導出成xls串流
SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd_HHmmss"); //日期格式
String fileNameDate = SDF.format(new java.util.Date());//把今天的日期格式化字串
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "inline; filename="+ fileNameDate+"_excel.xls");  

//將畫面資料匯出CSV串流
/* 	String downloadFileName = "myData.csv";
	response.setContentType( "text/csv" );
	response.setHeader( "Content-Disposition", "attachment; filename=" + downloadFileName );  */



%>
</body>
</html>