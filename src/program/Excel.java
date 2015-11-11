/**
 * 	<!-- Excel Action 下載 -->
    <action name="createExcel"  method="createExcel" class="action.Excel">  
        <result name="success" type="stream">  
            <!-- 下载文件的类型，如果你不知道是什么格式，可以去 tomcat\conf\web.xml下找 -->  
            <param name="contentType">application/vnd.ms-excel</param>  
            <!-- 返回流 excelStream为action中的流变量名称 -->  
            <param name="inputName">excelStream</param>  
            <!-- attachment 这个位置的参数挺特殊的，可以设置成下载时，是否出现个下载提示框，或者直接下载之类的。  
            fileName指定生成的文件名字(适合动态生成文件名，比如做报表时，一般都要说是几月的统计数据之类)为action中变量-->  
            <param name="contentDisposition">  
                attachment;filename=${excelFileName}  
            </param>  
            <param name="bufferSize">1024</param>  
        </result>  
    </action>  
 * 
 * 
 */

package program;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;



public class Excel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Excel() throws Exception {
		super();
	}
	private InputStream excelStream;  //輸出變量
	private String excelFileName; //下載文件名稱

	String dataList;
	String colHead;
	String reportName;
	
	public String createExcel(){
			
		
		try {  
			int rowN = 0;
			
			//第一步，創建webbook文件
            HSSFWorkbook wb = new HSSFWorkbook();  
            //第二步，添加sheet
            HSSFSheet sheet = wb.createSheet("表格1");  
            //第三步添加表頭第0行
            HSSFRow row = sheet.createRow(rowN++);  
            //第四步，設定樣式
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            //第五步，建立單元格
            HSSFCell cell;  
  
            
            
            //第六步，寫入內容
            
            //寫入Table Header
            JSONArray jColList = new JSONArray(java.net.URLDecoder.decode(colHead,"UTF-8"));
            
            for(int i = 0 ; i < jColList.length() ; i++){
    			JSONObject jCol = jColList.getJSONObject(i);
    			
    			String col=jCol.getString("name");
    			
    			if(!"button".equals(col)){
    				cell = row.createCell(i);  
                    cell.setCellValue(col);  
                    cell.setCellStyle(style); 
    			}
    		}		

  
          
            JSONArray jDataList = new JSONArray(java.net.URLDecoder.decode(dataList,"UTF-8"));
            
            for (int i = 0; i < jDataList.length(); i++) {  
            	JSONObject jData =jDataList.getJSONObject(i);
                row = sheet.createRow(rowN++);  
                
                for(int j = 0 ; j < jColList.length() ; j++){
        			JSONObject jCol = jColList.getJSONObject(j);
        			String col=jCol.getString("name");
        			if(!"button".equals(col)){
        				row.createCell(j).setCellValue(jData.get(jCol.getString("col")).toString()); 
        			}
        		}	
            }  

          //第七步，放置串流  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] fileContent = os.toByteArray();  
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  
  
            excelStream = is;             //���y  
            
            //reportName=java.net.URLDecoder.decode(reportName,"UTF-8");
            
            if(reportName==null || "".equals(reportName))
            	reportName="report";            	
            
            excelFileName = reportName+".xls"; //���W��
        }  
        catch(Exception e) {  
            e.printStackTrace();  
        }  
  
        return "success";  
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

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
