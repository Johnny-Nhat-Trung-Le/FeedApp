package dat250.feedapp.repositories.jpa;

import dat250.feedapp.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    @Query(value = """
            SELECT COUNT(*) FROM VOTES WHERE USER_ID = :id
            """,
            nativeQuery = true)
    Integer getVoteByUserID(@Param("id") UUID id);

    @Query(value = """
       SELECT * FROM VOTES WHERE VOTE_OPTION_ID = :id
       """,
            nativeQuery = true)
    List<Vote> getVotesByVoteOptionID(@Param("id") UUID id);

}
