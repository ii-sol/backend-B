package sinhan.server3;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import sinhan.server2.Server3Application;

@SpringBootApplication(scanBasePackages = {"sinhan.server2"})
@EnableJpaRepositories(basePackages = {"sinhan.server2.domain"})
@EnableMongoRepositories(basePackages = {"sinhan.server2.notification.mongo"})
public class Server2Application {

    public static void main(String[] args) {
        SpringApplication.run(Server3Application.class, args);
    }

//    @RabbitListener(queues = "hello")
//    public void listen(String message) {
//        System.out.println("Received: " + message);
//    }
}