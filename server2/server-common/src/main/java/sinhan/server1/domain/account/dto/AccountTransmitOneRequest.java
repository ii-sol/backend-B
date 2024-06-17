package sinhan.server1.domain.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountTransmitOneRequest {
    private String sendAccountNum;
    private String receiveAccountNum;
    private int amount;
    private String message;
}