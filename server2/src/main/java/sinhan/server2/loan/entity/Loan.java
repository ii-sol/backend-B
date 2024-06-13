package sinhan.server2.loan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import sinhan.server2.loan.dto.LoanDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    Date dueDate;

    @Column(nullable = false)
    Date createDate;

    int period;

    int childId;

    int parentId;

    float interestRate;

    int amount;

    int balance;

    int status;

    String title;

    String message;

    public Loan(LoanDto loanDto) {
        this.createDate = new Date();
        this.dueDate = new Date(System.currentTimeMillis() + (long)loanDto.getPeriod() * 30 * 24 * 60 * 60 * 1000);
        this.period = loanDto.getPeriod();
        this.childId = loanDto.getChildId();
        this.parentId = loanDto.getParentId();
        this.interestRate = loanDto.getInterestRate();
        this.amount = loanDto.getAmount();
        this.balance = loanDto.getAmount();
        this.status = 1;
        this.title = loanDto.getTitle();
        this.message = loanDto.getMessage();
    }
}
