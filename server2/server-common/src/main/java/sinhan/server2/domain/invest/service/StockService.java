package sinhan.server2.domain.invest.service;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import sinhan.server2.domain.invest.dto.StockFindDetailResponseDTO;
import sinhan.server2.domain.invest.entity.StockDivideOutput;
import sinhan.server2.domain.invest.entity.StockDuraionPriceOutput;
import sinhan.server2.domain.invest.entity.StockFianceResponseOutput;
import sinhan.server2.domain.invest.repository.StockRepository;

@Service
public class StockService {
    StockRepository stockRepository;
    @Autowired
        WebClient webClientP;
    @Autowired
        WebClient webClientF;
    @Autowired
        WebClient webDartClient;

    StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    public StockFindDetailResponseDTO getStocks(String ticker,int year){
        Mono<StockDuraionPriceOutput> stockDuraionPriceOutput = getApiCurrentPrice(ticker,year);
        Mono<StockFianceResponseOutput> stockFianceResponseOutput = getApiFiance(ticker);
        Mono<StockDivideOutput> stockDivideOutputMono = getApiDivide(ticker);
        stockDuraionPriceOutput.subscribe(
            price -> System.out.println("현재 주가: " + price),
            error -> System.err.println("에러 발생: " + error.getMessage())
        );
        stockFianceResponseOutput.subscribe(
            price -> System.out.println("현재 주가: " + price),
            error -> System.err.println("에러 발생: " + error.getMessage())
        );
        stockDivideOutputMono.subscribe(
            price -> System.out.println("현재 주가: " + price),
            error -> System.err.println("에러 발생: " + error.getMessage())
        );
//        System.out.println(stockDivideOutputMono.subscribe());
//        System.out.println(stockDivideOutputMono.block());
//        System.out.println(stockFianceResponseOutput.subscribe().toString());
//        System.out.println(stockFianceResponseOutput.block());
//        System.out.println(stockDivideOutputMono);
//        System.out.println(stockDuraionPriceOutput.block());
//        System.out.println(stockDuraionPriceOutput.block());
//        System.out.println(stockFianceResponseOutput.block());
//        System.out.println(stockDivideOutputMono.block());
        return null;
//        return StockFindDetailResponseDTO.builder()
//            .charts(Objects.requireNonNull(stockDuraionPriceOutput.block(),"char null 값입니다.").getOutput2())
//            .ROE(Objects.requireNonNull(stockFianceResponseOutput.block()).getOutput().get(0).getRoe())
//            .PSR(getPSR(Objects.requireNonNull(stockDuraionPriceOutput.block()),
//                Objects.requireNonNull(stockFianceResponseOutput.block())))
//            .PBR(Objects.requireNonNull(stockDuraionPriceOutput.block()).getOutput1().getPbr())
//            .changeSign(Objects.requireNonNull(stockDuraionPriceOutput.block()).getOutput1().getChangeSign())
//            .changePrice(Objects.requireNonNull(stockDuraionPriceOutput.block()).getOutput1().getChangePrice())
//            .changeRate(Objects.requireNonNull(stockDuraionPriceOutput.block()).getOutput1().getChangeRate())
//            .marketCapitalization(
//                Objects.requireNonNull(stockDuraionPriceOutput.block()).getOutput1().getMarketCapitalization())
//            .dividendYield(Objects.requireNonNull(stockDivideOutputMono.block()).getList().get(8).getDividendYield())
//            .companyName(Objects.requireNonNull(stockDuraionPriceOutput.block()).getOutput1().getCompanyName())
//            .currentPrice(Objects.requireNonNull(stockDuraionPriceOutput.block()).getOutput1().getCurrentPrice())
//            .PER(Objects.requireNonNull(stockDuraionPriceOutput.block()).getOutput1().getPer())
//            .build();
    }


    public Mono<StockDuraionPriceOutput> getApiCurrentPrice(String ticker,int year){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date endDate = new Date();
        Date startDate = new Date();

        String endDateString;
        String startDateString;
        String fid_period_div_code;
        if(year == 0 ){
            startDate.setMonth(startDate.getMonth()-1);
            fid_period_div_code = "D";
        }else if(year == 1){
            startDate.setYear(startDate.getYear()-1);
            fid_period_div_code = "W";
        } else {
            startDate.setYear(startDate.getYear()-1);
            fid_period_div_code = "W";
        }
        endDateString = simpleDateFormat.format(endDate);
        startDateString = simpleDateFormat.format(startDate);

        String uri = "/uapi/domestic-stock/v1/quotations/inquire-price";
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
            .bodyToMono(StockDuraionPriceOutput.class);
        mono.subscribe(
            price -> System.out.println("현재 주가: " + price),
            error -> System.err.println("에러 발생: " + error.getMessage())
        );
        return mono;
//        mono.subscribe(
//            price -> System.out.println("현재 주가: " + price),
//            error -> System.err.println("에러 발생: " + error.getMessage())
//        );
//        System.out.println(mono);
//        return mono;
    }

    public Mono<StockFianceResponseOutput> getApiFiance(String ticker){
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
            .retrieve().bodyToMono(StockFianceResponseOutput.class);
//        mono.subscribe(
//            price -> System.out.println("현재 주가: " + price),
//            error -> System.err.println("에러 발생: " + error.getMessage())
//        );
//        System.out.println(mono);
//        return mono;
    }

    public Mono<StockDivideOutput> getApiDivide(String ticker){
         Mono<StockDivideOutput> mono;
        return webDartClient.get().uri(
            uriBuilder1 -> {
                UriBuilder uriBuilder =
                 uriBuilder1.path("")
                    .queryParam("corp_code","00126380")
                    .queryParam("bsns_year","2023")
                    .queryParam("reprt_code","11011")
                    ;
                return uriBuilder.build();
            }).retrieve().bodyToMono(StockDivideOutput.class);
//        mono.subscribe(
//            price -> System.out.println("현재 주가: " + price),
//            error -> System.err.println("에러 발생: " + error.getMessage())
//        );
//        System.out.println(mono);
//        return mono;
    }
    public String getPSR(StockDuraionPriceOutput stockDuraionPriceOutput, StockFianceResponseOutput stockFianceResponseOutput){
        return stockDuraionPriceOutput.getOutput1().getCurrentPrice()*stockFianceResponseOutput.getOutput().get(0).getSps() + "";
    }
}
