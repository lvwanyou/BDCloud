package com.xl.cloud.action;

import com.xl.cloud.bean.*;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.DocToHtml;
import com.xl.cloud.util.JsonUtil;
import com.xl.cloud.util.TrimIllegalChar;
import com.xl.cloud.util.preview.ExcelToHtmlUtil;
import com.xl.cloud.util.preview.PPTtoHtmlUtil;
import com.xl.cloud.util.preview.PdfToHtmlUtil;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.docx4j.wml.P;
import org.opendope.answers.Repeat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 * YC_TODO:
 * 2017/9/18
 *
 * 线索管理
 */
@Controller
public class ThreadManageAction {
    private SqlDao sqlDao = new SqlDao();
    final Logger logger = Logger.getLogger(BuildCollection.class);
    private static final long serialVersionUID = 1L;
    final static TrimIllegalChar tic = new TrimIllegalChar();
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    // 操作记录
    public void actionLog(String name, String action, String module) throws UnknownHostException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        UserAction userActionBean = new UserAction();
        InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
        String hostAddress = address.getHostAddress();
        Date date = new Date();
        String createDate = sdf.format(date);
        userActionBean.setName(name);
        userActionBean.setAction(action);
        userActionBean.setModule(module);
        userActionBean.setCreateDate(createDate);
        userActionBean.setIp(hostAddress);
        sqlDao.setBeanToMysql(userActionBean);
    }

    //线索上报的跳转进度条页面
    @RequestMapping(value = "/ThreadManage/evidence_adding_Person.php")
    public String evidence_adding_Person(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                         HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        String clue = request.getParameter("cluesIDs3");
        
        map.put("result", clue);
        session.setAttribute("caseinfo2", clue);
        return "evidence_adding_Person";
    }

    /* YC_TODO: 2017/9/19 线索上报_涉嫌人员的静态页面 */
    @RequestMapping("/showAddInvolve.php")
    public String showAddInvolve_Person(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                        HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        return "addInvolve_Person";
    }

/*	 YC_TODO: 2017/9/21 线索上报_涉嫌人员检测
    @RequestMapping(value = "/checkName_person.php")
	public void checkName_person(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("text/html;charset=utf-8");
		String evName = request.getParameter("evName");
		String res = "succ";
		if (StringUtils.isEmpty(evName)) {
			evName = "-1";
		}
		Involve_Person involve_person = new Involve_Person();
		involve_person.setPhone(evName);
		List<Involve_Person>  involve_personList = sqlDao.getListfromMysql(involve_person);
		//System.out.println("------YC------involve_personList.size()的值 = " + involve_personList.size() + ", " + "当前类 = ThreadManageAction, 当前方法 = checkName_person;");
		if (involve_personList.size() > 0) {
			res = "exist";
		}

		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write("{\"res\":\"" + res + "\"}");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}*/


    // 查询嫌疑人是否存在
    @RequestMapping(value = "admin/checkEvName.php")
    public void checkEvName(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        response.setContentType("text/html;charset=utf-8");
        String SuspectPhone = request.getParameter("SuspectPhone");

        String suName = request.getParameter("suName");
        String suPhone = request.getParameter("suPhone");
        String suEmail = request.getParameter("suEmail");


        List<Involve_Person> involve_person1 = new ArrayList<Involve_Person>();

        if (SuspectPhone != null && !"".equals(SuspectPhone)) {
                /*String[] split = SuspectPhone.split(" ");
                String name = split[0];
				String phone = split[1];
				String mail = split[2];*/

            if (!"".equals(suName) && suName != null) {
                /*	SuspectInfo suspectInfo = new SuspectInfo();
                    suspectInfo.setSuspectName(suName);
					*/
                Involve_Person involve_person = new Involve_Person();
                involve_person.setName(suName);
                List<Involve_Person> eviTemps = sqlDao.getListfromMysql(involve_person);
                if (eviTemps.size() > 0) {
                    involve_person = eviTemps.get(0);
                    if (involve_person1.size() == 0) {
                        
                        involve_person1.add(involve_person);
                    } else {
                        int flag = 0;
                        for (Involve_Person Involve_Person11 : involve_person1) {
                            if (Involve_Person11.getId() != involve_person.getId()) {
                                flag = 1;
                            }
                        }
                        if (flag == 1) {
                            involve_person1.add(involve_person);
                        }
                    }
                }
            }


            if (!"".equals(suPhone) && suPhone != null) {
                Involve_Person Involve_Person2 = new Involve_Person();
                Involve_Person2.setPhone(suPhone);
                List<Involve_Person> eviTemps2 = sqlDao.getListfromMysql(Involve_Person2);
                if (eviTemps2.size() > 0) {
                    Involve_Person2 = eviTemps2.get(0);
                    if (involve_person1.size() == 0) {
                        involve_person1.add(Involve_Person2);
                    } else {
                        int flag = 0;
                        for (Involve_Person Involve_Person22 : involve_person1) {
                            if (Involve_Person22.getId() != Involve_Person2.getId()) {
                                flag = 1;
                            }
                        }
                        if (flag == 1) {
                            involve_person1.add(Involve_Person2);
                        }
                    }
                }
            }

            if (!"".equals(suEmail) && suEmail != null) {
                    /*SuspectInfo suspectInfo3 = new SuspectInfo();
                    suspectInfo3.setSuspectMail(suEmail);*/

                Involve_Person Involve_Person3 = new Involve_Person();
                Involve_Person3.setEmail(suEmail);
                List<Involve_Person> eviTemps3 = sqlDao.getListfromMysql(Involve_Person3);
                if (eviTemps3.size() > 0) {
                    Involve_Person3 = eviTemps3.get(0);
                    if (involve_person1.size() == 0) {
                        involve_person1.add(Involve_Person3);
                    } else {
                        int flag = 0;
                        for (Involve_Person Involve_Person33 : involve_person1) {
                            if (Involve_Person33.getId() != Involve_Person3.getId()) {
                                flag = 1;
                            }
                        }
                        if (flag == 1) {
                            involve_person1.add(Involve_Person3);
                        }
                    }
                }
            }

        }


        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(JsonUtil.list2json(involve_person1));
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


    /* YC_TODO: 2017/9/21 线索上报_涉嫌单位检测 */
    @RequestMapping(value = "/checkName_unit.php")
    public void checkName_unit(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        response.setContentType("text/html;charset=utf-8");
        String evName = request.getParameter("evName");
        String res = "succ";
        if (StringUtils.isEmpty(evName)) {
            evName = "-1";
        }
        Involve_Unit involve_Unit = new Involve_Unit();
        involve_Unit.setSusUnit(evName);
        List<Involve_Unit> involve_unitList = sqlDao.getListfromMysql(involve_Unit);
        if (involve_unitList.size() > 0) {
            res = "exist";
        }

        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write("{\"res\":\"" + res + "\"}");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
    /*@RequestMapping("/admin/addInvolve_Person.php")
    public void adminaddInvolve_Person(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
								  HttpSession session) {
		final String userName = (String) session.getAttribute("userName");
		String section = (String) session.getAttribute("section");
		User user = (User) session.getAttribute("user");
		final String addcaseid = request.getParameter("caseid");
		String dataTypes = request.getParameter("dataTypes");
		//String filelength = request.getParameter("filelength");
		String region = request.getParameter("region");
		String status = request.getParameter("status");
		String id = request.getParameter("id");
		String evName = request.getParameter("evName");// 证据名称
		String evName2 = request.getParameter("evName");
		String comment = request.getParameter("comment");// 证据描述
		String threadType = null;
		if ("涉税".equals(dataTypes)) {
			dataTypes = "8";
			threadType = "涉税";
		} else if ("非涉税".equals(dataTypes)) {
			dataTypes = "9";
			threadType = "非涉税";
		} else {
			dataTypes = "-1";
		}
	if(status.equals("0")) {

			Involve_Person involve_person = new Involve_Person();

			if(evName2!=null && !evName2.equals("")&& evName2!=""){
				String[] spliteName = evName2.split("\\s");
				involve_person.setName(spliteName[0]);
				if(spliteName.length>1){
			    	if(spliteName[1].indexOf("@")>0){
			    		involve_person.setEmail(spliteName[1]);
			    	}else{
			    		involve_person.setPhone(spliteName[1]);
			    	}
				}else if(spliteName.length>2){
					involve_person.setPhone(spliteName[1]);
					involve_person.setEmail(spliteName[2]);
				}
				involve_person.setSuspectName(evName2);

			}
		//	involve_person.setUploadStyle(uploadStyle);
			involve_person.setThreadType(threadType);

			involve_person.setSusItem(comment);

			involve_person.setReportPerson(userName);
			//involve_person.setFilePath(filepath);
			involve_person.setCreatedTime(dateFormat.format(new Date()));
			//involve_person.setFileType(fileType);
			if(region!=null && !region.equals("")&& region!=""){
			involve_person.setRegion(region);}
			sqlDao.setBeanToMysql(involve_person);
			//List<Involve_Person>  involve_personList = sqlDao.getListfromMysql(involve_person);
		//	spersonID = involve_personList.get(0).getId();
		//	evi.setSpersonID(spersonID);
		} else {
			Involve_Unit involve_unit = new Involve_Unit();
			involve_unit.setCreatedTime(dateFormat.format(new Date()));
		//	involve_unit.setFilePath(filepath);
		//	involve_unit.setFileType(fileType);
			involve_unit.setReportPerson(userName);
			involve_unit.setRegion(region);
			involve_unit.setSusItem(comment);
			involve_unit.setSusUnit(evName);
			involve_unit.setThreadType(threadType);
	//		involve_unit.setUploadStyle(uploadStyle);
			sqlDao.setBeanToMysql(involve_unit);
		//	List<Involve_Unit>  involve_personList = sqlDao.getListfromMysql(involve_unit);
		//	spersonID = involve_personList.get(0).getId();
		//	getName = evName;
		//	evi.setSunitID(spersonID);
		}


	}*/


    /* YC_TODO: 2017/9/20 线索上报_导入数据    里面包含周武智线索中上传数据的方法，不能删*/
    @RequestMapping("/addInvolve_Person.php")
    public void addInvolve_Person(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
                                  HttpSession session) {
        response.setContentType("text/html;charset=utf-8");
        final String userName = (String) session.getAttribute("userName");
        String section = (String) session.getAttribute("section");
        User user = (User) session.getAttribute("user");
//		final String addcaseid = request.getParameter("caseid");   //-2
//		String dataTypes = request.getParameter("dataTypes");
        String filelength = request.getParameter("filelength");
        String region = request.getParameter("region");
        String status = request.getParameter("status");
        String clueIDss = request.getParameter("id");                //线索ID

        String threadType = null;
        /*if ("涉税".equals(dataTypes)) {
            dataTypes = "8";
			threadType = "涉税";
		} else if ("非涉税".equals(dataTypes)) {
			dataTypes = "9";
			threadType = "非涉税";
		} else {
			dataTypes = "-1";
		}*/
        String uuid = request.getParameter("evUUID");
        session.setAttribute("evUUID", uuid);

        final String hdfsPath = "/tmp2/evidenceClueList/" + clueIDss;//删除collectionname
        String res = "{}";

        String evType = request.getParameter("evType");// 数据类型
        String evAdmin = userName;   // 管理人
        String tempPath = request.getParameter("dirPath");// 文件夹路径
        String fangshi = request.getParameter("fangshi");
        String fileType = null;
        if ("电子邮件".equals(evType)) {
            evType = "1";
            fileType = "电子邮件";
        } else if ("综合文档".equals(evType)) {
            evType = "2";
            fileType = "综合文档";
        } else if ("电子话单".equals(evType)) {
            evType = "3";
            fileType = "电子话单";
        } else if ("手机取证".equals(evType)) {
            evType = "4";
            fileType = "手机取证";
        } else if ("黑客数据".equals(evType)) {
            evType = "5";
            fileType = "黑客数据";
        } else if ("图片资料".equals(evType)) {
            evType = "6";
            fileType = "图片资料";
        } else {
            evType = "-1";
        }
        int uptype = -1;
        String uploadStyle = null;
        if (fangshi.equals("选择本地文件夹上传")) {
            if (!StringUtils.isEmpty(tempPath)) {
                tempPath = "/evidenceClueList/" + clueIDss;
            }
            uptype = 1;
            uploadStyle = "文件夹上传";
        } else if (fangshi.equals("选择本地文件上传")) {
            if (!StringUtils.isEmpty(tempPath)) {
                int inx = tempPath.lastIndexOf("\\");
                tempPath = tempPath.substring(inx + 1);
                tempPath = "/evidenceClueList/" + tempPath;
            }
            uptype = 0;
            uploadStyle = "文件上传";
        }
        

        final String dirPath = tempPath;

        EvidenceClue evi = new EvidenceClue();
        evi.setInvolvePersonID(Integer.parseInt(clueIDss));

        final String filepath = "/evidenceClueList/" + clueIDss + "/" + clueIDss + uuid.substring(0, 10);
        //以前这里有添加嫌疑人和涉嫌单位的代码、发给唐奇了
        evi.setEvType((evType));
        if ("6".equals(evType)) {
            evi.setDirPath(filepath);
        } else {
            evi.setDirPath("/tmp2/evidenceClueList/" + clueIDss + uuid.substring(0, 10) + ".har");//存放hdfs的xxx.har的文件路径
        }

        evi.setDirPath("/tmp2/evidenceClueList/" + clueIDss + uuid.substring(0, 10) + ".har");//存放hdfs的xxx.har的文件路径
        evi.setEvAdmin(evAdmin);
        evi.setUptype(uptype);
        evi.setUploadNum(filelength);
        evi.setSuccessNum("0");
        evi.setErrorNum(filelength);
        evi.setFinished("true");
        evi.setStatus("on");
        evi.setAddTime(dateFormat.format(new Date()));
        evi.setStartSolrTime(evi.getAddTime());
        evi.setCurrFlag("1");
        //evi.setOlState(1);
        evi.setUUID(clueIDss + uuid.substring(0, 10));
        sqlDao.setBeanToMysql(evi);

        EvidenceClue eviTemp = new EvidenceClue();
        eviTemp.setUUID(clueIDss + uuid.substring(0, 10));
        List<EvidenceClue> eviTemps = sqlDao.getListfromMysql(eviTemp);

        if (eviTemps != null && eviTemps.size() > 0) {
            eviTemp = eviTemps.get(0);
        }
        final int eviTempIDClue = eviTemp.getId();
        session.setAttribute("eviTempIDClue", eviTempIDClue);

        class Runs implements Runnable {
            @Override
            public void run() {
                String copyLocal = "hadoop fs -copyFromLocal " + filepath + " " + hdfsPath;
                String mkdir = "hadoop fs -mkdir -p " + hdfsPath;
                Process process;
                try {
                    logger.info("<============================================>");
                    logger.info("<=========mkdir:" + mkdir + "==========>");
                    logger.info("<=========unzipCmd:" + copyLocal + "==========>");
                    logger.info("<============================================>");
                    process = Runtime.getRuntime().exec(mkdir);
                    process.waitFor();
                    process = Runtime.getRuntime().exec(copyLocal);

                    InputStream iserror = process.getErrorStream();
                    InputStreamReader isrerror = new InputStreamReader(iserror);
                    BufferedReader brerror = new BufferedReader(isrerror);
                    while (true) {
                        String s = brerror.readLine();
                        // logger.info("run1--------------" + s);
                        if (s == null) {
                            break;
                        }
                    }
                    process.waitFor();
                    EvidenceClue evidence = new EvidenceClue();
                    evidence.setId(eviTempIDClue);
                    List<EvidenceClue> evidences = sqlDao.getListfromMysql(evidence);
                    
                    if (evidences.size() > 0) {
                        evidence = evidences.get(0);
                        evidence.setFinished("true");
                        sqlDao.updateToMysql(evidence);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Runs runs = new Runs();
        Thread unzipThread2 = new Thread(runs);
        unzipThread2.start();
        res = "{\"evID\":\"" + eviTempIDClue + "\",\"res\":\"succ\"}";
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(res);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 线索列表，添加后台修改数据路上传成功的数量  周武智
     *
     * @param request
     * @param map
     * @param session
     * @param response
     * @return
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @RequestMapping(value = "/admin/cluelist.php")
    public String showIndexQueue(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                 HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        String uploadNum = request.getParameter("uploadNum");
        if (uploadNum != null && !"".equals(uploadNum)) {
            String getId = session.getAttribute("eviTempIDClue").toString();
            //String cluIdE = request.getParameter("cluIdE");
            String successNum = request.getParameter("successNum");
            String errorNum = request.getParameter("errorNum");
            String evSize = request.getParameter("evSize");

            EvidenceClue evidence = new EvidenceClue();
            evidence.setId(Integer.parseInt(getId));
            List<EvidenceClue> listfromMysql = sqlDao.getListfromMysql(evidence);
            EvidenceClue evidence2 = listfromMysql.get(0);
            if (!"6".equals(evidence2.getEvType())) {
                String[] uu = evidence2.getDirPath().split("/");//截取dirPath的caseid+UUid的拼接路径
                String UUcaseid = uu[uu.length - 1];
                String hdfsPath = "/tmp2/evidenceClueList/" + evidence2.getInvolvePersonID();
                final String archive = "hadoop archive -archiveName " + UUcaseid + " -p " + hdfsPath + " " + "/tmp2/evidenceClueList/";//执行合并HDFS的小文件
                final String deleteFile = "hadoop fs -rm -R " + hdfsPath;//输出之前的小文件的目录
                Process process;
                try {
                    //hadoop archive -archiveName 22099f9b6cf4ea.har.har -p /tmp/emaildata/2875 /tmp/emaildata/
                    process = Runtime.getRuntime().exec(archive);
                    process.waitFor();
                    process = Runtime.getRuntime().exec(deleteFile);
                    process.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //		new Thread(r).start();
            int uploadNum2 = Integer.parseInt(evidence2.getUploadNum());
            int successNum2 = Integer.parseInt(successNum);
            int errorNum2 = uploadNum2 - successNum2;
            evidence2.setSuccessNum(successNum);
            evidence2.setErrorNum(errorNum2 + "");
            evidence2.setEvSize(evSize);
            //evidence2.setDirPath("/tmp/emaildata/"+UUcaseid+".har");//更新合并后的路径到数据库
            sqlDao.updateToMysql(evidence2);
        }
        return "cluelist";
    }

    /**
     * 线索列表
     *
     * @param request
     * @param map
     * @param session
     * @param response
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws UnknownHostException
     */
    @RequestMapping(value = "/admin/cluelistss.php")
    public void cluelistss(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                           HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, UnknownHostException {
        String name = request.getParameter("name");//涉嫌人员姓名
        
        String region = request.getParameter("region");//地区
        String startDate = request.getParameter("startDate");//开始时间
        String endDate = request.getParameter("endDate");//结束时间
        String pageno = request.getParameter("pageno");
        String threadType = request.getParameter("threadType");//线索类型
        String threadSource = request.getParameter("threadSource");//线索来源
        String disposal = request.getParameter("disposal");//线索处置
        String threadNum = request.getParameter("threadNum");//线索编号
        String susItem = request.getParameter("susItem");//线索内容
        String status = request.getParameter("status");//线索状态

        int pageIndex = 1;//当前页数
        int pageSize = 10;//每页个数
        int num = 0;//总页数
        if (!StringUtils.isEmpty(pageno)) {
            pageIndex = Integer.parseInt(pageno);
        }


        //List<Involve_Person> list = new ArrayList<Involve_Person>();
        Involve_Person involve_person = new Involve_Person();
        List<Involve_Person> list1 = new ArrayList<Involve_Person>();

        String dep55 = "";

        List<Involve_Person> list2;
        if (threadType != "全部" && !threadType.equals("全部")) {
            involve_person.setThreadType(threadType);
        }
        if (threadSource != "全部" && !threadSource.equals("全部")) {
            involve_person.setThreadSource(threadSource);
        }
        if (disposal != "全部" && !disposal.equals("全部")) {
            involve_person.setDisposal(disposal);
        }
        /*if(status!="全部"  && !status.equals("全部")){
             involve_person.setStatus(status);
		}*/
/*		if(name!=null && !name.equals("") ){
            involve_person.setName(name);
		}

		if(region!=null && !region.equals("")){
			involve_person.setRegion(region);
		}*/
        if (threadNum != null && !threadNum.equals("")) {
            involve_person.setThreadNum(threadNum);
        }
        if (susItem != null && !susItem.equals("")) {
            involve_person.setName(susItem);
        }
        int total = 0;
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            list1 = sqlDao.getListfromMysqlLikTimeecase(involve_person, startDate + " 00:00:00", endDate + " 23:59:59", (pageIndex - 1) * pageSize, pageSize);
            if (list1.size() > 0) {
                for (int i = 0; i < list1.size(); i++) {
                    int id = list1.get(i).getId();      //线索ID
                    
                    Involve_Person involve_Person2 = list1.get(i);

                    EvidenceClue ev = new EvidenceClue();
                    ev.setInvolvePersonID(id);
                    int getcountfromMysql = sqlDao.getcountfromMysql(ev);
                    
                   
                    if (getcountfromMysql > 0) {
                        // ips.setId(id);
                        involve_Person2.setStatuss("查看");
                        sqlDao.updateToMysql(involve_Person2);
                    } else {
                        // ips.setId(id);
                        involve_Person2.setStatuss("导入");
                        sqlDao.updateToMysql(involve_Person2);
                    }
                    String departmentIDsss55 = list1.get(i).getUserPartment();
                    Department department = new Department();

                    department.setId(Integer.parseInt(departmentIDsss55));
                    dep55 = sqlDao.getListfromMysql(department).get(0).getDepartmentName();

                    list1.get(i).setUserPartment(dep55);
                }
            }
            list2 = sqlDao.getListfromMysqlLikecase(involve_person, startDate + " 00:00:00", endDate + " 23:59:59");
            total = list2.size();
        } else {

            list1 = sqlDao.getListfromMysqlLike(involve_person, (pageIndex - 1) * pageSize, pageSize);
            if (list1.size() > 0) {
                for (int i = 0; i < list1.size(); i++) {
                    int id = list1.get(i).getId();                //线索ID
                    Involve_Person involve_Person2 = list1.get(i);

                    EvidenceClue ev = new EvidenceClue();
                    Involve_Person ips = new Involve_Person();
//				 ev.setSpersonID(id);
                    ev.setInvolvePersonID(id);
                    int getcountfromMysql = sqlDao.getcountfromMysql(ev);


                    if (getcountfromMysql > 0) {
                        //	 involve_person.setId(id);
                        involve_Person2.setStatuss("查看");
                        sqlDao.updateToMysql(involve_Person2);
                    } else {
                        //	 involve_person.setId(id);
                        involve_Person2.setStatuss("导入");
                        sqlDao.updateToMysql(involve_Person2);
                    }
                    String departmentIDsss55 = list1.get(i).getUserPartment();

                    if (!"".equals(departmentIDsss55) && departmentIDsss55 != null) {

                        Department department = new Department();
                        //查询的分局
                        department.setId(Integer.parseInt(departmentIDsss55));
                        dep55 = sqlDao.getListfromMysql(department).get(0).getDepartmentName();
                        list1.get(i).setUserPartment(dep55);
                    }
                }
            }
            list2 = sqlDao.getListfromMysqlLike(involve_person);
            total = sqlDao.getcountfromMysqlLike(involve_person);
        }

        List<Integer> pageList = new ArrayList<Integer>();

        num = total / pageSize;
        if (total % pageSize != 0) {
            num++;
        }

        JSONArray jsonArray = JSONArray.fromObject(list1);
        JSONArray jsonArray2 = JSONArray.fromObject(list2);
        String json_str = jsonArray.toString();
        String json_str2 = jsonArray2.toString();
        String result_data = "{\"dep55\":\"" + dep55 + "\",\"totalNum\":\"" + total + "\",\"totalPages\":\"" + num + "\",\"nowPage\":\""
                + pageIndex + "\",\"resData\":" + json_str + ",\"resData2\":" + json_str2 + "}";
        actionLog((String) session.getAttribute("userName"), "新增", "涉案人员");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result_data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        map.put("list", list1);//返回所有的线索信息
        map.put("totalNum", total);
        map.put("totalPages", num);
        map.put("nowPage", pageIndex);
        map.put("pageList", pageList);
        map.put("dep55", dep55);
    }


    //涉嫌单位
    @RequestMapping(value = "/admin/unitlist.php")
    public String unitlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                           HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, UnknownHostException {

        String name = request.getParameter("name");//涉嫌人员姓名
        String region = request.getParameter("region");//地区
        String startDate = request.getParameter("startDate");//开始时间
        String endDate = request.getParameter("endDate");//结束时间
        String pageno = request.getParameter("pageno");
        int pageIndex = 1;//当前页数
        int pageSize = 10;//每页个数
        int num = 0;//总页数
        if (!StringUtils.isEmpty(pageno)) {
            pageIndex = Integer.parseInt(pageno);
        }


        //List<Involve_Person> list = new ArrayList<Involve_Person>();
        Involve_Unit involve_unit = new Involve_Unit();
        List<Involve_Unit> list1 = new ArrayList<Involve_Unit>();
        if (name != null && !name.equals("")) {
            involve_unit.setSusUnit(name);
        }
        if (region != null && !region.equals("")) {
            involve_unit.setRegion(region);
        }

        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
            list1 = sqlDao.getListfromMysqlLikeev(involve_unit, startDate + " 00:00:00", endDate + " 23:59:59", (pageIndex - 1) * pageSize, pageSize);
        } else {
            list1 = sqlDao.getListfromMysqlLike(involve_unit, (pageIndex - 1) * pageSize, pageSize);
        }




/*			num = total / pageSize;
			if (total % pageSize != 0) {
				num++;
			}

			List<Integer> pageList = new ArrayList<Integer>();
			if (num < 5) {
				for (int i = 1; i <= num; i++) {
					pageList.add(i);
				}
			} else {
				if (pageIndex <= 5) {
					for (int i = 1; i <= 5; i++) {
						pageList.add(i);
					}
				} else {
					if (pageIndex + 2 <= num) {
						pageList.add(pageIndex - 2);
						pageList.add(pageIndex - 1);
						pageList.add(pageIndex);
						pageList.add(pageIndex + 1);
						pageList.add(pageIndex + 2);
					} else if (pageIndex + 1 <= num) {
						pageList.add(pageIndex - 3);
						pageList.add(pageIndex - 2);
						pageList.add(pageIndex - 1);
						pageList.add(pageIndex);
						pageList.add(pageIndex + 1);
					} else {
						pageList.add(pageIndex - 4);
						pageList.add(pageIndex - 3);
						pageList.add(pageIndex - 2);
						pageList.add(pageIndex - 1);
						pageList.add(pageIndex);
					}

				}
			}*/
        int total = sqlDao.getcountfromMysqlLike(involve_unit);
        num = total / pageSize;
        if (total % pageSize != 0) {
            num++;
        }

        JSONArray jsonArray = JSONArray.fromObject(list1);
        String json_str = jsonArray.toString();
        String result_data = "{\"totalNum\":\"" + total + "\",\"totalPages\":\"" + num + "\",\"nowPage\":\""
                + pageIndex + "\",\"resData\":" + json_str + "}";
        actionLog((String) session.getAttribute("userName"), "新增", "涉案单位");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result_data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
/*			map.put("list2", list1);//返回所有的线索信息
			map.put("totalNum2", total);
			map.put("totalPages2", num);
			map.put("nowPage2", pageIndex);
			map.put("pageList2", pageList);*/
        return "cluelist";
    }


    //删除线索
    @RequestMapping(value = "/deleteclue.php")
    public void deleteclue(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                           HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, UnknownHostException {
        String id = request.getParameter("id");
        Involve_Person involve_person = new Involve_Person();
        involve_person.setId(Integer.parseInt(id)); //获取线索id 去删除线索

        Evidence evidence = new Evidence();
        evidence.setSpersonID(Integer.parseInt(id));//获取线索id 去数据表删除对于数据
        List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);//取数据表查对应线索的数据然后删除hdfs上的文件
        sqlDao.deletefromMysql(involve_person);
        sqlDao.deletefromMysql(evidence);
        if (listfromMysql.size() > 0) {
            for (int i = 0; i < listfromMysql.size(); i++) {
                Evidence evidence1 = listfromMysql.get(i);
                String DirPath = evidence1.getDirPath();
                String evName = evidence1.getEvName();

                final String hdfsPath = DirPath;//去掉/tmp/+evName一级目录
                String mkdir = "hadoop fs -rm -R " + hdfsPath;
                Process process;

                try {
                    process = Runtime.getRuntime().exec(mkdir);
                    process.waitFor();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        actionLog((String) session.getAttribute("userName"), "删除", "删除线索");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write("{\"res\":\"succ\"}");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    //删除单位
    @RequestMapping(value = "/deleteunit.php")
    public void deleteunit(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                           HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, UnknownHostException {
        String id = request.getParameter("id");
        Involve_Unit iu = new Involve_Unit();
        iu.setId(Integer.parseInt(id)); //获取线索id 去删除线索

        Evidence evidence = new Evidence();
        evidence.setSunitID(Integer.parseInt(id));//获取线索id 去数据表删除对于数据
        List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);//取数据表查对应线索的数据然后删除hdfs上的文件
        sqlDao.deletefromMysql(iu);
        sqlDao.deletefromMysql(evidence);
        if (listfromMysql.size() > 0) {
            for (int i = 0; i < listfromMysql.size(); i++) {
                Evidence evidence1 = listfromMysql.get(i);
                String DirPath = evidence1.getDirPath();
                String evName = evidence1.getEvName();

                final String hdfsPath = DirPath;//去掉/tmp/+evName一级目录

                String mkdir = "hadoop fs -rm -R " + hdfsPath;
                Process process;

                try {
                    process = Runtime.getRuntime().exec(mkdir);
                    process.waitFor();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

        actionLog((String) session.getAttribute("userName"), "删除", "删除单位");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write("{\"res\":\"succ\"}");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


    //编辑线索
    @RequestMapping(value = "/editclue.php")
    public void editclue(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
                         HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, UnknownHostException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String emali = request.getParameter("emali");
        String region = request.getParameter("region");
        String threadType = request.getParameter("threadType");
        String susItem = request.getParameter("susItem");
        Involve_Person involve_person = new Involve_Person();

        involve_person.setId(Integer.parseInt(id));
        involve_person.setName(name);
        involve_person.setPhone(phone);
        involve_person.setEmail(emali);
        involve_person.setRegion(region);
        involve_person.setThreadType(threadType);
        involve_person.setSusItem(susItem);
        sqlDao.updateToMysql(involve_person);
        String res = "succ";

        actionLog((String) session.getAttribute("userName"), "编辑", "编辑线索");
        PrintWriter pw = null;

        try {
            pw = response.getWriter();
            pw.write("{\"res\":\"" + res + "\"}");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


	/*//关联案件
	@RequestMapping(value = "/admin/addcluecase.php")
	public void addclue(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		response.setContentType("textml; charset=UTF-8");
		String caseIDstr = request.getParameter("caseID");// 案件id
		String evIDstr = request.getParameter("evID");// 数据id
		String types = request.getParameter("types");//
		String res = "succ";

		int evID = Integer.parseInt(evIDstr);
		String[] split = caseIDstr.split(",");
		if(types.equals(1)||types=="1"||types.equals("1")){
		for (String string : split) {
			int caseID = Integer.parseInt(string);
			Evidence evidence = new Evidence();
			evidence.setSpersonID(evID);
			List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
			if(listfromMysql.size()>0){
				Involve_Person involve_person = new Involve_Person();
				for(int i=0;i<listfromMysql.size();i++){
					Evidence evidence2 = listfromMysql.get(i);
					evidence2.setCaseID(caseID);
					evidence2.setIndexFlag(-1);
					Evidence evidence3 = new Evidence();
					evidence3.setCaseID(caseID);
					evidence3.setUUID(evidence2.getUUID());
					List<Evidence> listfromMysql2 = sqlDao.getListfromMysql(evidence3);

					if(listfromMysql2.size()==0){
						sqlDao.setBeanToMysql(evidence2);
						involve_person.setId(evID);
						involve_person.setClstate(2);
						sqlDao.updateToMysql(involve_person);
						//再次关联案件修改状态
						Evidence evidence4 = new Evidence();
						evidence4.setSpersonID(evID);
						evidence4.setCaseID(-2);
						List<Evidence> listfromMysql4 = sqlDao.getListfromMysql(evidence4);
						if(listfromMysql4.size()>0){
							Evidence evidence5 = listfromMysql4.get(0);
							evidence5.setIndexFlag(-1);
							sqlDao.updateToMysqlCase(evidence5);
						}
					}else{
						involve_person.setId(evID);
						involve_person.setClstate(2);
						sqlDao.updateToMysql(involve_person);
					}
				}

			}
		}
		}else if(types.equals(2)||types=="2"||types.equals("2")){
			for (String string : split) {
				int caseID = Integer.parseInt(string);
				Evidence evidence = new Evidence();
				evidence.setSunitID(evID);
				List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
				if(listfromMysql.size()>0){
					Involve_Unit iu = new Involve_Unit();
					for(int i=0;i<listfromMysql.size();i++){
						Evidence evidence2 = listfromMysql.get(i);
						evidence2.setCaseID(caseID);
						Evidence evidence3 = new Evidence();
						evidence3.setCaseID(caseID);
						evidence3.setUUID(evidence2.getUUID());
						List<Evidence> listfromMysql2 = sqlDao.getListfromMysql(evidence3);

						if(listfromMysql2.size()==0){
							sqlDao.setBeanToMysql(evidence2);
							iu.setId(evID);
							iu.setClstate(2);
							sqlDao.updateToMysql(iu);
							//再次关联案件修改状态
							Evidence evidence4 = new Evidence();
							evidence4.setSpersonID(evID);
							evidence4.setCaseID(-2);
							List<Evidence> listfromMysql4 = sqlDao.getListfromMysql(evidence4);
							if(listfromMysql4.size()>0){
								Evidence evidence5 = listfromMysql4.get(0);
								evidence5.setIndexFlag(-1);
								sqlDao.updateToMysqlCase(evidence5);
							}
						}else{
							iu.setId(evID);
							iu.setClstate(2);
							sqlDao.updateToMysql(iu);
						}
					}

				}
			}
		}


		PrintWriter pw = null;

	try {
			pw = response.getWriter();

			pw.write("{\"res\":\"" + res + "\"}");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}*/


    // 查询证据列表下的数据列表
    @RequestMapping(value = "/admin/clueevidencelist.php")
    public String getEvidencelist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                  HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        String pageno = request.getParameter("pageno");
        String evid = request.getParameter("clueid");//线索ID

        int pageIndex = 1;
        int pageSize = 10;
        int num = 0;

        if (!StringUtils.isEmpty(pageno)) {
            pageIndex = Integer.parseInt(pageno);
        }
        Involve_Person ip = null;

        if (evid != null && !"".equals(evid)) {

            Involve_Person Person = new Involve_Person();
            Person.setId(Integer.parseInt(evid));
            List<Involve_Person> Person1 = sqlDao.getListfromMysql(Person);
            ip = Person1.get(0);
        }

//			Evidence evidence = new Evidence();
        EvidenceClue evidence = new EvidenceClue();
        evidence.setInvolvePersonID(Integer.parseInt(evid));

        List<EvidenceClue> listfromMysql1 = new ArrayList<EvidenceClue>();

        List<EvidenceClue> listfromMysql = sqlDao.getOrderListfromMysql(evidence, (pageIndex - 1) * pageSize, pageSize, "id");

        for (int i = 0; i < listfromMysql.size(); i++) {

            EvidenceClue evidence3 = listfromMysql.get(i);
            int flag = 0;
            for (int j = 0; j < listfromMysql1.size(); j++) {

                if (evidence3.getUUID().equals(listfromMysql1.get(j).getUUID())) {
                    flag = 1;
                }
            }

            if (flag == 0) {
                listfromMysql1.add(evidence3);
            }
        }


        int total = sqlDao.getcountfromMysqlLike(evidence);

        num = total / pageSize;
        if (total % pageSize != 0) {
            num++;
        }

        List<Integer> pageList = new ArrayList<Integer>();
        if (num < 5) {
            for (int i = 1; i <= num; i++) {
                pageList.add(i);
            }
        } else {
            if (pageIndex <= 5) {
                for (int i = 1; i <= 5; i++) {
                    pageList.add(i);
                }
            } else {
                if (pageIndex + 2 <= num) {
                    pageList.add(pageIndex - 2);
                    pageList.add(pageIndex - 1);
                    pageList.add(pageIndex);
                    pageList.add(pageIndex + 1);
                    pageList.add(pageIndex + 2);
                } else if (pageIndex + 1 <= num) {
                    pageList.add(pageIndex - 3);
                    pageList.add(pageIndex - 2);
                    pageList.add(pageIndex - 1);
                    pageList.add(pageIndex);
                    pageList.add(pageIndex + 1);
                } else {
                    pageList.add(pageIndex - 4);
                    pageList.add(pageIndex - 3);
                    pageList.add(pageIndex - 2);
                    pageList.add(pageIndex - 1);
                    pageList.add(pageIndex);
                }
            }
        }

        map.put("result", ip);
        map.put("logs", listfromMysql1);
        map.put("totalNum", total);
        map.put("totalPages", num);
        map.put("nowPage", pageIndex);
        map.put("pageList", pageList);


        return "elue_evidence";
    }


    // 查询单位证据列表
    @RequestMapping(value = "/admin/unitevidencelist.php")
    public String unitevidencelist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                   HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        String pageno = request.getParameter("pageno");

        String evid = request.getParameter("clueid");

        int pageIndex = 1;
        int pageSize = 10;
        int num = 0;

        if (!StringUtils.isEmpty(pageno)) {
            pageIndex = Integer.parseInt(pageno);
        }
        Involve_Unit iu = null;

        if (evid != null && !"".equals(evid)) {

            Involve_Unit Unit = new Involve_Unit();
            Unit.setId(Integer.parseInt(evid));
            List<Involve_Unit> Unit1 = sqlDao.getListfromMysql(Unit);
            iu = Unit1.get(0);
        }

        Evidence evidence = new Evidence();
        evidence.setSunitID(Integer.parseInt(evid));
        List<Evidence> listfromMysql1 = new ArrayList<Evidence>();

        List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
        for (int i = 0; i < listfromMysql.size(); i++) {
            Evidence evidence3 = listfromMysql.get(i);
            int flag = 0;
            for (int j = 0; j < listfromMysql1.size(); j++) {

                if (evidence3.getUUID().equals(listfromMysql1.get(j).getUUID())) {
                    flag = 1;
                }


            }
            if (flag == 0) {
                listfromMysql1.add(evidence3);
            }
        }

        int total = listfromMysql1.size();
        num = total / pageSize;
        if (total % pageSize != 0) {
            num++;
        }

        List<Integer> pageList = new ArrayList<Integer>();
        if (num < 5) {
            for (int i = 1; i <= num; i++) {
                pageList.add(i);
            }
        } else {
            if (pageIndex <= 5) {
                for (int i = 1; i <= 5; i++) {
                    pageList.add(i);
                }
            } else {
                if (pageIndex + 2 <= num) {
                    pageList.add(pageIndex - 2);
                    pageList.add(pageIndex - 1);
                    pageList.add(pageIndex);
                    pageList.add(pageIndex + 1);
                    pageList.add(pageIndex + 2);
                } else if (pageIndex + 1 <= num) {
                    pageList.add(pageIndex - 3);
                    pageList.add(pageIndex - 2);
                    pageList.add(pageIndex - 1);
                    pageList.add(pageIndex);
                    pageList.add(pageIndex + 1);
                } else {
                    pageList.add(pageIndex - 4);
                    pageList.add(pageIndex - 3);
                    pageList.add(pageIndex - 2);
                    pageList.add(pageIndex - 1);
                    pageList.add(pageIndex);
                }
            }
        }

        map.put("result", iu);
        map.put("logs", listfromMysql1);
        map.put("totalNum", total);
        map.put("totalPages", num);
        map.put("nowPage", pageIndex);
        map.put("pageList", pageList);


        return "unit_evidence";
    }

    /**
     * 无案件数据-上传完成修改数据
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/ThreadManage/getevidencelist.php")
    public String getevidencelist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                  HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        //String type=request.getParameter("type");
        //String sum=request.getParameter("sum");
        String uploadNum = request.getParameter("uploadNum");
        if (uploadNum != null && !"".equals(uploadNum)) {
            String getId = session.getAttribute("eviTempID").toString();

            String successNum = request.getParameter("successNum");
            String errorNum = request.getParameter("errorNum");
            String evSize = request.getParameter("evSize");

            Evidence evidence = new Evidence();
            evidence.setId(Integer.parseInt(getId));
            List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
            Evidence evidence2 = listfromMysql.get(0);
            if (!"6".equals(evidence2.getEvType())) {
                String[] uu = evidence2.getDirPath().split("/");//截取dirPath的caseid+UUid的拼接路径
                String UUcaseid = uu[uu.length - 1];
                String hdfsPath = "/tmp/emaildata/" + evidence2.getCaseID();
                final String archive = "hadoop archive -archiveName " + UUcaseid + " -p " + hdfsPath + " " + "/tmp/emaildata/";//执行合并HDFS的小文件
                final String deleteFile = "hadoop fs -rm -R " + hdfsPath;//输出之前的小文件的目录
                Process process;
                try {
                    //hadoop archive -archiveName 22099f9b6cf4ea.har.har -p /tmp/emaildata/2875 /tmp/emaildata/
                    process = Runtime.getRuntime().exec(archive);
                    //System.out.println("archive=========" + archive);
                    process.waitFor();
                    process = Runtime.getRuntime().exec(deleteFile);
                    //System.out.println("deleteFile=========" + deleteFile);
                    process.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int uploadNum2 = Integer.parseInt(evidence2.getUploadNum());
            int successNum2 = Integer.parseInt(successNum);
            int errorNum2 = uploadNum2 - successNum2;
            //evidence2.setUploadNum(uploadNum);
            evidence2.setSuccessNum(successNum);
            evidence2.setErrorNum(errorNum2 + "");
            evidence2.setEvSize(evSize);
            sqlDao.updateToMysql(evidence2);
            //actionLog((String) session.getAttribute("userName"),"编辑","案件管理");
        }
        return "cluelist";
    }

    /**
     * 线索列表-进度条实时更新数据  周武智
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/ThreadManage/updateEvidenceClue.php")
    public void updateEvidenceClue(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                   HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        String uploadNum = request.getParameter("uploadNum");
        if (uploadNum != null && !"".equals(uploadNum)) {
            String getId = session.getAttribute("eviTempIDClue").toString();

            String successNum = request.getParameter("successNum");
            String errorNum = request.getParameter("errorNum");
            String evSize = request.getParameter("evSize");
            //String cluIdE = request.getParameter("cluIdE");

            EvidenceClue evidence = new EvidenceClue();
            evidence.setId(Integer.parseInt(getId));
            List<EvidenceClue> listfromMysql = sqlDao.getListfromMysql(evidence);
            EvidenceClue evidence2 = listfromMysql.get(0);
            int uploadNum2 = Integer.parseInt(evidence2.getUploadNum());
            int successNum2 = Integer.parseInt(successNum);
            int errorNum2 = uploadNum2 - successNum2;
            //evidence2.setUploadNum(uploadNum);
            evidence2.setSuccessNum(successNum);
            evidence2.setErrorNum(errorNum2 + "");
            evidence2.setEvSize(evSize);
            sqlDao.updateToMysql(evidence2);
        }
    }

    /**
     * 无案件数据-实时更新数据
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/ThreadManage/updateEvidence.php")
    public void updateEvidence(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                               HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        //String type=request.getParameter("type");
        //String sum=request.getParameter("sum");
        String uploadNum = request.getParameter("uploadNum");
        if (uploadNum != null && !"".equals(uploadNum)) {
            String getId = session.getAttribute("eviTempID").toString();

            String successNum = request.getParameter("successNum");
            String errorNum = request.getParameter("errorNum");
            String evSize = request.getParameter("evSize");
           
            Evidence evidence = new Evidence();
            evidence.setId(Integer.parseInt(getId));
            List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
            Evidence evidence2 = listfromMysql.get(0);
            int uploadNum2 = Integer.parseInt(evidence2.getUploadNum());
            int successNum2 = Integer.parseInt(successNum);
            int errorNum2 = uploadNum2 - successNum2;
            //evidence2.setUploadNum(uploadNum);
            evidence2.setSuccessNum(successNum);
            evidence2.setErrorNum(errorNum2 + "");
            evidence2.setEvSize(evSize);
            sqlDao.updateToMysql(evidence2);
            //actionLog((String) session.getAttribute("userName"),"编辑","案件管理");
        }
    }

    @RequestMapping(value = "/admin/importevidence_clue.php")
    public String importevidence(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                 HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        return "importevidence_clue";
    }


    @RequestMapping(value = "/admin/importevidence_unit.php")
    public String importevidence_unit(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                      HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        return "importevidence_unit";
    }

    //导入数据
    @RequestMapping(value = "/admin/addclue.php")
    public String getCaseSummary(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                 HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        String evid = request.getParameter("case_summary_id");
        Involve_Person ip = new Involve_Person();

        if (evid != null && !"".equals(evid)) {

            Involve_Person Person = new Involve_Person();
            Person.setId(Integer.parseInt(evid));
            List<Involve_Person> Person1 = sqlDao.getListfromMysql(Person);
            ip = Person1.get(0);
        }

        map.put("result", ip);
        session.setAttribute("caseinfo2", ip);
        return "importevidence_clue";
    }

    //单位导入数据
    @RequestMapping(value = "/admin/addunit.php")
    public String addunit(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                          HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {


        String evid = request.getParameter("case_summary_id");
        Involve_Unit iu = new Involve_Unit();

        if (evid != null && !"".equals(evid)) {

            Involve_Unit Unit = new Involve_Unit();
            Unit.setId(Integer.parseInt(evid));
            List<Involve_Unit> Unit1 = sqlDao.getListfromMysql(Unit);
            iu = Unit1.get(0);
        }

        map.put("result", iu);
        session.setAttribute("caseinfo2", iu);
        return "importevidence_unit";
    }

    // 查询数据关联的案件id
    @RequestMapping(value = "/admin/dirpathselect.php")
    public void dirpathselect(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                              HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {


        String evid = request.getParameter("evID");

        Evidence evdence = new Evidence();
        Evidence evidence2 = new Evidence();
        evdence.setId(Integer.parseInt(evid));
        List<Evidence> Unit1 = sqlDao.getListfromMysql(evdence);
        Evidence evidence = Unit1.get(0);
        String dirpath = evidence.getDirPath();
        evidence2.setDirPath(dirpath);
        List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence2);
        String evid2 = null;
        for (Evidence evidence3 : listfromMysql) {
            int caseid = evidence3.getCaseID();

            int id = evidence3.getId();

            if (evid2 == null) {
                evid2 = id + "";
            } else {
                evid2 += "," + id;
            }


        }

        String result_data = "{\"caseid\":\"" + evid2 + "\"}";
       
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result_data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    // 查询所有的更新日志
    @RequestMapping(value = "/admin/aboutlogsss.php")
    public String aboutlogsss(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                              HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        String pageno = request.getParameter("pageno");


        int pageIndex = 1;
        int pageSize = 100;
        int num = 0;

        if (!StringUtils.isEmpty(pageno)) {
            pageIndex = Integer.parseInt(pageno);
        }
        UpdateLog update = new UpdateLog();


        List<UpdateLog> logs = sqlDao.getOrderListfromMysqlLike(update, (pageIndex - 1) * pageSize, pageSize, "id");
        int total = sqlDao.getcountfromMysqlLike(update);
        num = total / pageSize;
        List<Integer> pageList = new ArrayList<Integer>();
        if (num < 5) {
            for (int i = 1; i <= num; i++) {
                pageList.add(i);
            }
        } else {
            if (pageIndex <= 5) {
                for (int i = 1; i <= 5; i++) {
                    pageList.add(i);
                }
            } else {
                if (pageIndex + 2 <= num) {
                    pageList.add(pageIndex - 2);
                    pageList.add(pageIndex - 1);
                    pageList.add(pageIndex);
                    pageList.add(pageIndex + 1);
                    pageList.add(pageIndex + 2);
                } else if (pageIndex + 1 <= num) {
                    pageList.add(pageIndex - 3);
                    pageList.add(pageIndex - 2);
                    pageList.add(pageIndex - 1);
                    pageList.add(pageIndex);
                    pageList.add(pageIndex + 1);
                } else {
                    pageList.add(pageIndex - 4);
                    pageList.add(pageIndex - 3);
                    pageList.add(pageIndex - 2);
                    pageList.add(pageIndex - 1);
                    pageList.add(pageIndex);
                }
            }
        }

        JSONArray jsonArray = JSONArray.fromObject(logs);
        String json_str = jsonArray.toString();
        String result_data = "{\"totalNum\":\"" + total + "\",\"totalPages\":\"" + num + "\",\"nowPage\":\""
                + pageIndex + "\",\"resData\":" + json_str + "}";


        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result_data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        map.put("logs", result_data);
        map.put("totalNum", total);
        map.put("totalPages", num);
        map.put("nowPage", pageIndex);
        //map.put("pageList", pageList);

        // request.setAttribute("lab", map);
        return "updatalog";
    }

    // 线索导出
    @RequestMapping("/admin/Exportclue.php")
    @ResponseBody
    public void Exportclue(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                           HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        String stunoStr = request.getParameter("stunoStr");
        String[] getStr = stunoStr.split(",");

        Involve_Person involve_person = new Involve_Person();
        String filename = "data";
        String projectpath = request.getSession().getServletContext().getRealPath("");

        try {
            HSSFWorkbook wb1 = null;
            POIFSFileSystem fs = null;
            String path1 = projectpath + filename + ".xls";
            File file = new File(path1);
            create2(path1);
            fs = new POIFSFileSystem(new FileInputStream(path1));
            wb1 = new HSSFWorkbook(fs);

            for (String str : getStr) {
                int a = Integer.parseInt(str);
                involve_person.setId(a);
                List<Involve_Person> slist = sqlDao.getListfromMysql(involve_person);
                for (int i = 0; i < slist.size(); i++) {
                    write2(slist.get(i), wb1, path1);
                }
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
						/* ous.flush(); */
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
        } catch (Exception e) {
            // System.out.println(e);
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
        cell.setCellValue("线索编号");
        cell = row.createCell(1);
        cell.setCellValue("线索类型");
        cell = row.createCell(2);
        cell.setCellValue("线索接收时间");
        cell = row.createCell(3);
        cell.setCellValue("作案时间");
        cell = row.createCell(4);
        cell.setCellValue("线索来源方式");
        cell = row.createCell(5);
        cell.setCellValue("线索内容");
        cell = row.createCell(6);
        cell.setCellValue("创建时间");
        cell = row.createCell(7);
        cell.setCellValue("创建人");
        cell = row.createCell(8);
        cell.setCellValue("分局");
        cell = row.createCell(9);
        cell.setCellValue("线索状态");
        cell = row.createCell(10);
        FileOutputStream os = new FileOutputStream(path);
        // FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
        wb.write(os);
        os.close();
    }

    public static void write2(Involve_Person ip, HSSFWorkbook wb1, String path1) throws Exception {
        SqlDao sqlDao = new SqlDao();
        HSSFSheet sheet = wb1.getSheetAt(0);
        int begin = sheet.getFirstRowNum();
        int end = sheet.getLastRowNum();
        int m = 0;
        for (int n = begin; n <= end; n++) {
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
        cell.setCellValue(ip.getThreadNum());
        cell = row1.createCell(1);
        cell.setCellValue(ip.getThreadType());
        cell = row1.createCell(2);
        cell.setCellValue(ip.getThreadAcceptDate());
        cell = row1.createCell(3);
        cell.setCellValue(ip.getCrimeDate());
        cell = row1.createCell(4);
        cell.setCellValue(ip.getThreadSource());
        cell = row1.createCell(5);
        cell.setCellValue(ip.getSusItem());
        cell = row1.createCell(6);
        cell.setCellValue(ip.getCreatedTime());
        cell = row1.createCell(7);
        cell.setCellValue(ip.getReportPerson());
        cell = row1.createCell(8);
        /* YC: 获取分局信息*/
        String departMentsIDs = ip.getUserPartment();   //所属分局
        Department department = new Department();
        department.setId(Integer.parseInt(departMentsIDs));
        Department department2 = sqlDao.getListfromMysql(department).get(0);
        String departMentsNames = department2.getDepartmentName();
        cell.setCellValue(departMentsNames);
        cell = row1.createCell(9);
        cell.setCellValue(ip.getDisposal());
        cell = row1.createCell(10);

        FileOutputStream os = new FileOutputStream(path1);
        wb1.write(os);
        os.close();
    }

    //编辑线索
    @RequestMapping(value = "/admin/edit_person.php")
    public String edit_person(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
                              HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, UnknownHostException {
        String id = request.getParameter("id");
        Involve_Person involve_person = new Involve_Person();
        Involve_Person involve_Person2 = new Involve_Person();
        involve_person.setId(Integer.parseInt(id));
        List<Involve_Person> listfromMysql = sqlDao.getListfromMysql(involve_person);

        //	SuspectInfo suspectInfo = new SuspectInfo();//嫌疑人
        //	SuspectAddress suspectaddress = new SuspectAddress();//地区
        //	SuspectUnit unit = new SuspectUnit();//单位
        List<SuspectAddress> listaddress = new ArrayList<SuspectAddress>();//地区
        List<SuspectInfo> supec = new ArrayList<SuspectInfo>();//嫌疑人
        List<SuspectUnit> sunit = new ArrayList<SuspectUnit>();//单位
        String stu = "";
        String Unit = "";
        String SuAddress = "";
        if (listfromMysql.size() > 0) {
            involve_Person2 = listfromMysql.get(0);
            String threadAcceptDate = involve_Person2.getSuspectAddressID();//获取地区id
            String[] mains = threadAcceptDate.split(",");
            if (mains.length > 0 && mains != null && !"".equals(mains)) {
                for (String mainparit : mains) {
                    if (mainparit != null && mainparit.length() != 0) {
                        SuspectAddress sadd = new SuspectAddress();
                        sadd.setId(Integer.parseInt(mainparit));
                        List<SuspectAddress> listfromMysql3 = sqlDao.getListfromMysql(sadd);
                        SuspectAddress suspectaddress = listfromMysql3.get(0);
                        String Province = suspectaddress.getProvince();
                        String City = suspectaddress.getCity();
                        String Town = suspectaddress.getTown();
                        if ("".equals(SuAddress)) {
                            SuAddress = Province + " " + City + " " + Town;
                        } else {
                            SuAddress += "/" + Province + " " + City + " " + Town;
                        }
                    }
                }
            }

            String SuspectID = involve_Person2.getSuspectID();//获取嫌疑人id
            String[] mains2 = SuspectID.split(",");
            
            if (mains2.length > 0 && mains2 != null && !"".equals(mains2)) {
                for (String mainparit : mains2) {
                    SuspectInfo sup = new SuspectInfo();
                    if (mainparit != null && mainparit.length() != 0) {
                        sup.setId(Integer.parseInt(mainparit));
                        List<SuspectInfo> listfromMysql2 = sqlDao.getListfromMysql(sup);
                        if (listfromMysql2.size() > 0) {
                            SuspectInfo suspect = listfromMysql2.get(0);

                            String SuspectName = suspect.getSuspectName();
                            String SuspectPhone = suspect.getSuspectPhone();
                            String SuspectMail = suspect.getSuspectMail();
                            if ("".equals(stu)) {
                                stu = SuspectName + " " + SuspectPhone + " " + SuspectMail;
                            } else {
                                stu += "/" + SuspectName + " " + SuspectPhone + " " + SuspectMail;
                            }
                            //supec.add(suspect);
                        }
                    }
                }
            }


            String SuspectUnit = involve_Person2.getSuspectUnit();//获取单位id
            String[] mains3 = SuspectUnit.split(",");
            if (mains3.length > 0 && mains3 != null && !"".equals(mains3)) {
                for (String mainparit : mains3) {
                    if (mainparit != null && mainparit.length() != 0) {
                        SuspectUnit scunit = new SuspectUnit();
                        scunit.setId(Integer.parseInt(mainparit));
                        List<SuspectUnit> listfromMysql4 = sqlDao.getListfromMysql(scunit);
                        if (listfromMysql4.size() > 0) {
                            SuspectUnit unit = listfromMysql4.get(0);
                            String Name = unit.getName();
                            String Address = unit.getAddress();
                            String Number = unit.getCustomsRegistrationNumber();
                            if ("".equals(Unit)) {
                                Unit = Name + " " + Address + " " + Number;
                            } else {
                                Unit += "/" + Name + " " + Address + " " + Number;
                            }
                            //sunit.add(unit);
                        }
                    }
                }
            }

        }

        map.put("unit", Unit);//单位
        map.put("SuAddress", SuAddress);//地区
        map.put("Person", involve_Person2);//线索
        map.put("stu", stu);//嫌疑人

        return "edit_Person";
    }

    // 点提交按钮 编辑案件
    @RequestMapping(value = "/admin/editcaseconfirm.php")
    public String editcasecomfirm(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
        response.setContentType("text/html;charset=utf-8");
        String clue_id = request.getParameter("clue_id");// 案件id
        String threadAcceptDate = (String) session.getAttribute("threadAcceptDate");//线索接受日期
        String crimeDate = request.getParameter("crimeDate");//作案日期
        String susItem = request.getParameter("susItem");//内容
        String mainParty = request.getParameter("mainParty");//人员
        String unitMainPartyss = request.getParameter("unitMainPartys");//单位
        String cityMainPartyss = request.getParameter("cityMainPartys");//地区
        String threadtypes = request.getParameter("threadtypes");//类型
        String threadSources = request.getParameter("threadSources");//来源
        
        // 添加嫌疑人
        String mainParty2 = "";

        String suspectUnit = "";
        String suspectAddressID = "";
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String[] splits = mainParty.split("/");
        String[] splits2 = unitMainPartyss.split("/");//单位
        String[] splits3 = cityMainPartyss.split("/");

        for (String string : splits3) {
            String province = null;//省
            String city = null;//市
            String town = null;//区
            String[] split = string.split(" ");
            if (split.length > 0) {
                province = split[0];
            }
            if (split.length > 1) {
                String spu = split[1];
                city = spu;
            }
            if (split.length > 2) {
                String spu2 = split[2];
                town = spu2;
            }

            if (province != null && !"".equals(province)) {
                SuspectAddress suspectaddress = new SuspectAddress();

                suspectaddress.setProvince(province);
                suspectaddress.setCity(city);
                suspectaddress.setTown(town);
                List<SuspectAddress> listfromMysql = sqlDao.getListfromMysql(suspectaddress);

                int i = listfromMysql.size();
                if (i != 0) {
                    int id = listfromMysql.get(0).getId();
                    if (suspectAddressID == null) {
                        suspectAddressID = "" + id;
                    } else {
                        suspectAddressID += "," + id;
                    }
                } else {

                    sqlDao.setBeanToMysql(suspectaddress);
                    //	actionLog((String) session.getAttribute("userName"), "新增", "案件列表"+ caseName);
                    List<SuspectAddress> listfromMysql2 = sqlDao.getListfromMysql(suspectaddress);
                    int id = listfromMysql2.get(0).getId();

                    if (suspectAddressID == null) {
                        suspectAddressID = "" + id;
                    } else {
                        suspectAddressID += "," + id;
                    }
                }
            }
        }

        if (suspectAddressID.length() > 1) {
            String substring = suspectAddressID.substring(0, 1);
            if (",".equals(substring)) {
                suspectAddressID = suspectAddressID.substring(1, suspectAddressID.length());
            }
        }

        for (String string : splits2) {
            String name = null;
            String address = null;//地点
            String customsRegistrationNumber = null;//编号
            String[] split = string.split(" ");
            if (split.length > 0) {
                name = split[0];
            }
            if (split.length > 1) {
                String spu = split[1];
                address = spu;
            }
            if (split.length > 2) {
                String spu2 = split[2];
                customsRegistrationNumber = spu2;
            }

            if (name != null && !"".equals(name)) {
                SuspectUnit unit = new SuspectUnit();

                unit.setName(name);
                unit.setAddress(address);
                unit.setCustomsRegistrationNumber(customsRegistrationNumber);
                unit.setThreadID(clue_id);
                List<SuspectUnit> listfromMysql = sqlDao.getListfromMysql(unit);

                int i = listfromMysql.size();
                if (i != 0) {
                    int id = listfromMysql.get(0).getId();
                    if (suspectUnit == null) {
                        suspectUnit = "" + id;
                        
                    } else {
                        suspectUnit += "," + id;
                    }
                } else {

                    sqlDao.setBeanToMysql(unit);
                    //	actionLog((String) session.getAttribute("userName"), "新增", "案件列表"+ caseName);
                    List<SuspectUnit> listfromMysql2 = sqlDao.getListfromMysql(unit);
                    int id = listfromMysql2.get(0).getId();

                    /* YC: 插入线索重复的涉嫌单位信息*/
                    ClueRepeat clueRepeat = new ClueRepeat();
                    clueRepeat.setType("1"); // 单位名称
                    clueRepeat.setItem(name);
                    clueRepeat.setClueID(clue_id);
                    clueRepeat.setPrivateID((new Integer(id)).toString());
                    sqlDao.setBeanToMysql(clueRepeat);

                    ClueRepeat clueRepeat1 = new ClueRepeat();
                    clueRepeat1.setType("2"); // 单位编号
                    clueRepeat1.setItem(customsRegistrationNumber);
                    clueRepeat1.setClueID(clue_id);
                    clueRepeat1.setPrivateID((new Integer(id)).toString());
                    sqlDao.setBeanToMysql(clueRepeat1);

                    if (suspectUnit == null) {
                        suspectUnit = "" + id;
                    } else {
                        suspectUnit += "," + id;
                    }
                }
            }
        }
        if (suspectUnit.length() > 1) {
            String substring = suspectUnit.substring(0, 1);
            if (",".equals(substring)) {
                suspectUnit = suspectUnit.substring(1, suspectUnit.length());
            }
        }
        for (String string : splits) {
            String name = null;
            String number = null;
            String email = null;
            String[] split = string.split(" ");
            if (split.length > 0) {
                name = split[0];
            }
            if (split.length > 1) {
//						number = split[1];
                String spu = split[1];
                if (spu.indexOf("@") > 0) {
                    email = spu;
                } else {
                    number = spu;
                }
            }
            if (split.length > 2) {
//						email = split[2];
                String spu2 = split[2];
                if (spu2.indexOf("@") > 0) {
                    email = spu2;
                } else {
                    number = spu2;
                }
            }

            if (name != null && !"".equals(name)) {
                SuspectInfo suspectInfo = new SuspectInfo();

                suspectInfo.setSuspectName(name);
                suspectInfo.setSuspectPhone(number);
                suspectInfo.setSuspectMail(email);
                suspectInfo.setThreadID(clue_id);
                List<SuspectInfo> listfromMysql = sqlDao.getListfromMysql(suspectInfo);

                int i = listfromMysql.size();
                if (i != 0) {
                    int id = listfromMysql.get(0).getId();
                    if (mainParty2 == null) {
                        mainParty2 = "" + id;
                        
                    } else {
                        mainParty2 += "," + id;
                    }
                } else {
                    suspectInfo.setCreateTime(dateFormat2.format(new Date()));
                    sqlDao.setBeanToMysql(suspectInfo);
                    //	actionLog((String) session.getAttribute("userName"), "新增", "案件列表"+ caseName);
                    List<SuspectInfo> listfromMysql2 = sqlDao.getListfromMysql(suspectInfo);
                    int id = listfromMysql2.get(0).getId();
                    /* YC: 插入线索重复的涉嫌人员信息*/
                    ClueRepeat clueRepeat = new ClueRepeat();
                    clueRepeat.setType("3"); //人员号码
                    clueRepeat.setItem(number);
                    clueRepeat.setClueID(clue_id);
                    clueRepeat.setPrivateID((new Integer(id)).toString());
                    sqlDao.setBeanToMysql(clueRepeat);

                    ClueRepeat clueRepeat1 = new ClueRepeat();
                    clueRepeat1.setType("4"); //人员邮箱
                    clueRepeat1.setItem(email);
                    clueRepeat1.setClueID(clue_id);
                    clueRepeat1.setPrivateID((new Integer(id)).toString());
                    sqlDao.setBeanToMysql(clueRepeat1);

                    if (mainParty2 == null) {
                        mainParty2 = "" + id;
                    } else {
                        mainParty2 += "," + id;
                    }
                }
            }
        }
        if (mainParty2.length() > 1) {
            String substring = mainParty2.substring(0, 1);
            if (",".equals(substring)) {
                mainParty2 = mainParty2.substring(1, mainParty2.length());
            }
        }

        Involve_Person involve_person = new Involve_Person();
        involve_person.setId(Integer.parseInt(clue_id));
        involve_person.setThreadAcceptDate(threadAcceptDate);
        involve_person.setCrimeDate(crimeDate);
        involve_person.setSusItem(susItem);
        involve_person.setThreadType(threadtypes);
        involve_person.setThreadSource(threadSources);
        involve_person.setSuspectID(mainParty2);
        involve_person.setSuspectUnit(suspectUnit);
        involve_person.setSuspectAddressID(suspectAddressID);

        sqlDao.updateToMysql(involve_person);

        //	actionLog((String) session.getAttribute("userName"), "编辑", "案件列表:"+ caseName);
        //writeLog( userName,"修改", userName + " 修改案件:" + caseName);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write("{\"res\":\"succ\"}");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return "cluelist";
    }


    /**
     * 线索登记
     *
     * @author YC
     * @create 2017/11/10 14:32
     */
    @RequestMapping("/admin/addInvolve_Person.php")
    public String adminaddInvolve_Person(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
                                         HttpSession session) {
        final String userName = (String) session.getAttribute("userName");
//		        String section = (String) session.getAttribute("section");
//		        User user = (User) session.getAttribute("user");
        String dataTypes = request.getParameter("dataTypes");
        String status = request.getParameter("status");
        String comment = request.getParameter("comment");// 证据描述
        String threadNum = request.getParameter("threadNum"); //线索编号
        String mainParty = request.getParameter("mainPartyHidden"); //涉嫌人员
        String suspectUnit = request.getParameter("unitMainParty"); //涉嫌单位
        String susItem = request.getParameter("susItem"); //线索内容
        String region = request.getParameter("cityMainParty"); //作案区域
        String threadSource = request.getParameter("threadSource"); //线索来源
        String threadAcceptDate = request.getParameter("threadAcceptDate"); //线索接收日期
        String crimeDate = request.getParameter("crimeDate"); //作案日期
        String[] threadType = request.getParameterValues("threadTypePlus"); //线索类型

        User user = (User) session.getAttribute("user");            //登录人姓名
        int userIDss = user.getId();
        String userPartmentss = user.getPartment();
        String userSectionss = user.getSection();
        String userRoless = user.getUserrole();

        String departmentName = "";
        String sectionName = "";
        String roleName = "";
//		        if (!"".equals(userPartmentss) && userPartmentss != null) {
//
//		        	Department department = new Department();
//					department.setId(Integer.parseInt(userPartmentss));
//		        	Department departmentList = sqlDao.getListfromMysql(department).get(0);
//		        	departmentName = departmentList.getDepartmentName();
//				}

//		        if (!"".equals(userSectionss) && userSectionss != null) {
//
//		        	Section section = new Section();
//		        	section.setId(Integer.parseInt(userSectionss));
//		        	Section sectionList = sqlDao.getListfromMysql(section).get(0);
//		        	sectionName = sectionList.getSectionName();
//				}

//		        if (!"".equals(userRoless) && userRoless != null) {
//
//		        	Role role = new Role();
//		        	role.setId(Integer.parseInt(userRoless));
//		        	Role roleList = sqlDao.getListfromMysql(role).get(0);
//		        	roleName = roleList.getRoleName();
//				}

        // 添加线索类型
        String getThreadType = "";
        if (request.getParameterValues("threadType") != null) {
            for (String s : threadType) {
                String s1 = s + ",";
                getThreadType += s1;
            }
            getThreadType = getThreadType.substring(0, getThreadType.length() - 1);
        }

        // 添加作案区域信息
        String addressMainParty2 = null;
        String[] addressSplits = region.split("/");
        for (String string : addressSplits) {
            String province = null;
            String city = null;
            String town = null;
            String[] split = string.split(" ");
            if (split.length > 0) {
                province = split[0];
            }

            if (split.length > 1) {
                city = split[1];
            }

            if (split.length > 2) {
                town = split[2];
            }

            //下面的判断语句作用是防止插入空值
            if ((!"".equals(province) && province != null) || (!"".equals(city) && city != null) || (!"".equals(town) && town != null)) {
                SuspectAddress suspectAddress = new SuspectAddress();
                suspectAddress.setProvince(province);
                suspectAddress.setCity(city);
                suspectAddress.setTown(town);
                suspectAddress.setRegion(string);
                sqlDao.setBeanToMysql(suspectAddress);
                List<SuspectAddress> listfromMysql2 = sqlDao.getListfromMysql(suspectAddress);
                int id = listfromMysql2.get(0).getId();
                if (addressMainParty2 == null) {
                    addressMainParty2 = "" + id;
                } else {
                    addressMainParty2 += "," + id;
                }

            }
        }


        Involve_Person involve_person = new Involve_Person();
        involve_person.setSusItem(susItem);
        involve_person.setThreadType(getThreadType);
        involve_person.setCreatedTime(dateFormat.format(new Date()));
//		        involve_person.setSuspectID(mainParty2);
        involve_person.setReportPerson(userName);
//		        involve_person.setSuspectUnit(unitMainParty2);
        involve_person.setSuspectAddressID(addressMainParty2);
        involve_person.setThreadSource(threadSource);
        involve_person.setCrimeDate(crimeDate);
        involve_person.setThreadAcceptDate(threadAcceptDate);
        involve_person.setStatus("办案中");
        involve_person.setDisposal("未处置");
        involve_person.setThreadNum(threadNum);
        Integer userIDInteger = userIDss;
        involve_person.setUserID(userIDInteger.toString());

        involve_person.setUserPartment(userPartmentss);
        involve_person.setUserSection(userSectionss);
        involve_person.setUserRole(userRoless);

        sqlDao.setBeanToMysql(involve_person);
        List<Involve_Person> sqlDaoListfromMysql = sqlDao.getListfromMysql(involve_person);
        Integer getClueID = sqlDaoListfromMysql.get(0).getId();// 获取线索ID
        int countNum = 0;

        // 添加涉嫌人员
        String mainParty2 = null;
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String[] splits = mainParty.split("/");
        for (String string : splits) {
            String name = null;
            String number = null;
            String email = null;
            String[] split = string.split(" ");
            if (split.length > 0) {
                name = split[0];
            }

            if (split.length > 1) {
                String spu = split[1];
                if (spu.indexOf("@") > 0) {
                    email = spu;
                } else {
                    number = spu;
                }
            }

            if (split.length > 2) {
                String spu2 = split[2];
                if (spu2.indexOf("@") > 0) {
                    email = spu2;
                } else {
                    number = spu2;
                }
            }

            //下面的判断语句作用是防止插入空值
            if ((!"".equals(name) && name != null) || (!"".equals(number) && number != null) || (!"".equals(email) && email != null)) {
                SuspectInfo suspectInfo = new SuspectInfo();
                suspectInfo.setSuspectName(name);
                suspectInfo.setSuspectPhone(number);
                suspectInfo.setSuspectMail(email);
                suspectInfo.setCreateTime(dateFormat2.format(new Date()));
                suspectInfo.setThreadID(getClueID.toString());
                sqlDao.setBeanToMysql(suspectInfo);
                List<SuspectInfo> listfromMysql2 = sqlDao.getListfromMysql(suspectInfo);
                int id = listfromMysql2.get((listfromMysql2.size() - 1)).getId();
                if (mainParty2 == null) {
                    mainParty2 = "" + id;
                } else {
                    mainParty2 += "," + id;
                }
                Integer getID = new Integer(id);
                if (!StringUtils.isEmpty(number)) {
                    if (number.length() != 0 && number != null) {

                        ClueRepeat clueRepeatForEach = new ClueRepeat();
                        clueRepeatForEach.setClueID(getClueID.toString());
                        clueRepeatForEach.setType("3");
                        clueRepeatForEach.setItem(number);
                        clueRepeatForEach.setPrivateID(getID.toString());
                        sqlDao.setBeanToMysql(clueRepeatForEach);

                    }

                }

                if (!StringUtils.isEmpty(email)) {
                    if (email.length() != 0 && email != null) {

                        ClueRepeat clueRepeatForEach1 = new ClueRepeat();
                        clueRepeatForEach1.setClueID(getClueID.toString());
                        clueRepeatForEach1.setType("4");
                        clueRepeatForEach1.setItem(email);
                        clueRepeatForEach1.setPrivateID(getID.toString());
                        sqlDao.setBeanToMysql(clueRepeatForEach1);
                    }

                }

            }


        }

        // 添加涉嫌单位
        String unitMainParty2 = null;
        String[] unitSplits = suspectUnit.split("/");
        for (String string : unitSplits) {
            String unitName = null;
            String address = null;
            String customsRegistrationNumber = null;
            String privateID = null;
            String[] split = string.split(" ");
            if (split.length > 0) {
                unitName = split[0];
            }

            if (split.length > 1) {
                address = split[1];
            }

            if (split.length > 2) {
                customsRegistrationNumber = split[2];
            }
            //下面的判断语句作用是防止插入空值
            if ((!"".equals(unitName) && unitName != null) || (!"".equals(address) && address != null) || (!"".equals(customsRegistrationNumber) && customsRegistrationNumber != null)) {
                SuspectUnit supectUnit = new SuspectUnit();
                supectUnit.setName(unitName);
                supectUnit.setAddress(address);
                supectUnit.setCustomsRegistrationNumber(customsRegistrationNumber);
                supectUnit.setThreadID(getClueID.toString());

                sqlDao.setBeanToMysql(supectUnit);
                List<SuspectUnit> listfromMysql2 = sqlDao.getListfromMysql(supectUnit);
                int id = listfromMysql2.get((listfromMysql2.size() - 1)).getId();
                if (unitMainParty2 == null) {
                    unitMainParty2 = "" + id;
                } else {
                    unitMainParty2 += "," + id;
                }
                Integer getID = new Integer(id);
                if (!StringUtils.isEmpty(unitName)) {
                    if (unitName.length() != 0 && unitName != null) {
                        ClueRepeat clueRepeatForEach = new ClueRepeat();
                        clueRepeatForEach.setClueID(getClueID.toString());
                        clueRepeatForEach.setType("1");
                        clueRepeatForEach.setItem(unitName);
                        clueRepeatForEach.setPrivateID(getID.toString());
                        sqlDao.setBeanToMysql(clueRepeatForEach);
                    }

                }

                if (!StringUtils.isEmpty(customsRegistrationNumber)) {
                    if (customsRegistrationNumber.length() != 0 && customsRegistrationNumber != null) {
                        ClueRepeat clueRepeatForEach1 = new ClueRepeat();
                        clueRepeatForEach1.setClueID(getClueID.toString());
                        clueRepeatForEach1.setType("2");
                        clueRepeatForEach1.setItem(customsRegistrationNumber);
                        clueRepeatForEach1.setPrivateID(getID.toString());
                        sqlDao.setBeanToMysql(clueRepeatForEach1);
                    }

                }

            }


        }

        /* YC: 插入涉嫌人员和涉嫌单位的数据*/
        Involve_Person involve_person1 = new Involve_Person();
        involve_person1.setId(getClueID);
        List<Involve_Person> listfromMysql = sqlDao.getListfromMysql(involve_person1);
        Involve_Person involve_person2 = listfromMysql.get(0);
       
        involve_person2.setSuspectID(mainParty2);
        involve_person2.setSuspectUnit(unitMainParty2);
        sqlDao.updateToMysql(involve_person2);

        return "newThread_success";
    }


    /**
     * 线索登记_涉嫌人员批量导入的功能
     *
     * @author YC
     * @create 2017/11/10 14:27
     */
    @RequestMapping(value = "/exThreadSuspectlist.php")
    public void exSuspectlist(MultipartFile fileLoad, HttpServletRequest request, Map<String, Object> map,
                              HttpServletResponse response, HttpSession session) throws IOException {
        String res = "";
        String suspectNameID = "";
        if (fileLoad.getSize() > 0) {
            // 得到项目在服务器的真实根路径，如：/home/tomcat/webapp/项目名/
            String path = session.getServletContext().getRealPath("");
            String fileName = fileLoad.getOriginalFilename();
            File file = new File(path, fileName);
            fileLoad.transferTo(file);
            List<List<Object>> lists = ReadExcel.readExcel(file);
            int i = 0;
            List<SuspectInfo> suBeans = new ArrayList<SuspectInfo>();
            if (lists.size() > 0) {
                for (List<Object> list2 : lists) {
                    SuspectInfo suBean = new SuspectInfo();
                    if (i++ == 0) {
                        if (!(list2.get(0).toString()).equals("姓名")) {
                            return;
                        }

                        if (!(list2.get(1).toString()).equals("性别")) {
                            return;
                        }

                        if (!(list2.get(2).toString()).equals("手机号")) {
                            return;
                        }

                        if (!(list2.get(3).toString()).equals("电子邮箱")) {
                            return;
                        }

                        if (!(list2.get(4).toString()).equals("QQ号码")) {
                            return;
                        }

                        if (!(list2.get(5).toString()).equals("位置")) {
                            return;
                        }

                        if (!(list2.get(6).toString()).equals("所在组织名称")) {
                            return;
                        }

                        if (!(list2.get(7).toString()).equals("组织地址")) {
                            return;
                        }

                        if (!(list2.get(8).toString()).equals("身份证号")) {
                            return;
                        }

                        if (!(list2.get(9).toString()).equals("社保号")) {
                            return;
                        }

                        if (!(list2.get(10).toString()).equals("护照号")) {
                            return;
                        }

                        if (!(list2.get(11).toString()).equals("Facebook账号")) {
                            return;
                        }

                        if (!(list2.get(12).toString()).equals("Twitter账号")) {
                            return;
                        }

                        if (!(list2.get(13).toString()).equals("微信号")) {
                            return;
                        }

                        if (!(list2.get(14).toString()).equals("标签")) {
                            return;
                        }

                    }

//		                    continue; // 表头不读取
                    if (list2 == null || list2.size() == 0)
                        break; // 读取到空结束

                    if (list2.get(0).toString() == null) {
                        suBean.setSuspectName("");
                    } else {
                        suBean.setSuspectName(list2.get(0).toString());
                    }

                    if (list2.get(1).toString() == null) {
                        suBean.setSuspectSex("");
                    } else {
                        suBean.setSuspectSex(list2.get(1).toString());
                    }

                    if (list2.get(2).toString() == null) {
                        suBean.setSuspectPhone("");
                    } else {
                        suBean.setSuspectPhone(list2.get(2).toString());
                    }

                    if (list2.get(3).toString() == null) {
                        suBean.setSuspectMail("");
                    } else {
                        suBean.setSuspectMail(list2.get(3).toString());
                    }

                    if (list2.get(4).toString() == null) {
                        suBean.setSuspectQQ("");
                    } else {
                        suBean.setSuspectQQ(list2.get(4).toString());
                    }

                    if (list2.get(5).toString() == null) {
                        suBean.setSuspectHomeAddress("");
                    } else {
                        suBean.setSuspectHomeAddress(list2.get(5).toString());
                    }

                    if (list2.get(6).toString() == null) {
                        suBean.setSuspectUnitName("");
                    } else {
                        suBean.setSuspectUnitName(list2.get(6).toString());
                    }

                    if (list2.get(7).toString() == null) {
                        suBean.setSuspectUnitAddress("");
                    } else {
                        suBean.setSuspectUnitAddress(list2.get(7).toString());
                    }

                    if (list2.get(8).toString() == null) {
                        suBean.setSuspectIDCardNumber("");
                    } else {
                        suBean.setSuspectIDCardNumber(list2.get(8).toString());
                    }

                    if (list2.get(9).toString() == null) {
                        suBean.setSuspectSocialSecurity("");
                    } else {
                        suBean.setSuspectSocialSecurity(list2.get(9).toString());
                    }

                    if (list2.get(10).toString() == null) {
                        suBean.setSuspectPassport("");
                    } else {
                        suBean.setSuspectPassport(list2.get(10).toString());
                    }

                    if (list2.get(11).toString() == null) {
                        suBean.setSuspectFacebook("");
                    } else {
                        suBean.setSuspectFacebook(list2.get(11).toString());
                    }

                    if (list2.get(12).toString() == null) {
                        suBean.setSuspectTwitter("");
                    } else {
                        suBean.setSuspectTwitter(list2.get(12).toString());
                    }

                    if (list2.get(13).toString() == null) {
                        suBean.setSuspectMicroletters("");
                    } else {
                        suBean.setSuspectMicroletters(list2.get(13).toString());
                    }

                    if (list2.get(14).toString() == null) {
                        suBean.setLabel("");
                    } else {
                        suBean.setLabel(list2.get(14).toString());
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String createTime = sdf.format(date);
                    suBean.setCreateTime(createTime);

                    suBeans.add(suBean);
                }


                for (SuspectInfo suBean : suBeans) {
                    sqlDao.setBeanToMysql(suBean);
                    String getName = sqlDao.getListfromMysql(suBean).get(0).getSuspectName();
                    String getPhone = sqlDao.getListfromMysql(suBean).get(0).getSuspectPhone();
                    String getEmail = sqlDao.getListfromMysql(suBean).get(0).getSuspectMail();
                    suspectNameID += getName + " " + getPhone + " " + getEmail + "/";
                }

                if (suspectNameID.length() > 0) {
                    suspectNameID = suspectNameID.substring(0, suspectNameID.length() - 1);
                }

                actionLog((String) session.getAttribute("userName"), "导入", "嫌疑人画像 导入嫌疑人");
                res = "导入成功！";
            } else {
                res = "导入失败！";
            }

        } else {
            res = "导入失败！";
        }
        map.put("res", res);
        map.put("suspectNameID", suspectNameID);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JsonUtil.map2json(map));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

    /**
     * 线索登记_涉嫌人员，查询涉嫌单位是否存在
     *
     * @author YC
     * @create 2017/11/10 14:30
     */
    @RequestMapping(value = "/checkSuspectUnit.php")
    public void checkSuspectUnit(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        response.setContentType("text/html;charset=utf-8");
        String SuspectPhone = request.getParameter("SuspectPhone");
        String suName = request.getParameter("suName");
        String suPhone = request.getParameter("suPhone");
        String suEmail = request.getParameter("suEmail");
        List<SuspectUnit> suspectInfoList = new ArrayList<SuspectUnit>();
        if (SuspectPhone != null && !"".equals(SuspectPhone)) {
            if (!"".equals(suName) && suName != null) {
                SuspectUnit suspectUnit = new SuspectUnit();
                suspectUnit.setName(suName);
                List<SuspectUnit> eviTemps = sqlDao.getListfromMysql(suspectUnit);
                if (eviTemps.size() > 0) {
                    suspectUnit = eviTemps.get(0);
                    if (suspectInfoList.size() == 0) {
                       
                        suspectInfoList.add(suspectUnit);
                    } else {
                        int flag = 0;
                        for (SuspectUnit suspectInfo11 : suspectInfoList) {
                            if (suspectInfo11.getId() != suspectUnit.getId()) {
                                flag = 1;
                            }
                        }
                        if (flag == 1) {
                            suspectInfoList.add(suspectUnit);
                        }
                    }
                }
            }

            if (!"".equals(suEmail) && suEmail != null) {
                SuspectUnit suspectInfo3 = new SuspectUnit();
                suspectInfo3.setCustomsRegistrationNumber(suEmail);
                List<SuspectUnit> eviTemps3 = sqlDao.getListfromMysql(suspectInfo3);
                if (eviTemps3.size() > 0) {
                    suspectInfo3 = eviTemps3.get(0);
                    if (suspectInfoList.size() == 0) {
                        suspectInfoList.add(suspectInfo3);
                    } else {
                        int flag = 0;
                        for (SuspectUnit suspectInfo33 : suspectInfoList) {
                            if (suspectInfo33.getId() != suspectInfo3.getId()) {
                                flag = 1;
                            }
                        }
                        if (flag == 1) {
                            suspectInfoList.add(suspectInfo3);
                        }
                    }
                }
            }
        }

        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(JsonUtil.list2json(suspectInfoList));
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


    //数据列表临时跳转详情页面
    @RequestMapping(value = "/admin/goClueDetails.php")
    public String goClueDetails(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

//	  			String clueId = request.getParameter("oneClueId");
        String clueId = request.getParameter("id");

        User identify = (User) session.getAttribute("user");   //登录人职位
        String roless = identify.getPrivilege();

        Involve_Person involve_Person = new Involve_Person();
        involve_Person.setId(Integer.parseInt(clueId));
        Involve_Person involve_Personlist = sqlDao.getListfromMysql(involve_Person).get(0);

        String suspectId = involve_Personlist.getSuspectID();       //嫌疑人ID
        String[] suspectIdArray = suspectId.split(",");

        String suspectUnit = involve_Personlist.getSuspectUnit();    //涉嫌单位ID
        String[] suspectUnitArray = suspectUnit.split(",");

        String departMentsIDs = involve_Personlist.getUserPartment();   //所属分局
        Department department = new Department();
        department.setId(Integer.parseInt(departMentsIDs));
        Department department2 = sqlDao.getListfromMysql(department).get(0);
        String departMentsNames = department2.getDepartmentName();

        String suspectAddressID = involve_Personlist.getSuspectAddressID();
        String region = ""; // 作案区域信息
        /* YC: 获取作案区域信息*/
        if (suspectAddressID.length() != 0 && suspectAddressID != null) {
            String[] suspectAddressIDArray = suspectAddressID.split(",");
            for (int i = 0; i < suspectAddressIDArray.length; i++) {
                SuspectAddress suspectAddress = new SuspectAddress();
                suspectAddress.setId(Integer.parseInt(suspectAddressIDArray[i]));
                List<SuspectAddress> suspectAddressList = sqlDao.getListfromMysql(suspectAddress);
                if (i != (suspectAddressIDArray.length - 1)) {
                    region += suspectAddressList.get(0).getRegion() + ",";
                } else {
                    region += suspectAddressList.get(0).getRegion();
                }
            }
        }

        List<SuspectInfo> suspectInfoList1 = new ArrayList<SuspectInfo>();
        List<SuspectUnit> suspectUnit31 = new ArrayList<SuspectUnit>();
        if (suspectIdArray.length > 0 && suspectIdArray != null && !"".equals(suspectIdArray)) {
            for (String mainparit : suspectIdArray) {
                if (!"".equals(mainparit) && mainparit != null) {
                    //查询涉嫌人员
                    SuspectInfo suspectInfo = new SuspectInfo();
                    suspectInfo.setId(Integer.parseInt(mainparit));

                    List<SuspectInfo> suspectInfoList = sqlDao.getListfromMysql(suspectInfo);
                    suspectInfoList1.addAll(suspectInfoList);
                }
            }
        }

        if (suspectUnitArray.length > 0 && suspectUnitArray != null && !"".equals(suspectUnitArray)) {
            for (String mainparit1 : suspectUnitArray) {
                if (!"".equals(mainparit1) && mainparit1 != null) {
                    //查询涉嫌单位
                    SuspectUnit suspectUnit2 = new SuspectUnit();
                    suspectUnit2.setId(Integer.parseInt(mainparit1));

                    List<SuspectUnit> suspectUnit3 = sqlDao.getListfromMysql(suspectUnit2);
                    suspectUnit31.addAll(suspectUnit3);
                }
            }
        }

        map.put("roless", roless);            //登录人职位
        map.put("involve_Personlist", involve_Personlist);
        map.put("suspectInfoList", suspectInfoList1);
        map.put("suspectUnit3", suspectUnit31);
        map.put("departMentsNames", departMentsNames);
        map.put("region", region); // 作案区域信息
        return "clueDetails";
    }


    //线索处置和审批
    @RequestMapping(value = "/admin/clueHandleAndShenpi.php")
    public void getClueInfo(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                            HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        String clueId = request.getParameter("clueIds2");
//	  			String disposal = request.getParameter("paramHidden"); //线索处置

        String disposal = request.getParameter("chuzhi"); //线索处置

        String fenjuAndOther = request.getParameter("fenjuAndOther"); //分局   和  其他

        String departs21 = request.getParameter("departs21"); //勾选的分局名称
        String otherText = request.getParameter("otherText22"); //选择其他  输入的文本

        String ifAgrees = request.getParameter("ifAgrees"); //审批是否同意
        String opinionInfo = request.getParameter("shenpi"); //线索审批备注

        String filepath = request.getParameter("filepath"); //处理单路径
        String fileName22 = request.getParameter("fileName22"); //处理单截取出来的文件名


        Involve_Person involve_Person = new Involve_Person();
        involve_Person.setId(Integer.parseInt(clueId));
        List<Involve_Person> involve_Personlist = sqlDao.getListfromMysql(involve_Person);
        Involve_Person involve_Person1 = involve_Personlist.get(0);
        involve_Person1.setDisposal(disposal);
        involve_Person1.setSubstation(departs21);
        involve_Person1.setOtherText(otherText);
        involve_Person1.setAgreeStatus(ifAgrees);
        involve_Person1.setOpinionInfo(opinionInfo);
        involve_Person1.setFileUrl(filepath);
        involve_Person1.setFileName(fileName22);

        involve_Person1.setOther(fenjuAndOther);

        sqlDao.updateToMysql(involve_Person1);

        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write("{\"status\":\"success\"}");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    //文档挖掘    文档在线预览  与大搜索方法统一
    @RequestMapping(value = "/admin/lookOnlineOfClue.php")
    public void lookOnlineOfClue(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                 HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, Exception {

        String clueIDss = request.getParameter("clueIDss");
        String fileNamess22 = request.getParameter("fileNamess22");

        logger.info("**" + fileNamess22 + " clueIDss   ===" + clueIDss);

        String localpath = "/clueDetails/" + clueIDss + "/" + fileNamess22;
//			String localpath="D:/clueDetails/164/线索处置说明文档.doc";
        logger.info("线索处置localpath==" + localpath);
        //文件路径
        //先复制一份
        String newName = System.currentTimeMillis() + "";
        String targetPath = FilenameUtils.getFullPath(localpath) + newName + "." + FilenameUtils.getExtension(localpath);

        FileUtils.copyFile(new File(localpath), new File(targetPath));
        localpath = targetPath;
        logger.info(localpath + "线索处置文件是否存在：" + new File(localpath).exists());
        String extension = FilenameUtils.getExtension(localpath);
        String baseName = FilenameUtils.getBaseName(localpath);
        String previewPath = request.getSession().getServletContext().getRealPath("/preview");
        String sourceFileName = FilenameUtils.getName(localpath);
        String sourcePath = previewPath + "/" + sourceFileName;
        FileUtils.copyFile(new File(localpath), new File(sourcePath));
        // 输出路径
        String tartPath = previewPath + "/" + baseName + ".html";
        if ("pdf".equalsIgnoreCase(extension)) {
            // 转换pdf
            PdfToHtmlUtil.pdfToHtml(sourcePath, tartPath);
            baseName = baseName + ".html";
        } else if (extension.contains("xls") || extension.contains("XLS")) {
            //转换excel
            ExcelToHtmlUtil.excelToHtml(sourcePath, tartPath);

            baseName = baseName + ".html";
        } else if (extension.equalsIgnoreCase("docx") || extension.equalsIgnoreCase("doc")) {
            //转换word

            long time = System.currentTimeMillis();
            String htmlPath = request.getSession().getServletContext().getRealPath("/preview") + "/" + time + "/";
            if (!new File(htmlPath).exists()) {
                new File(htmlPath).mkdir();
            }
            if (extension.equalsIgnoreCase("docx")) {
                DocToHtml.convertDocxToHtml(sourcePath, htmlPath + time + ".html");
            } else {
                DocToHtml.convertDocToHtml(sourcePath, htmlPath + time + ".html", time + "");
            }
            logger.info("线索处置文件名称:" + htmlPath + time + ".html");
            baseName = time + "/" + time + ".html";

        } else if (extension.equalsIgnoreCase("ppt") || extension.equalsIgnoreCase("pptx")) {
            //转换ppt
            PPTtoHtmlUtil.pptToHtml(sourcePath, tartPath);
            baseName = baseName + ".html";
        }
        logger.info("线索处置文档预览路径：" + baseName);
        actionLog((String) session.getAttribute("userName"), "查看", "文档挖掘 在线预览");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            String result = "{\"res\":\"" + baseName + "\"}";
            writer.write(result);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    //线索列表 点情报立案关联案件
		/*@RequestMapping(value = "/admin/clue_case.php")
		public void clue_case(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException{
			response.setContentType("textml; charset=UTF-8");
			String caseId = request.getParameter("caseId");
			String id = request.getParameter("id");
			Involve_Person involve_person = new Involve_Person();
			involve_person.setId(Integer.parseInt(id));
			involve_person = sqlDao.getListfromMysql(involve_person).get(0);
			involve_person.setDisposal("情报已立案");
			if(caseId != null && !"".equals(caseId)){
				involve_person.setCaseID(caseId);
			}
		}*/

    // 线索列表上传文件到hdfs进度条
    @RequestMapping(value = "/admin/showHdfsProgressBarOfClue.php")
    public void showHdfsProgressBarOfClue(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        response.setContentType("textml; charset=UTF-8");

        String evUUID = (String) session.getAttribute("evUUID");// uuid
        int caseID = Integer.parseInt(request.getParameter("importClueIDs1"));// 案件id   换成线索ID
        
        // 2.构建HDFS上传路径文件夹名称
        String filePath = "/tmp2/evidenceClueList/" + caseID;//删除了evName
        // 查Linux上文件大小
        double fengzm = 0;
        File file = new File("/evidenceClueList/" + caseID + "/" + caseID + evUUID.substring(0, 10));// 传入文件路径
        if (file.exists()) {// 测试此文件是否存在
            // 如果是文件夹
            // 这里只检测了文件夹中第一层 如果有需要 可以继续递归检测
            if (file.isDirectory()) {
                int size = 0;
                for (File zf : file.listFiles()) {
                    if (zf.isDirectory())
                        continue;
                    size += zf.length();
                }
                fengzm = size;
            } else {
                // "+(file.length()/1024f)+"kb");
            }
            // 如果文件不存在
        } else {
            System.out.println("此文件不存在");
        }
        fengzm = fengzm / 1024f;// 作为分母

        // 执行Hadoop 查询上传数据大小命令
        String command = "hadoop fs -du -h " + filePath;
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String thisfilesize = br.readLine();
        String[] fengzis = thisfilesize.split("  ");
        String fengz = fengzis[0];
        String danwei = fengz.substring(fengz.length() - 1, fengz.length());
        fengz = fengz.substring(0, fengz.length() - 2);
        double fengzi = Double.parseDouble(fengz);
        if (danwei.equals("M")) {
            fengzi = fengzi * 1024;
        }
        if (danwei.equals("G")) {
            fengzi = fengzi * 1024 * 1024;
        }
        if (danwei.equals("T")) {
            fengzi = fengzi * 1024 * 1024 * 1024;
        }
        if (danwei.equals("P")) {
            fengzi = fengzi * 1024 * 1024 * 1024 * 1024;
        }

        // 计算百分比
        double baifenbi = fengzi / fengzm;
        DecimalFormat df = new DecimalFormat("######0.00");
        String jindu = df.format(baifenbi * 100);

        String result_data = "{\"baifenbi\":" + jindu + ",\"daxiao\":" + fengzi + "}";
        actionLog((String) session.getAttribute("userName"), "上传", "数据列表 上传数据");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result_data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    // 线索列表上传文件的日志展示
    @RequestMapping(value = "/admin/showAllHandledFile2OfClue.php")
    public void showAllHandledFile2OfClue(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        response.setContentType("textml; charset=UTF-8");

        int caseID = Integer.parseInt(request.getParameter("importClueIDs1Log"));// 案件id		换成线索ID
//			String evName = request.getParameter("evName");// 证据名称
        String evUUID = (String) session.getAttribute("evUUID");// uuid
        // int evIDInt = 0;
        Dealinfo dealinfo = new Dealinfo();
        // dealinfo.setEvID(caseID);
        // dealinfo.setId(-1);
        // List<Dealinfo> dealList = sqlDao.getListfromMysql(dealinfo);

        List<Dealinfo> dealList = new ArrayList<Dealinfo>();
			/*
			 * if (dealList.size() == 0) { dealinfo.setContent("正在初始化...，请稍等");
			 * dealList.add(dealinfo); }
			 */

        // 执行Hadoop 查询上传数据大小命令
        // String evName = "";
        // evName = (String) session.getAttribute("evName");
        String filePath = "/tmp2/evidenceClueList/" + caseID + "/" + caseID + evUUID.substring(0, 10);//删除了evName
        String command = "hadoop fs -du -h " + filePath;
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while ((line = br.readLine()) != null) {
            Dealinfo dealinfo2 = new Dealinfo();
            dealinfo2.setContent(line.split("/")[5] + "【成功上传到HDFS】");
            dealList.add(dealinfo2);
        }
        if (line == null) {
            dealinfo.setContent("正在初始化...，请稍等");
            dealList.add(dealinfo);
        }

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JsonUtil.list2json(dealList));
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
     * 线索重复_列表展示
     *
     * @author YC
     * @create 2017/11/21 13:28
     */
    @RequestMapping(value = "/list/repeat.php")
    public String showrepeat(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                             HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        
        int threadID = Integer.parseInt(request.getParameter("caseid"));// 案件id		换成线索ID
        System.out.println("拿到线索ID的值： " + threadID);

        ClueRepeatPlus clueRepeatPlusItem = new ClueRepeatPlus();
        sqlDao.deletefromMysql(clueRepeatPlusItem);

        Involve_Person involve_person = new Involve_Person();
        involve_person.setId(threadID);
        List<Involve_Person> listfromMysql = sqlDao.getListfromMysql(involve_person);

        /* YC: 先拿到线索重复的item值*/
        if (listfromMysql.size() > 0) {
            /* YC: 获取涉嫌人员的集合*/
            if (listfromMysql.get(0).getSuspectID().length() != 0 && listfromMysql.get(0).getSuspectID() != null) {
                String[] split = listfromMysql.get(0).getSuspectID().split(",");
                for (String s : split) {
                    System.out.println("获取涉嫌人员的各个ID： " + s);
                    Integer getSuspectID = Integer.parseInt(s);
                    SuspectInfo suspectInfo = new SuspectInfo();
                    suspectInfo.setId(getSuspectID);
                    List<SuspectInfo> listfromMysql1 = sqlDao.getListfromMysql(suspectInfo);
                    if (listfromMysql1.size() > 0) {
                        String suspectPhone = listfromMysql1.get(0).getSuspectPhone();
                        System.out.println("获取涉嫌人员的各个ID下的电话： " + suspectPhone);
                        String suspectMail = listfromMysql1.get(0).getSuspectMail();
                        System.out.println("获取涉嫌人员的各个ID下的邮箱： " + suspectMail);

                        if (suspectPhone.length() != 0 && suspectPhone != null) {
                            ClueRepeat clueRepeat = new ClueRepeat();
                            clueRepeat.setItem(suspectPhone);
                            clueRepeat.setType("3");
                            List<ClueRepeat> listfromMysql3 = sqlDao.getListfromMysql(clueRepeat);
                            System.out.println("Phone是否有匹配值： " + listfromMysql3.size());
                            for (ClueRepeat repeat : listfromMysql3) {
                                String id = repeat.getClueID();

                                Integer getClueID = new Integer(threadID);
                                if (!id.equals(getClueID.toString())) {
                                    System.out.println("Phone命中的ID值： " + id);
                                    String threadNum = "";
                                    String susItem = "";
                                    String reportPerson = "";
                                    String substation = "";
                                    String threadType = "";
                                    String suspectAddressID = "";
                                    String userPartment = "";
                                    String departmentName = "";
                                    /* YC: 获取线索的threadNum*/
                                    Involve_Person involve_person1 = new Involve_Person();
                                    involve_person1.setId(new Integer(repeat.getClueID()));
                                    if (sqlDao.getListfromMysql(involve_person1).size() > 0) {
                                        Involve_Person involve_person2 = sqlDao.getListfromMysql(involve_person1).get(0);
                                        threadNum = involve_person2.getThreadNum();
                                        susItem = involve_person2.getSusItem();
                                        reportPerson = involve_person2.getReportPerson();
                                        substation = involve_person2.getSubstation();
                                        threadType = involve_person2.getThreadType();
                                        suspectAddressID = involve_person2.getSuspectAddressID();

                                        userPartment = involve_person2.getUserPartment();
                                    }
                                    SuspectInfo suspectInfo1 = new SuspectInfo();
                                    suspectInfo1.setId(new Integer(repeat.getPrivateID()));
                                    List<SuspectInfo> getPersonInfoList = sqlDao.getListfromMysql(suspectInfo1);
                                    String suspectName = "";
                                    String suspectMail1 = "";
                                    if (getPersonInfoList.size() > 0) {
                                        suspectName = getPersonInfoList.get(0).getSuspectName();
                                        suspectMail1 = getPersonInfoList.get(0).getSuspectMail();
                                    }

                                    if (userPartment.length() != 0) {
                                        Department department = new Department();
                                        department.setId(new Integer(userPartment));
                                        List<Department> listfromMysql4 = sqlDao.getListfromMysql(department);

                                        if (listfromMysql4.size() > 0) {
                                            departmentName = listfromMysql4.get(0).getDepartmentName();
                                        }
                                    }

                                    String region = "";
                                    String[] split1 = suspectAddressID.split(",");
                                    for (String s1 : split1) {
                                        if (s1.length() != 0 && s1 != null) {
                                            SuspectAddress suspectAddress = new SuspectAddress();
                                            suspectAddress.setId(Integer.parseInt(s1));
                                            List<SuspectAddress> listfromMysql8 = sqlDao.getListfromMysql(suspectAddress);
                                            if (listfromMysql8.size() > 0) {
                                                region += listfromMysql8.get(0).getRegion();
                                            }
                                        }
                                    }

                                    ClueRepeatPlus clueRepeatPlusTemp = new ClueRepeatPlus();
                                    clueRepeatPlusTemp.setThreadNum(threadNum);
                                    List<ClueRepeatPlus> listfromMysql2 = sqlDao.getListfromMysql(clueRepeatPlusTemp);
                                    if (listfromMysql2.size() > 0) {
                                        String getThreadNumID = repeat.getPrivateID();
                                        ClueRepeatPlus clueRepeatPlusTemp1 = new ClueRepeatPlus();
                                        clueRepeatPlusTemp1.setType(getThreadNumID);
                                        List<ClueRepeatPlus> listfromMysql66 = sqlDao.getListfromMysql(clueRepeatPlusTemp1);
                                        if (listfromMysql66.size() > 0) {
                                            int id1 = listfromMysql66.get(0).getId();
                                            ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                            clueRepeatPlus.setId(id1);
                                            clueRepeatPlus.setSuspectPhone("<font color=red>" + repeat.getItem() + "</font>");
                                            sqlDao.updateToMysql(clueRepeatPlus);
                                        } else {
                                            ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                            clueRepeatPlus.setSuspectPhone("<font color=red>" + repeat.getItem() + "</font>");
                                            clueRepeatPlus.setSuspectMail(suspectMail1);
                                            clueRepeatPlus.setType(repeat.getPrivateID());
                                            clueRepeatPlus.setRegion(region);
                                            clueRepeatPlus.setReportPerson(reportPerson);
                                            clueRepeatPlus.setSubstation(departmentName);
                                            clueRepeatPlus.setSusItem(susItem);
                                            clueRepeatPlus.setThreadNum(threadNum);
                                            clueRepeatPlus.setThreadType(threadType);
                                            clueRepeatPlus.setSuspectName(suspectName);
                                            sqlDao.setBeanToMysql(clueRepeatPlus);
                                        }
                                    } else {
                                        ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                        clueRepeatPlus.setSuspectPhone("<font color=red>" + repeat.getItem() + "</font>");
                                        clueRepeatPlus.setSuspectMail(suspectMail1);
                                        clueRepeatPlus.setType(repeat.getPrivateID());
                                        clueRepeatPlus.setRegion(region);
                                        clueRepeatPlus.setReportPerson(reportPerson);
                                        clueRepeatPlus.setSubstation(departmentName);
                                        clueRepeatPlus.setSusItem(susItem);
                                        clueRepeatPlus.setThreadNum(threadNum);
                                        clueRepeatPlus.setThreadType(threadType);
                                        clueRepeatPlus.setSuspectName(suspectName);
                                        sqlDao.setBeanToMysql(clueRepeatPlus);
                                    }

                                }
                            }
                        }

                        if (suspectMail.length() != 0 && suspectMail != null) {
                            ClueRepeat clueRepeat1 = new ClueRepeat();
                            clueRepeat1.setItem(suspectMail);
                            clueRepeat1.setType("4");
                            List<ClueRepeat> listfromMysql4 = sqlDao.getListfromMysql(clueRepeat1);
                            System.out.println("Mail是否有匹配值： " + listfromMysql4.size());

                            for (ClueRepeat repeat : listfromMysql4) {
                                String id = repeat.getClueID();
                                Integer getClueID = new Integer(threadID);
                                if (!id.equals(getClueID.toString())) {
                                    System.out.println("Mail命中的ID值： " + id);
                                    String threadNum = "";
                                    String susItem = "";
                                    String reportPerson = "";
                                    String substation = "";
                                    String threadType = "";
                                    String suspectAddressID = "";
                                    String userPartment = "";
                                    String departmentName = "";
                                    /* YC: 获取线索的threadNum*/
                                    Involve_Person involve_person1 = new Involve_Person();
                                    involve_person1.setId(new Integer(repeat.getClueID()));
                                    if (sqlDao.getListfromMysql(involve_person1).size() > 0) {
                                        Involve_Person involve_person2 = sqlDao.getListfromMysql(involve_person1).get(0);
                                        threadNum = involve_person2.getThreadNum();
                                        susItem = involve_person2.getSusItem();
                                        reportPerson = involve_person2.getReportPerson();
                                        substation = involve_person2.getSubstation();
                                        threadType = involve_person2.getThreadType();
                                        suspectAddressID = involve_person2.getSuspectAddressID();

                                        userPartment = involve_person2.getUserPartment();
                                    }
                                    if (userPartment.length() != 0) {
                                        Department department = new Department();
                                        department.setId(new Integer(userPartment));
                                        List<Department> listfromMysql14 = sqlDao.getListfromMysql(department);

                                        if (listfromMysql14.size() > 0) {
                                            departmentName = listfromMysql14.get(0).getDepartmentName();
                                        }
                                    }

                                    String region = "";
                                    SuspectInfo suspectInfo1 = new SuspectInfo();
                                    suspectInfo1.setId(new Integer(repeat.getPrivateID()));
                                    List<SuspectInfo> getPersonInfoList = sqlDao.getListfromMysql(suspectInfo1);
                                    String suspectName = "";
                                    String suspectPhone1 = "";
                                    if (getPersonInfoList.size() > 0) {
                                        suspectName = getPersonInfoList.get(0).getSuspectName();
                                        suspectPhone1 = getPersonInfoList.get(0).getSuspectPhone();
                                    }
                                    String[] split1 = suspectAddressID.split(",");
                                    for (String s1 : split1) {
                                        if (s1.length() != 0 && s1 != null) {
                                            SuspectAddress suspectAddress = new SuspectAddress();
                                            suspectAddress.setId(Integer.parseInt(s1));
                                            List<SuspectAddress> listfromMysql8 = sqlDao.getListfromMysql(suspectAddress);
                                            if (listfromMysql8.size() > 0) {
                                                region += listfromMysql8.get(0).getRegion();
                                            }
                                        }
                                    }

                                    ClueRepeatPlus clueRepeatPlusTemp = new ClueRepeatPlus();
                                    clueRepeatPlusTemp.setThreadNum(threadNum);
                                    List<ClueRepeatPlus> listfromMysql2 = sqlDao.getListfromMysql(clueRepeatPlusTemp);
                                    if (listfromMysql2.size() > 0) {
                                        String getThreadNumID = repeat.getPrivateID();
                                        ClueRepeatPlus clueRepeatPlusTemp1 = new ClueRepeatPlus();
                                        clueRepeatPlusTemp1.setType(getThreadNumID);
                                        List<ClueRepeatPlus> listfromMysql66 = sqlDao.getListfromMysql(clueRepeatPlusTemp1);
                                        if (listfromMysql66.size() > 0) {
                                            int id1 = listfromMysql66.get(0).getId();
                                            ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                            clueRepeatPlus.setId(id1);
                                            clueRepeatPlus.setSuspectMail("<font color=red>" + repeat.getItem() + "</font>");
                                            sqlDao.updateToMysql(clueRepeatPlus);
                                        } else {
                                            ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                            clueRepeatPlus.setSuspectMail("<font color=red>" + repeat.getItem() + "</font>");
                                            clueRepeatPlus.setSuspectPhone(suspectPhone1);
                                            clueRepeatPlus.setType(repeat.getPrivateID());
                                            clueRepeatPlus.setThreadNum(threadNum);
                                            clueRepeatPlus.setRegion(region);
                                            clueRepeatPlus.setReportPerson(reportPerson);
                                            clueRepeatPlus.setSubstation(departmentName);
                                            clueRepeatPlus.setSusItem(susItem);
                                            clueRepeatPlus.setThreadNum(threadNum);
                                            clueRepeatPlus.setThreadType(threadType);
                                            clueRepeatPlus.setSuspectName(suspectName);
                                            sqlDao.setBeanToMysql(clueRepeatPlus);
                                        }
                                    } else {
                                        ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                        clueRepeatPlus.setSuspectMail("<font color=red>" + repeat.getItem() + "</font>");
                                        clueRepeatPlus.setSuspectPhone(suspectPhone1);
                                        clueRepeatPlus.setType(repeat.getPrivateID());
                                        clueRepeatPlus.setThreadNum(threadNum);
                                        clueRepeatPlus.setRegion(region);
                                        clueRepeatPlus.setReportPerson(reportPerson);
                                        clueRepeatPlus.setSubstation(departmentName);
                                        clueRepeatPlus.setSusItem(susItem);
                                        clueRepeatPlus.setThreadNum(threadNum);
                                        clueRepeatPlus.setThreadType(threadType);
                                        clueRepeatPlus.setSuspectName(suspectName);
                                        sqlDao.setBeanToMysql(clueRepeatPlus);
                                    }
                                }

                            }

                        }


                    }
                }

            }
        }

        Involve_Person involve_person11 = new Involve_Person();
        involve_person11.setId(threadID);
        List<Involve_Person> listfromMysql11 = sqlDao.getListfromMysql(involve_person11);

        /* YC: 先拿到线索重复的item值*/
        if (listfromMysql11.size() > 0) {
            /* YC: 获取涉嫌单位的集合*/
            if (listfromMysql11.get(0).getSuspectUnit().length() != 0 && listfromMysql11.get(0).getSuspectUnit() != null) {
                String[] split = listfromMysql11.get(0).getSuspectUnit().split(",");
                for (String s : split) {
                    Integer getSuspectID = Integer.parseInt(s);
                    SuspectUnit suspectInfo = new SuspectUnit();
                    suspectInfo.setId(getSuspectID);
                    List<SuspectUnit> listfromMysql1 = sqlDao.getListfromMysql(suspectInfo);
                    if (listfromMysql1.size() > 0) {
                        String suspectPhone = listfromMysql1.get(0).getName();
                        String suspectMail = listfromMysql1.get(0).getCustomsRegistrationNumber();

                        if (suspectPhone.length() != 0 && suspectPhone != null) {
                            ClueRepeat clueRepeat = new ClueRepeat();
                            clueRepeat.setItem(suspectPhone);
                            clueRepeat.setType("1");
                            List<ClueRepeat> listfromMysql3 = sqlDao.getListfromMysql(clueRepeat);
                            for (ClueRepeat repeat : listfromMysql3) {
                                String id = repeat.getClueID();
                                Integer getClueID = new Integer(threadID);
                                if (!id.equals(getClueID.toString())) {
                                    String threadNum = "";
                                    String susItem = "";
                                    String reportPerson = "";
                                    String substation = "";
                                    String threadType = "";
                                    String suspectAddressID = "";
                                    String userPartment = "";
                                    String departmentName = "";
                                    /* YC: 获取线索的threadNum*/

                                    Involve_Person involve_person1 = new Involve_Person();
                                    involve_person1.setId(new Integer(repeat.getClueID()));
                                    if (sqlDao.getListfromMysql(involve_person1).size() > 0) {
                                        Involve_Person involve_person2 = sqlDao.getListfromMysql(involve_person1).get(0);
                                        threadNum = involve_person2.getThreadNum();
                                        susItem = involve_person2.getSusItem();
                                        reportPerson = involve_person2.getReportPerson();
                                        substation = involve_person2.getSubstation();
                                        threadType = involve_person2.getThreadType();
                                        suspectAddressID = involve_person2.getSuspectAddressID();
                                        userPartment = involve_person2.getUserPartment();
                                    }
                                    SuspectUnit suspectInfo1 = new SuspectUnit();
                                    suspectInfo1.setId(new Integer(repeat.getPrivateID()));
                                    List<SuspectUnit> getUnitInfoList = sqlDao.getListfromMysql(suspectInfo1);
                                    String unitAddress = "";
                                    String unitNum = "";
                                    if (getUnitInfoList.size() > 0) {
                                        unitAddress = getUnitInfoList.get(0).getAddress();
                                        unitNum = getUnitInfoList.get(0).getCustomsRegistrationNumber();
                                    }

                                    if (userPartment.length() != 0) {
                                        Department department = new Department();
                                        department.setId(new Integer(userPartment));
                                        List<Department> listfromMysql4 = sqlDao.getListfromMysql(department);

                                        if (listfromMysql4.size() > 0) {
                                            departmentName = listfromMysql4.get(0).getDepartmentName();
                                        }
                                    }
                                    String region = "";
                                    String[] split1 = suspectAddressID.split(",");
                                    for (String s1 : split1) {
                                        if (s1.length() != 0 && s1 != null) {
                                            SuspectAddress suspectAddress = new SuspectAddress();
                                            suspectAddress.setId(Integer.parseInt(s1));
                                            List<SuspectAddress> listfromMysql8 = sqlDao.getListfromMysql(suspectAddress);
                                            if (listfromMysql8.size() > 0) {
                                                region += listfromMysql8.get(0).getRegion();
                                            }
                                        }
                                    }

                                    ClueRepeatPlus clueRepeatPlusTemp = new ClueRepeatPlus();
                                    clueRepeatPlusTemp.setThreadNum(threadNum);
                                    List<ClueRepeatPlus> listfromMysql2 = sqlDao.getListfromMysql(clueRepeatPlusTemp);
                                    if (listfromMysql2.size() > 0) {
                                        String getThreadNumID = repeat.getPrivateID();
                                        ClueRepeatPlus clueRepeatPlusTemp1 = new ClueRepeatPlus();
                                        clueRepeatPlusTemp1.setType(getThreadNumID);
                                        List<ClueRepeatPlus> listfromMysql66 = sqlDao.getListfromMysql(clueRepeatPlusTemp1);
                                        if (listfromMysql66.size() > 0) {
                                            int id1 = listfromMysql66.get(0).getId();
                                            ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                            clueRepeatPlus.setId(id1);
                                            clueRepeatPlus.setUnitCustomsRegistrationNumber("<font color=red>" + repeat.getItem() + "</font>");
                                            sqlDao.updateToMysql(clueRepeatPlus);
                                        }  else {
                                            ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                            clueRepeatPlus.setUnitName("<font color=red>" + repeat.getItem() + "</font>");
                                            clueRepeatPlus.setUnitCustomsRegistrationNumber(unitNum);
                                            clueRepeatPlus.setType(repeat.getPrivateID());
                                            clueRepeatPlus.setRegion(region);
                                            clueRepeatPlus.setReportPerson(reportPerson);
                                            clueRepeatPlus.setSubstation(departmentName);
                                            clueRepeatPlus.setSusItem(susItem);
                                            clueRepeatPlus.setThreadNum(threadNum);
                                            clueRepeatPlus.setThreadType(threadType);
                                            clueRepeatPlus.setUnitAddress(unitAddress);
                                            sqlDao.setBeanToMysql(clueRepeatPlus);
                                        }
                                    } else {
                                        ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                        clueRepeatPlus.setUnitName("<font color=red>" + repeat.getItem() + "</font>");
                                        clueRepeatPlus.setUnitCustomsRegistrationNumber(unitNum);
                                        clueRepeatPlus.setType(repeat.getPrivateID());
                                        clueRepeatPlus.setRegion(region);
                                        clueRepeatPlus.setReportPerson(reportPerson);
                                        clueRepeatPlus.setSubstation(departmentName);
                                        clueRepeatPlus.setSusItem(susItem);
                                        clueRepeatPlus.setThreadNum(threadNum);
                                        clueRepeatPlus.setThreadType(threadType);
                                        clueRepeatPlus.setUnitAddress(unitAddress);
                                        sqlDao.setBeanToMysql(clueRepeatPlus);
                                    }
                                }
                            }
                        }

                        if (suspectMail.length() != 0 && suspectMail != null) {
                            ClueRepeat clueRepeat1 = new ClueRepeat();
                            clueRepeat1.setItem(suspectMail);
                            clueRepeat1.setType("2");
                            List<ClueRepeat> listfromMysql4 = sqlDao.getListfromMysql(clueRepeat1);

                            for (ClueRepeat repeat : listfromMysql4) {
                                String id = repeat.getClueID();
                                Integer getClueID = new Integer(threadID);
                                if (!id.equals(getClueID.toString())) {
                                    String threadNum = "";
                                    String susItem = "";
                                    String reportPerson = "";
                                    String substation = "";
                                    String threadType = "";
                                    String suspectAddressID = "";
                                    String userPartment = "";
                                    String departmentName = "";
                                    /* YC: 获取线索的threadNum*/
                                    Involve_Person involve_person1 = new Involve_Person();
                                    involve_person1.setId(new Integer(repeat.getClueID()));
                                    if (sqlDao.getListfromMysql(involve_person1).size() > 0) {
                                        Involve_Person involve_person2 = sqlDao.getListfromMysql(involve_person1).get(0);
                                        threadNum = involve_person2.getThreadNum();
                                        susItem = involve_person2.getSusItem();
                                        reportPerson = involve_person2.getReportPerson();
                                        substation = involve_person2.getSubstation();
                                        threadType = involve_person2.getThreadType();
                                        suspectAddressID = involve_person2.getSuspectAddressID();
                                        userPartment = involve_person2.getUserPartment();
                                    }

                                    if (userPartment.length() != 0) {
                                        Department department = new Department();
                                        department.setId(new Integer(userPartment));
                                        List<Department> listfromMysql14 = sqlDao.getListfromMysql(department);
                                        if (listfromMysql14.size() > 0) {
                                            departmentName = listfromMysql14.get(0).getDepartmentName();
                                        }
                                    }

                                    String region = "";
                                    SuspectUnit suspectInfo1 = new SuspectUnit();
                                    suspectInfo1.setId(new Integer(repeat.getPrivateID()));
                                    List<SuspectUnit> getUnitInfoList = sqlDao.getListfromMysql(suspectInfo1);
                                    String unitAddress = "";
                                    String unitName = "";
                                    if (getUnitInfoList.size() > 0) {
                                        unitAddress = getUnitInfoList.get(0).getAddress();
                                        unitName = getUnitInfoList.get(0).getName();
                                    }
                                    String[] split1 = suspectAddressID.split(",");
                                    for (String s1 : split1) {
                                        if (s1.length() != 0 && s1 != null) {
                                            SuspectAddress suspectAddress = new SuspectAddress();
                                            suspectAddress.setId(Integer.parseInt(s1));
                                            List<SuspectAddress> listfromMysql8 = sqlDao.getListfromMysql(suspectAddress);
                                            if (listfromMysql8.size() > 0) {
                                                region += listfromMysql8.get(0).getRegion();
                                            }
                                        }
                                    }

                                    ClueRepeatPlus clueRepeatPlusTemp = new ClueRepeatPlus();
                                    clueRepeatPlusTemp.setThreadNum(threadNum);
                                    List<ClueRepeatPlus> listfromMysql2 = sqlDao.getListfromMysql(clueRepeatPlusTemp);

                                    //判断线索是否存在
                                    if (listfromMysql2.size() > 0) {
                                        String getThreadNumID = repeat.getPrivateID();
                                        ClueRepeatPlus clueRepeatPlusTemp1 = new ClueRepeatPlus();
                                        clueRepeatPlusTemp1.setType(getThreadNumID);
                                        List<ClueRepeatPlus> listfromMysql66 = sqlDao.getListfromMysql(clueRepeatPlusTemp1);
                                        if (listfromMysql66.size() > 0) {
                                            int id1 = listfromMysql66.get(0).getId();
                                            ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                            clueRepeatPlus.setId(id1);
                                            clueRepeatPlus.setUnitCustomsRegistrationNumber("<font color=red>" + repeat.getItem() + "</font>");
                                            sqlDao.updateToMysql(clueRepeatPlus);
                                        } else {
                                            ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                            clueRepeatPlus.setUnitCustomsRegistrationNumber("<font color=red>" + repeat.getItem() + "</font>");
                                            clueRepeatPlus.setUnitName(unitName);
                                            clueRepeatPlus.setType(repeat.getPrivateID());
                                            clueRepeatPlus.setThreadNum(threadNum);
                                            clueRepeatPlus.setRegion(region);
                                            clueRepeatPlus.setReportPerson(reportPerson);
                                            clueRepeatPlus.setSubstation(departmentName);
                                            clueRepeatPlus.setSusItem(susItem);
                                            clueRepeatPlus.setThreadNum(threadNum);
                                            clueRepeatPlus.setThreadType(threadType);
                                            clueRepeatPlus.setUnitAddress(unitAddress);
                                            sqlDao.setBeanToMysql(clueRepeatPlus);
                                        }
                                    } else {

                                        ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
                                        clueRepeatPlus.setUnitCustomsRegistrationNumber("<font color=red>" + repeat.getItem() + "</font>");
                                        clueRepeatPlus.setUnitName(unitName);
                                        clueRepeatPlus.setType(repeat.getPrivateID());
                                        clueRepeatPlus.setThreadNum(threadNum);
                                        clueRepeatPlus.setRegion(region);
                                        clueRepeatPlus.setReportPerson(reportPerson);
                                        clueRepeatPlus.setSubstation(departmentName);
                                        clueRepeatPlus.setSusItem(susItem);
                                        clueRepeatPlus.setThreadNum(threadNum);
                                        clueRepeatPlus.setThreadType(threadType);
                                        clueRepeatPlus.setUnitAddress(unitAddress);
                                        sqlDao.setBeanToMysql(clueRepeatPlus);
                                    }
                                }

                            }

                        }



                    }
                }

            }
        }

        map.put("threadID", threadID);
        return "theardRepeat";
    }

    /**
     * 线索重复
     *
     * @author YC
     * @create 2017/11/20 14:44
     */

    @RequestMapping("/list/Involve_Person_repeat.php")
    public void Involve_Person_repeat(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
                                      HttpSession session) {

        String pageno = request.getParameter("pageno");
        int pageIndex = 1;//当前页数
        int pageSize = 10;//每页个数
        int num = 0;//总页数
        if (!StringUtils.isEmpty(pageno)) {
            pageIndex = Integer.parseInt(pageno);
        }

        ClueRepeatPlus clueRepeatPlus = new ClueRepeatPlus();
        List<ClueRepeatPlus> list1 = sqlDao.getListfromMysql(clueRepeatPlus, (pageIndex - 1) * pageSize, pageSize);
        int total = sqlDao.getcountfromMysql(clueRepeatPlus);
        num = total / pageSize;
        if (total % pageSize != 0) {
            num++;
        }

        total = sqlDao.getcountfromMysql(clueRepeatPlus);
        JSONArray jsonArray = JSONArray.fromObject(list1);
        String json_str = jsonArray.toString();
        String result_data = "{\"totalNum\":\"" + total + "\",\"totalPages\":\"" + num + "\",\"nowPage\":\""
                + pageIndex + "\",\"resData\":" + json_str + "}";
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(result_data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        map.put("list", list1);//返回所有的线索信息
        map.put("totalNum", total);
        map.put("totalPages", num);
        map.put("nowPage", pageIndex);
    }

    /**
     * 线索重复值的计数
     * @author YC
     * @create 2017/11/24 14:23
     */
    @RequestMapping("/list/repeatConut.php")
    public void getRepeatConut(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
                                      HttpSession session) {

        class Runs implements Runnable {
            @Override
            public void run() {
        /* YC: 统计列表中命中次数*/
                Involve_Person involve_person = new Involve_Person();
                List<Involve_Person> listfromMysql3 = sqlDao.getListfromMysql(involve_person);
                for (Involve_Person involvePerson : listfromMysql3) {
                    int id = involvePerson.getId();
                    Involve_Person involve_person1 = new Involve_Person();
                    involve_person1.setId(id);
                    List<Involve_Person> listfromMysql = sqlDao.getListfromMysql(involve_person1);
                    if (listfromMysql.size() > 0) {
                        String getSuspectUnit = listfromMysql.get(0).getSuspectUnit();
                        String getSuspectID = listfromMysql.get(0).getSuspectID();
                        String[] split = getSuspectUnit.split(",");

                        String[] split1 = getSuspectID.split(",");

                        int getUnitNum = 0;
                        int getPersonNum = 0;
                        if (split.length != 0) {
                            for (String s : split) {
                                if (s.length() != 0) {
                                    Integer integerID = new Integer(id);
                                    SuspectUnit suspectUnit = new SuspectUnit();
                                    suspectUnit.setId(Integer.parseInt(s));
                                    List<SuspectUnit> listfromMysql1 = sqlDao.getListfromMysql(suspectUnit);
                                    for (SuspectUnit unit : listfromMysql1) {
                                        String name1 = unit.getName();
                                        String customsRegistrationNumber = unit.getCustomsRegistrationNumber();
                                        System.out.println("单位统计#######");
                                        System.out.println(name1);
                                        System.out.println(customsRegistrationNumber);
                                        if (name1.length() != 0 && name1 != null) {
                                            ClueRepeat clueRepeat = new ClueRepeat();
                                            clueRepeat.setType("1");
                                            clueRepeat.setItem(name1);
                                            int i = sqlDao.getcountfromMysql(clueRepeat);
                                            getUnitNum += i;

                                            ClueRepeat clueRepeat1 = new ClueRepeat();
                                            clueRepeat1.setClueID(integerID.toString());
                                            clueRepeat1.setItem(name1);
                                            clueRepeat1.setType("1");
                                            int i1 = sqlDao.getcountfromMysql(clueRepeat1);
                                            getUnitNum -= i1;
                                        }

                                        if (customsRegistrationNumber.length() != 0 && customsRegistrationNumber != null) {
                                            ClueRepeat clueRepeat2 = new ClueRepeat();
                                            clueRepeat2.setItem(customsRegistrationNumber);
                                            clueRepeat2.setType("2");
                                            int i2 = sqlDao.getcountfromMysql(clueRepeat2);
                                            getUnitNum += i2;

                                            ClueRepeat clueRepeat3 = new ClueRepeat();
                                            clueRepeat3.setType("2");
                                            clueRepeat3.setItem(customsRegistrationNumber);
                                            clueRepeat3.setClueID(integerID.toString());
                                            int i3 = sqlDao.getcountfromMysql(clueRepeat3);
                                            getUnitNum -= i3;
                                        }

                                    }
                                }
                            }
                        }

                        if (split1.length != 0) {
                            for (String s : split1) {
                                if (s.length() != 0) {
                                    Integer integerID = new Integer(id);
                                    SuspectInfo suspectInfo = new SuspectInfo();
                                    suspectInfo.setId(Integer.parseInt(s));
                                    List<SuspectInfo> listfromMysql2 = sqlDao.getListfromMysql(suspectInfo);
                                    for (SuspectInfo info : listfromMysql2) {
                                        String suspectPhone = info.getSuspectPhone();
                                        String suspectMail = info.getSuspectMail();
                                        System.out.println("人员统计#######");
                                        System.out.println(suspectPhone);
                                        System.out.println(suspectMail);
                                        if (suspectPhone.length() != 0 && suspectPhone != null) {
                                            ClueRepeat clueRepeat = new ClueRepeat();
                                            clueRepeat.setItem(suspectPhone);
                                            clueRepeat.setType("3");
                                            int i = sqlDao.getcountfromMysql(clueRepeat);
                                            getPersonNum += i;

                                            ClueRepeat clueRepeat2 = new ClueRepeat();
                                            clueRepeat2.setItem(suspectPhone);
                                            clueRepeat2.setType("3");
                                            clueRepeat2.setClueID(integerID.toString());
                                            int i2 = sqlDao.getcountfromMysql(clueRepeat2);
                                            getPersonNum -= i2;

                                        }

                                        if (suspectMail.length() != 0 && suspectMail != null) {
                                            ClueRepeat clueRepeat1 = new ClueRepeat();
                                            clueRepeat1.setItem(suspectMail);
                                            clueRepeat1.setType("4");
                                            int i1 = sqlDao.getcountfromMysql(clueRepeat1);
                                            getPersonNum += i1;

                                            ClueRepeat clueRepeat3 = new ClueRepeat();
                                            clueRepeat3.setItem(suspectMail);
                                            clueRepeat3.setType("4");
                                            clueRepeat3.setClueID(integerID.toString());
                                            int i3 = sqlDao.getcountfromMysql(clueRepeat3);
                                            getPersonNum -= i3;
                                        }

                                    }
                                }
                            }
                        }

                        Integer getCountNum = getUnitNum + getPersonNum;
                        Involve_Person involve_person11 = new Involve_Person();
                        involve_person11.setId(id);
                        List<Involve_Person> listfromMysql1 = sqlDao.getListfromMysql(involve_person11);
                        if (listfromMysql1.size() > 0) {
                            Involve_Person involve_person12 = listfromMysql1.get(0);
                            involve_person12.setCrossNum(getCountNum.toString());
                            sqlDao.updateToMysql(involve_person12);
                        }
                    }
                }

            }
        }
        Runs runs = new Runs();
        Thread unzipThread2 = new Thread(runs);
        unzipThread2.start();
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write("OK!");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 检测线索登记_涉嫌人员_是否存在
     *
     * @author YC
     * @create 2017/11/24 14:48
     */
    @RequestMapping(value = "/checkThreadPerson.php")
    public void checkThreadPerson(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        response.setContentType("text/html;charset=utf-8");
        String SuspectPhone = request.getParameter("SuspectPhone");

        String suPhone = request.getParameter("suPhone");
        String suEmail = request.getParameter("suEmail");


        List<SuspectInfo> suspectInfoList = new ArrayList<SuspectInfo>();

        if (SuspectPhone != null && !"".equals(SuspectPhone)) {
			/*String[] split = SuspectPhone.split(" ");
			String name = split[0];
			String phone = split[1];
			String mail = split[2];*/

            if (!"".equals(suPhone) && suPhone != null) {
                SuspectInfo suspectInfo2 = new SuspectInfo();
                suspectInfo2.setSuspectPhone(suPhone);
                List<SuspectInfo> eviTemps2 = sqlDao.getListfromMysql(suspectInfo2);
                if (eviTemps2.size() > 0) {
                    suspectInfo2 = eviTemps2.get(0);
                    if (suspectInfoList.size() == 0) {
                        suspectInfoList.add(suspectInfo2);
                    } else {
                        int flag = 0;
                        for (SuspectInfo suspectInfo22 : suspectInfoList) {
                            if (suspectInfo22.getId() != suspectInfo2.getId()) {
                                flag = 1;
                            }
                        }
                        if (flag == 1) {
                            suspectInfoList.add(suspectInfo2);
                        }
                    }
                }
            }

            if (!"".equals(suEmail) && suEmail !=null) {
                SuspectInfo suspectInfo3 = new SuspectInfo();
                suspectInfo3.setSuspectMail(suEmail);
                List<SuspectInfo> eviTemps3 = sqlDao.getListfromMysql(suspectInfo3);
                if (eviTemps3.size() > 0) {
                    suspectInfo3 = eviTemps3.get(0);
                    if (suspectInfoList.size() == 0) {
                        suspectInfoList.add(suspectInfo3);
                    } else {
                        int flag = 0;
                        for (SuspectInfo suspectInfo33 : suspectInfoList) {
                            if (suspectInfo33.getId() != suspectInfo3.getId()) {
                                flag = 1;
                            }
                        }
                        if (flag == 1) {
                            suspectInfoList.add(suspectInfo3);
                        }
                    }
                }
            }

        }


        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(JsonUtil.list2json(suspectInfoList));
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


}