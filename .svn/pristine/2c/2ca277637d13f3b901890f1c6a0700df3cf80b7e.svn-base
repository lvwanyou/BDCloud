package com.xl.cloud.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @Description: DCS文档转换服务Java调用代码示例
 * @author LB
 * @date 20151014
 */
public class DcsUtil {
	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 上传文件POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param filepath
	 *            文件路径
	 * @param type
	 *            转换类型
	 * @return 所代表远程资源的响应结果, json数据
	 */
	public static String SubmitPost(String url, String filepath, String type) {
		String requestJson = "";
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httppost = new HttpPost(url);
			FileBody file = new FileBody(new File(filepath));
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null,Charset.forName("UTF-8"));
			reqEntity.addPart("file", file); // file为请求后台的File upload;属性
			reqEntity.addPart("convertType", new StringBody(type, Charset.forName("UTF-8")));
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity resEntity = response.getEntity();
				requestJson = EntityUtils.toString(resEntity);
				EntityUtils.consume(resEntity);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// requestJson = e.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// requestJson = e.toString();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
		return requestJson;
	}

	/**
	 * 转换参数
	 * 0-----文档格式到高清html的转换
		1-----文档格式到html的转换
		2-----文档格式到txt的转换
		3-----文档格式到pdf的转换
		4-----文档格式到gif的转换
		5-----文档格式到png的转换
		6-----文档格式到jpg的转换
		7-----文档格式到tiff的转换
		8-----文档格式到bmp的转换
		9-----pdf文档格式到gif的转换
		10----pdf文档格式到png的转换
		11----pdf文档格式到jpg的转换
		12----pdf文档格式到tiff的转换
		13----pdf文档格式到bmp的转换
		14----pdf文档格式到html的转换
		15----html文档格式到微软文档格式的转换
		16----文档转换多个SVG返回分页加载页面(模版)
		17----tif文件转成html
		18----文档转换多个SVG
		19----压缩文件到html的转换(模版)
		20----PDF文件到html的转换(模版)
		21----ofd文件到html的转换(模版)
		22----两个doc文档合并
		23----图片到html的转换
		24----pdf文档格式到txt的转换
		25----文档按页转换（高清版）
		26----文档按页转换（标准版）
		27----获取文档页码（MS文档）
		28----获取pdf页码（PDF文件）

	 * @param args
	 *//*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 文件上传转换
		//如果是word，第三个参数就是1，如果是pdf，就是14，参考上面的转换参数
		String localpath = "C:/Users/admin/Desktop/23121.pdf";
		String type = "14";
		String filename = localpath.substring(23);
		System.out.println("filename:"+filename);
		String convertByFile = SubmitPost("http://dcs.yozosoft.com:80/upload", localpath, type);
		// 网络地址转换
		String convertByUrl = sendPost("http://dcs.yozosoft.com:80/onlinefile","downloadUrl=http://img.iyocloud.com:8000/"+filename+"&convertType=1");
		System.out.println(convertByFile);
		System.out.println(convertByUrl);
	}*/
}
	