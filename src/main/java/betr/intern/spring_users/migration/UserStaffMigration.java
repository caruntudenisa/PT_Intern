package betr.intern.spring_users.migration;

import betr.intern.spring_users.model.User;
import betr.intern.spring_users.repository.UserRepository;
import java.util.List;
import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class UserStaffMigration implements CustomTaskChange, ApplicationContextAware {

  private static ApplicationContext context;

  @Override
  public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  @Override
  public void execute(final Database database) throws CustomChangeException {
    if (context == null) {
      throw new CustomChangeException("Spring ApplicationContext has not been initialized yet!");
    }

    final UserRepository userRepository = context.getBean(UserRepository.class);
    final List<User> users = userRepository.findAll();

    for (final User user : users) {
      final String email = user.getEmail();
      final boolean isStaff = email != null && email.trim().toLowerCase().endsWith("@pitchtech.com");
      user.setIsStaff(isStaff);
      userRepository.save(user);
    }
  }

  @Override
  public String getConfirmationMessage() {
    return "Updated all users with isStaff based on their email domain.";
  }

  @Override
  public void setUp() throws SetupException {}

  @Override
  public void setFileOpener(final ResourceAccessor resourceAccessor) {}

  @Override
  public ValidationErrors validate(final Database database) {
    return null;
  }
}
