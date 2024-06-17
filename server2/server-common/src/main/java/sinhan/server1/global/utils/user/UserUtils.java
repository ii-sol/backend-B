package sinhan.server1.global.utils.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sinhan.server1.domain.tempuser.TempUser;
import sinhan.server1.domain.tempuser.TempUserRepository;
import sinhan.server1.global.exception.CustomException;
import sinhan.server1.global.exception.ErrorCode;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserUtils {

    private final TempUserRepository tempUserRepository;

    //serailNumber로 사용자 조회
    public TempUser getUser(Long serialNumber){
        return tempUserRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
