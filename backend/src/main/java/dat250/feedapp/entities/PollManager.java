package dat250.feedapp.entities;

import dat250.feedapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PollManager {
    private final Integer FIELDSIZE = 3;
    @Autowired
    private UserRepository userRepo;

    public User findUser(UUID id) {
        Optional<User> userOpt = userRepo.findById(id);
        return userOpt.orElse(null);
    }

    /**
     * Check if email has the standard format
     *
     * @param email
     * @return
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null || email.isEmpty()) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public User createUser(User user) {

        if (isValidEmail(user.getEmail())) {
            if (userRepo.findByEmail(user.getEmail()).isEmpty()) {
                if (user.getUsername().length() > FIELDSIZE && user.getPassword().length() > FIELDSIZE) {
                    return userRepo.save(user);
                }
            }
        }
        return null;

    }
}
