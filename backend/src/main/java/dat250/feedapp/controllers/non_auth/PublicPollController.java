package dat250.feedapp.controllers.non_auth;

import dat250.feedapp.dto.PollRequestDTO;
import dat250.feedapp.dto.VoteOptionDTO;
import dat250.feedapp.entities.PollManager;
import dat250.feedapp.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/public/polls")
public class PublicPollController {

    @Autowired
    private PollManager pollManager;


    @GetMapping
    public ResponseEntity<List<PollRequestDTO>> getPolls() {
        List<PollRequestDTO> polls = this.pollManager.findPolls();
        return ResponseEntity.ok(polls);
    }

    @PostMapping("/{pollId}")
    public ResponseEntity<Vote> createVote(@PathVariable UUID pollId, @RequestBody Vote vote, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Vote createdVote = this.pollManager.createVote(pollId, vote);
        if (createdVote != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdVote.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{pollId}/votes")
    public ResponseEntity<List<VoteOptionDTO>> getVotes(@PathVariable UUID pollId) {
        System.out.println(pollId);
        List<VoteOptionDTO> voteOptions = this.pollManager.getVoteOptions(pollId);
        return ResponseEntity.ok(voteOptions);
    }
}
