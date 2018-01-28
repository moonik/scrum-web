package scrumweb.project.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class TextArea extends ProjectField {
    private int minCharacters;
    private int maxCharacters;
}
