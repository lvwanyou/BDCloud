package com.xl.cloud.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xl.cloud.bean.Caseinfo;
import com.xl.cloud.bean.Coordinatelog;
import com.xl.cloud.bean.Department;
import com.xl.cloud.bean.Role;
import com.xl.cloud.bean.RoleTable;
import com.xl.cloud.bean.Section;
import com.xl.cloud.bean.User;
import com.xl.cloud.bean.UserAction;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.EncryptUtil;
import com.xl.cloud.util.JsonUtil;

import net.sf.json.JSONArray;

@Controller
public class CaseManageAction {
	private SqlDao sqlDao = new SqlDao();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**协同操作日志列表页
	 * @param request
	 * @param map
	 * @param session
	 * @param response
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/admin/case_xietong.php")
	public String case_xietong(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, UnknownHostException {
		
		String pageno = request.getParameter("pageno");
		String caseNum = request.getParameter("caseNum");
		String caseName = request.getParameter("caseName");
		String grantedName = request.getParameter("grantedName");
		
		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;
		
		if(!StringUtils.isEmpty(pageno)){
			pageIndex = Integer.parseInt(pageno);
		}
		
		Coordinatelog log = new Coordinatelog();
		if(!StringUtils.isEmpty(caseNum)){
			log.setCaseNum(caseNum);
		}
		if(!StringUtils.isEmpty(caseName)){
			log.setCaseName(caseName);
		}
		if(!StringUtils.isEmpty(grantedName)){
			log.setGrantedName(grantedName);
		}
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
		if (roleName.equals("科室数据") ) {// 科长才能授权
			log.setSection(user.getSection());
		} else if (roleName.equals("部门数据") ) {// 部长
			log.setDepartment(user.getPartment());
		}
		
		List<Coordinatelog> listfromMysql = new ArrayList<Coordinatelog>();
		int total = sqlDao.getcountfromMysqlLike(log);
		List<Coordinatelog> logs = sqlDao.getOrderListfromMysqlLike(log, (pageIndex-1) * pageSize, pageSize, "id");
		for (Coordinatelog Coordinatelog : logs) {
			/*String department = section2.getDepartment();
			String section = section2.getSection();*/
		/*
			String role = section2.getGrantedPri();
	        if(role != null && !role.equals("")){
	        	Role role3 =new Role();
	        	role3.setId(Integer.parseInt(role));
	        	List<Role> roleMysqlse = sqlDao.getListfromMysql(role3);
	        	if(roleMysqlse.size() > 0){
	    			Role Roled = roleMysqlse.get(0);
	    			String RoleName = Roled.getRoleName();
	    			section2.setGrantedPri(RoleName);
	    			}
			}else{
				section2.setGrantedPri("无");
			}*/
	        
		/*	Department department2 = new Department();
			Section section3 =new Section();*/
			/*
			department2.setId(Integer.parseInt(department));
			section3.setId(Integer.parseInt(section));
			
			List<Department> listfromMysql2 = sqlDao.getListfromMysql(department2);
			List<Section> listfromMysqlse = sqlDao.getListfromMysql(section3);
			
			if(listfromMysql2.size() > 0){
				Department department3 = listfromMysql2.get(0);
				String departmentName = department3.getDepartmentName();
				section2.setDepartment(departmentName);
			}
			if(listfromMysqlse.size() > 0){
			Section departmentse = listfromMysqlse.get(0);
			String seName = departmentse.getSectionName();
			section2.setSection(seName);
			}*/
		
			listfromMysql.add(Coordinatelog);
		}
		
		num = total / pageSize;
		if (total % pageSize != 0){
			num++;
		}
		num = num==0?1:num;
		actionLog((String) session.getAttribute("userName"), "查看", "协同办案");
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
		
		map.put("logs", listfromMysql);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("caseNum", caseNum);
		map.put("caseName", caseName);
		map.put("grantedName", grantedName);
		return "case_xietong";
	}
	// 操作记录
	public void actionLog(String name, String action, String module) throws UnknownHostException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	/**新建案件授权-选择案件页
	 * @param request
	 * @param map
	 * @param session
	 * @param response
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/admin/case_new_grant.php")
	public String case_new_grant(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		
		String pageno = request.getParameter("pageno");
		String searchparam = request.getParameter("searchparam");
		
		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;
		
		if(!StringUtils.isEmpty(pageno)){
			pageIndex = Integer.parseInt(pageno);
		}
		String appendSql = "";
		if(!StringUtils.isEmpty(searchparam)){
			appendSql = "caseName like '%"+searchparam+"%' or caseNum like '%"+searchparam+"%'";
		}
		Caseinfo caseinfo = new Caseinfo();
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
		if (roleName.equals("科室数据") ) {// 科长本科所有案件
			caseinfo.setSection(user.getSection());
		} else if (roleName.equals("部门数据") ) {// 部长
			caseinfo.setDepartment(user.getPartment());
		}
		List<Caseinfo> caseinfos = sqlDao.getOrderListfromMysql(caseinfo, appendSql, (pageIndex-1) * pageSize, pageSize, "id");
		
		//List<Caseinfo> a1 = new ArrayList<Caseinfo>();
		String aaa = "";
		if(caseinfos.size()>0){
			for (Caseinfo caseinfo2 : caseinfos) {
				String trustee = caseinfo2.getTrustee(); 
				if (!"".equals(trustee)) {
					aaa = trustee.substring(0, trustee.length()-1);
					caseinfo2.setTrustee(aaa);
					System.out.println("aaa===="+aaa);
				}
			}
		}
		
		
		
		int total = sqlDao.getcountfromMysql(caseinfo, appendSql);
		num = total / pageSize;
		if (total % pageSize != 0){
			num++;
		}
		num = num==0?1:num;
		
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
		
		map.put("dataList", caseinfos);
		//map.put("aaa", aaa);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("searchparam", searchparam);
		return "case_new_grant";
	}
	
	/**取消案件授权
	 * @param request
	 * @param map
	 * @param session
	 * @param response
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/admin/case_cancel_grant.php")
	public String case_cancel_grant(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		
		String pageno = request.getParameter("pageno");
		String searchparam = request.getParameter("searchparam");
		
		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;
		
		if(!StringUtils.isEmpty(pageno)){
			pageIndex = Integer.parseInt(pageno);
		}
		String appendSql = "";
		if(!StringUtils.isEmpty(searchparam)){
			appendSql = "caseName like '%"+searchparam+"%' or caseNum like '%"+searchparam+"%'";
		}
		Caseinfo caseinfo = new Caseinfo();
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
		if (roleName.equals("科室数据") ) {// 科长本科所有案件
			caseinfo.setSection(user.getSection());
		} else if (roleName.equals("部门数据") ) {// 部长
			caseinfo.setDepartment(user.getPartment());
		}
		List<Caseinfo> caseinfos = sqlDao.getOrderListfromMysql(caseinfo, appendSql, (pageIndex-1) * pageSize, pageSize, "id");
		
		String aaa = "";
		if(caseinfos.size()>0){
			for (Caseinfo caseinfo2 : caseinfos) {
				String trustee = caseinfo2.getTrustee(); 
				if (!"".equals(trustee)) {
					aaa = trustee.substring(0, trustee.length()-1);
					caseinfo2.setTrustee(aaa);
					System.out.println("aaa===="+aaa);
				}
			}
		}
		
		
		int total = sqlDao.getcountfromMysql(caseinfo, appendSql);
		num = total / pageSize;
		if (total % pageSize != 0){
			num++;
		}
		num = num==0?1:num;
		
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
		
		map.put("dataList", caseinfos);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		map.put("searchparam", searchparam);
		return "case_cancel_grant";
	}
	
	/**新建案件授权-选择人员
	 * @param request
	 * @param map
	 * @param session
	 * @param response
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/admin/case_new_granttwo.php")
	public String case_new_granttwo(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String id = request.getParameter("id");
		String noStr = request.getParameter("noStr");
		String str = request.getParameter("str");
				
		if(!StringUtils.isEmpty(id)){
			try {
				id = URLDecoder.decode(id, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(noStr)){
			try {
				noStr = URLDecoder.decode(noStr, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(str)){
			try {
				str = URLDecoder.decode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		map.put("id", id);
		map.put("noStr", noStr);
		map.put("str", str);
		return "case_new_granttwo";
	}
	
	@RequestMapping(value = "/admin/initUserTree.php")
	public void initUserTree(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		
		String resResult = null;
		StringBuilder sbtmp = new StringBuilder();
		Department department = new Department();
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
			String partment = user.getPartment();
			department.setId(Integer.parseInt(partment));
		} 
		if (roleName.equals("部门数据") ) {// 部长
			String partment = user.getPartment();
			department.setId(Integer.parseInt(partment));
		} 
		List<Department> departments = sqlDao.getListfromMysql(department);
		sbtmp.append("[");
		sbtmp.append("{\"id\":\"000\",");
		sbtmp.append("\"text\":\"缉私局\"");
		if (departments != null && departments.size() > 0) {
			sbtmp.append(",\"state\":\"open\"");
			sbtmp.append(",\"children\":[");
			for (int i = 0; i < departments.size(); i++) {
				Department tempD = departments.get(i);
				String departName = tempD.getDepartmentName();
				int id = tempD.getId();
				Section section = new Section();
				String roleName2="" ;
				if(user != null){
					String userrole = user.getUserrole();
					Role role = new Role();
					role.setId(Integer.parseInt(userrole));
					List<Role> roleList = sqlDao.getListfromMysql(role);
					if(roleList.size()>0){
						Role role2 = roleList.get(0);
						roleName2 = role2.getRoleName();
					}
				}
				/*if (roleName2.equals("科室数据") ) {// 科长
					section.setId(Integer.parseInt(user.getSection()));
				} 
				if (roleName.equals("部门数据") ) {// 部长
					String partment = user.getPartment();
					department.setId(Integer.parseInt(partment));
				} */
				section.setDepartment(id+"");
				List<Section> sections = sqlDao.getListfromMysql(section);
				sbtmp.append("{\"id\":\"099" + id + "\",");
				sbtmp.append("\"text\":\"" + departName.trim() + "\"");
				
				if (sections != null && sections.size() > 0) {
					if(i==0){
						sbtmp.append(",\"state\":\"open\"");
					}else{
						sbtmp.append(",\"state\":\"closed\"");
					}
					sbtmp.append(",\"children\":[");
					
					for (int j = 0; j < sections.size(); j++) {
						section = sections.get(j);
						String sectionName = section.getSectionName();
						// String evmaiBox = ei.getCreateTime();
						int sectionId = section.getId();
						sbtmp.append("{\"text\":\"" + sectionName.trim() + "\",");
						sbtmp.append("\"id\":\"088" + sectionId + "\"");
						User tuser = new User();
						tuser.setPartment(id+"");
						tuser.setSection(sectionId+"");
						List<User> users = sqlDao.getListfromMysql(tuser);
						if(users.size() > 0){
							if(i==0){
								sbtmp.append(",\"state\":\"open\"");
							}else{
								sbtmp.append(",\"state\":\"closed\"");
							}	
							sbtmp.append(",\"children\":[");
							for(User tempUser:users){
								if(!tempUser.getUsername().equals(user.getUsername())){
								Section section2 = new Section();
								section2.setId(Integer.parseInt(tempUser.getSection()) );
								List<Section> sections2 = sqlDao.getListfromMysql(section2);
								Section section3 = sections2.get(0);
								
								String roleName3="" ;
									String userrole = tempUser.getUserrole();
									Role role = new Role();
									role.setId(Integer.parseInt(userrole));
									List<Role> roleList = sqlDao.getListfromMysql(role);
									if(roleList.size()>0){
										Role role2 = roleList.get(0);
										roleName3 = role2.getRoleName();
									}
								sbtmp.append("{\"text\":\"" + tempUser.getUsername().trim() + "\",");
								sbtmp.append("\"pri\":\"" + roleName3 + "\",");
								sbtmp.append("\"sec\":\"" + section3.getSectionName()+ "\",");
								sbtmp.append("\"id\":" + tempUser.getId() + "},");
								}
							}
							String last=sbtmp.substring(sbtmp.length()-1,sbtmp.length());
							if(",".equals(last)){
								sbtmp.deleteCharAt(sbtmp.length() - 1);
							}
							sbtmp.append("]");
						}
						sbtmp.append("},");
					}
					sbtmp.deleteCharAt(sbtmp.length() - 1);
					sbtmp.append("]");
				}
				sbtmp.append("},");
			}
			sbtmp.deleteCharAt(sbtmp.length() - 1);
			sbtmp.append("]");
		}
		sbtmp.append("}]");

		resResult = sbtmp.toString();
//		System.out.println("resResult:"+resResult);
//		System.out.println("部门科室树："+resJson.toString());
//		resResult = "[{\"id\":\"023\",\"text\":\"办公室\",\"state\":\"open\",\"children\":[{\"text\":\"指挥中心\",\"id\":34},{\"text\":\"秘书科\",\"id\":35},{\"text\":\"机要科\",\"id\":36},{\"text\":\"综合治理科\",\"id\":37},{\"text\":\"信息化管理科\",\"id\":38},{\"text\":\"行政装备科\",\"id\":39}]},{\"id\":\"024\",\"text\":\"政治处\",\"state\":\"open\",\"children\":[{\"text\":\"人事科\",\"id\":40},{\"text\":\"宣教科\",\"id\":41},{\"text\":\"综合科\",\"id\":42},{\"text\":\"职业健康保护科\",\"id\":43}]},{\"id\":\"025\",\"text\":\"侦查处\",\"state\":\"open\",\"children\":[{\"text\":\"侦查一科\",\"id\":44},{\"text\":\"侦查二科\",\"id\":45},{\"text\":\"侦查三科\",\"id\":46},{\"text\":\"侦查四科\",\"id\":47},{\"text\":\"缉毒科\",\"id\":48}]},{\"id\":\"026\",\"text\":\"法制一处\",\"state\":\"open\",\"children\":[{\"text\":\"法制一科\",\"id\":49},{\"text\":\"法制二科\",\"id\":50},{\"text\":\"法制三科\",\"id\":51},{\"text\":\"法制四科\",\"id\":52},{\"text\":\"法制五科\",\"id\":53}]},{\"id\":\"027\",\"text\":\"法制二处\",\"state\":\"open\",\"children\":[{\"text\":\"审理一科\",\"id\":54},{\"text\":\"审理二科\",\"id\":55},{\"text\":\"审理三科\",\"id\":56},{\"text\":\"应诉复议科\",\"id\":57},{\"text\":\"审理四科\",\"id\":58}]},{\"id\":\"028\",\"text\":\"情报技术处\",\"state\":\"open\",\"children\":[{\"text\":\"情报一科\",\"id\":59},{\"text\":\"情报二科\",\"id\":60},{\"text\":\"情报三科\",\"id\":61},{\"text\":\"情报四科\",\"id\":62},{\"text\":\"情报五科\",\"id\":63}]},{\"id\":\"029\",\"text\":\"警务督察处\",\"state\":\"open\",\"children\":[{\"text\":\"纪检监察科\",\"id\":64},{\"text\":\"警务督察科\",\"id\":65}]},{\"id\":\"030\",\"text\":\"刑事技术处\",\"state\":\"open\",\"children\":[{\"text\":\"综合科\",\"id\":66},{\"text\":\"检验鉴定一科\",\"id\":67},{\"text\":\"检验鉴定二科\",\"id\":68}]},{\"id\":\"031\",\"text\":\"海上缉私处\",\"state\":\"open\",\"children\":[{\"text\":\"海上缉私一中队\",\"id\":73},{\"text\":\"海上缉私二中队\",\"id\":74},{\"text\":\"海上缉私三中队\",\"id\":75},{\"text\":\"综合科\",\"id\":80},{\"text\":\"政工科\",\"id\":81},{\"text\":\"船管科\",\"id\":82},{\"text\":\"办案科\",\"id\":83}]},{\"id\":\"032\",\"text\":\"查私处\",\"state\":\"open\",\"children\":[{\"text\":\"查私一科\",\"id\":69},{\"text\":\"查私二科\",\"id\":70},{\"text\":\"查私三科\",\"id\":71},{\"text\":\"查私四科\",\"id\":72}]},{\"id\":\"033\",\"text\":\"黄埔老港海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":79},{\"text\":\"政工科\",\"id\":84},{\"text\":\"侦查科\",\"id\":85},{\"text\":\"法制一科\",\"id\":86},{\"text\":\"情报技术科\",\"id\":87},{\"text\":\"查私科\",\"id\":88},{\"text\":\"法制二科\",\"id\":89}]},{\"id\":\"034\",\"text\":\"新塘海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":90},{\"text\":\"政工科\",\"id\":91},{\"text\":\"侦查科\",\"id\":92},{\"text\":\"法制科\",\"id\":93},{\"text\":\"情报技术科\",\"id\":94},{\"text\":\"查私科\",\"id\":95}]},{\"id\":\"035\",\"text\":\"东莞海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":96},{\"text\":\"政工科\",\"id\":97},{\"text\":\"侦查一科\",\"id\":98},{\"text\":\"侦查二科\",\"id\":99},{\"text\":\"法制一科\",\"id\":100},{\"text\":\"情报技术科\",\"id\":101},{\"text\":\"查私一科\",\"id\":102},{\"text\":\"查私二科\",\"id\":103},{\"text\":\"查私三科\",\"id\":104},{\"text\":\"法制二科\",\"id\":105}]},{\"id\":\"036\",\"text\":\"驻凤岗办事处缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":106},{\"text\":\"政工科\",\"id\":107},{\"text\":\"侦查科\",\"id\":108},{\"text\":\"法制一科\",\"id\":109},{\"text\":\"情报技术科\",\"id\":110},{\"text\":\"查私科\",\"id\":111},{\"text\":\"法制二科\",\"id\":112}]},{\"id\":\"037\",\"text\":\"太平海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":113},{\"text\":\"政工科\",\"id\":114},{\"text\":\"侦查一科\",\"id\":115},{\"text\":\"侦查二科\",\"id\":116},{\"text\":\"法制科\",\"id\":117},{\"text\":\"情报技术科\",\"id\":118},{\"text\":\"查私科\",\"id\":119}]},{\"id\":\"038\",\"text\":\"驻长安办事处缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":120},{\"text\":\"侦查科\",\"id\":121},{\"text\":\"法制科\",\"id\":122},{\"text\":\"查私科\",\"id\":123},{\"text\":\"情报技术科\",\"id\":124},{\"text\":\"政工科\",\"id\":125}]},{\"id\":\"039\",\"text\":\"驻广州经济技术开发区办事处缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"综合科\",\"id\":126},{\"text\":\"法制科\",\"id\":127},{\"text\":\"缉私科\",\"id\":128}]},{\"id\":\"040\",\"text\":\"黄埔新港海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"综合科\",\"id\":129},{\"text\":\"法制科\",\"id\":130},{\"text\":\"缉私科\",\"id\":131}]},{\"id\":\"041\",\"text\":\"新沙海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"综合科\",\"id\":132},{\"text\":\"缉私科\",\"id\":133},{\"text\":\"法制科\",\"id\":134}]},{\"id\":\"042\",\"text\":\"驻常平办事处缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"综合科\",\"id\":135},{\"text\":\"政工科\",\"id\":136},{\"text\":\"缉私一科\",\"id\":137},{\"text\":\"缉私二科\",\"id\":138},{\"text\":\"法制科\",\"id\":139},{\"text\":\"情报技术科\",\"id\":140}]},{\"id\":\"043\",\"text\":\"纪检监察处\",\"state\":\"open\",\"children\":[{\"text\":\"综合审理科\",\"id\":76},{\"text\":\"执纪监督科\",\"id\":77},{\"text\":\"执纪审查科\",\"id\":78}]}]";
//		resResult = "[{\"id\":\"023\",\"text\":\"办公室\",\"state\":\"open\",\"children\":[{\"text\":\"指挥中心\",\"id\":34},{\"text\":\"秘书科\",\"id\":35},{\"text\":\"机要科\",\"id\":36},{\"text\":\"综合治理科\",\"id\":37},{\"text\":\"信息化管理科\",\"id\":38},{\"text\":\"行政装备科\",\"id\":39}]},{\"id\":\"024\",\"text\":\"政治处\",\"state\":\"close\",\"children\":[{\"text\":\"人事科\",\"id\":40},{\"text\":\"宣教科\",\"id\":41},{\"text\":\"综合科\",\"id\":42},{\"text\":\"职业健康保护科\",\"id\":43}]},{\"id\":\"025\",\"text\":\"侦查处\",\"state\":\"close\",\"children\":[{\"text\":\"侦查一科\",\"id\":44},{\"text\":\"侦查二科\",\"id\":45},{\"text\":\"侦查三科\",\"id\":46},{\"text\":\"侦查四科\",\"id\":47},{\"text\":\"缉毒科\",\"id\":48}]},{\"id\":\"026\",\"text\":\"法制一处\",\"state\":\"close\",\"children\":[{\"text\":\"法制一科\",\"id\":49},{\"text\":\"法制二科\",\"id\":50},{\"text\":\"法制三科\",\"id\":51},{\"text\":\"法制四科\",\"id\":52},{\"text\":\"法制五科\",\"id\":53}]},{\"id\":\"027\",\"text\":\"法制二处\",\"state\":\"close\",\"children\":[{\"text\":\"审理一科\",\"id\":54},{\"text\":\"审理二科\",\"id\":55},{\"text\":\"审理三科\",\"id\":56},{\"text\":\"应诉复议科\",\"id\":57},{\"text\":\"审理四科\",\"id\":58}]},{\"id\":\"028\",\"text\":\"情报技术处\",\"state\":\"close\",\"children\":[{\"text\":\"情报一科\",\"id\":59},{\"text\":\"情报二科\",\"id\":60},{\"text\":\"情报三科\",\"id\":61},{\"text\":\"情报四科\",\"id\":62},{\"text\":\"情报五科\",\"id\":63}]},{\"id\":\"029\",\"text\":\"警务督察处\",\"state\":\"close\",\"children\":[{\"text\":\"纪检监察科\",\"id\":64},{\"text\":\"警务督察科\",\"id\":65}]},{\"id\":\"030\",\"text\":\"刑事技术处\",\"state\":\"close\",\"children\":[{\"text\":\"综合科\",\"id\":66},{\"text\":\"检验鉴定一科\",\"id\":67},{\"text\":\"检验鉴定二科\",\"id\":68}]},{\"id\":\"031\",\"text\":\"海上缉私处\",\"state\":\"close\",\"children\":[{\"text\":\"海上缉私一中队\",\"id\":73},{\"text\":\"海上缉私二中队\",\"id\":74},{\"text\":\"海上缉私三中队\",\"id\":75},{\"text\":\"综合科\",\"id\":80},{\"text\":\"政工科\",\"id\":81},{\"text\":\"船管科\",\"id\":82},{\"text\":\"办案科\",\"id\":83}]},{\"id\":\"032\",\"text\":\"查私处\",\"state\":\"close\",\"children\":[{\"text\":\"查私一科\",\"id\":69},{\"text\":\"查私二科\",\"id\":70},{\"text\":\"查私三科\",\"id\":71},{\"text\":\"查私四科\",\"id\":72}]},{\"id\":\"033\",\"text\":\"黄埔老港海关缉私分局\",\"state\":\"close\",\"children\":[{\"text\":\"办公室\",\"id\":79},{\"text\":\"政工科\",\"id\":84},{\"text\":\"侦查科\",\"id\":85},{\"text\":\"法制一科\",\"id\":86},{\"text\":\"情报技术科\",\"id\":87},{\"text\":\"查私科\",\"id\":88},{\"text\":\"法制二科\",\"id\":89}]}]";
//		resResult = "[{\"id\":\"01123\",\"text\":\"办公室\",\"state\":\"open\",\"children\":[{\"text\":\"指挥中心\",\"id\":34},{\"text\":\"秘书科\",\"id\":35},{\"text\":\"机要科\",\"id\":36},{\"text\":\"综合治理科\",\"id\":37},{\"text\":\"信息化管理科\",\"id\":38},{\"text\":\"行政装备科\",\"id\":39}]},{\"id\":\"01124\",\"text\":\"政治处\",\"state\":\"closed\",\"children\":[{\"text\":\"人事科\",\"id\":40},{\"text\":\"宣教科\",\"id\":41},{\"text\":\"综合科\",\"id\":42},{\"text\":\"职业健康保护科\",\"id\":43}]},{\"id\":\"01125\",\"text\":\"侦查处\",\"state\":\"closed\",\"children\":[{\"text\":\"侦查一科\",\"id\":44},{\"text\":\"侦查二科\",\"id\":45},{\"text\":\"侦查三科\",\"id\":46},{\"text\":\"侦查四科\",\"id\":47},{\"text\":\"缉毒科\",\"id\":48}]},{\"id\":\"01126\",\"text\":\"法制一处\",\"state\":\"closed\",\"children\":[{\"text\":\"法制一科\",\"id\":49},{\"text\":\"法制二科\",\"id\":50},{\"text\":\"法制三科\",\"id\":51},{\"text\":\"法制四科\",\"id\":52},{\"text\":\"法制五科\",\"id\":53}]},{\"id\":\"01127\",\"text\":\"法制二处\",\"state\":\"closed\",\"children\":[{\"text\":\"审理一科\",\"id\":54},{\"text\":\"审理二科\",\"id\":55},{\"text\":\"审理三科\",\"id\":56},{\"text\":\"应诉复议科\",\"id\":57},{\"text\":\"审理四科\",\"id\":58}]},{\"id\":\"01128\",\"text\":\"情报技术处\",\"state\":\"closed\",\"children\":[{\"text\":\"情报一科\",\"id\":59},{\"text\":\"情报二科\",\"id\":60},{\"text\":\"情报三科\",\"id\":61},{\"text\":\"情报四科\",\"id\":62},{\"text\":\"情报五科\",\"id\":63}]},{\"id\":\"01129\",\"text\":\"警务督察处\",\"state\":\"closed\",\"children\":[{\"text\":\"纪检监察科\",\"id\":64},{\"text\":\"警务督察科\",\"id\":65}]},{\"id\":\"01130\",\"text\":\"刑事技术处\",\"state\":\"closed\",\"children\":[{\"text\":\"综合科\",\"id\":66},{\"text\":\"检验鉴定一科\",\"id\":67},{\"text\":\"检验鉴定二科\",\"id\":68}]},{\"id\":\"01131\",\"text\":\"海上缉私处\",\"state\":\"closed\",\"children\":[{\"text\":\"海上缉私一中队\",\"id\":73},{\"text\":\"海上缉私二中队\",\"id\":74},{\"text\":\"海上缉私三中队\",\"id\":75},{\"text\":\"综合科\",\"id\":80},{\"text\":\"政工科\",\"id\":81},{\"text\":\"船管科\",\"id\":82},{\"text\":\"办案科\",\"id\":83}]},{\"id\":\"01132\",\"text\":\"查私处\",\"state\":\"closed\",\"children\":[{\"text\":\"查私一科\",\"id\":69},{\"text\":\"查私二科\",\"id\":70},{\"text\":\"查私三科\",\"id\":71},{\"text\":\"查私四科\",\"id\":72}]},{\"id\":\"01133\",\"text\":\"黄埔老港海关缉私分局\",\"state\":\"closed\",\"children\":[{\"text\":\"办公室\",\"id\":79},{\"text\":\"政工科\",\"id\":84},{\"text\":\"侦查科\",\"id\":85},{\"text\":\"法制一科\",\"id\":86},{\"text\":\"情报技术科\",\"id\":87},{\"text\":\"查私科\",\"id\":88},{\"text\":\"法制二科\",\"id\":89}]},{\"id\":\"01134\",\"text\":\"情报技术处\",\"state\":\"closed\",\"children\":[{\"text\":\"情报一科\",\"id\":90},{\"text\":\"情报二科\",\"id\":91},{\"text\":\"情报三科\",\"id\":92}]}]";
		
//		resResult = "[{\"id\":\"000\",\"text\":\"缉私局\",\"state\":\"open\",\"children\":[{\"id\":\"0997\",\"text\":\"政治处\",\"state\":\"closed\",\"children\":[{\"text\":\"人事科\",\"id\":\"0886\",\"state\":\"closed\",\"children\":[{\"text\":\"zhangsan\",\"pri\":\"科长\",\"id\":\"59\"}]},{\"text\":\"宣教科\",\"id\":\"0887\",\"state\":\"closed\",\"children\":[]},{\"text\":\"综合科\",\"id\":\"08845\",\"state\":\"closed\",\"children\":[]}]}]}]";
//		resResult = "[{\"id\":\"000\",\"text\":\"缉私局\",\"state\":\"open\",\"children\":[{\"id\":\"0997\",\"text\":\"政治处\",\"state\":\"closed\",\"children\":[{\"text\":\"人事科\",\"id\":\"0886\",\"state\":\"closed\",\"children\":[{\"text\":\"zhangsan\",\"pri\":\"科长\",\"id\":\"59\"}]},{\"text\":\"宣教科\",\"id\":\"0887\",\"state\":\"closed\",\"children\":[{}]},{\"text\":\"综合科\",\"id\":\"08845\",\"state\":\"closed\",\"children\":[{}]}]}]}]";
//		resResult = "[{\"id\":\"000\",\"text\":\"缉私局\",\"state\":\"open\",\"children\":[{\"id\":\"0997\",\"text\":\"政治处\",\"state\":\"closed\",\"children\":[{\"text\":\"人事科\",\"id\":\"0886\",\"state\":\"closed\",\"children\":[{\"text\":\"zhangsan\",\"pri\":\"科长\",\"id\":\"59\"}]},{\"text\":\"宣教科\",\"id\":\"0887\"},{\"text\":\"综合科\",\"id\":\"08845\"}]}]}]";
		
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
	
	@RequestMapping(value = "/admin/case_grant_confirm.php")
	public String case_grant_confirm(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String caseIDStr = request.getParameter("caseIDStr");
		String caseNameStr = request.getParameter("caseNameStr");
		String uidStr = request.getParameter("uidStr");
		String caseNOStr = request.getParameter("caseNOStr");
	/*	System.out.println("++++"+caseIDStr+"++++");
		System.out.println("++++"+caseNOStr+"++++");
		System.out.println("++++"+caseNameStr+"++++");
		System.out.println("++++"+uidStr+"++++");*/
				
		String[] caseIDs = null;
		String[] caseNOs = null;
		String[] caseNames = null;
		String[] uids = null;
		List<Caseinfo> caseinfos = new ArrayList<Caseinfo>();
		List<User> users = new ArrayList<User>();
		
		if(!StringUtils.isEmpty(caseIDStr)){
			try {
				caseIDStr = URLDecoder.decode(caseIDStr, "UTF-8");
				caseIDs = caseIDStr.split(",");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(caseNOStr)){
			try {
				caseNOStr = URLDecoder.decode(caseNOStr, "UTF-8");
				caseNOs = caseNOStr.split(",");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(caseNameStr)){
			try {
				caseNameStr = URLDecoder.decode(caseNameStr, "UTF-8");
				caseNames = caseNameStr.split("、");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(uidStr)){
			try {
				uidStr = URLDecoder.decode(uidStr, "UTF-8");
				uids = uidStr.split(",");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		if(caseIDs!=null && caseNames!=null && (caseIDs.length==caseNames.length)){
			for(int i=0; i<caseIDs.length; i++){
				Caseinfo caseinfo = new Caseinfo();
				try {
					caseinfo.setId(Integer.parseInt(caseIDs[i]));
				} catch (Exception e) {
					e.printStackTrace();
				}
				caseinfo.setCaseNum(caseNOs[i]);
				caseinfo.setCaseName(caseNames[i]);
				caseinfos.add(caseinfo);
			}
		}
		if(uids != null){
			for(int i=0; i<uids.length; i++){
				try {
					User tempUser = new User();
					tempUser.setId(Integer.parseInt(uids[i]));
					List<User> tempUsers = sqlDao.getListfromMysql(tempUser, 0, 1);
					
					if(tempUsers.size()>0){
						Section section2 = new Section();
						section2.setId(Integer.parseInt(tempUsers.get(0).getSection()) );
						List<Section> sections2 = sqlDao.getListfromMysql(section2);
						if(sections2.size()>0){
							Section section3 = sections2.get(0);
							tempUsers.get(0).setSection(section3.getSectionName());
						}
						Role role2 = new Role();
						String roleid=tempUsers.get(0).getUserrole();
						if(roleid!=null && !"".equals(roleid)){
							role2.setId(Integer.parseInt(roleid) );
							List<Role> roles2 = sqlDao.getListfromMysql(role2);
							if(roles2.size()>0){
								Role roles3 = roles2.get(0);
								tempUsers.get(0).setPrivilege(roles3.getRoleName());
								tempUsers.get(0).setUserrole(roles3.getRoleName());
							}
						}
						users.add(tempUsers.get(0));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		map.put("caseIDStr", caseIDStr);
		map.put("uidStr", uidStr);
		map.put("caseinfos", caseinfos);
		map.put("users", users);
		return "case_grant_confirm";
	}
	
	@RequestMapping(value = "/admin/addGrant.php")
	public void addGrant(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String username = request.getParameter("user");//(String) session.getAttribute("userName");
		String caseIDStr = request.getParameter("caseIDStr");
		String uidStr = request.getParameter("uidStr");
		/*System.out.println("++++"+caseIDStr+"++++");
		System.out.println("++++"+uidStr+"++++");*/
				
		List<Caseinfo> caseinfos = new ArrayList<Caseinfo>();
		List<User> users = new ArrayList<User>();
		String[] caseIDs = null;
		String[] uids = null;
		
		if(!StringUtils.isEmpty(caseIDStr)){
			caseIDs = caseIDStr.split(",");
		}
		if(!StringUtils.isEmpty(uidStr)){
			uids = uidStr.split(",");
		}
		
		if(caseIDs != null){
			for(int i=0; i<caseIDs.length; i++){
				Caseinfo caseinfo = new Caseinfo();
				try {
					caseinfo.setId(Integer.parseInt(caseIDs[i]));
					List<Caseinfo> caseList = sqlDao.getListfromMysql(caseinfo, 0, 1);
					if(caseList.size()>0){
						caseinfos.add(caseList.get(0));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(uids != null){
			for(int i=0; i<uids.length; i++){
				try {
					User tempUser = new User();
					tempUser.setId(Integer.parseInt(uids[i]));
					List<User> tempUsers = sqlDao.getListfromMysql(tempUser, 0, 1);
					if(tempUsers.size()>0){
						users.add(tempUsers.get(0));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		for(Caseinfo caseinfo:caseinfos){
			String un = caseinfo.getTrustee();
			String toUserName = "";
			for(User tempU:users){
				String uname = tempU.getUsername()+",";
				if (un!=null && un.contains(uname)) {
				} else {
					toUserName = toUserName + uname;
					Coordinatelog log = new Coordinatelog();
					Role role = new Role();
					role.setId(Integer.parseInt(tempU.getUserrole()));
					List<Role> rolelist = sqlDao.getListfromMysql(role);
					if(rolelist.size()>0){
						Role role2 = rolelist.get(0);
						log.setGrantedPri(role2.getRoleName());
					}
					Section section = new Section();
					section.setId(Integer.parseInt(tempU.getSection()));
					List<Section> sectionlist = sqlDao.getListfromMysql(section);
					if(sectionlist.size()>0){
						Section section2 = sectionlist.get(0);
						log.setSection(section2.getSectionName());
					}
					Department department = new Department();
					role.setId(Integer.parseInt(caseinfo.getDepartment()));
					List<Department> departmentlist = sqlDao.getListfromMysql(department);
					if(departmentlist.size()>0){
						Department department2 = departmentlist.get(0);
						log.setDepartment(department2.getDepartmentName());
					}
					log.setCaseNum(caseinfo.getCaseNum());
					log.setCaseName(caseinfo.getCaseName());
					log.setCaseType(caseinfo.getCaseType());
					log.setGrantName(username);
					log.setGrantedName(tempU.getUsername());
					log.setGrantedSec(tempU.getSection());
					log.setOperateTime(dateFormat.format(new Date()));
					log.setType("新增授权");
					sqlDao.setBeanToMysql(log);
				}
			}
			if(!toUserName.equals("")){
				if (StringUtils.isEmpty(un) || un.endsWith(",")) {
					un = un + toUserName;
				} else {
					un = un + "," + toUserName+",";
					/*System.out.println(un);*/
				}
				Caseinfo case_update = new Caseinfo();
				case_update.setId(caseinfo.getId());
				case_update.setTrustee(un);
				sqlDao.updateToMysql(case_update);
			}
		}
		
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write("{\"res\":\"succ\"}");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pw != null){
				pw.close();
			}
		}
		
	}
	
	/**取消案件授权-选择人员
	 * @param request
	 * @param map
	 * @param session
	 * @param response
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/admin/case_cancel_granttwo.php")
	public String case_cancel_granttwo(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String id = request.getParameter("id");
		String noStr = request.getParameter("noStr");
		String str = request.getParameter("str");
		String unameStr = request.getParameter("unameStr");
				
		List<User> users = new ArrayList<User>();
		if(!StringUtils.isEmpty(id)){
			try {
				id = URLDecoder.decode(id, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(noStr)){
			try {
				noStr = URLDecoder.decode(noStr, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(str)){
			try {
				str = URLDecoder.decode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(unameStr)){
			try {
				unameStr = URLDecoder.decode(unameStr, "UTF-8");
				
				String[] unames = unameStr.split(",");
				for(String uname:unames){
					User tempU = new User();
					tempU.setUsername(uname);
					List<User> uList = sqlDao.getListfromMysql(tempU, 0, 1);
					if(uList.size()>0){
						User user =	uList.get(0);
						String sections= user.getSection();
						String ptters = user.getUserrole();
						Section section2 = new Section();
						section2.setId(Integer.parseInt(sections) );
						List<Section> sections2 = sqlDao.getListfromMysql(section2);
						if(sections2.size()>0){
							Section section3 = sections2.get(0);
							Role role2 = new Role();
							role2.setId(Integer.parseInt(ptters));
							user.setSection(section3.getSectionName());
							List<Role> roles2 = sqlDao.getListfromMysql(role2);
							if(roles2.size()>0){
								Role roles3 = roles2.get(0);
								user.setUserrole(roles3.getRoleName());
							}
						}
						users.add(user);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		map.put("id", id);
		map.put("noStr", noStr);
		map.put("str", str);
		map.put("users", users);
		return "case_cancel_granttwo";
	}

	@RequestMapping(value = "/admin/case_cancel_confirm.php")
	public String case_cancel_confirm(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String caseIDStr = request.getParameter("caseIDStr");
		String caseNameStr = request.getParameter("caseNameStr");
		String uidStr = request.getParameter("uidStr");
		String caseNOStr = request.getParameter("caseNOStr");
				
		String[] caseIDs = null;
		String[] caseNOs = null;
		String[] caseNames = null;
		String[] uids = null;
		List<Caseinfo> caseinfos = new ArrayList<Caseinfo>();
		List<User> users = new ArrayList<User>();
		
		if(!StringUtils.isEmpty(caseIDStr)){
			try {
				caseIDStr = URLDecoder.decode(caseIDStr, "UTF-8");
				caseIDs = caseIDStr.split(",");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(caseNOStr)){
			try {
				caseNOStr = URLDecoder.decode(caseNOStr, "UTF-8");
				caseNOs = caseNOStr.split(",");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(caseNameStr)){
			try {
				caseNameStr = URLDecoder.decode(caseNameStr, "UTF-8");
				caseNames = caseNameStr.split("、");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(uidStr)){
			try {
				uidStr = URLDecoder.decode(uidStr, "UTF-8");
				uids = uidStr.split(",");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		if(caseIDs!=null && caseNames!=null && (caseIDs.length==caseNames.length)){
			for(int i=0; i<caseIDs.length; i++){
				Caseinfo caseinfo = new Caseinfo();
				try {
					caseinfo.setId(Integer.parseInt(caseIDs[i]));
				} catch (Exception e) {
					e.printStackTrace();
				}
				caseinfo.setCaseNum(caseNOs[i]);
				caseinfo.setCaseName(caseNames[i]);
				caseinfos.add(caseinfo);
			}
		}
		if(uids != null){
			for(int i=0; i<uids.length; i++){
				try {
					User tempUser = new User();
					tempUser.setId(Integer.parseInt(uids[i]));
					List<User> tempUsers = sqlDao.getListfromMysql(tempUser, 0, 1);
					if(tempUsers.size()>0){
						User user =	tempUsers.get(0);
						String sections= user.getSection();
						String ptters = user.getUserrole();
						Section section2 = new Section();
						section2.setId(Integer.parseInt(sections) );
						List<Section> sections2 = sqlDao.getListfromMysql(section2);
						if(sections2.size()> 0){
							Section section3 = sections2.get(0);
							Role role2 = new Role();
							role2.setId(Integer.parseInt(ptters) );
							user.setSection(section3.getSectionName());
							List<Role> roles2 = sqlDao.getListfromMysql(role2);
							if(roles2.size()>0){
								Role roles3 = roles2.get(0);
								user.setUserrole(roles3.getRoleName());	
							}
						}
						users.add(user);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		map.put("caseIDStr", caseIDStr);
		map.put("uidStr", uidStr);
		map.put("caseinfos", caseinfos);
		map.put("users", users);
		return "case_cancel_confirm";
	}
	
	@RequestMapping(value = "/admin/cancelGrant.php")
	public void cancelGrant(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
 		String username = (String) session.getAttribute("userName");
		String caseIDStr = request.getParameter("caseIDStr");
		String uidStr = request.getParameter("uidStr");
				
		List<Caseinfo> caseinfos = new ArrayList<Caseinfo>();
		List<User> users = new ArrayList<User>();
		String[] caseIDs = null;
		String[] uids = null;
		
		if(!StringUtils.isEmpty(caseIDStr)){
			caseIDs = caseIDStr.split(",");
		}
		if(!StringUtils.isEmpty(uidStr)){
			uids = uidStr.split(",");
		}
		
		if(caseIDs != null){
			for(int i=0; i<caseIDs.length; i++){
				Caseinfo caseinfo = new Caseinfo();
				try {
					caseinfo.setId(Integer.parseInt(caseIDs[i]));
					List<Caseinfo> caseList = sqlDao.getListfromMysql(caseinfo, 0, 1);
					if(caseList.size()>0){
						caseinfos.add(caseList.get(0));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(uids != null){
			for(int i=0; i<uids.length; i++){
				try {
					User tempUser = new User();
					tempUser.setId(Integer.parseInt(uids[i]));
					List<User> tempUsers = sqlDao.getListfromMysql(tempUser, 0, 1);
					if(tempUsers.size()>0){
						users.add(tempUsers.get(0));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		for(Caseinfo caseinfo:caseinfos){
			String un = caseinfo.getTrustee();
			boolean uflag = false;
			for(User tempU:users){
				String uname = tempU.getUsername()+",";
				if (un!=null && un.contains(uname)) {
					uflag = true;
					un = un.replace(uname, "");
					Coordinatelog log = new Coordinatelog();
					Role role = new Role();
					role.setId(Integer.parseInt(tempU.getUserrole()));
					List<Role> rolelist = sqlDao.getListfromMysql(role);
					if(rolelist.size()>0){
						Role role2 = rolelist.get(0);
						log.setGrantedPri(role2.getRoleName());
					}
					Section section = new Section();
					section.setId(Integer.parseInt(tempU.getSection()));
					List<Section> sectionlist = sqlDao.getListfromMysql(section);
					if(sectionlist.size()>0){
						Section section2 = sectionlist.get(0);
						log.setSection(section2.getSectionName());
					}
					Department department = new Department();
					role.setId(Integer.parseInt(caseinfo.getDepartment()));
					List<Department> departmentlist = sqlDao.getListfromMysql(department);
					if(departmentlist.size()>0){
						Department department2 = departmentlist.get(0);
						log.setDepartment(department2.getDepartmentName());
					}
					log.setCaseNum(caseinfo.getCaseNum());
					log.setCaseName(caseinfo.getCaseName());
					log.setCaseType(caseinfo.getCaseType());
					log.setGrantName(username);
					log.setGrantedName(tempU.getUsername());
					log.setGrantedSec(tempU.getSection());
					log.setOperateTime(dateFormat.format(new Date()));
					log.setType("取消授权");
					sqlDao.setBeanToMysql(log);
				}
			}
			if(uflag){
				Caseinfo case_update = new Caseinfo();
				case_update.setId(caseinfo.getId());
				case_update.setTrustee(un);
				sqlDao.updateToMysql(case_update);
			}
		}
		
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write("{\"res\":\"succ\"}");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pw != null){
				pw.close();
			}
		}
		
	}
	
	/**协同操作日志列表页
	 * @param request
	 * @param map
	 * @param session
	 * @param response
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/admin/userManager.php")
	public String userManager(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		
		int pageIndex = 1;
		int pageSize = 10;
		int num = 0;
		
		User user = new User();
		List<User> listfromMysql = new ArrayList<User>();
		List<User> users = sqlDao.getOrderListfromMysql(user, (pageIndex-1) * pageSize, pageSize, "id");
		for (User users2 : users) {
			String department = users2.getPartment();
			Department department2 = new Department();
			department2.setId(Integer.parseInt(department));
			List<Department> listfromMysql2 = sqlDao.getListfromMysql(department2);
			if(listfromMysql2.size()>0){
				Department department3 = listfromMysql2.get(0);
				String departmentName = department3.getDepartmentName();
				users2.setPartment(departmentName);
			}
			
			
			String section2 = users2.getSection();
			Section sections2 = new Section();
			if(section2!=null && !"".equals(section2)){
				 if("-1".equals(section2)){
					  users2.setSection("无");
				 }else{
						sections2.setId(Integer.parseInt(section2));
						List<Section> listfromMysqls = sqlDao.getListfromMysql(sections2);
						if(listfromMysqls.size() > 0){
							Section sections3 = listfromMysqls.get(0);
							String sectionsName = sections3.getSectionName();
							users2.setSection(sectionsName);
						} 
				 }

			}
			
			
			
			String ptters = users2.getUserrole();
			Role role2 = new Role();
			if(ptters!=null && !"".equals(ptters)){
				role2.setId(Integer.parseInt(ptters));
				List<Role> roles2 = sqlDao.getListfromMysql(role2);
				if (roles2.size() > 0) {
					Role roles3 = roles2.get(0);
					users2.setPrivilege(roles3.getId()+"/"+roles3.getRoleName());
				}
			}else{
				users2.setPrivilege("无");
			}
			
			
			
			/*String role2 = users2.getPrivilege();
			RoleTable RoleTable2 = new RoleTable();
			RoleTable2.setId(Integer.parseInt(role2));
			List<RoleTable> roleMysqls = sqlDao.getListfromMysql(RoleTable2);
			if(roleMysqls.size() > 0){
				RoleTable RoleTable3 = roleMysqls.get(0);
				String RoleTableName = RoleTable3.getPartterName();
				users2.setPrivilege(RoleTableName);
			}*/
			
			
			listfromMysql.add(users2);
		}
		int total = sqlDao.getcountfromMysqlLike(user);
		num = total / pageSize;
		if (total % pageSize != 0){
			num++;
		}
		num = num==0?1:num;
		
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
		
		map.put("users", users);
		map.put("totalNum", total);
		map.put("totalPages", num);
		map.put("nowPage", pageIndex);
		map.put("pageList", pageList);
		
		return "usermanager";
	}
	
	@RequestMapping(value = "/admin/initDSTree.php")
	public void initDSTree(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String resResult = null;
		StringBuilder sbtmp = new StringBuilder();
		List<Department> departments = sqlDao.getListfromMysql(new Department());
		
		sbtmp.append("[");
		sbtmp.append("{\"id\":\"000\",");
		sbtmp.append("\"text\":\"缉私局\"");
		if (departments != null && departments.size() > 0) {
			sbtmp.append(",\"state\":\"open\"");
			sbtmp.append(",\"children\":[");
			for (int i = 0; i < departments.size(); i++) {
				Department tempD = departments.get(i);
				String departName = tempD.getDepartmentName();
				int id = tempD.getId();
				Section section = new Section();
				section.setDepartment(id+"");
				List<Section> sections = sqlDao.getListfromMysql(section);
				sbtmp.append("{\"id\":\"099" + id + "\",");
				sbtmp.append("\"text\":\"" + departName.trim() + "\"");
				
				if (sections != null && sections.size() > 0) {
					if(i==0){
						sbtmp.append(",\"state\":\"open\"");
					}else{
						sbtmp.append(",\"state\":\"closed\"");
					}
					sbtmp.append(",\"children\":[");
					
					for (int j = 0; j < sections.size(); j++) {
						section = sections.get(j);
						String sectionName = section.getSectionName();
						// String evmaiBox = ei.getCreateTime();
						int sectionId = section.getId();
						sbtmp.append("{\"text\":\"" + sectionName.trim() + "\",");
						sbtmp.append("\"id\":\"" + sectionId + "\"");
						sbtmp.append("},");
					}
					sbtmp.deleteCharAt(sbtmp.length() - 1);
					sbtmp.append("]");
				}
				sbtmp.append("},");
			}
			sbtmp.deleteCharAt(sbtmp.length() - 1);
			sbtmp.append("]");
		}
		sbtmp.append("}]");

		resResult = sbtmp.toString();
//		System.out.println("resResult:"+resResult);
//		System.out.println("部门科室树："+resJson.toString());
//		resResult = "[{\"id\":\"023\",\"text\":\"办公室\",\"state\":\"open\",\"children\":[{\"text\":\"指挥中心\",\"id\":34},{\"text\":\"秘书科\",\"id\":35},{\"text\":\"机要科\",\"id\":36},{\"text\":\"综合治理科\",\"id\":37},{\"text\":\"信息化管理科\",\"id\":38},{\"text\":\"行政装备科\",\"id\":39}]},{\"id\":\"024\",\"text\":\"政治处\",\"state\":\"open\",\"children\":[{\"text\":\"人事科\",\"id\":40},{\"text\":\"宣教科\",\"id\":41},{\"text\":\"综合科\",\"id\":42},{\"text\":\"职业健康保护科\",\"id\":43}]},{\"id\":\"025\",\"text\":\"侦查处\",\"state\":\"open\",\"children\":[{\"text\":\"侦查一科\",\"id\":44},{\"text\":\"侦查二科\",\"id\":45},{\"text\":\"侦查三科\",\"id\":46},{\"text\":\"侦查四科\",\"id\":47},{\"text\":\"缉毒科\",\"id\":48}]},{\"id\":\"026\",\"text\":\"法制一处\",\"state\":\"open\",\"children\":[{\"text\":\"法制一科\",\"id\":49},{\"text\":\"法制二科\",\"id\":50},{\"text\":\"法制三科\",\"id\":51},{\"text\":\"法制四科\",\"id\":52},{\"text\":\"法制五科\",\"id\":53}]},{\"id\":\"027\",\"text\":\"法制二处\",\"state\":\"open\",\"children\":[{\"text\":\"审理一科\",\"id\":54},{\"text\":\"审理二科\",\"id\":55},{\"text\":\"审理三科\",\"id\":56},{\"text\":\"应诉复议科\",\"id\":57},{\"text\":\"审理四科\",\"id\":58}]},{\"id\":\"028\",\"text\":\"情报技术处\",\"state\":\"open\",\"children\":[{\"text\":\"情报一科\",\"id\":59},{\"text\":\"情报二科\",\"id\":60},{\"text\":\"情报三科\",\"id\":61},{\"text\":\"情报四科\",\"id\":62},{\"text\":\"情报五科\",\"id\":63}]},{\"id\":\"029\",\"text\":\"警务督察处\",\"state\":\"open\",\"children\":[{\"text\":\"纪检监察科\",\"id\":64},{\"text\":\"警务督察科\",\"id\":65}]},{\"id\":\"030\",\"text\":\"刑事技术处\",\"state\":\"open\",\"children\":[{\"text\":\"综合科\",\"id\":66},{\"text\":\"检验鉴定一科\",\"id\":67},{\"text\":\"检验鉴定二科\",\"id\":68}]},{\"id\":\"031\",\"text\":\"海上缉私处\",\"state\":\"open\",\"children\":[{\"text\":\"海上缉私一中队\",\"id\":73},{\"text\":\"海上缉私二中队\",\"id\":74},{\"text\":\"海上缉私三中队\",\"id\":75},{\"text\":\"综合科\",\"id\":80},{\"text\":\"政工科\",\"id\":81},{\"text\":\"船管科\",\"id\":82},{\"text\":\"办案科\",\"id\":83}]},{\"id\":\"032\",\"text\":\"查私处\",\"state\":\"open\",\"children\":[{\"text\":\"查私一科\",\"id\":69},{\"text\":\"查私二科\",\"id\":70},{\"text\":\"查私三科\",\"id\":71},{\"text\":\"查私四科\",\"id\":72}]},{\"id\":\"033\",\"text\":\"黄埔老港海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":79},{\"text\":\"政工科\",\"id\":84},{\"text\":\"侦查科\",\"id\":85},{\"text\":\"法制一科\",\"id\":86},{\"text\":\"情报技术科\",\"id\":87},{\"text\":\"查私科\",\"id\":88},{\"text\":\"法制二科\",\"id\":89}]},{\"id\":\"034\",\"text\":\"新塘海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":90},{\"text\":\"政工科\",\"id\":91},{\"text\":\"侦查科\",\"id\":92},{\"text\":\"法制科\",\"id\":93},{\"text\":\"情报技术科\",\"id\":94},{\"text\":\"查私科\",\"id\":95}]},{\"id\":\"035\",\"text\":\"东莞海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":96},{\"text\":\"政工科\",\"id\":97},{\"text\":\"侦查一科\",\"id\":98},{\"text\":\"侦查二科\",\"id\":99},{\"text\":\"法制一科\",\"id\":100},{\"text\":\"情报技术科\",\"id\":101},{\"text\":\"查私一科\",\"id\":102},{\"text\":\"查私二科\",\"id\":103},{\"text\":\"查私三科\",\"id\":104},{\"text\":\"法制二科\",\"id\":105}]},{\"id\":\"036\",\"text\":\"驻凤岗办事处缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":106},{\"text\":\"政工科\",\"id\":107},{\"text\":\"侦查科\",\"id\":108},{\"text\":\"法制一科\",\"id\":109},{\"text\":\"情报技术科\",\"id\":110},{\"text\":\"查私科\",\"id\":111},{\"text\":\"法制二科\",\"id\":112}]},{\"id\":\"037\",\"text\":\"太平海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":113},{\"text\":\"政工科\",\"id\":114},{\"text\":\"侦查一科\",\"id\":115},{\"text\":\"侦查二科\",\"id\":116},{\"text\":\"法制科\",\"id\":117},{\"text\":\"情报技术科\",\"id\":118},{\"text\":\"查私科\",\"id\":119}]},{\"id\":\"038\",\"text\":\"驻长安办事处缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"办公室\",\"id\":120},{\"text\":\"侦查科\",\"id\":121},{\"text\":\"法制科\",\"id\":122},{\"text\":\"查私科\",\"id\":123},{\"text\":\"情报技术科\",\"id\":124},{\"text\":\"政工科\",\"id\":125}]},{\"id\":\"039\",\"text\":\"驻广州经济技术开发区办事处缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"综合科\",\"id\":126},{\"text\":\"法制科\",\"id\":127},{\"text\":\"缉私科\",\"id\":128}]},{\"id\":\"040\",\"text\":\"黄埔新港海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"综合科\",\"id\":129},{\"text\":\"法制科\",\"id\":130},{\"text\":\"缉私科\",\"id\":131}]},{\"id\":\"041\",\"text\":\"新沙海关缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"综合科\",\"id\":132},{\"text\":\"缉私科\",\"id\":133},{\"text\":\"法制科\",\"id\":134}]},{\"id\":\"042\",\"text\":\"驻常平办事处缉私分局\",\"state\":\"open\",\"children\":[{\"text\":\"综合科\",\"id\":135},{\"text\":\"政工科\",\"id\":136},{\"text\":\"缉私一科\",\"id\":137},{\"text\":\"缉私二科\",\"id\":138},{\"text\":\"法制科\",\"id\":139},{\"text\":\"情报技术科\",\"id\":140}]},{\"id\":\"043\",\"text\":\"纪检监察处\",\"state\":\"open\",\"children\":[{\"text\":\"综合审理科\",\"id\":76},{\"text\":\"执纪监督科\",\"id\":77},{\"text\":\"执纪审查科\",\"id\":78}]}]";
//		resResult = "[{\"id\":\"023\",\"text\":\"办公室\",\"state\":\"open\",\"children\":[{\"text\":\"指挥中心\",\"id\":34},{\"text\":\"秘书科\",\"id\":35},{\"text\":\"机要科\",\"id\":36},{\"text\":\"综合治理科\",\"id\":37},{\"text\":\"信息化管理科\",\"id\":38},{\"text\":\"行政装备科\",\"id\":39}]},{\"id\":\"024\",\"text\":\"政治处\",\"state\":\"close\",\"children\":[{\"text\":\"人事科\",\"id\":40},{\"text\":\"宣教科\",\"id\":41},{\"text\":\"综合科\",\"id\":42},{\"text\":\"职业健康保护科\",\"id\":43}]},{\"id\":\"025\",\"text\":\"侦查处\",\"state\":\"close\",\"children\":[{\"text\":\"侦查一科\",\"id\":44},{\"text\":\"侦查二科\",\"id\":45},{\"text\":\"侦查三科\",\"id\":46},{\"text\":\"侦查四科\",\"id\":47},{\"text\":\"缉毒科\",\"id\":48}]},{\"id\":\"026\",\"text\":\"法制一处\",\"state\":\"close\",\"children\":[{\"text\":\"法制一科\",\"id\":49},{\"text\":\"法制二科\",\"id\":50},{\"text\":\"法制三科\",\"id\":51},{\"text\":\"法制四科\",\"id\":52},{\"text\":\"法制五科\",\"id\":53}]},{\"id\":\"027\",\"text\":\"法制二处\",\"state\":\"close\",\"children\":[{\"text\":\"审理一科\",\"id\":54},{\"text\":\"审理二科\",\"id\":55},{\"text\":\"审理三科\",\"id\":56},{\"text\":\"应诉复议科\",\"id\":57},{\"text\":\"审理四科\",\"id\":58}]},{\"id\":\"028\",\"text\":\"情报技术处\",\"state\":\"close\",\"children\":[{\"text\":\"情报一科\",\"id\":59},{\"text\":\"情报二科\",\"id\":60},{\"text\":\"情报三科\",\"id\":61},{\"text\":\"情报四科\",\"id\":62},{\"text\":\"情报五科\",\"id\":63}]},{\"id\":\"029\",\"text\":\"警务督察处\",\"state\":\"close\",\"children\":[{\"text\":\"纪检监察科\",\"id\":64},{\"text\":\"警务督察科\",\"id\":65}]},{\"id\":\"030\",\"text\":\"刑事技术处\",\"state\":\"close\",\"children\":[{\"text\":\"综合科\",\"id\":66},{\"text\":\"检验鉴定一科\",\"id\":67},{\"text\":\"检验鉴定二科\",\"id\":68}]},{\"id\":\"031\",\"text\":\"海上缉私处\",\"state\":\"close\",\"children\":[{\"text\":\"海上缉私一中队\",\"id\":73},{\"text\":\"海上缉私二中队\",\"id\":74},{\"text\":\"海上缉私三中队\",\"id\":75},{\"text\":\"综合科\",\"id\":80},{\"text\":\"政工科\",\"id\":81},{\"text\":\"船管科\",\"id\":82},{\"text\":\"办案科\",\"id\":83}]},{\"id\":\"032\",\"text\":\"查私处\",\"state\":\"close\",\"children\":[{\"text\":\"查私一科\",\"id\":69},{\"text\":\"查私二科\",\"id\":70},{\"text\":\"查私三科\",\"id\":71},{\"text\":\"查私四科\",\"id\":72}]},{\"id\":\"033\",\"text\":\"黄埔老港海关缉私分局\",\"state\":\"close\",\"children\":[{\"text\":\"办公室\",\"id\":79},{\"text\":\"政工科\",\"id\":84},{\"text\":\"侦查科\",\"id\":85},{\"text\":\"法制一科\",\"id\":86},{\"text\":\"情报技术科\",\"id\":87},{\"text\":\"查私科\",\"id\":88},{\"text\":\"法制二科\",\"id\":89}]}]";
//		resResult = "[{\"id\":\"01123\",\"text\":\"办公室\",\"state\":\"open\",\"children\":[{\"text\":\"指挥中心\",\"id\":34},{\"text\":\"秘书科\",\"id\":35},{\"text\":\"机要科\",\"id\":36},{\"text\":\"综合治理科\",\"id\":37},{\"text\":\"信息化管理科\",\"id\":38},{\"text\":\"行政装备科\",\"id\":39}]},{\"id\":\"01124\",\"text\":\"政治处\",\"state\":\"closed\",\"children\":[{\"text\":\"人事科\",\"id\":40},{\"text\":\"宣教科\",\"id\":41},{\"text\":\"综合科\",\"id\":42},{\"text\":\"职业健康保护科\",\"id\":43}]},{\"id\":\"01125\",\"text\":\"侦查处\",\"state\":\"closed\",\"children\":[{\"text\":\"侦查一科\",\"id\":44},{\"text\":\"侦查二科\",\"id\":45},{\"text\":\"侦查三科\",\"id\":46},{\"text\":\"侦查四科\",\"id\":47},{\"text\":\"缉毒科\",\"id\":48}]},{\"id\":\"01126\",\"text\":\"法制一处\",\"state\":\"closed\",\"children\":[{\"text\":\"法制一科\",\"id\":49},{\"text\":\"法制二科\",\"id\":50},{\"text\":\"法制三科\",\"id\":51},{\"text\":\"法制四科\",\"id\":52},{\"text\":\"法制五科\",\"id\":53}]},{\"id\":\"01127\",\"text\":\"法制二处\",\"state\":\"closed\",\"children\":[{\"text\":\"审理一科\",\"id\":54},{\"text\":\"审理二科\",\"id\":55},{\"text\":\"审理三科\",\"id\":56},{\"text\":\"应诉复议科\",\"id\":57},{\"text\":\"审理四科\",\"id\":58}]},{\"id\":\"01128\",\"text\":\"情报技术处\",\"state\":\"closed\",\"children\":[{\"text\":\"情报一科\",\"id\":59},{\"text\":\"情报二科\",\"id\":60},{\"text\":\"情报三科\",\"id\":61},{\"text\":\"情报四科\",\"id\":62},{\"text\":\"情报五科\",\"id\":63}]},{\"id\":\"01129\",\"text\":\"警务督察处\",\"state\":\"closed\",\"children\":[{\"text\":\"纪检监察科\",\"id\":64},{\"text\":\"警务督察科\",\"id\":65}]},{\"id\":\"01130\",\"text\":\"刑事技术处\",\"state\":\"closed\",\"children\":[{\"text\":\"综合科\",\"id\":66},{\"text\":\"检验鉴定一科\",\"id\":67},{\"text\":\"检验鉴定二科\",\"id\":68}]},{\"id\":\"01131\",\"text\":\"海上缉私处\",\"state\":\"closed\",\"children\":[{\"text\":\"海上缉私一中队\",\"id\":73},{\"text\":\"海上缉私二中队\",\"id\":74},{\"text\":\"海上缉私三中队\",\"id\":75},{\"text\":\"综合科\",\"id\":80},{\"text\":\"政工科\",\"id\":81},{\"text\":\"船管科\",\"id\":82},{\"text\":\"办案科\",\"id\":83}]},{\"id\":\"01132\",\"text\":\"查私处\",\"state\":\"closed\",\"children\":[{\"text\":\"查私一科\",\"id\":69},{\"text\":\"查私二科\",\"id\":70},{\"text\":\"查私三科\",\"id\":71},{\"text\":\"查私四科\",\"id\":72}]},{\"id\":\"01133\",\"text\":\"黄埔老港海关缉私分局\",\"state\":\"closed\",\"children\":[{\"text\":\"办公室\",\"id\":79},{\"text\":\"政工科\",\"id\":84},{\"text\":\"侦查科\",\"id\":85},{\"text\":\"法制一科\",\"id\":86},{\"text\":\"情报技术科\",\"id\":87},{\"text\":\"查私科\",\"id\":88},{\"text\":\"法制二科\",\"id\":89}]},{\"id\":\"01134\",\"text\":\"情报技术处\",\"state\":\"closed\",\"children\":[{\"text\":\"情报一科\",\"id\":90},{\"text\":\"情报二科\",\"id\":91},{\"text\":\"情报三科\",\"id\":92}]}]";
		
//		resResult = "[{\"id\":\"000\",\"text\":\"缉私局\",\"state\":\"open\",\"children\":[{\"id\":\"0997\",\"text\":\"政治处\",\"state\":\"closed\",\"children\":[{\"text\":\"人事科\",\"id\":\"0886\",\"state\":\"closed\",\"children\":[{\"text\":\"zhangsan\",\"pri\":\"科长\",\"id\":\"59\"}]},{\"text\":\"宣教科\",\"id\":\"0887\",\"state\":\"closed\",\"children\":[]},{\"text\":\"综合科\",\"id\":\"08845\",\"state\":\"closed\",\"children\":[]}]}]}]";
//		resResult = "[{\"id\":\"000\",\"text\":\"缉私局\",\"state\":\"open\",\"children\":[{\"id\":\"0997\",\"text\":\"政治处\",\"state\":\"closed\",\"children\":[{\"text\":\"人事科\",\"id\":\"0886\",\"state\":\"closed\",\"children\":[{\"text\":\"zhangsan\",\"pri\":\"科长\",\"id\":\"59\"}]},{\"text\":\"宣教科\",\"id\":\"0887\",\"state\":\"closed\",\"children\":[{}]},{\"text\":\"综合科\",\"id\":\"08845\",\"state\":\"closed\",\"children\":[{}]}]}]}]";
//		resResult = "[{\"id\":\"000\",\"text\":\"缉私局\",\"state\":\"open\",\"children\":[{\"id\":\"0997\",\"text\":\"政治处\",\"state\":\"closed\",\"children\":[{\"text\":\"人事科\",\"id\":\"0886\",\"state\":\"closed\",\"children\":[{\"text\":\"zhangsan\",\"pri\":\"科长\",\"id\":\"59\"}]},{\"text\":\"宣教科\",\"id\":\"0887\"},{\"text\":\"综合科\",\"id\":\"08845\"}]}]}]";
		
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
	
	//部门查询
	@RequestMapping(value = "/admin/getAllDepartments.php")
	public void getAllDepartments(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		
		List<Department> departments = sqlDao.getOrderListfromMysql(new Department(), "id");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(departments));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}
	//科室查询
	@RequestMapping(value = "/admin/getAllSections.php")
	public void getAllSections(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String department = request.getParameter("department");
		Department departments = new Department();
		departments.setDepartmentName(department);
		List<Department> depars = sqlDao.getOrderListfromMysql(departments, "id");
		List<Section> sections = new ArrayList<Section>();
		if(depars.size() >0){
			int depar = depars.get(0).getId();
			Section section = new Section();
			section.setDepartment(depar+"");
			sections = sqlDao.getOrderListfromMysql(section, "id");
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(sections));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}
	
	//科室查询
	@RequestMapping(value = "/admin/getAllRole.php")
	public void getAllRole(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String department = request.getParameter("department");
		Section section = new Section();
		section.setDepartment(department);
		List<Section> sections = sqlDao.getOrderListfromMysql(section, "id");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(sections));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}
	
	@RequestMapping(value = "/admin/addUser.php")
	public void addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String username = request.getParameter("addusername");
		String psw = request.getParameter("adduserpsw");
		String previlige = request.getParameter("adduserprevilige");
		String remark = request.getParameter("adduserremark");
		String section = request.getParameter("addsection");
		String sn = request.getParameter("addsn");
		String policeno = request.getParameter("addpoliceno");
		String cardno = request.getParameter("addcardno");
		String partment = request.getParameter("addpartment");
		String telephone = request.getParameter("addtelephone");
		String phone = request.getParameter("addphone");
		String email = request.getParameter("addemail");

		System.out.println("username:" + username);

		String username_l = "admin";//(String) session.getAttribute("userName");

		if (StringUtils.isEmpty(previlige)) {
			previlige = "科员";
		}
		String res = "succ";
		PrintWriter pw = null;
		if (username_l != null && username_l.equals("admin")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date today = new Date();
			String today_ = sdf.format(today);
			psw = EncryptUtil.string2MD5("xl_" + psw);

			User user = new User();
			User user2 = new User();
			User user3 = new User();
			user.setUsername(username);
			user2.setPoliceNO(policeno);
			user3.setCardNO(cardno);
			List<User> list = sqlDao.getListfromMysql(user);
			List<User> list2 = sqlDao.getListfromMysql(user2);
			List<User> list3 = sqlDao.getListfromMysql(user3);
			if (list.size() > 0) {
				System.out.println("user has exists!");
				res = "exist";
			}else if(list2.size() > 0){
				res = "exist2";
			}
			else if(list3.size() == 1){
				res = "exist3";
			}
			else {
				Department department =new Department();
				department.setDepartmentName(partment);
				List<Department> departmentlist = sqlDao.getListfromMysql(department);
				if(departmentlist.size()>0){
					int addPartterDepats= departmentlist.get(0).getId();
					user.setPartment(addPartterDepats+"");
				}
				
				if("-1".equals(section)){	                
					user.setSection("-1");
				}else{
					Section section2 = new Section();
					section2.setSectionName(section);
					List<Section> sectionlist = sqlDao.getListfromMysql(section2);
					if(sectionlist.size()>0){
						int addPartterSections= sectionlist.get(0).getId();
						user.setSection(addPartterSections+"");
					}
				}
				
				/*
				RoleTable RoleTable2 = new RoleTable();
				RoleTable2.setPartterName(previlige);
				List<RoleTable> roleMysqls = sqlDao.getListfromMysql(RoleTable2);
				if(roleMysqls.size()>0){
					int RoleTable3 = roleMysqls.get(0).getId();
					user.setPrivilege(RoleTable3+"");
				}*/
				
				user.setUserrole(previlige);
				user.setCreatedTime(today_);
				user.setPassword(psw);
				user.setRemark(remark);
				user.setSn(sn);
				user.setPoliceNO(policeno);
				user.setCardNO(cardno);
				user.setTelephone(telephone);
				user.setPhone(phone);
				user.setEmail(email);
				sqlDao.setBeanToMysql(user);
			}
		} else {
			res = "nopower";
		}
		try {
			pw = response.getWriter();
			pw.write("{\"res\":\"" + res + "\"}");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	@RequestMapping(value = "/admin/searchUser.php")
	public void searchUser(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		//String username = (String) session.getAttribute("userName");
		//username = "admin"; //因为目前无登录，所以seesion中无登录用户名，现暂时定为admin
		//String uname = "admin";//request.getParameter("uname");
		String page = request.getParameter("page");
		String name = request.getParameter("uname");
		String userprevilige = request.getParameter("userprevilige");
		String dsIdStr = request.getParameter("dsId");
		
		int pageIndex = 1;
		if (!StringUtils.isEmpty(page)) {
			try {
				pageIndex = Integer.parseInt(page);
			} catch (NumberFormatException e) {
			}
		}
		if (StringUtils.isEmpty(dsIdStr)) {
			dsIdStr = "000";
		}

		int dsId = 0;
		int pageSize = 15;
		User user = new User();
		List<User> ul = new ArrayList<User>();
		List<User> listfromMysql = new ArrayList<User>();
		int totalsize = 0;
		
		int startRow = (pageIndex - 1) * pageSize;
		if (!StringUtils.isEmpty(userprevilige) && !userprevilige.equals("全部")) {
			user.setUserrole(userprevilige);
		}
		if(name!=null && !name.equals("")){
			user.setUsername(name);
		}
/*		if (!StringUtils.isEmpty(name) && !userprevilige.equals("")) {
		
		}*/
		String appendSql = "";
		/*if (!StringUtils.isEmpty(uname)) {
			appendSql = "username like '%"+uname+"%'";
		}*/
		
		//根据以前的权限设定是 只有admin用户有用户管理权限
		//if(username!=null && username.equals("admin") && !StringUtils.isEmpty(dsIdStr)){
			if(dsIdStr.equals("000")){
				//ul = sqlDao.getOrderListfromMysql(user, "id");  //以前不能模糊查询   zwz改成下面的模糊查询
				ul = sqlDao.getOrderListfromMysqlLike(user, "id");
				listfromMysql = new ArrayList<User>();
				for (User users2 : ul) {
					String departmentws = users2.getPartment();
					Department department2 = new Department();
					
					department2.setId(Integer.parseInt(departmentws));
					List<Department> listfromMysql2 = sqlDao.getListfromMysql(department2);
                     if(listfromMysql2.size()>0){
                    	Department department3 = listfromMysql2.get(0);
     					String departmentName = department3.getDepartmentName();
     					users2.setPartment(departmentName);
					}
					
					String section2 = users2.getSection();
					  if("-1".equals(section2)){
						  users2.setSection("无");
					  }else{
							Section sections2 = new Section();
							sections2.setId(Integer.parseInt(section2));
							List<Section> listfromMysqls = sqlDao.getListfromMysql(sections2);
							if(listfromMysqls.size()>0){
								Section sections3 = listfromMysqls.get(0);
								String sectionsName = sections3.getSectionName();
								users2.setSection(sectionsName);
							}
					  }
					
					String ptters = users2.getUserrole();
					System.out.println("ptters=="+ptters);
					Role role2 = new Role();
					if(ptters!=null && !"".equals(ptters)){
						role2.setId(Integer.parseInt(ptters));
						List<Role> roles2 = sqlDao.getListfromMysql(role2);
						if (roles2.size() > 0) {
							Role roles3 = roles2.get(0);
							//users2.setPrivilege(roles3.getId()+"/"+roles3.getRoleName());   
							users2.setPrivilege(roles3.getRoleName());//zwz
						}
					}else{
						//users2.setPrivilege("无");
						users2.setPrivilege("无");   //zwz
					}
					
					listfromMysql.add(users2);
					
					
				}
				totalsize = sqlDao.getcountfromMysql(user, appendSql);
			}
			else if(dsIdStr.startsWith("099")){
				dsIdStr = dsIdStr.substring(3);
				dsId = Integer.parseInt(dsIdStr);
				Department department = new Department();
				department.setId(dsId);
				List<Department> departments = sqlDao.getListfromMysql(department, 0, 1);
				if(departments.size()>0){
					department = departments.get(0);
					user.setPartment(department.getId()+"");
					ul = sqlDao.getOrderListfromMysql(user, appendSql, startRow, pageSize, "id");
					listfromMysql = new ArrayList<User>();
					for (User users2 : ul) {
						String departmentws = users2.getPartment();
						Department department2 = new Department();
						department2.setId(Integer.parseInt(departmentws));
						List<Department> listfromMysql2 = sqlDao.getListfromMysql(department2);
						if(listfromMysql2.size()>0){
							Department department3 = listfromMysql2.get(0);
							String departmentName = department3.getDepartmentName();
							users2.setPartment(departmentName);
							
						}
						
						String section2 = users2.getSection();
						Section sections2 = new Section();
						sections2.setId(Integer.parseInt(section2));
						List<Section> listfromMysqls = sqlDao.getListfromMysql(sections2);
						if(listfromMysqls.size()>0){
							Section sections3 = listfromMysqls.get(0);
							String sectionsName = sections3.getSectionName();
							users2.setSection(sectionsName);
						}
					
						
						/*String role2 = users2.getPrivilege();
						RoleTable RoleTable2 = new RoleTable();
						RoleTable2.setId(Integer.parseInt(role2));
						List<RoleTable> roleMysqls = sqlDao.getListfromMysql(RoleTable2);
						if(roleMysqls.size()>0){
							RoleTable RoleTable3 = roleMysqls.get(0);
							String RoleTableName = RoleTable3.getPartterName();
							users2.setPrivilege(RoleTableName);
						}*/
						
						String role2 = users2.getUserrole();
						
						Role RoleTable2 = new Role();
						RoleTable2.setId(Integer.parseInt(role2));
						List<Role> roleMysqls = sqlDao.getListfromMysql(RoleTable2);
						if(roleMysqls.size()>0){
							Role RoleTable3 = roleMysqls.get(0);
							String RoleName = RoleTable3.getRoleName();
							
							users2.setPrivilege(RoleName);
						}
						
						listfromMysql.add(users2);

					}
					totalsize = sqlDao.getcountfromMysql(user, appendSql);
				}
			}
			else{
				String[] split = dsIdStr.split(",");
				for (String dsId2 : split) {
					dsId = Integer.parseInt(dsId2);
					Section section = new Section();
					section.setId(dsId);
					List<Section> sections = sqlDao.getListfromMysql(section, 0, 1);
					if(sections.size()>0){
						section = sections.get(0);
						user.setPartment(section.getDepartment());
						user.setSection(section.getId()+"");
						ul = sqlDao.getOrderListfromMysql(user, "id");
						//listfromMysql = new ArrayList<User>();
						for (User users2 : ul) {
							String departmentws = users2.getPartment();
							Department department2 = new Department();
							department2.setId(Integer.parseInt(departmentws));
							List<Department> listfromMysql2 = sqlDao.getListfromMysql(department2);
							if(listfromMysql2.size()>0){
								Department department3 = listfromMysql2.get(0);
								String departmentName = department3.getDepartmentName();
								users2.setPartment(departmentName);
							}
							
							
							String section2 = users2.getSection();
							Section sections2 = new Section();
							sections2.setId(Integer.parseInt(section2));
							List<Section> listfromMysqls = sqlDao.getListfromMysql(sections2);
							if(listfromMysqls.size()>0){
								Section sections3 = listfromMysqls.get(0);
								String sectionsName = sections3.getSectionName();
								users2.setSection(sectionsName);
							}
							
							
							String ptters = users2.getUserrole();
							Role role2 = new Role();
							if(ptters!=null && !"".equals(ptters)){
								role2.setId(Integer.parseInt(ptters));
								List<Role> roles2 = sqlDao.getListfromMysql(role2);
								if (roles2.size() > 0) {
									Role roles3 = roles2.get(0);
									//users2.setPrivilege(roles3.getId()+"/"+roles3.getRoleName());   
									users2.setPrivilege(roles3.getRoleName());   //zwz
								}
							}else{
								//users2.setPrivilege("无");
								users2.setPrivilege("无");  //zwz
							}
							
							listfromMysql.add(users2);
						}
						totalsize += sqlDao.getcountfromMysql(user, appendSql);
					}
				}
			}
		//}
		
		List<User> listfromMysql2 = new ArrayList<User>();
		int n=0;
		for (User user2 : listfromMysql) {
			int a=(pageIndex-1)*10;
			int b=pageIndex*10;
			if(n>=a && n<b){
				listfromMysql2.add(user2);
			}
			n++;
		}
		

		int l = totalsize / pageSize;
		if (totalsize % pageSize > 0)
			l++;
		JSONArray jsonArray = JSONArray.fromObject(listfromMysql2);
		String json_str = jsonArray.toString();
		
		System.out.println("json_str=" + json_str);
		int yu=totalsize%10;
		int totalPages =totalsize / 10;
		if(yu>0){
			totalPages=  totalPages+1;
		}
		String result_data = "{\"totalNum\":\"" + totalsize + "\",\"totalPages\":\"" + totalPages + "\",\"nowPage\":\""
				+ pageIndex + "\",\"resData\":" + json_str + "}";
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(result_data);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}
	//新增自动选择部门科室
	@RequestMapping(value = "/admin/searction.php")
	public void searction(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		
		String sectid = request.getParameter("sectid");
		
		if(sectid!=null && !sectid.equals("") && sectid!=""){
			if(sectid.startsWith("099")){
				String substring = sectid.substring(3);
				Department department = new Department();
				Section sections1 = new Section();
				department.setId(Integer.parseInt(substring));
				List<Department> list = sqlDao.getListfromMysql(department);
				List<String> list3 = new ArrayList<String>();
					if(list.size()>0){
						String departmentName = list.get(0).getDepartmentName();
						 int id = list.get(0).getId();
						sections1.setDepartment(id+"");
						List<Section> listfromMysql = sqlDao.getListfromMysql(sections1);
						for (Section section : listfromMysql) {
							String department2 = section.getSectionName();
							list3.add(department2);
						}
						map.put("list3", list3);
						map.put("departmentName", departmentName);
					}
			}else if(sectid.startsWith("000")){
			}else{
					  Section sections = new Section();
					  Department department2 = new Department();
					  sections.setId(Integer.parseInt(sectid));
					  List<Section> list2 = sqlDao.getListfromMysql(sections);
					  if(list2.size()>0){
						  List<String> list4 = new ArrayList<String>();
					   	  Section sections2 = new Section();
						  String departmentid = list2.get(0).getDepartment();
						  String sectionName = list2.get(0).getSectionName();
						  sections2.setDepartment(departmentid);
						  List<Section> listfromMysql = sqlDao.getListfromMysql(sections2);
						  for (Section section : listfromMysql)
						  {
								String department3 = section.getSectionName();
								list4.add(department3);
						  }
						  department2.setId(Integer.parseInt(departmentid));
						  List<Department> list3 = sqlDao.getListfromMysql(department2);
						  String departmentName2 = list3.get(0).getDepartmentName();
						  map.put("list4", list4);
						  map.put("departmentName2", departmentName2);
						  map.put("sectionName", sectionName);
						}
					}
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.map2json(map));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	
	
	@RequestMapping(value = "/admin/delUser.php")
	public void delUser(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		
		String delId = request.getParameter("delId");

		if (!StringUtils.isEmpty(delId)) {
			try {
				int delIdInt = Integer.parseInt(delId);
				User user = new User();
				user.setId(delIdInt);
				sqlDao.deletefromMysql(user);
			} catch (NumberFormatException e) {
			}
		}

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write("{\"res\":\"succ\"}");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}
	
	@RequestMapping(value = "/admin/resetPasswd.php")
	public void resetPasswd(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		
		String resetId = request.getParameter("resetId");

		if (!StringUtils.isEmpty(resetId)) {
			try {
				int resetIdInt = Integer.parseInt(resetId);
				User user = new User();
				user.setId(resetIdInt);
				user.setPassword(EncryptUtil.string2MD5("xl_123456"));
				sqlDao.updateToMysql(user);
			} catch (NumberFormatException e) {
			}
		}

		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write("{\"res\":\"succ\"}");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}
	
	@RequestMapping(value = "/admin/editUser.php")
	public void editUser(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String id = request.getParameter("id");
		String username = request.getParameter("username");
		String previlige = request.getParameter("userprevilige");
		String remark = request.getParameter("userremark");
		String section = request.getParameter("section");
		String sn = request.getParameter("sn");
		String policeno = request.getParameter("policeno");
		String cardno = request.getParameter("cardno");
		String partment = request.getParameter("partment");
		String telephone = request.getParameter("telephone");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");

		String username_l = "admin";//(String) session.getAttribute("userName");

		String res = "succ";
		PrintWriter pw = null;
		if (username_l != "" && "admin".equals(username_l)) {
			User user = new User();
			//user.setUsername(username);
			/*int idInt =0;
			try {
				idInt = Integer.parseInt(id);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			user.setId(Integer.parseInt(id));
			List<User> list = sqlDao.getListfromMysql(user);
			if (list.size() > 0) {
				Department department =new Department();
				department.setDepartmentName(partment);
				List<Department> departmentlist = sqlDao.getListfromMysql(department);
				if(departmentlist.size()>0){
					int addPartterDepats= departmentlist.get(0).getId();
					user.setPartment(addPartterDepats+"");
				}
				if("-1".equals(section)){		
					user.setSection("-1");		
				  }else{		
				     Section section2 = new Section();		
					section2.setSectionName(section);		
					List<Section> sectionlist = sqlDao.getListfromMysql(section2);		
					if(sectionlist.size()>0){		
						int addPartterSections= sectionlist.get(0).getId();		
					   user.setSection(addPartterSections+"");		
					   }		
					 }
				
				
				/*RoleTable RoleTable2 = new RoleTable();
				RoleTable2.setPartterName(previlige);
				List<RoleTable> roleMysqls = sqlDao.getListfromMysql(RoleTable2);
				if(roleMysqls.size()>0){
					int RoleTable3 = roleMysqls.get(0).getId();
					user.setPrivilege(RoleTable3+"");
				}*/
				
				user.setUserrole(previlige);
				user.setUsername(username);
				user.setRemark(remark);
		
				user.setSn(sn);
				user.setPoliceNO(policeno);
				user.setCardNO(cardno);
				
				user.setTelephone(telephone);
				user.setPhone(phone);
				user.setEmail(email);
				sqlDao.updateToMysql(user);
			}else{
				user.setId(-1);
				list = sqlDao.getListfromMysql(user);
				if (list.size() > 0) {
					System.out.println("user has exists!");
					res = "exist";
				}
			}
		} else {
			res = "nopower";
		}
		try {
			pw = response.getWriter();
			pw.write("{\"res\":\"" + res + "\"}");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	
	//角色查询
		@RequestMapping(value = "/admin/getAllPartter.php")
		public void getAllrole(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
				HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {
			
			//username = "admin"; //因为目前无登录，所以seesion中无登录用户名，现暂时定为admin
			String uname = request.getParameter("uname");
			String pageno = request.getParameter("pageno");
			int pageIndex = 1;
			int pageSize = 10;

			if (!StringUtils.isEmpty(pageno)) {
				pageIndex = Integer.parseInt(pageno);
			}
			RoleTable roleTable = new RoleTable();
			List<RoleTable> listfromMysql = new ArrayList<RoleTable>();
			if (!StringUtils.isEmpty(uname)) {
				roleTable.setPartterName(uname);
			}
			int count = sqlDao.getcountfromMysqlLike(roleTable);//getOrderListfromMysqlLike(section,0, 10, "id");
			List<RoleTable> listfromMysql3 = sqlDao.getOrderListfromMysqlLike(roleTable,(pageIndex - 1) * pageSize, pageSize,"id");
			listfromMysql = new ArrayList<RoleTable>();
			for (RoleTable users2 : listfromMysql3) {
				String departmentws = users2.getDepartid();
				Department department2 = new Department();
				department2.setId(Integer.parseInt(departmentws));
				List<Department> listfromMysql2 = sqlDao.getListfromMysql(department2);
				if(listfromMysql2.size()>0){
					Department department3 = listfromMysql2.get(0);
					String departmentName = department3.getDepartmentName();
					users2.setDepartid(departmentName);
				}
				
				
				String section2 = users2.getSectionid();
				Section sections2 = new Section();
				sections2.setId(Integer.parseInt(section2));
				List<Section> listfromMysqls = sqlDao.getListfromMysql(sections2);
				if(listfromMysqls.size()>0){
					Section sections3 = listfromMysqls.get(0);
					String sectionsName = sections3.getSectionName();
					users2.setSectionid(sectionsName);
				}
				
				listfromMysql.add(users2);
			}
			map.put("listfromMysql", listfromMysql);
			map.put("count", count);
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write(JsonUtil.map2json(map));
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}

		}
	//删除角色
		@RequestMapping(value = "/admin/delPartter.php")
		public void delrole(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
				HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {
			
				String delId = request.getParameter("ID");

				
					int delIdInt = Integer.parseInt(delId);
					RoleTable roleTable =new RoleTable();
					roleTable.setId(delIdInt);
					sqlDao.deletefromMysql(roleTable);
                   User user = new User();
                   user.setPrivilege(delId);
			    List<User> list = sqlDao.getListfromMysql(user);
				   if (list.size() > 0) {
					user.setPrivilege("无");
					sqlDao.updateToMysql(user);
				}
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write("{\"res\":\"succ\"}");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}

		}
	//新增角色
		@RequestMapping(value = "/admin/addPartter.php")
		public void addRole(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
			/*String addrolename = request.getParameter("addrolename");
			String polems = request.getParameter("polems");
			String addsection = request.getParameter("addsection");
			String addpartment = request.getParameter("addpartment");
			String adduser = "admin";
			*/
			String addPartterName = request.getParameter("addPartterName");
			String addPartterDes = request.getParameter("addPartterDes");
			String addPartterDepat = request.getParameter("addPartterDepat");
			String addPartterSection = request.getParameter("addPartterSection");
		/*	String addPartterShu = request.getParameter("addPartterShu");
			String addPartterShou = request.getParameter("addPartterShou");*/
			String adduser = "admin";

			String res = "succ";
			PrintWriter pw = null;
			if (adduser != "" && "admin".equals(adduser)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date today = new Date();
				String today_ = sdf.format(today);
				RoleTable roleTable =new RoleTable();
				roleTable.setPartterName(addPartterName);
				List<RoleTable> list = sqlDao.getListfromMysql(roleTable);
				if (list.size() > 0) {
					System.out.println("rolename has exists!");
					res = "exist";
				} else {
					Department department =new Department();
					department.setDepartmentName(addPartterDepat);
					List<Department> departmentlist = sqlDao.getListfromMysql(department);
					if(departmentlist.size()>0){
						int addPartterDepats= departmentlist.get(0).getId();
						roleTable.setDepartid(addPartterDepats+"");
					}
					
					
					Section section = new Section();
					section.setSectionName(addPartterSection);
					List<Section> sectionlist = sqlDao.getListfromMysql(section);
					if(sectionlist.size()>0){
						int addPartterSections= sectionlist.get(0).getId();
						roleTable.setSectionid(addPartterSections+"");
					}
					
					
					roleTable.setCreateName(adduser);
					roleTable.setCreateTime(today_);
				
					roleTable.setPartterDes(addPartterDes);
				
					//roleTable.setPartterShou(addPartterShou);
					
					sqlDao.setBeanToMysql(roleTable);
				}
			} else {
				res = "nopower";
			}
			try {
				pw = response.getWriter();
				pw.write("{\"res\":\"" + res + "\"}");
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		}
	
		//角色修改
		@RequestMapping(value = "/admin/editPartter.php")
		public void editRole(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
				HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {
			String editId = request.getParameter("editId");
			String partterEdit_name = request.getParameter("partterEdit_name");
			String partterEdit_des = request.getParameter("partterEdit_des");
			String partterEdit_depat = request.getParameter("partterEdit_depat");
			String partterEdit_section = request.getParameter("partterEdit_section");
			String partterEdit_shou = request.getParameter("partterEdit_shou");
			String adduser = "admin";


			String res = "succ";
			PrintWriter pw = null;
			if (adduser != null && "admin".equals(adduser)) {
				RoleTable roleTable =new RoleTable();
				roleTable.setCreateName(adduser);
				int idInt =0;
				try {
					idInt = Integer.parseInt(editId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				roleTable.setId(idInt);
				List<RoleTable> list = sqlDao.getListfromMysql(roleTable);
				if (list.size() > 0) {
					Department department =new Department();
					department.setDepartmentName(partterEdit_depat);
					List<Department> departmentlist = sqlDao.getListfromMysql(department);
					if(departmentlist.size()>0){
						int addPartterDepats= departmentlist.get(0).getId();
						roleTable.setDepartid(addPartterDepats+"");
					}
					
					
					Section section = new Section();
					section.setSectionName(partterEdit_section);
					List<Section> sectionlist = sqlDao.getListfromMysql(section);
					if(sectionlist.size()>0){
						int addPartterSections= sectionlist.get(0).getId();
						roleTable.setSectionid(addPartterSections+"");
					}
					
					roleTable.setPartterName(partterEdit_name);
					roleTable.setPartterDes(partterEdit_des);
					roleTable.setPartterShou(partterEdit_shou);
					sqlDao.updateToMysql(roleTable);
				}else{
					roleTable.setId(-1);
					list = sqlDao.getListfromMysql(roleTable);
					if (list.size() > 0) {
						System.out.println("adduser has exists!");
						res = "exist";
					}
				}
			} else {
				res = "nopower";
			}
			try {
				pw = response.getWriter();
				pw.write("{\"res\":\"" + res + "\"}");
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		}
		
	
	
	//查看角色权限
	@RequestMapping(value = "/admin/searchRole.php")
	public void searchRole(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
			HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		String username = (String) session.getAttribute("userName");
		username = "admin"; //因为目前无登录，所以seesion中无登录用户名，现暂时定为admin
		String uname = request.getParameter("uname");
		String page = request.getParameter("page");
		String userprevilige = request.getParameter("userprevilige");
		String dsIdStr = request.getParameter("dsId");

		int pageIndex = 1;
		if (!StringUtils.isEmpty(page)) {
			try {
				pageIndex = Integer.parseInt(page);
			} catch (NumberFormatException e) {
			}
		}
		if (StringUtils.isEmpty(dsIdStr)) {
			dsIdStr = "000";
		}

		int dsId = 0;
		int pageSize = 15;
		User user = new User();
		List<User> ul = new ArrayList<User>();
		int totalsize = 0;
		
		int startRow = (pageIndex - 1) * pageSize;
		if (!StringUtils.isEmpty(userprevilige) && !userprevilige.equals("全部")) {
			user.setPrivilege(userprevilige);
		}
		String appendSql = "";
		if (!StringUtils.isEmpty(uname)) {
			appendSql = "username like '%"+uname+"%'";
		}
		
		//根据以前的权限设定是 只有admin用户有用户管理权限
		if(username!=null && username.equals("admin") && !StringUtils.isEmpty(dsIdStr)){
			if(dsIdStr.equals("000")){
				ul = sqlDao.getOrderListfromMysql(user, appendSql, startRow, pageSize, "id");
				totalsize = sqlDao.getcountfromMysql(user, appendSql);
			}else if(dsIdStr.startsWith("099")){
				dsIdStr = dsIdStr.substring(3);
				dsId = Integer.parseInt(dsIdStr);
				Department department = new Department();
				department.setId(dsId);
				List<Department> departments = sqlDao.getListfromMysql(department, 0, 1);
				if(departments.size()>0){
					department = departments.get(0);
					user.setPartment(department.getDepartmentName());
					ul = sqlDao.getOrderListfromMysql(user, appendSql, startRow, pageSize, "id");
					totalsize = sqlDao.getcountfromMysql(user, appendSql);
				}
			}else{
				dsId = Integer.parseInt(dsIdStr);
				Section section = new Section();
				section.setId(dsId);
				List<Section> sections = sqlDao.getListfromMysql(section, 0, 1);
				if(sections.size()>0){
					section = sections.get(0);
					user.setPartment(section.getDepartment());
					user.setSection(section.getSectionName());
					ul = sqlDao.getOrderListfromMysql(user, appendSql, startRow, pageSize, "id");
					totalsize = sqlDao.getcountfromMysql(user, appendSql);
				}
			}
		}
		
		int l = totalsize / pageSize;
		if (totalsize % pageSize > 0)
			l++;
		JSONArray jsonArray = JSONArray.fromObject(ul);
		String json_str = jsonArray.toString();

		String result_data = "{\"totalNum\":\"" + totalsize + "\",\"totalPages\":\"" + l + "\",\"nowPage\":\""
				+ pageIndex + "\",\"resData\":" + json_str + "}";
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(result_data);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}
		
	
	//00000000000000000000000科室管理00000000000000000000000000000000000	
		
		//科室查询
		@RequestMapping(value = "/admin/searchsection.php")
		public void searchsection(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
				HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {
			response.setContentType("text/html;charset=utf-8");
			String username = (String) session.getAttribute("userName");
			username = "admin"; //因为目前无登录，所以seesion中无登录用户名，现暂时定为admin
			String uname = request.getParameter("uname");
			String pageno = request.getParameter("pageno");
			int pageIndex = 1;
			int pageSize = 10;

			if (!StringUtils.isEmpty(pageno)) {
				pageIndex = Integer.parseInt(pageno);
			}
			Section section = new Section();
			if (!StringUtils.isEmpty(uname)) {
				section.setSectionName(uname);
			}
			List<Section> listfromMysql = new ArrayList<Section>();
			List<Section> listfromMysql3 = sqlDao.getOrderListfromMysqlLike(section,(pageIndex - 1) * pageSize, pageSize,"id");
			int count = sqlDao.getcountfromMysqlLike(section);//getOrderListfromMysqlLike(section,0, 10, "id");
			for (Section section2 : listfromMysql3) {
				String department = section2.getDepartment();
				Department department2 = new Department();
				department2.setId(Integer.parseInt(department));
				List<Department> listfromMysql2 = sqlDao.getListfromMysql(department2);
				if(listfromMysql2.size()>0){
					Department department3 = listfromMysql2.get(0);
					String departmentName = department3.getDepartmentName();
					section2.setDepartment(departmentName);
				}
				
				listfromMysql.add(section2);
			}
			/**/
			map.put("listfromMysql", listfromMysql);
			map.put("count", count);
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write(JsonUtil.map2json(map));
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					writer.close();
				}
			}

		}
		
		//添加科室
		@RequestMapping(value = "/admin/addsection.php")
		public void addsection(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
			String username = (String)session.getAttribute("userName");
			String addSectionsnum = request.getParameter("addSectionsnum");
			String addSectionsname = request.getParameter("addSectionsname");
			String addpartment = request.getParameter("addpartment");
			String adduser = "admin";

			String res = "succ";
			PrintWriter pw = null;
			if (adduser != null && adduser.equals("admin")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date today = new Date();
				String today_ = sdf.format(today);
				Section section = new Section();
				Department department = new Department();
				department.setDepartmentName(addpartment);
				List<Department> listfromMysql2 = sqlDao.getListfromMysql(department);
				//int departments=0;
				if(listfromMysql2.size()>0){
					Department department3 = listfromMysql2.get(0);
					int departments = department3.getId();
					section.setDepartment(departments+"");
					section.setSectionName(addSectionsname);
					
					
					Section section2= new Section();
					List<Section> list = sqlDao.getListfromMysql(section);
				
					
						if (list.size()>0) {
							System.out.println("rolename has exists!");
							res = "exist";
						} else {
							section2.setDepartment(departments+"");
							section2.setFound(adduser);
							section2.setFoundtime(today_);
							section2.setSectionNum(addSectionsnum);
							section2.setSectionName(addSectionsname);
					
							section2.setRemarks("");
							sqlDao.setBeanToMysql(section2);
						}
				}
				//section.setFound(adduser);
				
				/*	Section section2 = new Section();
				section2.setSectionName(addSectionsname);
				List<Section> listfromMysql3 = sqlDao.getListfromMysql(section2);
				if(listfromMysql3.size()>0){
					Section department3 = listfromMysql3.get(0);
					int departments = department3.getId();
					section2.setId(departments);
				}*/
				
				//section3.getSectionName();
		
			} else {
				res = "nopower";
			}
			try {
				pw = response.getWriter();
				pw.write("{\"res\":\"" + res + "\"}");
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		}
		
		//修改科室
		@RequestMapping(value = "/admin/editSection.php")
		public void editSection(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
			String sectionEdit_num = request.getParameter("sectionEdit_num");
			String sectionEdit_name = request.getParameter("sectionEdit_name");
			String departmentedit = request.getParameter("departmentedit");
			String id = request.getParameter("editId");
			String adduser = "admin";
			String res = "succ";
			PrintWriter pw = null;
			if (adduser != null && adduser.equals("admin")) {
				Section roleTable =new Section();
				//roleTable.setFound(adduser);
				int idInt =0;
				try {
					idInt = Integer.parseInt(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Department department = new Department();
				department.setDepartmentName(departmentedit);
				List<Department> listfromMysql2 = sqlDao.getListfromMysql(department);
				int departments = 0;
				if(listfromMysql2.size()>0){
					Department department3 = listfromMysql2.get(0);
					departments = department3.getId();
					roleTable.setId(idInt);
				}
				List<Section> list = sqlDao.getListfromMysql(roleTable);
				if (list.size() > 0) {
					roleTable.setFound(adduser);
					roleTable.setSectionNum(sectionEdit_num);
					roleTable.setSectionName(sectionEdit_name);
					roleTable.setDepartment(departments+"");
					roleTable.setRemarks("");
					sqlDao.updateToMysql(roleTable);
				}/*else{
					roleTable.setId(-1);
					list = sqlDao.getListfromMysql(roleTable);
					if (list.size() > 0) {
						System.out.println("adduser has exists!");
						res = "exist";
					}
				}*/
			} else {
				res = "nopower";
			}
			try {
				pw = response.getWriter();
				pw.write("{\"res\":\"" + res + "\"}");
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		}
		
		
		//删除科室
		@RequestMapping(value = "/admin/deletsection.php")
		public void deletsection(HttpServletRequest request, Map<String, Object> map, HttpSession session,
				HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
				IllegalAccessException, InvocationTargetException {
				String ID = request.getParameter("ID");
				Section roleTable =new Section();
				roleTable.setId(Integer.parseInt(ID));
				sqlDao.deletefromMysql(roleTable);
				
				User user =new User();
				user.setPartment(roleTable.getId()+"");
				List<User> list = sqlDao.getListfromMysql(user);
				if (list.size() > 0) {
					user.setSection("无");
					sqlDao.updateToMysql(user);
				}
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					pw.write("{\"res\":\"succ\"}");
					pw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (pw != null) {
						pw.close();
					}
				}
		}
		
		//00000000000000000000000部门管理00000000000000000000000000000000000		
				/**
				 * 添加部门
				 * @param request
				 * @param response
				 * @param session
				 */
				@RequestMapping(value = "/admin/adddepartment.php")
				public void adddepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
					String departmentNum = request.getParameter("adddepartmentNum");
					String departmentName = request.getParameter("adddepartmentName");
					String username = (String) session.getAttribute("userName");
					username = "admin"; //因为目前无登录，所以seesion中无登录用户名，现暂时定为admin
					String res = "succ";
					PrintWriter pw = null;
					if (username != null && username.equals("admin")) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date today = new Date();
						String today_ = sdf.format(today);
						//section.setFound(adduser);
						/*List<Section> list = sqlDao.getListfromMysql(section);
						if (list.size() > 0) {
							System.out.println("rolename has exists!");
							res = "exist";
						} else {*/
				/*	List<Department> departmentList = sqlDao.getListfromMysql(department, 0, 1);
					if (departmentList.size() > 0) {
						res = "exist";
					} else {*/
						//department.setTime(dateFormat.format(new Date()));
						Department department = new Department();
						department.setDepartmentNum(departmentNum);
						department.setDepartmentName(departmentName);
						department.setFound(username);
						department.setFoundtime(today_);
						department.setRemarks("");
						sqlDao.setBeanToMysql(department);
					}
					try {
						pw = response.getWriter();
						pw.write("{\"res\":\"" + res + "\"}");
						pw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (pw != null) {
							pw.close();
						}
					}
				}
				
				
				//修改部门
				@RequestMapping(value = "/admin/editdepartmen.php")
				public void editdepartmen(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
					String id = request.getParameter("delId");
					String departmentNum = request.getParameter("departmentNum");
					String departmentName = request.getParameter("departmentEditName");

					String adduser = "admin";
					String res = "succ";
					PrintWriter pw = null;
					if (adduser != null && adduser.equals("admin")) {
						Department Departments =new Department();
						//roleTable.setFound(adduser);
						int idInt =0;
						try {
							idInt = Integer.parseInt(id);
						} catch (Exception e) {
							e.printStackTrace();
						}
						Departments.setId(idInt);
						List<Department> list = sqlDao.getListfromMysql(Departments);
						if (list.size() > 0) {
							Departments.setFound(adduser);
							Departments.setDepartmentNum(departmentNum);
							Departments.setDepartmentName(departmentName);
							Departments.setRemarks("");
							sqlDao.updateToMysql(Departments);
						}/*else{
							roleTable.setId(-1);
							list = sqlDao.getListfromMysql(roleTable);
							if (list.size() > 0) {
								System.out.println("adduser has exists!");
								res = "exist";
							}
						}*/
					} else {
						res = "nopower";
					}
					try {
						pw = response.getWriter();
						pw.write("{\"res\":\"" + res + "\"}");
						pw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (pw != null) {
							pw.close();
						}
					}
				}
				
				/*
				 * 删除部门
				 */
				@RequestMapping(value = "/admin/deletdepartment.php")
				public void deletdepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

					String ID = request.getParameter("delId");
					
					Department department = new Department();
				     	department.setId(Integer.parseInt(ID));
						sqlDao.deletefromMysql(department);
						
						
						Section roleTable =new Section();
						roleTable.setDepartment(department.getId()+"");
						sqlDao.deletefromMysql(roleTable);
						
						User user =new User();
						user.setPartment(department.getId()+"");
						List<User> list = sqlDao.getListfromMysql(user);
						if (list.size() > 0) {
							user.setPartment("无");
							sqlDao.updateToMysql(user);
						}
						PrintWriter pw = null;
						try {
							pw = response.getWriter();
							pw.write("{\"res\":\"succ\"}");
							pw.flush();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (pw != null) {
								pw.close();
							}
						}
				}
			
				//部门查询
				@RequestMapping(value = "/admin/searchdepartment.php")
				public void seardepartment(HttpServletRequest request, Map<String, Object> map, HttpServletResponse response,
						HttpSession session) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
						IllegalAccessException, InvocationTargetException {
					response.setContentType("text/html;charset=utf-8");
					String username = (String) session.getAttribute("userName");
					username = "admin"; //因为目前无登录，所以seesion中无登录用户名，现暂时定为admin
					String uname = request.getParameter("uname");
					String pageno = request.getParameter("pageno");
					int pageIndex = 1;
					int pageSize = 10;

					if (!StringUtils.isEmpty(pageno)) {
						pageIndex = Integer.parseInt(pageno);
					}
					Department Departments = new Department();
					if (!StringUtils.isEmpty(uname)) {
						Departments.setDepartmentName(uname);
					}
					List<Department> listfromMysql3 = sqlDao.getOrderListfromMysqlLike(Departments,(pageIndex - 1) * pageSize, pageSize,"id");
					int count = sqlDao.getcountfromMysqlLike(Departments);//getOrderListfromMysqlLike(section,0, 10, "id");
					/**/
					map.put("listfromMysql", listfromMysql3);
					map.put("count", count);
					PrintWriter writer = null;
					try {
						writer = response.getWriter();
						writer.write(JsonUtil.map2json(map));
						writer.flush();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (writer != null) {
							writer.close();
						}
					}

				}
						
				

}