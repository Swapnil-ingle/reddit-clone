package com.github.swapnil.reddit.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.github.swapnil.reddit.exception.SpringRedditException;

@ControllerAdvice(basePackages = "com.github.swapnil.reddit")
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { SpringRedditException.class })
	protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
		if (ex instanceof SpringRedditException) {
			final SpringRedditException restEx = (SpringRedditException) ex;
			return handleExceptionInternal(ex, restEx.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
					request);
		}

		return handleExceptionInternal(ex, "Internal server error", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
				request);
	}

	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}

		return new ResponseEntity<>(body, headers, status);
	}
}