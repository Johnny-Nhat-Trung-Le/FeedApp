package dat250.feedapp.repositories;

import dat250.feedapp.entities.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VoteOptionRepository extends JpaRepository<VoteOption, UUID> {
    @Modifying
    @Query(value = """
            DELETE FROM VOTE_OPTIONS WHERE POLL_ID = :id
            """,
            nativeQuery = true)
    void deleteVoteOptions(@Param("id") UUID id);

    @Query(value = """
            SELECT * FROM VOTE_OPTIONS WHERE POLL_ID = :pollId
            """,
            nativeQuery = true)
    List<VoteOption> getVoteOptionsByPollId(@Param("pollId") UUID pollId);
}

