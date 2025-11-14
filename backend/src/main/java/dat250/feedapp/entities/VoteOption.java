package dat250.feedapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "vote_options")
@Node("VoteOption")
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @org.springframework.data.neo4j.core.schema.Id
    private UUID id;

    @NotNull
    private String caption;

    @NotNull
    private Integer presentationOrder;

    @ManyToOne
    @JsonBackReference
    @Relationship(type = "IS_IN", direction = Relationship.Direction.OUTGOING)
    private Poll poll;

    @OneToMany(mappedBy = "voteOption", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Relationship(type = "HAS_VOTES", direction = Relationship.Direction.INCOMING)
    private Set<Vote> votes;

    public VoteOption(String caption, Integer presentationOrder) {
        this.caption = caption;
        this.presentationOrder = presentationOrder;
    }
}
