package com.github.swapnil.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.swapnil.reddit.dto.PostRequest;
import com.github.swapnil.reddit.dto.PostResponse;
import com.github.swapnil.reddit.model.Post;
import com.github.swapnil.reddit.model.Subreddit;
import com.github.swapnil.reddit.model.User;
import com.github.swapnil.reddit.repository.CommentRepository;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
	@Autowired
	private CommentRepository commentRepo;

	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "voteCount", constant = "0")
	@Mapping(target = "subreddit", source = "subreddit")
	@Mapping(target = "id", source = "postRequest.postId")
	public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "userName", source = "user.username")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	public abstract PostResponse mapToDto(Post post);

	Integer commentCount(Post post) {
		return commentRepo.findByPost(post).size();
	}

	String getDuration(Post post) {
		return TimeAgo.Companion.using(post.getCreatedDate().toEpochMilli());
	}
}
