package com.xl.cloud.action;

import com.xl.cloud.bean.Evidence;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NoCaseDataAction {
	private SqlDao sqlDao = new SqlDao();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final Logger logger = Logger.getLogger(BuildCollection.class);
	
	@RequestMapping(value = "/noCaseData/importevidence_NoCase.php")
	public String importevidenceNoCase(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return "importevidence_NoCase";
	}
	@RequestMapping(value = "/noCaseData/addevidence_NoCase.php")
	public String addevidenceNoCase(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return "addevidence_NoCase";
	}	
	@RequestMapping(value = "/noCaseData/evidence_adding_noCase.php")
	public String evidence_adding_noCase(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return "evidence_adding_noCase";
	}
	/**
	 * 无案件数据-数据列表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/noCaseData/showEvidence.php")
	public void showEvidence(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		//String dirName = request.getParameter("dirName");// 文价名称
		String pageIndexstr = request.getParameter("pageIndex");// 页数
		int pageIndex =Integer.parseInt(pageIndexstr);// 页数
		Evidence evidence = new Evidence();
		/*if (dirName != null || !"".equals(dirName)) {
			pictureInfo.setDirName(dirName);
		}*/
		evidence.setCaseID(0);
		int count = sqlDao.getcountfromMysql(evidence);
		List<Evidence> evidenceList = sqlDao.getListfromMysqlOderByAddtime(evidence, (pageIndex-1) * 10, 10);
		
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
	/**
	 * 无案件数据-关联案件
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/noCaseData/addCase.php")
	public void addCase(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String caseIDstr = request.getParameter("caseID");// 案件id
		String evIDstr = request.getParameter("evID");// 数据id
		int evID = Integer.parseInt(evIDstr);
		String[] split = caseIDstr.split(",");
		for (String string : split) {
			//System.out.println(string);
			int caseID = Integer.parseInt(string);
			Evidence evidence = new Evidence();
			evidence.setId(evID);
			List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
			if(listfromMysql.size()>0){
				Evidence evidence2 = listfromMysql.get(0);
				evidence2.setCaseID(caseID);
				Evidence evidence3 = new Evidence();
				evidence3.setCaseID(caseID);
				evidence3.setUUID(evidence2.getUUID());
				List<Evidence> listfromMysql2 = sqlDao.getListfromMysql(evidence3);
				if(listfromMysql2.size()==0){
					sqlDao.setBeanToMysql(evidence2);
				}
			}
		}
	}
	
	/**
	 * 无案件数据-上传完成修改数据
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
		@RequestMapping(value = "/noCaseData/getevidencelist.php")
		public String getevidencelist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
				HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {
				//String type=request.getParameter("type");
				//String sum=request.getParameter("sum");
			String uploadNum = request.getParameter("uploadNum");
			if(uploadNum != null && !"".equals(uploadNum)) {
				String getId = session.getAttribute("eviTempID").toString();
				//System.out.println("获取到的Id:\t"+getId);
				
				String successNum = request.getParameter("successNum");
				String errorNum = request.getParameter("errorNum");
				String evSize = request.getParameter("evSize");
				
				
				
				//System.out.println("uploadNum:\t"+uploadNum);
				//System.out.println("successNum:\t"+successNum);
				//System.out.println("errorNum:\t"+errorNum);
				//System.out.println("evSize:\t"+evSize);
				Evidence evidence = new Evidence();
				evidence.setId(Integer.parseInt(getId));
				List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
				Evidence evidence2 = listfromMysql.get(0);
				if(!"6".equals(evidence2.getEvType())){
				String []uu= evidence2.getDirPath().split("/");//截取dirPath的caseid+UUid的拼接路径
				String UUcaseid= uu[uu.length-1];
				String hdfsPath = "/tmp/emaildata/" + evidence2.getCaseID();
				final String archive="hadoop archive -archiveName "+UUcaseid+" -p "+hdfsPath+" "+"/tmp/emaildata/";//执行合并HDFS的小文件
				final String deleteFile="hadoop fs -rm -R "+hdfsPath;//输出之前的小文件的目录
				Process process;                
				try {
					//hadoop archive -archiveName 22099f9b6cf4ea.har.har -p /tmp/emaildata/2875 /tmp/emaildata/
					process = Runtime.getRuntime().exec(archive);
					//System.out.println("archive========="+archive);
					process.waitFor();
					process = Runtime.getRuntime().exec(deleteFile);
					//System.out.println("deleteFile========="+deleteFile);
					process.waitFor();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
				}
				int uploadNum2 = Integer.parseInt(evidence2.getUploadNum());
				int successNum2=Integer.parseInt(successNum);
				int errorNum2 =uploadNum2-successNum2;
				//evidence2.setUploadNum(uploadNum);
				evidence2.setSuccessNum(successNum);
				evidence2.setErrorNum(errorNum2+"");
				evidence2.setEvSize(evSize);
				sqlDao.updateToMysql(evidence2);
				//actionLog((String) session.getAttribute("userName"),"编辑","案件管理");
			}
			return "addevidence_NoCase";
		}
		/**
		 * 无案件数据-实时更新数据
		 * 
		 * @param request
		 * @param response
		 * @throws IOException
		 * @throws InterruptedException
		 */
		@RequestMapping(value = "/noCaseData/updateEvidence.php")
		public void updateEvidence(HttpServletRequest request, Map<String, Object> map, HttpSession session,
				HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {
				//String type=request.getParameter("type");
				//String sum=request.getParameter("sum");
			String uploadNum = request.getParameter("uploadNum");
			if(uploadNum != null && !"".equals(uploadNum)) {
				String getId = session.getAttribute("eviTempID").toString();
				//System.out.println("获取到的Id:\t"+getId);
				
				String successNum = request.getParameter("successNum");
				String errorNum = request.getParameter("errorNum");
				String evSize = request.getParameter("evSize");
				//System.out.println("uploadNum:\t"+uploadNum);
				//System.out.println("successNum:\t"+successNum);
				//System.out.println("errorNum:\t"+errorNum);
				//System.out.println("evSize:\t"+evSize);
				Evidence evidence = new Evidence();
				evidence.setId(Integer.parseInt(getId));
				List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
				Evidence evidence2 = listfromMysql.get(0);
				int uploadNum2 = Integer.parseInt(evidence2.getUploadNum());
				int successNum2=Integer.parseInt(successNum);
				int errorNum2 =uploadNum2-successNum2;
				//evidence2.setUploadNum(uploadNum);
				evidence2.setSuccessNum(successNum);
				evidence2.setErrorNum(errorNum2+"");
				evidence2.setEvSize(evSize);
				sqlDao.updateToMysql(evidence2);
				//actionLog((String) session.getAttribute("userName"),"编辑","案件管理");
			}
		}
		
		/**
		 * 有案件数据-上传完成修改数据
		 * 
		 * @param request
		 * @param response
		 * @throws IOException
		 * @throws InterruptedException
		 */
			@RequestMapping(value = "/noCaseData/getevidencelist2.php")
			public String getevidencelist2(HttpServletRequest request, Map<String, Object> map, HttpSession session,
					HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
					IllegalAccessException, InvocationTargetException {
					//String type=request.getParameter("type");
					//String sum=request.getParameter("sum");
				String uploadNum = request.getParameter("uploadNum");
				if(uploadNum != null && !"".equals(uploadNum)) {
					String getId = session.getAttribute("eviTempID").toString();
					//System.out.println("获取到的Id:\t"+getId);

					String successNum = request.getParameter("successNum");
					String errorNum = request.getParameter("errorNum");
					String evSize = request.getParameter("evSize");
					//System.out.println("uploadNum:\t"+uploadNum);
					//System.out.println("successNum:\t"+successNum);
					//System.out.println("errorNum:\t"+errorNum);
					//System.out.println("evSize:\t"+evSize);
					Evidence evidence = new Evidence();
					evidence.setId(Integer.parseInt(getId));
					List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
					Evidence evidence2 = listfromMysql.get(0);
					if(!"6".equals(evidence2.getEvType())){
					String []uu= evidence2.getDirPath().split("/");//截取dirPath的caseid+UUid的拼接路径
					String UUcaseid= uu[uu.length-1];
					String hdfsPath = "/tmp/emaildata/" + evidence2.getCaseID();
					final String archive="hadoop archive -archiveName "+UUcaseid+" -p "+hdfsPath+" "+"/tmp/emaildata/";//执行合并HDFS的小文件
					final String deleteFile="hadoop fs -rm -R "+hdfsPath;//输出之前的小文件的目录
					Process process;                
					try {
						//hadoop archive -archiveName 22099f9b6cf4ea.har.har -p /tmp/emaildata/2875 /tmp/emaildata/
						process = Runtime.getRuntime().exec(archive);
						System.out.println("archive========="+archive);
						process.waitFor();
						process = Runtime.getRuntime().exec(deleteFile);
						System.out.println("deleteFile========="+deleteFile);
						process.waitFor();
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
					}
//					Runnable r = new Runnable() {
//						@Override
//						public void run() {
//							Process process;
//							try {
//								process = Runtime.getRuntime().exec(archive);
//								System.out.println("archive========="+archive);
//								process.waitFor();
//								process = Runtime.getRuntime().exec(deleteFile);
//								System.out.println("deleteFile========="+deleteFile);
//								process.waitFor();
//							} catch (IOException | InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//					};
//					new Thread(r).start();
					int uploadNum2 = Integer.parseInt(evidence2.getUploadNum());
					int successNum2=Integer.parseInt(successNum);
					int errorNum2 =uploadNum2-successNum2;
					//evidence2.setUploadNum(uploadNum);
					evidence2.setSuccessNum(successNum);
					evidence2.setErrorNum(errorNum2+"");
					evidence2.setEvSize(evSize);
					//evidence2.setDirPath("/tmp/emaildata/"+UUcaseid+".har");//更新合并后的路径到数据库
					sqlDao.updateToMysql(evidence2);
					//actionLog((String) session.getAttribute("userName"),"编辑","案件管理");
					///emaildata/10/109db0166ed7
						
				}
				return "case_list";
			}
			/**
			 * 有案件数据-实时更新数据
			 * 
			 * @param request
			 * @param response
			 * @throws IOException
			 * @throws InterruptedException
			 */
			@RequestMapping(value = "/noCaseData/updateEvidence2.php")
			public void updateEvidence2(HttpServletRequest request, Map<String, Object> map, HttpSession session,
					HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
					IllegalAccessException, InvocationTargetException {
					//String type=request.getParameter("type");
					//String sum=request.getParameter("sum");
				String uploadNum = request.getParameter("uploadNum");
				if(uploadNum != null && !"".equals(uploadNum)) {
					String getId = session.getAttribute("eviTempID").toString();
					//System.out.println("获取到的Id:\t"+getId);

					String successNum = request.getParameter("successNum");
					String errorNum = request.getParameter("errorNum");
					String evSize = request.getParameter("evSize");
					//System.out.println("uploadNum:\t"+uploadNum);
					//System.out.println("successNum:\t"+successNum);
					//System.out.println("errorNum:\t"+errorNum);
					//System.out.println("evSize:\t"+evSize);
					Evidence evidence = new Evidence();
					evidence.setId(Integer.parseInt(getId));
					List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
					Evidence evidence2 = listfromMysql.get(0);
					int uploadNum2 = Integer.parseInt(evidence2.getUploadNum());
					int successNum2=Integer.parseInt(successNum);
					int errorNum2 =uploadNum2-successNum2;
					//evidence2.setUploadNum(uploadNum);
					evidence2.setSuccessNum(successNum);
					evidence2.setErrorNum(errorNum2+"");
					evidence2.setEvSize(evSize);
					sqlDao.updateToMysql(evidence2);
					//actionLog((String) session.getAttribute("userName"),"编辑","案件管理");
				}
			}
}







