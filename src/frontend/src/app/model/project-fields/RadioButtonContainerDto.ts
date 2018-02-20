import { RadioButtonDto } from './RadioButtonDto';
import { ProjectFieldDto } from './ProjectFieldDto';

export class RadioButtonContainerDto extends ProjectFieldDto {
    radioButton: RadioButtonDto;

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean, radioButton: RadioButtonDto) {
        super(id, fieldType, fieldName, isRequired);
        this.radioButton = radioButton;
    }
}