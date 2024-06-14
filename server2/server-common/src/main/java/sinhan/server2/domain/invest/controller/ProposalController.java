package sinhan.server2.domain.invest.controller;


import java.awt.event.WindowFocusListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server2.domain.utils.ApiResult;

@RestController
@Slf4j
public class ProposalController {
    //투자 제안 내역 달변 조회하기(부모)
    @GetMapping("/proposal/invest/history/{co}")
    public ApiResult getChildInvestProposal(@RequestParam("year") int year,@RequestParam("month") int month,@PathVariable("co") int childOrder){
        ApiResult<Integer> stringApiResult = ApiResult.responseSuccess(childOrder);
        return stringApiResult;
    }
    //투자 제안 내역 달변 조회하기(아이)
    @GetMapping("/proposal/invest/history")
    public ApiResult getInvestProposal(@RequestParam("year") int year,@RequestParam("month") int month){
        return ApiResult.responseSuccess(year);
    }
    //종목 제안 내역 달별 조회하기(부모)
    @GetMapping("/proposal/stock/history/{co}")
    public ApiResult getChildStockProposal(@RequestParam("year") int year,@RequestParam("month") int month,@PathVariable("co") int childOrder){
        return ApiResult.responseSuccess(year);
    }
    //종목 제안 내역 달별 조회하기(아이)
    @GetMapping("/proposal/stock/history")
    public ApiResult getStockProposal(@RequestParam("year") int year,@RequestParam("month") int month){
        return ApiResult.responseSuccess(year);
    }

    //전체 제안 내역 달별 조회하기(부모)
    @GetMapping("/proposal/history/{co}")
    public ApiResult getChildProposal(@RequestParam("year") int year,@RequestParam("month") int month,@PathVariable("co") int childOrder){
        return ApiResult.responseSuccess(year);
    }
    //전체 제안 내역 달별 조회하기(아이)
    @GetMapping("/proposal/history")
    public ApiResult getProposal(@RequestParam("year") int year,@RequestParam("month") int month){
        return ApiResult.responseSuccess(year);
    }
    //종목 제안하기
    @PostMapping("/proposal/stock/{po}")
    public ApiResult proposeStock(@PathVariable("po") int ParentOrder){
        return null;
    }
    //투자 제안하기
    @PostMapping("/proposal/invest/{po}")
    public ApiResult proposeInvest(@PathVariable("po") int ParentOrder){
        return null;
    }
    //투자제안 상세 조회하기
    @GetMapping("/proposal/invest/{proposalId}")
    public ApiResult getProposeInvestDetail(@PathVariable("proposalId") int proposalId){
        return null;
    }
    //투자 제안 수락/거절
//    @PostMapping("/proposal/invest/{proposalId}")
//    public ApiResult responseInvestPropose(@PathVariable("proposalId") int proposalId ){
//        return null;
//    }
    //종목 제안 수락/거절
    @PostMapping("/proposal/stock/{proposalId}")
    public ApiResult responseStockPropose(@PathVariable("proposalId") int proposalId ){
        return null;
    }
}
