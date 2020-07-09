package com.github.swapnil.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
	private Long postId;

	private String subredditName;

	private String postName;

	private String url;

	private String description;
}
