package com.github.swapnil.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.swapnil.reddit.dto.CommentsDto;
import com.github.swapnil.reddit.exception.PostNotFoundException;
import com.github.swapnil.reddit.mapper.CommentMapper;
import com.github.swapnil.reddit.model.Comment;
import com.github.swapnil.reddit.model.NotificationEmail;
import com.github.swapnil.reddit.model.Post;
import com.github.swapnil.reddit.model.User;
import com.github.swapnil.reddit.repository.CommentRepository;
import com.github.swapnil.reddit.repository.PostRepository;
import com.github.swapnil.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentsService {
	private static final String POST_URL = "";

	private final PostRepository postRepo;

	private final UserRepository userRepo;

	private final CommentRepository commentRepo;

	private final AuthService authSvc;

	private final CommentMapper commentMapper;

	private final MailService mailSvc;

	private final MailContentBuilder mailBuilder;

	public void save(CommentsDto commentsDto) {
		Post post = postRepo.findById(commentsDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));

		Comment comment = commentMapper.map(commentsDto, authSvc.getCurrentUser(), post);
		commentRepo.save(comment);

		String message = mailBuilder
				.build(post.getUser().getUsername() + " posted a comment on your post. " + POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailSvc.sendMail(
				new NotificationEmail(user.getUsername() + " commented on your post.", user.getEmail(), message));
	}

	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
		return commentRepo.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}

	public List<CommentsDto> getAllCommentsByUsername(String username) {
		User user = userRepo.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User with username '%s' not found", username)));
		return commentRepo.findByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}
}
