package com.xl.cloud.action;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xl.cloud.bean.Section;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class Panorama {


	public List<Info> infos=new ArrayList<Info>(); 
	
	public List<Info> getInfos() {
		return infos;
	}

	public void setInfos(List<Info> infos) {
		this.infos = infos;
	}
	
	public void initInfos(){
		infos.clear();
		Info a=new Info();
		a.setAddr("潮州市新桥东路新泽里A区四幢3号铺面");
		a.setName("潮州市供销社房地产公司");
		a.setIdentify_num("");
		a.setArea("68.0400");
		a.setProperty_num("2012010337");
		a.setAnnual_tax("918.54");
		a.setRent("81648");
		a.setRent_tax("9797.76");
		a.setPersonal_income("4082.4");
		a.setEducation_pay("489.888");
		a.setStamp_tax("81.648");
		a.setSum("15370.24");
	
		Info b=new Info();
		b.setAddr("潮州市新桥东路新英里第21号铺");
		b.setName("许伟奇");
		b.setIdentify_num("440520197101114215");
		b.setArea("63.6200");;
		b.setProperty_num("2009000823");
		b.setAnnual_tax("858.87");
		b.setRent("76344");
		b.setRent_tax("9161.28");
		b.setPersonal_income("3817.2");
		b.setEducation_pay("458.064");
		b.setStamp_tax("76.344");
		b.setSum("14371.76");
		
		Info c=new Info();
		c.setAddr("新桥东内路新泽里四幢门市3号");
		//潮州市新桥东路新桥花园鱼肉菜市场6号铺面
		c.setName("章光丰");
		c.setIdentify_num("440520195605050316");
		c.setArea("19.7600");;
		c.setProperty_num("2011015411");
		c.setAnnual_tax("266.76");
		c.setRent("23712");
		c.setRent_tax("2845.44");
		c.setPersonal_income("1185.6");
		c.setEducation_pay("142.272");
		c.setStamp_tax("23.712");
		c.setSum("4463.784");
		
		Info d=new Info();
		d.setAddr("广东省潮州市湘桥区西新街道新桥路新泽里A区");
		//潮州市新桥东路新兴市场第15号铺
		d.setName("吴楚花");
		d.setIdentify_num("445102198308121222");
		d.setArea("13.6400");;
		d.setProperty_num("2016037616");
		d.setAnnual_tax("184.14");
		d.setRent("16368");
		d.setRent_tax("1964.16");
		d.setPersonal_income("818.4");
		d.setEducation_pay("98.208");
		d.setStamp_tax("16.368");
		d.setSum("3081.276");
//		a.area="100";
//		a.property_num="100";
//		a.annual_tax="99.99";
//		a.rent="199";
//		a.rent_tax="20";
//		a.personal_income="30";
//		a.education_pay="330";
//		a.stamp_tax="102";
//		a.sum="123";
		infos.add(a);
		infos.add(b);
		infos.add(c);
		infos.add(d);
	}
	private SqlDao sqlDao = new SqlDao();
	
	// 查询黑客数据库
	@RequestMapping(value = "/panoramaSearch.php")
	public void panoramaSearch(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	//	System.out.println("----------------  into method ");
		response.setContentType("textml; charset=UTF-8");
		initInfos();
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("panoramaLabels", infos);
		PrintWriter writer = null;
		JSONObject json = JSONObject.fromObject(hashMap); 
		try {
			writer = response.getWriter();
			writer.write(json.toString());
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
