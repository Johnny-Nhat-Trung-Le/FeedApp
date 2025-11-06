package dat250.feedapp.entities;

import dat250.feedapp.securities.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "USERS")
@Node("User")
public class User implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @org.springframework.data.neo4j.core.schema.Id
    private UUID id;

    @NotNull
    @Size(min = 3)
    private String username;

    @NotNull
    @Size(min = 3)
    @Email
    private String email;

    @NotNull
    @Size(min = 3)
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role.name()));
    }


    @Override
    public void eraseCredentials() {
        password = null;
    }
}
