package com.xl.cloud.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Test;
import org.w3c.dom.Document;

public class DocToHtml {

	/**
	 * 转2007word
	 * @param docxFilePath
	 * @param htmlPath
	 * @throws Exception
	 */
	public static void convertDocxToHtml(String docxFilePath, String htmlPath) throws Exception {
		WordprocessingMLPackage wordMLPackage = Docx4J.load(new File(docxFilePath));
		HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
		String imageFilePath = htmlPath.substring(0, htmlPath.lastIndexOf("/") + 1) + "/images";
		htmlSettings.setImageDirPath(imageFilePath);
		htmlSettings.setImageTargetUri("images");
		htmlSettings.setWmlPackage(wordMLPackage);
		String userCSS = "html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, img,  ol, ul, li, table, caption, tbody, tfoot, thead, tr, th, td " + "{ margin: 0; padding: 0; border: 0;}"
				+ "body {line-height: 1;} ";
		htmlSettings.setUserCSS(userCSS);
		OutputStream os;
		os = new FileOutputStream(htmlPath);
		Docx4jProperties.getProperties().setProperty("docx4j.Log4j.Configurator.disabled", "true");
		// Log4jConfigurator.configure();
		// org.docx4j.convert.out.pdf.viaXSLFO.Conversion.log.setLevel(Level.OFF);
		Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);
		Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
	}

	/**
	 * 转2003word
	 * @param fileName
	 * @param outPutFile
	 * @throws Exception
	 */
	public static void convertDocToHtml(String fileName, String outPutFile) throws Exception {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		final String folderName=System.currentTimeMillis()+"";
		String basePath=FilenameUtils.getFullPath(fileName);
		try {
			File file = new File(fileName);
			if (file.exists()) {
				in = new FileInputStream(fileName);
				HWPFDocument wordDocument = new HWPFDocument(in);
				// WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));
				WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
				// 处理图片
				wordToHtmlConverter.setPicturesManager(new PicturesManager() {
					public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
						return folderName+"/" + suggestedName;
					}
				});
				//创建文件夹
				if(!new File(basePath+folderName).exists()){
					new File(basePath+folderName).mkdir();
				}
				wordToHtmlConverter.processDocument(wordDocument);
				// save pictures
				// 保存图片
				List pics = wordDocument.getPicturesTable().getAllPictures();
				if (pics != null) {
					for (int i = 0; i < pics.size(); i++) {
						Picture pic = (Picture) pics.get(i);
						try {
							
							pic.writeImageContent(new FileOutputStream(basePath+folderName+"/" + pic.suggestFullFileName()));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
				Document htmlDocument = wordToHtmlConverter.getDocument();
				out = new ByteArrayOutputStream();
				DOMSource domSource = new DOMSource(htmlDocument);
				StreamResult streamResult = new StreamResult(out);

				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer serializer = tf.newTransformer();
				// 这样转码服务器正常，但是本地调试中文乱码
				// 服务器编码改成utf-8
				serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
				serializer.setOutputProperty(OutputKeys.INDENT, "yes");
				serializer.setOutputProperty(OutputKeys.METHOD, "html");
				serializer.transform(domSource, streamResult);
				writeFile(new String(out.toByteArray()), outPutFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}

	}
	
	/***
	 * 转word2003
	 * @param fileName
	 * @param outPutFile
	 * @param folderName 存放生成图片的路径
	 * @throws Exception
	 */
	public static void convertDocToHtml(String fileName, String outPutFile,final String folderName) throws Exception {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		String basePath=FilenameUtils.getFullPath(fileName);
		try {
			File file = new File(fileName);
			if (file.exists()) {
				in = new FileInputStream(fileName);
				HWPFDocument wordDocument = new HWPFDocument(in);
				// WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));
				WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
				// 处理图片
				wordToHtmlConverter.setPicturesManager(new PicturesManager() {
					public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
						//return folderName+"/" + suggestedName;
						return "./" + suggestedName;
					}
				});
				//创建文件夹
				if(!new File(basePath+folderName).exists()){
					new File(basePath+folderName).mkdir();
				}
				wordToHtmlConverter.processDocument(wordDocument);
				// save pictures
				// 保存图片
				List pics = wordDocument.getPicturesTable().getAllPictures();
				if (pics != null) {
					for (int i = 0; i < pics.size(); i++) {
						Picture pic = (Picture) pics.get(i);
						try {
							
							pic.writeImageContent(new FileOutputStream(basePath+folderName+"/" + pic.suggestFullFileName()));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
				Document htmlDocument = wordToHtmlConverter.getDocument();
				out = new ByteArrayOutputStream();
				DOMSource domSource = new DOMSource(htmlDocument);
				StreamResult streamResult = new StreamResult(out);

				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer serializer = tf.newTransformer();
				// 这样转码服务器正常，但是本地调试中文乱码
				// 服务器编码改成utf-8
				serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
				serializer.setOutputProperty(OutputKeys.INDENT, "yes");
				serializer.setOutputProperty(OutputKeys.METHOD, "html");
				serializer.transform(domSource, streamResult);
				writeFile(new String(out.toByteArray()), outPutFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}

	}
	
	private static void writeFile(String content, String path) {  
        FileOutputStream fos = null;  
        BufferedWriter bw = null;  
        try {  
            File file = new File(path);  
            fos = new FileOutputStream(file);  
           
            bw = new BufferedWriter(new OutputStreamWriter(fos,"utf-8"));  
            bw.write(content);  
        } catch (FileNotFoundException fnfe) {  
            fnfe.printStackTrace();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        } finally {  
            try {  
                if (bw != null)  
                    bw.close();  
                if (fos != null)  
                    fos.close();  
            } catch (IOException ie) {  
            }  
        }  
    }  

	@Test
	public void test1() throws Exception {
		convertDocToHtml("d:/test.doc", "d:/testdoc333.html");
		//d://
		//System.out.println(FilenameUtils.getFullPath("d:/test.doc"));
	}
}
