package com.xl.cloud.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xl.cloud.bean.Caseinfo;
import com.xl.cloud.bean.ClueWarn;
import com.xl.cloud.bean.Dealinfo;
import com.xl.cloud.bean.Evidence;
import com.xl.cloud.bean.IndexQueue;
import com.xl.cloud.bean.SuspectInfo;
import com.xl.cloud.common.Global;
import com.xl.cloud.common.OCR;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.DateUtil;
import com.xl.cloud.util.EsClient;
import com.xl.cloud.util.JsonUtil;
import com.xl.cloud.util.SearchAllUtil;

@Controller
public class BuildCollection {
	private SqlDao dao = new SqlDao();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	SimpleDateFormat sdf_bz = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf_bz2 = new SimpleDateFormat("yyyy-MM-dd");
	final Logger logger = Logger.getLogger(BuildCollection.class);
	FileOutputStream fos = null;

	public String readConf(String filePath, String collectionName) {
		BufferedReader br = null;
		String line = null;
		StringBuffer buf = new StringBuffer();

		try {
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
				if (line.contains("<dataDir>") && line.contains("</dataDir>")) {
					String dataDir = "<dataDir>${solr.data.dir:parastorfs:///output/" + collectionName
							+ "_collection}</dataDir>";
					buf.append(dataDir);
				}
				// 如果不用修改, 则按原来的内容回写
				else {
					buf.append(line);
				}
				buf.append(System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
				}
			}
		}

		return buf.toString();
	}

	public void writeConf(String filePath, String content) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(filePath));
			bw.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					bw = null;
				}
			}
		}
	}

	int mapPercent = 0;
	String collectionName = "";
	int evID = 0;
	String dirPath;
	int uptype = 0;
	String evType = "0";
	int caseId = 0;
	String evSize = "";

	/**
	 * 解压上传完成后，通过evidence的unzipFinished字段判断是否开始执行建立索引
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param map
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/unzipFinished.php")
	public void unzipFinished(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map<String, Object> map) throws IOException, InterruptedException {
		logger.info("---------------into unzipFinished ------------");
		PrintWriter pw = response.getWriter();
		String evIDStr = request.getParameter("evID");
		System.out.println("vinto unzipFinish    "+evIDStr);
		Evidence evidence = new Evidence();
		final String userName = request.getParameter("userName");
		mapPercent = 0;
		collectionName = "";
		evID = 0;
		dirPath = "";
		uptype = 0;
		evSize = "";
		if (!StringUtils.isEmpty(evIDStr)) {
			evID = Integer.parseInt(evIDStr);
			evidence.setId(evID);
			List<Evidence> evlist = dao.getListfromMysql(evidence, 0, 1);
			if (evlist.size() > 0) {
				evidence = evlist.get(0);
				collectionName = evidence.getEvName();
				dirPath = evidence.getDirPath();
				uptype = evidence.getUptype();
				caseId = evidence.getCaseID();
				logger.info("uptype---------------" + uptype);
				evType = evidence.getEvType() + "";
				evSize = evidence.getEvSize();
				String percentStr = evidence.getPercent();
				if (!StringUtils.isEmpty(percentStr)) {
					mapPercent = Integer.parseInt(percentStr);
				}
			}
		}
		logger.info("evID---------------" + evID);

		System.out.println("collectionName:" + collectionName);

		if (collectionName.equals("")) {
			pw.write("{\"status\":\"\"}");
			pw.flush();
			if (pw != null) {
				pw.close();
			}
			return;
		}
		Evidence searchEvi = new Evidence();
		searchEvi.setFinished("true");
		searchEvi.setStatus("on");
		searchEvi.setId(evID);
		final int eviTempID = evidence.getId();
		List<Evidence> evidences = dao.getListfromMysql(searchEvi);
		logger.info("---------------evidences------------" + evidences.size());

		if (evidences.size() > 0) {
			logger.info("---------------start indexing ------------");
			session.setAttribute("NameNode", Global.NameNodeIP);
			Runnable mainRun = new Runnable() {
				@Override
				public void run() {
					try {

						/**
						 * 任务开始之前存储任务处理状态
						 */
						Dealinfo dealInfo = new Dealinfo();
						dealInfo.setEvID(eviTempID);
						logger.info("---------------into indexing run------------" + eviTempID);
						Process process = null;
						String command = "";
						logger.info("uptype------***" + uptype);
						logger.info("dirPath---------" + dirPath);
						logger.info("---------" + Global.TYPE);

						/**
						 * 执行 spark 命令，传入的参数是
						 */
						command = "sudo -u root  /usr/local/data/lib/es/sparkES.sh har://hdfs-emailService" +dirPath+"/*";	

						System.out.println("evType  ===  &&&&&&&&&&&&&&&&&&&  " + evType);
						/**
						 * 根据上传的证据类型，执行不同的建索引的类
						 */
						if (evType.equals("1")) {// 邮件
							Caseinfo case_temp = new Caseinfo();
							case_temp.setId(caseId);
							List<Caseinfo> cases = dao.getListfromMysql(case_temp);
							String caseName = "";
							if (cases.size() > 0) {
								caseName = cases.get(0).getCaseName();
							}
							command += " com.parseemail.es.ESParseEmail" + " " + caseId+ " " + caseName+" "+evID;
						} else if (evType.equals("2")) {// 文档
							// 文档类型需要提供案件名称和案件类型
							Caseinfo case_temp = new Caseinfo();
							case_temp.setId(caseId);
							List<Caseinfo> cases = dao.getListfromMysql(case_temp);
							String caseName = "";
							String caseType = "";
							if (cases.size() > 0) {
								caseName = cases.get(0).getCaseName();
								caseType = cases.get(0).getCaseType();
							}
							command += " com.parseemail.es.ESParseDoc " + caseName + " " + caseType + " " + caseId+" "+evID;
						} else if (evType.equals("3")) {// 话单
							command += " com.parseemail.es.ESParseCallList" + " " + caseId;
						} else if (evType.equals("4")) {// 手机取证
							command += " com.parseemail.es.ESParseHackerDB" + " " + caseId;
						} else if (evType.equals("5")) {// 黑客数据库
							command += " com.parseemail.es.ESParseHackerDB" + " " + caseId;
						} else if (evType.equals("6")) {// 图片数据直接建索引，暂时不用spark解析
							//
							String startTime = sdf_bz.format(new Date());
							System.out.println("《《索引开始创建时间》》" + startTime);
							Caseinfo ca = new Caseinfo();
							ca.setId(caseId);
							List<Caseinfo> list = dao.getListfromMysql(ca);
							Caseinfo ca2 = list.get(0);
							String cn = ca2.getCaseName();
							IndexQueue iq = new IndexQueue();
							iq.setUserName(userName);
							iq.setCaseID(caseId);
							iq.setCaseName(cn);
							iq.setDataName(collectionName);
							iq.setDataType(evType);
							iq.setDataSize(evSize);
							iq.setStartTime(startTime);
							iq.setRunningState(3);
							dao.setBeanToMysql(iq);
							//
							Evidence searchEvi = new Evidence();
							searchEvi.setId(evID);
							List<Evidence> evidences = dao.getListfromMysql(searchEvi);
							if (evidences.size() > 0) {
								Caseinfo case_temp = new Caseinfo();
								case_temp.setId(caseId);
								List<Caseinfo> cases = dao.getListfromMysql(case_temp);
								String caseName = "";
								if (cases.size() > 0) {
									caseName = cases.get(0).getCaseName();
								}
								Evidence e = evidences.get(0);
								e.getCaseID();
								e.getUUID();
								// 文件路径
								String picPath = e.getDirPath();
								String evName =e.getEvName();
								// 获取文件
								if(picPath.contains("_")){
									String path = "hadoop fs -copyToLocal " + "/tmp/"+picPath+" "+"/emaildata"+picPath;
									String path3 = "cp -rf /emaildata"+picPath+"/* /emaildata/yuantu/";
									//cp -rf /emaildata/20170925111833_dcc2/* /emaildata/yuantu/
									Process process2 = Runtime.getRuntime().exec(path);
									process2.waitFor();
									Process process3 = Runtime.getRuntime().exec(path3);
									process3.waitFor();
									logger.info("hdfs下载到linux》》》》》》》" + path);
									//process2.waitFor();
									picPath="/emaildata"+picPath;
								}
								File a = new File(picPath);
								File[] listFile = a.listFiles();
								String filename = "";
								String content = "";
								String picDirpath = "";
								String favoriteLabel = "";
								String favoriteTime = "";
								List<Map<String, String>> indexData = new ArrayList<>();
								Map<String, String> map = null;
								System.out.println("############3---picPath##############" + picPath);
								try {
								for (File file : listFile) {
									map = new HashMap<>();
									filename = file.getName();
									/** 此处调用OCR获取图片内容bmp,jpg,png */
									try {
										if (filename.contains("jpg")) {
											content = new OCR().recognizeText(file, "jpg");
										}
									} catch (Exception e2) {
										//logger.info(e2);
									}
//									if (filename.contains("bmp")) {
//										content = new OCR().recognizeText(file, "bmp");
//									} else if (filename.contains("jpeg")) {
//										content = new OCR().recognizeText(file, "jpeg");
//									} else if (filename.contains("png")) {
//										content = new OCR().recognizeText(file, "png");
//									} else if (filename.contains("jpg")) {
//										content = new OCR().recognizeText(file, "jpg");
//									}
//									System.out.println("############3---content##############" + content);
									if(picPath.contains("_")){
										picDirpath = "/emaildata"+picPath + "/" + filename;
									}else{
										picDirpath = picPath + "/" + filename;
									}
									/**
									 * 截取图片名称
									 */
									String picname = filename.substring(0, filename.lastIndexOf("."));
									map.put("picname",picname);
									map.put("picdesc", content);
									map.put("picDirpath",picDirpath);
									map.put("caseID", caseId + "");
									map.put("caseName", caseName);
									map.put("starFlag", "0");
									map.put("favoriteLabel", favoriteLabel);
									map.put("favoriteTime", favoriteTime);
									indexData.add(map);
								}
								} catch (Exception e2) {
									// TODO: handle exception
									String endTime = sdf_bz.format(new Date());
									List<IndexQueue> iqList = dao.getListfromMysql(iq);
									int iqID = iqList.get(0).getId();
									IndexQueue iqq = new IndexQueue();
									iqq.setId(iqID);
									//System.out.println("获取新建数据id》》》》》" + iqID);
									List<IndexQueue> iqqList = dao.getListfromMysql(iqq);
									if (iqqList.size() > 0) {
										IndexQueue iqqq = iqqList.get(0);
										iqqq.setEndTime(endTime);
										iqqq.setRunningState(2);
										dao.updateToMysql(iqqq);
									}
									System.out.println("《《索引失败创建结束创建时间》》" + endTime);
									
								}
								EsClient.save("pic", "picindex", indexData);
								dealInfo.setContent("1");
								dao.setBeanToMysql(dealInfo);
								Evidence searchEvi2 = new Evidence();
								searchEvi2= evidences.get(0);
								System.out.println("《《索引id》》"+searchEvi2.getId());
								searchEvi2.setIndexFlag(1);
								dao.updateToMysql(searchEvi2);
								//索引成功结束时间
								String endTime = sdf_bz.format(new Date());
								List<IndexQueue> iqList = dao.getListfromMysql(iq);
								int iqID = iqList.get(0).getId();
								IndexQueue iqq = new IndexQueue();
								iqq.setId(iqID);
								//System.out.println("获取新建数据id》》》》》" + iqID);
								List<IndexQueue> iqqList = dao.getListfromMysql(iqq);
								if (iqqList.size() > 0) {
									IndexQueue iqqq = iqqList.get(0);
									iqqq.setEndTime(endTime);
									iqqq.setRunningState(1);
									dao.updateToMysql(iqqq);
								}
								System.out.println("《《索引成功创建结束创建时间》》" + endTime);
								return;

							} else {
								dealInfo.setContent("-1");
								dao.setBeanToMysql(dealInfo);
								Evidence searchEvi2 = new Evidence();
								searchEvi2= evidences.get(0);
								searchEvi2.setIndexFlag(2);
								dao.updateToMysql(searchEvi2);
								String endTime = sdf_bz.format(new Date());
								List<IndexQueue> iqList = dao.getListfromMysql(iq);
								int iqID = iqList.get(0).getId();
								IndexQueue iqq = new IndexQueue();
								iqq.setId(iqID);
								//System.out.println("获取新建数据id》》》》》" + iqID);
								List<IndexQueue> iqqList = dao.getListfromMysql(iqq);
								if (iqqList.size() > 0) {
									IndexQueue iqqq = iqqList.get(0);
									iqqq.setEndTime(endTime);
									iqqq.setRunningState(2);
									dao.updateToMysql(iqqq);
								}
								System.out.println("《《索引失败创建结束创建时间》》" + endTime);
								return;
							}

						}
						logger.info("spark 执行 脚本:" + command);
						String startTime = sdf_bz.format(new Date());
						System.out.println("《《索引开始创建时间》》" + startTime);
						Caseinfo ca = new Caseinfo();
						ca.setId(caseId);
						List<Caseinfo> list = dao.getListfromMysql(ca);
						String cn ="";
						if(list.size()>0){
							Caseinfo ca2 = list.get(0);
							cn= ca2.getCaseName();
						}
						IndexQueue iq = new IndexQueue();
						iq.setUserName(userName);
						iq.setCaseID(caseId);
						iq.setCaseName(cn);
						iq.setDataName(collectionName);
						iq.setDataType(evType);
						iq.setDataSize(evSize);
						iq.setStartTime(startTime);
						iq.setRunningState(3);
						dao.setBeanToMysql(iq);
						Process p = Runtime.getRuntime().exec(command);
						BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
						String line = null;
						int indexFlag = -1;
						while ((line = br.readLine()) != null) {
							if (line.contains("final status: SUCCEEDED")) {
								indexFlag = 1;
								dealInfo.setContent("1");
								dao.setBeanToMysql(dealInfo);
								logger.info(line);
								String endTime = sdf_bz.format(new Date());
								List<IndexQueue> iqList = dao.getListfromMysql(iq);
								int iqID = iqList.get(0).getId();
								IndexQueue iqq = new IndexQueue();
								iqq.setId(iqID);
								//System.out.println("获取新建数据id》》》》》" + iqID);
								List<IndexQueue> iqqList = dao.getListfromMysql(iqq);
								if (iqqList.size() > 0) {
									IndexQueue iqqq = iqqList.get(0);
									iqqq.setEndTime(endTime);
									iqqq.setRunningState(1);
									dao.updateToMysql(iqqq);
								}
								System.out.println("《《索引成功创建结束创建时间》》" + endTime);
							} else if (line.contains("FAILED")) {
								indexFlag = 2;
								dealInfo.setContent("-1");
								dao.setBeanToMysql(dealInfo);
								logger.info(line);
								String endTime = sdf_bz.format(new Date());
								List<IndexQueue> iqList = dao.getListfromMysql(iq);
								int iqID = iqList.get(0).getId();
								IndexQueue iqq = new IndexQueue();
								iqq.setId(iqID);
								//System.out.println("获取新建数据id》》》》》" + iqID);
								List<IndexQueue> iqqList = dao.getListfromMysql(iqq);
								if (iqqList.size() > 0) {
									IndexQueue iqqq = iqqList.get(0);
									iqqq.setEndTime(endTime);
									iqqq.setRunningState(2);
									dao.updateToMysql(iqqq);
								}
								System.out.println("《《索引失败创建结束创建时间》》" + endTime);
							}

						}

						//p.waitFor();
						// 执行完成后，更新该证据状态，indexFlag=1
						Evidence searchEvi = new Evidence();
						searchEvi.setFinished("true");
						searchEvi.setStatus("on");
						searchEvi.setId(eviTempID);
						List<Evidence> evidences = dao.getListfromMysql(searchEvi);
						System.out.println("flag====="+indexFlag+"  ==  evidences.size() = "+evidences.size()+"数据ID"+evidences.get(0).getId());
						String uuid ="";
						String evuuid ="";
						if (evidences.size() > 0) {
							Evidence indexEv = evidences.get(0);
							uuid = indexEv.getUUID();
							evuuid = ""+indexEv.getId();
							indexEv.setIndexFlag(indexFlag);
							dao.updateToMysql(indexEv);
						}
						/*if (!evType.equals("3")) {// 话单
							System.out.println("这批数据++"+evuuid);
							SearchAllUtil.dataMining(caseId + "", evuuid);
						}*/
				        //索引建立完成后 线索命中查询 并把结果存储到数据库
						String intex="";
						String type="";
						if(evType.equals("1")){//邮件
							intex="es";
							type="email";
						}else if(evType.equals("2")){//文档
							intex="doc";
							type="docType";
						}else if(evType.equals("3")){//话单
							/*intex="call";
							type="callList";*/
						}else if(evType.equals("4")){//手机取证
							intex="pic";
							type="picindex";
						}else if(evType.equals("5")){//黑客数据库
							intex="hackerdb";
							type="hkdb";
						}else if(evType.equals("6")){//图片数据直接建索引，暂时不用spark解析
							intex="es";
							type="email";
						}
						if(!"".equals(intex)){
							Caseinfo caseinfo = new Caseinfo();
							caseinfo.setId(caseId);
							List<Caseinfo> listfromMysql = dao.getListfromMysql(caseinfo);
							if(listfromMysql.size()>0){
								Caseinfo caseinfo2 = listfromMysql.get(0);
								String caseName = caseinfo2.getCaseName();
								String mainParty = caseinfo2.getMainParty();
								if(mainParty!=null && !"".equals(mainParty)){
									String[] split = mainParty.split(",");
									for (String string : split) {
										if(!"".equals(string)){
											SuspectInfo suspectInfo = new SuspectInfo();
											suspectInfo.setId(Integer.parseInt(string));
											List<SuspectInfo> listfromMysql2 = dao.getListfromMysql(suspectInfo);
											if(listfromMysql2.size()>0){
												SuspectInfo suspectInfo2 = listfromMysql2.get(0);
												String name = suspectInfo2.getSuspectName();
												String phone = suspectInfo2.getSuspectPhone();
												String email = suspectInfo2.getSuspectMail();
												/*
												 * 匹配手机号
												 * 
												 * */
												  //精确搜索
												  BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
												  mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
												  //caseid
												  mustQuery.must(QueryBuilders.termsQuery("caseID",caseId+""));
												   // 组合 模糊查询  should 
													BoolQueryBuilder evdenceuuid = QueryBuilders.boolQuery(); 
													MatchQueryBuilder urluuid = QueryBuilders.matchPhraseQuery("file_download_url", "*"+uuid+"*");  
													evdenceuuid.should(urluuid);
													mustQuery.must(evdenceuuid);
												  
												  
												  //关键词搜索
												  QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(phone)
														  .defaultOperator(QueryStringQueryBuilder.Operator.AND);//不同关键词之间使用and关系
												  mustQuery.must(queryBuilder);//添加第4条must的条件 关键词全文搜索筛选条件
												  SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch(intex).setTypes(type)
														  .setQuery(mustQuery)
														  .setFrom(0)//分页起始位置（跳过开始的n个）
											              .setSize(10);//本次返回的文档数量
												  //排序 执行
												  SearchResponse searchResponse = searchRequestBuilder
														  .execute().actionGet();//执行搜索
												  int totalHits = (int) searchResponse.getHits().getTotalHits();
													ArrayList<SearchHit[]> arrayList = new ArrayList<SearchHit[]>();
													SearchHit[] hits1 = searchResponse.getHits().getHits();
													arrayList.add(hits1);
//													long totalHits0 = searchResponse.getHits().getTotalHits()/10000;
//													for(int i=1;i<=totalHits0;i++){
//														searchResponse = searchRequestBuilder.setQuery(mustQuery).setFrom(i*10000)// 分页起始位置（跳过开始的n个）
//																.setSize(10000).execute().actionGet();
//														SearchHit[] hits  = searchResponse.getHits().getHits();
//														arrayList.add(hits);
//													}
												  String fileName="";
												  for (SearchHit[] searchHit2 : arrayList) {
													  for (SearchHit searchHit : searchHit2) {
													  Map<String, Object> source = searchHit.getSource();
													  String downloadUrl = (String) source.get("file_download_url");
													  if("".equals(fileName)){
														  fileName+=downloadUrl; 
													  }else{
														  fileName+="↑|↑"+downloadUrl;
													  }
													 }
												  }
												  //结果存储到数据库
												  if(totalHits!=0){
													  ClueWarn clueWarn = new ClueWarn();
													  clueWarn.setCaseName(caseName);
													  clueWarn.setClue(phone);
													  clueWarn.setSuspectName(name);
													  clueWarn.setCaseId(caseId);
													  List<ClueWarn> listClueWarn = dao.getListfromMysql(clueWarn);
													  if(listClueWarn.size()>0){
														  ClueWarn clueWarn2 = listClueWarn.get(0);
														  clueWarn2.setClueTime(sdf_bz2.format(new Date()));
														  clueWarn2.setNum(clueWarn2.getNum()+totalHits);
														  clueWarn2.setFileName(clueWarn2.getFileName()+"↑|↑"+fileName);
														  dao.updateToMysql(clueWarn2);
													  }else{
														  clueWarn.setClueTime(sdf_bz2.format(new Date()));
														  clueWarn.setNum(totalHits);
														  clueWarn.setFileName(fileName);
														  dao.setBeanToMysql(clueWarn);
													  }
												  }
													/*
													 * 匹配邮箱
													 * 
													 * */
												//精确搜索
												  BoolQueryBuilder mustQuery2 = QueryBuilders.boolQuery();
												  mustQuery2.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档
												  //caseid
												  mustQuery2.must(QueryBuilders.termsQuery("caseID",caseId+""));
												// 组合 模糊查询  should 
													BoolQueryBuilder evdenceuuid2 = QueryBuilders.boolQuery(); 
													MatchQueryBuilder urluuid2 = QueryBuilders.matchPhraseQuery("file_download_url", "*"+uuid+"*");  
													evdenceuuid2.should(urluuid2);
													mustQuery2.must(evdenceuuid2);
												  //关键词搜索
												  QueryBuilder queryBuilder2 = QueryBuilders.queryStringQuery(email)
														  .defaultOperator(QueryStringQueryBuilder.Operator.AND);//不同关键词之间使用and关系
												  mustQuery2.must(queryBuilder2);//添加第4条must的条件 关键词全文搜索筛选条件
												  SearchRequestBuilder searchRequestBuilder2 = EsClient.getClient().prepareSearch(intex).setTypes(type)
														  .setQuery(mustQuery2)
														  .setFrom(0)//分页起始位置（跳过开始的n个）
											              .setSize(10);//本次返回的文档数量
												  //排序 执行
												  SearchResponse searchResponse2 = searchRequestBuilder2
														  .execute().actionGet();//执行搜索
												  int totalHits2 = (int) searchResponse2.getHits().getTotalHits();
												  ArrayList<SearchHit[]> arrayList2 = new ArrayList<SearchHit[]>();
													SearchHit[] hits11 = searchResponse2.getHits().getHits();
													arrayList2.add(hits11);
//													long totalHits01 = searchResponse2.getHits().getTotalHits()/10000;
//													for(int i=1;i<=totalHits01;i++){
//														searchResponse2 = searchRequestBuilder2.setQuery(mustQuery2).setFrom(i*10000)// 分页起始位置（跳过开始的n个）
//																.setSize(10000).execute().actionGet();
//														SearchHit[] hits  = searchResponse2.getHits().getHits();
//														arrayList2.add(hits);
//													}
												  String fileName2="";
												  for (SearchHit[] searchHit22 : arrayList2) {
													  for (SearchHit searchHit : searchHit22) {
													  Map<String, Object> source2 = searchHit.getSource();
													  String downloadUrl2 = (String) source2.get("file_download_url");
													  if("".equals(fileName2)){
														  fileName2+=downloadUrl2; 
													  }else{
														  fileName2+="↑|↑"+downloadUrl2;
													  }
												  }
												  }
												  //结果存储到数据库
												  if(totalHits2!=0){
													  ClueWarn clueWarn = new ClueWarn();
													  clueWarn.setCaseName(caseName);
													  clueWarn.setClue(email);
													  clueWarn.setSuspectName(name);
													  clueWarn.setCaseId(caseId);
													  List<ClueWarn> listClueWarn = dao.getListfromMysql(clueWarn);
													  if(listClueWarn.size()>0){
														  ClueWarn clueWarn2 = listClueWarn.get(0);
														  clueWarn2.setClueTime(sdf_bz2.format(new Date()));
														  clueWarn2.setNum(clueWarn2.getNum()+totalHits2);
														  clueWarn2.setFileName(clueWarn2.getFileName()+"↑|↑"+fileName2);
														  dao.updateToMysql(clueWarn2);
													  }else{
														  clueWarn.setClueTime(sdf_bz2.format(new Date()));
														  clueWarn.setNum(totalHits2);
														  clueWarn.setFileName(fileName2);
														  dao.setBeanToMysql(clueWarn);
													  }
												  }
											}
										}
									}
								}
							}
						}
						
					} catch (Exception e) {
						System.out.println("=======>>>bibiibib========================================================================");
						e.printStackTrace();
						//logger.info(e.getMessage());
					}

				}



			};
			// mainRun为建立索引线程
			Thread t2 = new Thread(mainRun);
			t2.start();
			pw.flush();
			if (pw != null) {
				pw.close();
			}
		}

	}

	/**
	 * 
	 * @param sparkHistoryServerIp
	 *            配置的spark historyServer 的IP
	 * @return 处理进度百分比
	 * @throws IOException
	 */
	@RequestMapping(value = "/sparkDealProcess.php")
	public void getSparkCompleteProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		PrintWriter pw = null;
		String evID = request.getParameter("evID");
		//System.out.println("后台+=-++"+evID);
		int evIDInt = 0;
		List<Dealinfo> dealList = new ArrayList<Dealinfo>();
		Dealinfo dealinfo = new Dealinfo();
		if(evID.contains(",")){
			String[] evid2 = evID.split(",");
			for(int i = 0;i<evid2.length;i++){
				evIDInt = Integer.parseInt(evid2[i]);
				dealinfo.setEvID(evIDInt);
				dealList = dao.getOrderListfromMysql(dealinfo, 0, 1, "id");
			}
		}
		else if(!StringUtils.isEmpty(evID)) {
			evIDInt = Integer.parseInt(evID);
			dealinfo.setEvID(evIDInt);
			dealList = dao.getOrderListfromMysql(dealinfo, 0, 1, "id");
		}

		String percent = null;
		if (dealList.size() > 0) {
			if (dealList.get(0).getContent().equals("1")) {
				percent = "1";
			} else {
				percent = "0";
			}
		}

		try {
			pw = response.getWriter();
			pw.write("{\"percent\":\"" + percent + "\"}");
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	//线索管理查询状态
	@RequestMapping(value = "/EvsparkProcess.php")
	public void EvsparkProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		PrintWriter pw = null;
		String evID = request.getParameter("evID");
		String evidstr = request.getParameter("evidstr");
		
		Dealinfo dealInfo = new Dealinfo();
		dealInfo.setEvID(Integer.parseInt(evID));
		
		String percent = null;
		
		Evidence evidence = new Evidence();
		evidence.setId(Integer.parseInt(evID));
		List<Evidence> listfromMysql = dao.getListfromMysql(evidence);
		if(listfromMysql.size() > 0){
		Evidence evidence2 = listfromMysql.get(0);
		
		int flag=0;
		String[] split = evidstr.split(",");
		for (String string : split) {
			if(!evID.equals(string)){
				Evidence evidence3 = new Evidence();
				evidence3.setId(Integer.parseInt(string));
				List<Evidence> listfromMysql2 = dao.getListfromMysql(evidence3);
				int indexFlag = listfromMysql2.get(0).getIndexFlag();
				if(indexFlag==-1){
					flag=-1;
				}
			}
		}
		if(flag==-1){
			dealInfo.setContent("-1");
			percent = "0";
		}else{
			dealInfo.setContent("1");
			percent = "1";
			evidence2.setIndexFlag(1);
			dao.updateToMysql(evidence2);
		}
		Dealinfo dealInfo2 = new Dealinfo();
		dealInfo2.setEvID(Integer.parseInt(evID));
		List<Dealinfo> listfromMysql2 = dao.getListfromMysql(dealInfo2);
		if(listfromMysql2.size() < 1){
			dao.setBeanToMysql(dealInfo);
		    }
		}
		try {
			pw = response.getWriter();
			pw.write("{\"percent\":\"" + percent + "\"}");
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	/**
	 * 添加案件完成页面调用 进度条的展示
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/showProcessing.php")
	public void showProcessing(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, InterruptedException {
		// String collectionName = (String)
		// session.getAttribute("collectionName");

		String evID = request.getParameter("evID");
		int evIDInt = 0;
		Evidence evidence = new Evidence();
		int percent = 0;
		String allhandleeml = "0";

		if (!StringUtils.isEmpty(evID)) {
			evIDInt = Integer.parseInt(evID);
			evidence.setId(evIDInt);
			List<Evidence> evList = dao.getListfromMysql(evidence, 0, 1);
			if (evList.size() > 0) {
				evidence = evList.get(0);
				String pstr = evidence.getPercent();
				if (!StringUtils.isEmpty(pstr)) {
					percent = Integer.parseInt(pstr);
				}
				String hemlStr = evidence.getMailbox();
				if (!StringUtils.isEmpty(hemlStr)) {
					allhandleeml = hemlStr;
				}
			}
		}

		PrintWriter pw = null;
		pw = response.getWriter();

		// File f = null;
		// String ip = request.getLocalAddr();
		// logger.info(" into -showProcessing.php---------------ip:"+ip);
		// if (ip.contains("172.16.104.154")) {
		// f = new File("D:\\12.log");
		// } else {
		// f = new File("/email/cloudconf/" + collectionName +
		// "_collection.log");
		// }
		// if (!f.exists()) {
		// pw.write("{\"completion\":\"0%\"}");
		// return;
		// }
		// FileInputStream fis = new FileInputStream(f);
		// String temp = IOUtils.toString(fis);

		// if (temp.contains("Job failed")||temp.contains("Live merging
		// failed")) {
		// session.setAttribute("all_is_done", "true");
		// pw.write("{\"completion\":\"101%\"}");
		// pw.close();
		// fis.close();
		// return;
		// } else if (temp.contains("Committing live merge...")) {
		// Evidence evidence = new Evidence();
		// evidence.setEvName(collectionName);
		// evidence.setStatus("on");
		// List<Evidence> evidences = dao.getListfromMysql(evidence);
		//
		// if (evidences.size() > 0) {
		// fos = new FileOutputStream("/email/cloudconf/" + collectionName +
		// "_collectionHandledFileRecord.log",true);
		//
		// session.setAttribute("all_is_done", "true");
		// //session.removeAttribute("collectionName");
		// evidence = evidences.get(0);
		// evidence.setStatus("off");
		// dao.updateToMysql(evidence);
		// fos.write(("【"+sdf_bz.format(new
		// Date())+"】【合并索引】\n").getBytes("UTF-8"));
		//
		// String restartSolr="sudo -u solr solrctl --zk
		// "+Global.NameNodeIP+":2181/solr collection --reload "+collectionName
		// + "_collection";
		// Process process = Runtime.getRuntime().exec(restartSolr);
		// process.waitFor();
		//
		// fos.write(("【"+sdf_bz.format(new
		// Date())+"】【处理完成】\n").getBytes("UTF-8"));
		// if(fos!=null){
		// fos.close();
		// }
		// }
		//
		// pw.write("{\"completion\":\"100%\"}");
		// pw.close();
		// fis.close();
		// return;
		// } else if (temp.contains("MTree merge renaming solr shard")) {
		// pw.write("{\"completion\":\"95%\"}");
		// pw.close();
		// fis.close();
		// return;
		// } else if (temp.contains("MTree merge iteration")) {
		// String mkdir_core1 = "sudo -u hdfs hadoop fs -mkdir -p /solr/" +
		// collectionName + "_collection/core_node1/data/index/";
		// Process process = Runtime.getRuntime().exec(mkdir_core1);
		// process.waitFor();
		// String mkdir_core2 = "sudo -u hdfs hadoop fs -mkdir -p /solr/" +
		// collectionName + "_collection/core_node2/data/index/";
		// process = Runtime.getRuntime().exec(mkdir_core2);
		// process.waitFor();
		// String chown = "sudo -u hdfs hadoop fs -chmod -R 777 /solr/" +
		// collectionName + "_collection/";
		// process = Runtime.getRuntime().exec(chown);
		// process.waitFor();
		// pw.write("{\"completion\":\"90%\"}");
		// pw.close();
		// fis.close();
		// return;
		// } else {
		// for (int i = 89; i >= 0; i--) {
		// if (temp.contains("map " + i + "")) {
		// pw.write("{\"completion\":\"" + i + "%\"}");
		// break;
		// }
		// }
		// }
		pw.write("{\"completion\":\"" + percent + "\",\"allhandleeml\":\"" + allhandleeml + "\"}");
		pw.close();
		// if(mapPercent==100){
		// mapPercent = 0;
		// }
		// fis.close();
	}

	/**
	 * 添加案件完成页面调用 展示已处理的个数和百分比
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/showHandledCounts.php")
	public void showHandledCounts(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, InterruptedException {
		// logger.info( "into action showhandedcounts.php");
		// String ip = request.getLocalAddr();
		// String collectionName = (String)
		// session.getAttribute("collectionName");
		// System.out.println(collectionName);

		String evID = request.getParameter("evID");
		int evIDInt = 0;
		Evidence evidence = new Evidence();
		String collectionName = "";

		if (!StringUtils.isEmpty(evID)) {
			evIDInt = Integer.parseInt(evID);
			evidence.setId(evIDInt);
			List<Evidence> evList = dao.getListfromMysql(evidence, 0, 1);
			if (evList.size() > 0) {
				evidence = evList.get(0);
				collectionName = evidence.getEvName();
			}
		}

		File f = null;
		// int handledEMLCounts = 0;
		// int handledZIPCounts = 0;
		// int handledRARCounts = 0;
		// int handledTARCounts = 0;
		// int handledGZCounts = 0;
		// int failedZIPCounts = 0;
		// int failedRARCounts = 0;
		// int failedTARCounts = 0;
		// int failedGZCounts = 0;
		int uploadedEMLCounts = 0;
		int tempEmlCount = evidence.getEmlCount();
		if (!StringUtils.isEmpty(collectionName)) {
			f = new File("/email/cloudconf/" + collectionName + "_collectionHandledFileRecord.log");
			if (f.exists()) {
				FileInputStream fis = null;
				InputStreamReader isr = null;
				BufferedReader br = null;
				try {
					fis = new FileInputStream(f);

					isr = new InputStreamReader(fis, "UTF-8");
					br = new BufferedReader(isr);

					String fileinhdfs = null;
					while ((fileinhdfs = br.readLine()) != null) {
						// if(fileinhdfs.contains("正在处理")&&fileinhdfs.contains("parastorfs:///")){
						// handledEMLCounts++;
						// }else
						// if(fileinhdfs.contains("解压成功")&&fileinhdfs.endsWith(".zip")){
						// handledZIPCounts++;
						// }else
						// if(fileinhdfs.contains("解压成功")&&fileinhdfs.endsWith(".rar")){
						// handledRARCounts++;
						// }else
						// if(fileinhdfs.contains("解压成功")&&fileinhdfs.endsWith(".tar")){
						// handledTARCounts++;
						// }else
						// if(fileinhdfs.contains("解压成功")&&fileinhdfs.endsWith("tar.gz")){
						// handledGZCounts++;
						// }else
						// if(fileinhdfs.contains("解压失败")&&fileinhdfs.endsWith(".zip")){
						// failedZIPCounts++;
						// }else
						// if(fileinhdfs.contains("解压失败")&&fileinhdfs.endsWith(".rar")){
						// failedRARCounts++;
						// }else
						// if(fileinhdfs.contains("解压失败")&&fileinhdfs.endsWith(".tar")){
						// failedTARCounts++;
						// }else
						// if(fileinhdfs.contains("解压失败")&&fileinhdfs.endsWith("tar.gz")){
						// failedGZCounts++;
						// }else
						if (fileinhdfs.contains("上传成功") && fileinhdfs.endsWith("eml")) {
							uploadedEMLCounts++;
							// }else
							// if(fileinhdfs.contains("已解压文件")&&fileinhdfs.endsWith("eml")){
							// uploadedEMLCounts++;
						} else if (fileinhdfs.contains("上传完成")) {
							break;
						}
					}
				} catch (Exception e) {
					// e.printStackTrace();
				} finally {
					if (br != null) {
						br.close();
					}
					if (isr != null) {
						isr.close();
					}
					if (fis != null) {
						fis.close();
					}
				}
			}
		}
		if (uploadedEMLCounts != 0 && uploadedEMLCounts > tempEmlCount) {
			Evidence evi = new Evidence();
			evi.setId(evIDInt);
			evi.setEmlCount(uploadedEMLCounts);
			dao.updateToMysql(evi);
		}

		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write("{\"uploadedEMLCounts\":\"" + uploadedEMLCounts + "\"}");
			pw.flush();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}

		// -------------------------------- xl start
		// String fileContent = IOUtils.toString(new FileInputStream(f));
		// String pattern = "Indexing\\s(\\d+)\\s";
		// Pattern p = Pattern.compile(pattern);
		// Matcher m = p.matcher(fileContent);
		// String totalFileCount = null;
		// if (m.find()) {
		// totalFileCount = m.group(1);
		// }
		//
		// String handledPercent = "0%";
		// if (!"0".equals(totalFileCount)) {
		// double a = handledEMLCounts;
		// double b = Double.parseDouble(totalFileCount);
		// float c = (float) (a / b * 100);
		// int d = (int) c;
		// handledPercent = d + "%";
		// }
	}

	// 添加案件完成页面调用
	// ----------------------------- xl start
	@RequestMapping(value = "/showAllHandledFile.php")
	public void showAllHandledFile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		response.setContentType("text/html; charset=UTF-8");
		// String collectionName = (String)
		// session.getAttribute("collectionName");

		String evID = request.getParameter("evID");
		int evIDInt = 0;
		List<Dealinfo> dealList = new ArrayList<Dealinfo>();
		Dealinfo dealinfo = new Dealinfo();

		if (!StringUtils.isEmpty(evID)) {
			evIDInt = Integer.parseInt(evID);
			dealinfo.setEvID(evIDInt);
			dealList = dao.getOrderListfromMysql(dealinfo, 0, 50, "id");
		}

		if (dealList.size() == 0) {
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

	@RequestMapping(value = "/getDealInfo.php")
	public void getDealInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		String evID = request.getParameter("evID");
		int evIDInt = 0;
		List<Dealinfo> dealList = new ArrayList<Dealinfo>();

		if (!StringUtils.isEmpty(evID)) {
			evIDInt = Integer.parseInt(evID);
			Dealinfo dealinfo = new Dealinfo();
			dealinfo.setEvID(evIDInt);

			dealList = dao.getOrderListfromMysql(dealinfo, 0, 50, "id");
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

	/**
	 * 重新构建索引，在索引建立完成后，但是没有搜索结果的情况下，可以尝试重新构建索引
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/rebuiltCollection.php")
	public void rebuiltCollection(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, InterruptedException {
		String collectionNameCN = request.getParameter("collectionName");
		collectionNameCN = URLEncoder.encode(collectionNameCN, "UTF-8");
		String collectionName = collectionNameCN.replace("%", "X_L");
		PrintWriter pw = null;
		pw = response.getWriter();

		fos = new FileOutputStream("/email/cloudconf/" + collectionName + "_collectionHandledFileRecord.log", true);

		fos.write(("【" + sdf_bz.format(new Date()) + "】【删除索引一】\n").getBytes("UTF-8"));
		String removeCoreNode1 = "sudo -u hdfs hadoop fs -rm -R /solr/" + collectionName + "_collection/core_node1";
		Process process = Runtime.getRuntime().exec(removeCoreNode1);
		process.waitFor();
		fos.write(("【" + sdf_bz.format(new Date()) + "】【删除索引二】\n").getBytes("UTF-8"));
		String removeCoreNode2 = "sudo -u hdfs hadoop fs -rm -R /solr/" + collectionName + "_collection/core_node2";
		process = Runtime.getRuntime().exec(removeCoreNode2);
		process.waitFor();

		fos.write(("【" + sdf_bz.format(new Date()) + "】【创建目录一】\n").getBytes("UTF-8"));
		String createCoreNode1 = "sudo -u hdfs hadoop fs -mkdir /solr/" + collectionName + "_collection/core_node1";
		process = Runtime.getRuntime().exec(createCoreNode1);
		process.waitFor();

		fos.write(("【" + sdf_bz.format(new Date()) + "】【创建目录二】\n").getBytes("UTF-8"));

		String createCoreNode2 = "sudo -u hdfs hadoop fs -mkdir /solr/" + collectionName + "_collection/core_node2";
		process = Runtime.getRuntime().exec(createCoreNode2);
		process.waitFor();

		fos.write(("【" + sdf_bz.format(new Date()) + "】【合并索引步骤一】\n").getBytes("UTF-8"));
		String copyCollection1 = "sudo -u hdfs hadoop fs -cp -p /solr/" + collectionName
				+ "_collection/results/part-00000/data /solr/" + collectionName + "_collection/core_node1/";
		process = Runtime.getRuntime().exec(copyCollection1);
		process.waitFor();

		fos.write(("【" + sdf_bz.format(new Date()) + "】【合并索引步骤二】\n").getBytes("UTF-8"));

		String copyCollection2 = "sudo -u hdfs hadoop fs -cp -p /solr/" + collectionName
				+ "_collection/results/part-00001/data /solr/" + collectionName + "_collection/core_node2/";
		process = Runtime.getRuntime().exec(copyCollection2);
		process.waitFor();

		fos.write(("【" + sdf_bz.format(new Date()) + "】【合并索引步骤三】\n").getBytes("UTF-8"));

		String chown = "sudo -u hdfs hadoop fs -chmod  -R  777 /solr/" + collectionName + "_collection/";
		process = Runtime.getRuntime().exec(chown);
		process.waitFor();

		fos.write(("【" + sdf_bz.format(new Date()) + "】【合并索引步骤四】\n").getBytes("UTF-8"));

		String restartSolr = "sudo -u solr solrctl --zk " + Global.ZKHost + ":2181/solr collection --reload "
				+ collectionName + "_collection";
		process = Runtime.getRuntime().exec(restartSolr);
		process.waitFor();

		fos.write(("【" + sdf_bz.format(new Date()) + "】【重新建立索引完成】\n").getBytes("UTF-8"));
		if (fos != null) {
			fos.close();
		}
		pw.write("{\"res\":\"succ\"}");
		pw.close();

	}

	/**
	 * 删除证据和案件
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/deleteCollection.php")
	public void deleteCollection(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, InterruptedException {
		String collectionNameCN = request.getParameter("collectionName");
		collectionNameCN = URLEncoder.encode(collectionNameCN, "UTF-8");
		String collectionName = collectionNameCN.replace("%", "X_L");
		PrintWriter pw = null;
		pw = response.getWriter();

		logger.info(("【" + sdf_bz.format(new Date()) + "】【删除_collectionHandledFileRecord.log】\n").getBytes("UTF-8"));
		File f = new File("/email/cloudconf/" + collectionName + "_collectionHandledFileRecord.log");
		if (f.exists()) {
			f.delete();
		}
		logger.info(("【" + sdf_bz.format(new Date()) + "】【删除_collection.log】\n").getBytes("UTF-8"));
		f = new File("/email/cloudconf/" + collectionName + "_collection.log");
		if (f.exists()) {
			f.delete();
		}
		logger.info(("【" + sdf_bz.format(new Date()) + "】【删除morphlines.conf】\n").getBytes("UTF-8"));
		f = new File("/email/cloudconf/" + collectionName + "morphlines.conf");
		if (f.exists()) {
			f.delete();
		}
		logger.info(("【" + sdf_bz.format(new Date()) + "】【删除_collection.properties】\n").getBytes("UTF-8"));
		f = new File("/email/cloudconf/log4j_" + collectionName + "_collection.properties");
		if (f.exists()) {
			f.delete();
		}

		logger.info(("【" + sdf_bz.format(new Date()) + "】【删除索引目录】\n").getBytes("UTF-8"));
		String removeHDFSIndexDIR = "sudo -u hdfs hadoop fs -rm -R /solr/" + collectionName + "_collection/";
		Process process = Runtime.getRuntime().exec(removeHDFSIndexDIR);
		process.waitFor();
		logger.info(("【" + sdf_bz.format(new Date()) + "】【删除数据目录】\n").getBytes("UTF-8"));
		String removeDataDir = "sudo -u hdfs hadoop fs -rm -R /tmp/" + collectionName;
		process = Runtime.getRuntime().exec(removeDataDir);
		process.waitFor();
		logger.info(("【" + sdf_bz.format(new Date()) + "】【删除索引配置文件一】\n").getBytes("UTF-8"));
		String deleteSolrConf = "solrctl --zk " + Global.ZKHost + ":2181/solr instancedir --delete  " + collectionName
				+ "_collection";
		process = Runtime.getRuntime().exec(deleteSolrConf);
		process.waitFor();
		logger.info(("【" + sdf_bz.format(new Date()) + "】【删除索引配置文件二】\n").getBytes("UTF-8"));
		String deleteCollection = "solrctl --zk " + Global.ZKHost + ":2181/solr collection --delete " + collectionName
				+ "_collection";
		process = Runtime.getRuntime().exec(deleteCollection);
		process.waitFor();

		Evidence evidence = new Evidence();
		evidence.setEvName(collectionName);
		List<Evidence> evidences = dao.getListfromMysql(evidence);
		if (evidences.size() > 0) {
			evidence = evidences.get(0);
			int caseid = evidence.getCaseID();
			Caseinfo case_temp = new Caseinfo();
			case_temp.setId(caseid);
			List<Caseinfo> cases = dao.getListfromMysql(case_temp);
			if (cases.size() > 0) {
				case_temp = cases.get(0);
				dao.deletefromMysql(case_temp);
			}
			dao.deletefromMysql(evidence);

		}

		pw.write("{\"res\":\"succ\"}");
		pw.close();

	}

	@RequestMapping(value = "/getEvidence.php")
	public void getEvidence(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map<String, Object> map) throws IOException, InterruptedException {
		String evID = request.getParameter("evID");
		String type = request.getParameter("type");
		Evidence evidence = new Evidence();

		if (!StringUtils.isEmpty(evID)) {
			int evIDInt = Integer.parseInt(evID);
			evidence.setId(evIDInt);
			List<Evidence> evlist = dao.getListfromMysql(evidence, 0, 1);
			if (evlist.size() > 0) {
				evidence = evlist.get(0);
				String startSolrTime = evidence.getStartSolrTime();
				if (StringUtils.isEmpty(startSolrTime)) {
					System.out.println("evID++++++"+evID);
					startSolrTime = evidence.getAddTime();
					Evidence updateEvi = new Evidence();
					updateEvi.setId(evidence.getId());
					updateEvi.setStartSolrTime(startSolrTime);
					updateEvi.setIndexFlag(0);
					dao.updateToMysql(updateEvi);
				}
				String finishTime = evidence.getFinishTime();
				String nowDate = sdf_bz.format(new Date());

				if (type != null && type.equals("0")) {
					if (!StringUtils.isEmpty(finishTime)) {
						nowDate = finishTime;
					} else {
						Evidence updateEvi = new Evidence();
						updateEvi.setId(evidence.getId());
						updateEvi.setFinishTime(nowDate);
						dao.updateToMysql(updateEvi);
					}

				}

				String betweenStr = DateUtil.getBetween(startSolrTime, nowDate);
				evidence.setAddTime(betweenStr);
			}
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.bean2json(evidence));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}

	@RequestMapping(value = "/setStartSolrTime.php")
	public void setStartSolrTime(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Map<String, Object> map) throws IOException, InterruptedException {
		String evID = request.getParameter("evID");
		System.out.println(evID);
		Evidence evidence = new Evidence();

		if (!StringUtils.isEmpty(evID)) {
			int evIDInt = Integer.parseInt(evID);
			evidence.setId(evIDInt);
			List<Evidence> evlist = dao.getListfromMysql(evidence, 0, 1);
			if (evlist.size() > 0) {
				evidence = evlist.get(0);
				String nowDate = sdf_bz.format(new Date());
				Evidence updateEvi = new Evidence();
				updateEvi.setId(evidence.getId());
				updateEvi.setStartSolrTime(nowDate);
				dao.updateToMysql(updateEvi);

			}
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write("{\"res\":\"succ\"}");
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