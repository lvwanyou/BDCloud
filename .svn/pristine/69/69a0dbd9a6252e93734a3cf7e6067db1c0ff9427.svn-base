package com.xl.cloud.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.highlight.HighlightField;
import org.junit.Test;

import com.xl.cloud.bean.BankDetail;
import com.xl.cloud.bean.BillDetail;
import com.xl.cloud.bean.ContainerDetail;
import com.xl.cloud.bean.CreditDetail;
import com.xl.cloud.bean.EmlDetail;
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
import com.xl.cloud.bean.WechatDetail;
import com.xl.cloud.common.Global;
import com.xl.cloud.dao.SqlDao;

/**
 * 数据挖掘功能,在一个案件建立索引后，将数据信息中银行卡号，身份证号等
 * 
 * @author lcl
 *
 */
public class SearchAllUtil {
	private static SqlDao sqlDao = new SqlDao();
	private static String[] dataArr = new String[] { "手机号",  "集装箱号","银行卡号", "身份证号","邮箱号", "信用证",  "运输车号", "护照", "电汇",
			"车牌号", "固定电话", "QQ", "微信", "推特号", "支付宝", "发票", "价格" };

	public static void dataMining(String caseId, String evuuid) {
		for (int i = 0; i < dataArr.length; i++) {
			try {
				// searchAll(dataArr[i], caseId, evuuid);
				if (dataArr[i].equals("手机号")) {
					System.out.println("手机号====");
					teleToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("身份证号")) {
					System.out.println("身份证号====");
					identityToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("银行卡号")) {
					System.out.println("银行卡号====");
					bankToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("邮箱号")) {
					System.out.println("邮箱号====");
					emlToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("信用证")) {
					System.out.println("信用证====");
					creditToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("集装箱号")) {
					System.out.println("集装箱号====");
					containerToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("运输车号")) {
					System.out.println("运输车号====");
					lateToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("护照")) {
					System.out.println("护照====");
					passportToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("电汇")) {
					System.out.println("电汇====");
					transferToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("车牌号")) {
					System.out.println("车牌号====");
					plateToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("固定电话")) {
					System.out.println("固定电话====");
					fixedToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("QQ")) {
					System.out.println("QQ====");
					qqToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("微信")) {
					System.out.println("微信====");
					wechatToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("推特号")) {
					System.out.println("推特号====");
					TwitterDetailToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("支付宝")) {
					System.out.println("支付宝====");
					PaymentDetailToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("发票")) {
					System.out.println("发票====");
					BillDetailToDB(caseId, evuuid);
				}
				if (dataArr[i].equals("价格")) {
					System.out.println("价格====");
					PriceDetailToDB(caseId, evuuid);
				}

			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println(e.getMessage());
				 continue;
			}
		}
	}

	public static Map<String, Object> searchAll(String regexpQuery, String caseId, String evuuid) throws Exception {

		List<String> list = new ArrayList<String>();
		Map<String, Object> map = null;
		try {

			// 精确搜索
			BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
			// mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件
			// 此处为匹配所有文档
			System.out.println("传进来的++" + regexpQuery);
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
			} else if ("电汇".equals(regexpQuery)) {
				quickflag = Global.regTT;
			} else if ("信用证".equals(regexpQuery)) {
				quickflag = Global.regLC;
			} else if ("护照".equals(regexpQuery)) {
				quickflag = Global.passport;
			} else if ("微信".equals(regexpQuery)) {
				quickflag = Global.weChat;
			} else if ("QQ".equals(regexpQuery)) {
				quickflag = Global.QQnumber;
			} else if ("推特号".equals(regexpQuery)) {
				quickflag = Global.twitter;
			} else if ("支付宝".equals(regexpQuery)) {
				quickflag = Global.regUP;
			} else if ("价格".equals(regexpQuery)) {
				quickflag = Global.regPrice;
			} else if ("发票".equals(regexpQuery)) {
				quickflag = Global.regStamp;
			}

			/*
			 * if (!"".equals(quickflag)) { QueryBuilder qb =
			 * QueryBuilders.regexpQuery("content", quickflag);
			 * mustQuery.should(qb); }
			 */

//			if (!"".equals(caseId)) {
//				// QueryBuilder qb = QueryBuilders.matchPhraseQuery("caseID",
//				// caseId);
//				QueryBuilder qb = QueryBuilders.termQuery("caseID", caseId);
//				mustQuery.must(qb);
//			}

			if (!"".equals(evuuid)) {
				QueryBuilder qb = QueryBuilders.termQuery("evID", evuuid);
				mustQuery.must(qb);
			}
			/*
			 * // 组合 模糊查询 should BoolQueryBuilder evdenceuuid =
			 * QueryBuilders.boolQuery(); MatchQueryBuilder urluuid =
			 * QueryBuilders.matchPhraseQuery("evID", "*"+evuuid+"*");
			 * evdenceuuid.should(urluuid); mustQuery.must(evdenceuuid);
			 */

			// "doc","docType",
			SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("es", "doc")
					.setTypes("email", "docType").setQuery(mustQuery);
			// .addHighlightedField("*")
			// /* 星号表示在所有字段都高亮 */.setHighlighterRequireFieldMatch(false)//
			// 配置高亮显示搜索结果
			// .setHighlighterPreTags("<font color='red'
			// >").setHighlighterPostTags("</font>");// 配置高亮显示搜索结果

			SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
			SearchHit[] hits = searchResponse.getHits().getHits();
			// int totalHits = (int) searchResponse.getHits().getTotalHits();
			// System.out.println(hits);
			// String fileName = "";
			System.out.println("hits  =====" + hits.length);
			if (hits.length > 0) {
				for (SearchHit searchHit : hits) {
					Map<String, Object> source = searchHit.getSource();
					String downloadUrl = (String) source.get("file_download_url");
					String content = (String) source.get("content");
					/*
					 * if ("".equals(fileName)) { fileName += downloadUrl;
					 * //.split("/")[8]; 截取文件名 } else { fileName += "↑↑" +
					 * downloadUrl; }
					 */
					Pattern pattern = Pattern.compile(quickflag);
					// 创建匹配给定输入与此模式的匹配器。
					Matcher matcher = pattern.matcher(content);

					System.out.println("matcher  ===" + matcher);
					// 查找字符串中是否有符合的子字符串
					while (matcher.find()) {
						// 查找到符合的即输出
						String group = matcher.group();
						if (!"Email".equals(group) && !"email".equals(group)) {
							list.add(group + "↓↓" + downloadUrl);
						}
					}
					/*
					 * Map<String, HighlightField> highlightFields =
					 * searchHit.getHighlightFields(); HighlightField
					 * highlightcontent = highlightFields.get("content");
					 * if(highlightcontent!=null){ if
					 * ((highlightcontent.toString()).indexOf("font") != -1) {
					 * String gaoliang = highlightcontent.toString(); String
					 * gaoliang1 = gaoliang.split("font")[1]; String gaoliang2 =
					 * gaoliang1.substring(14, gaoliang1.length() - 2);
					 * if(!"Email".equals(gaoliang2)&&!"email".equals(gaoliang2)
					 * ){ list.add(gaoliang2+"↓↓"+downloadUrl); } } }
					 */
				}
			}
			
			map = new HashMap<>();
			map.put("list", list);
			// map.put("fileName", fileName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的电话号
	 * 
	 */
	public static void teleToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("手机号", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("手机号");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		TeleDetail teleDetail = null;
		if (listStr != null) {
			for (String teleNum : listStr) {
				teleDetail = new TeleDetail();
				teleDetail.setCaseId(caseId);
				teleDetail.setTeleNum(teleNum.split("↓↓")[0]);
				List<TeleDetail> listfromMysql = sqlDao.getListfromMysql(teleDetail);
				fileName = teleNum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					TeleDetail teleDetail2 = listfromMysql.get(0);
					teleDetail2.setCount(teleDetail2.getCount() + 1);
					teleDetail2.setEvid(teleDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(teleDetail2);
				} else {
					teleDetail.setBelongCity("北京");
					teleDetail.setBelongProvince("北京");
					teleDetail.setTeleType("联通");
					teleDetail.setCount(1);
					teleDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(teleDetail);
				}
			}
		}

	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的身份证号
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void identityToDB(String caseId, String evuuid) throws Exception {
		System.out.println("进入身份证号");
		Map<String, Object> searchAll = searchAll("身份证号", caseId, evuuid);

		System.out.println("searchAll===" + searchAll);
		if (searchAll == null) {
			return;
		}
		System.out.println("身份");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		IdentityDetail identityDetail = null;
		if (listStr != null) {
			System.out.println("进入身份校验====");
			for (String idenNum : listStr) {
				String s = idenNum.split("↓↓")[0];// 传入的身份证号
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
				String lastbit = list.get(list.size() - 1);
				if ("1".equals(lastbit)) {
					lastbit = "0";
				} else if ("0".equals(lastbit)) {
					lastbit = "1";
				} else if ("X".equals(lastbit) || "x".equals(lastbit)) {
					lastbit = "2";
				} else if ("9".equals(lastbit)) {
					lastbit = "3";
				} else if ("8".equals(lastbit)) {
					lastbit = "4";
				} else if ("7".equals(lastbit)) {
					lastbit = "5";
				} else if ("6".equals(lastbit)) {
					lastbit = "6";
				} else if ("5".equals(lastbit)) {
					lastbit = "7";
				} else if ("4".equals(lastbit)) {
					lastbit = "8";
				} else if ("3".equals(lastbit)) {
					lastbit = "9";
				} else if ("2".equals(lastbit)) {
					lastbit = "10";
				}
				double c = 0;
				double num = 0;
				for (int i = 0; i < list2.size(); i++) {
					int a = (Integer.parseInt(list.get(i)));
					int b = (Integer.parseInt(list2.get(i)));
					double num2 = a * b;
					num += num2;
					c = (num) % 11;
				}
				int x = Integer.parseInt(lastbit);
				if (c == x) {
					System.out.println("校验成功身份证++++++++");
					identityDetail = new IdentityDetail();
					identityDetail.setCaseId(caseId);
					identityDetail.setIdentityNum(idenNum.split("↓↓")[0]);
					List<IdentityDetail> listfromMysql = sqlDao.getListfromMysql(identityDetail);
					fileName = idenNum.split("↓↓")[1];
					if (listfromMysql.size() > 0) {
						IdentityDetail identityDetail2 = listfromMysql.get(0);
						identityDetail2.setCount(identityDetail2.getCount() + 1);
						identityDetail2.setEvid(identityDetail2.getEvid() + "↑↑" + fileName);
						sqlDao.updateToMysql(identityDetail2);
					} else {
						identityDetail.setBirthdate("1990-08-17");
						identityDetail.setGender("男");
						identityDetail.setBelongarea("北京");
						identityDetail.setCount(1);
						identityDetail.setEvid(fileName);
						sqlDao.setBeanToMysql(identityDetail);
					}
				}
			}
		}

	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的车牌号
	 * 
	 */
	public static void plateToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("车牌号", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("che");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		PlateDetail plateDetail = null;
		if (listStr != null) {
			for (String platenum : listStr) {
				plateDetail = new PlateDetail();
				plateDetail.setCaseId(caseId);
				plateDetail.setPlatenum(platenum.split("↓↓")[0]);
				List<PlateDetail> listfromMysql = sqlDao.getListfromMysql(plateDetail);
				fileName = platenum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					PlateDetail plateDetail2 = listfromMysql.get(0);
					plateDetail2.setCount(plateDetail2.getCount() + 1);
					plateDetail2.setEvid(plateDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(plateDetail2);
				} else {
					plateDetail.setPlateCity("湖南");
					plateDetail.setPlateType("轿车");
					plateDetail.setCount(1);
					plateDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(plateDetail);
				}
			}
		}
	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的电汇号信息
	 * 
	 */
	public static void transferToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("电汇", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("电汇");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		TransferDetail transferDetail = null;
		if (listStr != null) {
			for (String transfernum : listStr) {
				transferDetail = new TransferDetail();
				transferDetail.setCaseId(caseId);
				transferDetail.setTransfernum(transfernum.split("↓↓")[0]);
				List<TransferDetail> listfromMysql = sqlDao.getListfromMysql(transferDetail);
				fileName = transfernum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					TransferDetail transferDetail2 = listfromMysql.get(0);
					transferDetail2.setCount(transferDetail2.getCount() + 1);
					transferDetail2.setEvid(transferDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(transferDetail2);
				} else {
					transferDetail.setCount(1);
					transferDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(transferDetail);
				}
			}
		}
	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的运输车号信息
	 * 
	 */
	public static void lateToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("运输车号", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("运输车");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		TransportDetail transportDetail = null;
		if (listStr != null) {
			for (String transportnum : listStr) {
				transportDetail = new TransportDetail();
				transportDetail.setCaseId(caseId);
				transportDetail.setTransportnum(transportnum.split("↓↓")[0]);
				List<TransportDetail> listfromMysql = sqlDao.getListfromMysql(transportDetail);
				fileName = transportnum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					TransportDetail transportDetail2 = listfromMysql.get(0);
					transportDetail2.setCount(transportDetail2.getCount() + 1);
					transportDetail2.setEvid(transportDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(transportDetail2);
				} else {
					transportDetail.setCount(1);
					transportDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(transportDetail);
				}
			}
		}

	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的护照号信息
	 * 
	 */
	public static void passportToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("护照", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("护照");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		PassportDetail passportDetail = null;
		if (listStr != null) {
			for (String passportnum : listStr) {
				passportDetail = new PassportDetail();
				passportDetail.setCaseId(caseId);
				passportDetail.setPassportnum(passportnum.split("↓↓")[0]);
				List<PassportDetail> listfromMysql = sqlDao.getListfromMysql(passportDetail);
				fileName = passportnum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					PassportDetail passportDetail2 = listfromMysql.get(0);
					passportDetail2.setCount(passportDetail2.getCount() + 1);
					passportDetail2.setEvid(passportDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(passportDetail2);
				} else {
					passportDetail.setCount(1);
					passportDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(passportDetail);
				}
			}
		}

	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的固定电话号码信息
	 * 
	 */
	public static void fixedToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("固定电话", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("电话");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		FixedDetail fixedDetail = new FixedDetail();
		if (listStr != null) {
			for (String fixednum : listStr) {
				fixedDetail = new FixedDetail();
				fixedDetail.setCaseId(caseId);
				fixedDetail.setFixednum(fixednum.split("↓↓")[0]);
				List<FixedDetail> listfromMysql = sqlDao.getListfromMysql(fixedDetail);
				fileName = fixednum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					FixedDetail fixedDetail2 = listfromMysql.get(0);
					fixedDetail2.setCount(fixedDetail2.getCount() + 1);
					fixedDetail2.setEvid(fixedDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(fixedDetail2);
				} else {
					fixedDetail.setFixedCity("贵阳");
					fixedDetail.setFixedProvince("贵州");
					fixedDetail.setCount(1);
					fixedDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(fixedDetail);
				}
			}
		}
	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的邮箱号信息
	 * 
	 */
	public static void emlToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("邮箱号", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("邮箱");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		EmlDetail emlDetail = null;
		if (listStr != null) {
			for (String emlnum : listStr) {
				// 查找到符合的即SET
				emlDetail = new EmlDetail();
				emlDetail.setCaseId(caseId);
				emlDetail.setEmlnum(emlnum.split("↓↓")[0]);
				List<EmlDetail> listfromMysql = sqlDao.getListfromMysql(emlDetail);
				fileName = emlnum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					EmlDetail emlDetail2 = listfromMysql.get(0);
					emlDetail2.setCount(emlDetail2.getCount() + 1);
					emlDetail2.setEvid(emlDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(emlDetail2);
				} else {
					emlDetail.setEmlType("QQ");
					emlDetail.setCount(1);
					emlDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(emlDetail);
				}
			}
		}
	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的信用证号信息
	 * 
	 */
	public static void creditToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("信用证", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("信用");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		CreditDetail creditDetail = null;
		if (listStr != null) {
			for (String creditnum : listStr) {
				// 查找到符合的即SET
				creditDetail = new CreditDetail();
				creditDetail.setCaseId(caseId);
				creditDetail.setCreditnum(creditnum.split("↓↓")[0]);
				List<CreditDetail> listfromMysql = sqlDao.getListfromMysql(creditDetail);
				fileName = creditnum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					CreditDetail creditDetail2 = listfromMysql.get(0);
					creditDetail2.setCount(creditDetail2.getCount() + 1);
					creditDetail2.setEvid(creditDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(creditDetail2);
				} else {
					creditDetail.setCount(1);
					creditDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(creditDetail);
				}
			}
		}
	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的集装箱号信息
	 * 
	 */
	public static void containerToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("集装箱号", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("集装箱");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		ContainerDetail containerDetail = null;
		if (listStr != null) {
			for (String containernum : listStr) {
				String s = containernum.split("↓↓")[0];// 传入的集装箱号
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < s.length(); i++) {
					String substring = s.substring(i, i + 1);
					String replace = substring.replace("A", "10").replace("B", "12").replace("C", "13")
							.replace("D", "14").replace("E", "15").replace("F", "16").replace("G", "17")
							.replace("H", "18").replace("I", "19").replace("J", "20").replace("K", "21")
							.replace("L", "23").replace("M", "24").replace("N", "25").replace("O", "26")
							.replace("P", "27").replace("Q", "28").replace("R", "29").replace("S", "30")
							.replace("T", "31").replace("U", "32").replace("V", "34").replace("W", "35")
							.replace("X", "36").replace("Y", "37").replace("Z", "38");
					list.add(replace);
				}
				double c = 0;
				double num = 0;
				for (int i = 0; i < list.size() - 1; i++) {
					double d = Math.pow(2, i);
					// System.out.println("n" + i + "次方" + d);
					int a = (Integer.parseInt(list.get(i)));
					double num2 = a * d;
					num += num2;
					c = (num) % 11;
				}
				int x = Integer.parseInt(list.get(list.size() - 1));
				if (c == x) {
					containerDetail = new ContainerDetail();
					containerDetail.setCaseId(caseId);
					containerDetail.setContainernum(s);
					List<ContainerDetail> listfromMysql = sqlDao.getListfromMysql(containerDetail);
					fileName = containernum.split("↓↓")[1];
					if (listfromMysql.size() > 0) {
						ContainerDetail container2 = listfromMysql.get(0);
						container2.setCount(container2.getCount() + 1);
						container2.setEvid(container2.getEvid() + "↑↑" + fileName);
						sqlDao.updateToMysql(container2);
					} else {
						containerDetail.setCount(1);
						containerDetail.setEvid(fileName);
						sqlDao.setBeanToMysql(containerDetail);
					}
				}

			}
		}
	}

	// 银行卡校验
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		System.out.println("进入银行卡校验");
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")
				|| nonCheckCodeCardId.trim().length() < 15 || nonCheckCodeCardId.trim().length() > 18) {
			// 如果传的数据不合法返回N
			System.out.println("银行卡号不合法！");
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
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的银行卡号信息
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void bankToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("银行卡号", caseId, evuuid);
		System.out.println("进入银行卡号阶段");
		if (searchAll == null) {
			return;
		}
		System.out.println("银行");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		BankDetail bankDetail = null;
		if (listStr != null) {
			for (String banknum : listStr) {
				String s = banknum.split("↓↓")[0];// 传入的身份证号
				char res = getBankCardCheckCode(s.substring(0, s.length() - 1));

				System.out.println("银行卡校验返回值+" + res);
				if (res != 'N') {
					String charJX = s.substring(s.length() - 1);// 存入不含校验位的卡号
					if (charJX.equals(String.valueOf(res))) {
						bankDetail = new BankDetail();
						bankDetail.setCaseId(caseId);
						bankDetail.setBanknum(banknum.split("↓↓")[0]);
						List<BankDetail> listfromMysql = sqlDao.getListfromMysql(bankDetail);
						fileName = banknum.split("↓↓")[1];
						if (listfromMysql.size() > 0) {
							BankDetail bankDetail2 = listfromMysql.get(0);
							bankDetail2.setCount(bankDetail2.getCount() + 1);
							bankDetail2.setEvid(bankDetail2.getEvid() + "↑↑" + fileName);
							sqlDao.updateToMysql(bankDetail2);
						} else {
							// bankDetail.setBankname("民生银行");
							// bankDetail.setBankType("工资卡");
							// bankDetail.setBankCity("上海");
							// bankDetail.setBankaddress("徐汇支行");
							bankDetail.setCount(1);
							bankDetail.setEvid(fileName);
							sqlDao.setBeanToMysql(bankDetail);
						}
					}
				}

			}
		}

	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的QQ号信息
	 * 
	 */
	public static void qqToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("QQ", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("QQ");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		QqDetail qqDetail = null;
		if (listStr != null) {
			for (String qqnum : listStr) {
				qqDetail = new QqDetail();
				qqDetail.setCaseId(caseId);
				qqDetail.setQqnum(qqnum.split("↓↓")[0]);
				List<QqDetail> listfromMysql = sqlDao.getListfromMysql(qqDetail);
				fileName = qqnum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					QqDetail qqDetail2 = listfromMysql.get(0);
					qqDetail2.setCount(qqDetail2.getCount() + 1);
					qqDetail2.setEvid(qqDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(qqDetail2);
				} else {
					qqDetail.setCount(1);
					qqDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(qqDetail);
				}
			}
		}
	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的微信信息
	 * 
	 */
	public static void wechatToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("微信", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("微信");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		WechatDetail wechatDetail = null;
		if (listStr != null) {
			for (String wechatnum : listStr) {
				wechatDetail = new WechatDetail();
				wechatDetail.setCaseId(caseId);
				wechatDetail.setWechatnum(wechatnum.split("↓↓")[0]);
				List<WechatDetail> listfromMysql = sqlDao.getListfromMysql(wechatDetail);
				fileName = wechatnum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					WechatDetail wechatDetail2 = listfromMysql.get(0);
					wechatDetail2.setCount(wechatDetail2.getCount() + 1);
					wechatDetail2.setEvid(wechatDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(wechatDetail2);
				} else {
					wechatDetail.setCount(1);
					wechatDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(wechatDetail);
				}
			}
		}
	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的推特信息
	 * @throws Exception
	 * 
	 */
	public static void TwitterDetailToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("推特号", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("推特");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		TwitterDetail twitterDetail = null;
		if (listStr != null) {
			for (String twitternum : listStr) {
				twitterDetail = new TwitterDetail();
				twitterDetail.setCaseId(caseId);
				twitterDetail.setTwitternum(twitternum.split("↓↓")[0]);
				List<TwitterDetail> listfromMysql = sqlDao.getListfromMysql(twitterDetail);
				fileName = twitternum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					TwitterDetail twitterDetail2 = listfromMysql.get(0);
					twitterDetail2.setCount(twitterDetail2.getCount() + 1);
					twitterDetail2.setEvid(twitterDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(twitterDetail2);
				} else {
					twitterDetail.setCount(1);
					twitterDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(twitterDetail);
				}
			}
		}
	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的支付宝信息
	 * 
	 */
	public static void PaymentDetailToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("支付宝", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("支付宝");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		PaymentDetail paymentDetail = null;
		if (listStr != null) {
			for (String paymentnum : listStr) {
				paymentDetail = new PaymentDetail();
				paymentDetail.setCaseId(caseId);
				paymentDetail.setPaymentnum(paymentnum.split("↓↓")[0]);
				List<PaymentDetail> listfromMysql = sqlDao.getListfromMysql(paymentDetail);
				fileName = paymentnum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					PaymentDetail paymentDetail2 = listfromMysql.get(0);
					paymentDetail2.setCount(paymentDetail2.getCount() + 1);
					paymentDetail2.setEvid(paymentDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(paymentDetail2);
				} else {
					paymentDetail.setCount(1);
					paymentDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(paymentDetail);
				}
			}
		}

	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的发票信息
	 * @throws Exception
	 * 
	 */
	public static void BillDetailToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("发票", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("发票");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		BillDetail billDetail = null;
		if (listStr != null) {
			for (String billnum : listStr) {
				billDetail = new BillDetail();
				billDetail.setCaseId(caseId);
				billDetail.setBillnum(billnum.split("↓↓")[0]);
				List<BillDetail> listfromMysql = sqlDao.getListfromMysql(billDetail);
				fileName = billnum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					BillDetail billDetail2 = listfromMysql.get(0);
					billDetail2.setCount(billDetail2.getCount() + 1);
					billDetail2.setEvid(billDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(billDetail2);
				} else {
					billDetail.setCount(1);
					billDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(billDetail);
				}
			}
		}
	}

	/**
	 * 
	 * @param regexpQuery
	 * @param caseId
	 *            根据案件ID 查询出相应的价格信息
	 * @throws Exception
	 * 
	 */
	public static void PriceDetailToDB(String caseId, String evuuid) throws Exception {
		Map<String, Object> searchAll = searchAll("价格", caseId, evuuid);
		if (searchAll == null) {
			return;
		}
		System.out.println("价格");
		// String fileName = (String) searchAll.get("fileName");
		List<String> listStr = (List<String>) searchAll.get("list");
		String fileName = "";
		PriceDetail priceDetail = null;
		if (listStr != null) {
			for (String pricenum : listStr) {
				priceDetail = new PriceDetail();
				priceDetail.setCaseId(caseId);
				priceDetail.setPricenum(pricenum.split("↓↓")[0]);
				List<PriceDetail> listfromMysql = sqlDao.getListfromMysql(priceDetail);
				fileName = pricenum.split("↓↓")[1];
				if (listfromMysql.size() > 0) {
					PriceDetail priceDetail2 = listfromMysql.get(0);
					priceDetail2.setCount(priceDetail2.getCount() + 1);
					priceDetail2.setEvid(priceDetail2.getEvid() + "↑↑" + fileName);
					sqlDao.updateToMysql(priceDetail2);
				} else {
					priceDetail.setCount(1);
					priceDetail.setEvid(fileName);
					sqlDao.setBeanToMysql(priceDetail);
				}
			}
		}
	}

	// 主线程
	public static void main(String[] args) {
		//SearchAllUtil.dataMining(2297 + "", "3140");
		/*
		 * String bankNo = "6217850500003632316"; char res =
		 * getBankCardCheckCode(bankNo.substring(0, bankNo.length()-1));
		 * if(res!='N'){ System.out.println("校验结果为："+res); String charJX =
		 * bankNo.substring(bankNo.length()-1);//存入不含校验位的卡号
		 * System.out.println("银行卡的校验位为："+charJX);
		 * if(charJX.equals(String.valueOf(res))){ System.out.println("银行卡合法！");
		 * }else{ System.out.println("银行卡不合法！"); } }
		 */
		/*
		 * int sumOdd = 0; int sumEven = 0; String number =
		 * "6228480419292040179"; int length = number.length(); int[] wei = new
		 * int[length]; for (int i = 0; i < number.length(); i++) { wei[i] =
		 * Integer.parseInt(number.substring(length - i - 1, length - i));//
		 * 从最末一位开始提取，每一位上的数值 System.out.println("第" + i + "位数字是：" + wei[i]); }
		 * for (int i = 0; i < length / 2; i++) { sumOdd += wei[2 * i]; if
		 * ((wei[2 * i + 1] * 2) > 9) wei[2 * i + 1] = wei[2 * i + 1] * 2 - 9;
		 * else wei[2 * i + 1] *= 2; sumEven += wei[2 * i + 1]; }
		 * System.out.println("奇数位的和是：" + sumOdd); System.out.println("偶数位的和是："
		 * + sumEven); if ((sumOdd + sumEven) % 10 == 0){
		 * System.out.println("Recept."); }else{ System.out.println(
		 * "Can not recept."); }
		 */
	}

	 @Test
	public void test12() {
		/*
		 * String s = "CBHU3202732";// 初次获取得到集装箱号 List<String> list = new
		 * ArrayList<String>(); for (int i = 0; i < s.length(); i++) { String
		 * substring = s.substring(i, i + 1); String replace =
		 * substring.replace("A", "10").replace("B", "12").replace("C",
		 * "13").replace("D", "14") .replace("E", "15").replace("F",
		 * "16").replace("G", "17").replace("H", "18").replace("I", "19")
		 * .replace("J", "20").replace("K", "21").replace("L",
		 * "23").replace("M", "24").replace("N", "25") .replace("O",
		 * "26").replace("P", "27").replace("Q", "28").replace("R",
		 * "29").replace("S", "30") .replace("T", "31").replace("U",
		 * "32").replace("V", "34").replace("W", "35").replace("X", "36")
		 * .replace("Y", "37").replace("Z", "38"); list.add(replace);
		 * //System.out.println("aaaaa" + list.get(i)); } double c = 0; for (int
		 * i = 0; i < list.size(); i++) double d = Math.pow(2, i);
		 * //System.out.println("n" + i + "次方" + d); int a =
		 * (Integer.parseInt(list.get(i))); double num = a * d; c = (num++) %
		 * 11; } int x = Integer.parseInt(list.get(list.size() - 1)); if (c ==
		 * x) { System.out.println(s); }
		 */
		/*
		 * String s = "430522199709198074"; List<String> list = new
		 * ArrayList<String>(); List<String> list2 = new ArrayList<String>();
		 * list2.add("7"); list2.add("9"); list2.add("10"); list2.add("5");
		 * list2.add("8"); list2.add("4"); list2.add("2"); list2.add("1");
		 * list2.add("6"); list2.add("3"); list2.add("7"); list2.add("9");
		 * list2.add("10"); list2.add("5"); list2.add("8"); list2.add("4");
		 * list2.add("2"); for (int i = 0; i < s.length(); i++) { String
		 * substring = s.substring(i, i + 1); list.add(substring); } String
		 * lastbit=list.get(list.size() - 1); if("1".equals(lastbit)){
		 * lastbit="0"; }else if ("0".equals(lastbit)) { lastbit="1"; }else if
		 * ("X".equals(lastbit)||"x".equals(lastbit)) { lastbit="2"; }else if
		 * ("9".equals(lastbit)) { lastbit="3"; }else if ("8".equals(lastbit)) {
		 * lastbit="4"; }else if ("7".equals(lastbit)) { lastbit="5"; }else if
		 * ("6".equals(lastbit)) { lastbit="6"; }else if ("5".equals(lastbit)) {
		 * lastbit="7"; }else if ("4".equals(lastbit)) { lastbit="8"; }else if
		 * ("3".equals(lastbit)) { lastbit="9"; }else if ("2".equals(lastbit)) {
		 * lastbit="10"; } double c = 0; double num = 0 ; for (int i = 0; i <
		 * list2.size(); i++) { int a = (Integer.parseInt(list.get(i))); int b =
		 * (Integer.parseInt(list2.get(i))); double num2 = a * b; num+=num2; c =
		 * (num) % 11; } int x = Integer.parseInt(lastbit); if (c == x) {
		 * System.out.println("合格+++++"); }
		 */
		// 目标文本
		// String content="沪A2968学 浙C365W5430CNY 15.34RMB 324 $ 432523￥
		// 52219970919807x QQ：740556217 护照 P45511245 SKYPE: 集装箱号 awrf4551132 qq
		// 740556217 0.52352512元 ￥2852152 $3.2 $3.2rr CNY222.255 RMB25.2 发票
		// 0354874155552推特号 a45511245 微信 15787455562 微信 157-8745-5562 qq
		// 7282352334221312313121343232ss 6222021001004480455 微信226000@qq.com
		// sfsdfdsfs@163.com 1231546489710 沪A29685 浙C365W5 310105196312120103
		// 116796349 微信 catcat555 推特 twdlll@126.com E50806300
		// zhifubao@hotmail.com 021-58651125 I93110282 运输 C62BK 4623665
		// 5500033333 55er432223 66666 防守端发送方34234防守对方打死的人确认阿三的发达省份沙发上的方法";

	/*	String string2 = "013884124572------12444523264-----15745425412-------143722154512";
		String quickflag3 = "((13|14|15|17|18)[0-9]{9}|(13|14|15|17|18)[0-9]{1}-[0-9]{4}-[0-9]{4}|手机[：: ](13|14|15|17|18)[0-9]{1}-[0-9]{4}-[0-9]{4})";
		Pattern pattern = Pattern.compile(quickflag3);
		Matcher matcher = pattern.matcher(string2);
		while (matcher.find()) {
			String temp = matcher.group();
			System.out.println(temp);
		}*/
		dataMining("2299", "3151");
	}

}
