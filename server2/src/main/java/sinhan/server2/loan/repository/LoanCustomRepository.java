package sinhan.server2.loan.repository;

import java.util.List;
import sinhan.server2.loan.dto.LoanDto;

public interface LoanCustomRepository {
    List<LoanDto> findByChildID(int childId);

    void acceptLoan(int loanId);

    void refuseLoan(int loanId);

    LoanDto findLoanById(int loanId);
}
