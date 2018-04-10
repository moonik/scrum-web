import { CheckBoxDto } from './CheckBoxDto';
import { FieldContentDto } from './FieldContentDto';

export class CheckBoxContainerContentDto extends FieldContentDto {
    checkBoxes: Array<CheckBoxDto> = [];

    constructor(projectFieldId: number, projectFieldName: string, checkBoxes: Array<CheckBoxDto>) {
        super(projectFieldId, projectFieldName);
        this.checkBoxes = checkBoxes;
    }
}
