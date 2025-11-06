package dat250.feedapp.repositories.neo;

import dat250.feedapp.entities.Poll;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface NeoPollRepository extends Neo4jRepository<Poll, UUID> {
}
