package com.xl.cloud.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

 
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PositionUtil{
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	
	/**
	 * 基站信息查询地址
	 * @param lac 区域位置码
	 * @param cell 基站号
	 * @param mnc 运营商 移动：0 联通：1
	 * @return 地址信息（string）
	 */
	public static String getAddressByCell(String lac,String cell,String mnc){
		String result = null;
		String url = "http://api.cellocation.com/cell/";
		Map params = new HashMap();
//		params.put("mnc", "0");//移动：0 联通：1
//		params.put("cell", "55272");//基站号
		params.put("mcc", "460");//国家代码 中国：460
		params.put("mnc", mnc);
		params.put("ci", cell);
		params.put("lac", lac);
		params.put("output", "json");//返回的数据格式
		
		try{
			result = net(url,params,"GET");
			JSONObject object = JSONObject.fromObject(result);
			//System.out.println(result);
			if(object.getInt("errcode")==0){
				/*JSONArray a = object.getJSONObject("result").getJSONArray("data");
				String res = object.getJSONObject("result").getJSONArray("data").getJSONObject(0).get("ADDRESS").toString();*/
				String res = object.get("address").toString();
				res+="-"+object.get("lat").toString()+"-"+object.get("lon").toString();
				//System.out.println("经度:"+object.get("lat").toString());
				//System.out.println("纬度:"+object.get("lon").toString());
				res+="-"+object.get("lat").toString()+"-"+object.get("lon").toString();
				/*if(res!=null&&!"".equals(res))
					res=res.split(";")[0];*/
				
				return res;
			}else{
				//System.out.println(object.get("error_code")+":"+object.get("reason"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 建立连接调用接口
	 * @param strUrl 接口url
	 * @param params 参数
	 * @param method GET或POST
	 * @return 接口返回信息（string）
	 */
	public static String net(String strUrl,Map params,String method) throws Exception{
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		
		try{
			StringBuffer sb = new StringBuffer();
			if(method==null || method.equals("GET")){
				strUrl = strUrl+"?"+urlencode(params);
				//System.out.println("url:"+strUrl);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if(method==null || method.equals("GET")){
				conn.setRequestMethod("GET");
			}else{
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent",userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			
			if(params!=null && method.equals("POST")){
				try{
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is,DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine())!=null){
				sb.append(strRead);
			}
			rs = sb.toString();
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if (reader != null){
				reader.close();
			}
			if (conn != null){
				conn.disconnect();
			}
		}
		
		return rs;
	}
	
	/**
	 * 参数格式转换
	 * @param 参数内容
	 * @return 
	 */
	public static String urlencode(Map<String,Object> data){
		StringBuilder sb = new StringBuilder();
		/*for(int i=0;i<data.size();i++){*/
		for(Map.Entry i : data.entrySet()){
			try{				
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		String res = sb.toString().substring(0, sb.toString().length()-1);
		//System.out.println("params:"+res);
		return res;
	}
	
	public static void main(String[] args){
		//System.out.println(getAddressByCell("21031","21031","1"));
	}
}