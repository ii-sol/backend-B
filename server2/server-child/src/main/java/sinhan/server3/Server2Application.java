package sinhan.server3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import sinhan.server2.Server3Application;
import sinhan.server2.notification.mongo.NotificationRepository;

@SpringBootApplication(scanBasePackages = {"sinhan.server3", "sinhan.server2"})
public class Server2Application {

    public static void main(String[] args) {
        SpringApplication.run(Server3Application.class, args);
    }

//    @RabbitListener(queues = "hello")
//    public void listen(String message) {
//        System.out.println("Received: " + message);
//    }
}