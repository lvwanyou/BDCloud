package com.xl.cloud.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import com.sun.star.i18n.Calendar;
import com.xl.cloud.bean.Cellinfo_v2;
import com.xl.cloud.common.Global;

public class BaseIDSqlDao {
	public static final String url = "jdbc:mysql://"+Global.DBhostIP+Global.baseID_DBName; //172.16.102.20 /baseidlib
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = Global.DBhostUser;  
    public static final String password =Global.DBhostPassWord; // "qsdhjuy543"; // "123456";//
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    static{
    	System.out.println("BaseIDSqlDao，数据库IP："+Global.DBhostIP);
    	System.out.println("url--"+url);
    }
    
    
    public BaseIDSqlDao() {
    	super();
    }
    public BaseIDSqlDao(String sql) {  
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    
    public static Connection getConnection()
    {
    	Connection _conn = null;
    	try {  
            Class.forName(name);//指定连接类型  
            _conn = DriverManager.getConnection(url, user, password);//获取连接   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    	return _conn;
    }
    /**
	 * 数据查询
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysql(T t,String lac,String ci) {
		BaseIDSqlDao db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = getTableName(t.getClass().getSimpleName(), lac, ci);
		List<T> tList = new ArrayList<T>();
		//sql = "SELECT * FROM " + collectionName;
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {

					continue;
				}
				value = value + s + "=\"" + m.invoke(t).toString() + "\" and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			if (dateflag) {
				if(collectionName.contains("OrderTitle")||collectionName.contains("CountTitle")){
					sql = sql + " order by date asc";
				}else{
				    sql = sql + " order by date desc";
				}
			}
			if(collectionName.contains("Clue")) {
				sql = sql + " order by id desc";
			}
			
			db1 = new BaseIDSqlDao(sql);// 创建BaseIDSqlDao对象
			// System.out.println(sql);

//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
//			System.out.println(sql);
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();// com.xl.wechat.AutoReply

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}
						if ((typename != null) && typename.equals("Integer")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}
					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	/**
	 * 根据获取到的ci和lac确定表名
	 * @param args
	 */
	private String getTableName(String collectionName,String lac,String ci){
		long Lac;
		long Ci;
		if(lac!=null&&!"".equals(lac))
			Lac=Long.parseLong(lac);
		else
			return collectionName;
		if(ci!=null&&!"".equals(ci))
		    Ci=Long.parseLong(ci);
		else
			return collectionName;
		if(Lac==63235L&&Ci>=26055L||Lac>=63235L)
			return collectionName+"_49";
		if(Lac==59228L&&Ci>=130165798L||Lac>=59228L)
			return collectionName+"_48";
		if(Lac==57686L&&Ci>=2832L||Lac>=57686L)
			return collectionName+"_47";
		if(Lac==55075L&&Ci>=1941L||Lac>=55075L)
			return collectionName+"_46";
		if(Lac==54051L&&Ci>=118905326L||Lac>=54051L)
			return collectionName+"_45";
		if(Lac==50944L&&Ci>=77615975L||Lac>=50944L)
			return collectionName+"_44";
		if(Lac==47892L&&Ci>=16381L||Lac>=47892L)
			return collectionName+"_43";
		if(Lac==45922L&&Ci>=64714451L||Lac>=45922L)
			return collectionName+"_42";
		if(Lac==43050L&&Ci>=13745186L||Lac>=43050L)
			return collectionName+"_41";
		if(Lac==42319L&&Ci>=10823L||Lac>=42319L)
			return collectionName+"_40";
		if(Lac==41042L&&Ci>=34964L||Lac>=41042L)
			return collectionName+"_39";
		if(Lac==38171L&&Ci>=53333L||Lac>=38171L)
			return collectionName+"_38";
		if(Lac==37112L&&Ci>=3335L||Lac>=37112L)
			return collectionName+"_37";
		if(Lac==34388L&&Ci>=14793L||Lac>=34388L)
			return collectionName+"_36";
		if(Lac==33256L&&Ci>=46292L||Lac>=33256L)
			return collectionName+"_35";
		if(Lac==32783L&&Ci>=134330416L||Lac>=32783L)
			return collectionName+"_34";
		if(Lac==30614L&&Ci>=23842L||Lac>=30614L)
			return collectionName+"_33";
		if(Lac==29811L&&Ci>=199190273L||Lac>=29811L)
			return collectionName+"_32";
		if(Lac==29189L&&Ci>=112357891L||Lac>=29189L)
			return collectionName+"_31";
		if(Lac==28559L&&Ci>=13334L||Lac>=28559L)
			return collectionName+"_30";
		if(Lac==26893L&&Ci>=132491028L||Lac>=26893L)
			return collectionName+"_29";
		if(Lac==26140L&&Ci>=14482L||Lac>=26140L)
			return collectionName+"_28";
		if(Lac==25272&&Ci>=37663L||Lac>=25272L)
			return collectionName+"_27";
		if(Lac==24383L&&Ci>=57551L||Lac>=24383L)
			return collectionName+"_26";
		if(Lac==22821L&&Ci>=4902L||Lac>=22821L)
			return collectionName+"_25";
		if(Lac==22419L&&Ci>=16138L||Lac>=22419L)
			return collectionName+"_24";
		if(Lac==21916L&&Ci>=35122L||Lac>=21916L)
			return collectionName+"_23";
		if(Lac==21466L&&Ci>=13292L||Lac>=21466L)
			return collectionName+"_22";
		if(Lac==21114L&&Ci>=225315329L||Lac>=21114L)
			return collectionName+"_21";
		if(Lac==20753L&&Ci>=56878L||Lac>20753L)
			return collectionName+"_20";
		if(Lac==18421L&&Ci>=79825933L||Lac>18421L)
			return collectionName+"_19";
		if(Lac==17336L&&Ci>=22071L||Lac>17336L)
			return collectionName+"_18";
		if(Lac==16430L&&Ci>=15450L||Lac>16430L)
			return collectionName+"_17";
		if(Lac==14242L&&Ci>=9318L||Lac>14242L)
			return collectionName+"_16";
		if(Lac==13744L&&Ci>=129071115L||Lac>13744L)
			return collectionName+"_15";
		if(Lac==13059L&&Ci>=90920192L||Lac>13059L)
			return collectionName+"_14";
		if(Lac==12525L&&Ci>=6778L||Lac>12525L)
			return collectionName+"_13";
		if(Lac==10304L&&Ci>=53275651L||Lac>10304L)
			return collectionName+"_12";
		if(Lac==9935L&&Ci>=111120397L||Lac>9935L)
			return collectionName+"_11";
		if(Lac==9615L&&Ci>=181000450L||Lac>9615L)
			return collectionName+"_10";
		if(Lac==9339L&&Ci>=3811L||Lac>9339L)
			return collectionName+"_9";
		if(Lac==8470L&&Ci>=59595L||Lac>8470L)
			return collectionName+"_8";
		if(Lac==6196L&&Ci>=1386L||Lac>6196L)
			return collectionName+"_7";
		if(Lac==4605L&&Ci>=45342L||Lac>4605L)
			return collectionName+"_6";
		if(Lac==4247L&&Ci>=54011L||Lac>4247L)
			return collectionName+"_5";
		if(Lac==2018L&&Ci>=186L||Lac>2018L)
			return collectionName+"_4";
		if(Lac==1005L&&Ci>=59633L||Lac>1005L)
			return collectionName+"_3";
		if(Lac==5L&&Ci>=1702L||Lac>5L)
			return collectionName+"_2";
		return collectionName+"_1";
		
	}

	public static void main(String[] args) {
		// test
		System.out.println("kaishichaxun");
		
		Cellinfo_v2 bit=new Cellinfo_v2();
		bit.setCi("1");
		bit.setLac("0");
		BaseIDSqlDao bis=new BaseIDSqlDao();
		
		long a=System.currentTimeMillis();
		System.out.println(a);
		Cellinfo_v2 bit2;
		bit2=bis.getListfromMysql(bit,bit.getLac(),bit.getCi()).get(0);
		bit.setCi("1");
		bit2=bis.getListfromMysql(bit,bit.getLac(),bit.getCi()).get(0);
		bit.setCi("21");
		bit2=bis.getListfromMysql(bit,bit.getLac(),bit.getCi()).get(0);
		bit.setCi("33");
		bit2=bis.getListfromMysql(bit,bit.getLac(),bit.getCi()).get(0);
		bit.setCi("257");
		bit=bis.getListfromMysql(bit,bit.getLac(),bit.getCi()).get(0);
		System.out.println(System.currentTimeMillis()-a+"ms");
		String addr=bit.getAddr();
		String lat=bit.getLat();
		String lng=bit.getLon();
		System.out.println("地点："+addr+"\n经度："+lng+"\n纬度："+lat);

		System.out.println("操作成功");

	}

}
