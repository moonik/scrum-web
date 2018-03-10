import { ProjectFieldDto } from '../model/project-fields/ProjectFieldDto';
import { CheckBoxContainerDto } from '../model/project-fields/CheckBoxContainerDto';
import { InputFieldDto } from '../model/project-fields/InputFieldDto';
import { ListElementsContainerDto } from '../model/project-fields/ListElementsContainerDto';
import { RadioButtonContainerDto } from '../model/project-fields/RadioButtonContainerDto';
import { TextAreaDto } from '../model/project-fields/TextAreaDto';
import { Injectable } from '@angular/core';

import * as fieldTypes from '../constants/field-type';

@Injectable()
export class FieldCreator {

    private fieldTypes = fieldTypes.default;

    constructor(){}

    public createField(formData: any, id: number): ProjectFieldDto {
        if (formData.fieldType === this.fieldTypes.inputField) {
            return this.createInputField(formData, id);
        } else if (formData.fieldType === this.fieldTypes.textArea) {
            return this.createTextArea(formData, id);
        } else if (formData.fieldType === this.fieldTypes.checkBox) {
            return this.createCheckBoxContainer(formData, id);
        } else if (formData.fieldType === this.fieldTypes.list) {
            return this.createListElementsContainer(formData, id);
        } else if (formData.fieldType === this.fieldTypes.radioButton) {
            return this.createRadioButtonContainer(formData, id);
        }
    }

    private createInputField(formData: any, id: number): InputFieldDto {
        return new InputFieldDto(
            id, 
            formData.fieldType, 
            formData.fieldName, 
            formData.isRequired, 
            formData.maxChars, 
            formData.minChars
        );
    }

    private createTextArea(formData: any, id: number): TextAreaDto {
        return new TextAreaDto(
            id, 
            formData.fieldType, 
            formData.fieldName, 
            formData.isRequired, 
            formData.maxChars, 
            formData.minChars
        );
    }

    private createCheckBoxContainer(formData: any, id: number): CheckBoxContainerDto {
        return new CheckBoxContainerDto(
            id, 
            formData.fieldType, 
            formData.fieldName, 
            formData.isRequired,
            formData.elements
        );
    }

    private createRadioButtonContainer(formData: any, id: number): RadioButtonContainerDto {
        return new RadioButtonContainerDto(
            id, 
            formData.fieldType, 
            formData.fieldName, 
            formData.isRequired,
            formData.elements
        );
    }

    private createListElementsContainer(formData: any, id: number): ListElementsContainerDto {
        return new ListElementsContainerDto(
            id, 
            formData.fieldType, 
            formData.fieldName, 
            formData.isRequired,
            formData.elements
        );
    }
}