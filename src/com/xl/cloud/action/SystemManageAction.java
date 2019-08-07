package com.xl.cloud.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xl.cloud.bean.ActionMenu;
import com.xl.cloud.bean.Department;
import com.xl.cloud.bean.Evidence;
import com.xl.cloud.bean.Organization;
import com.xl.cloud.bean.Role;
import com.xl.cloud.bean.Section;
import com.xl.cloud.bean.User;
import com.xl.cloud.common.Global;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.JsonUtil;

import jodd.mail.EmailAttachment;
import jodd.mail.EmailUtil;
import jodd.mail.ReceivedEmail;


/**
 * 系统管理
 */
@Controller
public class SystemManageAction {
	private SqlDao sqlDao = new SqlDao();
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	final Logger logger = Logger.getLogger(BuildCollection.class);
	
	@RequestMapping(value = "/system/showAction.php")
	public String showAction(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return "action";
	}
	@RequestMapping(value = "/system/showOrganization.php")
	public String showOrganization(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return "organization";
	}	
	@RequestMapping(value = "/system/showRole.php")
	public String showRole(HttpServletRequest request, Map<String, Object> map, HttpSession session,
			HttpServletResponse response) throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return "roleManage";
	}
	/**
	 * 获取左侧菜单
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/getAction.php")
	public void getAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		ActionMenu actionMenu = new ActionMenu();
		List<ActionMenu> actionMenuList = sqlDao.getOrderListfromMysqlLike(actionMenu, "actionOrder");
		User user = (User) session.getAttribute("user");
		String userrole = user.getUserrole();
		Role role = new Role();
		role.setId(Integer.parseInt(userrole));
		List<Role> roles = sqlDao.getListfromMysql(role);
		String roleMenu="";
		if(roles.size()>0){
			Role role2 = roles.get(0);
			roleMenu = role2.getRoleMenu();
		}
		String[] split = roleMenu.split(",");
		List<ActionMenu> actionMenuList2 = new ArrayList<ActionMenu>();
		for (ActionMenu actionMenu2 : actionMenuList) {
			for (String menu : split) {
				if(menu.equals(actionMenu2.getId()+"")){
					actionMenuList2.add(actionMenu2);
				}
			}
		}
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(actionMenuList2));
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
	 * 添加菜单
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/addAction.php")
	public void addAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String actionName = request.getParameter("actionName");// 名称
		String icon = request.getParameter("icon");// 图片地址
		String route = request.getParameter("route");// 路由地址
		String isHide = request.getParameter("isHide");// 是否隐藏
		String parent = request.getParameter("parent");// 上级菜单
		int actionOrder = 0;
		List<ActionMenu> actionMenuList = sqlDao.getOrderListfromMysqlLike(new ActionMenu(), "actionOrder");
		if(actionMenuList.size()>0){
			actionOrder=actionMenuList.get(0).getActionOrder()+1;
		}
		ActionMenu actionMenu = new ActionMenu();
		actionMenu.setActionName(actionName);
		actionMenu.setIcon(icon);
		actionMenu.setRoute(route);
		actionMenu.setIsHide(isHide);
		actionMenu.setParent(parent);
		actionMenu.setActionOrder(actionOrder);
		sqlDao.setBeanToMysql(actionMenu);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(actionOrder+"");
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
	 * 修改菜单
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/editAction.php")
	public void editAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String id = request.getParameter("id");// id
		String actionName = request.getParameter("actionName");// 名称
		String icon = request.getParameter("icon");// 图片地址
		String route = request.getParameter("route");// 路由地址
		String isHide = request.getParameter("isHide");// 是否隐藏
		ActionMenu actionMenu = new ActionMenu();
		ActionMenu actionMenu1 = new ActionMenu();
		actionMenu1.setId(Integer.parseInt(id));
		List<ActionMenu> actionMenuList = sqlDao.getListfromMysql(actionMenu1);
		if(actionMenuList.size()>0){
			actionMenu=actionMenuList.get(0);
		}
		actionMenu.setActionName(actionName);
		actionMenu.setIcon(icon);
		actionMenu.setRoute(route);
		actionMenu.setIsHide(isHide);
		sqlDao.updateToMysql(actionMenu);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(actionMenu.toString());
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
	 * 获取菜单树
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/getManage.php")
	public void getManage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		
		StringBuilder sbtmp2 =getTree();
    	String resResult = sbtmp2.toString();
    	System.out.println("resResult="+resResult);
    	
		
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
	 //获取树
    public StringBuilder getTree(){
    	StringBuilder sbtmp = new StringBuilder();
    	ActionMenu actionMenu = new ActionMenu();
    	List<ActionMenu> listfromMysql = sqlDao.getListfromMysql(actionMenu);
    	if(listfromMysql.size()>0){
    		sbtmp.append("[");
	    	for (ActionMenu actionMenu2 : listfromMysql) {
	    		String id = actionMenu2.getId()+"";
				if("0".equals(actionMenu2.getParent())){
					sbtmp.append("{\"id\":\""+actionMenu2.getId()+"\",");
					sbtmp.append("\"text\":\""+actionMenu2.getActionName()+"\"");
					StringBuilder childrenSbtmp = getChildren(id);
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
    public StringBuilder getChildren(String parentId){
    	StringBuilder sbtmp = new StringBuilder();
    	ActionMenu phoneTree = new ActionMenu();
    	phoneTree.setParent(parentId);
    	List<ActionMenu> listfromMysql = sqlDao.getListfromMysql(phoneTree);
    	if(listfromMysql.size()>0){
	    	for (ActionMenu phoneTree2 : listfromMysql) {
	    		String name = phoneTree2.getActionName();
	    		sbtmp.append("{\"id\":\""+phoneTree2.getId()+"\",");
				sbtmp.append("\"text\":\""+name+"\"");
				sbtmp.append("},");
			}
	    	sbtmp.deleteCharAt(sbtmp.length() - 1);//去掉最后一个逗号	
    	}
    	return sbtmp;
    }
    /**
	 * 添加机构
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/addOrganize.php")
	public void addOrganize(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		
		String organizeName = request.getParameter("organizeName");// 图片地址
		String number = request.getParameter("number");// 路由地址
		String isHide = request.getParameter("isHide");// 是否隐藏
		String parent = request.getParameter("parent");// 上级菜单
		String remark = request.getParameter("remark");// 名称
		Organization t = new Organization();
		t.setOrganizeName(organizeName);
		t.setNumber(number);
		t.setRemark(remark);
		t.setIsHide(isHide);
		t.setParent(parent);
		sqlDao.setBeanToMysql(t);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(t.toString());
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
	 * 获得所有机构
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/getOrganize.php")
	public void getOrganize(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String name = request.getParameter("name");//机构名称
		Organization t = new Organization();
		t.setOrganizeName(name);
		List<Organization> listfromMysql = sqlDao.getListfromMysqlLike(t);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(listfromMysql));
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
	 * 获取机构树
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/getOrganizetree.php")
	public void getOrganizetree(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		StringBuilder sbtmp2 =getTree2();
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
	 //获取机构树
    public StringBuilder  getTree2(){
    	StringBuilder sbtmp = new StringBuilder();
    	Organization actionMenu = new Organization();
    	List<Organization> listfromMysql = sqlDao.getListfromMysql(actionMenu);
    	if(listfromMysql.size()>0){
    		sbtmp.append("[");
	    	for (Organization actionMenu2 : listfromMysql) {
	    		String id = actionMenu2.getId()+"";
				if("0".equals(actionMenu2.getParent())){
					sbtmp.append("{\"id\":\""+actionMenu2.getId()+"\",");
					sbtmp.append("\"text\":\""+actionMenu2.getOrganizeName()+"\"");
					StringBuilder childrenSbtmp = getChildren2(id);
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
    //获取机构孩子
    public StringBuilder getChildren2(String parentId){
    	StringBuilder sbtmp = new StringBuilder();
    	Organization phoneTree = new Organization();
    	phoneTree.setParent(parentId);
    	List<Organization> listfromMysql = sqlDao.getListfromMysql(phoneTree);
    	if(listfromMysql.size()>0){
	    	for (Organization phoneTree2 : listfromMysql) {
	    		String name = phoneTree2.getOrganizeName();
	    		String parent = phoneTree2.getId()+"";
	    		sbtmp.append("{\"id\":\""+phoneTree2.getId()+"\",");
				sbtmp.append("\"text\":\""+name+"\"");
				StringBuilder childrenSbtmp = getChildren2(parent);
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
    /**
	 * 添加角色
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/addRole.php")
	public void addRole(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		
		String roleName = request.getParameter("roleName");// 角色名称
		String describe = request.getParameter("describe");// 描述
		//String organization = request.getParameter("organization");// 所属机构
		String department = request.getParameter("department");
		String section = request.getParameter("section");
		String dataScope = request.getParameter("dataScope");// 数据范围
		String roleMenu = request.getParameter("roleMenu");// 授权功能
		String uname = (String) session.getAttribute("userName");//添加人姓名
		Role t = new Role();
		t.setRoleName(roleName);
		t.setRoleDescribe(describe);
		//t.setOrganization(organization);
		t.setDepartment(department);
		t.setSection(section);
		t.setDataScope(dataScope);
		t.setRoleMenu(roleMenu);
		t.setAddTime(dateFormat2.format(new Date()));
		t.setAddMan(uname);
		sqlDao.setBeanToMysql(t);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(t.toString());
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
	 * 编辑角色
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/editRole.php")
	public void editRole(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String roleid = request.getParameter("roleid");// 角色id
		String roleName = request.getParameter("roleName");// 角色名称
		String describe = request.getParameter("describe");// 描述
		//String organization = request.getParameter("organization");// 所属机构
		String department = request.getParameter("department");
		String section = request.getParameter("section");
		String dataScope = request.getParameter("dataScope");// 数据范围
		String roleMenu = request.getParameter("roleMenu");// 授权功能
		String uname = (String) session.getAttribute("userName");//添加人姓名
		Role t = new Role();
		t.setId(Integer.parseInt(roleid));
		t.setRoleName(roleName);
		t.setRoleDescribe(describe);
		//t.setOrganization(organization);
		t.setDepartment(department);
		t.setSection(section);
		t.setDataScope(dataScope);
		t.setRoleMenu(roleMenu);
		//t.setAddTime(dateFormat2.format(new Date()));
		t.setAddMan(uname);
		sqlDao.updateToMysql(t);
		
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(t.toString()+"");
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
	 * 删除角色
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/deleteRole.php")
	public void deleteRole(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String roleid = request.getParameter("roleid");// 角色id
		Role t = new Role();
		t.setId(Integer.parseInt(roleid));
		sqlDao.deletefromMysql(t);
	}
	
    /**
	 * 获得所有角色
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/getRole.php")
	public void getRole(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		String name = request.getParameter("name");//角色名称
		String pageIndex = request.getParameter("pageIndex");//页数
		int page=Integer.parseInt(pageIndex);
		Map<String, Object> map = new HashMap<String, Object>();
		Role t = new Role();
		t.setRoleName(name);
		int count = sqlDao.getcountfromMysqlLike(t);
		List<Role> listfromMysql = sqlDao.getListfromMysqlLike(t, (page - 1) * 10, 10);
		for (Role role : listfromMysql) {
			//String organizationid = role.getOrganization();
			String department = role.getDepartment();
			String section = role.getSection();
			/*Organization organization = new Organization();
			organization.setId(Integer.parseInt(organizationid));
			List<Organization> listfromMysql2 = sqlDao.getListfromMysql(organization);
			if(listfromMysql2.size()>0){
				role.setOrganization(listfromMysql2.get(0).getId()+"/"+listfromMysql2.get(0).getOrganizeName());
			}*/
			if(department!=null && !"".equals(department)){
				Department organization = new Department();
				organization.setId(Integer.parseInt(department));
				List<Department> listfromMysql2 = sqlDao.getListfromMysql(organization);
				if(listfromMysql2.size()>0){
					role.setDepartment(listfromMysql2.get(0).getId()+"/"+listfromMysql2.get(0).getDepartmentName());
				}
			}
			if(section!=null && !"".equals(section)){
				Section organization2 = new Section();
				organization2.setId(Integer.parseInt(section));
				List<Section> listfromMysql3 = sqlDao.getListfromMysql(organization2);
				if(listfromMysql3.size()>0){
					role.setSection(listfromMysql3.get(0).getId()+"/"+listfromMysql3.get(0).getSectionName());
				}
			}
			
		}
		map.put("list", listfromMysql);
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
    /**
	 * 获得所有角色
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/system/getAllRole.php")
	public void getAllRole(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		response.setContentType("textml; charset=UTF-8");
		Role t = new Role();
		List<Role> listfromMysql = sqlDao.getListfromMysql(t);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.list2json(listfromMysql));
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
		 * 获得所有部门
		 * 
		 * @param request
		 * @param response
		 * @throws IOException
		 * @throws InterruptedException
		 */
		@RequestMapping(value = "/system/getdDepartment.php")
		public void getdDepartment(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
			response.setContentType("textml; charset=UTF-8");
			Department t = new Department();
			List<Department> listfromMysql = sqlDao.getListfromMysqlLike(t);
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write(JsonUtil.list2json(listfromMysql));
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
		 * 获得部门对应科室
		 * 
		 * @param request
		 * @param response
		 * @throws IOException
		 * @throws InterruptedException
		 */
		@RequestMapping(value = "/system/getSection.php")
		public void getSection(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
			response.setContentType("textml; charset=UTF-8");
			String department = request.getParameter("department");//部门id
			Section t = new Section();
			t.setDepartment(department);
			List<Section> listfromMysql = sqlDao.getListfromMysqlLike(t);
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.write(JsonUtil.list2json(listfromMysql));
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
		 * 谷歌浏览器与客户端下载
		 * 
		 * @param request
		 * @param response
		 * @throws IOException
		 * @throws InterruptedException
		 */
		@RequestMapping(value = "/SystemManage/kehuduanAndliulanqi.php")
		public void kehuduanAndliulanqi(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
			request.setCharacterEncoding("utf-8");

			File f = new File("/opt/ClientAndChrome/KeHuDuan.zip");


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
	
	
	
}






