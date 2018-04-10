import { ListElementDto } from './ListElementDto';
import { FieldContentDto } from './FieldContentDto';

export class ListElementsContainerContentDto extends FieldContentDto {
    listElements: Array<ListElementDto> = [];

    constructor(projectFieldId: number, projectFieldName: string, listElements: Array<ListElementDto>) {
        super(projectFieldId, projectFieldName);
        this.listElements = listElements;
    }
}
