package com.xl.cloud.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xl.cloud.bean.IndexQueue;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.JsonUtil;

import net.sf.json.JSONArray;

@Controller
public class IndexQueueAction {
	private SqlDao sqlDao = new SqlDao();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final Logger logger = Logger.getLogger(BuildCollection.class);
	
	/**
	 * 无案件数据-数据列表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	@RequestMapping(value = "/showIndexQueue.php")
	public String showIndexQueue(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return "showIndexQueue";
	}
	@RequestMapping(value = "/showIndexQueueInfo.php")
	public void showIndexQueueInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		String pageIndexstr = request.getParameter("pageIndex");// 页数
		int pageIndex =Integer.parseInt(pageIndexstr);// 每页
		IndexQueue indexQueue = new IndexQueue();
		int count = sqlDao.getcountfromMysql(indexQueue);
		List<IndexQueue> indexQueueList = sqlDao.getOrderListfromMysql(indexQueue, (pageIndex - 1) * 10, 10,"startTime");

		map.put("indexQueueList", indexQueueList);
		map.put("count", count);
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
	
	//Running索引创建中 showIndexRunning
	@RequestMapping(value = "/showIndexRunning.php")
	public void showIndexRunning(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		response.setContentType("textml; charset=UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		String pageIndexstr = request.getParameter("pageIndex");// 页数
		int pageIndex =Integer.parseInt(pageIndexstr);// 每页
		IndexQueue indexQueue = new IndexQueue();
		indexQueue.setRunningState(3);
		int count = sqlDao.getcountfromMysql(indexQueue);
		List<IndexQueue> indexQueueList = sqlDao.getOrderListfromMysql(indexQueue, (pageIndex - 1) * 10, 10,"startTime");
		map.put("indexQueueList", indexQueueList);
		map.put("count", count);
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







