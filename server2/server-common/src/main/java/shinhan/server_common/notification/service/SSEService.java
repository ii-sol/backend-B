package shinhan.server_common.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import shinhan.server_common.notification.dto.NotificationFindAllResponse;
import shinhan.server_common.notification.entity.Notification;
import shinhan.server_common.domain.tempuser.TempUser;
import shinhan.server_common.domain.tempuser.TempUserRepository;
import shinhan.server_common.global.exception.CustomException;
import shinhan.server_common.global.exception.ErrorCode;
import shinhan.server_common.notification.utils.MessageHandler;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SSEService {
    private final Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    private final TempUserRepository tempUserRepository;
    private final shinhan.server_common.notification.mongo.NotificationRepository NotificationRepository;

    //SSE에 등록
    public SseEmitter subscribe(Long serialNumber) {
        SseEmitter emitter = new SseEmitter(7200000L);// 2시간 타임아웃
        sseEmitters.put(serialNumber, emitter);

        //이부분은 원래 없음
        String newMesssage= MessageHandler.getMessage(0,"알파코");
        sendNotification(serialNumber, "alfo", 1, newMesssage);
        //여기까지 원래 없음

        emitter.onCompletion(()-> sseEmitters.remove(serialNumber));
        emitter.onTimeout(()->{
            sseEmitters.remove(serialNumber);
            log.info("SSE 타임아웃" + serialNumber);
        });
        emitter.onError((e)->{
            sseEmitters.remove(serialNumber);
            log.info("SSE 에러 발생"+serialNumber);
        });

        return emitter;
    }

    //알람 보내기 => message는 각자 자기 도메인에서 MessageHandler.getMessage(여러 인수들); 해서 나온거 가져오기
    public void sendNotification(Long serialNumber, String senderName, Integer functionCode, String message){
        SseEmitter emitter = sseEmitters.get(serialNumber);
        Notification savedNotification = saveNotification(serialNumber, senderName, functionCode, message);
    //emitter가 null인 경우
        try{
            emitter.send(SseEmitter.event().name("notification").data(savedNotification));
        } catch (IOException e) {

            //몽고 디비에서 저장된거 삭제하기
            NotificationRepository.deleteByNotificationSerialNumber(savedNotification.getNotificationSerialNumber());
            //emitter를 삭제해야하나? => 재시도 로직을 넣어야 하나? => 재시도 횟수
            sseEmitters.remove(serialNumber);

            throw new CustomException(ErrorCode.FAILED_NOTIFICATION);
        }

    }


    //private : 알림 저장하기
    private Notification saveNotification(Long serialNumber, String senderName, Integer functionCode, String message){
         TempUser receiver = tempUserRepository.findBySerialNumber(serialNumber);

        System.out.println(receiver.getSerialNumber());

        Notification notification = Notification.builder()
                .receiverSerialNumber(serialNumber)
                .sender(senderName)
                .functionCode(functionCode)
                .message(message)
                .createDate(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime())
                .build();

        //notification 몽고 디비에 저장
        NotificationRepository.save(notification);

        return notification;

    }

    //알람 전체 조회
    public List<NotificationFindAllResponse> findAllNotifications(Long receiverSerialNumber) {
        return NotificationRepository.findAllByReceiverSerialNumber(receiverSerialNumber)
                .stream().map(notification -> {
                    return NotificationFindAllResponse.from(notification);
                })
                .toList();
    }

    //알람 개별 삭제
    public void deleteNotification(String notificationSerailNumber) {
        NotificationRepository.deleteByNotificationSerialNumber(notificationSerailNumber);
    }

    //알람 전체 삭제
    public void deleteAllNotifications(Long receiverSerialNumber){
        NotificationRepository.deleteByReceiverSerialNumber(receiverSerialNumber);
    }

}
