import { CheckBoxContainerContentDto } from './CheckBoxContainerContentDto';
import { InputFieldContentDto } from './InputFieldContentDto';
import { ListElementsContainerContentDto } from './ListElementsContainerContentDto';
import { RadioButtonContainerContentDto } from './RadioButtonContainerContentDto';
import { TextAreaContentDto } from './TextAreaContentDto';

export class FieldsContentCollector {
    public checkBoxContainerContentDtos: Array<CheckBoxContainerContentDto> = [];
    public inputFieldContentDtos: Array<InputFieldContentDto> = [];
    public listElementsContainerContentDtos: Array<ListElementsContainerContentDto> = [];
    public radioButtonContainerContentDtos: Array<RadioButtonContainerContentDto> = [];
    public textAreaContentDtos: Array<TextAreaContentDto> = [];
}
