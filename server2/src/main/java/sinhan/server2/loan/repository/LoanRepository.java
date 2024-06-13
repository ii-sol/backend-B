package sinhan.server2.loan.repository;

import sinhan.server2.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long>, LoanCustomRepository {
}
