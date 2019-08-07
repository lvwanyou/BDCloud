package com.xl.cloud.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.jdbc.Statement;
import com.xl.cloud.bean.Caseinfo;
import com.xl.cloud.bean.Cellinfo_v2;
import com.xl.cloud.bean.Coordinatelog;
import com.xl.cloud.bean.HackCount;
import com.xl.cloud.bean.HackerDBCount;
import com.xl.cloud.bean.Ipoffline;
import com.xl.cloud.bean.allesDTO;
import com.xl.cloud.dao.BaseIDSqlDao;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.dao.ip_SqlDAO;
import com.xl.cloud.util.EsClient;
import com.xl.cloud.util.JsonUtil;
import com.xl.cloud.util.RWProperties;

import oracle.net.aso.k;

@Controller
public class HackerSearch {

	private SqlDao sqlDao = new SqlDao();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static  ip_SqlDAO ip_Sqldao;
	private static boolean isConnNet=false;//是否联网标志
	
	private BaseIDSqlDao baseDao = new BaseIDSqlDao();
	final Logger logger = Logger.getLogger(BuildCollection.class);
	static{
		//读取配置文件地址
		String configPath = HackerSearch.class.getResource("/").getPath()+"ConnNet.config";
		System.out.println(configPath);
		//System.out.println(HOST);
		String isConn = RWProperties.getProperty("isConnNet", configPath);
		if(!StringUtils.isEmpty(isConn)){
			if(isConn.equals("1"))
					isConnNet=true;
			else
				isConnNet=false;
		}
		System.out.println("isConnNet-----"+isConnNet);
	}



	 public HackerSearch() throws Exception {
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
			if (rs.findColumn(columnName) > 0 ) {  
				return true;  
			}   
		}  
		catch (SQLException e) {  
			return false;  
		} 			       
		return false;  
	} 
	
	@RequestMapping(value = "/hackerSearchResults.php")
	public String hackerSearchResults(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map<String, Object> map) throws IOException, InterruptedException {
		String big_search_box = request.getParameter("big_search_box");
		String typess = request.getParameter("typess");
		
		String lac = request.getParameter("LAC");
		String cid = request.getParameter("CID");
		
		session.setAttribute("typess", typess);
		session.setAttribute("big_search_boxx", big_search_box);
		
		session.setAttribute("lac", lac);
		session.setAttribute("cid", cid);
		
		return "hackerSearchResults";
	}


	// 查询黑客数据库
	@RequestMapping(value = "/hackerSearchGHDB.php")
	public void hackerSearchGHDB(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setContentType("textml; charset=UTF-8");
		String big_search_box = request.getParameter("big_search_box");
		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
		
		List<String> temp = new ArrayList<String>();
		temp.add("");
		temp.add(null);
       /**
        * 多个字段 field 之间 查询，如过字段查询之间是或的关系，用should; 
        */
		//搜索or空格多条件
				if(big_search_box!=null && !"".equals(big_search_box)){
					String[] orKeylist = big_search_box.split(" ");
					// 组合 模糊查询  should  
					BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
					for (String string : orKeylist) {
						MatchQueryBuilder email = QueryBuilders.matchPhraseQuery("email", "*"+string+"*");  
						MatchQueryBuilder qqNum = QueryBuilders.matchPhraseQuery("qqNum", "*"+string+"*");  
						MatchQueryBuilder dataSource = QueryBuilders.matchPhraseQuery("dataSource", "*"+string+"*");  
						MatchQueryBuilder qunNum = QueryBuilders.matchPhraseQuery("qunNum", "*"+string+"*"); 
						MatchQueryBuilder qunText = QueryBuilders.matchPhraseQuery("qunText", "*"+string+"*");  
						MatchQueryBuilder ID = QueryBuilders.matchPhraseQuery("ID", "*"+string+"*"); 
						MatchQueryBuilder realName = QueryBuilders.matchPhraseQuery("realName", "*"+string+"*");  
						MatchQueryBuilder telephone = QueryBuilders.matchPhraseQuery("telephone", "*"+string+"*"); 
						ors.should(email).should(qqNum).should(dataSource).should(qunNum).should(qunText).should(ID).should(realName).should(telephone);
					}
						mustQuery.must(ors);
				}
		/*//qq
	    mustQuery.should(QueryBuilders.matchPhraseQuery("email", big_search_box));
	    mustQuery.should(QueryBuilders.matchPhraseQuery("qqNum", big_search_box));
	    mustQuery.should(QueryBuilders.matchPhraseQuery("dataSource", big_search_box));
	    mustQuery.should(QueryBuilders.matchPhraseQuery("qunNum", big_search_box));
	    mustQuery.should(QueryBuilders.matchPhraseQuery("qunText", big_search_box));
	    
	    //jingdong 
	    mustQuery.should(QueryBuilders.matchPhraseQuery("ID", big_search_box));
	    mustQuery.should(QueryBuilders.matchPhraseQuery("realName", big_search_box));
	    mustQuery.should(QueryBuilders.matchPhraseQuery("telephone", big_search_box));*/
	    
		SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("hackerdb").setTypes("hkdb")
				.setQuery(mustQuery).addHighlightedField("*")/* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)// 配置高亮显示搜索结果
				.setHighlighterPreTags("<font color='red'>").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果
	
		int pageIndex = 1;// 页数
		int pageSize = 8;// 每页
		String pageno = request.getParameter("pageno");
		if (!StringUtils.isEmpty(pageno)) {
			pageIndex = Integer.parseInt(pageno);
		}
		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageIndex - 1) * pageSize)// 分页起始位置（跳过开始的n个）
				.setSize(pageSize)// 本次返回的文档数量
				.execute().actionGet();// 执行搜索
		SearchHit[] hitss = searchResponse.getHits().getHits();
		List<allesDTO> allesDTOList = new ArrayList<allesDTO>();
		
		for (SearchHit searchHit : hitss) {
			Map<String, Object> source = searchHit.getSource();
			allesDTO allesDTO = new allesDTO();
			//System.out.println("************"+searchHit);
			//黑客数据库
			String email = (String) source.get("email");//  为 或匹配关系。
			String dataSource = (String) source.get("dataSource");//

			//逻辑判断：为QQ数据
			if(dataSource.equals("QQ")){
				/**qq 信息*/
			String nick=(String) source.get("nick");
			String gender=(String) source.get("gender");
			String qqNum = (String) source.get("qqNum");//
			String qqPasswd = (String) source.get("qqPasswd");
			String emailPasswd = (String) source.get("emailPasswd");
			String qunNum=(String) source.get("qunNum");//
			String createDate=(String) source.get("createDate");
			String title=(String) source.get("title");
			String qunText=(String) source.get("qunText");//
			
			Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

			//qq data 
			allesDTO.setNick(nick);
			allesDTO.setGender(gender);
			allesDTO.setQqPasswd(qqPasswd);
			allesDTO.setEmailPasswd(emailPasswd);
			allesDTO.setCreateDate(createDate);
			allesDTO.setTitle(title);

			if(email!=null||"".equals(email)){
				if(email.equals(big_search_box)){
					allesDTO.setEmail(highlightFields.get("email").getFragments()[0].toString());
				}else{
					allesDTO.setEmail(email);
				}
			}
			if(qqNum!=null||"".equals(qqNum)){
				if(qqNum.equals(big_search_box)){
					allesDTO.setQqNum(highlightFields.get("qqNum").getFragments()[0].toString());
				}else{
					allesDTO.setQqNum(qqNum);
				}
			}
			if(dataSource!=null||"".equals(dataSource)){
				if(dataSource.equals(big_search_box)){
					allesDTO.setDataSource(highlightFields.get("dataSource").getFragments()[0].toString());
				}else{
					allesDTO.setDataSource(dataSource);
				}
			}
			if(qunNum!=null||"".equals(qunNum)){
				if(qunNum.equals(big_search_box)){
					allesDTO.setQunNum(highlightFields.get("qunNum").getFragments()[0].toString());
				}else{
					allesDTO.setQunNum(qunNum);
				}
			}
			if(qunText!=null||"".equals(qunText)){
				if(qunText.equals(big_search_box)){
					allesDTO.setQunText(highlightFields.get("qunText").getFragments()[0].toString());
				}else{
					allesDTO.setQunText(qunText);
				}
			}
		//	System.out.println("********1"+highlightFields.get("email").getFragments()[0].toString());
			//System.out.println("********2"+highlightFields.get("qqNum").getFragments()[0].toString());
			//System.out.println("********3"+highlightFields.get("dataSource").getFragments()[0].toString());
			//System.out.println("********4"+highlightFields.get("qunNum").getFragments()[0].toString());
			//System.out.println("********5"+highlightFields.get("qunText").getFragments()[0].toString());
			//allesDTO.setQunNum(highlightFields.get("qunNum").getFragments()[0].toString());
	
		
			allesDTOList.add(allesDTO);
			}else if(dataSource.equals("JD")){
				/**jd 信息*/
				String ID=(String)source.get("ID");
				String nick=(String) source.get("userName");
				String qqNum = (String) source.get("qqNum");//
				String emailPasswd = (String) source.get("emailPasswd");
				String createDate=(String) source.get("createDate");
				String telephone=(String) source.get("telephone");
				String realName=(String) source.get("realName");//
				
				Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

				//jd data
				allesDTO.setNick(nick);
				allesDTO.setEmailPasswd(emailPasswd);
				allesDTO.setCreateDate(createDate);
								
				if(telephone!=null||"".equals(telephone)){
					if(telephone.equals(big_search_box)){
						allesDTO.setTelephone(highlightFields.get("telephone").getFragments()[0].toString());
					}else{
						allesDTO.setTelephone(telephone);
					}
				}
				if(email!=null||"".equals(email)){
					if(email.equals(big_search_box)){
						allesDTO.setEmail(highlightFields.get("email").getFragments()[0].toString());
					}else{
						allesDTO.setEmail(email);
					}
				}
				if(qqNum!=null||"".equals(qqNum)){
					if(qqNum.equals(big_search_box)){
						allesDTO.setQqNum(highlightFields.get("qqNum").getFragments()[0].toString());
					}else{
						allesDTO.setQqNum(qqNum);
					}
				}
				if(dataSource!=null||"".equals(dataSource)){
					if(dataSource.equals(big_search_box)){
						allesDTO.setDataSource(highlightFields.get("dataSource").getFragments()[0].toString());
					}else{
						allesDTO.setDataSource(dataSource);
					}
				}
				if(realName!=null||"".equals(realName)){
					if(realName.equals(big_search_box)){
						allesDTO.setRealName(highlightFields.get("realName").getFragments()[0].toString());
					}else{
						allesDTO.setRealName(realName);
					}
				}
				if(ID!=null||"".equals(ID)){
					if(ID.equals(big_search_box)){
						allesDTO.setID(highlightFields.get("ID").getFragments()[0].toString());
					}else{
						allesDTO.setID(ID);
					}
				}

				allesDTOList.add(allesDTO);
			}else if(dataSource.equals("网易")){
				/**网易 信息*/
				String ID=(String)source.get("网易");
				//String nick=(String) source.get("userName");
				//String qqNum = (String) source.get("qqNum");//
				String emailPasswd = (String) source.get("emailPasswd");
				//String createDate=(String) source.get("createDate");
				//String telephone=(String) source.get("telephone");
				//String realName=(String) source.get("realName");//
				
				Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

				//网易 data
				//allesDTO.setNick(nick);
				allesDTO.setEmailPasswd(emailPasswd);
				//allesDTO.setCreateDate(createDate);
								
			/*	if(telephone!=null||"".equals(telephone)){
					if(telephone.equals(big_search_box)){
						allesDTO.setTelephone(highlightFields.get("telephone").getFragments()[0].toString());
					}else{
						allesDTO.setTelephone(telephone);
					}
				}*/
				if(email!=null||"".equals(email)){
					if(email.equals(big_search_box)){
						allesDTO.setEmail(highlightFields.get("email").getFragments()[0].toString());
					}else{
						allesDTO.setEmail(email);
					}
				}
				/*if(qqNum!=null||"".equals(qqNum)){
					if(qqNum.equals(big_search_box)){
						allesDTO.setQqNum(highlightFields.get("qqNum").getFragments()[0].toString());
					}else{
						allesDTO.setQqNum(qqNum);
					}
				}*/
				if(dataSource!=null||"".equals(dataSource)){
					if(dataSource.equals(big_search_box)){
						allesDTO.setDataSource(highlightFields.get("dataSource").getFragments()[0].toString());
					}else{
						allesDTO.setDataSource(dataSource);
					}
				}
				/*if(realName!=null||"".equals(realName)){
					if(realName.equals(big_search_box)){
						allesDTO.setRealName(highlightFields.get("realName").getFragments()[0].toString());
					}else{
						allesDTO.setRealName(realName);
					}
				}*/
				if(ID!=null||"".equals(ID)){
					if(ID.equals(big_search_box)){
						allesDTO.setID(highlightFields.get("ID").getFragments()[0].toString());
					}else{
						allesDTO.setID(ID);
					}
				}

				allesDTOList.add(allesDTO);
			}else if(dataSource.equals("腾讯")){
				/**腾讯 信息*/
				String ID=(String)source.get("腾讯");
				String nick=(String) source.get("userName");
				//String qqNum = (String) source.get("qqNum");//
				String emailPasswd = (String) source.get("emailPasswd");
				String createDate=(String) source.get("time");
				//String telephone=(String) source.get("telephone");
				//String realName=(String) source.get("realName");//
				
				Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

				//jd data
				allesDTO.setNick(nick);
				allesDTO.setEmailPasswd(emailPasswd);
				allesDTO.setCreateDate(createDate);
								
				/*if(telephone!=null||"".equals(telephone)){
					if(telephone.equals(big_search_box)){
						allesDTO.setTelephone(highlightFields.get("telephone").getFragments()[0].toString());
					}else{
						allesDTO.setTelephone(telephone);
					}
				}*/
				if(email!=null||"".equals(email)){
					if(email.equals(big_search_box)){
						allesDTO.setEmail(highlightFields.get("email").getFragments()[0].toString());
					}else{
						allesDTO.setEmail(email);
					}
				}
				/*if(qqNum!=null||"".equals(qqNum)){
					if(qqNum.equals(big_search_box)){
						allesDTO.setQqNum(highlightFields.get("qqNum").getFragments()[0].toString());
					}else{
						allesDTO.setQqNum(qqNum);
					}
				}*/
				if(dataSource!=null||"".equals(dataSource)){
					if(dataSource.equals(big_search_box)){
						allesDTO.setDataSource(highlightFields.get("dataSource").getFragments()[0].toString());
					}else{
						allesDTO.setDataSource(dataSource);
					}
				}
				/*if(realName!=null||"".equals(realName)){
					if(realName.equals(big_search_box)){
						allesDTO.setRealName(highlightFields.get("realName").getFragments()[0].toString());
					}else{
						allesDTO.setRealName(realName);
					}
				}*/
				if(ID!=null||"".equals(ID)){
					if(ID.equals(big_search_box)){
						allesDTO.setID(highlightFields.get("ID").getFragments()[0].toString());
					}else{
						allesDTO.setID(ID);
					}
				}

				allesDTOList.add(allesDTO);
			}else if(dataSource.equals("百度云")){
				/**百度云 信息*/
				String ID=(String)source.get("ID");
				String nick=(String) source.get("userName");
				String qqNum = (String) source.get("qqNum");//
				String emailPasswd = (String) source.get("emailPasswd");
				String createDate=(String) source.get("time");
				String telephone=(String) source.get("telephone");
				String realName=(String) source.get("realName");//
				
				Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

				//jd data
				allesDTO.setNick(nick);
				allesDTO.setEmailPasswd(emailPasswd);
				allesDTO.setCreateDate(createDate);
								
				if(telephone!=null||"".equals(telephone)){
					if(telephone.equals(big_search_box)){
						allesDTO.setTelephone(highlightFields.get("telephone").getFragments()[0].toString());
					}else{
						allesDTO.setTelephone(telephone);
					}
				}
				if(email!=null||"".equals(email)){
					if(email.equals(big_search_box)){
						allesDTO.setEmail(highlightFields.get("email").getFragments()[0].toString());
					}else{
						allesDTO.setEmail(email);
					}
				}
				if(qqNum!=null||"".equals(qqNum)){
					if(qqNum.equals(big_search_box)){
						allesDTO.setQqNum(highlightFields.get("qqNum").getFragments()[0].toString());
					}else{
						allesDTO.setQqNum(qqNum);
					}
				}
				if(dataSource!=null||"".equals(dataSource)){
					if(dataSource.equals(big_search_box)){
						allesDTO.setDataSource(highlightFields.get("dataSource").getFragments()[0].toString());
					}else{
						allesDTO.setDataSource(dataSource);
					}
				}
				if(realName!=null||"".equals(realName)){
					if(realName.equals(big_search_box)){
						allesDTO.setRealName(highlightFields.get("realName").getFragments()[0].toString());
					}else{
						allesDTO.setRealName(realName);
					}
				}
				if(ID!=null||"".equals(ID)){
					if(ID.equals(big_search_box)){
						allesDTO.setID(highlightFields.get("ID").getFragments()[0].toString());
					}else{
						allesDTO.setID(ID);
					}
				}

				allesDTOList.add(allesDTO);
			}else if(dataSource.equals("珍爱网")){
				/**珍爱网 信息*/
				String ID=(String)source.get("ID");
				String nick=(String) source.get("userName");
				String qqNum = (String) source.get("qqNum");//
				String emailPasswd = (String) source.get("emailPasswd");
				String createDate=(String) source.get("time");
				String telephone=(String) source.get("telephone");
				String realName=(String) source.get("realName");//
				
				Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

				//jd data
				allesDTO.setNick(nick);
				allesDTO.setEmailPasswd(emailPasswd);
				allesDTO.setCreateDate(createDate);
								
				if(telephone!=null||"".equals(telephone)){
					if(telephone.equals(big_search_box)){
						allesDTO.setTelephone(highlightFields.get("telephone").getFragments()[0].toString());
					}else{
						allesDTO.setTelephone(telephone);
					}
				}
				if(email!=null||"".equals(email)){
					if(email.equals(big_search_box)){
						allesDTO.setEmail(highlightFields.get("email").getFragments()[0].toString());
					}else{
						allesDTO.setEmail(email);
					}
				}
				if(qqNum!=null||"".equals(qqNum)){
					if(qqNum.equals(big_search_box)){
						allesDTO.setQqNum(highlightFields.get("qqNum").getFragments()[0].toString());
					}else{
						allesDTO.setQqNum(qqNum);
					}
				}
				if(dataSource!=null||"".equals(dataSource)){
					if(dataSource.equals(big_search_box)){
						allesDTO.setDataSource(highlightFields.get("dataSource").getFragments()[0].toString());
					}else{
						allesDTO.setDataSource(dataSource);
					}
				}
				if(realName!=null||"".equals(realName)){
					if(realName.equals(big_search_box)){
						allesDTO.setRealName(highlightFields.get("realName").getFragments()[0].toString());
					}else{
						allesDTO.setRealName(realName);
					}
				}
				if(ID!=null||"".equals(ID)){
					if(ID.equals(big_search_box)){
						allesDTO.setID(highlightFields.get("ID").getFragments()[0].toString());
					}else{
						allesDTO.setID(ID);
					}
				}

				allesDTOList.add(allesDTO);
			}else if(dataSource.equals("新浪")){
				/**新浪 信息*/
				String ID=(String)source.get("ID");
				String nick=(String) source.get("userName");
				String qqNum = (String) source.get("qqNum");//
				String emailPasswd = (String) source.get("emailPasswd");
				String createDate=(String) source.get("time");
				String telephone=(String) source.get("telephone");
				String realName=(String) source.get("realName");//
				
				Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

				//jd data
				allesDTO.setNick(nick);
				allesDTO.setEmailPasswd(emailPasswd);
				allesDTO.setCreateDate(createDate);
								
				if(telephone!=null||"".equals(telephone)){
					if(telephone.equals(big_search_box)){
						allesDTO.setTelephone(highlightFields.get("telephone").getFragments()[0].toString());
					}else{
						allesDTO.setTelephone(telephone);
					}
				}
				if(email!=null||"".equals(email)){
					if(email.equals(big_search_box)){
						allesDTO.setEmail(highlightFields.get("email").getFragments()[0].toString());
					}else{
						allesDTO.setEmail(email);
					}
				}
				if(qqNum!=null||"".equals(qqNum)){
					if(qqNum.equals(big_search_box)){
						allesDTO.setQqNum(highlightFields.get("qqNum").getFragments()[0].toString());
					}else{
						allesDTO.setQqNum(qqNum);
					}
				}
				if(dataSource!=null||"".equals(dataSource)){
					if(dataSource.equals(big_search_box)){
						allesDTO.setDataSource(highlightFields.get("dataSource").getFragments()[0].toString());
					}else{
						allesDTO.setDataSource(dataSource);
					}
				}
				if(realName!=null||"".equals(realName)){
					if(realName.equals(big_search_box)){
						allesDTO.setRealName(highlightFields.get("realName").getFragments()[0].toString());
					}else{
						allesDTO.setRealName(realName);
					}
				}
				if(ID!=null||"".equals(ID)){
					if(ID.equals(big_search_box)){
						allesDTO.setID(highlightFields.get("ID").getFragments()[0].toString());
					}else{
						allesDTO.setID(ID);
					}
				}

				allesDTOList.add(allesDTO);
			}else{	//其他数据
				dataSource = (String) source.get("dataSource");//
				String nick=(String) source.get("userName");
				String emailPasswd = (String) source.get("emailPasswd");
				String createDate=(String) source.get("createDate");
				Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
				allesDTO.setNick(nick);
				allesDTO.setEmailPasswd(emailPasswd);
				allesDTO.setCreateDate(createDate);
								
				if(email!=null||"".equals(email)){
					if(email.contains(big_search_box)){
						if(highlightFields.size()>0){
							allesDTO.setEmail(highlightFields.get("email").getFragments()[0].toString());
						}
					}else{
						allesDTO.setEmail(email);
					}
				}
				if(nick!=null||"".equals(nick)){
					if(nick.contains(big_search_box)){
						if(highlightFields.size()>0){
						allesDTO.setNick(highlightFields.get("userName").getFragments()[0].toString());
						}
					}else{
						allesDTO.setNick(dataSource);
					}
				}
				allesDTOList.add(allesDTO);
			}
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
	
	@RequestMapping(value = "/hackerSearchIP.php")
	public void hackerSearchIP(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		response.setContentType("textml; charset=UTF-8");
		
		String getIp = request.getParameter("big_search_box");
		ip_Sqldao=new ip_SqlDAO();
	    java.sql.Connection conn=ip_Sqldao.getConnection();
	    Statement st=(Statement) conn.createStatement();
			String sql="SELECT * FROM ipoff WHERE minip<=INET_ATON('"+getIp+"') ORDER BY minip DESC LIMIT 1";
			ResultSet rs=st.executeQuery(sql);
        	Ipoffline ipoffline=new Ipoffline();
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
			rs.close();
		ip_Sqldao.close();
            /*通过输出continent判断是否是公有ip，如果continent="局域网IP"，则multiarea中不可获得经纬度。
			System.out.println("*********continent"+ipoffline.getContinent());
            System.out.println("*********multiarea"+ipoffline.getMultiarea());*/
            
            String address = "";
            if(ipoffline.getMultiarea() != null && !"".equals(ipoffline.getMultiarea())){
            	String[] splits=ipoffline.getMultiarea().split(",|\\:");
            	String lat=splits[1].replace("\"", "");
            	String lon=splits[3].replace("\"", "");
            	address=lat+"-"+lon;
//            	getIpAddress.add(address);
            	System.out.println("lat=="+lat);
            	System.out.println("lon=="+lon);
            }
            
			
				//处理读取离线地图包数据
				Set set = new HashSet();
				List<String> getIpAddressNew = new ArrayList<String>();
//				for (String getAddresslists : address) {
//					if (set.add(address)) {
						getIpAddressNew.add(address);
//					}
//				}		
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
	
			@RequestMapping(value = "/getChineseAdressIP.php")
			public void getChineseAdressIP(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
				response.setContentType("textml; charset=UTF-8");
				
				String getIp = request.getParameter("big_search_box");
				System.out.println("中文地址getIp："+getIp);
				
				String multiarea="";
				String country = "";
				ip_Sqldao=new ip_SqlDAO();
			    java.sql.Connection conn=ip_Sqldao.getConnection();
			    Statement st=(Statement) conn.createStatement();
				String sql="SELECT * FROM ipoff WHERE minip<=INET_ATON('"+getIp+"') ORDER BY minip DESC LIMIT 1";
				
				ResultSet rs1=st.executeQuery(sql); 
				if(rs1!=null){
				while(rs1.next()){
						if(isExistColumn(rs1, "multiarea")){
							multiarea = rs1.getString("multiarea");
							country = rs1.getString("country");
						break;
						}
					}			
				}
				rs1.close();
				ip_Sqldao.close();
				String address = "";
				if(multiarea !=null && !"".equals(multiarea)){
					String multiarea1=multiarea.replace("\"", "");
					String[] k = multiarea1.split(",|\\:");
					String lon=k[k.length-5];
					String lat=k[k.length-3];
					address=lon+"-"+lat;
					System.out.println("country=-="+country);
				} 
				List<String> getIpAddressNew1 = new ArrayList<String>();
				getIpAddressNew1.add(address + "-"+ country);	
//				getIpAddressNew1.add(country);
				
				System.out.println("中文地址："+address);
				PrintWriter writer = null;
				try {
					writer = response.getWriter();
					writer.write(JsonUtil.list2json(getIpAddressNew1));
					writer.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						writer.close();
					}
				}
		}
			@RequestMapping(value = "/hackerSearchJizhan.php")
			public void hackerSearchJizhan(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
				response.setContentType("textml; charset=UTF-8");
				
				String lac = request.getParameter("lac");
				String cid = request.getParameter("cid");
				
				System.out.println("获取的lac=="+lac);
				System.out.println("获取的cid=="+cid);
				
				Cellinfo_v2 cellinfo_v2 = new Cellinfo_v2();
				cellinfo_v2.setLac(lac);
				cellinfo_v2.setCi(cid);
				
				Cellinfo_v2 bit= baseDao.getListfromMysql(cellinfo_v2, cellinfo_v2.getLac(), cellinfo_v2.getCi()).get(0);
				String jizhanInfo = bit.getLat() + "-" + bit.getLon() + "-" + bit.getAddr();
				
				List<String> jizhanInfoList = new ArrayList<String>();
				jizhanInfoList.add(jizhanInfo);
				
				System.out.println("中文地址："+jizhanInfo);
				PrintWriter writer = null;
				try {
					writer = response.getWriter();
					writer.write(JsonUtil.list2json(jizhanInfoList));
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