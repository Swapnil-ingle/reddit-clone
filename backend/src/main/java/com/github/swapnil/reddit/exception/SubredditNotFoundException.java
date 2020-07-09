package com.github.swapnil.reddit.exception;

public class SubredditNotFoundException extends SpringRedditException {
	private static final long serialVersionUID = -3846235915277549171L;

	public SubredditNotFoundException(String subredditName) {
		super(String.format("Subreddit (%s) not found", subredditName));
	}

	public SubredditNotFoundException(String subredditName, Exception e) {
		super(String.format("Subreddit (%s) not found", subredditName), e);
	}
}
