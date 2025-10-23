package dat250.feedapp.repositories;

import dat250.feedapp.entities.VoteOption;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface VoteOptionRepository extends CrudRepository<VoteOption, UUID> {
    @Modifying
    @Query(value = """
            DELETE FROM VOTE_OPTIONS WHERE POLL_ID = :id
            """,
            nativeQuery = true)
    void deleteVoteOptions(@Param("id") UUID id);


}
