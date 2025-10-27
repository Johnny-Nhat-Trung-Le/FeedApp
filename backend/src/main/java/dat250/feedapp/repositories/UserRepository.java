package dat250.feedapp.repositories;

import dat250.feedapp.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    List<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
