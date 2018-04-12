export interface FieldCreator<T> {
    createField(field: any): void;
    createInputField(field: any): T;
    createTextArea(field: any): T;
    createCheckBoxContainer(field: any): T;
    createRadioButtonContainer(field: any): T;
    createListElementsContainer(field: any): T;
}
