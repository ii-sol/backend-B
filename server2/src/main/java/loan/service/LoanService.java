package loan.service;

import java.util.List;
import loan.dto.LoanDto;
import loan.repository.LoanRepository;
import loan.entity.Loan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<LoanDto> getLoanByChildId(int childId) {
        return loanRepository.findByChildID(childId);
    }

    @Transactional
    public void saveLoan(LoanDto loanDto) {
        Loan loan = new Loan(loanDto);
        loanRepository.save(loan);
    }

    public void accecptLoan(int loanId) {
        loanRepository.acceptLoan(loanId);
    }

    public void refuseLoan(int loanId) {
        loanRepository.refuseLoan(loanId);
    }
}
