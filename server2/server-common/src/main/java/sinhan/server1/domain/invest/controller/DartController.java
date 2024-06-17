package sinhan.server1.domain.invest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server1.domain.invest.dto.StockFindCurrentResponseDTO;
import sinhan.server1.domain.invest.dto.StockFindDetailResponseDTO;
import sinhan.server1.domain.invest.service.StockService;
import sinhan.server1.domain.utils.ApiResult;

@RestController
public class DartController {
    StockService stockService;
    @Autowired
    DartController(StockService stockService){
        this.stockService = stockService;
    }
    @GetMapping("/stocks")
    public ApiResult getStocks(){
        return ApiResult.responseSuccess("asd");
    }

    //개별 종목 조회하기
    @GetMapping("/stocks/{ticker}")
    public ApiResult getStock(@PathVariable("ticker") String ticker){
        System.out.println(ticker);
        StockFindCurrentResponseDTO result = stockService.getStockCurrent(ticker);
        return ApiResult.responseSuccess(result);
    }
    @GetMapping("/stocks/{ticker}/{year}")
    public ApiResult getStock(@PathVariable("ticker") String ticker,@PathVariable("year") String year){
        StockFindDetailResponseDTO result = stockService.getStockDetail(ticker,year);
            return ApiResult.responseSuccess(result);
        }


}
