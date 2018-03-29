import { CheckBoxContainerContentDto } from './CheckBoxContainerContentDto';
import { InputFieldContentDto } from './InputFieldContentDto';
import { ListElementsContainerContentDto } from './ListElementsContainerContentDto';
import { RadioButtonContainerContentDto } from './RadioButtonContainerContentDto';
import { TextAreaContentDto } from './TextAreaContentDto';

export class FieldsContentCollector {
    checkBoxContainerContentDtos: Array<CheckBoxContainerContentDto> = [];
    inputFieldContentDtos: Array<InputFieldContentDto> = [];
    listElementsContainerContentDtos: Array<ListElementsContainerContentDto> = [];
    radioButtonContainerContentDtos: Array<RadioButtonContainerContentDto> = [];
    textAreaContentDtos: Array<TextAreaContentDto> = [];
}

