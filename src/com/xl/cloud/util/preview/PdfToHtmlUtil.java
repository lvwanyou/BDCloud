package com.xl.cloud.util.preview;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.tools.PDFToImage;
import org.junit.Test;

/**
 * 
 * @ClassName: PdfToHtmlUtils.java
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: lebron·dong
 * @date: 2017年8月13日 下午9:19:06
 *
 *        Modification History:
 */
public class PdfToHtmlUtil {

	@Test
	public void test1() throws Exception {
		String sourcePath = "D:/Cache/PDF测试1.pdf";
		String targetPath = "D:/Cache/test.html";
		pdfToHtml(sourcePath, targetPath);
	}

	public static void pdfToHtml(String sourcePath, String targetPath) throws Exception {
		//文件名称
		String fileName = FilenameUtils.getBaseName(sourcePath);
		String prefix = "<!doctype html><html lang='en'><head><meta charset='UTF-8'><title>文件预览" 
				+ "</title><style>img{width:1024px;}</style></head><body>";
		String postfix = "</body></html>";
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix);
		// 转图片
		PDFToImage.main(new String[] { sourcePath });
		// 输出html中
		String fullPath = FilenameUtils.getFullPath(sourcePath);
		File[] files = new File(fullPath).listFiles();
		List<Integer> numberList = new ArrayList<Integer>();
		for (File file : files) {
			if (FilenameUtils.getExtension(file.getName()).equals("jpg") && 
					file.getName().contains(fileName)) {
				// 获得数字
				int index = file.getName().lastIndexOf(".");
				String substring = file.getName().substring(fileName.length(), index);
				//System.out.println(substring);
				numberList.add(Integer.parseInt(substring));
			}
		}
		// 图片数字从小到大
		int min = Collections.min(numberList);
		int max = Collections.max(numberList);
		for (int i = min; i <= max; i++) {
			buffer.append("<div align='center'><img src='")
				.append("./")
				.append(fileName)
				.append(i)
				.append(".jpg")
				.append("'></div>");
		}
		//输出html
		buffer.append(postfix);
		//FileUtils.write(new File(targetPath), buffer.toString(), "utf-8");
		IOUtils.write(buffer, new FileOutputStream(new File(targetPath)),"utf-8");
	}
}