package com.github.swapnil.reddit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.swapnil.reddit.dto.VoteDto;
import com.github.swapnil.reddit.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
public class VoteController {
	private final VoteService voteSvc;
	
	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
		voteSvc.vote(voteDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
