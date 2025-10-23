package dat250.feedapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @NotNull
    private UUID pollId;

    public VoteOption(String caption, Integer presentationOrder) {
        this.caption = caption;
        this.presentationOrder = presentationOrder;

    }

}
