package sinhan.server2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// common
@SpringBootApplication//(scanBasePackages = {"server-child", "server-parent"})
public class Server3Application {

    public static void main(String[] args) {
        SpringApplication.run(Server3Application.class, args);
    }

//    @RabbitListener(queues = "hello")
//    public void listen(String message) {
//        System.out.println("Received: " + message);
//    }
}
