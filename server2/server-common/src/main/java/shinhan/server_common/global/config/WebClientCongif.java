package shinhan.server_common.global.config;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@PropertySource("classpath:config.properties")
public class WebClientCongif {
    HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);
    @Value("${DATA.GO.KR.KEY}")
    private String DataKey;
    @Value("${PROD}")
    private String PROD;
    @Value("${appsecret}")
    private String appsecret;
    @Value("${appkey}")
    private String appkey;
    @Value("${authorization}")
    private String authorization;
    @Value("${dart_key}")
    private String dartKey;
    @Value("${dart_url}")
    private String dartUrl;
    @Bean
    public WebClient webClientP(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("appsecret", appsecret);
        headers.add("appkey", appkey);
        headers.add("authorization", "Bearer " + authorization);
        headers.add("content-type","application/json");
        headers.add("custtype","P");
        headers.add("tr_id","FHKST03010100");
        return WebClient.builder().baseUrl(PROD).defaultHeaders(headers1 -> {headers1.addAll(headers);})
        .build();
    }

    @Bean
    public WebClient webClientF(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("appsecret", appsecret);
        headers.add("appkey", appkey);
        headers.add("authorization", "Bearer " + authorization);
        headers.add("content-type","application/json");
        headers.add("custtype","P");
        headers.add("tr_id","FHKST66430300");
        return WebClient.builder().baseUrl(PROD).defaultHeaders(headers1 -> {headers1.addAll(headers);})
            .build();
    }

    @Bean
    public WebClient webDartClient(){
        return WebClient.builder().baseUrl(dartUrl+"?crtfc_key="+dartKey)
            .build();
    }
}
