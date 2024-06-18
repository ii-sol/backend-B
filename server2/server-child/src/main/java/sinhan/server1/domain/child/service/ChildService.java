package sinhan.server1.domain.child.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sinhan.server1.domain.child.dto.ChildFindOneResponse;
import sinhan.server1.domain.child.dto.ChildUpdateRequest;
import sinhan.server1.domain.child.dto.FamilySaveRequest;
import sinhan.server1.domain.child.dto.JoinInfoSaveRequest;
import sinhan.server1.domain.child.dto.LoginInfoFindRequest;
import sinhan.server1.domain.child.dto.PhoneFindRequest;
import sinhan.server1.domain.child.dto.ScoreUpdateRequest;
import sinhan.server1.domain.child.entity.Parents;
import sinhan.server1.domain.child.entity.Child;
import sinhan.server1.domain.child.entity.Family;
import sinhan.server1.domain.child.repository.ChildRepository;
import sinhan.server1.domain.child.repository.FamilyRepository;
import sinhan.server1.domain.child.repository.ParentsRepository;
import sinhan.server1.global.security.dto.FamilyInfoResponse;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ChildService {

    private final PasswordEncoder passwordEncoder;
    private ChildRepository childRepository;
    private ParentsRepository parentsRepository;
    private FamilyRepository familyRepository;

    @Transactional
    public ChildFindOneResponse getUser(long sn) {
        Child child = childRepository.findBySerialNum(sn)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        return child.convertToUserFindOneResponse();
    }

    @Transactional
    public ChildFindOneResponse updateUser(ChildUpdateRequest childUpdateRequest) {
        Child child = childRepository.findBySerialNum(childUpdateRequest.getSerialNum())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));


        child.setPhoneNum(childUpdateRequest.getPhoneNum());
        child.setName(childUpdateRequest.getName());
        child.setBirthDate(childUpdateRequest.getBirthdate());
        child.setProfileId(childUpdateRequest.getProfileId());

        Child updatedChild = childRepository.save(child);

        return updatedChild.convertToUserFindOneResponse();
    }

    @Transactional
    public boolean connectFamily(FamilySaveRequest familySaveRequest) {
        Child child = childRepository.findBySerialNum(familySaveRequest.getSn())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        Parents parents = parentsRepository.findByPhoneNum(familySaveRequest.getPhoneNum())
                .orElseThrow(() -> new NoSuchElementException("부모 사용자가 존재하지 않습니다."));

        Family family = familyRepository.save(new Family(child, parents, familySaveRequest.getParentsAlias()));

        return !familyRepository.findMyFamilyInfo(family.getId()).isEmpty();
    }

    @Transactional
    public boolean disconnectFamily(long sn, long familySn) {
        Child child = childRepository.findBySerialNum(sn)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        Parents parents = parentsRepository.findBySerialNum(familySn)
                .orElseThrow(() -> new NoSuchElementException("부모 사용자가 존재하지 않습니다."));

        Family family = familyRepository
                .findByChildSerialNumAndFamilySerialNum(child, parents)
                .orElseThrow(() -> new NoSuchElementException("가족 관계가 존재하지 않습니다."));

        familyRepository.delete(family.getId());

        return familyRepository.findById(family.getId()).isEmpty();
    }

    @Transactional
    public List<String> getPhones() {
        return childRepository.findAllPhones();
    }

    public int updateScore(ScoreUpdateRequest scoreUpdateRequest) {
        Child child = childRepository.findBySerialNum(scoreUpdateRequest.getSn())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        child.setScore(child.getScore() + scoreUpdateRequest.getChange());
        Child updatedChild = childRepository.save(child);

        return updatedChild.getScore();
    }

    @Transactional
    public ChildFindOneResponse join(JoinInfoSaveRequest joinInfoSaveRequest) {
        long serialNum = childRepository.generateSerialNum();
        log.info("Generated serial number={}", serialNum);
        Child child = childRepository.save(joinInfoSaveRequest.convertToUser(serialNum, passwordEncoder));

        return child.convertToUserFindOneResponse();
    }

    @Transactional
    public boolean checkPhone(PhoneFindRequest phoneFindRequest) {
        return childRepository.findByPhoneNum(phoneFindRequest.getPhoneNum()).isEmpty();
    }

    @Transactional
    public ChildFindOneResponse login(@Valid LoginInfoFindRequest loginInfoFindRequest) throws AuthException {
        Child child = childRepository.findByPhoneNum(loginInfoFindRequest.getPhoneNum()).orElseThrow(() -> new AuthException("INVALID_CREDENTIALS"));

        if (!passwordEncoder.matches(loginInfoFindRequest.getAccountInfo(), child.getAccountInfo())) {
            throw new AuthException("INVALID_CREDENTIALS");
        }

        return child.convertToUserFindOneResponse();
    }

    @Transactional()
    public List<FamilyInfoResponse> getFamilyInfo(long sn) {
        return familyRepository.findMyFamilyInfo(sn)
                .stream()
                .map(myFamily -> new FamilyInfoResponse(myFamily.getSn(), myFamily.getName()))
                .collect(Collectors.toList());
    }
}
