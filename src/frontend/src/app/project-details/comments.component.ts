import {Component, OnInit, HostListener, Input} from '@angular/core';
import {IssueService} from '../issue/issue.service';
import {IssueComment} from '../model/IssueComment';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NotificationService} from '../shared/notification.service';

@Component({
  selector: 'app-issue-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./project-details.component.css'],
  providers: [IssueService]
})
export class CommentsComponent {

    @Input() comments = [];
    @Input() issue;
    commentForm: FormGroup;
    newComment: IssueComment = new IssueComment();
    hoveredCommentId: number;
    selectedComment: IssueComment = new IssueComment();
    oldContent: string;

    constructor(private issueService: IssueService, private fb: FormBuilder, private notificationService: NotificationService) {
        this.commentForm = fb.group({
            content: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]]
        });
    }

    addComment() {
        return this.issueService.addComment(this.issue.key, this.newComment)
          .subscribe(
            data => {
              this.comments.push(data);
              let content = 'New comment in task ' + this.issue.key + ': ' + this.newComment.content;
              this.sendNotification(content);
              this.newComment.content = '';
            });
    }

    deleteComment(comment: IssueComment) {
        return this.issueService.deleteComment(comment.id)
          .subscribe(
            () => {
              const index = this.comments.indexOf(comment);
              this.comments.splice(index, 1);
            }
          );
    }

    editComment(comment: IssueComment, commentId: number) {
        return this.issueService.editComment(commentId, comment)
          .subscribe();
    }

    getCurrentUser(): string {
        return localStorage.getItem('currentUser');
    }

    mouseEnter(comment: any) {
        this.hoveredCommentId = comment.id;
        comment.hover = true;
    }

    mouseLeave(comment: any) {
        comment.hover = false;
    }

    checkCommentLength(): boolean {
        return this.commentForm.controls.content.errors.minlength || this.commentForm.controls.content.errors.maxlength;
    }

    checkControl(name: string): boolean {
        return this.commentForm.controls[name].invalid &&
            (this.commentForm.controls[name].touched || this.commentForm.controls[name].dirty);
    }

    checkIfEdited(comment: any) {
        return this.oldContent !== comment.content;
    }

    clickInside($event: Event, comment: any) {
        $event.preventDefault();
        $event.stopPropagation();  // <- that will stop propagation on lower layers
        this.oldContent = comment.content;
        this.selectedComment = comment;
        this.selectedComment.editing = true;
    }

    @HostListener('document:click', ['$event']) clickedOutside($event) {
        this.selectedComment.editing = false;
        if (this.checkIfEdited(this.selectedComment)) {
          this.editComment(this.selectedComment, this.selectedComment.id);
        }
    }

    private sendNotification(content: string) {
        let users = this.issue.assignees.filter(a => a.username !== localStorage.getItem('currentUser'));
        if (this.checkReporter()) {
            users = users.concat(this.issue.reporter.username);
        }
        users.forEach(i => this.notificationService.sendNotification(i.username, content));
    }

    private checkReporter() {
        return !this.issue.assignees.includes(this.issue.reporter) &&
            this.issue.reporter !== localStorage.getItem('currentUser');
    }
}
