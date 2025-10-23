package dat250.feedapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @NotEmpty
    private String question;
    @NotNull
    private Instant publishedAt;
    @NotNull
    private Instant ValidUntil;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    public Poll(String question, Instant publishedAt, Instant validUntil, User creator) {
        this.question = question;
        this.publishedAt = publishedAt;
        ValidUntil = validUntil;
        this.creator = creator;
    }
}