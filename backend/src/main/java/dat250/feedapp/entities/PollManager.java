package dat250.feedapp.entities;

import dat250.feedapp.dto.PollRequestDTO;
import dat250.feedapp.messager.PollBrokerManager;
import dat250.feedapp.messager.PollEventListener;
import dat250.feedapp.messager.PollEventPublisher;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private PollBrokerManager pollBrokerManager;

    @Autowired
    private PollEventListener pollEventListener;

    @Autowired
    private PollEventPublisher pollEventPublisher;

    private UUID anonymousID = UUID.fromString("08696b10-34b0-4741-812a-b261947c3a16");

    public User findUser(UUID id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.orElse(null);
    }

    public PollRequestDTO findPoll(UUID id) {

        Optional<Poll> pollOpt = pollRepository.findById(id);

        if (pollOpt.isPresent()) {
            return convertPollToDTO(pollOpt.get());
        }
        return null;
    }

    private PollRequestDTO convertPollToDTO(Poll poll) {
        return PollRequestDTO.builder()
                .id(poll.getId())
                .question(poll.getQuestion())
                .publishedAt(poll.getPublishedAt())
                .validUntil(poll.getValidUntil())
                .creator(poll.getCreator().getId().toString())
                .options(poll.getOptions())
                .build();

    }

    public List<PollRequestDTO> findPolls() {
        return pollRepository.findAll().stream().map(this::convertPollToDTO).collect(Collectors.toList());
    }

    public Poll createPoll(Poll poll) {
        poll.setPublishedAt(Instant.now());
        pollRepository.save(poll);
        pollBrokerManager.registerQueue(poll.getId());
        pollEventListener.registerListener(poll.getId());
        return poll;
    }

    public boolean deletePoll(UUID id, String token) {
        Optional<Poll> maybePoll = pollRepository.findById(id);
        // To avoid cyclic dependency
        String tokenUser = jwtService.getUsernameByToken(token);
        if (maybePoll.isPresent()) {
            Poll poll = maybePoll.get();
            if (poll.getCreator().getUsername().equals(tokenUser)) {
                pollRepository.deleteById(id);
                return !pollRepository.existsById(id);
            }
        }
        return false;
    }


    public Vote createVote(UUID pollId, Vote vote) {
        if (pollRepository.existsById(pollId))
            //anonymous voting
            if (vote.getUserId() == null) {
                vote.setUserId(anonymousID);
                return saveVote(pollId,vote);
            } else if (userRepository.existsById(vote.getUserId())) {
                if (voteRepository.getVoteByUserID(vote.getUserId()) == 0) {
                    return saveVote(pollId,vote);
                }
            }
        return null;
    }

    /**
     * save the {@link Vote} to the repository and notify message service
     * @param pollId
     * @param vote
     * @return created Vote
     */
    private Vote saveVote(UUID pollId, Vote vote){
        vote.setPublishedAt(Instant.now());
        voteRepository.save(vote);
        pollEventPublisher.publishVote(pollId, vote);
        return vote;
    }
    public Vote updateVote(UUID pollId, Vote vote) {
        if (pollRepository.existsById(pollId) && userRepository.existsById(vote.getUserId())) {
            vote.setPublishedAt(Instant.now());
            voteRepository.save(vote);
            pollEventPublisher.publishVote(pollId, vote);
            return vote;
        }
        return null;
    }


    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()
                && userRepository.findByUsername(user.getUsername()).isEmpty()) {
            //Encrypt the password before saving the user
            String password = user.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            return userRepository.save(user);
        }
        return null;
    }

    public String login(String username, String password) {
        //Specify what type of authentication token we want to use
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                password
        );
        //Authentication Object
        Authentication authentication = authManager.authenticate(token);
        //Check that user and password matches
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username);
        }
        return null;
    }

    public UUID getUserId(String username, String password) {
        // TODO FIX
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return user.getId();
        }
        return null;
    }
}
