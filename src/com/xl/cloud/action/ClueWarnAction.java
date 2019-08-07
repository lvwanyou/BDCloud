package com.xl.cloud.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xl.cloud.bean.Caseinfo;
import com.xl.cloud.bean.ClueWarn;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.EsClient;
import com.xl.cloud.util.JsonUtil;


@Controller
public class ClueWarnAction {

	private SqlDao sqlDao = new SqlDao();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final Logger logger = Logger.getLogger(BuildCollection.class);
	

	/**
	 * 线索预警-点击详情
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/clueWarn/getEsClue.php")
	public void getEsClue(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
			response.setContentType("textml; charset=UTF-8");
			String url = request.getParameter("url");
			  //精确搜索
			  BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
			  mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
			  //url
			  mustQuery.must(QueryBuilders.termsQuery("file_download_url",url));
			  SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es","doc").setTypes("email","docType")
					  .setQuery(mustQuery)
					  .setFrom(0)//分页起始位置（跳过开始的n个）
		              .setSize(10);//本次返回的文档数量
			  //排序 执行
			  SearchResponse searchResponse = searchRequestBuilder
					  .execute().actionGet();//执行搜索
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
	 * 线索命中查询
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/clueWarn/getClue.php")
	public void getClue(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String caseId = request.getParameter("caseId");
		List<ClueWarn> clueWarnList = new ArrayList<ClueWarn>();
		ClueWarn clueWarn = new ClueWarn();
		if(caseId!=null && !"".equals(caseId)){
			clueWarn.setCaseId(Integer.parseInt(caseId));
		}
		clueWarnList = sqlDao.getListfromMysql(clueWarn);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			 writer.write(JsonUtil.list2json(clueWarnList));
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