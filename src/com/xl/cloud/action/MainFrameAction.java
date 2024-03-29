package com.xl.cloud.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Range;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.xl.cloud.bean.CallListBean;
import com.xl.cloud.bean.CaseType;
import com.xl.cloud.bean.CaseTypeSta;
import com.xl.cloud.bean.Caseinfo;
import com.xl.cloud.bean.Cellinfo_v2;
import com.xl.cloud.bean.Coordinatelog;
import com.xl.cloud.bean.Dealinfo;
import com.xl.cloud.bean.Department;
import com.xl.cloud.bean.DomainNameInfo;
import com.xl.cloud.bean.EmailDTO;
import com.xl.cloud.bean.Email_WorkDTO;
import com.xl.cloud.bean.Email_WorkLinkDTO;
import com.xl.cloud.bean.Evidence;
import com.xl.cloud.bean.HackCount;
import com.xl.cloud.bean.HackerDBCount;
import com.xl.cloud.bean.Ipoffline;
import com.xl.cloud.bean.Label;
import com.xl.cloud.bean.LabelData;
import com.xl.cloud.bean.PicInfo;
import com.xl.cloud.bean.PicInfo;
import com.xl.cloud.bean.PictureInfo;
import com.xl.cloud.bean.ReadExcel;
import com.xl.cloud.bean.Role;
import com.xl.cloud.bean.Section;
import com.xl.cloud.bean.SuspectInfo;
import com.xl.cloud.bean.SysLog;
import com.xl.cloud.bean.TicketAddrestDTO;
import com.xl.cloud.bean.TicketDegreeDTO;
import com.xl.cloud.bean.TicketLinkDTO;
import com.xl.cloud.bean.TicketLinksDTO;
import com.xl.cloud.bean.TicketdDTO;
import com.xl.cloud.bean.UpdateLog;
import com.xl.cloud.bean.User;
import com.xl.cloud.bean.UserAction;
import com.xl.cloud.bean.UserActionNum;
import com.xl.cloud.bean.allesDTO;
import com.xl.cloud.common.Global;
import com.xl.cloud.dao.BaseIDSqlDao;
import com.xl.cloud.dao.EmailDao;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.dao.ip_SqlDAO;
import com.xl.cloud.util.DocToHtml;
import com.xl.cloud.util.EncryptUtil;
import com.xl.cloud.util.EsClient;
import com.xl.cloud.util.EsUpdate;
import com.xl.cloud.util.JsonUtil;
import com.xl.cloud.util.PositionUtil;
import com.xl.cloud.util.RWProperties;
import com.xl.cloud.util.RandomValidateCode;
import com.xl.cloud.util.UrlCodeUtil;
import com.xl.cloud.util.ZipFileUtil;
import com.xl.cloud.util.preview.ExcelToHtmlUtil;
import com.xl.cloud.util.preview.PPTtoHtmlUtil;
import com.xl.cloud.util.preview.PdfToHtmlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class MainFrameAction {
	private SqlDao sqlDao = new SqlDao();
	private EmailDao emailDao = new EmailDao();
	static  ip_SqlDAO ip_Sqldao;
	private BaseIDSqlDao baseDao = new BaseIDSqlDao();
	private static boolean isConnNet=false;//是否联网标志
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public List<LabelData> getLabelDataInfo = new ArrayList<LabelData>();
	public List<UserAction> getUserActionInfo = new ArrayList<UserAction>();
	public List<DomainNameInfo> getBeanStr = new ArrayList<DomainNameInfo>();
	public List<TicketDegreeDTO> Ticket = new ArrayList<TicketDegreeDTO>();
	public List<TicketLinkDTO> TicketDTO = new ArrayList<TicketLinkDTO>();
	public List<Label> labelListExport = new ArrayList<Label>();
	public List<Email_WorkDTO> email_workDTOExport = new ArrayList<Email_WorkDTO>();
	public List<Email_WorkDTO> email_workDTOExport1 = new ArrayList<Email_WorkDTO>();
	public List<TicketLinksDTO> ticketLinksListOut = new ArrayList<TicketLinksDTO>();
	public static String getLinkmanStr = "";
	public static String getPhone = "";
	public static String getLinkmanName = "";
	final Logger logger = Logger.getLogger(BuildCollection.class);
	public static ArrayList<TicketdDTO> caseidlist3 = new ArrayList<TicketdDTO>();

	static{
		//读取配置文件地址
		String configPath = MainFrameAction.class.getResource("/").getPath()+"ConnNet.config";
		String isConn = RWProperties.getProperty("isConnNet", configPath);
		if(!StringUtils.isEmpty(isConn)){
			if(isConn.equals("1"))
				isConnNet=true;
			else
				isConnNet=false;
		}
		System.out.println("isConnNet-----"+isConnNet);
	}

	public MainFrameAction() throws Exception {
		// TODO Auto-generated constructor stub
		ip_Sqldao=new ip_SqlDAO();
	}

	/** 
	 * 判断查询结果集中是否存在某列 
	 * @param rs 查询结果集 
	 * @param columnName 列名 
	 * @return true 存在; false 不存咋 
	 */
	public boolean isExistColumn(ResultSet rs, String columnName) {  
		try {  
			if (rs!=null && rs.findColumn(columnName) > 0 ) {  
				return true;  
			}   
		}  
		catch (SQLException e) {  
			return false;  
		} 			       
		return false;  
	}  

	/**
	 * 查询黑客数据库更新数据信息
	 */
	@RequestMapping(value = "/panorama.php")
	public String getPanoramaMap(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		// 在此处理：： 现在的黑客数据搜索页面 加下黑客数量 表是hackerdbcount 这个表 然后统计下问题
		return "NewFile";
	}
	
	@RequestMapping(value = "/admin/default.php")
	public String defaultPage(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "default";
	}

	@RequestMapping(value = "/admin/sectionmanager.php")
	public String rolemanager(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "sectionmanager";
	}

	@RequestMapping(value = "/getcaselist.php")
	public String getCase_list(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "case_list";
	}


	@RequestMapping(value = "/getcaselist2.php")
	public String getCase_list2(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "case_list2";
	}

	@RequestMapping(value = "/admin/departmentmanager.php")
	public String departmentmanager(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "departmentmanager";
	}

	@RequestMapping(value = "/admin/top.php")
	public String top(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "top";
	}

	@RequestMapping(value = "/admin/left.php")
	public String left(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "left";
	}

	@RequestMapping(value = "/admin/footer.php")
	public String footer(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "footer";
	}

	@RequestMapping(value = "/admin/index.php")
	public String index(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "index";
	}

	@RequestMapping(value = "/admin/ticketAnalysis.php")
	public String ticketAnalysis(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "ticketAnalysis";
	}

	@RequestMapping(value = "/admin/huadan.php")
	public String huadan(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		String caseid = request.getParameter("caseid");
		String allphone = request.getParameter("allphone");
		String casename = request.getParameter("casename");

		logger.info("---------" + casename);

		// 进行解码
		casename = URLDecoder.decode(casename, "utf-8");

		logger.info("----解码后fileUrl-----" + casename);
		//System.out.println("ticketAnalysis########： " + casename);
		request.setAttribute("caseid", caseid);
		request.setAttribute("allphone", allphone);
		request.setAttribute("casename", casename);

		return "ticketAnalysis";
	}

	@RequestMapping(value = "/admin/youjian.php")
	public String youjian(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {

		String caseid = request.getParameter("caseid");
		String casename = request.getParameter("casename");

		//casename = new String(casename.getBytes("iso-8859-1"),"utf-8");
		casename = URLDecoder.decode(casename, "utf-8");
		request.setAttribute("caseid", caseid);
		request.setAttribute("casename", casename);
		//System.out.println("email_workbench########： " + casename);
		return "email_workbench";
	}


	@RequestMapping(value = "/admin/labelStatistics.php")
	public String labelStatistics(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "labelStatistics";
	}

	@RequestMapping(value = "/admin/userActionStatistics.php")
	public String userActionStatistics(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "userActionStatistics";
	}

	// 操作记录
	public void actionLog(String name, String action, String module) throws UnknownHostException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

	// 日志
	public void writeLog(String name, String action, String module) throws UnknownHostException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

	/**
	 * 登录se
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

	// 加密
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	// 解密
	public static String convertMD5(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	/**
	 * 登录首页
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
	@RequestMapping(value = "/admin/Login.php")
	public String isLogin(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		// String uname = (String) session.getAttribute("userName");
		// if (uname != null) {
		// return "redirect:/CaseSpace.php";
		// }
		return "Login";
	}

	@RequestMapping(value = "/admin/login.php")
	public String islogin(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		// String uname = (String) session.getAttribute("userName");
		// if (uname != null) {
		// return "redirect:/CaseSpace.php";
		// }
		return "Login";
	}

	/**
	 * 退出系统
	 */
	@RequestMapping(value = "/logout.php")
	public String logout(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		request.getSession().invalidate();
		return "Login";
	}

	/**
	 * 修改密码
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/editPwd.php")
	public void editUser(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {
		String id = request.getParameter("id");

		String password = request.getParameter("password");
		String res = "succ";
		PrintWriter pw = null;

		User user = new User();

		int idInt = 0;
		try {
			idInt = Integer.parseInt(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setId(idInt);
		List<User> list = sqlDao.getListfromMysql(user);
		if (list.size() > 0) {
			user.setPassword(EncryptUtil.string2MD5("xl_" + password));
			sqlDao.updateToMysql(user);
			actionLog((String) session.getAttribute("userName"), "编辑", "案件管理");
		} else {
			user.setId(-1);
			list = sqlDao.getListfromMysql(user);
			if (list.size() > 0) {
				res = "exist";
			}
		}

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

	/**
	 * 登录页面生成验证码
	 */

	@RequestMapping(value = "/getVerify.php")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		RandomValidateCode randomValidateCode = new RandomValidateCode();
		try {
			randomValidateCode.getRandcode(request, response);// 输出验证码图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// login
	@RequestMapping(value = "/log.php")
	public String Login(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {

		String uname = (String) session.getAttribute("userName");

		/*
		 * if (uname != null) { return "redirect:/CaseSpace.php"; }
		 */

		String username = request.getParameter("username");
		String password = "xl_" + request.getParameter("password");
		String validate = request.getParameter("validate");

		String sn = request.getParameter("sn");
		if (StringUtils.isEmpty(validate)) {
			map.put("msg", "");
			return "Login";
		} else {
			validate = validate.toUpperCase();
		}

		//		String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
		//		if (!validate.equals(random)) {
		//			map.put("msg", "验证码错误！");
		//			writeLog( username,"登录",username + " 登录失败");
		//			return "Login";
		//		}

		User admin = new User();
		//admin.setUsername(username);
		admin.setPoliceNO(username);
		List<User> lists = sqlDao.getListfromMysql(admin);
		if (lists.isEmpty()) {
			map.put("msg", "没有该用户！");
			writeLog( username,"登录", username + " 登录失败");
			return "Login";
		} else {
			String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
			if (!validate.equals(random)) {
				map.put("msg", "验证码错误！");
				writeLog( lists.get(0).getUsername(),"登录",lists.get(0).getUsername() + " 登录失败");
				return "Login";
			}
			if (string2MD5(password).equals(lists.get(0).getPassword())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date today = new Date();
				String today_ = sdf.format(today);
				User b = lists.get(0);
				String tempsn = b.getSn();
				b.setLastLoginTime(today_);
				sqlDao.updateToMysql(b);
				// if(StringUtils.isEmpty(tempsn)){
				// map.put("msg", "该用户还未绑定加密狗！");
				// writeLog("2", username, username+" 登录失败");
				// return "Login";
				// }else
				if (sn.equals(tempsn)) {
					writeLog(b.getUsername(),"登录", b.getUsername() + " 登录北斗云");
					/*String ptters = b.getPrivilege();
					RoleTable role2 = new RoleTable();
					role2.setId(Integer.parseInt(ptters));
					List<RoleTable> roles2 = sqlDao.getListfromMysql(role2);
					if (roles2.size() > 0) {
						RoleTable roles3 = roles2.get(0);
						b.setPrivilege(roles3.getPartterName());
					}*/
					String ptters = b.getUserrole();
					//System.out.println("ptters="+ptters);
					Role role2 = new Role();
					role2.setId(Integer.parseInt(ptters));
					List<Role> roles2 = sqlDao.getListfromMysql(role2);
					if (roles2.size() > 0) {
						Role roles3 = roles2.get(0);
						b.setPrivilege(roles3.getRoleName());
					}
					session.setAttribute("privilege", b.getPrivilege());
					session.setAttribute("userName", b.getUsername());
					session.setAttribute("partment", b.getPartment());
					session.setAttribute("section", b.getSection());
					session.setAttribute("role", b.getUserrole());
					session.setAttribute("sUserID", b.getId());
					session.setAttribute("cmfIP", Global.CmfIP);
					/*
					 * int prilevel = 0; if
					 * (Global.PRIMAP.containsKey(b.getPrivilege())) { prilevel
					 * = Global.PRIMAP.get(b.getPrivilege()); }
					 * session.setAttribute("prilevel", prilevel);
					 */
					session.setAttribute("user", b);
					return "redirect:/admin/default.php";
				} else {
					map.put("msg", "该用户与加密狗不匹配！");
					writeLog( b.getUsername(),"登录", b.getUsername() + " 登录失败");
					return "Login";
				}
			} else {
				map.put("msg", "用户名或密码错误！");
				writeLog( lists.get(0).getUsername(),"登录", lists.get(0).getUsername() + " 登录失败");
				return "Login";
			}

		}
	}

	@RequestMapping(value = "/admin/logrizhi.php")
	public String log(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		SysLog sl = new SysLog();
		String pageno = request.getParameter("pageno");
		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		Coordinatelog log = new Coordinatelog();

		List<SysLog> logs = sqlDao.getOrderListfromMysqlLike(sl, (pageIndex - 1) * pageSize, pageSize, "id");
		int total = sqlDao.getcountfromMysqlLike(sl);
		num = total / pageSize;
		if (total % pageSize != 0) {
			num++;
		}
		num = num == 0 ? 1 : num;

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

		map.put("logs", logs);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);

		return "log";
	}

	// 操作日志分页
	@RequestMapping(value = "/page.php")
	public void page(HttpServletRequest request, Map<String, Object> map, HttpSession session, Model model,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		int pageSize = 8;// 每页页数
		String pagework = request.getParameter("pagework");
		String sertext = request.getParameter("sertext");

		int pageIndex = 1;
		if (!StringUtils.isEmpty(pagework)) {
			pageIndex = Integer.parseInt(pagework);
		}

		String json_str = "";
		int l = 0;
		int total = 0;
		List<SysLog> logs = new ArrayList<SysLog>();
		if (sertext == null || "".equals(sertext) || "undefined".equals(sertext)) {
			SysLog syslog = new SysLog();
			syslog.setType("2");
			total = sqlDao.getcountfromMysql(syslog);
			l = total / pageSize;
			if (total % pageSize == 0) {
			} else {
				l++;
			}
			int startRowwork = (pageIndex - 1) * pageSize;
			logs = sqlDao.getOrderListfromMysql(syslog, startRowwork, pageSize, "id");
			JSONArray jsonArray = JSONArray.fromObject(logs);
			json_str = jsonArray.toString();
		} else {
			SysLog syslog = new SysLog();
			syslog.setType("2");
			syslog.setContent(sertext);
			total = sqlDao.getcountfromMysqlLike(syslog);
			l = total / pageSize;
			if (total % pageSize == 0) {
			} else {
				l++;
			}
			int startRowwork = (pageIndex - 1) * pageSize;
			logs = sqlDao.getOrderListfromMysqlLike(syslog, startRowwork, pageSize, "id");
			JSONArray jsonArray = JSONArray.fromObject(logs);
			json_str = jsonArray.toString();
		}
		String result_data = "{\"totalPages\":\"" + l + "\",\"nowPage\":\"" + pageIndex + "\",\"total\":" + total
				+ ",\"resData\":" + json_str + "}";
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

	/**
	 * 工作台
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
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/admin/workbench.php")
	public String workbench(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {
		actionLog((String) session.getAttribute("userName"), "查看", "大屏展示");
		User user = (User) session.getAttribute("user");
		String userrole = user.getUserrole();
		Role role = new Role();
		int flag=0;
		if(userrole!=null && !"".equals(userrole)){
			role.setId(Integer.parseInt(userrole));
			List<Role> listfromMysql = sqlDao.getListfromMysql(role);
			if(listfromMysql.size()>0){
				Role role2 = listfromMysql.get(0);
				String roleMenu = role2.getRoleMenu();
				String[] split = roleMenu.split(",");
				for (String string : split) {
					if("1".equals(string)){
						flag=1;
					}
				}
			}
		}
		if(flag==0){
			String ret="";
			if(userrole!=null && !"".equals(userrole)){
				role.setId(Integer.parseInt(userrole));
				List<Role> listfromMysql2 = sqlDao.getListfromMysql(role);
				if(listfromMysql2.size()>0){
					Role role2 = listfromMysql2.get(0);
					String roleName = role2.getRoleName();
					if("管理员".equals(roleName)){
						ret="usermanager";
					}else{
						ret="newcase";
					}
					/*String roleMenu = role2.getRoleMenu();
					String split = roleMenu.split(",")[1];
					ActionMenu actionMenu = new ActionMenu();
					if(split!=null){
						role2.setId(Integer.parseInt(split));
						List<ActionMenu> listfromMysql = sqlDao.getListfromMysql(actionMenu);
						if(listfromMysql.size()>0){
							ActionMenu actionMenu2 = listfromMysql.get(flag);
							String route = actionMenu2.getRoute();
							String[] split2 = route.split("/");
							int length = split2.length;

						}
					}*/
				}
			}
			return ret;
		}else{
			return "workbench";
		}


	}

	@RequestMapping(value = "/admin/dataMining.php")
	public String dataMining(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "dataMining";
	}

	@RequestMapping(value = "/admin/newcase.php")
	public String newcase(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "newcase";
	}

	@RequestMapping(value = "/admin/role_management.php")
	public String role_management(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "role_management";
	}

	@RequestMapping(value = "/admin/system_log.php")
	public String system_log(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "system_log";
	}

	@RequestMapping(value = "/admin/case_list.php")
	public String caselist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "case_list";
	}

	@RequestMapping(value = "/admin/evidence_adding.php")
	public String evidence_adding(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "evidence_adding";
	}

	// 查询案件列表
	@RequestMapping(value = "/getcaselist_refresh.php")
	public void getcaselist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String pageno = request.getParameter("pageno");
		String caseNum = request.getParameter("caseNum");
		String caseName = request.getParameter("caseName");
		// 案件受理日期
		// 案件所属城市
		String shi = request.getParameter("shi"); // 案件所属城市
		String caseType = request.getParameter("caseType");// (案件类型)
		String section = request.getParameter("section"); // 所属科室
		String userName = request.getParameter("userName");// 案件负责人
		String createdTime = request.getParameter("createdTime");
		String status ="办案中";// request.getParameter("status");// 案件状态
		String endTime = request.getParameter("endTime");
		String labels = request.getParameter("ids");
		String label = "";
		label = labels;
		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;
		int total = 0;

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		Caseinfo cas = new Caseinfo();
		if (!StringUtils.isEmpty(caseNum)) {
			cas.setCaseNum(caseNum);
		}
		if (!StringUtils.isEmpty(caseName)) {
			cas.setCaseName(caseName);
		}
		if (!StringUtils.isEmpty(shi)) {
			//			cas.setShi(shi);
			//			cas.setSheng(shi);
			cas.setAllArea(shi);
		}
		if (!StringUtils.isEmpty(label)) {
			cas.setLabel(label);
		}
		if (!StringUtils.isEmpty(section)) {
			Section section5 = new Section();
			section5.setSectionName(section);
			List<Section> listfromMysqlse = sqlDao.getListfromMysql(section5);
			if (listfromMysqlse.size() > 0) {
				Section departmentse = listfromMysqlse.get(0);
				int seName = departmentse.getId();
				cas.setSection(seName+"");
			}
		}
		if (!StringUtils.isEmpty(label)) {
			cas.setLabel(label);
		}
		/*if (!StringUtils.isEmpty(createdTime)) {
			cas.setCreatedTime(createdTime);
		}*/
		if (!StringUtils.isEmpty(status)) {
			cas.setStatus(status);
		}
		if (!StringUtils.isEmpty(caseType)) {
			cas.setCaseType(caseType);
		}
		List<Caseinfo> listfromMysql = new ArrayList<Caseinfo>();

		User user = (User) session.getAttribute("user");
		String roleName="" ;
		if(user != null){
			String userrole = user.getUserrole();
			Role role = new Role();
			role.setId(Integer.parseInt(userrole));
			List<Role> roleList = sqlDao.getListfromMysql(role);
			if(roleList.size()>0){
				Role role2 = roleList.get(0);
				roleName = role2.getDataScope();

			}
		}
		if (roleName.equals("科室数据") ) {// 科长
			cas.setSection(user.getSection()+"/"+user.getUsername());
		} else if (roleName.equals("部门数据") ) {// 部长
			cas.setDepartment(user.getPartment());
		} else if (roleName.equals("个人数据") ) {// 科员
			cas.setUserName(user.getUsername());
		} else if (roleName.equals("所有数据")) {// 局长
			cas.setId(-1);
		} else {
			cas.setId(-2);
		}
		List<Caseinfo> logs =new ArrayList<Caseinfo>();
		if(createdTime!=null && !"".equals(createdTime) && endTime!=null && !"".equals(endTime)){
			logs = sqlDao.getListfromMysqlLikTimeecaseOR(cas, createdTime, endTime, (pageIndex - 1) * pageSize, pageSize);
			total = sqlDao.getListfromMysqlLikeTimecaseCountOR(cas, createdTime, endTime);
		}else{
			total = sqlDao.getcountfromMysqlLikeOR(cas);
			logs = sqlDao.getOrderListfromMysqlLikeOR(cas, (pageIndex - 1) * pageSize, pageSize, "id");
		}

		for (Caseinfo section2 : logs) {
			String sectionw = section2.getSection();
			Section section3 = new Section();
			section3.setId(Integer.parseInt(sectionw));
			List<Section> listfromMysqlse = sqlDao.getListfromMysql(section3);
			if (listfromMysqlse.size() > 0) {
				Section departmentse = listfromMysqlse.get(0);
				String seName = departmentse.getSectionName();
				section2.setSection(seName);
			}
			listfromMysql.add(section2);

		}

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

		map.put("logs", listfromMysql);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("caseNum", caseNum);
		map.put("caseName", caseName);
		map.put("label", label);
		map.put("shi", shi);
		map.put("caseType", caseType);
		map.put("section", section);
		map.put("userName", userName);
		map.put("createdTime", createdTime);
		map.put("status", status);
		// 放入map中
		CaseType type = new CaseType();
		List<CaseType> caseTypeList = sqlDao.getListfromMysql(type);
		Section section2 = new Section();
		List<Section> caseSection2 = sqlDao.getListfromMysql(section2);
		map.put("caseSection2", caseSection2);
		map.put("caseTypeList", caseTypeList);
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
	// 查询案件列表
	@RequestMapping(value = "/getcaselist_refresh2.php")
	public void getcaselist2(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String pageno = request.getParameter("pageno");
		String caseNum = request.getParameter("caseNum");
		String caseName = request.getParameter("caseName");
		// 案件受理日期
		// 案件所属城市
		String shi = request.getParameter("shi"); // 案件所属城市
		String caseType = request.getParameter("caseType");// (案件类型)
		String section = request.getParameter("section"); // 所属科室
		String userName = request.getParameter("userName");// 案件负责人
		String createdTime = request.getParameter("createdTime");
		String status ="已结案";// request.getParameter("status");// 案件状态
		String endTime = request.getParameter("endTime");
		String labels = request.getParameter("ids");
		String label = "";
		label = labels;
		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;
		int total = 0;

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		Caseinfo cas = new Caseinfo();
		if (!StringUtils.isEmpty(caseNum)) {
			cas.setCaseNum(caseNum);
		}
		if (!StringUtils.isEmpty(caseName)) {
			cas.setCaseName(caseName);
		}
		if (!StringUtils.isEmpty(shi)) {
			//			cas.setShi(shi);
			//			cas.setSheng(shi);
			cas.setAllArea(shi);
		}
		if (!StringUtils.isEmpty(label)) {
			cas.setLabel(label);
		}
		if (!StringUtils.isEmpty(section)) {
			Section section5 = new Section();
			section5.setSectionName(section);
			List<Section> listfromMysqlse = sqlDao.getListfromMysql(section5);
			if (listfromMysqlse.size() > 0) {
				Section departmentse = listfromMysqlse.get(0);
				int seName = departmentse.getId();
				cas.setSection(seName+"");
			}
		}
		if (!StringUtils.isEmpty(label)) {
			cas.setLabel(label);
		}
		/*if (!StringUtils.isEmpty(createdTime)) {
				cas.setCreatedTime(createdTime);
			}*/
		if (!StringUtils.isEmpty(status)) {
			cas.setStatus(status);
		}
		if (!StringUtils.isEmpty(caseType)) {
			cas.setCaseType(caseType);
		}
		List<Caseinfo> listfromMysql = new ArrayList<Caseinfo>();

		User user = (User) session.getAttribute("user");
		String roleName="" ;
		if(user != null){
			String userrole = user.getUserrole();
			Role role = new Role();
			role.setId(Integer.parseInt(userrole));
			List<Role> roleList = sqlDao.getListfromMysql(role);
			if(roleList.size()>0){
				Role role2 = roleList.get(0);
				roleName = role2.getDataScope();

			}
		}
		if (roleName.equals("科室数据") ) {// 科长
			cas.setSection(user.getSection()+"/"+user.getUsername());
		} else if (roleName.equals("部门数据") ) {// 部长
			cas.setDepartment(user.getPartment());
		} else if (roleName.equals("个人数据") ) {// 科员
			cas.setUserName(user.getUsername());
		} else if (roleName.equals("所有数据")) {// 局长
			cas.setId(-1);
		} else {
			cas.setId(-2);
		}
		List<Caseinfo> logs =new ArrayList<Caseinfo>();
		if(createdTime!=null && !"".equals(createdTime) && endTime!=null && !"".equals(endTime)){
			logs = sqlDao.getListfromMysqlLikTimeecaseOR(cas, createdTime, endTime, (pageIndex - 1) * pageSize, pageSize);
			total = sqlDao.getListfromMysqlLikeTimecaseCountOR(cas, createdTime, endTime);
		}else{
			total = sqlDao.getcountfromMysqlLikeOR(cas);
			logs = sqlDao.getOrderListfromMysqlLikeOR(cas, (pageIndex - 1) * pageSize, pageSize, "id");
		}

		for (Caseinfo section2 : logs) {
			String sectionw = section2.getSection();
			Section section3 = new Section();
			section3.setId(Integer.parseInt(sectionw));
			List<Section> listfromMysqlse = sqlDao.getListfromMysql(section3);
			if (listfromMysqlse.size() > 0) {
				Section departmentse = listfromMysqlse.get(0);
				String seName = departmentse.getSectionName();
				section2.setSection(seName);
			}
			listfromMysql.add(section2);

		}

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

		map.put("logs", listfromMysql);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("caseNum", caseNum);
		map.put("caseName", caseName);
		map.put("label", label);
		map.put("shi", shi);
		map.put("caseType", caseType);
		map.put("section", section);
		map.put("userName", userName);
		map.put("createdTime", createdTime);
		map.put("status", status);
		// 放入map中
		CaseType type = new CaseType();
		List<CaseType> caseTypeList = sqlDao.getListfromMysql(type);
		Section section2 = new Section();
		List<Section> caseSection2 = sqlDao.getListfromMysql(section2);
		map.put("caseSection2", caseSection2);
		map.put("caseTypeList", caseTypeList);
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

	// 查询证据列表
	@RequestMapping(value = "/getEvidencelist.php")
	public String getEvidencelist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String pageno = request.getParameter("pageno");

		String evid = request.getParameter("evid");

		String addTime = request.getParameter("addTime");
		String evAdmin = request.getParameter("evAdmin");
		String uptype = request.getParameter("uptype");
		String evSize = request.getParameter("evSize"); // 案件标签(案件类型)
		String evName = request.getParameter("evName"); // 所属科室
		String dataTypes = request.getParameter("dataTypes");
		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		Evidence ev = new Evidence();
		if (!StringUtils.isEmpty(dataTypes)) {
			ev.setDataTypes(Integer.parseInt(dataTypes));
		}
		if (!StringUtils.isEmpty(addTime)) {
			ev.setAddTime(addTime);
		}
		if (!StringUtils.isEmpty(evAdmin)) {
			ev.setEvAdmin(evAdmin);
		}
		if (!StringUtils.isEmpty(uptype)) {
			ev.setUptype(-1);
		}
		if (!StringUtils.isEmpty(evSize)) {
			ev.setEvSize(evSize);
		}
		if (!StringUtils.isEmpty(evName)) {
			ev.setEvName(evName);
		}
		if (!StringUtils.isEmpty(evid)) {
			ev.setCaseID(Integer.parseInt(evid));
		}

		Caseinfo onecas = null;
		if (evid != null && !"".equals(evid)) {
			Caseinfo cas = new Caseinfo();
			cas.setId(Integer.parseInt(evid));
			List<Caseinfo> casinfo = sqlDao.getListfromMysql(cas);
			if (casinfo.size() > 0) {
				onecas = casinfo.get(0);

				String sectionw = casinfo.get(0).getSection();
				Section section3 = new Section();
				section3.setId(Integer.parseInt(sectionw));
				List<Section> listfromMysqlse = sqlDao.getListfromMysql(section3);
				if (listfromMysqlse.size() > 0) {
					Section departmentse = listfromMysqlse.get(0);
					String seName = departmentse.getSectionName();
					onecas.setSection(seName);
				}
			}

		}

		List<Evidence> logs = sqlDao.getOrderListfromMysqlLike(ev, (pageIndex - 1) * pageSize, pageSize, "id");
		int total = sqlDao.getcountfromMysqlLike(ev);
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

		map.put("result", onecas);
		map.put("logs", logs);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("dataTypes", dataTypes);
		map.put("addTime", addTime);
		map.put("evAdmin", evAdmin);
		map.put("uptype", uptype);
		map.put("evSize", evSize);
		map.put("evName", evName);

		return "addevidence";
	}
	// 查询证据列表
	@RequestMapping(value = "/getEvidencelist2.php")
	public String getEvidencelist2(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String pageno = request.getParameter("pageno");

		String evid = request.getParameter("evid");

		String addTime = request.getParameter("addTime");
		String evAdmin = request.getParameter("evAdmin");
		String uptype = request.getParameter("uptype");
		String evSize = request.getParameter("evSize"); // 案件标签(案件类型)
		String evName = request.getParameter("evName"); // 所属科室
		String dataTypes = request.getParameter("dataTypes");
		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		Evidence ev = new Evidence();
		if (!StringUtils.isEmpty(dataTypes)) {
			ev.setDataTypes(Integer.parseInt(dataTypes));
		}
		if (!StringUtils.isEmpty(addTime)) {
			ev.setAddTime(addTime);
		}
		if (!StringUtils.isEmpty(evAdmin)) {
			ev.setEvAdmin(evAdmin);
		}
		if (!StringUtils.isEmpty(uptype)) {
			ev.setUptype(-1);
		}
		if (!StringUtils.isEmpty(evSize)) {
			ev.setEvSize(evSize);
		}
		if (!StringUtils.isEmpty(evName)) {
			ev.setEvName(evName);
		}
		if (!StringUtils.isEmpty(evid)) {
			ev.setCaseID(Integer.parseInt(evid));
		}

		Caseinfo onecas = null;
		if (evid != null && !"".equals(evid)) {
			Caseinfo cas = new Caseinfo();
			cas.setId(Integer.parseInt(evid));
			List<Caseinfo> casinfo = sqlDao.getListfromMysql(cas);
			if (casinfo.size() > 0) {
				onecas = casinfo.get(0);

				String sectionw = casinfo.get(0).getSection();
				Section section3 = new Section();
				section3.setId(Integer.parseInt(sectionw));
				List<Section> listfromMysqlse = sqlDao.getListfromMysql(section3);
				if (listfromMysqlse.size() > 0) {
					Section departmentse = listfromMysqlse.get(0);
					String seName = departmentse.getSectionName();
					onecas.setSection(seName);
				}
			}

		}

		List<Evidence> logs = sqlDao.getOrderListfromMysqlLike(ev, (pageIndex - 1) * pageSize, pageSize, "id");
		int total = sqlDao.getcountfromMysqlLike(ev);
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

		map.put("result", onecas);
		map.put("logs", logs);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("dataTypes", dataTypes);
		map.put("addTime", addTime);
		map.put("evAdmin", evAdmin);
		map.put("uptype", uptype);
		map.put("evSize", evSize);
		map.put("evName", evName);

		return "addevidence2";
	}

	// 新建案件成功信息传入导入页面
	@RequestMapping(value = "/getNewCaseInfo.php")
	public String getNewCaseInfo(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String caseId = request.getParameter("case_id");

		Caseinfo caseinfo = new Caseinfo();
		if (caseId != null) {
			Caseinfo cas = new Caseinfo();
			cas.setId(Integer.parseInt(caseId));
			List<Caseinfo> casinfo = sqlDao.getListfromMysql(cas);
			if (casinfo.size() > 0) {
				caseinfo = casinfo.get(0);
				String sectionw = casinfo.get(0).getSection();
				Section section3 = new Section();
				section3.setId(Integer.parseInt(sectionw));
				List<Section> listfromMysqlse = sqlDao.getListfromMysql(section3);
				if (listfromMysqlse.size() > 0) {
					Section departmentse = listfromMysqlse.get(0);
					String seName = departmentse.getSectionName();
					caseinfo.setSection(seName);
				}
			}
		}
		map.put("result", caseinfo);
		session.setAttribute("caseinfo2", caseinfo);
		return "importevidence_newcase";
	}

	// 查询案件摘要信息传入证据导入页面
	@RequestMapping(value = "/getCaseSummary.php")
	public String getCaseSummary(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String caseId = request.getParameter("case_summary_id");

		Caseinfo caseinfo = new Caseinfo();
		if (caseId != null) {
			Caseinfo cas = new Caseinfo();
			cas.setId(Integer.parseInt(caseId));
			List<Caseinfo> casinfo = sqlDao.getListfromMysql(cas);
			if (casinfo.size() > 0) {
				caseinfo = casinfo.get(0);
				String sectionw = casinfo.get(0).getSection();
				Section section3 = new Section();
				section3.setId(Integer.parseInt(sectionw));
				List<Section> listfromMysqlse = sqlDao.getListfromMysql(section3);
				if (listfromMysqlse.size() > 0) {
					Section departmentse = listfromMysqlse.get(0);
					String seName = departmentse.getSectionName();
					caseinfo.setSection(seName);
				}
			}

		}
		map.put("result", caseinfo);
		session.setAttribute("caseinfo2", caseinfo);
		return "importevidence";
	}
	//既往案件  查询案件摘要信息传入证据导入页面
	@RequestMapping(value = "/getCaseSummary2.php")
	public String getCaseSummary2(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String caseId = request.getParameter("case_summary_id");

		Caseinfo caseinfo = new Caseinfo();
		if (caseId != null) {
			Caseinfo cas = new Caseinfo();
			cas.setId(Integer.parseInt(caseId));
			List<Caseinfo> casinfo = sqlDao.getListfromMysql(cas);
			if (casinfo.size() > 0) {
				caseinfo = casinfo.get(0);
				String sectionw = casinfo.get(0).getSection();
				Section section3 = new Section();
				section3.setId(Integer.parseInt(sectionw));
				List<Section> listfromMysqlse = sqlDao.getListfromMysql(section3);
				if (listfromMysqlse.size() > 0) {
					Section departmentse = listfromMysqlse.get(0);
					String seName = departmentse.getSectionName();
					caseinfo.setSection(seName);
				}
			}

		}
		map.put("result", caseinfo);
		session.setAttribute("caseinfo2", caseinfo);
		return "importevidence2";
	}

	// 案件列表按条件查询
	@RequestMapping(value = "/searchbycondit.php")
	public void searchbycondit(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String pageno = request.getParameter("pageno");
		String caseNum = request.getParameter("caseNum");
		String caseName = request.getParameter("caseName");
		String casestatusstr = request.getParameter("casestatus");
		String caseType = request.getParameter("caseType");
		String section = request.getParameter("section");
		String shi = request.getParameter("shi");
		String createdTime = request.getParameter("createdTime");
		String endTime = request.getParameter("endTime");
		String labels = request.getParameter("ids");
		String label = "";

		label = labels;
		String casestatus = "";
		if ("0".equals(casestatusstr)) {
			casestatus = "办案中";
		} else if ("1".equals(casestatusstr)) {
			casestatus = "已结案";
		}

		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		Caseinfo cas = new Caseinfo();
		if (!StringUtils.isEmpty(caseNum)) {
			cas.setCaseNum(caseNum);
		}
		if (!StringUtils.isEmpty(caseName)) {
			cas.setCaseName(caseName);
		}
		if (!StringUtils.isEmpty(casestatus)) {
			cas.setStatus(casestatus);
		}

		if (!StringUtils.isEmpty(caseType)) {
			cas.setCaseType(caseType);
		}
		if (!StringUtils.isEmpty(section)) {
			Section section2 = new Section();
			section2.setSectionName(section);
			List<Section> listfromMysqlse = sqlDao.getListfromMysql(section2);
			if (listfromMysqlse.size() > 0) {
				Section section3 = listfromMysqlse.get(0);
				int seName = section3.getId();
				cas.setSection(seName + "");
			}
		}
		if (!StringUtils.isEmpty(shi)) {
			cas.setShi(shi);
			cas.setSheng(shi);
		}
		if (!StringUtils.isEmpty(label)) {
			cas.setLabel(label);
		}

		int total;

		// 得到所有的案件信息
		// 在此判断，如果为科员:科员只能查看被授权的案件
		List<Caseinfo> listfromMysql = new ArrayList<Caseinfo>();
		List<Caseinfo> logs = new ArrayList<Caseinfo>();
		User identify = (User) session.getAttribute("user");

		if (!StringUtils.isEmpty(createdTime) && !StringUtils.isEmpty(endTime)) {

			if (identify == null || !identify.getPrivilege().equals("科员")) { // 包含时间条件下判断非科员的搜索总范围

				logs = sqlDao.getListfromMysqlLikTimeecase(cas, createdTime, endTime, (pageIndex - 1) * pageSize,
						pageSize);
				total = sqlDao.getListfromMysqlLikeTimecaseCount(cas, createdTime, endTime);
			} else {
				List<Caseinfo> allCasesfromMysql = new ArrayList<Caseinfo>();
				List<Caseinfo> allCases = new ArrayList<Caseinfo>();
				Coordinatelog log = new Coordinatelog();
				log.setGrantedName(identify.getUsername());
				List<Coordinatelog> Granted_cases = sqlDao.getOrderListfromMysql(log, "id");
				listfromMysql = new ArrayList<Caseinfo>();
				for (Coordinatelog section1 : Granted_cases) {
					// 根据得到的协同案件查询到对应的案件
					Caseinfo case1 = new Caseinfo();
					case1.setCaseName(section1.getCaseName());
					case1.setCaseType(section1.getCaseType());
					case1.setSection(section1.getSection());
					case1.setCaseNum(section1.getCaseNum());

					listfromMysql = sqlDao.getListfromMysql(case1);
					allCases.add(listfromMysql.get(0));
				}
				allCasesfromMysql = sqlDao.getListfromMysqlLikTimeecase(cas, createdTime, endTime);
				int count = 0; // 自定义分页 pageIndex传进来的页码
				for (Caseinfo temp1 : allCases) {
					for (Caseinfo temp2 : allCasesfromMysql) {
						if (temp1.getCaseNum().equals(temp2.getCaseNum())
								&& temp1.getCaseName().equals(temp2.getCaseName())) {
							if (count >= (pageIndex - 1) * pageSize && count < (pageIndex - 1) * pageSize + 10) {
								logs.add(temp1);
							}
							count++;
						}
					}
				}
				total = count;
			}

		} else {
			if (identify == null || !identify.getPrivilege().equals("科员")) { // 不包含时间条件下判断非科员的搜索总范围
				// 判断非科员、游客、局长（即其他职位），根据科室显示对应的搜索总范围
				if (identify != null && !identify.getPrivilege().equals("局长")) {
					cas.setSection(identify.getSection());
				}
				logs = sqlDao.getOrderListfromMysqlLikeTime(cas, (pageIndex - 1) * pageSize, pageSize, "id");
				total = sqlDao.getcountfromMysqlLikeTime(cas);
			} else {
				List<Caseinfo> allCasesfromMysql = new ArrayList<Caseinfo>();
				List<Caseinfo> allCases = new ArrayList<Caseinfo>();
				Coordinatelog log = new Coordinatelog();
				log.setGrantedName(identify.getUsername());
				List<Coordinatelog> Granted_cases = sqlDao.getOrderListfromMysql(log, "id");
				listfromMysql = new ArrayList<Caseinfo>();
				for (Coordinatelog section1 : Granted_cases) {
					// 根据得到的协同案件查询到对应的案件
					Caseinfo case1 = new Caseinfo();
					case1.setCaseName(section1.getCaseName());
					case1.setCaseType(section1.getCaseType());
					case1.setSection(section1.getSection());
					case1.setCaseNum(section1.getCaseNum());

					listfromMysql = sqlDao.getListfromMysql(case1);
					allCases.add(listfromMysql.get(0));
				}
				allCasesfromMysql = sqlDao.getOrderListfromMysqlLikeTime(cas, "id");
				int count = 0; // 自定义分页 pageIndex传进来的页码

				for (Caseinfo temp1 : allCases) {
					for (Caseinfo temp2 : allCasesfromMysql) {
						if (temp1.getCaseNum().equals(temp2.getCaseNum())
								&& temp1.getCaseName().equals(temp2.getCaseName())) {
							if (count >= (pageIndex - 1) * pageSize && count < (pageIndex - 1) * pageSize + 10) {
								logs.add(temp1);
							}
							count++;
						}

					}
				}
				total = count;
			}
		}

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
		int i = 0;
		for (Caseinfo caseinfo : logs) {
			Section section2 = new Section();
			section2.setId(Integer.parseInt(caseinfo.getSection()));
			List<Section> sections2 = sqlDao.getListfromMysql(section2);
			if (sections2.size() > 0) {
				Section section3 = sections2.get(0);
				logs.get(i).setSection(section3.getSectionName());
				i++;
			}
		}
		map.put("logs", logs);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("caseNum", caseNum);
		map.put("caseName", caseName);
		map.put("casestatus", casestatus);
		map.put("caseType", caseType);
		map.put("section", section);
		map.put("shi", shi);
		map.put("createdTime", createdTime);
		map.put("endTime", endTime);
		map.put("caseLabel", label);
		CaseType type = new CaseType();
		List<CaseType> caseTypeList = sqlDao.getListfromMysql(type);
		// 放到map中，前台遍历
		Section section2 = new Section();
		List<Section> caseSection2 = sqlDao.getListfromMysql(section2);
		map.put("caseTypeList", caseTypeList);
		map.put("caseSection2", caseSection2);
		map.put("createdTime", createdTime);

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

	@RequestMapping(value = "/getcaseone.php")
	public void getcaseone(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, IOException {
		String id = request.getParameter("id");
		Caseinfo cas = new Caseinfo();
		Caseinfo caseinfo = new Caseinfo();
		String suspectStr = "";
		cas.setId(Integer.parseInt(id));
		List<Caseinfo> casinfo = sqlDao.getListfromMysql(cas);
		String rolename22 ="";
		// 设置标记人
		HttpSession httpSession = request.getSession();
		String username11 = (String) httpSession.getAttribute("userName");

		if (casinfo.size() > 0) {
			caseinfo = casinfo.get(0);
			Section section2 = new Section();
			section2.setId(Integer.parseInt(casinfo.get(0).getSection()));
			List<Section> sections2 = sqlDao.getListfromMysql(section2);
			if (sections2.size() > 0) {
				Section section3 = sections2.get(0);
				caseinfo.setSection(section3.getSectionName());

				String mainParty = caseinfo.getMainParty();
				if (mainParty.length()>0) {
					String[] split = mainParty.split(",");
					for (String string : split) {
						if (!"".equals(string) && string !=null) {
							SuspectInfo suspectInfo = new SuspectInfo();
							suspectInfo.setId(Integer.parseInt(string));
							List<SuspectInfo> listfromMysql = sqlDao.getListfromMysql(suspectInfo);
							if (listfromMysql.size() > 0) {
								SuspectInfo suspectInfo2 = listfromMysql.get(0);
								String suspectName = suspectInfo2.getSuspectName();
								String suspectPhone = suspectInfo2.getSuspectPhone();
								String suspectMail = suspectInfo2.getSuspectMail();
								String suspectCity = suspectInfo2.getSuspectHomeAddress();
								if ("".equals(suspectStr)) {
									suspectStr += suspectName + " " + suspectPhone + " " + suspectMail + " " + suspectCity;
								} else {
									suspectStr += "/" + suspectName + " " + suspectPhone + " " + suspectMail + " "
											+ suspectCity;
								}
							}
						}
					}
				}
				/*caseinfo.setMainParty(suspectStr);      更改案件授权前的查询
				actionLog((String) session.getAttribute("userName"), "查看", "案件列表");
				JSONObject fromObject = JSONObject.fromObject(caseinfo);
				String json = fromObject.toString();
				PrintWriter writer = response.getWriter();
				writer.println(json);
				writer.close();*/
				User user =new User();
				user.setUsername(username11);
				List<User> list = sqlDao.getListfromMysql(user);

				String userroleNum = "";
				if(list.size()>0){
					User user2 = list.get(0);
					userroleNum = user2.getUserrole();
				}

				if (!"".equals(userroleNum) && userroleNum !=null) {
					Role role = new Role();
					role.setId(Integer.parseInt(userroleNum));
					List<Role> list1 = sqlDao.getListfromMysql(role);
					Role role2 = list1.get(0);
					rolename22 = role2.getRoleName();
				}
			}
		}

		int personFlags;
		if ("主任科员".equals(rolename22) || "科员".equals(rolename22)) {
			personFlags = 1;
		}else{
			personFlags = 2;
		}

		caseinfo.setMainParty(suspectStr);
		actionLog((String) session.getAttribute("userName"), "查看", "案件列表");
		JSONObject fromObject = JSONObject.fromObject(caseinfo);
		String json = fromObject.toString();


		String result_data = "{\"personFlags\":\"" + personFlags + "\",\"json\":" + json + "}";

		PrintWriter writer = response.getWriter();
		writer.write(result_data);
		writer.close();
	}

	// 修改案件 点编辑按钮取单条 显示在编辑案件页面
	@RequestMapping(value = "/admin/edit_case.php")
	public String editcase(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, IOException {

		String id = request.getParameter("id");
		Caseinfo cas = new Caseinfo();
		Caseinfo onecas = new Caseinfo();
		cas.setId(Integer.parseInt(id));
		String stu = "";
		List<Caseinfo> casinfo = sqlDao.getListfromMysql(cas);
		if (casinfo.size() > 0) {
			onecas = casinfo.get(0);
			String main = onecas.getMainParty();
			if(!"".equals(main) && main != null){


				String[] mains = main.split(",");
				if (mains !=null && !"".equals(mains)) {
					for (String mainparit : mains) {
						if (!"".equals(mainparit) && mainparit !=null) {
							SuspectInfo suspectInfo = new SuspectInfo();
							suspectInfo.setId(Integer.parseInt(mainparit));
							List<SuspectInfo> suspectInfo2 = sqlDao.getListfromMysql(suspectInfo);
							if(suspectInfo2.size()>0){
								SuspectInfo suspect = suspectInfo2.get(0);
								String SuspectName = suspect.getSuspectName();
								String SuspectPhone = suspect.getSuspectPhone();
								String SuspectMail = suspect.getSuspectMail();
								if ("".equals(stu)) {
									stu = SuspectName + " " + SuspectPhone + " " + SuspectMail;
								} else {
									stu += "/" + SuspectName + " " + SuspectPhone + " " + SuspectMail;
								}
							}
						}
					}
				}
			}
		}

		map.put("stu", stu);
		map.put("result", onecas);
		map.put("caseType", onecas.getCaseType());
		// 获得案件类型列表
		CaseType type = new CaseType();
		List<CaseType> caseTypeList = sqlDao.getListfromMysql(type);

		// request.setAttribute("caseTypeList", caseTypeList);
		map.put("caseTypeList", caseTypeList);
		return "edit_case";
	}
	// 既往案件列表  点编辑按钮取单条 显示在编辑案件页面
	@RequestMapping(value = "/admin/edit_case2.php")
	public String editcase2(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, IOException {

		String id = request.getParameter("id");
		Caseinfo cas = new Caseinfo();
		Caseinfo onecas = new Caseinfo();
		cas.setId(Integer.parseInt(id));
		String stu = "";
		List<Caseinfo> casinfo = sqlDao.getListfromMysql(cas);
		if (casinfo.size() > 0) {
			onecas = casinfo.get(0);
			String main = onecas.getMainParty();
			if(!"".equals(main) && main != null){


				String[] mains = main.split(",");
				if (mains !=null && !"".equals(mains)) {
					for (String mainparit : mains) {
						if (!"".equals(mainparit) && mainparit !=null) {
							SuspectInfo suspectInfo = new SuspectInfo();
							suspectInfo.setId(Integer.parseInt(mainparit));
							List<SuspectInfo> suspectInfo2 = sqlDao.getListfromMysql(suspectInfo);
							if(suspectInfo2.size()>0){
								SuspectInfo suspect = suspectInfo2.get(0);
								String SuspectName = suspect.getSuspectName();
								String SuspectPhone = suspect.getSuspectPhone();
								String SuspectMail = suspect.getSuspectMail();
								if ("".equals(stu)) {
									stu = SuspectName + " " + SuspectPhone + " " + SuspectMail;
								} else {
									stu += "/" + SuspectName + " " + SuspectPhone + " " + SuspectMail;
								}
							}
						}
					}
				}
			}
		}

		map.put("stu", stu);
		map.put("result", onecas);
		map.put("caseType", onecas.getCaseType());
		// 获得案件类型列表
		CaseType type = new CaseType();
		List<CaseType> caseTypeList = sqlDao.getListfromMysql(type);

		// request.setAttribute("caseTypeList", caseTypeList);
		map.put("caseTypeList", caseTypeList);
		return "edit_case2";
	}

	@RequestMapping(value = "/admin/suspect_list.php")
	public String suspectlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "suspect_list";
	}

	@RequestMapping(value = "/admin/addSuspect_page.php")
	public String addSuspectPage(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "addSuspect_page";
	}

	@RequestMapping(value = "/admin/addLabel_page.php")
	public String addLabelPage(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "addLabel";
	}

	@RequestMapping(value = "/admin/addevidence.php")
	public String addevidence(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "addevidence";
	}

	@RequestMapping(value = "/admin/partter.php")
	public String partter(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "partter";
	}

	@RequestMapping(value = "/admin/importevidence.php")
	public String importevidence(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "importevidence";
	}
	@RequestMapping(value = "/admin/importevidence2.php")
	public String importevidence2(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "importevidence2";
	}

	@RequestMapping(value = "/admin/importevidence_newcase.php")
	public String importevidence_newcase(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "importevidence_newcase";
	}

	@RequestMapping(value = "/admin/newcase_success.php")
	public String newcase_success(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "case_list";
	}
	// 添加日志
	@RequestMapping(value = "/addLogs.php")
	public String addLogs(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {
		response.setContentType("text/html;charset=utf-8");
		String version = request.getParameter("version");
		String addcomment=request.getParameter("addcomment");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = sdf.format(date);

		UpdateLog updatelog=new UpdateLog();
		updatelog.setVersion(version);
		updatelog.setUpdateContent(addcomment);
		updatelog.setUpdateTime(time);
		sqlDao.setBeanToMysql(updatelog);
		actionLog((String) session.getAttribute("userName"), "新增", "标签管理");
		map.put("updatelog", updatelog);
		map.put("time", time);
		session.setAttribute("updatelog", map);
		return "updatalog";
	}
	//新增日志
	@RequestMapping(value = "/admin/addLog.php")
	public String addLog(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "addLog";
	}
	// 删除嫌疑人
	@RequestMapping(value = "/admin/delSuspect_page.php")
	public void delSuspect_page(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {

		String checkbox=request.getParameter("checkbox");
		String[] s=checkbox.split(",");
		//			SuspectInfo suspect=new SuspectInfo();
		//			suspect.setId(Integer.parseInt(s[0]));

		//sqlDao.deletefromMysql(suspect);
		for (int i=0;i<s.length;i++) {
			SuspectInfo suspect=new SuspectInfo();
			suspect.setId(Integer.parseInt(s[i]));
			sqlDao.deletefromMysql(suspect);
			actionLog((String)session.getAttribute("userName"),"删除","嫌疑人管理");
		}

		PrintWriter pw=null;
		try{
			pw=response.getWriter();
			pw.write("{\"res\":\"succ\"}");
			pw.flush();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}

		/*String lab = request.getParameter("lab");
			Label labe = new Label();
			labe.setId(Integer.parseInt(lab));
			sqlDao.deletefromMysql(labe);
			actionLog((String) session.getAttribute("userName"), "删除", "标签管理");
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
			}*/
	}
	// 按条件查询标签
	@RequestMapping(value = "/searchByConditOfLabel.php")
	public String searchByConditOfLabel(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {

		String pageno = request.getParameter("pageno");
		String label = request.getParameter("label");

		int pageIndex = 1;
		int pageSize = 30;
		int num = 0;
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		Label lab = new Label();
		if (!StringUtils.isEmpty(label)) {
			lab.setLabel(label);
		}

		List<Label> logs = sqlDao.getOrderListfromMysqlLike(lab, (pageIndex - 1) * pageSize, pageSize, "id");
		actionLog((String) session.getAttribute("userName"), "搜索标签："+label, "标签管理");
		int total = sqlDao.getcountfromMysqlLike(lab);
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
		map.put("logs", logs);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("label", label);

		return "label_list";
	}

	// 删除标签
	@RequestMapping(value = "/admin/delCase.php")
	public void delCase(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {
		String lab = request.getParameter("lab");
		Label labe = new Label();
		labe.setId(Integer.parseInt(lab));
		sqlDao.deletefromMysql(labe);
		actionLog((String) session.getAttribute("userName"), "删除标签："+labe, "标签管理");
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

	// 标签详情信息显示
	@RequestMapping(value = "/getLabelone.php")
	public void getLabelone(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, IOException {

		String id = request.getParameter("id");
		Label lab = new Label();
		lab.setId(Integer.parseInt(id));
		List<Label> list = sqlDao.getListfromMysql(lab);
		actionLog((String) session.getAttribute("userName"), "查看", "标签管理");
		JSONObject fromObject = JSONObject.fromObject(list.get(0));
		String json = fromObject.toString();
		PrintWriter writer = response.getWriter();
		writer.println(json);
		writer.close();
	}

	// 修改标签 点编辑按钮取单条信息
	@RequestMapping(value = "/editLabel.php")
	public String editLabel(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, IOException {

		String id = request.getParameter("id");
		Label lab = new Label();
		lab.setId(Integer.parseInt(id));
		List<Label> list = sqlDao.getListfromMysql(lab);
		Label onelab = list.get(0);
		request.setAttribute("result", onelab);

		return "edit_label";
	}

	// 添加标签
	@RequestMapping(value = "/addLabel.php")
	public String addLabel(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {
		response.setContentType("text/html;charset=utf-8");
		String label = request.getParameter("label2");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = sdf.format(date);

		Label lab = new Label();
		lab.setLabel(label);
		lab.setTime(time);
		sqlDao.setBeanToMysql(lab);
		actionLog((String) session.getAttribute("userName"), "新增标签："+label, "标签管理");
		map.put("label", label);
		map.put("time", time);
		session.setAttribute("lab", map);
		return "newlabel_success";
	}

	// 收藏弹出框 新建标签
	@RequestMapping(value = "admin/addFavoLabel.php")
	public void addFavoLabel(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {
		response.setContentType("text/html;charset=utf-8");
		String favoLabelName = request.getParameter("favoLabelName");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = sdf.format(date);

		Label lab = new Label();
		lab.setLabel(favoLabelName);
		lab.setTime(time);
		sqlDao.setBeanToMysql(lab);
		actionLog((String) session.getAttribute("userName"), "收藏", "收藏标签");
		map.put("label", favoLabelName);
		map.put("time", time);

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

	// 编辑标签信息
	@RequestMapping(value = "/editLabelComFirm.php")
	public String editLabelComFirm(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
		response.setContentType("text/html;charset=utf-8");
		String labid = request.getParameter("labid");

		String label = request.getParameter("label");

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date date = new Date();
		// String time = sdf.format(date);// 创建时间
		Label lab = new Label();
		lab.setId(Integer.parseInt(labid));
		lab.setLabel(label);
		// lab.setTime(time);
		sqlDao.updateToMysql(lab);
		actionLog((String) session.getAttribute("userName"), "编辑", "案件管理");
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
		return "label_list";
	}

	// 查询所有的label
	@RequestMapping(value = "/admin/label_list.php")
	public String getLabellist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String pageno = request.getParameter("pageno");
		String label = request.getParameter("label");
		String time = request.getParameter("time");

		int pageIndex = 1;
		int pageSize = 30;
		int num = 0;

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		Label lab = new Label();

		if (!StringUtils.isEmpty(label)) {
			lab.setLabel(label);
		}
		if (!StringUtils.isEmpty(time)) {
			lab.setTime(time);
		}

		List<Label> logs = sqlDao.getOrderListfromMysqlLike(lab, (pageIndex - 1) * pageSize, pageSize, "id");
		int total = sqlDao.getcountfromMysqlLike(lab);
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

		map.put("logs", logs);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("label", label);
		map.put("time", time);
		// request.setAttribute("lab", map);
		return "label_list";
	}

	// 新建案件
	@RequestMapping(value = "/addcase.php")
	public String addNewCase(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws UnknownHostException {
		response.setContentType("text/html;charset=utf-8");
		// String userName = (String) session.getAttribute("userName");
		String caseNum = request.getParameter("caseNum");// 案件编号
		String caseName = request.getParameter("caseName");// 案件名称
		String loc_province = request.getParameter("province");// sheng
		String loc_city = request.getParameter("city");// shi
		String loc_town = request.getParameter("town");// qu
		String acceptTime = request.getParameter("acceptTime");
		HttpSession httpSession = request.getSession();
		String userName = (String) httpSession.getAttribute("userName");
		String section = (String) httpSession.getAttribute("section");
		String label = request.getParameter("caselabel");// 标签
		String mainParty = request.getParameter("mainParty");// 主要当事人
		String relatedCompany = request.getParameter("relatedCompany");// 涉嫌单位
		String caseType = request.getParameter("caseType");// 涉嫌单位
		String relatedObject = request.getParameter("relatedObject");// 涉案物品
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 创建时间
		Date date = new Date();
		String createdTime = sdf.format(date);
		// String res = "succ";
		// String res = "succ";
		String mainParty2 = null;
		// 添加嫌疑人
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
				if (spu.indexOf("@")>0) {
					email=spu;
				}else{
					number = spu;
				}
			}

			if (split.length > 2) {
				//				email = split[2];

				String spu2 = split[2];
				if (spu2.indexOf("@")>0) {
					email=spu2;
				}else{
					number = spu2;
				}

			}
			if ((!"".equals(name) && name!= null) || (!"".equals(number) && number!= null) || (!"".equals(email) && email != null)) {
				SuspectInfo suspectInfo = new SuspectInfo();
				suspectInfo.setSuspectName(name);
				suspectInfo.setSuspectPhone(number);
				suspectInfo.setSuspectMail(email);
				suspectInfo.setLabel(label);
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
					List<SuspectInfo> listfromMysql2 = sqlDao.getListfromMysql(suspectInfo);
					int id = listfromMysql2.get(0).getId();
					if (mainParty2 == null) {
						mainParty2 = "" + id;
					} else {
						mainParty2 += "," + id;
					}
				}
			}
		}
		User user = (User) session.getAttribute("user");

		Caseinfo cas = new Caseinfo();
		cas.setCaseNum(caseNum);
		cas.setCaseName(caseName);
		cas.setCaseType(caseType);
		cas.setLabel(label);
		cas.setStatus("办案中");
		cas.setMainParty(mainParty2);
		cas.setRelatedCompany(relatedCompany);
		cas.setRelatedObject(relatedObject);
		cas.setSheng(loc_province);
		cas.setShi(loc_city);
		cas.setQu(loc_town);
		cas.setAllArea(loc_province+loc_city+loc_town);
		cas.setAcceptTime(acceptTime);
		cas.setCreatedTime(createdTime);
		cas.setUserName(userName);
		String sectionsName = null;
		Section sections2 = new Section();
		if (!"".equals(section) && section != null) {
			sections2.setId(Integer.parseInt(section));
			List<Section> listfromMysqls = sqlDao.getListfromMysql(sections2);
			if (listfromMysqls.size() > 0) {
				Section sections3 = listfromMysqls.get(0);
				sectionsName = sections3.getSectionName();
			}
		}
		cas.setSection(section);
		sqlDao.setBeanToMysql(cas);
		actionLog((String) httpSession.getAttribute("userName"), "新增"+ caseName, "新建案件");
		writeLog( userName,"添加",userName + " 添加案件" + caseName);
		Caseinfo cas0 = new Caseinfo();
		List<Caseinfo> cas0s = sqlDao.getListfromMysql(cas);
		if (cas0s != null && cas0s.size() > 0) {
			cas0 = cas0s.get(0);
		}
		final int caseID = cas0.getId();

		map.put("caseID", caseID);
		map.put("caseNum", caseNum);
		map.put("caseName", caseName);
		map.put("caseType", caseType);
		map.put("section", sectionsName);
		map.put("userName", userName);
		map.put("caselabel", label);
		map.put("mainParty", mainParty);
		map.put("relatedCompany", relatedCompany);
		map.put("acceptTime", acceptTime);
		map.put("relatedObject", relatedObject);
		map.put("loc_city", loc_city);
		session.setAttribute("cas1", map);

		return "newcase_success";

	}

	// 上传文件到hdfs
	@RequestMapping(value = "/addNewCase.php")
	public void addNewCase(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
		response.setContentType("text/html;charset=utf-8");
		final String userName = (String) session.getAttribute("userName");
		String section = (String) session.getAttribute("section");
		User user = (User) session.getAttribute("user");
		final String addcaseid = request.getParameter("caseid");
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
		session.setAttribute("evName", evName);
		final String uuid = request.getParameter("evUUID");
		session.setAttribute("evUUID", uuid);
		// 证据名称编码
		// String oevName = evName;
		// if (!StringUtils.isEmpty(evName)) {
		// try {
		// evName = URLEncoder.encode(evName, "UTF-8");
		// evName = evName.replace("%", "X_L");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		final String collectionName = evName;
		final String hdfsPath = "/tmp/emaildata/" + addcaseid;//去除evname
		String res = "{}";

		String evType = request.getParameter("evType");// 数据类型
		String comment = request.getParameter("comment");// 证据描述
		String evAdmin = userName;
		// 管理人
		/*
		 * String evAdmin = request.getParameter("evAdmin");// 管理人
		 */ String tempPath = request.getParameter("dirPath");// 文件夹路径
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
			 if (!StringUtils.isEmpty(tempPath)) {
				 tempPath = "/emaildata/" + evName;
			 }
			 uptype = 1;
		 } else if (fangshi.equals("选择本地文件上传")) {
			 if (!StringUtils.isEmpty(tempPath)) {
				 int inx = tempPath.lastIndexOf("\\");
				 tempPath = tempPath.substring(inx + 1);
				 tempPath = "/emaildata/" + tempPath;
			 }
			 uptype = 0;
		 }
		 final String dirPath = tempPath;

		 Evidence evi = new Evidence();
		 evi.setCaseID(Integer.parseInt(addcaseid));

		 final String filepath = "/emaildata/" + addcaseid + "/" + addcaseid + uuid.substring(0, 10);
		 evi.setEvType(Integer.parseInt(evType));
		 evi.setDataTypes(Integer.parseInt(dataTypes));
		 evi.setComment(comment);
		 //evi.setDirPath("/tmp/emaildata/"+addcaseid + uuid.substring(0, 10)+".har");//存放hdfs的xxx.har的文件路径
		 if("6".equals(evType)){
			 evi.setDirPath(filepath);
		 }else{
			 evi.setDirPath("/tmp/emaildata/"+addcaseid + uuid.substring(0, 10)+".har");//存放hdfs的xxx.har的文件路径
		 }
		 //evi.setDirPath(filepath);
		 evi.setEvAdmin(evAdmin);
		 evi.setUptype(uptype);
		 evi.setEvName(collectionName);
		 evi.setUploadNum(filelength);
		 evi.setSuccessNum("0");
		 evi.setErrorNum(filelength);
		 evi.setFinished("true");
		 evi.setStatus("on");
		 evi.setAddTime(dateFormat.format(new Date()));
		 evi.setStartSolrTime(evi.getAddTime());
		 evi.setCurrFlag("1");
		 evi.setUUID(addcaseid + uuid.substring(0, 10));
		 sqlDao.setBeanToMysql(evi);
		 actionLog(evAdmin, "新增", "案件列表 添加证据" + collectionName);
		 writeLog(evAdmin,"添加", evAdmin + " 添加证据" + collectionName);
		 Evidence eviTemp = new Evidence();
		 eviTemp.setUUID(addcaseid + uuid.substring(0, 10));
		 List<Evidence> eviTemps = sqlDao.getListfromMysql(eviTemp);
		 if (eviTemps != null && eviTemps.size() > 0) {
			 eviTemp = eviTemps.get(0);
		 }
		 final int eviTempID = eviTemp.getId();
		 session.setAttribute("eviTempID", eviTempID);

		 class Runs implements Runnable {
			 @Override
			 public void run() {
				 String copyLocal = "hadoop fs -copyFromLocal " + filepath + " " + hdfsPath;
				 String mkdir = "hadoop fs -mkdir -p " + hdfsPath;
				 //				String archive="hadoop archive -archiveName "+addcaseid + uuid.substring(0, 10)+".har -p "+hdfsPath+" "+"/tmp/emaildata/";
				 //				String deleteFile="hadoop fs -rm -R "+hdfsPath;
				 Process process;
				 try {
					 logger.info("<============================================>");
					 logger.info("<=========mkdir:" + mkdir + "==========>");
					 logger.info("<=========unzipCmd:" + copyLocal + "==========>");
					 logger.info("<============================================>");
					 process = Runtime.getRuntime().exec(mkdir);
					 process.waitFor();
					 process = Runtime.getRuntime().exec(copyLocal);
					 process.waitFor();
					 //					process = Runtime.getRuntime().exec(archive);
					 //					process.waitFor();
					 //					process = Runtime.getRuntime().exec(deleteFile);
					 // InputStream isinfo = process.getInputStream();

					 // 为错误输出流单独再开一个线程
					 // Thread t = new Thread(new
					 // InputStreamRunnable(process.getErrorStream(),"ErrorStream"));
					 // t.start();

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
					 // logger.info("unzip done--------------");
					 Evidence evidence = new Evidence();
					 evidence.setId(eviTempID);
					 List<Evidence> evidences = sqlDao.getListfromMysql(evidence);
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
		 res = "{\"evID\":\"" + eviTempID + "\",\"res\":\"succ\",\"UUcaseid\":\"" + addcaseid + uuid.substring(0, 10) + "\"}";
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

	// 点提交按钮 编辑案件
	@RequestMapping(value = "/editcaseconfirm.php")
	public String editcasecomfirm(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
		response.setContentType("text/html;charset=utf-8");
		String caseid = request.getParameter("caseid");// 案件id
		String userName = (String) session.getAttribute("userName");
		String caseNum = request.getParameter("caseNum");
		String caseName = request.getParameter("caseName");
		String caseType = request.getParameter("caseType");
		String sheng = request.getParameter("sheng");
		String shi = request.getParameter("shi");
		String qu = request.getParameter("qu");

		String mainParty = request.getParameter("mainParty");// 主要当事人
		String relatedCompany = request.getParameter("relatedCompany");// 涉嫌单位
		String relatedObject = request.getParameter("relatedObject");// 涉案物品
		String label = request.getParameter("label");// 标签
		String acceptTime = request.getParameter("acceptTime");
		String status = request.getParameter("status");

		// 添加嫌疑人
		String mainParty2 = "";
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
				//				number = split[1];
				String spu = split[1];
				if (spu.indexOf("@")>0) {
					email=spu;
				}else{
					number = spu;
				}
			}
			if (split.length > 2) {
				//				email = split[2];
				String spu2 = split[2];
				if (spu2.indexOf("@")>0) {
					email=spu2;
				}else{
					number = spu2;
				}
			}

			if (name!= null && !"".equals(name)) {
				SuspectInfo suspectInfo = new SuspectInfo();

				suspectInfo.setSuspectName(name);
				suspectInfo.setSuspectPhone(number);
				suspectInfo.setSuspectMail(email);
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
					actionLog((String) session.getAttribute("userName"), "新增", "案件列表"+ caseName);
					List<SuspectInfo> listfromMysql2 = sqlDao.getListfromMysql(suspectInfo);
					int id = listfromMysql2.get(0).getId();

					if (mainParty2 == null) {
						mainParty2 = "" + id;
					} else {
						mainParty2 += "," + id;
					}
				}
			}
		}
		if(mainParty2.length()>1){
			String substring = mainParty2.substring(0, 1);
			if(",".equals(substring)){
				mainParty2=	mainParty2.substring(1, mainParty2.length());
			}
		}



		Caseinfo cas = new Caseinfo();
		cas.setId(Integer.parseInt(caseid));
		cas.setCaseNum(caseNum);
		cas.setCaseName(caseName);
		cas.setCaseType(caseType);
		cas.setSheng(sheng);
		cas.setShi(shi);
		cas.setQu(qu); 
		cas.setMainParty(mainParty2);
		cas.setRelatedCompany(relatedCompany);
		cas.setRelatedObject(relatedObject);
		cas.setLabel(label);
		cas.setAcceptTime(acceptTime);

		cas.setStatus(status);
		sqlDao.updateToMysql(cas);
		actionLog((String) session.getAttribute("userName"), "编辑", "案件列表:"+ caseName);
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
		return "case_list";
	}

	// 查询所有嫌疑人信息
	@RequestMapping(value = "/getSuspectlist.php")
	public String getSuspectlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {

		String pageno = request.getParameter("pageno");
		String createTime = request.getParameter("createTime");// 创建日期
		String suspectName = request.getParameter("suspectName");// 嫌疑人姓名
		String suspectPhone = request.getParameter("suspectPhone");// 嫌疑人手机号
		String suspectMail = request.getParameter("suspectMail");// 嫌疑人邮箱
		String suspectQQ = request.getParameter("suspectQQ");// 嫌疑人QQ
		String suspectIDCardNumber = request.getParameter("suspectIDCardNumber");
		String suspectPassport = request.getParameter("suspectPassport");
		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		SuspectInfo si = new SuspectInfo();
		if (!StringUtils.isEmpty(createTime)) {
			si.setCreateTime(createTime);
		}

		if (!StringUtils.isEmpty(suspectName)) {
			si.setSuspectName(suspectName);
		}

		if (!StringUtils.isEmpty(suspectPhone)) {
			si.setSuspectPhone(suspectPhone);
		}

		if (!StringUtils.isEmpty(suspectMail)) {
			si.setSuspectMail(suspectMail);
		}

		if (!StringUtils.isEmpty(suspectQQ)) {
			si.setSuspectQQ(suspectQQ);
		}

		if (!StringUtils.isEmpty(suspectIDCardNumber)) {
			si.setSuspectIDCardNumber(suspectIDCardNumber);
		}

		if (!StringUtils.isEmpty(suspectPassport)) {
			si.setSuspectPassport(suspectPassport);
		}

		List<SuspectInfo> logs = sqlDao.getOrderListfromMysqlLike(si, (pageIndex - 1) * pageSize, pageSize, "id");
		actionLog((String) session.getAttribute("userName"), "查看", "嫌疑人管理");
		int total = sqlDao.getcountfromMysqlLike(si);
		List<SuspectInfo> listfromMysql = sqlDao.getListfromMysql(si);


		//权限
		User user = (User) session.getAttribute("user");
		Caseinfo cas = new Caseinfo();
		String roleName="" ;
		if(user != null){
			String userrole = user.getUserrole();
			Role role = new Role();
			role.setId(Integer.parseInt(userrole));
			List<Role> roleList = sqlDao.getListfromMysql(role);
			if(roleList.size()>0){
				Role role2 = roleList.get(0);
				roleName = role2.getDataScope();
			}
		}
		String department ="";
		String section ="";
		String suoyou ="";
		if (roleName.equals("科室数据") ) {// 科长
			cas.setSection(user.getSection()+"/"+user.getUsername());
			section=user.getSection();
		}else if (roleName.equals("部门数据") ) {// 部长
			cas.setDepartment(user.getPartment());
			department= user.getPartment();
		} else if (roleName.equals("个人数据") ) {// 科员
			cas.setUserName(user.getUsername());
		} else if (roleName.equals("所有数据")) {// 局长
			cas.setId(-1);
			suoyou="suoyou";
		} else {
			cas.setId(-2);
		}
		//int caseflag = 0;
		List<SuspectInfo> logs2 = new ArrayList<SuspectInfo>();
		List<Caseinfo> casList = sqlDao.getListfromMysqlLikeOR(cas);
		for (Caseinfo caseinfo : casList) {
			String mainParty = caseinfo.getMainParty();
			if(mainParty!=null && !"".equals(mainParty)){
				String[] split = mainParty.split(",");
				for (String string : split) {
					if(string!=null && !"".equals(string)){
						int caseSuspId = Integer.parseInt(string);
						for (SuspectInfo suspectInfo3 : listfromMysql) {
							int id = suspectInfo3.getId();
							String createPerson = suspectInfo3.getCreatePerson();
							String userid = user.getId()+"";
							if (caseSuspId == id) {
								int flag1 = 0;
								for (SuspectInfo string3 : logs2) {
									int id2 = string3.getId();
									if(suspectInfo3.getId()==id2){
										flag1 = 1;
									}
								}if(flag1==0){
									logs2.add(suspectInfo3);
								}


							}else if(userid.equals(createPerson)){

								int flag2 = 0;
								for (SuspectInfo string4 : logs2) {
									int id4 = string4.getId();
									if(suspectInfo3.getId()==id4){
										flag2 = 1;
									}
								}if(flag2==0){
									logs2.add(suspectInfo3);
								}
								//logs2.add(suspectInfo3);
							}else if(!"".equals(department) || !"".equals(section) || !"".equals(suoyou)){
								int flag = 0;
								for (SuspectInfo string2 : logs2) {
									int id2 = string2.getId();

									if(id2==suspectInfo3.getId()){
										flag=1;
									}
								}
								if(flag==0){																
									String section2 = suspectInfo3.getSection();
									String department2 = suspectInfo3.getDepartment();
									String partment3 = user.getPartment();
									String section3 = user.getSection();
									if(!"".equals(department)){
										if(partment3.equals(department2)){
											logs2.add(suspectInfo3);
										}
									}
									if(!"".equals(section)){
										if(section3.equals(section2)){
											logs2.add(suspectInfo3);
										}
									}
									if(!"".equals(suoyou)){
										logs2.add(suspectInfo3);
									}
								}

							}
						}
					}

				}
			}
		}
		int size = logs2.size();//总数
		List<SuspectInfo> logs3 = new ArrayList<SuspectInfo>();

		int iflag=0;
		for (SuspectInfo suspectInfo : logs2) {
			int qishi = (pageIndex-1)*10;
			int jieshu = (pageIndex)*10;
			if(iflag>=qishi && iflag <jieshu){
				logs3.add(suspectInfo);
			}
			iflag++;
		}


		num = size / pageSize;
		if (size % pageSize != 0) {
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

		map.put("logs", logs3);
		//map.put("totalNum", total);
		map.put("totalNum", size);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("createTime", createTime);
		map.put("suspectName", suspectName);
		map.put("suspectPhone", suspectPhone);
		map.put("suspectMail", suspectMail);
		map.put("suspectQQ", suspectQQ);
		map.put("suspectIDCardNumber", suspectIDCardNumber);
		map.put("suspectPassport", suspectPassport);
		request.setAttribute("si", map);
		return "suspect_list";
	}

	// 新增嫌疑人信息
	@RequestMapping(value = "/addSuspectlist.php")
	public String addSuspectlist(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws UnknownHostException {
		response.setContentType("text/html;charset=utf-8");
		String suspectName = request.getParameter("suspectName");// 嫌疑人姓名
		String suspectSex = request.getParameter("suspectSex");// 嫌疑人性别
		String label = request.getParameter("caselabel");
		// Integer suspectSex = Integer.parseInt(isuspectSex);
		String suspectPhone = request.getParameter("suspectPhone");// 嫌疑人手机号
		String suspectMail = request.getParameter("suspectMail");// 嫌疑人邮箱
		String suspectQQ = request.getParameter("suspectQQ");// 嫌疑人QQ
		String suspectHomeAddress = request.getParameter("suspectHomeAddress");
		String suspectUnitName = request.getParameter("suspectUnitName");
		String suspectUnitAddress = request.getParameter("suspectUnitAddress");
		String suspectProvince = request.getParameter("province");
		String suspectCity = request.getParameter("city");
		String suspectTown = request.getParameter("town");
		String suspectIDCardNumber = request.getParameter("suspectIDCardNumber");
		String suspectSocialSecurity = request.getParameter("suspectSocialSecurity");
		String suspectPassport = request.getParameter("suspectPassport");
		String suspectMicroletters = request.getParameter("suspectMicroletters");
		String suspectFacebook = request.getParameter("suspectFacebook");
		String suspectTwitter = request.getParameter("suspectTwitter");
		String rests = request.getParameter("rests");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String createTime = sdf.format(date);

		SuspectInfo si = new SuspectInfo();
		si.setLabel(label);
		si.setSuspectName(suspectName);
		si.setSuspectSex(suspectSex);
		si.setSuspectPhone(suspectPhone);
		si.setSuspectMail(suspectMail);
		si.setSuspectHomeAddress(suspectHomeAddress);
		si.setSuspectQQ(suspectQQ);
		si.setSuspectUnitAddress(suspectUnitAddress);
		si.setSuspectUnitName(suspectUnitName);
		si.setCreateTime(createTime);
		si.setSuspectProvince(suspectProvince);
		si.setSuspectCity(suspectCity);
		si.setSuspectTown(suspectTown);
		si.setSuspectIDCardNumber(suspectIDCardNumber);
		si.setSuspectSocialSecurity(suspectSocialSecurity);
		si.setSuspectPassport(suspectPassport);
		si.setSuspectMicroletters(suspectMicroletters);
		si.setSuspectFacebook(suspectFacebook);
		si.setSuspectTwitter(suspectTwitter);
		si.setRests(rests);
		User user = (User) session.getAttribute("user");
		si.setCreatePerson(user.getId()+"");
		si.setDepartment(user.getPartment());
		si.setSection(user.getSection());
		/*
		 * si.setSuspectProvince(suspectProvince);
		 * si.setSuspectCity(suspectCity); si.setSuspectTown(suspectTown);
		 */
		sqlDao.setBeanToMysql(si);
		actionLog((String) session.getAttribute("userName"), "新增", "嫌疑人:"+suspectName);
		map.put("suspectName", suspectName);
		map.put("suspectSex", suspectSex);
		map.put("suspectPhone", suspectPhone);
		map.put("suspectMail", suspectMail);
		map.put("suspectQQ", suspectQQ);
		map.put("suspectHomeAddress", suspectHomeAddress);
		map.put("suspectUnitName", suspectUnitName);
		map.put("suspectUnitAddress", suspectUnitAddress);
		map.put("suspectProvince", suspectProvince);
		map.put("suspectCity", suspectCity);
		map.put("suspectTown", suspectTown);
		map.put("suspectIDCardNumber", suspectIDCardNumber);
		map.put("suspectSocialSecurity", suspectSocialSecurity);
		map.put("suspectPassport", suspectPassport);
		map.put("suspectMicroletters", suspectMicroletters);
		map.put("suspectFacebook", suspectFacebook);
		map.put("suspectTwitter", suspectTwitter);
		session.setAttribute("si", map);

		return "newsuspect_success";
	}

	/*
	 * // 按条件查询嫌疑人
	 * 
	 * @RequestMapping(value = "/searchByConditOfSuspect.php") public String
	 * searchByConditOfSuspect(HttpServletRequest request, Map<String, Object>
	 * map, HttpSession session, HttpServletResponse response) throws
	 * SecurityException, IllegalArgumentException, NoSuchMethodException,
	 * IllegalAccessException, InvocationTargetException {
	 * 
	 * String pageno = request.getParameter("pageno"); String suspectName =
	 * request.getParameter("suspectName"); String suspectPhone =
	 * request.getParameter("suspectPhone"); String suspectMail =
	 * request.getParameter("suspectMail"); String suspectQQ =
	 * request.getParameter("suspectQQ"); String suspectIDCardNumber =
	 * request.getParameter("suspectIDCardNumber"); String suspectPassport =
	 * request.getParameter("suspectPassport");
	 * 
	 * int pageIndex = 1; int pageSize = 10; int num = 0; if
	 * (!StringUtils.isEmpty(pageno)) { pageIndex = Integer.parseInt(pageno); }
	 * 
	 * SuspectInfo si = new SuspectInfo(); if
	 * (!StringUtils.isEmpty(suspectName)) { si.setSuspectName(suspectName); }
	 * if (!StringUtils.isEmpty(suspectPhone)) {
	 * si.setSuspectPhone(suspectPhone); } if
	 * (!StringUtils.isEmpty(suspectMail)) { si.setSuspectMail(suspectMail); }
	 * if (!StringUtils.isEmpty(suspectQQ)) { si.setSuspectQQ(suspectQQ); } if
	 * (!StringUtils.isEmpty(suspectIDCardNumber)) {
	 * si.setSuspectIDCardNumber(suspectIDCardNumber); } if
	 * (!StringUtils.isEmpty(suspectPassport)) {
	 * si.setSuspectPassport(suspectPassport); } List<SuspectInfo> logs =
	 * sqlDao.getOrderListfromMysqlLike(si, (pageIndex - 1) * pageSize,
	 * pageSize, "id"); int total = sqlDao.getcountfromMysqlLike(si); num =
	 * total / pageSize; if (total % pageSize != 0) { num++; }
	 * 
	 * List<Integer> pageList = new ArrayList<Integer>(); if (num < 5) { for
	 * (int i = 1; i <= num; i++) { pageList.add(i); } } else { if (pageIndex <=
	 * 5) { for (int i = 1; i <= 5; i++) { pageList.add(i); } } else { if
	 * (pageIndex + 2 <= num) { pageList.add(pageIndex - 2);
	 * pageList.add(pageIndex - 1); pageList.add(pageIndex);
	 * pageList.add(pageIndex + 1); pageList.add(pageIndex + 2); } else if
	 * (pageIndex + 1 <= num) { pageList.add(pageIndex - 3);
	 * pageList.add(pageIndex - 2); pageList.add(pageIndex - 1);
	 * pageList.add(pageIndex); pageList.add(pageIndex + 1); } else {
	 * pageList.add(pageIndex - 4); pageList.add(pageIndex - 3);
	 * pageList.add(pageIndex - 2); pageList.add(pageIndex - 1);
	 * pageList.add(pageIndex); } } } map.put("logs", logs); map.put("totalNum",
	 * total); map.put("totalPages", num); map.put("nowPage", pageIndex);
	 * map.put("pageList", pageList); map.put("suspectName", suspectName);
	 * map.put("suspectPhone", suspectPhone); map.put("suspectMail",
	 * suspectMail); return "suspect_list"; }
	 */

	// 嫌疑人详情信息显示

	@RequestMapping(value = "/getSuspectOne.php")
	public void getSuspectOne(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, IOException {

		String id = request.getParameter("id");

		List<Caseinfo> caseinfos5 = new ArrayList<Caseinfo>();
		SuspectInfo si = new SuspectInfo();
		si.setId(Integer.parseInt(id));
		List<SuspectInfo> siInfo = sqlDao.getListfromMysql(si);
		SuspectInfo suspectInfo2 = siInfo.get(0);
		SuspectInfo suspectInfo = siInfo.get(0);

		String suspectPhone = suspectInfo.getSuspectPhone();
		String suspectMail = suspectInfo.getSuspectMail();
		Caseinfo caseinfo = new Caseinfo();

		// caseinfo.setMainParty(id);
		List<Caseinfo> caseinfos = sqlDao.getListfromMysqlLike(caseinfo);
		for (Caseinfo caseinfo2 : caseinfos) {
			String mainParty = caseinfo2.getMainParty();
			String[] split = mainParty.split(",");
			for (String string : split) {
				if (id.equals(string)) {
					String Section1 = caseinfo2.getSection();
					Section section2 = new Section();
					section2.setId(Integer.parseInt(Section1));
					List<Section> roles2 = sqlDao.getListfromMysql(section2);
					if (roles2.size() > 0) {
						Section roles3 = roles2.get(0);
						caseinfo2.setSection(roles3.getSectionName());
					}
					caseinfos5.add(caseinfo2);
				}
			}
		}

		Caseinfo caseinfo3 = new Caseinfo();
		caseinfo3.setMainParty(suspectMail);
		List<Caseinfo> caseinfos3 = sqlDao.getListfromMysqlLike(caseinfo3);
		actionLog((String) session.getAttribute("userName"), "查看", "人物画像");
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("json", suspectInfo2);
		hashMap.put("caseinfos", caseinfos5);
		hashMap.put("caseinfos2", caseinfos3);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(hashMap));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 修改嫌疑人 点编辑按钮取单条信息
	@RequestMapping(value = "/editSuspect.php")
	public String editSuspect(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, IOException {

		String id = request.getParameter("id");
		SuspectInfo si = new SuspectInfo();
		si.setId(Integer.parseInt(id));
		List<SuspectInfo> siInfo = sqlDao.getListfromMysql(si);

		SuspectInfo onesi = siInfo.get(0);
		request.setAttribute("result", onesi);
		return "edit_suspect";
	}

	// 编辑人物画像
	@RequestMapping(value = "/editSuspectComFirm.php")
	public String editSuspectComFirm(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("suspectId");
		String suspectName = request.getParameter("suspectName");// 嫌疑人姓名
		String suspectSex = request.getParameter("suspectSex");// 嫌疑人性别
		String label = request.getParameter("caselabel");
		// Integer suspectSex = Integer.parseInt(isuspectSex);
		String suspectPhone = request.getParameter("suspectPhone");// 嫌疑人手机号
		String suspectMail = request.getParameter("suspectMail");// 嫌疑人邮箱
		String suspectQQ = request.getParameter("suspectQQ");// 嫌疑人QQ
		String suspectHomeAddress = request.getParameter("suspectHomeAddress");// 嫌疑人户口所在地区
		String suspectUnitName = request.getParameter("suspectUnitName");// 嫌疑人单位名称
		String suspectUnitAddress = request.getParameter("suspectUnitAddress");// 嫌疑人单位地址
		String suspectIDCardNumber = request.getParameter("suspectIDCardNumber");
		String suspectSocialSecurity = request.getParameter("suspectSocialSecurity");
		String suspectPassport = request.getParameter("suspectPassport");
		String suspectMicroletters = request.getParameter("suspectMicroletters");
		String suspectFacebook = request.getParameter("suspectFacebook");
		String suspectTwitter = request.getParameter("suspectTwitter");
		String caselabel = request.getParameter("caselabel");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String createTime = sdf.format(date);// 创建时间
		SuspectInfo si = new SuspectInfo();
		si.setLabel(label);
		si.setId(Integer.parseInt(id));
		si.setSuspectName(suspectName);
		si.setSuspectSex(suspectSex);
		si.setSuspectPhone(suspectPhone);
		si.setSuspectMail(suspectMail);
		si.setSuspectQQ(suspectQQ);
		si.setSuspectHomeAddress(suspectHomeAddress);
		si.setSuspectUnitAddress(suspectUnitAddress);
		si.setSuspectUnitName(suspectUnitName);
		si.setCreateTime(createTime);
		si.setSuspectIDCardNumber(suspectIDCardNumber);
		si.setSuspectSocialSecurity(suspectSocialSecurity);
		si.setSuspectPassport(suspectPassport);
		si.setSuspectMicroletters(suspectMicroletters);
		si.setSuspectFacebook(suspectFacebook);
		si.setSuspectTwitter(suspectTwitter);
		si.setLabel(caselabel);
		sqlDao.updateToMysql(si);
		actionLog((String) session.getAttribute("userName"), "编辑", "人物画像"+suspectName);
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
		return "suspect_list";
	}

	// 批量导入人物画像
	@RequestMapping(value = "/exSuspectlist.php")
	public String exSuspectlist(MultipartFile fileLoad, HttpServletRequest request, Map<String, Object> map,
			HttpServletResponse response, HttpSession session) throws IOException {
		if (fileLoad.getSize() > 0) {
			// 得到项目在服务器的真实根路径，如：/home/tomcat/webapp/项目名/
			String path = session.getServletContext().getRealPath("");
			String fileName = fileLoad.getOriginalFilename();
			File file = new File(path, fileName);
			fileLoad.transferTo(file);
			List<List<Object>> lists = ReadExcel.readExcel(file);
			int i = 0;
			List<SuspectInfo> suBeans = new ArrayList<SuspectInfo>();
			for (List<Object> list2 : lists) {
				SuspectInfo suBean = new SuspectInfo();
				if (i++ == 0)
					continue; // 表头不读取
				if (list2 == null || list2.size() == 0)
					break; // 读取到空结束

				if (list2.get(0).toString() == "null") {
					suBean.setSuspectName("");
				} else {
					suBean.setSuspectName(list2.get(0).toString());
				}

				if (list2.get(1).toString() == "null") {
					suBean.setSuspectSex("");
				} else {
					suBean.setSuspectSex(list2.get(1).toString());
				}

				if (list2.get(2).toString() == "null") {
					suBean.setSuspectPhone("");
				} else {
					suBean.setSuspectPhone(list2.get(2).toString());
				}

				if (list2.get(3).toString() == "null") {
					suBean.setSuspectMail("");
				} else {
					suBean.setSuspectMail(list2.get(3).toString());
				}

				if (list2.get(4).toString() == "null") {
					suBean.setSuspectQQ("");
				} else {
					suBean.setSuspectQQ(list2.get(4).toString());
				}

				if (list2.get(5).toString() == "null") {
					suBean.setSuspectHomeAddress("");
				} else {
					suBean.setSuspectHomeAddress(list2.get(5).toString());
				}

				if (list2.get(6).toString() == "null") {
					suBean.setSuspectUnitName("");
				} else {
					suBean.setSuspectUnitName(list2.get(6).toString());
				}

				if (list2.get(7).toString() == "null") {
					suBean.setSuspectUnitAddress("");
				} else {
					suBean.setSuspectUnitAddress(list2.get(7).toString());
				}

				if (list2.get(8).toString() == "null") {
					suBean.setSuspectIDCardNumber("");
				} else {
					suBean.setSuspectIDCardNumber(list2.get(8).toString());
				}

				if (list2.get(9).toString() == "null") {
					suBean.setSuspectSocialSecurity("");
				} else {
					suBean.setSuspectSocialSecurity(list2.get(9).toString());
				}

				if (list2.get(10).toString() == "null") {
					suBean.setSuspectPassport("");
				} else {
					suBean.setSuspectPassport(list2.get(10).toString());
				}

				if (list2.get(11).toString() == "null") {
					suBean.setSuspectFacebook("");
				} else {
					suBean.setSuspectFacebook(list2.get(11).toString());
				}

				if (list2.get(12).toString() == "null") {
					suBean.setSuspectTwitter("");
				} else {
					suBean.setSuspectTwitter(list2.get(12).toString());
				}

				if (list2.get(13).toString() == "null") {
					suBean.setSuspectMicroletters("");
				} else {
					suBean.setSuspectMicroletters(list2.get(13).toString());
				}

				if (list2.get(14).toString() == "null") {
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
			}
			actionLog((String) session.getAttribute("userName"), "导入", "嫌疑人画像 导入嫌疑人");
		}
		return "newsuspect_success";
	}

	// 获取案件标签list
	@RequestMapping(value = "/getAllLabel.php")
	public void getAllLable(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		int pageSize = 10;
		String _pageIndex = request.getParameter("page");
		int pageIndex = Integer.parseInt(_pageIndex);
		Label label = new Label();

		int totalsize = sqlDao.getcountfromMysql2(label);

		if (totalsize == 0) {
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write("{\"totalNum\":\"" + 0 + "\",\"totalPages\":\"" + 1 + "\",\"nowPage\":\"" + 1
						+ "\",\"resData\":\"\"}");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
			return;
		}

		int labelSize = sqlDao.getcountfromMysql(label);
		labelSize = 10;

		// List<Label> ul = sqlDao.getOrderListfromMysql2(label, 0, labelSize,
		// "id");
		List<Label> ul = sqlDao.getOrderListfromMysqlLike(label, (pageIndex - 1) * labelSize, labelSize, "id");

		JSONArray jsonArray = JSONArray.fromObject(ul);
		String json_str = jsonArray.toString();
		int l = totalsize / pageSize;
		if (totalsize % pageSize > 0)
			l++;
		String result_data = "{\"totalNum\":\"" + totalsize + "\",\"totalPages\":\"" + l + "\",\"nowPage\":\""
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
	}

	// 新增案件上传文件的日志展示
	@RequestMapping(value = "showAllHandledFile2.php")
	public void showAllHandledFile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		int caseID = Integer.parseInt(request.getParameter("caseid"));// 案件id
		String evName = request.getParameter("evName");// 证据名称
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
		String filePath = "/tmp/emaildata/" + caseID + "/" + caseID + evUUID.substring(0, 10);//删除了evName
		String command = "hadoop fs -du -h " + filePath;
		// System.out.println("hadoop 查日志命令 : " + command);// 打印路径
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

	// 新增案件上传文件到hdfs进度
	@RequestMapping(value = "showHdfsProgressBar.php")
	public void showHdfsProgressBar(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String evName;
		// 1,从session中获取evName,caseNum
		evName = (String) session.getAttribute("evName");// 证据名称
		String evUUID = (String) session.getAttribute("evUUID");// uuid
		int caseID = Integer.parseInt(request.getParameter("caseid"));// 案件id
		// 2.构建HDFS上传路径文件夹名称
		String filePath = "/tmp/emaildata/" + caseID;//删除了evName
		// 查Linux上文件大小
		double fengzm = 0;
		File file = new File("/emaildata/" + caseID + "/" + caseID + evUUID.substring(0, 10));// 传入文件路径
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
			// System.out.println("此文件不存在");
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

	// 案件类型list
	@RequestMapping(value = "showAllCaseType.php")
	public void showAllCaseType(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");

		CaseType caseType = new CaseType();
		List<CaseType> caseTypeList = sqlDao.getListfromMysql(caseType);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(caseTypeList));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 查询所有科室list
	@RequestMapping(value = "showAllSection.php")
	public void showAllSection(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");

		// CaseType caseType = new CaseType();
		// List<CaseType> caseTypeList = sqlDao.getListfromMysql(caseType);
		Section section = new Section();
		List<Section> sectionList = sqlDao.getListfromMysql(section);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(sectionList));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 电子邮件工作台点击 更改案件按钮显示案件编号和案件 名称
	@RequestMapping(value = "/admin/getCaseOfEmail.php")
	public void getCaseOfEmail(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String casenumorname = request.getParameter("casenumorname"); // 接收输入写入的值进行模糊查询
		Caseinfo cas = new Caseinfo();
		Caseinfo cas2 = new Caseinfo();
		User user = (User) session.getAttribute("user");
		String roleName="" ;
		if(user != null){
			String userrole = user.getUserrole();
			Role role = new Role();
			role.setId(Integer.parseInt(userrole));
			List<Role> roleList = sqlDao.getListfromMysql(role);
			if(roleList.size()>0){
				Role role2 = roleList.get(0);
				roleName = role2.getDataScope();
			}
		}
		if (roleName.equals("科室数据") ) {// 科长
			cas.setSection(user.getSection()+"/"+user.getUsername());
			cas2.setSection(user.getSection()+"/"+user.getUsername());
		}else if (roleName.equals("部门数据") ) {// 部长
			cas.setDepartment(user.getPartment());
			cas2.setDepartment(user.getPartment());
		} else if (roleName.equals("个人数据") ) {// 科员
			cas.setUserName(user.getUsername());
			cas2.setUserName(user.getUsername());
		} else if (roleName.equals("所有数据")) {// 局长
			cas.setId(-1);
			cas2.setId(-1);
		} else {
			cas.setId(-2);
			cas2.setId(-2);
		}
		if (casenumorname != null && !"".equals(casenumorname)) {
			cas.setCaseNum(casenumorname);
		}
		List<Caseinfo> casList = sqlDao.getListfromMysqlLikeOR(cas);

		if (casenumorname != null && !"".equals(casenumorname)) {
			cas2.setCaseName(casenumorname);
		}
		int caseflag = 0;
		List<Caseinfo> casList2 = sqlDao.getListfromMysqlLikeOR(cas2);
		for (Caseinfo caseinfo2 : casList2) {
			for (Caseinfo caseinfo : casList) {
				if (caseinfo.getId() == caseinfo2.getId()) {
					caseflag = 1;
				}
			}
			if (caseflag == 0) {
				casList.add(caseinfo2);
			}
		}
		SuspectInfo suspectInfo = new SuspectInfo();
		List<SuspectInfo> listfromMysql = sqlDao.getListfromMysql(suspectInfo);

		int i = 0;
		for (Caseinfo caseinfo : casList) {
			String suspectNumsStr = "";
			String mainParty = caseinfo.getMainParty();
			int xyrtotal=0;
			/*<<<<<<< .mine
			System.out.println("邮件mainParty=="+mainParty);
			if (!"".equals(mainParty) && mainParty !=null) {
				String[] split = mainParty.split(",");
				for (String string : split) {
					if (string !=null && !"".equals(string)) {
						int caseSuspId = Integer.parseInt(string);
						for (SuspectInfo suspectInfo3 : listfromMysql) {
							int id = suspectInfo3.getId();
							String suspectName = suspectInfo3.getSuspectName();
							String suspectPhone = suspectInfo3.getSuspectPhone();
							if (caseSuspId == id) {
								if ("".equals(suspectNumsStr)) {
									suspectNumsStr = suspectName + " " + suspectPhone;
								} else {
									suspectNumsStr += "/" + suspectName + " " + suspectPhone;
								}
							}
=======*/
			if(mainParty!=null && !"".equals(mainParty)){
				String[] split = mainParty.split(",");
				xyrtotal = split.length;
				//System.out.println(xyrtotal+"eeeeeeeeeeeeeee");
				for (String string : split) {
					if(string!=null && !"".equals(string)){
						int caseSuspId = Integer.parseInt(string);
						for (SuspectInfo suspectInfo3 : listfromMysql) {
							int id = suspectInfo3.getId();
							String suspectName = suspectInfo3.getSuspectName();
							String suspectPhone = suspectInfo3.getSuspectPhone();
							String suspectEmail = suspectInfo3.getSuspectMail();

							if (caseSuspId == id) {
								if ("".equals(suspectNumsStr)) {
									suspectNumsStr = suspectName + " " + suspectPhone + " " + suspectEmail+" "+caseSuspId+" "+xyrtotal;
								} else {
									suspectNumsStr += "/" + suspectName + " " + suspectPhone + " " + suspectEmail+" "+caseSuspId+" "+xyrtotal;
								}
							}
							//System.out.println(suspectNumsStr);
						}
					}

				}
			}
			casList.get(i).setMainParty(suspectNumsStr);
			casList.get(i).setRemark(String.valueOf(xyrtotal));//占时替换为案件下嫌疑人个数
			i++;
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(casList));
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
	 * 文档挖掘页面 点击收藏时弹出窗口里的标签
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/showAllLabelOfDoc.php")
	public void showAllLabelOfDoc(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		Label label = new Label();
		List<Label> labelList = sqlDao.getListfromMysqlLike(label);
		actionLog((String) session.getAttribute("userName"), "查看", "文档挖掘");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(labelList));
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
	 * 收藏夹 查询所有用户
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/showAllUser.php")
	public void showAllUser(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		User user = new User();
		List<User> userList = sqlDao.getListfromMysqlLike(user);
		actionLog((String) session.getAttribute("userName"), "查看", "收藏夹");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(userList));
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
	 * 数据管理-邮件工作台-查询邮件 执行分布式搜索访问并处理数据
	 */
	@RequestMapping(value = "/admin/searchEmlsF2.php")
	public void searchEmlsF2(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String sortType = request.getParameter("sortType");
		String regexpQuery = request.getParameter("regexpQuery");
		String emailKeyword = request.getParameter("emailKeyword");
		String caseidStr = request.getParameter("caseidStr");
		String pageIndexstr = request.getParameter("pageIndex");
		int pageIndex = 1;
		int pageSize = 10;
		if (pageIndexstr != null && !"".equals(pageIndexstr)) {
			pageIndex = Integer.parseInt(pageIndexstr);
		}
		String read = "";
		String star = "";
		String[] caseids = { "" };
		if (caseidStr != null && !"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		String sortConditon = "";
		if (sortType.equals("未读")) {
			read = "0";
			sortConditon = "date";
		} else if (sortType.equals("已读")) {
			read = "1";
			sortConditon = "date";
		} else if (sortType.equals("星标")) {
			star = "1";
			sortConditon = "date";
		} else if (sortType.equals("日期")) {
			sortConditon = "date";
		} else if (sortType.equals("收件人")) {
			sortConditon = "toWho";
		} else if (sortType.equals("发件人")) {
			sortConditon = "fromWho";
		}
		String quickflag = "";
		if ("手机号".equals(regexpQuery)) {
			quickflag = Global.regPhone;
		} else if ("固定电话".equals(regexpQuery)) {
			quickflag = Global.regTel;
		} else if ("身份证号".equals(regexpQuery)) {
			quickflag = Global.regSFZ;
		} else if ("邮箱号".equals(regexpQuery)) {
			quickflag = Global.regEmail;
		} else if ("银行卡号".equals(regexpQuery)) {
			quickflag = Global.regCard;
		} else if ("车牌号".equals(regexpQuery)) {
			quickflag = Global.regLicense;
		} else if ("运输车号".equals(regexpQuery)) {
			quickflag = Global.regModel;
		} else if ("集装箱号".equals(regexpQuery)) {
			quickflag = Global.regContainer;
		} else if ("装箱单".equals(regexpQuery)) {
			quickflag = Global.regPacking;
		} else if ("提单号".equals(regexpQuery)) {
			quickflag = Global.regBill;
		} else if ("价格".equals(regexpQuery)) {
			quickflag = Global.regPrice;
		} else if ("合同编号".equals(regexpQuery)) {
			quickflag = Global.regContract;
		} else if ("发票代码".equals(regexpQuery)) {
			quickflag = Global.regStamp;
		} else if ("信用证".equals(regexpQuery)) {
			quickflag = Global.regLC;
		} else if ("电汇".equals(regexpQuery)) {
			quickflag = Global.regTT;
		} else if ("GPS".equals(regexpQuery)) {
			quickflag = Global.regGPS;
		}
		// System.out.println("es查询开始========>>"+new Date());;
		// 精确搜索
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
		// caseid集合
		if (caseidStr != null) {
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		}
		// 已读未读状态

		if (!"".equals(read)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("readFlag", read));
		}
		// 星标状态
		if (!"".equals(star)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("starFlag", star));
		}
		// 关键词搜索
		if (emailKeyword != null && !"".equals(emailKeyword)) {
			QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(emailKeyword)// .escape(true)//escape
					// 转义
					// 设为true，避免搜索[]、结尾为!的关键词时异常
					// 但无法搜索*
					.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
			mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
		}
		// 特殊号码
		if (!"".equals(quickflag)) {
			QueryBuilder qb = QueryBuilders.regexpQuery("content", quickflag);
			mustQuery.must(qb);
		}

		/*
		 * QueryBuilder queryBuilder2 =
		 * QueryBuilders.queryStringQuery(big_search_box)//.escape(true)//escape
		 * 转义 设为true，避免搜索[]、结尾为!的关键词时异常 但无法搜索*
		 * .defaultOperator(QueryStringQueryBuilder.Operator.AND);//
		 * 不同关键词之间使用and关系 mustQuery.must(queryBuilder2);//添加第4条must的条件
		 * 关键词全文搜索筛选条件
		 */ /*
		 * if(caseidStr!=null && !"".equals(caseidStr)){ for(int
		 * i=0;i<caseids.length;i++){ String caseid = caseids[i];
		 * mustQuery.must(QueryBuilders.matchQuery("caseID",caseid)); //
		 * 添加第3条must的条件 } }
		 */
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>")// 配置高亮显示搜索结果
				// 模糊查询
				// .setQuery(QueryBuilders.matchQuery("subject",
				// "信用管家").operator(Operator.AND)) //根据tom分词查询name,默认or
				.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize);// 本次返回的文档数量
		// searchRequestBuilder =
		// searchRequestBuilder.addAggregation(AggregationBuilders.terms("agg1(聚类返回时根据此key获取聚类结果)")
		// .size(1000)/*返回1000条聚类结果*/.field("要在文档中聚类的字段，如果是嵌套的则用点连接父子字段，如【person.company.name】"));

		// 排序 执行
		SearchResponse searchResponse = searchRequestBuilder.addSort(sortConditon, SortOrder.DESC)// 排序.addSort(SortBuilders.fieldSort(sortConditon))//按类型排序
				.execute().actionGet();// 执行搜索
		SearchHit[] hits = searchResponse.getHits().getHits();
		long totalHits = searchResponse.getHits().getTotalHits();
		List<EmailDTO> emailDTOList = new ArrayList<EmailDTO>();

		for (SearchHit searchHit : hits) {
			String esID = (String) searchHit.getId();
			Map<String, Object> source = searchHit.getSource();
			String subject = (String) source.get("subject");
			String fromWho = (String) source.get("fromWho");
			String toWho = (String) source.get("toWho");
			String downloadUrl = (String) source.get("file_download_url");
			String ip = (String) source.get("ip");
			String date = (String) source.get("date");
			String content = (String) source.get("content");
			String readFlag = (String) source.get("readFlag");
			String starFlag = (String) source.get("starFlag");

			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

			HighlightField highlightsubject = highlightFields.get("subject");
			HighlightField highlightfromWho = highlightFields.get("fromWho");
			HighlightField highlighttoWho = highlightFields.get("toWho");
			HighlightField highlightdate = highlightFields.get("date");
			HighlightField highlightcontent = highlightFields.get("content");
			EmailDTO emailDTO = new EmailDTO();
			emailDTO.setEsID(esID);
			emailDTO.setRead(Integer.parseInt(readFlag));
			emailDTO.setStar(Integer.parseInt(starFlag));
			if (highlightsubject != null) {
				// 取得定义的高亮标签
				Text[] fragments = highlightsubject.fragments();
				// 为title串值增加自定义的高亮标签
				String content3 = "";
				for (Text text : fragments) {
					content3 += text;
				}
				emailDTO.setSubject(content3);
			} else {
				emailDTO.setSubject(subject);
			}
			if (highlightfromWho != null) {
				Text[] fragments = highlightfromWho.fragments();
				String content3 = "";
				for (Text text : fragments) {
					content3 += text;
				}
				emailDTO.setFromWho(content3);
			} else {
				emailDTO.setFromWho(fromWho);
			}
			if (highlighttoWho != null) {
				Text[] fragments = highlighttoWho.fragments();
				String content3 = "";
				for (Text text : fragments) {
					content3 += text;
				}
				emailDTO.setToWho(content3);
			} else {
				emailDTO.setToWho(toWho);
			}
			if (highlightcontent != null) {
				content.replace(emailKeyword, "<font color='red' >" + emailKeyword + "</font>");
				String s1 = content.replaceAll(emailKeyword, "<font color='red' >" + emailKeyword + "</font>");

				Text[] fragments = highlightcontent.fragments();
				String content3 = "";
				for (Text text : fragments) {
					content3 += text;
				}
				emailDTO.setContent(s1);
			} else {
				emailDTO.setContent(content);
			}
			if (highlightdate != null) {
				Text[] fragments = highlightdate.fragments();
				String content3 = "";
				for (Text text : fragments) {
					content3 += text;
				}
				String date2 = highlightdate.toString();
				emailDTO.setDate(content3);
			} else {
				emailDTO.setDate(date);
			}
			emailDTO.setDownloadUrl(downloadUrl);
			emailDTO.setIp(ip);
			emailDTOList.add(emailDTO);
		}
		/*
		 * 查询未读数目
		 */
		// 精确搜索
		BoolQueryBuilder mustQuery2 = QueryBuilders.boolQuery();
		mustQuery2.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件
		// 此处为匹配所有文档
		// caseid集合
		/*
		 * if(caseids!=null){
		 * mustQuery2.must(QueryBuilders.termsQuery("caseID",caseids)); }
		 */
		// 未读
		mustQuery2.must(QueryBuilders.matchPhraseQuery("readFlag", "0"));
		// 星标状态
		if (!"".equals(star)) {
			mustQuery2.must(QueryBuilders.matchPhraseQuery("starFlag", star));
		}
		// 关键词搜索
		if (emailKeyword != null && !"".equals(emailKeyword)) {
			QueryBuilder queryBuilder2 = QueryBuilders.queryStringQuery(emailKeyword)// .escape(true)//escape
					// 转义
					// 设为true，避免搜索[]、结尾为!的关键词时异常
					// 但无法搜索*
					.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
			mustQuery2.must(queryBuilder2);// 添加第4条must的条件 关键词全文搜索筛选条件
		}
		// 特殊号码
		if (!"".equals(quickflag)) {
			QueryBuilder qb2 = QueryBuilders.regexpQuery("content", quickflag);
			mustQuery2.must(qb2);
		}
		SearchRequestBuilder searchRequestBuilder2 = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery2).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>")// 配置高亮显示搜索结果
				// 模糊查询
				// .setQuery(QueryBuilders.matchQuery("subject",
				// "信用管家").operator(Operator.AND)) //根据tom分词查询name,默认or
				.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize);// 本次返回的文档数量
		// searchRequestBuilder =
		// searchRequestBuilder.addAggregation(AggregationBuilders.terms("agg1(聚类返回时根据此key获取聚类结果)")
		// .size(1000)/*返回1000条聚类结果*/.field("要在文档中聚类的字段，如果是嵌套的则用点连接父子字段，如【person.company.name】"));

		// 排序 执行
		SearchResponse searchResponse2 = searchRequestBuilder2.addSort(sortConditon, SortOrder.DESC)// 排序.addSort(SortBuilders.fieldSort(sortConditon))//按类型排序
				.execute().actionGet();// 执行搜索
		long read0 = searchResponse2.getHits().getTotalHits();
		if ("1".equals(read)) {
			read0 = 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("emailDTOList", emailDTOList);
		map.put("count", totalHits);
		map.put("read0", read0);
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
	 * 从HDFS文件系统下载邮件
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "admin/downloadEML.php")
	public void downloadEML(HttpServletRequest request, HttpServletResponse response)
			throws IOException, InterruptedException {

		String tomPath = "/temp/";
		File filePath = new File(tomPath);
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		String emlpath = request.getParameter("emlpath");
		logger.info("---------" + emlpath);
		emlpath = URLDecoder.decode(emlpath, "utf-8");
		logger.info("----解码后fileUrl-----" + emlpath);
		// try {
		// emlpath = new String(emlpath.getBytes("ISO8859-1"), "utf-8");
		// } catch (UnsupportedEncodingException e1) {
		// // e1.printStackTrace();
		// }
		// String emlpath = "http//:172.16.102.220:8020/tmp/download.txt";
		String browser = request.getHeader("user-agent");
		String[] temp1 = emlpath.split(",");
		logger.info("---------------" + temp1);
		if (temp1.length == 1) {// 单个文件下载
			String[] temp11 = emlpath.split("/");

			String temp2 = emlpath.split("/")[temp11.length - 1];

			String cmd = "hadoop fs -copyToLocal " + emlpath + " /temp/";
			Process process = Runtime.getRuntime().exec(cmd);
			logger.info("hdfs下载到linux》》》》》》》" + cmd);
			process.waitFor();
			File temFile = null;
			try {

				temFile = new File("/temp/" + temp2);
				if (!temFile.exists()) {
					response.getWriter().write("ERROR:File Not Found");
				}
				String fileName = temp2;

				Pattern p = Pattern.compile(".*MSIE.*?;.*");
				Matcher m = p.matcher(browser);

				if (m.matches()) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", ""));
				} else {
					response.setHeader("Content-Disposition", "attachment; filename="
							+ new String(fileName.getBytes("UTF-8"), "ISO8859-1").replace(" ", ""));
				}
				response.setContentType("application/x-download");
				OutputStream ot = response.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(temFile));
				BufferedOutputStream bos = new BufferedOutputStream(ot);
				byte[] buffer = new byte[4096];
				int length1 = 0;
				while ((length1 = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, length1);
				}
				bos.close();
				bis.close();
				ot.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (temFile != null) {
					temFile.delete();
				}
			}
		}

	}

	/**
	 * 邮件工作台-更改邮件状态和星标
	 */
	/*
	 * @RequestMapping(value = "/admin/upEmailStatus.php") public void
	 * upEmailStatus(HttpServletRequest request, HttpServletResponse response,
	 * HttpSession session) throws IOException { response.setContentType(
	 * "textml; charset=UTF-8"); String read = request.getParameter("read");
	 * String star = request.getParameter("star"); String esID =
	 * request.getParameter("esID"); //System.out.println(
	 * "esID  =  ==================   "+esID); String key=""; String value="";
	 * if(!"".equals(read) ){ key="readFlag"; value=read; }if(!"".equals(star)
	 * ){ key="starFlag"; value=star; } EsClient.update("es", "email", esID,
	 * key, value); PrintWriter writer = null; try { writer =
	 * response.getWriter(); writer.write("success"); writer.flush(); } catch
	 * (Exception e) { e.printStackTrace(); } finally { if (writer != null) {
	 * writer.close(); } } }
	 */

	/**
	 * 邮件挖掘一个页面点击星星收藏
	 */
	@RequestMapping(value = "/admin/favoEmail.php")
	public void favoEmail(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String emailId = request.getParameter("esid"); // 数据库图片id
		String emailStarFlag = request.getParameter("flag"); // 数据库图片星标
		String emailLabel = request.getParameter("emailLabel"); // 取标签

		PrintWriter writer = null;
		try {
			Map<String, String> updateMap = new HashMap<>();
			if (emailStarFlag.equals("0")) {
				updateMap.put("starFlag", "1");
				updateMap.put("favoriteLabel", emailLabel);
				actionLog((String) session.getAttribute("userName"), "收藏", "邮件挖掘");
			}
			if (emailStarFlag.equals("1")) {
				updateMap.put("starFlag", "0");
				updateMap.put("favoriteLabel", emailLabel);
			}
			// 设置收藏时间
			String time = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
			// 设置收藏人
			HttpSession httpSession = request.getSession();
			String favoritePerson = (String) httpSession.getAttribute("userName");

			updateMap.put("favoriteTime", time);
			updateMap.put("favoritePerson", favoritePerson);
			EsUpdate.updateTest("es", "email", emailId, updateMap);

			writer = response.getWriter();
			writer.write("{\"status\":\"success\"}");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	/**
	 * 图片挖掘-图片GPS定位页面的展示
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/admin/showPictureInfoGPS.php")
	public void showPictureInfoGPS(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		String dirName = request.getParameter("dirName");// 文价名称
		String pageIndexstr = request.getParameter("pageIndex");// 页数
		String casenumorname = request.getParameter("casenumorname");
		int pageIndex = 1;
		if (pageIndexstr != null) {
			pageIndex = Integer.parseInt(pageIndexstr);// 每页
		}
		PictureInfo pictureInfo = new PictureInfo();
		if (dirName != null || !"".equals(dirName)) {
			pictureInfo.setDirName(dirName);
		}
		int count = sqlDao.getcountfromMysql(pictureInfo);
		List<PictureInfo> pictureList = sqlDao.getListfromMysqlLike(pictureInfo, (pageIndex - 1) * 10, 10);
		List<PictureInfo> caseid = sqlDao.getListfromMysql(pictureInfo);
		int caseid1 = 0;
		String caseName = "";
		String caseNum = "";
		ArrayList<Caseinfo> arrayList = new ArrayList<Caseinfo>();
		for (PictureInfo pictureInfo2 : caseid) {
			caseid1 = pictureInfo2.getCaseID();

			Caseinfo caseinfo = new Caseinfo();
			caseinfo.setId(caseid1);
			List<Caseinfo> listfromMysql = sqlDao.getListfromMysql(caseinfo);
			actionLog((String) session.getAttribute("userName"), "查看", "图片GPS定位");
			Caseinfo caseinfo2 = listfromMysql.get(0);
			int flag = 0;
			for (Caseinfo caseinfo3 : arrayList) {
				int id = caseinfo3.getId();
				if (caseid1 == id) {
					flag = 1;
				}
			}
			if (flag == 0) {
				arrayList.add(caseinfo2);
			}
		}

		map.put("pictureList", pictureList);
		map.put("count", count);
		map.put("arrayList", arrayList);
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
	 * 图片挖掘-从es上查询图片
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/admin/showPictureInfo.php")
	public void showPictureInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String pageIndexStr = (String) request.getParameter("pageIndex");
		String dirName = (String) request.getParameter("dirName");
		String caseidStr = (String) request.getParameter("caseidStr");
		int pageIndex = Integer.parseInt(pageIndexStr);
		String[] caseids = { "" };
		if (!"".equals(caseidStr) && caseidStr != null) {
			caseids = caseidStr.split(" ");
		}
		// 分页
		int pageSize = 10;// 每页个数
		// 按条件查询
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		// caseid集合
		mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		// 组合 模糊查询 should
		if (dirName != null && !"".equals(dirName)) {
			//mustQuery.should(QueryBuilders.termsQuery("picname", "*"+dirName+"*"));//matchQuery
			WildcardQueryBuilder picname = QueryBuilders.wildcardQuery("picname", "*"+dirName+"*"); 
			mustQuery.must(picname);
		}

		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery).setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize);// 本次返回的文档数量

		/*SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery).setFrom(0)// 分页起始位置（跳过开始的n个）
				.setSize(1);*/// 本次返回的文档数量
		// 排序 执行
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();// 执行搜索

		String string = searchResponse.toString();

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(string);
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
	 * 图片GPS-从es上查询图片并处理，只输出有经纬度的图片
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/admin/showPictureInfo_GPS.php")
	public void showPictureInfo_GPS(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String pageIndexStr = (String) request.getParameter("pageIndex");
		String dirName = (String) request.getParameter("dirName");
		String caseidStr = (String) request.getParameter("caseidStr");
		int pageIndex = Integer.parseInt(pageIndexStr);
		String[] caseids = { "" };
		List<PicInfo> piclist=new ArrayList<PicInfo>();
		if (!"".equals(caseidStr) && caseidStr != null) {
			caseids = caseidStr.split(" ");
		}
		// 分页
		int pageSize = 10;// 每页个数
		// 按条件查询
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		// caseid集合
		mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		// 组合 模糊查询 should
		if (dirName != null && !"".equals(dirName)) {
			//mustQuery.should(QueryBuilders.termsQuery("picname", "*"+dirName+"*"));//matchQuery
			WildcardQueryBuilder picname = QueryBuilders.wildcardQuery("picname", "*"+dirName+"*"); 
			mustQuery.must(picname);

		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery).setFrom(0)// 分页起始位置（跳过开始的n个）
				.setSize(1);// 本次返回的文档数量
		// 排序 执行
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();// 执行搜索
		String string = searchResponse.toString();
		//获取所有图片的数量
		long totalnum=searchResponse.getHits().getTotalHits();
		long resnum=totalnum;
		//如果数据量过大，只查前20000条数据，防止查询过多内存溢出
		if(resnum>20000){
			totalnum=20000;
			resnum=20000;
		}
		//循环的目的是把所有的图片都查出并存储到piclist中
		while(resnum>0){
			if(resnum>5000)
				searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery).setFrom((int)(totalnum-resnum))// 分页起始位置（跳过开始的n个）
				.setSize(5000);// 本次返回的文档数量
			else
				searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery).setFrom((int)(totalnum-resnum))// 分页起始位置（跳过开始的n个）
				.setSize(5000);// 本次返回的文档数量
			SearchResponse searchResponse2 = searchRequestBuilder.execute().actionGet();// 执行搜索
			SearchHits hits = searchResponse2.getHits();
			SearchHit[] hitss = hits.hits();


			for (SearchHit searchHit : hitss) {
				Map<String, Object> source = searchHit.getSource();
				PicInfo pic =new PicInfo();
				String favoriteLabel=(String)source.get("favoriteLabel");
				pic.setFavoriteLabel(favoriteLabel);
				String picDirpath=(String)source.get("picDirpath");
				pic.setPicDirpath(picDirpath);
				String favoriteTime=(String)source.get("favoriteTime");
				pic.setFavoriteTime(favoriteTime);
				String starFlag=(String)source.get("starFlag");
				pic.setStarFlag(starFlag);
				String picdesc=(String)source.get("picdesc");
				pic.setPicdesc(picdesc);
				String caseID=(String)source.get("caseID");
				pic.setCaseID(caseID);
				String picname=(String)source.get("picname");
				pic.setPicname(picname);
				String caseName=(String)source.get("caseName");
				pic.setCaseName(caseName);

				//String picPath="D:/beidouyun_Img/"+picDirpath;	//windows下测试路径
				String picPath=picDirpath;
				if (StringUtils.isNotEmpty(picPath)) {
					File picFile = new File(picPath);
					Metadata metadata;
					String Longitude = null;
					String Latitude = null;

					try {
						metadata = JpegMetadataReader.readMetadata(picFile);
						Directory gps = metadata.getDirectory(GpsDirectory.class);

						Directory exif = metadata.getDirectory(ExifDirectory.class);
						int n = gps.getTagCount();
						Iterator tags3 = gps.getTagIterator();
						while (tags3.hasNext()) {
							Tag tag = (Tag) tags3.next();
							if (tag.getTagName().equals("GPS Latitude")) {
								Latitude = tag.getDescription();
							}
							if (tag.getTagName().equals("GPS Longitude")) {
								Longitude = tag.getDescription();
							}
						}
						//当图片存在经纬度信息时才返回到前台
						if (Latitude != null && Longitude != null) {
							piclist.add(pic);
						}
					}catch (JpegProcessingException e) {
						//e.printStackTrace();
					} catch (MetadataException e) {
						//e.printStackTrace();
					}
				}

			}
			resnum-=5000;
		}//while——end
		/*
		 *只返回单页的数目 
		 */
		List<PicInfo> piclist2=new ArrayList<PicInfo>();
		for(int i=(pageIndex-1)*pageSize;i<pageIndex*pageSize&&i<piclist.size();i++)
			piclist2.add(piclist.get(i));
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("piclist", piclist2);
		hashMap.put("total", piclist.size());
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			//writer.write(string);
			writer.write(JsonUtil.map2json(hashMap));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 查询所有
	@RequestMapping(value = "/admin/Searchdocandemail.php")
	public void Searchdocandemail(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String big_search_box = request.getParameter("big_search_box");

		String caseidStr = request.getParameter("caseidStr");
		String[] caseids = { "" };
		if (caseidStr != null && !"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}

		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

		QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(big_search_box)// .escape(true)//escape
				// 转义
				// 设为true，避免搜索[]、结尾为!的关键词时异常
				// 但无法搜索*
				.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
		mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件

		if (!"".equals(caseidStr)) {
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("doc", "es", "pic")
				.setTypes("docType", "email", "picindex").setQuery(mustQuery);

		searchRequestBuilder = searchRequestBuilder.addAggregation(AggregationBuilders.terms("agg1(聚类返回时根据此key获取聚类结果)")
				.size(1000)/* 返回1000条聚类结果 */.field("要在文档中聚类的字段，如果是嵌套的则用点连接父子字段，如【person.company.name】"));
		int pageIndex = 1;// 页数
		int pageSize = 8;// 每页
		String pageno = request.getParameter("pageno");
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索

		SearchHit[] hits = searchResponse.getHits().getHits();
		List<allesDTO> allesDTOList = new ArrayList<allesDTO>();

		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
			String type = searchHit.getType();

			if (searchHit.type().equals("email")) {
				allesDTO allesDTO = new allesDTO();
				allesDTO.set_type(type);
				String subject = (String) source.get("subject");
				String fromWho = (String) source.get("fromWho");
				String toWho = (String) source.get("toWho");
				String downloadUrl = (String) source.get("file_download_url");
				String date = (String) source.get("date");
				String content = (String) source.get("content");
				String attachmentname = (String) source.get("attachmentname");

				HighlightField highlightsubject = highlightFields.get("subject");
				HighlightField highlightfromWho = highlightFields.get("fromWho");
				HighlightField highlighttoWho = highlightFields.get("toWho");
				HighlightField highlightdate = highlightFields.get("date");
				HighlightField highlightcontent = highlightFields.get("content");
				HighlightField highlightcontenturl = highlightFields.get("file_download_url");

				if (highlightsubject != null) {
					Text[] titleTexts = highlightsubject.fragments();
					String subject2 = "";
					for (Text text : titleTexts) {
						subject2 += text;
					}
					allesDTO.setSubject(subject2);
				} else {
					allesDTO.setSubject(subject);
				}
				if (highlightfromWho != null) {
					Text[] titleTexts = highlightfromWho.fragments();
					String fromWho2 = "";
					for (Text text : titleTexts) {
						fromWho2 += text;
					}
					allesDTO.setFromWho(fromWho2);
				} else {
					allesDTO.setFromWho(fromWho);
				}
				if (highlighttoWho != null) {
					Text[] titleTexts = highlighttoWho.fragments();
					String toWho2 = "";
					for (Text text : titleTexts) {
						toWho2 += text;
					}
					allesDTO.setToWho(toWho2);
				} else {
					allesDTO.setToWho(toWho);
				}
				if (highlightdate != null) {
					Text[] titleTexts = highlightdate.fragments();
					String date2 = "";
					for (Text text : titleTexts) {
						date2 += text;
					}
					allesDTO.setDate(date2);
				} else {
					allesDTO.setDate(date);
				}
				if (highlightcontenturl != null) {
					Text[] titleTexts = highlightcontenturl.fragments();
					String url2 = "";
					for (Text text : titleTexts) {
						url2 += text;
					}
					allesDTO.setFiledownloadurl(url2);
				} else {
					allesDTO.setFiledownloadurl(downloadUrl);
				}
				allesDTO.setAttachmentname(attachmentname);
				allesDTO.setContent(content);
				allesDTOList.add(allesDTO);
			}

			if (searchHit.type().equals("docType")) {
				allesDTO allesDTO = new allesDTO();
				allesDTO.set_type(type);
				String esDocId = searchHit.getId();
				String fileName = (String) source.get("fileName");
				int fileSize = (int) source.get("fileSize");
				String caseID = (String) source.get("caseID");
				String docType = (String) source.get("docType");
				String editDate = (String) source.get("editDate");
				String caseName = (String) source.get("caseName");
				String file_download_url = (String) source.get("file_download_url");

				HighlightField highlightfileName = highlightFields.get("fileName");
				HighlightField highlightfileSize = highlightFields.get("fileSize");
				HighlightField highlightcaseID = highlightFields.get("caseID");
				HighlightField highlightdocType = highlightFields.get("docType");
				HighlightField highlighteditDate = highlightFields.get("editDate");
				HighlightField highlightcaseName = highlightFields.get("caseName");
				HighlightField highlighturl = highlightFields.get("file_download_url");

				if (highlightfileName != null) {
					Text[] titleTexts = highlightfileName.fragments();
					String fileName2 = "";
					for (Text text : titleTexts) {
						fileName2 += text;
					}
					allesDTO.setFileName(fileName2);
				} else {
					allesDTO.setFileName(fileName);
				}
				if (highlightfileSize != null) {
					Text[] titleTexts = highlightfileSize.fragments();
					int fileSize2 = 0;
					for (Text text : titleTexts) {
						fileSize2 += Integer.parseInt(text.toString());
					}
					allesDTO.setFileSize(fileSize2);
				} else {
					allesDTO.setFileSize(fileSize);
				}
				if (highlightdocType != null) {
					Text[] titleTexts = highlightdocType.fragments();
					String docType2 = "";
					for (Text text : titleTexts) {
						docType2 += text;
					}
					allesDTO.setDocType(docType2);
				} else {
					allesDTO.setDocType(docType);
				}
				if (highlighteditDate != null) {
					Text[] titleTexts = highlighteditDate.fragments();
					String editDate2 = "";
					for (Text text : titleTexts) {
						editDate2 += text;
					}
					allesDTO.setEditDate(editDate2);
				} else {
					allesDTO.setEditDate(editDate);
				}
				if (highlighturl != null) {
					Text[] titleTexts = highlighturl.fragments();
					String url2 = "";
					for (Text text : titleTexts) {
						url2 += text;
					}
					allesDTO.setFile_download_url(url2);
				} else {
					allesDTO.setFile_download_url(file_download_url);
				}
				if (highlightcaseName != null) {
					Text[] titleTexts = highlightcaseName.fragments();
					String caseName2 = "";
					for (Text text : titleTexts) {
						caseName2 += text;
					}
					allesDTO.setCaseName(caseName2);
				} else {
					allesDTO.setCaseName(caseName);
				}
				if (highlightcaseID != null) {
					Text[] titleTexts = highlightcaseID.fragments();
					String caseID2 = "";
					for (Text text : titleTexts) {
						caseID2 += text;
					}
					allesDTO.setCaseID(caseID2);
				} else {
					allesDTO.setCaseID(caseID);
				}
				allesDTO.setEsDocId(esDocId);
				allesDTOList.add(allesDTO);
			}
			if (searchHit.type().equals("picindex")) {

				allesDTO allesDTO = new allesDTO();

				String picDirpath = (String) source.get("picDirpath");
				String caseID = (String) source.get("caseID");
				String picdesc = (String) source.get("picdesc");
				String picname = (String) source.get("picname");

				allesDTO.setCaseID(caseID);
				allesDTO.setPicDirpath(picDirpath);


				HighlightField highlightpicdesc = highlightFields.get("picdesc");
				HighlightField highlightpicname = highlightFields.get("picname");

				if (highlightpicdesc != null) {
					String picDesc2 = highlightpicdesc.toString();
					allesDTO.setPicdesc(picDesc2);
				} else {
					allesDTO.setPicdesc(picdesc);
				}

				if (highlightpicname != null) {
					String picname2 = highlightpicname.toString();
					allesDTO.setPicname(picname2);
					;
				} else {
					allesDTO.setPicname(picname);
				}

				allesDTOList.add(allesDTO);
			}

		}
		HashMap<Object, Object> hashMap = new HashMap<>();
		//hashMap.put("caseidlist", caseidlist);
		hashMap.put("allesDTOList", allesDTOList);
		hashMap.put("total", searchResponse.getHits().getTotalHits());
		hashMap.put("types", searchResponse.getHits().getHits().toString());

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(hashMap));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 查询黑客数据库
	@RequestMapping(value = "/admin/SearchGHDB.php")
	public void SearchGHDB(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String big_search_box = request.getParameter("big_search_box");

		String caseidStr = request.getParameter("caseidStr");
		String[] caseids = { "" };
		if (caseidStr != null && !"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		BoolQueryBuilder mustQuery2 = QueryBuilders.boolQuery();
		mustQuery2.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件
		// 此处为匹配所有文档
		// 组合 模糊查询  should  
		BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
		TermQueryBuilder subject = QueryBuilders.termQuery("subject", big_search_box); 
		TermQueryBuilder content = QueryBuilders.termQuery("content", big_search_box);  
		TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", big_search_box);
		TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", big_search_box);
		TermQueryBuilder attachmentname = QueryBuilders.termQuery("attachmentname", big_search_box);
		TermQueryBuilder attachmentContent = QueryBuilders.termQuery("attachmentContent", big_search_box);  
		ors.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
		mustQuery2.must(ors);

		SearchRequestBuilder searchRequestBuilder2 = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery2).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

		searchRequestBuilder2 = searchRequestBuilder2
				.addAggregation(AggregationBuilders.terms("agg1(聚类返回时根据此key获取聚类结果)").size(1000)
						/* 返回1000条聚类结果 */.field("要在文档中聚类的字段，如果是嵌套的则用点连接父子字段，如【person.company.name】"));

		SearchResponse searchResponse2 = searchRequestBuilder2.setFrom((1 - 1) * 1000)// 分页起始位置（跳过开始的n个）
				.setSize(10)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索
		ArrayList<SearchHit[]> arrayList = new ArrayList<SearchHit[]>();
		SearchHit[] hits1 = searchResponse2.getHits().getHits();
		arrayList.add(hits1);
		//		long totalHits = searchResponse2.getHits().getTotalHits()/10000;
		//		for(int i=1;i<=totalHits;i++){
		//			searchResponse2 = searchRequestBuilder2.setQuery(mustQuery2).setFrom(i*10000)// 分页起始位置（跳过开始的n个）
		//					.setSize(10000).execute().actionGet();
		//			SearchHit[] hits  = searchResponse2.getHits().getHits();
		//			arrayList.add(hits);
		//		}
		ArrayList<allesDTO> caseidlist = new ArrayList<allesDTO>();
		for (SearchHit[] searchHit3 : arrayList) {
			for (SearchHit searchHit2 : searchHit3) {
				allesDTO allesDTO2 = new allesDTO();
				Map<String, Object> source2 = searchHit2.getSource();
				String caseID = (String) source2.get("caseID");
				allesDTO2.setCaseID(caseID);
				caseidlist.add(allesDTO2);
			}
		}
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

		QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(big_search_box)// .escape(true)//escape
				// 转义
				// 设为true，避免搜索[]、结尾为!的关键词时异常
				// 但无法搜索*
				.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
		mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件

		if (caseidStr != null && !"".equals(caseidStr)) {
			for (int i = 0; i < caseids.length; i++) {
				String caseid = caseids[i];
				mustQuery.must(QueryBuilders.matchQuery("caseID", caseid)); // 添加第3条must的条件
			}
		}

		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("hackerdb").setTypes("hkdb")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red'>").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果
		searchRequestBuilder = searchRequestBuilder.addAggregation(AggregationBuilders.terms("agg1(聚类返回时根据此key获取聚类结果)")
				.size(1000)/* 返回1000条聚类结果 */.field("要在文档中聚类的字段，如果是嵌套的则用点连接父子字段，如【person.company.name】"));
		int pageIndex = 1;// 页数
		int pageSize = 8;// 每页
		String pageno = request.getParameter("pageno");
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索

		SearchHits hits = searchResponse.getHits();
		SearchHit[] hitss = hits.hits();
		List<allesDTO> allesDTOList = new ArrayList<allesDTO>();
		allesDTO allesDTO = new allesDTO();
		for (SearchHit searchHit : hitss) {

			Map<String, Object> source = searchHit.getSource();
			String email = (String) source.get("email");
			String qqNum = (String) source.get("qqNum");
			String qqPasswd = (String) source.get("qqPasswd");
			String emailPasswd = (String) source.get("emailPasswd");
			String dataSource = (String) source.get("dataSource");
			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

			HighlightField highlightemail = highlightFields.get("email");
			HighlightField highlightdataSource = highlightFields.get("dataSource");
			HighlightField highlightqqNum = highlightFields.get("qqNum");
			HighlightField highlightqqPasswd = highlightFields.get("qqPasswd");
			HighlightField highlightemailPasswd = highlightFields.get("emailPasswd");

			if (highlightemail != null) {
				Text[] titleTexts = highlightemail.fragments();
				String email2 = "";
				for (Text text : titleTexts) {
					email2 += text;
				}
				allesDTO.setEmail(email2);
			} else {
				allesDTO.setEmail(email);
			}
			if (highlightqqPasswd != null) {
				Text[] titleTexts = highlightqqPasswd.fragments();
				String qqPasswd2 = "";
				for (Text text : titleTexts) {
					qqPasswd2 += text;
				}
				allesDTO.setQqPasswd(qqPasswd2);
			} else {
				allesDTO.setQqPasswd(qqPasswd);
			}
			if (highlightdataSource != null) {
				Text[] titleTexts = highlightdataSource.fragments();
				String dataSource2 = "";
				for (Text text : titleTexts) {
					dataSource2 += text;
				}
				allesDTO.setDataSource(dataSource2);
			} else {
				allesDTO.setDataSource(dataSource);
			}
			if (highlightqqNum != null) {
				Text[] titleTexts = highlightqqNum.fragments();
				String qqnum2 = "";
				for (Text text : titleTexts) {
					qqnum2 += text;
				}
				allesDTO.setQqNum(qqnum2);
			} else {
				allesDTO.setQqNum(qqNum);
			}
			if (highlightemailPasswd != null) {
				Text[] titleTexts = highlightemailPasswd.fragments();
				String emailPasswd2 = "";
				for (Text text : titleTexts) {
					emailPasswd2 += text;
				}
				allesDTO.setEmailPasswd(emailPasswd2);
			} else {
				allesDTO.setEmailPasswd(emailPasswd);
			}

			allesDTOList.add(allesDTO);

		}
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("caseidlist", caseidlist);
		hashMap.put("allesDTOList", allesDTOList);
		hashMap.put("total", searchResponse.getHits().getTotalHits());

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(hashMap));
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
	 * 查询黑客数据库更新数据信息
	 */
	@RequestMapping(value = "/hackerSearch.php")
	public String getHackerDBCount(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		// 在此处理：： 现在的黑客数据搜索页面 加下黑客数量 表是hackerdbcount 这个表 然后统计下问题
		List<HackerDBCount> listHackerCount = new ArrayList<HackerDBCount>();
		int pageIndex = 1;
		int pageSize = 10;
		// 开始为空。
		String importtime = request.getParameter("importtime");
		String hackcount_str = request.getParameter("hackcount");
		HackerDBCount cas = new HackerDBCount();

		if (!StringUtils.isEmpty(importtime)) {
			cas.setImportTime(importtime);
		}
		if (!StringUtils.isEmpty(hackcount_str)) {
			cas.setHackCount(hackcount_str);
		}

		listHackerCount = sqlDao.getOrderListfromMysqlLike(cas, "importtime");

		if (listHackerCount.size() == 0) {
			session.setAttribute("importtime", 0);
			session.setAttribute("hackcount", 0);
		} else {
			session.setAttribute("importtime", listHackerCount.get(0).getImportTime());
			HackCount hackcount = new HackCount();
			long temp = Long.parseLong(listHackerCount.get(0).getHackCount().toString());

			long sum_temp = 0;
			for (int i = 0; i < listHackerCount.size(); i++) {
				sum_temp += Long.parseLong(listHackerCount.get(i).getHackCount().toString());
			}
			// 单位为 个 、 万、 亿
			// temp 为更新的数据信息
			if (temp / 10000 == 0) {
				hackcount.setGewei(temp);
				map.put("hackcc", hackcount);
			} else {
				hackcount.setGewei(temp % 10000);
				temp /= 10000;
				if (temp / 10000 == 0) {
					hackcount.setWanwei(temp % 10000);
					map.put("hackcc", hackcount);
				} else {
					hackcount.setWanwei(temp % 10000);
					temp /= 10000;
					hackcount.setYiwei(temp);
					map.put("hackcc", hackcount);
				}
			}
			HackCount hackcount_sum = new HackCount();
			// sum_temp为总的信息条数
			if (sum_temp / 10000 == 0) {
				hackcount_sum.setGewei(sum_temp);
				map.put("hackcc_sum", hackcount_sum);
			} else {
				hackcount_sum.setGewei(sum_temp % 10000);
				sum_temp /= 10000;
				if (sum_temp / 10000 == 0) {
					hackcount_sum.setWanwei(sum_temp % 10000);
					map.put("hackcc_sum", hackcount_sum);
				} else {
					hackcount_sum.setWanwei(sum_temp % 10000);
					sum_temp /= 10000;
					hackcount_sum.setYiwei(sum_temp);
					map.put("hackcc_sum", hackcount_sum);
				}
			}
		}
		return "hackerSearch";
	}

	// 查询邮件
	@RequestMapping(value = "/admin/total.php")
	public void total(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String big_search_box = request.getParameter("big_search_box");

		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

		// 组合 模糊查询  should  
		BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
		TermQueryBuilder subject = QueryBuilders.termQuery("subject", big_search_box); 
		TermQueryBuilder content = QueryBuilders.termQuery("content", big_search_box);  
		TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", big_search_box);
		TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", big_search_box);
		TermQueryBuilder attachmentname = QueryBuilders.termQuery("attachmentname", big_search_box);
		TermQueryBuilder attachmentContent = QueryBuilders.termQuery("attachmentContent", big_search_box);  
		ors.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
		mustQuery.must(ors);

		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

		searchRequestBuilder = searchRequestBuilder.addAggregation(AggregationBuilders.terms("agg1(聚类返回时根据此key获取聚类结果)")
				.size(1000)/* 返回1000条聚类结果 */.field("要在文档中聚类的字段，如果是嵌套的则用点连接父子字段，如【person.company.name】"));

		HashMap<Object, Object> hashMap = new HashMap<>();
		SearchResponse searchResponse = searchRequestBuilder// 分页起始位置（跳过开始的n个）
				// 本次返回的文档数量
				.execute().actionGet();// 执行搜索
		hashMap.put("total", searchResponse.getHits().getTotalHits());

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(hashMap));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	//大搜索邮件查询
	@RequestMapping(value = "/admin/SearchResultss.php")
	public void SearchResultss(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String big_search_box = request.getParameter("big_search_box");
		String caseidStr = request.getParameter("caseidStr");
		String[] caseids = { "" };
		if (caseidStr != null && !"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}

		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

		if (!"".equals(caseidStr)) {
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		}

		/*QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(big_search_box)
				.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
		mustQuery.must(queryBuilder);*/
		// 组合 模糊查询  should  
		BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
		TermQueryBuilder subject1 = QueryBuilders.termQuery("subject", big_search_box); 
		TermQueryBuilder content1 = QueryBuilders.termQuery("content", big_search_box);  
		TermQueryBuilder fromWho1 = QueryBuilders.termQuery("fromWho", big_search_box);
		TermQueryBuilder toWho1 = QueryBuilders.termQuery("toWho", big_search_box);
		TermQueryBuilder attachmentname1 = QueryBuilders.termQuery("attachmentname", big_search_box);
		TermQueryBuilder attachmentContent1 = QueryBuilders.termQuery("attachmentContent", big_search_box);  
		ors.should(subject1).should(content1).should(fromWho1).should(toWho1).should(attachmentname1).should(attachmentContent1);
		mustQuery.must(ors);

		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email").setQuery(mustQuery);

		searchRequestBuilder = searchRequestBuilder.addAggregation(AggregationBuilders.terms("agg1(聚类返回时根据此key获取聚类结果)")
				.size(1000)/* 返回1000条聚类结果 */.field("要在文档中聚类的字段，如果是嵌套的则用点连接父子字段，如【person.company.name】"));
		int pageIndex = 1;// 页数
		int pageSize = 10;// 每页
		String pageno = request.getParameter("pageno");
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索

		SearchHit[] hits = searchResponse.getHits().getHits();
		List<allesDTO> allesDTOList = new ArrayList<allesDTO>();

		for (SearchHit searchHit : hits) {
			allesDTO allesDTO = new allesDTO();
			Map<String, Object> source = searchHit.getSource();
			String subject = (String) source.get("subject");
			String fromWho = (String) source.get("fromWho");
			String toWho = (String) source.get("toWho");
			String downloadUrl = (String) source.get("file_download_url");
			String date = (String) source.get("date");
			String caseName = (String) source.get("caseName");
			String content = (String) source.get("content");
			String attachmentname = (String) source.get("attachmentname");
			String subjects1 = subject.replace(big_search_box,
					"<font style='color: red;background-color: yellow;'>" + big_search_box + "</font>");
			allesDTO.setSubject(subject);
			String fromWhos1 = fromWho.replace(big_search_box,
					"<font style='color: red;background-color: yellow;'>" + big_search_box + "</font>");
			allesDTO.setFromWho(fromWho);
			String toWhos1 = toWho.replace(big_search_box,
					"<font style='color: red;background-color: yellow;'>" + big_search_box + "</font>");
			allesDTO.setToWho(toWho);
			String dates1 = date.replace(big_search_box,
					"<font style='color: red;background-color: yellow;'>" + big_search_box + "</font>");
			allesDTO.setDate(date);
			String contents1 = content.replace(big_search_box,
					"<font style='color: red;background-color: yellow;'>" + big_search_box + "</font>");
			allesDTO.setContent(content);
			allesDTO.setCaseName(caseName);
			allesDTO.setFiledownloadurl(downloadUrl);
			allesDTO.setAttachmentname(attachmentname);
			allesDTOList.add(allesDTO);

		}
		HashMap<Object, Object> hashMap = new HashMap<>();
		//	hashMap.put("caseidlist", caseidlist);
		hashMap.put("allesDTOList", allesDTOList);
		hashMap.put("total", searchResponse.getHits().getTotalHits());

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(hashMap));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 大搜索文档查询
	@RequestMapping(value = "/admin/SearchResultdoc.php")
	public void SearchResultdoc(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");

		String big_search_box = request.getParameter("big_search_box");

		String caseidStr = request.getParameter("caseidStr");
		String[] caseids = { "" };
		if (caseidStr != null && !"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

		QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(big_search_box)// .escape(true)//escape
				// 转义
				// 设为true，避免搜索[]、结尾为!的关键词时异常
				// 但无法搜索*
				.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
		mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件

		if (!"".equals(caseidStr)) {
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("doc").setTypes("docType").setQuery(mustQuery);
		searchRequestBuilder = searchRequestBuilder.addAggregation(AggregationBuilders.terms("agg1(聚类返回时根据此key获取聚类结果)")
				.size(1000)/* 返回1000条聚类结果 */.field("要在文档中聚类的字段，如果是嵌套的则用点连接父子字段，如【person.company.name】"));
		int pageIndex = 1;// 页数
		int pageSize = 8;// 每页
		String pageno = request.getParameter("pageno");
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索
		// String string = searchResponse.toString();

		SearchHit[] hits = searchResponse.getHits().getHits();
		List<allesDTO> allesDTOList = new ArrayList<allesDTO>();
		for (SearchHit searchHit : hits) {
			allesDTO allesDTO = new allesDTO();
			Map<String, Object> source = searchHit.getSource();
			// String id = searchHit.getId();
			String esDocId = searchHit.getId();

			String fileName = (String) source.get("fileName");
			int fileSize = (int) source.get("fileSize");
			String caseID = (String) source.get("caseID");
			String docType = (String) source.get("docType");
			String editDate = (String) source.get("editDate");
			String caseName = (String) source.get("caseName");
			String file_download_url = (String) source.get("file_download_url");
			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

			HighlightField highlightfileName = highlightFields.get("fileName");
			HighlightField highlightfileSize = highlightFields.get("fileSize");
			HighlightField highlightcaseID = highlightFields.get("caseID");
			HighlightField highlightdocType = highlightFields.get("docType");
			HighlightField highlighteditDate = highlightFields.get("editDate");
			HighlightField highlightcaseName = highlightFields.get("caseName");
			HighlightField highlighturl = highlightFields.get("file_download_url");

			if (highlightfileName != null) {
				Text[] titleTexts = highlightfileName.fragments();
				String fileName2 = "";
				for (Text text : titleTexts) {
					fileName2 += text;
				}
				allesDTO.setFileName(fileName2);
			} else {
				allesDTO.setFileName(fileName);
			}
			if (highlightfileSize != null) {
				Text[] titleTexts = highlightfileSize.fragments();
				int fileSize2 = 0;
				for (Text text : titleTexts) {
					fileSize2 += Integer.parseInt(text.toString());
				}
				allesDTO.setFileSize(fileSize2);
			} else {
				allesDTO.setFileSize(fileSize);
			}
			if (highlightdocType != null) {
				Text[] titleTexts = highlightdocType.fragments();
				String docType2 = "";
				for (Text text : titleTexts) {
					docType2 += text;
				}
				allesDTO.setDocType(docType2);
			} else {
				allesDTO.setDocType(docType);
			}
			if (highlighteditDate != null) {
				Text[] titleTexts = highlighteditDate.fragments();
				String editDate2 = "";
				for (Text text : titleTexts) {
					editDate2 += text;
				}
				allesDTO.setEditDate(editDate2);
			} else {
				allesDTO.setEditDate(editDate);
			}
			if (highlighturl != null) {
				Text[] titleTexts = highlighturl.fragments();
				String url2 = "";
				for (Text text : titleTexts) {
					url2 += text;
				}
				allesDTO.setFile_download_url(url2);
			} else {
				allesDTO.setFile_download_url(file_download_url);
			}
			if (highlightcaseName != null) {
				Text[] titleTexts = highlightcaseName.fragments();
				String caseName2 = "";
				for (Text text : titleTexts) {
					caseName2 += text;
				}
				allesDTO.setCaseName(caseName2);
			} else {
				allesDTO.setCaseName(caseName);
			}
			if (highlightcaseID != null) {
				Text[] titleTexts = highlightcaseID.fragments();
				String caseID2 = "";
				for (Text text : titleTexts) {
					caseID2 += text;
				}
				allesDTO.setCaseID(caseID2);
			} else {
				allesDTO.setCaseID(caseID);
			}
			allesDTO.setEsDocId(esDocId);
			allesDTOList.add(allesDTO);
		}
		HashMap<Object, Object> hashMap = new HashMap<>();
		//	hashMap.put("caseidlist", caseidlist);
		hashMap.put("allesDTOList", allesDTOList);
		hashMap.put("total", searchResponse.getHits().getTotalHits());
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(hashMap));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 大搜索选择案件
	@RequestMapping(value = "/admin/getmohucase.php")
	public void getCase(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		// String big_search_box = request.getParameter("big_search_box"); //
		// 接收输入写入的值进行模糊查询
		// String caseid = request.getParameter("caseID");
		String[] caseID = request.getParameterValues("caseID");
		String casenumorname = request.getParameter("casenumorname");
		List<Caseinfo> casList2 = new ArrayList<>();

		if (caseID != null || !caseID.equals("")) {
			for (int i = 0; i < caseID.length; i++) {
				String id = caseID[i];
				id.replace(" ", "");
				Caseinfo cas = new Caseinfo();
				cas.setId(Integer.parseInt(id));
				List<Caseinfo> listfromMysql = sqlDao.getListfromMysql(cas);
				if (listfromMysql.size() > 0) {
					Caseinfo caseinfo = listfromMysql.get(0);
					if (caseinfo.getCaseName().indexOf(casenumorname) != -1) {
						int flag = 0;
						for (Caseinfo caseinfo2 : casList2) {
							if (caseinfo2.getId() == caseinfo.getId()) {
								flag = 1;
							}
						}
						if (flag == 0) {
							casList2.add(caseinfo);
						}

					}
					if (caseinfo.getCaseNum().indexOf(casenumorname) != -1) {
						int flag = 0;
						for (Caseinfo caseinfo2 : casList2) {
							if (caseinfo2.getId() == caseinfo.getId()) {
								flag = 1;
							}
						}
						if (flag == 0) {
							casList2.add(caseinfo);
						}
					}
				}
			}
		}

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(casList2));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 电子邮件工作台
	@RequestMapping(value = "/admin/email_workbench.php")
	public String email_workbench(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {
		actionLog((String) session.getAttribute("userName"), "查看", "邮件挖掘");
		return "email_workbench";
	}
	//标记管理
	@RequestMapping(value = "/admin/signManage.php")
	public String signManage(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {
		Caseinfo cas = new Caseinfo();
		User identify = (User) session.getAttribute("user");
		if (identify == null || !identify.getPrivilege().equals("科员")) { // 不包含时间条件下判断非科员的搜索总范围
			// 判断非科员、游客、局长（即其他职位），根据科室显示对应的搜索总范围
			if (identify != null && !identify.getPrivilege().equals("局长")) {
				cas.setSection(identify.getSection());
			}

		}
		actionLog((String) session.getAttribute("userName"), "查看", "标记管理");
		return "signManage";
	}
	// 电子邮件工作台 文档在线预览
	/*@RequestMapping(value = "/admin/lookOnline.php")
	public void lookOnline(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, IOException {

		String tomPath = "/temp/";
		File filePath = new File(tomPath);
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		// 获取hdfs文件路径
		String fileUrl = request.getParameter("lookdocpath");
		logger.info("----解码前fileUrl-----" + fileUrl);
		// 进行解码
		fileUrl = URLDecoder.decode(fileUrl, "utf-8");

		logger.info("----解码后fileUrl-----" + fileUrl);
		String[] temp1 = fileUrl.split(",");
		logger.info("-------temp1--------" + Arrays.toString(temp1));

		String[] temp11 = fileUrl.split("/");
		logger.info("-----temp11------" + Arrays.toString(temp11));

		String fileName = fileUrl.split("/")[temp11.length - 1]; // 文件名称
		logger.info("----fileName----" + fileName);

		String fileType = fileName.split("\\.")[1];
		logger.info("====fileType=====" + fileType);

		String cmd = "hadoop fs -copyToLocal " + fileUrl + " /temp/";
		Process process = Runtime.getRuntime().exec(cmd);
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String localpath = "/temp/" + fileName;
		logger.info("文件是否存在：" + new File("/temp/" + fileName).exists());

//		String localpath="d:/36.PPT";
		//文件路径
		//先复制一份
		String newName=System.currentTimeMillis()+"";
		String targetPath=FilenameUtils.getFullPath(localpath)+newName+"."+FilenameUtils.getExtension(localpath);

		FileUtils.copyFile(new File(localpath), new File(targetPath));
		localpath = targetPath;
		logger.info(localpath + "-文件是否存在：" + new File(localpath).exists());
		String extension = FilenameUtils.getExtension(localpath);
		try {
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
			}else if(extension.contains("xls") || extension.contains("XLS")){
				//转换excel
				ExcelToHtmlUtil.excelToHtml(sourcePath, tartPath);
			}else if(extension.equalsIgnoreCase("docx")||extension.equalsIgnoreCase("doc")){
				//转换word
				long time = System.currentTimeMillis();
				String htmlPath = request.getSession().getServletContext().getRealPath("/preview") + "/" + time + "/";
				if (!new File(htmlPath).exists()) {
					new File(htmlPath).mkdir();
				}
				if(extension.equalsIgnoreCase("docx")){
					DocToHtml.convertDocxToHtml(sourcePath, htmlPath + time+ ".html");
				}else{
					DocToHtml.convertDocToHtml(sourcePath, htmlPath + time+ ".html",time+"");
				}
				logger.info("文件名称:" + htmlPath + time + ".html");
				response.sendRedirect("/BDCloud/preview/" + time + "/" + time + ".html");
				return;
			}else if(extension.equalsIgnoreCase("ppt") || extension.equalsIgnoreCase("pptx")){
				//转换ppt
				PPTtoHtmlUtil.pptToHtml(sourcePath, tartPath);
			}
			response.sendRedirect("/BDCloud/preview/" + baseName + ".html");
		} catch (Exception e) {
			logger.error("send json error", e);
		}
	}*/

	//文档挖掘    文档在线预览  与大搜索方法统一
	@RequestMapping(value = "/admin/lookOnline.php")
	public void lookOnline(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, Exception {

		String tomPath = "/temp/";
		File filePath = new File(tomPath);
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		String fileUrl = request.getParameter("lookOnlinePath"); // 获取hdfs文件路径
		logger.info("文档台hdfs路径：" + fileUrl);

		String[] temp1 = fileUrl.split(",");
		logger.info("temp1---------------" + Arrays.toString(temp1));

		String[] temp11 = fileUrl.split("/");
		logger.info("temp11-----------" + temp11);

		String fileName = fileUrl.split("/")[temp11.length - 1]; // 文件名称
		logger.info("fileName--------" + fileName);

		//		String fileType = fileName.split("\\.")[1];
		String fileType111 = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		String fileType = fileType111.split("\\.")[1];
		logger.info("=========" + fileType);


		String cmd = "hadoop fs -copyToLocal " + fileUrl + " /temp/";
		Process process = Runtime.getRuntime().exec(cmd);
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String localpath = "/temp/" + fileName;

		//		String localpath="d:/tes.txt";
		//文件路径
		//先复制一份
		String newName=System.currentTimeMillis()+"";
		String targetPath=FilenameUtils.getFullPath(localpath)+newName+"."+FilenameUtils.getExtension(localpath);

		FileUtils.copyFile(new File(localpath), new File(targetPath));
		localpath = targetPath;
		logger.info(localpath + "文件是否存在：" + new File(localpath).exists());
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
			baseName=baseName+".html";
		}else if(extension.contains("xls") || extension.contains("XLS")){
			//转换excel
			ExcelToHtmlUtil.excelToHtml(sourcePath, tartPath);

			baseName=baseName+".html";
		}else if(extension.equalsIgnoreCase("docx") || extension.equalsIgnoreCase("doc")){
			//转换word

			long time = System.currentTimeMillis();
			String htmlPath = request.getSession().getServletContext().getRealPath("/preview") + "/" + time + "/";
			if (!new File(htmlPath).exists()) {
				new File(htmlPath).mkdir();
			}
			if(extension.equalsIgnoreCase("docx")){
				DocToHtml.convertDocxToHtml(sourcePath, htmlPath + time+ ".html");
			}else{
				DocToHtml.convertDocToHtml(sourcePath, htmlPath + time+ ".html",time+"");
			}
			logger.info("文件名称:" + htmlPath + time + ".html");
			baseName = time + "/" + time + ".html";
			logger.info("baseName::" + baseName);
		}else if(extension.equalsIgnoreCase("ppt") || extension.equalsIgnoreCase("pptx")){
			//转换ppt
			PPTtoHtmlUtil.pptToHtml(sourcePath, tartPath);
			baseName = baseName + ".html";
		}
		logger.info("文档预览路径：" + baseName);
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

	// txt文档在线预览
	@RequestMapping(value = "/admin/lookOnlineTxt.php")
	public void lookOnlineTxt(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, IOException {
		// 读取文本
		String id = request.getParameter("docId");
		logger.info("doc id：" + id);
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("doc").setTypes("docType");
		SearchResponse actionGet = searchRequestBuilder.setQuery(QueryBuilders.termsQuery("_id", id)).execute()
				.actionGet();
		SearchHit searchHit = actionGet.getHits().getHits()[0];
		// 拿到的内容生成一个新的html页面
		StringBuffer buffer = new StringBuffer();
		buffer.append("<!DOCTYPE html><html><head><meta charset=\"utf-8\"> <title>文本预览</title>"
				+ "<style>.mydiv{ width:1024px;height:768; position:absolute;  left:50%; top:50%;  margin:-100px 0 0 -150px }</style>"
				+ "<script src=\"../template/js/jquery/jquery-2.0.3.min.js\"></script>"
				+ "</head><body><div class=\"mydiv\">").append(searchHit.getSource().get("content"))
		.append("</div>")
		.append("<script>$(function(){ $(window).resize();});"
				+ "$(window).resize(function(){$(\".mydiv\").css({ position: \"absolute\", left: ($(window).width() - $(\".mydiv\").outerWidth())/2, top: ($(window).height() - $(\".mydiv\").outerHeight())/2 });});"
				+ "</script>")
		.append("</body></html>");
		String realPath = request.getSession().getServletContext().getRealPath("/preview");
		String fileName = System.currentTimeMillis() + "";
		IOUtils.write(buffer.toString(), new FileOutputStream(new File(realPath + "/" + fileName + ".html")));
		logger.info("生成文档路径:\r\n" + realPath + "/" + fileName + ".html");
		response.sendRedirect("/BDCloud/preview/" + fileName + ".html");
	}

	// 大搜索的txt文档在线预览
	@RequestMapping(value = "/admin/lookOnlineTxtBigData.php")
	public void lookOnlineTxtBigData(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, IOException {
		// 读取文本
		String id = request.getParameter("docId");
		logger.info("doc id：" + id);
		// 截取字符串
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("doc").setTypes("docType");
		SearchResponse actionGet = searchRequestBuilder.setQuery(QueryBuilders.termsQuery("_id", id)).execute()
				.actionGet();
		SearchHit searchHit = actionGet.getHits().getHits()[0];
		// 拿到的内容生成一个新的html页面
		StringBuffer buffer = new StringBuffer();
		buffer.append("<!DOCTYPE html><html><head><meta charset=\"utf-8\"> <title>文本预览</title>"
				+ "<style>.mydiv{ width:1024px;height:768; position:absolute;  left:50%; top:50%;  margin:-100px 0 0 -150px }</style>"
				+ "<script src=\"../template/js/jquery/jquery-2.0.3.min.js\"></script>"
				+ "</head><body><div class=\"mydiv\">").append(searchHit.getSource().get("content"))
		.append("</div>")
		.append("<script>$(function(){ $(window).resize();});"
				+ "$(window).resize(function(){$(\".mydiv\").css({ position: \"absolute\", left: ($(window).width() - $(\".mydiv\").outerWidth())/2, top: ($(window).height() - $(\".mydiv\").outerHeight())/2 });});"
				+ "</script>")
		.append("</body></html>");
		String realPath = request.getSession().getServletContext().getRealPath("/preview");
		String fileName = System.currentTimeMillis() + "";
		IOUtils.write(buffer.toString(), new FileOutputStream(new File(realPath + "/" + fileName + ".html")));
		logger.info("生成文档路径:\r\n" + realPath + "/" + fileName + ".html");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			String result = "{\"res\":\"" + fileName + ".html" + "\"}";
			writer.write(result);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	// 大搜素
	@RequestMapping(value = "/getGimps.php")
	public String Getgimps(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map<String, Object> map) throws IOException, InterruptedException {
		actionLog((String) session.getAttribute("userName"), "查看", "智能检索");
		return "gimps";
	}

	// 大搜索页面 文档在线预览
	@RequestMapping(value = "/admin/lookOnlineOfDoc.php")
	public void lookOnlineOfDoc(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, Exception {

		logger.info("大搜索页面lookOnlineOfSearchpath:" + request.getParameter("lookOnlineOfSearchpath"));
		String tomPath = "/temp/";
		File filePath = new File(tomPath);
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		String fileUrl = request.getParameter("lookOnlineOfSearchpath"); // 获取hdfs文件路径
		logger.info("大搜索hdfs路径：" + fileUrl);
		logger.info("fileUrl---------" + fileUrl);

		logger.info(fileUrl);
		String[] temp1 = fileUrl.split(",");
		logger.info("temp1---------------" + Arrays.toString(temp1));

		String[] temp11 = fileUrl.split("/");
		logger.debug("temp11-----------" + temp11);

		String fileName = fileUrl.split("/")[temp11.length - 1]; // 文件名称
		logger.debug("fileName--------" + fileName);

		//		String fileType = fileName.split("\\.")[1];
		String fileType111 = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		String fileType = fileType111.split("\\.")[1];
		logger.info("=========" + fileType);

		String cmd = "hadoop fs -copyToLocal " + fileUrl + " /temp/";
		Process process = Runtime.getRuntime().exec(cmd);
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String localpath = "/temp/" + fileName;

		//		String localpath="d:/2.PPTX";
		//文件路径
		//先复制一份
		String newName=System.currentTimeMillis()+"";
		String targetPath=FilenameUtils.getFullPath(localpath)+newName+"."+FilenameUtils.getExtension(localpath);

		FileUtils.copyFile(new File(localpath), new File(targetPath));
		localpath = targetPath;
		logger.info(localpath + "文件是否存在：" + new File(localpath).exists());
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
			baseName=baseName+".html";
		}else if(extension.contains("xls") || extension.contains("XLS")){
			//转换excel
			ExcelToHtmlUtil.excelToHtml(sourcePath, tartPath);

			baseName=baseName+".html";
		}else if(extension.equalsIgnoreCase("docx") || extension.equalsIgnoreCase("doc")){
			//转换word

			long time = System.currentTimeMillis();
			String htmlPath = request.getSession().getServletContext().getRealPath("/preview") + "/" + time + "/";
			if (!new File(htmlPath).exists()) {
				new File(htmlPath).mkdir();
			}
			if(extension.equalsIgnoreCase("docx")){
				DocToHtml.convertDocxToHtml(sourcePath, htmlPath + time+ ".html");
			}else{
				DocToHtml.convertDocToHtml(sourcePath, htmlPath + time+ ".html",time+"");
			}
			logger.info("文件名称:" + htmlPath + time + ".html");
			baseName = time + "/" + time + ".html";
			logger.info("baseName::" + baseName);
		}else if(extension.equalsIgnoreCase("ppt") || extension.equalsIgnoreCase("pptx")){
			//转换ppt
			PPTtoHtmlUtil.pptToHtml(sourcePath, tartPath);
			baseName = baseName + ".html";
		}
		logger.info("大搜索预览路径：" + baseName);
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

	// 大搜索结果
	@RequestMapping(value = "/SearchResults.php")
	public String SearchResults(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map<String, Object> map) throws IOException, InterruptedException {
		String big_search_box = request.getParameter("big_search_box");
		// request.setAttribute("big_search_box", big_search_box);
		String typess = request.getParameter("typess");
		session.setAttribute("typess", typess);
		session.setAttribute("big_search_boxx", big_search_box);
		actionLog((String) session.getAttribute("userName"), "搜索", "智能检索 大搜索："+big_search_box);
		return "search_result";
	}

	@RequestMapping(value = "/admin/paper_work_list.php")
	public String paper_work_list(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map<String, Object> map) throws IOException, InterruptedException {
		actionLog((String) session.getAttribute("userName"), "查看", "文档挖掘");
		return "paper_work_list";
	}

	@RequestMapping(value = "/admin/pdf_searcher.php")
	public String pdf_searcher(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map<String, Object> map) throws IOException, InterruptedException {

		return "pdf_searcher";
	}

	@RequestMapping(value = "/admin/pdf_searcher_result.php")
	public String pdf_searcher_result(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map<String, Object> map) throws IOException, InterruptedException {

		return "pdf_searcher_result";
	}

	@RequestMapping(value = "/admin/imgworkbench.php")
	public String imgworkbench(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "imgworkbench";
	}

	// 点击左侧图片GPS打开页面
	@RequestMapping(value = "/admin/newDemoGPS.php")
	public String newDemoGPS(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "imgGPS";
	}

	/**
	 * 实现图片GPS的精准定位
	 * 
	 * @param request
	 * @param map
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/pictureGPS.php")
	public void pictureGPS(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws Exception {
		String picPath1 = request.getParameter("pUrl");
		int index = picPath1.indexOf("picture/");
		String subpicPath = picPath1.substring(index + "picture/".length());
		String picPath = "/emaildata/" + subpicPath;
		//String picPath = "D:/beidouyun_Img/emaildata/" + subpicPath;//  windows下的路径	

		// 进行url解码
		picPath = UrlCodeUtil.urlDecode(picPath);
		if (StringUtils.isNotEmpty(picPath)) {
			// request.setAttribute("latitude", "latitude");
			// request.setAttribute("longitude", "longitude");
			File picFile = new File(picPath);
			Metadata metadata;
			String Longitude = null;
			String Latitude = null;
			String brand = null; // 商家
			String model = null; // 型号
			String shootTool = null; // 拍摄工具信息
			String date_photograph = null; // 拍摄日期
			Double longitude = 0.0;
			Double latitude = 0.0;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

			try {
				metadata = JpegMetadataReader.readMetadata(picFile);
				Directory gps = metadata.getDirectory(GpsDirectory.class);

				Directory exif = metadata.getDirectory(ExifDirectory.class);
				Iterator tags = exif.getTagIterator();
				while (tags.hasNext()) {
					Tag tag = (Tag) tags.next();
					if (tag.getTagName().equals("Make")) {
						brand = tag.getDescription();
					}
					if (tag.getTagName().equals("Model")) {
						model = tag.getDescription();
					}
					if (tag.getTagName().equals("Date/Time")) {
						Date d = null;
						try {
							d = dateFormat.parse(tag.getDescription());
						} catch (Exception e) {
							e.printStackTrace();
						}
						date_photograph = dateFormat2.format(d);
					}
				}
				shootTool = brand + ":" + model;
				int n = gps.getTagCount();
				Iterator tags3 = gps.getTagIterator();
				while (tags3.hasNext()) {
					Tag tag = (Tag) tags3.next();
					if (tag.getTagName().equals("GPS Latitude")) {
						Latitude = tag.getDescription();
					}
					if (tag.getTagName().equals("GPS Longitude")) {
						Longitude = tag.getDescription();
					}
				}

				if (Latitude != null && Longitude != null) {
					Double du = Double.parseDouble(Latitude.substring(0, Latitude.indexOf("\"")).trim());
					String f = Latitude.substring(Latitude.indexOf("\"") + 1, Latitude.indexOf("'")).trim();
					Double fen = Double.parseDouble(f);
					String m = Latitude.substring(Latitude.indexOf("'") + 1, Latitude.length()).trim();
					Double miao = Double.parseDouble(m);
					latitude = du + fen / 60 + miao / 60 / 60;
					logger.info("latitude：" + latitude);

					Double du2 = Double.parseDouble(Longitude.substring(0, Longitude.indexOf("\"")).trim());
					String f2 = Longitude.substring(Longitude.indexOf("\"") + 1, Longitude.indexOf("'")).trim();
					Double fen2 = Double.parseDouble(f2);
					String m2 = Longitude.substring(Longitude.indexOf("'") + 1, Longitude.length()).trim();
					Double miao2 = Double.parseDouble(m2);
					longitude = du2 + fen2 / 60 + miao2 / 60 / 60;
					logger.info("longitude：" + longitude);
				}
				// map.put("picname", picName);
				map.put("latitude", latitude);
				map.put("longitude", longitude);
				map.put("shootTool", shootTool);
				map.put("date_photograph", date_photograph);

			} catch (JpegProcessingException e) {
				e.printStackTrace();
			} catch (MetadataException e) {
				e.printStackTrace();
			}
		}
		actionLog((String) session.getAttribute("userName"), "查看", "图片GPS精准定位");
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

	// 执行分布式搜索文档工作台访问并处理数据
	@RequestMapping(value = "/admin/getEvidence.php")
	public void getEvidence(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 10;// 每页个数

		// 按条件查询
		String evName = (String) request.getParameter("evName");// 文件关键词
		String caseName = (String) request.getParameter("caseName");// 案件名
		String evStatus = (String) request.getParameter("evStatus");// 文件类型
		String sortType = (String) request.getParameter("sortType");// 排序类型
		String pageno = request.getParameter("pageno");
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		Caseinfo cas = new Caseinfo();
		User user = (User) session.getAttribute("user");
		String roleName="" ;
		if(user != null){
			String userrole = user.getUserrole();
			Role role = new Role();
			role.setId(Integer.parseInt(userrole));
			List<Role> roleList = sqlDao.getListfromMysql(role);
			if(roleList.size()>0){
				Role role2 = roleList.get(0);
				roleName = role2.getDataScope();
			}
		}
		if (roleName.equals("科室数据") ) {// 科长
			cas.setSection(user.getSection()+"/"+user.getUsername());
		}else if (roleName.equals("部门数据") ) {// 部长
			cas.setDepartment(user.getPartment());
		} else if (roleName.equals("个人数据") ) {// 科员
			cas.setUserName(user.getUsername());
		} else if (roleName.equals("所有数据")) {// 局长
			cas.setId(-1);
		} else {
			cas.setId(-2);
		}
		//int caseflag = 0;
		List<Caseinfo> casList = sqlDao.getListfromMysqlLikeOR(cas);
		/*SuspectInfo suspectInfo = new SuspectInfo();*/
		ArrayList<Integer> caseidList = new ArrayList<Integer>();
		User users=new User();

		for (Caseinfo caseinfo : casList) {

			caseidList.add(caseinfo.getId());
		}
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();

		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

		// caseid集合
		/*caseidList.size()>0*/
		//if (caseidList.size()>0) {
		mustQuery.must(QueryBuilders.termsQuery("caseID", caseidList));
		//}

		if (evStatus != null && !"".equals(evStatus)) {
			if("全部"==evStatus || "全部".equals(evStatus)){
				//不做任何操作
			}else{
				mustQuery.must(QueryBuilders.matchPhraseQuery("docType", evStatus));// 添加文件类型查询条件
			}
		}
		// 组合 模糊查询 should
		if (evName != null && !"".equals(evName)) {
			//mustQuery.must(QueryBuilders.matchPhraseQuery("fileName", evName));

			mustQuery.must( QueryBuilders.queryStringQuery(evName)
					.defaultOperator(QueryStringQueryBuilder.Operator.AND));   //周武智加的文档挖掘搜索全字段

		}
		if (caseName != null && !"".equals(caseName)) {
			// QueryBuilder qb = QueryBuilders.regexpQuery("caseName",
			// caseName);
			mustQuery.must(QueryBuilders.matchPhraseQuery("caseName", caseName));
			// mustQuery.must(qb);
		}

		/*
		 * if(evName!=null && caseName!=null || evName.equals("") &&
		 * caseName.equals("")){ //QueryBuilder qb =
		 * QueryBuilders.termsQuery("caseName", caseName);
		 * 
		 * mustQuery.must(QueryBuilders.termsQuery("caseName", caseName)); }
		 */
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("doc").setTypes("docType")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */
				.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果

				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>")
				.setFrom((pageIndex - 1) * pageSize).setSize(pageSize);
		/*
		 * if (evName != null && !"".equals(evName)) {
		 * searchRequestBuilder.setQuery(QueryBuilders.matchQuery("fileName","*"
		 * +evName+"*").operator(Operator.AND)) ; } if (caseName != null &&
		 * !"".equals(caseName)) {
		 * searchRequestBuilder.setQuery(QueryBuilders.matchQuery("caseName","*"
		 * +caseName+"*").operator(Operator.AND)) ; }
		 */

		// 获取排序的类型
		String sortConditon = "";
		if (sortType.equals("创建时间")) {
			sortConditon = "createDate";
		} else if (sortType.equals("修改时间")) {
			sortConditon = "viewDate";
		} else if (sortType.equals("文件大小")) {
			sortConditon = "fileSize";
		} else if (sortType.equals("访问时间")) {
			sortConditon = "editDate";
		} else if (sortType.equals("文件名")) {
			sortConditon = "fileName";
		} else if (sortType.equals("文件类型")) {
			sortConditon = "docType";
		}
		SearchResponse searchResponse = null;
		if (sortConditon.equals("fileSize")) {
			searchResponse = searchRequestBuilder// 分页起始位置（跳过开始的n个）
					.addSort(sortConditon, SortOrder.ASC)// 按类型排序

					.execute().actionGet();// 执行搜索
		} else if (sortConditon.equals("fileName")) {
			searchResponse = searchRequestBuilder// 分页起始位置（跳过开始的n个）
					.addSort("fileNameSort", SortOrder.ASC)// 按类型排序
					// .addSort(SortBuilders.fieldSort(sortConditon).order(SortOrder.ASC))
					.execute().actionGet();// 执行搜索
		} else {
			searchResponse = searchRequestBuilder// 分页起始位置（跳过开始的n个）
					.addSort(sortConditon, SortOrder.ASC)// 按类型排序
					// .addSort(SortBuilders.fieldSort(sortConditon).order(SortOrder.DESC))
					.execute().actionGet();// 执行搜索
		}
		String string = searchResponse.toString();

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(string);
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
	 * 从HDFS文件系统下载文档
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "admin/downloadDOC.php")
	public void downloadDOC(HttpServletRequest request, HttpServletResponse response)
			throws IOException, InterruptedException {

		String tomPath = "/temp/";
		File filePath = new File(tomPath);
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		String docpath = request.getParameter("docpath");
		logger.info("---------" + docpath);

		// 进行解码
		docpath = URLDecoder.decode(docpath, "utf-8");

		logger.info("----解码后fileUrl-----" + docpath);

		String browser = request.getHeader("user-agent");
		String[] temp1 = docpath.split(",");
		logger.info("---------------" + temp1);
		if (temp1.length == 1) {// 单个文件下载
			String[] temp11 = docpath.split("/");

			String temp2 = docpath.split("/")[temp11.length - 1];

			String cmd = "hadoop fs -copyToLocal " + docpath + " /temp/";
			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			File temFile = null;
			try {
				temFile = new File("/temp/" + temp2);
				if (!temFile.exists()) {
					response.getWriter().write("ERROR:File Not Found");
				}
				String fileName = temp2;

				Pattern p = Pattern.compile(".*MSIE.*?;.*");
				Matcher m = p.matcher(browser);

				if (m.matches()) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", ""));
				} else {
					response.setHeader("Content-Disposition", "attachment; filename="
							+ new String(fileName.getBytes("UTF-8"), "ISO8859-1").replace(" ", ""));
				}
				response.setContentType("application/x-download");
				OutputStream ot = response.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(temFile));
				BufferedOutputStream bos = new BufferedOutputStream(ot);
				byte[] buffer = new byte[4096];
				int length1 = 0;
				while ((length1 = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, length1);
				}
				bos.close();
				bis.close();
				ot.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (temFile != null) {
					temFile.delete();
				}
			}
		} else {

			String[] file1 = new String[temp1.length];

			// 多个文件下载
			for (int i = 0; i < temp1.length; i++) {
				String moreTemp = temp1[i];
				String[] file11 = moreTemp.split("/");
				file1[i] = "/temp/" + file11[file11.length - 1];
				String cmd = "hadoop fs -copyToLocal " + moreTemp + " /temp/";
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			}

			String zipFilePath = "/temp/file.zip";
			try {
				ZipFileUtil.zipFiles(file1, zipFilePath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			File temFile = null;
			try {
				temFile = new File(zipFilePath);
				if (!temFile.exists()) {
					response.getWriter().write("ERROR:File Not Found");
				}
				String fileName = "file.zip";

				Pattern p = Pattern.compile(".*MSIE.*?;.*");
				Matcher m = p.matcher(browser);

				if (m.matches()) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", ""));
				} else {
					response.setHeader("Content-Disposition", "attachment; filename="
							+ new String(fileName.getBytes("UTF-8"), "ISO8859-1").replace(" ", ""));
				}
				response.setContentType("application/x-download");
				OutputStream ot = response.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(temFile));
				BufferedOutputStream bos = new BufferedOutputStream(ot);
				byte[] buffer = new byte[4096];
				int length1 = 0;
				while ((length1 = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, length1);
				}
				bos.close();
				bis.close();
				ot.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (temFile != null) {
					temFile.delete();
				}
			}

		}
	}

	// 执行分布式搜索pdf检索
	@RequestMapping(value = "/admin/getPdfEvidence.php")
	public void getPdfEvidence(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 10;// 每页个数

		// 按条件查询
		String evName = (String) request.getParameter("evName");// 文件名
		String evStatus = ".pdf";
		String evNum = (String) request.getParameter("evNum");// 文件关键词
		String pageno = request.getParameter("pageno");
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		if (evStatus != null && !"".equals(evStatus)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("docType", evStatus));
		}
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

		if (evNum != null && !"".equals(evNum)) {
			QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(evNum)// .escape(true)//escape
					// 转义
					// 设为true，避免搜索[]、结尾为!的关键词时异常
					// 但无法搜索*
					.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
			mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
		}

		if (evName != null && !"".equals(evName)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("fileName", evName));
			// searchRequestBuilder.setQuery(QueryBuilders.matchPhraseQuery("fileName",evName));
			// .setQuery(QueryBuilders.matchQuery("fileName",
			// "evName").operator(Operator.AND)) //根据tom分词查询name,默认or
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("doc").setTypes("docType")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

		// 获取排序的类型
		String sortConditon = "";
		sortConditon = "createDate";

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.addSort(sortConditon, SortOrder.DESC)// 按类型排序
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索
		String string = searchResponse.toString();

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(string);
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
	 * 从HDFS文件系统导出DOC文档
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "admin/exportDOC.php")
	public void exportDOC(HttpServletRequest request, HttpServletResponse response)
			throws IOException, InterruptedException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String zipname = dateFormat.format(new Date()) + "文档压缩包.zip";

		String tomPath = "/temp/";
		File filePath = new File(tomPath);
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		String docpath = request.getParameter("docpath");
		docpath = URLDecoder.decode(docpath, "utf-8");
		logger.info("---------" + docpath);

		String browser = request.getHeader("user-agent");
		String[] temp1 = docpath.split(",");
		logger.info("---------------" + temp1);
		if (temp1.length == 1) {// 单个文件下载
			String[] temp11 = docpath.split("/");

			String temp2 = docpath.split("/")[temp11.length - 1];
			String cmd = "hadoop fs -copyToLocal " + docpath + " /temp/";

			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			File temFile = null;
			try {
				temFile = new File("/temp/" + temp2);
				if (!temFile.exists()) {
					response.getWriter().write("ERROR:File Not Found");
					return;
				}
				String fileName = temp2;

				Pattern p = Pattern.compile(".*MSIE.*?;.*");
				Matcher m = p.matcher(browser);

				if (m.matches()) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", ""));
				} else {
					response.setHeader("Content-Disposition", "attachment; filename="
							+ new String(fileName.getBytes("UTF-8"), "ISO8859-1").replace(" ", ""));
				}
				response.setContentType("application/x-download");
				// response.reset();
				OutputStream ot = response.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(temFile));
				BufferedOutputStream bos = new BufferedOutputStream(ot);
				byte[] buffer = new byte[4096];
				int length1 = 0;
				while ((length1 = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, length1);
				}
				bos.close();
				bis.close();
				ot.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (temFile != null) {
					temFile.delete();
				}
			}
		} else {

			String[] file1 = new String[temp1.length];

			// 多个文件下载
			for (int i = 0; i < temp1.length; i++) {
				String moreTemp = temp1[i];
				String[] file11 = moreTemp.split("/");
				file1[i] = "/temp/" + file11[file11.length - 1];
				String cmd = "hadoop fs -copyToLocal " + moreTemp + " /temp/";
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			}

			String zipFilePath = "/temp/file.zip";
			try {
				ZipFileUtil.zipFiles(file1, zipFilePath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			File temFile = null;
			try {
				temFile = new File(zipFilePath);
				if (!temFile.exists()) {
					response.getWriter().write("ERROR:File Not Found");
				}
				// String fileName = "file.zip";
				String fileName = zipname;// "file.zip";
				Pattern p = Pattern.compile(".*MSIE.*?;.*");
				Matcher m = p.matcher(browser);

				if (m.matches()) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", ""));
				} else {
					response.setHeader("Content-Disposition", "attachment; filename="
							+ new String(fileName.getBytes("UTF-8"), "ISO8859-1").replace(" ", ""));
				}
				response.setContentType("application/x-download");
				OutputStream ot = response.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(temFile));
				BufferedOutputStream bos = new BufferedOutputStream(ot);
				byte[] buffer = new byte[4096];
				int length1 = 0;
				while ((length1 = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, length1);
				}
				bos.close();
				bis.close();
				ot.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (temFile != null) {
					temFile.delete();
				}
			}
		}
	}

	// 执行分布式话单数据检索
	@RequestMapping(value = "/admin/getTicket.php")
	public void getTicket(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 5;// 每页个数

		// 按条件查询
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间

		String suspectNumsStr = request.getParameter("suspectNumsStr");// 嫌疑人手机号list
		String pageno = request.getParameter("pageno");
		// String suspectNums="";

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
			Caseinfo caseinfo2 = new Caseinfo();
			List<Caseinfo> caseinfoList = sqlDao.getListfromMysql(caseinfo2);
			Caseinfo caseinfo = caseinfoList.get(0);
			String mainParty = caseinfo.getMainParty();
			String[] split = mainParty.split(",");
			for (String string : split) {
				SuspectInfo suspectInfo = new SuspectInfo();
				suspectInfo.setId(Integer.parseInt(string));
				List<SuspectInfo> listfromMysql = sqlDao.getListfromMysql(suspectInfo);
				if (listfromMysql.size() > 0) {
					SuspectInfo suspectInfo2 = listfromMysql.get(0);
					String suspectPhone = suspectInfo2.getSuspectPhone();
					if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
						suspectNumsStr = suspectPhone;
					} else {
						suspectNumsStr += "," + suspectPhone;
					}
				}
			}
		}

		String string = "";

		if (suspectNumsStr != null && !"".equals(suspectNumsStr)) {
			String[] suspectNums = suspectNumsStr.split(",");
			for (String suspectNum : suspectNums) {

				BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
				mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件
				// 此处为匹配所有文档
				mustQuery.must(QueryBuilders.termsQuery("localNum", suspectNums));// 添加must的条件
				// localNum字段必须为suspectNum
				if (!StringUtils.isEmpty(pageno)) {
					pageIndex = Integer.parseInt(pageno);
				}
				if (startDate != null && !"".equals(startDate)) {
					RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("startTime")
							.from(startDate + " 00:00:00").to(endDate + " 23:59:59");
					mustQuery.must(rangequerybuilder);
				}

				SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("call")
						.setTypes("callList").setQuery(mustQuery).addHighlightedField("*")
						/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
						.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

				// 获取排序的类型
				String sortConditon = "";
				sortConditon = "startTime";

				SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
						.addSort(sortConditon, SortOrder.DESC)// 按类型排序
						.setSize(pageSize)// 本次返回的文档数量
						.execute().actionGet();// 执行搜索
				string = searchResponse.toString();
			}
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			// logger.info("/admin/getTicket.php查询结果："+string);
			writer.write(string);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 通话记录统计查询
	@RequestMapping(value = "/admin/getCallLogStatistics.php")
	public void getCallLogFromEs(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 5;// 每页个数

		// 按条件查询
		String caseName13 = request.getParameter("caseName13").trim();
		String haoma = request.getParameter("haoma").trim();
		logger.info("--haoma--" + haoma);
		logger.info("--caseName13--" + caseName13);
		String caseidStr = request.getParameter("caseidStr");//案件id
		String suspectNumsStr = request.getParameter("suspectNumsStr");// 嫌疑人手机号list
		String startTime = (String) request.getParameter("startTime");// 时间范围
		String endTime = (String) request.getParameter("endTime");// 时间范围
		String pageno = request.getParameter("pageno");
		// String suspectNums="";

		String[] caseids = { "" };
		if (!"".equals(caseidStr) && caseidStr !=null) {
			caseids = caseidStr.split(" ");
		}
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		// 如果未选嫌疑人 默认查找最新数据下的嫌疑人
		if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
			suspectNumsStr="-1010101011111";//默认查不到
		}

		if (caseName13 != null && !caseName13.equals("")) {
			SuspectInfo suspectInfo5 = new SuspectInfo();
			suspectNumsStr = "";
			suspectInfo5.setSuspectName(caseName13);
			List<SuspectInfo> listfromMysql4 = sqlDao.getListfromMysql(suspectInfo5);
			if (listfromMysql4.size() > 0) {
				for (SuspectInfo suspectInfo : listfromMysql4) {
					String suspectPhone = suspectInfo.getSuspectPhone();
					if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
						suspectNumsStr = suspectPhone;
					} else {
						suspectNumsStr += "," + suspectPhone;
					}
				}

			} else {
				suspectNumsStr = caseName13;
			}

		}
		long totalHits=0;
		String[] suspectNums = suspectNumsStr.split(",");
		SuspectInfo suspectInfo3 = new SuspectInfo();
		List<SuspectInfo> listfromMysql3 = sqlDao.getListfromMysql(suspectInfo3);

		List<String> list = new ArrayList<>();
		Map<Object, Object> map = new HashMap<>();
		ArrayList<TicketdDTO> caseidlist = new ArrayList<TicketdDTO>();


		CallListBean call=new CallListBean();
		call.setLocalNum(suspectNumsStr);
		call.setCaseID(caseidStr);
		if (haoma != null && !"".equals(haoma)) {
			call.setDialNumber(haoma);
		}
		List<CallListBean> callList;
		List<CallListBean> callList2;

		if (startTime != null && !"".equals(startTime)){
			callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call, startTime+ 
					" 00:00:00", endTime+ " 23:59:59", (pageIndex - 1) * pageSize, pageSize);
			totalHits=sqlDao.getcountfromMysqlLike_callLog(call,startTime+ 
					" 00:00:00", endTime+ " 23:59:59");
		}
		else{
			callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call,"","", (pageIndex - 1) * pageSize, pageSize);
			totalHits=sqlDao.getcountfromMysqlLike_callLog(call,"", "");
		}

		if (startTime != null && !"".equals(startTime))
			callList2=sqlDao.getOrderListfromMysqlLikTime_callLog(call, startTime+ 
					" 00:00:00", endTime+ " 23:59:59", 0, 10000);
		else
			callList2=sqlDao.getOrderListfromMysqlLikTime_callLog(call,"","", 0, 10000);



		//totalHits = searchResponse.getHits().getTotalHits();

		// 获取排序的类型
		//			String sortConditon = "";
		//			sortConditon = "startTime";

		//			SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
		//					.addSort(sortConditon, SortOrder.DESC)// 按类型排序
		//					.setSize(pageSize)// 本次返回的文档数量
		//					.execute().actionGet();// 执行搜索


		//			SearchResponse searchResponse3 = searchRequestBuilder.setFrom(0)// 分页起始位置（跳过开始的n个）
		//					.addSort(sortConditon, SortOrder.DESC)// 按类型排序
		//					.setSize(10000)// 本次返回的文档数量
		//					.execute().actionGet();// 执行搜索
		//	String string = searchResponse.toString();

		//	SearchHit[] hits = searchResponse.getHits().getHits();
		//			ArrayList<SearchHit[]> arrayList = new ArrayList<SearchHit[]>();
		//			SearchHit[] hits1 = searchResponse3.getHits().getHits();
		//			arrayList.add(hits1);
		//			long totalHits2 = searchResponse3.getHits().getTotalHits()/10000;
		//			for(int i=1;i<=totalHits2;i++){
		//				searchResponse3 = searchRequestBuilder.setQuery(mustQuery).addSort(sortConditon, SortOrder.DESC).setFrom(i*10000)// 分页起始位置（跳过开始的n个）
		//						.setSize(10000).execute().actionGet();
		//	SearchHit[] hits2  = searchResponse.getHits().getHits();
		//	arrayList.add(hits2);
		//}



		for (CallListBean call1 : callList2) {

			TicketdDTO ticktd2 = new TicketdDTO();
			//Map<String, Object> source = searchHit2.getSource();
			String method = call1.getMethod();//(String) source.get("method");
			String name =call1.getName();// (String) source.get("name");
			String startTime2 =call1.getStartTime();// (String) source.get("startTime");
			String localNum =call1.getLocalNum();// (String) source.get("localNum");
			String dialNumber =call1.getDialNumber(); //(String) source.get("dialNumber");
			String callDuration =call1.getCallDuration();// (String) source.get("callDuration");
			String position =call1.getPosition();// (String) source.get("position");
			ticktd2.setMethod(method);
			ticktd2.setCallDuration(callDuration);
			ticktd2.setDialNumber(dialNumber);
			ticktd2.setLocalNum(localNum);
			ticktd2.setName(name);
			ticktd2.setStartTime(startTime2);
			ticktd2.setPosition(position);
			caseidlist3.add(ticktd2);

		}






		for (CallListBean call2 : callList) {
			TicketdDTO ticktd = new TicketdDTO();
			//Map<String, Object> source = searchHit2.getSource();
			String method =call2.getMethod();// (String) source.get("method");
			String name =call2.getName();// (String) source.get("name");
			String startTime2 =call2.getStartTime();// (String) source.get("startTime");


			String localNum =call2.getLocalNum();// (String) source.get("localNum");
			String dialNumber =call2.getDialNumber();// (String) source.get("dialNumber");


			String callDuration =call2.getCallDuration();// (String) source.get("callDuration");
			//String position = (String) source.get("position");
			/////////////////// 把地址由基站ID转为地址////////////////////////
			String baseId[] =call2.getPosition().split(",");// ((String) source.get("position")).split(",");
			String position = null;
			String res="";
			if (baseId.length == 2 && baseId[0] != null && !"".equals(baseId[0])){
				if(isConnNet)
					res=PositionUtil.getAddressByCell(baseId[0], baseId[1], "0");
				else{
					Cellinfo_v2 bitSear=new Cellinfo_v2();//条件控制类
					bitSear.setCi(baseId[0]); 	//bitSear.setCELL("63102");
					bitSear.setLac(baseId[1]);        //bitSear.setLAC("29632");

					try{
						Cellinfo_v2 bit=baseDao.getListfromMysql(bitSear,bitSear.getLac(),bitSear.getCi()).get(0);							
						res+=bit.getAddr()+"-"+bit.getLat()+"-"+bit.getLon();
					}catch(Exception e){
						res=" - - ";
					}	
				}
				position = res.split("-")[0];
			}
			else 
				position=call2.getPosition();//position = "";
			/////////////////////////////////////////////////	
			for (SuspectInfo suspectInfo : listfromMysql3) {
				if (localNum.equals(suspectInfo.getSuspectPhone())) {
					name = suspectInfo.getSuspectName();
					ticktd.setName(name);
				}
			}
			ticktd.setLocalNum(localNum);
			ticktd.setDialNumber(dialNumber);
			//String[] suspectNums = suspectNumsStr.split(",");
			//for (String suspect : suspectNums) {
			//if(suspect.equals(dialNumber)){
			//ticktd.setLocalNum(dialNumber);
			//ticktd.setDialNumber(localNum);
			//}
			//}
			ticktd.setMethod(method);
			ticktd.setCallDuration(callDuration);

			ticktd.setStartTime(startTime2);
			ticktd.setPosition(position);
			caseidlist.add(ticktd);



		}




		if(totalHits<=0){
			//如果在本地号码找不到嫌疑人则查找嫌疑人属于对方号码的记录
			CallListBean call_temp=new CallListBean();
			call_temp.setDialNumber(suspectNumsStr);
			call_temp.setCaseID(caseidStr);
			if (haoma != null && !"".equals(haoma)) {
				call_temp.setLocalNum(haoma);
			}
			List<CallListBean> callList3;


			if (startTime != null && !"".equals(startTime)){
				callList3=sqlDao.getOrderListfromMysqlLikTime_callLog(call_temp, startTime+ 
						" 00:00:00", startTime+ " 23:59:59", (pageIndex - 1) * pageSize, pageSize);
				totalHits=sqlDao.getcountfromMysqlLike_callLog(call_temp,startTime+ 
						" 00:00:00", startTime+ " 23:59:59");
			}
			else{
				callList3=sqlDao.getOrderListfromMysqlLikTime_callLog(call_temp,"","", (pageIndex - 1) * pageSize, pageSize);
				totalHits=sqlDao.getcountfromMysqlLike_callLog(call_temp,"", "");
			}





			// 获取排序的类型
			//			String sortConditon2 = "";
			//			sortConditon2 = "startTime";




			for (CallListBean call4 : callList3) {
				TicketdDTO ticktd = new TicketdDTO();
				//	Map<String, Object> source = searchHit2.getSource();
				String method =call4.getMethod();// (String) source.get("method");
				String name =call4.getName();// (String) source.get("name");
				String startTime2 =call4.getStartTime();// (String) source.get("startTime");


				String localNum =call4.getLocalNum();// (String) source.get("localNum");
				String dialNumber =call4.getDialNumber();// (String) source.get("dialNumber");


				String callDuration =call4.getCallDuration();// (String) source.get("callDuration");
				/////////////////// 把地址由基站ID转为地址////////////////////////
				String baseId[] =call4.getPosition().split(",");// ((String) source.get("position")).split(",");
				String position = null;
				String res="";
				if (baseId.length ==2 && baseId[0] != null && !"".equals(baseId[0])){
					if(isConnNet)
						res=PositionUtil.getAddressByCell(baseId[0], baseId[1], "0");
					else{
						Cellinfo_v2 bitSear=new Cellinfo_v2();//条件控制类
						bitSear.setCi(baseId[0]); 	//bitSear.setCELL("63102");
						bitSear.setLac(baseId[1]);        //bitSear.setLAC("29632");

						try{
							Cellinfo_v2 bit=baseDao.getListfromMysql(bitSear,bitSear.getLac(),bitSear.getCi()).get(0);							
							res+=bit.getAddr()+"-"+bit.getLat()+"-"+bit.getLon();
						}catch(Exception e){
							res=" - - ";
						}	
					}
					position = res.split("-")[0];
				}
				else
					position=call4.getPosition();//position = "";
				/////////////////////////////////////////////////	
				for (SuspectInfo suspectInfo : listfromMysql3) {
					if (dialNumber.equals(suspectInfo.getSuspectPhone())) {
						name = suspectInfo.getSuspectName();
						ticktd.setName(name);
					}
				}
				ticktd.setLocalNum(localNum);
				ticktd.setDialNumber(dialNumber);
				//String[] suspectNums = suspectNumsStr.split(",");
				for (String suspect : suspectNums) {
					if(suspect.equals(dialNumber)){
						ticktd.setLocalNum(dialNumber);
						ticktd.setDialNumber(localNum);
					}
				}

				ticktd.setMethod(method);
				ticktd.setCallDuration(callDuration);

				ticktd.setStartTime(startTime2);
				ticktd.setPosition(position);
				caseidlist.add(ticktd);



			}

		}










		map.put("ticketList", caseidlist);
		map.put("total", totalHits);
		actionLog((String) session.getAttribute("userName"), "查看", "话单记录统计");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			// writer.write(string);
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

	// 执行分布式话单数据检索 ——相互通话页面
	@RequestMapping(value = "/admin/getTicket_each.php")
	public void getTicket_each(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 5;// 每页个数

		// 按条件查询
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String suspectMail = (String) request.getParameter("suspectMail");// 通话次数
		String suspectNumsStr = request.getParameter("suspectNumsStr");// 嫌疑人手机号list
		String caseId = request.getParameter("caseId");// 案件id
		String pageno = request.getParameter("pageno");

		String SuspectNameOrPhone = request.getParameter("SuspectNameOrPhone");// 嫌疑人手机号或姓名
		String dialNum = request.getParameter("dialNum");// 对方手机号

		// 如果未选嫌疑人 默认查找最新数据下的嫌疑人
		if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
			/*Evidence evidence = new Evidence();
				evidence.setIndexFlag(1);// 已建立索引的ev
				evidence.setEvType(3);// 话单类型
				List<Evidence> evidence0 = sqlDao.getListfromMysql(evidence);
				if (evidence0.size() > 0) {
					Evidence evidence3 = evidence0.get(evidence0.size() - 1);
					int caseid = evidence3.getCaseID();
					Caseinfo caseinfo2 = new Caseinfo();
					caseinfo2.setId(caseid);
					List<Caseinfo> caseinfoList = sqlDao.getListfromMysql(caseinfo2);
					if (caseinfoList.size() > 0) {
						Caseinfo caseinfo = caseinfoList.get(0);
						String mainParty = caseinfo.getMainParty();
						String[] split = mainParty.split(",");
						for (String string : split) {
							SuspectInfo suspectInfo = new SuspectInfo();
							suspectInfo.setId(Integer.parseInt(string));
							List<SuspectInfo> listfromMysql = sqlDao.getListfromMysql(suspectInfo);
							if (listfromMysql.size() > 0) {
								SuspectInfo suspectInfo2 = listfromMysql.get(0);
								String suspectPhone = suspectInfo2.getSuspectPhone();
								if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
									suspectNumsStr = suspectPhone;
								} else {
									suspectNumsStr += "," + suspectPhone;
								}
							}
						}
					}
				}*/
			suspectNumsStr="-1";
		}

		// 当从输入框搜索时，就不根据从案件列表获取的嫌疑人号码搜索，根据输入框输入的进行搜索
		if (SuspectNameOrPhone != null && !"".equals(SuspectNameOrPhone))
			suspectNumsStr = "";

		String string = "";
		long totalnum = 0; // 返回总共有多少数据

		ArrayList<TicketLinkDTO> ticketList = new ArrayList<TicketLinkDTO>();
		String[] caseIds = null;
		if (caseId != null && !"".equals(caseId)) {
			caseIds = caseId.split(",");
		}
		String[] suspectNums = null;
		if (suspectNumsStr != null && !"".equals(suspectNumsStr)) {
			suspectNums = suspectNumsStr.split(",");
		}

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件
		// 此处为匹配所有文档
		if (suspectNums != null && suspectNums.length > 0)
			mustQuery.must(QueryBuilders.termsQuery("localNum", suspectNums)); // 选择多个联系人，多选
		if (caseIds != null && caseIds.length > 0)
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseIds)); // 选择多个案件，多选
		if (dialNum != null && !"".equals(dialNum))
			mustQuery.must(QueryBuilders.matchPhraseQuery("dialNumber", dialNum));// 添加must的条件,单选
		if (SuspectNameOrPhone != null && !"".equals(SuspectNameOrPhone)) {
			QueryBuilder qb = QueryBuilders.multiMatchQuery(SuspectNameOrPhone, "name", "localNum");
			mustQuery.must(qb);
		}
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("startTime").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			mustQuery.must(rangequerybuilder);
		}
		// 精确日期范围
		if (suspectMail != null && !"".equals(suspectMail)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("startTime").from(suspectMail + " 00:00:00")
					.to(suspectMail + " 23:59:59");
			mustQuery.must(rangequerybuilder);
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("call").setTypes("callList")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

		// 获取排序的类型
		String sortConditon = "";
		sortConditon = "startTime";

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.addSort(sortConditon, SortOrder.DESC)// 按类型排序
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索
		string += searchResponse.toString();
		totalnum = searchResponse.getHits().getTotalHits();
		SearchHit[] hits = searchResponse.getHits().getHits();

		for (SearchHit searchHit : hits) {
			TicketLinkDTO tic = new TicketLinkDTO();
			Map<String, Object> source = searchHit.getSource();
			tic.setLocalNum((String) source.get("localNum"));
			tic.setCallDuration((String) source.get("callDuration"));
			tic.setDialNumber((String) source.get("dialNumber"));
			tic.setMethod((String) source.get("method"));
			tic.setPosition((String) source.get("position"));
			/////////////////// 把地址由基站ID转为地址////////////////////////
			String baseId[] = ((String) source.get("position")).split(",");
			String positionName = null;
			if (baseId.length==2 && baseId[0] != null && !"".equals(baseId[0]))
				positionName = PositionUtil.getAddressByCell(baseId[0], baseId[1], "0");
			else
				positionName = "";
			tic.setPositionName(positionName);
			tic.setStartTime((String) source.get("startTime"));
			ticketList.add(tic);
		}
		// }if(suspectNumsStr != null && !"".equals(suspectNumsStr))_end

		Map<Object, Object> map = new HashMap<>();
		map.put("ticketList", ticketList);
		map.put("total", totalnum);
		actionLog((String) session.getAttribute("userName"), "查看", "相互通话");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			// writer.write(string);
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
	 * 话单分析-联系人分析
	 * 
	 * @param request
	 * @param response
	 * @throws UnknownHostException 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/admin/contactAnalysis.php")
	public void contactAnalysis(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
		response.setContentType("textml; charset=UTF-8");
		String suspectNumsStr = request.getParameter("suspectNumsStr");// 嫌疑人手机号list
		String startDate2 = request.getParameter("startDate2");// 开始时间
		String endDate2 = request.getParameter("endDate2");// 结束时间
		String xingmingshouji = (String) request.getParameter("xingmingshouji");
		String gongtong = (String) request.getParameter("gongtong");
		String caseidStr = request.getParameter("caseidStr");//案件id
		// 如果未选嫌疑人 默认查找最新数据下的嫌疑人

		String[] caseids = { "" };
		if (!"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
			suspectNumsStr="-1";
		}

		if (xingmingshouji != null && !xingmingshouji.equals("")) {
			SuspectInfo suspectInfo5 = new SuspectInfo();
			suspectNumsStr = "";
			suspectInfo5.setSuspectName(xingmingshouji);
			List<SuspectInfo> listfromMysql4 = sqlDao.getListfromMysql(suspectInfo5);
			if (listfromMysql4.size() > 0) {
				for (SuspectInfo suspectInfo : listfromMysql4) {
					String suspectPhone = suspectInfo.getSuspectPhone();
					if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
						suspectNumsStr = suspectPhone;
					} else {
						suspectNumsStr += "," + suspectPhone;
					}
				}

			} else {
				suspectNumsStr = xingmingshouji;
			}

		}
		SuspectInfo suspectInfo3 = new SuspectInfo();
		List<SuspectInfo> listfromMysql3 = sqlDao.getListfromMysql(suspectInfo3);

		List<TicketLinksDTO> ticketLinksList = new ArrayList<TicketLinksDTO>();
		String[] suspectNums = suspectNumsStr.split(",");
		for (String suspectNum : suspectNums) {
			TicketLinksDTO ticketLinksDTO = new TicketLinksDTO();

			CallListBean call=new CallListBean();
			call.setLocalNum(suspectNum);
			call.setCaseID(caseidStr);
			call.setDialNumber(gongtong);

			List<CallListBean> callList=new ArrayList<CallListBean>();
			if (startDate2 != null && !"".equals(startDate2))
				callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call, startDate2+ 
						" 00:00:00", endDate2+ " 23:59:59", 0, 10000);
			else
				callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call,"","", 0, 10000);


			ArrayList<TicketLinkDTO> ticketLinkDTOList = new ArrayList<TicketLinkDTO>();

			long totalHits =callList.size();// searchResponse.getHits().getTotalHits()/10000;


			String linkman = null;
			for (SuspectInfo suspectInfo4 : listfromMysql3) {
				if (suspectNum.equals(suspectInfo4.getSuspectPhone())) {
					linkman = suspectInfo4.getSuspectName();
				}
			}
			//for (SearchHit[] searchHit2 : arrayList) {
			for (CallListBean call2 : callList) {
				//Map<String, Object> source = searchHit.getSource();
				String localNum =call2.getLocalNum();//(String) source.get("localNum");
				String dialNumber =call2.getDialNumber();// (String) source.get("dialNumber");

				ArrayList<TicketLinkDTO> ticketLinkDTOList2 = ticketLinkDTOList;
				if (ticketLinkDTOList2.size() != 0) {
					int flag = 0;
					for (int i = 0; i < ticketLinkDTOList2.size(); i++) {
						String dialNumber2 = ticketLinkDTOList2.get(i).getDialNumber();
						if (dialNumber.equals(dialNumber2)) {
							ticketLinkDTOList.get(i).setNumber(ticketLinkDTOList.get(i).getNumber() + 1);
							flag = 1;
						}
					}
					if (flag == 0) {
						TicketLinkDTO ticketLinkDTO = new TicketLinkDTO();
						ticketLinkDTO.setDialNumber(dialNumber);
						ticketLinkDTO.setNumber(1);
						ticketLinkDTO.setLocalNum(localNum);
						ticketLinkDTOList.add(ticketLinkDTO);
					}
				} else {
					TicketLinkDTO ticketLinkDTO = new TicketLinkDTO();
					ticketLinkDTO.setDialNumber(dialNumber);
					ticketLinkDTO.setNumber(1);
					ticketLinkDTO.setLocalNum(localNum);
					ticketLinkDTOList.add(ticketLinkDTO);
				}
			}
			//	}
			TicketDTO = ticketLinkDTOList;
			for (int i = 0; i < ticketLinkDTOList.size(); i++) {
				for (int j = 0; j < ticketLinkDTOList.size() - 1; j++) {// 比较两个整数
					if (ticketLinkDTOList.get(i).getNumber() > ticketLinkDTOList.get(j).getNumber()) {
						/* 交换 */
						TicketLinkDTO ticketLinkDTO = ticketLinkDTOList.get(i);
						ticketLinkDTOList.set(i, ticketLinkDTOList.get(j));
						ticketLinkDTOList.set(j, ticketLinkDTO);
					}
				}
			}
			ticketLinksDTO.setLicketLinkList(ticketLinkDTOList);
			ticketLinksDTO.setLinkman(linkman);
			ticketLinksList.add(ticketLinksDTO);
		}

		ArrayList<TicketLinkDTO> arrayList = new ArrayList<>();
		for (TicketLinksDTO ticketLinksDTO : ticketLinksList) {
			List<TicketLinkDTO> licketLinkList = ticketLinksDTO.getLicketLinkList();
			arrayList.addAll(licketLinkList);
		}
		List<TicketLinkDTO> aliketickets = new ArrayList<>();
		List<String> strings = new ArrayList<>();
		for (TicketLinkDTO ticketLinkDTO : arrayList) {

			for (TicketLinkDTO ticketLinkDTO2 : arrayList) {

				if (!ticketLinkDTO.getLocalNum().equals(ticketLinkDTO2.getLocalNum())) {
					if (ticketLinkDTO.getDialNumber().equals(ticketLinkDTO2.getDialNumber())) {
						int flag = 0;
						for (String string : strings) {
							if (string.equals(ticketLinkDTO.getDialNumber())) {
								flag = 1;
							}
						}
						if (flag == 0) {
							strings.add(ticketLinkDTO.getDialNumber());
						}
					}
				}
			}
		}

		for (TicketLinkDTO ticketLinkDTO : arrayList) {
			for (String string2 : strings) {
				if (string2.equals(ticketLinkDTO.getDialNumber())) {
					String localNum = ticketLinkDTO.getLocalNum();
					int number = ticketLinkDTO.getNumber();
					TicketLinkDTO ticketLinkDTO4 = new TicketLinkDTO();
					ticketLinkDTO4.setLocalNum(localNum);
					ticketLinkDTO4.setDialNumber(string2);
					ticketLinkDTO4.setNumber(number);
					aliketickets.add(ticketLinkDTO4);
				}
			}
		}

		ArrayList<TicketLinkDTO> arrayList2 = new ArrayList<TicketLinkDTO>();
		for (TicketLinkDTO ticketLinkDTO5 : aliketickets) {
			int a = 0;
			int b = 0;
			for (TicketLinkDTO ticketLinkDTO : arrayList2) {
				if (ticketLinkDTO5.getDialNumber().equals(ticketLinkDTO.getDialNumber())) {
					a = 1;
					arrayList2.get(b).setNumber(arrayList2.get(b).getNumber() + ticketLinkDTO5.getNumber());
				}
				b++;
			}
			if (a == 0) {
				TicketLinkDTO ticketLinkDTO6 = new TicketLinkDTO();
				ticketLinkDTO6.setLocalNum(ticketLinkDTO5.getLocalNum());
				ticketLinkDTO6.setDialNumber(ticketLinkDTO5.getDialNumber());
				ticketLinkDTO6.setNumber(ticketLinkDTO5.getNumber());
				arrayList2.add(ticketLinkDTO6);
			}
		}
		ticketLinksListOut = ticketLinksList;
		Map<Object, Object> map = new HashMap<>();
		map.put("ticketLinksList", ticketLinksList);
		map.put("aliketickets", aliketickets);
		map.put("strings", arrayList2);
		actionLog((String) session.getAttribute("userName"), "查看", "话单 联系人分析");
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


	public String searchName(String dialNumber) {
		// 精确搜索
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery());
		// 添加第1条must的条件 此处为匹配所有文档
		mustQuery.must(QueryBuilders.matchPhraseQuery("dialNumber", dialNumber));// 添加must的条件

		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("call").setTypes("callList");
		//.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
		//.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果
		// 执行搜索
		SearchResponse searchResponse = searchRequestBuilder.setFrom(0)// 分页起始位置（跳过开始的n个）
				.setSize(10)// 本次返回的文档数量
				.execute().actionGet();

		ArrayList<TicketLinkDTO> ticketLinkDTOList = new ArrayList<TicketLinkDTO>();
		ArrayList<SearchHit[]> arrayList = new ArrayList<SearchHit[]>();
		SearchHit[] hits1 = searchResponse.getHits().getHits();
		arrayList.add(hits1);
		//		long totalHits = searchResponse.getHits().getTotalHits()/10000;
		//		for(int i=1;i<=totalHits;i++){
		//			searchResponse = searchRequestBuilder.setQuery(mustQuery).setFrom(i*10000)// 分页起始位置（跳过开始的n个）
		//					.setSize(10000).execute().actionGet();
		//			SearchHit[] hits  = searchResponse.getHits().getHits();
		//			arrayList.add(hits);
		//		}

		String dialName = null;

		for (SearchHit[] searchHit2 : arrayList) {
			for (SearchHit searchHit : searchHit2) {
				Map<String, Object> source = searchHit.getSource();
				dialName = (String) source.get("name");
				if (dialName != null && !"".equals(dialName))
					break;
			}
		}
		return dialName;
	}

	/**
	 * 话单分析-相互通话
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/admin/contactAnalysis_each.php")
	public void contactAnalysis_each(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String suspectNumsStr = request.getParameter("suspectNumsStr");// 嫌疑人手机号list
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String caseidStr = request.getParameter("caseidStr");// 案件Id
		int pageIndex = 1;// 页数
		int pageSize = 100;// 每页个数
		int count = 1;// 临时

		SuspectInfo suspectInfoTemp = new SuspectInfo();
		List<SuspectInfo> listfromMysql_Sus = sqlDao.getListfromMysql(suspectInfoTemp);



		List<TicketLinksDTO> ticketLinksList = new ArrayList<TicketLinksDTO>();


		String[] caseids = { "" };
		if (!"".equals(caseidStr) && caseidStr!=null) {
			caseids = caseidStr.split(" ");
		}
		// 如果未选嫌疑人 默认查找最新数据下的嫌疑人
		if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
			suspectNumsStr="-1";
		}
		if (suspectNumsStr != null && !"".equals(suspectNumsStr)) {
			String[] suspectNums = suspectNumsStr.split(",");



			for (String suspectNum : suspectNums) {
				TicketLinksDTO ticketLinksDTO = new TicketLinksDTO();

				CallListBean call=new CallListBean();
				call.setLocalNum(suspectNum);


				List<CallListBean> callList=new ArrayList<CallListBean>();
				List<CallListBean> callListTemp;
				for(String caseId:caseids){
					//依次查询所有案件id
					call.setCaseID(caseId);
					if (startDate != null && !"".equals(startDate))
						callListTemp=sqlDao.getOrderListfromMysqlLikTime_callLog(call, startDate+ " 00:00:00", endDate+ " 23:59:59", 0, 1000);
					else
						callListTemp=sqlDao.getListfromMysql(call, 0, 1000);
					callList.addAll(callListTemp);

				}


				ArrayList<TicketLinkDTO> ticketLinkDTOList = new ArrayList<TicketLinkDTO>();
				String linkman = "";
				for (CallListBean call2 : callList) {
					//	Map<String, Object> source = searchHit.getSource();
					//linkman = (String) source.get("name");
					count++;
					String localNum =call2.getLocalNum();// (String) source.get("localNum");
					for (SuspectInfo suspectInfo3 : listfromMysql_Sus) {
						int id = suspectInfo3.getId();
						String suspectName = suspectInfo3.getSuspectName();
						String suspectPhone = suspectInfo3.getSuspectPhone();
						if (suspectPhone.equals(localNum)) {
							linkman=suspectName;
						}
					}
					if(linkman.equals(""))
						linkman="未知用户";

					String dialNumber =call2.getDialNumber();// (String) source.get("dialNumber");
					String dialName ="";// searchName(dialNumber);
					for (SuspectInfo suspectInfo3 : listfromMysql_Sus) {
						int id = suspectInfo3.getId();
						String suspectName = suspectInfo3.getSuspectName();
						String suspectPhone = suspectInfo3.getSuspectPhone();
						if (suspectPhone.equals(dialNumber)) {
							dialName=suspectName;
						}
					}
					if(dialName.equals(""))
						dialName="未知用户";

					ArrayList<TicketLinkDTO> ticketLinkDTOList2 = ticketLinkDTOList;
					if (ticketLinkDTOList2.size() != 0) {
						int flag = 0;
						for (int i = 0; i < ticketLinkDTOList2.size(); i++) {
							String dialNumber2 = ticketLinkDTOList2.get(i).getDialNumber();
							if (dialNumber.equals(dialNumber2)) {
								ticketLinkDTOList.get(i).setNumber(ticketLinkDTOList.get(i).getNumber() + 1);
								flag = 1;
							}
						}
						if (flag == 0) {
							TicketLinkDTO ticketLinkDTO = new TicketLinkDTO();
							ticketLinkDTO.setDialNumber(dialNumber);
							ticketLinkDTO.setNumber(1);
							ticketLinkDTO.setLocalNum(localNum);
							ticketLinkDTO.setDialName(dialName);
							ticketLinkDTOList.add(ticketLinkDTO);
						}
					} else {
						TicketLinkDTO ticketLinkDTO = new TicketLinkDTO();
						ticketLinkDTO.setDialNumber(dialNumber);
						ticketLinkDTO.setNumber(1);
						ticketLinkDTO.setLocalNum(localNum);
						ticketLinkDTO.setDialName(dialName);
						ticketLinkDTOList.add(ticketLinkDTO);
					}
				}
				ticketLinksDTO.setLicketLinkList(ticketLinkDTOList);
				ticketLinksDTO.setLinkman(linkman);
				ticketLinksList.add(ticketLinksDTO);
			}
		}
		ArrayList<TicketLinkDTO> arrayList = new ArrayList<>();
		for (TicketLinksDTO ticketLinksDTO : ticketLinksList) {
			List<TicketLinkDTO> licketLinkList = ticketLinksDTO.getLicketLinkList();
			arrayList.addAll(licketLinkList);
		}

		List<String> aliketickets = new ArrayList<>();
		for (TicketLinkDTO ticketLinkDTO : arrayList) { // 获取共同联系人
			for (TicketLinkDTO ticketLinkDTO2 : arrayList) {
				if (!ticketLinkDTO.getLocalNum().equals(ticketLinkDTO2.getLocalNum())) {
					if (ticketLinkDTO.getDialNumber().equals(ticketLinkDTO2.getDialNumber())) {
						aliketickets.add(ticketLinkDTO.getDialNumber());
					}
				}
			}
		}
		//嫌疑人电话列表
		List<String>localnumList=new ArrayList<String>();
		for(TicketLinksDTO ticket:ticketLinksList){
			localnumList.add(((TicketLinkDTO)(ticket.getLicketLinkList().get(0))).getLocalNum());
		}
		Map<Object, Object> map = new HashMap<>();
		
		map.put("ticketLinksList", ticketLinksList);
		map.put("aliketickets", aliketickets);
		map.put("localnumList", localnumList);

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
	 * 话单分析-活动地图通话记录按通话地点次数排序
	 * 
	 * @param request
	 * @param response
	 * @throws UnknownHostException 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/admin/contactMap.php")
	public void contactMap(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
		response.setContentType("textml; charset=UTF-8");
		String suspectNumsStr = request.getParameter("suspectNumsStr");// 嫌疑人手机号list
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String startTime_get = request.getParameter("startTime");
		String endTime_get = request.getParameter("endTime");
		String caseId = request.getParameter("caseId");
		int pageIndex = 1;// 页数
		int pageSize = 100;// 每页个数
		int count = 1;// 临时

		// 如果未选嫌疑人 默认查找最新数据下的嫌疑人
		if (suspectNumsStr == null || "".equals(suspectNumsStr)) {

			suspectNumsStr="-1";
		}

		List<TicketLinksDTO> ticketLinksList = new ArrayList<TicketLinksDTO>();
		if (suspectNumsStr != null && !"".equals(suspectNumsStr)) {
			String[] suspectNums = suspectNumsStr.split(",");

			for (String suspectNum : suspectNums) {
				TicketLinksDTO ticketLinksDTO = new TicketLinksDTO();
				CallListBean call=new CallListBean();
				call.setLocalNum(suspectNum);
				call.setCaseID(caseId);

				List<CallListBean> callList;
				if (startDate != null && !"".equals(startDate))
					callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call, startDate+ " 00:00:00", endDate+ " 23:59:59", 0, 1000);
				else
					callList=sqlDao.getListfromMysql(call, 0, 1000);


				ArrayList<TicketLinkDTO> ticketLinkDTOList = new ArrayList<TicketLinkDTO>();
				ArrayList<SearchHit[]> arrayList = new ArrayList<SearchHit[]>();



				String linkman = null;

				for (CallListBean call2 : callList) {
					//Map<String, Object> source = searchHit.getSource();
					linkman = call2.getName();

					///////////////// 只对符合时间段的记录进行统计//////////////////////////////////
					String startTime = call2.getStartTime();
					Date d1 = null;// 获取到的时间
					Date d2 = null;// 开始时间
					Date d3 = null;// 结束时间
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
					try {
						d1 = dateFormat.parse(dateFormat.format(df1.parse(startTime)));
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (startTime_get != null && !"".equals(startTime_get))
							d2 = dateFormat.parse(startTime_get + ":00");
						else
							d2 = dateFormat.parse("00:00:00");

					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (endTime_get != null && !"".equals(endTime_get))
							d3 = dateFormat.parse(endTime_get + ":00");
						else
							d3 = dateFormat.parse("23:59:59");

					} catch (Exception e) {
						e.printStackTrace();
					}
					boolean time_flag;
					if (d2.getTime() > d3.getTime())
						time_flag = (d1.getTime() > d2.getTime() || d1.getTime() < d3.getTime()) && count < pageSize;
					else
						time_flag = (d1.getTime() > d2.getTime() && d1.getTime() < d3.getTime()) && count < pageSize;
					if (time_flag) {

						String localNum = call2.getLocalNum();//(String) source.get("localNum");

						String dialNumber =call2.getDialNumber();// (String) source.get("dialNumber");
						//String dialName = searchName(dialNumber);
						String position =call2.getPosition();// (String) source.get("position");

						ArrayList<TicketLinkDTO> ticketLinkDTOList2 = ticketLinkDTOList;
						if (ticketLinkDTOList2.size() != 0) {
							int flag = 0;
							for (int i = 0; i < ticketLinkDTOList2.size(); i++) {
								String position2 = ticketLinkDTOList2.get(i).getPosition();
								if (position.equals(position2)) {
									ticketLinkDTOList.get(i).setNumber(ticketLinkDTOList.get(i).getNumber() + 1);
									flag = 1;
								}
							}
							if (flag == 0) {
								TicketLinkDTO ticketLinkDTO = new TicketLinkDTO();
								ticketLinkDTO.setDialNumber(dialNumber);
								ticketLinkDTO.setNumber(1);
								ticketLinkDTO.setLocalNum(localNum);
								//	ticketLinkDTO.setDialName(dialName);

								ticketLinkDTO.setPosition(position);
								ticketLinkDTOList.add(ticketLinkDTO);
							}
						} else {
							TicketLinkDTO ticketLinkDTO = new TicketLinkDTO();
							ticketLinkDTO.setDialNumber(dialNumber);
							ticketLinkDTO.setNumber(1);
							ticketLinkDTO.setLocalNum(localNum);
							//	ticketLinkDTO.setDialName(dialName);
							ticketLinkDTO.setPosition(position);
							ticketLinkDTOList.add(ticketLinkDTO);
						}
					} // if(time_falg)_end
				}
				/////////////////////////////////sssssssssss/////////////////////////
				// 排序
				for (int i = 0; i < ticketLinkDTOList.size(); i++) {
					/////////////////// 把地址由基站ID转为地址////////////////////////
					String baseId[] = ticketLinkDTOList.get(i).getPosition().split(",");
					String position = null;
					String res="";
					if (baseId.length ==2 && baseId[0] != null && !"".equals(baseId[0])){
						if(isConnNet)
							res=PositionUtil.getAddressByCell(baseId[0], baseId[1], "0");
						else{
							Cellinfo_v2 bitSear=new Cellinfo_v2();//条件控制类
							bitSear.setCi(baseId[0]); 	//bitSear.setCELL("63102");
							bitSear.setLac(baseId[1]);        //bitSear.setLAC("29632");
							try{
								Cellinfo_v2 bit=baseDao.getListfromMysql(bitSear,bitSear.getLac(),bitSear.getCi()).get(0);							
								res+=bit.getAddr()+"-"+bit.getLat()+"-"+bit.getLon();
							}catch(Exception e){
								res=" - - ";
							}							
						}
						position = res.split("-")[0];
					}
					else 
						position=ticketLinkDTOList.get(i).getPosition();//position = "";
					if (!"".equals(position)&&position.split(";").length==2) {
						ticketLinkDTOList.get(i).setPositionName_sim((position.split(";"))[0]);
						ticketLinkDTOList.get(i).setLat(res.split("-")[1]);
						ticketLinkDTOList.get(i).setLon(res.split("-")[2]);

					}
					ticketLinkDTOList.get(i).setPositionName(position);
					for (int j = i + 1; j < ticketLinkDTOList.size(); j++) {
						if (ticketLinkDTOList.get(i).getNumber() < ticketLinkDTOList.get(j).getNumber()) {
							int temp = ticketLinkDTOList.get(i).getNumber();
							ticketLinkDTOList.get(i).setNumber(ticketLinkDTOList.get(j).getNumber());
							ticketLinkDTOList.get(j).setNumber(temp);
						}
					}
				}
				ticketLinksDTO.setLicketLinkList(ticketLinkDTOList);
				ticketLinksDTO.setLinkman(linkman);
				ticketLinksList.add(ticketLinksDTO);
			}
		}

		Map<Object, Object> map = new HashMap<>();
		map.put("ticketLinksList", ticketLinksList);
		actionLog((String) session.getAttribute("userName"), "查看", "话单分析-活动地图");
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
	 * 话单分析——活动地图指定地点通话记录详情
	 */
	// 通话时长与频次
	@RequestMapping(value = "/admin/getCallRecord.php")
	public void getCallRecord(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String startTime_get = request.getParameter("startTime");
		String endTime_get = request.getParameter("endTime");
		int pageIndex = 1;// 页数
		int pageSize = 100;// 每页个数
		int count = 1;// 临时
		String localNum = request.getParameter("localNum"); // 查询主机号
		String position = request.getParameter("position"); // 通讯地址
		String pageSizeStr = request.getParameter("pageSize");
		String caseId = request.getParameter("caseId");
		if (!StringUtils.isEmpty(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}



		CallListBean call=new CallListBean();
		call.setLocalNum(localNum);
		call.setPosition(position);
		call.setCaseID(caseId);
		List<CallListBean> callList;
		if (startDate != null && !"".equals(startDate))
			callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call, startDate+ " 00:00:00", endDate+ " 23:59:59", 0, 1000);
		else
			callList=sqlDao.getListfromMysql(call, 0, 1000);


		ArrayList<TicketLinkDTO> ticketLinkDTOList = new ArrayList<TicketLinkDTO>();
		// 提取时间段在18：00-00：00的数据
		for (CallListBean call2 : callList) {
			//	Map<String, Object> source = searchHit.getSource();
			String startTime = call2.getStartTime();//(String) source.get("startTime");
			Date d1 = null;// 获取到的时间
			Date d2 = null;// 开始时间
			Date d3 = null;// 结束时间
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			try {
				d1 = dateFormat.parse(dateFormat.format(df1.parse(startTime)));

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (startTime_get != null && !"".equals(startTime_get))
					d2 = dateFormat.parse(startTime_get + ":00");
				else
					d2 = dateFormat.parse("00:00:00");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (endTime_get != null && !"".equals(endTime_get))
					d3 = dateFormat.parse(endTime_get + ":00");
				else
					d3 = dateFormat.parse("23:59:59");

			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean time_flag;
			if (d2.getTime() > d3.getTime())
				time_flag = (d1.getTime() > d2.getTime() || d1.getTime() < d3.getTime()) && count <= pageSize;
			else
				time_flag = (d1.getTime() > d2.getTime() && d1.getTime() < d3.getTime()) && count <= pageSize;
			if (time_flag) {
				TicketLinkDTO tic = new TicketLinkDTO();
				tic.setCallDuration(call2.getCallDuration());//(String) source.get("callDuration"));
				tic.setDialNumber(call2.getDialNumber());//(String) source.get("dialNumber"));
				// tic.setLocalNum(localNum);
				tic.setMethod(call2.getMethod());//(String) source.get("method"));
				tic.setStartTime(startTime);
				// tic.setPosition(position);
				ticketLinkDTOList.add(tic);
				count++;

			}
		}
		Map<Object, Object> map = new HashMap<>();
		map.put("ticketList", ticketLinkDTOList);

		//String string = searchResponse.toString();

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			// writer.write(string);
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
	 * 话单分析-疑似居住地图通话记录按通话地点次数排序
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/admin/addressMap.php")
	public void addressMap(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String suspectNumsStr = request.getParameter("suspectNumsStr");// 嫌疑人手机号list
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String caseId = request.getParameter("caseId");// 案件Id
		String startTime_get = request.getParameter("startTime");
		String endTime_get = request.getParameter("endTime");

		// 如果未选嫌疑人 默认查找最新数据下的嫌疑人
		if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
			suspectNumsStr="-1";
		}


		int pageIndex = 1;// 页数
		int pageSize = 100;// 每页个数
		int count = 0; // 统计已找到的记录个数
		List<TicketLinksDTO> ticketLinksList = new ArrayList<TicketLinksDTO>();
		if (suspectNumsStr != null && !"".equals(suspectNumsStr)) { // 实际情况只有一个号码
			String[] suspectNums = suspectNumsStr.split(",");

			for (String suspectNum : suspectNums) {
				TicketLinksDTO ticketLinksDTO = new TicketLinksDTO();
				CallListBean call=new CallListBean();
				call.setLocalNum(suspectNum);
				call.setCaseID(caseId);

				List<CallListBean> callList;
				if (startDate != null && !"".equals(startDate))
					callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call, startDate+ " 18:00:00", endDate+ " 23:59:59", 0, 1000);
				else
					callList=sqlDao.getListfromMysql(call, 0, 1000);


				ArrayList<TicketLinkDTO> ticketLinkDTOList = new ArrayList<TicketLinkDTO>();
				ArrayList<SearchHit[]> arrayList = new ArrayList<SearchHit[]>();

				String linkman = null;


				for (CallListBean call2 : callList) {	
					//Map<String, Object> source = searchHit.getSource();
					linkman =call2.getName();// (String) source.get("name");
					///////////////// 只对符合时间段的记录进行统计//////////////////////////////////
					String startTime = call2.getStartTime();//(String) source.get("startTime");
					Date d1 = null;// 获取到的时间
					Date d2 = null;// 开始时间
					Date d3 = null;// 结束时间
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
					try {
						d1 = dateFormat.parse(dateFormat.format(df1.parse(startTime)));
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (startTime_get != null && !"".equals(startTime_get))
							d2 = dateFormat.parse(startTime_get + ":00");
						else
							d2 = dateFormat.parse("20:00:00");

					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						if (endTime_get != null && !"".equals(endTime_get))
							d3 = dateFormat.parse(endTime_get + ":00");
						else
							d3 = dateFormat.parse("08:00:00");

					} catch (Exception e) {
						e.printStackTrace();
					}
					boolean time_flag;
					if (d2.getTime() > d3.getTime())
						time_flag = (d1.getTime() > d2.getTime() || d1.getTime() < d3.getTime()) && count < pageSize;
					else
						time_flag = (d1.getTime() > d2.getTime() && d1.getTime() < d3.getTime()) && count < pageSize;

					if (time_flag) {


						count++;
						String localNum = call2.getLocalNum();//(String) source.get("localNum");

						String dialNumber =call2.getDialNumber();// (String) source.get("dialNumber");
						//						Date d13333333333=new Date();
						//				//		String dialName = searchName(dialNumber);
						//						Date d14444444444444=new Date();
						String position =call2.getPosition();// (String) source.get("position");


						ArrayList<TicketLinkDTO> ticketLinkDTOList2 = ticketLinkDTOList;
						if (ticketLinkDTOList2.size() != 0) {
							int flag = 0;
							for (int i = 0; i < ticketLinkDTOList2.size(); i++) {
								String position2 = ticketLinkDTOList2.get(i).getPosition();
								if (position.equals(position2)) {
									ticketLinkDTOList.get(i).setNumber(ticketLinkDTOList.get(i).getNumber() + 1);
									flag = 1;
								}
							}
							if (flag == 0) {
								TicketLinkDTO ticketLinkDTO = new TicketLinkDTO();
								ticketLinkDTO.setDialNumber(dialNumber);
								ticketLinkDTO.setNumber(1);
								ticketLinkDTO.setLocalNum(localNum);
								//	ticketLinkDTO.setDialName(dialName);
								ticketLinkDTO.setPosition(position);
								ticketLinkDTOList.add(ticketLinkDTO);
							}
						} else {
							TicketLinkDTO ticketLinkDTO = new TicketLinkDTO();
							ticketLinkDTO.setDialNumber(dialNumber);
							ticketLinkDTO.setNumber(1);
							ticketLinkDTO.setLocalNum(localNum);
							//	ticketLinkDTO.setDialName(dialName);
							ticketLinkDTO.setPosition(position);
							ticketLinkDTOList.add(ticketLinkDTO);
						}

					} // if(d1.getTime()>d2.getTime()&&d1.getTime()<d3.getTime()&&count<pageSize)
					// — — — —end

				}
				// 排序
				for (int i = 0; i < ticketLinkDTOList.size(); i++) {
					/////////////////// 把地址由基站ID转为地址////////////////////////
					String baseId[] = ticketLinkDTOList.get(i).getPosition().split(",");
					String position = null;
					String res="";
					if (baseId.length ==2 && baseId[0] != null && !"".equals(baseId[0])){
						if(isConnNet)  //判断是否联网获取ID转换地址
							res=PositionUtil.getAddressByCell(baseId[0], baseId[1], "0");
						else{
							Cellinfo_v2 bitSear=new Cellinfo_v2();//条件控制类
							bitSear.setCi(baseId[0]); 	//bitSear.setCELL("63102");
							bitSear.setLac(baseId[1]);        //bitSear.setLAC("29632");

							try{
								Cellinfo_v2 bit=baseDao.getListfromMysql(bitSear,bitSear.getLac(),bitSear.getCi()).get(0);							
								res+=bit.getAddr()+"-"+bit.getLat()+"-"+bit.getLon();
							}catch(Exception e){
								res=" - - ";
							}	
						}
						position = res.split("-")[0];
					}
					else
						position=ticketLinkDTOList.get(i).getPosition();//position = "";
					if (!"".equals(position)&&position.split(";").length==2) {
						ticketLinkDTOList.get(i).setPositionName_sim((position.split(";"))[0]);
						ticketLinkDTOList.get(i).setLat(res.split("-")[1]);
						ticketLinkDTOList.get(i).setLon(res.split("-")[2]);
					}
					ticketLinkDTOList.get(i).setPositionName(position);
					for (int j = i + 1; j < ticketLinkDTOList.size(); j++) {
						if (ticketLinkDTOList.get(i).getNumber() < ticketLinkDTOList.get(j).getNumber()) {
							int temp = ticketLinkDTOList.get(i).getNumber();
							ticketLinkDTOList.get(i).setNumber(ticketLinkDTOList.get(j).getNumber());
							ticketLinkDTOList.get(j).setNumber(temp);
						}
					}
				}
				ticketLinksDTO.setLicketLinkList(ticketLinkDTOList);
				ticketLinksDTO.setLinkman(linkman);
				ticketLinksList.add(ticketLinksDTO);
			}

		}


		Map<Object, Object> map = new HashMap<>();
		map.put("ticketLinksList", ticketLinksList);

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
	 * 话单分析——疑似居住地地图指定地点通话记录详情
	 */
	// 通话时长与频次
	@RequestMapping(value = "/admin/getAddrCallRecord.php")
	public void getAddrCallRecord(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String caseId = request.getParameter("caseId");// 案件ID
		String startTime_get = request.getParameter("startTime");
		String endTime_get = request.getParameter("endTime");
		int pageIndex = 1;// 页数
		int pageSize = 100;// 每页个数
		int count = 0; // 统计查找到符合时间段的记录的个数

		String localNum = request.getParameter("localNum"); // 查询主机号
		String position = request.getParameter("position"); // 通讯地址
		String pageSizeStr = request.getParameter("pageSize"); // 根据第一个表返回查询到的记录条数查询相应数量
		if (!StringUtils.isEmpty(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}


		CallListBean call=new CallListBean();
		call.setLocalNum(localNum);
		call.setPosition(position);
		call.setCaseID(caseId);
		List<CallListBean> callList;
		if (startDate != null && !"".equals(startDate))
			callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call, startDate+ " 18:00:00", endDate+ " 23:59:59", 0, 1000);
		else
			callList=sqlDao.getListfromMysql(call, 0, 1000);



		ArrayList<TicketLinkDTO> ticketLinkDTOList = new ArrayList<TicketLinkDTO>();

		// 提取时间段在18：00-00：00的数据
		for (CallListBean call2 : callList) {
			//Map<String, Object> source = searchHit.getSource();
			String startTime =call2.getStartTime();// (String) source.get("startTime");
			Date d1 = null;// 获取到的时间
			Date d2 = null;// 开始时间
			Date d3 = null;// 结束时间
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			try {
				d1 = dateFormat.parse(dateFormat.format(df1.parse(startTime)));

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (startTime_get != null && !"".equals(startTime_get))
					d2 = dateFormat.parse(startTime_get + ":00");
				else
					d2 = dateFormat.parse("20:00:00");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (endTime_get != null && !"".equals(endTime_get))
					d3 = dateFormat.parse(endTime_get + ":00");
				else
					d3 = dateFormat.parse("08:00:00");

			} catch (Exception e) {
				e.printStackTrace();
			}
			boolean time_flag;
			if (d2.getTime() > d3.getTime())
				time_flag = (d1.getTime() > d2.getTime() || d1.getTime() < d3.getTime()) && count <= pageSize;
			else
				time_flag = (d1.getTime() > d2.getTime() && d1.getTime() < d3.getTime()) && count <= pageSize;
			if (time_flag) {
				TicketLinkDTO tic = new TicketLinkDTO();
				tic.setCallDuration(call2.getCallDuration());//(String) source.get("callDuration"));
				tic.setDialNumber(call2.getDialNumber());//(String) source.get("dialNumber"));
				// tic.setLocalNum(localNum);
				tic.setMethod(call2.getMethod());//(String) source.get("method"));
				tic.setStartTime(startTime);
				// tic.setPosition(position);
				ticketLinkDTOList.add(tic);
				count++;

			}
		}
		Map<Object, Object> map = new HashMap<>();
		map.put("ticketList", ticketLinkDTOList);

		//String string = searchResponse.toString();

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
	 * 话单分析-案件嫌疑人列表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/admin/getCasecriminalSuspect.php")
	public void getCasecriminalSuspect(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String casenumorname = request.getParameter("casenumorname");
		Caseinfo cas = new Caseinfo();
		if (casenumorname != null || !"".equals(casenumorname)) {
			cas.setCaseName(casenumorname);
		}
		User user = (User) session.getAttribute("user");
		String roleName="" ;
		if(user != null){
			String userrole = user.getUserrole();
			Role role = new Role();
			role.setId(Integer.parseInt(userrole));
			List<Role> roleList = sqlDao.getListfromMysql(role);
			if(roleList.size()>0){
				Role role2 = roleList.get(0);
				roleName = role2.getDataScope();
			}
		}
		if (roleName.equals("科室数据") ) {// 科长
			cas.setSection(user.getSection()+"/"+user.getUsername());
		}else if (roleName.equals("部门数据") ) {// 部长
			cas.setDepartment(user.getPartment());
		} else if (roleName.equals("个人数据") ) {// 科员
			cas.setUserName(user.getUsername());
		} else if (roleName.equals("所有数据")) {// 局长
			cas.setId(-1);
		} else {
			cas.setId(-2);
		}
		List<Caseinfo> casList = sqlDao.getListfromMysqlLikeOR(cas);
		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(casList));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 通话时长与频次
	@RequestMapping(value = "/admin/getPhoneInfo.php")
	public void getPhoneInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 2;// 每页个数
		// 按条件查询
		String sortType = (String) request.getParameter("sortType");// 排序类型
		String pageno = request.getParameter("pageno");
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();

		mustQuery.must(QueryBuilders.matchAllQuery()); // 全匹配
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("call").setTypes("callList")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果
		// 获取排序的类型
		String sortConditon = "";
		/*
		 * if (sortType.equals("创建时间")) { sortConditon="createDate"; }else if
		 * (sortType.equals("修改时间")) { sortConditon="editDate"; }else if
		 * (sortType.equals("文件大小")) { sortConditon="fileSize"; }else if
		 * (sortType.equals("访问时间")) { sortConditon="editDate"; }else if
		 * (sortType.equals("文件名")) { sortConditon="fileName"; }else if
		 * (sortType.equals("文件类型")) { sortConditon="docType"; }
		 */
		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				// .addSort(sortConditon, SortOrder.DESC)//按类型排序
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索
		String string = searchResponse.toString();
		actionLog((String) session.getAttribute("userName"), "查看", "通话时长与频次");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(string);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 嫌疑人居住地话单
	@RequestMapping(value = "/admin/getAddrestTicket.php")
	public void getAddrestTicket(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 100;// 每页个数
		// 按条件查询
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String suspectMail = (String) request.getParameter("suspectMail");// 通话次数
		String pageno = request.getParameter("pageno");

		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("startTime").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			// System.out.println("时间分割");
			mustQuery.must(rangequerybuilder);
		}
		// 精确日期范围
		if (suspectMail != null && !"".equals(suspectMail)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("startTime").from(suspectMail + " 00:00:00")
					.to(suspectMail + " 23:59:59");
			// System.out.println("时间分割");
			mustQuery.must(rangequerybuilder);
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("call").setTypes("callList")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索

		ArrayList<TicketAddrestDTO> ticketDTOList = new ArrayList<TicketAddrestDTO>();
		SearchHit[] hits = searchResponse.getHits().getHits();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			String posTime2 = (String) source.get("dialNumber");
			String callDuration2 = (String) source.get("callDuration");
			String position2 = (String) source.get("position");
			if (posTime2 != null) {
				if (ticketDTOList.size() != 0) {
					int flag = 0;
					for (int i = 0; i < ticketDTOList.size(); i++) {
						String PosstartTime2 = ticketDTOList.get(i).getDialNumber();
						if (posTime2.equals(PosstartTime2)) {
							ticketDTOList.get(i).setNum(ticketDTOList.get(i).getNum() + 1);
							flag = 1;
						}
					}
					if (flag == 0) {
						TicketAddrestDTO ticketLinkDTO = new TicketAddrestDTO();
						ticketLinkDTO.setDialNumber(posTime2);
						ticketLinkDTO.setTime(callDuration2);
						ticketLinkDTO.setAddrest(position2);
						ticketLinkDTO.setCardType("无");
						ticketLinkDTO.setNum(1);
						ticketDTOList.add(ticketLinkDTO);
					}
				} else {
					TicketAddrestDTO ticketLinkDTO = new TicketAddrestDTO();
					ticketLinkDTO.setDialNumber(posTime2);
					ticketLinkDTO.setTime(callDuration2);
					ticketLinkDTO.setAddrest(position2);
					ticketLinkDTO.setCardType("无");
					ticketLinkDTO.setNum(1);
					ticketDTOList.add(ticketLinkDTO);
				}
			}
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(ticketDTOList));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 执行分布式话单通话次数数据
	@RequestMapping(value = "/admin/getNumberTicket.php")
	public void getNumberTicket(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 100;// 每页个数

		// 按条件查询
		String caseidStr = request.getParameter("caseidStr");//案件id
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String suspectNumsStr = request.getParameter("suspectNumsStr");// 嫌疑人手机号list
		String posstartTime = (String) request.getParameter("suspectMail");// 通话时间
		// String suspectNums="";
		String[] caseids = { "" };
		if (!"".equals(caseidStr) && caseidStr!=null) {
			caseids = caseidStr.split(" ");
		}
		// 如果未选嫌疑人 默认查找最新数据下的嫌疑人
		if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
			suspectNumsStr="-1";
		}

		ArrayList<TicketDegreeDTO> ticketDTOList = new ArrayList<TicketDegreeDTO>();
		ArrayList<TicketDegreeDTO> ticketDTOList2 = new ArrayList<TicketDegreeDTO>();
		if (suspectNumsStr != null && !"".equals(suspectNumsStr)) {
			String[] suspectNums = suspectNumsStr.split(",");
			for (String suspectNum : suspectNums) {
				TicketLinksDTO ticketLinksDTO = new TicketLinksDTO();

				CallListBean call=new CallListBean();
				call.setLocalNum(suspectNum);
				call.setCaseID(caseidStr);

				List<CallListBean> callList=new ArrayList<CallListBean>();
				if (startDate != null && !"".equals(startDate))
					callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call, startDate+ 
							" 00:00:00", endDate+ " 23:59:59", (pageIndex - 1) * pageSize, pageSize);
				else
					callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call,"","", (pageIndex - 1) * pageSize, pageSize);

				for (CallListBean call2 : callList) {
					//Map<String, Object> source = searchHit.getSource();
					String posTime =call2.getStartTime();// (String) source.get("startTime");
					if (posTime != null) {
						String posTime2 = posTime.split(" ")[0];
						if (ticketDTOList.size() != 0) {
							int flag = 0;
							for (int i = 0; i < ticketDTOList.size(); i++) {
								String PosstartTime = ticketDTOList.get(i).getPosstartTime();
								String PosstartTime2 = PosstartTime.split(" ")[0];
								if (posTime2.equals(PosstartTime2)) {
									ticketDTOList.get(i).setDegreesum(ticketDTOList.get(i).getDegreesum() + 1);
									flag = 1;
								}
							}
							if (flag == 0) {
								TicketDegreeDTO ticketLinkDTO = new TicketDegreeDTO();
								ticketLinkDTO.setPosstartTime(posTime2);
								ticketLinkDTO.setDegreesum(1);
								ticketDTOList.add(ticketLinkDTO);
							}
						} else {
							TicketDegreeDTO ticketLinkDTO = new TicketDegreeDTO();
							ticketLinkDTO.setPosstartTime(posTime2);
							ticketLinkDTO.setDegreesum(1);
							ticketDTOList.add(ticketLinkDTO);
						}
					}

				}
				for (int i = 0; i < ticketDTOList.size() - 1; i++) {
					for (int j = 0; j < ticketDTOList.size() - 1 - i; j++) {
						if (ticketDTOList.get(j).getDegreesum() < ticketDTOList.get(j + 1).getDegreesum()) {
							TicketDegreeDTO strTemp = new TicketDegreeDTO();
							strTemp.setPosstartTime(ticketDTOList.get(j).getPosstartTime());
							strTemp.setDegreesum(ticketDTOList.get(j).getDegreesum());
							ticketDTOList.get(j).setPosstartTime(ticketDTOList.get(j + 1).getPosstartTime());
							ticketDTOList.get(j).setDegreesum(ticketDTOList.get(j + 1).getDegreesum());
							ticketDTOList.get(j + 1).setPosstartTime(strTemp.getPosstartTime());
							ticketDTOList.get(j + 1).setDegreesum(strTemp.getDegreesum());
						}
					}
				}
				Ticket = ticketDTOList;
			}
		}
		if (ticketDTOList.size() > 10) {
			for (int i = 0; i < 10; i++) {
				ticketDTOList2.add(ticketDTOList.get(i));
			}
		}else{
			ticketDTOList2.addAll(ticketDTOList);
		}
		actionLog((String) session.getAttribute("userName"), "查看", "通话次数");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(ticketDTOList2));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 查询嫌疑人是否存在
	@RequestMapping(value = "/checkEvName.php")
	public void checkEvName(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("text/html;charset=utf-8");
		String SuspectPhone = request.getParameter("SuspectPhone");

		String suName = request.getParameter("suName");
		String suPhone = request.getParameter("suPhone");
		String suEmail = request.getParameter("suEmail");


		List<SuspectInfo> suspectInfoList = new ArrayList<SuspectInfo>();

		if (SuspectPhone != null && !"".equals(SuspectPhone)) {
			/*String[] split = SuspectPhone.split(" ");
			String name = split[0];
			String phone = split[1];
			String mail = split[2];*/

			if (!"".equals(suName) && suName != null) {
				SuspectInfo suspectInfo = new SuspectInfo();
				suspectInfo.setSuspectName(suName);
				List<SuspectInfo> eviTemps = sqlDao.getListfromMysql(suspectInfo);
				if (eviTemps.size() > 0) {
					suspectInfo = eviTemps.get(0);
					if (suspectInfoList.size() == 0) {
						suspectInfoList.add(suspectInfo);
					} else {
						int flag = 0;
						for (SuspectInfo suspectInfo11 : suspectInfoList) {
							if (suspectInfo11.getId() != suspectInfo.getId()) {
								flag = 1;
							}
						}
						if (flag == 1) {
							suspectInfoList.add(suspectInfo);
						}
					}
				}
			}


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

	// 查询案件编号是否存在
	@RequestMapping(value = "/admin/checkCaseNum.php")
	public void checkCaseNum(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("text/html;charset=utf-8");
		String caseinfonum = request.getParameter("caseinfonum");
		String res = "succ";

		Caseinfo caseinfo = new Caseinfo();
		caseinfo.setCaseNum(caseinfonum);
		List<Caseinfo> caseinfos = sqlDao.getListfromMysql(caseinfo);
		if (caseinfos.size() > 0) {
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

	// 查询案件编号是否存在
	@RequestMapping(value = "/admin/checkName.php")
	public void checkName(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("text/html;charset=utf-8");
		String evName = request.getParameter("evName");
		String res = "succ";

		Evidence evi = new Evidence();
		evi.setEvName(evName);
		List<Evidence> evidences = sqlDao.getListfromMysql(evi);
		if (evidences.size() > 0) {
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

	// 查询案件标签是否存在
	@RequestMapping(value = "/admin/checkCaseLable.php")
	public void checkCaseLable(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("text/html;charset=utf-8");
		String labellinfonum = request.getParameter("labellinfonum");
		String res = "succ";

		Label labell = new Label();
		labell.setLabel(labellinfonum);
		List<Label> labellinfos = sqlDao.getListfromMysql(labell);
		if (labellinfos.size() > 0) {
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

	/**
	 * 数据挖掘- 执行分布式搜索访问并处理数据
	 */
	@RequestMapping(value = "/admin/searchAll.php")
	public void searchAll(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String regexpQuery = request.getParameter("regexpQuery");// 类型
		String suspectNumsStr = request.getParameter("suspectNumsStr");// 案件caseID-list
		String pageno = request.getParameter("pageno");
		int pageIndex = 1;
		int pageSize = 10;// 每页个数
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		List<String> list = new ArrayList<String>();

		if (suspectNumsStr != null && !"".equals(suspectNumsStr)) {
			String[] suspectNums = suspectNumsStr.split(",");

			for (String suspectNum : suspectNums) {
				// 精确搜索
				BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
				mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件
				// 此处为匹配所有文档

				String quickflag = "";
				if ("身份证号".equals(regexpQuery)) {
					quickflag = Global.regSFZ;
				} else if ("手机号".equals(regexpQuery)) {
					quickflag = Global.regPhone;
				} else if ("邮箱号".equals(regexpQuery)) {
					quickflag = Global.regEmail;
				} else if ("集装箱号".equals(regexpQuery)) {
					quickflag = Global.regContainer;
				} else if ("发票代码".equals(regexpQuery)) {
					quickflag = Global.regStamp;
				} else if ("车牌号".equals(regexpQuery)) {
					quickflag = Global.regLicense;
				} else if ("GPS".equals(regexpQuery)) {
					quickflag = Global.regGPS;
				}
				if (!"".equals(quickflag)) {
					QueryBuilder qb = QueryBuilders.regexpQuery("content", quickflag);
					mustQuery.must(qb);
				}
				SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("doc", "es", "hackerdb")
						.setTypes("docType", "email", "hkdb").setQuery(mustQuery).addHighlightedField("*")
						/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
						.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>")// 配置高亮显示搜索结果
						.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
						.setSize(pageSize);// 本次返回的文档数量
				// 执行搜索
				SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
						.setSize(pageSize)// 本次返回的文档数量
						.execute().actionGet();
				SearchHit[] hits = searchResponse.getHits().getHits();
				for (SearchHit searchHit : hits) {
					Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
					HighlightField highlightcontent = highlightFields.get("content");
					String gaoliang = highlightcontent.toString();
					String gaoliang1 = gaoliang.split("font")[1];
					String gaoliang2 = gaoliang1.substring(14, gaoliang1.length() - 2);
					list.add(gaoliang2);
				}
			}
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write(JsonUtil.list2json(list));
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
	}

	// 大搜索图片查询
	@RequestMapping(value = "/admin/SearchResultPic.php")
	public void SearchResultPic(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");

		String big_search_box = request.getParameter("big_search_box");

		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

		QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("picdesc", big_search_box);// .escape(true)//escape
		// 转义
		// 设为true，避免搜索[]、结尾为!的关键词时异常
		// 但无法搜索*
		mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件

		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery);/*.addHighlightedField("*") 星号表示在所有字段都高亮 .setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果
				 */
		searchRequestBuilder = searchRequestBuilder.addAggregation(AggregationBuilders.terms("agg1(聚类返回时根据此key获取聚类结果)")
				.size(1000)/* 返回1000条聚类结果 */.field("要在文档中聚类的字段，如果是嵌套的则用点连接父子字段，如【person.company.name】"));
		int pageIndex = 1;// 页数
		int pageSize = 8;// 每页
		String pageno = request.getParameter("pageno");
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索

		SearchHit[] hits = searchResponse.getHits().getHits();
		List<allesDTO> allesDTOList = new ArrayList<allesDTO>();
		for (SearchHit searchHit : hits) {
			allesDTO allesDTO = new allesDTO();
			Map<String, Object> source = searchHit.getSource();
			String picDirpath = (String) source.get("picDirpath");
			String caseID = (String) source.get("caseID");
			String picdesc = (String) source.get("picdesc");
			String picname = (String) source.get("picname");

			allesDTO.setCaseID(caseID);
			allesDTO.setPicDirpath(picDirpath);
			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

			HighlightField highlightpicdesc = highlightFields.get("picdesc");
			HighlightField highlightpicname = highlightFields.get("picname");

			if (highlightpicdesc != null) {
				String picDesc2 = highlightpicdesc.toString();
				allesDTO.setPicdesc(picDesc2);
			} else {
				allesDTO.setPicdesc(picdesc);
			}

			if (highlightpicname != null) {
				String picname2 = highlightpicname.toString();
				allesDTO.setPicname(picname2);
				;
			} else {
				allesDTO.setPicname(picname);
			}

			allesDTOList.add(allesDTO);
		}
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("allesDTOList", allesDTOList);
		hashMap.put("total", searchResponse.getHits().getTotalHits());
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(hashMap));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public void create(String path) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);

		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);

		// 给Excel的单元格设置样式和赋值
		cell.setCellValue("姓名");
		cell = row.createCell(1);
		cell.setCellValue("性别");
		cell = row.createCell(2);
		cell.setCellValue("电子邮箱");
		cell = row.createCell(3);
		cell.setCellValue("QQ号码");
		cell = row.createCell(4);
		cell.setCellValue("创建日期");
		cell = row.createCell(5);
		cell.setCellValue("身份证号");
		cell = row.createCell(6);
		cell.setCellValue("社保号");
		cell = row.createCell(7);
		cell.setCellValue("护照号");
		cell = row.createCell(8);
		cell.setCellValue("Facebook账号");
		cell = row.createCell(9);
		cell.setCellValue("Twitter账号");
		cell = row.createCell(10);
		cell.setCellValue("微信号");
		cell = row.createCell(11);
		cell.setCellValue("所在组织名称");
		cell = row.createCell(12);
		cell.setCellValue("组织地址");
		cell = row.createCell(13);
		cell.setCellValue("位置");
		cell = row.createCell(14);
		cell.setCellValue("标签");

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	public static void write(SuspectInfo bean, HSSFWorkbook wb, String path) throws Exception {
		HSSFSheet sheet = wb.getSheetAt(0);
		int begin = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();
		int m = 0;
		for (int n = begin; n <= end; n++) {
			try {
				m++;
			} catch (Exception e) {
				break;
			}
		}

		HSSFRow row1 = sheet.createRow(m);

		HSSFCell cell = row1.createCell(0);
		cell.setCellValue(bean.getSuspectName());
		cell = row1.createCell(1);
		cell.setCellValue(bean.getSuspectSex());
		cell = row1.createCell(2);
		cell.setCellValue(bean.getSuspectMail());
		cell = row1.createCell(3);
		cell.setCellValue(bean.getSuspectQQ());
		cell = row1.createCell(4);
		cell.setCellValue(bean.getCreateTime());
		cell = row1.createCell(5);
		cell.setCellValue(bean.getSuspectIDCardNumber());
		cell = row1.createCell(6);
		cell.setCellValue(bean.getSuspectSocialSecurity());
		cell = row1.createCell(7);
		cell.setCellValue(bean.getSuspectPassport());
		cell = row1.createCell(8);
		cell.setCellValue(bean.getSuspectFacebook());
		cell = row1.createCell(9);
		cell.setCellValue(bean.getSuspectTwitter());
		cell = row1.createCell(10);
		cell.setCellValue(bean.getSuspectMicroletters());
		cell = row1.createCell(11);
		cell.setCellValue(bean.getSuspectUnitName());
		cell = row1.createCell(12);
		cell.setCellValue(bean.getSuspectUnitAddress());
		cell = row1.createCell(13);
		cell.setCellValue(bean.getSuspectHomeAddress());
		cell = row1.createCell(14);
		cell.setCellValue(bean.getLabel());

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	// 批量导出人物画像
	@RequestMapping("/ExportWorkSpace.php")
	@ResponseBody
	public void delete(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String docpath = request.getParameter("docpath");
		String[] getStr = docpath.split(",");
		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");
		SuspectInfo suspectInfo = new SuspectInfo();
		try {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			String path = projectpath + filename + ".xls";
			File file = new File(path);
			create(path);
			fs = new POIFSFileSystem(new FileInputStream(path));
			wb = new HSSFWorkbook(fs);
			for (String str : getStr) {
				int a = Integer.parseInt(str);
				suspectInfo.setId(a);
				List<SuspectInfo> slist = sqlDao.getListfromMysql(suspectInfo);
				for (int i = 0; i < slist.size(); i++) {
					write(slist.get(i), wb, path);
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
		cell.setCellValue("通话时间");
		cell = row.createCell(1);
		cell.setCellValue("嫌疑人姓名");
		cell = row.createCell(2);
		cell.setCellValue("嫌疑人号码");
		cell = row.createCell(3);
		cell.setCellValue("对方号码");
		cell = row.createCell(4);
		cell.setCellValue("通信时长");
		cell = row.createCell(5);
		cell.setCellValue("通信地点");
		cell = row.createCell(6);
		cell.setCellValue("通信类型");
		/*
		 * cell = row.createCell(7); cell.setCellValue("护照号"); cell =
		 * row.createCell(8); cell.setCellValue("Facebook账号"); cell =
		 * row.createCell(9); cell.setCellValue("Twitter账号"); cell =
		 * row.createCell(10); cell.setCellValue("微信号"); cell =
		 * row.createCell(11); cell.setCellValue("所在组织名称"); cell =
		 * row.createCell(12); cell.setCellValue("组织地址"); cell =
		 * row.createCell(13); cell.setCellValue("位置");
		 */

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	public static void write1(TicketdDTO ticketdDTO, HSSFWorkbook wb1, String path1) throws Exception {
		HSSFSheet sheet = wb1.getSheetAt(0);
		int begin = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();
		int m = 0;
		for (int n = begin; n <= end; n++) {
			try {
				m++;
			} catch (Exception e) {
				break;
			}
		}

		HSSFRow row1 = sheet.createRow(m);

		HSSFCell cell = row1.createCell(0);
		cell.setCellValue(ticketdDTO.getStartTime());
		cell = row1.createCell(1);
		cell.setCellValue(ticketdDTO.getName());
		cell = row1.createCell(2);
		cell.setCellValue(ticketdDTO.getLocalNum());
		cell = row1.createCell(3);
		cell.setCellValue(ticketdDTO.getDialNumber());
		cell = row1.createCell(4);
		cell.setCellValue(ticketdDTO.getCallDuration());
		cell = row1.createCell(5);
		cell.setCellValue(ticketdDTO.getPosition());
		cell = row1.createCell(6);
		cell.setCellValue(ticketdDTO.getMethod());
		/*
		 * cell = row1.createCell(7);
		 * cell.setCellValue(ticketdDTO.getSuspectPassport()); cell =
		 * row1.createCell(8);
		 * cell.setCellValue(ticketdDTO.getSuspectFacebook()); cell =
		 * row1.createCell(9);
		 * cell.setCellValue(ticketdDTO.getSuspectTwitter()); cell =
		 * row1.createCell(10);
		 * cell.setCellValue(ticketdDTO.getSuspectMicroletters()); cell =
		 * row1.createCell(11);
		 * cell.setCellValue(ticketdDTO.getSuspectUnitName()); cell =
		 * row1.createCell(12);
		 * cell.setCellValue(ticketdDTO.getSuspectUnitAddress()); cell =
		 * row1.createCell(13);
		 * cell.setCellValue(ticketdDTO.getSuspectHomeAddress());
		 */

		FileOutputStream os = new FileOutputStream(path1);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb1.write(os);
		os.close();
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
		cell.setCellValue("案件类型");
		cell = row.createCell(1);
		cell.setCellValue("案件名称");
		cell = row.createCell(2);
		cell.setCellValue("案件编号");
		cell = row.createCell(3);
		cell.setCellValue("创建时间");
		cell = row.createCell(4);
		cell.setCellValue("操作人");
		cell = row.createCell(5);

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	public static void write2(Caseinfo ci, HSSFWorkbook wb1, String path1) throws Exception {
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
		cell.setCellValue(ci.getCaseType());
		cell = row1.createCell(1);
		cell.setCellValue(ci.getCaseName());
		cell = row1.createCell(2);
		cell.setCellValue(ci.getCaseNum());
		cell = row1.createCell(3);
		cell.setCellValue(ci.getCreatedTime());
		cell = row1.createCell(4);
		cell.setCellValue(ci.getTrustee());
		cell = row1.createCell(5);

		FileOutputStream os = new FileOutputStream(path1);
		wb1.write(os);
		os.close();
	}

	// 导出话单次数分析导出
	@RequestMapping("/admin/Exporthd.php")
	public void Exporthd(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");
		try {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			String path = projectpath + filename + ".xls";
			File file = new File(path);
			createTicket(path);
			fs = new POIFSFileSystem(new FileInputStream(path));
			wb = new HSSFWorkbook(fs);

			for (int i = 0; i < Ticket.size(); i++) {
				writeTicket(Ticket.get(i), wb, path);
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
		}
	}

	public void createTicket(String path) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);

		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);

		// 给Excel的单元格设置样式和赋值
		cell.setCellValue("日期");
		cell = row.createCell(1);
		cell.setCellValue("汇总");

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	public static void writeTicket(TicketDegreeDTO ticketDegreeDTO, HSSFWorkbook wb, String path) throws Exception {
		HSSFSheet sheet = wb.getSheetAt(0);
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
		cell.setCellValue(ticketDegreeDTO.getPosstartTime());
		cell = row1.createCell(1);
		cell.setCellValue(ticketDegreeDTO.getDegreesum() + "次");

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	/*
	 * YC_TODO: 2017/9/13 智能话单分析_联系人分析导出功能
	 */
	@RequestMapping("/admin/ExporthdContacts.php")
	public void ExporthdContacts(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
		String getClickName = request.getParameter("getlinkmanJSP");
		//getClickName = new String(getClickName.getBytes("iso-8859-1"), "utf-8");
		getClickName= URLDecoder.decode(getClickName, "utf-8");
		getLinkmanName = "";
		getPhone = "";
		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");
		try {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			String path = projectpath + filename + ".xls";
			File file = new File(path);
			createContacts(path);
			fs = new POIFSFileSystem(new FileInputStream(path));
			wb = new HSSFWorkbook(fs);
			if (!StringUtils.isEmpty(getClickName)) {
				for (TicketLinksDTO getTest : ticketLinksListOut) {
					if (getTest.getLinkman().equals(getClickName)) {
						List<TicketLinkDTO> test = getTest.getLicketLinkList();
						if (test.size() > 0) {
							getLinkmanStr = getTest.getLinkman().toString();
							for (TicketLinkDTO test1 : test) {
								if (!test1.getDialNumber().equals("")) {
									writeContacts(test1, wb, path);
								}
							}
						}
					}
				}
			} else {
				for (TicketLinksDTO getTest : ticketLinksListOut) {
					List<TicketLinkDTO> test = getTest.getLicketLinkList();
					if (test.size() > 0) {
						getLinkmanStr = getTest.getLinkman().toString();
						// writeContactsLists(getTest, wb, path);
						for (TicketLinkDTO test1 : test) {
							if (!test1.getDialNumber().equals("")) {
								writeContacts(test1, wb, path);
							}
						}
					}
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

	public void createContacts(String path) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);

		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);

		// 给Excel的单元格设置样式和赋值
		cell.setCellValue("嫌疑人姓名");
		cell = row.createCell(1);
		cell.setCellValue("嫌疑人手机号");
		cell = row.createCell(2);
		cell.setCellValue("对方号码");
		cell = row.createCell(3);
		cell.setCellValue("通话次数");

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	public static void writeContacts(TicketLinkDTO ticketLinkDTO, HSSFWorkbook wb, String path) throws Exception {
		HSSFSheet sheet = wb.getSheetAt(0);
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
		HSSFCell cell = row1.createCell(1);
		if (getPhone.equals(ticketLinkDTO.getLocalNum().toString())) {

		} else {
			cell.setCellValue(ticketLinkDTO.getLocalNum());
			getPhone = ticketLinkDTO.getLocalNum().toString();
		}

		cell = row1.createCell(0);

		if (getLinkmanName.equals(getLinkmanStr)) {

		} else {
			cell.setCellValue(getLinkmanStr);
			getLinkmanName = getLinkmanStr;
		}

		cell = row1.createCell(2);
		cell.setCellValue(ticketLinkDTO.getDialNumber());
		cell = row1.createCell(3);
		cell.setCellValue(ticketLinkDTO.getNumber());

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	// 话单分析——相互通话页面话单数据导出
	@RequestMapping("/admin/Exporthd_each.php")
	@ResponseBody
	public void Exporthd_each(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {



		String count1=request.getParameter("count");//导出数目
		int size=0;
		if (!StringUtils.isEmpty(count1)) {
			size = Integer.parseInt(count1);
		}
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

			/*			for (SearchHit searchHit2 : hits2) {

				TicketdDTO ticktd = new TicketdDTO();
				Map<String, Object> source = searchHit2.getSource();
				String method = (String) source.get("method");
				String name = (String) source.get("name");
				String startTime2 = (String) source.get("startTime");
				String localNum = (String) source.get("localNum");
				String dialNumber = (String) source.get("dialNumber");
				String callDuration = (String) source.get("callDuration");
				String position = (String) source.get("position");
				ticktd.setMethod(method);
				ticktd.setCallDuration(callDuration);
				ticktd.setDialNumber(dialNumber);
				ticktd.setLocalNum(localNum);
				ticktd.setName(name);
				ticktd.setStartTime(startTime2);
				ticktd.setPosition(position);
				caseidlist.add(ticktd);

			}*/
			for (int i = 0; i < caseidlist3.size(); i++) {
				write1(caseidlist3.get(i), wb1, path1);
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

	// 案件统计——案件记录导出
	@RequestMapping("/admin/Exporthd_case.php")
	@ResponseBody
	public void Exporthd_case(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String caseName = request.getParameter("caseName");
		String caseType = request.getParameter("caseType");

		// 解决字符编码问题
		/*
		 * try { caseName=new String(caseName.getBytes("iso8859-1"),"UTF-8"); }
		 * catch (UnsupportedEncodingException e1) { // TODO Auto-generated
		 * catch block e1.printStackTrace(); } try { caseType=new
		 * String(caseType.getBytes("iso8859-1"),"UTF-8"); } catch
		 * (UnsupportedEncodingException e1) { // TODO Auto-generated catch
		 * block e1.printStackTrace(); }
		 */

		Caseinfo cas = new Caseinfo();
		List<Caseinfo> logs = new ArrayList<Caseinfo>();
		/*
		 * if (!StringUtils.isEmpty(caseName)) { //System.out.println("书屌丝");
		 * cas.setCaseName(caseName); }
		 * 
		 * if (!StringUtils.isEmpty(caseType)) {
		 * //System.out.println("casetypelsdlsk"); cas.setCaseType(caseType); }
		 */
		List<Caseinfo> logs2 = sqlDao.getListfromMysql(cas);
		if (!StringUtils.isEmpty(caseName) && !StringUtils.isEmpty(caseType)) {
			for (Caseinfo ci : logs2) {
				if (ci.getCaseName().contains(caseName) && ci.getCaseType().contains(caseType))
					logs.add(ci);
			}
		} else if (!StringUtils.isEmpty(caseName)) {
			for (Caseinfo ci : logs2) {
				if (ci.getCaseName().contains(caseName))
					logs.add(ci);
			}
		} else if (!StringUtils.isEmpty(caseType)) {
			for (Caseinfo ci : logs2) {
				if (ci.getCaseType().contains(caseType))
					logs.add(ci);
			}
		} else
			logs = logs2;

		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");

		ArrayList<Caseinfo> caseidlist = new ArrayList<Caseinfo>();
		try {
			HSSFWorkbook wb1 = null;
			POIFSFileSystem fs = null;
			String path1 = projectpath + filename + ".xls";
			File file = new File(path1);
			create2(path1);
			fs = new POIFSFileSystem(new FileInputStream(path1));
			wb1 = new HSSFWorkbook(fs);

			for (Caseinfo c : logs) {

				Caseinfo ci = new Caseinfo();

				ci.setCaseName(c.getCaseName());
				ci.setCaseType(c.getCaseType());
				ci.setCaseNum(c.getCaseNum());
				ci.setCreatedTime(c.getCreatedTime());
				ci.setTrustee(c.getTrustee());
				caseidlist.add(ci);

			}
			for (int i = 0; i < caseidlist.size(); i++) {
				write2(caseidlist.get(i), wb1, path1);
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

	// 查询所有的更新日志
	@RequestMapping(value = "/admin/aboutMe.php")
	public String about_me(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String pageno = request.getParameter("pageno");
		String updateContent = request.getParameter("updateContent");
		String updateTime = request.getParameter("updateTime");
		String version=request.getParameter("version");

		int pageIndex = 1;
		int pageSize = 4;
		int num = 0;

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		UpdateLog update = new UpdateLog();

		if (!StringUtils.isEmpty(updateContent)) {
			update.setUpdateContent(updateContent);
		}
		if (!StringUtils.isEmpty(updateTime)) {
			update.setUpdateTime(updateTime);
		}
		if(!StringUtils.isEmpty(version)){
			update.setVersion(version);
		}

		List<UpdateLog> logs = sqlDao.getOrderListfromMysqlLike(update, (pageIndex - 1) * pageSize, pageSize, "id");
		int total = sqlDao.getcountfromMysqlLike(update);
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
		map.put("logs", logs);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("updateContent", updateContent);
		map.put("updateTime", updateTime);
		map.put("version", version);
		// request.setAttribute("lab", map);
		return "updatalog";
	}


	// 查询所有的更新日志
	@RequestMapping(value = "/admin/aboutMes.php")
	public String about_mes(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String pageno = request.getParameter("pageno");


		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		UpdateLog update = new UpdateLog();


		List<UpdateLog> logs = sqlDao.getOrderListfromMysqlLike(update, (pageIndex - 1) * pageSize, pageSize, "id");
		int total = sqlDao.getcountfromMysqlLike(update);
		num = total / pageSize;

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

		/*			map.put("logs", result_data);
				map.put("totalNum", total);
				map.put("totalPages", num);
				map.put("nowPage", pageIndex);*/
		//	map.put("pageList", pageList);

		// request.setAttribute("lab", map);
		return "updatalog";
	}

	/**
	 * 收藏夹页面跳转
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
	@RequestMapping(value = "/admin/favorite.php")
	public String favorite(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "favorite";
	}

	/**
	 * 查看收藏的电子邮件
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
	 */
	@RequestMapping(value = "/admin/showEmail.php")
	public void showEmail(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 10;// 每页个数

		String pageno = request.getParameter("pageIndex");
		String favoriteLabel = request.getParameter("favoriteLabel");
		String favoCaseName = request.getParameter("favoCaseName");
		String favoUser = request.getParameter("favoUser");
		logger.info("--favoCaseName--" + favoCaseName);

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		Caseinfo cas = new Caseinfo();
		User user = (User) session.getAttribute("user");
		String roleName="" ;
		if(user != null){
			String userrole = user.getUserrole();
			Role role = new Role();
			role.setId(Integer.parseInt(userrole));
			List<Role> roleList = sqlDao.getListfromMysql(role);
			if(roleList.size()>0){
				Role role2 = roleList.get(0);
				roleName = role2.getDataScope();

			}
		}
		if (roleName.equals("科室数据") ) {// 科长
			cas.setSection(user.getSection()+"/"+user.getUsername());
		} else if (roleName.equals("部门数据") ) {// 部长
			cas.setDepartment(user.getPartment());
		} else if (roleName.equals("个人数据") ) {// 科员
			cas.setUserName(user.getUsername());
		} else if (roleName.equals("所有数据")) {// 局长
			cas.setId(-1);
		} else {
			cas.setId(-2);
		}
		List<Caseinfo> listfromMysqlLikeOR = sqlDao.getListfromMysqlLikeOR(cas);
		ArrayList<String> caseids = new ArrayList<String>();
		for (Caseinfo caseinfo : listfromMysqlLikeOR) {
			caseids.add(caseinfo.getId()+"");
		}

		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();

		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
		mustQuery.must(QueryBuilders.matchPhraseQuery("starFlag", "1"));
		// caseid集合
		mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		logger.info("电子邮件收藏类别查询：" + favoriteLabel);
		// && !StringUtils.equals(favoriteLabel, "undefined")
		if (StringUtils.isNotEmpty(favoriteLabel)) {
			mustQuery.must(QueryBuilders.wildcardQuery("favoriteLabel", "*" + favoriteLabel + "*"));
		}
		// 按 案件名称搜索
		if (StringUtils.isNotEmpty(favoCaseName)) {
			mustQuery.must(QueryBuilders.wildcardQuery("caseName","*" + favoCaseName + "*"));
		}
		// 按收藏人搜索
		if (StringUtils.isNotEmpty(favoUser)) {
			mustQuery.must(QueryBuilders.wildcardQuery("favoritePerson", "*" + favoUser + "*"));
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索
		String string = searchResponse.toString();

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(string);
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
	 * 文档工作台点击星标收藏文档
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
	 */
	@RequestMapping(value = "/admin/favoDocument.php")
	public void favoDocument(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String docId = request.getParameter("esid");
		String flag = request.getParameter("flag");
		String docLabel = request.getParameter("docLabel"); // 取标签
		logger.info("--starflag--" + flag);

		PrintWriter writer = null;
		try {
			Map<String, String> updateMap = new HashMap<>();
			if (flag.equals("0")) {
				updateMap.put("starFlag", "1");
				updateMap.put("favoriteLabel", docLabel);
				actionLog((String) session.getAttribute("userName"), "收藏", "文档挖掘");
			}
			if (flag.equals("1")) {
				updateMap.put("starFlag", "0");
				updateMap.put("favoriteLabel", docLabel);
			}
			// 设置收藏时间
			String time = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
			// 设置收藏人
			HttpSession httpSession = request.getSession();
			String favoritePerson = (String) httpSession.getAttribute("userName");

			updateMap.put("favoriteTime", time);
			updateMap.put("favoritePerson", favoritePerson);
			EsUpdate.updateTest("doc", "docType", docId, updateMap);

			writer = response.getWriter();
			writer.write("{\"status\":\"success\"}");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
	/**
	 * 收藏夹文档更改标签
	 * @param request
	 * @param map
	 * @param session
	 * @param response
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/admin/changeDocLabel.php")
	public void changeDocLabel(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String docId = request.getParameter("esIdvalues");
		String docLabel = request.getParameter("labelsValues"); // 取标签

		String docIdSub = docId.substring(0, docId.length()-1);
		String docLabelSub = docLabel.substring(0, docLabel.length()-1);

		String[] split = docIdSub.split(",");
		PrintWriter writer = null;
		try {
			Map<String, String> updateMap = new HashMap<>();
			updateMap.put("favoriteLabel", docLabelSub);

			// 设置收藏时间
			//String time = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
			// 设置收藏人
			//HttpSession httpSession = request.getSession();
			//String favoritePerson = (String) httpSession.getAttribute("userName");

			//			updateMap.put("favoriteTime", time);
			//			updateMap.put("favoritePerson", favoritePerson);
			for (String splitId : split) {
				EsUpdate.updateTest("doc", "docType", splitId, updateMap);
			}


			writer = response.getWriter();
			writer.write("{\"status\":\"success\"}");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
	/**
	 * 收藏夹邮件更改标签
	 * @param request
	 * @param map
	 * @param session
	 * @param response
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/admin/changeEmailLabel.php")
	public void changeEmailLabel(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String docId = request.getParameter("esIdvalues_email");
		String emailLabel = request.getParameter("labelsValues_email"); // 取标签

		String emailIdSub = docId.substring(0, docId.length()-1);
		String emailLabelSub = emailLabel.substring(0, emailLabel.length()-1);

		String[] split = emailIdSub.split(",");
		PrintWriter writer = null;
		try {
			Map<String, String> updateMap = new HashMap<>();
			updateMap.put("favoriteLabel", emailLabelSub);


			for (String splitId : split) {
				EsUpdate.updateTest("es", "email", splitId, updateMap);
			}

			writer = response.getWriter();
			writer.write("{\"status\":\"success\"}");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
	/**
	 * 收藏夹图片更改标签
	 * @param request
	 * @param map
	 * @param session
	 * @param response
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/admin/changePicLabel.php")
	public void changePicLabel(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String picId = request.getParameter("esIdvalues_pic");
		String picLabel = request.getParameter("labelsValues_pic"); // 取标签

		String picIdSub = picId.substring(0, picId.length()-1);
		String picLabelSub = picLabel.substring(0, picLabel.length()-1);

		String[] split = picIdSub.split(",");
		PrintWriter writer = null;
		try {
			Map<String, String> updateMap = new HashMap<>();
			updateMap.put("favoriteLabel", picLabelSub);

			for (String splitPicId : split) {
				EsUpdate.updateTest("pic", "picindex", splitPicId, updateMap);
			}

			writer = response.getWriter();
			writer.write("{\"status\":\"success\"}");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	/**
	 * 文档工作台修改已读未读
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
	 */
	@RequestMapping(value = "/admin/upReadFlag.php")
	public void upReadFlag(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String docId = request.getParameter("esId");
		String flag = request.getParameter("readflag");

		PrintWriter writer = null;
		try {
			Map<String, String> updateMap = new HashMap<>();
			if (!"1".equals(flag)) {
				updateMap.put("readFlag", "1");
			}
			EsUpdate.updateTest("doc", "docType", docId, updateMap);

			writer = response.getWriter();
			writer.write("{\"status\":\"success\"}");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	/**
	 * 图片挖掘点击星标收藏图片
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
	 */
	@RequestMapping(value = "/admin/favoImg.php")
	public void favoImg(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String imgId = request.getParameter("esid"); // 数据库图片id
		String imgStarFlag = request.getParameter("flag"); // 数据库图片星标
		String imgLabel = request.getParameter("picLabel"); // 取标签

		PrintWriter writer = null;
		try {
			Map<String, String> updateMap = new HashMap<>();
			if (imgStarFlag.equals("0")) {
				updateMap.put("starFlag", "1");
				updateMap.put("favoriteLabel", imgLabel);
				actionLog((String) session.getAttribute("userName"), "收藏", "图片挖掘");
			}
			if (imgStarFlag.equals("1")) {
				updateMap.put("starFlag", "0");
				updateMap.put("favoriteLabel", imgLabel);

			}
			// 设置收藏时间
			String time = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
			// 设置收藏人
			HttpSession httpSession = request.getSession();
			String favoritePerson = (String) httpSession.getAttribute("userName");

			updateMap.put("favoriteTime", time);
			updateMap.put("favoritePerson", favoritePerson);
			EsUpdate.updateTest("pic", "picindex", imgId, updateMap);

			writer = response.getWriter();
			writer.write("{\"status\":\"success\"}");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	// 查询所有已收藏的文档
	@RequestMapping(value = "/admin/showAllDoc.php")
	public void showAllDoc(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 10;// 每页个数

		String pageno = request.getParameter("pageIndex");
		String favoriteLabel = request.getParameter("favoriteLabel");
		String favoCaseName = request.getParameter("favoCaseName");
		String favoUser = request.getParameter("favoUser");
		String evStatus = (String) request.getParameter("evStatus");// 文件类型

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		Caseinfo cas = new Caseinfo();
		User user = (User) session.getAttribute("user");
		String roleName="" ;
		if(user != null){
			String userrole = user.getUserrole();
			Role role = new Role();
			role.setId(Integer.parseInt(userrole));
			List<Role> roleList = sqlDao.getListfromMysql(role);
			if(roleList.size()>0){
				Role role2 = roleList.get(0);
				roleName = role2.getDataScope();

			}
		}
		if (roleName.equals("科室数据") ) {// 科长
			cas.setSection(user.getSection()+"/"+user.getUsername());
		} else if (roleName.equals("部门数据") ) {// 部长
			cas.setDepartment(user.getPartment());
		} else if (roleName.equals("个人数据") ) {// 科员
			cas.setUserName(user.getUsername());
		} else if (roleName.equals("所有数据")) {// 局长
			cas.setId(-1);
		} else {
			cas.setId(-2);
		}
		List<Caseinfo> listfromMysqlLikeOR = sqlDao.getListfromMysqlLikeOR(cas);
		ArrayList<String> caseids = new ArrayList<String>();
		for (Caseinfo caseinfo : listfromMysqlLikeOR) {
			caseids.add(caseinfo.getId()+"");
		}
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();

		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
		mustQuery.must(QueryBuilders.matchPhraseQuery("starFlag", "1"));
		// caseid集合
		mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		logger.info("文档收藏类别查询：" + favoriteLabel);
		if (StringUtils.isNotEmpty(favoriteLabel) && !StringUtils.equals(favoriteLabel, "undefined")) {
			mustQuery.must(QueryBuilders.wildcardQuery("favoriteLabel", "*" + favoriteLabel + "*"));
		}
		// 搜索按案件名称查询
		if (StringUtils.isNotEmpty(favoCaseName)) {
			mustQuery.must(QueryBuilders.wildcardQuery("caseName", "*" + favoCaseName + "*"));
		}
		if (evStatus != null && !"".equals(evStatus)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("docType", evStatus));// 添加文件类型查询条件
		}

		// 搜索 按收藏人查询
		if (StringUtils.isNotEmpty(favoUser)) {
			mustQuery.must(QueryBuilders.wildcardQuery("favoritePerson", "*" + favoUser + "*"));
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("doc").setTypes("docType")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索
		String string = searchResponse.toString();

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(string);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 查询所有已收藏的文档
	@RequestMapping(value = "/admin/showAllPic.php")
	public void showAllPic(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 10;// 每页个数

		String pageno = request.getParameter("pageIndex");
		String favoriteLabel = request.getParameter("favoriteLabel");
		String favoPicName = request.getParameter("favoPicName"); // 图片名称
		String favoriteNameOfPic = request.getParameter("favoriteNameOfPic"); // 收藏人

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		Caseinfo cas = new Caseinfo();
		User user = (User) session.getAttribute("user");
		String roleName="" ;
		if(user != null){
			String userrole = user.getUserrole();
			Role role = new Role();
			role.setId(Integer.parseInt(userrole));
			List<Role> roleList = sqlDao.getListfromMysql(role);
			if(roleList.size()>0){
				Role role2 = roleList.get(0);
				roleName = role2.getDataScope();

			}
		}
		if (roleName.equals("科室数据") ) {// 科长
			cas.setSection(user.getSection()+"/"+user.getUsername());
		} else if (roleName.equals("部门数据") ) {// 部长
			cas.setDepartment(user.getPartment());
		} else if (roleName.equals("个人数据") ) {// 科员
			cas.setUserName(user.getUsername());
		} else if (roleName.equals("所有数据")) {// 局长
			cas.setId(-1);
		} else {
			cas.setId(-2);
		}
		List<Caseinfo> listfromMysqlLikeOR = sqlDao.getListfromMysqlLikeOR(cas);
		ArrayList<String> caseids = new ArrayList<String>();
		for (Caseinfo caseinfo : listfromMysqlLikeOR) {
			caseids.add(caseinfo.getId()+"");
		}

		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();

		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
		mustQuery.must(QueryBuilders.matchPhraseQuery("starFlag", "1"));

		// caseid集合
		mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		logger.info("图片收藏类别查询：" + favoriteLabel);
		// && !StringUtils.equals(favoriteLabel, "undefined")
		if (StringUtils.isNotEmpty(favoriteLabel)) {
			mustQuery.must(QueryBuilders.wildcardQuery("favoriteLabel", "*" + favoriteLabel + "*"));
		}
		// 按 案件名称搜索
		if (StringUtils.isNotEmpty(favoPicName)) {
			mustQuery.must(QueryBuilders.wildcardQuery("picname", "*" + favoPicName + "*"));
		}

		// 按收藏人搜索
		if (StringUtils.isNotEmpty(favoriteNameOfPic)) {
			mustQuery.must(QueryBuilders.wildcardQuery("favoritePerson", "*" + favoriteNameOfPic + "*"));
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索
		String string = searchResponse.toString();

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(string);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 执行分布式话单通话次数数据统计 第三个页面通话统计
	@RequestMapping(value = "/admin/getTicketCount.php")
	public void getTicketCount(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 1000;// 每页个数

		// 按条件查询
		// String startDate = request.getParameter("startDate");// 开始时间
		// endDate = request.getParameter("endDate");// 结束时间
		String suspectNumsStr = request.getParameter("suspectNumsStr");// 嫌疑人手机号list
		String posstartTime = (String) request.getParameter("posstartTime");// 通话时间
		String endTime = (String) request.getParameter("endTime");
		String caseidStr = request.getParameter("caseidStr");//案件id
		// String suspectNums="";
		String[] caseids = { "" };
		if (!"".equals(caseidStr)){
			caseids = caseidStr.split(" ");
		}
		// 如果未选嫌疑人 默认查找最新数据下的嫌疑人
		if (suspectNumsStr == null || "".equals(suspectNumsStr)) {
			suspectNumsStr="-1";
		}

		Map<String, Object> map = new HashMap<>();
		ArrayList<TicketDegreeDTO> ticketDTOList = new ArrayList<TicketDegreeDTO>();
		ArrayList<TicketDegreeDTO> ticketDTOList1 = new ArrayList<TicketDegreeDTO>();
		if (suspectNumsStr != null && !"".equals(suspectNumsStr)) {
			// suspectNums = suspectNumsStr.split(",")[0];
			String[] suspectNums = suspectNumsStr.split(",");
			for (String suspectNum : suspectNums) {
				CallListBean call=new CallListBean();
				call.setLocalNum(suspectNum);
				call.setCaseID(caseidStr);

				List<CallListBean> callList=new ArrayList<CallListBean>();
				long totalHits=0;
				if (posstartTime != null && !"".equals(posstartTime)){
					callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call, posstartTime+ 
							" 00:00:00", endTime+ " 23:59:59", (pageIndex - 1) * pageSize, pageSize);
					totalHits=sqlDao.getcountfromMysqlLike_callLog(call,posstartTime+ 
							" 00:00:00", endTime+ " 23:59:59");
				}
				else{
					callList=sqlDao.getOrderListfromMysqlLikTime_callLog(call,"","", (pageIndex - 1) * pageSize, pageSize);
					totalHits=sqlDao.getcountfromMysqlLike_callLog(call,"", "");
				}

				//SearchHit[] hits = searchResponse.getHits().getHits();
				map.put("星期一", 0);
				map.put("星期二", 0);
				map.put("星期三", 0);
				map.put("星期四", 0);
				map.put("星期五", 0);
				map.put("星期六", 0);
				map.put("星期日", 0);

				//long totalHits = searchResponse.getHits().getTotalHits();
				map.put("上午", 0);
				map.put("下午", 0);
				map.put("晚上", 0);

				for (CallListBean call2 : callList) {
					//Map<String, Object> source = searchHit.getSource();
					String posTime =call2.getStartTime();// (String) source.get("startTime");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
					try {
						Date date_util = sdf.parse(posTime);
						String weeks = date_util.toString().substring(0, 3);
						if (weeks.equals("Mon")) {
							map.put("星期一", Integer.parseInt(map.get("星期一").toString()) + 1);
						}
						if (weeks.equals("Tue")) {
							map.put("星期二", Integer.parseInt(map.get("星期二").toString()) + 1);
						}
						if (weeks.equals("Wed")) {
							map.put("星期三", Integer.parseInt(map.get("星期三").toString()) + 1);
						}
						if (weeks.equals("Thu")) {
							map.put("星期四", Integer.parseInt(map.get("星期四").toString()) + 1);
						}
						if (weeks.equals("Fri")) {
							map.put("星期五", Integer.parseInt(map.get("星期五").toString()) + 1);
						}
						if (weeks.equals("Sat")) {
							map.put("星期六", Integer.parseInt(map.get("星期六").toString()) + 1);
						}
						if (weeks.equals("Sun")) {
							map.put("星期日", Integer.parseInt(map.get("星期日").toString()) + 1);
						}

						Date ticketDate; // 话单中的时间
						Date myStartDates;
						Date myEndDates; // 我的第一个时间段

						Date myStartDates2;
						Date myEndDates2; // 我的第二个时间段

						Date myStartDates3;
						Date myEndDates3; // 我的第三个时间段
						String subTicketDate = posTime.substring(11, 19); // 话单中的时间
						try {
							ticketDate = sdf1.parse(subTicketDate);
							long ticketTime = ticketDate.getTime();

							myStartDates = sdf1.parse("08:00:00");
							long myStartTimes = myStartDates.getTime();

							myEndDates = sdf1.parse("15:59:59");
							long myEndTimes = myEndDates.getTime();
							if (ticketTime >= myStartTimes && ticketTime <= myEndTimes) {
								map.put("上午", Integer.parseInt(map.get("上午").toString()) + 1);
							}
							myStartDates2 = sdf1.parse("16:00:00");
							long myStartTimes2 = myStartDates2.getTime();

							myEndDates2 = sdf1.parse("23:59:59");
							long myEndTimes2 = myEndDates2.getTime();
							if (ticketTime >= myStartTimes2 && ticketTime <= myEndTimes2) {
								map.put("下午", Integer.parseInt(map.get("下午").toString()) + 1);
							}
							myStartDates3 = sdf1.parse("00:00:00");
							long myStartTimes3 = myStartDates3.getTime();

							myEndDates3 = sdf1.parse("07:59:59");
							long myEndTimes3 = myEndDates3.getTime();
							if (ticketTime >= myStartTimes3 && ticketTime <= myEndTimes3) {
								map.put("晚上", Integer.parseInt(map.get("晚上").toString()) + 1);
							}
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (posTime != null) {
						String posTime2 = posTime.split(" ")[0];
						if (ticketDTOList.size() != 0) {
							int flag = 0;
							for (int i = 0; i < ticketDTOList.size(); i++) {
								String PosstartTime = ticketDTOList.get(i).getPosstartTime();
								String PosstartTime2 = PosstartTime.split(" ")[0];
								if (posTime2.equals(PosstartTime2)) {
									ticketDTOList.get(i).setDegreesum(ticketDTOList.get(i).getDegreesum() + 1);
									flag = 1;
								}
							}
							if (flag == 0) {
								TicketDegreeDTO ticketLinkDTO = new TicketDegreeDTO();
								ticketLinkDTO.setPosstartTime(posTime2);
								ticketLinkDTO.setDegreesum(1);
								ticketDTOList.add(ticketLinkDTO);
							}
						} else {
							TicketDegreeDTO ticketLinkDTO = new TicketDegreeDTO();
							ticketLinkDTO.setPosstartTime(posTime2);
							ticketLinkDTO.setDegreesum(1);
							ticketDTOList.add(ticketLinkDTO);
						}
					}
					for (int i = 0; i < ticketDTOList.size() - 1; i++) {
						for (int j = 0; j < ticketDTOList.size() - 1 - i; j++) {
							if (ticketDTOList.get(j).getDegreesum() < ticketDTOList.get(j + 1).getDegreesum()) {
								TicketDegreeDTO strTemp = new TicketDegreeDTO();
								strTemp.setPosstartTime(ticketDTOList.get(j).getPosstartTime());
								strTemp.setDegreesum(ticketDTOList.get(j).getDegreesum());
								ticketDTOList.get(j).setPosstartTime(ticketDTOList.get(j + 1).getPosstartTime());
								ticketDTOList.get(j).setDegreesum(ticketDTOList.get(j + 1).getDegreesum());
								ticketDTOList.get(j + 1).setPosstartTime(strTemp.getPosstartTime());
								ticketDTOList.get(j + 1).setDegreesum(strTemp.getDegreesum());
							}
						}
					}
				}
			}
		}
		if (ticketDTOList.size() > 10) {
			for (int i = 0; i < 10; i++) {
				ticketDTOList1.add(ticketDTOList.get(i));
			}
		}else{
			ticketDTOList1.addAll(ticketDTOList);
		}

		map.put("ticketDTOList", ticketDTOList1);
		//map.put("ticketDTOList",ticketDTOList);
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


	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date y = c.getTime();
		String year = format.format(y);
	}

	// 查询数据批次
	@RequestMapping(value = "/admin/showDataList.php")
	public void showDataList(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		Evidence ev = new Evidence();
		int evNum = sqlDao.getcountfromMysqlLike(ev);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(evNum + "");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 查询命中嫌疑人
	@RequestMapping(value = "/admin/showSuspect.php")
	public void showSuspect(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		SuspectInfo sup = new SuspectInfo();
		int suspectNum = sqlDao.getcountfromMysqlLike(sup);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(suspectNum + "");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 查询年度案件总数
	@RequestMapping(value = "/admin/showAllCaseNum.php")
	public void showAllCaseNum(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		// String createdTime=request.getParameter("createdTime");
		// String endTime =request.getParameter("endTime");
		// String okTime = request.getParameter("2017-8-25");
		String createdTime = "";
		String endTime = "";
		// String okTime ="2016-08-25";
		Caseinfo cas = new Caseinfo();
		// 过去一年
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date y = c.getTime();
		String year = format.format(y);

		createdTime = year;
		endTime = format.format(new Date());

		int total = sqlDao.getListfromMysqlLikecaseCount(cas, createdTime, endTime);
		// request.setAttribute("rt",total);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(total + "");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	// 查询案件总数
	@RequestMapping(value = "/admin/showAllCase.php")
	public void showAllCase(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		Caseinfo cas = new Caseinfo();
		int total = sqlDao.getcountfromMysqlLike(cas);

		// request.setAttribute("rt",total);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(total + "");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 邮件工作台 发件人排序
		@RequestMapping(value = "/admin/getEmail_Workbench.php")
		public void getEmail_Workbench(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
			response.setContentType("textml; charset=UTF-8");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String caseidStr = request.getParameter("caseidStr");
			String pageno = request.getParameter("pageno");
			String XYRemail = request.getParameter("XYRemail");//案件下嫌疑人的邮箱
			String XYRConnectNum = request.getParameter("XYRConnectNum");//案件下嫌疑人的邮箱的收发件次数
			
			if (caseidStr == null || "".equals(caseidStr)) {
				caseidStr="-1";
			}
			String XYRemail2="";
			if(XYRemail!=null&&!"".equals(XYRemail)){
				String email[] = XYRemail.split(",");
				for (String string : email) {
					XYRemail2 +="'"+string+"'"+","; 
				}
				XYRemail2=XYRemail2.substring(0, XYRemail2.lastIndexOf(","));
			}
			if(XYRConnectNum == null || "".equals(XYRConnectNum)){
				XYRConnectNum="100";//没输入次数，默认查询100次以上的
			}
			String[] caseids = { "" };
			if (!"".equals(caseidStr)) {
				caseids = caseidStr.split(" ");
			}
			//获取caseID，目前只对一个案件分析 
			String caseId = caseids[0];
			int caseidInt = Integer.valueOf(caseId);
			String tableName = "";
			if(caseidInt%9==0){
				tableName="from_to_count_0";
			} else if(caseidInt%9==1){
				tableName="from_to_count_1";
			} else if(caseidInt%9==2){
				tableName="from_to_count_2";
			} else if(caseidInt%9==3){
				tableName="from_to_count_3";
			} else if(caseidInt%9==4){
				tableName="from_to_count_4";
			} else if(caseidInt%9==5){
				tableName="from_to_count_5";
			} else if(caseidInt%9==6){
				tableName="from_to_count_6";
			} else if(caseidInt%9==7){
				tableName="from_to_count_7";
			} else if(caseidInt%9==8){
				tableName="from_to_count_8";
			}
			List<Email_WorkDTO> email_workDTO = null;
			try {
				if(caseidInt!=-1){
					email_workDTO = emailDao.getFromToCount(caseId,"1",tableName,XYRemail2,XYRConnectNum);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			email_workDTOExport1 = email_workDTO;
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write(JsonUtil.list2json(email_workDTO));
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}

		// 邮件工作台 收件人排序
		@RequestMapping(value = "/admin/getEmail_Work.php")
		public void getEmail_Work(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
			response.setContentType("textml; charset=UTF-8");
			String pageno = request.getParameter("pageno");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String caseidStr = request.getParameter("caseidStr");
			String XYRemail = request.getParameter("XYRemail");//案件下嫌疑人的邮箱
			String XYRConnectNum = request.getParameter("XYRConnectNum");//案件下嫌疑人的邮箱的收发件次数过滤
			if (caseidStr == null || "".equals(caseidStr)) {
				caseidStr="-1";
			}
			String XYRemail2="";
			if(XYRemail!=null&&!"".equals(XYRemail)){
				String email[] = XYRemail.split(",");
				for (String string : email) {
					XYRemail2 +="'"+string+"'"+","; 
				}
				XYRemail2=XYRemail2.substring(0, XYRemail2.lastIndexOf(","));
			}
			if(XYRConnectNum == null || "".equals(XYRConnectNum)){
				XYRConnectNum="100";
			}
			String[] caseids = { "" };
			if (!"".equals(caseidStr)) {
				caseids = caseidStr.split(" ");
			}
			//获取caseID，目前只对一个案件分析 
			String caseId = caseids[0];
			int caseidInt = Integer.valueOf(caseId);
			String tableName = "";
			if(caseidInt%9==0){
				tableName="from_to_count_0";
			} else if(caseidInt%9==1){
				tableName="from_to_count_1";
			} else if(caseidInt%9==2){
				tableName="from_to_count_2";
			} else if(caseidInt%9==3){
				tableName="from_to_count_3";
			} else if(caseidInt%9==4){
				tableName="from_to_count_4";
			} else if(caseidInt%9==5){
				tableName="from_to_count_5";
			} else if(caseidInt%9==6){
				tableName="from_to_count_6";
			} else if(caseidInt%9==7){
				tableName="from_to_count_7";
			} else if(caseidInt%9==8){
				tableName="from_to_count_8";
			}
			List<Email_WorkDTO> email_workDTO = null;
			try {
				if(caseidInt!=-1){
					email_workDTO = emailDao.getFromToCount(caseId,"2",tableName,XYRemail2,XYRConnectNum);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			email_workDTOExport = email_workDTO;
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write(JsonUtil.list2json(email_workDTO));
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}
		}
	// 邮件挖掘-收发件 导出功能
	@RequestMapping("/ExportToFromEmail.php")
	public void ExportToFromEmail(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");
		try {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			String path = projectpath + filename + ".xls";
			File file = new File(path);
			createToFromData(path);
			fs = new POIFSFileSystem(new FileInputStream(path));
			wb = new HSSFWorkbook(fs);
			for (int i = 0; i < email_workDTOExport.size(); i++) {
				writeToFromData(email_workDTOExport.get(i), wb, path);
			}

			for (int i = 0; i < email_workDTOExport1.size(); i++) {
				writeToFromData1(email_workDTOExport1.get(i), wb, path);
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
			System.out.println(e);
		}
	}

	public void createToFromData(String path) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("收件人邮箱");
		HSSFSheet sheet1 = wb.createSheet("发件人邮箱");

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);
		HSSFRow row1 = sheet1.createRow(0);
		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);
		HSSFCell cell1 = row1.createCell(0);

		// 给Excel的单元格设置样式和赋值
		cell.setCellValue("收件人邮件");
		cell1.setCellValue("发件人邮件");
		cell = row.createCell(1);
		cell.setCellValue("邮件总次数");
		cell1 = row1.createCell(1);
		cell1.setCellValue("邮件总次数");

		FileOutputStream os = new FileOutputStream(path);
		wb.write(os);
		os.close();
	}

	public static void writeToFromData(Email_WorkDTO bean, HSSFWorkbook wb, String path) throws Exception {
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFSheet sheet1 = wb.getSheetAt(1);
		int begin = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();
		int m = 0;
		for (int n = begin; n <= end; n++) {
			try {
				m++;
			} catch (Exception e) {
				break;
			}
		}
		HSSFRow row1 = sheet.createRow(m);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue(bean.getFormWho());
		cell = row1.createCell(1);
		cell.setCellValue(bean.getEmailNum() + "次");

		FileOutputStream os = new FileOutputStream(path);
		wb.write(os);
		os.close();
	}

	public static void writeToFromData1(Email_WorkDTO bean, HSSFWorkbook wb, String path) throws Exception {
		HSSFSheet sheet1 = wb.getSheetAt(1);
		int begin = sheet1.getFirstRowNum();
		int end = sheet1.getLastRowNum();
		int m = 0;
		for (int n = begin; n <= end; n++) {
			try {
				m++;
			} catch (Exception e) {
				break;
			}
		}

		HSSFRow row1 = sheet1.createRow(m);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue(bean.getToWho());
		cell = row1.createCell(1);
		cell.setCellValue(bean.getEmailNum() + "次");

		FileOutputStream os = new FileOutputStream(path);
		wb.write(os);
		os.close();
	}

	// 图片人脸识别
	@RequestMapping(value = "/admin/faceDetect.php")
	public String faceDetect(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "faceDetect";
	}

	// 图片人脸识别实现
	@RequestMapping(value = "/admin/getFaceDetect.php")
	public void getFaceDetect(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws Exception {

		String picPath1 = request.getParameter("pUrl");
		int index = picPath1.indexOf("picture/");
		String subpicPath = picPath1.substring(index + "picture/".length());
		String imagePath = "/emaildata/" + subpicPath;
		//String imagePath = "D:/beidouyun_Img/emaildata/" + subpicPath; //windows 下路径
		// String imagePath ="C:/Users/xunli/Pictures/hy.jpg";//测试时写死
		String toPath = "/emaildata/faceDetect/";
		//String toPath = "D:/emaildata/faceDetect/";//windows 下路径
		// String toPath = "C:/Users/xunli/Desktop/test/";//测试时路径
		// picPath="C:/Users/admin/Desktop/shanxi.jpg";
		// 进行url解码
		imagePath = UrlCodeUtil.urlDecode(imagePath);
		logger.info("---picPath---" + imagePath);
		if (StringUtils.isNotEmpty(imagePath)) {
			// 导入opencv库
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			// 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
			// String xmlFilePath =
			// "C:\\MyApps\\opencv\\sources\\data\\lbpcascades\\lbpcascade_frontalface.xml";//haarcascade_frontalface_alt.xml
			String xmlFilePath = MainFrameAction.class.getResource("/lbpcascade_frontalface.xml").getPath();//linux下不加substring(1)

			CascadeClassifier faceDetector = new CascadeClassifier(xmlFilePath);

			// 将图片转换为api所能接受的格式
			Mat image = Highgui.imread(imagePath);
			// 调用分类器上的方法传递图像和matofrect对象，进行面部检测
			MatOfRect faceDetections = new MatOfRect();
			faceDetector.detectMultiScale(image, faceDetections);

			// 遍历所有脸部检测，用矩形标记，并提取脸部图片并保存到指定路径
			int i = 1;
			String[] paths = imagePath.split("/");
			String path = paths[paths.length - 1];
			for (org.opencv.core.Rect rect : faceDetections.toArray()) {
				Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
						new Scalar(255, 255, 0));
				Mat roi_img = new Mat(image, new Range(rect.y, rect.y + rect.height),
						new Range(rect.x, rect.x + rect.width));
				// Imgcodecs.imwrite("C:\\Users\\win10\\Desktop\\test\\"+i+".png",
				// roi_img);
				Highgui.imwrite(toPath + i + path, roi_img);
				i++;
			}

			// 返回标记后的图片
			// String filename =
			// "C:\\Users\\win10\\Desktop\\test\\faceDetection.png";
			String filename = toPath + "Detec" + path;
			Highgui.imwrite(filename, image);
			logger.info("----------------------print test1------------------------");

			// map.put("picname", picName);
			map.put("detecPic", filename);// 返回图片路径给页面
		}
		//////// 获取图片信息//////////////////////////////////
		// imagePath ="C:\\Users\\xunli\\Pictures\\test.jpg";//测试时写死
		String brand = "";
		String model = "";
		String date_photograph = "";
		String shootTool = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

		try {
			File picFile = new File(imagePath);
			Metadata metadata;
			logger.info("----------------------print test2------------------------");
			metadata = JpegMetadataReader.readMetadata(picFile);
			logger.info("----------------------print test3------------------------");
			Directory exif = metadata.getDirectory(ExifDirectory.class);
			Iterator tags = exif.getTagIterator();

			while (tags.hasNext()) {
				Tag tag = (Tag) tags.next();
				if (tag.getTagName().equals("Make")) {
					brand = tag.getDescription();
				}
				if (tag.getTagName().equals("Model")) {
					model = tag.getDescription();
				}
				if (tag.getTagName().equals("Date/Time")) {
					Date d = null;
					try {
						d = dateFormat.parse(tag.getDescription());
					} catch (Exception e) {
						e.printStackTrace();
					}
					date_photograph = dateFormat2.format(d);
				}
			}
			shootTool = brand + ":" + model;
		} catch (Exception e) {
			logger.info("--------------PNG CAN'T BE ANALYSED!-----------------");
		}
		///////// 获取图片信息——end///////////////////////////
		map.put("shootTool", shootTool);
		map.put("date_photograph", date_photograph);

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
	 * 获取本地图片至jsp
	 */
	@RequestMapping("/admin/getPic.php")
	public void getPic(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws IOException {
		String url = request.getParameter("url");
		FileInputStream fis = null;
		// File file = new File("C:\\Users\\win10\\Desktop\\test\\Detechy.jpg");
		File file = new File(url);
		// File file = new File("home/images/test.png"); 服务器目录和本地图片的区别是图片路径
		fis = new FileInputStream(file);

		// 设置返回的文件类型
		String imgType = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
		switch (imgType) {
		case "jpg":
			response.setContentType("image/jpg");
		case "png":
			response.setContentType("image/png");
		}

		response.setHeader("Access-Control-Allow-Origin", "*");// 设置该图片允许跨域访问
		IOUtils.copy(fis, response.getOutputStream());
	}

	/**
	 * 预检测所有图片，返回有人脸的图片到人脸识别页面
	 * 
	 **/
	@RequestMapping("/admin/preGetFaceUrl.php")
	public void preGetFaceUrl(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws Exception {
		response.setContentType("textml; charset=UTF-8");
		String pageIndexStr = (String) request.getParameter("pageIndex");
		String dirName = (String) request.getParameter("dirName");
		String caseidStr = (String) request.getParameter("caseidStr");
		int pageIndex = Integer.parseInt(pageIndexStr);
		String[] caseids = { "" };
		if (!"".equals(caseidStr) && caseidStr != null) {
			caseids = caseidStr.split(" ");
		}
		// 分页
		int pageSize = 10;// 每页个数
		// 按条件查询
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		// caseid集合
		mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		// 组合 模糊查询 should
		if (dirName != null && !"".equals(dirName)) {
			//mustQuery.should(QueryBuilders.termsQuery("picname", "*"+dirName+"*"));//matchQuery
			WildcardQueryBuilder picname = QueryBuilders.wildcardQuery("picname", "*"+dirName+"*"); 
			mustQuery.must(picname);

		}


		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery).setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize);// 本次返回的文档数量

		/*SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery).setFrom(0)// 分页起始位置（跳过开始的n个）
				.setSize(1);*/// 本次返回的文档数量
		// 排序 执行
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();// 执行搜索

		String string = searchResponse.toString();

		List<PicInfo> piclist=new ArrayList<PicInfo>();

		////////////////////////////////////////////////////////////
		//获取所有图片的数量
		long totalnum=searchResponse.getHits().getTotalHits();
		long resnum=totalnum;
		while(resnum>0){
			if(resnum>5000)
				searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery).setFrom((int)(totalnum-resnum))// 分页起始位置（跳过开始的n个）
				.setSize(5000);// 本次返回的文档数量
			else
				searchRequestBuilder = EsClient.getClient().prepareSearch("pic").setTypes("picindex")
				.setQuery(mustQuery).setFrom((int)(totalnum-resnum))// 分页起始位置（跳过开始的n个）
				.setSize(5000);// 本次返回的文档数量
			SearchResponse searchResponse2 = searchRequestBuilder.execute().actionGet();// 执行搜索
			SearchHits hits = searchResponse2.getHits();
			SearchHit[] hitss = hits.hits();


			for (SearchHit searchHit : hitss) {
				Map<String, Object> source = searchHit.getSource();
				PicInfo pic =new PicInfo();
				String favoriteLabel=(String)source.get("favoriteLabel");
				pic.setFavoriteLabel(favoriteLabel);
				String picDirpath=(String)source.get("picDirpath");
				pic.setPicDirpath(picDirpath);
				String favoriteTime=(String)source.get("favoriteTime");
				pic.setFavoriteTime(favoriteTime);
				String starFlag=(String)source.get("starFlag");
				pic.setStarFlag(starFlag);
				String picdesc=(String)source.get("picdesc");
				pic.setPicdesc(picdesc);
				String caseID=(String)source.get("caseID");
				pic.setCaseID(caseID);
				String picname=(String)source.get("picname");
				pic.setPicname(picname);
				String caseName=(String)source.get("caseName");
				pic.setCaseName(caseName);

				//String imagePath="D:/beidouyun_Img"+picDirpath;//windows测试路径
				String imagePath = picDirpath;
				//System.out.println("图片"+imagePath);

				if (StringUtils.isNotEmpty(imagePath)) {
					// 导入opencv库
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

					// 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
					// String xmlFilePath =
					// "C:\\MyApps\\opencv\\sources\\data\\lbpcascades\\lbpcascade_frontalface.xml";//haarcascade_frontalface_alt.xml
					String xmlFilePath = MainFrameAction.class.getResource("/lbpcascade_frontalface.xml").getPath();//linux下不加substring(1)

					CascadeClassifier faceDetector = new CascadeClassifier(xmlFilePath);

					// 将图片转换为api所能接受的格式
					Mat image = Highgui.imread(imagePath);
					// 调用分类器上的方法传递图像和matofrect对象，进行面部检测
					MatOfRect faceDetections = new MatOfRect();
					faceDetector.detectMultiScale(image, faceDetections);

					//System.out.println("人脸数量："+faceDetections.toArray().length);
					if(!faceDetections.empty()){
						System.out.println("有人脸："+imagePath);
						piclist.add(pic);
					}

				}

			}
			resnum-=5000;
		}//while——end
		/*
		 *只返回单页的数目 
		 */
		List<PicInfo> piclist2=new ArrayList<PicInfo>();
		for(int i=(pageIndex-1)*pageSize;i<pageIndex*pageSize&&i<piclist.size();i++)
			piclist2.add(piclist.get(i));
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("piclist", piclist2);
		hashMap.put("total", piclist.size());
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			//writer.write(string);
			writer.write(JsonUtil.map2json(hashMap));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}


	// 执行工作台的数据增加统计
	@RequestMapping(value = "/admin/SearchData.php")
	public void SearchData(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		List<String> arrayList = new ArrayList<String>();
		List<Evidence> Edata = new ArrayList<Evidence>();
		Evidence ev = new Evidence();
		int mon = 1;
		// int Size=sqlDao.getcountfromMysqlLike(ev);yyyy-MM-dd HH:mm:ss
		SimpleDateFormat sdf=new SimpleDateFormat("MM");
		String presentMon=sdf.format(new Date()).toString();
		int premon=Integer.parseInt(presentMon);
		for (int i = 1; i <= premon; i++) {
			String star = "";
			String end = "";
			if (i > 9) {
				star = i + "";
			} else {
				star = "0" + i;
			}
			if (i > 8) {
				end = i + 1 + "";
			} else {
				end = "0" + i + 1;
			}
			List<Evidence> logs = sqlDao.getListfromMysqlLikeev(ev, "2017-" + star + "-01 00:00:00",
					"2017-" + end + "-01 00:00:00");
			long size = 0;
			long size2 = 0;
			long size3 = 0;
			String sizestr = "";
			/*
			 * if(size != 0.00){ java.text.DecimalFormat df = new
			 * java.text.DecimalFormat("########.00"); df.format(size); }
			 */
			DecimalFormat df = new DecimalFormat("###############0.00000 ");

			for (Evidence evidence : logs) {
				String evSize = evidence.getEvSize();
				String split = evSize.split("\\.")[0];
				if (!"".equals(split)) {
					long parseLong = Long.parseLong(split);
					size += parseLong;
				}

				//size += Double.parseDouble(evSize);

				/**/


			}

			String gb = getPrintSize(size);
			/*size = size * 100 / 1024;
					size2 = size / 100;
					size3 = size2 % 100;*/



			/*size = size * 100 / 1024;
			size2 = size / 100;
			size3 = size2 % 100;*/

			/*String temp = df.format(gb);
			sizestr = "" + temp;*/
			arrayList.add(gb);

		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(arrayList));
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
	 * kb转换GB
	 * @param size
	 * @return
	 */
	public static String getPrintSize(long size) {
		BigDecimal s;
		BigDecimal bd1 = new BigDecimal(size);
		BigDecimal bd2 = new BigDecimal(1024);

		bd1 = bd1.divide(bd2,10,BigDecimal.ROUND_HALF_UP);
		s = bd1.divide(bd2,10,BigDecimal.ROUND_HALF_UP);

		return s.toString();
	}

	// 执行工作台的黑客数据增加条统计
	@RequestMapping(value = "/admin/HackerDB.php")
	public void HackerDB(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		List<String> arrayList = new ArrayList<String>();
		List<HackerDBCount> Edata = new ArrayList<HackerDBCount>();
		HackerDBCount hack = new HackerDBCount();
		for (int i = 1; i <= 12; i++) {
			String star = "";
			String end = "";
			if (i > 9) {
				star = i + "";
			} else {
				star = "0" + i;
			}
			if (i > 8) {
				end = i + 1 + "";
			} else {
				end = "0" + i + 1;
			}
			List<HackerDBCount> logs = sqlDao.getListfromMysqlLikeHackDB(hack, "2017-" + star + "-01 00:00:00",
					"2017-" + end + "-01 23:59:59");
			long sum = 0;
			long sum1 = 0;
			for (HackerDBCount hacker : logs) {
				String count = hacker.getHackCount();

				if (!"".equals(count)) {
					long parseLong = Long.parseLong(count);
					sum += parseLong;
					sum1 = sum / 10000;
				}

			}
			arrayList.add("" + sum1);
		}

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(arrayList));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	@RequestMapping(value = "/admin/Email_Search.php")
	public void Email_Search(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String startDate = request.getParameter("startDate_d2");// 开始时间
		String endDate = request.getParameter("endDate_d2");

		int pageIndex = 1;// 页数
		int pageSize = 100;// 每页个数
		int count = 1;// 临时
		List<Email_WorkDTO> ticketLinksList = new ArrayList<Email_WorkDTO>();

		// 精确搜索
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery());
		// 添加第1条must的条件 此处为匹配所有文档
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("startTime").from(startDate + " 00:00:00")
					.to(endDate + "23:59:59");
			mustQuery.must(rangequerybuilder);
		}

		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red' >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

		// 执行搜索
		SearchResponse searchResponse = searchRequestBuilder.setFrom(0)// 分页起始位置（跳过开始的n个）
				.setSize(10)// 本次返回的文档数量
				.execute().actionGet();
		String string2 = searchResponse.toString();

		ArrayList<Email_WorkLinkDTO> Email_WorkLinkDTO = new ArrayList<Email_WorkLinkDTO>();
		ArrayList<SearchHit[]> arrayList = new ArrayList<SearchHit[]>();
		SearchHit[] hits1 = searchResponse.getHits().getHits();
		arrayList.add(hits1);
		long totalHits = searchResponse.getHits().getTotalHits()/10000;
		for(int i=1;i<=totalHits;i++){
			searchResponse = searchRequestBuilder.setQuery(mustQuery).setFrom(i*10000)// 分页起始位置（跳过开始的n个）
					.setSize(10).execute().actionGet();
			SearchHit[] hits  = searchResponse.getHits().getHits();
			arrayList.add(hits);
		}

		String linkman = null;

		for (SearchHit[] searchHit2 : arrayList) {
			for (SearchHit searchHit : searchHit2) {
				Map<String, Object> source = searchHit.getSource();
				linkman = (String) source.get("name") + count;
				count++;

				String fromWho = (String) source.get("formWho");
				String toWho = (String) source.get("toWho");

				ArrayList<Email_WorkLinkDTO> Email_WorkLinkDTO2 = Email_WorkLinkDTO;
				if (Email_WorkLinkDTO2.size() != 0) {
					int flag = 0;
					for (int i = 0; i < Email_WorkLinkDTO2.size(); i++) {
						String formWho1 = Email_WorkLinkDTO2.get(i).getFormWho();
						if (fromWho.equals(formWho1)) {
							Email_WorkLinkDTO.get(i).setEmailNum(Email_WorkLinkDTO.get(i).getEmailNum() + 1);
							flag = 1;
						}
					}
					if (flag == 0) {
						Email_WorkLinkDTO email_workLink = new Email_WorkLinkDTO();
						email_workLink.setFormWho("formWho");
						email_workLink.setToWho("toWho");
						Email_WorkLinkDTO.add(email_workLink);
					}
				} else {
					Email_WorkLinkDTO email_workLink = new Email_WorkLinkDTO();
					email_workLink.setFormWho("formWho");
					email_workLink.setToWho("toWho");
					Email_WorkLinkDTO.add(email_workLink);
				}
			}
		}
		Map<Object, Object> map = new HashMap<>();
		map.put("Email_WorkLinkDTO", Email_WorkLinkDTO);
		// map.put("aliketickets", aliketickets);

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

	// 执行工作台的数据类型
	@RequestMapping(value = "/admin/DataType.php")
	public void DataType(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		response.setContentType("textml; charset=UTF-8");
		List<String> arrayList = new ArrayList<String>();
		List<String> arrayList1 = new ArrayList<String>();
		String dataType = "";

		Evidence ev = new Evidence();

		long size0=0;
		long size1=0;
		long size2=0;
		long size5=0;
		double size6=22;
		long size7;
		double size8;
		int evtype = 1;
		if (evtype == 1) {
			dataType = "电子邮箱";
			ev.setEvType(evtype);
			//int logs = sqlDao.getcountfromMysql(ev);

			List<Evidence> log = sqlDao.getListfromMysql(ev);
			for (Evidence evidence : log) {
				String evSize = evidence.getEvSize();
				String split = evSize.split("\\.")[0];
				if (!"".equals(split)) {
					long parseLong = Long.parseLong(split);
					size0 += parseLong;
				}
			}
			long sizes = size0 * 1024;
			String gb = convertFileSize(sizes);
			arrayList.add(dataType + " " + gb);
			evtype++;
		}
		if (evtype == 2) {
			dataType = "综合文档";
			ev.setEvType(evtype);
			//int logs = sqlDao.getcountfromMysql(ev);
			List<Evidence> log = sqlDao.getListfromMysql(ev);

			for (Evidence evidence : log) {
				String evSize = evidence.getEvSize();
				String split = evSize.split("\\.")[0];
				if (!"".equals(split)) {
					long parseLong = Long.parseLong(split);
					size1 += parseLong;
				}
			}
			long sizes = size1 * 1024;
			String gb = convertFileSize(sizes);
			arrayList.add(dataType + " " + gb);
			evtype++;

		}
		if (evtype == 3) {
			dataType = "电子话单";
			ev.setEvType(evtype);
			//int logs = sqlDao.getcountfromMysql(ev);
			List<Evidence> log = sqlDao.getListfromMysql(ev);

			for (Evidence evidence : log) {
				String evSize = evidence.getEvSize();
				String split = evSize.split("\\.")[0];
				if (!"".equals(split)) {
					long parseLong = Long.parseLong(split);
					size2 += parseLong;
				}
			}
			long sizes = size2 * 1024;
			String gb = convertFileSize(sizes);
			arrayList.add(dataType + " " + gb);
			evtype++;

		}
		if(evtype == 4){
			evtype=5;
		}
		/*if (evtype == 4) {
				dataType = "手机取证";
				ev.setEvType(evtype);
				//int logs = sqlDao.getcountfromMysql(ev);
				List<Evidence> log = sqlDao.getListfromMysql(ev);
				for (Evidence evidence : log) {
					String evSize = evidence.getEvSize();
					String split = evSize.split("\\.")[0];
					if (!"".equals(split)) {
						long parseLong = Long.parseLong(split);
						size += parseLong;
					}
				}
				//long sizes = size * 1024;
				String gb = convertFileSize(size);
				arrayList.add(dataType + " " + gb);
				evtype++;

			}*/
		if (evtype == 6) {
			dataType = "图片资料";
			ev.setEvType(evtype);
			//int logs = sqlDao.getcountfromMysql(ev);
			List<Evidence> log = sqlDao.getListfromMysql(ev);

			for (Evidence evidence : log) {
				String evSize = evidence.getEvSize();
				String split = evSize.split("\\.")[0];
				if (!"".equals(split)) {
					long parseLong = Long.parseLong(split);
					size5 += parseLong;
				}
			}
			long sizes = size5 * 1024;  
			String gb = convertFileSize(sizes);
			arrayList.add(dataType + " " + gb);
			evtype++;

		}
		size7=(size0+size1+size2+size5)* 1024;
		String TB = convertFileSize(size7);
		double s = (Double.parseDouble(TB.substring(0,TB.length()-3)));
		size8=size6-s;
		arrayList1.add(""+size8);
		/*if (evtype == 7) {
				dataType = "剩余空间";
				ev.setEvType(evtype);
				int logs = sqlDao.getcountfromMysql(ev);
				arrayList.add(dataType + " " + logs);
				evtype++;
				log6=logs;
			}*/
		map.put("arrayList", arrayList);
		map.put("arrayList1", arrayList1);
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
	/***
	 * KB装换成TB
	 * @param size
	 * @return
	 */
	public static String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;
		long tb = gb * 1024;

		if (size >= tb) {
			return String.format("%.4f TB", (float) size / tb);
		}/* else if (size >= gb) {
				float f = (float) size / gb;
				return String.format(f > 100 ? "%.0f GB" : "%.2f GB", f);
			} else if (size >= mb) {
				float f = (float) size / mb;
				return String.format(f > 100 ? "%.0f MB" : "%.2f MB", f);
			} else if (size >= kb) {
				float f = (float) size / kb;
				return String.format(f > 100 ? "%.0f KB" : "%.2f KB", f);
			}*/ else {
				return String.format("%.4f TB", (float) size / tb);
			}
	}
	/**
	 * 案件统计页面
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/admin/caseStatistics.php")
	public String caseStatistics(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map<String, Object> map) throws IOException, InterruptedException {

		return "caseStatistics";
	}

	/**
	 * 案件统计，按条件查询
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
	@RequestMapping(value = "/searchCase_caseStatistics.php")
	public void caseSearch_caseStatistics(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		response.setContentType("textml; charset=UTF-8");

		String pageno = request.getParameter("pageno");
		String caseName = request.getParameter("caseName");
		String caseType = request.getParameter("caseType");
		String okTime = request.getParameter("okTime");

		String createdTime = request.getParameter("createdTime");
		String endTime = request.getParameter("endTime");

		int pageIndex = 1;
		int pageSize = 5;
		int num = 0;
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		Caseinfo cas = new Caseinfo();
		if (!StringUtils.isEmpty(caseName)) {
			cas.setCaseName(caseName);
		}

		if (!StringUtils.isEmpty(caseType)) {
			cas.setCaseType(caseType);
		}
		List<Caseinfo> logs;
		int total;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		// 过去七天
		c.setTime(new Date());
		c.add(Calendar.DATE, -7);
		Date d = c.getTime();
		String day = format.format(d);
		if ("day".equals(okTime)) {
			createdTime = format.format(d);
			endTime = format.format(new Date());
		}

		// 过去一月
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Date m = c.getTime();
		String mon = format.format(m);
		if ("mon".equals(okTime)) {
			createdTime = format.format(m);
			endTime = format.format(new Date());
		}
		// 过去三个月
		c.setTime(new Date());
		c.add(Calendar.MONTH, -3);
		Date m3 = c.getTime();
		String mon3 = format.format(m3);
		if ("mon3".equals(okTime)) {
			createdTime = format.format(m3);
			endTime = format.format(new Date());
		}

		// 过去半年
		c.setTime(new Date());
		c.add(Calendar.MONTH, -6);
		Date m6 = c.getTime();
		String mon6 = format.format(m6);
		if ("mon6".equals(okTime)) {
			createdTime = format.format(m6);
			endTime = format.format(new Date());
		}

		// 过去一年
		c.setTime(new Date());
		c.add(Calendar.YEAR, -1);
		Date y = c.getTime();
		String year = format.format(y);
		if ("year".equals(okTime)) {
			createdTime = format.format(y);
			endTime = format.format(new Date());
		}
		if (!StringUtils.isEmpty(createdTime) && !StringUtils.isEmpty(endTime)) {
			logs = sqlDao.getListfromMysqlLikecase(cas, createdTime, endTime, (pageIndex - 1) * pageSize, pageSize);
			total = sqlDao.getListfromMysqlLikecaseCount(cas, createdTime, endTime);
		} else {
			logs = sqlDao.getOrderListfromMysqlLike(cas, (pageIndex - 1) * pageSize, pageSize, "id");
			total = sqlDao.getcountfromMysqlLike(cas);
		}
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
		Map<Object, Object> hashmap = new HashMap<>();
		hashmap.put("logs", logs);
		hashmap.put("totalNum", total);
		hashmap.put("totalPages", num);
		hashmap.put("nowPage", pageIndex);
		hashmap.put("pageList", pageList);
		hashmap.put("caseName", caseName);
		hashmap.put("caseType", caseType);
		hashmap.put("createdTime", createdTime);
		hashmap.put("endTime", endTime);
		CaseType type = new CaseType();
		List<CaseType> caseTypeList = sqlDao.getListfromMysql(type);
		Section section = new Section();
		List<Section> Section = sqlDao.getListfromMysql(section);
		/*
		 * for(CaseType ct:caseTypeList) //System.out.println(ct.getTypeName());
		 */
		// 放到map中，前台遍历
		hashmap.put("caseTypeList", caseTypeList);
		hashmap.put("createdTime", createdTime);
		hashmap.put("Section", Section);

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(hashmap));
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
	 * 返回趋势图和案件类型图所需的数据
	 * 
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/getStatistics.php")
	public void getStatistics(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String createdTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		int num = 0;
		int total_type = 0;// 所有案件类型的总数y
		Caseinfo cas = new Caseinfo();
		List<Caseinfo> logs;
		int total;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();

		if (!StringUtils.isEmpty(createdTime) && !StringUtils.isEmpty(endTime)) {
			// logs = sqlDao.getListfromMysqlLikecase(cas,
			// createdTime,endTime,0, pageSize);
			/*
			 * logs = sqlDao.getListfromMysqlLikecase(cas,createdTime,endTime);
			 * //数据库中提供的按时间查询的结果不正确 total =
			 * sqlDao.getListfromMysqlLikecaseCount(cas,createdTime,endTime);
			 */
			// 查询搜索结果，对返回结果按时间保存
			List<Caseinfo> logs2 = sqlDao.getListfromMysql(cas);
			total = 0;
			logs = new ArrayList<Caseinfo>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy年MM月");
			Date startTime = null;
			Date endTime_date = null;
			try {
				startTime = dateFormat2.parse(createdTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				endTime_date = dateFormat2.parse(endTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (Caseinfo ci : logs2) {
				Date creatDate = null;

				try {
					creatDate = dateFormat.parse(ci.getCreatedTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (creatDate.getTime() >= startTime.getTime() && creatDate.getTime() <= endTime_date.getTime()) {
					logs.add(ci);
					total++;
				}
			}

		} else {
			// logs = sqlDao.getOrderListfromMysqlLike(cas, 0, pageSize, "id");
			logs = sqlDao.getListfromMysql(cas);
			total = sqlDao.getcountfromMysqlLike(cas);
		}
		//////// 获取案件类型与对应数量/////////////////////////
		HashMap<String, String> hashMap1 = new HashMap<String, String>();// 获取类型和存储数目
		List<CaseTypeSta> types = new ArrayList<CaseTypeSta>();
		for (Caseinfo ci : logs) {
			int mark = 0;
			for (Map.Entry<String, String> entry : hashMap1.entrySet()) {
				if (entry.getKey().equals(ci.getCaseType())) {
					int count = Integer.parseInt(entry.getValue());
					count++;
					String counts = "" + count;
					hashMap1.put(entry.getKey(), counts);
					mark = 1;
				}
			}
			if (mark == 0) {
				hashMap1.put(ci.getCaseType(), "1");
			}
		}
		for (Map.Entry<String, String> entry : hashMap1.entrySet()) {
			CaseTypeSta type = new CaseTypeSta();
			type.setCount(Integer.parseInt(entry.getValue()));
			type.setType(entry.getKey());
			types.add(type);

			total_type += type.getCount();
		}
		//////// 获取案件类型与对应数量——end/////////////////////////
		/////////////////// 获取每月案件数量///////////////////////////
		int jan = 0;
		int feb = 0;
		int mar = 0;
		int apr = 0;
		int may = 0;
		int jun = 0;
		int jul = 0;
		int aug = 0;
		int sep = 0;
		int oct = 0;
		int nov = 0;
		int dec = 0;
		for (Caseinfo ci : logs) {
			Date d = null;// 案件创建日期
			Date d1 = null;// 一月
			Date d2 = null;// 二月
			Date d3 = null;// 三月
			Date d4 = null;// 4月
			Date d5 = null;// 5月
			Date d6 = null;// 6月
			Date d7 = null;// 7月
			Date d8 = null;// 8月
			Date d9 = null;// 9月
			Date d10 = null;// 10月
			Date d11 = null;// 11月
			Date d12 = null;// 12月
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); // 字符串转换成date必须格式匹配
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
			try {
				d = dateFormat.parse(dateFormat.format(df1.parse(ci.getCreatedTime())));

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d1 = dateFormat.parse("01-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d2 = dateFormat.parse("02-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d3 = dateFormat.parse("03-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d4 = dateFormat.parse("04-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d5 = dateFormat.parse("05-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d6 = dateFormat.parse("06-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d7 = dateFormat.parse("07-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d8 = dateFormat.parse("08-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d9 = dateFormat.parse("09-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d10 = dateFormat.parse("10-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d11 = dateFormat.parse("11-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				d12 = dateFormat.parse("12-01");

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (d.getTime() >= d1.getTime() && d.getTime() < d2.getTime())
				jan++;
			if (d.getTime() >= d2.getTime() && d.getTime() < d3.getTime())
				feb++;
			if (d.getTime() >= d3.getTime() && d.getTime() < d4.getTime())
				mar++;
			if (d.getTime() >= d4.getTime() && d.getTime() < d5.getTime())
				apr++;
			if (d.getTime() >= d5.getTime() && d.getTime() < d6.getTime())
				may++;
			if (d.getTime() >= d6.getTime() && d.getTime() < d7.getTime())
				jun++;
			if (d.getTime() >= d7.getTime() && d.getTime() < d8.getTime())
				jul++;
			if (d.getTime() >= d8.getTime() && d.getTime() < d9.getTime())
				aug++;
			if (d.getTime() >= d9.getTime() && d.getTime() < d10.getTime())
				sep++;
			if (d.getTime() >= d10.getTime() && d.getTime() < d11.getTime())
				oct++;
			if (d.getTime() >= d11.getTime() && d.getTime() < d12.getTime())
				nov++;
			if (d.getTime() >= d12.getTime())
				dec++;
		}
		/////////////////// 获取每月案件数量——end///////////////////////////
		Map<Object, Object> hashmap = new HashMap<>();
		hashmap.put("logs", logs);
		hashmap.put("totalNum", total);
		hashmap.put("totalPages", num);
		hashmap.put("createdTime", createdTime);
		hashmap.put("endTime", endTime);
		hashmap.put("types", types);
		hashmap.put("jan", jan);
		hashmap.put("feb", feb);
		hashmap.put("mar", mar);
		hashmap.put("apr", apr);
		hashmap.put("may", may);
		hashmap.put("jun", jun);
		hashmap.put("jul", jul);
		hashmap.put("aug", aug);
		hashmap.put("sep", sep);
		hashmap.put("oct", oct);
		hashmap.put("nov", nov);
		hashmap.put("dec", dec);
		hashmap.put("total_type", total_type);

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(hashmap));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	// 图片选择案件
	@RequestMapping(value = "/admin/getimgcase.php")
	public void getimgcase(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String casenumorname = request.getParameter("casenumorname"); // 接收输入写入的值进行模糊查询
		Caseinfo cas = new Caseinfo();
		if (casenumorname != null && !"".equals(casenumorname)) {
			cas.setCaseNum(casenumorname);
		}
		List<Caseinfo> casList = sqlDao.getListfromMysqlLike(cas);
		int i = 0;
		for (Caseinfo caseinfo : casList) {
			String suspectNumsStr = "";
			String mainParty = caseinfo.getMainParty();
			String[] split = mainParty.split(",");
			for (String string : split) {
				SuspectInfo suspectInfo = new SuspectInfo();
				suspectInfo.setId(Integer.parseInt(string));
				List<SuspectInfo> listfromMysql = sqlDao.getListfromMysql(suspectInfo);
				SuspectInfo suspectInfo2 = listfromMysql.get(0);
				String suspectName = suspectInfo2.getSuspectName();
				String suspectPhone = suspectInfo2.getSuspectPhone();
				if ("".equals(suspectNumsStr)) {
					suspectNumsStr = suspectName + " " + suspectPhone;
				} else {
					suspectNumsStr += "/" + suspectName + " " + suspectPhone;
				}
			}
			casList.get(i).setMainParty(suspectNumsStr);
			i++;
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(casList));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	@RequestMapping(value = "/admin/typestatistics.php")
	public String typestatistics(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		return "typestatistics";
	}

	/*
	 * YC_TODO:
	 * 2017/9/15
	 *
	 * 查询所有标签
	 */
	@RequestMapping(value = "/findLabel.php")
	public void find_Label(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String pageno = request.getParameter("pageno");
		String labelName = request.getParameter("labelName");
		int pageIndex = 1;
		int pageSize = 5;
		int num = 0;

		List<LabelData> getLabelData = new ArrayList<LabelData>();

		Label labelBean = new Label();

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		} else {
			pageSize = 100000;
		}
		if (!StringUtils.isEmpty(labelName)) {
			labelBean.setLabel(labelName);
		}

		List<Label> labelList = sqlDao.getOrderListfromMysqlLike(labelBean, (pageIndex - 1) * pageSize, pageSize, "id");
		labelListExport = sqlDao.getListfromMysqlLike(labelBean);

		int total = sqlDao.getcountfromMysqlLike(labelBean);
		num = total / pageSize;
		if (total % pageSize != 0) {
			num++;
		}
		num = num == 0 ? 1 : num;

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

		for (Label getLabelName : labelList) {
			LabelData labelDataBean = new LabelData();
			labelBean.setLabel(getLabelName.getLabel());
			labelDataBean.setLabelName(getLabelName.getLabel());

			Caseinfo caseinfoBean = new Caseinfo();
			caseinfoBean.setLabel(getLabelName.getLabel());
			int getCaseCount = sqlDao.getcountfromMysqlLike(caseinfoBean);
			List<Caseinfo> getCaseLists = sqlDao.getListfromMysqlLike(caseinfoBean);
			int getDataNum = 0;
			for (Caseinfo getCaseinfo : getCaseLists) {
				Evidence evidence = new Evidence();
				evidence.setCaseID(getCaseinfo.getId());
				int getEveryDataNum = sqlDao.getcountfromMysql(evidence);
				getDataNum += getEveryDataNum;
			}
			labelDataBean.setLabelCaseNum(getCaseCount);

			SuspectInfo suspectInfoBean = new SuspectInfo();
			suspectInfoBean.setLabel(getLabelName.getLabel());
			int getSupectCount = sqlDao.getcountfromMysqlLike(suspectInfoBean);
			labelDataBean.setLabelSupectNum(getSupectCount);
			labelDataBean.setLabelDataNum(getDataNum);

			getLabelData.add(labelDataBean);
		}

		/*		for (int i = 0; i < getLabelData.size() - 1; i++) {
			for (int j = 0; j < getLabelData.size() - 1 - i; j++) {
				int caseNum = getLabelData.get(j).getLabelCaseNum();
				int dataNum = getLabelData.get(j).getLabelDataNum();
				int suspectNum = getLabelData.get(j).getLabelSupectNum();

				int caseNum1 = getLabelData.get(j + 1).getLabelCaseNum();
				int dataNum1 = getLabelData.get(j + 1).getLabelDataNum();
				int suspectNum1 = getLabelData.get(j + 1).getLabelSupectNum();
				if ((caseNum + dataNum + suspectNum) < (caseNum1 + dataNum1 + suspectNum1)) {
					LabelData strTemp = new LabelData();
					strTemp.setLabelName(getLabelData.get(j).getLabelName());
					strTemp.setLabelCaseNum(caseNum);
					strTemp.setLabelDataNum(dataNum);
					strTemp.setLabelSupectNum(suspectNum);

					getLabelData.get(j).setLabelName(getLabelData.get(j + 1).getLabelName());
					getLabelData.get(j).setLabelCaseNum(caseNum1);
					getLabelData.get(j).setLabelDataNum(dataNum1);
					getLabelData.get(j).setLabelSupectNum(suspectNum1);

					getLabelData.get(j + 1).setLabelName(strTemp.getLabelName());
					getLabelData.get(j + 1).setLabelCaseNum(strTemp.getLabelCaseNum());
					getLabelData.get(j + 1).setLabelDataNum(strTemp.getLabelDataNum());
					getLabelData.get(j + 1).setLabelSupectNum(strTemp.getLabelSupectNum());
				}
			}
		}*/

		/* YC_TODO: 2017/9/15  */
		map.put("logs", getLabelData);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
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

	// 标签统计 导出功能
	@RequestMapping("/ExportLabelStatistics.php")
	public void ExportLabelStatistics(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");
		try {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			String path = projectpath + filename + ".xls";
			File file = new File(path);
			createLabelData(path);
			fs = new POIFSFileSystem(new FileInputStream(path));
			wb = new HSSFWorkbook(fs);

			Label labelBean = new Label();
			/*
			 * List<Label> labelList1 = sqlDao.getListfromMysqlLike(labelBean);
			 */
			List<LabelData> getLabelData1 = new ArrayList<LabelData>();
			for (Label getLabelName : labelListExport) {
				LabelData labelDataBean = new LabelData();
				labelBean.setLabel(getLabelName.getLabel());
				labelDataBean.setLabelName(getLabelName.getLabel());

				Caseinfo caseinfoBean = new Caseinfo();
				caseinfoBean.setLabel(getLabelName.getLabel());
				int getCaseCount = sqlDao.getcountfromMysqlLike(caseinfoBean);
				List<Caseinfo> getCaseLists = sqlDao.getListfromMysqlLike(caseinfoBean);
				int getDataNum = 0;
				for (Caseinfo getCaseinfo : getCaseLists) {
					Evidence evidence = new Evidence();
					evidence.setCaseID(getCaseinfo.getId());
					int getEveryDataNum = sqlDao.getcountfromMysql(evidence);
					getDataNum += getEveryDataNum;
				}

				labelDataBean.setLabelCaseNum(getCaseCount);
				SuspectInfo suspectInfoBean = new SuspectInfo();
				suspectInfoBean.setLabel(getLabelName.getLabel());
				int getSupectCount = sqlDao.getcountfromMysqlLike(suspectInfoBean);
				labelDataBean.setLabelSupectNum(getSupectCount);
				labelDataBean.setLabelDataNum(getDataNum);
				getLabelData1.add(labelDataBean);
			}

			for (int i = 0; i < getLabelData1.size(); i++) {
				writeLabelData(getLabelData1.get(i), wb, path);
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
			System.out.println(e);
		}
	}

	public void createLabelData(String path) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);

		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);

		// 给Excel的单元格设置样式和赋值
		cell.setCellValue("标签名称");
		cell = row.createCell(1);
		cell.setCellValue("案件次数");
		cell = row.createCell(2);
		cell.setCellValue("嫌疑人次数");
		cell = row.createCell(3);
		cell.setCellValue("数据次数");
		cell = row.createCell(4);

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	public static void writeLabelData(LabelData bean, HSSFWorkbook wb, String path) throws Exception {
		HSSFSheet sheet = wb.getSheetAt(0);
		int begin = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();
		int m = 0;
		for (int n = begin; n <= end; n++) {
			try {
				m++;
			} catch (Exception e) {
				break;
			}
		}
		HSSFRow row1 = sheet.createRow(m);

		// HSSFHyperlink link1 = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue(bean.getLabelName());
		cell = row1.createCell(1);
		cell.setCellValue(bean.getLabelCaseNum() + "次");
		cell = row1.createCell(2);
		cell.setCellValue(bean.getLabelSupectNum() + "次");
		cell = row1.createCell(3);
		cell.setCellValue(bean.getLabelDataNum() + "次");
		cell = row1.createCell(4);

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	// 用户行为统计
	@RequestMapping(value = "/find_action.php")
	public void find_action(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String pageno = request.getParameter("pageno");
		String userName = request.getParameter("userName");
		String userAction = request.getParameter("userAction");

		int keepNum = 0;
		int lookNum = 0;
		int addNum = 0;
		int editNum =0;
		int deleteNum =0; 

		int pageIndex = 1;
		int pageSize = 5;
		int num = 0;

		UserAction userActionBean = new UserAction();
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		if (!StringUtils.isEmpty(userName)) {
			userActionBean.setName(userName);
		}
		if (!StringUtils.isEmpty(userAction)) {
			userActionBean.setAction(userAction);
		}
		List<UserAction> userActionList = sqlDao.getOrderListfromMysqlLike(userActionBean, (pageIndex - 1) * pageSize,
				pageSize, "id");
		List<UserAction> userActionList1 = sqlDao.getListfromMysqlLike(userActionBean);
		int total = sqlDao.getcountfromMysqlLike(userActionBean);
		num = total / pageSize;
		if (total % pageSize != 0) {
			num++;
		}
		num = num == 0 ? 1 : num;

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

		//直接从数据库读取相应操作的条数，避免用for循环造成echart加载卡顿。
		UserAction userAction_num=new UserAction();
		userAction_num.setAction("新增");
		addNum=sqlDao.getcountfromMysql(userAction_num);
		userAction_num.setAction("编辑");
		editNum=sqlDao.getcountfromMysql(userAction_num);
		userAction_num.setAction("查看");
		lookNum=sqlDao.getcountfromMysql(userAction_num);
		userAction_num.setAction("删除");
		deleteNum=sqlDao.getcountfromMysql(userAction_num);
		userAction_num.setAction("收藏");
		keepNum=sqlDao.getcountfromMysql(userAction_num);

		UserActionNum userActionNumBean = new UserActionNum();
		userActionNumBean.setAddNum(addNum);
		userActionNumBean.setDeleteNum(deleteNum);
		userActionNumBean.setEditNum(editNum);
		userActionNumBean.setKeepNum(keepNum);
		userActionNumBean.setLookNum(lookNum);

		map.put("logs", userActionList);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("userActionNumBean", userActionNumBean);
		getUserActionInfo = userActionList1;
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
	// 用户行为统计 导出功能
	@RequestMapping("/ExportUserActionStatistics.php")
	public void ExportUserActionStatistics(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");
		try {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			String path = projectpath + filename + ".xls";
			File file = new File(path);
			createUserActionData(path);
			fs = new POIFSFileSystem(new FileInputStream(path));
			wb = new HSSFWorkbook(fs);
			for (int i = 0; i < getUserActionInfo.size(); i++) {
				writeUserActionData(getUserActionInfo.get(i), wb, path);
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
			System.out.println(e);
		}
	}

	public void createUserActionData(String path) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);

		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);

		// 给Excel的单元格设置样式和赋值
		cell.setCellValue("标签名称");
		cell = row.createCell(1);
		cell.setCellValue("案件次数");
		cell = row.createCell(2);
		cell.setCellValue("嫌疑人次数");
		cell = row.createCell(3);
		cell.setCellValue("数据次数");
		cell = row.createCell(4);

		FileOutputStream os = new FileOutputStream(path);
		wb.write(os);
		os.close();
	}

	public static void writeUserActionData(UserAction bean, HSSFWorkbook wb, String path) throws Exception {
		HSSFSheet sheet = wb.getSheetAt(0);
		int begin = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();
		int m = 0;
		for (int n = begin; n <= end; n++) {
			try {
				m++;
			} catch (Exception e) {
				break;
			}
		}
		HSSFRow row1 = sheet.createRow(m);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue(bean.getName());
		cell = row1.createCell(1);
		cell.setCellValue(bean.getAction());
		cell = row1.createCell(2);
		cell.setCellValue(bean.getModule());
		cell = row1.createCell(3);
		cell.setCellValue(bean.getCreateDate());
		cell = row1.createCell(4);

		FileOutputStream os = new FileOutputStream(path);
		wb.write(os);
		os.close();

	}

	@RequestMapping("/getDocunmentAction.php")
	public void getDocunmentAction(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {
		actionLog((String) session.getAttribute("userName"), "查看", "文档挖掘");
	}

	@RequestMapping("/getImgAction.php")
	public void getImgAction(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {
		actionLog((String) session.getAttribute("userName"), "查看", "图片挖掘");
	}

	/**
	 * 图片挖掘从HDFS文件系统下载图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "admin/downloadPicture.php")
	public void downloadPicture(HttpServletRequest request, HttpServletResponse response)
			throws IOException, InterruptedException {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String downloadDate = sim.format(date);// 获取下载时间
		String tomPath = "/temp/";
		File filePath = new File(tomPath);
		if (!filePath.exists()) {
			filePath.mkdir();
		}
		String picturePath = request.getParameter("picturePath");// 获取图片路径
		logger.info("---------" + picturePath);
		// 进行解码
		picturePath = URLDecoder.decode(picturePath, "utf-8");
		logger.info("----解码后fileUrl-----" + picturePath);
		String browser = request.getHeader("user-agent");
		String[] temp1 = picturePath.split(",");// 多个路径以逗号截取存放到temp1中
		logger.info("---------------" + temp1);
		if (temp1.length == 1) {// 单个文件下载
			String[] temp11 = picturePath.split("/");
			String temp2 = picturePath.split("/")[temp11.length - 1];// 获取下载文件名
			File temFile = null;
			try {
				temFile = new File(picturePath);// 服务器文件路径
				if (!temFile.exists()) {
					response.getWriter().write("ERROR:File Not Found");
				}
				String fileName = temp2;
				Pattern p = Pattern.compile(".*MSIE.*?;.*");
				Matcher m = p.matcher(browser);
				if (m.matches()) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", ""));
				} else {
					response.setHeader("Content-Disposition", "attachment; filename="
							+ new String(fileName.getBytes("UTF-8"), "ISO8859-1").replace(" ", ""));
				}
				response.setContentType("application/x-download");
				OutputStream ot = response.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(temFile));
				BufferedOutputStream bos = new BufferedOutputStream(ot);
				byte[] buffer = new byte[4096];
				int length1 = 0;
				while ((length1 = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, length1);
				}
				bos.close();
				bis.close();
				ot.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (temFile != null) {
					temFile.delete();
				}
			}
		} else {
			String[] file1 = new String[temp1.length];
			// 多个文件下载
			for (int i = 0; i < temp1.length; i++) {
				file1[i] = temp1[i];
			}
			String zipFilePath = "/temp/" + downloadDate + "文件压缩包.zip";
			try {
				ZipFileUtil.zipFiles(file1, zipFilePath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			File temFile = null;
			try {
				temFile = new File(zipFilePath);
				if (!temFile.exists()) {
					response.getWriter().write("ERROR:File Not Found");
				}
				String fileName = downloadDate + "文件压缩包.zip";
				Pattern p = Pattern.compile(".*MSIE.*?;.*");
				Matcher m = p.matcher(browser);

				if (m.matches()) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replace("+", ""));
				} else {
					response.setHeader("Content-Disposition", "attachment; filename="
							+ new String(fileName.getBytes("UTF-8"), "ISO8859-1").replace(" ", ""));
				}
				response.setContentType("application/x-download");
				OutputStream ot = response.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(temFile));
				BufferedOutputStream bos = new BufferedOutputStream(ot);
				byte[] buffer = new byte[4096];
				int length1 = 0;
				while ((length1 = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, length1);
				}
				bos.close();
				bis.close();
				ot.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (temFile != null) {
					temFile.delete();
				}
			}

		}
	}
	// 操作日志用户行为统计
	@RequestMapping(value = "/useLog.php")
	public void useLog(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException, UnknownHostException {

		String pageno = request.getParameter("pageno");
		String userAction=request.getParameter("userAction");//操作类型
		String createdTime = request.getParameter("createdTime");//开始时间
		String endTime = request.getParameter("endTime");//结束时间
		String userName = request.getParameter("userName");//操作用户
		String department = request.getParameter("department");// 

		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;

		UserAction userActionBean = new UserAction();

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		} else {
			pageSize = 100000;
		}
		if (!StringUtils.isEmpty(userName)) {
			userActionBean.setName(userName);
		}
		if (!StringUtils.isEmpty(userAction)) {
			userActionBean.setAction(userAction);
		}
		if(!StringUtils.isEmpty(createdTime)){
			userActionBean.setCreateDate(createdTime);
		}

		if(!StringUtils.isEmpty(userName)){
			userActionBean.setName(userName);
		}
		if (!StringUtils.isEmpty(department)) {
			Department depart = new Department();
			depart.setDepartmentName(department);;
			List<Department> listfromMysqlse = sqlDao.getListfromMysql(depart);
			if (listfromMysqlse.size() > 0) {
				Department departmentse = listfromMysqlse.get(0);
				int seName = departmentse.getId();
				depart.setDepartmentName(seName+"");
			}
		}

		List<UserAction> userActionList = sqlDao.getOrderListfromMysqlLike(userActionBean, (pageIndex - 1) * pageSize,
				pageSize, "id");
		List<UserAction> userActionList1 = sqlDao.getListfromMysqlLike(userActionBean);
		int total = sqlDao.getcountfromMysqlLike(userActionBean);
		num = total / pageSize;
		if (total % pageSize != 0) {
			num++;
		}
		num = num == 0 ? 1 : num;

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
		int keepNum = 0;
		int lookNum = 0;
		int addNum = 0;
		int editNum = 0;
		int deleteNum = 0;
		for (UserAction getEveryNum : userActionList) {
			if (getEveryNum.getAction().equals("新增")) {
				++addNum;
			}
			if (getEveryNum.getAction().equals("编辑")) {
				++editNum;
			}
			if (getEveryNum.getAction().equals("查看")) {
				++lookNum;
			}
			if (getEveryNum.getAction().equals("删除")) {
				++deleteNum;
			}
			if (getEveryNum.getAction().equals("收藏")) {
				++keepNum;
			}
		}
		UserActionNum userActionNumBean = new UserActionNum();
		userActionNumBean.setAddNum(addNum);
		userActionNumBean.setDeleteNum(deleteNum);
		userActionNumBean.setEditNum(editNum);
		userActionNumBean.setKeepNum(keepNum);
		userActionNumBean.setLookNum(lookNum);

		map.put("logs", userActionList);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("userActionNumBean", userActionNumBean);
		getUserActionInfo = userActionList1;
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


	//搜索操作日志
	// 查询案件列表
	@RequestMapping(value = "/getUpdateLog.php")
	public void getUpdateLog(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {

		String pageno = request.getParameter("pageno");

		String module=request.getParameter("keyword");//操作类型
		String createdTime = request.getParameter("createdTime");//开始时间
		String endTime = request.getParameter("endTime");//结束时间
		String userName = request.getParameter("userName");//操作用户
		String department = request.getParameter("department");// 



		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;
		int total = 0;

		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		UserAction useraction=new UserAction(); 
		if (!StringUtils.isEmpty(module)) {
			useraction.setModule(module);
		}
		if (!StringUtils.isEmpty(createdTime)) {
			useraction.setCreateDate(createdTime);
		}
		if (!StringUtils.isEmpty(userName)) {
			useraction.setName(userName);

		}
		if (!StringUtils.isEmpty(department)) {
			Department depart = new Department();
			depart.setDepartmentName(department);;
			List<Department> listfromMysqlse = sqlDao.getListfromMysql(depart);
			if (listfromMysqlse.size() > 0) {
				Department departmentse = listfromMysqlse.get(0);
				int seName = departmentse.getId();
				depart.setDepartmentName(seName+"");
			}
		}

		List<Caseinfo> listfromMysql = new ArrayList<Caseinfo>();

		List<UserAction> logs =new ArrayList<UserAction>();
		if(createdTime!=null && !"".equals(createdTime) && endTime!=null && !"".equals(endTime)){
			logs = sqlDao.getListfromMysqlLikTimeecase(useraction, createdTime, endTime, (pageIndex - 1) * pageSize, pageSize);
			total = sqlDao.getListfromMysqlLikeTimecaseCount(useraction, createdTime, endTime);
		}else{
			total = sqlDao.getcountfromMysqlLike(useraction);
			logs = sqlDao.getOrderListfromMysqlLike(useraction, (pageIndex - 1) * pageSize, pageSize, "id");
		}


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

		map.put("logs", listfromMysql);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("keyword", module);
		map.put("createdTime", createdTime);
		map.put("userName", userName);
		map.put("department", department);
		map.put("endTime", endTime);

		// 放入map中
		Department depart = new Department();
		List<Department> departList = sqlDao.getListfromMysql(depart);

		map.put("departList", departList);
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
}