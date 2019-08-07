package com.xl.cloud.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xl.cloud.bean.Email_WorkDTO;

/**
 * 邮件统计分析查询DAO
 * @author xunliinfo
 *
 */
public class EmailDao {


	/**
	 * 收发件人分析
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 * @throws SQLException 
	 */
	public List<Email_WorkDTO> getFromToCount(String caseId ,String FromToType,String tableName,String XYRemail,String connectNum) throws SQLException {
		DBHelper db1 = null;
		String sql = null;
		if(FromToType.equals("1")){//
			if(XYRemail!=null&&!"".equals(XYRemail)){//根据嫌疑人区分
				sql = "select t.countNum,t.toWho from (select sum(t.countNum) as countNum,t.toWho from "+tableName+" t where t.caseId='"+caseId+"' and  t.fromWho in("+XYRemail+") group by t.toWho order by countNum desc) t where t.countNum >="+connectNum+"";
			}/*else{//根据案件区分
				sql = "select t.countNum,t.toWho from (select sum(t.countNum) as countNum,t.toWho from "+tableName+" t where t.caseId='"+caseId+"' group by t.toWho order by countNum desc) t where t.countNum >="+connectNum+"";
			}*/
		}else{//收件人排序
	
			if(XYRemail!=null&&!"".equals(XYRemail)){
				sql = "select t.countNum,t.fromWho from (select sum(t.countNum) as countNum,t.fromWho from "+tableName+" t where t.caseId='"+caseId+"' and  t.toWho in("+XYRemail+") group by t.fromWho order by countNum desc) t where t.countNum >="+connectNum+"";
			}/*else{
				sql = "select t.countNum,t.fromWho from (select sum(t.countNum) as countNum,t.fromWho from "+tableName+" t where t.caseId='"+caseId+"' group by t.fromWho order by countNum desc) t where t.countNum >="+connectNum+"";
			}*/
		}
		System.out.println(sql);
			db1 = new DBHelper(sql);
			ResultSet ret = null;
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			List<Email_WorkDTO> fromToList = new ArrayList<>();
			Email_WorkDTO dto = null;
			if (ret != null) {
				while (ret.next()) {
					dto = new Email_WorkDTO();
					dto.setEmailNum(ret.getInt("countNum"));
					if(FromToType.equals("1")){
						dto.setToWho(ret.getString("toWho"));
					}else{
						dto.setFormWho(ret.getString("fromWho"));
					}
					fromToList.add(dto);
				}
			}

		return fromToList;
	}
	/**
	 * 嫌疑人关系图分析
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 * @throws SQLException 
	 */
	public List<Email_WorkDTO> getXYRFromToCount(String caseId, String tableName, String connectNum, String xyrEmail,String emlType)
			throws SQLException {
		DBHelper db1 = null;
		String sql = null;
		System.out.println("emlType："+emlType);
		if(emlType.equals("收件箱")){
			sql = "select t.countNum,t.fromWho,t.toWho from (select sum(t.countNum)as countNum,t.fromWho,t.toWho from " + tableName + " t where t.caseId='" + caseId
					+ "' and t.toWho like '%" + xyrEmail + "%'"+ " group by t.fromWho) t where t.countNum>="+connectNum+" order by countNum DESC";
		}else if(emlType.equals("发件箱")){
			System.out.println("进入发件箱");
			sql = "select t.countNum,t.fromWho,t.toWho from (select sum(t.countNum)as countNum,t.fromWho,t.toWho from " + tableName + " t where t.caseId='" + caseId
									+ "' and fromWho='" + xyrEmail + "'"+ " group by t.fromWho,t.toWho) t where t.countNum>="+connectNum+" order by countNum DESC";
		}else{
			sql = "select t.countNum,t.fromWho,t.toWho from (select sum(t.countNum) as countNum,t.fromWho,t.toWho from " + tableName + " t where t.caseId='" + caseId
					+ "' and fromWho='" + xyrEmail + "'"
					+ " group by t.fromWho,t.toWho union all select t.countNum,t.fromWho,t.toWho from(select sum(t.countNum) as countNum,t.fromWho,'"
					+ xyrEmail + "' as toWho from " + tableName + " t where t.caseId = '" + caseId + "' and t.toWho like '%"
					+ xyrEmail + "%' group by t.fromWho order by countNum DESC) t order by countNum DESC) t where t.countNum>="+connectNum+"";
		}
		
		
		System.out.println("sql嫌疑人关系图=======" + sql);
		db1 = new DBHelper(sql);
		ResultSet ret = null;
		ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
		List<Email_WorkDTO> fromToList = new ArrayList<>();
		Email_WorkDTO dto = null;
		if (ret != null) {
			while (ret.next()) {
				dto = new Email_WorkDTO();
				dto.setEmailNum(ret.getInt("countNum"));
				dto.setFormWho(ret.getString("fromWho"));
				dto.setToWho(ret.getString("toWho"));
				fromToList.add(dto);
			}
		}

		return fromToList;
	}
	
	
}
