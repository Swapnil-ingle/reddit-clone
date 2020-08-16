import { Component, OnInit, Input } from '@angular/core';
import { PostModel } from '../post-model';
import { faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { VotePayload } from './vote-payload';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { PostService } from '../post.service';
import { ToastrService } from 'ngx-toastr';
import { VoteService } from '../vote.service';
import { VoteType } from './vote-type';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {
  @Input() post: PostModel;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  upvoteColor: string;
  downvoteColor: string;
  votePayload: VotePayload;

  constructor(private voteService: VoteService,
    private authService: AuthService,
    private postService: PostService,
    private toastr: ToastrService) {
    // Initialized votePayload with undefined values
    this.votePayload = {
      type: undefined,
      postId: undefined
    };
  }

  ngOnInit(): void {
  }

  upvotePost() {
    this.votePayload.type = VoteType.UPVOTE;
    this.vote();
  }

  downvotePost() {
    this.votePayload.type = VoteType.DOWNVOTE;
    this.vote();
  }

  vote() {
    this.votePayload.postId = this.post.id;

    this.voteService.vote(this.votePayload).subscribe(() => {
      this.updateVoteDetails();
    }, error => {
      this.toastr.error(error.error);
      throwError(error);
    });
  }

  private updateVoteDetails() {
    this.postService.getPost(this.post.id).subscribe(post => {
      this.post = post;
    });
  }
}