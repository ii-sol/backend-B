package sinhan.server1.domain.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sinhan.server1.domain.tempuser.TempUser;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_serial_number", referencedColumnName = "serialNumber")
    TempUser receiver;

    String sender; // 별명
    int messageCode; // 메세지 코드
    int functionCode; // 기능

    LocalDateTime createDate;

    @Builder
    public Notification(int messageCode, int functionCode, TempUser receiver, String sender, LocalDateTime createDate) {
        this.messageCode = messageCode;
        this.functionCode = functionCode;
        this.receiver = receiver;
        this.sender = sender;
        this.createDate = createDate;
    }
}
