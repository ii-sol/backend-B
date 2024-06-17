package sinhan.server1.domain.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sinhan.server1.domain.tempuser.TempUser;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "sender_account_num", nullable = false)
    private String senderAccountNum;

    @Column(name = "receiver_account_num", nullable = false)
    private String receiverAccountNum;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "message_code", nullable = true)
    private Byte messageCode;

    @Builder
    public AccountHistory(String senderAccountNum, String receiverAccountNum, int amount, LocalDateTime createDate, Byte messageCode) {
        this.senderAccountNum = senderAccountNum;
        this.receiverAccountNum = receiverAccountNum;
        this.amount = amount;
        this.createDate = createDate;
        this.messageCode = messageCode;
    }
}
