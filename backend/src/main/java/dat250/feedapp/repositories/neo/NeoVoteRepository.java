package dat250.feedapp.repositories.neo;

import dat250.feedapp.entities.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface NeoVoteRepository extends Neo4jRepository<Vote, UUID> {

    @Query(value = """
                 MATCH (v:Vote {id: $id})
                 DETACH DELETE v
            """,
            nativeQuery = true)
    void deleteVoteById(@Param("id") UUID id);
}
