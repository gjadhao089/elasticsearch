package com.test.elasticsearch;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.search.suggest.completion.RegexOptions;

public class IndexCreation {

	public static void createIndex(TransportClient client) {
		  IndexResponse response = client.prepareIndex("department","type").get();
          String _index = response.getIndex();
          String _type = response.getType();
          String _id = response.getId();
          long _version = response.getVersion();
          System.out.println("_index"+_index);
          System.out.println("_type"+_type);
          System.out.println("_id"+_id);
          System.out.println("_version"+_version);
	}
	
	public static void deleteIndex(TransportClient client) {
		DeleteIndexRequest request = new DeleteIndexRequest("department");
		DeleteIndexResponse deleteResponse = client.admin().indices().delete(request).actionGet();
		System.out.println("DeleteReponse"+deleteResponse.isAcknowledged());
	}
	
	public static void createDepartmentSchema(TransportClient client) {
		PutMappingRequest request = new PutMappingRequest("department");
		Map<String, Object> jsonMap = new HashMap<>();
		Map<String, Object> departmentName = new HashMap<>();
		Map<String, Object> totalEmployee = new HashMap<>();
		Map<String, Object> departmentLocation = new HashMap<>();
		Map<String, Object> departmentLogoName = new HashMap<>();
		departmentName.put("type", "text");
		departmentLocation.put("type", "text");
		departmentLogoName.put("type", "text");
		totalEmployee.put("type", "integer");
		Map<String, Object> properties = new HashMap<>();
		properties.put("departmentName", departmentName);
		properties.put("totalEmployee", totalEmployee);
		properties.put("departmentLocation", departmentLocation);
		properties.put("departmentLogoName", departmentLogoName);
		jsonMap.put("properties", properties);
		request.source(jsonMap); 	
		AcknowledgedResponse putMappingResponse = client.admin().indices().putMapping(request).actionGet();
		System.out.println("putMappingResponse"+putMappingResponse.isAcknowledged());
	}
	
	public static void storeDataInAbove(TransportClient client) {
		JsonBuilder jsonBuilder = new PostDataRequest.JsonBuilder(); 
		Map<String, Object> mapData = new HashMap<>();
		mapData.put("departmentName", "IT");
		mapData.put("totalEmployee", 109);
		mapData.put("departmentLocation", "Mumbai");
		mapData.put("departmentLogoName", "Relience");
		jsonBuilder.addDoc(mapData); 
		jsonBuilder.addDoc("{\"total\":1000}"); 
		PostDataRequest postDataRequest = new PostDataRequest("test-post-data", jsonBuilder); 
		AcknowledgedResponse postDataResponse = client.bulk(postDataRequest);
		
	}

	
	public static void getDocbyId(TransportClient client) {
		GetResponse response = client.prepareGet("department", "_doc", "1").get();
		System.out.println("response "+response.getId());
		System.out.println("response "+response.getSourceAsString());
		
	}
	
}
