package dat250.feedapp.repositories;

import dat250.feedapp.entities.Poll;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PollRepository  extends CrudRepository<Poll, UUID> {

}
