package dat250.feedapp.entities;

import dat250.feedapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PollManager {
    @Autowired
    private UserRepository userRepo;

    public User findUser(UUID id) {
        Optional<User> userOpt = userRepo.findById(id);
        return userOpt.orElse(null);
    }

    public User createUser(User user) {
        if (userRepo.findByEmail(user.getEmail()).isEmpty()) {
            return userRepo.save(user);
        }
        return null;

    }
}
