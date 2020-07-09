package com.github.swapnil.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.swapnil.reddit.dto.PostRequest;
import com.github.swapnil.reddit.dto.PostResponse;
import com.github.swapnil.reddit.exception.PostNotFoundException;
import com.github.swapnil.reddit.exception.SubredditNotFoundException;
import com.github.swapnil.reddit.mapper.PostMapper;
import com.github.swapnil.reddit.model.Post;
import com.github.swapnil.reddit.model.Subreddit;
import com.github.swapnil.reddit.model.User;
import com.github.swapnil.reddit.repository.PostRepository;
import com.github.swapnil.reddit.repository.SubredditRepository;
import com.github.swapnil.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
	private final SubredditRepository subredditRepo;

	private final AuthService authSvc;

	private final PostMapper postMapper;

	private final PostRepository postRepo;

	private final UserRepository userRepo;

	public Post save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepo.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));

		User user = authSvc.getCurrentUser();
		Post post = postMapper.map(postRequest, subreddit, user);
		postRepo.save(post);
		return post;
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepo.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepo.findAll().stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		Subreddit subreddit = subredditRepo.findById(subredditId)
				.orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));

		List<Post> posts = postRepo.findAllBySubreddit(subreddit);
		return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

		List<Post> posts = postRepo.findByUser(user);
		return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
}
