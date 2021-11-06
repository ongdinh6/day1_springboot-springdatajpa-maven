package com.tma.demo.configs.elasticsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.tma.demo.repositories")
public class ElasticsearchBeanConfig {
    @Value("${elasticsearch.host}")
    private String esHost;
    @Value("${elasticsearch.port}")
    private int esPort;
    @Value("${elasticsearch.clustername}")
    private String esClusterName;

//    @Bean
//    public Client client() throws Exception {
//        Settings esSettings = Settings.builder()
//                .put("client.transport.sniff", true)
//                .put("cluster.name", esClusterName)
//                .build();
//        TransportClient client = new PreBuiltTransportClient(esSettings);
//        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost), esPort));
//        return client;
//    }

//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
//        return new ElasticsearchTemplate(client());
//    }
}
