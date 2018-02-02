package scrumweb.issue.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.projectfield.domain.ProjectField;

import javax.persistence.Entity;

@Entity
@Getter @Setter
@NoArgsConstructor
public class InputFieldContent extends FieldContent {

    private String content;

    public InputFieldContent(ProjectField projectField, String content) {
        super(projectField);
        this.content = content;
    }
}
