import { ListElementDto } from './ListElementDto';
import { ProjectFieldDto } from './ProjectFieldDto';

export class ListElementsContainerDto extends ProjectFieldDto {
    public listElements: Array<ListElementDto> = [];

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean, listElements: Array<ListElementDto>) {
        super(id, fieldType, fieldName, isRequired);
        this.listElements = listElements;
    }
}