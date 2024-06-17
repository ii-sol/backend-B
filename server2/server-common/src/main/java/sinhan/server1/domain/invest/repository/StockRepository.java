package sinhan.server1.domain.invest.repository;

import org.springframework.stereotype.Repository;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import sinhan.server1.domain.invest.entity.StockDivideOutput;
import sinhan.server1.domain.invest.entity.StockDuraionPriceOutput;
import sinhan.server1.domain.invest.entity.StockFianceResponseOutput;

@Repository
public class StockRepository {
    @Autowired
    WebClient webClientP;
    @Autowired
    WebClient webClientF;
    @Autowired
    WebClient webDartClient;

    public StockDuraionPriceOutput getApiCurrentPrice(String ticker,String year){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date endDate = new Date();
        Date startDate = new Date();

        String endDateString;
        String startDateString;
        String fid_period_div_code;
        if(year.equals("0")){
            startDate.setMonth(startDate.getMonth()-1);
            fid_period_div_code = "D";
        }else if(year.equals("1")){
            startDate.setYear(startDate.getYear()-1);
            fid_period_div_code = "W";
        } else {
            startDate.setYear(startDate.getYear()-1);
            fid_period_div_code = "W";
        }
        endDateString = simpleDateFormat.format(endDate);
        startDateString = simpleDateFormat.format(startDate);

        String uri = "/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice";
        Mono<StockDuraionPriceOutput> mono = webClientP.get().uri(
                uriBuilder -> {
                    return uriBuilder.path(uri)
                        .queryParam("fid_cond_mrkt_div_code","J")
                        .queryParam("fid_input_iscd",ticker)
                        .queryParam("fid_input_date_1",startDateString)
                        .queryParam("fid_input_date_2",endDateString)
                        .queryParam("fid_period_div_code",fid_period_div_code)
                        .queryParam("fid_org_adj_prc",0).build();
                })
            .retrieve()
//            .onStatus(HttpStatus::is5xxServerError, response -> {
//                // 5xx 서버 오류 처리
//                return Mono.error(new Exception("서버에서 오류 발생: " + response.statusCode()));
//            })
            .bodyToMono(StockDuraionPriceOutput.class)
            .onErrorResume(Exception.class, error -> {
                // 예외 발생 시 대체 처리
                System.err.println("오류 발생: " + error.getMessage());
                return Mono.empty(); // 또는 기본값 등을 반환할 수 있음
            });
//            .subscribe(
//                price -> System.out.println("현재 주가: " + price),
//                error -> System.err.println("에러 발생: " + error.getMessage())
//            );
        return mono.block();
//        mono.subscribe(
//            price -> System.out.println("현재 주가: " + price),
//            error -> System.err.println("에러 발생: " + error.getMessage())
//        );
//        System.out.println(mono);
//        return mono;
    }

    public StockFianceResponseOutput getApiFiance(String ticker){
        String uri = "/uapi/domestic-stock/v1/finance/financial-ratio";
        Mono<StockFianceResponseOutput> mono;
        return webClientF.get().uri(
                uriBuilder -> {
                    URI build = uriBuilder.path(uri)
                        .queryParam("fid_cond_mrkt_div_code", "J")
                        .queryParam("fid_input_iscd", ticker)
                        .queryParam("fid_div_cls_code", "0").build();
                    System.out.println(build);
                    return uriBuilder.build();
                })
            .retrieve().bodyToMono(StockFianceResponseOutput.class).block();
//        mono.subscribe(
//            price -> System.out.println("현재 주가: " + price),
//            error -> System.err.println("에러 발생: " + error.getMessage())
//        );
//        System.out.println(mono);
//        return mono;
    }

    public StockDivideOutput getApiDivide(String ticker){
        return webDartClient.get().uri(
            uriBuilder1 -> {
                UriBuilder uriBuilder =
                    uriBuilder1.path("")
                        .queryParam("corp_code","00126380")
                        .queryParam("bsns_year","2023")
                        .queryParam("reprt_code","11011")
                    ;
                return uriBuilder.build();
            }).retrieve().bodyToMono(StockDivideOutput.class).block();
//        mono.subscribe(
//            price -> System.out.println("현재 주가: " + price),
//            error -> System.err.println("에러 발생: " + error.getMessage())
//        );
//        System.out.println(mono);
//        return mono;
    }

}
