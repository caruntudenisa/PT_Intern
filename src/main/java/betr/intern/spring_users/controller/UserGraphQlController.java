package betr.intern.spring_users.controller;

import betr.intern.spring_users.model.User;
import betr.intern.spring_users.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserGraphQlController {

  private final UserService userService;

  public UserGraphQlController(final UserService userService) {
    this.userService = userService;
  }

  @QueryMapping
  public User userById(@Argument final Long id) {
    return userService.getUserById(id).orElse(null);
  }
}
