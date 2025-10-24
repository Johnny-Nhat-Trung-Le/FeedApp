package dat250.feedapp.entities;

import dat250.feedapp.dto.PollDTO;
import dat250.feedapp.repositories.PollRepository;
import dat250.feedapp.repositories.UserRepository;
import dat250.feedapp.repositories.VoteOptionRepository;
import dat250.feedapp.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
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

    public User findUser(UUID id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.orElse(null);
    }

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
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


}
