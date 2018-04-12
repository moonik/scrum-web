import { ListElementDto } from './ListElementDto';
import { FieldContentDto } from './FieldContentDto';

export class ListElementsContainerContentDto extends FieldContentDto {
    elements: Array<ListElementDto> = [];

    constructor(projectFieldId: number, projectFieldName: string, elements: Array<ListElementDto>) {
        super(projectFieldId, projectFieldName);
        this.elements = elements;
    }
}
