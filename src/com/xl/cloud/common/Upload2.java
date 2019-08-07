package com.xl.cloud.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.xl.cloud.bean.Dealinfo;
import com.xl.cloud.bean.PictureInfo;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.TrimIllegalChar;

public class Upload2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static TrimIllegalChar tic=new TrimIllegalChar();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String caseNum = "";
		System.out.println("进入上传了。。。。。");
		int caseID = Integer.valueOf(request.getParameter("case_id"));
		String evUUID = request.getParameter("evUUID");
		//String caseNum = "";
		//String caseNum2 = (String) session.getAttribute("caseNum");
		String savePath = "/emaildata/";
		
		File f1 = new File(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(f1); //设置上传文件的目的地
		
		//产生servlet上传对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		List<FileItem> fileList = null;    
        try {    
        	fileList = upload.parseRequest(request);
        } catch (FileUploadException ex) {    
            System.out.println("没有上传文件");  
            return;    
        }  
        //遍历上传文件写入磁盘
        Iterator<FileItem> it = fileList.iterator();
       // FileItem next = it.next();
       // long size = next.getSize();
        File saveFile = null;
        File savedir = null;
        File savedir2 = null;
        while(it.hasNext()){  
        	FileItem item = it.next();    
        	if(item.isFormField()){
        		caseNum = item.getString("UTF-8");
        		if(!StringUtils.isEmpty(caseNum)){
        			try {
        				//caseNum2 = URLEncoder.encode(caseNum2, "UTF-8");
        				//caseNum2 = caseNum2.replace("%", "X_L");
        			} catch (Exception e) {
        			}
        		}
        		System.out.println("上传路径："+savePath+caseID);
        	}else if(!item.isFormField()){
                String tempName = item.getName();
               // System.out.println("~~~~~~~原格式~~~~~~"+tempName);
        		tempName = tic.trim(tempName);
               // System.out.println("~~~~~~~转换后~~~~~~"+tempName);
                String[] sss =  tempName.split("/");

                savedir = new File(savePath+caseID + "/" +caseID+evUUID.substring(0, 10));
                if(!savedir.exists()){
                	savedir.mkdirs();
                }
                saveFile = new File(savedir+ "/" + sss[sss.length-1]);

                //如果是图片 图片保存到识图文件夹库
                int falg=0;
                String dirType = tempName.split("\\.")[1];
                if("img".equalsIgnoreCase(dirType) || "png".equalsIgnoreCase(dirType) || "jpg".equalsIgnoreCase(dirType) || "jpeg".equalsIgnoreCase(dirType) || "bmp".equalsIgnoreCase(dirType) || "gif".equalsIgnoreCase(dirType)){
                	savedir2 = new File("/emaildata/yuantu");
                    if(!savedir2.exists()){
                    	savedir2.mkdirs();
                    }
                    falg=1;
                }

                BufferedReader reader = null;
                try {
                    item.write(saveFile);
                    response.setCharacterEncoding("utf-8");
                    if(falg==1){
                    	copyFile(saveFile,"/emaildata/yuantu/"+ sss[sss.length-1]);
                    }
//                    response.setContentType("text/html");
//                    response.getWriter().print("{\"res\":\"succ\"}");
                   //response.getWriter().print("succ");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                	if(reader != null){
                		reader.close();
                	}
                }
            }    
        }    
	}
	
	public void copyFile(File file,String copyFilePath){
		File copyFile = new File(copyFilePath);
		if(!copyFile.exists()){
			copyFile.getParentFile().mkdirs();
		}
		if(file.exists()){
			try {
				InputStream in = new FileInputStream(file); 
	            FileOutputStream fos = new FileOutputStream(copyFilePath); 
	            byte[] buffer = new byte[1024]; 
	            int n; 
	            while ( (n = in.read(buffer)) != -1) { 
	                fos.write(buffer, 0, n); 
	            } 
	            in.close(); 
	            fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

}
