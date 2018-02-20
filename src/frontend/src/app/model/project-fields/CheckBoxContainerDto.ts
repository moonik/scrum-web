import { CheckBoxDto } from './CheckBoxDto';
import { ProjectFieldDto } from './ProjectFieldDto';

export class CheckBoxContainerDto extends ProjectFieldDto {
    checkBoxes: Array<CheckBoxDto> = [];

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean, checkBoxes: Array<CheckBoxDto>) {
        super(id, fieldType, fieldName, isRequired);
        this.checkBoxes = checkBoxes;
    }
}