package com.github.swapnil.reddit.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
	private Long id;

	private Long postId;

	private Instant createdDate;

	private String text;

	private String username;
}
