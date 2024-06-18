package sinhan.server1.global.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:.env")
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().load();
    }
}
