package dat250.feedapp.controllers;

import dat250.feedapp.entities.Poll;
import dat250.feedapp.entities.PollManager;
import dat250.feedapp.entities.Vote;
import dat250.feedapp.messager.PollEventPublisher;
import dat250.feedapp.repositories.VoteOptionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollController {

    @Autowired
    private PollManager pollManager;

    @Autowired
    private VoteOptionRepository voteOptionRepository;


    @PostMapping
    public ResponseEntity<Poll> createPoll(@Valid @RequestBody Poll poll, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("error is here");
            System.out.println(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().build();
        }

        if (this.pollManager.findUser(poll.getCreator().getId()) != null) {
            Poll createdPoll = this.pollManager.createPoll(poll);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdPoll.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.badRequest().build();
    }

    //Delete poll (Pathvariable id) Husk sjekk at det er creator som får lov til å slette
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable UUID id) {
        if (this.pollManager.deletePoll(id)) {
            System.out.println(voteOptionRepository.findAll());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{pollId}")
    public ResponseEntity<Poll> getPoll(@PathVariable UUID pollId) {
        Poll poll = this.pollManager.findPoll(pollId);
        if (poll != null) {
            return ResponseEntity.ok(poll);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Poll>> getPolls() {
        Iterable<Poll> polls = this.pollManager.findPolls();
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

    @PutMapping("/{pollId}")
    public ResponseEntity<Vote> updateVote(@PathVariable UUID pollId, @RequestBody Vote vote, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Vote updatedVote = this.pollManager.createVote(pollId, vote);
        if (updatedVote != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
