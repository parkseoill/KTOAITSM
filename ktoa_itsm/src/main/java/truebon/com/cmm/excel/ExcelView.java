package truebon.com.cmm.excel;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : ExcelView.java
 * @Description : Excel View class
 * @Modification Information
 *
 * @author 이유리
 * @since 2022.10.18
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

public class ExcelView extends AbstractView {
	
	/** 로그설정 */
	private static final Logger LOGGER  = LoggerFactory.getLogger(ExcelView.class);

    private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Override
    protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook();

        setContentType(CONTENT_TYPE_XLSX);

        buildExcelDocument(model, workbook, request, response);
        
        // 파일명
        String sFilename = "";
        if(model.get("filename") != null){
            sFilename = (String)model.get("filename");
        }else if(request.getAttribute("filename") != null){
            sFilename = (String)request.getAttribute("filename");
        }else{
            sFilename = getClass().getSimpleName();
         }

        response.setContentType(getContentType());
        
        String header = request.getHeader("User-Agent");
        sFilename = sFilename.replaceAll("\r","").replaceAll("\n","");
        if(header.contains("MSIE") || header.contains("Trident") || header.contains("Chrome")){
            sFilename = URLEncoder.encode(sFilename,"UTF-8").replaceAll("\\+","%20");
            response.setHeader("Content-Disposition","attachment;filename="+sFilename+".xlsx;");
        }else{
            sFilename = new String(sFilename.getBytes("UTF-8"),"ISO-8859-1");
            response.setHeader("Content-Disposition","attachment;filename=\""+sFilename + ".xlsx\"");
        }
        
        ServletOutputStream out = response.getOutputStream();
        out.flush();
        workbook.write(out);
        out.flush();
    }
    
    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map model
    		, XSSFWorkbook wb, HttpServletRequest req
    		, HttpServletResponse resp) 
    		throws Exception {
    	
	        Map<String, Object> dataMap = (Map<String, Object>)model.get("dataMap");
	        XSSFCell cell = null;
	 
	        String sheetNm = (String) dataMap.get("sheetNm"); // 엑셀 시트 이름
	        String[] columnTitle = (String[]) dataMap.get("columnTitle"); // 각 컬럼 이름
	        String[] columnName = (String[]) dataMap.get("columnName"); // 각 컬럼의 변수 이름
	        List<EgovMap> dataList = (List<EgovMap>) dataMap.get("list"); // 데이터가 담긴 리스트 
	        
	        XSSFSheet sheet = wb.createSheet(sheetNm);
	        sheet.setDefaultColumnWidth(12);
	        
	        // 컬럼제목 삽입
	        for(int i=0; i<columnTitle.length; i++){
	            setText(getCell(sheet, 0, i), columnTitle[i]);
	            sheet.autoSizeColumn(i);
	            int columnWidth = (sheet.getColumnWidth(i))*5;
	            sheet.setColumnWidth(i, columnWidth);
	            
	            if(dataList.size() < 1){
	                cell = getCell(sheet, 1, i);
	                if(i==0){
	                    setText(cell, "등록된 정보가 없습니다.");
	                }
	            }
	        }
	        
	        // 컬럼리스트 삽입
	        if(dataList.size() > 0){
	            for (int i = 0; i<dataList.size(); i++) {
	            	
	            	EgovMap dataEgovMap = dataList.get(i);
	
	                for(int j=0; j<columnName.length; j++){
	                    String data = (String) dataEgovMap.get(columnName[j]);
	                    cell = getCell(sheet, 1 + i, j);
	                    setText(cell, data);
	                }
	            }
	        }else{ 
	            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnTitle.length-1));
	        }
    }

    /**
     * Excel Cell Get함수 
     * @param sheet - Cell을 가지는 Sheet
     * @param row - 열 위치정보를 가지는 int
     * @param col - 행 위치정보를 가지는 int
     * @return XSSFCell
     */
    protected XSSFCell getCell(XSSFSheet sheet, int row, int col) {
        XSSFRow sheetRow = sheet.getRow(row);
        if (sheetRow == null) {
            sheetRow = sheet.createRow(row);
        }
        XSSFCell cell = sheetRow.getCell((short) col);
        if (cell == null) {
            cell = sheetRow.createCell((short) col);
        }
        return cell;
    }

    /**
     * Excel Cell Set함수
     * @param cell - 값을 담으려는 cell
     * @param text - cell에 삽입할 정보가 담긴 text
     */
    protected void setText(XSSFCell cell, String text) {
        cell.setCellValue(text);
    }            
}
