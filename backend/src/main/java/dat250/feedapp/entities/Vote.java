package dat250.feedapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private UUID voteOptionId;

    private Instant publishedAt;

    @NotNull
    private UUID userId;

    public Vote(UUID voteOptionId, UUID userId) {
        this.userId = userId;
        this.voteOptionId = voteOptionId;
        this.publishedAt = Instant.now();
    }
}
