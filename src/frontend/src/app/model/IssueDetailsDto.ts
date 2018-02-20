import { UserProfileDto } from './UserProfileDto';
import { FieldsContentCollector } from './fields-content/FieldsContentCollector';
import { FieldContentDto } from './fields-content/FieldContentDto';

export class IssueDetailsDto {
    public id: number;
    public key: string;
    public summary: string;
    public description: string;
    public assignees: Array<any> = [];
    public reporter: UserProfileDto;
    public estimateTime: string;
    public remainingTime: string;
    public priority: string;
    public issueType: string;
    public createdDate: string;
    public lastUpdate: string;
    public projectFields: FieldsContentCollector = new FieldsContentCollector();
    public fieldContents: Array<FieldContentDto> = [];
}
