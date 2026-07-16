package betr.intern.spring_users.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class MongoAuditConfig {

  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null || !authentication.isAuthenticated()) {
        return Optional.of("SYSTEM");
      }

      return Optional.of(authentication.getName());
    };
  }
}
