package sinhan.server2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Server2Application {

    public static void main(String[] args) {
        SpringApplication.run(Server2Application.class, args);
    }

    @RabbitListener(queues = "hello")
    public void listen(String message) {
        System.out.println("Received: " + message);
    }

}
