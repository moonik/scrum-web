import {Component, OnInit, HostListener, Input} from '@angular/core';
import {IssueService} from '../issue/issue.service';
import {IssueComment} from '../model/IssueComment';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-issue-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./project-details.component.css'],
  providers: [IssueService]
})
export class CommentsComponent {

    @Input() comments = [];
    @Input() issueKey;
    commentForm: FormGroup;
    newComment: IssueComment = new IssueComment();
    hoveredCommentId: number;
    selectedComment: IssueComment = new IssueComment();
    oldContent: string;

    constructor(private issueService: IssueService, private fb: FormBuilder) {
        this.commentForm = fb.group({
            content: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]]
        });
    }

    addComment() {
        return this.issueService.addComment(this.issueKey, this.newComment)
          .subscribe(
            data => {
              this.comments.push(data);
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
        return this.commentForm.controls[name].invalid && (this.commentForm.controls[name].touched || this.commentForm.controls[name].dirty);
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
}