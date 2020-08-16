### A letter to the audience
> Hello World, 
>
> I'm learning Angular 9.
> 
> This project is based on an Angular tutorial series I found on [freeCodeCamp.org](https://www.freecodecamp.org/). 
> 
> Please indulge yourself :D. 
> 
> Happy coding!
> 
> Swapnil I.

### About the project
I started this as a learning experiment in the Angular. This is me first time doing Angular.

The goal of the project is not JUST to finish the project following the tutorial series but rather learning the concepts while achieving something tangible in the process (*P.S: I hate non-project based learning*). To corroborate this, I'm also writing some blog(s) to solidify the underlying technology that I've used in the project (ex: [Adding Sign-up with Angular](https://swapnil-ingle.github.io/add-signup-with-angular), [GitHub blogs](https://swapnil-ingle.github.io/))

### Progress so far...
#### Backend
* The backend is completely implemented with all the REST-API endpoints and working fine.
* The backend was done in Java using Spring Boot.
* Libraries like Lombok and mapstruct were used to reduce boiler plate code.
* The user authentication is done using JWT token with 15 min expire/refresh token approach.
* The backend API documentation is done using Swagger.

#### Frontend
* User can sign-up, sign-in, log-out.
* Non-Registered users can view sub-reddits, post and comments but cannot post anything.
* Registered users can: 
> 1. Create sub-reddits.
> 2. Add posts under sub-reddits.
> 3. Add comments under posts.
> 4. Upvote/Downvote posts.

#### Interesting To-Do(s):
* Add option for user-profile picture.
* Add proper view/message for user who has signed-up but not yet verified email.

#### Latest Changes:
* Added: 1. Changed the nav-bar to not display Sign-Up/Login button when user is logged in. 2. Added user-
profile dropdown button instead. 3. Added user-profile button and page
* Added 'Subreddit-Overview' page.
* SI: 1. Bug fixes for voting module. 2. Header persistent issue fix
* Added: AuthGuard to prevent unauthorized access to certain modules
* Added logout functionality

#### Screenshot(s)
##### Reddit Homepage
![alt text](https://github.com/Swapnil-ingle/reddit-clone/blob/master/readme/imgs/reddit-home.png "Reddit HomePage")

##### Sub-Reddits Overview Page
![alt text](https://github.com/Swapnil-ingle/reddit-clone/blob/master/readme/imgs/reddit-subreddit-overview.png "Reddit HomePage")
