package shinhan.server_child;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shinhan.server_common.ServerCommonApplication;

@SpringBootApplication(scanBasePackages = {"shinhan.server_child", "shinhan.server_common"})
public class ServerChildApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerCommonApplication.class, args);
    }

//    @RabbitListener(queues = "hello")
//    public void listen(String message) {
//        System.out.println("Received: " + message);
//    }
}