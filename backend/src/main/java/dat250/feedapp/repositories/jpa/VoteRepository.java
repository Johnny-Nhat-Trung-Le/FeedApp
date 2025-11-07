package dat250.feedapp.repositories.jpa;

import dat250.feedapp.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {

    @Query(value = """
            SELECT COUNT(*) FROM VOTES WHERE USER_ID = :id
            """,
            nativeQuery = true)
    Integer getVoteByUserID(@Param("id") UUID id);

    @Query(value = """
            SELECT * FROM VOTES WHERE ID = (SELECT VOTES.ID  FROM POLLS, VOTES
                        WHERE POLLS.id = :pollId 
                        AND USER_ID = :userId) 
            LIMIT 1
            """,
            nativeQuery = true)
    Vote getOldVoteId(@Param("pollId") UUID pollId, @Param("userId") UUID userId);
}
