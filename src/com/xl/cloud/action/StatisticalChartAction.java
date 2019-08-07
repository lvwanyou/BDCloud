package com.xl.cloud.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xl.cloud.bean.Caseinfo;
import com.xl.cloud.bean.Evidence;
import com.xl.cloud.bean.SuspectInfo;
import com.xl.cloud.bean.TicketdDTO;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.EsClient;
import com.xl.cloud.util.JsonUtil;

@Controller
public class StatisticalChartAction {
	private SqlDao sqlDao = new SqlDao();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final Logger logger = Logger.getLogger(BuildCollection.class);
	
	@RequestMapping(value = "/chart/dataTendency.php")
	public String importevidenceNoCase(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return "dataTendency";
	}

	/**
	 * 报表统计-数据趋势图
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/chart/getDataTendency.php")
	public void getDataTendency(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
        DateFormat df2 = new SimpleDateFormat("yyyy"); 
        String format2 = df2.format(new Date());
        int year =Integer.parseInt(format2);
        
		Calendar start = Calendar.getInstance();  
		start.set(2017, 0, 2);  
	    Long startTIme = start.getTimeInMillis();  
	    Calendar end = Calendar.getInstance();  
	    end.set(year, 12, 0); 
	    Long endTime = end.getTimeInMillis();  
	    Long oneDay = 1000 * 60 * 60 * 24l;  
	    Long time = startTIme;  
	    ArrayList<Long> arrayList = new ArrayList<Long>();
	    ArrayList<Long> arrayList2 = new ArrayList<Long>();
	    //所有数据
        Evidence evidence = new Evidence();
        List<Evidence> listfromMysqlLike = sqlDao.getListfromMysqlLike(evidence);
	    
        //黑客数据
        Evidence evidence2 = new Evidence();
        evidence2.setEvType(5);
        List<Evidence> listfromMysqlLike2 = sqlDao.getListfromMysqlLike(evidence2);
	    
	    while (time <= endTime) {  
	        Date d = new Date(time);  
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	        String format = df.format(d);
	        
	        //所有数据
	        long size=0;
	        for (Evidence evidence3 : listfromMysqlLike) {
	        	if(format.equals( evidence3.getAddTime().split(" ")[0])){
	        		size+=(long)Double.parseDouble(evidence3.getEvSize());
	        	}
			}
	        arrayList.add(size);
	      //黑客数据
	        long size2=0;
	        for (Evidence evidence3 : listfromMysqlLike2) {
	        	if(format.equals( evidence3.getAddTime().split(" ")[0])){
	        		size2+=(long)Double.parseDouble(evidence3.getEvSize());
	        	}
			}
	        arrayList2.add(size2);
	        time += oneDay;  
	    }  
	    Map<String, Object> map = new HashMap<String, Object>();
		map.put("arrayList", arrayList);
		map.put("arrayList2", arrayList2);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(map));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	/**
	 * 报表统计-数据趋势-数据列表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/chart/getEvidence.php")
	public void getEvidence(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		String pageno = request.getParameter("pageno");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String evType = request.getParameter("evType");
		String evName = request.getParameter("evName");
		int pageIndex = Integer.parseInt(pageno);
		Evidence evidence = new Evidence();
		List<Evidence> evidenceList = new ArrayList<Evidence>();
		List<Evidence> evidenceList2 = new ArrayList<Evidence>();
		List<Evidence> evidenceList3 = new ArrayList<Evidence>();
		if (evType != null && !"".equals(evType)) {
			if ("电子邮件".equals(evType)) {
				evType = "1";
			} else if ("综合文档".equals(evType)) {
				evType = "2";
			} else if ("电子话单".equals(evType)) {
				evType = "3";
			} else if ("手机取证".equals(evType)) {
				evType = "4";
			} else if ("黑客数据".equals(evType)) {
				evType = "5";
			} else if ("图片资料".equals(evType)) {
				evType = "6";
			}
			evidence.setEvType(Integer.parseInt(evType));
		}
		if (evName != null && !"".equals(evName)) {
			evidence.setEvName(evName);
		}
		int count = 0;
		
		if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
			evidenceList = sqlDao.getListfromMysqlLikeev(evidence, startDate + " 00:00:00", endDate + " 23:59:59", (pageIndex - 1) * 6, 6);
			List<Evidence> listfromMysqlLikeev = sqlDao.getListfromMysqlLikeev(evidence, startDate + " 00:00:00", endDate + " 23:59:59");
			count = listfromMysqlLikeev.size();
		}else{
			count = sqlDao.getcountfromMysql(evidence);
			evidenceList = sqlDao.getListfromMysqlOderByAddtime(evidence, (pageIndex-1) * 6, 6);
		}
		for (Evidence evidence2 : evidenceList) {
			int caseID = evidence2.getCaseID();
			if(caseID==10){
				evidence2.setComment("无");
			}else{
				Caseinfo caseinfo = new Caseinfo();
				caseinfo.setId(caseID);
				List<Caseinfo> listfromMysql = sqlDao.getListfromMysql(caseinfo);
				if(listfromMysql.size()>0){
					Caseinfo caseinfo2 = listfromMysql.get(0);
					evidence2.setComment(caseinfo2.getCaseName());
				}
			}
			evidenceList2.add(evidence2);
		}
		map.put("evidenceList", evidenceList);
		map.put("count", count);
		// getListfromMysqlLike(pictureInfo);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(map));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	//数据趋势-数据列表导出
	@RequestMapping("/chart/Exporthd.php")
	@ResponseBody
	public void Exporthd(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response)throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException{
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String evType = request.getParameter("evType");
		String evName = request.getParameter("evName");
		
		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");
			  try {
				    HSSFWorkbook wb1 = null;
					POIFSFileSystem fs = null;
					String path1 = projectpath + filename + ".xls";
					File file = new File(path1);
					create1(path1);
					fs = new POIFSFileSystem(new FileInputStream(path1));
					wb1 = new HSSFWorkbook(fs);
					///
					Evidence evidence = new Evidence();
					List<Evidence> evidenceList = new ArrayList<Evidence>();
					List<Evidence> evidenceList2 = new ArrayList<Evidence>();
					if (evType != null && !"".equals(evType)) {
						if ("电子邮件".equals(evType)) {
							evType = "1";
						} else if ("综合文档".equals(evType)) {
							evType = "2";
						} else if ("电子话单".equals(evType)) {
							evType = "3";
						} else if ("手机取证".equals(evType)) {
							evType = "4";
						} else if ("黑客数据".equals(evType)) {
							evType = "5";
						} else if ("图片资料".equals(evType)) {
							evType = "6";
						}
						evidence.setEvType(Integer.parseInt(evType));
					}
					if (evName != null && !"".equals(evName)) {
						evidence.setEvName(evName);
					}
					int count = 0;
					if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
						evidenceList = sqlDao.getListfromMysqlLikeev(evidence, startDate, endDate);
						count = evidenceList.size();
					}else{
						evidenceList = sqlDao.getListfromMysqlLike(evidence);
						count = sqlDao.getcountfromMysql(evidence);
					}
					for (Evidence evidence2 : evidenceList) {
						int caseID = evidence2.getCaseID();
						if(caseID==10){
							evidence2.setComment("无");
						}else{
							Caseinfo caseinfo = new Caseinfo();
							caseinfo.setId(caseID);
							List<Caseinfo> listfromMysql = sqlDao.getListfromMysql(caseinfo);
							if(listfromMysql.size()>0){
								Caseinfo caseinfo2 = listfromMysql.get(0);
								evidence2.setComment(caseinfo2.getCaseName());
							}
						}
						evidenceList2.add(evidence2);
					}
					
					///
					  for(int i = 0;i<evidenceList2.size();i++){
							write1(evidenceList2.get(i), wb1, path1);
						 }
					  String fileName = null;// 下载文件名 
							InputStream ins = null;
							OutputStream ous = null;
						 try {
								if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
									fileName = new String(file.getName().getBytes(request.getCharacterEncoding()), "ISO8859-1");
								} else {
									fileName = URLEncoder.encode(file.getName(), "UTF-8");
								}
								response.addHeader("content-disposition", "attachment; filename=" + fileName);
								ins = new FileInputStream(file);
								ous = response.getOutputStream();
								int len = 0;
								byte[] buf = new byte[1024];
								while ((len = ins.read(buf)) != -1) {
									ous.write(buf, 0, len);
								}
								/*ous.flush();*/
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								if (ins != null) {
									try {
										ins.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
								if (ous != null) {
									try {
										ous.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
							// System.out.println(JSON.toJSONString(bean));
			  } catch (Exception e) {
				System.out.println(e);
			}
	}
	/**
	 * 报表统计-数据趋势图
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/chart/typestatistics.php")
	public void typestatistics(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		//System.out.println("posstartTime"+posstartTime);
		Map<String, Object> map = new HashMap<String, Object>();
		
/*		if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
			evidenceList = sqlDao.getListfromMysqlLikeev(evidence, startDate, endDate, (pageIndex - 1) * 10, 10);
			List<Evidence> listfromMysqlLikeev = sqlDao.getListfromMysqlLikeev(evidence, startDate, endDate);
			count = listfromMysqlLikeev.size();
		}else{
			evidenceList = sqlDao.getListfromMysqlLike(evidence, (pageIndex - 1) * 10, 10);
			count = sqlDao.getcountfromMysql(evidence);
		}*/
	  
		for(int i=1;i<7;i++){
			List<Evidence> list = new ArrayList<Evidence>();
			Evidence evidence = new Evidence();
			evidence.setEvType(i);
			if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
				list = sqlDao.getListfromMysqlLikeev(evidence, startDate + " 00:00:00", endDate + " 23:59:59");
			}else{
				list = sqlDao.getListfromMysql(evidence);
			}
			long size=0;
			for (Evidence evidence2 : list) {
				size+=(long)Double.parseDouble(evidence2.getEvSize());
			}
			String evtype="";
			if(i==1){
				evtype="电子邮件";
			}
			if(i==2){
				evtype="综合文档";
			}
			if(i==3){
				evtype="电子话单";
			}
			if(i==4){
				evtype="手机取证";
			}
			/*if(i==5){
				evtype="黑客数据";
			}*/
			if(i==6){
				evtype="图片资料";
			}
			map.put(evtype, size);
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(map));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	public void create1(String path) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);
		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);
		// 给Excel的单元格设置样式和赋值
		cell.setCellValue("数据名称");
		cell = row.createCell(1);
		cell.setCellValue("数据大小");
		cell = row.createCell(2);
		cell.setCellValue("数据类型");
		cell = row.createCell(3);
		cell.setCellValue("关联案件");
		cell = row.createCell(4);
		cell.setCellValue("操作人");
		cell = row.createCell(5);
		cell.setCellValue("导入日期");
	
		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}
	public static void write1(Evidence evidence, HSSFWorkbook wb1, String path1) throws Exception {
		HSSFSheet sheet = wb1.getSheetAt(0);
		int begin = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();
		int m = 0;
		for (int n = begin; n <= end; n++) {
			// System.out.println(sheet.getRow(i).getCell(0).toString());
			try {
				// String cell2 = sheet.getRow(n).getCell(0).toString();
				m++;
			} catch (Exception e) {
				break;
			}
		}
		
		HSSFRow row1 = sheet.createRow(m);

		// HSSFHyperlink link1 = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue(evidence.getEvName());
		cell = row1.createCell(1);
		cell.setCellValue(evidence.getEvSize()+" kb");
		cell = row1.createCell(2);
		cell.setCellValue(evidence.getEvType());
		cell = row1.createCell(3);
		cell.setCellValue(evidence.getComment());
		cell = row1.createCell(4);
		cell.setCellValue(evidence.getEvAdmin());
		cell = row1.createCell(5);
		cell.setCellValue(evidence.getAddTime());
		FileOutputStream os = new FileOutputStream(path1);
		wb1.write(os);
		os.close();
	}

	//点击查看显示数据
	@RequestMapping(value = "/chart/getEvidences.php")
	public void getEvidences(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		Map<String, Object> map = new HashMap<String, Object>();


		String evType = request.getParameter("evType");
		Evidence evidence = new Evidence();
		List<Evidence> evidenceList = new ArrayList<Evidence>();
		List<Evidence> evidenceList2 = new ArrayList<Evidence>();
		if (evType != null && !"".equals(evType)) {

			evidence.setEvType(Integer.parseInt(evType));
		}

			
			int count = 0;
			if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
				
				evidenceList = sqlDao.getListfromMysqlLikeev(evidence, startDate + " 00:00:00", endDate + " 23:59:59");
				count = evidenceList.size();
			}else{
				evidenceList = sqlDao.getListfromMysql(evidence);
				count = sqlDao.getcountfromMysql(evidence);
			}
			
			
		for (Evidence evidence2 : evidenceList) {
			int caseID = evidence2.getCaseID();
			if(caseID==0){
				evidence2.setComment("无案件");
			}else{
				Caseinfo caseinfo = new Caseinfo();
				caseinfo.setId(caseID);
				List<Caseinfo> listfromMysql = sqlDao.getListfromMysql(caseinfo);
				if(listfromMysql.size()>0){
					Caseinfo caseinfo2 = listfromMysql.get(0);
					evidence2.setComment(caseinfo2.getCaseName());
				}
			}
			evidenceList2.add(evidence2);
		}
		map.put("evidenceList", evidenceList2);
		map.put("count", count);
		// getListfromMysqlLike(pictureInfo);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(map));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	
	
	
	public void create2(String path) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);
		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);
		// 给Excel的单元格设置样式和赋值
		cell.setCellValue("数据类型");
		cell = row.createCell(1);
		cell.setCellValue("数据大小");

		
		
	
		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}
	public static void write2(String type,long size, HSSFWorkbook wb1, String path1) throws Exception {
		HSSFSheet sheet = wb1.getSheetAt(0);
		int begin = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();
		int m = 0;
		for (int n = begin; n <= end; n++) {
			// System.out.println(sheet.getRow(i).getCell(0).toString());
			try {
				// String cell2 = sheet.getRow(n).getCell(0).toString();
				m++;
			} catch (Exception e) {
				break;
			}
		}
		
		HSSFRow row1 = sheet.createRow(m);

		// HSSFHyperlink link1 = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue(type);
		cell = row1.createCell(1);
		cell.setCellValue(size+" kb");

		FileOutputStream os = new FileOutputStream(path1);
		wb1.write(os);
		os.close();
	}
	
	
	//导出
	@RequestMapping("admin/Exporthd2.php")
	@ResponseBody
	public void Exporthd2(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response)throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException{

		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		try {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			String path = projectpath + filename + ".xls";
			File file = new File(path);
			create2(path);
			fs = new POIFSFileSystem(new FileInputStream(path));
			wb = new HSSFWorkbook(fs);
			List<Evidence> list =  new ArrayList<Evidence>();
			for(int i=1;i<7;i++){
				Evidence evidence = new Evidence();
				if(i==4) continue;
				if(i==5) continue;
				evidence.setEvType(i);
				
				if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
					list  = sqlDao.getListfromMysqlLikeev(evidence, startDate + " 00:00:00", endDate + " 23:59:59");
			
				}else{
					list = sqlDao.getListfromMysql(evidence);
				}
				
				long size=0;
				for (Evidence evidence2 : list) {
					size+=(long)Double.parseDouble(evidence2.getEvSize());
				}
				String evtype="";
				if(i==1){
					evtype="电子邮件";
					
				}
				if(i==2){
					evtype="综合文档";
				}
				if(i==3){
					evtype="电子话单";
				}
/*				if(i==4){
					evtype="手机取证";
				}*/
/*				if(i==5){
					evtype="黑客数据";
				}*/
				if(i==6){
					evtype="图片资料";
				}
				//map.put(evtype, size);
				write2(evtype,size, wb, path);
			}
			String fileName = null;// 下载文件名 
			InputStream ins = null;
			OutputStream ous = null;
			try {
				if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
					fileName = new String(file.getName().getBytes(request.getCharacterEncoding()), "ISO8859-1");
				} else {
					fileName = URLEncoder.encode(file.getName(), "UTF-8");
				}
				response.addHeader("content-disposition", "attachment; filename=" + fileName);
				ins = new FileInputStream(file);
				ous = response.getOutputStream();
				int len = 0;
				byte[] buf = new byte[1024];
				while ((len = ins.read(buf)) != -1) {
					ous.write(buf, 0, len);
				}
				/*ous.flush();*/
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (ins != null) {
					try {
						ins.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (ous != null) {
					try {
						ous.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// System.out.println(JSON.toJSONString(bean));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}







