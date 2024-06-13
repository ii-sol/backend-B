package sinhan.server2.loan.service;

import java.util.List;
import sinhan.server2.loan.dto.LoanDto;
import sinhan.server2.loan.repository.LoanCustomRepository;
import sinhan.server2.loan.repository.LoanRepository;
import sinhan.server2.loan.entity.Loan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {

    private final LoanCustomRepository loanCustomRepository;
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanCustomRepository = loanRepository;
        this.loanRepository = loanRepository;
    }

    public List<LoanDto> getLoanByChildId(int childId) {
        return loanCustomRepository.findByChildID(childId);
    }

    @Transactional
    public void saveLoan(LoanDto loanDto) {
        Loan loan = new Loan(loanDto);
        loanRepository.save(loan);
    }

    public void acceptLoan(int loanId) {
        loanCustomRepository.acceptLoan(loanId);
    }

    public void refuseLoan(int loanId) {
        loanCustomRepository.refuseLoan(loanId);
    }

    public LoanDto findOne(int loanId) {
        return loanCustomRepository.findLoanById(loanId);
    }
}
