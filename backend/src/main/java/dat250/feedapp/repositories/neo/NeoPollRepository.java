package dat250.feedapp.repositories.neo;

import dat250.feedapp.entities.Poll;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NeoPollRepository extends Neo4jRepository<Poll, UUID> {
}
