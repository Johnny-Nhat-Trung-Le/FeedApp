package dat250.feedapp.repositories;

import dat250.feedapp.entities.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VoteRepository extends CrudRepository<Vote, UUID> {
}
