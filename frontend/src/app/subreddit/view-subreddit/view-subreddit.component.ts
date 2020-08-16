import { Component, OnInit } from '@angular/core';
import { SubredditService } from '../subreddit.service';
import { ActivatedRoute } from '@angular/router';
import { PostModel } from 'src/app/shared/post-model';
import { PostService } from 'src/app/shared/post.service';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-view-subreddit',
  templateUrl: './view-subreddit.component.html',
  styleUrls: ['./view-subreddit.component.css']
})
export class ViewSubredditComponent implements OnInit {
  id: number;
  posts: Array<PostModel>;
  subredditName: string;

  constructor(private postService: PostService, private activatedRoute: ActivatedRoute) {
    this.id = this.activatedRoute.snapshot.params.id;

    postService.getPostsBySubreddit(this.id).subscribe(data => {
      this.posts = data;
    }, error => {
      throwError(error);
    });
  }

  ngOnInit(): void {
  }
}
