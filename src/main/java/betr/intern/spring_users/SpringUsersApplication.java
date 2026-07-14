package betr.intern.spring_users;

import betr.intern.spring_users.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringUsersApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SpringUsersApplication.class, args);
  }

  @Bean
  public CommandLineRunner seedData(final UserRepository userRepository) {
    return args -> {
      if (userRepository.count() == 0) {
        userRepository.save(
            new betr.intern.spring_users.model.User("initial@gmail.com", "Initial User"));
        System.out.println("Utilizator de test inserat!");
      }
    };
  }
}
