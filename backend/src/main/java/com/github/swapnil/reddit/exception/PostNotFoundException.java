package com.github.swapnil.reddit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends SpringRedditException {
	private static final long serialVersionUID = -7557706785126462490L;

	public PostNotFoundException(String id) {
		super(String.format("Post with id '%s' not found", id));
	}

	public PostNotFoundException(String id, Exception e) {
		super(String.format("Post with id '%s' not found", id), e);
	}
}
