package dat250.feedapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAuthDTO {
    private String username;
    private String password;
}
