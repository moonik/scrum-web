package scrumweb.project.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scrumweb.issue.domain.IssueType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class ProjectField {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private FieldType fieldType;

    @NotNull
    private String name;

    @NotNull
    private Boolean isRequired;

    public enum FieldType {
        CHECKBOX,
        RADIO_BUTTON,
        INPUT_FIELD,
        TEXT_AREA,
        LIST
    }

}
