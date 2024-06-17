package sinhan.server1.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sinhan.server1.domain.account.entity.AccountHistory;
import sinhan.server1.domain.tempuser.TempUser;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistoryFindAllResponse {
    private String senderName;
    private String recieverName;
    private int amount;
    private int messageCode;
    private LocalDateTime createDate;

    public static AccountHistoryFindAllResponse of(AccountHistory accountHistory, TempUser sender, TempUser reciever ){
        return AccountHistoryFindAllResponse.builder()
                .senderName(sender.getName())
                .recieverName(reciever.getName())
                .amount(accountHistory.getAmount())
                .messageCode(accountHistory.getMessageCode())
                .createDate(accountHistory.getCreateDate())
                .build();
    }
}
