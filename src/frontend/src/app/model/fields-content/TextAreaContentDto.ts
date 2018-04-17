import { FieldContentDto } from './FieldContentDto';

export class TextAreaContentDto extends FieldContentDto {
    content: string;

    constructor(projectFieldId: number, projectFieldName: string, content: string) {
        super(projectFieldId, projectFieldName);
        this.content = content;
    }
}
