package sinhan.server1.domain.account.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sinhan.server1.domain.account.entity.AccountHistory;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Integer> , AccountHistoryRepositoryCustom{

}
