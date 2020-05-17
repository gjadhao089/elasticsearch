package com.test.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;

public class TaskRunner {

	public static void main(String[] arg) throws UnknownHostException {
		try {
            // create client for localhost es
			Settings settings = Settings.builder()
			        .put("cluster.name", "elasticsearch").build();
            TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("vl-pun-bco-dv26.bmc.com"), 9200));      

        // to create the index    
        IndexCreation.createIndex(client);

        //to delete index
        IndexCreation.deleteIndex(client);
        
        IndexCreation.createDepartmentSchema(client);
        
        IndexCreation.storeDataInAbove(client);
        
        IndexCreation.getDocbyId(client);
           
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
}
