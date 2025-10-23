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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createPoll(PollDTO pollDTO) {
        Poll poll = pollDTO.getPoll();
        Poll createdPoll = pollRepository.save(poll);
        List<VoteOption> voteOptions = pollDTO.getVoteOptions();
        for (VoteOption vo : voteOptions) {
            vo.setPollId(createdPoll.getId());
        }
        voteOptionRepository.saveAll(voteOptions);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deletePoll(UUID id) {
        voteOptionRepository.deleteVoteOptions(id);
        pollRepository.deleteById(id);
        return !(pollRepository.existsById(id));
    }


}
