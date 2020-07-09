package com.github.swapnil.reddit.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.swapnil.reddit.dto.VoteDto;
import com.github.swapnil.reddit.exception.PostNotFoundException;
import com.github.swapnil.reddit.exception.SpringRedditException;
import com.github.swapnil.reddit.model.Post;
import com.github.swapnil.reddit.model.Vote;
import com.github.swapnil.reddit.model.VoteType;
import com.github.swapnil.reddit.repository.PostRepository;
import com.github.swapnil.reddit.repository.VoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {
	private final VoteRepository voteRepo;

	private final PostRepository postRepo;

	private final AuthService authSvc;

	@Transactional
	public void vote(VoteDto voteDto) {
		Post post = postRepo.findById(voteDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(String.valueOf(voteDto.getPostId())));

		Optional<Vote> voteByPostAndUser = voteRepo.findTopByPostAndUserOrderByIdDesc(post,
				authSvc.getCurrentUser());

		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getType())) {
			throw new SpringRedditException(String.format("You have already %sD this post", voteDto.getType()));
		}

		if (VoteType.UPVOTE.equals(voteDto.getType())) {
			Integer voteCount = post.getVoteCount();
			post.setVoteCount(voteCount == null ? 1 : voteCount + 1);
		} else {
			Integer voteCount = post.getVoteCount();
			post.setVoteCount(voteCount == null ? -1 : voteCount - 1);
		}

		voteRepo.save(mapToVote(voteDto, post));
		postRepo.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder().voteType(voteDto.getType()).post(post).user(authSvc.getCurrentUser()).build();
	}
}
