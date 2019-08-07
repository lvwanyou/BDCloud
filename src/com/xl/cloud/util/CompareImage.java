package com.xl.cloud.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.mysql.fabric.xmlrpc.base.Array;

public class CompareImage {
	public static List<String> hitPictureDir=new ArrayList<String>();
	public static String[][] getPX(String args){
		int[] rgb = new int[3];
		
		File file = new File(args);
		BufferedImage bi = null;
		try{
			bi = ImageIO.read(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		
		String[][] list = new String[width][height];
		
		for(int i = minx;i<width;i++){//从原点坐标开始循环获取每个像素点的值
			for(int j = miny;j<height;j++){
				int pixel = bi.getRGB(i, j);//24位RGB，高8位红色，中8位绿色，低八位蓝色
				rgb[0] = (pixel & 0xff0000) >> 16;//右移16位并 取最后8位即原来的高八位 即红色
				rgb[1] = (pixel & 0xff00) >> 8;//同理 绿色
				rgb[2] = (pixel & 0xff);//蓝色
				
				list[i][j] = rgb[0]+","+rgb[1]+","+rgb[2];
			}
		}
		
		return list;
	}
	
	

//2.0 (第一个参数对应的图片长宽必须小于等于第二个参数的图片)
	public static double compareImage2(String imgPath1,String imgPath2){
		String[] images = {imgPath1,imgPath2};
		if(images.length == 0){
			System.out.println("Usage>java BMPLoader ImageFile.bmp");
			System.exit(0);
		}
		
		File file1 = new File(images[0]);
		File file2 = new File(images[1]);
		BufferedImage bi = null;
		BufferedImage bi2 = null;
		try{
			bi = ImageIO.read(file1);
			bi2 = ImageIO.read(file2);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		int Awidth = bi.getWidth();      //小
		int Aheight = bi.getHeight();
		int Bwidth = bi2.getWidth();     //大
		int Bheight = bi2.getHeight();
		
		String[][] list1 = getPX(images[0]);
		String[][] list2 = getPX(images[1]);
		int similarity = 0;
		int difference = 0;
		double simi_degree = 0;
		double limit = 0;
		
		//获取原图四个角的点像素
		String[] Bvalue1 = list1[0][0].toString().split(",");
		String[] Bvalue2 = list1[0][Aheight-1].toString().split(",");
		String[] Bvalue3 = list1[Awidth-1][0].toString().split(",");
		String[] Bvalue4 = list1[Awidth-1][Aheight-1].toString().split(",");
		
		for(int i=0;i<=Bwidth-Awidth;i++){
			for(int j=0;j<=Bheight-Aheight;j++){
				try{
					//获取对比图四个角点像素
					String[] Avalue1 = list2[i][j].toString().split(",");
					String[] Avalue2 = list2[i][j+Aheight-1].toString().split(",");
					String[] Avalue3 = list2[i+Awidth-1][j].toString().split(",");
					String[] Avalue4 = list2[i+Awidth-1][j+Aheight-1].toString().split(",");
					//如果四个点的坐标都在差异范围内					
					if(Math.abs(Integer.parseInt(Avalue1[0])-Integer.parseInt(Bvalue1[0]))<5
							&& Math.abs(Integer.parseInt(Avalue2[0])-Integer.parseInt(Bvalue2[0]))<5
							&& Math.abs(Integer.parseInt(Avalue3[0])-Integer.parseInt(Bvalue3[0]))<5
							&& Math.abs(Integer.parseInt(Avalue4[0])-Integer.parseInt(Bvalue4[0]))<5){//进行第二次对比

						for(int m=0;m<Awidth;m++){
							for(int n=0;n<Aheight;n++){
								try{
									String[] Avalue = list2[i+m][j+n].toString().split(",");
									String[] Bvalue = list1[m][n].toString().split(",");
										
									if(Math.abs(Integer.parseInt(Avalue[0])-Integer.parseInt(Bvalue[0]))<5){
										similarity++;
									}else{
										difference++;
									}
								}catch(Exception e){
									continue;
								}
							}
						}
						
						try{
							simi_degree = Double.parseDouble(similarity+"")/Double.parseDouble((similarity+difference)+"");
						}catch(Exception e){
							simi_degree=0;
						}
						
						if(difference==0) simi_degree=1;

						if(simi_degree>0.1){
							if(simi_degree>limit) 
								limit=simi_degree;
						}else{
							similarity=0;
							difference=0;
							simi_degree=0;
						}
						
					}
					
				}catch(Exception e){
					continue;
				}
			}
		}
//		System.out.println("*********************************************************");
//		System.out.println("相似程度："+limit);
//		System.out.println("*********************************************************");
//		System.out.println("");
		return limit;
	}

	//1.0 只能对比完全相同的图片
	public static double compareImage(String imgPath1,String imgPath2){
		String[] images = {imgPath1,imgPath2};
		if(images.length == 0){
			System.out.println("Usage>java BMPLoader ImageFile.bmp");
			System.exit(0);
		}
		
		String[][] list1 = getPX(images[0]);
		String[][] list2 = getPX(images[1]);
		int similarity = 0;
		int difference = 0;
		int i = 0,j = 0;
		for(String[] strings:list1){
			if((i+1)==list1.length){
				continue;
			}
			for(int m=0;m<strings.length;m++){
				try{
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					
					for(int n = 0;n<value2.length;n++){
						for(int k = 0;k<3;k++){
							if(Math.abs(Integer.parseInt(value1[k])-Integer.parseInt(value2[k]))<5){
								similarity++;
							}else{
								difference++;
							}
						}
					}
//尝试更精确的对比色值，未实现					
				/*for(int n = 0;n<value2.length;n++){
					int differCount=0;
					for(int k = 0;k<3;k++){
						if(Math.abs(Integer.parseInt(value1[k])-Integer.parseInt(value2[k]))>5){
							differCount++;
						}
					}
					if(differCount>=2){
						similarity++;
					}else{
						difference++;
					}
				}*/
										
					
				}catch(RuntimeException e){
					continue;
				}
				j++;
			}
			i++;
		}
		//交换再比较（？）
		list1 = getPX(images[1]);
		list2 = getPX(images[0]);
		i=0;
		j=0;
		for(String[] strings:list1){
			if((i+1)==list1.length){
				continue;
			}
			for(int m=0;m<strings.length;m++){
				try{
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					
					
					for(int n = 0;n<value2.length;n++){
						for(int k = 0;k<3;k++){
							if(Math.abs(Integer.parseInt(value1[k])-Integer.parseInt(value2[k]))<5){
								similarity++;
							}else{
								difference++;
							}
						}
					}
					
					/*for(int n = 0;n<value2.length;n++){
						int differCount=0;
						for(int k = 0;k<3;k++){
							if(Math.abs(Integer.parseInt(value1[k])-Integer.parseInt(value2[k]))>5){
								differCount++;
							}
						}
						if(differCount>=2){
							difference++;
						}else{
							similarity++;
						}
					}*/
					
				}catch(RuntimeException e){
					continue;
				}
				j++;
			}
			i++;
		}
		
		double simi_degree;
		try{
			simi_degree = Double.parseDouble(similarity+"")/Double.parseDouble((similarity+difference)+"");
		}catch(Exception e){
			simi_degree=0;
		}
		
		if(difference==0) simi_degree=1;
		
//		System.out.println("相似像素数量："+similarity);
//		System.out.println("不相似像素数量："+difference);
//		System.out.println("相似程度："+simi_degree);
		
		return simi_degree;
	}
	
	//与目标文件夹对比
	public static void compare(String picture,String filePath){
		
		hitPictureDir=new ArrayList<>();
		System.out.println("11111111111111111");
		File picturesFile = new File(filePath);
		if (!picturesFile.exists()) {
			picturesFile.mkdir();
			System.out.println("yo");
		}
		
		File filelist = new File(filePath);
		String[] file = filelist.list();
		File temp = null;
		
		for(int i=0;i<file.length;i++){
			temp = new File(filePath+file[i]);
			//System.out.println("test:"+temp);
			if(temp.isFile()){
				String path = filePath+file[i];
//				double simi = Demo.compareImage(picture,path);
				double simi2 = CompareImage.compareImage2(picture,path);

				System.out.println("对比图片："+path);
				System.out.println("相似度："+simi2);
				System.out.println();
				
				if(simi2 > 0.2){
					hitPictureDir.add(path);
					System.out.println("***************************");
					System.out.println("相似图片："+hitPictureDir);
					System.out.println("相似度："+simi2);
					System.out.println("***************************");
				}
			}
		}
		System.out.println(hitPictureDir);
	}
	
	public static void main(String[] args){
//		Demo.compareImage("H:\\test6.png", "H:\\test5.png");
		CompareImage.compare("G:/b/hy1.PNG", "G:/a/");//与文件夹内文件对比
//		Demo.compareImage2("H:\\test6.png", "H:\\test5.png");//两张图片对比
	}
}
