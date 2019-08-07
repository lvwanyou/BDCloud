package com.xl.cloud.action;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.*;

public class readandwrite {
    public static void main(String[] args) throws IOException { 
        String [] str = {"省","市","区","街","路","里","幢","村","室","园","苑","巷","号"};
        String inString = "";
        String tmpString = "";
        File inFile = new File("C://in.csv"); // 读取的CSV文件
        File outFile = new File("C://outtest.csv");//输出的CSV文
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            CsvReader creader = new CsvReader(reader, ',');
            CsvWriter cwriter = new CsvWriter(writer,',');
            while(creader.readRecord()){
                inString = creader.getRawRecord();//读取一行数据
                for(int i = 0;i < str.length;i++){
                    tmpString = inString.replace(str[i], "," + str[i] + ",");
                    inString = tmpString;
                }
                //第一个参数表示要写入的字符串数组，每一个元素占一个单元格，第二个参数为true时表示写完数据后自动换行
            cwriter.writeRecord(inString.split(","), true);
            //注意，此时再用cwriter.write(inString)方法写入数据将会看到只往第一个单元格写入了数据，“，”没起到调到下一个单元格的作用
            //如果用cwriter.write(String str)方法来写数据，则要用cwriter.endRecord()方法来实现换行
            //cwriter.endRecord();//换行
            cwriter.flush();//刷新数据
            }  
            creader.close();
            cwriter.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
