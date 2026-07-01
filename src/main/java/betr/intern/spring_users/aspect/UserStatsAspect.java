package betr.intern.spring_users.aspect;

import betr.intern.spring_users.model.User;
import betr.intern.spring_users.service.UserStatsService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component

public class UserStatsAspect {

    private final UserStatsService userStatsService;

    @Autowired
    public UserStatsAspect(UserStatsService userStatsService) {
        this.userStatsService = userStatsService;
    }

    @AfterReturning(
            pointcut = "execution(* betr.intern.spring_users.service.UserService.getUserById(..))",
            returning = "result"
    )

    public void trackUserLookup(Object result) {

        if (result instanceof Optional) {
            Optional<?> optionalUser = (Optional<?>) result;

            if (optionalUser.isPresent() && optionalUser.get() instanceof User) {
                User user = (User) optionalUser.get();
                userStatsService.incrementCount(user.getName());
            }
        }
    }
}
