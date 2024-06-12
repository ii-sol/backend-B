package loan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class Loan {

    @Id @GeneratedValue
    int id;

    @Column(nullable = false)
    Date dueDate;

    @Column(nullable = false)
    Date createDate;

    int childId;

    int ParentId;

    float interestRate;

    int amount;

    int balance;

    int status;

    String title;

    String message;
}
