package dat250.feedapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "votes")
@Node("Vote")
@Data
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @org.springframework.data.neo4j.core.schema.Id
    private UUID id;

    private Instant publishedAt;

    @NotNull
    private UUID userId;

    @NotNull
    @ManyToOne
    @JsonBackReference
    @Relationship(type = "VOTES_ON", direction = Relationship.Direction.OUTGOING)
    private VoteOption voteOption;

}
