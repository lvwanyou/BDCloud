package com.xl.cloud.action;

import com.csvreader.CsvReader;
//import com.dao.SqlDao;
import com.xl.cloud.bean.Evidence;
import com.xl.cloud.bean.User;
import com.xl.cloud.bean.CallListBean;
import com.xl.cloud.dao.SqlDao;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainAction {
    SqlDao sqlDao = new SqlDao();
    List<List<Object>> listob2 = new ArrayList<List<Object>>();
    Integer ticketUploadNum = 0;

    /**
     * app首页设置
     *
     * @param request
     * @param map
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "/huadan.php")
    public String appindexSetting(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                                  HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        return "huadan";
    }

    /**
     * 上传
     *
     * @param request
     * @param map
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/uploadNew.php")
    public String uploadNew(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                            HttpServletResponse response) throws Exception {
        System.out.println("### 进人了方法uploadNew ###");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        System.out.println("通过传统方式form表单提交方式导入excel文件！");
        String res = "";

        String caseidUser = request.getParameter("caseidUser");
        String evTypeUser = request.getParameter("evTypeUser");
        String evNameUser = request.getParameter("evNameUser");
        String commentUser = request.getParameter("commentUser");
        String dataTypesUser = request.getParameter("dataTypesUser");
        String fangshiUser = request.getParameter("fangshiUser");
//		int row = Integer.parseInt(request.getParameter("row"));
//		int col = Integer.parseInt(request.getParameter("col"));

//		System.out.println(row);
//		System.out.println(col);
        InputStream in = null;
        List<List<Object>> listob = new ArrayList<List<Object>>();
        List<List<Object>> listob1 = new ArrayList<List<Object>>();

        listob2 = new ArrayList<List<Object>>();

        MultipartFile file = multipartRequest.getFile("file7");
        if (file.isEmpty()) {
            throw new Exception("文件不存在！");
        }
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        System.out.println(fileType);
        in = file.getInputStream();

        int cells = 0;
        List<Object> lObjects = new ArrayList<Object>();
        String inString = "";
        String tmpString = "";
        List<List<Object>> lines = new ArrayList<List<Object>>();
        if (".csv".equals(fileType) || ".CSV".equals(fileType)) {
            CommonsMultipartFile cf = (CommonsMultipartFile) file;
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();

            File f = fi.getStoreLocation();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "GBK"));
                CsvReader creader = new CsvReader(reader, ',');
                while (creader.readRecord()) {
                    inString = creader.getRawRecord();//读取一行数据
// 	               System.out.println(inString);
                    String string = inString.replaceAll(" ", "");
// 	               System.out.println(string);
                    List<Object> lObjects2 = new ArrayList<Object>();
                    String[] strings = string.split(",");
                    for (int i = 0; i < strings.length; i++) {
                        lObjects2.add(strings[i]);
                    }

                    listob.add(lObjects2);
                }
                creader.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            listob2 = listob;
            lObjects = listob.get(0);
            int length = 20;
            if (listob.size() < 20) {
                length = listob.size();
            }
            for (int i = 0; i < length; i++) {
                List<Object> lo = listob.get(i);
// 				System.out.println(lo.get(col));

                cells = lo.size();
                listob1.add(lo);
            }

        } else if(".xlsx".equals(fileType) || ".XLSX".equals(fileType) || ".xls".equals(fileType) || ".XLS".equals(fileType)) {
            int num = 0;
            listob = ImportExcleUtil.getBankListByExcel(in, file.getOriginalFilename());
            listob2 = listob;
            session.setAttribute("listlo", listob);
            lObjects = listob.get(0);
            //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
            int length = 20;
            if (listob.size() < 20) {
                length = listob.size();
            }
            for (int i = 0; i < length; i++) {
                List<Object> lo = listob.get(i);
// 				System.out.println(lo.get(col));
                cells = lo.size();
                //listob1.add(lo);
                if (lo.size() > num) {
                    num = lo.size();
                }
            }
            for (int i = 0; i < length; i++) {
                List<Object> lo = listob.get(i);
// 				System.out.println(lo.get(col));
                cells = lo.size();
                if (lo.size() < num) {
                    for (int k = 0; k < num - cells; k++) {
                        lo.add("");
                    }
                }
                listob1.add(lo);
            }
        }


        res = "{\"status\":\"succ\",\"data\":" + listob + "}";
        map.put("list", listob);
        request.setAttribute("list", listob);
        map.put("list1", listob1);
        map.put("cells", cells);
        map.put("lObjects", lObjects);

        map.put("caseID", caseidUser);
        map.put("evTypeUser", evTypeUser);
        map.put("evNameUser", evNameUser);
        map.put("commentUser", commentUser);
        map.put("dataTypesUser", dataTypesUser);
        map.put("fangshiUser", fangshiUser);

        return "huadan2";
    }

    /**
     * 上传
     *
     * @param request
     * @param map
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/upload1New.php")
    public String upload1New(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                             HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        System.out.println("通过传统方式form表单提交方式导入excel文件夹！");
        String res = "";
        String caseidUser = request.getParameter("caseidUser");
        String evTypeUser = request.getParameter("evTypeUser");
        String evNameUser = request.getParameter("evNameUser");
        String commentUser = request.getParameter("commentUser");
        String dataTypesUser = request.getParameter("dataTypesUser");
        String fangshiUser = request.getParameter("fangshiUser");

//		int row = Integer.parseInt(request.getParameter("row"));
//		int col = Integer.parseInt(request.getParameter("col"));

//		System.out.println(row);
//		System.out.println(col);
        InputStream in = null;
        List<List<Object>> listob = new ArrayList<List<Object>>();
        List<List<Object>> listob1 = new ArrayList<List<Object>>();
        listob2 = new ArrayList<List<Object>>();
        int cells = 0;
        List<Object> lObjects = new ArrayList<Object>();
        String inString = "";
        String tmpString = "";
        List<List<Object>> lines = new ArrayList<List<Object>>();
        int k = 0;
//		Iterator iter = multipartRequest.getFileMap().values().iterator();
        List<MultipartFile> lFiles = multipartRequest.getFiles("file8");
        System.out.println("获取文件数： " + lFiles.size());
        ticketUploadNum = lFiles.size();
//		while (iter.hasNext()) {
        for (int j = 0; j < lFiles.size(); j++) {
            MultipartFile file = lFiles.get(j);//multipartRequest.getFile("upfile");
            if (file.isEmpty()) {
                throw new Exception("文件不存在！");
            }
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            System.out.println(fileType);
            in = file.getInputStream();


            if (".csv".equals(fileType) || ".CSV".equals(fileType)) {

                CommonsMultipartFile cf = (CommonsMultipartFile) file;
                DiskFileItem fi = (DiskFileItem) cf.getFileItem();

                File f = fi.getStoreLocation();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "GBK"));
                    CsvReader creader = new CsvReader(reader, ',');
                    while (creader.readRecord()) {
                        inString = creader.getRawRecord();//读取一行数据
//	 	               System.out.println(inString);
                        String string = inString.replaceAll(" ", "");
//	 	               System.out.println(string);
                        List<Object> lObjects2 = new ArrayList<Object>();
                        String[] strings = string.split(",");
                        for (int i = 0; i < strings.length; i++) {
                            lObjects2.add(strings[i]);
                        }

                        listob.add(lObjects2);
                    }
                    creader.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                for (int i = 0; i < listob.size(); i++) {
                    listob2.add(listob.get(i));
                }
                lObjects = listob.get(0);
                if (k == 0) {
                    int length = 20;
                    if (listob.size() < 20) {
                        length = listob.size();
                    }
                    for (int i = 0; i < length; i++) {
                        List<Object> lo = listob.get(i);
//		 				System.out.println(lo.get(col));

                        cells = lo.size();
                        listob1.add(lo);
                    }
                }
                k++;
            } else if(".xlsx".equals(fileType) || ".XLSX".equals(fileType) || ".xls".equals(fileType) || ".XLS".equals(fileType)) {
                listob = ImportExcleUtil.getBankListByExcel(in, file.getOriginalFilename());
                System.out.println(listob.size());
                for (int i = 0; i < listob.size(); i++) {
                    listob2.add(listob.get(i));
                }

                session.setAttribute("listlo", listob);
                lObjects = listob.get(0);
                if (k == 0) {
                    //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
                    int length = 20;
                    if (listob.size() < 20) {
                        length = listob.size();
                    }
                    for (int i = 0; i < length; i++) {
                        List<Object> lo = listob.get(i);
//		 				System.out.println(lo.get(col));

                        cells = lo.size();
                        listob1.add(lo);
                    }
                }
                k++;
            }
        }

        System.out.println(listob2.size());

        res = "{\"status\":\"succ\",\"data\":" + listob + "}";
        map.put("list", listob);
        //request.setAttribute("list", listob);
        map.put("list1", listob1);
        map.put("cells", cells);
        map.put("lObjects", lObjects);
        map.put("caseID", caseidUser);
        map.put("evTypeUser", evTypeUser);
        map.put("evNameUser", evNameUser);
        map.put("commentUser", commentUser);
        map.put("dataTypesUser", dataTypesUser);
        map.put("fangshiUser", fangshiUser);

        return "huadan2";
    }


    /**
     * 上传
     *
     * @param request
     * @param map
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/importExcel.php")
    public void importExcel(HttpServletRequest request, Map<String, Object> map, HttpSession session,
                            HttpServletResponse response) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        response.setContentType("text/html;charset=utf-8");
        final String userName = (String) session.getAttribute("userName");
        String section = (String) session.getAttribute("section");
        User user = (User) session.getAttribute("user");
        final String addcaseid = request.getParameter("caseID");
        String dataTypes = request.getParameter("dataTypes");
        String filelength = request.getParameter("filelength");
        if ("移动设备".equals(dataTypes)) {
            dataTypes = "1";
        } else if ("通信运营".equals(dataTypes)) {
            dataTypes = "2";
        } else if ("社交网站".equals(dataTypes)) {
            dataTypes = "3";
        } else if ("音频视频".equals(dataTypes)) {
            dataTypes = "4";
        } else if ("采集数据".equals(dataTypes)) {
            dataTypes = "5";
        } else if ("口供资料".equals(dataTypes)) {
            dataTypes = "6";
        } else {
            dataTypes = "-1";
        }
        String evName = request.getParameter("evName");// 证据名称
        final String collectionName = evName;
        String evType = request.getParameter("evType");// 数据类型
        String comment = request.getParameter("comment");// 证据描述
        String evAdmin = userName;
        // 管理人
        /*
         * String evAdmin = request.getParameter("evAdmin");// 管理人
         */
        String tempPath = request.getParameter("dirPath");// 文件夹路径
        String fangshi = request.getParameter("fangshi");

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
        } else {
            evType = "-1";
        }
        int uptype = -1;
        if (fangshi.equals("选择本地文件夹上传")) {
            uptype = 1;
        } else if (fangshi.equals("选择本地文件上传")) {
            uptype = 0;
        }



        String res = "";
        String strs = request.getParameter("strs");
        String caseID = request.getParameter("caseID");
        Map<String, Integer> map2 = new HashMap<String, Integer>();
        String[] strings = strs.split(";");
        for (int i = 0; i < strings.length; i++) {
            if (!"".equals(strings[i]) && strings[i] != null) {
                map2.put(strings[i], i);
            }
        }

        try {
            for (int i = 0; i < listob2.size(); i++) {
                List<Object> lo = (List<Object>) listob2.get(i);
                CallListBean callListBean = new CallListBean();
                Iterator<Map.Entry<String, Integer>> entries = map2.entrySet().iterator();
                String cid = "";
                String lac = "";
                while (entries.hasNext()) {
                    Map.Entry<String, Integer> entry = entries.next();
                    String key = entry.getKey();
                    String value = "";
                    if (lo.get(entry.getValue()) != null) {
                        value = lo.get(entry.getValue()).toString();
                    }

                    if ("主叫号码".equals(key)) {
                        callListBean.setLocalNum(value);
                    } else if ("对方号码".equals(key)) {
                        callListBean.setDialNumber(value);
                    } else if ("呼叫类型".equals(key)) {
                        callListBean.setMethod(value);
                    } else if ("通话时间".equals(key)) {
                        String getDate = "";
                        boolean test = isValidDate(value);
                        boolean test1 = isValidDate1(value);
                        if (test || test1) {
                            getDate = value.replace("/", "-");
                        }
                        callListBean.setStartTime(getDate);
                    } else if ("通话时长".equals(key)) {
                        callListBean.setCallDuration(value);
                    } else if ("通话位置".equals(key)) {
                        callListBean.setPosition(value);
                    } else if ("CID".equals(key)) {
                        cid = value;
                    } else if ("LAC".equals(key)) {
                        lac = value;
                    } else if ("通话类型".equals(key)) {
                        callListBean.setCallType(value);
                    } else if ("通话所在地".equals(key)) {
                        callListBean.setCallHome(value);
                    }
                }
                //获取基站信息
                String position = cid + "," + lac;
                if (",".equals(position.trim())) {
                    position = "";
                }
                callListBean.setPosition(position);
                callListBean.setCaseID(caseID);
                sqlDao.setBeanToMysql(callListBean);
            }

            if (ticketUploadNum != 0) {
                Evidence evi = new Evidence();
                evi.setCaseID(Integer.parseInt(addcaseid));
                evi.setEvType(Integer.parseInt(evType));
                evi.setDataTypes(Integer.parseInt(dataTypes));
                evi.setComment(comment);
                evi.setEvAdmin(evAdmin);
                evi.setUptype(uptype);
                evi.setEvName(collectionName);
                evi.setUploadNum(ticketUploadNum.toString());
                evi.setSuccessNum(ticketUploadNum.toString());
                evi.setErrorNum("0");
                evi.setFinished("true");
                evi.setStatus("on");
                evi.setAddTime(dateFormat.format(new Date()));
                evi.setStartSolrTime(evi.getAddTime());
                evi.setCurrFlag("1");
                evi.setIndexFlag(1);
                sqlDao.setBeanToMysql(evi);
                ticketUploadNum = 0;
            } else {
                Evidence evi = new Evidence();
                evi.setCaseID(Integer.parseInt(addcaseid));
                evi.setEvType(Integer.parseInt(evType));
                evi.setDataTypes(Integer.parseInt(dataTypes));
                evi.setComment(comment);
                evi.setEvAdmin(evAdmin);
                evi.setUptype(uptype);
                evi.setEvName(collectionName);
                evi.setUploadNum("1");
                evi.setSuccessNum("1");
                evi.setErrorNum("0");
                evi.setFinished("true");
                evi.setStatus("on");
                evi.setAddTime(dateFormat.format(new Date()));
                evi.setStartSolrTime(evi.getAddTime());
                evi.setCurrFlag("1");
                evi.setIndexFlag(1);
                sqlDao.setBeanToMysql(evi);
            }
        } catch (Exception e) {
            System.out.println("xxx");
        }

        PrintWriter pw = null;
        try {
            pw = response.getWriter();

            pw.write(res);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            listob2 = new ArrayList<List<Object>>();
            if (pw != null) {
                pw.close();
            }
        }
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }

        return convertSuccess;
    }

    public static boolean isValidDate1(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }

        return convertSuccess;
    }
}
