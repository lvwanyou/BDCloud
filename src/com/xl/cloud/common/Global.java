package com.xl.cloud.common;

import java.util.HashMap;
import java.util.Map;

public class Global {

	static java.util.ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("path");
	public static String DBhostIP = bundle.getString("DBhostIP");
	public static String DBName = bundle.getString("DBName");//数据库名字
	public static String DBhostUser = bundle.getString("DBhostUser");//数据库用户
	public static String DBhostPassWord = bundle.getString("DBhostPassWord");//数据库密码
	public static String ESIP1 = bundle.getString("ESIP1");//esIP节点
	public static String ESIP2 = bundle.getString("ESIP2");//esIP节点
	public static String ESIP3 = bundle.getString("ESIP3");//esIP节点
	public static String ESIP4 = bundle.getString("ESIP4");//esIP节点
	public static int pageSize = 20; //分页数据每页显示条数
	public static int overTime=3;
	public static String workspace=System.getProperty("user.dir");
	public static String path;
	public static String filePath = path+":/temp";
	public static String order=filePath+"/order.txt";
	public static String copyOrder=filePath+"/CopyOrder.txt";
	public static String onceonly=filePath+"/onceonly.txt";
	public static String imgPath=path+"pic/";
	public static String statue;
	public static String userName;
	public static String TYPE = bundle.getString("TYPE");
	public static String FingerIP = bundle.getString("FingerIP");   //指纹库IP
	
	public static String MainCollectionIP = bundle.getString("MainCollectionIP");
	public static String NameNodeHostName = bundle.getString("NameNodeHostName");
	public static String NameNodeIP = bundle.getString("NameNodeIP");
	public static String ZKHost = bundle.getString("ZKHost");
	public static String DockerHostIP = bundle.getString("DockerHostIP");
	public static String JAR = bundle.getString("JAR");
	public static String CmfIP = bundle.getString("CmfIP");
	public static String HOST = "http://"+bundle.getString("NameNodeIP")+":8983/solr/";
	public static String administrator="xunli";
	public static String administratorpass="pass";
	public static String tomcatPath = "";
	
	public static String regContainer = "([A-Z]{4}[0-9]{7})"; //集装箱号
    public static String regPhone = "((13|14|15|17|18)[0-9]{9}|(13|14|15|17|18)[0-9]{1}[- ][0-9]{4}[- ][0-9]{4}|手机[：: ](13|14|15|17|18)[0-9]{1}[- ][0-9]{4}[- ][0-9]{4})"; //手机号
    public static String regEmail = "([a-zA-Z0-9]+\\@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+)"; //邮箱
    public static String regCard = "([6]{1}[0-9]{15,18}|[6]{1}[0-9]{3}[- ][0-9]{4}[- ][0-9]{4}[- ][0-9]{4,7})"; //银行卡号
    public static String regContract = "(合同[：: ][0-9]{0,5}[0-9a-zA-Z-]{5,20})"; //合同编号
    public static String regStamp = "(发票[：: ][0-2]{1}[0-9]{10}|NO[：: ][0-2]{1}[0-9]{10})";//"([0-2]\\d{11}|\\d{6}[1-4]\\d{3})"; //发票代码
    public static String regPacking = "([0-9a-zA-Z-]{8,20})"; //装箱单
    
    public static String regBill = "([0-9a-zA-Z-]{8,20}|B\\/L)"; //提单号
    public static String regPrice = "((￥|\\$|CNY|RMB)[0-9\\.]{1,}|[0-9\\.]+元|[0-9\\.]+(￥|\\$|CNY|RMB))"; //价格
    public static String regModel = "(车架号[：: ][a-zA-Z0-9]{6}[a-zA-Z0-9]{11})"; //车架号
    public static String regLicense = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1})"; //车牌号
    public static String regLC = "(信用证L/C[：: ][0-9a-zA-z]{5,11})"; //信用证
    public static String regTT = "(电汇[：: ][0-9a-zA-z]{5,11}|NO[：: ][0-9a-zA-z]{5,11})"; //电汇
    public static String regSFZ = "((\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x))"; //身份证号
    public static String regGPS = "([0-9]{0,3}\\.[0-9]{1,6},[0-9]{0,3}\\.[0-9]{1,6})"; //GPS
    public static String regTel = "([0-9]{3,4}[- ][0-9]{8}|[0-9]{3,4}[- ][0-9]{4}[- ][0-9]{4}|[0-9]{3,4}[- ][0-9]{4}[\\ ]{1}[0-9]{4})"; //固定电话-
    public static String QQnumber = "(QQ[：: ][1-9]\\d{5,9}|qq[：: ][1-9]\\d{5,9})";  //QQ号码
    public static String weChat = "(微信[：: ][(13|14|15|17|18)][0-9]{9}|微信[：: ][a-zA-Z0-9]+\\@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+|微信[：: ](13|14|15|17|18)[0-9]{1}-[0-9]{4}-[0-9]{4})"; //微信号
    public static String twitter = "(SKYPE[：: ][a-zA-Z]{1}[a-zA-Z0-9]{5,19}|skype[：: ][a-zA-Z]{1}[a-zA-Z0-9]{5,19})";//推特号
    public static String passport = "((护照[：: ]1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]{7,8}))";  //护照编号
    public static String zfbpay = "(1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)[0-9]{8}|[a-zA-Z0-9]+\\@[a-zA-Z0-9]+\\.[a-zA-Z0-9])";
    public static String regUP = "(支付宝[：: ][(13|14|15|17|18)][0-9]{9}|支付宝[：: ](13|14|15|17|18)[0-9]{1}-[0-9]{4}-[0-9]{4})";//支付宝
    public static Map<String, Integer> PRIMAP = new HashMap<String, Integer>();
	
    //离线地图数据
	public static String ip_DBName = bundle.getString("ip_DBName");//'离线ip转地址'数据库名字
	//离线基站数据库名
	public static String baseID_DBName = bundle.getString("baseID_DBName");//'离线ip转地址'数据库名字
	
	static{
		System.out.println("数据库IP："+DBhostIP);
		PRIMAP.put("局长", 2);
		PRIMAP.put("副局长", 2);
		PRIMAP.put("分局局长", 2);
		PRIMAP.put("分局副局长", 2);
		PRIMAP.put("处长", 2);
		PRIMAP.put("副处长", 2);
		PRIMAP.put("调研员", 0);
		PRIMAP.put("副调研员", 0);
		PRIMAP.put("科长", 1);
		PRIMAP.put("主任科员兼副科长", 1);
		PRIMAP.put("副科长", 1);
		PRIMAP.put("主任科员", 0);
		PRIMAP.put("副主任科员", 0);
		PRIMAP.put("科员", 0);
	}
	
}
