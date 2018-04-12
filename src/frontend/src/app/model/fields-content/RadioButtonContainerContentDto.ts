import { RadioButtonDto } from './RadioButtonDto';
import { FieldContentDto } from './FieldContentDto';

export class RadioButtonContainerContentDto extends FieldContentDto {
    element: RadioButtonDto;

    constructor(projectFieldId: number, projectFieldName: string, element: RadioButtonDto) {
        super(projectFieldId, projectFieldName);
        this.element = element;
    }
}
