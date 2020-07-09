package com.github.swapnil.reddit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.swapnil.reddit.dto.CommentsDto;
import com.github.swapnil.reddit.service.CommentsService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
	private final CommentsService commentsSvc;

	@GetMapping("/by-postId/{postId}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentsSvc.getAllCommentsForPost(postId));
	}

	@GetMapping("/by-user/{username}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsByUsername(@PathVariable String username) {
		return ResponseEntity.status(HttpStatus.OK).body(commentsSvc.getAllCommentsByUsername(username));
	}

	@PostMapping
	public ResponseEntity<Void> createComments(@RequestBody CommentsDto commentsDto) {
		commentsSvc.save(commentsDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
