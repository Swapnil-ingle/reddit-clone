import { Component, OnInit, Input } from '@angular/core';
import { PostModel } from '../post-model';
import {faArrowUp, faArrowDown} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  upvoteColor: string;
  downvoteColor: string;
  @Input() post: PostModel;

  constructor() { }

  ngOnInit(): void {
  }

  upvotePost() {
    // TODO: Upvote Post
  }

  downvotePost() {
    // TODO: Downvote Post
  }
}
