import { CheckBoxContainerDto } from './CheckBoxContainerDto';
import { InputFieldDto } from './InputFieldDto';
import { ListElementsContainerDto } from './ListElementsContainerDto';
import { RadioButtonContainerDto } from './RadioButtonContainerDto';
import { TextAreaDto } from './TextAreaDto';

export class ProjectFieldsCollector {
    public checkBoxContainerDtos: Array<CheckBoxContainerDto> = [];
    public inputFieldDtos: Array<InputFieldDto> = [];
    public listElementsContainerDtos: Array<ListElementsContainerDto> = [];
    public radioButtonContainerDtos: Array<RadioButtonContainerDto> = [];
    public textAreaDtos: Array<TextAreaDto> = [];
}
