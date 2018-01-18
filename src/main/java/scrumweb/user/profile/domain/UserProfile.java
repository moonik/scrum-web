package scrumweb.user.profile.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter @Setter
public class UserProfile {

    @Id
    @GeneratedValue
    private Long id;

    private String firstname;
    private String lastname;
    private String photo;

    public UserProfile(String firstname, String lastname, String photo) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.photo = photo;
    }
}
