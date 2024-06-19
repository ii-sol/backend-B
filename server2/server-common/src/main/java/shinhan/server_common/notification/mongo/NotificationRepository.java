package shinhan.server_common.notification.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import shinhan.server_common.notification.entity.Notification;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, Integer> {
    public void deleteByNotificationSerialNumber(String notificationSerialNumber);

    public void deleteByReceiverSerialNumber(Long receiverSerialNumber);

    public List<Notification> findAllByReceiverSerialNumber(Long receiverSerialNumber);
}
