package sinhan.server1.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sinhan.server1.domain.notification.entity.Notification;
import sinhan.server1.domain.tempuser.TempUser;
import sinhan.server1.domain.tempuser.TempUserRepository;
import sinhan.server1.global.exception.CustomException;
import sinhan.server1.global.exception.ErrorCode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SSEService {
    private final Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
    private final TempUserRepository tempUserRepository;
    public SseEmitter subscribe(Long serialNumber) {
        SseEmitter emitter = new SseEmitter(7200000L);// 2시간 타임아웃
        sseEmitters.put(serialNumber, emitter);

        emitter.onCompletion(()-> sseEmitters.remove(serialNumber));
        emitter.onTimeout(()->{
            sseEmitters.remove(serialNumber);
            log.info("SSE 타임아웃" + serialNumber);
        });

        return emitter;
    }

    public void sendNotification(Long serialNumber, String senderName, Integer functionCode, Integer messageCode){
        SseEmitter emitter = sseEmitters.get(serialNumber);
        TempUser receiver = tempUserRepository.findBySerialNumber(serialNumber);
₩
        Notification notification = Notification.builder()
                .receiver(receiver)
                .sender(senderName)
                .functionCode(functionCode)
                .messageCode(messageCode)
                .createDate(LocalDateTime.now())
                .build();

        //notification 몽고 디비에 저장

            try{
                emitter.send(SseEmitter.event().name("notification").data(notification));
            } catch (IOException e) {

                //몽고 디비에서 저장된거 삭제하기


                throw new CustomException(ErrorCode.FAILED_NOTIFICATION);
            }
        }

    }

}
