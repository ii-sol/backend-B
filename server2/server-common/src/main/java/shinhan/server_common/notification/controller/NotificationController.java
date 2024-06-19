package shinhan.server_common.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import shinhan.server_common.notification.dto.NotificationFindAllResponse;
import shinhan.server_common.global.utils.ApiUtils;
import shinhan.server_common.notification.service.SSEService;

import java.util.List;

import static shinhan.server_common.global.utils.ApiUtils.success;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final SSEService sseService;

    //SSE 구독하기 -> 프론트에서 로그인할때 부름
//    @GetMapping("/subscribe/{}")
//    public SseEmitter subscribeSSE(TempUser tempUser){
//
//        return sseService.subscribe(tempUser.getSerialNumber());
//    }


    @GetMapping("/subscribe/{usn}")
    public SseEmitter subscribeSSE(@PathVariable("usn") Long usn){

        return sseService.subscribe(usn);
    }

    //해당 사용자의 모든 알림 가져오기
    @GetMapping("/{usn}")
    public ApiUtils.ApiResult findAllNotifications(@PathVariable("usn") Long usn){
        List<NotificationFindAllResponse> response = sseService.findAllNotifications(usn);
        return success(response);
    }

    //개별 알림 삭제하기
    @DeleteMapping("/{nsn}")
    public ApiUtils.ApiResult deleteNotification( @PathVariable("nsn") String nsn){
        sseService.deleteNotification(nsn);
        return success(null);
    }

    //알림 전체 삭제하기
    @DeleteMapping("/all/{rsn}")
    public ApiUtils.ApiResult deleteNotification(@PathVariable("rsn") Long rsn){
        sseService.deleteAllNotifications(rsn);
        return success(null);
    }


}
