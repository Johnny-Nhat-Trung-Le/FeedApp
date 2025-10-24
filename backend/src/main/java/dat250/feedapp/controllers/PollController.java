package dat250.feedapp.controllers;

import dat250.feedapp.dto.PollDTO;
import dat250.feedapp.entities.Poll;
import dat250.feedapp.entities.PollManager;
import dat250.feedapp.entities.User;
import dat250.feedapp.repositories.VoteOptionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
            return ResponseEntity.badRequest().build();
        }

        if (this.pollManager.findUser(poll.getCreator().getId()) != null) {
                Poll createdPoll = this.pollManager.createPoll(poll);
                System.out.println("POLLLLL underherherherhehrherehrhrhhrrerr");

                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdPoll.getId())
                        .toUri();

                return ResponseEntity.created(location).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable UUID id) {
        if (this.pollManager.deletePoll(id)) {
            System.out.println(voteOptionRepository.findAll());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //Delete poll (Pathvariable id) Husk sjekk at det er creator som får lov til å slette

    @GetMapping("/{pollId}")
    public ResponseEntity<Poll> getPoll(@PathVariable UUID pollId){
        Poll poll = this.pollManager.findPoll(pollId);
        if (poll != null) {
            return ResponseEntity.ok(poll);
        }
        return ResponseEntity.notFound().build();
    }

    //Get Polls()
    //Create Vote (@Pathvariable PollId, @RequestBody  Vote)
    //Update Vote (@Pathvariable PollId, @RequestBody Vote, (userId?))
}
