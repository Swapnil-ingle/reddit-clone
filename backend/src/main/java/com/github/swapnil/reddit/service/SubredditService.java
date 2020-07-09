package com.github.swapnil.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.swapnil.reddit.dto.SubredditDto;
import com.github.swapnil.reddit.exception.SpringRedditException;
import com.github.swapnil.reddit.mapper.SubredditMapper;
import com.github.swapnil.reddit.model.Subreddit;
import com.github.swapnil.reddit.model.User;
import com.github.swapnil.reddit.repository.SubredditRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {
	private final SubredditRepository subredditRepo;

	private final SubredditMapper subredditMapper;

	private final AuthService authSvc;

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		User user = authSvc.getCurrentUser();
		Subreddit saved = subredditRepo.save(subredditMapper.mapDtoToSubreddit(subredditDto, user));
		subredditDto.setId(saved.getId());

		return subredditDto;
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepo.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(Collectors.toList());
	}

	@Transactional
	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepo.findById(id)
				.orElseThrow(() -> new SpringRedditException("No subreddit found with given ID"));
		return subredditMapper.mapSubredditToDto(subreddit);
	}
}
