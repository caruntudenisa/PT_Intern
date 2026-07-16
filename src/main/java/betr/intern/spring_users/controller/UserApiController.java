package betr.intern.spring_users.controller;

import betr.intern.spring_users.api.UsersApi;
import betr.intern.spring_users.mapper.UserMapper;
import betr.intern.spring_users.model.User;
import betr.intern.spring_users.model.UserStats;
import betr.intern.spring_users.model.dto.UserDto;
import betr.intern.spring_users.service.UserService;
import betr.intern.spring_users.service.UserStatsService;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApiController implements UsersApi {

  private final UserService userService;
  private final UserStatsService userStatsService;
  private final UserMapper userMapper;

  public UserApiController(
      final UserService userService,
      final UserStatsService userStatsService,
      final UserMapper userMapper) {
    this.userService = userService;
    this.userStatsService = userStatsService;
    this.userMapper = userMapper;
  }

  // Finding all the users in Json format
  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @Override
  public ResponseEntity<UserDto> updateUser(final String id, final UserDto userDto) {
    try {
      final User userDetails = userMapper.toEntity(userDto);
      final User updatedUser = userService.updateUser(id, userDetails);
      return ResponseEntity.ok(userMapper.toDto(updatedUser));
    } catch (final RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Void> deteleUser(final @PathVariable String id) {
    try {
      userService.deleteUser(id);
      return ResponseEntity.ok().build();
    } catch (final RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/stats")
  public Map<String, UserStats> getStats() {
    return userStatsService.getStats();
  }

  @PutMapping("/stats/reset")
  public ResponseEntity<Void> resetStats() {
    userStatsService.resetStats();
    return ResponseEntity.ok().build();
  }
}
