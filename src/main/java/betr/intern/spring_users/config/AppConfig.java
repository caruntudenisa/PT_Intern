package betr.intern.spring_users.config;

import betr.intern.spring_users.service.UserStatsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserStatsService userStatsService(){
        return new UserStatsService();
    }
}
