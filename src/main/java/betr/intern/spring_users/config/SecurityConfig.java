package betr.intern.spring_users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // for applying all the security in all the urls
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth -> // bloc of configuration for acces rules
            auth.requestMatchers("/stats")
                    .permitAll() // acces for everyone until login
                    .requestMatchers(HttpMethod.POST, "/users")
                    .hasRole("ADMIN") // only admins will have acces to this route, to create new
                    // resources
                    .requestMatchers(HttpMethod.PUT, "/users/**")
                    .hasRole("ADMIN") // only admins will have acces to these resources, for update
                    .anyRequest()
                    .authenticated() // for the rest of the endpoints
            )
        .httpBasic(Customizer.withDefaults());
    return http
        .build(); // they will run under a SecurityFilterChain object that will be applied at the
    // beginning of the app to filter the security
  }

  @Bean
  public UserDetailsService userDetailsService(
      final PasswordEncoder
          passwordEncoder) { // interface used for verifying the name, role and password
    final UserDetails user1 =
        User.withUsername("user1")
            .password(passwordEncoder.encode("password1"))
            .roles("USER")
            .build();

    final UserDetails user2 =
        User.withUsername("user2")
            .password(passwordEncoder.encode("password2"))
            .roles("USER")
            .build();
    final UserDetails admin =
        User.withUsername("admin")
            .password(passwordEncoder.encode("password3"))
            .roles("ADMIN")
            .build();
    return new InMemoryUserDetailsManager(user1, user2, admin);
  }

  @Bean
  public PasswordEncoder
      passwordEncoder() { // class that implements the UserDetailsService interface and will map the
    // objects in the ram memory
    return new BCryptPasswordEncoder();
  }
}
