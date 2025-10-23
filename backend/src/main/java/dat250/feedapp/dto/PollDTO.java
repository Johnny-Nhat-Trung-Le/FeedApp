package dat250.feedapp.dto;

import dat250.feedapp.entities.Poll;
import dat250.feedapp.entities.VoteOption;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PollDTO {

    private Poll poll;

    @NotNull
    @Size(min=2)
    private List<VoteOption> voteOptions;

}
