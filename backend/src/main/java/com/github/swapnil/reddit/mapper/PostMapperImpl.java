package com.github.swapnil.reddit.mapper;

import com.github.swapnil.reddit.dto.PostRequest;
import com.github.swapnil.reddit.dto.PostResponse;
import com.github.swapnil.reddit.model.Post;
import com.github.swapnil.reddit.model.Subreddit;
import com.github.swapnil.reddit.model.User;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-12T16:47:17+0530",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
@Component
public class PostMapperImpl extends PostMapper {

    @Override
    public Post map(PostRequest postRequest, Subreddit subreddit, User user) {
        if ( postRequest == null && subreddit == null && user == null ) {
            return null;
        }

        Post post = new Post();

        if ( postRequest != null ) {
            post.setDescription( postRequest.getDescription() );
            post.setId( postRequest.getPostId() );
            post.setPostName( postRequest.getPostName() );
            post.setUrl( postRequest.getUrl() );
        }
        if ( subreddit != null ) {
            post.setSubreddit( subreddit );
            post.setUser( subreddit.getUser() );
        }
        post.setCreatedDate( java.time.Instant.now() );
        post.setVoteCount( Integer.parseInt( "0" ) );

        return post;
    }

    @Override
    public PostResponse mapToDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse postResponse = new PostResponse();

        String username = postUserUsername( post );
        if ( username != null ) {
            postResponse.setUserName( username );
        }
        String name = postSubredditName( post );
        if ( name != null ) {
            postResponse.setSubredditName( name );
        }
        postResponse.setId( post.getId() );
        postResponse.setPostName( post.getPostName() );
        postResponse.setUrl( post.getUrl() );
        postResponse.setDescription( post.getDescription() );
        postResponse.setVoteCount( post.getVoteCount() );

        postResponse.setDuration( getDuration(post) );
        postResponse.setDownVote( isPostDownVoted(post) );
        postResponse.setCommentCount( commentCount(post) );
        postResponse.setUpVote( isPostUpVoted(post) );

        return postResponse;
    }

    private String postUserUsername(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private String postSubredditName(Post post) {
        if ( post == null ) {
            return null;
        }
        Subreddit subreddit = post.getSubreddit();
        if ( subreddit == null ) {
            return null;
        }
        String name = subreddit.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
