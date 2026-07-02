package betr.intern.spring_users.aspect;

import betr.intern.spring_users.model.User;
import betr.intern.spring_users.service.UserStatsService;
import java.util.Optional;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserStatsAspect {

  private final UserStatsService userStatsService;

  @Autowired
  public UserStatsAspect(final UserStatsService userStatsService) {
    this.userStatsService = userStatsService;
  }

  @AfterReturning(
      pointcut = "execution(* betr.intern.spring_users.service.UserService.getUserById(..))",
      returning = "result")
  public void trackUserLookup(final Object result) {

    if (result instanceof Optional) {
      final Optional<?> optionalUser = (Optional<?>) result;

      if (optionalUser.isPresent() && optionalUser.get() instanceof User) {
        final User user = (User) optionalUser.get();
        userStatsService.incrementCount(user.getId());
      }
    }
  }
}
