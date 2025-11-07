package dat250.feedapp.repositories;

import dat250.feedapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    // TODO find a way to use this??
    //Optional<User> findByUsernameAndPassword(String username, String password);
}
