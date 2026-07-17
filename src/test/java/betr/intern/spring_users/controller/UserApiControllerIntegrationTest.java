package betr.intern.spring_users.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import betr.intern.spring_users.model.User;
import betr.intern.spring_users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class UserApiControllerIntegrationTest {

  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

  @DynamicPropertySource
  static void setProperties(final DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    registry.add("spring.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Autowired private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    this.userRepository.deleteAll();
  }

  @Test
  void testGetAllUsers_ReturnsUsers() throws Exception {
    final User u1 = new User("alice@gmail.com", "Alice");
    final User u2 = new User("bob@gmail.com", "Bob");
    this.userRepository.save(u1);
    this.userRepository.save(u2);

    this.mockMvc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].name", is("Alice")))
        .andExpect(jsonPath("$[1].name", is("Bob")));
  }

  @Test
  void testUpdateUser_Success() throws Exception {
    final User user = new User("old@gmail.com", "OldName");
    final User saved = this.userRepository.save(user);

    final String requestJson =
        "{"
            + "\"name\":\"NewName\","
            + "\"email\":\"new@gmail.com\","
            + "\"isStaff\":false"
            + "}";

    this.mockMvc
        .perform(
            put("/user/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("NewName")))
        .andExpect(jsonPath("$.email", is("new@gmail.com")));
  }

  @Test
  void testUpdateUser_NotFound() throws Exception {
    final String requestJson =
        "{"
            + "\"name\":\"NewName\","
            + "\"email\":\"new@gmail.com\","
            + "\"isStaff\":false"
            + "}";

    this.mockMvc
        .perform(
            put("/user/non-existent-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isNotFound());
  }

  @Test
  void testDeleteUser_Success() throws Exception {
    final User user = new User("delete@gmail.com", "ToDelete");
    final User saved = this.userRepository.save(user);

    this.mockMvc.perform(delete("/users/" + saved.getId())).andExpect(status().isOk());

    assertFalse(this.userRepository.findById(saved.getId()).isPresent());
  }

  @Test
  void testDeleteUser_NotFound() throws Exception {
    this.mockMvc.perform(delete("/users/non-existent-id")).andExpect(status().isNotFound());
  }
}
