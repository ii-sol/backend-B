package sinhan.server1.domain.invest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sinhan.server1.global.utils.ApiResult;

public class InvestController {
    //투자 거래 내역 조회하기(부모)
    @GetMapping("/invest/history/{co}")
    public ApiResult getInvestHistory(@PathVariable("co") int childOrder){
        return null;
    }
    //투자 거래 내역 조회하기(아이)
    @GetMapping("/invest/history")
    public ApiResult getInvestHistory(){
        return null;
    }
    //포트폴리오 조회하기(부모)
    @GetMapping("/invest/portfolio/{co}")
    public ApiResult getInvestPortfolio(@PathVariable("co") int childOrder){
        return null;
    }
    //포트폴리오 조회하기(아이)
     @GetMapping("/invest/portfolio")
     public ApiResult getInvestPortfolio(){
        return null;
    }
    //종목 판매/구매하기
    @PostMapping("/invest")
    public ApiResult invest(){
        return null;
    }
}
