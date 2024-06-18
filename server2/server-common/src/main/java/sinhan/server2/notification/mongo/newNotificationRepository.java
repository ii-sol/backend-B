package sinhan.server2.notification.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import sinhan.server2.notification.entity.Notification;

public interface newNotificationRepository extends MongoRepository<Notification, Integer> {
    public void deleteByNotificationSerialNumber(String notificationSerialNumber);

    public void deleteByReceiverSerialNumber(Long receiverSerialNumber);
}
