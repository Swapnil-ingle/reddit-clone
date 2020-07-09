package com.github.swapnil.reddit.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.github.swapnil.reddit.dto.SubredditDto;
import com.github.swapnil.reddit.model.Post;
import com.github.swapnil.reddit.model.Subreddit;
import com.github.swapnil.reddit.model.User;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
	@Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);

	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	Subreddit mapDtoToSubreddit(SubredditDto subredditDto, User user);

	default Integer mapPosts(List<Post> numberOfPosts) {
		return numberOfPosts.size();
	}
}
