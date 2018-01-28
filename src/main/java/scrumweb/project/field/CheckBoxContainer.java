package scrumweb.project.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class CheckBoxContainer extends ProjectField{

    @OneToMany
    private Set<CheckBox> checkBoxes;
}
