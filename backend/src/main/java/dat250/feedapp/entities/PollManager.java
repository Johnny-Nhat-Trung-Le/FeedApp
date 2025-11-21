package dat250.feedapp.entities;

import dat250.feedapp.dto.PollRequestDTO;
import dat250.feedapp.dto.VoteOptionDTO;
import dat250.feedapp.messager.PollBrokerManager;
import dat250.feedapp.messager.PollEventListener;
import dat250.feedapp.messager.PollEventPublisher;
import dat250.feedapp.repositories.VoteService;
import dat250.feedapp.repositories.jpa.PollRepository;
import dat250.feedapp.repositories.jpa.UserRepository;
import dat250.feedapp.repositories.jpa.VoteOptionRepository;
import dat250.feedapp.repositories.jpa.VoteRepository;
import dat250.feedapp.repositories.neo.NeoPollRepository;
import dat250.feedapp.repositories.neo.NeoVoteRepository;
import dat250.feedapp.securities.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PollManager {

    private final UUID anonymousID = UUID.fromString("08696b10-34b0-4741-812a-b261947c3a16");
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

    @Autowired
    private NeoVoteRepository neoVoteRepository;
    @Autowired
    private NeoPollRepository neoPollRepository;

    @Autowired
    private VoteService voteService;


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
        neoPollRepository.save(poll);
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
                return saveVote(pollId, vote);
            } else if (userRepository.existsById(vote.getUserId())) {
                if (voteRepository.getVoteByUserID(vote.getUserId()) == 0) {
                    return saveVote(pollId, vote);
                }
            }
        return null;
    }

    /**
     * save the {@link Vote} to the repository and notify message service
     *
     * @param pollId
     * @param vote
     * @return created Vote
     */
    private Vote saveVote(UUID pollId, Vote vote) {
        vote.setPublishedAt(Instant.now());
        voteRepository.save(vote);
        neoVoteRepository.save(vote);
        pollEventPublisher.publishVote(pollId, vote);
        return vote;
    }

    public Vote updateVote(UUID pollId, Vote vote) {
        if (pollRepository.existsById(pollId) && userRepository.existsById(vote.getUserId())) {
            neoVoteRepository.deleteVoteById(vote.getId());
            return saveVote(pollId, vote);
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

    public List<VoteOptionDTO> getVoteOptions(UUID pollId) {
        List<VoteOptionDTO> voteOptions = new ArrayList<>();
        Poll poll = pollRepository.findById(pollId).orElse(null);
        if (poll == null) {
            return voteOptions;
        }

        Set<VoteOption> set = poll.getOptions();
        for (VoteOption vo : set) {
            int count = getNeoVoteCount(vo.getId()).intValue();
            if (count > 0) {
                System.out.println("THIS IS CACHE");
                VoteOptionDTO dto = VoteOptionDTO.builder()
                        .id(vo.getId())
                        .count(count)
                        .build();
                voteOptions.add(dto);
            } else {
                System.out.println("THIS IS H2");
                List<Vote> h2Votes = voteRepository.getVotesByVoteOptionID(vo.getId());
                VoteOptionDTO dto = VoteOptionDTO.builder()
                        .id(vo.getId())
                        .count(h2Votes.size())
                        .build();
                voteOptions.add(dto);
            }
        }
        return voteOptions;
    }

    private Long getNeoVoteCount(UUID id) {
        return voteService.getVoteCountByVoteOptionId(id);
    }

    public UUID getUserId(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return user.getId();
        }
        return null;
    }
}
