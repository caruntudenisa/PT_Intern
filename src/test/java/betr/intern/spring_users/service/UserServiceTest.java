package betr.intern.spring_users.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import betr.intern.spring_users.model.User;
import betr.intern.spring_users.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserService userService;

  @Test
  void testCreateUser() {
    final User user = new User("test@gmail.com", "Test User");
    final User savedUser = new User("test@gmail.com", "Test User");
    savedUser.setId("123");

    when(this.userRepository.save(user)).thenReturn(savedUser);

    final User result = this.userService.createUser(user);

    assertNotNull(result);
    assertEquals("123", result.getId());
    assertEquals("test@gmail.com", result.getEmail());
    assertEquals("Test User", result.getName());
    verify(this.userRepository, times(1)).save(user);
  }

  @Test
  void testGetAllUsers() {
    final User u1 = new User("u1@gmail.com", "U1");
    final User u2 = new User("u2@gmail.com", "U2");
    final List<User> users = Arrays.asList(u1, u2);

    when(this.userRepository.findAll()).thenReturn(users);

    final List<User> result = this.userService.getAllUsers();

    assertEquals(2, result.size());
    assertEquals("u1@gmail.com", result.get(0).getEmail());
    assertEquals("u2@gmail.com", result.get(1).getEmail());
    verify(this.userRepository, times(1)).findAll();
  }

  @Test
  void testGetUserById_Found() {
    final User user = new User("test@gmail.com", "Test");
    user.setId("123");

    when(this.userRepository.findById("123")).thenReturn(Optional.of(user));

    final Optional<User> result = this.userService.getUserById("123");

    assertTrue(result.isPresent());
    assertEquals("123", result.get().getId());
    verify(this.userRepository, times(1)).findById("123");
  }

  @Test
  void testGetUserById_NotFound() {
    when(this.userRepository.findById("123")).thenReturn(Optional.empty());

    final Optional<User> result = this.userService.getUserById("123");

    assertFalse(result.isPresent());
    verify(this.userRepository, times(1)).findById("123");
  }

  @Test
  void testUpdateUser_Success() {
    final User existingUser = new User("old@gmail.com", "Old Name");
    existingUser.setId("123");

    final User userDetails = new User("new@gmail.com", "New Name");

    final User updatedUser = new User("new@gmail.com", "New Name");
    updatedUser.setId("123");

    when(this.userRepository.findById("123")).thenReturn(Optional.of(existingUser));
    when(this.userRepository.save(existingUser)).thenReturn(updatedUser);

    final User result = this.userService.updateUser("123", userDetails);

    assertNotNull(result);
    assertEquals("123", result.getId());
    assertEquals("new@gmail.com", result.getEmail());
    assertEquals("New Name", result.getName());
    verify(this.userRepository, times(1)).findById("123");
    verify(this.userRepository, times(1)).save(existingUser);
  }

  @Test
  void testUpdateUser_NotFound() {
    final User userDetails = new User("new@gmail.com", "New Name");

    when(this.userRepository.findById("123")).thenReturn(Optional.empty());

    final RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              this.userService.updateUser("123", userDetails);
            });

    assertEquals("The User doesn't exist123", exception.getMessage());
    verify(this.userRepository, times(1)).findById("123");
    verify(this.userRepository, never()).save(any(User.class));
  }

  @Test
  void testDeleteUser_Success() {
    when(this.userRepository.existsById("123")).thenReturn(true);
    doNothing().when(this.userRepository).deleteById("123");

    assertDoesNotThrow(() -> this.userService.deleteUser("123"));

    verify(this.userRepository, times(1)).existsById("123");
    verify(this.userRepository, times(1)).deleteById("123");
  }

  @Test
  void testDeleteUser_NotFound() {
    when(this.userRepository.existsById("123")).thenReturn(false);

    final RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              this.userService.deleteUser("123");
            });

    assertEquals("There is no user with this id!", exception.getMessage());
    verify(this.userRepository, times(1)).existsById("123");
    verify(this.userRepository, never()).deleteById(anyString());
  }
}
