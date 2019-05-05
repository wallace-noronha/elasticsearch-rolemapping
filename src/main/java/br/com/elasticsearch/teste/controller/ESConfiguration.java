package br.com.elasticsearch.teste.controller;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfiguration {

	@Bean
	public RestHighLevelClient restClientHighLevel() {
	RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
			new HttpHost("localhost", 9200, "http")).setHttpClientConfigCallback(new HttpClientConfigCallback() {
				public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
		        	CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		    		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "changeme"));
		            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
		            return httpClientBuilder;
        }}));
		return client;
	}
}
