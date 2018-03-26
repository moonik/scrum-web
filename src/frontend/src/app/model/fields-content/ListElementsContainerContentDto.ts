import { ListElementDto } from './ListElementDto';
import { FieldContentDto } from './FieldContentDto';

export class ListElementsContainerContentDto extends FieldContentDto {
    public listElements: Array<ListElementDto> = [];
}