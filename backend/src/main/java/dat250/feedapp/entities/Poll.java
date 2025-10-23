package dat250.feedapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "polls")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @NotEmpty
    private String question;

    private Instant publishedAt;

    @NotNull
    @Future
    private Instant validUntil;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    public Poll(String question, Instant validUntil, User creator) {
        this.question = question;
        this.publishedAt = Instant.now();
        this.validUntil = validUntil;
        this.creator = creator;
    }
}