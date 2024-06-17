package sinhan.server1.domain.account.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import sinhan.server1.domain.account.entity.Account;
import sinhan.server1.domain.account.entity.AccountHistory;

import java.time.LocalDateTime;
import java.util.List;

public class AccountHistoryRepositoryImpl implements AccountHistoryRepositoryCustom{

    @Autowired
    EntityManager entityManager;

    //optional?
    // userId가 senderId혹은 receiverId에 있고, 주어진 StatusCode와 일치하고 받은 날짜에 있는 계좌 내역을 가지고 온다.
    @Override
    public List<AccountHistory> findByUserAndCreateDateBetween(Account account, LocalDateTime start, LocalDateTime end) {
        String jpql = "select a from AccountHistory a where (a.senderAccountNum = :accountNum or a.receiverAccountNum = :accountNum) and a.createDate between :start and :end";
        List<AccountHistory> accountHistories = entityManager.createQuery(jpql, AccountHistory.class)
                .setParameter("accountNum", account.getAccountNum())
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();

        return accountHistories;

    }
}
