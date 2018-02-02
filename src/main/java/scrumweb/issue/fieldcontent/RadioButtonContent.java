package scrumweb.issue.fieldcontent;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.projectfield.domain.ProjectField;
import scrumweb.projectfield.domain.RadioButton;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RadioButtonContent extends FieldContent {

    @OneToOne
    private RadioButton radioButton;

    public RadioButtonContent(ProjectField projectField, RadioButton radioButton) {
        super(projectField);
        this.radioButton = radioButton;
    }
}
