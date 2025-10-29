package dat250.feedapp.entities;

import dat250.feedapp.repositories.PollRepository;
import dat250.feedapp.repositories.UserRepository;
import dat250.feedapp.repositories.VoteOptionRepository;
import dat250.feedapp.repositories.VoteRepository;
import dat250.feedapp.securities.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class PollManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteOptionRepository voteOptionRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    public User findUser(UUID id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.orElse(null);
    }

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            //Encrypt the password before saving the user
            String password = user.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            return userRepository.save(user);
        }
        return null;
    }

    public Poll findPoll(UUID id) {
        Optional<Poll> pollOpt = pollRepository.findById(id);
        return pollOpt.orElse(null);
    }

    public Iterable<Poll> findPolls() {
        return pollRepository.findAll();
    }

    public Poll createPoll(Poll poll) {
        poll.setPublishedAt(Instant.now());
        return pollRepository.save(poll);
    }

    public boolean deletePoll(UUID id) {
        pollRepository.deleteById(id);
        return !pollRepository.existsById(id);
    }


    public Vote createVote(UUID pollId, Vote vote) {
        if (pollRepository.existsById(pollId) && userRepository.existsById(vote.getUserId())) {
            vote.setPublishedAt(Instant.now());
            return voteRepository.save(vote);
        }
        return null;
    }

    //TODO maybe have separate service class for this?
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        //Authentication Object
        Authentication authentication = authManager.authenticate(token);
        //Check that user and password is correct
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username);
        }
        return null;
    }
}
