package dat250.feedapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class VoteOptionDTO {
    UUID id;
    Integer count;
}

