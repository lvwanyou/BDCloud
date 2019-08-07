package com.xl.cloud.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xl.cloud.bean.PhoneTree;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.phoneBean.AliContacts;
import com.xl.cloud.phoneBean.AppList;
import com.xl.cloud.phoneBean.AppLocation;
import com.xl.cloud.phoneBean.AppUseRecord;
import com.xl.cloud.phoneBean.Bookmarks;
import com.xl.cloud.phoneBean.BrowserHistory;
import com.xl.cloud.phoneBean.CallLog;
import com.xl.cloud.phoneBean.Cookies;
import com.xl.cloud.phoneBean.DiMess;
import com.xl.cloud.phoneBean.DiPersonMess;
import com.xl.cloud.phoneBean.DiPersonSet;
import com.xl.cloud.phoneBean.LoactionSearch;
import com.xl.cloud.phoneBean.MailBox;
import com.xl.cloud.phoneBean.MediaFile;
import com.xl.cloud.phoneBean.Message;
import com.xl.cloud.phoneBean.OfficialAccMess;
import com.xl.cloud.phoneBean.Phone;
import com.xl.cloud.phoneBean.PhotoLocation;
import com.xl.cloud.phoneBean.Qmail;
import com.xl.cloud.phoneBean.QmailContacts;
import com.xl.cloud.phoneBean.QmailIdInfo;
import com.xl.cloud.phoneBean.RouteSearch;
import com.xl.cloud.phoneBean.SMSContact;
import com.xl.cloud.phoneBean.ShortMessage;
import com.xl.cloud.phoneBean.TMapPersonMess;
import com.xl.cloud.phoneBean.TbSearchHistory;
import com.xl.cloud.phoneBean.UsedNumber;
import com.xl.cloud.phoneBean.WeChatContacts;
import com.xl.cloud.phoneBean.WeChatGroupAndMember;
import com.xl.cloud.phoneBean.WeChatMediaFile;
import com.xl.cloud.phoneBean.WeChatMessage;
import com.xl.cloud.phoneBean.Wifi;
import com.xl.cloud.util.EsClient;
import com.xl.cloud.util.JsonUtil;

/**
 * 手机取证
 */
@Controller
public class PhoneDataAction {
	private SqlDao dao = new SqlDao();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final Logger logger = Logger.getLogger(BuildCollection.class);
	
	//跳转页面
	@RequestMapping(value = "/phone/showHtmlData.php")
	public String showPhoneData(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return "htmlData";
	}
	//左侧菜单 树
	@RequestMapping(value = "/phone/hemlDataTree.php")
	public void initUserTree(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		StringBuilder sbtmp2 =getTree("6plus(2017-9-15-1010)");
    	
    	String resResult = sbtmp2.toString();
		
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(resResult);
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
	 * 数据列表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/phone/getPhoneData.php")
	public void getPhoneData(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String pageIndexstr = request.getParameter("pageIndex");// 页数str
		int pageIndex =Integer.parseInt(pageIndexstr);// 页数int
		//String phoneSearch ="张";
		String type =request.getParameter("dsId");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		/**
        * 多个字段 field 之间 查询，如果字段查询之间是或的关系，用should; 
        */
		//phone_callLog
	  //  mustQuery.should(QueryBuilders.matchPhraseQuery("name", phoneSearch));
	   // mustQuery.should(QueryBuilders.matchPhraseQuery("telPhone", phoneSearch));
	  //  mustQuery.should(QueryBuilders.matchPhraseQuery("mail", phoneSearch));
	    		 SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("phonetest")
	    		.setTypes(type)
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red'>").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果
		SearchResponse searchResponse = searchRequestBuilder
				.setFrom((pageIndex-1)*10)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setSize(10)
				.execute().actionGet();// 执行搜索
		SearchHit[] hitss = searchResponse.getHits().getHits();
		long totalHits = searchResponse.getHits().getTotalHits();
		System.out.println("总条数："+totalHits);
		
		List<Object> phoneList = new ArrayList<Object>();
		for (SearchHit searchHit : hitss) {
			Map<String, Object> source = searchHit.getSource();
			
			//电子邮箱 QQ邮箱通讯录对象
			if(type.equals("qqemail_mailList")){
				QmailContacts qc = new QmailContacts();
				qc.setcName((String)source.get("cName"));
				qc.setMail((String)source.get("mail"));
				qc.setMd5((String)source.get("md5"));
				phoneList.add(qc);
			}
			//通话记录
			if(type.equals("phone_callLog")){
				CallLog cl = new CallLog();
				cl.setName((String)source.get("name"));
				cl.setTelPhone((String)source.get("telPhone"));
				cl.setCallStatus((String)source.get("callStatus"));
				cl.setCallTime((String)source.get("callTime"));
				cl.setDuration((String)source.get("duration"));
				cl.setMd5((String)source.get("md5"));
				phoneList.add(cl);
			}
			//通讯录
			if(type.equals("phone_mailList")){
				Phone phone = new Phone();
				phone.setName((String)source.get("name"));
				phone.setTelphone((String)source.get("telphone"));
				phone.setDeleteFlag((String)source.get("deleteFlag"));
				phone.setCreateTime((String)source.get("createTime"));
				phone.setLastUpdateTime((String)source.get("lastUpdateTime"));
				phone.setMd5((String)source.get("md5"));
				phoneList.add(phone);
			}
			//手机信息 短信联系人对象
			if(type.equals("phone_smsContact")){
				SMSContact sms = new SMSContact();
				sms.setName((String)source.get("name"));
				sms.setTelphone((String)source.get("telphone"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			//QQ邮箱 与邮件相关页面对象（垃圾箱 已删除 已发送 收件箱 草稿箱）
			if(type.equals("qqEmail")){
				Qmail qmail = new Qmail();
				qmail.setSender((String)source.get("sender"));
				qmail.setRecipient((String)source.get("recipient"));
				qmail.setCc((String)source.get("cc"));
				qmail.setTime((String)source.get("time"));
				qmail.setBcc((String)source.get("bcc"));
				qmail.setTheme((String)source.get("theme"));
				qmail.setContent((String)source.get("content"));
				qmail.setAnnex((String)source.get("annex"));
				qmail.setAnnexPath((String)source.get("annexPath"));
				qmail.setRead((String)source.get("read"));
				qmail.setMd5((String)source.get("md5"));
				phoneList.add(qmail);
			}
			//支付宝陌生人、好友页面对象
			if(type.equals("zfb_friends")){
				AliContacts sms = new AliContacts();
				sms.setMemoName((String)source.get("memoName"));
				sms.setNickName((String)source.get("nickName"));
				sms.setFullName((String)source.get("fullName"));
				sms.setAuthStatus((String)source.get("authStatus"));
				sms.setAcc((String)source.get("acc"));
				sms.setMemberLevel((String)source.get("memberLevel"));
				sms.setArea((String)source.get("area"));
				sms.setSignature((String)source.get("signature"));
				sms.setContacts((String)source.get("contacts"));
				sms.setTelephone((String)source.get("telephone"));
				sms.setAvatar((String)source.get("avatar"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			//程序列表页面对象
			if(type.equals("phone_AppList")){
				AppList sms = new AppList();
				sms.setName((String)source.get("name"));
				sms.setPackageName((String)source.get("packageName"));
				sms.setVersion((String)source.get("version"));
				sms.setType((String)source.get("type"));
				sms.setSize((String)source.get("size"));
				sms.setUpdateDate((String)source.get("updateDate"));
				sms.setInstallPath((String)source.get("installPath"));
				sms.setPermission((String)source.get("permission"));
				sms.setIcon((String)source.get("icon"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			
			//应用程序地理位置页面对象
			if(type.equals("phone_AppLocation")){
				AppLocation sms = new AppLocation();
				sms.setAppName((String)source.get("appName"));
				sms.setUserId((String)source.get("userId"));
				sms.setUserName((String)source.get("userName"));
				sms.setLatitude((String)source.get("latitude"));
				sms.setLongitude((String)source.get("longitude"));
				sms.setLocation((String)source.get("location"));
				sms.setAdd((String)source.get("add"));
				sms.setTime((String)source.get("time"));
				sms.setShareObjId((String)source.get("shareObjId"));
				sms.setSrcAccId((String)source.get("srcAccId"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			
			//系统日志 应用程序使用记录 页面对象
			if(type.equals("phone_AppUseRecord")){
				AppUseRecord sms = new AppUseRecord();
				sms.setPackageName((String)source.get("packageName"));
				sms.setLastUpdateTime((String)source.get("lastUpdateTime"));
				sms.setPackagePath((String)source.get("packagePath"));
				sms.setNewestModifiedTime((String)source.get("newestModifiedTime"));
				sms.setLastRunTime((String)source.get("lastRunTime"));
				sms.setFirstInstallTime((String)source.get("firstInstallTime"));
				sms.setStatus((String)source.get("status"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			//浏览器书签页面对象
			if(type.equals("browser_bookmarks")){
				Bookmarks sms = new Bookmarks();
				sms.setTitle((String)source.get("title"));
				sms.setUrl((String)source.get("url"));
				sms.setDate((String)source.get("date"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			
			//各个浏览器的实力记录页面对象
			if(type.equals("browser_history")){
				BrowserHistory sms = new BrowserHistory();
				sms.setTitle((String)source.get("title"));
				sms.setUrl((String)source.get("url"));
				sms.setDate((String)source.get("date"));
				sms.setTimes((String)source.get("times"));
				sms.setLastTime((String)source.get("lastTime"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			//浏览器Cookies页面对象
			if(type.equals("browser_cookies")){
				Cookies sms = new Cookies();
				sms.setName((String)source.get("name"));
				sms.setModifiedDate((String)source.get("modifiedDate"));
				sms.setLastTime((String)source.get("lastTime"));
				sms.setFileName((String)source.get("fileName"));
				sms.setUserName((String)source.get("userName"));
				sms.setClickTimes((String)source.get("clickTimes"));
				sms.setCookiesPath((String)source.get("cookiesPath"));
				sms.setPreviewPath((String)source.get("previewPath"));
				sms.setDomainName((String)source.get("domainName"));
				sms.setPath((String)source.get("path"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			

			//滴滴出行 消息页面对象
			if(type.equals("Didi_Mess")){
				DiMess sms = new DiMess();
				sms.setSenderId((String)source.get("senderId"));
				sms.setSenderName((String)source.get("senderName"));
				sms.setRecipientId((String)source.get("recipientId"));
				sms.setRecipientName((String)source.get("recipientName"));
				sms.setMess((String)source.get("mess"));
				sms.setTime((String)source.get("time"));
				sms.setMessType((String)source.get("messType"));
				sms.setAnnex((String)source.get("annex"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			//滴滴出行 个人信息页面对象
			if(type.equals("Didi_PersonMess")){
				DiPersonMess sms = new DiPersonMess();
				sms.setTelephone((String)source.get("telephone"));
				sms.setAdd((String)source.get("add"));
				sms.setCity((String)source.get("city"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}	
			//滴滴出行 个人设置对象
			if(type.equals("Didi_PersonSet")){
				DiPersonSet sms = new DiPersonSet();
				sms.setHomeAdd((String)source.get("homeAdd"));
				sms.setCompAdd((String)source.get("compAdd"));
				sms.setHomeLongitude((String)source.get("homeLongitude"));
				sms.setHomeLatitude((String)source.get("homeLatitude"));
				sms.setCompLongitude((String)source.get("compLongitude"));
				sms.setCompLatitude((String)source.get("compLatitude"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			//地图信息 腾讯地图 搜索位置页面对象
			if(type.equals("TMap_LoactionSearch")){
				LoactionSearch sms = new LoactionSearch();
				sms.setAddId((String)source.get("addId"));
				sms.setTime((String)source.get("time"));
				sms.setAddname((String)source.get("addname"));
				sms.setAdd((String)source.get("add"));
				sms.setLongitude((String)source.get("longitude"));
				sms.setLatitude((String)source.get("latitude"));
				sms.setRemarks((String)source.get("remarks"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			//电子邮箱-邮件列表
		/*	if(type.equals("phone_smsContact")){
				MailBox sms = new MailBox();
				sms.setEmailNick((String)source.get("emailNick"));
				sms.setEmailPassword((String)source.get("emailPassword"));
				sms.setEmail((String)source.get("email"));
				sms.setName((String)source.get("name"));
				sms.setAcceptEmail((String)source.get("acceptEmail"));
				sms.setSendEmail((String)source.get("sendEmail"));
				sms.setEmailTheme((String)source.get("emailTheme"));
				sms.setSendTime((String)source.get("sendTime"));
				sms.setIsRead((String)source.get("isRead"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}*/
			//手机内的媒体文件（图片 视频）页面对象
			if(type.equals("phone_MediaFile")){
				MediaFile sms = new MediaFile();
				sms.setFile((String)source.get("file"));
				sms.setFileName((String)source.get("fileName"));
				sms.setSize((String)source.get("size"));
				sms.setModifiedTime((String)source.get("modifiedTime"));
				sms.setCreateTime((String)source.get("createTime"));
				sms.setFilePath((String)source.get("filePath"));
				sms.setDeleteflag((String)source.get("delete"));
				sms.setLocalShoot((String)source.get("localShoot"));
				sms.setFileMd5((String)source.get("fileMd5"));
				sms.setSelfTimer((String)source.get("selfTimer"));
				sms.setMd5((String)source.get("md5"));
				phoneList.add(sms);
			}
			//短信 彩信 页面对象
			if(type.equals("phone_Message")){
				Message sms = new Message();
				sms.setOtherSide((String)source.get("otherSide"));
				sms.setSender((String)source.get("sender"));
				sms.setType((String)source.get("type"));
				sms.setTime((String)source.get("time"));
				sms.setContent((String)source.get("content"));
				sms.setImg((String)source.get("img"));
				phoneList.add(sms);
			}
			//微信公众号消息
			if(type.equals("WeChat_OfficialAccMess")){
				OfficialAccMess sms = new OfficialAccMess();
				sms.setOfficialAcc((String)source.get("officialAcc"));
				sms.setTitle((String)source.get("title"));
				sms.setPic((String)source.get("pic"));
				sms.setDescription((String)source.get("description"));
				sms.setUrl((String)source.get("url"));
				sms.setTime((String)source.get("time"));
				sms.setMd5((String)source.get("md5"));
			}
			//拍照位置信息页面对象
			if(type.equals("phone_PhotoLocation")){
				PhotoLocation sms = new PhotoLocation();
				sms.setName((String)source.get("name"));
				sms.setSize((String)source.get("size"));
				sms.setType((String)source.get("type"));
				sms.setDate((String)source.get("date"));
				sms.setPath((String)source.get("path"));
				sms.setLongitude((String)source.get("longitude"));
				sms.setLatitude((String)source.get("latitude"));
				sms.setMd5((String)source.get("md5"));
			}
			//QQ邮箱 账号信息对象
			if(type.equals("qqEmail_IdInfo")){
				QmailIdInfo sms = new QmailIdInfo();
				sms.setMail((String)source.get("mail"));
				sms.setNickName((String)source.get("nickName"));
				sms.setPwd((String)source.get("pwd"));
				sms.setMd5((String)source.get("md5"));
			}
			//腾讯地图 搜索路线对象
			if(type.equals("TMap_RouteSearch")){
				RouteSearch sms = new RouteSearch();
				sms.setRouteId((String)source.get("routeId"));
				sms.setRouteType((String)source.get("routeType"));
				sms.setStartName((String)source.get("startName"));
				sms.setStartAdd((String)source.get("startAdd"));
				sms.setStartLongitude((String)source.get("startLongitude"));
				sms.setStartLatitude((String)source.get("startLatitude"));
				sms.setEndName((String)source.get("endName"));
				sms.setEndAdd((String)source.get("endAdd"));
				sms.setEndLongitude((String)source.get("endLongitude"));
				sms.setEndLatitude((String)source.get("endLatitude"));
				sms.setMd5((String)source.get("md5"));
			}
			//腾讯地图  个人信息对象
			if(type.equals("TMap_PersonMess")){
				TMapPersonMess sms = new TMapPersonMess();
				sms.setUserId((String)source.get("userId"));
				sms.setUserName((String)source.get("userName"));
				sms.setNickName((String)source.get("nickName"));
				sms.setBirth((String)source.get("birth"));
				sms.setSex((String)source.get("sex"));
				sms.setAvatar((String)source.get("avatar"));
				sms.setMail((String)source.get("mail"));
				sms.setTelephone((String)source.get("telephone"));
				sms.setAdd((String)source.get("add"));
				sms.setSignature((String)source.get("signature"));
				sms.setSinaId((String)source.get("sinaId"));
				sms.setSinaName((String)source.get("sinaName"));
				sms.setQq((String)source.get("qq"));
				sms.setQqName((String)source.get("qqName"));
				sms.setTbId((String)source.get("tbId"));
				sms.setTbName((String)source.get("tbName"));
				sms.setCUId((String)source.get("CUId"));
				sms.setUpdateTime((String)source.get("updateTime"));
				sms.setMd5((String)source.get("md5"));
			}
			//短信息
			if(type.equals("phone_shortMsg")){
				ShortMessage sms = new ShortMessage();
				sms.setSender((String)source.get("sender"));
				sms.setAddressee((String)source.get("addressee"));
				sms.setSendTime((String)source.get("sendTime"));
				sms.setAcceptTime((String)source.get("acceptTime"));
				sms.setSendContent((String)source.get("sendContent"));
				sms.setAcceptContent((String)source.get("acceptContent"));
				sms.setIsDelete((String)source.get("isDelete"));
			}
			//淘宝搜索历史页面对象
			if(type.equals("taobao_history")){
				TbSearchHistory sms = new TbSearchHistory();
				sms.setKeyWord((String)source.get("keyWord"));
				sms.setTime((String)source.get("time"));
				sms.setMd5((String)source.get("md5"));
			}
			//系统日志中 使用过的号码 页面对象
			if(type.equals("phone_used")){
				UsedNumber sms = new UsedNumber();
				sms.setNumber((String)source.get("number"));
				sms.setIccid((String)source.get("iccid"));
				sms.setLastUpdateTime((String)source.get("lastUpdateTime"));
				sms.setMd5((String)source.get("md5"));
			}
			//微信通讯录下的页面对象
			if(type.equals("WeChat_Contacts")){
				WeChatContacts sms = new WeChatContacts();
				sms.setWxId((String)source.get("wxId"));
				sms.setFriendAcc((String)source.get("friendAcc"));
				sms.setFriendNick((String)source.get("friendNick"));
				sms.setSex((String)source.get("sex"));
				sms.setSignature((String)source.get("signature"));
				sms.setRemarks((String)source.get("remarks"));
				sms.setLocation((String)source.get("location"));
				sms.setAvatar((String)source.get("avatar"));
				sms.setQq((String)source.get("qq"));
				sms.setQqNick((String)source.get("qqNick"));
				sms.setMail((String)source.get("mail"));
				sms.setTelephone((String)source.get("telephone"));
				sms.setDescription((String)source.get("description"));
				sms.setTab((String)source.get("tab"));
				sms.setMd5((String)source.get("md5"));
			}
			//微信群相关页面对象
			if(type.equals("WeChat_Group")){
				WeChatGroupAndMember sms = new WeChatGroupAndMember();
				sms.setGroupId((String)source.get("groupId"));
				sms.setGroupName((String)source.get("groupName"));
				sms.setGroupLord((String)source.get("groupLord"));
				sms.setLordNick((String)source.get("lordNick"));
				sms.setMemberNum((String)source.get("memberNum"));
				sms.setMyNick((String)source.get("myNick"));
				sms.setMemberNick((String)source.get("memberNick"));
				sms.setMemberId((String)source.get("memberId"));
				sms.setMemberSignature((String)source.get("memberSignature"));
				sms.setMemberLocation((String)source.get("memberLocation"));
				sms.setMemberAvatar((String)source.get("memberAvatar"));
				sms.setMd5((String)source.get("md5"));
			}
			//微信媒体文件（图片、音频、视频）页面对象
			if(type.equals("WeChat_MediaFile")){
				WeChatMediaFile sms = new WeChatMediaFile();
				sms.setFile((String)source.get("file"));
				sms.setFileName((String)source.get("fileName"));
				sms.setSize((String)source.get("size"));
				sms.setModifiedTime((String)source.get("modifiedTime"));
				sms.setCreateTime((String)source.get("createTime"));
				sms.setFilePath((String)source.get("filePath"));
				sms.setMd5((String)source.get("md5"));
			}
			//微信 聊天记录
			if(type.equals("WeChat_Message")){
				WeChatMessage sms = new WeChatMessage();
				sms.setOtherSide((String)source.get("otherSide"));
				sms.setSender((String)source.get("sender"));
				sms.setType((String)source.get("type"));
				sms.setMessType((String)source.get("messType"));
				sms.setContent((String)source.get("content"));
				sms.setTime((String)source.get("time"));
			}
			//位置信息中 Wi-Fi信息 页面对象
			if(type.equals("phone_WiFi")){
				Wifi sms = new Wifi();
				sms.setMAC((String)source.get("MAC"));
				sms.setName((String)source.get("name"));
				sms.setLastJoinTime((String)source.get("lastJoinTime"));
				sms.setLongitude((String)source.get("longitude"));
				sms.setLatitude((String)source.get("latitude"));
				sms.setHorizon((String)source.get("horizon"));
				sms.setDetectionTime((String)source.get("detectionTime"));
				sms.setTerminalIp((String)source.get("terminalIp"));
				sms.setEncryption((String)source.get("encryption"));
				sms.setConnType((String)source.get("connType"));
				sms.setMd5((String)source.get("md5"));
			}
			//System.out.println(phoneList.toString());
		}
		map.put("count", totalHits);
		map.put("phoneList", phoneList);
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

	 //获取树
    public StringBuilder  getTree(String phone){
    	StringBuilder sbtmp = new StringBuilder();
    	PhoneTree phoneTree = new PhoneTree();
    	phoneTree.setPhone(phone);
    	List<PhoneTree> listfromMysql = dao.getListfromMysql(phoneTree);
    	if(listfromMysql.size()>0){
    		sbtmp.append("[");
	    	for (PhoneTree phoneTree2 : listfromMysql) {
	    		String name = phoneTree2.getName();
				if("0".equals(phoneTree2.getParent())){
					sbtmp.append("{\"id\":\""+phoneTree2.getType()+"\",");
					sbtmp.append("\"text\":\""+name+"\"");
					StringBuilder childrenSbtmp = getChildren(phone,name);
					if(childrenSbtmp.length()>0){
						sbtmp.append(",\"state\":\"closed\"");
						sbtmp.append(",\"children\":[");
						sbtmp.append(childrenSbtmp);
						sbtmp.append("]");
					}
					sbtmp.append("},");
				}
			}
	    	sbtmp.deleteCharAt(sbtmp.length() - 1);//去掉最后一个逗号	
	    	sbtmp.append("]");
    	}
    	return sbtmp;
    }
    
    //获取孩子
    public StringBuilder getChildren(String phone,String parentName){
    	StringBuilder sbtmp = new StringBuilder();
    	PhoneTree phoneTree = new PhoneTree();
    	phoneTree.setPhone(phone);
    	phoneTree.setParent(parentName);
    	List<PhoneTree> listfromMysql = dao.getListfromMysql(phoneTree);
    	if(listfromMysql.size()>0){
	    	for (PhoneTree phoneTree2 : listfromMysql) {
	    		String name = phoneTree2.getName();
	    		sbtmp.append("{\"id\":\""+phoneTree2.getType()+"\",");
				sbtmp.append("\"text\":\""+name+"\"");
				StringBuilder childrenSbtmp = getChildren(phone,name);
				if(childrenSbtmp.length()>0){
					sbtmp.append(",\"state\":\"closed\"");
					sbtmp.append(",\"children\":[");
					sbtmp.append(childrenSbtmp);
					sbtmp.append("]");
				}
				sbtmp.append("},");
			}
	    	sbtmp.deleteCharAt(sbtmp.length() - 1);//去掉最后一个逗号	
    	}
    	return sbtmp;
    }
	
}







