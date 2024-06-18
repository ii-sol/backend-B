package sinhan.server1.domain.user.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.user.dto.JoinInfoSaveRequest;
import sinhan.server1.domain.user.dto.LoginInfoFindRequest;
import sinhan.server1.domain.user.dto.ParentsFindOneResponse;
import sinhan.server1.domain.user.dto.ParentsUpdateRequest;
import sinhan.server1.domain.user.entity.Family;
import sinhan.server1.domain.user.entity.Parents;
import sinhan.server1.domain.user.repository.FamilyRepository;
import sinhan.server1.domain.user.repository.ParentsRepository;
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
    private ParentsRepository parentsRepository;
    private FamilyRepository familyRepository;

    @Transactional
    public ParentsFindOneResponse getUser(long sn) {
        Parents parents = parentsRepository.findBySerialNum(sn)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        return parents.convertToUserFindOneResponse();
    }

    @Transactional
    public ParentsFindOneResponse updateUser(ParentsUpdateRequest parentsUpdateRequest) {
        Parents parents = parentsRepository.findBySerialNum(parentsUpdateRequest.getSerialNum())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));


        parents.setPhoneNum(parentsUpdateRequest.getPhoneNum());
        parents.setName(parentsUpdateRequest.getName());
        parents.setBirthDate(parentsUpdateRequest.getBirthdate());
        parents.setProfileId(parentsUpdateRequest.getProfileId());

        Parents updatedParents = parentsRepository.save(parents);

        return updatedParents.convertToUserFindOneResponse();
    }

    @Transactional
    public boolean disconnectFamily(long sn, long familySn) {
        Parents parents = parentsRepository.findBySerialNum(sn)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        Family family = familyRepository
                .findByUserSerialNumAndFamilySn(parents, familySn)
                .orElseThrow(() -> new NoSuchElementException("가족 관계가 존재하지 않습니다."));

        familyRepository.delete(family.getId());

        Optional<Family> checkFamily = familyRepository.findById(family.getId());
        return checkFamily.isEmpty();
    }

    @Transactional
    public List<String> getPhones() {
        return parentsRepository.findAllPhones();
    }

    @Transactional
    public ParentsFindOneResponse join(JoinInfoSaveRequest joinInfoSaveRequest) {
        long serialNum = parentsRepository.generateSerialNum();
        log.info("Generated serial number={}", serialNum);
        Parents parents = parentsRepository.save(joinInfoSaveRequest.convertToUser(serialNum, passwordEncoder));

        return parents.convertToUserFindOneResponse();
    }

    @Transactional
    public ParentsFindOneResponse login(@Valid LoginInfoFindRequest loginInfoFindRequest) throws AuthException {
        Parents parents = parentsRepository.findByPhoneNum(loginInfoFindRequest.getPhoneNum()).orElseThrow(() -> new AuthException("INVALID_CREDENTIALS"));

        if (!passwordEncoder.matches(loginInfoFindRequest.getAccountInfo(), parents.getAccountInfo())) {
            throw new AuthException("INVALID_CREDENTIALS");
        }

        return parents.convertToUserFindOneResponse();
    }

    @Transactional()
    public List<FamilyInfoResponse> getFamilyInfo(long sn) {
        return familyRepository.findMyFamilyInfo(sn)
                .stream()
                .map(myFamily -> new FamilyInfoResponse(myFamily.getSn(), myFamily.getName()))
                .collect(Collectors.toList());
    }
}
