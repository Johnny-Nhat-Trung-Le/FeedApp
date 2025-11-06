package dat250.feedapp.controllers.non_auth;

import dat250.feedapp.dto.UserAuthDTO;
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

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class AccessController {
    @Autowired
    private PollManager pollManager;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserAuthDTO userAuthDTO) {
        String user = userAuthDTO.getUsername();
        String password = userAuthDTO.getPassword();
        String sessionToken = this.pollManager.login(user, password);
        if (sessionToken != null) {

            return ResponseEntity.ok(sessionToken);
        }
        //will never occur if there is bad request, since authentication manager throws 401 response code
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        User createdUser = this.pollManager.createUser(user);
        if (createdUser != null) {
            UserDTO userDTO = UserDTO.builder().username(createdUser.getUsername()).email(createdUser.getEmail()).build();
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
            //TODO remove after debug
            System.out.println(createdUser.getId());
            return ResponseEntity.created(location).body(userDTO);
        }
        return ResponseEntity.badRequest().build();
    }

}
