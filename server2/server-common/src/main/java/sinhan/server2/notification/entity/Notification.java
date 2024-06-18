package sinhan.server2.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sinhan.server2.domain.tempuser.TempUser;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @Column(unique = true, nullable = false)
    String notificationSerialNumber;

    Long receiverSerialNumber;

    String sender; // 별명
    int messageCode; // 메세지 코드
    int functionCode; // 기능

    LocalDateTime createDate;

    @Builder
    public Notification(int messageCode, int functionCode, Long receiverSerialNumber, String sender, LocalDateTime createDate) {
        this.notificationSerialNumber = UUID.randomUUID().toString();
        this.messageCode = messageCode;
        this.functionCode = functionCode;
        this.receiverSerialNumber = receiverSerialNumber;
        this.sender = sender;
        this.createDate = createDate;
    }
}
