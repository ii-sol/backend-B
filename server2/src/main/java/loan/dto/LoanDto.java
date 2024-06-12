package loan.dto;

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

    public LoanDto(Date dueDate, Date createDate, int balance, int status, String title, int amount) {
        this.dueDate = dueDate;
        this.createDate = createDate;
        this.balance = balance;
        this.status = status;
        this.title = title;
        this.amount = amount;
    }
}
