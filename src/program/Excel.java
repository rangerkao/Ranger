package program;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Excel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Excel() throws Exception {
		super();
	}
	//private InputStream excelStream;  //輸出變量
	private static String excelFileName; //下載文件名稱

	String dataList;
	String colHead;
	String reportName;
	
	public InputStream createExcel() throws JSONException, UnsupportedEncodingException{
		
		//proccess head
		JSONArray jaHead = new JSONArray(java.net.URLDecoder.decode(colHead,"UTF-8"));
		List<Map<String,String>> head = new ArrayList<Map<String,String>>();
		
		for(int i = 0 ; i < jaHead.length() ; i++){
			JSONObject jo = jaHead.getJSONObject(i);
			Map<String,String> m = new HashMap<String,String>();
			
			String name = jo.getString("name");
			String colName = jo.getString("col");
			if(!"button".equals(name)){
				m.put("name", name);
				m.put("col", colName);
				head.add(m);
			}
		}
		
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		JSONArray jaData = new JSONArray(java.net.URLDecoder.decode(dataList,"UTF-8"));
		for(int i = 0 ; i < jaData.length() ; i++){
			JSONObject jo = jaData.getJSONObject(i);			
			String [] names = JSONObject.getNames(jo);

			Map<String,Object> m = new HashMap<String,Object>();
		
			for(int j = 0 ; j< names.length ; j++){
				m.put(names[j], jo.getString(names[j]));
			}
			data.add(m);
		}

		return createExcel(head,data);
	}
	
	public static InputStream createExcel(List<Map<String,String>> head,List<Map<String,Object>> data){
		InputStream excelStream = null;
		int rowN = 0;
		try {  
			//第一步，創建webbook文件
            HSSFWorkbook wb = new HSSFWorkbook();  

            //第二步，添加sheet
            HSSFSheet sheet = wb.createSheet("表格1");  
            //第三步添加表頭第0行
            HSSFRow row = sheet.createRow(rowN++);  
            //第四步，設定樣式
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            //第五部，建立單元格
            HSSFCell cell;  

            
            for(int i = 0 ; i < head.size() ; i++){
    			String name=head.get(i).get("name");
    			
				cell = row.createCell(i);  
                cell.setCellValue(name);  
                cell.setCellStyle(style); 
    		}		

  
          //第六部，寫入內容
            for (int i = 0; i < data.size(); i++) {  
                row = sheet.createRow(rowN++);  
                
                for(int j = 0 ; j < head.size() ; j++){
                	String col=head.get(j).get("col");
                	Map<String,Object> m = data.get(i);
                	if(data.get(i).get(col)!=null)
                		row.createCell(j).setCellValue(m.get(col).toString());
                	else
                		row.createCell(j).setCellValue("");
        		}	
            }  

          //第七步，放置串流  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] fileContent = os.toByteArray();  
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  
            
            excelStream = is;            
            
            //reportName=java.net.URLDecoder.decode(reportName,"UTF-8");
            
            /*if(rName==null || "".equals(rName))
            	rName="report";            	
            
            excelFileName = rName+".xls"; //���W��
*/        }  
        catch(Exception e) {  
            e.printStackTrace();  
        }  
  
        return excelStream;  
	}

/*	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}*/

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public String getDataList() {
		return dataList;
	}

	public void setDataList(String dataList) {
		this.dataList = dataList;
	}

	public String getColHead() {
		return colHead;
	}

	public void setColHead(String colHead) {
		this.colHead = colHead;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


}
