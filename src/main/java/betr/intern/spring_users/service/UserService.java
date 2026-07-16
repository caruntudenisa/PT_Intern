package betr.intern.spring_users.service;

import betr.intern.spring_users.model.User;
import betr.intern.spring_users.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(final User user) {
    return userRepository.save(user);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(final String id) {
    return userRepository.findById(id);
  }

  public User updateUser(final String id, final User userDetails) {
    final User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("The User doesn't exist" + id));

    user.setName(userDetails.getName());
    user.setEmail(userDetails.getEmail());

    return userRepository.save(user);
  }

  public void deleteUser(final String id) {

    if (!userRepository.existsById(id)) {
      throw new RuntimeException("There is no user with this id!");
    }
    userRepository.deleteById(id);
  }
}
