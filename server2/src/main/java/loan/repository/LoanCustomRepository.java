package loan.repository;

import java.util.List;
import loan.dto.LoanDto;

public interface LoanCustomRepository {
    List<LoanDto> findByChildID(int childId);

    void acceptLoan(int loanId);

    void refuseLoan(int loanId);
}
