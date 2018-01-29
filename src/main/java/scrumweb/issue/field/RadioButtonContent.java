package scrumweb.issue.field;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import scrumweb.project.field.RadioButton;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class RadioButtonContent extends FieldContent {

    @OneToOne
    private RadioButton radioButton;
}
