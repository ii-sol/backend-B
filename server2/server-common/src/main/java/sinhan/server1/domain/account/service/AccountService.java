package sinhan.server1.domain.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.account.dto.AccountFindOneResponse;
import sinhan.server1.domain.account.dto.AccountHistoryFindAllResponse;
import sinhan.server1.domain.account.dto.AccountTransmitOneRequest;
import sinhan.server1.domain.account.dto.AccountTransmitOneResponse;
import sinhan.server1.domain.account.entity.Account;
import sinhan.server1.domain.account.repository.AccountHistoryRepository;
import sinhan.server1.domain.account.repository.AccountRepository;
import sinhan.server1.domain.tempuser.TempUser;
import sinhan.server1.global.utils.account.AccountUtils;
import sinhan.server1.global.utils.user.UserUtils;

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
    private final AccountUtils accountUtils;
    private final UserUtils userUtils;

    //계좌 개별 조회
    public AccountFindOneResponse findAccount(Long serialNumber, Integer status) {
        TempUser tempUser = userUtils.getUser(serialNumber);
        Account findAccount = accountRepository.findByUserAndStatus(tempUser, status);
        return AccountFindOneResponse.from(findAccount);
    }

    //계좌 내역 조회
    public List<AccountHistoryFindAllResponse> findAccountHistory(Long serialNumber, Integer year, Integer month, Integer status) {
        TempUser tempUser = userUtils.getUser(serialNumber);
        Account findAccount = accountRepository.findByUserAndStatus(tempUser, status);

        LocalDate start = LocalDate.of(year,month,1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        List<AccountHistoryFindAllResponse> findAccountHistories = accountHistoryRepository.findByUserAndCreateDateBetween(findAccount, startDateTime, endDateTime)
                .stream()
                .map(history -> {
                            Account senderAccount = accountUtils.getAccount(history.getSenderAccountNum());
                            Account recieverAccount = accountUtils.getAccount(history.getReceiverAccountNum());
                            TempUser sender = userUtils.getUser(senderAccount.getUser().getSerialNumber());
                            TempUser reciever = userUtils.getUser(recieverAccount.getUser().getSerialNumber());

                            return AccountHistoryFindAllResponse.of(history, sender, reciever);
                        }
                ).toList();

        return findAccountHistories;
    }

    // 이체하기
    public AccountTransmitOneResponse transferMoney(AccountTransmitOneRequest request) {
        Account senderAccount = accountUtils.getAccount(request.getSendAccountNum());
        Account recieverAccount = accountUtils.getAccount(request.getReceiveAccountNum());
        TempUser reciever = userUtils.getUser(recieverAccount.getUser().getSerialNumber());

        accountUtils.transferMoneyByAccount(senderAccount, recieverAccount, request.getAmount(), 1);

        return AccountTransmitOneResponse.of(senderAccount, request, reciever);
    }
}
