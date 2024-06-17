package sinhan.server1.domain.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.account.dto.AccountFindOneResponse;
import sinhan.server1.domain.account.dto.AccountHistoryFindAllResponse;
import sinhan.server1.domain.account.entity.Account;
import sinhan.server1.domain.account.repository.AccountHistoryRepository;
import sinhan.server1.domain.account.repository.AccountRepository;
import sinhan.server1.domain.tempuser.TempUser;
import sinhan.server1.domain.tempuser.TempUserRepository;
import sinhan.server1.global.exception.CustomException;
import sinhan.server1.global.exception.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final TempUserRepository tempUserRepository;

    //계좌 개별 조회
    public AccountFindOneResponse findAccount(long serialNumber, Integer status) {
        TempUser tempUser = getUser(serialNumber);
        Account findAccount = accountRepository.findByUserAndStatus(tempUser, status);
        return AccountFindOneResponse.from(findAccount);
    }

    //계좌 내역 조
    public List<AccountHistoryFindAllResponse> findAccountHistory(long serialNumber, Integer year, Integer month, Integer status) {
        TempUser tempUser = getUser(serialNumber);
        Account findAccount = accountRepository.findByUserAndStatus(tempUser, status);

        LocalDate start = LocalDate.of(year,month,1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        List<AccountHistoryFindAllResponse> findAccountHistories = accountHistoryRepository.findByUserAndCreateDateBetween(findAccount, startDateTime, endDateTime)
                .stream()
                .map(history -> {
                            Account senderAccount = getAccount(history.getSenderAccountNum());
                            Account recieverAccount = getAccount(history.getReceiverAccountNum());
                            TempUser sender = getUser(senderAccount.getUser().getId());
                            TempUser reciever = getUser(recieverAccount.getUser().getId());

                            return AccountHistoryFindAllResponse.of(history, sender, reciever);
                        }
                ).toList();

        return findAccountHistories;
    }

    //사용자 조회
    private TempUser getUser(long serialNumber){
        return tempUserRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    //계좌 조회
    private Account getAccount(String accountNum){
        return accountRepository.findByAccountNum(accountNum)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));
    }
}
