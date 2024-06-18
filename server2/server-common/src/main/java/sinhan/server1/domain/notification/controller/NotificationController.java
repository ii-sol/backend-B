package sinhan.server1.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sinhan.server1.domain.notification.service.SSEService;
import sinhan.server1.domain.tempuser.TempUser;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final SSEService sseService;

    //SSE 구독하기 -> 프론트에서 로그인할때 부름
    @GetMapping("/subscribe")
    public SseEmitter subscribeSSE(TempUser tempUser){
        return sseService.subscribe(tempUser.getSerialNumber());
    }

    //해당 사용자의 모든 알림 가져오기

    //개별 알림 삭제하기

    //알림 전체 삭제하기


}
