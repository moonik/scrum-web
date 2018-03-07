import { RadioButtonDto } from './RadioButtonDto';
import { ProjectFieldDto } from './ProjectFieldDto';

export class RadioButtonContainerDto extends ProjectFieldDto {
    public radioButtons: Array<RadioButtonDto> = [];

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean, radioButtons: Array<RadioButtonDto>) {
        super(id, fieldType, fieldName, isRequired);
        this.radioButtons = radioButtons;
    }
}