package sinhan.server1.domain.invest.controller;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server1.domain.invest.service.StockService;
import sinhan.server1.global.utils.ApiResult;

@RestController
public class StockController {
    StockService stockService;
    StockController(StockService stockService){
        this.stockService = stockService;
    }
    //전체 종목 조회하기

    //거래 가능 종목 리스트 조회(부모)
    @GetMapping("/my-stock/{co}")
    public ApiResult getMyChildStock(){
        return ApiResult.responseSuccess("asdf");
    }
    //거래 가능 종목 리스트 조회(아이)
    @GetMapping("/my-stocks")
    public ApiResult getMyStock(){
        return ApiResult.responseSuccess("asdf");
    }
    //거래 가능 종목 리스트 추가
    @PostMapping("/my-stocks/{co}")
    public ApiResult setMyStocks(@RequestParam("co") int childOrder, @RequestParam("ticker") String ticker){

        return ApiResult.responseSuccess("sad");
    }
    //거래 가능 종목 리스트 삭제
    @DeleteMapping("/my-stocks/{co}")
    public ApiResult deleteMyChildStock(@PathVariable("co") int childOrder, @RequestParam("ticker") String ticker) {
        return ApiResult.responseSuccess(childOrder);
    }

    //거래 가능 종목 리스트 삭제
    @DeleteMapping("/my-stocks")
    public ApiResult deleteMyStock(@RequestParam("ticker") String ticker){
        return ApiResult.responseSuccess("adfasd");
    }

}
