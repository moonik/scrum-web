package scrumweb.user.profile.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.project.domain.Project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 10)
    private String username;

    @NotNull
    @Size(min = 3, max = 10)
    private String firstname;

    @NotNull
    @Size(min = 3, max = 10)
    private String lastname;

    private String photo;

    public UserProfile(String username, String firstname, String lastname, String photo) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.photo = photo;
    }
}
