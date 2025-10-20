package dat250.feedapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    private String caption;
    @NonNull
    private Integer presentationOrder;

    @ManyToOne
    @JoinColumn(name = "poll")
    @JsonBackReference
    private Poll poll;

}
