package dat250.feedapp.repositories;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoteService {
    private final Neo4jClient neo4jClient;

    public VoteService(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }


    public Long getVoteCountByVoteOptionId(UUID voteOptionId) {
        return neo4jClient.query("""
                OPTIONAL MATCH (:VoteOption {id: $id})--(v:Vote)
                RETURN count(v) AS c
            """)
                .bind(voteOptionId.toString()).to("id")
                .fetchAs(Long.class)
                .one()
                .orElse(0L);
    }

}
