
export class IssueDto {
    public id: number;
    public summary: string;
    public issueType: string;
    public priority: string;
    public assignees: Array<string>;
}