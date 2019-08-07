package com.xl.cloud.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.xl.cloud.util.TrimIllegalChar;

public class UploadOfClue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static TrimIllegalChar tic=new TrimIllegalChar();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 线索详情页上传处理单文件   --> 周
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("进入上传了。。。");
		int caseID = Integer.valueOf(request.getParameter("clueDetailsId"));
		System.out.println("UploadOfClue方法中的caseid=========>"+caseID);
		
		String savePath = "/clueDetails/";
		File savedir = null;
//		File savedir2 = null;
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
        File saveFile = null;
        while(it.hasNext()){  
        	FileItem item = it.next();  
            if(!item.isFormField()){    
                String tempName = item.getName();
        		tempName = tic.trim(tempName);
                savedir = new File(savePath + caseID);
                if(!savedir.exists()){
                	savedir.mkdirs();
                }
                saveFile = new File(savedir+"/"+  tempName);

                //如果是图片 图片保存到识图文件夹库
               /* int falg=0;
                String dirType = tempName.split("\\.")[1];
                if("img".equalsIgnoreCase(dirType) || "png".equalsIgnoreCase(dirType) || "jpg".equalsIgnoreCase(dirType) || "jpeg".equalsIgnoreCase(dirType) || "bmp".equalsIgnoreCase(dirType) || "gif".equalsIgnoreCase(dirType)){
                	savedir2 = new File("/emaildata/yuantu");
                    if(!savedir2.exists()){
                    	savedir2.mkdirs();
                    }
                    falg=1;
                    //saveFile2 = new File(savedir2+"/"+  tempName);
                }*/
                System.out.println("linux存储路径=======》"+saveFile);
                BufferedReader reader = null;
                try {
                    item.write(saveFile);
                    response.setCharacterEncoding("utf-8");
                   /* if(falg==1){
                    	copyFile(saveFile,"/emaildata/yuantu/"+tempName);
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
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
