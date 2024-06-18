package sinhan.server1.domain.user.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.user.dto.*;
import sinhan.server1.domain.user.entity.Family;
import sinhan.server1.domain.user.entity.User;
import sinhan.server1.domain.user.repository.FamilyRepository;
import sinhan.server1.domain.user.repository.UserRepository;
import sinhan.server1.global.security.dto.FamilyInfoResponse;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private FamilyRepository familyRepository;

    @Transactional
    public UserFindOneResponse getUser(long sn){
        User user = userRepository.findBySerialNum(sn)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        return user.convertToUserFindOneResponse();
    }

    @Transactional
    public UserFindOneResponse updateUser(UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findBySerialNum(userUpdateRequest.getSerialNum())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));


        user.setPhoneNum(userUpdateRequest.getPhoneNum());
        user.setName(userUpdateRequest.getName());
        user.setBirthDate(userUpdateRequest.getBirthdate());
        user.setProfileId(userUpdateRequest.getProfileId());

        User updatedUser = userRepository.save(user);

        return updatedUser.convertToUserFindOneResponse();
    }

    @Transactional
    public FamilyFindOneResponse connectFamily(FamilySaveRequest familySaveRequest) {
        User user = userRepository.findBySerialNum(familySaveRequest.getUserSn())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        return familyRepository.save(new Family(user, familySaveRequest.getFamilySn())).convertToFamilyFindOneResponse();
    }

    @Transactional
    public boolean disconnectFamily(long sn, long familySn) {
        User user = userRepository.findBySerialNum(sn)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        Family family = familyRepository
                .findByUserSerialNumAndFamilySn(user, familySn)
                .orElseThrow(() -> new NoSuchElementException("가족 관계가 존재하지 않습니다."));

        familyRepository.delete(family.getId());

        Optional<Family> checkFamily = familyRepository.findById(family.getId());
        return checkFamily.isEmpty();
    }

    @Transactional
    public List<String> getPhones() {
        return userRepository.findAllPhones();
    }

    public int updateScore(ScoreUpdateRequest scoreUpdateRequest) {
        User user = userRepository.findBySerialNum(scoreUpdateRequest.getSn())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        user.setScore(user.getScore() + scoreUpdateRequest.getChange());
        User updatedUser = userRepository.save(user);

        return updatedUser.getScore();
    }

    @Transactional
    public UserFindOneResponse join(JoinInfoSaveRequest joinInfoSaveRequest) {
        long serialNum = userRepository.generateSerialNum();
        log.info("Generated serial number={}", serialNum);
        User user = userRepository.save(joinInfoSaveRequest.convertToUser(serialNum, passwordEncoder));

        return user.convertToUserFindOneResponse();
    }

    @Transactional
    public UserFindOneResponse login(@Valid LoginInfoFindRequest loginInfoFindRequest) throws AuthException {
        User user = userRepository.findByPhoneNum(loginInfoFindRequest.getPhoneNum()).orElseThrow(() -> new AuthException("INVALID_CREDENTIALS"));

        if (!passwordEncoder.matches(loginInfoFindRequest.getAccountInfo(), user.getAccountInfo())) {
            throw new AuthException("INVALID_CREDENTIALS");
        }

        return user.convertToUserFindOneResponse();
    }

    @Transactional()
    public List<FamilyInfoResponse> getFamilyInfo(long sn) {
        return familyRepository.findMyFamilyInfo(sn).stream().map(fi -> new FamilyInfoResponse(fi.getSn())).collect(Collectors.toList());
    }
}
