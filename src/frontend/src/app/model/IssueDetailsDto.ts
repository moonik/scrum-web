import { UserProfileDto } from './UserProfileDto';
import { FieldsContentCollector } from './fields-content/FieldsContentCollector';
import { FieldContentDto } from './fields-content/FieldContentDto';
import { IssueComment } from './IssueComment';

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
    fieldsContentCollector: FieldsContentCollector = new FieldsContentCollector();
    fieldContents: Array<FieldContentDto>;
    comments: Array<IssueComment> = [];
}

