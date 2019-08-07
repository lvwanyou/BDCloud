package com.xl.cloud.action;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;
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
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xl.cloud.bean.BankDetail;
import com.xl.cloud.bean.BillDetail;
import com.xl.cloud.bean.Caseinfo;
import com.xl.cloud.bean.ContainerDetail;
import com.xl.cloud.bean.CreditDetail;
import com.xl.cloud.bean.EmailDTO;
import com.xl.cloud.bean.EmlDetail;
import com.xl.cloud.bean.Evidence;
import com.xl.cloud.bean.FixedDetail;
import com.xl.cloud.bean.IdentityDetail;
import com.xl.cloud.bean.PassportDetail;
import com.xl.cloud.bean.PaymentDetail;
import com.xl.cloud.bean.PlateDetail;
import com.xl.cloud.bean.PriceDetail;
import com.xl.cloud.bean.QqDetail;
import com.xl.cloud.bean.TeleDetail;
import com.xl.cloud.bean.TransferDetail;
import com.xl.cloud.bean.TransportDetail;
import com.xl.cloud.bean.TwitterDetail;
import com.xl.cloud.bean.UserAction;
import com.xl.cloud.bean.WechatDetail;
import com.xl.cloud.bean.regexFind;
import com.xl.cloud.common.Global;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.EsClient;
import com.xl.cloud.util.JsonUtil;
@Controller
public class ExcavateData {

	private SqlDao sqlDao = new SqlDao();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final Logger logger = Logger.getLogger(BuildCollection.class);
	
	
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
	
	   //数据挖掘电话号码查询
		@RequestMapping(value = "/admin/phone_list.php")
		public void getPhonelist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
				HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException, UnknownHostException {

			String pageno = request.getParameter("pageno");
			String caseId = request.getParameter("caseId");
			 String[] caseids = {""};
			 if(caseId!=null && !"".equals(caseId)){
					caseids=caseId.split(",");
					
			  }
			 
			int pageIndex = 1;
			int pageSize = 10;
			int num = 0;

			if (!StringUtils.isEmpty(pageno)) {
				pageIndex = Integer.parseInt(pageno);
			}
			TeleDetail telt = new TeleDetail();
			
			if (!StringUtils.isEmpty(caseId)) {
				telt.setCaseId(caseId);
			}
			List<TeleDetail> logs =	new ArrayList<TeleDetail>();
			if(caseids!=null && !"".equals(caseids)){
		        	for(int i=0;i<caseids.length;i++){
		        		String caseid = caseids[i];
		        		telt.setCaseId(caseid);
		        		List<TeleDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
		        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 电话号码");
		        		logs.addAll(logs2);
		        	}
			    }else{
			    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
			    	
			    }
			// System.out.println(logs.size());
			int total = sqlDao.getcountfromMysqlLike(telt);
			//身份证号查询
			IdentityDetail identityDetail =  new IdentityDetail();
			identityDetail.setCaseId(caseId);
			int sfztotal = sqlDao.getcountfromMysqlLike(identityDetail);
			//固定电话查询
			FixedDetail fixedDetail =  new FixedDetail();
			fixedDetail.setCaseId(caseId);
			int gddhtotal = sqlDao.getcountfromMysqlLike(fixedDetail);
			//银行卡号查询
			BankDetail bankDetail =  new BankDetail();
			bankDetail.setCaseId(caseId);
			int yhtotal = sqlDao.getcountfromMysqlLike(bankDetail);
			//邮箱账号查询
			EmlDetail emlDetail =  new EmlDetail();
			emlDetail.setCaseId(caseId);
			int yxtotal = sqlDao.getcountfromMysqlLike(emlDetail);
			//车牌信息查询
			PlateDetail plateDetail =  new PlateDetail();
			plateDetail.setCaseId(caseId);
			int cptotal = sqlDao.getcountfromMysqlLike(plateDetail);
			//护照信息查询
			PassportDetail passportDetail =  new PassportDetail();
			passportDetail.setCaseId(caseId);
			int hztotal = sqlDao.getcountfromMysqlLike(passportDetail);
			//信用证信息查询
			CreditDetail creditDetail =  new CreditDetail();
			creditDetail.setCaseId(caseId);
			int xyztotal = sqlDao.getcountfromMysqlLike(creditDetail);
			//集装箱信息查询
			ContainerDetail containerDetail =  new ContainerDetail();
			containerDetail.setCaseId(caseId);
			int jzxtotal = sqlDao.getcountfromMysqlLike(containerDetail);
			//运输车号信息查询
			TransportDetail transportDetail =  new TransportDetail();
			transportDetail.setCaseId(caseId);
			int ysctotal = sqlDao.getcountfromMysqlLike(transportDetail);
			//电汇信息查询
			TransferDetail transferDetail =  new TransferDetail();
			transferDetail.setCaseId(caseId);
			int dhtotal = sqlDao.getcountfromMysqlLike(transferDetail);
			
			//QQ信息查询
			QqDetail qqDetail =  new QqDetail();
			qqDetail.setCaseId(caseId);
			int qqtotal = sqlDao.getcountfromMysqlLike(qqDetail);
			//微信信息查询
			WechatDetail wechatDetail =  new WechatDetail();
			wechatDetail.setCaseId(caseId);
			int wxtotal = sqlDao.getcountfromMysqlLike(wechatDetail);
			//推特信息查询
			TwitterDetail twitterDetail =  new TwitterDetail();
			twitterDetail.setCaseId(caseId);
			int tttotal = sqlDao.getcountfromMysqlLike(twitterDetail);
			//价格信息查询
			PriceDetail priceDetail =  new PriceDetail();
			priceDetail.setCaseId(caseId);
			int jgtotal = sqlDao.getcountfromMysqlLike(priceDetail);
			//支付宝信息查询
			PaymentDetail paymentDetail =  new PaymentDetail();
			paymentDetail.setCaseId(caseId);
			int zfbtotal = sqlDao.getcountfromMysqlLike(paymentDetail);
			//发票信息查询
			BillDetail billDetail =  new BillDetail();
			billDetail.setCaseId(caseId);
			int fptotal = sqlDao.getcountfromMysqlLike(billDetail);
			
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
			map.put("sfztotal", sfztotal);
			map.put("gddhtotal", gddhtotal);
			map.put("yhtotal", yhtotal);
			map.put("dhtotal", dhtotal);
			map.put("ysctotal", ysctotal);
			map.put("yxtotal", yxtotal);
			map.put("jzxtotal", jzxtotal);
			map.put("xyztotal", xyztotal);
			map.put("cptotal", cptotal);
			map.put("hztotal", hztotal);
			
			map.put("qqtotal", qqtotal);
			map.put("wxtotal", wxtotal);
			map.put("tttotal", tttotal);
			map.put("jgtotal", jgtotal);
			map.put("zfbtotal", zfbtotal);
			map.put("fptotal", fptotal);
			
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
		
		   //数据挖掘身份证号码查询
				@RequestMapping(value = "/admin/sfz_list.php")
				public void getsfzlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
						HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
						IllegalAccessException, InvocationTargetException {

					String pageno = request.getParameter("pageno");
					String caseId = request.getParameter("caseId");
					 String[] caseids = {""};
					 if(caseId!=null && !"".equals(caseId)){
							caseids=caseId.split(",");
							
					  }
					 
					int pageIndex = 1;
					int pageSize = 10;
					int num = 0;

					if (!StringUtils.isEmpty(pageno)) {
						pageIndex = Integer.parseInt(pageno);
					}
					IdentityDetail telt = new IdentityDetail();
					
					if (!StringUtils.isEmpty(caseId)) {
						telt.setCaseId(caseId);
					}
					List<IdentityDetail> logs =	new ArrayList<IdentityDetail>();
					if(caseids!=null && !"".equals(caseids)){
				        	for(int i=0;i<caseids.length;i++){
				        		String caseid = caseids[i];
				        		telt.setCaseId(caseid);
				        		List<IdentityDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
				        		logs.addAll(logs2);
				        	}
					    }else{
					    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
					    	
					    }
					// System.out.println(logs.size());
					int total = sqlDao.getcountfromMysqlLike(telt);
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
				
				
				 //数据挖掘运输车号码查询
				@RequestMapping(value = "/admin/ys_list.php")
				public void getyslist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
						HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
						IllegalAccessException, InvocationTargetException, UnknownHostException {

					String pageno = request.getParameter("pageno");
					String caseId = request.getParameter("caseId");
					 String[] caseids = {""};
					 if(caseId!=null && !"".equals(caseId)){
							caseids=caseId.split(",");
							
					  }
					 
					int pageIndex = 1;
					int pageSize = 10;
					int num = 0;

					if (!StringUtils.isEmpty(pageno)) {
						pageIndex = Integer.parseInt(pageno);
					}
					TransportDetail telt = new TransportDetail();
					
					if (!StringUtils.isEmpty(caseId)) {
						telt.setCaseId(caseId);
					}
					List<TransportDetail> logs =	new ArrayList<TransportDetail>();
					if(caseids!=null && !"".equals(caseids)){
				        	for(int i=0;i<caseids.length;i++){
				        		String caseid = caseids[i];
				        		telt.setCaseId(caseid);
				        		List<TransportDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
				        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 运输车号码");
				        		logs.addAll(logs2);
				        	}
					    }else{
					    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
					    	
					    }
					// System.out.println(logs.size());
					int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						 //数据挖掘电汇号码查询
						@RequestMapping(value = "/admin/dh_list.php")
						public void getdhlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							TransferDetail telt = new TransferDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<TransferDetail> logs =	new ArrayList<TransferDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<TransferDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						 //数据挖掘车牌号码查询
						@RequestMapping(value = "/admin/cp_list.php")
						public void getcplist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							PlateDetail telt = new PlateDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<PlateDetail> logs =	new ArrayList<PlateDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<PlateDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						 //数据挖掘护照号码查询
						@RequestMapping(value = "/admin/hz_list.php")
						public void gethzlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							PassportDetail telt = new PassportDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<PassportDetail> logs =	new ArrayList<PassportDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<PassportDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 护照号码");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						 //数据挖掘固定号码查询
						@RequestMapping(value = "/admin/gddh_list.php")
						public void getgddhlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							FixedDetail telt = new FixedDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<FixedDetail> logs =	new ArrayList<FixedDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<FixedDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 固定号码");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						 //数据挖掘邮箱查询
						@RequestMapping(value = "/admin/yx_list.php")
						public void getyxlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							EmlDetail telt = new EmlDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<EmlDetail> logs =	new ArrayList<EmlDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<EmlDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						
						 //数据挖掘信用证查询
						@RequestMapping(value = "/admin/xyz_list.php")
						public void getxyzlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							CreditDetail telt = new CreditDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<CreditDetail> logs =	new ArrayList<CreditDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<CreditDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 信用证");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						
						 //数据挖掘集装箱号查询
						@RequestMapping(value = "/admin/jzx_list.php")
						public void getjzxlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							ContainerDetail telt = new ContainerDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<ContainerDetail> logs =	new ArrayList<ContainerDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<ContainerDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 集装箱号");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						
						 //数据挖掘银行卡号查询
						@RequestMapping(value = "/admin/yhk_list.php")
						public void getyhklist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							BankDetail telt = new BankDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<BankDetail> logs =	new ArrayList<BankDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<BankDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 银行卡号");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						 //数据挖掘QQ号查询
						@RequestMapping(value = "/admin/qq_list.php")
						public void getqqlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							QqDetail telt = new QqDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<QqDetail> logs =	new ArrayList<QqDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<QqDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 QQ号");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						
						 //数据挖掘微信号查询
						@RequestMapping(value = "/admin/wx_list.php")
						public void getwxlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							WechatDetail telt = new WechatDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<WechatDetail> logs =	new ArrayList<WechatDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<WechatDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 微信号");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						
						
						 //数据挖掘支付宝号查询
						@RequestMapping(value = "/admin/zfb_list.php")
						public void getzfblist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							PaymentDetail telt = new PaymentDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<PaymentDetail> logs =	new ArrayList<PaymentDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<PaymentDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 支付宝号");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						
						 //数据挖掘价格信息查询
						@RequestMapping(value = "/admin/jg_list.php")
						public void getjglist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							PriceDetail telt = new PriceDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<PriceDetail> logs =	new ArrayList<PriceDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<PriceDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 价格信息");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						
						
						 //数据挖掘发票信息查询
						@RequestMapping(value = "/admin/fp_list.php")
						public void getfplist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							BillDetail telt = new BillDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<BillDetail> logs =	new ArrayList<BillDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<BillDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 发票信息");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						//数据挖掘推特信息查询
						@RequestMapping(value = "/admin/tt_list.php")
						public void getttlist(HttpServletRequest request, Map<String, Object> map, HttpSession session,
								HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
								IllegalAccessException, InvocationTargetException, UnknownHostException {

							String pageno = request.getParameter("pageno");
							String caseId = request.getParameter("caseId");
							 String[] caseids = {""};
							 if(caseId!=null && !"".equals(caseId)){
									caseids=caseId.split(",");
									
							  }
							 
							int pageIndex = 1;
							int pageSize = 10;
							int num = 0;

							if (!StringUtils.isEmpty(pageno)) {
								pageIndex = Integer.parseInt(pageno);
							}
							TwitterDetail telt = new TwitterDetail();
							
							if (!StringUtils.isEmpty(caseId)) {
								telt.setCaseId(caseId);
							}
							List<TwitterDetail> logs =	new ArrayList<TwitterDetail>();
							if(caseids!=null && !"".equals(caseids)){
						        	for(int i=0;i<caseids.length;i++){
						        		String caseid = caseids[i];
						        		telt.setCaseId(caseid);
						        		List<TwitterDetail> logs2 =	sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
						        		actionLog((String) session.getAttribute("userName"), "查看", "数据挖掘 推特信息");
						        		logs.addAll(logs2);
						        	}
							    }else{
							    	logs = sqlDao.getOrderListfromMysqlLike(telt, (pageIndex - 1) * pageSize, pageSize, "id");
							    	
							    }
							// System.out.println(logs.size());
							int total = sqlDao.getcountfromMysqlLike(telt);
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
						
						
						//邮件工作台时间线数据处理
						public List<EmailDTO> getDomainEmail = new ArrayList<EmailDTO>();
						/**
						 * 数据管理-邮件工作台-邮件工作台时间线查询
						 * @throws UnknownHostException 
						 */
						@RequestMapping(value = "/Excavateldata/getEmailList.php")
						public void getEmailList(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
							response.setContentType("textml; charset=UTF-8");
							String sortType = request.getParameter("sortType");
							String emailKeyword = request.getParameter("emailKeyword");
							String caseidStr = request.getParameter("caseidStr");//案件id
							String pageIndexstr = request.getParameter("pageIndex");
							String startDate = request.getParameter("startDate");// 开始时间
							String endDate = request.getParameter("endDate");// 结束时间
							//高级搜索条件
							String orKey = request.getParameter("orKey");//or
							String andKey = request.getParameter("andKey");//and
							String notKey = request.getParameter("notKey");//not
							String timeType = request.getParameter("timeType");//之间,之外,之前,之后
							String kaishiTime = request.getParameter("kaishiTime");// 开始时间
							String jieshuTime = request.getParameter("jieshuTime");// 结束时间
							String matchingType = request.getParameter("matchingType");//1任意,2全部
							String XYRemail = request.getParameter("XYRemail");//案件下的嫌疑人邮箱
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
							actionLog((String) session.getAttribute("userName"), "查看", "邮件挖掘 ");
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
							mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
							
							//高级搜索or
							if(orKey!=null && !"".equals(orKey)){
								String[] orKeylist = orKey.split(" ");
								 // 组合 模糊查询  should  
						        BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
								for (String string : orKeylist) {
							        MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", "*"+string+"*");  
							        MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", "*"+string+"*");  
							        MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", "*"+string+"*");  
							        ors.should(subject).should(content).should(attachmentContent);
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
							        MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", "*"+string+"*");  
							        MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", "*"+string+"*");  
							        MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", "*"+string+"*");  
							        ands.should(subject).should(content).should(attachmentContent);
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
							        MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", "*"+string+"*");  
							        MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", "*"+string+"*");  
							        MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", "*"+string+"*");  
							        nots.should(subject).should(content).should(attachmentContent);
							        if("2".equals(matchingType)){
							        	mustQuery.mustNot(nots);
									}
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
							// 嫌疑人邮箱名关键词 全部
							if (XYRemail != null && !"".equals(XYRemail)) {
								BoolQueryBuilder ors = QueryBuilders.boolQuery();
								TermsQueryBuilder fromWho = QueryBuilders.termsQuery("fromWho", XYRemails);
								ors.should(fromWho);
								//多个嫌疑人对收件人模糊查询
								for (String string : XYRemails) {
									WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho",  "*"+string+"*");
									ors.should(toWho2);
								}
								System.out.println("XYRemail====Excavateldata/getEmailList.php==="+XYRemail);
								mustQuery.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
							}
							
							// 关键词搜索
							if (emailKeyword != null && !"".equals(emailKeyword)) {
//								QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(emailKeyword)
//										.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
//								mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
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
								
								actionLog((String) session.getAttribute("userName"), "搜索关键词："+emailKeyword, "邮件挖掘 ");
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
							for (SearchHit searchHit : hits) {
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
								int quickflagflag = 0;
								EmailDTO emailDTO = new EmailDTO();
								emailDTO.setEsID(esID);
								emailDTO.setRead(Integer.parseInt(readFlag));
								emailDTO.setStar(Integer.parseInt(starFlag));
								emailDTO.setIp(ip);
								emailDTO.setAttachmentname(attachmentname);
								if (emailKeyword != null && !"".equals(emailKeyword)) {
									String s1 = subject.replace(emailKeyword,
											"<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
									emailDTO.setSubject(s1);
								} else {
									emailDTO.setSubject(subject);
								}
								if (emailKeyword != null && !"".equals(emailKeyword)) {
									String s1 = fromWho.replace(emailKeyword,
											"<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
									emailDTO.setFromWho(s1);
								} else {
									emailDTO.setFromWho(fromWho);
								}
								if (emailKeyword != null && !"".equals(emailKeyword)) {
									String s1 = toWho.replace(emailKeyword,
											"<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
									emailDTO.setToWho(s1);
								} else {
									emailDTO.setToWho(toWho);
								}
								if (emailKeyword != null && !"".equals(emailKeyword)) {
									String s1 = content.replace(emailKeyword,
											"<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
									emailDTO.setContent(s1);
								} else {
									emailDTO.setContent(content);
								}
								if (emailKeyword != null && !"".equals(emailKeyword)) {
									String s1 = date.replace(emailKeyword,
											"<font style='color: red;background-color: yellow;'>" + emailKeyword + "</font>");
									emailDTO.setDate(s1);
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
									        MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", "*"+string+"*");  
									        MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", "*"+string+"*");  
									        MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", "*"+string+"*");  
									        ors.should(subject).should(content).should(attachmentContent);
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
									        MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", "*"+string+"*");  
									        MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", "*"+string+"*");  
									        MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", "*"+string+"*");  
									        ands.should(subject).should(content).should(attachmentContent);
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
									        MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", "*"+string+"*");  
									        MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", "*"+string+"*");  
									        MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", "*"+string+"*");  
									        nots.should(subject).should(content).should(attachmentContent);
									        if("2".equals(matchingType)){
									        	mustQuery2.mustNot(nots);
											}
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
								System.out.println("XYRemail====Excavateldata/getEmailList.php==="+XYRemail);
								mustQuery2.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
							}
							// 关键词搜索
							if (emailKeyword != null && !"".equals(emailKeyword)) {
								emailKeyword=emailKeyword.toLowerCase();
								// 组合 模糊查询  should  
								BoolQueryBuilder ors2 = QueryBuilders.boolQuery(); 
								//TermQueryBuilder subject = QueryBuilders.termQuery("subject", emailKeyword);
								MatchQueryBuilder subject = QueryBuilders.matchPhraseQuery("subject", emailKeyword);
								MatchQueryBuilder content = QueryBuilders.matchPhraseQuery("content", emailKeyword);  
								MatchQueryBuilder fromWho = QueryBuilders.matchPhraseQuery("fromWho", emailKeyword);
								MatchQueryBuilder toWho = QueryBuilders.matchPhraseQuery("toWho", emailKeyword);
								MatchQueryBuilder attachmentname = QueryBuilders.matchPhraseQuery("attachmentname", emailKeyword);
								MatchQueryBuilder attachmentContent = QueryBuilders.matchPhraseQuery("attachmentContent", emailKeyword);  
								ors2.should(subject).should(content).should(fromWho).should(toWho).should(attachmentname).should(attachmentContent);
								mustQuery2.must(ors2);
							}
							// 日期范围
							if (startDate != null && !"".equals(startDate)) {
								RangeQueryBuilder rangequerybuilder2 = QueryBuilders.rangeQuery("date").from(startDate + " 00:00:00")
										.to(endDate + " 23:59:59");
								mustQuery2.must(rangequerybuilder2);
							}
							SearchRequestBuilder searchRequestBuilder2 = EsClient.getClient().prepareSearch("es").setTypes("email")
									.setQuery(mustQuery2).setFrom(0)// 分页起始位置（跳过开始的n个）
									.setSize(1);// 本次返回的文档数量
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
						 * 数据管理-邮件工作台-邮件工作台时间线控件数据展示查询
						 * @throws UnknownHostException 
						 */
						@RequestMapping(value = "/Excavateldata/getTimeList.php")
						public void getTimeList(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnknownHostException {
							response.setContentType("textml; charset=UTF-8");
							String caseidStr = request.getParameter("caseidStr");//案件id
							String startDate = request.getParameter("startDate");// 开始时间
							String endDate = request.getParameter("endDate");// 结束时间
							String guanjianci = request.getParameter("guanjianci");//关键词
							String emailKeyword2 = request.getParameter("emailKeyword2");//邮件查询关键词
							String XYRemail = request.getParameter("XYRemail");//案件下嫌疑人的邮箱
							String allcount = request.getParameter("allcount");//邮件总数
							String[] XYRemails = { "" };
							if (!"".equals(XYRemail)&&XYRemail!=null) {
								XYRemails = XYRemail.split(",");
							}
							// 默认最新数据的案件
							if (caseidStr == null || "".equals(caseidStr)) {
								caseidStr="-1";
							}
							actionLog((String) session.getAttribute("userName"), "查看", "邮件挖掘 ");
							String[] caseids = { "" };
							if (!"".equals(caseidStr)) {
								caseids = caseidStr.split(" ");
							}
							// 精确搜索
							BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
							mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
							// caseid集合
							if (!"".equals(caseidStr)) {
								mustQuery.must(QueryBuilders.termsQuery("caseID", caseids));
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
								System.out.println("Excavateldata/getTimeList.php"+XYRemail);
								mustQuery.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
							}
							// 多条件筛选关键词搜索
							if (emailKeyword2 != null && !"".equals(emailKeyword2)) {
								emailKeyword2=emailKeyword2.toLowerCase();
								// 组合 模糊查询  should  
								String[] emailKeywordlist = emailKeyword2.split(" ");
								for (String emailKeywordstring : emailKeywordlist) {
								BoolQueryBuilder ors = QueryBuilders.boolQuery(); 
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
							// 关键词搜索
							if (guanjianci != null && !"".equals(guanjianci)) {
								QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(guanjianci)
										.defaultOperator(QueryStringQueryBuilder.Operator.AND);// 不同关键词之间使用and关系
								mustQuery.must(queryBuilder);// 添加第4条must的条件 关键词全文搜索筛选条件
								actionLog((String) session.getAttribute("userName"), "搜索关键词："+guanjianci, "邮件挖掘 ");
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
									.setSize(Integer.valueOf(allcount));// 本次返回的文档数量;
							// 排序 执行
							SearchResponse searchResponse = searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH)
										.addSort("date", SortOrder.ASC).execute().actionGet();// 执行搜索
							
							Map<String, Object> map = new HashMap<String, Object>();
							ArrayList<SearchHit[]> hitList = new ArrayList<SearchHit[]>();
							SearchHit[] hits1 = searchResponse.getHits().getHits();
							hitList.add(hits1);
//							long totalHits0 = searchResponse.getHits().getTotalHits()/10000;
//							for(int i=1;i<=totalHits0;i++){
//								searchResponse = searchRequestBuilder.setQuery(mustQuery).setFrom(i*10000)// 分页起始位置（跳过开始的n个）
//										.setSize(10000).addSort("date", SortOrder.ASC).execute().actionGet();
//								SearchHit[] hits  = searchResponse.getHits().getHits();
//								hitList.add(hits);
//							}
							
							//存储时间
							ArrayList<String> arrayList = new ArrayList<>();
							for (SearchHit[] searchHit2 : hitList) {
								  for (SearchHit searchHit : searchHit2) {
								Map<String, Object> source = searchHit.getSource();
								String data = (String) source.get("date");
								arrayList.add(data);
							}
							}
							//拿时间对应的收发件总数
							Map<String, Integer> map2 = new HashMap<String, Integer>();
							ArrayList<String> listkey2 = new ArrayList<String>();
							for (String string : arrayList) {
								//日期数据空格
								String riqi = string.split(" ")[0];
								
								Set<String> set2 = map2.keySet();
								int flag=0;
								for (String key : set2) {
									if(riqi.equals(key)){
										Integer integer = map2.get(key)+1;
										map2.put(riqi, integer);
										flag=1;
									}
								}
								if(flag==0){
									map2.put(riqi, 1);
									listkey2.add(riqi);
								}
							}
							ArrayList<Integer> listkey = new ArrayList<Integer>();
							for (String integer : listkey2) {
								Integer integer2 = map2.get(integer);
								listkey.add(integer2);
							}
							
							map.put("hits", listkey2);
							map.put("count", listkey);
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
						 * 数据管理-邮件工作台-快速标记查询
						 */
						@RequestMapping(value = "/quickFlagsType.php")
						public void quickFlagsType(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
							response.setContentType("textml; charset=UTF-8");
							String sortType = request.getParameter("sortType");// 排序类型
							String regexpQuery = request.getParameter("regexpQuery");// 标记类型
							String emailKeyword = request.getParameter("emailKeyword");// 关键词
							String caseidStr = request.getParameter("caseidStr");// 案件id
							String pageIndexstr = request.getParameter("pageIndex");// 页码
							String startDate = request.getParameter("startDate");// 开始时间
							String endDate = request.getParameter("endDate");// 结束时间
							String XYRemail = request.getParameter("XYRemail");//案件下嫌疑人的邮箱
							String[] XYRemails = { "" };
							if (!"".equals(XYRemail)&&XYRemail!=null) {
								XYRemails = XYRemail.split(",");
							}
							String nowTime1 = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
							String nowTime = nowTime1.substring(0, 10);
							List<regexFind> logs = new ArrayList<regexFind>();
							List<EmailDTO> emailDTOList = new ArrayList<EmailDTO>();
							int pageIndex = 1;
							int pageSize = 10;
							int read0 = 0;
							int isRead = 0;
							if (pageIndexstr != null && !"".equals(pageIndexstr)) {
								pageIndex = Integer.parseInt(pageIndexstr);
							}
							// 默认最新数据的案件
							if (caseidStr == null || "".equals(caseidStr)) {
								caseidStr = "-1";
							}
							String riqi = "";
							int total = 0;
							String read = "";
							String star = "";
							String fuj = "";
							String[] caseids = { "" };
							if (!"".equals(caseidStr)) {
								caseids = caseidStr.split(" ");
							}
							regexFind regexFind = new regexFind();
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
								quickflag = "1";
							} else if ("固定电话".equals(regexpQuery)) {
								quickflag = "7";
							} else if ("身份证号".equals(regexpQuery)) {
								quickflag = "6";
							} else if ("邮箱号".equals(regexpQuery)) {
								quickflag = "4";
							} else if ("银行卡号".equals(regexpQuery)) {
								quickflag = "2";
							} else if ("车牌号".equals(regexpQuery)) {
								quickflag = "5";
							} else if ("运输车号".equals(regexpQuery)) {
								quickflag = Global.regModel;
							} else if ("集装箱号".equals(regexpQuery)) {
								quickflag = "3";
							} else if ("支付宝号".equals(regexpQuery)) {
								quickflag = Global.regUP;
							} else if ("价格".equals(regexpQuery)) {
								quickflag = "8";
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

							// System.out.println("es查询开始========>>"+new Date());;
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
							// 嫌疑人邮箱名关键词 全部
							if (XYRemail != null && !"".equals(XYRemail)) {
								BoolQueryBuilder ors = QueryBuilders.boolQuery();
								TermsQueryBuilder fromWho = QueryBuilders.termsQuery("fromWho", XYRemails);
								ors.should(fromWho);
								//多个嫌疑人对收件人模糊查询
								for (String string : XYRemails) {
									WildcardQueryBuilder toWho2 = QueryBuilders.wildcardQuery("toWho",  "*"+string+"*");
									ors.should(toWho2);
								}
								System.out.println("/quickFlagsType.php=="+XYRemail);
								mustQuery.must(ors);// 添加第4条must的条件 关键词全文搜索筛选条件
							}
							// 关键词搜索
							if (emailKeyword != null && !"".equals(emailKeyword)) {
								// 组合 模糊查询 should
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
							// 标记不为空
							if (!"".equals(quickflag)) {
								regexFind.setRegxType(quickflag);
								
								//获取正则匹配的路径file_download_url
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
								if (XYRemail != null && !"".equals(XYRemail)) {
									logs = sqlDao.getRegexList(caseidStr, quickflag, (pageIndex - 1) * pageSize, pageSize,tableRegexgName,XYRemails);
									total = sqlDao.getRegexCount1(caseidStr, quickflag,tableRegexgName,XYRemails);
									isRead = sqlDao.getReadCount1(caseidStr, quickflag,tableRegexgName,XYRemails);
								}else{
									logs = sqlDao.getRegexList1(caseidStr, quickflag, (pageIndex - 1) * pageSize, pageSize,tableRegexgName);
									total = sqlDao.getRegexCount(caseidStr, quickflag,tableRegexgName);
									isRead = sqlDao.getReadCount(caseidStr, quickflag,tableRegexgName);
								}
								
								List<String> regexListURL = new ArrayList<String>();

								for (int i = 0; i < logs.size(); i++) {
									regexListURL.add(logs.get(i).getFile_download_url());
								}

								mustQuery.must(QueryBuilders.termsQuery("file_download_url", regexListURL));
								SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es").setTypes("email").setQuery(mustQuery);
								
//								System.out.println(searchRequestBuilder.toString());
								// 排序 执行
								SearchResponse searchResponse = null;
								if ("date".equals(sortConditon)) {
									searchResponse = searchRequestBuilder
											.addSort(SortBuilders.fieldSort(sortConditon).order(SortOrder.DESC))// 按类型排序
											.execute().actionGet();// 执行搜索
								} else {
									searchResponse = searchRequestBuilder.addSort(SortBuilders.fieldSort(sortConditon).order(SortOrder.ASC))// 按类型排序
											.execute().actionGet();// 执行搜索
								}

								SearchHit[] hits1 = searchResponse.getHits().getHits();
								if ("1".equals(read)) {
									read0 = 0;
								}

								for (SearchHit searchHit : hits1) {
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

									EmailDTO emailDTO = new EmailDTO();
									emailDTO.setEsID(esID);
									emailDTO.setRead(Integer.parseInt(readFlag));
									emailDTO.setStar(Integer.parseInt(starFlag));
									emailDTO.setIp(ip);
									emailDTO.setAttachmentname(attachmentname);
									emailDTO.setSubject(subject);
									emailDTO.setFromWho(fromWho);
									emailDTO.setToWho(toWho);
									for (regexFind log : logs) {
										content = content.replace(log.getRegContext(),
												"<font style='color: red;background-color: yellow;'>" + log.getRegContext() + "</font>");
										if (subject.contains(log.getRegContext())) {
											subject = subject.replace(log.getRegContext(),
													"<font style='color: red;background-color: yellow;'>" + log.getRegContext()
															+ "</font>");
										}
										if (toWho.contains(log.getRegContext())) {
											toWho = toWho.replace(log.getRegContext(), "<font style='color: red;background-color: yellow;'>"
													+ log.getRegContext() + "</font>");
										}
										if (fromWho.contains(log.getRegContext())) {
											fromWho = fromWho.replace(log.getRegContext(),
													"<font style='color: red;background-color: yellow;'>" + log.getRegContext()
															+ "</font>");
										}
										if (attachmentname.contains(log.getRegContext())) {
											attachmentname = attachmentname.replace(log.getRegContext(),
													"<font style='color: red;background-color: yellow;'>" + log.getRegContext()
															+ "</font>");
										}
									}
									emailDTO.setContent(content);
									emailDTO.setDate(date);
									emailDTO.setDownloadUrl(downloadUrl);

									emailDTO.setCaseID(caseID);
									emailDTO.setCaseName(caseName);
									emailDTOList.add(emailDTO);
//									System.out.println("emailDTOList>>>" + emailDTOList.size());
									if ("0".equals(readFlag)) {
										read0 += 1;
									}
								}

							}

							Map<String, Object> map = new HashMap<String, Object>();
							map.put("emailDTOList", emailDTOList);
							map.put("count", total);
							map.put("read0", isRead);
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
