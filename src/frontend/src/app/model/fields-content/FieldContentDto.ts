export class FieldContentDto {
    projectFieldId: number;
    projectFieldName: string;

    constructor(projectFieldId: number, projectFieldName: string) {
        this.projectFieldId = projectFieldId;
        this.projectFieldName = projectFieldName;
    }
}

