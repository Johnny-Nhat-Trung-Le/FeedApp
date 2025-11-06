package dat250.feedapp.repositories.neo;

import dat250.feedapp.entities.Vote;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface NeoVoteRepository extends Neo4jRepository<Vote, UUID> {
}
