package sinhan.server2.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sinhan.server2.notification.dto.NotificationFindAllResponse;
import sinhan.server2.notification.entity.Notification;
import sinhan.server2.domain.tempuser.TempUser;
import sinhan.server2.domain.tempuser.TempUserRepository;
import sinhan.server2.global.exception.CustomException;
import sinhan.server2.global.exception.ErrorCode;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private final sinhan.server2.notification.mongo.newNotificationRepository newNotificationRepository;

    //SSE에 등록
    public SseEmitter subscribe(Long serialNumber) {
        SseEmitter emitter = new SseEmitter(7200000L);// 2시간 타임아웃
        sseEmitters.put(serialNumber, emitter);

        //이부분은 원래 없음
        sendNotification(serialNumber, "alfo", 1, 1);

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

    //알람 보내기
    public void sendNotification(Long serialNumber, String senderName, Integer functionCode, Integer messageCode){
        SseEmitter emitter = sseEmitters.get(serialNumber);
        Notification savedNotification = saveNotification(serialNumber, senderName, functionCode, messageCode);
    //emitter가 null인 경우
        try{
            emitter.send(SseEmitter.event().name("notification").data(savedNotification));
        } catch (IOException e) {

            //몽고 디비에서 저장된거 삭제하기
            newNotificationRepository.deleteByNotificationSerialNumber(savedNotification.getNotificationSerialNumber());
            //emitter를 삭제해야하나? => 재시도 로직을 넣어야 하나? => 재시도 횟수
            sseEmitters.remove(serialNumber);

            throw new CustomException(ErrorCode.FAILED_NOTIFICATION);
        }

    }


    //private : 알림 저장하기
    private Notification saveNotification(Long serialNumber, String senderName, Integer functionCode, Integer messageCode){
         TempUser receiver = tempUserRepository.findBySerialNumber(serialNumber);

        System.out.println(receiver.getSerialNumber());

        Notification notification = Notification.builder()
                .receiverSerialNumber(serialNumber)
                .sender(senderName)
                .functionCode(functionCode)
                .messageCode(messageCode)
                .createDate(LocalDateTime.now())
                .build();

        //notification 몽고 디비에 저장
        newNotificationRepository.save(notification);

        return notification;

    }

    //알람 전체 조회
    public List<NotificationFindAllResponse> findAllNotifications(Long receiverSerialNumber) {
        return newNotificationRepository.findAllByReceiverSerialNumber(receiverSerialNumber)
                .stream().map(notification -> {
                    return NotificationFindAllResponse.from(notification);
                })
                .toList();
    }

    //알람 개별 삭제
    public void deleteNotification(String notificationSerailNumber) {
        newNotificationRepository.deleteByNotificationSerialNumber(notificationSerailNumber);
    }

    //알람 전체 삭제
    public void deleteAllNotifications(Long receiverSerialNumber){
        newNotificationRepository.deleteByReceiverSerialNumber(receiverSerialNumber);
    }

}
