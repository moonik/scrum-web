import { FieldContentDto } from './FieldContentDto';

export class InputFieldContentDto extends FieldContentDto {
    content: string;

    constructor(projectFieldId: number, projectFieldName: string, content: string) {
        super(projectFieldId, projectFieldName);
        this.content = content;
    }
}
