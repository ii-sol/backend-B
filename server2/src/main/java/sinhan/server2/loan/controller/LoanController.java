package sinhan.server2.loan.controller;

import java.util.List;
import sinhan.server2.loan.dto.LoanDto;
import sinhan.server2.loan.service.LoanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sinhan.server2.global.utils.ApiUtils.ApiResult;
import sinhan.server2.global.utils.ApiUtils;

@RestController
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/child/loan")
    public ApiResult<List<LoanDto>> getChildLoan() {
        int childId = 1;
        List<LoanDto> loans = loanService.getLoanByChildId(childId);

        for(LoanDto loan : loans) {
            loan.setParentName("엄마");
        }
        return ApiUtils.success(loans);
    }

    @PostMapping("/child/loan/create")
    public ApiResult<String> createChildLoan(@RequestBody LoanDto loan) {
        loan.setChildId(1);
        loan.setInterestRate(4.5F);
        loan.setStatus(1);
        loan.setParentName("엄마");

        loanService.saveLoan(loan);

        return ApiUtils.success("Loan created successfully");
    }

    @PostMapping("/child/loan/accept")
    public ApiResult<String> acceptChildLoan(@RequestParam int loanId) {
        loanService.acceptLoan(loanId);

        return ApiUtils.success("Loan accepted successfully");
    }

    @PostMapping("/child/loan/refuse")
    public ApiResult<String> refuseChildLoan(@RequestParam int loanId) {
        loanService.refuseLoan(loanId);

        return ApiUtils.success("Loan refused successfully");
    }

    @GetMapping("/child/loan/detail/{loanId}")
    public ApiResult<LoanDto> getChildLoan(@PathVariable int loanId) {
        LoanDto loanDto = loanService.findOne(loanId);

        return ApiUtils.success(loanDto);
    }
}
