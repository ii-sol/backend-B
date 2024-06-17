package sinhan.server1.global.utils.account;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sinhan.server1.domain.account.entity.Account;
import sinhan.server1.domain.account.entity.AccountHistory;
import sinhan.server1.domain.account.repository.AccountHistoryRepository;
import sinhan.server1.domain.account.repository.AccountRepository;
import sinhan.server1.global.exception.CustomException;
import sinhan.server1.global.exception.ErrorCode;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountUtils {
    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    //받은 계좌객체로 송금하기
    public void transferMoneyByAccount(Account senderAccount, Account recieverAccount, Integer amount, Integer messageCode){

        //sender와 reciever 계좌 잔액 update
        updateBalance(senderAccount, senderAccount.getBalance()-amount);
        updateBalance(recieverAccount, recieverAccount.getBalance()+amount);

        //계좌 내역에 저장하기
        AccountHistory newAccountHistory = AccountHistory.builder()
                .senderAccountNum(senderAccount.getAccountNum())
                .receiverAccountNum(recieverAccount.getAccountNum())
                .amount(amount)
                .createDate(LocalDateTime.now())
                .messageCode(messageCode)
                .build();

        accountHistoryRepository.save(newAccountHistory);
    }

    //계좌 번호로 계좌 조회
    public Account getAccount(String accountNum){
        return accountRepository.findByAccountNum(accountNum)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    //계좌 잔액 update 후 레포지토리에 update
    private void updateBalance(Account account, int balance){
        account.setBalance(balance);
        accountRepository.save(account);
    }
}
