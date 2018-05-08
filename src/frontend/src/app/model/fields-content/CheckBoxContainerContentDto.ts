import { CheckBoxDto } from './CheckBoxDto';
import { FieldContentDto } from './FieldContentDto';

export class CheckBoxContainerContentDto extends FieldContentDto {
    elements: Array<CheckBoxDto> = [];

    constructor(projectFieldId: number, projectFieldName: string, elements: Array<CheckBoxDto>) {
        super(projectFieldId, projectFieldName);
        this.elements = elements;
    }
}
