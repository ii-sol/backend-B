package sinhan.server1.domain.parents.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.parents.dto.*;
import sinhan.server1.domain.parents.entity.Child;
import sinhan.server1.domain.parents.entity.Family;
import sinhan.server1.domain.parents.entity.Parents;
import sinhan.server1.domain.parents.repository.ChildRepository;
import sinhan.server1.domain.parents.repository.FamilyRepository;
import sinhan.server1.domain.parents.repository.ParentsRepository;
import sinhan.server1.global.security.dto.FamilyInfoResponse;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ParentsService {

    private final PasswordEncoder passwordEncoder;
    private ParentsRepository parentsRepository;
    private ChildRepository childRepository;
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

        if (isUpdated(parentsUpdateRequest, updatedParents)) {
            return updatedParents.convertToUserFindOneResponse();
        } else {
            throw new InternalError("회원 정보 변경이 실패하였습니다.");
        }
    }

    private static boolean isUpdated(ParentsUpdateRequest parentsUpdateRequest, Parents updatedParents) {
        return updatedParents.getPhoneNum().equals(parentsUpdateRequest.getPhoneNum())
                && updatedParents.getName().equals(parentsUpdateRequest.getName())
                && updatedParents.getBirthDate().equals(parentsUpdateRequest.getBirthdate())
                && updatedParents.getProfileId() == parentsUpdateRequest.getProfileId();
    }

    @Transactional
    public boolean disconnectFamily(long sn, long familySn) {
        Parents parents = parentsRepository.findBySerialNum(sn)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        Child child = childRepository.findBySerialNum(sn)
                .orElseThrow(() -> new NoSuchElementException("아이 사용자가 존재하지 않습니다."));

        Family family = familyRepository
                .findByChildSerialNumAndParentsSerialNum(child, parents)
                .orElseThrow(() -> new NoSuchElementException("가족 관계가 존재하지 않습니다."));

        familyRepository.delete(family.getId());

        return familyRepository.findById(family.getId()).isEmpty();
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
    public boolean checkPhone(PhoneFindRequest phoneFindRequest) {
        return parentsRepository.findByPhoneNum(phoneFindRequest.getPhoneNum()).isEmpty();
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
