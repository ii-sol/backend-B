package sinhan.server1.domain.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.account.dto.AccountFindOneResponse;
import sinhan.server1.domain.account.dto.AccountHistoryFindAllResponse;
import sinhan.server1.domain.account.dto.AccountTransmitOneRequest;
import sinhan.server1.domain.account.dto.AccountTransmitOneResponse;
import sinhan.server1.domain.account.service.AccountService;
import sinhan.server1.global.utils.ApiUtils;

import java.util.List;

import static sinhan.server1.global.utils.ApiUtils.success;

@RestController
@RequestMapping("/account")
@Slf4j
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService; // 생성자 주입

//    //계좌 개별 조회하기
//    @GetMapping("{status}")
//    public ApiUtils.ApiResult findAccount(TempUser tempUser, @PathVariable("status") Integer status){
//        AccountFindOneResponse response = accountService.findAccount(tempUser.getSerialNumber(), status);
//        return success(response);
//    }
//
//    //이체하기 - 공통 : AccountNum받아서 ?? 그럼 이체하기는 공통 모듈에 넣어두고 각각 부모모듈에 있는 부모 account DB에다가도 요청하고 아이모듈에 있는 아이 account DB에다가도 요청하는건가?
//    @PostMapping("transmit")
//    public ApiUtils.ApiResult transmitMoney(TempUser tempUser, @RequestBody AccountTransmitOneRequest transferRequest){
//        AccountTransmitOneResponse response = accountService.transferMoney(transferRequest);
//        return success(response);
//    }
//
//    //계좌 내역 보기 => 공통 : response 형태 이게 맞나 모르겠
//    @GetMapping("history")
//    public ApiUtils.ApiResult findAccountHistory(TempUser tempUser, @RequestParam Integer year, @RequestParam Integer month, @RequestParam Integer status){
//        List<AccountHistoryFindAllResponse> response = accountService.findAccountHistory(tempUser.getSerialNumber(), year, month, status);
//        return success(response);
//    }

    //계좌 개별 조회하기
    @GetMapping("")
    public ApiUtils.ApiResult findAccount(@RequestParam("status") Integer status, @RequestParam("sn") Long sn){
        AccountFindOneResponse response = accountService.findAccount(sn, status);
        return success(response);
    }

    //이체하기 - 공통 : AccountNum받아서 ?? 그럼 이체하기는 공통 모듈에 넣어두고 각각 부모모듈에 있는 부모 account DB에다가도 요청하고 아이모듈에 있는 아이 account DB에다가도 요청하는건가?
    @PostMapping("transmit")
    public ApiUtils.ApiResult transmitMoney(@RequestBody AccountTransmitOneRequest transferRequest){
        AccountTransmitOneResponse response = accountService.transferMoney(transferRequest);
        return success(response);
    }

    //계좌 내역 보기 => 공통 : response 형태 이게 맞나 모르겠
    @GetMapping("history")
    public ApiUtils.ApiResult findAccountHistory(@RequestParam("sn") Long sn, @RequestParam("year") Integer year, @RequestParam("month") Integer month, @RequestParam("status") Integer status){
        List<AccountHistoryFindAllResponse> response = accountService.findAccountHistory(sn, year, month, status);
        return success(response);
    }
}
