package sinhan.server2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import sinhan.server2.notification.mongo.NotificationRepository;

// common
@SpringBootApplication//(scanBasePackages = {"server-child", "server-parent"})
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = NotificationRepository.class))
@EnableMongoRepositories(basePackages = {"sinhan.server2.notification.mongo"})
public class Server3Application {

    public static void main(String[] args) {
        SpringApplication.run(Server3Application.class, args);
    }

//    @RabbitListener(queues = "hello")
//    public void listen(String message) {
//        System.out.println("Received: " + message);
//    }
}
