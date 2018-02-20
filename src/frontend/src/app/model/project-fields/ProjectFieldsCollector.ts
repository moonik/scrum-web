import { CheckBoxContainerDto } from './CheckBoxContainerDto';
import { InputFieldDto } from './InputFieldDto';
import { ListElementsContainerDto } from './ListElementsContainerDto';
import { RadioButtonContainerDto } from './RadioButtonContainerDto';
import { TextAreaDto } from './TextAreaDto';

export class ProjectFieldsCollector {
    checkBoxContainerDtos: Array<CheckBoxContainerDto> = [];
    inputFieldDtos: Array<InputFieldDto> = [];
    listElementsContainerDtos: Array<ListElementsContainerDto> = [];
    radioButtonContainerDtos: Array<RadioButtonContainerDto> = [];
    textAreaDtos: Array<TextAreaDto> = [];
}