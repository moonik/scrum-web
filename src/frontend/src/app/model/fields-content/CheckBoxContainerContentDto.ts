import { CheckBoxDto } from './CheckBoxDto';
import { FieldContentDto } from './FieldContentDto';

export class CheckBoxContainerContentDto extends FieldContentDto {
    checkBoxes: Array<CheckBoxDto> = [];
}