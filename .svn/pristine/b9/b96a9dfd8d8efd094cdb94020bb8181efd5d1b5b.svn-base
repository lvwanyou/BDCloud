package com.xl.cloud.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.net.URLCodec;
import org.junit.Test;

public class UrlCodeUtil {

	/**
	 * url字符串解码
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String urlDecode(String value) throws Exception {
		StringEncoder encoder = new URLCodec();
		StringDecoder decode = new URLCodec();
		return decode.decode(value);
	}
	
	/**
	 * url字符串编码,防止中文乱码
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String urlEncode(String value) throws Exception {
		StringEncoder encoder = new URLCodec();
		return encoder.encode(value);
	}
	
	
	@Test
	public void test1() throws Exception {
		System.out.println(urlDecode("%E7%A8%8B.jpg"));
		System.out.println(urlDecode("C:/Users/admin/Desktop/shanxi.jpg"));
	}
}
