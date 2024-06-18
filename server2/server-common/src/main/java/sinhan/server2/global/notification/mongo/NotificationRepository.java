package sinhan.server2.global.notification.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import sinhan.server2.global.notification.entity.Notification;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, Integer> {
    public void deleteByNotificationSerialNumber(String notificationSerialNumber);

    public void deleteByReceiverSerialNumber(Long receiverSerialNumber);

    public List<Notification> findAllByReceiverSerialNumber(Long receiverSerialNumber);
}
