package dat250.feedapp.controllers;

import dat250.feedapp.dto.PollDTO;
import dat250.feedapp.entities.Poll;
import dat250.feedapp.entities.PollManager;
import dat250.feedapp.repositories.VoteOptionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Poll> createPoll(@Valid @RequestBody PollDTO pollDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        if (this.pollManager.findUser(pollDTO.getPoll().getCreator().getId()) != null) {
            try {
                this.pollManager.createPoll(pollDTO);
                System.out.println("POLLLLL underherherherhehrherehrhrhhrrerr");
                System.out.println(pollDTO.getPoll().getId());
                return ResponseEntity.ok(pollDTO.getPoll());
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().build();
            }
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
    //Get Poll (@Pathvariable PollId)
    //Get Polls()
    //Create Vote (@Pathvariable PollId, @RequestBody  Vote)
    //Update Vote (@Pathvariable PollId, @RequestBody Vote, (userId?))
}
