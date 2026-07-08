package betr.intern.spring_users.config;

import betr.intern.spring_users.model.Role;
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
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/stats", "/graphiql/**", "/ws/stats/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/users")
                    .hasRole(Role.ADMIN.name())
                    .requestMatchers(HttpMethod.PUT, "/users/**")
                    .hasRole(Role.ADMIN.name())
                    .anyRequest()
                    .authenticated())
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService(final PasswordEncoder passwordEncoder) {
    final UserDetails user1 =
        User.withUsername("user1")
            .password(passwordEncoder.encode("password1"))
            .roles(Role.USER.name())
            .build();

    final UserDetails user2 =
        User.withUsername("user2")
            .password(passwordEncoder.encode("password2"))
            .roles(Role.USER.name())
            .build();
    final UserDetails admin =
        User.withUsername("admin")
            .password(passwordEncoder.encode("password3"))
            .roles(Role.ADMIN.name())
            .build();
    return new InMemoryUserDetailsManager(user1, user2, admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
