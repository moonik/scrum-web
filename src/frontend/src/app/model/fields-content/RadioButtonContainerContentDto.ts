import { RadioButtonDto } from './RadioButtonDto';
import { FieldContentDto } from './FieldContentDto';

export class RadioButtonContainerContentDto extends FieldContentDto {
    radioButton: RadioButtonDto;

    constructor(projectFieldId: number, projectFieldName: string, radioButton: RadioButtonDto) {
        super(projectFieldId, projectFieldName);
        this.radioButton = radioButton;
    }
}
