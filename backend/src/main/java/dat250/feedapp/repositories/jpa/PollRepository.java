package dat250.feedapp.repositories.jpa;

import dat250.feedapp.entities.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PollRepository extends JpaRepository<Poll, UUID>{

}
