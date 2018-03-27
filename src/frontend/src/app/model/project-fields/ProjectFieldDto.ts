export class ProjectFieldDto {
    public id: number;
    public fieldType: string;
    public fieldName: string;
    public isRequired: boolean;

    constructor(id: number, fieldType: string, fieldName: string, isRequired: boolean) {
        this.id = id;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.isRequired = isRequired;
    }
}