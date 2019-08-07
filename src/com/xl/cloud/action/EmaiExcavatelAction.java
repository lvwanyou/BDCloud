package com.xl.cloud.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xl.cloud.bean.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auxilii.msgparser.Message;
import com.auxilii.msgparser.MsgParser;
import com.mysql.jdbc.Statement;
import com.xl.cloud.common.Global;
import com.xl.cloud.dao.EmailDao;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.dao.ip_SqlDAO;
import com.xl.cloud.util.EsClient;
import com.xl.cloud.util.EsUpdate;
import com.xl.cloud.util.JsonUtil;

import jodd.mail.EmailAttachment;
import jodd.mail.EmailMessage;
import jodd.mail.EmailUtil;
import jodd.mail.ReceivedEmail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class EmaiExcavatelAction {
	private SqlDao sqlDao = new SqlDao();
	private EmailDao  emailDao = new EmailDao();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public List<DomainNameInfo> getBeanStr = new ArrayList<DomainNameInfo>();
	public List<EmailDTO> getDomainEmail = new ArrayList<EmailDTO>();
	final Logger logger = Logger.getLogger(BuildCollection.class);
	private ip_SqlDAO ip_Sqldao =new ip_SqlDAO();

	public EmaiExcavatelAction() throws Exception{
		//		ip_Sqldao1=new ip_SqlDAO();
	}
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

	/** 
	 * 判断查询结果集中是否存在某列 
	 * @param rs 查询结果集 
	 * @param columnName 列名 
	 * @return true 存在; false 不存咋 
	 */
	public boolean isExistColumn(ResultSet rs, String columnName) {  
		try {  
			if (rs!=null&&rs.findColumn(columnName) > 0 ) {  
				return true;  
			}   
		}  
		catch (SQLException e) {  
			return false;  
		} 			       
		return false;  
	}  

	/**
	 * 数据管理-邮件工作台-查询邮件 执行分布式搜索访问并处理数据，邮件列表
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/emaiExcavatel/getEmailList.php")
	public void getEmailList(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		response.setContentType("textml; charset=UTF-8");
		String sortType = request.getParameter("sortType");
		String guanjianci = request.getParameter("guanjianci");//时间线关键词
		String regexpQuery = request.getParameter("regexpQuery");   //regexpQuery
		String emailKeyword3 = request.getParameter("emailKeyword3");//嫌疑人邮箱名关键词
		String caseidStr = request.getParameter("caseidStr");
        System.out.println("caseidStr: " + caseidStr);
		String pageIndexstr = request.getParameter("pageIndex");
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		//筛选条件添加
		String emailKeyword = request.getParameter("emailKeyword");//搜索中的筛选条件
		//高级搜索条件
		String orKey = request.getParameter("orKey");//or
		String andKey = request.getParameter("andKey");//and
		String notKey = request.getParameter("notKey");//not
		String timeType = request.getParameter("timeType");//之间,之外,之前,之后
		String kaishiTime = request.getParameter("kaishiTime");// 开始时间
		String jieshuTime = request.getParameter("jieshuTime");// 结束时间
		String matchingType = request.getParameter("matchingType");//1任意,2全部
		String emailstr = request.getParameter("emailstr");//邮箱地址
		String emailType = request.getParameter("emailType");//收发类型
		String XYRemail =request.getParameter("XYRemail");//获取案件下嫌疑人的邮箱
		String emlType = request.getParameter("emlType");//嫌疑人收发件箱
		
		String[] XYRemails = { "" };
		if (!"".equals(XYRemail)&&XYRemail!=null) {
			XYRemails = XYRemail.split(",");
		}
		int pageIndex = 1;
		int pageSize = 10;
		if (pageIndexstr != null && !"".equals(pageIndexstr)) {
			pageIndex = Integer.parseInt(pageIndexstr);
		}
		// 默认最新数据的案件
		if (caseidStr == null || "".equals(caseidStr)) {
			caseidStr="-1";
		}
		String riqi = "";
		String read = "";
		String star = "";
		String fuj = "";

		String[] caseids = { "" };
		if (!"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		String sortConditon = "";
		if ("未读".equals(sortType)) {
			read = "0";
			sortConditon = "date";
		} else if ("已读".equals(sortType)) {
			read = "1";
			sortConditon = "date";
		} else if ("星标".equals(sortType)) {
			star = "1";
			sortConditon = "date";
		} else if ("日期".equals(sortType)) {
			sortConditon = "date";
		} else if ("收件人".equals(sortType)) {
			sortConditon = "toWho";
		} else if ("发件人".equals(sortType)) {
			sortConditon = "fromWho";
		} else if ("IP".equals(sortType)) {
			sortConditon = "ip";
		} else if ("附件".equals(sortType)) {
			fuj = "123";
			sortConditon = "date";
		}
		// 精确搜索
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		//mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

		//高级搜索or
		if(orKey!=null && !"".equals(orKey)){
			String[] orKeylist = orKey.split(" ");
			// 组合 模糊查询  should  
			BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
			for (String string : orKeylist) {
				TermQueryBuilder subject = QueryBuilders.termQuery("subject", string); 
				TermQueryBuilder content = QueryBuilders.termQuery("content", string);  
				TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", string);
				TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", string);
				TermQueryBuilder attachmentname = QueryBuilders.termQuery("attachmentname", string);
				TermQueryBuilder attachmentContent = QueryBuilders.termQuery("attachmentContent", string);  
				ors.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
			}
			if("1".equals(matchingType)){
				mustQuery.should(ors);
			}else{
				mustQuery.must(ors);
			}
		}

		//高级搜索and
		if(andKey!=null && !"".equals(andKey)){
			String[] andKeylist = andKey.split(" ");
			for (String string : andKeylist) {
				// 组合 模糊查询  should 
				BoolQueryBuilder ands = QueryBuilders.boolQuery(); 
				TermQueryBuilder subject = QueryBuilders.termQuery("subject", string); 
				TermQueryBuilder content = QueryBuilders.termQuery("content", string);  
				TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", string);
				TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", string);
				TermQueryBuilder attachmentname = QueryBuilders.termQuery("attachmentname", string);
				TermQueryBuilder attachmentContent = QueryBuilders.termQuery("attachmentContent", string);  
				ands.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
				if("1".equals(matchingType)){
					mustQuery.should(ands);
				}else{
					mustQuery.must(ands);
				}
			}
		}
		//高级搜索not
		if(notKey!=null && !"".equals(notKey)){
			String[] notKeylist = notKey.split(" ");
			for (String string : notKeylist) {
				// 组合 模糊查询  should  
				BoolQueryBuilder nots = QueryBuilders.boolQuery(); 
				TermQueryBuilder subject = QueryBuilders.termQuery("subject", string); 
				TermQueryBuilder content = QueryBuilders.termQuery("content", string);  
				TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", string);
				TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", string);
				TermQueryBuilder attachmentname = QueryBuilders.termQuery("attachmentname", string);
				TermQueryBuilder attachmentContent = QueryBuilders.termQuery("attachmentContent", string);  
				nots.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
				if("2".equals(matchingType)){
					mustQuery.mustNot(nots);
				}
			}
		}
		//高级搜索邮箱地址
		if(emailstr!=null && !"".equals(emailstr)){
			String[] emailstrKeylist = emailstr.split(" ");
			// 组合 模糊查询  should  
			BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
			for (String string : emailstrKeylist) {
				WildcardQueryBuilder fromWho = QueryBuilders.wildcardQuery("fromWho", "*"+string+"*");  
				WildcardQueryBuilder toWho = QueryBuilders.wildcardQuery("toWho", "*"+string+"*");  
				if("收发".equals(emailType)){
					emailstrs.should(fromWho).should(toWho);
				}else if("发".equals(emailType)){
					emailstrs.should(fromWho);
				}else if("收".equals(emailType)){
					emailstrs.should(toWho);
				}
			}
			if("1".equals(matchingType)){
				mustQuery.should(emailstrs);
			}else{
				mustQuery.must(emailstrs);
			}
		}
		//高级搜索 日期
		if("之间".equals(timeType) && kaishiTime!=null && !"".equals(kaishiTime) && jieshuTime!=null && !"".equals(jieshuTime)){
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").from(kaishiTime + " 00:00:00")
					.to(jieshuTime + " 23:59:59");
			if("1".equals(matchingType)){
				mustQuery.should(rangequerybuilder);
			}else{
				mustQuery.must(rangequerybuilder);
			}
		}else if("之外".equals(timeType) && kaishiTime!=null && !"".equals(kaishiTime) && jieshuTime!=null && !"".equals(jieshuTime)){
			// 组合 模糊查询  should  
			BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
			RangeQueryBuilder subject = QueryBuilders.rangeQuery("date").lt(kaishiTime + " 00:00:00");
			RangeQueryBuilder content = QueryBuilders.rangeQuery("date").gt(jieshuTime + " 23:59:59");   
			ors.should(subject).should(content);
			if("1".equals(matchingType)){
				mustQuery.should(ors);
			}else{
				mustQuery.must(ors);
			}
		}else if("之前".equals(timeType) && kaishiTime!=null && !"".equals(kaishiTime)){
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").lt(kaishiTime + " 00:00:00");
			if("1".equals(matchingType)){
				mustQuery.should(rangequerybuilder);
			}else{
				mustQuery.must(rangequerybuilder);
			}
		}else if("之后".equals(timeType) && jieshuTime!=null && !"".equals(jieshuTime)){
			RangeQueryBuilder rangequerybuilder2 = QueryBuilders.rangeQuery("date").gt(jieshuTime + " 23:59:59");
			if("1".equals(matchingType)){
				mustQuery.should(rangequerybuilder2);
			}else{
				mustQuery.must(rangequerybuilder2);
			}
		}

		// caseid集合
		if (!"".equals(caseidStr)) {
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		}
		// 已读未读状态
		if (!"".equals(read)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("readFlag", read));
			actionLog((String) session.getAttribute("userName"), "搜索邮件状态", "邮件挖掘 ");
		}
		// 星标状态
		if (!"".equals(star)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("starFlag", star));
			actionLog((String) session.getAttribute("userName"), "搜索星标", "邮件挖掘 ");
		}
		// 附件
		if (!"".equals(fuj)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("attachmentname", sortType));
			actionLog((String) session.getAttribute("userName"), "搜索附件", "邮件挖掘 ");
		}
		// 多条件筛选关键词搜索
		if (emailKeyword != null && !"".equals(emailKeyword)) {
			emailKeyword=emailKeyword.toLowerCase();
			// 组合 模糊查询  should  
			String[] emailKeywordlist = emailKeyword.split(" ");
			for (String emailKeywordstring : emailKeywordlist) {
			BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
			//TermQueryBuilder subject = QueryBuilders.termQuery("subject", emailKeyword);
			MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", emailKeywordstring);
			MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", emailKeywordstring);  
			MatchQueryBuilder fromWho = QueryBuilders.matchPhraseQuery("fromWho", emailKeywordstring);
			MatchQueryBuilder toWho = QueryBuilders.matchPhraseQuery("toWho", emailKeywordstring);
			MatchQueryBuilder attachmentname = QueryBuilders.matchPhraseQuery("attachmentname", emailKeywordstring);
			MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", emailKeywordstring);  
			ors.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
			mustQuery.must(ors);
			actionLog((String) session.getAttribute("userName"), "搜索关键词："+emailKeywordstring, "邮件挖掘 ");
			}
		}
		// 时间线关键词搜索
		if (guanjianci != null && !"".equals(guanjianci)) {
			QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(guanjianci)
					.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
			mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
			actionLog((String) session.getAttribute("userName"), "搜索关键词："+guanjianci, "邮件挖掘 ");
		}
		// 嫌疑人邮箱 全部
			if (XYRemail != null && !"".equals(XYRemail)) {
				BoolQueryBuilder ors = QueryBuilders.boolQuery();
				if(emlType.equals("收件箱")){
					//多个嫌疑人对收件人模糊查询
					for (String string : XYRemails) {
						WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho",  "*"+string+"*");
						ors.should(toWho2);
					}
				}else if(emlType.equals("发件箱")){
					TermsQueryBuilder fromWho = QueryBuilders.termsQuery("fromWho", XYRemails);
					ors.should(fromWho);
				}
				mustQuery.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
			}
			// 收发件关键词
			if (emailKeyword3 != null && !"".equals(emailKeyword3)) {
				QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(emailKeyword3)
						.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
				mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
				actionLog((String) session.getAttribute("userName"), "搜索关键词："+emailKeyword3, "邮件挖掘 ");
			}	
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			mustQuery.must(rangequerybuilder);
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery).setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(10);// 本次返回的文档数量
		// 排序 执行
		SearchResponse searchResponse = null;
		if ("date".equals(sortConditon)) {
			searchResponse = searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
					.addSort(sortConditon, SortOrder.ASC).execute().actionGet();// 执行搜索
		} else {
			searchResponse = searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
					.addSort(sortConditon, SortOrder.ASC).execute().actionGet();// 执行搜索
		}
		SearchHit[] hits = searchResponse.getHits().getHits();
		long totalHits = searchResponse.getHits().getTotalHits();
		List<EmailDTO> emailDTOList = new ArrayList<EmailDTO>();
		MainFrameAction.ip_Sqldao=new ip_SqlDAO();
		java.sql.Connection conn1=MainFrameAction.ip_Sqldao.getConnection();//MainFrameAction.				 
		Statement stmt=(Statement) conn1.createStatement();
		ResultSet rs1=null;
		for (SearchHit searchHit : hits) {
			EmailDTO emailDTO = new EmailDTO();
			String esID = (String) searchHit.getId();
			Map<String, Object> source = searchHit.getSource();
			String subject = (String) source.get("subject");
			String fromWho = (String) source.get("fromWho");
			String toWho = (String) source.get("toWho");
			String downloadUrl = (String) source.get("file_download_url");
			String date = (String) source.get("date");
			String attachmentname = (String) source.get("attachmentname");// LW:附件名
			String content = (String) source.get("content");
			String readFlag = (String) source.get("readFlag");
			String starFlag = (String) source.get("starFlag");
			String ip = (String) source.get("ip");
			String caseName = (String) source.get("caseName");
			String caseID = (String) source.get("caseID");
			String multiarea="";
			PreparedStatement st1=conn1.prepareStatement("SELECT * FROM ipoff WHERE minip<=INET_ATON('"+ip+"') ORDER BY minip DESC LIMIT 1");
			st1.execute();
			rs1=st1.executeQuery();
			if(rs1!=null){
				while(rs1.next()){
					if(isExistColumn(rs1, "multiarea")){
						multiarea = rs1.getString("multiarea");
						break;
					}
				}
			}

			//  [{"w":"47.615640","j":"-122.210880","p":"华盛顿","c":"贝尔维","d":""}]
			if(multiarea!=null&&!"".equals(multiarea)){
				String multiarea1=multiarea.replace("\"", "");
				String[] k = multiarea1.split(",|\\:");
				String lon=k[k.length-5];
				String lat=k[k.length-3];

				String address=lon+"-"+lat;
				emailDTO.setMultiarea(address);
			} 

			//Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
			int quickflagflag = 0;

			emailDTO.setEsID(esID);
			emailDTO.setRead(Integer.parseInt(readFlag));
			emailDTO.setStar(Integer.parseInt(starFlag));
			emailDTO.setIp(ip);
			emailDTO.setCaseName(caseName);
			emailDTO.setCaseID(caseID);
			emailDTO.setAttachmentname(attachmentname);
			if (emailKeyword != null && !"".equals(emailKeyword)) {
				Pattern p = Pattern.compile("(?i)"+emailKeyword);
				Matcher m = p.matcher( subject) ;
				String mv = null;
				String string = subject;
				while ( m.find() ) {
					mv = m.group(0);
					string = string.replaceAll(mv, "<font style='color: red;background-color: yellow;'>" + mv + "</font>");
				}
				//				String s1 = subject.replace(emailKeyword,
				//						"<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
				//emailDTO.setSubject(s1);
				emailDTO.setSubject(string);
			} else {
				emailDTO.setSubject(subject);
			}
			if (emailKeyword != null && !"".equals(emailKeyword)) {
				Pattern p = Pattern.compile("(?i)"+emailKeyword);
				Matcher m = p.matcher( fromWho) ;
				String mv = null;
				String string = fromWho;
				while ( m.find() ) {
					mv = m.group(0);
					string = string.replaceAll(mv, "<font style='color: red;background-color: yellow;'>" + mv + "</font>");
				}
				//				String s1 = fromWho.replace(emailKeyword,
				//						"<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
				emailDTO.setFromWho(string);
			} else {
				emailDTO.setFromWho(fromWho);
			}
			if (emailKeyword != null && !"".equals(emailKeyword)) {
				Pattern p = Pattern.compile("(?i)"+emailKeyword);
				Matcher m = p.matcher( toWho) ;
				String mv = null;
				String string = toWho;
				while ( m.find() ) {
					mv = m.group(0);
					
					string = string.replaceAll(mv, "<font style='color: red;background-color: yellow;'>" + mv + "</font>");
				}
				//				String s1 = toWho.replace(emailKeyword,
				//						"<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
				emailDTO.setToWho(string);
			} else {
				emailDTO.setToWho(toWho);
			}
			if (emailKeyword != null && !"".equals(emailKeyword)) {
				Pattern p = Pattern.compile("(?i)"+emailKeyword);
				Matcher m = p.matcher( content) ;
				String mv = null;
				String string = content;
				while ( m.find() ) {
					mv = m.group(0);
					
					string = string.replaceAll(mv, "<font style='color: red;background-color: yellow;'>" + mv + "</font>");
				}
				//				String s1 = content.replace(emailKeyword,
				//						"<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
				emailDTO.setContent(string);
			}/* else if (quickflag != null && !"".equals(quickflag)) {
				quickflagflag = 1;
				String s1 = content;
				Pattern pattern = Pattern.compile(quickflag);
				// 创建匹配给定输入与此模式的匹配器。
				Matcher matcher = pattern.matcher(content);
				// 查找字符串中是否有符合的子字符串
				while (matcher.find()) {
					quickflagflag = 0;
					// 查找到符合的即输出
					String group = matcher.group();
					s1 = s1.replace(group, "<font style='color: red;background-color: yellow;'>" + group + "</font>");
				}
				emailDTO.setContent(s1);
			}*/ else {
				emailDTO.setContent(content);
			}
			if (emailKeyword != null && !"".equals(emailKeyword)) {
				Pattern p = Pattern.compile("(?i)"+emailKeyword);
				Matcher m = p.matcher( date) ;
				String mv = null;
				String string = date;
				while ( m.find() ) {
					mv = m.group(0);
					
					string = string.replaceAll(mv, "<font style='color: red;background-color: yellow;'>" + mv + "</font>");
				}
				//				String s1 = date.replace(emailKeyword,
				//						"<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
				emailDTO.setDate(string);
			} else {
				emailDTO.setDate(date);
			}
			emailDTO.setDownloadUrl(downloadUrl);
			if (quickflagflag == 0) {
				emailDTOList.add(emailDTO);
			}
		}

		/*
		 * 查询未读数目
		 */
		// 精确搜索
		BoolQueryBuilder mustQuery2 = QueryBuilders.boolQuery();
		//高级搜索or
		if(orKey!=null && !"".equals(orKey)){
			String[] orKeylist = orKey.split(" ");
			// 组合 模糊查询  should  
			BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
			for (String string : orKeylist) {
				TermQueryBuilder subject = QueryBuilders.termQuery("subject", string); 
				TermQueryBuilder content = QueryBuilders.termQuery("content", string);  
				TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", string);
				TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", string);
				TermQueryBuilder attachmentname = QueryBuilders.termQuery("attachmentname", string);
				TermQueryBuilder attachmentContent = QueryBuilders.termQuery("attachmentContent", string);  
				ors.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
			}
			if("1".equals(matchingType)){
				mustQuery2.should(ors);
			}else{
				mustQuery2.must(ors);
			}
		}

		//高级搜索and
		if(andKey!=null && !"".equals(andKey)){
			String[] andKeylist = andKey.split(" ");
			for (String string : andKeylist) {
				// 组合 模糊查询  should 
				BoolQueryBuilder ands = QueryBuilders.boolQuery(); 
				TermQueryBuilder subject = QueryBuilders.termQuery("subject", string); 
				TermQueryBuilder content = QueryBuilders.termQuery("content", string);  
				TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", string);
				TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", string);
				TermQueryBuilder attachmentname = QueryBuilders.termQuery("attachmentname", string);
				TermQueryBuilder attachmentContent = QueryBuilders.termQuery("attachmentContent", string);  
				ands.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
				if("1".equals(matchingType)){
					mustQuery2.should(ands);
				}else{
					mustQuery2.must(ands);
				}
			}
		}
		//高级搜索not
		if(notKey!=null && !"".equals(notKey)){
			String[] notKeylist = notKey.split(" ");
			for (String string : notKeylist) {
				// 组合 模糊查询  should  
				BoolQueryBuilder nots = QueryBuilders.boolQuery(); 
				TermQueryBuilder subject = QueryBuilders.termQuery("subject", string); 
				TermQueryBuilder content = QueryBuilders.termQuery("content", string);  
				TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", string);
				TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", string);
				TermQueryBuilder attachmentname = QueryBuilders.termQuery("attachmentname", string);
				TermQueryBuilder attachmentContent = QueryBuilders.termQuery("attachmentContent", string);  
				nots.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
				if("2".equals(matchingType)){
					mustQuery2.mustNot(nots);
				}
			}
		}
		//高级搜索邮箱地址
		if(emailstr!=null && !"".equals(emailstr)){
			String[] emailstrKeylist = emailstr.split(" ");
			// 组合 模糊查询  should  
			BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
			for (String string : emailstrKeylist) {
				WildcardQueryBuilder fromWho = QueryBuilders.wildcardQuery("fromWho", "*"+string+"*");  
				WildcardQueryBuilder toWho = QueryBuilders.wildcardQuery("toWho", "*"+string+"*");  
				if("收发".equals(emailType)){
					emailstrs.should(fromWho).should(toWho);
				}else if("发".equals(emailType)){
					emailstrs.should(fromWho);
				}else if("收".equals(emailType)){
					emailstrs.should(toWho);
				}
			}
			if("1".equals(matchingType)){
				mustQuery2.should(emailstrs);
			}else{
				mustQuery2.must(emailstrs);
			}
		}
		//高级搜索 日期
		if("之间".equals(timeType) && kaishiTime!=null && !"".equals(kaishiTime) && jieshuTime!=null && !"".equals(jieshuTime)){
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").from(kaishiTime + " 00:00:00")
					.to(jieshuTime + " 23:59:59");
			if("1".equals(matchingType)){
				mustQuery2.should(rangequerybuilder);
			}else{
				mustQuery2.must(rangequerybuilder);
			}
		}else if("之外".equals(timeType) && kaishiTime!=null && !"".equals(kaishiTime) && jieshuTime!=null && !"".equals(jieshuTime)){
			// 组合 模糊查询  should  
			BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
			RangeQueryBuilder subject = QueryBuilders.rangeQuery("date").lt(kaishiTime + " 00:00:00");
			RangeQueryBuilder content = QueryBuilders.rangeQuery("date").gt(jieshuTime + " 23:59:59");   
			ors.should(subject).should(content);
			if("1".equals(matchingType)){
				mustQuery2.should(ors);
			}else{
				mustQuery2.must(ors);
			}
		}else if("之前".equals(timeType) && kaishiTime!=null && !"".equals(kaishiTime)){
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").lt(kaishiTime + " 00:00:00");
			if("1".equals(matchingType)){
				mustQuery2.should(rangequerybuilder);
			}else{
				mustQuery2.must(rangequerybuilder);
			}
		}else if("之后".equals(timeType) && jieshuTime!=null && !"".equals(jieshuTime)){
			RangeQueryBuilder rangequerybuilder2 = QueryBuilders.rangeQuery("date").gt(jieshuTime + " 23:59:59");
			if("1".equals(matchingType)){
				mustQuery2.should(rangequerybuilder2);
			}else{
				mustQuery2.must(rangequerybuilder2);
			}
		}

		// caseid集合
		if (!"".equals(caseidStr)) {
			mustQuery2.must(QueryBuilders.termsQuery("caseID", caseids));
		}
		// 附件
		if (!"".equals(fuj)) {
			mustQuery2.must(QueryBuilders.matchPhraseQuery("attachmentname", sortType));
		}
		// 未读
		mustQuery2.must(QueryBuilders.matchPhraseQuery("readFlag", "0"));
		// 星标状态
		if (!"".equals(star)) {
			mustQuery2.must(QueryBuilders.matchPhraseQuery("starFlag", star));
		}
		// 多条件筛选关键词搜索
		if (emailKeyword != null && !"".equals(emailKeyword)) {
			emailKeyword=emailKeyword.toLowerCase();
			// 组合 模糊查询  should
			String[] emailKeywordlist = emailKeyword.split(" ");
			for (String emailKeywordstring : emailKeywordlist) {
			BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
			MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", emailKeywordstring);
			MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", emailKeywordstring);  
			MatchQueryBuilder fromWho = QueryBuilders.matchPhraseQuery("fromWho", emailKeywordstring);
			MatchQueryBuilder toWho = QueryBuilders.matchPhraseQuery("toWho", emailKeywordstring);
			MatchQueryBuilder attachmentname = QueryBuilders.matchPhraseQuery("attachmentname", emailKeywordstring);
			MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", emailKeywordstring);  
			ors.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
			mustQuery2.must(ors);
			actionLog((String) session.getAttribute("userName"), "搜索关键词："+emailKeyword, "邮件挖掘 ");
			}
		}
		// 时间线关键词搜索
		if (guanjianci != null && !"".equals(guanjianci)) {
			QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(guanjianci)
					.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
			mustQuery2.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
			actionLog((String) session.getAttribute("userName"), "搜索关键词："+guanjianci, "邮件挖掘 ");
		}
		// 嫌疑人邮箱名关键词
			if (emailKeyword3 != null && !"".equals(emailKeyword3)) {
				QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(emailKeyword3)
						.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
				mustQuery2.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
				actionLog((String) session.getAttribute("userName"), "搜索关键词："+emailKeyword3, "邮件挖掘 ");
			}
		// 嫌疑人邮箱 未读
		if (XYRemail != null && !"".equals(XYRemail)) {
			BoolQueryBuilder ors = QueryBuilders.boolQuery();
			if(emlType.equals("收件箱")){
				//多个嫌疑人对收件人模糊查询
				for (String string : XYRemails) {
					WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho",  "*"+string+"*");
					ors.should(toWho2);
				}
			}else if(emlType.equals("发件箱")){
				TermsQueryBuilder fromWho = QueryBuilders.termsQuery("fromWho", XYRemails);
				ors.should(fromWho);
			}
			mustQuery2.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
		}
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder2 = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			mustQuery2.must(rangequerybuilder2);
		}
		SearchRequestBuilder searchRequestBuilder2 = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery2).setFrom(0)// 分页起始位置（跳过开始的n个）
				.setSize(10);// 本次返回的文档数量
		// 排序 执行
		SearchResponse searchResponse2 = searchRequestBuilder2.addSort(sortConditon, SortOrder.ASC)// 排序.addSort(SortBuilders.fieldSort(sortConditon))//按类型排序
				.execute().actionGet();// 执行搜索
		long read0 = searchResponse2.getHits().getTotalHits();
		if ("1".equals(read)) {
			read0 = 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		getDomainEmail = emailDTOList;
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
	 * 邮件工作台-更改邮件状态和星标
	 */
	@RequestMapping(value = "/emaiExcavatel/upEmailStatus.php")
	public void upEmailStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String read = request.getParameter("read");
		String star = request.getParameter("star");
		String esId = request.getParameter("esId");
		String emlpath = request.getParameter("emlpath");
		String caseIDs = request.getParameter("caseIDs");
		
		System.out.println("read="+read);
		System.out.println("star="+star);
		System.out.println("esId="+esId);
		System.out.println("emlpath="+emlpath);
		System.out.println("caseIDs="+caseIDs);
		
		
		int caseidInt = Integer.valueOf(caseIDs);
		String tableRegexgName = "";
		if(caseidInt%9==0){
			tableRegexgName = "regexfind_0";
		} else if(caseidInt%9==1){
			tableRegexgName = "regexfind_1";
		} else if(caseidInt%9==2){
			tableRegexgName = "regexfind_2";
		} else if(caseidInt%9==3){
			tableRegexgName = "regexfind_3";
		} else if(caseidInt%9==4){
			tableRegexgName = "regexfind_4";
		} else if(caseidInt%9==5){
			tableRegexgName = "regexfind_5";
		} else if(caseidInt%9==6){
			tableRegexgName = "regexfind_6";
		} else if(caseidInt%9==7){
			tableRegexgName = "regexfind_7";
		} else if(caseidInt%9==8){
			tableRegexgName = "regexfind_8";
		}
		
		
		sqlDao.updateRead(emlpath, tableRegexgName);
		
		String key = "";
		String value = "";
		if (!"".equals(read)) {
			key = "readFlag";
			value = read;
		}
		if (!"".equals(star)) {
			key = "starFlag";
			value = star;
		}
		EsClient.update("es", "email", esId, key, value);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(value + "");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

//	/**
//	 * 邮件挖掘-邮箱关系图
//	 * @author suny
//	 * @time 2017-8-25
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws InterruptedException
//	 */
//	@RequestMapping(value = "/emaiExcavatel/contactsAnalyze.php")
//	public void contactsAnalyze(HttpServletRequest request, HttpServletResponse response, HttpSession session)
//			throws IOException {
//		response.setContentType("textml; charset=UTF-8");
//		String caseidStr = request.getParameter("caseidStr");// 案件id
//		String suspectsNameStr = request.getParameter("suspectsName");// 嫌疑人姓名
//		String startDate = request.getParameter("startDate");// 开始时间
//		String endDate = request.getParameter("endDate");// 结束时间
//		List<EmailNodeDTO> emailNodeDTOList = new ArrayList<EmailNodeDTO>();
//		if (caseidStr == null || "".equals(caseidStr)) {
//			caseidStr="-1";
//		}
//		String[] caseids = caseidStr.split(" ");
//		int caseidi = 0;
//		int caseidiflag = 0;
//		for (String caseid : caseids) {
//			caseidiflag = 0;
//			Caseinfo caseinfo = new Caseinfo();
//			caseinfo.setId(Integer.parseInt(caseid));
//			List<Caseinfo> listfromMysql = sqlDao.getListfromMysql(caseinfo);
//			String caseName = listfromMysql.get(0).getCaseName();
//			// 精确搜索
//			BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
//			mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件
//			// 此处为匹配所有文档
//			// caseid
//			mustQuery.must(QueryBuilders.matchPhraseQuery("caseID", caseid));
//
//			// 日期范围
//			if (startDate != null && !"".equals(startDate)) {
//				RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
//						.to(endDate + " 23:59:59");
//				mustQuery.must(rangequerybuilder);
//			}
//			SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email");
//			// 关键词搜索
//			if (suspectsNameStr != null && !"".equals(suspectsNameStr)) {
//				QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(suspectsNameStr)
//						.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
//				mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
//				actionLog((String) session.getAttribute("userName"), "搜索关键词："+suspectsNameStr, "邮件挖掘 ");
//			}
//			searchRequestBuilder.setQuery(mustQuery).setFrom(0)// 分页起始位置（跳过开始的n个）
//			.setSize(10000);// 本次返回的文档数量
//			// 排序 执行
//			SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();// 执行搜索
//			//searchResponse = searchRequestBuilder.execute().actionGet();
//			//do {
//			//searchResponse = EsClient.getClient().prepareSearchScroll(searchResponse.getScrollId()).execute().actionGet();
//			//} while(searchResponse.getHits().getHits().length != 0);
//
//			ArrayList<SearchHit[]> arrayList = new ArrayList<SearchHit[]>();
//			SearchHit[] hits1 = searchResponse.getHits().getHits();
//			arrayList.add(hits1);
//			long totalHits = searchResponse.getHits().getTotalHits()/10000;
//			for(int i=1;i<=totalHits;i++){
//				searchResponse = searchRequestBuilder.setQuery(mustQuery).setFrom(i*10000)// 分页起始位置（跳过开始的n个）
//						.setSize(10000).execute().actionGet();
//				SearchHit[] hits  = searchResponse.getHits().getHits();
//				arrayList.add(hits);
//			}
//			// List<EmailDTO> emailDTOList = new ArrayList<EmailDTO>();
//			for (SearchHit[] searchHit2 : arrayList) {
//				for (SearchHit searchHit : searchHit2) {
//					caseidiflag = 1;
//					String esID = (String) searchHit.getId();
//					Map<String, Object> source = searchHit.getSource();
//					String fromWho = (String) source.get("fromWho");
//					String toWho = (String) source.get("toWho");
//					// 发件人邮箱添加到node
//					EmailNodeDTO emailNodeDTO = new EmailNodeDTO();
//					emailNodeDTO.setName(fromWho.replace("&lt;", "<").replace("&gt", ">"));
//					emailNodeDTO.setToWho(toWho.replace("&lt;", "<").replace("&gt", ">"));
//					emailNodeDTO.setCategory(caseidi);
//					emailNodeDTO.setCaseName(caseName);
//					if (emailNodeDTOList.size() > 0) {
//						int flag = 0;
//						int i = 0;
//						for (EmailNodeDTO emailNodeDTO2 : emailNodeDTOList) {
//							if ((fromWho.replace("&lt;", "<").replace("&gt", ">")).equals(emailNodeDTO2.getName())) {
//								flag = 1;
//								emailNodeDTOList.get(i).setValue(emailNodeDTOList.get(i).getValue() + 1);
//								emailNodeDTOList.get(i).setSymbolSize(emailNodeDTOList.get(i).getSymbolSize() + 1);
//								if (!toWho.equals(emailNodeDTO2.getToWho())) {
//									emailNodeDTOList.get(i).setToWho(emailNodeDTOList.get(i).getToWho() + "/" + toWho);
//								}
//							}
//							i++;
//						}
//						if (flag == 0) {
//							emailNodeDTO.setId(i);
//							emailNodeDTO.setValue(1);
//							emailNodeDTO.setSymbolSize(1);
//							emailNodeDTOList.add(emailNodeDTO);
//						}
//					} else {
//						emailNodeDTO.setId(0);
//						emailNodeDTO.setValue(1);
//						emailNodeDTO.setSymbolSize(1);
//						emailNodeDTOList.add(emailNodeDTO);
//					}
//				}
//				for (SearchHit searchHit : searchHit2) {
//					// 收件人邮箱添加到node
//					String esID = (String) searchHit.getId();
//					Map<String, Object> source = searchHit.getSource();
//					String fromWho = (String) source.get("fromWho");
//					String toWho = (String) source.get("toWho");
//					String[] toWhos = toWho.split(";;");
//					for (String toWho2 : toWhos) {
//						EmailNodeDTO emailNodeDTO3 = new EmailNodeDTO();
//						emailNodeDTO3.setName(toWho2.replace("&lt;", "<").replace("&gt", ">"));
//						emailNodeDTO3.setToWho(fromWho.replace("&lt;", "<").replace("&gt", ">"));
//						emailNodeDTO3.setCategory(caseidi);
//						emailNodeDTO3.setCaseName(caseName);
//						if (emailNodeDTOList.size() > 0) {
//							int flag = 0;
//							int i = 0;
//							for (EmailNodeDTO emailNodeDTO4 : emailNodeDTOList) {
//								if ((toWho2.replace("&lt;", "<").replace("&gt", ">")).equals(emailNodeDTO4.getName())) {
//									flag = 1;
//									emailNodeDTOList.get(i).setValue(emailNodeDTOList.get(i).getValue() + 1);
//									emailNodeDTOList.get(i).setSymbolSize(emailNodeDTOList.get(i).getSymbolSize() + 1);
//								}
//								i++;
//							}
//							if (flag == 0) {
//								emailNodeDTO3.setId(i);
//								emailNodeDTO3.setValue(1);
//								emailNodeDTO3.setSymbolSize(1);
//								emailNodeDTOList.add(emailNodeDTO3);
//							}
//						} else {
//							emailNodeDTO3.setId(0);
//							emailNodeDTO3.setValue(1);
//							emailNodeDTO3.setSymbolSize(1);
//							emailNodeDTOList.add(emailNodeDTO3);
//						}
//					}
//				} // 遍历hits结束
//				if (caseidiflag == 1) {
//					caseidi++;
//				}
//			}
//		}
//		// Map<String, Object> map = new HashMap<String, Object>();
//		PrintWriter writer = null;
//		try {
//			writer = response.getWriter();
//			writer.write(JsonUtil.list2json(emailNodeDTOList));
//			writer.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (writer != null) {
//				writer.close();
//			}
//		}
//	}

	/**
	 * 邮件挖掘-嫌疑人关系图
	 * @author suny
	 * @time 2017-8-28
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/emaiExcavatel/contactsAnalyze2.php")
	public void contactsAnalyze2(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String caseidStr = request.getParameter("caseidStr");// 案件id
		String emailKeywordD5 = request.getParameter("emailKeywordD5");// 关键字
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String connectNum = request.getParameter("connectNum");//联系次数最小值
		String XYRID = request.getParameter("XYRID");//获取单个案件下的所选嫌疑人的id（前台传过来一个以逗号拼接的id
		String XYRemail = request.getParameter("XYRemail");//获取单个案件下的所选嫌疑人的邮箱
		String emlType = request.getParameter("emlType");//获取单个案件下的所选嫌疑人的查询的类型
		
		//获取勾选的邮箱地址
		if("".equals(connectNum)||connectNum==null){
			connectNum="10";
		}//第一次展示所有次数关系图
		/**
		 * ============前端选择的案件 ID 集合 ===============
		 */
		if (caseidStr == null || "".equals(caseidStr)) {
			caseidStr="-1";
		}
		String[] caseidStrs = caseidStr.split(" ");
		// 时间===================================="+new Date());
		/**
		 * ============嫌疑人list===============
		 */
		List<SuspectInfo> suspectsEmailList = new ArrayList<SuspectInfo>();
		/**
		 * ============所选案件下的所有嫌疑人 ===============
		 */
		for (String caseid : caseidStrs) {
			Caseinfo caseinfo = new Caseinfo();
			caseinfo.setId(Integer.parseInt(caseid));
			List<Caseinfo> listfromMysql = sqlDao.getListfromMysql(caseinfo);
			Caseinfo caseinfo2 = listfromMysql.get(0);
			String mainParty = caseinfo2.getMainParty();
			String[] split = mainParty.split(",");
			//XYRID="21290,20789";
			if(XYRID!=null&&!"".equals(XYRID)){//如果选择案件下的嫌疑人分析
				split=XYRID.split(",");
			}
			// 遍历嫌疑人
			for (String string : split) {
				if(!"".equals(string)){
					SuspectInfo suspectInfo = new SuspectInfo();
					suspectInfo.setId(Integer.parseInt(string));
					List<SuspectInfo> suspectsEmail = sqlDao.getListfromMysqlLike(suspectInfo);
					if (suspectsEmail.size() > 0) {
						suspectInfo = suspectsEmail.get(0);
					}
					int susFlag = 0;
					for (SuspectInfo suspectInfo2 : suspectsEmailList) {
						if (suspectInfo.getId() == suspectInfo2.getId()) {
							susFlag = 1;
						}
					}
					if (susFlag == 0) {
						suspectsEmailList.add(suspectInfo);
					}
				}
			}
		}
		String xyrs[]=null;
		List<String> xyrslist = new ArrayList<String>();
		if(XYRemail!=null&&!"".equals(XYRemail)){//如果选择案件下的嫌疑人分析
			xyrs=XYRemail.split(",");
		}else{
			for (int i = 0; i < suspectsEmailList.size(); i++) {
				xyrslist.add(suspectsEmailList.get(i).getSuspectMail());
			}
		}

		/**
		 * ============创建node集合 和link集合 ===============
		 */
		List<EmailNodeDTO2> nodes = new ArrayList<EmailNodeDTO2>();
		List<EmailNode2LinkDTO> links = new ArrayList<EmailNode2LinkDTO>();
		/**
		 * ============查询===============
		 */
		List<Email_WorkDTO> arrayList = new ArrayList<Email_WorkDTO>();
		String caseid1=caseidStrs[0];//获取单个案件的id
		int caseidInt = Integer.valueOf(caseid1);
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
		//选择多个嫌疑人邮箱分析，循环调用，把结果添加到arratList中
		try {
			if(caseidStr!="-1"){
				if(XYRemail!=null&&!"".equals(XYRemail)){
					for (int i = 0; i < xyrs.length; i++) {
						arrayList.addAll(emailDao.getXYRFromToCount(caseid1,tableName,connectNum,xyrs[i],emlType));
					}
				}else{
					for (int i = 0; i < xyrslist.size(); i++) {
						arrayList.addAll(emailDao.getXYRFromToCount(caseid1,tableName,connectNum,xyrslist.get(i),emlType));
					}
				}
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		for (SuspectInfo suspectInfo : suspectsEmailList) {
			String suspectName = suspectInfo.getSuspectName();
			String suspectMail = suspectInfo.getSuspectMail();
			for (Email_WorkDTO email_WorkDTO : arrayList) {
				String fromWho = email_WorkDTO.getFormWho();// 发件人
				String toWho = email_WorkDTO.getToWho();
				int emailNum = email_WorkDTO.getEmailNum();
				//String[] toWhos = toWho.split(";");
				if(suspectMail!=null && !"".equals(suspectMail)){
					if (fromWho.indexOf(suspectMail) != -1 ||  toWho.indexOf(suspectMail) != -1) {
						// 发件人添加到nodescaseidi
						EmailNodeDTO2 node1 = new EmailNodeDTO2();
						if (fromWho.indexOf(suspectMail) != -1) {
							node1.setName(suspectName+"&^&"+suspectMail);
							node1.setIsSuspect(1);
						} else {
							node1.setName(fromWho);
							node1.setIsSuspect(0);
						}
						int flag1 = 0;
						for (EmailNodeDTO2 node : nodes) {
							if (node1.getName().equals(node.getName())) {
								flag1 = 1;
							}
						}
						if (flag1 == 0) {
							nodes.add(node1);
						}
						// 收件人添加到nodes
						//for (String toWho2 : toWhos) {
							EmailNodeDTO2 node2 = new EmailNodeDTO2();
							if (toWho.indexOf(suspectMail) != -1) {
								node2.setName(suspectName+"&^&"+suspectMail);
								node2.setIsSuspect(1);
							} else {
								node2.setName(toWho);
								node2.setIsSuspect(0);
							}
							int flag2 = 0;
							for (EmailNodeDTO2 node : nodes) {
								if (node2.getName().equals(node.getName())) {
									flag2 = 1;
								}
							}
							if (flag2 == 0) {
								nodes.add(node2);
							}
							// 连线
							EmailNode2LinkDTO link = new EmailNode2LinkDTO();
							link.setFromWho(node1.getName());
							link.setToWho(node2.getName());
							link.setEsID("1");
							link.setValue(emailNum);
							links.add(link);
						//}
					}
				}
			} 
		} 

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodes", nodes);// nodes"+nodes.size()+"=links="+links.size()+"==linksise="+linksise+"==
		map.put("links", links);
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

	// 收发件分析 导出功能
	@RequestMapping("/ExportMail.php")
	public void ExportMail(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
	IllegalAccessException, InvocationTargetException {
		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");
		try {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			String path = projectpath + filename + ".xls";
			File file = new File(path);
			createMail(path);
			fs = new POIFSFileSystem(new FileInputStream(path));
			wb = new HSSFWorkbook(fs);
			for (EmailDTO testData : getDomainEmail) {
			}
			for (int i = 0; i < getDomainEmail.size(); i++) {
				writeMail(getDomainEmail.get(i), wb, path);
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

	public void createMail(String path) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet2");

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);

		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);

		// 给Excel的单元格设置样式和赋值
		cell.setCellValue("邮件主题");
		cell = row.createCell(1);
		cell.setCellValue("发件人");
		cell = row.createCell(2);
		cell.setCellValue("收件人");
		cell = row.createCell(3);
		cell.setCellValue("发送日期");
		cell = row.createCell(4);

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	public static void writeMail(EmailDTO bean, HSSFWorkbook wb, String path) throws Exception {
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
		cell.setCellValue(bean.getSubject());
		cell = row1.createCell(1);
		cell.setCellValue(bean.getFromWho());
		cell = row1.createCell(2);
		cell.setCellValue(bean.getToWho());
		cell = row1.createCell(3);
		cell.setCellValue(bean.getDate());
		cell = row1.createCell(4);

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	/**
	 * 数据管理-邮件工作台-快速标记查询
	 */
	@RequestMapping(value = "/emaiExcavatel/quickFlags.php")
	public void quickFlags(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String sortType = request.getParameter("sortType");
		String regexpQuery = request.getParameter("regexpQuery");
		String emailKeyword = request.getParameter("emailKeyword");
		String caseidStr = request.getParameter("caseidStr");
		String pageIndexstr = request.getParameter("pageIndex");
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String XYRemail = request.getParameter("XYRemail");// 案件下的指定嫌疑人邮箱
		String[] XYRemails = { "" };
		if (!"".equals(XYRemail)&&XYRemail!=null) {
			XYRemails = XYRemail.split(",");
		}
		int pageIndex = 1;
		int pageSize = 10;
		if (pageIndexstr != null && !"".equals(pageIndexstr)) {
			pageIndex = Integer.parseInt(pageIndexstr);
		}
		// 默认最新数据的案件
		if (caseidStr == null || "".equals(caseidStr)) {
			caseidStr="-1";
		}
		String riqi = "";
		String read = "";
		String star = "";
		String fuj = "";
		String[] caseids = { "" };
		if (!"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		String sortConditon = "";
		if ("未读".equals(sortType)) {
			read = "0";
			sortConditon = "date";
		} else if ("已读".equals(sortType)) {
			read = "1";
			sortConditon = "date";
		} else if ("星标".equals(sortType)) {
			star = "1";
			sortConditon = "date";
		} else if ("日期".equals(sortType)) {
			sortConditon = "date";
		} else if ("收件人".equals(sortType)) {
			sortConditon = "toWho";
		} else if ("发件人".equals(sortType)) {
			sortConditon = "fromWho";
		} else if ("IP".equals(sortType)) {
			sortConditon = "ip";
		} else if ("附件".equals(sortType)) {
			fuj = "123";
			sortConditon = "date";
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
		} else if ("支付宝号".equals(regexpQuery)) {
			quickflag = Global.regUP;
		} else if ("价格".equals(regexpQuery)) {
			quickflag = Global.regPrice;
		} else if ("发票代码".equals(regexpQuery)) {
			quickflag = Global.regStamp;
		} else if ("信用证".equals(regexpQuery)) {
			quickflag = Global.regLC;
		} else if ("电汇".equals(regexpQuery)) {
			quickflag = Global.regTT;
		} else if ("QQ号".equals(regexpQuery)) {
			quickflag = Global.QQnumber;
		} else if ("推特号".equals(regexpQuery)) {
			quickflag = Global.twitter;
		} else if ("微信".equals(regexpQuery)) {
			quickflag = Global.weChat;
		} else if ("护照编号".equals(regexpQuery)) {
			quickflag = Global.passport;
		}
		// 精确搜索
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
		// caseid集合
		if (!"".equals(caseidStr)) {
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
		// 附件
		if (!"".equals(fuj)) {
			mustQuery.mustNot(QueryBuilders.matchPhraseQuery("attachmentname", ""));
		}
		// 嫌疑人邮箱   
		if (XYRemail != null && !"".equals(XYRemail)) {
			BoolQueryBuilder ors = QueryBuilders.boolQuery();
			TermsQueryBuilder fromWho = QueryBuilders.termsQuery("fromWho", XYRemails);
			ors.should(fromWho);
			//多个嫌疑人对收件人模糊查询
			for (String string : XYRemails) {
				WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho",  "*"+string+"*");
				ors.should(toWho2);
			}
			
			mustQuery.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
		}
		// 关键词搜索
		if (emailKeyword != null && !"".equals(emailKeyword)) {
			emailKeyword=emailKeyword.toLowerCase();
			// 组合 模糊查询  should  
			BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
			//TermQueryBuilder subject = QueryBuilders.termQuery("subject", emailKeyword);
			MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", emailKeyword);
			MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", emailKeyword);  
			MatchQueryBuilder fromWho = QueryBuilders.matchPhraseQuery("fromWho", emailKeyword);
			MatchQueryBuilder toWho = QueryBuilders.matchPhraseQuery("toWho", emailKeyword);
			MatchQueryBuilder attachmentname = QueryBuilders.matchPhraseQuery("attachmentname", emailKeyword);
			MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", emailKeyword);  
			ors.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
			mustQuery.must(ors);
		}
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			mustQuery.must(rangequerybuilder);
		}

		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery)
				// 模糊查询
				.setFrom(0)// 分页起始位置（跳过开始的n个）
				.setSize(10);// 本次返回的文档数量

		// 排序 执行
		SearchResponse searchResponse = null;
		if ("date".equals(sortConditon)) {
			searchResponse = searchRequestBuilder.addSort(SortBuilders.fieldSort(sortConditon).order(SortOrder.DESC))// 按类型排序
					.execute().actionGet();// 执行搜索
		} else {
			searchResponse = searchRequestBuilder.addSort(SortBuilders.fieldSort(sortConditon).order(SortOrder.ASC))// 按类型排序
					.execute().actionGet();// 执行搜索
		}

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
		int read0 = 0;
		if ("1".equals(read)) {
			read0 = 0;
		}
		List<EmailDTO> emailDTOList = new ArrayList<EmailDTO>();

		long fori = 0;// 计数
		for (SearchHit[] searchHit2 : arrayList) {
			for (SearchHit searchHit : searchHit2) {
				String esID = (String) searchHit.getId();
				Map<String, Object> source = searchHit.getSource();
				String subject = (String) source.get("subject");
				String fromWho = (String) source.get("fromWho");
				String toWho = (String) source.get("toWho");
				String downloadUrl = (String) source.get("file_download_url");
				String date = (String) source.get("date");
				String attachmentname = (String) source.get("attachmentname");// LW:附件名
				String content = (String) source.get("content");
				String readFlag = (String) source.get("readFlag");
				String starFlag = (String) source.get("starFlag");
				String ip = (String) source.get("ip");

				String caseID = (String) source.get("caseID");
				String caseName = (String) source.get("caseName");

				int quickflagflag = 0;
				EmailDTO emailDTO = new EmailDTO();
				emailDTO.setEsID(esID);
				emailDTO.setRead(Integer.parseInt(readFlag));
				emailDTO.setStar(Integer.parseInt(starFlag));
				emailDTO.setIp(ip);
				int zhenzeflag=0;
				Pattern pattern = Pattern.compile(quickflag);
				// 创建匹配给定输入与此模式的匹配器。
				Matcher matcher = pattern.matcher(content);
				// 查找字符串中是否有符合的子字符串
				while (matcher.find()) {
					zhenzeflag=0;
					quickflagflag = 1;
					// 查找到符合的即输出
					String group = matcher.group();
					if("身份证号".equals(regexpQuery)){
						String s = group;// 初次获取得到身份证号
						List<String> list = new ArrayList<String>();
						List<String> list2 = new ArrayList<String>();
						list2.add("7");
						list2.add("9");
						list2.add("10");
						list2.add("5");
						list2.add("8");
						list2.add("4");
						list2.add("2");
						list2.add("1");
						list2.add("6");
						list2.add("3");
						list2.add("7");
						list2.add("9");
						list2.add("10");
						list2.add("5");
						list2.add("8");
						list2.add("4");
						list2.add("2");
						for (int i = 0; i < s.length(); i++) {
							String substring = s.substring(i, i + 1);
							list.add(substring);
						}
						String lastbit=list.get(list.size() - 1);
						if("1".equals(lastbit)){
							lastbit="0";
						}else if ("0".equals(lastbit)) {
							lastbit="1";
						}else if ("0".equals(lastbit)) {
							lastbit="1";
						}else if ("X".equals(lastbit)||"x".equals(lastbit)) {
							lastbit="2";
						}else if ("9".equals(lastbit)) {
							lastbit="3";
						}else if ("8".equals(lastbit)) {
							lastbit="4";
						}else if ("7".equals(lastbit)) {
							lastbit="5";
						}else if ("6".equals(lastbit)) {
							lastbit="6";
						}else if ("5".equals(lastbit)) {
							lastbit="7";
						}else if ("4".equals(lastbit)) {
							lastbit="8";
						}else if ("3".equals(lastbit)) {
							lastbit="9";
						}else if ("2".equals(lastbit)) {
							lastbit="10";
						}
						double c = 0;
						double num = 0 ;
						for (int i = 0; i < list2.size(); i++) {
							int a = (Integer.parseInt(list.get(i)));
							int b = (Integer.parseInt(list2.get(i)));
							double num2 = a * b;
							num+=num2;
							c = (num) % 11;
						}
						double x = Integer.parseInt(lastbit);
						if (c != x) {
							zhenzeflag=1;
						}
					}
					if("集装箱号".equals(regexpQuery)){
						String s =group;// 初次获取得到集装箱号
						List<String> list = new ArrayList<String>();
						for (int i = 0; i < s.length(); i++) {
							String substring = s.substring(i, i + 1);
							String replace = substring.replace("A", "10").replace("B",
									"12").replace("C", "13").replace("D", "14")
									.replace("E", "15").replace("F", "16").replace("G",
											"17").replace("H", "18").replace("I", "19")
									.replace("J", "20").replace("K", "21").replace("L",
											"23").replace("M", "24").replace("N", "25")
									.replace("O", "26").replace("P", "27").replace("Q",
											"28").replace("R", "29").replace("S", "30")
									.replace("T", "31").replace("U", "32").replace("V",
											"34").replace("W", "35").replace("X", "36")
									.replace("Y", "37").replace("Z", "38");
							list.add(replace);
						}
						double c = 0;
						double num = 0;
						for (int i = 0; i < list.size()-1; i++) {
							double d = Math.pow(2, i);
							int a = (Integer.parseInt(list.get(i)));
							double num2 = a * d;
							num+=num2;
							c = (num) % 11;
						}
						double x = Integer.parseInt(list.get(list.size() - 1));
						if (c != x) {
							zhenzeflag=1;
						}
					}
					if("银行卡号".equals(regexpQuery)){
						String sfz = group;
						char res = getBankCardCheckCode(sfz.substring(0, sfz.length()-1));//得到的校验码
						String charJX = sfz.substring(sfz.length() - 1);// 银行卡号最后一位
						if(!charJX.equals(String.valueOf(res))){//如果校验码不等于卡号最后一位，说明银行卡号是错的
							zhenzeflag=1;
						}
					}
					content = content.replace(group,
							"<font style='color: red;background-color: yellow;'>" + group + "</font>");
					if(subject.contains(group)){
						subject = subject.replace(group,
								"<font style='color: red;background-color: yellow;'>" + group + "</font>");
					}
					if(toWho.contains(group)){
						toWho = toWho.replace(group,
								"<font style='color: red;background-color: yellow;'>" + group + "</font>");
					}
					if(fromWho.contains(group)){
						fromWho = fromWho.replace(group,
								"<font style='color: red;background-color: yellow;'>" + group + "</font>");
					}
					if(attachmentname.contains(group)){
						attachmentname = attachmentname.replace(group,
								"<font style='color: red;background-color: yellow;'>" + group + "</font>");
					}

				}
				emailDTO.setContent(content);
				emailDTO.setDate(date);
				emailDTO.setDownloadUrl(downloadUrl);

				emailDTO.setCaseID(caseID);
				emailDTO.setCaseName(caseName);
				emailDTO.setAttachmentname(attachmentname);
				emailDTO.setSubject(subject);
				emailDTO.setFromWho(fromWho);
				emailDTO.setToWho(toWho);

				if (quickflagflag == 1) {
					if(zhenzeflag!=1){
						if (fori >= ((pageIndex - 1) * 10) && fori < (pageIndex * 10)) {// 页数
							emailDTOList.add(emailDTO);
						}
						if ("0".equals(readFlag)) {
							read0 += 1;
						}
						fori += 1;
					}
				}
			}
		}
		/*
		 * 查询未读数目
		 */

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("emailDTOList", emailDTOList);
		map.put("count", fori);
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
	 * 获取原格式邮件详情
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/getCorrectEml.php")
	public void getCorrectEml(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String emlPath = request.getParameter("emlpath");
		JSONObject resResult = new JSONObject();
		String attachmentname = request.getParameter("attachmentname");
		String keyword = request.getParameter("keyword");// 关键词
		String orkey = request.getParameter("orkey");
		String andkey = request.getParameter("andkey");
		File tempFilePath = new File("/temp/");

		String regexpQuery = request.getParameter("zhengzeType");
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
		} else if ("支付宝号".equals(regexpQuery)) {
			quickflag = Global.regUP;
		} else if ("价格".equals(regexpQuery)) {
			quickflag = Global.regPrice;
		} else if ("发票代码".equals(regexpQuery)) {
			quickflag = Global.regStamp;
		} else if ("信用证".equals(regexpQuery)) {
			quickflag = Global.regLC;
		} else if ("电汇".equals(regexpQuery)) {
			quickflag = Global.regTT;
		} else if ("QQ号".equals(regexpQuery)) {
			quickflag = Global.QQnumber;
		} else if ("推特号".equals(regexpQuery)) {
			quickflag = Global.twitter;
		} else if ("微信".equals(regexpQuery)) {
			quickflag = Global.weChat;
		} else if ("护照编号".equals(regexpQuery)) {
			quickflag = Global.passport;
		}
		if (!tempFilePath.exists()) {
			tempFilePath.mkdir();
		}
		try {
			String cmd = "hadoop fs -copyToLocal " + emlPath + " /temp/";
			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
		} catch (Exception e1) {
			// e1.printStackTrace();
		}
		int inx = emlPath.lastIndexOf("/");
		String fname = emlPath.substring(inx + 1);//D:/Desktop/BDCloud备份包/016a616c-e6b6-4c22-95f6-c1de721f45be.eml
		File tempFile = new File("/temp/" + fname);
		String content = "";
		if(fname.endsWith(".eml")){
			try {
				if (tempFile.exists()) {
					ReceivedEmail email = EmailUtil.parseEML(tempFile);
					List<EmailMessage> messageList = email.getAllMessages();
					for (EmailMessage message : messageList) {
						content = message.getContent();
					}
					List<EmailAttachment> attachments = email.getAttachments();
					if (attachments != null) {
						String destPathStr = Global.tomcatPath + "/eml/";

						File destPath = new File(destPathStr);
						if (!destPath.exists()) {
							destPath.mkdirs();
						}
						for (EmailAttachment attachment : attachments) {
							if (attachment.isInline()) {
								attachment.writeToFile(new File(destPathStr + attachment.getName()));
								String contentId = attachment.getContentId();
								contentId = contentId.replace("<", "");
								contentId = contentId.replace(">", "");
								content = content.replace("src=\"cid:" + contentId + "\"",
										"src=\"/emlpic/" + attachment.getName() + "\"");
							}
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (Exception e) {
			}
		}else{
			MsgParser msgp = new MsgParser();
			try {
				Message msg = msgp.parseMsg(tempFile);
				content = msg.getBodyText();

			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (content == null) {
			resResult.put("content", "");
		} else {
			content = content.replaceAll("<span ([^>]{0,})>", "").replaceAll("</span>","");
			if (!"".equals(keyword) && keyword != null) {
				String[] split = keyword.split(" ");
				for (String string : split) {
					content = content.replace(string,
							"<font style='color: red;background-color: yellow;'>" + string + "</font>");
				}
			}
			if (!"".equals(quickflag)) {
				Pattern pattern = Pattern.compile(quickflag);
				Matcher matcher = pattern.matcher(content);
				while (matcher.find()) {
					String temp = matcher.group(1);
					content = content.replace(temp,
							"<font style='color: red;background-color: yellow;'>" + temp + "</font>");
				}
			}
			if (!StringUtils.isEmpty(orkey)) {
				String[] split = orkey.split(" ");
				for (String string : split) {
					content = content.replace(string,
							"<font style='color: red;background-color: yellow;'>" + string + "</font>");
				}
			}
			if (!StringUtils.isEmpty(andkey)) {
				String[] split = andkey.split(" ");
				for (String string : split) {
					content = content.replace(string,
							"<font style='color: red;background-color: yellow;'>" + string + "</font>");
				}
			}
			// content = content.replaceAll("\r\n", "<br/>");
			// content = content.replaceAll("\n", "<br/>");
			//			 if(!content.startsWith("<html")){
			//			 content = "<pre>"+content+"</pre>";
			//			 }

			//String span = "<span id='d'>bb</span>";
			//String regSpan =  span.replaceAll("<span ([^>]{0,})>", "").replaceAll("</span>","");

			resResult.put("content", content);
		}
		// content = content.replaceAll("\r\n", "<br/>");
		// content = content.replaceAll("\n", "<br/>");
		if (attachmentname == null)
			resResult.put("attfile", "null");
		else {
			if(fname.endsWith(".eml")){
				JSONArray ja = new JSONArray();
				JSONArray ja2 = new JSONArray();
				//			JSONArray ja3 = new JSONArray();
				String[] attfileArray = attachmentname.split("-----");
				for (int k = 0; k < attfileArray.length; k++) {
					try {
						String[] temparr = attfileArray[k].split(":");
						if (temparr.length > 1) {
							ja.add(temparr[1]);
							ja2.add(URLEncoder.encode(temparr[1], "utf-8"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				resResult.put("attfile", ja);
				resResult.put("attfile_encode", ja2);
				//			resResult.put("fujianPic", ja3);
			}
		}
		PrintWriter pw = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			pw = response.getWriter();
			// pw.write(content);
			pw.write("{\"resData\":" + resResult.toString() + "}");
			pw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	/**
	 * 下载邮件附件
	 * @param request
	 * @param response
	 * @param session
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/downAttachment.php")
	public void downAttachment(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		String path = request.getParameter("path");
		String destPathStr = Global.tomcatPath + "/eml/";
		File destPath = new File(destPathStr);
		if (!destPath.exists()) {
			destPath.mkdirs();
		}

		try {
			path = URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String cmd = "hadoop fs -get " + path + " " + destPathStr;
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String fname = "";
		if (!StringUtils.isEmpty(path)) {
			int inx = path.lastIndexOf("/");
			fname = path.substring(inx + 1);
		}

		String name = request.getParameter("name");

		try {
			name = URLDecoder.decode(name, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		File f = new File(name);
		try {
			ReceivedEmail tempMail = EmailUtil.parseEML(new File(destPathStr + fname));
			List<EmailAttachment> attachments = tempMail.getAttachments();
			String fileName = null;
			if (tempMail != null && attachments != null) {
				for (EmailAttachment temp : attachments) {
					fileName = temp.getName();
					if (fileName == null)
						continue;
					if (fileName.contains("<script") || fileName.contains("<style")) {
						String preName = fileName.substring(0, fileName.indexOf("<"));
						String suffix = null;
						if (fileName.contains(".")) {
							suffix = fileName.substring(fileName.lastIndexOf("."));
						} else {
							suffix = ".txt";
						}
						fileName = preName + suffix;
					}
					if (fileName.equals(name)) {
						temp.writeToFile(f);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		String fileName = f.getName();
		try {
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(f.getName().getBytes(request.getCharacterEncoding()), "ISO8859-1");
				// fileName = URLDecoder.decode(fileName, "ISO8859-1");
				response.setHeader("content-disposition", "attachment;filename=\"" + fileName + "\"");
				response.setContentType("application/octet-stream");
			} else if (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0
					|| request.getHeader("USER-AGENT").indexOf("like Gecko") > 0) {
				fileName = URLEncoder.encode(fileName, "utf-8");
				fileName = fileName.replace("+", "%20");// 处理空格变“+”的问题
				response.addHeader("content-disposition", "attachment; filename=" + fileName);
			} else {
				fileName = URLDecoder.decode(fileName, "UTF-8");
				response.addHeader("content-disposition", "attachment; filename=" + fileName);
			}
			InputStream is = new FileInputStream(f);
			OutputStream os = response.getOutputStream();
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1) {
				os.write(buf, 0, len);
			}
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加标记
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/emaiExcavatel/addBiaoJi.php")
	public void addBiaoJi(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		
		String mailESiD = request.getParameter("mailESiD");    //标记索引上的Id  新建时是没用的

		String caseIDs = request.getParameter("caseIDs");
		String caseNames = request.getParameter("caseNames");
		String emailurl = request.getParameter("emailurl");
		String emailTitle = request.getParameter("emailTitle");
		String biaojiContent = request.getParameter("biaojiContent");
		
		String esIDs2 = request.getParameter("esIDs2");     //邮件索引es上的id
		logger.info("email上的esID："+esIDs2);
		
		User user1 = (User) session.getAttribute("user");
		int user1ID = user1.getId();
		String user1IDs = String.valueOf(user1ID);
		System.out.println("user1ID="+user1ID);

		PrintWriter writer = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("caseID", caseIDs);
			map.put("caseName", caseNames);
			map.put("file_download_url", emailurl);
			map.put("content", biaojiContent);
			map.put("emailTitle", emailTitle);
			map.put("emailEsID", esIDs2);
			map.put("userID", user1IDs);

			// 设置标记时间   
			String biaojiTime = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
			
			// 设置标记人
			HttpSession httpSession = request.getSession();
			String biaojiPerson = (String) httpSession.getAttribute("userName");

			User user = new User();
			user.setUsername(biaojiPerson);
			List<User> listfromMysql = sqlDao.getListfromMysql(user);

			String departmentName="";
			if(listfromMysql.size()>0){
				User user2 = listfromMysql.get(0);
				String partment = user2.getPartment();
				Department partment2 = new Department();
				partment2.setId(Integer.parseInt(partment));
				List<Department> listfromMysql2 = sqlDao.getListfromMysql(partment2);
				if(listfromMysql2.size()>0){
					Department Department2 = listfromMysql2.get(0);
					departmentName = Department2.getDepartmentName();
				}
			}

			String sectionName="";
			if(listfromMysql.size()>0){
				User user2 = listfromMysql.get(0);
				String section = user2.getSection();
				//			Department partment2 = new Department();
				Section section2 = new Section();
				section2.setId(Integer.parseInt(section));
				List<Section> listfromMysql2 = sqlDao.getListfromMysql(section2);
				if(listfromMysql2.size()>0){
					Section section3 = listfromMysql2.get(0);
					sectionName = section3.getSectionName();
				}
			}

			Caseinfo caseinfo = new Caseinfo();
			caseinfo.setId(Integer.parseInt(caseIDs));
			List<Caseinfo> cas = sqlDao.getListfromMysql(caseinfo);

			String supName="";
			String supPhone="";
			String supEmail="";
			SuspectInfo suspectInfo =new SuspectInfo();
			if(cas.size()>0){
				Caseinfo cas2 = cas.get(0);
				String MainPartys = cas2.getMainParty();

				String[] split = MainPartys.split(",");
				if(split.length>0){
					for (String caseinfo2 : split) {
						if (!"".equals(caseinfo2) && caseinfo2 !=null) {
							suspectInfo.setId(Integer.parseInt(caseinfo2));
							List<SuspectInfo> listfromMysql2 = sqlDao.getListfromMysql(suspectInfo);	
							if(listfromMysql2.size()>0){
								SuspectInfo suspectInfo2 = listfromMysql2.get(0);
								supName =suspectInfo2.getSuspectName();
								supPhone =suspectInfo2.getSuspectPhone();
								supEmail =suspectInfo2.getSuspectMail();
							}
						}
					}
				}
			}
			
			map.put("saveTime", biaojiTime);
			map.put("name", biaojiPerson);
			map.put("department", departmentName);
			map.put("section", sectionName);
			map.put("suspectName", supName);
			map.put("phone", supPhone);
			map.put("email", supEmail);

			List<Map<String, String>> indexData = new ArrayList<>();
			indexData.add(map);
			EsClient.save("mail", "clue", indexData);
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
	 * 当改邮件被标记过时调用此方法修改es
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/emaiExcavatel/updateBiaoJi2.php")
	public void updateBiaoJi2(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");

		String biaojiContent = request.getParameter("biaojiContent");
		String mailESiD = request.getParameter("mailESiD");
		logger.info("email上的esID："+mailESiD);

		PrintWriter writer = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("content", biaojiContent);
			

			//EsClient.save("mail", "clue", indexData);  
			EsUpdate.updateTest("mail", "clue", mailESiD, map);
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
	 * 标记人页面来的修改标记
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/emaiExcavatel/editBiaoJi.php")
	public void editBiaoJi(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		
		String esID22 = request.getParameter("esID22");
		String editContent = request.getParameter("editContent");
		logger.info("添加的标记文本："+editContent);

		PrintWriter writer = null;
		try {
			Map<String, String> updateMap = new HashMap<>();
			updateMap.put("content", editContent);
			EsUpdate.updateTest("mail", "clue", esID22, updateMap);
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
	 * 标记人页面来的删除标记
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/emaiExcavatel/delectBiaoJi.php")
	public void delectBiaoJi(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		
		String delectEsID22 = request.getParameter("delectEsID22");
		logger.info("删除标记ID："+delectEsID22);
		
		EsClient.getClient().prepareDelete("mail", "clue",delectEsID22)  
                .execute()  
                .actionGet();  

		PrintWriter writer = null;
		try {
			
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
	 * 案件页面来的删除标记
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/emaiExcavatel/delectBiaoJiByCaseName.php")
	public void delectBiaoJiByCaseName(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		
		String esID22ByCaseName = request.getParameter("esID22ByCaseName");
		logger.info("案件删除标记ID："+esID22ByCaseName);
		
		EsClient.getClient().prepareDelete("mail", "clue",esID22ByCaseName)  
                .execute()  
                .actionGet();  

		PrintWriter writer = null;
		try {
			
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
	 * 案件页面来的修改标记
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(value = "/emaiExcavatel/editBiaoJiByCaseName.php")
	public void editBiaoJiByCaseName(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		
		String esID22ByCaseName = request.getParameter("esID22ByCaseName");
		String editContentCase = request.getParameter("editContentCase");
		logger.info("修改的标记id："+esID22ByCaseName);
		logger.info("的标记文本："+editContentCase);

		PrintWriter writer = null;
		try {
			Map<String, String> updateMap = new HashMap<>();
			updateMap.put("content", editContentCase);
			EsUpdate.updateTest("mail", "clue", esID22ByCaseName, updateMap);
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
	 * 邮件工作台-高级搜索-邮箱列表
	 */
	@RequestMapping(value = "/emaiExcavatel/seekEmail.php")
	public void seekEmail(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String caseidStr = request.getParameter("caseidStr");
		String emailAddress = request.getParameter("emailAddress");

		// 默认最新数据的案件
		if (caseidStr == null || "".equals(caseidStr)) {
			caseidStr="-1";
		}
		String[] caseids = { "" };
		if (!"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		// 精确搜索
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
		// caseid集合
		if (!"".equals(caseidStr)) {
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		}
		//邮箱地址
		if(emailAddress!=null && !"".equals(emailAddress)){
			// 组合 模糊查询  should  
			BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
			WildcardQueryBuilder fromWho = QueryBuilders.wildcardQuery("fromWho", "*"+emailAddress+"*");  
			WildcardQueryBuilder toWho = QueryBuilders.wildcardQuery("toWho", "*"+emailAddress+"*");  
			emailstrs.should(fromWho).should(toWho);
			mustQuery.must(emailstrs);
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email");
		searchRequestBuilder.setQuery(mustQuery)
		.setFrom(0)// 分页起始位置（跳过开始的n个）
		.setSize(10);
		// 排序 执行
		SearchResponse searchResponse = searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
				.addSort("date", SortOrder.ASC).execute().actionGet();// 执行搜索
		ArrayList<SearchHit[]> arrayList2 = new ArrayList<SearchHit[]>();
		SearchHit[] hits1 = searchResponse.getHits().getHits();
		arrayList2.add(hits1);
//		long totalHits = searchResponse.getHits().getTotalHits()/10000;
//		for(int i=1;i<=totalHits;i++){
//			searchResponse = searchRequestBuilder.setQuery(mustQuery).setFrom(i*10000)// 分页起始位置（跳过开始的n个）
//					.setSize(10000).execute().actionGet();
//			SearchHit[] hits  = searchResponse.getHits().getHits();
//			arrayList2.add(hits);
//		}

		ArrayList<String> arrayList = new ArrayList<>();
		for (SearchHit[] searchHit2 : arrayList2) {
			for (SearchHit searchHit : searchHit2) {
				Map<String, Object> source = searchHit.getSource();
				String fromWho = (String) source.get("fromWho");
				int flag1=0;
				for (String string2 : arrayList) {
					if(string2.equals(fromWho)){
						flag1=1;
					}
				}
				if(flag1==0){
					if(emailAddress!=null && !"".equals(emailAddress)){
						if( fromWho.indexOf(emailAddress)>0){
							arrayList.add(fromWho);
						}
					}else{
						arrayList.add(fromWho);
					}
				}
				String toWho = (String) source.get("toWho");
				String[] toWhos = toWho.split(";;");
				for (String string : toWhos) {
					int flag2=0;
					for (String string2 : arrayList) {
						if(string2.equals(string)){
							flag2=1;
						}
					}
					if(flag2==0){
						if(emailAddress!=null && !"".equals(emailAddress)){
							if(string.indexOf(emailAddress)>0){
								arrayList.add(string);
							}
						}else{
							arrayList.add(string);
						}
					}
				}
			}
			ArrayList<Map> mapList = new ArrayList<Map>();
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			for (String string : arrayList) {
				int lastIndex=string.length();
				if(string.indexOf("&gt")>0){
					lastIndex=string.lastIndexOf("&gt");
				}
				String yuming = string.substring(string.lastIndexOf("@")+1, lastIndex);
				int j=0;
				Set<String> set = map.keySet();
				for (String key : set) {
					if(key.equals(yuming)){
						String string2=string;
						if(string.indexOf("&gt")<1){
							string2="("+string+")";
						}
						map.get(key).add(string2.replace("&lt;", "(").replace("&gt", ")"));
						j=1;
					}
				}
				if(j==0){
					List<String> list= new ArrayList<String>();
					String string2=string;
					if(string.indexOf("&gt")<1){
						string2="("+string+")";
					}
					list.add(string2.replace("&lt;", "(").replace("&gt", ")"));
					if(yuming!=null && !"".equals(yuming)){
						map.put(yuming, list);
					}
				}
			}
			Set<String> set2 = map.keySet();
			for (String key : set2) {
				Map<String, List<String>> map2 = new HashMap<String, List<String>>();
				List<String> list2= map.get(key);
				list2.add(key);
				map2.put("yuming", list2);
				mapList.add(map2);
			}
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write(JsonUtil.list2json(mapList));
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

	/**
	 * 邮件工作台-高级搜索-邮箱标记
	 */
	@RequestMapping(value = "/emaiExcavatel/signEmail.php")
	public void signEmail(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		
		String pageIndex = request.getParameter("pageIndex");
		String name = request.getParameter("name");

		actionLog((String) session.getAttribute("userName"), "查看", "邮件标记 ");
		// 精确搜索
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
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
		// caseid集合
		mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		//邮箱地址
		if(name!=null && !"".equals(name)){
			// 组合 模糊查询  should  
			BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
			WildcardQueryBuilder name1 = QueryBuilders.wildcardQuery("name", "*"+name+"*");  
			emailstrs.should(name1);
			mustQuery.must(emailstrs);
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("mail").setTypes("clue")
				.setQuery(mustQuery)
				.setFrom((Integer.parseInt(pageIndex) - 1) * 10)// 分页起始位置（跳过开始的n个）
				.setSize(10);
		
		TermsBuilder termsBuildernName = AggregationBuilders.terms("aggs-class").field("name").size(0);//此处也可继续设置order、size等
	    TermsBuilder termsBuildernCaseName = AggregationBuilders.terms("aggs-classNum").field("caseID").size(0);//此处也可继续设置order、size等
	    searchRequestBuilder.addAggregation(termsBuildernName.subAggregation(termsBuildernCaseName));
		// 排序 执行
		SearchResponse searchResponse = searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
				.addSort("saveTime", SortOrder.DESC).execute().actionGet();// 执行搜索
		
		Map<String, Aggregation> aggMap = searchResponse.getAggregations().asMap();
		StringTerms nameTerms = (StringTerms) aggMap.get("aggs-class");
		Iterator<Bucket> nameBucketIt = nameTerms.getBuckets().iterator();

		List<SignDTO> signDTO1 = new ArrayList<SignDTO>();
		
		while(nameBucketIt.hasNext()){
			Bucket nameBucket = nameBucketIt.next();
			//System.out.println(gradeBucket.getKey() + "===姓名1===" + gradeBucket.getDocCount());
			StringTerms caseTerms = (StringTerms) nameBucket.getAggregations().asMap().get("aggs-classNum");
			Iterator<Bucket> classBucketIt = caseTerms.getBuckets().iterator();
			
			while(classBucketIt.hasNext()){
					Bucket caseBucket = classBucketIt.next();
					
					Caseinfo caseinfo = new Caseinfo();
					caseinfo.setId(Integer.parseInt((String) caseBucket.getKey()));
					List<Caseinfo> caseinfos = sqlDao.getListfromMysql(caseinfo);
					String caseName = caseinfos.get(0).getCaseName();
					
					String departmentID = caseinfos.get(0).getDepartment();
					String sectionID = caseinfos.get(0).getSection();
					
					Department department2 = new Department();
					department2.setId(Integer.parseInt(departmentID));
					Department department = sqlDao.getListfromMysql(department2).get(0);
					String departmentName = department.getDepartmentName();
					
					
					Section section = new Section();
					section.setId(Integer.parseInt(sectionID));
					Section section2 = sqlDao.getListfromMysql(section).get(0);
					String sectionName = section2.getSectionName();
					
					SignDTO signDTO = new SignDTO();
					signDTO.setName((String) nameBucket.getKey());
					signDTO.setCaseID(caseName);
					signDTO.setNum((int) caseBucket.getDocCount());
					signDTO.setDepartment(departmentName);
					signDTO.setSection(sectionName);
					signDTO1.add(signDTO);
					
					//System.out.println(nameBucket.getKey() + "--*--" +caseBucket.getKey() + "--*--" + caseBucket.getDocCount());
			}
		}

			for (int i = 0; i < signDTO1.size() - 1; i++) {
				for (int j = 0; j < signDTO1.size() - 1 - i; j++) {
					if (signDTO1.get(j).getNum() < signDTO1.get(j + 1).getNum()) {
						SignDTO strTemp = new SignDTO();
						
						strTemp.setName(signDTO1.get(j).getName());
						strTemp.setCaseID(signDTO1.get(j).getCaseID());
						strTemp.setNum(signDTO1.get(j).getNum());
						
						signDTO1.get(j).setName(signDTO1.get(j + 1).getName());
						signDTO1.get(j).setCaseID(signDTO1.get(j + 1).getCaseID());
						signDTO1.get(j).setNum(signDTO1.get(j + 1).getNum());
						
						signDTO1.get(j + 1).setName(strTemp.getName());
						signDTO1.get(j + 1).setCaseID(strTemp.getCaseID());
						signDTO1.get(j + 1).setNum(strTemp.getNum());
					}
				}
			}
			
		
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			//writer.write(searchResponse.toString());
			writer.write(JsonUtil.list2json(signDTO1));
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
	 * 标记管理-点击案件名查询
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/emaiExcavatel/signEmailByCaseName.php")
	public void signEmailByCaseName(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		
		String pageIndex = request.getParameter("pageIndex");
		String userName = request.getParameter("userName");			//人名
		String caseName = request.getParameter("caseName");      	//案件名

		// 精确搜索 
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
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
		
		mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		
		if(userName!=null && !"".equals(userName)){
			
			BoolQueryBuilder emailstrs1 = QueryBuilders.boolQuery();
			WildcardQueryBuilder name11 = QueryBuilders.wildcardQuery("name", "*"+userName+"*");  
			emailstrs1.should(name11);
			mustQuery.must(emailstrs1);
		} 
		
		if(caseName!=null && !"".equals(caseName)){
			
			BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
			WildcardQueryBuilder name1 = QueryBuilders.wildcardQuery("caseName", "*"+caseName+"*");  
			emailstrs.should(name1);
			mustQuery.must(emailstrs);
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("mail").setTypes("clue")
				.setQuery(mustQuery)
				.setFrom((Integer.parseInt(pageIndex) - 1) * 3)// 分页起始位置（跳过开始的n个）
				.setSize(3);
		
		// 排序 执行
		SearchResponse searchResponse = searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
			.addSort("saveTime", SortOrder.DESC).execute().actionGet();// 执行搜索
		
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(searchResponse.toString());
			//writer.write(JsonUtil.list2json(signDTO1));
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
	 * 数据管理-邮件工作台-查询邮件 收发件人
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/emaiExcavatel/getEmailListtofrom.php")
	public void getEmailListtofrom(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		response.setContentType("textml; charset=UTF-8");

		String caseidStr = request.getParameter("caseidStr");
		String pageIndexstr = request.getParameter("pageIndex");
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String emailkey = request.getParameter("emailkey");//关键词
		String emailstr = request.getParameter("emailstr");//邮箱地址
		String emailstr2 = request.getParameter("emailstr2");//嫌疑人邮箱
		String emailType = request.getParameter("tofromType");//收发类型
		String XYRemail = request.getParameter("XYRemail");//案件下的嫌疑人邮箱
		
		String selectKey = request.getParameter("selectKey");  //收发件分析页面的筛选条件  日期  未读 已读 星标
		
		String[] XYRemails = { "" };
		if (!"".equals(XYRemail) && XYRemail != null) {
			XYRemails = XYRemail.split(",");
		}
		int pageIndex = 1;
		int pageSize = 10;
		if (pageIndexstr != null && !"".equals(pageIndexstr)) {
			pageIndex = Integer.parseInt(pageIndexstr);
		}
		String[] caseids = { "" };
		if (!"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		
		/**
		 * 周武智  加的   
		 */
		String riqi = "";
		String read = "";
		String star = "";
		String fuj = "";
		
		String sortConditon = "";
		if ("未读".equals(selectKey)) {
			read = "0";
			sortConditon = "date";
		} else if ("已读".equals(selectKey)) {
			read = "1";
			sortConditon = "date";
		} else if ("星标".equals(selectKey)) {
			star = "1";
			sortConditon = "date";
		} else if ("日期".equals(selectKey)) {
			sortConditon = "date";
		}  else if ("附件".equals(selectKey)) {
			fuj = "123";
			sortConditon = "date";
		}
		/* 周武智加的  结尾 */
		
		// 精确搜索
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
/*		BoolQueryBuilder xyror = QueryBuilders.boolQuery();
		for (String string : XYRemails) {
			TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", emailstr);//点击的邮箱
			WildcardQueryBuilder toWho = QueryBuilders.wildcardQuery("toWho", "*"+string+"*");//嫌疑人的邮箱
			xyror.must(fromWho).must(toWho);
		}
		mustQuery.must(xyror);*/
		//mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
		if("".equals(emailstr2)||emailstr2==null){//收发件的条件查询
			//邮箱地址
			if(emailstr!=null && !"".equals(emailstr)){
				// 组合 模糊查询  should  
				BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
				TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", emailstr);
				TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", emailstr);
				if("from".equals(emailType)){
					emailstrs.should(fromWho);
					
				}else if("to".equals(emailType)){
					emailstrs.should(toWho);
				}else{
					emailstrs.should(fromWho).should(toWho);
				}
				mustQuery.must(emailstrs);
			}
		}else{//嫌疑人关系图的查询，点击线，查嫌疑人和跟他通信的邮箱
			// 组合 模糊查询  should  
			BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
			TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", emailstr);
			TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", emailstr);
			WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho", "*"+emailstr2+"*");//嫌疑人邮箱
			TermQueryBuilder fromWho2 = QueryBuilders.termQuery("fromWho", emailstr2);//嫌疑人邮箱
			if("to".equals(emailType)){
				emailstrs.must(fromWho).must(toWho2);
			}else if("from".equals(emailType)){
				emailstrs.must(toWho).must(fromWho2);
			}else{
				emailstrs.should(fromWho).should(toWho);
			}
			mustQuery.must(emailstrs);
		}

		// 已读未读状态            周武智加的 
		if (!"".equals(read)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("readFlag", read));
		}
		// 星标状态
		if (!"".equals(star)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("starFlag", star));
		}
		// 附件
		if (!"".equals(fuj)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("attachmentname", selectKey));
		}
		//  新加的结束
		
		// 关键词搜索
		if (emailkey != null && !"".equals(emailkey)) {
//			QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(emailkey)
//					.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
//			mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
			emailkey=emailkey.toLowerCase();
			// 组合 模糊查询  should  
			BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
			//TermQueryBuilder subject = QueryBuilders.termQuery("subject", emailKeyword);
			MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", emailkey);
			MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", emailkey);  
			MatchQueryBuilder fromWho = QueryBuilders.matchPhraseQuery("fromWho", emailkey);
			MatchQueryBuilder toWho = QueryBuilders.matchPhraseQuery("toWho", emailkey);
			MatchQueryBuilder attachmentname = QueryBuilders.matchPhraseQuery("attachmentname", emailkey);
			MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", emailkey);  
			ors.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
			mustQuery.must(ors);
			actionLog((String) session.getAttribute("userName"), "搜索关键词："+emailkey, "邮件挖掘 ");
		}
		// caseid集合
		if (!"".equals(caseidStr)) {
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		}
		else{
			mustQuery.must(QueryBuilders.termsQuery("caseID", ""));
		}
		
				
		// 嫌疑人邮箱名
		if (XYRemail != null && !"".equals(XYRemail)) {
			BoolQueryBuilder ors = QueryBuilders.boolQuery();
			TermsQueryBuilder fromWho = QueryBuilders.termsQuery("fromWho", XYRemails);
			ors.should(fromWho);
			//多个嫌疑人对收件人模糊查询
			for (String string : XYRemails) {
				WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho",  "*"+string+"*");
				ors.should(toWho2);
			}
			mustQuery.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
		}
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			mustQuery.must(rangequerybuilder);
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery).setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize);// 本次返回的文档数量
		// 排序 执行
		SearchResponse searchResponse =  searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
				.addSort("date", SortOrder.ASC).execute().actionGet();// 执行搜索
		
		
		/*SearchResponse searchResponse = null;
		if ("date".equals(sortConditon)) {
			searchResponse = searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
					.addSort(sortConditon, SortOrder.ASC).execute().actionGet();// 执行搜索
		} else {
			searchResponse = searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
					.addSort(sortConditon, SortOrder.ASC).execute().actionGet();// 执行搜索
		}*/
		
		
		SearchHit[] hits = searchResponse.getHits().getHits();
		long totalHits = searchResponse.getHits().getTotalHits();
		//long all = 0;
		//long notRead = 0;
		List<EmailDTO> emailDTOList = new ArrayList<EmailDTO>();
		for (SearchHit searchHit : hits) {
			EmailDTO emailDTO = new EmailDTO();
			String esID = (String) searchHit.getId();
			Map<String, Object> source = searchHit.getSource();
			String subject = (String) source.get("subject");

			String fromWho = (String) source.get("fromWho");
			String toWho = (String) source.get("toWho");
			String downloadUrl = (String) source.get("file_download_url");
			String date = (String) source.get("date");
			String attachmentname = (String) source.get("attachmentname");// LW:附件名
			String content = (String) source.get("content");
			String readFlag = (String) source.get("readFlag");
			String starFlag = (String) source.get("starFlag");
			String ip = (String) source.get("ip");
			String caseName = (String) source.get("caseName");
			String caseID = (String) source.get("caseID");
			//前台高亮显示
			if (emailkey == null) {
				emailkey = "";
			}
			
			content = content.replace(emailkey,
					"<font style='color: red;background-color: yellow;'>" + emailkey + "</font>");
			if(subject.contains(emailkey)){
				subject = subject.replace(emailkey,
						"<font style='color: red;background-color: yellow;'>" + emailkey + "</font>");
			}
			if(toWho.contains(emailkey)){
				toWho = toWho.replace(emailkey,
						"<font style='color: red;background-color: yellow;'>" + emailkey + "</font>");
			}
			if(fromWho.contains(emailkey)){
				fromWho = fromWho.replace(emailkey,
						"<font style='color: red;background-color: yellow;'>" + emailkey + "</font>");
			}
			/*if(attachmentname.contains(emailkey)){
				attachmentname = attachmentname.replace(emailkey,
						"<font style='color: red;background-color: yellow;'>" + emailkey + "</font>");
			}*/
			
			emailDTO.setEsID(esID);
			emailDTO.setRead(Integer.parseInt(readFlag));
			emailDTO.setStar(Integer.parseInt(starFlag));
			emailDTO.setIp(ip);
			emailDTO.setCaseName(caseName);
			emailDTO.setCaseID(caseID);
			emailDTO.setAttachmentname(attachmentname);
			emailDTO.setSubject(subject);
			emailDTO.setFromWho(fromWho);
			emailDTO.setToWho(toWho);
			emailDTO.setContent(content);
			emailDTO.setDate(date);
			emailDTO.setDownloadUrl(downloadUrl);
			emailDTOList.add(emailDTO);
		}
		/*
		 * 查询未读数目
		 */
		// 精确搜索
		BoolQueryBuilder mustQuery2 = QueryBuilders.boolQuery();

		// caseid集合
		if (!"".equals(caseidStr)) {
			mustQuery2.must(QueryBuilders.termsQuery("caseID", caseids));
		}else{
			mustQuery2.must(QueryBuilders.termsQuery("caseID", ""));
		}
		
		
		// 附件   周武智
		if (!"".equals(fuj)) {
			mustQuery2.must(QueryBuilders.matchPhraseQuery("attachmentname", selectKey));
		}
		// 未读
		mustQuery2.must(QueryBuilders.matchPhraseQuery("readFlag", "0"));
		// 星标状态
		if (!"".equals(star)) {
			mustQuery2.must(QueryBuilders.matchPhraseQuery("starFlag", star));
		}
		//邮箱地址
		if("".equals(emailstr2)||emailstr2==null){//收发件的条件查询
			//邮箱地址
			if(emailstr!=null && !"".equals(emailstr)){
				// 组合 模糊查询  should  
				BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
				TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", emailstr);
				TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", emailstr);
				if("from".equals(emailType)){
					emailstrs.should(fromWho);
				}else if("to".equals(emailType)){
					emailstrs.should(toWho);
				}else{
					emailstrs.should(fromWho).should(toWho);
				}
				mustQuery2.must(emailstrs);
			}
		}else{//嫌疑人关系图的查询，点击线，查嫌疑人和跟他通信的邮箱
			// 组合 模糊查询  should  
			BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
			TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", emailstr);
			TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", emailstr);
			WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho", "*"+emailstr2+"*");//嫌疑人邮箱
			TermQueryBuilder fromWho2 = QueryBuilders.termQuery("fromWho", emailstr2);//嫌疑人邮箱
			if("to".equals(emailType)){
				emailstrs.must(fromWho).must(toWho2);
			}else if("from".equals(emailType)){
				emailstrs.must(toWho).must(fromWho2);
			}else{
				emailstrs.should(fromWho).should(toWho);
			}
			mustQuery2.must(emailstrs);
		}
		// 关键词搜索
		if (emailkey != null && !"".equals(emailkey)) {
			// 组合 模糊查询  should  
						BoolQueryBuilder ors2 = QueryBuilders.boolQuery(); 
						//TermQueryBuilder subject = QueryBuilders.termQuery("subject", emailKeyword);
						MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", emailkey);
						MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", emailkey);  
						MatchQueryBuilder fromWho = QueryBuilders.matchPhraseQuery("fromWho", emailkey);
						MatchQueryBuilder toWho = QueryBuilders.matchPhraseQuery("toWho", emailkey);
						MatchQueryBuilder attachmentname = QueryBuilders.matchPhraseQuery("attachmentname", emailkey);
						MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", emailkey);  
						ors2.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
			mustQuery2.must(ors2);// 添加第4条must的条件 关键词全文搜索筛选条件
			//actionLog((String) session.getAttribute("userName"), "搜索关键词："+emailkey, "邮件挖掘 ");
		}
		// 未读
		mustQuery2.must(QueryBuilders.matchPhraseQuery("readFlag", "0"));
		// 嫌疑人邮箱 未读
		if (XYRemail != null && !"".equals(XYRemail)) {
					BoolQueryBuilder ors = QueryBuilders.boolQuery();
					TermsQueryBuilder fromWho = QueryBuilders.termsQuery("fromWho", XYRemails);
					ors.should(fromWho);
					//多个嫌疑人对收件人模糊查询
					for (String string : XYRemails) {
						WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho",  "*"+string+"*");
						ors.should(toWho2);
					}
					
					mustQuery2.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
		}
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder2 = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			mustQuery2.must(rangequerybuilder2);
		}
		SearchRequestBuilder searchRequestBuilder2 = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery2).setFrom(0)// 分页起始位置（跳过开始的n个）
				.setSize(10);// 本次返回的文档数量
		
		// 排序 执行    没按日期  已读 未读  星标 附件搜索前的 
		SearchResponse searchResponse2 = searchRequestBuilder2.addSort("date", SortOrder.ASC)// 排序.addSort(SortBuilders.fieldSort(sortConditon))//按类型排序
				.execute().actionGet();// 执行搜索
		
		long read0 = searchResponse2.getHits().getTotalHits();
		Map<String, Object> map = new HashMap<String, Object>();
		getDomainEmail = emailDTOList;
		map.put("emailDTOList", emailDTOList);
		map.put("count", totalHits);
		map.put("read0",read0);
		
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
	
	
	
	
	//-----------------
	@RequestMapping(value = "/emaiExcavatel/getEmailListtofrom2.php")
	public void getEmailListtofrom2(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		response.setContentType("textml; charset=UTF-8");

		String caseidStr = request.getParameter("caseidStr");
		String pageIndexstr = request.getParameter("pageIndex");
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String emailkey = request.getParameter("emailkey");//关键词
		String emailstr = request.getParameter("emailstr");//邮箱地址
		String emailstr2 = request.getParameter("emailstr2");//嫌疑人邮箱
		String emailType = request.getParameter("tofromType");//收发类型
		String XYRemail = request.getParameter("XYRemail");//案件下的嫌疑人邮箱
		
		String selectKey = request.getParameter("selectKey");  //收发件分析页面的筛选条件  日期  未读 已读 星标
		
		String[] XYRemails = { "" };
		if (!"".equals(XYRemail) && XYRemail != null) {
			XYRemails = XYRemail.split(",");
		}
		int pageIndex = 1;
		int pageSize = 10;
		if (pageIndexstr != null && !"".equals(pageIndexstr)) {
			pageIndex = Integer.parseInt(pageIndexstr);
		}
		String[] caseids = { "" };
		if (!"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		
		/**
		 * 周武智  加的   
		 */
		String riqi = "";
		String read = "";
		String star = "";
		String fuj = "";
		
		String sortConditon = "";
		if ("未读".equals(selectKey)) {
			read = "0";
			sortConditon = "date";
		} else if ("已读".equals(selectKey)) {
			read = "1";
			sortConditon = "date";
		} else if ("星标".equals(selectKey)) {
			star = "1";
			sortConditon = "date";
		} else if ("日期".equals(selectKey)) {
			sortConditon = "date";
		}  else if ("附件".equals(selectKey)) {
			fuj = "123";
			sortConditon = "date";
		}
		/* 周武智加的  结尾 */
		
		// 精确搜索
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		BoolQueryBuilder xyror3 = QueryBuilders.boolQuery();
		if(emailstr!=null && !"".equals(emailstr)){
			if("from".equals(emailType)){
				for (String string : XYRemails) {
					BoolQueryBuilder xyror = QueryBuilders.boolQuery();
					TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", emailstr);//点击的邮箱
					WildcardQueryBuilder toWho = QueryBuilders.wildcardQuery("toWho", string);//嫌疑人的邮箱
					xyror.must(fromWho).must(toWho);
					xyror3.should(xyror);
				}
			}else if("to".equals(emailType)){
				for (String string : XYRemails) {
					BoolQueryBuilder xyror = QueryBuilders.boolQuery();
					TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", string);//点击的邮箱
					WildcardQueryBuilder toWho = QueryBuilders.wildcardQuery("toWho", emailstr);//嫌疑人的邮箱
					xyror.must(fromWho).must(toWho);
					xyror3.should(xyror);
				}
			}
		}
		
		mustQuery.must(xyror3);
		//mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
			//邮箱地址
/*			if(emailstr!=null && !"".equals(emailstr)){
				// 组合 模糊查询  should  
				BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
				TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", emailstr);
				TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", emailstr);
				if("from".equals(emailType)){
					emailstrs.must(fromWho).must();
					
				}else if("to".equals(emailType)){
					emailstrs.should(toWho);
				}else{
					emailstrs.should(fromWho).should(toWho);
				}
				mustQuery.must(emailstrs);
			}*/
		

		// 已读未读状态            周武智加的 
		if (!"".equals(read)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("readFlag", read));
		}
		// 星标状态
		if (!"".equals(star)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("starFlag", star));
		}
		// 附件
		if (!"".equals(fuj)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("attachmentname", selectKey));
		}
		//  新加的结束
		
		// 关键词搜索
		if (emailkey != null && !"".equals(emailkey)) {
//			QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(emailkey)
//					.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
//			mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
			emailkey=emailkey.toLowerCase();
			// 组合 模糊查询  should  
			BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
			//TermQueryBuilder subject = QueryBuilders.termQuery("subject", emailKeyword);
			MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", emailkey);
			MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", emailkey);  
			MatchQueryBuilder fromWho = QueryBuilders.matchPhraseQuery("fromWho", emailkey);
			MatchQueryBuilder toWho = QueryBuilders.matchPhraseQuery("toWho", emailkey);
			MatchQueryBuilder attachmentname = QueryBuilders.matchPhraseQuery("attachmentname", emailkey);
			MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", emailkey);  
			ors.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
			mustQuery.must(ors);
			actionLog((String) session.getAttribute("userName"), "搜索关键词："+emailkey, "邮件挖掘 ");
		}
		// caseid集合
		if (!"".equals(caseidStr)) {
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		}
		else{
			mustQuery.must(QueryBuilders.termsQuery("caseID", ""));
		}
		
				
		/*// 嫌疑人邮箱名
		if (XYRemail != null && !"".equals(XYRemail)) {
			BoolQueryBuilder ors = QueryBuilders.boolQuery();
			TermsQueryBuilder fromWho = QueryBuilders.termsQuery("fromWho", XYRemails);
			ors.should(fromWho);
			//多个嫌疑人对收件人模糊查询
			for (String string : XYRemails) {
				WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho",  "*"+string+"*");
				ors.should(toWho2);
			}
			mustQuery.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
		}*/
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			mustQuery.must(rangequerybuilder);
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery).setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize);// 本次返回的文档数量
		// 排序 执行
		SearchResponse searchResponse =  searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
				.addSort("date", SortOrder.ASC).execute().actionGet();// 执行搜索
		SearchHit[] hits = searchResponse.getHits().getHits();
		long totalHits = searchResponse.getHits().getTotalHits();
		//long all = 0;
		//long notRead = 0;
		List<EmailDTO> emailDTOList = new ArrayList<EmailDTO>();
		for (SearchHit searchHit : hits) {
			EmailDTO emailDTO = new EmailDTO();
			String esID = (String) searchHit.getId();
			Map<String, Object> source = searchHit.getSource();
			String subject = (String) source.get("subject");

			String fromWho = (String) source.get("fromWho");
			String toWho = (String) source.get("toWho");
			String downloadUrl = (String) source.get("file_download_url");
			String date = (String) source.get("date");
			String attachmentname = (String) source.get("attachmentname");// LW:附件名
			String content = (String) source.get("content");
			String readFlag = (String) source.get("readFlag");
			String starFlag = (String) source.get("starFlag");
			String ip = (String) source.get("ip");
			String caseName = (String) source.get("caseName");
			String caseID = (String) source.get("caseID");
			//前台高亮显示
			if (emailkey == null) {
				emailkey = "";
			}
			
			content = content.replace(emailkey,
					"<font style='color: red;background-color: yellow;'>" + emailkey + "</font>");
			if(subject.contains(emailkey)){
				subject = subject.replace(emailkey,
						"<font style='color: red;background-color: yellow;'>" + emailkey + "</font>");
			}
			if(toWho.contains(emailkey)){
				toWho = toWho.replace(emailkey,
						"<font style='color: red;background-color: yellow;'>" + emailkey + "</font>");
			}
			if(fromWho.contains(emailkey)){
				fromWho = fromWho.replace(emailkey,
						"<font style='color: red;background-color: yellow;'>" + emailkey + "</font>");
			}
			/*if(attachmentname.contains(emailkey)){
				attachmentname = attachmentname.replace(emailkey,
						"<font style='color: red;background-color: yellow;'>" + emailkey + "</font>");
			}*/
			
			emailDTO.setEsID(esID);
			emailDTO.setRead(Integer.parseInt(readFlag));
			emailDTO.setStar(Integer.parseInt(starFlag));
			emailDTO.setIp(ip);
			emailDTO.setCaseName(caseName);
			emailDTO.setCaseID(caseID);
			emailDTO.setAttachmentname(attachmentname);
			emailDTO.setSubject(subject);
			emailDTO.setFromWho(fromWho);
			emailDTO.setToWho(toWho);
			emailDTO.setContent(content);
			emailDTO.setDate(date);
			emailDTO.setDownloadUrl(downloadUrl);
			emailDTOList.add(emailDTO);
		}
		/*
		 * 查询未读数目===============================================
		 */
		// 精确搜索
		BoolQueryBuilder mustQuery2 = QueryBuilders.boolQuery();

		// caseid集合
		if (!"".equals(caseidStr)) {
			mustQuery2.must(QueryBuilders.termsQuery("caseID", caseids));
		}else{
			mustQuery2.must(QueryBuilders.termsQuery("caseID", ""));
		}
		
		BoolQueryBuilder xyror2 = QueryBuilders.boolQuery();
		if(emailstr!=null && !"".equals(emailstr)){
			if("from".equals(emailType)){
				for (String string : XYRemails) {
					BoolQueryBuilder xyror = QueryBuilders.boolQuery();
					TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", emailstr);//点击的邮箱
					WildcardQueryBuilder toWho = QueryBuilders.wildcardQuery("toWho", string);//嫌疑人的邮箱
					xyror.must(fromWho).must(toWho);
					xyror2.should(xyror);
				}
			}else if("to".equals(emailType)){
				for (String string : XYRemails) {
					BoolQueryBuilder xyror = QueryBuilders.boolQuery();
					TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", string);//点击的邮箱
					WildcardQueryBuilder toWho = QueryBuilders.wildcardQuery("toWho", emailstr);//嫌疑人的邮箱
					xyror.must(fromWho).must(toWho);
					xyror2.should(xyror);
				}
			}
		}
		mustQuery2.must(xyror2);
		//邮箱地址
	/*	if("".equals(emailstr2)||emailstr2==null){//收发件的条件查询
			//邮箱地址
			if(emailstr!=null && !"".equals(emailstr)){
				// 组合 模糊查询  should  
				BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
				TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", emailstr);
				TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", emailstr);
				if("from".equals(emailType)){
					emailstrs.should(fromWho);
				}else if("to".equals(emailType)){
					emailstrs.should(toWho);
				}else{
					emailstrs.should(fromWho).should(toWho);
				}
				mustQuery2.must(emailstrs);
			}
		}else{//嫌疑人关系图的查询，点击线，查嫌疑人和跟他通信的邮箱
			// 组合 模糊查询  should  
			BoolQueryBuilder emailstrs = QueryBuilders.boolQuery();
			TermQueryBuilder fromWho = QueryBuilders.termQuery("fromWho", emailstr);
			TermQueryBuilder toWho = QueryBuilders.termQuery("toWho", emailstr);
			WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho", "*"+emailstr2+"*");//嫌疑人邮箱
			TermQueryBuilder fromWho2 = QueryBuilders.termQuery("fromWho", emailstr2);//嫌疑人邮箱
			if("to".equals(emailType)){
				emailstrs.must(fromWho).must(toWho2);
			}else if("from".equals(emailType)){
				emailstrs.must(toWho).must(fromWho2);
			}else{
				emailstrs.should(fromWho).should(toWho);
			}
			mustQuery2.must(emailstrs);
		}*/
		// 关键词搜索
		if (emailkey != null && !"".equals(emailkey)) {
			// 组合 模糊查询  should  
						BoolQueryBuilder ors2 = QueryBuilders.boolQuery(); 
						//TermQueryBuilder subject = QueryBuilders.termQuery("subject", emailKeyword);
						MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", emailkey);
						MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", emailkey);  
						MatchQueryBuilder fromWho = QueryBuilders.matchPhraseQuery("fromWho", emailkey);
						MatchQueryBuilder toWho = QueryBuilders.matchPhraseQuery("toWho", emailkey);
						MatchQueryBuilder attachmentname = QueryBuilders.matchPhraseQuery("attachmentname", emailkey);
						MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", emailkey);  
						ors2.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
			mustQuery2.must(ors2);// 添加第4条must的条件 关键词全文搜索筛选条件
			//actionLog((String) session.getAttribute("userName"), "搜索关键词："+emailkey, "邮件挖掘 ");
		}
		// 未读
		mustQuery2.must(QueryBuilders.matchPhraseQuery("readFlag", "0"));
		// 嫌疑人邮箱 未读
/*		if (XYRemail != null && !"".equals(XYRemail)) {
					BoolQueryBuilder ors = QueryBuilders.boolQuery();
					TermsQueryBuilder fromWho = QueryBuilders.termsQuery("fromWho", XYRemails);
					ors.should(fromWho);
					//多个嫌疑人对收件人模糊查询
					for (String string : XYRemails) {
						WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho",  "*"+string+"*");
						ors.should(toWho2);
					}
					
					mustQuery2.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
		}*/
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder2 = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			mustQuery2.must(rangequerybuilder2);
		}
		SearchRequestBuilder searchRequestBuilder2 = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery2).setFrom(0)// 分页起始位置（跳过开始的n个）
				.setSize(10);// 本次返回的文档数量
		
		// 排序 执行    没按日期  已读 未读  星标 附件搜索前的 
		SearchResponse searchResponse2 = searchRequestBuilder2.addSort("date", SortOrder.ASC)// 排序.addSort(SortBuilders.fieldSort(sortConditon))//按类型排序
				.execute().actionGet();// 执行搜索
		
		long read0 = searchResponse2.getHits().getTotalHits();
		Map<String, Object> map = new HashMap<String, Object>();
		getDomainEmail = emailDTOList;
		map.put("emailDTOList", emailDTOList);
		map.put("count", totalHits);
		map.put("read0",read0);
		
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
	
	// 银行卡校验
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")
				|| nonCheckCodeCardId.trim().length() < 15 || nonCheckCodeCardId.trim().length() > 18) {
			// 如果传的数据不合法返回N
			//System.out.println("银行卡号不合法！");
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		// 执行luh算法
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) { // 偶数位处理
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	/**
	 * 数据管理-邮件工作台-快速标记查询数量
	 */
	@RequestMapping(value = "/emaiExcavatel/quickFlagstotal.php")
	public void quickFlagstotal(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String caseidStr = request.getParameter("caseidStr");
		String XYRemail = request.getParameter("XYRemail");
		
		String[] XYRemails = { "" };
		if (!"".equals(XYRemail)&&XYRemail!=null) {
			XYRemails = XYRemail.split(",");
		}
		
		String[] dataArr = new String[] { "手机号","集装箱号","银行卡号","身份证号","邮箱号","车牌号", "固定电话","价格" };
		String quickflag = "";

		// 默认最新数据的案件
		if (caseidStr == null || "".equals(caseidStr)) {
			caseidStr="-1";
		}
		String[] caseids = { "" };//2307 [23007]

		if (!"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		int caseidInt = Integer.valueOf(caseidStr);
		String tableRegexgName = "";
		if(caseidInt%9==0){
			tableRegexgName = "regexfind_0";
		} else if(caseidInt%9==1){
			tableRegexgName = "regexfind_1";
		} else if(caseidInt%9==2){
			tableRegexgName = "regexfind_2";
		} else if(caseidInt%9==3){
			tableRegexgName = "regexfind_3";
		} else if(caseidInt%9==4){
			tableRegexgName = "regexfind_4";
		} else if(caseidInt%9==5){
			tableRegexgName = "regexfind_5";
		} else if(caseidInt%9==6){
			tableRegexgName = "regexfind_6";
		} else if(caseidInt%9==7){
			tableRegexgName = "regexfind_7";
		} else if(caseidInt%9==8){
			tableRegexgName = "regexfind_8";
		}
		long talfori = 0;// 手机号计数
		long jzfori = 0;// 集装箱号计数
		long yhkfori = 0;// 银行卡号计数
		long sfzfori = 0;// 身份证号计数
		long yxfori = 0;// 邮箱号计数
		long cpfori = 0;// 车牌号计数
		long gddhfori = 0;// 固定电话计数
		long jgfori = 0;// 价格计数
		for (int i = 0; i < dataArr.length; i++) {
			if (XYRemail != null && !"".equals(XYRemail)) {
				if ("手机号".equals(dataArr[i])) {
					quickflag = "1";
					talfori = sqlDao.getRegexCount1(caseidStr, quickflag,tableRegexgName,XYRemails);	
				} else if ("固定电话".equals(dataArr[i])) {
					quickflag = "7";
					gddhfori = sqlDao.getRegexCount1(caseidStr, quickflag,tableRegexgName,XYRemails);
				} else if ("身份证号".equals(dataArr[i])) {
					quickflag = "6";
					sfzfori = sqlDao.getRegexCount1(caseidStr, quickflag,tableRegexgName,XYRemails);	
				} else if ("邮箱号".equals(dataArr[i])) {
					quickflag = "4";
					yxfori = sqlDao.getRegexCount1(caseidStr, quickflag,tableRegexgName,XYRemails);	
				} else if ("银行卡号".equals(dataArr[i])) {
					quickflag = "2";
					yhkfori = sqlDao.getRegexCount1(caseidStr, quickflag,tableRegexgName,XYRemails);	
				} else if ("车牌号".equals(dataArr[i])) {
					quickflag = "5";
					cpfori = sqlDao.getRegexCount1(caseidStr, quickflag,tableRegexgName,XYRemails);
				} else if ("集装箱号".equals(dataArr[i])) {
					quickflag = "3";
					jzfori = sqlDao.getRegexCount1(caseidStr, quickflag,tableRegexgName,XYRemails);	
				} else if ("价格".equals(dataArr[i])) {
					quickflag = "8";
					jgfori = sqlDao.getRegexCount1(caseidStr, quickflag,tableRegexgName,XYRemails);
				}
			}else{
				if ("手机号".equals(dataArr[i])) {
					quickflag = "1";
					talfori = sqlDao.getRegexCount(caseidStr, quickflag,tableRegexgName);	
				} else if ("固定电话".equals(dataArr[i])) {
					quickflag = "7";
					gddhfori = sqlDao.getRegexCount(caseidStr, quickflag,tableRegexgName);
				} else if ("身份证号".equals(dataArr[i])) {
					quickflag = "6";
					sfzfori = sqlDao.getRegexCount(caseidStr, quickflag,tableRegexgName);	
				} else if ("邮箱号".equals(dataArr[i])) {
					quickflag = "4";
					yxfori = sqlDao.getRegexCount(caseidStr, quickflag,tableRegexgName);	
				} else if ("银行卡号".equals(dataArr[i])) {
					quickflag = "2";
					yhkfori = sqlDao.getRegexCount(caseidStr, quickflag,tableRegexgName);	
				} else if ("车牌号".equals(dataArr[i])) {
					quickflag = "5";
					cpfori = sqlDao.getRegexCount(caseidStr, quickflag,tableRegexgName);
				} else if ("集装箱号".equals(dataArr[i])) {
					quickflag = "3";
					jzfori = sqlDao.getRegexCount(caseidStr, quickflag,tableRegexgName);	
				} else if ("价格".equals(dataArr[i])) {
					quickflag = "8";
					jgfori = sqlDao.getRegexCount(caseidStr, quickflag,tableRegexgName);
				}
			}
			
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("talfori", talfori);
		map.put("jzfori", jzfori);
		map.put("yhkfori", yhkfori);
		map.put("sfzfori", sfzfori);
		map.put("yxfori", yxfori);
		map.put("cpfori", cpfori);
		map.put("gddhfori", gddhfori);
		map.put("jgfori", jgfori);
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
	
	//标记前查一遍是否有
		@RequestMapping(value = "/admin/editBiaojiOnEmail.php")
		public void editBiaoji(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
			response.setContentType("textml; charset=UTF-8");

			String esId23 = request.getParameter("esId23");
			System.out.println("emailEsID="+esId23);
			
			BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
			
			if (!"".equals(esId23)) {
				mustQuery.must(QueryBuilders.matchPhraseQuery("emailEsID", esId23));
			}
			
			SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("mail").setTypes("clue");

			SearchResponse searchResponse = searchRequestBuilder.setQuery(mustQuery)
//					.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
//					.setSize(pageSize)// 本次返回的文档数量
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

	// 邮件工作台 域名分析
	@RequestMapping(value = "/admin/getEmail_domain.php")
	public void getEmail_domain(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String caseidStr = request.getParameter("caseidStr");
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间
		String XYRemail =request.getParameter("XYRemail");//获取案件下嫌疑人的邮箱
		String emlType = request.getParameter("emlType");//嫌疑人收发件箱

		System.out.println("XYRemail: " + XYRemail);
		System.out.println("emlType: " + emlType);
		String[] XYRemails = { "" };
		if (!"".equals(XYRemail)&&XYRemail!=null) {
			XYRemails = XYRemail.split(",");
		}
		/*
		 * if(caseidStr== null || "".equals(caseidStr)){ caseidStr=""; }
		 * String[] caseids = {""}; if(!"".equals(caseidStr)){
		 * caseids=caseidStr.split(" "); }
		 */

		/*
		 * YC_TODO: 2017/9/12 邮件挖掘_域名分析的域名分析结果展示根据最新的案件展示
		 */
		if (caseidStr == null || "".equals(caseidStr)) {
			/*Evidence evidence = new Evidence();
			evidence.setIndexFlag(1);
			evidence.setEvType(1);
			List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
			if (listfromMysql.size() > 0) {
				Evidence evidence3 = listfromMysql.get(listfromMysql.size() - 1);
				int caseid = evidence3.getCaseID();
				caseidStr = "" + caseid;
			}*/
			caseidStr="-1";
		}

		String[] caseids = { "" };
		if (!"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}

		// 精确搜索
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
		// caseid集合
		if (!"".equals(caseidStr)) {
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		}

		// 嫌疑人邮箱 全部
		if (XYRemail != null && !"".equals(XYRemail)) {
			BoolQueryBuilder ors = QueryBuilders.boolQuery();
			if(emlType.equals("收件箱")){
				//多个嫌疑人对收件人模糊查询
				for (String string : XYRemails) {
					WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho",  "*"+string+"*");
					ors.should(toWho2);
				}
			}else if(emlType.equals("发件箱")){
				TermsQueryBuilder fromWho = QueryBuilders.termsQuery("fromWho", XYRemails);
				ors.should(fromWho);
			}
			mustQuery.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
		}
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			// System.out.println("时间分割");
			mustQuery.must(rangequerybuilder);
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery);
		long totalHits = searchRequestBuilder.setFrom(0).setSize(-1).execute().actionGet().getHits().getTotalHits();
		int getCount = (int)totalHits;
		SearchResponse searchResponse = searchRequestBuilder.setFrom(0).setSize(getCount).execute().actionGet();// 执行搜索
		ArrayList<SearchHit[]> arrayList = new ArrayList<SearchHit[]>();
		SearchHit[] hits1 = searchResponse.getHits().getHits();
		arrayList.add(hits1);
//		long totalHits = searchResponse.getHits().getTotalHits();
		System.out.println("总数： " + totalHits);
//		for(int i=1;i<=totalHits;i++){
//			searchResponse = searchRequestBuilder.setQuery(mustQuery).setFrom(i*10000)// 分页起始位置（跳过开始的n个）
//					.setSize(10000).execute().actionGet();
//			SearchHit[] hits  = searchResponse.getHits().getHits();
//			arrayList.add(hits);
//		}
		List<EmailDTO> emailDTOList = new ArrayList<EmailDTO>();
		String getDomainName = "";
		System.out.println("大小： " + arrayList.get(0).length);
		for (SearchHit[] searchHit2 : arrayList) {
			for (SearchHit searchHit : searchHit2) {
				Map<String, Object> source = searchHit.getSource();
				String fromWho = (String) source.get("fromWho");
				String toWho = (String) source.get("toWho");
				getDomainName += fromWho + toWho;
			}
		}
		// 测试截取含有域名的字符串
		String dn = "com|cn|org|com.cn|xyz|net|gg|gov.cn|love";
		Pattern p = Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.(" + dn + ")"); // 邮箱验证
		Matcher m = p.matcher(getDomainName);
		List<String> emailList = new ArrayList<>();
		while (m.find()) {
			String[] testStr = m.group().split("@");
			// 去除包涵连续两个点的邮箱
			if (!m.group().contains("..")) {
				// emailList.add(m.group());
				emailList.add(testStr[1]);
			}
		}

		/*
		 * String[] strData = getDomainName.split("@"); List<String> getStr =
		 * new ArrayList<>();
		 */
		List<DomainNameInfo> beanStr = new ArrayList<DomainNameInfo>();
		/*
		 * for (int i = 1; i < strData.length; i++) { String[] strData2 =
		 * strData[i].split("\\."); String str2 = strData2[0]; getStr.add(str2);
		 * }
		 */
		for (String string : emailList) {
			if (beanStr.size() > 0) {
				int flag = 0;
				for (int i = 0; i < beanStr.size(); i++) {
					if (string.equals(beanStr.get(i).getType())) {
						beanStr.get(i).setNum(beanStr.get(i).getNum() + 1);
						flag = 1;
					}
				}
				if (flag == 0) {
					DomainNameInfo str = new DomainNameInfo();
					str.setType(string);
					str.setNum(1);
					beanStr.add(str);
				}
			} else {
				DomainNameInfo str = new DomainNameInfo();
				str.setType(string);
				str.setNum(1);
				beanStr.add(str);
			}
		}
		for (int i = 0; i < beanStr.size() - 1; i++) {
			for (int j = 0; j < beanStr.size() - 1 - i; j++) {
				if (beanStr.get(j).getNum() < beanStr.get(j + 1).getNum()) {
					DomainNameInfo strTemp = new DomainNameInfo();
					strTemp.setType(beanStr.get(j).getType());
					strTemp.setNum(beanStr.get(j).getNum());
					beanStr.get(j).setType(beanStr.get(j + 1).getType());
					beanStr.get(j).setNum(beanStr.get(j + 1).getNum());
					beanStr.get(j + 1).setType(strTemp.getType());
					beanStr.get(j + 1).setNum(strTemp.getNum());
				}
			}
		}
		getBeanStr = beanStr;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", totalHits);
		map.put("beanStr", beanStr);
		map.put("totalHits", totalHits);
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

	// 域名分析 导出功能
	@RequestMapping("/ExportDomainMailWorkSpace.php")
	public void getDomainMailInfo(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								  HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String filename = "data";
		String projectpath = request.getSession().getServletContext().getRealPath("");
		try {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			String path = projectpath + filename + ".xls";
			File file = new File(path);
			createDomainMail(path);
			fs = new POIFSFileSystem(new FileInputStream(path));
			wb = new HSSFWorkbook(fs);
			// System.out.println("测试数据！");

			for (int i = 0; i < getBeanStr.size(); i++) {
				writeDomainMail(getBeanStr.get(i), wb, path);
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

	public void createDomainMail(String path) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);

		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);

		// 给Excel的单元格设置样式和赋值
		cell.setCellValue("域名类型");
		cell = row.createCell(1);
		cell.setCellValue("命中次数");

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	public static void writeDomainMail(DomainNameInfo bean, HSSFWorkbook wb, String path) throws Exception {
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
		cell.setCellValue(bean.getType());
		cell = row1.createCell(1);
		cell.setCellValue(bean.getNum() + "次");

		FileOutputStream os = new FileOutputStream(path);
		// FileOutputStream os = new FileOutputStream("c:\\spider\\tex.xls");
		wb.write(os);
		os.close();
	}

	// 邮件工作台 域名分析 IP _one分布图
	@RequestMapping(value = "/admin/getEmail_getOnlyOneIpAddress.php")
	public void getEmail_getOnlyOneIpAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		response.setContentType("textml; charset=UTF-8");

		String getIp = request.getParameter("ip");


		List<String> getIpAddress = new ArrayList<String>();
		ip_Sqldao=new ip_SqlDAO();
		java.sql.Connection conn=ip_Sqldao.getConnection();
		ResultSet rs=null;


		//String sql="SELECT * FROM ipoff WHERE minip<=INET_ATON('"+getIp+"') ORDER BY minip DESC LIMIT 1";
		PreparedStatement st1=conn.prepareStatement("SELECT * FROM ipoff WHERE minip<=INET_ATON('"+getIp+"') ORDER BY minip DESC LIMIT 1");
		st1.execute();
		rs=st1.executeQuery();
		Ipoffline ipoffline=new Ipoffline();
		if(rs!=null){
			while(rs.next()){
				if(isExistColumn(rs,"id")){
					ipoffline.setId(rs.getInt("id"));
					ipoffline.setMinip(rs.getLong("minip"));
					ipoffline.setMaxip(rs.getLong("maxip"));
					ipoffline.setContinent(rs.getString("continent"));
					ipoffline.setAreacode(rs.getString("areacode"));
					ipoffline.setCountry(rs.getString("country"));
					ipoffline.setMultiarea(rs.getString("multiarea"));
					ipoffline.setUser(rs.getString("user"));
				}
				break;
			}
		}
		/*	            通过输出continent判断是否是公有ip，如果continent="局域网IP"，则multiarea中不可获得经纬度。
	            System.out.println("*********multiarea"+ipoffline.getMultiarea());*/
		if(ipoffline.getMultiarea()!=null&&!"".equals(ipoffline.getMultiarea())){
			String[] splits=ipoffline.getMultiarea().split(",|\\:");
			String lat=splits[1].replace("\"", "");
			String lon=splits[3].replace("\"", "");
			String address=lat+"-"+lon;
			getIpAddress.add(address);
		}

		//处理读取离线地图包数据
		Set set = new HashSet();
		List<String> getIpAddressNew = new ArrayList<String>();
		for (String getAddresslists : getIpAddress) {
			if (set.add(getAddresslists)) {
				getIpAddressNew.add(getAddresslists);
			}
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(getIpAddressNew));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}

	// 邮件工作台 域名分析 IP分布图
	@RequestMapping(value = "/admin/getEmail_getIpAddress.php")
	public void getEmail_getIpAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		response.setContentType("textml; charset=UTF-8");
		// 分页
		int pageIndex = 1;// 页数
		int pageSize = 5;// 每页个数

		// 按条件查询
		// String sortType = (String) request.getParameter("sortType");// 排序类型
		String pageno = request.getParameter("pageno");
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}

		String sortType = request.getParameter("sortType");
		String caseidStr = request.getParameter("caseidStr");
		String emailKeyword = request.getParameter("emailKeyword");
		String startDate = request.getParameter("startDate");// 开始时间
		String endDate = request.getParameter("endDate");// 结束时间

		// 默认最新数据的案件
		if (caseidStr == null || "".equals(caseidStr)) {
			/*Evidence evidence = new Evidence();
			evidence.setIndexFlag(1);
			evidence.setEvType(1);
			List<Evidence> listfromMysql = sqlDao.getListfromMysql(evidence);
			actionLog((String) session.getAttribute("userName"), "查看", "邮件挖掘 ");
			if (listfromMysql.size() > 0) {
				Evidence evidence3 = listfromMysql.get(listfromMysql.size() - 1);
				int caseid = evidence3.getCaseID();
				caseidStr = "" + caseid;
			}*/
			caseidStr="-1";
		}
		String[] caseids = { "" };
		if (!"".equals(caseidStr)) {
			caseids = caseidStr.split(" ");
		}
		String riqi = "";
		String read = "";
		String star = "";
		String fuj = "";
		String sortConditon = "";
		if ("未读".equals(sortType)) {
			read = "0";
			sortConditon = "date";
		} else if ("已读".equals(sortType)) {
			read = "1";
			sortConditon = "date";
		} else if ("星标".equals(sortType)) {
			star = "1";
			sortConditon = "date";
		} else if ("日期".equals(sortType)) {
			sortConditon = "date";
		} else if ("收件人".equals(sortType)) {
			sortConditon = "toWho";
		} else if ("发件人".equals(sortType)) {
			sortConditon = "fromWho";
		} else if ("IP".equals(sortType)) {
			sortConditon = "ip";
		} else if ("附件".equals(sortType)) {
			fuj = "123";
			sortConditon = "date";
		}

		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();

		mustQuery.must(QueryBuilders.matchAllQuery()); // 全匹配
		// 添加第1条must的条件 	caseid集合
		if (!"".equals(caseidStr)) {
			mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
		}
		// 已读未读状态
		if (!"".equals(read)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("readFlag", read));
			actionLog((String) session.getAttribute("userName"), "搜索邮件状态", "邮件挖掘 ");
		}
		// 星标状态
		if (!"".equals(star)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("starFlag", star));
			actionLog((String) session.getAttribute("userName"), "搜索星标", "邮件挖掘 ");
		}
		// 附件
		if (!"".equals(fuj)) {
			mustQuery.must(QueryBuilders.matchPhraseQuery("attachmentname", sortType));
			actionLog((String) session.getAttribute("userName"), "搜索附件", "邮件挖掘 ");
		}
		// 关键词搜索
		if (emailKeyword != null && !"".equals(emailKeyword)) {
			QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(emailKeyword)
					.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
			mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
			actionLog((String) session.getAttribute("userName"), "搜索关键词："+emailKeyword, "邮件挖掘 ");
		}
		// 日期范围
		if (startDate != null && !"".equals(startDate)) {
			RangeQueryBuilder rangequerybuilder = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
					.to(endDate + " 23:59:59");
			mustQuery.must(rangequerybuilder);
		}
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email")
				.setQuery(mustQuery).setFrom(0)// 分页起始位置（跳过开始的n个）
				.setSize(10);// 本次返回的文档数量
		// 排序 执行
		SearchResponse searchResponse = null;
		if ("date".equals(sortConditon)) {
			searchResponse = searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
					.addSort(sortConditon, SortOrder.ASC).execute().actionGet();// 执行搜索
		} else {
			searchResponse = searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
					.addSort(sortConditon, SortOrder.ASC).execute().actionGet();// 执行搜索
		}

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
		String getDomainName = null;
		List<String> getIpAddress = new ArrayList<String>();
		ip_Sqldao=new ip_SqlDAO();
		java.sql.Connection conn=ip_Sqldao.getConnection();
		ResultSet rs=null;
		for (SearchHit[] searchHit2 : arrayList) {
			for (SearchHit searchHit : searchHit2) {
				Map<String, Object> source = searchHit.getSource();
				String getIp = (String) source.get("ip");


				//String sql="SELECT * FROM ipoff WHERE minip<=INET_ATON('"+getIp+"') ORDER BY minip DESC LIMIT 1";
				PreparedStatement st1=conn.prepareStatement("SELECT * FROM ipoff WHERE minip<=INET_ATON('"+getIp+"') ORDER BY minip DESC LIMIT 1");
				st1.execute();
				rs=st1.executeQuery();
				Ipoffline ipoffline=new Ipoffline();
				if(rs!=null){
					while(rs.next()){
						if(isExistColumn(rs,"id")){
							ipoffline.setId(rs.getInt("id"));
							ipoffline.setMinip(rs.getLong("minip"));
							ipoffline.setMaxip(rs.getLong("maxip"));
							ipoffline.setContinent(rs.getString("continent"));
							ipoffline.setAreacode(rs.getString("areacode"));
							ipoffline.setCountry(rs.getString("country"));
							ipoffline.setMultiarea(rs.getString("multiarea"));
							ipoffline.setUser(rs.getString("user"));
						}
						break;
					}
				}
				/*	            通过输出continent判断是否是公有ip，如果continent="局域网IP"，则multiarea中不可获得经纬度。
	            System.out.println("*********multiarea"+ipoffline.getMultiarea());*/
				if(ipoffline.getMultiarea()!=null&&!"".equals(ipoffline.getMultiarea())){
					String[] splits=ipoffline.getMultiarea().split(",|\\:");
					String lat=splits[1].replace("\"", "");
					String lon=splits[3].replace("\"", "");
					String address=lat+"-"+lon;
					getIpAddress.add(address);
				}
			}
			//处理读取离线地图包数据
			Set set = new HashSet();
			List<String> getIpAddressNew = new ArrayList<String>();
			for (String getAddresslists : getIpAddress) {
				if (set.add(getAddresslists)) {
					getIpAddressNew.add(getAddresslists);
				}
			}
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write(JsonUtil.list2json(getIpAddressNew));
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

}