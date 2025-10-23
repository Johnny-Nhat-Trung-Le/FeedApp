package dat250.feedapp.controllers;

import dat250.feedapp.dto.UserDTO;
import dat250.feedapp.entities.PollManager;
import dat250.feedapp.entities.User;
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
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private PollManager pollManager;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        User user = this.pollManager.findUser(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        User createdUser = this.pollManager.createUser(user);
        if (createdUser != null) {
            UserDTO userDTO = UserDTO.builder()
                    .username(createdUser.getUsername())
                    .email(createdUser.getEmail())
                    .build();
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdUser.getId())
                    .toUri();
            //TODO remove after debug
            System.out.println(createdUser.getId());
            return ResponseEntity.created(location).body(userDTO);
        }
        return ResponseEntity.badRequest().build();
    }

}
