import { RadioButtonDto } from './RadioButtonDto';
import { ProjectFieldDto } from './ProjectFieldDto';

export class RadioButtonContainerDto extends ProjectFieldDto {
    elements: Array<RadioButtonDto> = [];

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean, elements: Array<RadioButtonDto>) {
        super(id, fieldType, fieldName, isRequired);
        this.elements = elements;
    }
}
