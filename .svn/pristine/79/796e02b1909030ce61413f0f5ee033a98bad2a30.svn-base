package com.xl.cloud.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class RWProperties {
	

	private static Properties property = null;
	
	/**
	 * 通过属性配置文件的key值获得相应的value值
	 * @param key 属性配置文件的Key值
	 * @param fileName 属性配置文件的完整路径，如：
	 * @return key值所对应的Value值
	 */
	
	public static String getProperty(String key,String fileName){
		String value = null;
		InputStream is =null;
		try {
			File f = new File(fileName);
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();
			}
			if(!f.exists()){
				f.createNewFile();
			}
			is = new FileInputStream(f);
			
//			System.out.println("路径为"+fileName);
			property=new Properties();
			property.load(is);
			value = property.getProperty(key);
//			System.out.println("配置文件的key值"+key);
//			System.out.println("配置文件的值"+value);
		} catch (NullPointerException e) {
			System.out.println("属性文件没有找到!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	/**
	 * 通过属性配置文件的key值获得相应的value值
	 * @param key 属性配置文件的Key值
	 * @param fileName 属性配置文件的完整路径，如：
	 * @return key值所对应的Value值
	 */
	
	public static Map<String, String> getPropertys(List<String> keys,String fileName){
		InputStream is =null;
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			File f = new File(fileName);
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();
			}
			if(!f.exists()){
				f.createNewFile();
			}
			is = new FileInputStream(f);
			
//			System.out.println("路径为"+fileName);
			property=new Properties();
			property.load(is);
			
			for(String key:keys){
				String value = property.getProperty(key);
				dataMap.put(key, value);
			}
		} catch (NullPointerException e) {
			System.out.println("属性文件没有找到!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataMap;
	}
	
	/**
	 * 修改属性配置文件
	 * @param key 属性配置文件的key值
	 * @param value 属性配置文件的value值
	 * @param fileName 属性配置文件的完整路径
	 * @return 属性文件修改成功返回true，失败返回false
	 */
	public static boolean setProperty(String key,String value,String fileName){
		OutputStream fos = null;
		InputStream fis=null;
		boolean flag = false;
//		URL url = RWProperties.class.getResource(filesURL);
		try {
			property=new Properties();
			File f = new File(fileName);
			if(!f.exists()){
				f.createNewFile();
			}
			fis=new FileInputStream(fileName);
			property.load(fis);
			
//			PropertiesLoaderUtils.loadProperties("");
//			System.out.println("写入属性文件===="+key+"=========value="+value);
			property.setProperty(key, value);
			fos = new FileOutputStream(fileName);
			property.store(fos, key);
			fos.flush();

			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("属性文件未找到！");
			e.printStackTrace();
		} finally{
			try {
				if(fos!=null){
					fos.close();
				}
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public static void main(String[] args){
		String fileName=RWProperties.class.getResource("/").toString().substring(6)+"web_spec.properties";
		setProperty("web_spec", "1", fileName);
		String str = getProperty("web_spec",fileName);
		System.out.println(str);
	}
	
}
