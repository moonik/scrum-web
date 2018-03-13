import { ProjectFieldDto } from '../model/project-fields/ProjectFieldDto';
import { CheckBoxContainerDto } from '../model/project-fields/CheckBoxContainerDto';
import { InputFieldDto } from '../model/project-fields/InputFieldDto';
import { ListElementsContainerDto } from '../model/project-fields/ListElementsContainerDto';
import { RadioButtonContainerDto } from '../model/project-fields/RadioButtonContainerDto';
import { TextAreaDto } from '../model/project-fields/TextAreaDto';
import { ProjectFieldsCollector } from '../model/project-fields/ProjectFieldsCollector';
import { Injectable } from '@angular/core';

import * as fieldTypes from '../constants/field-type';

@Injectable()
export class FieldCreator {

    private fieldTypes = fieldTypes.default;
    private projectFieldsCollector: ProjectFieldsCollector = new ProjectFieldsCollector();

    constructor(){}

    public createField(field: any): void {
        if (field.fieldType === this.fieldTypes.inputField) {
            this.projectFieldsCollector.inputFieldDtos.push(this.createInputField(field));
        } else if (field.fieldType === this.fieldTypes.textArea) {
            this.projectFieldsCollector.textAreaDtos.push(this.createTextArea(field));
        } else if (field.fieldType === this.fieldTypes.checkBox) {
            this.projectFieldsCollector.checkBoxContainerDtos.push(this.createCheckBoxContainer(field));
        } else if (field.fieldType === this.fieldTypes.list) {
            this.projectFieldsCollector.listElementsContainerDtos.push(this.createListElementsContainer(field));
        } else if (field.fieldType === this.fieldTypes.radioButton) {
            this.projectFieldsCollector.radioButtonContainerDtos.push(this.createRadioButtonContainer(field));
        }
    }

    private createInputField(field: any): InputFieldDto {
        return new InputFieldDto(
            field.id, 
            field.fieldType, 
            field.fieldName, 
            field.isRequired, 
            field.maxCharacters, 
            field.minCharacters
        );
    }

    private createTextArea(field: any): TextAreaDto {
        return new TextAreaDto(
            field.id, 
            field.fieldType, 
            field.fieldName, 
            field.isRequired, 
            field.maxCharacters, 
            field.minCharacters
        );
    }

    private createCheckBoxContainer(field: any): CheckBoxContainerDto {
        return new CheckBoxContainerDto(
            field.id, 
            field.fieldType, 
            field.fieldName, 
            field.isRequired,
            field.elements
        );
    }

    private createRadioButtonContainer(field: any): RadioButtonContainerDto {
        return new RadioButtonContainerDto(
            field.id, 
            field.fieldType, 
            field.fieldName, 
            field.isRequired,
            field.elements
        );
    }

    private createListElementsContainer(field: any): ListElementsContainerDto {
        return new ListElementsContainerDto(
            field.id, 
            field.fieldType, 
            field.fieldName, 
            field.isRequired,
            field.elements
        );
    }

    public getProjectFieldCollector():ProjectFieldsCollector {
        return this.projectFieldsCollector;
    }
}