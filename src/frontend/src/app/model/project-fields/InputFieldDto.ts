import { ProjectFieldDto } from './ProjectFieldDto';

export class InputFieldDto extends ProjectFieldDto {
    maxCharacters: number;
    minCharacters: number;

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean, maxCharacters: number, minCharacters: number) {
        super(id, fieldType, fieldName, isRequired);
        this.maxCharacters = maxCharacters;
        this.minCharacters = minCharacters;
    }
}
