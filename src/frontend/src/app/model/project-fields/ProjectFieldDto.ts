export class ProjectFieldDto {
    id: number;
    fieldType: string;
    fieldName: string;
    isRequired: boolean;

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean) {
        this.id = id;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.isRequired = isRequired;
    }
}
