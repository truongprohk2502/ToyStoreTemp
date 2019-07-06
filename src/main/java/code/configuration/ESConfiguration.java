//package code.configuration;
//
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//import java.net.InetAddress;
//
//@Configuration
//@EnableElasticsearchRepositories(basePackages = "code.repository")
//public class ESConfiguration {
//
//    private String esHost = "localhost";
//
//    private int esPort = 9300;
//
//    private String esClusterName = "tryhard-cluster";
//
//    @Bean
//    public Client client() throws Exception {
//        Settings esSettings = Settings.builder()
//                .put("cluster.name", esClusterName)
//                .build();
//        TransportClient client = new PreBuiltTransportClient(esSettings);
//        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost), esPort));
//        return client;
//    }
//
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
//        return new ElasticsearchTemplate(client());
//    }
//
//}
