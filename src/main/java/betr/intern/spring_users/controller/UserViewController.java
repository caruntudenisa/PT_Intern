package betr.intern.spring_users.controller;

import betr.intern.spring_users.config.AppProperties;
import betr.intern.spring_users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

  private final UserService userService;
  private final AppProperties appProperties;

  @Autowired
  public UserViewController(final UserService userService, final AppProperties appProperties) {
    this.userService = userService;
    this.appProperties = appProperties;
  }

  // will return the html page with the list of users
  @GetMapping("/")
  public String listUsers(final Model model) {
    model.addAttribute("users", userService.getAllUsers());
    model.addAttribute("pageTitle", appProperties.getTitle());
    // under this attribute I will save the users
    return "users";
  }
}
