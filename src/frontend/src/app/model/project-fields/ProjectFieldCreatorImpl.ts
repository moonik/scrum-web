import {CheckBoxContainerDto} from './CheckBoxContainerDto';
import {InputFieldDto} from './InputFieldDto';
import {ListElementsContainerDto} from './ListElementsContainerDto';
import {RadioButtonContainerDto} from './RadioButtonContainerDto';
import {TextAreaDto} from './TextAreaDto';
import {ProjectFieldsCollector} from './ProjectFieldsCollector';
import {Injectable} from '@angular/core';
import { FieldCreator } from '../../shared/field-creator';

import * as fieldTypes from '../../constants/field-type';
import { ProjectFieldDto } from '../../model/project-fields/ProjectFieldDto';

@Injectable()
export class ProjectFieldCreatorImpl implements FieldCreator<ProjectFieldDto> {

    private fieldTypes = fieldTypes.default;
    projectFieldsCollector: ProjectFieldsCollector = new ProjectFieldsCollector();

    constructor() {}

    createField(field: any): void {
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

    public createInputField(field: any): InputFieldDto {
        return new InputFieldDto(
            field.id,
            field.fieldType,
            field.fieldName,
            field.isRequired,
            field.maxCharacters
        );
    }

    public createTextArea(field: any): TextAreaDto {
        return new TextAreaDto(
            field.id,
            field.fieldType,
            field.fieldName,
            field.isRequired,
            field.maxCharacters
        );
    }

    public createCheckBoxContainer(field: any): CheckBoxContainerDto {
        return new CheckBoxContainerDto(
            field.id,
            field.fieldType,
            field.fieldName,
            field.isRequired,
            field.elements
        );
    }

    public createRadioButtonContainer(field: any): RadioButtonContainerDto {
        return new RadioButtonContainerDto(
            field.id,
            field.fieldType,
            field.fieldName,
            field.isRequired,
            field.elements
        );
    }

    public createListElementsContainer(field: any): ListElementsContainerDto {
        return new ListElementsContainerDto(
            field.id,
            field.fieldType,
            field.fieldName,
            field.isRequired,
            field.elements
        );
    }
}
