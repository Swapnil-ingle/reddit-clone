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
import com.github.swapnil.reddit.model.Vote;
import com.github.swapnil.reddit.model.VoteType;
import com.github.swapnil.reddit.repository.CommentRepository;
import com.github.swapnil.reddit.repository.VoteRepository;
import com.github.swapnil.reddit.service.AuthService;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private AuthService authService;

	@Autowired
	private VoteRepository voteRepo;

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
	@Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
	@Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
	public abstract PostResponse mapToDto(Post post);

	boolean isPostUpVoted(Post post) {
		return checkVoteType(post, VoteType.UPVOTE);
	}

	boolean isPostDownVoted(Post post) {
		return checkVoteType(post, VoteType.DOWNVOTE);
	}

	Integer commentCount(Post post) {
		return commentRepo.findByPost(post).size();
	}

	String getDuration(Post post) {
		return TimeAgo.Companion.using(post.getCreatedDate().toEpochMilli());
	}

	private boolean checkVoteType(Post post, VoteType voteType) {
		if (!authService.isLoggedIn()) {
			return false;
		}

		Vote voteByUser = voteRepo.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser()).orElse(null);
		if (voteByUser != null && voteByUser.getVoteType() != null) {
			return voteByUser.getVoteType().equals(voteType);
		}

		return false;
	}
}
