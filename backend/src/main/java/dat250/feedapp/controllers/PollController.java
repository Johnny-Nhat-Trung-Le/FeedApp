package dat250.feedapp.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollController {

    //Create poll (@Valid @requestBody poll, BindingRes)
    //Delete poll (Pathvariable id) Husk sjekk at det er creator som får lov til å slette
    //Get Poll (@Pathvariable PollId)
    //Get Polls()
    //Create Vote (@Pathvariable PollId, @RequestBody  Vote)
    //Update Vote (@Pathvariable PollId, @RequestBody Vote, (userId?))
}
