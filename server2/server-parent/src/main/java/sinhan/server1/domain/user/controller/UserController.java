package sinhan.server1.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sinhan.server1.domain.user.dto.JoinInfoSaveRequest;
import sinhan.server1.domain.user.dto.LoginInfoFindRequest;
import sinhan.server1.domain.user.dto.ParentsFindOneResponse;
import sinhan.server1.domain.user.dto.ParentsUpdateRequest;
import sinhan.server1.domain.user.service.UserService;
import sinhan.server1.global.security.JwtService;
import sinhan.server1.global.security.dto.FamilyInfoResponse;
import sinhan.server1.global.security.dto.JwtTokenResponse;
import sinhan.server1.global.security.dto.UserInfoResponse;
import sinhan.server1.global.utils.ApiUtils;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static sinhan.server1.global.utils.ApiUtils.error;
import static sinhan.server1.global.utils.ApiUtils.success;

@Slf4j
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    @GetMapping("/users/{sn}")
    public ApiUtils.ApiResult getUser(@PathVariable("sn") long sn) throws Exception {
        UserInfoResponse userInfo = jwtService.getUserInfo(jwtService.getAccessToken());
        if (userInfo.getSn() != sn) {
            List<FamilyInfoResponse> familyInfo = userInfo.getFamilyInfo();

            Set<Long> familyIds = new java.util.HashSet<>();
            for (FamilyInfoResponse info : familyInfo) {
                familyIds.add(info.getSn());
            }

            if (!familyIds.contains(sn)) {
                throw new AuthException("UNAUTHORIZED");
            }
        }

        ParentsFindOneResponse user = userService.getUser(sn);
        return user.getSerialNumber() == sn ? success(user) : error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/users")
    public ApiUtils.ApiResult updateUser(@RequestBody ParentsUpdateRequest parentsUpdateRequest) throws Exception {
        UserInfoResponse userInfo = jwtService.getUserInfo(jwtService.getAccessToken());
        parentsUpdateRequest.setSerialNum(userInfo.getSn());

        ParentsFindOneResponse user = userService.updateUser(parentsUpdateRequest);
        return user != null ? success(user) : error("잘못된 사용자 요청입니다.", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/users/{family-sn}")
    public ApiUtils.ApiResult disconnectFamily(@PathVariable long familySn) throws Exception {
        UserInfoResponse userInfo = jwtService.getUserInfo(jwtService.getAccessToken());

        if (!isFamilyUser(familySn)) {
            throw new NoSuchElementException("아이 사용자가 존재하지 않습니다.");
        }

        if (isDisconnected(familySn)) {
            boolean isDisconnected = userService.disconnectFamily(userInfo.getSn(), familySn);

            if (isDisconnected) {
                return success("가족 관계가 삭제되었습니다.");
            }
        }

        return error("가족 관계가 삭제되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isFamilyUser(long familySn) {
        // TODO: 아이 사용자 확인 이벤트 등록 - 콜백
        return true;
    }

    private boolean isDisconnected(long familySn) {
        // TODO: 아이 서버 가족 관계 생성 이벤트 등록 - 콜백
        return true;
    }

    @GetMapping("/users/phones")
    public ApiUtils.ApiResult getPhones() {
        List<String> phones = userService.getPhones();

        return phones.isEmpty() ? error("전화번호부를 가져오지 못했습니다.", HttpStatus.NOT_FOUND) : success(phones);
    }

    @GetMapping("/auth/main")
    public ApiUtils.ApiResult main() {
        return success("초기 화면");
    }

    @PostMapping("/auth/join")
    public ApiUtils.ApiResult join(@Valid @RequestBody JoinInfoSaveRequest joinInfoSaveRequest, HttpServletResponse response) {
        ParentsFindOneResponse user = userService.join(joinInfoSaveRequest);

        if (user != null) {
            return success("가입되었습니다.");
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return error("가입에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/login")
    public ApiUtils.ApiResult login(@Valid @RequestBody LoginInfoFindRequest loginInfoFindRequest, HttpServletResponse response) throws AuthException, JsonProcessingException {
        try {
            ParentsFindOneResponse user = userService.login(loginInfoFindRequest);
            List<FamilyInfoResponse> myFamilyInfo = userService.getFamilyInfo(user.getSerialNumber());
            setFamilyName(myFamilyInfo);

            myFamilyInfo.forEach(info -> log.info("Family Info - SN: {}, Name: {}", info.getSn(), info.getName()));

            JwtTokenResponse jwtTokenResponse = new JwtTokenResponse(jwtService.createAccessToken(user.getSerialNumber(), myFamilyInfo), jwtService.createRefreshToken(user.getSerialNumber()));
            jwtService.sendJwtToken(response, jwtTokenResponse);

            return success("로그인되었습니다.");
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return error("로그인에 실패하였습니다. " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private void setFamilyName(List<FamilyInfoResponse> myFamilyInfo) {
        // TODO: 아이 이름 가져오기 이벤트 등록 - 콜백
    }

    @PostMapping("/auth/logout")
    public ApiUtils.ApiResult logout(HttpServletRequest request, HttpServletResponse response) {
        return success(""); // main으로 redirection
    }

    @PostMapping("/auth/token")
    public ApiUtils.ApiResult refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtService.getRefreshToken();
        if (refreshToken == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return error("Refresh-Token을 찾지 못했습니다.", HttpStatus.BAD_REQUEST);
        }

        try {
            long sn = jwtService.getUserInfo(refreshToken).getSn();
            List<FamilyInfoResponse> myFamilyInfo = userService.getFamilyInfo(sn);
            setFamilyName(myFamilyInfo);

            String newAccessToken = jwtService.createAccessToken(sn, myFamilyInfo);
            jwtService.sendAccessToken(response, newAccessToken);
            return success("Authorization이 새로 발급되었습니다.");
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return error("Refresh-Token 검증에 실패하였습니다. " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
