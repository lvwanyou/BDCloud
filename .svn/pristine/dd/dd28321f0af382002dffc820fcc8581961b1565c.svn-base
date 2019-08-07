package com.xl.cloud.util;

import java.io.File;

public class TrimIllegalChar {
	public String trim(String str){
		if(str.contains("!")){
			str=str.replace("!", "2P");
		}
		if(str.contains("#")){
			str=str.replace("#", "2O");
		}
		if(str.contains("$")){
			str=str.replace("$", "2I");
		}
		if(str.contains("&")){
			str=str.replace("&", "2U");
		}
		if(str.contains("'")){
			str=str.replace("'", "2Y");
		}
		if(str.contains("(")){
			str=str.replace("(", "2E");
		}
		if(str.contains(")")){
			str=str.replace(")", "2Q");
		}
		if(str.contains("*")){
			str=str.replace("*", "2A");
		}
		
		if(str.contains(" ")){
			str=str.replace(" ", "2W");
		}
		if(str.contains("+")){
			str=str.replace("+", "2B");
		}
		if(str.contains(",")){
			str=str.replace(",", "2C");
		}
		if(str.contains(":")){
			str=str.replace(":", "3A");
		}
		if(str.contains(";")){
			str=str.replace(";", "3B");
		}
		
		if(str.contains("=")){
			str=str.replace("=", "3D");
		}
		if(str.contains("?")){
			str=str.replace("?", "3F");
		}
		if(str.contains("[")){
			str=str.replace("[", "5B");
		}
		
		if(str.contains("]")){
			str=str.replace("]", "5D");
		}
		if(str.contains("\\")){
			str=str.replace("\\", File.separator);
		}
		if(str.contains("%")){
			str=str.replace("%", "2Z");
		}
		if(str.contains("{")){
			str=str.replace("{", "7B");
		}
		if(str.contains("}")){
			str=str.replace("}", "7D");
		}
		if(str.contains("|")){
			str=str.replace("|", "7C");
		}
		if(str.contains("~")){
			str=str.replace("~", "7E");
		}
		if(str.contains("`")){
			str=str.replace("`", "6A");
		}
		if(str.contains("^")){
			str=str.replace("^", "5E");
		}
		
		if(str.contains("�")){
			str=str.replace("�", "5Q");
		}
		if(str.contains("ˬ")){
			str=str.replace("ˬ", "5W");
		}
		if(str.contains("�")){
			str=str.replace("�", "5R");
		}
		
		return str;
		
	}
	
	//返译
	public String no_trim(String str){
		if(str.contains("2P")){
			str=str.replace("2P", "!");
		}
		if(str.contains("2O")){
			str=str.replace("2O", "#");
		}
		if(str.contains("2I")){
			str=str.replace("2I", "$");
		}
		if(str.contains("2U")){
			str=str.replace("2U", "&");
		}
		if(str.contains("2Y")){
			str=str.replace("2Y", "'");
		}
		if(str.contains("2E")){
			str=str.replace("2E", "(");
		}
		if(str.contains("2Q")){
			str=str.replace("2Q", ")");
		}
		if(str.contains("2A")){
			str=str.replace("2A", "*");
		}
		
		if(str.contains("2W")){
			str=str.replace("2W", " ");
		}
		if(str.contains("2B")){
			str=str.replace("2B", "+");
		}
		if(str.contains("2C")){
			str=str.replace("2C", ",");
		}
		if(str.contains("3A")){
			str=str.replace("3A", ":");
		}
		if(str.contains("3B")){
			str=str.replace("3B", ";");
		}
		
		if(str.contains("3D")){
			str=str.replace("3D", "=");
		}
		if(str.contains("3F")){
			str=str.replace("3F", "?");
		}
		if(str.contains("5B")){
			str=str.replace("5B", "[");
		}
		
		if(str.contains("5D")){
			str=str.replace("5D", "]");
		}
		if(str.contains(File.separator)){
			str=str.replace(File.separator,"\\" );
		}
		if(str.contains("2Z")){
			str=str.replace("2Z", "%");
		}
		if(str.contains("7B")){
			str=str.replace("7B", "{");
		}
		if(str.contains("7D")){
			str=str.replace("7D", "}");
		}
		if(str.contains("7C")){
			str=str.replace("7C", "|");
		}
		if(str.contains("7E")){
			str=str.replace("7E", "~");
		}
		if(str.contains("6A")){
			str=str.replace("6A", "`");
		}
		if(str.contains("5E")){
			str=str.replace("5E", "^");
		}
		
		if(str.contains("5Q")){
			str=str.replace("5Q", "�");
		}
		if(str.contains("5W")){
			str=str.replace("5W", "ˬ");
		}
		if(str.contains("5R")){
			str=str.replace("5R", "�");
		}
		return str;
		
		}
	
	public boolean stripNonCharCodepoints(char[] input) {
		char ch;
		for (int i = 0; i < input.length; i++) {
			ch = input[i];
			// Strip all non-characters
			// http://unicode.org/cldr/utility/list-unicodeset.jsp?a=[:Noncharacter_Code_Point=True:]
			// and non-printable control characters except tabulator, new line
			// and carriage return
			if (ch % 0x10000 == 0xffff || // 0xffff - 0x10ffff range step
					ch % 0x10000 == 0xfffe || // 0xfffe - 0x10fffe range
					ch == 0xfdd0 || ch == 0xfdef)//
			{
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		String test="hdfs://172.16.102.120:8020/tmp/case02_SZ/emaildata/case02_SZ/annchen@otsbjs.com.cn/垃圾邮件/亚马逊 向您推荐 _我是落花生的女儿_ 和其它商品.eml";
		String test1 = "/emaildata/1957/1957e6d12d345e/Re2020S187220-20Balance20920containers20-20-20David20Chen2028david@bona-agro.com2920-202016-07-28202242.eml";
		String test2 = "/emaildata/1957/1957e6d12d345e/Re  S1872 - Balance 9 containers - - David Chen (david@bona-agro.com) - 2016-07-28 2242.eml";
		String a ="2Z";
		TrimIllegalChar tir=new TrimIllegalChar();
		System.out.println(tir.stripNonCharCodepoints(test1.toCharArray()));
		System.out.println(tir.no_trim(a));
		
	}
}
