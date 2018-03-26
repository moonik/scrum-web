import { ListElementDto } from './ListElementDto';
import { ProjectFieldDto } from './ProjectFieldDto';

export class ListElementsContainerDto extends ProjectFieldDto {
    public elements: Array<ListElementDto> = [];

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean, elements: Array<ListElementDto>) {
        super(id, fieldType, fieldName, isRequired);
        this.elements = elements;
    }
}