package dat250.feedapp.dto;

import dat250.feedapp.entities.VoteOption;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PollRequestDTO {
    private UUID id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private String creator;
    private Set<VoteOption> options;
}
