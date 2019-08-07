package com.xl.cloud.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.xl.cloud.bean.PictureInfo;
import com.xl.cloud.dao.SqlDao;
import com.xl.cloud.util.CompareImage;
import com.xl.cloud.util.JsonUtil;
import com.xl.cloud.util.TrimIllegalChar;

public class UploadShitu extends HttpServlet {
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
		System.out.println("进入上传了。。。");
		String savePath = "/shitu/";
		File savedir = null;
		File f1 = new File(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(f1); //设置上传文件的目的地
		
		//产生servlet上传对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		
		List<FileItem> fileList = null;    
        try {    
        	fileList = upload.parseRequest(request);
        } catch (FileUploadException ex) {    
            System.out.println("没有上传文件");  
            return;    
        }  
        //遍历上传文件写入磁盘
        Iterator<FileItem> it = fileList.iterator();
        //FileItem next = it.next();
        File saveFile = null;
      //  FileItem next = it.next();
       // long size = next.getSize();
        while(it.hasNext()){  
        	FileItem item = it.next();  
        	
            if(!item.isFormField()){    
                String tempName = item.getName();
               // System.out.println("~~~~~~~原格式~~~~~~"+tempName);
        		tempName = tic.trim(tempName);
               // System.out.println("~~~~~~~转换后~~~~~~"+tempName);
                savedir = new File(savePath);
                if(!savedir.exists()){
                	savedir.mkdirs();
                }
                saveFile = new File("/shitu/"+  tempName);
                System.out.println("linux存储路径=======》"+saveFile);
                BufferedReader reader = null;
                PrintWriter writer = null;
                try {
                    item.write(saveFile);
                    response.setCharacterEncoding("utf-8");
                    CompareImage compareImage = new CompareImage();
                    CompareImage.compare("/shitu/"+ tempName, "/emaildata/yuantu/");
    				List<String> hitPictureDirs = compareImage.hitPictureDir;
    				for (String string : hitPictureDirs) {
    					System.out.println("相同结果 路径 :    "+string);
					}
                    //System.out.println("结果"+dirPath);
                    writer = response.getWriter();
                	writer.write(JsonUtil.list2json(hitPictureDirs));
        			writer.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                	if(reader != null){
                		reader.close();
                	}
                	if (writer != null) {
        				writer.close();
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
