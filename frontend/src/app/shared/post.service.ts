import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PostModel } from './post-model';
import { CreatePostPayload } from '../post/create-post/create-post.payload';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  constructor(private httpClient: HttpClient) { }

  getAllPosts(): Observable<Array<PostModel>> {
    return this.httpClient.get<Array<PostModel>>('http://localhost:8080/api/posts/');
  }

  getPost(id: number): Observable<PostModel> {
    return this.httpClient.get<PostModel>('http://localhost:8080/api/posts/' + id);
  }

  getAllPostsByUser(name: string): Observable<PostModel[]> {
    return this.httpClient.get<PostModel[]>('http://localhost:8080/api/posts/by-user/' + name);
  }

  createPost(postPayload: CreatePostPayload): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/posts/', postPayload);
  }

  getPostsBySubreddit(id: number) : Observable<Array<PostModel>> {
    return this.httpClient.get<Array<PostModel>>('http://localhost:8080/api/posts/by-subreddit/' + id);
  }
}
