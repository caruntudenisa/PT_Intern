package betr.intern.spring_users.repository;

import betr.intern.spring_users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  // we declared the User that specifies the type of object is saved in this repository and the ID
}
