package scrumweb.project.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class RadioButtonContainer extends ProjectField {
    @OneToMany
    private Set<RadioButton> radioButtons;
}
