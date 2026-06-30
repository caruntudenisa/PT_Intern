package betr.intern.spring_users.service;


import betr.intern.spring_users.model.User;
import betr.intern.spring_users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    public User createUser(User user){
        return userRepository.save(user);
    }

    public  List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public  Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }


    public  User updateUser(Long id, User userDetails){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("The User doesn't exist"+id));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());

        return userRepository.save(user);

    }

    public void deleteUser(Long id){

        if(!userRepository.existsById(id)){
            throw new RuntimeException("There is no user with this id!");
        }
        userRepository.deleteById(id);
    }

}
