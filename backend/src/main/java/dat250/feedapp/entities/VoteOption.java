package dat250.feedapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "voteOptions")
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String caption;

    @NotNull
    private Integer presentationOrder;

    @ManyToOne
    @JoinColumn(name = "poll")
    @JsonBackReference
    private Poll poll;

    public VoteOption(String caption, Integer presentationOrder) {
        this.caption = caption;
        this.presentationOrder = presentationOrder;
    }
}
