package sinhan.server1.domain.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sinhan.server1.domain.tempuser.TempUser;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "account_num", unique = true, nullable = false)
    private String accountNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sn", referencedColumnName = "serial_num", unique = true, nullable = false)
    private TempUser user;

    @Column(name = "balance", nullable = true)
    private Integer balance;

    @Column(name = "status", nullable = false)
    private int status;

    @Builder
    public Account(int id, String accountNum, TempUser user, Integer balance, int status) {
        this.accountNum = accountNum;
        this.user = user;
        this.balance = balance;
        this.status = status;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
