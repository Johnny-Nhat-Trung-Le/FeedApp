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
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private String question;
    @NonNull
    private Instant publishedAt;
    @NonNull
    private Instant ValidUntil;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

}