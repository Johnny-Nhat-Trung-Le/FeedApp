package dat250.feedapp.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "polls")
@Node("Poll")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @org.springframework.data.neo4j.core.schema.Id
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

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private Set<VoteOption> options;

    public Poll(String question, Instant validUntil, User creator) {
        this.question = question;
        this.validUntil = validUntil;
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "validUntil=" + validUntil +
                ", publishedAt=" + publishedAt +
                ", question='" + question + '\'' +
                ", id=" + id +
                '}';
    }
}