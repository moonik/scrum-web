package scrumweb.user.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.UserProfileDto;
import scrumweb.user.profile.domain.UserProfile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 5, max = 30)
    private String name;

    @Size(min = 5, max = 300)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="OWNER_ID")
    @Column(unique = true)
    private UserProfile owner;

    private String icon;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<UserProfile> access;

    public Project(String name, String description, String icon, Set<UserProfile> access) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.access = access;
    }
}
