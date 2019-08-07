package com.xl.cloud.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.stereotype.Service;

import com.xl.cloud.bean.EmailStatus;
import com.xl.cloud.common.Global;
import com.xl.cloud.dao.SqlDao;

 /**
  * Created by suny on 2017/8/22.
  */
 public class EsUpdate {
     Log log = LogFactory.getLog(getClass());
     public static void  updateTest(String index,String type,String id,String key,String value) throws IOException{
    	 TransportClient ts = EsClient.getClient();
    	// 方法一:创建一个UpdateRequest,然后将其发送给client.
    	 XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
         UpdateRequest uRequest = new UpdateRequest();
         uRequest.index(index);
         uRequest.type(type);
         uRequest.id(id);
         uRequest.refresh(true);
         //
         try {
			uRequest.doc(jsonBuild.startObject().field(key,value).endObject());
			ts.update(uRequest).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     
     public static void  updateTest(String index,String type,String id,Map<String, String> map) throws IOException{
    	 TransportClient ts = EsClient.getClient();
    	// 方法一:创建一个UpdateRequest,然后将其发送给client.
    	 XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
         UpdateRequest uRequest = new UpdateRequest();
         uRequest.index(index);
         uRequest.type(type);
         uRequest.id(id);
         uRequest.refresh(true);
         //
         try {
			/*
			*/
        	Set<String> keySet = map.keySet();
        	XContentBuilder startObject = jsonBuild.startObject();
        	for (String key : keySet) {
        		String value=map.get(key);
        		startObject.field(key, value);
			}
        	uRequest.doc(startObject.endObject());
        	ts.update(uRequest).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     
     public static void main(String[] args) throws IOException {
    	 
    	 updateTest("doc","docType","AV52SxXC2WJ57RRG1Rav","favoriteLabel","我是是是");
 	}
     
     @Test
     public void test1()throws Exception {
    	 Map<String, String> map=new HashMap<>();
 //   	 map.put("starFlag","0");
    	 map.put("favoriteLabel","dognhaodaongao");
    	 //类型
    	 //map.put("starType","走私");
    	 String[] values={
    		"AV52SxXC2WJ57RRG1Rav","AV52S7dP6dEQCf_fkS1P",
    		"AV54-Yom6dEQCf_fkXcd"
    	 };
    	 for (String id : values) {
			
    		 updateTest("doc","docType",id,map);
		}
     }
     
     @Test
     public void test2()throws Exception {
    	 updateTest("doc","docType","AV4IsAVofDHmfbSxFCwi","starFlag","1");
    	 updateTest("doc","docType","AV4IzBpPDoU7JZ3rpMOG","starFlag","1");
     }
    
 }
