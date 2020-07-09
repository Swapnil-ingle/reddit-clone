package com.github.swapnil.reddit.exception;

public class SpringRedditException extends RuntimeException {
	private static final long serialVersionUID = 410596283227584651L;

	public SpringRedditException(String exception) {
		super(exception);
	}

	public SpringRedditException(String message, Exception e) {
		super(message, e);
	}
}
