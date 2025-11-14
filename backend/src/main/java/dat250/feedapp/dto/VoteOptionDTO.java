package dat250.feedapp.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
public class VoteOptionDTO {
    UUID id;
    Integer count;
}
