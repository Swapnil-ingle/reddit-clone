package com.github.swapnil.reddit.dto;

import com.github.swapnil.reddit.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
	private VoteType type;

	private Long postId;
}
