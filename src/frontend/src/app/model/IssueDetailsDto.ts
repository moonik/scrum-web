import { UserProfileDto } from './UserProfileDto';
import { FieldsContentCollector } from './FieldsContentCollector';
import { FieldContentDto } from './FieldContentDto';

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
    projectFields: FieldsContentCollector = new FieldsContentCollector();
    fieldContents: Array<FieldContentDto> = [];
}