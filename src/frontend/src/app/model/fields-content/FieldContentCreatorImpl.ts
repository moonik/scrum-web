import { CheckBoxContainerContentDto } from './CheckBoxContainerContentDto';
import { InputFieldContentDto } from './InputFieldContentDto';
import { ListElementsContainerContentDto } from './ListElementsContainerContentDto';
import { RadioButtonContainerContentDto } from './RadioButtonContainerContentDto';
import { TextAreaContentDto } from './TextAreaContentDto';
import { FieldsContentCollector } from './FieldsContentCollector';
import { Injectable } from '@angular/core';
import { FieldCreator } from '../../shared/field-creator';

import * as fieldTypes from '../../constants/field-type';
import { FieldContentDto } from './FieldContentDto';

@Injectable()
export class FieldContentCreatorImpl implements FieldCreator<FieldContentDto> {

    private fieldTypes = fieldTypes.default;
    fieldContentCollector: FieldsContentCollector = new FieldsContentCollector();

    constructor() {}

    createField(field: any): void {
        if (field.fieldType === this.fieldTypes.inputField) {
            this.fieldContentCollector.inputFieldContentDtos.push(this.createInputField(field));
        } else if (field.fieldType === this.fieldTypes.textArea) {
            this.fieldContentCollector.textAreaContentDtos.push(this.createTextArea(field));
        } else if (field.fieldType === this.fieldTypes.checkBox && this.filterOutNotSelected(field).length > 0) {
            this.fieldContentCollector.checkBoxContainerContentDtos.push(this.createCheckBoxContainer(field));
        } else if (field.fieldType === this.fieldTypes.list && this.findSelected(field, field.selected) !== null) {
            console.log(field);
            this.fieldContentCollector.listElementsContainerContentDtos.push(this.createListElementsContainer(field));
        } else if (field.fieldType === this.fieldTypes.radioButton && this.findSelected(field, field.selected) !== null) {
            this.fieldContentCollector.radioButtonContainerContentDtos.push(this.createRadioButtonContainer(field));
        }
    }

    public createInputField(field: any): InputFieldContentDto {
        return new InputFieldContentDto(
            field.id,
            field.fieldName,
            field.content
        );
    }

    public createTextArea(field: any): TextAreaContentDto {
        return new TextAreaContentDto(
            field.id,
            field.fieldName,
            field.content
        );
    }

    public createCheckBoxContainer(field: any): CheckBoxContainerContentDto {
        const selectedItems = this.filterOutNotSelected(field);
        return new CheckBoxContainerContentDto(
            field.id,
            field.fieldName,
            this.filterOutNotSelected(field)
        );
    }

    public createRadioButtonContainer(field: any): RadioButtonContainerContentDto {
        return new RadioButtonContainerContentDto(
            field.id,
            field.fieldName,
            this.findSelected(field, field.selected)
        );
    }

    public createListElementsContainer(field: any): ListElementsContainerContentDto {
        const selected = [this.findSelected(field, field.selected)];
        return new ListElementsContainerContentDto(
            field.id,
            field.fieldName,
            selected
        );
    }

    private filterOutNotSelected(field: any) {
        return field.elements.filter(e => e.selected);
    }

    private findSelected(field: any, selected: number) {
        return field.elements.find(e => e.id === selected);
    }
}
