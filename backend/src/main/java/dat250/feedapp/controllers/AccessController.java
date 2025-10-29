package dat250.feedapp.controllers;

import dat250.feedapp.dto.UserAuthDTO;
import dat250.feedapp.entities.PollManager;
import dat250.feedapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AccessController {
    @Autowired
    private PollManager pollManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserAuthDTO userAuthDTO) {
        String user = userAuthDTO.getUsername();
        String password = userAuthDTO.getPassword();
        String sessionToken = this.pollManager.login(user, password);
        if (sessionToken != null) {
            return ResponseEntity.ok(sessionToken);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody User user) {
        //TODO take code from UserController over here
        return ResponseEntity.ok().build();
    }
}
