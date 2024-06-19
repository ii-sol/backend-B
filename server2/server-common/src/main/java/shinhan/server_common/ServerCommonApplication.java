package shinhan.server_common;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import shinhan.server_common.notification.mongo.NotificationRepository;

// common
@SpringBootApplication//(scanBasePackages = {"server-child", "server-parent"})
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = NotificationRepository.class))
@EnableMongoRepositories(basePackages = {"shinhan.server_common.notification.mongo"})
public class ServerCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerCommonApplication.class, args);
    }

//    @RabbitListener(queues = "hello")
//    public void listen(String message) {
//        System.out.println("Received: " + message);
//    }
}
