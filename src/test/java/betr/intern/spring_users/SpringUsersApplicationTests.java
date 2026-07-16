package betr.intern.spring_users;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import betr.intern.spring_users.model.User;
import betr.intern.spring_users.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringUsersApplicationTests {

  @Autowired private UserRepository userRepository;

  @Test
  void contextLoads() {}

  @Test
  void testUserStaffMigration() {
    final List<User> users = userRepository.findAll();
    assertFalse(users.isEmpty(), "User list should not be empty");


    final Optional<User> normalUser =
        users.stream().filter(u -> "admin@example.com".equals(u.getEmail())).findFirst();
    assertTrue(normalUser.isPresent(), "Normal user admin@example.com should be present");
    assertFalse(
        Boolean.TRUE.equals(normalUser.get().getIsStaff()), "Normal user should not be staff");


    final Optional<User> staffUser =
        users.stream().filter(u -> "staff1@pitchtech.com".equals(u.getEmail())).findFirst();
    assertTrue(staffUser.isPresent(), "Staff user staff1@pitchtech.com should be present");
    assertTrue(Boolean.TRUE.equals(staffUser.get().getIsStaff()), "Staff user should be staff");
  }
}
