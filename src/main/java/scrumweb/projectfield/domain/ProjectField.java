package scrumweb.projectfield.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scrumweb.dto.projectfield.ProjectFieldDto;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Entity
@NoArgsConstructor
@Getter @Setter
public abstract class ProjectField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private FieldType fieldType;

    @NotNull
    private String name;

    @NotNull
    private Boolean isRequired;

    public ProjectField(FieldType fieldType, String name, Boolean isRequired) {
        this.fieldType = fieldType;
        this.name = name;
        this.isRequired = isRequired;
    }

    public abstract void edit(ProjectField projectField);

    public enum FieldType {
        CHECKBOX,
        RADIO_BUTTON,
        INPUT_FIELD,
        TEXT_AREA,
        LIST;

        public static FieldType getFieldType(String fieldType) {
            return Arrays.stream(FieldType.values())
                    .filter(f -> f.toString().equalsIgnoreCase(fieldType))
                    .findFirst()
                    .orElse(null);
        }
    }
}
