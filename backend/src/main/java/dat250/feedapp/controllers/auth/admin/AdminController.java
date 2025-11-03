package dat250.feedapp.controllers.auth.admin;

import dat250.feedapp.dto.PollRequestDTO;
import dat250.feedapp.entities.PollManager;
import dat250.feedapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin/")
public class AdminController {
    @Autowired
    private PollManager pollManager;

    @GetMapping("secret")
    public String secret() {
        return "This is a secret message only for ADMIN";
    }

    @GetMapping("polls/{pollId}")
    public ResponseEntity<PollRequestDTO> getPoll(@PathVariable UUID pollId) {
        PollRequestDTO poll = this.pollManager.findPoll(pollId);
        if (poll != null) {
            return ResponseEntity.ok(poll);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        User user = this.pollManager.findUser(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}
