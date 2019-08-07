package com.xl.cloud.common;

import java.io.File;
import java.io.IOException;

public class TestOcr {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//输入图片地址
		String path = "E://5.jpg";  
		System.out.println("ORC Test Begin......");
        try {   
            String valCode = new OCR().recognizeText(new File(path), "jpg");   
            System.out.println(valCode);   
        } catch (IOException e) {   
            e.printStackTrace();   
        } catch (Exception e) {
			e.printStackTrace();
		}    
        System.out.println("ORC Test End......");
	}


}
