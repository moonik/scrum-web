import { UserProfileDto } from './UserProfileDto';
import { FieldsContentCollector } from './fields-content/FieldsContentCollector';
import { FieldContentDto } from './fields-content/FieldContentDto';

export class IssueDetailsDto {
    id: number;
    key: string;
    summary: string;
    description: string;
    assignees: Array<any> = [];
    reporter: UserProfileDto;
    estimateTime: string;
    remainingTime: string;
    priority: string;
    issueType: string;
    createdDate: string;
    lastUpdate: string;
    projectFields: FieldsContentCollector = new FieldsContentCollector();
    fieldContents: Array<FieldContentDto> = [];
}
