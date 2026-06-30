package betr.intern.spring_users.controller;

import betr.intern.spring_users.service.UserStatsService;
import betr.intern.spring_users.model.User;
import betr.intern.spring_users.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserApiController {


    private final UserService userService;
    private final UserStatsService userStatsService;

    public UserApiController(UserService userService, UserStatsService userStatsService) {
        this.userService = userService;
        this.userStatsService = userStatsService;
    }

    //Finding all the users in Json format
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    //Finding one specific user in Json format
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //The update of an user
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails){
        try{
            User updatedUser = userService.updateUser(id,userDetails);
            return ResponseEntity.ok(updatedUser);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deteleUser(@PathVariable Long id){
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
        catch(RuntimeException e){
            return ResponseEntity.notFound().build(); }
    }

    @GetMapping("/stats")
    public Map<String, Integer> getStats() {
        return userStatsService.getStats();
    }

    @PutMapping("/stats/reset")
    public ResponseEntity<Void> resetStats() {
        userStatsService.resetStats();
        return ResponseEntity.ok().build();
    }

}
