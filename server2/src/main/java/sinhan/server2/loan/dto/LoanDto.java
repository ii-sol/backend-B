package sinhan.server2.loan.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {

    int id;

    Date dueDate;

    Date createDate;

    int period;

    int childId;

    int parentId;

    String parentName;

    float interestRate;

    int amount;

    int balance;

    int status;

    String title;

    String message;

    public LoanDto(int id, Date dueDate, Date createDate, int balance, int status, String title, int amount, float interestRate) {
        this.interestRate = interestRate;
        this.id = id;
        this.dueDate = dueDate;
        this.createDate = createDate;
        this.balance = balance;
        this.status = status;
        this.title = title;
        this.amount = amount;
    }

    public LoanDto(int id, Date dueDate, Date createDate, int period, int childId, int parentId, float interestRate, int amount,
        int status, String title, String message) {
        this.id = id;
        this.dueDate = dueDate;
        this.createDate = createDate;
        this.period = period;
        this.childId = childId;
        this.parentId = parentId;
        this.interestRate = interestRate;
        this.amount = amount;
        this.balance = amount;
        this.status = status;
        this.title = title;
        this.message = message;

    }

    public LoanDto(int id, Date dueDate, Date createDate, int period, int childId, int parentId, float interestRate, int amount, int balance, int status, String title, String message) {
        this.id = id;
        this.dueDate = dueDate;
        this.createDate = createDate;
        this.period = period;
        this.childId = childId;
        this.parentId = parentId;
        this.parentName = "엄마";
        this.interestRate = interestRate;
        this.amount = amount;
        this.balance = balance;
        this.status = status;
        this.title = title;
        this.message = message;
    }
}
