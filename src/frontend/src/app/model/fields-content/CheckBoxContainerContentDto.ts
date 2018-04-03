import { CheckBoxDto } from './CheckBoxDto';
import { FieldContentDto } from './FieldContentDto';

export class CheckBoxContainerContentDto extends FieldContentDto {
    public checkBoxes: Array<CheckBoxDto> = [];
}
