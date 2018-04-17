import { ProjectFieldDto } from './ProjectFieldDto';

export class TextAreaDto extends ProjectFieldDto {
    maxCharacters: number;

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean, maxCharacters: number) {
        super(id, fieldType, fieldName, isRequired);
        this.maxCharacters = maxCharacters;
    }
}
