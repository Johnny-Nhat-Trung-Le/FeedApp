package dat250.feedapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private UUID voteOptionId;
    @NonNull
    private Instant publishedAt;
    @NonNull
    private UUID userId;

}
