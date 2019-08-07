package com.xl.cloud.dao;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;

import com.xl.cloud.common.Global;  
public class ip_SqlDAO {
		public static final String url = "jdbc:mysql://"+Global.DBhostIP+Global.ip_DBName; //172.16.102.20 
	    public static final String name = "com.mysql.jdbc.Driver";  
	    public static final String user = Global.DBhostUser;  
	    public static final String password =Global.DBhostPassWord; // "qsdhjuy543"; // "123456";//
	  
	    public Connection conn = null;  
	    public PreparedStatement pst = null;  
	  
	    static{
	    	System.out.println("DBHelper，数据库IP："+Global.DBhostIP);
	    }
	    
	    public ip_SqlDAO() throws Exception{
	        try{
	            Class.forName(name) ;
	            this.conn = DriverManager.getConnection(url,user,password) ;
	        }catch(Exception e){
	            throw e ;
	        }
		}
	    public Connection getConnection(){
	        return this.conn ;
	    }
	    public void close() throws Exception{
	        if(this.conn != null){
	            try{
	                this.conn.close() ;
	            }catch(Exception e){
	                throw e ;
	            }
	        }

			if (this.pst != null) {
				try{
					this.pst.close();
				}catch (Exception e){
					throw e;
				}
			}

		}
	  
}
