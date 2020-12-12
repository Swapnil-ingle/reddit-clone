package com.github.swapnil.reddit.mapper;

import com.github.swapnil.reddit.dto.SubredditDto;
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
public class SubredditMapperImpl implements SubredditMapper {

    @Override
    public SubredditDto mapSubredditToDto(Subreddit subreddit) {
        if ( subreddit == null ) {
            return null;
        }

        SubredditDto subredditDto = new SubredditDto();

        subredditDto.setId( subreddit.getId() );
        subredditDto.setName( subreddit.getName() );
        subredditDto.setDescription( subreddit.getDescription() );

        subredditDto.setNumberOfPosts( mapPosts(subreddit.getPosts()) );

        return subredditDto;
    }

    @Override
    public Subreddit mapDtoToSubreddit(SubredditDto subredditDto, User user) {
        if ( subredditDto == null && user == null ) {
            return null;
        }

        Subreddit subreddit = new Subreddit();

        if ( subredditDto != null ) {
            subreddit.setName( subredditDto.getName() );
            subreddit.setDescription( subredditDto.getDescription() );
        }
        if ( user != null ) {
            subreddit.setUser( user );
        }
        subreddit.setCreatedDate( java.time.Instant.now() );

        return subreddit;
    }
}
