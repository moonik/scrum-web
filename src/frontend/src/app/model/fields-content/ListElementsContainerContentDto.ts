import { ListElementDto } from './ListElementDto';
import { FieldContentDto } from './FieldContentDto';

export class ListElementsContainerContentDto extends FieldContentDto {
    listElements: Array<ListElementDto> = [];
}