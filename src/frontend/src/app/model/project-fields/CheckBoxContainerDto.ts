import { CheckBoxDto } from './CheckBoxDto';
import { ProjectFieldDto } from './ProjectFieldDto';

export class CheckBoxContainerDto extends ProjectFieldDto {
    elements: Array<CheckBoxDto> = [];

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean, elements: Array<CheckBoxDto>) {
        super(id, fieldType, fieldName, isRequired);
        this.elements = elements;
    }
}
