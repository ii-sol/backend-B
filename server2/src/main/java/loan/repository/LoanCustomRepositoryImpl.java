package loan.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import loan.dto.LoanDto;
import org.springframework.stereotype.Repository;

@Repository
public class LoanCustomRepositoryImpl implements LoanCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LoanDto> findByChildID(int childId) {
        String query = "SELECT new loan.dto.LoanDto(l.dueDate, l.createDate, l.balance, l.status, l.title, l.amount) " +
            "FROM Loan l WHERE l.childId = :childId";
        return entityManager.createQuery(query, LoanDto.class)
            .setParameter("childId", childId)
            .getResultList();
    }

    @Override
    public void acceptLoan(int loanId) {
        String query = "UPDATE Loan l SET l.status = :status WHERE l.id = :id";
        entityManager.createQuery(query)
            .setParameter("status", "3")
            .setParameter("id", loanId)
            .executeUpdate();
    }

    @Override
    public void refuseLoan(int loanId) {
        String query = "UPDATE Loan l SET l.status = :status WHERE l.id = :id";
        entityManager.createQuery(query)
            .setParameter("status", "5")
            .setParameter("id", loanId)
            .executeUpdate();
    }
}
